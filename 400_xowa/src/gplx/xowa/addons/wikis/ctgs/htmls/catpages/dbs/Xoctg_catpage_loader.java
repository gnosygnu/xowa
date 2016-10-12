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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.addons.wikis.ctgs.dbs.*;
import gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.urls.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.dbs.*;
public class Xoctg_catpage_loader {
	public Xoctg_catpage_ctg Load_ctg_or_null(Xow_wiki wiki, Xoctg_catpage_mgr catpage_mgr, Xoctg_catpage_url cat_url, Xoa_ttl cat_ttl, int limit) {
		// get cat_id from page_tbl
		Xow_db_mgr db_mgr = wiki.Data__core_mgr();
		Xowd_page_tbl page_tbl = db_mgr.Db__core().Tbl__page();
		Xowd_page_itm page_itm = page_tbl.Select_by_ttl_as_itm_or_null(cat_ttl);
		if (page_itm == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "category does not exist in page table; ttl=~{0}", cat_ttl.Full_db());
			return null;
		}
		int cat_id = page_itm.Id();

		// get cat_core_itm from cat_core_tbl
		Xowd_cat_core_tbl cat_core_tbl = Xodb_cat_db_.Get_cat_core_or_fail(db_mgr);
		Xowd_category_itm cat_core_itm = cat_core_tbl.Select(cat_id);
		if (cat_core_itm == Xowd_category_itm.Null) {
			Gfo_usr_dlg_.Instance.Log_many("", "", "category does not exist in cat_core table; ttl=~{0}", cat_ttl.Full_db());	// NOTE: this is not rare as Category pages can be created as deliberately empty, or as redirects; fr.w:Catégorie:Utilisateur_hess-4; DATE:2016-09-12
			return null;
		}

		// load itms from cat_link_db for each grp_tid
		Xoctg_catpage_ctg rv = new Xoctg_catpage_ctg(cat_id, cat_ttl.Page_txt());
		for (byte grp_tid = Xoa_ctg_mgr.Tid__subc; grp_tid < Xoa_ctg_mgr.Tid___max; ++grp_tid) {
			Xoctg_catpage_grp grp = rv.Grp_by_tid(grp_tid);				
			grp.Count_all_(cat_core_itm.Count_by_tid(grp_tid)); // set total counts per grp based on cat_core_itm;

			// init url_vars
			boolean url_is_from = cat_url.Grp_fwds()[grp_tid];		// EX: "pagefrom=A"; "pageuntil=B"
			byte[] url_sortkey = cat_url.Grp_keys()[grp_tid];

			// set prev / next props to dflt values
			if (url_is_from) {					// url is from; EX: from=A
				if (Bry_.Len_eq_0(url_sortkey))	// no sortkey specified for group, so group always starts with 1st itm -> disable previous link
					grp.Prev_disable_(true);
			}
			else {								// url is until; EX: until=A
				grp.Prev_disable_(true);		// default prev link to disabled; loader will load 201 items and set to false if 201st found
				grp.Next_sortkey_(url_sortkey);	// next sortkey is always sortkey of until url arg;
			}

			// load links
			Xoctg_catlink_loader loader = new Xoctg_catlink_loader();
			if (cat_core_itm.File_idx() == -1)	// v3 or v4: loop over all cat_link dbs {
				loader.Make_attach_mgr__v3_v4	(db_mgr, cat_core_tbl.Conn());
			else								// v2: use cat_link_db
				loader.Make_attach_mgr__v2		(db_mgr, cat_core_itm.File_idx());
			loader.Run(rv, wiki, catpage_mgr, page_tbl, cat_id, grp_tid, url_is_from, url_sortkey, limit);
		}
		return rv;
	}
}
