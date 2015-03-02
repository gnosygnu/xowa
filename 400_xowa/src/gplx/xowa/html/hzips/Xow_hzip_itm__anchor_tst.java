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
package gplx.xowa.html.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import org.junit.*; import gplx.xowa.html.*;
public class Xow_hzip_itm__anchor_tst {
	@Before public void init() {fxt.Clear();} private Xow_hzip_mgr_fxt fxt = new Xow_hzip_mgr_fxt();
	@Test   public void Srl_lnki_text_n() {
		byte[][] brys = Bry_.Ary(Xow_hzip_dict.Bry_lnki_text_n, Bry_.ints_(2), Bry_.new_ascii_("A"), Xow_hzip_dict.Escape_bry);
		fxt.Test_save(brys, "<a xtid='a_lnki_text_n' href=\"/wiki/A\" id='xowa_lnki_0' title='A'>A</a>");
		fxt.Test_load(brys, "<a href='/wiki/A' title='A'>A</a>");
	}
	@Test   public void Srl_lnki_text_n_alt_case() {
		byte[][] brys = Bry_.Ary(Xow_hzip_dict.Bry_lnki_text_n, Bry_.ints_(2), Bry_.new_ascii_("a"), Xow_hzip_dict.Escape_bry);
		fxt.Test_save(brys, "<a xtid='a_lnki_text_n' href=\"/wiki/A\" id='xowa_lnki_0' title='A'>a</a>");
		fxt.Test_load(brys, "<a href='/wiki/A' title='a'>a</a>");
	}
	@Test   public void Srl_lnki_text_n_ns() {
		byte[][] brys = Bry_.Ary(Xow_hzip_dict.Bry_lnki_text_n, Bry_.ints_(12), Bry_.new_ascii_("A"), Xow_hzip_dict.Escape_bry);
		fxt.Test_save(brys, "<a xtid='a_lnki_text_n' href=\"/wiki/Template:A\" id='xowa_lnki_0' title='Template:A'>Template:A</a>");
		fxt.Test_load(brys, "<a href='/wiki/Template:A' title='Template:A'>Template:A</a>");
	}
	@Test   public void Srl_lnki_text_n_smoke() {
		byte[][] brys = Bry_.Ary(Bry_.new_ascii_("a_1"), Xow_hzip_dict.Bry_lnki_text_n, Bry_.ints_(2), Bry_.new_ascii_("A"), Xow_hzip_dict.Escape_bry, Bry_.new_ascii_("a_2"));
		fxt.Test_save(brys, "a_1<a xtid='a_lnki_text_n' href=\"/wiki/A\" id='xowa_lnki_0' title='A'>A</a>a_2");
		fxt.Test_load(brys, "a_1<a href='/wiki/A' title='A'>A</a>a_2");
	}
	@Test   public void Srl_lnki_text_y() {
		byte[][] brys = Bry_.Ary(Xow_hzip_dict.Bry_lnki_text_y, Bry_.ints_(2), Bry_.new_ascii_("A"), Xow_hzip_dict.Escape_bry, Bry_.new_ascii_("A1"), Xow_hzip_dict.Bry_a_rhs);
		fxt.Test_save(brys, "<a xtid='a_lnki_text_y' href=\"/wiki/A\" id='xowa_lnki_0' title='A'>A1</a>");
		fxt.Test_load(brys, "<a href='/wiki/A' title='A'>A1</a>");
	}
	@Test   public void Srl_lnki_text_y_nest() {
		byte[][] brys = Bry_.Ary
		( Xow_hzip_dict.Bry_lnki_text_y, Bry_.ints_(2), Bry_.new_ascii_("A"), Xow_hzip_dict.Escape_bry
		,	Bry_.new_ascii_("B"), Xow_hzip_dict.Bry_lnki_text_y, Bry_.ints_(2), Bry_.new_ascii_("C"), Xow_hzip_dict.Escape_bry, Bry_.new_ascii_("C1"), Xow_hzip_dict.Bry_a_rhs, Bry_.new_ascii_("D")
		, Xow_hzip_dict.Bry_a_rhs
		);
		fxt.Test_save(brys, "<a xtid='a_lnki_text_y' href=\"/wiki/A\">B<a xtid='a_lnki_text_y' href=\"/wiki/C\">C1</a>D</a>");
		fxt.Test_load(brys, "<a href='/wiki/A' title='A'>B<a href='/wiki/C' title='C'>C1</a>D</a>");
	}
	@Test   public void Srl_noop() {
		byte[][] brys = Bry_.Ary(Bry_.new_utf8_("<a href='/wiki/A'>A"), Xow_hzip_dict.Bry_a_rhs);
		fxt.Test_save(brys, "<a href='/wiki/A'>A</a>");
	}
	@Test   public void Srl_lnke_txt() {
		byte[][] brys = Bry_.Ary(Xow_hzip_dict.Bry_lnke_txt, Bry_.new_ascii_("http://a.org"), Xow_hzip_dict.Escape_bry);
		fxt.Test_save(brys, "<a xtid='a_lnke_txt' rel=\"nofollow\" class=\"external free\" href=\"http://a.org\">http://a.org</a>");
		fxt.Test_load(brys, "<a rel=\"nofollow\" class=\"external free\" href=\"http://a.org\">http://a.org</a>");
	}
	@Test   public void Srl_lnke_brk_y() {
		byte[][] brys = Bry_.Ary(Xow_hzip_dict.Bry_lnke_brk_text_y, Bry_.new_ascii_("http://a.org"), Xow_hzip_dict.Escape_bry, Bry_.new_ascii_("A1"), Xow_hzip_dict.Bry_a_rhs);
		fxt.Test_save(brys, "<a xtid='a_lnke_brk_y' class=\"external text\"  rel=\"nofollow\" href=\"http://a.org\">A1</a>");
		fxt.Test_load(brys, "<a rel=\"nofollow\" class=\"external text\" href=\"http://a.org\">A1</a>");
	}
	@Test   public void Srl_lnke_brk_n() {
		byte[][] brys = Bry_.Ary(Xow_hzip_dict.Bry_lnke_brk_text_n, Bry_.new_ascii_("http://a.org"), Xow_hzip_dict.Escape_bry, Xow_hzip_int_.Save_bin_int_abrv(123));
		fxt.Test_save(brys, "<a xtid='a_lnke_brk_n' class=\"external autonumber\"  rel=\"nofollow\" href=\"http://a.org\">[123]</a>");
		fxt.Test_load(brys, "<a rel=\"nofollow\" class=\"external autonumber\" href=\"http://a.org\">[123]</a>");
	}
	@Test   public void Html_lnki_ttl() {
		fxt.Test_html("[[A]]", "<a xtid='a_lnki_text_n' href=\"/wiki/A\" xowa_redlink='1'>A</a>");
	}
	@Test   public void Html_lnki_capt() {
		fxt.Test_html("[[A|a]]", "<a xtid='a_lnki_text_y' href=\"/wiki/A\" xowa_redlink='1'>a</a>");
	}
	@Test   public void Html_lnki_trail() {
		fxt.Test_html("[[A]]b", "<a xtid='a_lnki_text_y' href=\"/wiki/A\" xowa_redlink='1'>Ab</a>");
	}
	@Test   public void Html_lnke_txt() {
		fxt.Test_html("http://a.org", "<a xtid='a_lnke_txt' href=\"http://a.org\" class=\"external text\" rel=\"nofollow\">http://a.org</a>");
	}
	@Test   public void Html_lnke_brk_n() {
		fxt.Test_html("[http://a.org]", "<a xtid='a_lnke_brk_n' href=\"http://a.org\" class=\"external text\" rel=\"nofollow\">[1]</a>");
	}
	@Test   public void Html_lnke_brk_y() {
		fxt.Test_html("[http://a.org A]", "<a xtid='a_lnke_brk_y' href=\"http://a.org\" class=\"external text\" rel=\"nofollow\">A</a>");
	}
}
