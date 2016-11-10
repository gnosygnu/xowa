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
	private final    Xowe_wiki wiki;
	private final    Hash_adp_bry cache = Hash_adp_bry.cs();	// NOTE: wiki titles are not case-sensitive when ns is "1st-letter" (EX: w:earth an w:Earth); in these cases, two entries will be stored
	private Xow_page_cache_wkr load_wkr;
	public Xow_page_cache(Xowe_wiki wiki) {this.wiki = wiki;}
	public Xow_page_cache_wkr Load_wkr() {return load_wkr;}
	public void Load_wkr_(Xow_page_cache_wkr v) {this.load_wkr = v;}
	public byte[] Get_or_load_as_src(Xoa_ttl ttl) {
		Xow_page_cache_itm rv = Get_or_load_as_itm(ttl);
		return rv == null ? null : rv.Wtxt__direct();
	}
	public void Add(byte[] ttl_full_db, Xow_page_cache_itm itm) {
		cache.Add(ttl_full_db, itm);
	}
	public Xow_page_cache_itm Get_or_load_as_itm(Xoa_ttl ttl) {
		byte[] ttl_full_db = ttl.Full_db();
		Xow_page_cache_itm rv = (Xow_page_cache_itm)cache.Get_by_bry(ttl_full_db);
		if		(rv == Xow_page_cache_itm.Missing) {
			return null;
		}
		else if (rv == null) {
			return Load_page(ttl, ttl_full_db);
		}
		return rv;
	}
	private Xow_page_cache_itm Load_page(Xoa_ttl ttl, byte[] ttl_full_db) {
		Xow_page_cache_itm rv = null;
		Xoa_ttl page_ttl = ttl;
		boolean page_exists = false;
		byte[] page_text = null;
		byte[] page_redirect_from = null;
		if (load_wkr != null) {
			page_text = load_wkr.Get_page_or_null(ttl_full_db);
			page_exists = page_text != null;
		}
		if (page_text == null) {
			Xoae_page page = wiki.Data_mgr().Load_page_by_ttl(ttl);	// NOTE: do not call Db_mgr.Load_page; need to handle redirects
			page_ttl = page.Ttl();
			page_text = page.Db().Text().Text_bry();
			page_exists = page.Db().Page().Exists();
			page_redirect_from = page.Redirect_trail().Itms__get_wtxt_at_0th_or_null();
		}
		if (page_exists) {
			rv = new Xow_page_cache_itm(page_ttl, page_text, page_redirect_from);
			synchronized (this) {	// LOCK:high-usage;DATE:2016-07-14
				cache.Add_bry_obj(ttl_full_db, rv);
			}
		}
		else {
			synchronized (this) {	// LOCK:high-usage;DATE:2016-07-14
				cache.Add_bry_obj(ttl_full_db, Xow_page_cache_itm.Missing);
				rv = null;
			}
		}
		return rv;
	}
	public Xow_page_cache_itm Get_or_load_as_itm_2(Xoa_ttl ttl) {	// NOTE: same as Get_or_load_as_itm, but handles redirects to missing pages; DATE:2016-05-02
		byte[] ttl_full_db = ttl.Full_db();
		Xow_page_cache_itm rv = (Xow_page_cache_itm)cache.Get_by_bry(ttl_full_db);
		if		(rv == Xow_page_cache_itm.Missing) return null;
		else if (rv == null) {
			Xoae_page page = wiki.Data_mgr().Load_page_by_ttl(ttl);	// NOTE: do not call Db_mgr.Load_page; need to handle redirects
			if (	page.Db().Page().Exists()				// page exists
				||	page.Redirect_trail().Itms__len() > 0 ) {		// page redirects to missing page; note that page.Missing == true and page.Redirected_src() != null; PAGE: en.w:Shah_Rukh_Khan; DATE:2016-05-02
				rv = new Xow_page_cache_itm(page.Ttl(), page.Db().Text().Text_bry(), page.Redirect_trail().Itms__get_wtxt_at_0th_or_null());
				synchronized (this) {	// LOCK:high-usage;DATE:2016-07-14
					cache.Add_bry_obj(ttl_full_db, rv);
				}
			}
			else {
				synchronized (this) {	// LOCK:high-usage;DATE:2016-07-14
					cache.Add_bry_obj(ttl_full_db, Xow_page_cache_itm.Missing);
					rv = null;
				}
			}
		}
		return rv;
	}
	public void Free_mem_all() {
		synchronized (this) {	// LOCK:app-level; DATE:2016-07-06
			cache.Clear();
		}
	}
}
