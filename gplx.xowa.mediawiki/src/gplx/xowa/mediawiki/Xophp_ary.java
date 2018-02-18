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
package gplx.xowa.mediawiki; import gplx.*; import gplx.xowa.*;
import gplx.core.brys.*;
public class Xophp_ary implements Bry_bfr_able {
	private final    Ordered_hash hash = Ordered_hash_.New();
	private int nxt_idx;
	public Xophp_ary Add(Object val) {
		int key = nxt_idx++;
		Xophp_ary_itm itm = Xophp_ary_itm.New(key, val);
		hash.Add_if_dupe_use_nth(key, itm);
		return this;
	}
	public Xophp_ary Add(int key, Object val) {
		nxt_idx = key + 1;
		Xophp_ary_itm itm = Xophp_ary_itm.New(key, val);
		hash.Add_if_dupe_use_nth(key, itm);
		return this;
	}
	public Xophp_ary Add(double key, Object val) {
		int key_as_int = (int)key;
		nxt_idx = key_as_int + 1;
		Xophp_ary_itm itm = Xophp_ary_itm.New(key_as_int, val);
		hash.Add_if_dupe_use_nth(key_as_int, itm);
		return this;
	}
	public Xophp_ary Add(boolean key, Object val) {
		int key_as_int = key ? 1 : 0;
		nxt_idx = key_as_int + 1;
		Xophp_ary_itm itm = Xophp_ary_itm.New(key_as_int, val);
		hash.Add_if_dupe_use_nth(key_as_int, itm);
		return this;
	}
	public Xophp_ary Add(String key, Object val) {
		Xophp_ary_itm itm = null;
		int key_as_int = Int_.Parse_or(key, Int_.Min_value);
		if (key_as_int != Int_.Min_value) {
			itm = Xophp_ary_itm.New(key_as_int, val);
			nxt_idx = key_as_int + 1;
			hash.Add_if_dupe_use_nth(key_as_int, itm);
		}
		else {
			itm = Xophp_ary_itm.New(key, val);
			hash.Add_if_dupe_use_nth(key, itm);
		}
		return this;
	}
	public Object Get(Object key) {
		Xophp_ary_itm itm = (Xophp_ary_itm)hash.Get_by(key);
		return itm.Val();
	}
	public void Unset(Object key) {
		hash.Del(key);
	}
	public boolean Has(Object key) {
		return hash.Has(key);
	}
	public Xophp_ary Values() {
		Xophp_ary rv = new Xophp_ary();
		int len = hash.Len();
		for (int i = 0; i < len; i++) {
			Xophp_ary_itm old_itm = (Xophp_ary_itm)hash.Get_at(i);
			rv.Add(i, old_itm.Val());
		}
		return rv;
	}
	public Xophp_ary_itm[] To_ary() {
		return (Xophp_ary_itm[])hash.To_ary(Xophp_ary_itm.class);
	}
	public void To_bfr(Bry_bfr bfr) {
		Xophp_ary_itm[] itms = To_ary();
		for (Xophp_ary_itm itm : itms) {
			itm.To_bfr(bfr);
		}
	}
	public static Xophp_ary New() {return new Xophp_ary();}
}
