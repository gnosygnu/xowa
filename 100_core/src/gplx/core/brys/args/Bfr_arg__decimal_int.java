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
public class Bfr_arg__decimal_int implements Bfr_arg {
	public int Val() {return val;} public Bfr_arg__decimal_int Val_(int v) {val = v; return this;} int val;
	public Bfr_arg__decimal_int Places_(int v) {places = v; multiple = (int)Math_.Pow(10, v); return this;} int multiple = 1000, places = 3;
	public void Bfr_arg__add(Bry_bfr bfr) {
		bfr.Add_int_variable(val / multiple).Add_byte(Byte_ascii.Dot).Add_int_fixed(val % multiple, places);
	}
}
