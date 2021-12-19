/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.types.custom.brys.wtrs.args;
import gplx.types.basics.utls.MathUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
public class BryBfrArgDecimal implements BryBfrArg {
	public int Val() {return val;} public BryBfrArgDecimal ValSet(int v) {val = v; return this;} private int val;
	public BryBfrArgDecimal PlacesSet(int v) {places = v; multiple = (int)MathUtl.Pow(10, v); return this;} private int multiple = 1000, places = 3;
	@Override public void AddToBfr(BryWtr bfr) {
		bfr.AddIntVariable(val / multiple).AddByte(AsciiByte.Dot).AddIntFixed(val % multiple, places);
	}
}
