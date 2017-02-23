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
package gplx.xowa.mediawiki.includes.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import org.junit.*; import gplx.core.tests.*;
public class XomwStringUtilsTest {
	private final    XomwStringUtilsFxt fxt = new XomwStringUtilsFxt();
	@Test  public void Delimiter_explode() {
		// basic
		fxt.Test__delimiter_explode("a|b|c"                             , "a", "b", "c");
		// empty
		fxt.Test__delimiter_explode("|a||c|"                            , "", "a", "", "c", "");
		// nest_1
		fxt.Test__delimiter_explode("a|-{b|c}-|d"                       , "a", "-{b|c}-", "d");
		// nest_many
		fxt.Test__delimiter_explode("a|-{b-{c|d}-e}-|f"                 , "a", "-{b-{c|d}-e}-", "f");
	}
	@Test  public void Replace_markup() {
		// basic
		fxt.Test__replace_markup("a!!b"             , "!!", "||", "a||b");
		// missing
		fxt.Test__replace_markup("abcd"             , "!!", "||", "abcd");
		// eos
		fxt.Test__replace_markup("a!!"              , "!!", "||", "a||");
		// ignore
		fxt.Test__replace_markup("a!!b<!!>!!c"      , "!!", "||", "a||b<!!>||c");
		// ignore asym_lhs
		fxt.Test__replace_markup("a!!b<!!<!!>!!c"   , "!!", "||", "a||b<!!<!!>||c");
		// ignore asym_lhs
		fxt.Test__replace_markup("a!!b<!!>!!>!!c"   , "!!", "||", "a||b<!!>||>||c");	// NOTE: should probably be "!!>!!>", but unmatched ">" are escaped to "&gt;"
	}
}
class XomwStringUtilsFxt {
	public void Test__delimiter_explode(String src_str, String... expd) {
		List_adp tmp = List_adp_.New();
		gplx.core.btries.Btrie_rv trv = new gplx.core.btries.Btrie_rv();

		byte[][] actl = XomwStringUtils.delimiterExplode(tmp, trv, Bry_.new_u8(src_str));
		Gftest.Eq__ary(expd, actl, "src=~{0}", src_str);
	}
	public void Test__replace_markup(String src_str, String find, String repl, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		XomwStringUtils.replaceMarkup(src_bry, 0, src_bry.length, Bry_.new_a7(find), Bry_.new_a7(repl));
		Gftest.Eq__str(expd, src_bry);
	}
}
