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
package gplx.xowa.addons.htmls.tocs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.htmls.*;
import org.junit.*; import gplx.core.tests.*;
public class Xoh_toc_wkr__txt__basic__tst {
	@Before public void init() {fxt.Clear();} private final    Xoh_toc_wkr__txt__fxt fxt = new Xoh_toc_wkr__txt__fxt();
	@Test   public void Basic() {
		fxt.Test__both("a b c", "a_b_c", "a b c");
	}
	@Test   public void Ws() {
		fxt.Test__both(" a b ", "a_b", "a b");
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
	public void Clear() {wkr.Clear();}
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
		Gftest.Eq__str(expd, Xoh_toc_wkr__txt.Remove_comment(tmp, html_bry, 0, html_bry.length));
	}
}
