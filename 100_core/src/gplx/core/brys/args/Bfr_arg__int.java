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
public class Bfr_arg__int implements Bfr_arg {
	private int val, val_digits;
	public Bfr_arg__int(int v) {Set(v);}
	public Bfr_arg__int Set(int v) {
		this.val = Int_.cast(v); 
		this.val_digits = Int_.DigitCount(val);
		return this;
	}
	public void Bfr_arg__add(Bry_bfr bfr) {bfr.Add_int_digits(val_digits, val);}
}
