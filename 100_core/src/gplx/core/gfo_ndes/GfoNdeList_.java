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
package gplx.core.gfo_ndes; import gplx.*; import gplx.core.*;
import gplx.core.lists.*; /*ComparerAble*/
public class GfoNdeList_ {
	public static final    GfoNdeList Null = new GfoNdeList_null();
	public static GfoNdeList new_() {return new GfoNdeList_base();}
}
class GfoNdeList_base implements GfoNdeList {
	public int Count() {return list.Count();}
	public GfoNde FetchAt_asGfoNde(int i) {return (GfoNde)list.Get_at(i);}
	public void Add(GfoNde rcd) {list.Add(rcd);}
	public void Del(GfoNde rcd) {list.Del(rcd);}
	public void Clear() {list.Clear();}
	public void Sort_by(ComparerAble comparer) {list.Sort_by(comparer);}
	List_adp list = List_adp_.New();
}
class GfoNdeList_null implements GfoNdeList {
	public int Count() {return 0;}
	public GfoNde FetchAt_asGfoNde(int index) {return null;}
	public void Add(GfoNde rcd) {}
	public void Del(GfoNde rcd) {}
	public void Clear() {}
	public void Sort_by(ComparerAble comparer) {}
}
