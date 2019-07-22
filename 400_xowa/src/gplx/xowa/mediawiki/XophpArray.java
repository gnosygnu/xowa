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
		Set(XophpArrayItm.New_int(key, val));
		return this;
	}
	public XophpArray Add(int key, Object val) {
		nxt_idx = key + 1;
		Set(XophpArrayItm.New_int(key, val));
		return this;
	}
	public XophpArray Add(double key, Object val) {
		int key_as_int = (int)key;
		nxt_idx = key_as_int + 1;
		Set(XophpArrayItm.New_int(key_as_int, val));
		return this;
	}
	public XophpArray Add(boolean key, Object val) {
		int key_as_int = key ? 1 : 0;
		nxt_idx = key_as_int + 1;
		Set(XophpArrayItm.New_int(key_as_int, val));
		return this;
	}
	public XophpArray Add(String key, Object val) {
		int key_as_int = Int_.Parse_or(key, Int_.Min_value);
		if (key_as_int == Int_.Min_value) {
			Set(XophpArrayItm.New_str(key, val));
		}
		else {
			Set(XophpArrayItm.New_int(key_as_int, val));
			nxt_idx = key_as_int + 1;
		}
		return this;
	}
	public XophpArray Get_at_ary(int i) {return (XophpArray)Get_at(i);}
	public String Get_at_str(int i) {return (String)Get_at(i);}
	public Object Get_at(int i) {
		if (i < 0 || i >= hash.Len()) return null;
		XophpArrayItm itm = (XophpArrayItm)hash.Get_at(i);
		return itm == null ? null : itm.Val();
	}
	public void Del_at(int i) {
		XophpArrayItm itm = (XophpArrayItm)hash.Get_at(i);
		if (itm != null) {
			hash.Del(itm.Key());
		}
	}
	public XophpArray Add_many(Object... val) {
		for (Object itm : val) {
			Add(itm);
		}
		return this;
	}
	public Object Get_by_obj(Object key) {return Get_by(Object_.Xto_str_strict_or_null(key));}
	public Object Get_by(int key) {return Get_by(Int_.To_str(key));}
	public Object Get_by(String key) {
		XophpArrayItm itm = (XophpArrayItm)hash.Get_by(key);
		return itm.Val();
	}
	public void Set(int key, Object val) {
		this.Set(XophpArrayItm.New_int(key, val));
	}
	public void Unset(int key) {Unset(Int_.To_str(key));}
	public void Unset(String key) {
		hash.Del(key);
	}
	public boolean Has_obj(Object key) {return Has(Object_.Xto_str_strict_or_null(key));}
	public boolean Has(String key) {
		return hash.Has(key);
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
	private void Set(XophpArrayItm itm) {
		String key = itm.Key();
		XophpArrayItm cur = (XophpArrayItm)hash.Get_by(key);
		if (cur == null) {
			hash.Add(key, itm);
		}
		else {
			cur.Val_(itm.Val());
		}
	}
	public static XophpArray New(Object... vals) {
		XophpArray rv = new XophpArray();
		for (Object val : vals)
			rv.Add(val);
		return rv;
	}
}
