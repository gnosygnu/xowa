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
package gplx.xowa.parsers.htmls;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.BoolUtl;
class Mwh_atr_parser_fxt {
	private final BryWtr expd_bfr = BryWtr.New(), actl_bfr = BryWtr.New();
	private final Mwh_atr_parser parser = new Mwh_atr_parser();
	private final Mwh_doc_wkr__atr_bldr wkr = new Mwh_doc_wkr__atr_bldr();
	public Mwh_atr_itm Make_pair(String key, String val)	{return new Mwh_atr_itm(BryUtl.Empty, BoolUtl.Y, BoolUtl.N, BoolUtl.Y,  -1,  -1, -1, -1, BryUtl.NewU8(key)	, -1, -1, BryUtl.NewU8(val)	, -1, -1);}
	public Mwh_atr_itm Make_name(String key)				{return new Mwh_atr_itm(BryUtl.Empty, BoolUtl.Y, BoolUtl.N, BoolUtl.N,  -1,  -1, -1, -1, BryUtl.NewU8(key)	, -1, -1, BryUtl.NewU8(key)	, -1, -1);}
	public Mwh_atr_itm Make_fail(int bgn, int end)			{return new Mwh_atr_itm(BryUtl.Empty, BoolUtl.N, BoolUtl.N, BoolUtl.N, bgn, end, -1, -1, null				, -1, -1, null				, -1, -1);}
	public void Test_val_as_int(String raw, int expd) {
		byte[] src = BryUtl.NewU8(raw);
		Mwh_atr_itm itm = new Mwh_atr_itm(src, true, false, false, 0, src.length, -1, -1, null, 0, src.length, src, -1, -1);
		GfoTstr.Eq(expd, itm.Val_as_int_or(-1));
	}
	public void Test_parse(String raw, Mwh_atr_itm... expd) {
		Mwh_atr_itm[] actl = Exec_parse(raw);
		Test_print(expd, actl);
	}
	private Mwh_atr_itm[] Exec_parse(String raw) {
		byte[] bry = BryUtl.NewU8(raw);
		parser.Parse(wkr, -1, -1, bry, 0, bry.length);
		return wkr.To_atr_ary();
	}
	public void Test_print(Mwh_atr_itm[] expd_ary, Mwh_atr_itm[] actl_ary) {
		int expd_len = expd_ary.length;
		int actl_len = actl_ary.length;
		int len = expd_len > actl_len ? expd_len : actl_len;
		for (int i = 0; i < len; ++i) {
			To_bfr(expd_bfr, i < expd_len ? expd_ary[i] : null, actl_bfr, i < actl_len ? actl_ary[i] : null);
		}
		GfoTstr.EqLines(expd_bfr.ToStrAndClear(), actl_bfr.ToStrAndClear());
	}
	private void To_bfr(BryWtr expd_bfr, Mwh_atr_itm expd_itm, BryWtr actl_bfr, Mwh_atr_itm actl_itm) {
		To_bfr__main(expd_bfr, expd_itm);
		To_bfr__main(actl_bfr, actl_itm);
		To_bfr__head(expd_bfr, expd_itm);
		To_bfr__head(actl_bfr, actl_itm);
		if (expd_itm != null && expd_itm.Atr_bgn() != -1) {
			To_bfr__atr_rng(expd_bfr, expd_itm);
			To_bfr__atr_rng(actl_bfr, actl_itm);
		}
	}
	private void To_bfr__head(BryWtr bfr, Mwh_atr_itm itm) {
		if (itm == null) return;
		bfr.AddStrA7("head:").AddYn(itm.Valid()).AddByteSemic().AddYn(itm.Repeated()).AddByteSemic().AddYn(itm.Key_exists()).AddByteNl();
	}
	private void To_bfr__main(BryWtr bfr, Mwh_atr_itm itm) {
		if (itm == null) return;
		if (itm.Valid()) {
			bfr.AddStrA7("key:").Add(itm.Key_bry()).AddByteNl();
			bfr.AddStrA7("val:").Add(itm.Val_as_bry()).AddByteNl();
		}
//			else
//				To_bfr__atr_rng(bfr, itm);
	}
	private void To_bfr__atr_rng(BryWtr bfr, Mwh_atr_itm itm) {
		if (itm == null) return;
		bfr.AddStrA7("rng:").AddIntVariable(itm.Atr_bgn()).AddByteSemic().AddIntVariable(itm.Atr_end()).AddByteNl();
	}
}
