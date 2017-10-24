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
package gplx.xowa.addons.wikis.htmls.css.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.htmls.*; import gplx.xowa.addons.wikis.htmls.css.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
import gplx.xowa.addons.wikis.htmls.css.mgrs.*;
public class Xob_css_cmd implements Xob_cmd {
	private final    Xob_bldr bldr; private final    Xowe_wiki wiki; private final    Gfo_usr_dlg usr_dlg;
	private Io_url css_dir; private String css_key;
	public Xob_css_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.bldr = bldr; this.wiki = wiki; this.usr_dlg = wiki.Appe().Usr_dlg();}
	public String Cmd_key() {return Xob_cmd_keys.Key_text_css;}
	public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return null;}
	public void Cmd_init(Xob_bldr bldr) {
		if (css_dir == null) css_dir = wiki.App().Fsys_mgr().Wiki_css_dir(wiki.Domain_str());	// EX: /xowa/user/anonymous/wiki/en.wikipedia.org
		if (css_key == null) css_key = Xowd_css_core_mgr.Key_default;
	}
	public void Cmd_run() {
		usr_dlg.Plog_many("", "", Cmd_key() + ":bgn;");			
		bldr.App().Html__css_installer().Install(wiki, null);				// download from wmf
		usr_dlg.Plog_many("", "", Cmd_key() + ":css_dir; dir=~{0}", css_dir.Raw());
		wiki.Init_db_mgr();	// NOTE: must follow Install b/c Init_assert also calls Install; else will download any css from db
		Xow_db_file core_db = wiki.Db_mgr_as_sql().Core_data_mgr().Db__core();
		core_db.Conn().Txn_bgn("bldr__css");
		core_db.Tbl__css_core().Create_tbl();
		core_db.Tbl__css_file().Create_tbl();
		gplx.xowa.addons.wikis.htmls.css.mgrs.Xowd_css_core_mgr.Set(core_db.Tbl__css_core(), core_db.Tbl__css_file(), css_dir, css_key);
		core_db.Tbl__cfg().Upsert_yn(Xowd_cfg_key_.Grp__wiki_schema, Xow_db_file_schema_props.Key__tbl_css_core, Bool_.Y);
		core_db.Conn().Txn_end();
		usr_dlg.Plog_many("", "", Cmd_key() + ":end;");
	}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_end() {}
	public void Cmd_term() {}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_css_dir_))		css_dir = m.ReadIoUrl("v");
		else if	(ctx.Match(k, Invk_css_key_))		css_key = m.ReadStr("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_css_dir_ = "css_dir_", Invk_css_key_ = "css_key_";
}
