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
package gplx.xowa.langs.funcs; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.xowa.parsers.tmpls.*;
public class Xol_func_itm {
	public byte			Tid()		{return tid;}		private byte tid = Xot_defn_.Tid_null;
	public Xot_defn		Func()		{return func;}		private Xot_defn func = Xot_defn_.Null;
	public int			Colon_pos() {return colon_pos;} private int colon_pos = -1;
	public int			Subst_bgn() {return subst_bgn;} private int subst_bgn = -1;
	public int			Subst_end() {return subst_end;} private int subst_end = -1;
	public void Clear() {
		tid = Xot_defn_.Tid_null;
		func = Xot_defn_.Null;
		colon_pos = subst_bgn = subst_end = -1;
	}
	public void Init_by_subst(byte tid, int bgn, int end) {this.tid = tid; this.subst_bgn = bgn; this.subst_end = end;}
	public void Func_(Xot_defn v, int colon_pos) {
		if (tid == Xot_defn_.Tid_null) tid = Xot_defn_.Tid_func; // only set tid if subst did not set it
		this.func = v; 
		this.colon_pos = colon_pos;
	}
}
