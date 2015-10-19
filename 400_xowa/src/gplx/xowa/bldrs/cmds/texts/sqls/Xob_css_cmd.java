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
package gplx.xowa.bldrs.cmds.texts.sqls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.texts.*;
import gplx.xowa.bldrs.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*; import gplx.xowa.htmls.css.*;
public class Xob_css_cmd implements Xob_cmd {
	private final Xob_bldr bldr; private final Xowe_wiki wiki; private final Gfo_usr_dlg usr_dlg;
	private Io_url css_dir; private String css_key;
	public Xob_css_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.bldr = bldr; this.wiki = wiki; this.usr_dlg = wiki.Appe().Usr_dlg();}
	public String Cmd_key() {return Xob_cmd_keys.Key_text_css;}
	public void Cmd_init(Xob_bldr bldr) {
		if (css_dir == null) css_dir = wiki.App().Fsys_mgr().Wiki_css_dir(wiki.Domain_str());	// EX: /xowa/user/anonymous/wiki/en.wikipedia.org
		if (css_key == null) css_key = Xowd_css_core_mgr.Key_default;
	}
	public void Cmd_run() {
		usr_dlg.Plog_many("", "", Cmd_key() + ":bgn;");			
		bldr.App().Html__css_installer().Install(wiki, null);				// download from wmf
		usr_dlg.Plog_many("", "", Cmd_key() + ":css_dir; dir=~{0}", css_dir.Raw());
		wiki.Init_db_mgr();	// NOTE: must follow Install b/c Init_assert also calls Install; else will download any css from db
		Xowd_db_file core_db = wiki.Db_mgr_as_sql().Core_data_mgr().Db__core();
		core_db.Conn().Txn_bgn("bldr__css");
		core_db.Tbl__css_core().Create_tbl();
		core_db.Tbl__css_file().Create_tbl();
		gplx.xowa.htmls.css.Xowd_css_core_mgr.Set(core_db.Tbl__css_core(), core_db.Tbl__css_file(), css_dir, css_key);
		core_db.Tbl__cfg().Upsert_yn(Xow_cfg_consts.Grp__wiki_schema, Xowd_db_file_schema_props.Key__tbl_css_core, Bool_.Y);
		core_db.Conn().Txn_end();
		usr_dlg.Plog_many("", "", Cmd_key() + ":end;");
	}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_end() {}
	public void Cmd_term() {}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_css_dir_))		css_dir = m.ReadIoUrl("v");
		else if	(ctx.Match(k, Invk_css_key_))		css_key = m.ReadStr("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_css_dir_ = "css_dir_", Invk_css_key_ = "css_key_";
}
