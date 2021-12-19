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
package gplx.xowa.htmls.core.wkrs.glys;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.xowa.htmls.core.wkrs.bfr_args.*;
class Bfr_arg__hatr__style implements BryBfrArg {
	private final byte[] atr_bgn;
	private int max_w, w;
	private byte[] xtra_cls;
	public Bfr_arg__hatr__style(byte[] key) {
		this.atr_bgn = Bfr_arg__hatr_.Bld_atr_bgn(key);
		this.Clear();
	}
	public void Set_args(int max_w, int w, byte[] xtra_cls) {this.max_w = max_w; this.w = w; this.xtra_cls = xtra_cls;}
	public void Clear() {max_w = 0; w = 0; xtra_cls = null;}
	public void Bfr_arg__clear() {this.Clear();}
	public boolean Bfr_arg__missing() {return max_w == 0 && xtra_cls == null;}
	public void AddToBfr(BryWtr bfr) {
		if (Bfr_arg__missing()) return;
		bfr.Add(atr_bgn);
		if (max_w > 0) {
			bfr.Add(Style__frag_1);
			bfr.AddIntVariable(max_w);
			bfr.Add(Style__frag_3);
		}
		if (w > 0) {
			bfr.AddByteSpace();
			bfr.Add(Style__frag_2);
			bfr.AddIntVariable(w);
			bfr.Add(Style__frag_3);
		}
		if (xtra_cls != null) {
			bfr.Add(xtra_cls);
		}
		bfr.AddByteQuote();
	}
	private static final byte[]
	  Style__frag_1 = BryUtl.NewA7("max-width:")
	, Style__frag_2 = BryUtl.NewA7("_width:")
	, Style__frag_3 = BryUtl.NewA7("px;")
	;
}