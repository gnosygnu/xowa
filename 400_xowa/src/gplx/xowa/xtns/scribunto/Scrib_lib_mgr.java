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
package gplx.xowa.xtns.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Scrib_lib_mgr {
	private List_adp list = List_adp_.New();
	public int Len() {return list.Count();}
	public void Add(Scrib_lib v) {list.Add(v); v.Init();}
	public Scrib_lib Get_at(int i) {return (Scrib_lib)list.Get_at(i);}
	public void Init_for_core(Scrib_core core, Io_url script_dir) {
		int len = list.Count();
		for (int i = 0; i < len; i++) {
			Scrib_lib lib = Get_at(i).Clone_lib(core);
			lib.Register(core, script_dir);
		}
	}
}
