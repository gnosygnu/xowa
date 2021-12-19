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
package gplx.xowa.xtns.scribunto.libs;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import org.junit.*;
import gplx.core.btries.*;
import gplx.xowa.parsers.xndes.*;
public class Scrib_lib_text__nowiki_util_tst {
	private final Scrib_lib_text__nowiki_util_fxt fxt = new Scrib_lib_text__nowiki_util_fxt();
	@Test public void Basic() {
		// noop
		fxt.Test__Strip_tag("abc", "abc");

		// one
		fxt.Test__Strip_tag("a<nowiki>b</nowiki>c", "abc");

		// mixed case
		fxt.Test__Strip_tag("a<NOwiki>b</noWIKI>c", "abc");

		// multiple: consecutive
		fxt.Test__Strip_tag("a<nowiki>b</nowiki>c<nowiki>d</nowiki>e", "abcde");

		// dangling: left
		fxt.Test__Strip_tag("a<nowiki>b<nowiki>c</nowiki>d", "ab<nowiki>cd");

		// dangling: right
		fxt.Test__Strip_tag( "a<nowiki>b</nowiki>c</nowiki>d", "abc</nowiki>d");

		// nested
		fxt.Test__Strip_tag("a<nowiki>b<nowiki>c</nowiki>d</nowiki>e", "ab<nowiki>cd</nowiki>e");
	}
}
class Scrib_lib_text__nowiki_util_fxt {
	private final Scrib_lib_text__nowiki_util util = new Scrib_lib_text__nowiki_util();
	private final Btrie_slim_mgr trie;
	public Scrib_lib_text__nowiki_util_fxt() {
		this.trie = util.Make_trie(Xop_xnde_tag_.Tag__nowiki.Name_bry());
	}
	public void Test__Strip_tag(String src, String expd) {
		byte[] actl = util.Strip_tag(BryUtl.NewA7("Page"), BryUtl.NewU8(src), trie);
		GfoTstr.Eq(expd, actl);
	} 
}
