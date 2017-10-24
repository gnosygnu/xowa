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
package gplx.langs.htmls; import gplx.*; import gplx.langs.*;
import org.junit.*;
public class Gfh_utl__basic__tst {
	@Before public void init() {fxt.Clear();} private Gfh_class_fxt fxt = new Gfh_class_fxt();
	@Test   public void Escape()	 	{
		fxt.Test_escape_html(Bool_.Y, Bool_.Y, Bool_.Y, Bool_.Y, Bool_.Y, "a<b"			, "a&lt;b");	// basic
		fxt.Test_escape_html(Bool_.Y, Bool_.Y, Bool_.N, Bool_.Y, Bool_.Y, "a<&b"		, "a&lt;&b");	// fix: & not escaped when <> present
		fxt.Test_escape_html(Bool_.Y, Bool_.Y, Bool_.Y, Bool_.Y, Bool_.Y, "a<>'&\"b"	, "a&lt;&gt;&#39;&amp;&quot;b");
	}
	@Test   public void Escape_for_atr_val() {
		fxt.Test_escape_for_atr("abc"		, Bool_.N, "abc");			// basic
		fxt.Test_escape_for_atr("a'\"b"		, Bool_.Y, "a&#39;\"b");	// quote is '
		fxt.Test_escape_for_atr("a'\"b"		, Bool_.N, "a'&quot;b");	// quote is "
	}
	@Test   public void Unescape()	 	{
		fxt.Test_unescape_html(Bool_.Y, Bool_.Y, Bool_.Y, Bool_.Y, Bool_.Y, "a&lt;&gt;&#39;&amp;&quot;b"		, "a<>'&\"b");	// basic
	}
}
class Gfh_class_fxt {
	private Bry_bfr tmp_bfr = Bry_bfr_.Reset(255);
	public void Clear() {
		tmp_bfr.Clear();
	}
	public void Test_del_comments(String src, String expd) {
		byte[] actl = Gfh_utl.Del_comments(tmp_bfr, Bry_.new_u8(src));
		Tfds.Eq(expd, String_.new_a7(actl));
	}
	public void Test_escape_html(boolean lt, boolean gt, boolean amp, boolean quote, boolean apos, String src, String expd) {
		byte[] actl = Gfh_utl.Escape_html_as_bry(Bry_.new_a7(src), lt, gt, amp, quote, apos);
		Tfds.Eq(expd, String_.new_a7(actl));
	}
	public void Test_escape_for_atr(String src, boolean quote_is_apos, String expd) {
		byte[] actl = Gfh_utl.Escape_for_atr_val_as_bry(tmp_bfr, quote_is_apos ? Byte_ascii.Apos : Byte_ascii.Quote, src);
		Tfds.Eq(expd, String_.new_u8(actl));
	}
	public void Test_unescape_html(boolean lt, boolean gt, boolean amp, boolean quote, boolean apos, String src, String expd) {
		byte[] bry = Bry_.new_u8(src);
		byte[] actl = Gfh_utl.Unescape(false, tmp_bfr, bry, 0, bry.length, lt, gt, amp, quote, apos);
		Tfds.Eq(expd, String_.new_a7(actl));
	}
}
