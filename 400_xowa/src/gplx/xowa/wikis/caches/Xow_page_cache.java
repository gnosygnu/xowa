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
public class Xow_page_cache {
	private Xow_wiki wiki;
	private Hash_adp_bry cache = Hash_adp_bry.cs_();	// NOTE: wiki titles are not case-sensitive when ns is "1st-letter" (EX: w:earth an w:Earth); in these cases, two entries will be stored
	public Xow_page_cache(Xow_wiki wiki) {this.wiki = wiki;}
	public byte[] Get_or_load_as_src(Xoa_ttl ttl) {
		Xow_page_cache_itm rv = Get_or_load_as_itm(ttl);
		return rv == null ? null : rv.Src();
	}
	public Xow_page_cache_itm Get_or_load_as_itm(Xoa_ttl ttl) {
		byte[] ttl_full_db = ttl.Full_db();
		Xow_page_cache_itm rv = (Xow_page_cache_itm)cache.Get_by_bry(ttl_full_db);
		if (rv == null) {
			Xoa_page page = wiki.Data_mgr().Get_page(ttl, true);	// NOTE: do not call Db_mgr.Load_page; need to handle redirects
			if (!page.Missing()) {
				rv = new Xow_page_cache_itm(page.Ttl(), page.Data_raw());
				cache.Add_bry_obj(ttl_full_db, rv);
			}
		}
		return rv;
	}
	public void Free_mem_all() {cache.Clear();}
}
