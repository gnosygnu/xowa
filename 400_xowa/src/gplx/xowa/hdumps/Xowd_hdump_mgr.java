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
package gplx.xowa.hdumps; import gplx.*; import gplx.xowa.*;
import gplx.intl.*; import gplx.dbs.*;
import gplx.xowa.hdumps.core.*; import gplx.xowa.hdumps.loads.*; import gplx.xowa.hdumps.htmls.*; import gplx.xowa.apps.fsys.*;
public class Xowd_hdump_mgr {
	private final Xoav_app app; private final Xowv_wiki wiki; private final Xowv_db_mgr wiki_db_mgr;
	private Xodb_page dbpg = new Xodb_page();
	private Hdump_load_mgr load_mgr = new Hdump_load_mgr();
	private Hdump_html_body html_body = new Hdump_html_body();
	public Xowd_hdump_mgr(Xoav_app app, Xowv_wiki wiki) {
		this.app = app; this.wiki = wiki; this.wiki_db_mgr = wiki.Db_mgr();
		html_body.Init_by_app(app.Usr_dlg(), app.Fsys_mgr(), app.Utl_encoder_fsys());
	}	
	public void Load(Hdump_page rv, byte[] ttl_bry) {
		Xoa_ttl ttl = wiki.Ttl_parse(ttl_bry);
		wiki_db_mgr.Tbl_mgr().Tbl__page().Select_by_ttl(dbpg, app.Db_mgr().Get(wiki_db_mgr.Key__core()), ttl.Ns(), ttl.Page_db());
		if (dbpg.Redirect_id() != -1) Select_by_id(rv, dbpg);
		if (dbpg.Html_db_id() == -1) return;	// should return "not found" message
		load_mgr.Load2(rv, app.Db_mgr().Get(wiki_db_mgr.Key_by_idx(dbpg.Html_db_id())), dbpg.Id(), ttl);
		Bry_bfr bfr = app.Utl_bfr_mkr().Get_m001();
		html_body.Init_by_page(wiki.Domain_bry(), rv).Write(bfr);
		rv.Page_body_(bfr.Mkr_rls().Xto_bry_and_clear());
	}
	private void Select_by_id(Hdump_page hpg, Xodb_page dbpg) {
		int redirect_count = 0;
		while (redirect_count < 5) {
			int redirect_id = dbpg.Redirect_id();
			wiki_db_mgr.Tbl_mgr().Tbl__page().Select_by_id(dbpg, app.Db_mgr().Get(wiki_db_mgr.Key__core()), redirect_id);
			if (redirect_id == -1) break;
		}
	}
}
