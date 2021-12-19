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
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.args.BryBfrArgClearable;
import gplx.types.custom.brys.wtrs.BryWtr;
public class Bfr_arg__hatr_bry implements BryBfrArgClearable {
	private final byte[] atr_bgn;		
	public Bfr_arg__hatr_bry(byte[] key) {this.atr_bgn = Bfr_arg__hatr_.Bld_atr_bgn(key); this.BfrArgClear();}
	private BryBfrArgClearable arg;
	public byte[] Src() {return src;} private byte[] src;
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public Bfr_arg__hatr_bry Set_by_bry(byte[] bry)						{src = bry; return this;}
	public Bfr_arg__hatr_bry Set_by_mid(byte[] bry, int bgn, int end)	{src = bry; src_bgn = bgn; src_end = end; return this;}
	public Bfr_arg__hatr_bry Set_by_arg(BryBfrArgClearable v)			{arg = v; return this;}
	public Bfr_arg__hatr_bry Set_by_mid_or_empty(byte[] bry, int bgn, int end)	{
		if (end == -1)	this.Set_by_bry(BryUtl.Empty);
		else			this.Set_by_mid(bry, bgn, end);
		return this;
	}
	public Bfr_arg__hatr_bry Set_by_mid_or_null(byte[] bry, int bgn, int end) {
		if (end != -1)	this.Set_by_mid(bry, bgn, end);
		return this;
	}
	public void BfrArgClear() {arg = null; src = null; src_bgn = src_end = -1;}
	public boolean BfrArgIsMissing() {return src == null && (arg == null || arg.BfrArgIsMissing());}
	public void AddToBfr(BryWtr bfr) {
		if (BfrArgIsMissing()) return;
		bfr.Add(atr_bgn);
		if		(src == null)	arg.AddToBfr(bfr);
		else if	(src_bgn == -1)	bfr.Add(src);
		else					bfr.AddMid(src, src_bgn, src_end);
		bfr.AddByteQuote();
	}
}
