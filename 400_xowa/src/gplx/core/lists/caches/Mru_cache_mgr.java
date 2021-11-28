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
package gplx.core.lists.caches; import gplx.*; import gplx.core.*; import gplx.core.lists.*;
import gplx.core.logs.*;
public class Mru_cache_mgr {
	private final Mru_cache_time_mgr time_mgr;
	private final Gfo_log_wtr log_wtr;
	private final Hash_adp key_hash = Hash_adp_.New();
	private final Sorted_hash val_hash;
	private final Bry_bfr log_bfr = Bry_bfr_.New_w_size(255);
	private final Mru_cache_itm_comparer comparer;
	private final Ordered_hash dirty = Ordered_hash_.New();
	private long cache_max, cache_size, compress_size;
	Mru_cache_mgr(Mru_cache_time_mgr time_mgr, Gfo_log_wtr log_wtr, long cache_max, long compress_size, long used_weight) {
		this.time_mgr = time_mgr;
		this.log_wtr = log_wtr;
		this.cache_max = cache_max;
		this.compress_size = compress_size;
		this.comparer = new Mru_cache_itm_comparer(used_weight, time_mgr.Now());
		this.val_hash = new Sorted_hash(comparer);
	}
	public long Cache_max() {return cache_max;} public void Cache_max_(long v) {this.cache_max = v;}
	public Object Get_or_null(String key) {
		Object itm_obj = key_hash.Get_by(key);
		if (itm_obj == null) return null;
		Mru_cache_itm itm = (Mru_cache_itm)itm_obj;
		dirty.Add_if_dupe_use_1st(key, itm);
		itm.Dirty(time_mgr.Now());
		return itm.Val();
	}
	public void Add(String key, Object val, long val_size) {
		if (cache_size + val_size > cache_max) {
			Compress(val_size);
		}
		Mru_cache_itm itm = new Mru_cache_itm(key, val, val_size, time_mgr.Now());
		key_hash.Add(key, itm);
		val_hash.Add(itm, itm);
		cache_size += val_size;
	}
	public void Compress(long val_size) {
		int dirty_len = dirty.Len();
		for (int i = 0; i < dirty_len; i++) {
			Mru_cache_itm dirty_itm = (Mru_cache_itm)dirty.Get_at(i);
			val_hash.Del(dirty_itm);
			dirty_itm.Update();
			val_hash.Add(dirty_itm, dirty_itm);
		}
		dirty.Clear();

		while (cache_size + val_size > compress_size) {
			Mru_cache_itm old = (Mru_cache_itm)val_hash.Del_val_at_0();
			key_hash.Del(old.Key());
			cache_size -= old.Size();
			if (log_wtr != null) {
				Write(Bool_.Y, old);
				log_wtr.Write(log_bfr);
			}
		}
	}
	public void Flush() {log_wtr.Flush();}
	public void Print() {
		Object[] vals = val_hash.Values_array();
		int vals_len = vals.length;
		for (int i = vals_len - 1; i >= 0; i--) {
			Mru_cache_itm val = (Mru_cache_itm)vals[i];
			Write(Bool_.N, val);
		}
		Io_mgr.Instance.SaveFilBfr(log_wtr.Fil_dir().GenSubFil("cache_mru_final.csv"), log_bfr);
		log_bfr.Clear();
	}
	private void Write(boolean write_time, Mru_cache_itm itm) {
		// 20180625_112443.506|Q2|123uses|10ms|1234bytes\n
		if (write_time) log_bfr.Add_dte_under(Datetime_now.Get_force()).Add_byte_pipe();
		log_bfr.Add_str_u8(Object_.Xto_str_strict_or_null_mark(itm.Key()));
		log_bfr.Add_byte_pipe().Add_long_fixed(comparer.Score(itm), 10);
		log_bfr.Add_byte_pipe().Add_long_variable(itm.Size());
		log_bfr.Add_byte_pipe().Add_long_variable(itm.Time_dif());
		log_bfr.Add_byte_pipe().Add_long_variable(itm.Used());
		log_bfr.Add_byte_nl();
	}
	public void Clear() {
		key_hash.Clear();
		val_hash.Clear();
		log_bfr.Clear();
		dirty.Clear();
		cache_size = 0;
	}
	public static Mru_cache_mgr New_by_mb_secs(Gfo_log_wtr log_wtr, long cache_max_in_mb, long compress_size, long used_weight_in_secs) {
		return new Mru_cache_mgr(new Mru_cache_time_mgr__clock(), log_wtr, Io_mgr.Len_mb * cache_max_in_mb, Io_mgr.Len_mb * compress_size, gplx.core.envs.System_.Ticks__per_second * used_weight_in_secs);
	}

	// TEST
	public Object[] Values_array() {return val_hash.Values_array();}
	@gplx.Internal protected static Mru_cache_mgr New_test(Mru_cache_time_mgr time_mgr, Gfo_log_wtr log_wtr, long cache_max, long compress_size, long used_weight) {return new Mru_cache_mgr(time_mgr, log_wtr, cache_max, compress_size, used_weight);}
}
