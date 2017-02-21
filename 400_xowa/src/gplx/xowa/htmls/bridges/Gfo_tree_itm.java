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
package gplx.xowa.htmls.bridges; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
interface Gfo_tree_itm {
	int Tid();
}
class Gfo_tree_itm_ {
	public static final int Tid_data = 1, Tid_list = 2;
}
class Gfo_tree_data implements Gfo_tree_itm {
	public int Tid() {return Gfo_tree_itm_.Tid_data;}
	public Gfo_tree_data(String key, Object val, int val_tid) {
		this.key = key; this.val = val; this.val_tid = val_tid; 
	}
	public String Key() {return key;} private final String key;
	public Object Val() {return val;} private final Object val;
	public int Val_tid() {return val_tid;} private final int val_tid;
}
class Gfo_tree_list implements Gfo_tree_itm {
	private final Ordered_hash list = Ordered_hash_.New();
	public Gfo_tree_list(String key) {this.key = key;}
	public int Tid() {return Gfo_tree_itm_.Tid_list;}
	public String Key() {return key;} private final String key;
	public void Clear() {list.Clear();}
	public int Len() {return list.Count();}
	public Gfo_tree_data Add_data(String key, Object val, int val_tid)	{Gfo_tree_data rv = new Gfo_tree_data(key, val, val_tid);	this.Add(key, rv); return rv;}
	public Gfo_tree_list Add_list(String key)							{Gfo_tree_list rv = new Gfo_tree_list(key);					this.Add(key, rv); return rv;}
	private void Add(String key, Gfo_tree_itm itm) {list.Add(key, itm);}
	public Gfo_tree_itm Get_at(int i) {return (Gfo_tree_itm)list.Get_at(i);}
}
