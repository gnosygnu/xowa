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
	// TODO: lowercase count
	public int Count() {return hash.Len();}
	public boolean Count_bool() {return hash.Len() > 0;}
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
	public XophpArray Add_as_key_and_val_many(String... val) {
		for (String itm : val) {
			Add(itm, itm);
		}
		return this;
	}
	public XophpArray Add_many(Object... val) {
		for (Object itm : val) {
			Add(itm);
		}
		return this;
	}
	public XophpArray Get_at_ary(int i) {return (XophpArray)Get_at(i);}
	public String Get_at_str(int i) {return (String)Get_at(i);}
	public int Get_at_int(int i) {return Int_.Cast(Get_at(i));}
	public Object Get_at(int i) {
		if (i < 0 || i >= hash.Len()) return null;
		XophpArrayItm itm = (XophpArrayItm)hash.Get_at(i);
		return itm == null ? null : itm.Val();
	}
	public XophpArrayItm Get_at_itm(int i) {
		if (i < 0 || i >= hash.Len()) return null;
		return (XophpArrayItm)hash.Get_at(i);
	}
	public void Del_at(int i) {
		XophpArrayItm itm = (XophpArrayItm)hash.Get_at(i);
		if (itm != null) {
			hash.Del(itm.Key());
		}
	}
	public Object Get_by_obj(Object key) {return Get_by(Object_.Xto_str_strict_or_null(key));}
	public Object Get_by(int key) {return Get_by(Int_.To_str(key));}
	public boolean Get_by_bool(String key) {return Bool_.Cast(this.Get_by(key));}
	public int Get_by_int(String key) {return Int_.Cast(this.Get_by(key));}
	public XophpArray Get_by_ary(String key) {return (XophpArray)this.Get_by(key);}
	public String Get_by_str(char key) {return (String)this.Get_by(Char_.To_str(key));}
	public String Get_by_str(String key) {return (String)this.Get_by(key);}
	public Object Get_by(String key) {
		XophpArrayItm itm = (XophpArrayItm)hash.Get_by(key);
		return itm == null ? null : itm.Val();
	}
	public void Set(int key, Object val) {
		this.Set(XophpArrayItm.New_int(key, val));
	}
	public void Set(String key, Object val) {
		this.Set(XophpArrayItm.New_str(key, val));
	}
	// TODO: lowercase unset
	public void Unset(int key) {Unset(Int_.To_str(key));}
	public void Unset(String key) {
		hash.Del(key);
	}
	public boolean in_array(String v) {return Has(v);}
	public boolean Has_obj(Object key) {return Has(Object_.Xto_str_strict_or_null(key));}
	public boolean Has(String key) {
		return hash.Has(key);
	}
	public boolean isset(String key) {return hash.Has(key);}
	public boolean isset(int idx)    {return idx >= 0 && idx < hash.Count();}
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
	public Object pop() {return Pop();}
	// TODO: remove uppercase Pop
	public Object Pop() {
		int pos = this.Count() - 1;
		if (pos < 0) return null;
		XophpArrayItm itm = (XophpArrayItm)hash.Get_at(pos);
		this.Del_at(pos);
		return itm.Val();
	}
	public void Itm_str_concat_end(int idx, String v) {
		String itm = (String)this.Get_at(idx);
		itm += v;
		this.Set(idx, itm);
	}
	public XophpArray Clone() {
		XophpArray rv = new XophpArray();
		int len = hash.Len();
		for (int i = 0; i < len; i++) {
			XophpArrayItm itm = (XophpArrayItm)hash.Get_at(i);
			rv.Add(itm.Key(), itm.Val());
		}
		return rv;
	}
	public static XophpArray New(Object... vals) {
		XophpArray rv = new XophpArray();
		for (Object val : vals)
			rv.Add(val);
		return rv;
	}
	public static boolean is_array(Object val) {
		return Type_.Eq_by_obj(val, XophpArray.class);
	}
	public Object end() {
		int len = hash.Len();
		return len == 0 ? null : ((XophpArrayItm)hash.Get_at(len - 1)).Val();
	}
	public static final    XophpArray False = null; // handles code like "if ($var === false)" where var is an Object;
}
