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
package gplx.xowa.guis.views; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
public class Xog_tab_close_mgr {
	private List_adp list = List_adp_.New();
	public void Clear() {list.Clear();}
	public void Add(Xog_tab_close_lnr lnr) {list.Add(lnr);} 
	public int Len() {return list.Count();} 
	public Xog_tab_close_lnr Get_at(int i) {return (Xog_tab_close_lnr)list.Get_at(i);}
	public boolean When_close(Xog_tab_itm tab, Xoa_url url) {
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Xog_tab_close_lnr lnr = Get_at(i);
			if (!lnr.When_close(tab, url)) return false;
		}
		return true;
	}
}
