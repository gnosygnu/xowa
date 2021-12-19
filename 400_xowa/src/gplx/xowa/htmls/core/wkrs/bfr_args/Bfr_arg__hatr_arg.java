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
import gplx.types.custom.brys.wtrs.args.BryBfrArgClearable;
import gplx.types.custom.brys.wtrs.BryWtr;
public class Bfr_arg__hatr_arg implements BryBfrArgClearable {
	private final byte[] atr_bgn;
	private BryBfrArgClearable val_as_arg;
	public Bfr_arg__hatr_arg(byte[] key) {this.atr_bgn = Bfr_arg__hatr_.Bld_atr_bgn(key);}
	public Bfr_arg__hatr_arg Set_by_arg(BryBfrArgClearable v)			{val_as_arg = v; return this;}
	public Bfr_arg__hatr_arg Set_by_arg_empty()							{val_as_arg = Bfr_arg__html_atr__empty.Instance; return this;}
	public void BfrArgClear() {val_as_arg = null;}
	public boolean BfrArgIsMissing() {return val_as_arg == null || val_as_arg.BfrArgIsMissing();}
	public void AddToBfr(BryWtr bfr) {
		if (BfrArgIsMissing()) return;
		bfr.Add(atr_bgn);
		val_as_arg.AddToBfr(bfr);
		bfr.AddByteQuote();
	}
}
class Bfr_arg__html_atr__empty implements BryBfrArgClearable {
	public void BfrArgClear() {}
	public boolean BfrArgIsMissing() {return false;}
	public void AddToBfr(BryWtr bfr) {}
        public static final Bfr_arg__html_atr__empty Instance = new Bfr_arg__html_atr__empty(); Bfr_arg__html_atr__empty() {}
}
