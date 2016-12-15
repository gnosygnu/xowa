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
	private final    Object thread_lock = new Object();
	private final    Xowe_wiki wiki;
	private final    Ordered_hash cache = Ordered_hash_.New_bry();	// NOTE: wiki titles are not case-sensitive when ns is "1st-letter" (EX: w:earth an w:Earth); in these cases, two entries will be stored
	private final    Hash_adp ifexists_cache = Hash_adp_bry.cs();
	private final    List_adp deleted = List_adp_.New();
	private Xow_page_cache_wkr load_wkr;
	public Xow_page_cache(Xowe_wiki wiki) {this.wiki = wiki;}
	public Xow_page_cache_wkr Load_wkr() {return load_wkr;}
	public void Load_wkr_(Xow_page_cache_wkr v) {this.load_wkr = v;}
	public byte[] Get_or_load_as_src(Xoa_ttl ttl) {
		Xow_page_cache_itm rv = Get_or_load_as_itm(ttl);
		return rv == null ? null : rv.Wtxt__direct();
	}
	public boolean Get_ifexist_by_mem(byte[] ifexists_key) {
		Xow_page_cache_itm ifexists_itm = (Xow_page_cache_itm)ifexists_cache.Get_by(ifexists_key);
		if		(ifexists_itm == Xow_page_cache_itm.Missing)
			return false;
		else if (ifexists_itm != null) 
			return true;

		// check page_cache
		ifexists_itm = (Xow_page_cache_itm)cache.Get_by(ifexists_key);
		if		(ifexists_itm == Xow_page_cache_itm.Missing)
			return false;
		else if (ifexists_itm != null)
			return true;

		return false;
	}
	public boolean Load_ifexist(Xoa_ttl ttl) {
		// check ifexists_cache
		byte[] ifexists_key = ttl.Full_db();
		Xow_page_cache_itm ifexists_itm = null;
		
		// do load
		if (load_wkr != null) {
			// load_wkr
			byte[] page_text = load_wkr.Get_page_or_null(ifexists_key);
			ifexists_itm = page_text == null ? Xow_page_cache_itm.Missing : new Xow_page_cache_itm(false, ttl, Bry_.Empty, Bry_.Empty);
		}
		else {
			// page_tbl
			gplx.xowa.wikis.data.tbls.Xowd_page_itm page_itm = new gplx.xowa.wikis.data.tbls.Xowd_page_itm();
			wiki.Db_mgr().Load_mgr().Load_by_ttl(page_itm, ttl.Ns(), ttl.Page_db());
			// wiki.Data__core_mgr().Db__core().Tbl__page().Select_by_ttl(page_itm, ttl);
			ifexists_itm = page_itm.Exists() ? new Xow_page_cache_itm(false, ttl, Bry_.Empty, Bry_.Empty) : Xow_page_cache_itm.Missing;				
		}

		// add to ifexists_cache only (not page_cache)
		ifexists_cache.Add(ifexists_key, ifexists_itm);
		return ifexists_itm != Xow_page_cache_itm.Missing;
	}
	public void Add(byte[] ttl_full_db, Xow_page_cache_itm itm) {
		cache.Add(ttl_full_db, itm);
	}
	private void Add_safe(byte[] ttl_full_db, Xow_page_cache_itm itm) {
		synchronized (thread_lock) {	// LOCK:high-usage;DATE:2016-07-14
			if (!cache.Has(ttl_full_db)) {	// check again that itm is not in cache; note that this is necessary as cache.Get is not in "synchronized" block (for performance reasons); DATE:2016-12-12
				cache.Add(ttl_full_db, itm);
			}
		}
	}
	public Xow_page_cache_itm Get_or_load_as_itm(Xoa_ttl ttl) {
		byte[] ttl_full_db = ttl.Full_db();
		Xow_page_cache_itm rv = (Xow_page_cache_itm)cache.Get_by(ttl_full_db);
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
			rv = new Xow_page_cache_itm(false, page_ttl, page_text, page_redirect_from);
			Add_safe(ttl_full_db, rv);
		}
		else {
			Add_safe(ttl_full_db, Xow_page_cache_itm.Missing);
			rv = null;
		}
		return rv;
	}
	public Xow_page_cache_itm Get_or_load_as_itm_2(Xoa_ttl ttl) {	// NOTE: same as Get_or_load_as_itm, but handles redirects to missing pages; DATE:2016-05-02
		byte[] ttl_full_db = ttl.Full_db();
		Xow_page_cache_itm rv = (Xow_page_cache_itm)cache.Get_by(ttl_full_db);
		if		(rv == Xow_page_cache_itm.Missing) return null;
		else if (rv == null) {
			Xoae_page page = wiki.Data_mgr().Load_page_by_ttl(ttl);	// NOTE: do not call Db_mgr.Load_page; need to handle redirects
			if (	page.Db().Page().Exists()				// page exists
				||	page.Redirect_trail().Itms__len() > 0 ) {		// page redirects to missing page; note that page.Missing == true and page.Redirected_src() != null; PAGE: en.w:Shah_Rukh_Khan; DATE:2016-05-02
				rv = new Xow_page_cache_itm(false, page.Ttl(), page.Db().Text().Text_bry(), page.Redirect_trail().Itms__get_wtxt_at_0th_or_null());
				Add_safe(ttl_full_db, rv);
			}
			else {
				Add_safe(ttl_full_db, Xow_page_cache_itm.Missing);
				rv = null;
			}
		}
		return rv;
	}
	public void Free_mem(boolean clear_permanent_itms) {
		synchronized (thread_lock) {	// LOCK:app-level; DATE:2016-07-06
			if (clear_permanent_itms) {
				cache.Clear();
				ifexists_cache.Clear();
			}
			else {
				int len = cache.Count();
				for (int i = 0; i < len; i++) {
					Xow_page_cache_itm itm = (Xow_page_cache_itm)cache.Get_at(i);
					if (!itm.Cache_permanently())
						deleted.Add(itm);
				}
				len = deleted.Len();
				for (int i = 0; i < len; i++) {
					Xow_page_cache_itm itm = (Xow_page_cache_itm)deleted.Get_at(i);
					if (itm.Ttl() != null)	// missing is null
						cache.Del(itm.Ttl().Full_db());
				}
				deleted.Clear();
			}
		}
	}
}
