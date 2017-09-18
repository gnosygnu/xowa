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
package gplx.xowa.xtns.hieros; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Hiero_html_mgr_tst {
	@Before public void init() {fxt.Reset();} private Hiero_html_mgr_fxt fxt = new Hiero_html_mgr_fxt(new Xop_fxt());
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
