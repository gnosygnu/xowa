/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.caches;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
public class Lru_cache_root {
	private final Ordered_hash hash = Ordered_hash_.New();
	public Lru_cache Get_by_key(String key) {return (Lru_cache)hash.GetByOrNull(key);}
	public void Add(Lru_cache grp) {
		hash.Add(grp.Key(), grp);
	}
	public void Del(String key) {
		hash.Del(key);
	}
	public void Clear_caches_all() {
		int len = hash.Len();
		for (int i = 0; i < len; i++) {
			Lru_cache grp = (Lru_cache)hash.GetAt(i);
			grp.Clear_all();
		}
	}
	public void Clear_caches_min() {
		int len = hash.Len();
		for (int i = 0; i < len; i++) {
			Lru_cache grp = (Lru_cache)hash.GetAt(i);
			grp.Clear_min(0);
		}
	}
	public String Print_contents(boolean grps_only_or_both) {
		BryWtr bfr = BryWtr.New();
		int len = hash.Len();
		for (int i = 0; i < len; i++) {
			Lru_cache grp = (Lru_cache)hash.GetAt(i);
			grp.To_str(bfr, grps_only_or_both);
		}
		return bfr.ToStrAndClear();
	}
	public static final Lru_cache_root Instance = new Lru_cache_root();
}
