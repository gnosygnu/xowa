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
import org.junit.*; import gplx.core.tests.*; import gplx.core.strings.*;
import gplx.langs.regxs.*;
public class XophpRegex__tst {
	private final    XophpRegex__fxt fxt = new XophpRegex__fxt();
	@After public void term() {fxt.Term();}
	@Test  public void Basic() {
		fxt.Test__preg_match("a", "abc", fxt.Expd__y().Add("a")); // found
		fxt.Test__preg_match("z", "abc", fxt.Expd__n()); // not found
		fxt.Test__preg_match("c", "abc", 1, fxt.Expd__y().Add("c")); // offset
		fxt.Test__preg_match("c", "abc", 3, fxt.Expd__err()); // offset: too large
		fxt.Test__preg_match("c", "abc", -1, fxt.Expd__err()); // offset: negative
	}
	@Test  public void Not_found() {
		fxt.Test__preg_match("a", "abc", fxt.Expd__y().Add("a")); // found
		fxt.Test__preg_match("z", "abc", fxt.Expd__n()); // not found
		fxt.Test__preg_match("c", "abc", 1, fxt.Expd__y().Add("c")); // offset
		fxt.Test__preg_match("c", "abc", 3, fxt.Expd__err()); // offset: too large
		fxt.Test__preg_match("c", "abc", -1, fxt.Expd__err()); // offset: negative
	}
	@Test  public void Character_classes() {
		fxt.Test__preg_match("[bc]", "abc", fxt.Expd__y().Add("b")); // character class
		fxt.Test__preg_match("[bc]", "abc", XophpRegex_.PREG_OFFSET_CAPTURE, 2, fxt.Expd__y().Add("c", 2)); // character class
	}
	@Test  public void Groups() {
		fxt.Test__preg_match("(foo)(bar)(baz)", "foobarbaz", XophpRegex_.PREG_OFFSET_CAPTURE, 0, fxt.Expd__y()
			.Add("foobarbaz", 0)
			.Add("foo", 0)
			.Add("bar", 3)
			.Add("baz", 6)
		);
		fxt.Test__preg_match("(foo)(bar)(baz)", "foobarbaz", XophpRegex_.PREG_NO_FLAG, 0, fxt.Expd__y()
			.Add("foobarbaz")
			.Add("foo")
			.Add("bar")
			.Add("baz")
		);
	}
	@Test  public void preg_quote() {
		Gftest.Eq__str("abc", XophpRegex_.preg_quote("abc", "/"));
		Gftest.Eq__str("\\.\\\\\\+\\*\\?\\[\\^\\]\\$\\(\\)\\{\\}\\=\\!\\<\\>\\|\\:\\-\\#", XophpRegex_.preg_quote(".\\+*?[^]$(){}=!<>|:-#", "/"));
	}
}
class XophpRegex__fxt {
	private String_bldr print_php = null;//String_bldr_.new_();
	public XophpRegex__expd Expd__err() {return new XophpRegex__expd(XophpRegex_.PREG_ERR);}
	public XophpRegex__expd Expd__y() {return new XophpRegex__expd(1);}
	public XophpRegex__expd Expd__n() {return new XophpRegex__expd(XophpRegex_.NOT_FOUND);}
	public void Test__preg_match(String pattern, String str, XophpRegex__expd rslt) {Test__preg_match(pattern, str, XophpRegex_.PREG_NO_FLAG, 0, rslt);}
	public void Test__preg_match(String pattern, String str, int offset, XophpRegex__expd rslt) {Test__preg_match(pattern, str, XophpRegex_.PREG_NO_FLAG, offset, rslt);}
	public void Test__preg_match(String pattern, String str, int flags, int offset, XophpRegex__expd rslt) {
		if (print_php != null) {
			String flag_str = "";
			switch (flags) {
				case XophpRegex_.PREG_OFFSET_CAPTURE: flag_str = "PREG_OFFSET_CAPTURE"; break;
				case XophpRegex_.PREG_UNMATCHED_AS_NULL: flag_str = "PREG_UNMATCHED_AS_NULL"; break;
				case XophpRegex_.PREG_NO_FLAG: flag_str = "0"; break;
			}
			print_php.Add(String_.Format("\necho \"<br>\" . preg_match('/{0}/', '{1}', $m, {2}, {3}) . ' '; var_dump($m);", pattern, str, flag_str, offset));
		}
		XophpArray actl_matches = XophpArray.New();
		int actl_pos = XophpRegex_.preg_match(Regx_adp_.new_(pattern), XophpRegex_.MODIFIER_NONE, str, actl_matches, flags, offset);
		Gftest.Eq__int(rslt.Pos(), actl_pos);
		XophpArray expd_matches = rslt.Matches();
		if (expd_matches != null) {
			Gftest.Eq__ary__lines(expd_matches.To_str(), actl_matches.To_str());
		}
	}
	public void Term() {
		if (print_php != null)
			Tfds.Write(print_php.Add_char_nl().To_str_and_clear());
	}
}
class XophpRegex__expd {
	public XophpRegex__expd(int pos) {
		this.pos = pos;
	}
	public int Pos() {return pos;} private final    int pos;
	public XophpArray Matches() {return matches;} private XophpArray matches;
	public XophpRegex__expd Add(String... ary) {
		if (matches == null) matches = XophpArray.New();
		for (Object itm : ary)
			matches.Add(itm);
		return this;
	}
	public XophpRegex__expd Add(String s, int pos) {
		if (matches == null) matches = XophpArray.New();
		matches.Add(XophpArray.New(s, pos));
		return this;
	}
}
