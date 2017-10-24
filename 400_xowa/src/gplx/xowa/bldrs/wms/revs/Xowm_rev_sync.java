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
package gplx.xowa.bldrs.wms.revs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wms.*;
import gplx.xowa.bldrs.wms.*;
class Xowm_rev_sync {
	private final    Ordered_hash cur_hash = Ordered_hash_.New_bry(), new_hash = Ordered_hash_.New_bry();
	private final    List_adp del_list = List_adp_.New();
	public int Batch_size() {return batch_size;} public void Batch_size_(int v) {batch_size = v;} private int batch_size = 50;
	public Xowm_rev_wkr__meta Wkr__cur_meta() {return wkr__cur_meta;} private Xowm_rev_wkr__meta wkr__cur_meta;
	public Xowm_rev_wkr__meta Wkr__new_meta() {return wkr__new_meta;} private Xowm_rev_wkr__meta wkr__new_meta;
	public Xowm_rev_wkr__text Wkr__new_text() {return wkr__new_text;} private Xowm_rev_wkr__text wkr__new_text;
	public void Init(Xowm_rev_wkr__meta wkr__new_meta, Xowm_rev_wkr__meta wkr__cur_meta, Xowm_rev_wkr__text wkr__new_text) {
		this.wkr__cur_meta = wkr__cur_meta;
		this.wkr__new_meta = wkr__new_meta;
		this.wkr__new_text = wkr__new_text;
	}
	public void Exec(String domain_str, Xoa_ttl[] ttls_ary) {
		synchronized (new_hash) {
			Xowm_rev_sync_utl.Build_itms(cur_hash, ttls_ary);
			Xowm_rev_sync_utl.Build_itms(new_hash, ttls_ary);
			int len = new_hash.Count();
			for (int i = 0; i < len; i += batch_size)
				Exec_batch(domain_str, i, len);
		}
	}
	private void Exec_batch(String domain_str, int itms_bgn, int itms_len) {
		int itms_end = itms_bgn + batch_size; if (itms_end > itms_len) itms_end = itms_len;
		wkr__cur_meta.Fetch_meta(domain_str, cur_hash, itms_bgn, itms_end);
		wkr__new_meta.Fetch_meta(domain_str, new_hash, itms_bgn, itms_end);
		Xowm_rev_sync_utl.Remove_unchanged(cur_hash, new_hash, del_list, itms_bgn, itms_end);
		wkr__new_text.Fetch_text(new_hash, itms_bgn, itms_end);
		/*
		loop (changed texts) {
			parse page
		}
		loop (changed texts) {
			get other ref
			get files
		}
		loop (changed texts) {
			update category
			update search			
		}
		*/
	}
}
class Xowm_rev_sync_utl {
	public static void Build_itms(Ordered_hash hash, Xoa_ttl[] ttls_ary) {
		int len = ttls_ary.length;
		for (int i = 0; i < len; ++i) {
			Xoa_ttl ttl = ttls_ary[i];
			byte[] key = ttl.Full_db();
			if (hash.Has(key)) continue;	// ignore dupes
			hash.Add(key, new Wmapi_itm__pge().Init_ttl(ttl.Ns().Id(), ttl.Page_db()));
		}
	}
	public static void Remove_unchanged(Ordered_hash cur_hash, Ordered_hash new_hash, List_adp del_list, int bgn, int end) {
		del_list.Clear();
		for (int i = bgn; i < end; ++i) {
			Wmapi_itm__pge new_itm = (Wmapi_itm__pge)new_hash.Get_at(i);
			byte[] new_key = new_itm.Page_ttl();
			Wmapi_itm__pge cur_itm = (Wmapi_itm__pge)cur_hash.Get_by(new_key); if (cur_itm == null) continue;	// itm not found; ignore
			if (new_itm.Eq_meta(cur_itm, 0))	// itm is same; add to deleted list
				del_list.Add(new_itm);
		}
		int len = del_list.Count();
		for (int i = 0; i < len; ++i) {
			Wmapi_itm__pge itm = (Wmapi_itm__pge)del_list.Get_at(i);
			new_hash.Del(itm.Page_ttl());
		}
	}
}
