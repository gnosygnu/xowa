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
package gplx.xowa.wmfs.apis; import gplx.*; import gplx.xowa.*; import gplx.xowa.wmfs.*;
import gplx.xowa.wmfs.*;
interface Xowm_update_meta_wkr {
	void Fetch_meta(Ordered_hash hash, int bgn, int end);
}
interface Xowm_update_text_wkr {
	void Fetch_text(Ordered_hash hash, int bgn, int end);
}
class Xowm_update_meta_wkr__xo {
	public void Fetch_meta(Ordered_hash hash, int bgn, int end) {
//			for (int i = bgn; i < end; ++i) {
//				Xowm_rev_itm itm = (Xowm_rev_itm)hash.Get_at(i);
//				if (i != bgn) tmp_bfr.Add_byte(Byte_ascii.Pipe);
//				tmp_bfr.Add(itm.Ttl().Full_db());
//			}
	}
}
class Xowm_updater {
	private final Ordered_hash trgs_hash = Ordered_hash_.new_bry_(), srcs_hash = Ordered_hash_.new_bry_();
	private final List_adp del_list = List_adp_.new_();
	public int Batch_size() {return batch_size;} public void Batch_size_(int v) {batch_size = v;} private int batch_size = 50;
	public Xowm_update_meta_wkr Wkr__trg_meta() {return wkr__trg_meta;} private Xowm_update_meta_wkr wkr__trg_meta;
	public Xowm_update_meta_wkr Wkr__src_meta() {return wkr__src_meta;} private Xowm_update_meta_wkr wkr__src_meta;
	public Xowm_update_text_wkr Wkr__trg_text() {return wkr__trg_text;} private Xowm_update_text_wkr wkr__trg_text;
	public void Init(Xowm_update_meta_wkr wkr__trg_meta, Xowm_update_meta_wkr wkr__src_meta, Xowm_update_text_wkr wkr__trg_text) {
		this.wkr__trg_meta = wkr__trg_meta;
		this.wkr__src_meta = wkr__src_meta;
		this.wkr__trg_text = wkr__trg_text;
	}
	public void Exec(Xoa_ttl[] ttls_ary) {
		synchronized (trgs_hash) {
			Build_itms(trgs_hash, ttls_ary);
			Build_itms(srcs_hash, ttls_ary);
			int len = trgs_hash.Count();
			for (int i = 0; i < len; i += batch_size)
				Exec_batch(i, len);
		}
	}
	private void Build_itms(Ordered_hash hash, Xoa_ttl[] ttls_ary) {
		int len = ttls_ary.length;
		for (int i = 0; i < len; ++i) {
			Xoa_ttl ttl = ttls_ary[i];
			byte[] key = ttl.Full_db();
			if (hash.Has(key)) continue;	// ignore dupes
			hash.Add(key, new Xowm_rev_itm(ttl));
		}
	}
	private void Exec_batch(int itms_bgn, int itms_len) {
		int itms_end = itms_bgn + batch_size; if (itms_end > itms_len) itms_end = itms_len;
		wkr__trg_meta.Fetch_meta(trgs_hash, itms_bgn, itms_end);
		wkr__src_meta.Fetch_meta(srcs_hash, itms_bgn, itms_end);
		Remove_unchanged(srcs_hash, trgs_hash, itms_bgn, itms_end);
		wkr__trg_text.Fetch_text(trgs_hash, itms_bgn, itms_end);
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
	private void Remove_unchanged(Ordered_hash trg_hash, Ordered_hash src_hash, int bgn, int end) {
		del_list.Clear();
		for (int i = bgn; i < end; ++i) {
			Xowm_rev_itm trg_itm = (Xowm_rev_itm)trg_hash.Get_at(i);
			byte[] trg_key = trg_itm.Ttl().Full_db();
			Xowm_rev_itm src_itm = (Xowm_rev_itm)src_hash.Get_by(trg_key);
			if (src_itm == null) continue;	// itm not found; ignore
			if (trg_itm.Eq_meta(src_itm))	// itm is same; add to deleted list
				del_list.Add(trg_itm);
		}
		int len = del_list.Count();
		for (int i = 0; i < len; ++i) {
			Xowm_rev_itm itm = (Xowm_rev_itm)del_list.Get_at(i);
			trg_hash.Del(itm.Ttl().Full_db());
		}
	}
}
class Xowm_rev_meta_api__wm {
	public Xowm_rev_itm[] Get(Gfo_usr_dlg usr_dlg, Xowmf_mgr wmf_mgr, Xow_wmf_api_mgr api_mgr, String domain_str, Xoa_ttl[] ttls_ary) {
		byte[] ttls_bry = To_api_arg(ttls_ary);
		byte[] json = api_mgr.Api_exec(usr_dlg, wmf_mgr, domain_str, "action=query&prop=revisions&rvprop=size|ids|timestamp&format=jsonfm&titles=" + String_.new_u8(ttls_bry));
		return Parse(json);
	}
	private byte[] To_api_arg(Xoa_ttl[] ttls_ary) {
		return null;
	}
	private Xowm_rev_itm[] Parse(byte[] json) {
		return null;
	}
}
class Xowm_rev_itm {
	public Xowm_rev_itm(Xoa_ttl ttl) {
		this.ttl = ttl;
	}
	public Xoa_ttl Ttl() {return ttl;} private final Xoa_ttl ttl;
	public int Page_id() {return page_id;} private int page_id;
	public int Rev_id() {return rev_id;} private int rev_id;
	public int Rev_len() {return rev_len;} private int rev_len;
	public byte[] Rev_time() {return rev_time;} private byte[] rev_time;
	public byte[] Rev_text() {return rev_text;} private byte[] rev_text;
	public byte[] Rev_user() {return rev_user;} private byte[] rev_user;
	public byte[] Rev_note() {return rev_note;} private byte[] rev_note;
	public void Init_meta(int page_id, int rev_id, int rev_len, byte[] rev_time, byte[] rev_user, byte[] rev_note) {
		this.page_id = page_id; this.rev_id = rev_id;
		this.rev_len = rev_len; this.rev_time = rev_time;
		this.rev_user = rev_user; this.rev_note = rev_note;
	}
	public void Init_text(byte[] rev_text) {
		this.rev_text = rev_text;
	}
	public boolean Eq_meta(Xowm_rev_itm comp) {
		return rev_len == comp.rev_len && Bry_.Eq(rev_time, comp.rev_time);
	}
}
