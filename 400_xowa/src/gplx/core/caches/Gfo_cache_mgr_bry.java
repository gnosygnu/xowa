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
package gplx.core.caches;
import gplx.core.envs.*;
import gplx.libs.files.Io_mgr;
import gplx.types.commons.lists.ComparerAble;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.libs.files.Io_url;
import gplx.types.basics.wrappers.BoolRef;
public class Gfo_cache_mgr_bry extends Gfo_cache_mgr_base {
	public Object Get_or_null(byte[] key) {return Base_get_or_null(key);}
	public void Add(byte[] key, Object val) {Base_add(key, val);}
	public void Del(byte[] key) {Base_del(key);}
}
class Gfo_cache_itm_bry {
	public Gfo_cache_itm_bry(Object key, Object val) {this.key = key; this.val = val; this.Touched_update();}
	public Object Key() {return key;} private Object key;
	public Object Val() {return val;} private Object val;
	public long Touched() {return touched;} private long touched;
	public Gfo_cache_itm_bry Touched_update() {touched = SystemUtl.Ticks(); return this;}
}
class Gfo_cache_itm_comparer implements ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Gfo_cache_itm_bry lhs = (Gfo_cache_itm_bry)lhsObj;
		Gfo_cache_itm_bry rhs = (Gfo_cache_itm_bry)rhsObj;
		return LongUtl.Compare(lhs.Touched(), rhs.Touched());
	}
	public static final Gfo_cache_itm_comparer Touched_asc = new Gfo_cache_itm_comparer(); // TS.static
}
class Io_url_exists_mgr {
	private gplx.core.caches.Gfo_cache_mgr_bry cache_mgr = new gplx.core.caches.Gfo_cache_mgr_bry();
	public Io_url_exists_mgr() {
		cache_mgr.Compress_max_(IntUtl.MaxValue);
	}
	public boolean Has(Io_url url) {
		byte[] url_key = url.RawBry();
		Object rv_obj = cache_mgr.Get_or_null(url_key);
		if (rv_obj != null) return ((BoolRef)rv_obj).Val(); // cached val exists; use it
		boolean exists = Io_mgr.Instance.ExistsFil(url);
		cache_mgr.Add(url_key, BoolRef.New(exists));
		return exists;
	}
}
