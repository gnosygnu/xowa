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
package gplx.xowa.dbs.hdumps; import gplx.*; import gplx.xowa.*; import gplx.xowa.dbs.*;
import gplx.dbs.*;
import gplx.xowa.dbs.*; import gplx.xowa.dbs.hdumps.saves.*; import gplx.xowa.pages.*;
import gplx.xowa.html.*; import gplx.xowa.gui.*; 
public class Xodb_html_mgr implements GfoInvkAble {
	private Xodb_file hdump_db_file;
	public Xodb_html_mgr() {
		save_mgr = db_mgr.Save_mgr();
	}
	public boolean Enabled() {return enabled;} public void Enabled_(boolean v) {enabled = v;} private boolean enabled;
	public Hdump_db_mgr Db_mgr() {return db_mgr;} private Hdump_db_mgr db_mgr = new Hdump_db_mgr();
	@gplx.Internal protected Hdump_save_mgr Save_mgr() {return save_mgr;} private Hdump_save_mgr save_mgr;
	public void Write(Bry_bfr bfr, Xow_wiki wiki, Xoa_page page) {
		page.File_queue().Clear();	// need to reset uid to 0;
		Xoh_page_wtr_wkr wkr = wiki.Html_mgr().Page_wtr_mgr().Wkr(Xopg_view_mode.Tid_read);
		wkr.Write_body(bfr, Xoh_wtr_ctx.Hdump, page);
		page.Hdump_data().Body_(bfr.XtoAryAndClear());
	}
	public void Save(Xoa_page page) {
		if (!enabled) return;
		if (hdump_db_file == null) hdump_db_file = Hdump_db_file_init(this, page);
		Xow_wiki wiki = page.Wiki();
		Bry_bfr tmp_bfr = wiki.Utl_bry_bfr_mkr().Get_m001();
		this.Write(tmp_bfr, wiki, page);
		save_mgr.Update(page);
		tmp_bfr.Mkr_rls();
	}		
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_enabled))				return Yn.Xto_str(enabled);
		else if	(ctx.Match(k, Invk_enabled_))				enabled = m.ReadYn("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static Xodb_file Hdump_db_file_init(Xodb_html_mgr html_mgr, Xoa_page page) {
		Xow_wiki wiki = page.Wiki();
		Xodb_mgr_sql db_mgr_as_sql = wiki.Db_mgr_as_sql();
		Xodb_file rv = db_mgr_as_sql.Fsys_mgr().Get_tid_root(Xodb_file_tid.Tid_html);
		if (rv == null) rv = Hdump_db_mgr_setup.Setup(db_mgr_as_sql);
		html_mgr.Db_mgr().Text_tbl().Provider_(rv.Provider());
		return rv;
	}
	private static final String Invk_enabled = "enabled", Invk_enabled_ = "enabled_";
}
