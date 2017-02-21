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
package gplx.core.memorys; import gplx.*; import gplx.core.*;
public class Gfo_memory_mgr {
	private final    List_adp list = List_adp_.New();
	public void Reg_safe(Gfo_memory_itm itm) {synchronized (list) {Reg_fast(itm);}}
	public void Reg_fast(Gfo_memory_itm itm) {list.Add(itm);}
	public void Rls_safe() {synchronized (list) {Rls_fast();}}
	public void Rls_fast() {
		int len = list.Len();
		for (int i = 0; i < len; ++i) {
			Gfo_memory_itm itm = (Gfo_memory_itm)list.Get_at(i);
			itm.Rls_mem();
		}
		list.Clear();
	}
	public static final    Gfo_memory_mgr Instance = new Gfo_memory_mgr(); Gfo_memory_mgr() {}
}
