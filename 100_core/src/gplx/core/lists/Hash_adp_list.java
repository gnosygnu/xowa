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
package gplx.core.lists; import gplx.*; import gplx.core.*;
public class Hash_adp_list extends Hash_adp_base {
	@gplx.New public List_adp Get_by(Object key) {return ((List_adp)Fetch_base(key));}
	public List_adp Get_by_or_new(Object key) {
		List_adp rv = Get_by(key);
		if (rv == null) {
			rv = List_adp_.New();
			Add_base(key, rv);
		}
		return rv;
	}
	public void AddInList(Object key, Object val) {
		List_adp list = Get_by_or_new(key);
		list.Add(val);
	}
	public void DelInList(Object key, Object val) {
		List_adp list = Get_by(key);
		if (list == null) return;
		list.Del(val);
		if (list.Count() == 0) Del(key);
	}
	public static Hash_adp_list new_() {return new Hash_adp_list();} Hash_adp_list() {}
}
