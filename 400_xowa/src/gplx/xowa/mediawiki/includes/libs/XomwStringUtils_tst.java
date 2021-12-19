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
package gplx.xowa.mediawiki.includes.libs;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import org.junit.*;
public class XomwStringUtils_tst {
	private final XomwStringUtils_fxt fxt = new XomwStringUtils_fxt();
	@Test public void Delimiter_explode() {
		// basic
		fxt.Test_delimiter_explode("a|b|c"                             , "a", "b", "c");
		// empty
		fxt.Test_delimiter_explode("|a||c|"                            , "", "a", "", "c", "");
		// nest_1
		fxt.Test_delimiter_explode("a|-{b|c}-|d"                       , "a", "-{b|c}-", "d");
		// nest_many
		fxt.Test_delimiter_explode("a|-{b-{c|d}-e}-|f"                 , "a", "-{b-{c|d}-e}-", "f");
	}
	@Test public void Replace_markup() {
		// basic
		fxt.Test_replace_markup("a!!b"             , "!!", "||", "a||b");
		// missing
		fxt.Test_replace_markup("abcd"             , "!!", "||", "abcd");
		// eos
		fxt.Test_replace_markup("a!!"              , "!!", "||", "a||");
		// ignore
		fxt.Test_replace_markup("a!!b<!!>!!c"      , "!!", "||", "a||b<!!>||c");
		// ignore asym_lhs
		fxt.Test_replace_markup("a!!b<!!<!!>!!c"   , "!!", "||", "a||b<!!<!!>||c");
		// ignore asym_lhs
		fxt.Test_replace_markup("a!!b<!!>!!>!!c"   , "!!", "||", "a||b<!!>||>||c");	// NOTE: should probably be "!!>!!>", but unmatched ">" are escaped to "&gt;"
	}
	@Test public void delimiterReplace() {
		// basic
		fxt.Test_delimiterReplace("/*", "*/", "a/*0*/c"         , "9", "a9c");
		// overlapping; "/*/"
		fxt.Test_delimiterReplace("/*", "*/", "a/*/0/*/c"       , "9", "a9c");
		// dangling bgn; "/* /*"
		fxt.Test_delimiterReplace("/*", "*/", "a/*0/*1*/c"      , "9", "a9c"); // fails if "a/*9c"
		// dangling end; "*/ */"
		fxt.Test_delimiterReplace("/*", "*/", "a/*0*/1*/c"      , "9", "a91*/c");
	}
}
class XomwStringUtils_fxt {
	public void Test_delimiter_explode(String src_str, String... expd) {
		List_adp tmp = List_adp_.New();
		gplx.core.btries.Btrie_rv trv = new gplx.core.btries.Btrie_rv();

		byte[][] actl = XomwStringUtils.delimiterExplode(tmp, trv, BryUtl.NewU8(src_str));
		GfoTstr.EqLines(expd, actl, "src=~{0}", src_str);
	}
	public void Test_replace_markup(String src_str, String find, String repl, String expd) {
		byte[] src_bry = BryUtl.NewU8(src_str);
		XomwStringUtils.replaceMarkup(src_bry, 0, src_bry.length, BryUtl.NewA7(find), BryUtl.NewA7(repl));
		GfoTstr.Eq(expd, src_bry);
	}
	// byte[] startDelim, byte[] endDelim, byte[] replace, byte[] subject
	public void Test_delimiterReplace(String bgn, String end, String src, String repl, String expd) {
		BryWtr bfr = BryWtr.New();
		XomwStringUtils.delimiterReplace(bfr, BryUtl.NewU8(bgn), BryUtl.NewU8(end), BryUtl.NewU8(repl), BryUtl.NewU8(src));
		GfoTstr.Eq(expd, bfr.ToStrAndClear());
	}
}
