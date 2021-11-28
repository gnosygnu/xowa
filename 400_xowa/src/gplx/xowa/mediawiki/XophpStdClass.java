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
public class XophpStdClass {
	private final List_adp list = List_adp_.New();
	private final Ordered_hash hash = Ordered_hash_.New();
	public int Len() {return list.Len();}
	public boolean Has(String key) {return hash.Has(key);}
	public void Add_at_as_itm(XophpStdClass itm) {
		list.Add(itm);
	}
	public void Add_by_as_obj(String key, Object itm) {
		list.Add(itm);
		hash.Add(key, itm);
	}
	public XophpStdClass Get_at_as_itm(int idx)      {return (XophpStdClass)list.Get_at(idx);}
	public Object Get_by_as_obj(String key)          {return hash.Get_by(key);}
	public XophpStdClass Get_by_as_itm(String key)   {return (XophpStdClass)hash.Get_by(key);}
	public String Get_by_as_str(String key)          {return (String)hash.Get_by(key);}
	public String Get_at_as_str(int idx)             {return (String)list.Get_at(idx);}
	public XophpStdClass Get_by_ary_as_itm(String... keys) {
		return (XophpStdClass)Get_by_ary_or_null(false, keys, keys.length - 1, 0);
	}
	public boolean Comp_str(String key, String expd) {
		String actl = Get_by_as_str(key);
		return String_.Eq(expd, actl);
	}
	public void Set_by_as_itm(String key, XophpStdClass itm) {
		hash.Add_if_dupe_use_nth(key, itm);
	}
	public void Set_by_as_itm(String[] keys, XophpStdClass rv) {
		int keys_last_idx = keys.length - 1;
		XophpStdClass itm = (XophpStdClass)Get_by_ary_or_null(true, keys, keys_last_idx - 1, 0);
		itm.Set_by_as_itm(keys[keys_last_idx], rv);
	}
	public void Set_by_as_str(String key, String val) {
		hash.Add_if_dupe_use_nth(key, val);
	}
	public void Set_at_as_str(int idx, String val) {
		list.Del_at(idx);
		list.Add_at(idx, val);
	}
	public void Del_by(String key) {
		Object itm = hash.Get_by(key);
		hash.Del(key);
		list.Del(itm);
	}
	private Object Get_by_ary_or_null(boolean create, String[] keys, int keys_idx_last, int keys_idx) {
		if (keys_idx == keys_idx_last) {
			return hash.Get_by(keys[keys_idx_last]);
		}

		String key = keys[keys_idx];
		XophpStdClass itm = Get_by_as_itm(key);
		if (itm == null) {
			// set
			if (create) {
				itm = new XophpStdClass();
				Set_by_as_itm(key, itm);
			}
			// get
			else {
				return null;
			}
		}
		return itm.Get_by_ary_or_null(create, keys, keys_idx_last, keys_idx + 1);
	}
}
