/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.xtns.wdatas.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.core.ios.*;
import gplx.langs.jsons.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.texts.sqls.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.apps.apis.xowa.bldrs.imports.*;
import gplx.xowa.xtns.wdatas.core.*; import gplx.xowa.xtns.wdatas.parsers.*;	
class Xob_wbase_json_dump_db {
	private final    Gfo_usr_dlg usr_dlg; private final    Xoae_app app; private final    Xowe_wiki wiki; private final    Xob_bldr bldr;
	private final    Json_parser json_parser;
	private final    Xob_wdata_pid_sql pid_cmd = new Xob_wdata_pid_sql(); private final    Xob_wdata_qid_sql qid_cmd = new Xob_wdata_qid_sql();
	private Xowd_page_tbl page_tbl;
	private Xob_ns_to_db_mgr ns_to_db_mgr; 
	private DateAdp page_modified_on;
	private Xowd_db_mgr db_mgr;
	private Xowd_page_tbl page_core_tbl;
	private Io_stream_zip_mgr text_zip_mgr; private byte text_zip_tid;
	private Xow_ns_mgr ns_mgr;
	public Xob_wbase_json_dump_db(Xob_bldr bldr, Xowe_wiki wiki) {
		this.app = bldr.App(); this.usr_dlg = app.Usr_dlg(); this.wiki = wiki; this.bldr = bldr;
		this.json_parser = bldr.App().Wiki_mgr().Wdata_mgr().Jdoc_parser();
		this.ns_mgr = wiki.Ns_mgr();
	}
	public void Parse_bgn(long src_fil_len, String src_fil_name) {
		Xowe_wiki_.Create(wiki, src_fil_len, src_fil_name);
		this.db_mgr = wiki.Data__core_mgr();
		this.page_tbl = db_mgr.Tbl__page();
		pid_cmd.Cmd_ctor(bldr, wiki); qid_cmd.Cmd_ctor(bldr, wiki);
		wiki.Ns_mgr().Add_defaults();
		wiki.Ns_mgr().Add_new(Wdata_wiki_mgr.Ns_property, Wdata_wiki_mgr.Ns_property_name);
		wiki.Ns_mgr().Init();
		Xoapi_import import_cfg = app.Api_root().Bldr().Wiki().Import();
		this.ns_to_db_mgr = new Xob_ns_to_db_mgr(new Xob_ns_to_db_wkr__text(), db_mgr, import_cfg.Text_db_max());
		this.text_zip_mgr = Xoa_app_.Utl__zip_mgr(); text_zip_tid = import_cfg.Zip_tid_text();
		byte[] ns_file_map = import_cfg.New_ns_file_map(src_fil_len);
		Xob_ns_file_itm.Init_ns_bldr_data(Xowd_db_file_.Tid_text, wiki.Ns_mgr(), ns_file_map);
		this.page_modified_on = DateAdp_.Now();
		this.page_core_tbl = db_mgr.Tbl__page();
		page_tbl.Insert_bgn();
		qid_cmd.Wkr_bgn(bldr);
		pid_cmd.Pid_bgn();
	}
	private int page_id = 0, page_count_main = 0;
	public void Parse_cmd(byte[] json_bry) {
		Json_doc jdoc = json_parser.Parse(json_bry); 
		if (jdoc == null)	{usr_dlg.Warn_many("", "", "wbase.json_dump:json is invalid: json=~{0}", json_bry); return;}
		byte[] id = jdoc.Get_val_as_bry_or(id_key, null);
		if (id == null)		{usr_dlg.Warn_many("", "", "wbase.json_dump:id is invalid: json=~{0}", json_bry); return;}
		boolean jdoc_is_qid = Bry_.Has_at_bgn(id, Byte_ascii.Ltr_Q, 0);
		Xow_ns ns = jdoc_is_qid ? ns_mgr.Ns_main() : ns_mgr.Ids_get_or_null(Wdata_wiki_mgr.Ns_property);
		int random_int = ns.Count() + 1; ns.Count_(random_int);
		byte[] json_zip = text_zip_mgr.Zip(text_zip_tid, json_bry);
		Xowd_db_file text_db = ns_to_db_mgr.Get_by_ns(ns.Bldr_data(), json_zip.length);
		db_mgr.Create_page(page_core_tbl, text_db.Tbl__text(), ++page_id, ns.Id(), id, Bool_.N, page_modified_on, json_zip, json_bry.length, random_int, text_db.Id(), -1);
		if (jdoc_is_qid) {
			qid_cmd.Parse_jdoc(jdoc);
			++page_count_main;
		}
		else
			pid_cmd.Parse_jdoc(jdoc);
	}
	public void Parse_end() {
		page_tbl.Insert_end();
		page_tbl.Create_index();
		qid_cmd.Qid_end();
		pid_cmd.Pid_end();
		ns_to_db_mgr.Rls_all();
		Xowd_db_file db_core = db_mgr.Db__core();
		db_core.Tbl__site_stats().Update(page_count_main, page_id, ns_mgr.Ns_file().Count());	// save page stats
		db_core.Tbl__ns().Insert(ns_mgr);															// save ns
		db_mgr.Tbl__cfg().Insert_str(Xow_cfg_consts.Grp__wiki_init, Xow_cfg_consts.Key__init__modified_latest, page_modified_on.XtoStr_fmt(DateAdp_.Fmt_iso8561_date_time));
	}
	private static final    byte[] id_key = Bry_.new_a7("id");
}
