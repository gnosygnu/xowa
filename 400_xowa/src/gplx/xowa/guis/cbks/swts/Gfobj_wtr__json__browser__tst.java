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
package gplx.xowa.guis.cbks.swts; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*; import gplx.xowa.guis.cbks.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.core.gfobjs.*;
public class Gfobj_wtr__json__browser__tst {
	private final    Gfobj_wtr__json__browser__fxt fxt = new Gfobj_wtr__json__browser__fxt();
	@Test   public void Json_proc() {
		fxt.Test__json_proc 
		( "proc_name"
		, fxt.Make__nde
		(   fxt.Make__fld_str	("k1", "v1")
		,   fxt.Make__fld_long	("k2", 2)
		,   fxt.Make__fld_int	("k3", 3)
		)
		, "return proc_name('{\"k1\":\"v1\",\"k2\":2,\"k3\":3}');"
		);
	}
}
class Gfobj_wtr__json__browser__fxt extends Gfobj_wtr__json_fxt {		public Gfobj_wtr__json__browser__fxt Test__json_proc() {return this;}
	public void Test__json_proc(String proc_name, Gfobj_nde root, String expd) {
		Gfobj_wtr__json__browser wtr = new Gfobj_wtr__json__browser();
		String actl = wtr.Write_as_func__swt(proc_name, root);
		Gftest.Eq__str(expd, actl, "json_write");
	}
}
