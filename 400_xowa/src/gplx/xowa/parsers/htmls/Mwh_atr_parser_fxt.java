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
package gplx.xowa.parsers.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
class Mwh_atr_parser_fxt {
	private final    Bry_bfr expd_bfr = Bry_bfr_.New(), actl_bfr = Bry_bfr_.New();
	private final    Mwh_atr_parser parser = new Mwh_atr_parser();
	private final    Mwh_doc_wkr__atr_bldr wkr = new Mwh_doc_wkr__atr_bldr();
	public Mwh_atr_itm Make_pair(String key, String val)	{return new Mwh_atr_itm(Bry_.Empty, Bool_.Y, Bool_.N, Bool_.Y,  -1,  -1, -1, -1, Bry_.new_u8(key)	, -1, -1, Bry_.new_u8(val)	, -1, -1);}
	public Mwh_atr_itm Make_name(String key)				{return new Mwh_atr_itm(Bry_.Empty, Bool_.Y, Bool_.N, Bool_.N,  -1,  -1, -1, -1, Bry_.new_u8(key)	, -1, -1, Bry_.new_u8(key)	, -1, -1);}
	public Mwh_atr_itm Make_fail(int bgn, int end)			{return new Mwh_atr_itm(Bry_.Empty, Bool_.N, Bool_.N, Bool_.N, bgn, end, -1, -1, null				, -1, -1, null				, -1, -1);}
	public void Test_val_as_int(String raw, int expd) {
		byte[] src = Bry_.new_u8(raw);
		Mwh_atr_itm itm = new Mwh_atr_itm(src, true, false, false, 0, src.length, -1, -1, null, 0, src.length, src, -1, -1);
		Tfds.Eq_int(expd, itm.Val_as_int_or(-1));
	}
	public void Test_parse(String raw, Mwh_atr_itm... expd) {
		Mwh_atr_itm[] actl = Exec_parse(raw);
		Test_print(expd, actl);
	}
	private Mwh_atr_itm[] Exec_parse(String raw) {
		byte[] bry = Bry_.new_u8(raw);
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
		Tfds.Eq_str_lines(expd_bfr.To_str_and_clear(), actl_bfr.To_str_and_clear());
	}
	private void To_bfr(Bry_bfr expd_bfr, Mwh_atr_itm expd_itm, Bry_bfr actl_bfr, Mwh_atr_itm actl_itm) {
		To_bfr__main(expd_bfr, expd_itm);
		To_bfr__main(actl_bfr, actl_itm);
		To_bfr__head(expd_bfr, expd_itm);
		To_bfr__head(actl_bfr, actl_itm);
		if (expd_itm != null && expd_itm.Atr_bgn() != -1) {
			To_bfr__atr_rng(expd_bfr, expd_itm);
			To_bfr__atr_rng(actl_bfr, actl_itm);
		}
	}
	private void To_bfr__head(Bry_bfr bfr, Mwh_atr_itm itm) {
		if (itm == null) return;
		bfr.Add_str_a7("head:").Add_yn(itm.Valid()).Add_byte_semic().Add_yn(itm.Repeated()).Add_byte_semic().Add_yn(itm.Key_exists()).Add_byte_nl();			
	}
	private void To_bfr__main(Bry_bfr bfr, Mwh_atr_itm itm) {
		if (itm == null) return;
		if (itm.Valid()) {
			bfr.Add_str_a7("key:").Add(itm.Key_bry()).Add_byte_nl();
			bfr.Add_str_a7("val:").Add(itm.Val_as_bry()).Add_byte_nl();
		}
//			else
//				To_bfr__atr_rng(bfr, itm);
	}
	private void To_bfr__atr_rng(Bry_bfr bfr, Mwh_atr_itm itm) {
		if (itm == null) return;
		bfr.Add_str_a7("rng:").Add_int_variable(itm.Atr_bgn()).Add_byte_semic().Add_int_variable(itm.Atr_end()).Add_byte_nl();
	}
}
