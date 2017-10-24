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
package gplx.xowa.bldrs.cmds.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.xowa.bldrs.wkrs.*; import gplx.xowa.bldrs.xmls.*;
import gplx.xowa.wikis.dbs.*; import gplx.xowa.wikis.data.tbls.*;
public class Xob_xml_dumper_cmd implements Xob_cmd {
	private final    Xowe_wiki wiki; private final    Gfo_usr_dlg usr_dlg;
	private final    Xob_xml_dumper xml_dumper = new Xob_xml_dumper(); private int commit_interval = 1000;
	private Io_url dump_url;
	public Xob_xml_dumper_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.wiki = wiki; this.usr_dlg = wiki.Appe().Usr_dlg();}
	public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return null;}
	public String Cmd_key() {return Xob_cmd_keys.Key_util_xml_dump;}
	public void Cmd_init(Xob_bldr bldr) {
		dump_url = wiki.Fsys_mgr().Root_dir().GenSubFil(wiki.Domain_str() + "-dump.xml");
		Io_mgr.Instance.DeleteFil(dump_url);
	}
	public void Cmd_run() {
		usr_dlg.Plog_many("", "", Cmd_key() + ":bgn;");
		String wiki_abrv = "";
		String main_page = String_.Format("https://{0}/wiki/{1}", wiki.Domain_str(), String_.new_u8(wiki.Props().Main_page()));
		String ns_case = "first-letter";	// TODO_OLD:
		xml_dumper.Write_root_bgn(wiki.Ns_mgr(), wiki.Domain_itm(), wiki_abrv, main_page, ns_case, "XOWA " + Xoa_app_.Version);
		Xodb_page_rdr page_rdr = wiki.Db_mgr().Load_mgr().Get_page_rdr(wiki);
		Xowd_page_itm page = new Xowd_page_itm();
		int page_count = 0;
		try {
			while (page_rdr.Move_next()) {
				page_rdr.Read(page);
				page.Ttl_(wiki.Ttl_parse(page.Ns_id(), page.Ttl_page_db()));
				xml_dumper.Write_page(page);
				if ((++page_count % commit_interval) == 0) Commit();
			}
		}
		catch (Exception e) {throw Err_.new_exc(e, "xo", "xml_dumper failed");}
		finally {page_rdr.Rls();}
		xml_dumper.Write_root_end();
		this.Commit();
		usr_dlg.Plog_many("", "", Cmd_key() + ":end;");
	}
	private void Commit() {
		Io_mgr.Instance.AppendFilStr(dump_url, xml_dumper.Bld_str());
	}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_end() {}
	public void Cmd_term() {}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_commit_interval_))		commit_interval = m.ReadInt("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_commit_interval_ = "commit_interval_";
}
