/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.langs.htmls; import gplx.*; import gplx.langs.*;
import org.junit.*;
public class Html_utl_tst {
	@Before public void init() {fxt.Clear();} private Html_atr_cls_fxt fxt = new Html_atr_cls_fxt();
	@Test   public void Basic() 		{fxt.Test_del_comments("a<!-- b -->c"				, "ac");}
	@Test   public void Bgn_missing() 	{fxt.Test_del_comments("a b c"						, "a b c");}
	@Test   public void End_missing() 	{fxt.Test_del_comments("a<!-- b c"					, "a<!-- b c");}
	@Test   public void Multiple()	 	{fxt.Test_del_comments("a<!--b-->c<!--d-->e"		, "ace");}
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
class Html_atr_cls_fxt {
	private Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	public void Clear() {
		tmp_bfr.Clear();
	}
	public void Test_del_comments(String src, String expd) {
		byte[] actl = Html_utl.Del_comments(tmp_bfr, Bry_.new_u8(src));
		Tfds.Eq(expd, String_.new_a7(actl));
	}
	public void Test_escape_html(boolean lt, boolean gt, boolean amp, boolean quote, boolean apos, String src, String expd) {
		byte[] actl = Html_utl.Escape_html_as_bry(Bry_.new_a7(src), lt, gt, amp, quote, apos);
		Tfds.Eq(expd, String_.new_a7(actl));
	}
	public void Test_escape_for_atr(String src, boolean quote_is_apos, String expd) {
		byte[] actl = Html_utl.Escape_for_atr_val_as_bry(tmp_bfr, quote_is_apos ? Byte_ascii.Apos : Byte_ascii.Quote, src);
		Tfds.Eq(expd, String_.new_u8(actl));
	}
	public void Test_unescape_html(boolean lt, boolean gt, boolean amp, boolean quote, boolean apos, String src, String expd) {
		byte[] bry = Bry_.new_u8(src);
		byte[] actl = Html_utl.Unescape(false, tmp_bfr, bry, 0, bry.length, lt, gt, amp, quote, apos);
		Tfds.Eq(expd, String_.new_a7(actl));
	}
}
