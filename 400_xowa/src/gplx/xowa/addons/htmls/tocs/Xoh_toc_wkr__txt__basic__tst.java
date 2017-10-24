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
package gplx.xowa.addons.htmls.tocs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.htmls.*;
import org.junit.*; import gplx.core.tests.*; import gplx.langs.htmls.*;
public class Xoh_toc_wkr__txt__basic__tst {
	@Before public void init() {fxt.Clear();} private final    Xoh_toc_wkr__txt__fxt fxt = new Xoh_toc_wkr__txt__fxt();
	@Test   public void Basic() {
		fxt.Test__both("a b c", "a_b_c", "a b c");
	}
	@Test   public void Ws() {
		fxt.Test__both(" a b ", "a_b", "a b");
	}
	@Test   public void Nl() {
		fxt.Test__both("\na b\n", "a_b", "a b");
	}
	@Test   public void Empty() {	// PAGE:s.w:Colac,_Victoria DATE:2016-07-17
		fxt.Test__both("", "", "");
	}
	@Test   public void Amp__ncr() {
		fxt.Test__both("&#91;a&#93;", ".5Ba.5D", "&#91;a&#93;");
	}
	@Test   public void Encode() {
		fxt.Test__both("a+b", "a.2Bb", "a+b");
	}
	@Test   public void Comment() {
		fxt.Test__text("a<!--b-->c", "ac");
	}
	@Test   public void Remove_comment__one() {
		fxt.Test__remove_comment("a<!--b-->c", "ac");
	}
	@Test   public void Remove_comment__many() {
		fxt.Test__remove_comment("1<!--2-->3<!--4-->5", "135");
	}
	@Test   public void Remove_comment__dangling() {
		fxt.Test__remove_comment("1<!--2-->3<!--4->5", "13");
	}
}
class Xoh_toc_wkr__txt__fxt {
	private final    Xoh_toc_wkr__txt wkr = new Xoh_toc_wkr__txt();
	private final    Xoh_toc_itm itm = new Xoh_toc_itm();
	private final    Bry_bfr tmp = Bry_bfr_.New();
	private final    Xow_tidy_mgr_interface__test tidy_mgr = new Xow_tidy_mgr_interface__test();
	public void Clear() {
		wkr.Clear();
		tidy_mgr.Clear();
		wkr.Init(tidy_mgr, Xoa_page_.Main_page_bry);
	}
	public void Init__tidy(String html, String tidy)		{tidy_mgr.Add(Bry_.new_u8(html), Bry_.new_u8(tidy));}
	public void Test__anch(String html, String expd_anch)	{Test__both(html, expd_anch, null);}
	public void Test__text(String html, String expd_text)	{Test__both(html, null, expd_text);}
	public void Test__both(String html, String expd)		{Test__both(html, expd, expd);}
	public void Test__both(String html, String expd_anch, String expd_text) {
		wkr.Calc_anch_text(itm, Bry_.new_u8(html));
		if (expd_anch != null)	Gftest.Eq__str(expd_anch, itm.Anch(), "anch");
		if (expd_text != null)	Gftest.Eq__str(expd_text, itm.Text(), "text");
	}
	public void Test__remove_comment(String html, String expd) {
		byte[] html_bry = Bry_.new_u8(html);
		Gftest.Eq__str(expd, Gfh_utl.Del_comments(tmp, html_bry, 0, html_bry.length));
	}
}
class Xow_tidy_mgr_interface__test implements gplx.xowa.htmls.core.htmls.tidy.Xow_tidy_mgr_interface {
	private final    Ordered_hash hash = Ordered_hash_.New_bry();
	public void Clear() {hash.Clear();}
	public void Add(byte[] html, byte[] tidy) {hash.Add(html, tidy);}
	public void Exec_tidy(Bry_bfr bfr, boolean indent, byte[] page_url) {
		byte[] html = bfr.To_bry_and_clear();
		byte[] actl = (byte[])hash.Get_by_or_fail(html);
		bfr.Add(actl);
	}
}
