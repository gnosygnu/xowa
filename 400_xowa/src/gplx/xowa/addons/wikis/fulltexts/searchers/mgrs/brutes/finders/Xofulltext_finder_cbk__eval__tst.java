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
package gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.brutes.finders; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*; import gplx.xowa.addons.wikis.fulltexts.searchers.*; import gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.*; import gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.brutes.*;
import org.junit.*; import gplx.core.tests.*;
public class Xofulltext_finder_cbk__eval__tst {
	private final    Xofulltext_finder_cbk__eval__fxt fxt = new Xofulltext_finder_cbk__eval__fxt();
	@Test   public void Exact() {
		fxt.Init__search("a");
		// y: basic match
		fxt.Test__eval_y("a");
		// n: no match
		fxt.Test__eval_n("z");
		// n: wildcard_bgn not enabled
		fxt.Test__eval_n("az");
	}
	@Test   public void Or() {
		fxt.Init__search("a, c");
		// y: lone char
		fxt.Test__eval_y("a"  , "c");
		// y: one char
		fxt.Test__eval_y("a b", "b c");
		// y: both chars
		fxt.Test__eval_y("a c", "a b c");
		// n: no chars
		fxt.Test__eval_n("b");
	}
	@Test   public void And() {
		fxt.Init__search("a + c");
		// y: both chars
		fxt.Test__eval_y("a c", "a b c");
		// n: one char only
		fxt.Test__eval_n("a", "c", "a b", "b c");
	}
	@Test   public void And__shorthand() {
		fxt.Init__search("a c");
		// y: both chars
		fxt.Test__eval_y("a b c");
		// n: one char only
		fxt.Test__eval_n("a", "c");
	}
	@Test   public void Not() {
		fxt.Init__search("-a");
		// y: no chars
		fxt.Test__eval_y("b");
		// n: char exists
		fxt.Test__eval_n("a");
	}
	@Test   public void Trim_end() {
		fxt.Init__search("a");
		// y: single
		fxt.Test__eval_y("a!");
		// y: many
		fxt.Test__eval_y("a!!!");
	}
	@Test   public void Trim_bgn() {
		fxt.Init__search("a");
		// y: single
		fxt.Test__eval_y("!a");
		// y: many
		fxt.Test__eval_y("!!!a");
	}
	@Test   public void Trim_both() {
		fxt.Init__search("a");
		// y: single
		fxt.Test__eval_y("'a'");
		// y: many
		fxt.Test__eval_y("'''a'''");
	}
	@Test   public void Slash() {
		fxt.Init__search("a");
		// y: slash before, after
		fxt.Test__eval_y("a/b/c", "b/a/c", "b/c/a");
	}
	@Test   public void Brack() {
		fxt.Init__search("a");
		// y
		fxt.Test__eval_y("[[a]]");
	}
	// .
	// ...
	// -
	// a'b
	// https://site/page
	// ()
	// []
	// <>
}
class Xofulltext_finder_cbk__eval__fxt {
	private boolean case_match = false;
	private boolean auto_wildcard_bgn = false;
	private boolean auto_wildcard_end = false;
	private byte wildcard_byte = Byte_ascii.Star;
	private byte not_byte = Byte_ascii.Dash;
	private final    Xofulltext_finder_mgr finder = new Xofulltext_finder_mgr();
	private final    Xofulltext_finder_cbk__eval cbk = new Xofulltext_finder_cbk__eval();
	public void Init__search(String query) {
		finder.Init(Bry_.new_u8(query), case_match, auto_wildcard_bgn, auto_wildcard_end, wildcard_byte, not_byte);
	}
	public void Test__eval_y(String... texts) {Test__eval(Bool_.Y, texts);}
	public void Test__eval_n(String... texts) {Test__eval(Bool_.N, texts);}
	public void Test__eval(boolean expd, String... texts) {
		for (String text : texts) {
			byte[] text_bry = Bry_.new_u8(text);
			cbk.found = false;
			finder.Match(text_bry, 0, text_bry.length, cbk);
			Gftest.Eq__bool(expd, cbk.found, "query={0} text={1}", finder.Query(), text);
		}
	}
}
