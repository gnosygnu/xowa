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
package gplx.xowa.mediawiki.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*;
import org.junit.*; import gplx.core.tests.*;
public class XomwMagicWordArrayTest {
	private final    XomwMagicWordArrayFxt fxt = new XomwMagicWordArrayFxt();
	@Test  public void Nil() {
		fxt.Init__word(Bool_.Y, "img_nil", "nil");
		fxt.Init__ary("img_nil");
		fxt.Test__matchVariableStartToEnd("nil", "img_nil", "");
		fxt.Test__matchVariableStartToEnd("nila", null, null);
	}
	@Test  public void Bgn() {
		fxt.Init__word(Bool_.Y, "img_bgn", "bgn$1");
		fxt.Init__ary("img_bgn");
		fxt.Test__matchVariableStartToEnd("bgna", "img_bgn", "a");
		fxt.Test__matchVariableStartToEnd("bgn", "img_bgn", "");
	}
	@Test  public void End() {
		fxt.Init__word(Bool_.Y, "img_end", "$1end");
		fxt.Init__ary("img_end");
		fxt.Test__matchVariableStartToEnd("aend", "img_end", "a");
		fxt.Test__matchVariableStartToEnd("end", "img_end", "");
	}
	@Test  public void Smoke() {
		fxt.Init__word(Bool_.Y, "img_upright", "upright", "upright=$1", "upright $1");
		fxt.Init__word(Bool_.Y, "img_width", "$1px");
		fxt.Init__ary("img_upright", "img_width");

		fxt.Test__matchVariableStartToEnd("upright=123", "img_upright", "123");
		fxt.Test__matchVariableStartToEnd("123px", "img_width", "123");
	}
}
class XomwMagicWordArrayFxt {
	private final    XomwMagicWordMgr magic_word_mgr = new XomwMagicWordMgr();
	private XomwMagicWordArray magic_word_ary;
	public void Init__word(boolean cs, String word, String... synonyms) {
		magic_word_mgr.Add(Bry_.new_u8(word), cs, Bry_.Ary(synonyms));
	}
	public void Init__ary(String... words) {
		magic_word_ary = new XomwMagicWordArray(magic_word_mgr, Bry_.Ary(words));
	}
	public void Test__matchVariableStartToEnd(String src, String expd_name, String expd_val) {
		byte[][] rv = new byte[2][];
		magic_word_ary.matchVariableStartToEnd(rv, Bry_.new_u8(src));
		Gftest.Eq__str(expd_name, rv[0], expd_name);
		Gftest.Eq__str(expd_val , rv[1], expd_val);
	}
}
