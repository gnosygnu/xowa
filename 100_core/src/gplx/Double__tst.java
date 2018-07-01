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
package gplx;
import org.junit.*;
public class Double__tst {
	private Double__fxt fxt = new Double__fxt();
	@Test  public void Xto_str_loose() {			
		fxt.Test_Xto_str_loose(2449.6000000d		, "2449.6");
		fxt.Test_Xto_str_loose(623.700d				, "623.7");
	}
}
class Double__fxt {
	public void Test_Xto_str_loose(double v, String expd) {Tfds.Eq(expd, Double_.To_str_loose(v));}
}
