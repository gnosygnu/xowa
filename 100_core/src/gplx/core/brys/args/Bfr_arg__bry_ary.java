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
package gplx.core.brys.args; import gplx.*; import gplx.core.*; import gplx.core.brys.*;
public class Bfr_arg__bry_ary implements Bfr_arg {
	private byte[][] bry_ary;
	public Bfr_arg__bry_ary Set(byte[]... v) {this.bry_ary = v; return this;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		for (byte[] bry : bry_ary)
			bfr.Add(bry);
	}
}
