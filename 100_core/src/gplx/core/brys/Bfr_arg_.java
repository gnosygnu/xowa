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
package gplx.core.brys; import gplx.*; import gplx.core.*;
import gplx.core.brys.args.*; 	import gplx.core.brys.fmtrs.*;
public class Bfr_arg_ {
	public static Bfr_arg__int			New_int(int v)			{return new Bfr_arg__int(v);}
	public static Bfr_arg__byte			New_byte(byte v)		{return new Bfr_arg__byte(v);}
	public static Bfr_arg__bry			New_bry(String v)		{return Bfr_arg__bry.New(Bry_.new_u8(v));}
	public static Bfr_arg__bry			New_bry(byte[] v)		{return Bfr_arg__bry.New(v);}
	public static Bfr_arg__bry_fmtr		New_bry_fmtr__null()	{return new Bfr_arg__bry_fmtr(null, null);}
	public static Bfr_arg__bry_fmtr		New_bry_fmtr(Bry_fmtr v, Bfr_arg... arg_ary) {return new Bfr_arg__bry_fmtr(v, arg_ary);}
	public static final Bfr_arg Noop = new Bfr_arg___noop();
	public static void Clear(Bfr_arg_clearable... ary) {
		for (Bfr_arg_clearable arg : ary)
			arg.Bfr_arg__clear();
	}
}
class Bfr_arg___noop implements gplx.core.brys.Bfr_arg {
	public void Bfr_arg__add(Bry_bfr bfr) {}
}
