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
package gplx.xowa.addons.wikis.ctgs.edits; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*;
import gplx.dbs.*;
import gplx.xowa.parsers.*;
import gplx.xowa.addons.wikis.ctgs.dbs.*;
import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.addons.wikis.directorys.specials.items.bldrs.*;
public class Xoctg_edit_mgr {
	public void Update(Xowe_wiki wiki, byte[] ttl_bry, int page_id, byte[] old_text, byte[] new_text) {
//			// get page
//			Xoa_ttl ttl = wiki.Ttl_parse(ttl_bry);
//			Xoae_page wpg = Xoae_page.New_edit(wiki, ttl);
//			wpg.Db().Page().Id_(page_id);
//			wpg.Db().Text().Text_bry_(old_text);
//
//			// parse page
//			Xop_ctx pctx = wiki.Parser_mgr().Ctx().Clear_all();
//			wiki.Parser_mgr().Parse(wpg, true);
//
//			// get cat_link_db
//			Xoax_ctg_addon cat_addon = Xoax_ctg_addon.Get(wiki);
//			Xowd_cat_core_tbl cat_core_tbl = Xodb_cat_db_.Get_cat_core_or_fail(wiki.Data__core_mgr());
//
//			// get cat_link_conn
//			Xowd_page_itm tmp_page = new Xowd_page_itm();
//			wiki.Data__core_mgr().Db__core().Tbl__page().Select_by_id(tmp_page, page_id);
//			Db_conn cat_link_conn = wiki.Data__core_mgr().Props().Layout_text().Tid_is_lot()
//				? wiki.Data__core_mgr().Dbs__get_by_id_or_fail(tmp_page.Cat_db_id()).Conn()
//				: wiki.Data__core_mgr().Db__core().Conn();
//			Xowd_cat_link_tbl cat_link_tbl = new Xowd_cat_link_tbl(cat_link_conn, Bool_.N);
//
//			// delete old
//			int cat_len = wpg.Wtxt().Ctgs__len();
//			for (int i = 0; i < cat_len; i++) {
//				Xoa_ttl ctg_ttl = wpg.Wtxt().Ctgs__get_at(i);
//				wiki.Data__core_mgr().Db__core().Tbl__page().Select_by_ttl(tmp_page, ctg_ttl);
////				int ctg_id = tmp_page.Cat_db_id();
////				Object data = cat_core_tbl.Select_by_cat_id(ctg_id);
////				data.cat_pages--
////				cat_core_Tbl.Save(data);
//			}
////			cat_link_tbl.Delete_by_page_id(page_id);
//
//			// insert new
//			wpg.Db().Text().Text_bry_(new_text);
//			wiki.Parser_mgr().Parse(wpg, true);
//
//			cat_len = wpg.Wtxt().Ctgs__len();
//			Xodb_wiki_db wiki_db_mgr = Xodb_wiki_db.Make(Xodb_wiki_db_tid.Tid__core, wiki.Data__core_mgr().Db__core().Url());
//			for (int i = 0; i < cat_len; i++) {
//				Xoa_ttl ctg_ttl = wpg.Wtxt().Ctgs__get_at(i);
//				boolean exists = wiki.Data__core_mgr().Db__core().Tbl__page().Select_by_ttl(tmp_page, ctg_ttl);
//				int ctg_id = tmp_page.Cat_db_id();
//				if (!exists) {
//					ctg_id = Xopg_db_mgr.Create(wiki_db_mgr, gplx.xowa.wikis.nss.Xow_ns_.Tid__category, ctg_ttl.Page_db(), Bry_.Empty);					
//				}				
////				Object data = cat_core_tbl.Select_by_cat_id(ctg_id);
////				data.cat_pages++
////				cat_core_Tbl.Save(data);
////				cat_link_tbl.Insert_by_page_id(page_id, ctg_id);
//			}
	}
}
