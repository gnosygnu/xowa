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
package gplx.core.lists.binary_searches; import gplx.*; import gplx.core.*; import gplx.core.lists.*;
interface Binary_search_grp {
	int Len();
	Object Get_at(int i);
}
class Binary_search_grp__ary implements Binary_search_grp {
	private final    Object[] ary;
	public Binary_search_grp__ary(Object[] ary) {this.ary = ary;}
	public int Len() {return ary.length;}
	public Object Get_at(int i) {return ary[i];}
}
class Binary_search_grp__list implements Binary_search_grp {
	private final    List_adp list;
	public Binary_search_grp__list(List_adp list) {this.list = list;}
	public int Len() {return list.Len();}
	public Object Get_at(int i) {return list.Get_at(i);}
}
