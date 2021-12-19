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
import gplx.types.basics.utls.IntUtl;
public class Bfr_arg__hatr_id implements BryBfrArgClearable {
	private final byte[] atr_bgn;
	private final byte[] bry; private int num;
	public Bfr_arg__hatr_id(byte[] atr_key, byte[] bry)	{this.bry = bry; this.atr_bgn = Bfr_arg__hatr_.Bld_atr_bgn(atr_key);}
	public Bfr_arg__hatr_id Set(int num)				{this.num = num; return this;}
	public byte[] Get_id_val()							{return BryUtl.Add(bry, IntUtl.ToBry(num));}
	public void BfrArgClear()						{num = -1;}
	public boolean BfrArgIsMissing()						{return num == -1;}
	public void AddToBfr(BryWtr bfr) {
		if (BfrArgIsMissing()) return;
		bfr.Add(atr_bgn);
		bfr.Add(bry);
		bfr.AddIntVariable(num);
		bfr.AddByteQuote();
	}
	public static Bfr_arg__hatr_id New_id(String v) {return new Bfr_arg__hatr_id(gplx.langs.htmls.Gfh_atr_.Bry__id, BryUtl.NewU8(v));}
	public static Bfr_arg__hatr_id New_id(byte[] v) {return new Bfr_arg__hatr_id(gplx.langs.htmls.Gfh_atr_.Bry__id, v);}
	public static final int Id__ignore = -1;
}
