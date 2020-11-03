/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.wbases.imports.json;

import gplx.Bool_;
import gplx.Bry_;
import gplx.Byte_ascii;
import gplx.DateAdp;
import gplx.DateAdp_;
import gplx.Datetime_now;
import gplx.Gfo_usr_dlg;
import gplx.core.ios.Io_stream_zip_mgr;
import gplx.langs.jsons.Json_doc;
import gplx.langs.jsons.Json_parser;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.Xowe_wiki_;
import gplx.xowa.bldrs.Xob_bldr;
import gplx.xowa.bldrs.Xob_ns_to_db_mgr;
import gplx.xowa.bldrs.Xobldr_cfg;
import gplx.xowa.bldrs.cmds.Xob_ns_file_itm;
import gplx.xowa.bldrs.cmds.texts.sqls.Xob_ns_to_db_wkr__text;
import gplx.xowa.wikis.data.Xow_db_file;
import gplx.xowa.wikis.data.Xow_db_file_;
import gplx.xowa.wikis.data.Xow_db_mgr;
import gplx.xowa.wikis.data.Xowd_cfg_key_;
import gplx.xowa.wikis.data.tbls.Xowd_page_tbl;
import gplx.xowa.wikis.nss.Xow_ns;
import gplx.xowa.wikis.nss.Xow_ns_mgr;
import gplx.xowa.xtns.wbases.Wdata_wiki_mgr;
import gplx.xowa.xtns.wbases.imports.Xob_wdata_pid;
import gplx.xowa.xtns.wbases.imports.Xob_wdata_qid;

class Xowb_json_dump_db {
	private final Xoae_app app; private final Gfo_usr_dlg usr_dlg; private final Xowe_wiki wiki; private final Xob_bldr bldr;
	private final Json_parser json_parser = new Json_parser();
	private final Xob_wdata_pid pid_cmd; private final Xob_wdata_qid qid_cmd;
	private Xow_ns_mgr ns_mgr; private Xow_db_mgr db_mgr; 
	private Xowd_page_tbl page_tbl; private Xob_ns_to_db_mgr ns_to_db_mgr; 
	private Io_stream_zip_mgr text_zip_mgr; private byte text_zip_tid;
	private DateAdp page_modified_on;
	private int page_id = 0, page_count_main = 0;
	public Xowb_json_dump_db(Xob_bldr bldr, Xowe_wiki wiki) {
		this.app = bldr.App(); this.usr_dlg = app.Usr_dlg(); this.wiki = wiki; this.bldr = bldr;
		this.ns_mgr = wiki.Ns_mgr();
		this.pid_cmd = new Xob_wdata_pid(wiki.Data__core_mgr().Db__wbase().Conn());
		this.qid_cmd = new Xob_wdata_qid(wiki.Data__core_mgr().Db__wbase().Conn());
	}
	public void Parse_all_bgn(long src_fil_len, String src_fil_name) {
		// load wiki
		Xowe_wiki_.Create(wiki, src_fil_len, src_fil_name);
		this.db_mgr = wiki.Data__core_mgr();
		this.page_tbl = db_mgr.Tbl__page();
		pid_cmd.Cmd_ctor(bldr, wiki); qid_cmd.Cmd_ctor(bldr, wiki);

		// create ns_mgr
		wiki.Ns_mgr().Add_defaults();
		wiki.Ns_mgr().Add_new(Wdata_wiki_mgr.Ns_property, Wdata_wiki_mgr.Ns_property_name);
		wiki.Ns_mgr().Init();

		// init ns_map
		this.ns_to_db_mgr = new Xob_ns_to_db_mgr(new Xob_ns_to_db_wkr__text(), db_mgr, Xobldr_cfg.Max_size__text(app));
		byte[] ns_file_map = Xobldr_cfg.New_ns_file_map(app, src_fil_len);
		Xob_ns_file_itm.Init_ns_bldr_data(Xow_db_file_.Tid__text, wiki.Ns_mgr(), ns_file_map);

		// start import
		this.text_zip_mgr = wiki.Utl__zip_mgr();
		this.text_zip_tid = Xobldr_cfg.Zip_mode__text(app);
		this.page_modified_on = Datetime_now.Get();
		page_tbl.Insert_bgn();
		qid_cmd.Page_wkr__bgn();
		pid_cmd.Pid__bgn();
	}
	public void Parse_doc(byte[] json_bry) {
		// parse to jdoc
		Json_doc jdoc = json_parser.Parse(json_bry); 
		if (jdoc == null)	{usr_dlg.Warn_many("", "", "wbase.json_dump:json is invalid: json=~{0}", json_bry); return;}

		// extract xid
		byte[] id = jdoc.Get_val_as_bry_or(Bry__id_key, null);
		if (id == null)		{usr_dlg.Warn_many("", "", "wbase.json_dump:id is invalid: json=~{0}", json_bry); return;}
		boolean jdoc_is_qid = Bry_.Has_at_bgn(id, Byte_ascii.Ltr_Q, 0);
		Xow_ns ns = jdoc_is_qid ? ns_mgr.Ns_main() : ns_mgr.Ids_get_or_null(Wdata_wiki_mgr.Ns_property);

		// create page entry
		int random_int = ns.Count() + 1; ns.Count_(random_int);
		byte[] json_zip = text_zip_mgr.Zip(text_zip_tid, json_bry);
		Xow_db_file text_db = ns_to_db_mgr.Get_by_ns(ns.Bldr_data(), json_zip.length);
		db_mgr.Create_page(page_tbl, text_db.Tbl__text(), ++page_id, ns.Id(), id, Bool_.N, page_modified_on, json_zip, json_bry.length, random_int, text_db.Id(), -1);

		// insert text
		if (jdoc_is_qid) {
			qid_cmd.Qid__run(jdoc);
			++page_count_main;
		}
		else
			pid_cmd.Pid__run(jdoc);
	}
	public void Parse_all_end() {
		page_tbl.Insert_end();
		page_tbl.Create_idx();
		qid_cmd.Qid__end();
		pid_cmd.Pid__end();
		ns_to_db_mgr.Rls_all();

		// cleanup core
		Xow_db_file db_core = db_mgr.Db__core();
		db_core.Tbl__site_stats().Update(page_count_main, page_id, ns_mgr.Ns_file().Count());	// save page stats
		db_core.Tbl__ns().Insert(ns_mgr);														// save ns
		db_mgr.Tbl__cfg().Insert_str(Xowd_cfg_key_.Grp__wiki_init, Xowd_cfg_key_.Key__init__modified_latest, page_modified_on.XtoStr_fmt(DateAdp_.Fmt_iso8561_date_time));
	}
	private static final byte[] Bry__id_key = Bry_.new_a7("id");
}
