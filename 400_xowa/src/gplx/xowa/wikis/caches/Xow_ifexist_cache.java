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
import gplx.core.caches.*;
import gplx.xowa.wikis.data.tbls.*;
public class Xow_ifexist_cache {
	private final    Xowe_wiki wiki;
	private final    Xow_page_cache page_cache;
	private final    Gfo_cache_mgr cache_mgr = new Gfo_cache_mgr().Max_size_(64 * Io_mgr.Len_mb).Reduce_by_(32 * Io_mgr.Len_mb);
	private final    Hash_adp ns_loaded_hash = Hash_adp_.New();		
	private final    Xow_ifexist_itm itm__exists = new Xow_ifexist_itm(Bool_.Y), itm__missing = new Xow_ifexist_itm(Bool_.N);
	public Xow_ifexist_cache(Xowe_wiki wiki, Xow_page_cache page_cache) {
		this.wiki = wiki;
		this.page_cache = page_cache;
	}
	public Xow_ifexist_cache Cache_sizes_(int max, int reduce) {
		cache_mgr.Max_size_(max).Reduce_by_(reduce);
		return this;
	}
	public void Load_wkr_(Xow_page_cache_wkr v) {this.load_wkr = v;} private Xow_page_cache_wkr load_wkr;
	public void Clear() {
		cache_mgr.Clear();
		ns_loaded_hash.Clear();
	}
	public void Add(Xoa_ttl ttl) {
		byte[] key = ttl.Full_db();
		cache_mgr.Add(key, itm__exists, key.length);
	}
	public void Add_ns_loaded(int... ns_ids) {
		for (int ns_id : ns_ids)
			ns_loaded_hash.Add(ns_id, ns_id);
	}
	public byte Get_by_mem(Xoa_ttl ttl) {
		byte[] ttl_full_db = ttl.Full_db();

		// check cache_mgr
		Xow_ifexist_itm found = (Xow_ifexist_itm)cache_mgr.Get_by_key(ttl_full_db);
		if (found != null) return found.Exists() ? Bool_.Y_byte : Bool_.N_byte;

		// check ns_loaded cache (xomp only); if exists, return false, since all pages in ns are loaded, and still not found
		if (ns_loaded_hash.Has(ttl.Ns().Id())) return Bool_.N_byte;

		// check page_cache since full page + text could be loaded there
		Xow_page_cache_itm itm = (Xow_page_cache_itm)page_cache.Get_or_null(ttl_full_db);
		if		(itm == Xow_page_cache_itm.Missing)
			return Bool_.N_byte;
		else if (itm != null)
			return Bool_.Y_byte;

		return Bool_.__byte;
	}
	public boolean Load(Xoa_ttl ttl) {
		byte[] key = ttl.Full_db();
		Xow_ifexist_itm itm = null;
		// gplx.core.consoles.Console_adp__sys.Instance.Write_str("ifexist_cache:" + String_.new_u8(key));

		if (load_wkr != null) {
			// load_wkr; should call ifexist method, but for now, load entire page
			byte[] page_text = load_wkr.Get_page_or_null(key);
			itm = page_text == null ? itm__missing : itm__exists;
		}
		else {
			// page_tbl
			Xowd_page_itm page_itm = new Xowd_page_itm();
			wiki.Db_mgr().Load_mgr().Load_by_ttl(page_itm, ttl.Ns(), ttl.Page_db());
			itm = page_itm.Exists() ? itm__exists : itm__missing;
		}

		// add
		cache_mgr.Add(key, itm, key.length);
		return itm == itm__exists;
	}
}
class Xow_ifexist_itm implements Rls_able {		
	public Xow_ifexist_itm(boolean exists) {this.exists = exists;}
	public boolean Exists() {return exists;} private final    boolean exists;
	public void Rls() {}		
}
