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
import gplx.xowa.langs.cfgs.*;
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
			Cfg_nde_root root = wiki.Appe().Lang_mgr().Groups();
			int len = root.Root_len();
			for (int i = 0; i < len; i++) {
				Cfg_nde_obj nde = root.Root_get_at(i);
				Scrib_lang_names_grps(list, nde);
			}
			scrib_lang_names = (KeyVal[])list.To_ary(KeyVal.class);
		}
		return scrib_lang_names;
	}	private static KeyVal[] scrib_lang_names;
	private void Scrib_lang_names_grps(List_adp list, Cfg_nde_obj nde) {
		int len = nde.Nde_subs_len();
		for (int i = 0; i < len; i++) {
			Cfg_nde_obj lang_nde = nde.Nde_subs_get_at(i);
			if (lang_nde.Nde_typ_is_grp())
				Scrib_lang_names_grps(list, lang_nde);
			else {
				Xoac_lang_itm lang_itm = (Xoac_lang_itm)lang_nde;
				KeyVal kv = KeyVal_.new_(String_.new_u8(lang_itm.Key_bry()), String_.new_u8(lang_itm.Local_name_bry()));
				list.Add(kv);
			}
		}
	}
	public void Free_mem_all() {
		tmpl_result_cache.Clear();
		defn_cache.Free_mem_all();
		page_cache.Free_mem_all();
		lst_cache.Free_mem_all();
		scrib_lang_names = null;
	}
}
