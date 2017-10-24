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
import gplx.core.gfobjs.*;
public class Gfobj_wtr__json__browser extends Gfobj_wtr__json {		private final    Bry_bfr bfr;
	public Gfobj_wtr__json__browser() {
		this.Opt_ws_(Bool_.N).Opt_backslash_2x_(Bool_.Y);
		this.bfr = this.Bfr();
	}
	public String Write_as_func__swt(String func_name, Gfobj_grp root) {return Write_as_func(Bool_.Y, func_name, root);}
	public String Write_as_func__drd(String func_name, Gfobj_grp root) {return Write_as_func(Bool_.N, func_name, root);}
	private String Write_as_func(boolean write_return, String func_name, Gfobj_grp root) {
		if (write_return) bfr.Add(Bry__func_bgn);	// NOTE: Android WebView fails if return is passed; EX: "return 'true'" works on SWT.Browser, but not WebView
		bfr.Add_str_u8(func_name);
		bfr.Add(Bry__args_bgn);
		this.Write(root);
		bfr.Add(Bry__args_end);
		return this.To_str();
	}
	private static final    byte[]
	  Bry__func_bgn	= Bry_.new_a7("return ")
	, Bry__args_bgn = Bry_.new_a7("('")
	, Bry__args_end = Bry_.new_a7("');")
	;
}
