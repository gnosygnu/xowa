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
package gplx.xowa.mediawiki; import gplx.*; import gplx.xowa.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.core.primitives.*;
import gplx.langs.regxs.*;
public class XophpRegex_match_all_tst {
	private final XophpRegex_match_all_fxt fxt = new XophpRegex_match_all_fxt();
	@Test  public void Pattern_order() {
		fxt.Test__preg_match_all
			( XophpRegex_.Pattern("<[^>]+>(.*)</[^>]+>", XophpRegex_.MODIFIER_U)
			, "<b>example: </b><div align=left>this is a test</div>"
			, XophpRegex_.PREG_PATTERN_ORDER
			, fxt.Expd()
			.Add_many(XophpArray.New("<b>example: </b>", "<div align=left>this is a test</div>"))
			.Add_many(XophpArray.New("example: ", "this is a test"))
			);
	}
//		@Test  public void Pattern_order_matches() {
//			// PCRE does not allow duplicate named groups by default. PCRE 6.7 and later allow them if you turn on that option or use the mode modifier (?J). 
//			fxt.Test__preg_match_all
//				( XophpRegex_.Pattern("(?<match>foo)|(?<match>bar)", XophpRegex_.MODIFIER_U | XophpRegex_.MODIFIER_J) // (?J) changed to MODIFIER_J
//				, "foo bar"
//				, XophpRegex_.PREG_PATTERN_ORDER
//				, fxt.Expd()
//				  .Add(0, "<b>example: </b>").Add(0, "<div align=left>this is a test</div>")
//				  .Add(1, "example: ").Add(1, "this is a test")
//				);
//		}
	@Test  public void Set_order() {
		fxt.Test__preg_match_all
			( XophpRegex_.Pattern("<[^>]+>(.*)</[^>]+>", XophpRegex_.MODIFIER_U)
			, "<b>example: </b><div align=left>this is a test</div>"
			, XophpRegex_.PREG_SET_ORDER
			, fxt.Expd()
			  .Add_many(XophpArray.New("<b>example: </b>", "example: "))
			  .Add_many(XophpArray.New("<div align=left>this is a test</div>", "this is a test"))
			);
	}
	@Test  public void Offset_capture() {
		fxt.Test__preg_match_all
			( XophpRegex_.Pattern("(foo)(bar)(baz)", XophpRegex_.MODIFIER_U)
			, "foobarbaz"
			, XophpRegex_.PREG_OFFSET_CAPTURE
			, fxt.Expd()
				.Add_many
				( XophpArray.New("foobarbaz", "0")
				, XophpArray.New("foo", "0")
				, XophpArray.New("bar", "3")
				, XophpArray.New("baz", "6")
				)
			);
	}
}
class XophpRegex_match_all_fxt {
	public XophpRegex_match_all_expd Expd() {return new XophpRegex_match_all_expd();}
	public void Test__preg_match_all(Regx_adp pattern, String subject, XophpRegex_match_all_expd rslt) {Test__preg_match_all(pattern, subject, XophpRegex_.PREG_NO_FLAG, 0, rslt);}
	public void Test__preg_match_all(Regx_adp pattern, String subject, int flags, XophpRegex_match_all_expd rslt) {Test__preg_match_all(pattern, subject, flags, 0, rslt);}
	public void Test__preg_match_all(Regx_adp pattern, String subject, int flags, int offset, XophpRegex_match_all_expd expd) {
		XophpArray actl = XophpArray.New();
		XophpRegex_.preg_match_all(pattern, subject, actl, flags, offset);

		Gftest.Eq__ary__lines(expd.Ary().To_str(), actl.To_str());
	}
}
class XophpRegex_match_all_expd {
	public XophpArray Ary() {return ary;} private final XophpArray ary = XophpArray.New();
	public XophpRegex_match_all_expd Add(int idx, Object val) {
		XophpArray sub = ary.Get_at_ary(idx);
		if (sub == null) {
			sub = XophpArray.New();
			ary.Set(idx, sub);
		}
		sub.Add(val);
		return this;
	}
	public XophpRegex_match_all_expd Add_many(Object... vals) {
		for (Object val : vals)
			ary.Add(val);
		return this;
	}
}
