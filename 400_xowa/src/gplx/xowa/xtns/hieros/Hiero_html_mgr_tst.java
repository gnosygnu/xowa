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
package gplx.xowa.xtns.hieros; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Hiero_html_mgr_tst {
	@Before public void init() {fxt.Reset();} private Hiero_html_mgr_fxt fxt = new Hiero_html_mgr_fxt();
	@Test   public void Empty() {
		fxt.Test_html_full_str
		( "<hiero></hiero>"
		, String_.Concat_lines_nl_skip_last
		( "<table class='mw-hiero-table mw-hiero-outer' dir='ltr'>"
		, "  <tr>"
		, "    <td>"
		, ""
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test   public void Glyph_1() {
		fxt.Init_hiero_A1_B1();
		fxt.Test_html_full_str
		( "<hiero>A1</hiero>"
		, String_.Concat_lines_nl_skip_last
		( "<table class='mw-hiero-table mw-hiero-outer' dir='ltr'>"
		, "  <tr>"
		, "    <td>"
		, "      <table class=\"mw-hiero-table\">"
		, "        <tr>"
		, "          <td>"
		, "            <img style='margin: 1px; height: 38px;' src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_A1.png' title='A1' alt='A1' />"
		, "          </td>"
		, "        </tr>"
		, "      </table>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test   public void Mirrored() {
		fxt.Init_hiero_A1_B1();
		fxt.Test_html_full_str
		( "<hiero>A1\\</hiero>"
		, String_.Concat_lines_nl_skip_last
		( "<table class='mw-hiero-table mw-hiero-outer' dir='ltr'>"
		, "  <tr>"
		, "    <td>"
		, "      <table class=\"mw-hiero-table\">"
		, "        <tr>"
		, "          <td>"
		, "            <img class=\"mw-mirrored\" style='margin: 1px; height: 38px;' src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_A1.png' title='A1' alt='A1' />"
		, "          </td>"
		, "        </tr>"
		, "      </table>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test   public void Glyph_2() {
		fxt.Init_hiero_A1_B1();
		fxt.Test_html_full_str("<hiero>A1-B1</hiero>", String_.Concat_lines_nl_skip_last
		( "<table class='mw-hiero-table mw-hiero-outer' dir='ltr'>"
		, "  <tr>"
		, "    <td>"
		, "      <table class=\"mw-hiero-table\">"
		, "        <tr>"
		, "          <td>"
		, "            <img style='margin: 1px; height: 38px;' src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_A1.png' title='A1' alt='A1' />"
		, "          </td>"
		, "          <td>"
		, "            <img style='margin: 1px; height: 38px;' src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_B1.png' title='B1' alt='B1' />"
		, "          </td>"
		, "        </tr>"
		, "      </table>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test   public void Void_half() {
		fxt.Init_hiero_A1_B1();
		fxt.Test_html_full_str
		( "<hiero>A1 . B1</hiero>"
		, String_.Concat_lines_nl_skip_last
		( "<table class='mw-hiero-table mw-hiero-outer' dir='ltr'>"
		, "  <tr>"
		, "    <td>"
		, "      <table class=\"mw-hiero-table\">"
		, "        <tr>"
		, "          <td>"
		, "            <img style='margin: 1px; height: 38px;' src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_A1.png' title='A1' alt='A1' />"
		, "          </td>"
		, "          <td>"
		, "            <table class=\"mw-hiero-table\" style=\"width: 22px;\">"
		, "              <tr>"
		, "                <td>&#160;"
		, "                </td>"
		, "              </tr>"
		, "            </table>"
		, "          </td>"
		, "          <td>"
		, "            <img style='margin: 1px; height: 38px;' src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_B1.png' title='B1' alt='B1' />"
		, "          </td>"
		, "        </tr>"
		, "      </table>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test   public void Void_full() {
		fxt.Init_hiero_A1_B1();
		fxt.Test_html_full_str
		( "<hiero>A1 .. B1</hiero>"
		, String_.Concat_lines_nl_skip_last
		( "<table class='mw-hiero-table mw-hiero-outer' dir='ltr'>"
		, "  <tr>"
		, "    <td>"
		, "      <table class=\"mw-hiero-table\">"
		, "        <tr>"
		, "          <td>"
		, "            <img style='margin: 1px; height: 38px;' src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_A1.png' title='A1' alt='A1' />"
		, "          </td>"
		, "          <td>"
		, "            <table class=\"mw-hiero-table\" style=\"width: 44px;\">"
		, "              <tr>"
		, "                <td>&#160;"
		, "                </td>"
		, "              </tr>"
		, "            </table>"
		, "          </td>"
		, "          <td>"
		, "            <img style='margin: 1px; height: 38px;' src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_B1.png' title='B1' alt='B1' />"
		, "          </td>"
		, "        </tr>"
		, "      </table>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test   public void New_line() {
		fxt.Init_hiero_A1_B1();
		fxt.Test_html_full_str
		( "<hiero>A1 ! B1</hiero>"
		, String_.Concat_lines_nl_skip_last
		( "<table class='mw-hiero-table mw-hiero-outer' dir='ltr'>"
		, "  <tr>"
		, "    <td>"
		, "      <table class=\"mw-hiero-table\">"
		, "        <tr>"
		, "          <td>"
		, "            <img style='margin: 1px; height: 38px;' src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_A1.png' title='A1' alt='A1' />"
		, "          </td>"
		, "        </tr>"
		, "      </table>"
		, "      <table class=\"mw-hiero-table\">"
		, "        <tr>"
		, "          <td>"
		, "            <img style='margin: 1px; height: 38px;' src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_B1.png' title='B1' alt='B1' />"
		, "          </td>"
		, "        </tr>"
		, "      </table>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test   public void Cartouche() {
		fxt.Init_hiero_A1_B1().Init_hiero_cartouche();
		fxt.Test_html_full_str
		( "<hiero>< A1 ></hiero>"
		, String_.Concat_lines_nl_skip_last
		( "<table class='mw-hiero-table mw-hiero-outer' dir='ltr'>"
		, "  <tr>"
		, "    <td>"
		, "      <table class=\"mw-hiero-table\">"
		, "        <tr>"
		, "          <td>"
		, "            <img src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_Ca1.png' height='44' title='&lt;' alt='&lt;' />"
		, "          </td>"
		, "          <td>"
		, "            <table class=\"mw-hiero-table\">"
		, "              <tr>"
		, "                <td class='mw-hiero-box' style='height: 2px;'>"
		, "                </td>"
		, "              </tr>"
		, "              <tr>"
		, "                <td>"
		, "                  <table class=\"mw-hiero-table\">"
		, "                    <tr>"
		, "          <td>"
		, "            <img style='margin: 1px; height: 38px;' src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_A1.png' title='A1' alt='A1' />"
		, "          </td>"
		, "                    </tr>"
		, "                  </table>"
		, "                </td>"
		, "              </tr>"
		, "              <tr>"
		, "                <td class='mw-hiero-box' style='height: 2px;'>"
		, "                </td>"
		, "              </tr>"
		, "            </table>"
		, "          </td>"
		, "          <td>"
		, "            <img src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_Ca2.png' height='44' title='&gt;' alt='&gt;' />"
		, "          </td>"
		, "        </tr>"
		, "      </table>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test   public void Superposition_regular() {
		fxt.Init_hiero_A1_B1();
		fxt.Test_html_full_str("<hiero>A1:B1</hiero>", String_.Concat_lines_nl_skip_last
		( "<table class='mw-hiero-table mw-hiero-outer' dir='ltr'>"
		, "  <tr>"
		, "    <td>"
		, "      <table class=\"mw-hiero-table\">"
		, "        <tr>"
		, "          <td>"
		, "            <img style='margin: 1px; height: 20px;' src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_A1.png' title='A1' alt='A1' />"
		, "            <br/>"
		, "            <img style='margin: 1px; height: 20px;' src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_B1.png' title='B1' alt='B1' />"
		, "          </td>"
		, "        </tr>"
		, "      </table>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test   public void Superposition_phoneme() {
		fxt.Init_hiero_p_t();
		fxt.Test_html_full_str("<hiero>t:p</hiero>", String_.Concat_lines_nl_skip_last
		( "<table class='mw-hiero-table mw-hiero-outer' dir='ltr'>"
		, "  <tr>"
		, "    <td>"
		, "      <table class=\"mw-hiero-table\">"
		, "        <tr>"
		, "          <td>"
		, "            <img style='margin: 1px; height: 11px;' src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_X1.png' title='X1 [t]' alt='t' />"
		, "            <br/>"
		, "            <img style='margin: 1px; height: 15px;' src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_Q3.png' title='Q3 [p]' alt='p' />"
		, "          </td>"
		, "        </tr>"
		, "      </table>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test   public void Superposition_prefab() {
		fxt.Init_hiero_a_A1();
		fxt.Test_html_full_str("<hiero>a:A1</hiero>", String_.Concat_lines_nl_skip_last
		( "<table class='mw-hiero-table mw-hiero-outer' dir='ltr'>"
		, "  <tr>"
		, "    <td>"
		, "      <table class=\"mw-hiero-table\">"
		, "        <tr>"
		, "          <td>"
		, "            <img style='margin: 1px; height: 38px;' src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_a&amp;A1.png' title='a&amp;A1' alt='a&amp;A1' />"
		, "          </td>"
		, "        </tr>"
		, "      </table>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test   public void Juxtaposition_regular() {
		fxt.Init_hiero_A1_B1();
		fxt.Test_html_full_str("<hiero>A1*B1</hiero>", String_.Concat_lines_nl_skip_last
		( "<table class='mw-hiero-table mw-hiero-outer' dir='ltr'>"
		, "  <tr>"
		, "    <td>"
		, "      <table class=\"mw-hiero-table\">"
		, "        <tr>"
		, "          <td>"
		, "            <img style='margin: 1px; height: 38px;' src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_A1.png' title='A1' alt='A1' /> "
		, "            <img style='margin: 1px; height: 38px;' src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_B1.png' title='B1' alt='B1' />"
		, "          </td>"
		, "        </tr>"
		, "      </table>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test   public void Juxtaposition_phoneme() {
		fxt.Init_hiero_p_t();
		fxt.Test_html_full_str("<hiero>t*p</hiero>", String_.Concat_lines_nl_skip_last
		( "<table class='mw-hiero-table mw-hiero-outer' dir='ltr'>"
		, "  <tr>"
		, "    <td>"
		, "      <table class=\"mw-hiero-table\">"
		, "        <tr>"
		, "          <td>"
		, "            <img style='margin: 1px; height: 11px;' src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_X1.png' title='X1 [t]' alt='t' /> "
		, "            <img style='margin: 1px; height: 15px;' src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_Q3.png' title='Q3 [p]' alt='p' />"
		, "          </td>"
		, "        </tr>"
		, "      </table>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test   public void Juxtaposition_prefab() {
		fxt.Init_hiero_a_A1();
		fxt.Test_html_full_str("<hiero>a*A1</hiero>", String_.Concat_lines_nl_skip_last
		( "<table class='mw-hiero-table mw-hiero-outer' dir='ltr'>"
		, "  <tr>"
		, "    <td>"
		, "      <table class=\"mw-hiero-table\">"
		, "        <tr>"
		, "          <td>"
		, "            <img style='margin: 1px; height: 38px;' src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_a&amp;A1.png' title='a&amp;A1' alt='a&amp;A1' />"
		, "          </td>"
		, "        </tr>"
		, "      </table>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test   public void Para_if_text() {// PURPOSE: check that paras are handled correctly; EX: w:Hieroglyphics; DATE:2014-04-23
		fxt.Fxt().Init_para_y_();
		fxt.Test_html_full_str(String_.Concat_lines_nl_skip_last
		( "a"	// should always be in <p>
		, ""
		, "<hiero></hiero> b"	// <hiero> should not be in <p> but "b" should be;
		), String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "</p>"
		, "<table class='mw-hiero-table mw-hiero-outer' dir='ltr'>"
		, "  <tr>"
		, "    <td>"
		, ""
		, "    </td>"
		, "  </tr>"
		, "</table>"
		, ""
		, "<p> b"	// NOTE: space seems wrong, but is "harmless"
		, "</p>"
		));
		fxt.Fxt().Init_para_n_();
	}		
	@Test   public void Para_skip_if_list() {// PURPOSE: do not add para if in list; EX:de.d:Damascus; DATE:2014-06-06
		fxt.Fxt().Init_para_y_();
		fxt.Test_html_full_str(String_.Concat_lines_nl_skip_last
		( ":<hiero></hiero> a"	// a should not be in para
		, ":b"
		), String_.Concat_lines_nl_skip_last
		( "<dl>"
		, "  <dd><table class='mw-hiero-table mw-hiero-outer' dir='ltr'>"
		, "  <tr>"
		, "    <td>"
		, ""
		, "    </td>"
		, "  </tr>"
		, "</table>"
		, " a"
		, "  </dd>"
		, "  <dd>b"
		, "  </dd>"
		, "</dl>"
		));
		fxt.Fxt().Init_para_n_();
	}
}
class Hiero_html_mgr_fxt {
	private Hiero_xtn_mgr xtn_mgr;
	public Xop_fxt Fxt() {return fxt;} private Xop_fxt fxt = new Xop_fxt();
	public void Reset() {
		fxt.Reset();
		xtn_mgr = new Hiero_xtn_mgr();
		xtn_mgr.Xtn_init_by_app(fxt.App());
		xtn_mgr.Clear();
	}
	public Hiero_html_mgr_fxt Init_hiero_A1_B1() {
		this.Init_file("A1", 29, 38);
		this.Init_file("B1", 23, 38);
		return this;
	}
	public Hiero_html_mgr_fxt Init_hiero_cartouche() {
		this.Init_phoneme("<", "Ca1");
		this.Init_phoneme(">", "Ca2");
		return this;
	}
	public Hiero_html_mgr_fxt Init_hiero_p_t() {
		this.Init_phoneme("p", "Q3");
		this.Init_phoneme("t", "X1");
		this.Init_file("Q3", 12, 15);
		this.Init_file("X1", 20, 11);
		return this;
	}
	public Hiero_html_mgr_fxt Init_hiero_a_A1() {
		this.Init_prefab("a&A1");
		this.Init_file("a&A1", 37, 38);
		return this;
	}
	public Hiero_html_mgr_fxt Init_prefab(String prefab)					{xtn_mgr.Prefab_mgr().Add(Bry_.new_utf8_(prefab)); return this;}
	public Hiero_html_mgr_fxt Init_file(String s, int w, int h)				{xtn_mgr.File_mgr().Add(Bry_.new_utf8_(s), w, h); return this;}
	public Hiero_html_mgr_fxt Init_phoneme(String phoneme, String code)		{xtn_mgr.Phoneme_mgr().Add(Bry_.new_utf8_(phoneme), Bry_.new_utf8_(code)); return this;}
	public void Test_html_full_str(String raw, String expd)					{fxt.Test_html_full_str(raw, expd);}
}	
