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
public class Bfr_arg__bry implements Bfr_arg_clearable {
	private int tid;
	private byte[] src; private int src_bgn, src_end;
	private Bfr_arg arg;
	public void Set_by_mid(byte[] src, int bgn, int end)		{this.tid = Tid_mid; this.src = src; this.src_bgn = bgn; this.src_end = end;}
	public void Set_by_val(byte[] src)							{this.tid = Tid_val; this.src = src;}
	public void Set_by_arg(Bfr_arg arg)							{this.tid = Tid_arg; this.arg = arg;}
	public void Bfr_arg__clear() {
		tid = Tid_nil;
		src = null; src_bgn = src_end = -1;
		arg = null;
	}
	public boolean Bfr_arg__missing() {return tid == Tid_nil;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		switch (tid) {
			case Tid_val:			bfr.Add(src); break;
			case Tid_mid:			bfr.Add_mid(src, src_bgn, src_end); break;
			case Tid_arg:			arg.Bfr_arg__add(bfr); break;
			case Tid_nil:			break;
		}
	}
	public static Bfr_arg__bry New_empty()		{return new Bfr_arg__bry();}
	public static Bfr_arg__bry New(byte[] v)	{Bfr_arg__bry rv = new Bfr_arg__bry(); rv.Set_by_val(v); return rv;}
	private static final int Tid_nil = 0, Tid_val = 1, Tid_mid = 2, Tid_arg = 3;
}
