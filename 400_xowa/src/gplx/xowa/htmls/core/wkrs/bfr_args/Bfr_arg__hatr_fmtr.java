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
package gplx.xowa.htmls.core.wkrs.bfr_args;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.fmts.fmtrs.*;
public class Bfr_arg__hatr_fmtr implements BryBfrArg {
	private final byte[] atr_bgn;
	private final BryFmtr fmtr = BryFmtr.New();
	private Object[] args;
	public Bfr_arg__hatr_fmtr(byte[] key, String fmt, String... keys) {
		this.atr_bgn = Bfr_arg__hatr_.Bld_atr_bgn(key);
		this.fmtr.FmtSet(fmt).KeysSet(keys);
		this.Clear();
	}
	public void Set_args(Object... args) {this.args = args;}
	public void Clear() {args = null;}
	public void Bfr_arg__clear() {this.Clear();}
	public boolean Bfr_arg__missing() {return args == null;}
	public void AddToBfr(BryWtr bfr) {
		if (Bfr_arg__missing()) return;
		bfr.Add(atr_bgn);
		fmtr.BldToBfrMany(bfr, args);
		bfr.AddByteQuote();
	}
}
