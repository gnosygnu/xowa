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
class Mru_cache_itm {
	private final long time_bgn;
	private long time_dirty;
	private long used_dirty = -1;
	public Mru_cache_itm(Object key, Object val, long size, long time) {
		this.key = key;
		this.val = val;
		this.size = size;
		this.time_bgn = this.time_cur = time;
	}
	public Object Key() {return key;} private final Object key;
	public Object Val() {return val;} private final Object val;
	public long Size() {return size;} private long size;
	public long Time_cur() {return time_cur;} private long time_cur;
	public long Time_dif() {return time_cur - time_bgn;}
	public long Used() {return used;} private long used;
	public void Dirty(long time) {
		this.time_dirty = time;
		if (used_dirty == -1)
			used_dirty = used + 1;
		else
			used_dirty++;
	}
	public void Update() {
		this.time_cur = time_dirty;
		this.used = used_dirty;
		used_dirty = -1;
	}
	public void Update(long time) {
		this.time_cur = time;
		used++;
	}
}
class Mru_cache_itm_comparer implements ComparerAble {
	private long used_weight;
	private long time_bgn;
	public Mru_cache_itm_comparer(long used_weight, long time_bgn) {
		Init(used_weight, time_bgn);
	}
	public void Init(long used_weight, long time_bgn) {
		this.used_weight = used_weight;
		this.time_bgn = time_bgn;
	}
	public long Score(Mru_cache_itm itm) {
		return (itm.Time_cur() - time_bgn) + (itm.Used() * used_weight);
	}
	public int compare(Object lhsObj, Object rhsObj) {
		Mru_cache_itm lhs = (Mru_cache_itm)lhsObj;
		Mru_cache_itm rhs = (Mru_cache_itm)rhsObj;
		int rv = Long_.Compare(Score(lhs), Score(rhs));
		return rv == 0
			? CompareAble_.Compare_obj(lhs.Key(), rhs.Key())
			: rv;
	}
}
