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
package gplx.xowa.htmls.modules.popups; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.modules.*;
import org.junit.*;
import gplx.xowa.apps.apis.xowa.html.modules.*;
import gplx.xowa.guis.views.*;
public class Xow_popup_anchor_finder__hdr_tst {
	@Before public void init() {fxt.Clear();} private Xop_popup_hdr_finder_fxt fxt = new Xop_popup_hdr_finder_fxt();
	@Test   public void Basic() {
		String src_str = String_.Concat_lines_nl_skip_last
		( "a"
		, "==b1=="
		, "c"
		);
		fxt.Test_find(src_str, "b1",  1);
		fxt.Test_find_not(src_str, "b");
		fxt.Test_find_not(src_str, "a");
	}
	@Test   public void Mid() {
		String src_str = String_.Concat_lines_nl_skip_last
		( "a"
		, "==b=="
		, "c"
		, "==d=="
		, "e"
		);
		fxt.Test_find(src_str, "d",  9);
	}
	@Test   public void Eos() {
		String src_str = String_.Concat_lines_nl_skip_last
		( "a"
		, "==b=="
		);
		fxt.Test_find(src_str, "b",  1);
	}
	@Test   public void Bos() {
		String src_str = String_.Concat_lines_nl_skip_last
		( "==a=="
		, "b"
		);
		fxt.Test_find(src_str, "a",  -1);
	}
	@Test   public void Trim() {
		String src_str = String_.Concat_lines_nl_skip_last
		( "a"
		, "== b =="
		, "c"
		);
		fxt.Test_find(src_str, "b",   1);
	}
	@Test   public void Ws() {
		String src_str = String_.Concat_lines_nl_skip_last
		( "a"
		, "== b c =="
		, "d"
		);
		fxt.Test_find(src_str, "b c",   1);
	}
}
class Xop_popup_hdr_finder_fxt {
	private Xow_popup_anchor_finder finder = new Xow_popup_anchor_finder();
	public void Clear() {
	}
	public void Test_find_not(String src_str, String hdr_str) {Test_find(src_str, hdr_str, Bry_find_.Not_found);}
	public void Test_find(String src_str, String hdr_str, int expd)  {
		byte[] src = Bry_.new_u8(src_str);
		byte[] hdr = Bry_.new_u8(hdr_str);
		Tfds.Eq(expd, finder.Find(src, src.length, hdr, 0), hdr_str);
	}
}
