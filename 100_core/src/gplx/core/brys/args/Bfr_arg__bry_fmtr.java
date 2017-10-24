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
import gplx.core.brys.fmtrs.*;
public class Bfr_arg__bry_fmtr implements Bfr_arg {
	private Bry_fmtr fmtr; private Object[] arg_ary;
	public Bfr_arg__bry_fmtr(Bry_fmtr fmtr, Object[] arg_ary) {Set(fmtr, arg_ary);}
	public Bfr_arg__bry_fmtr Set(Bry_fmtr fmtr, Object... arg_ary) {
		this.fmtr = fmtr; this.arg_ary = arg_ary;
		return this;
	}
	public void Bfr_arg__add(Bry_bfr bfr) {fmtr.Bld_bfr_many(bfr, arg_ary);}
}
