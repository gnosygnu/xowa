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
package gplx.xowa.parsers.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*; import gplx.core.log_msgs.*; import gplx.xowa.parsers.amps.*;
public class Xop_sanitizer_tst {
	Xop_sanitizer_fxt fxt = new Xop_sanitizer_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Space()						{fxt.tst_Escape_id("a b"						, "a_b");}
	@Test  public void Colon()						{fxt.tst_Escape_id("a%3Ab"						, "a:b");}
	@Test  public void Percent()					{fxt.tst_Escape_id("a%b"						, "a%b");}
	@Test  public void Amp_eos()					{fxt.tst_Escape_id("a&"							, "a&");}
	@Test  public void Amp_unrecognized()			{fxt.tst_Escape_id("a&bcd"						, "a&bcd");}
	@Test  public void Amp_name()					{fxt.tst_Escape_id("a&lt;b"						, "a<b");}
	@Test  public void Amp_ncr_dec_pass()			{fxt.tst_Escape_id("a&#33;b"					, "a!b");}
	@Test  public void Amp_ncr_dec_fail()			{fxt.tst_Escape_id("a&#33x;b"					, "a&#33x;b");}
	@Test  public void Amp_ncr_hex_pass()			{fxt.tst_Escape_id("a&#x21;b"					, "a!b");}
}
class Xop_sanitizer_fxt {
	public Xop_sanitizer sanitizer;
	public void Clear() {
		if (sanitizer != null) return;
		sanitizer = new Xop_sanitizer(Xop_amp_mgr.Instance, new Gfo_msg_log(Xoa_app_.Name));
	}
	public void tst_Escape_id(String raw, String expd)  {
		byte[] raw_bry = Bry_.new_u8(raw);
		Tfds.Eq(expd, String_.new_u8(sanitizer.Escape_id(raw_bry)));
	}
}
