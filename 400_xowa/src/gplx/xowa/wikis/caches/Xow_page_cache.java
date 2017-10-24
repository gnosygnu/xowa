/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.wikis.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
public class Xow_page_cache {
	private final    Object thread_lock = new Object();
	private final    Xowe_wiki wiki;
	private final    Ordered_hash cache = Ordered_hash_.New_bry();	// NOTE: wiki titles are not case-sensitive when ns is "1st-letter" (EX: w:earth an w:Earth); in these cases, two entries will be stored
	private final    List_adp deleted = List_adp_.New();		
	public Xow_page_cache(Xowe_wiki wiki) {this.wiki = wiki;}
	public void Load_wkr_(Xow_page_cache_wkr v) {this.load_wkr = v;} private Xow_page_cache_wkr load_wkr;
	public Xow_page_cache_itm Get_or_null(byte[] ttl_full_db) {return (Xow_page_cache_itm)cache.Get_by(ttl_full_db);}
	public byte[] Get_or_load_as_src(Xoa_ttl ttl) {
		Xow_page_cache_itm rv = Get_or_load_as_itm(ttl);
		return rv == null ? null : rv.Wtxt__direct();
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
	public void Del(byte[] ttl_full_db) {
		cache.Del(ttl_full_db);
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
		// gplx.core.consoles.Console_adp__sys.Instance.Write_str("page_cache:" + String_.new_u8(ttl_full_db));
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
			}
			else {
				// gather non-permanent items
				int len = cache.Count();
				for (int i = 0; i < len; i++) {
					Xow_page_cache_itm itm = (Xow_page_cache_itm)cache.Get_at(i);
					if (!itm.Cache_permanently())
						deleted.Add(itm);
				}

				// remove non-permanent items
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
