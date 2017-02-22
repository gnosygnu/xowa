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
package gplx.xowa.parsers.amps; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*; import gplx.core.tests.*;
public class Xop_amp_mgr__decode__tst {
	@Before public void init() {} private final    Xop_amp_mgr_fxt fxt = new Xop_amp_mgr_fxt();
	@Test  public void Text()						{fxt.Test__decode_as_bry("a"				, "a");}
	@Test  public void Name()						{fxt.Test__decode_as_bry("&amp;"			, "&");}
	@Test  public void Name_w_text()				{fxt.Test__decode_as_bry("a&amp;b"			, "a&b");}
	@Test  public void Name_fail_semic_missing()	{fxt.Test__decode_as_bry("a&ampb"			, "a&ampb");}
	@Test  public void Name_fail_amp_only()			{fxt.Test__decode_as_bry("a&"				, "a&");}
	@Test  public void Num_fail()					{fxt.Test__decode_as_bry("&#!;"				, "&#!;");}		// ! is not valid num
	@Test  public void Hex_fail()					{fxt.Test__decode_as_bry("&#x!;"			, "&#x!;");}	// ! is not valid hex
	@Test  public void Num_basic()					{fxt.Test__decode_as_bry("&#0931;"			, "Σ");}
	@Test  public void Num_zero_padded()			{fxt.Test__decode_as_bry("&#00931;"			, "Σ");}
	@Test  public void Hex_upper()					{fxt.Test__decode_as_bry("&#x3A3;"			, "Σ");}
	@Test  public void Hex_lower()					{fxt.Test__decode_as_bry("&#x3a3;"			, "Σ");}
	@Test  public void Hex_zero_padded()			{fxt.Test__decode_as_bry("&#x03a3;"			, "Σ");}
	@Test  public void Hex_upper_x()				{fxt.Test__decode_as_bry("&#X3A3;"			, "Σ");}
	@Test  public void Num_fail_large_codepoint()	{fxt.Test__decode_as_bry("&#538189831;"		, "&#538189831;");}
	@Test  public void Num_ignore_extra_x()			{fxt.Test__decode_as_bry("&#xx26D0;"		, Char_.To_str(Char_.By_int(9936)));}	// 2nd x is ignored
}
class Xop_amp_mgr_fxt {
	private final    Xop_amp_mgr amp_mgr = Xop_amp_mgr.Instance;
	public void Test__decode_as_bry(String raw, String expd) {
		Gftest.Eq__str(expd, String_.new_u8(amp_mgr.Decode_as_bry(Bry_.new_u8(raw))));
	}
	public void Test__parse_tkn__ent(String raw, String expd) {
		Xop_amp_mgr_rslt rv = Exec__parse_tkn(raw);
		Xop_amp_tkn_ent tkn = (Xop_amp_tkn_ent)rv.Tkn();
		Gftest.Eq__byte(Xop_tkn_itm_.Tid_html_ref, tkn.Tkn_tid());
		Gftest.Eq__str(expd, tkn.Xml_name_bry());
	}
	public void Test__parse_tkn__ncr(String raw, int expd) {
		Xop_amp_mgr_rslt rv = Exec__parse_tkn(raw);
		Xop_amp_tkn_num tkn = (Xop_amp_tkn_num)rv.Tkn();
		Gftest.Eq__byte(Xop_tkn_itm_.Tid_html_ncr, tkn.Tkn_tid());
		Gftest.Eq__int(expd, tkn.Val());
	}
	public void Test__parse_tkn__txt(String raw, int expd) {
		Xop_amp_mgr_rslt rv = Exec__parse_tkn(raw);
		Gftest.Eq__null(Bool_.Y, rv.Tkn());
		Gftest.Eq__int(expd, rv.Pos());
	}
	private Xop_amp_mgr_rslt Exec__parse_tkn(String raw) {
		byte[] src = Bry_.new_u8(raw);
		return amp_mgr.Parse_tkn(new Xop_tkn_mkr(), src, src.length, 0, 1);
	}
}
