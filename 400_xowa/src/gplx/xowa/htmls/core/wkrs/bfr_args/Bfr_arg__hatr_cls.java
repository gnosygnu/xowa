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
public class Bfr_arg__hatr_cls implements BryBfrArg {
	private final byte[] atr_bgn;
	private byte[][] ary;
	public Bfr_arg__hatr_cls()							{this.atr_bgn = Bfr_arg__hatr_.Bld_atr_bgn(gplx.langs.htmls.Gfh_atr_.Bry__class);}
	public Bfr_arg__hatr_cls Set_by_ary(byte[][] v)		{ary = v; return this;}
	public void Bfr_arg__clear()						{ary = null;}
	public boolean Bfr_arg__missing()						{return ary == null;}
	public void AddToBfr(BryWtr bfr) {
		if (Bfr_arg__missing()) return;
		bfr.Add(atr_bgn);
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			if (i != 0) bfr.AddByteSpace();
			bfr.Add(ary[i]);
		}
		bfr.AddByteQuote();
	}
}
