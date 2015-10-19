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
package gplx.xowa.wikis.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.xowa.wikis.xwikis.sitelinks.*;
public class Xow_cache_mgr {
	private Xowe_wiki wiki;
	public Xow_cache_mgr(Xowe_wiki wiki) {
		this.wiki = wiki;
		page_cache = new Xow_page_cache(wiki);
		defn_cache = new Xow_defn_cache(wiki.Lang());
		lst_cache = new Xow_defn_cache(wiki.Lang());
	}
	public Hash_adp Tmpl_result_cache() {return tmpl_result_cache;} private Hash_adp tmpl_result_cache = Hash_adp_bry.cs();
	public Xow_page_cache Page_cache() {return page_cache;} private Xow_page_cache page_cache;
	public Xow_defn_cache Defn_cache() {return defn_cache;} private Xow_defn_cache defn_cache;
	public Xow_defn_cache Lst_cache() {return lst_cache;} private Xow_defn_cache lst_cache;
	public KeyVal[] Scrib_lang_names() {
		if (scrib_lang_names == null) {
			List_adp list = List_adp_.new_();
			Xoa_sitelink_itm_mgr itm_mgr = wiki.App().Xwiki_mgr__sitelink_mgr().Itm_mgr();
			int len = itm_mgr.Len();
			for (int i = 0; i < len; ++i) {
				Xoa_sitelink_itm itm = itm_mgr.Get_at(i);
				KeyVal kv = KeyVal_.new_(String_.new_u8(itm.Key()), String_.new_u8(itm.Name()));
				list.Add(kv);
			}
			scrib_lang_names = (KeyVal[])list.To_ary(KeyVal.class);
		}
		return scrib_lang_names;
	}
	public void Free_mem_all() {
		tmpl_result_cache.Clear();
		defn_cache.Free_mem_all();
		page_cache.Free_mem_all();
		lst_cache.Free_mem_all();
		scrib_lang_names = null;
	}
	private static KeyVal[] scrib_lang_names;
}
