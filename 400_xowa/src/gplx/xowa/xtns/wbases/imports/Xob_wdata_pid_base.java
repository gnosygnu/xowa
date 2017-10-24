/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.wbases.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.langs.jsons.*; import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.parsers.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;
public abstract class Xob_wdata_pid_base extends Xob_itm_dump_base implements Xob_page_wkr, Gfo_invk {
	private Json_parser parser;
	public Xob_wdata_pid_base Ctor(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki); return this;}
	public abstract String Page_wkr__key();
	public abstract void Pid_bgn();
	public abstract void Pid_add(byte[] src_lang, byte[] src_ttl, byte[] trg_ttl);
	public abstract void Pid_datatype(byte[] pid, byte[] datatype_bry);
	public abstract void Pid_end();
	public void Page_wkr__bgn() {
		this.Init_dump(this.Page_wkr__key(), wiki.Tdb_fsys_mgr().Site_dir().GenSubDir_nest("data", "pid"));	// NOTE: must pass in correct make_dir in order to delete earlier version (else make_dirs will append)
		parser = bldr.App().Wiki_mgr().Wdata_mgr().Jdoc_parser();
		this.Pid_bgn();
	}
	public void Page_wkr__run(Xowd_page_itm page) {
		if (page.Ns_id() != Wdata_wiki_mgr.Ns_property) return;
		Json_doc jdoc = parser.Parse(page.Text()); 
		if (jdoc == null) {
			bldr.Usr_dlg().Warn_many(GRP_KEY, "json.invalid", "json is invalid: ns=~{0} id=~{1}", page.Ns_id(), String_.new_u8(page.Ttl_page_db()));
			return;
		}
		Parse_jdoc(jdoc);
	}
	public void Page_wkr__run_cleanup() {}
	public void Parse_jdoc(Json_doc jdoc) {
		Wdata_doc_parser wdoc_parser = app.Wiki_mgr().Wdata_mgr().Wdoc_parser(jdoc);
		byte[] qid = wdoc_parser.Parse_qid(jdoc);

		// add datatype
		byte[] datatype = jdoc.Root_nde().Get_as_bry(Wdata_dict_mainsnak.Itm__datatype.Key_str());
		this.Pid_datatype(qid, datatype);

		// add langs
		Ordered_hash list = wdoc_parser.Parse_langvals(qid, jdoc, Bool_.Y);
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Wdata_langtext_itm label = (Wdata_langtext_itm)list.Get_at(i);
			this.Pid_add(label.Lang(), label.Text(), qid);
		}
	}
	public void Page_wkr__end() {this.Pid_end();}
	static final String GRP_KEY = "xowa.wdata.pid_wkr";
}
