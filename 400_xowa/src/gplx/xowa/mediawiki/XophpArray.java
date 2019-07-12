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
public class XophpArray implements Bry_bfr_able {
	private final    Ordered_hash hash = Ordered_hash_.New();
	private int nxt_idx;
	public int Len() {return hash.Len();}
	public void Clear() {
		hash.Clear();
		nxt_idx = 0;
	}
	public XophpArray Add(Object val) {
		int key = nxt_idx++;
		XophpArrayItm itm = XophpArrayItm.New_int(key, val);
		Set_or_add(key, itm);
		return this;
	}
	public XophpArray Add(int key, Object val) {
		nxt_idx = key + 1;
		XophpArrayItm itm = XophpArrayItm.New_int(key, val);
		Set_or_add(key, itm);
		return this;
	}
	public XophpArray Add(double key, Object val) {
		int key_as_int = (int)key;
		nxt_idx = key_as_int + 1;
		XophpArrayItm itm = XophpArrayItm.New_int(key_as_int, val);
		Set_or_add(key_as_int, itm);
		return this;
	}
	public XophpArray Add(boolean key, Object val) {
		int key_as_int = key ? 1 : 0;
		nxt_idx = key_as_int + 1;
		XophpArrayItm itm = XophpArrayItm.New_int(key_as_int, val);
		Set_or_add(key_as_int, itm);
		return this;
	}
	public XophpArray Add(String key, Object val) {
		XophpArrayItm itm = null;
		int key_as_int = Int_.Parse_or(key, Int_.Min_value);
		if (key_as_int == Int_.Min_value) {
			itm = XophpArrayItm.New_str(key, val);
			Set_or_add(key, itm);
		}
		else {
			itm = XophpArrayItm.New_int(key_as_int, val);
			Set_or_add(key_as_int, itm);
			nxt_idx = key_as_int + 1;
		}
		return this;
	}
	public XophpArrayItm Get_at(int i) {
		return (XophpArrayItm)hash.Get_at(i);
	}
	public void Del_at(int i) {
		XophpArrayItm itm = (XophpArrayItm)hash.Get_at(i);
		if (itm != null) {
			if (itm.Key_as_str() == null)
				hash.Del(itm.Key_as_int());
			else
				hash.Del(itm.Key_as_str());
		}
	}
	private void Set_or_add(Object key, XophpArrayItm val) {
		XophpArrayItm itm = (XophpArrayItm)hash.Get_by(key);
		if (itm == null) {
			hash.Add(key, val);
		}
		else {
			itm.Val_(val.Val());
		}
	}
	public XophpArray Add_many(Object... val) {
		for (Object itm : val) {
			Add(itm);
		}
		return this;
	}
	public Object Get(Object key) {
		XophpArrayItm itm = (XophpArrayItm)hash.Get_by(key);
		return itm.Val();
	}
	public void Set(int key, Object val) {
		hash.Del(key);
		this.Add(key, val);
	}
	public void Unset(Object key) {
		hash.Del(key);
	}
	public boolean Has(Object key) {
		return hash.Has(key);
	}
	public XophpArray Values() {
		XophpArray rv = new XophpArray();
		int len = hash.Len();
		for (int i = 0; i < len; i++) {
			XophpArrayItm old_itm = (XophpArrayItm)hash.Get_at(i);
			rv.Add(i, old_itm.Val());
		}
		return rv;
	}
	public XophpArrayItm[] To_ary() {
		return (XophpArrayItm[])hash.To_ary(XophpArrayItm.class);
	}
	public String To_str() {
		Bry_bfr bfr = Bry_bfr_.New();
		To_bfr(bfr);
		return bfr.To_str_and_clear();
	}
	public void To_bfr(Bry_bfr bfr) {
		XophpArrayItm[] itms = To_ary();
		for (XophpArrayItm itm : itms) {
			itm.To_bfr(bfr);
		}
	}
	public static XophpArray New(Object... vals) {
		XophpArray rv = new XophpArray();
		for (Object val : vals)
			rv.Add(val);
		return rv;
	}
}
