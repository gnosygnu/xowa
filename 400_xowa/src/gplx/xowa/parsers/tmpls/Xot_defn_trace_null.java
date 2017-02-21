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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xot_defn_trace_null implements Xot_defn_trace {
	public void Clear() {}
	public void Trace_bgn(Xop_ctx ctx, byte[] src, byte[] name, Xot_invk caller, Xot_invk self, Xot_defn defn) {}
	public void Trace_end(int trg_bgn, Bry_bfr trg) {}
	public void Print(byte[] src, Bry_bfr bb) {}
	public static final Xot_defn_trace_null Instance = new Xot_defn_trace_null(); Xot_defn_trace_null() {}
}
