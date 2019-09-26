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
import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.core.makes.tests.*;
class Hiero_html_fxt {
	private final    Hiero_html_mgr_fxt hiero_fxt = new Hiero_html_mgr_fxt(new Xop_fxt());
	private final    Xoh_make_fxt make_fxt = new Xoh_make_fxt();
	private final    Xop_fxt parser_fxt;
	private String hdump_atr;
	public Hiero_html_fxt() {
		this.parser_fxt = hiero_fxt.Fxt();
	}
	public Hiero_html_mgr_fxt Hiero_fxt() {return hiero_fxt;}
	public Hiero_html_fxt Hdump_n_() {return Hdump_(Bool_.N);}
	public Hiero_html_fxt Hdump_y_() {return Hdump_(Bool_.Y);}
	private Hiero_html_fxt Hdump_(boolean v) {
		this.hdump_atr = v ? " " + String_.new_u8(Hiero_hdump_wkr.HDUMP_ATR) : " ";
		return this;
	}
	public void Test__hview(String wtxt, String expd) {
		parser_fxt.Hctx_(Xoh_wtr_ctx.Basic);
		parser_fxt.Test_html_full_str(wtxt, expd);
	}
	public void Test__hdump(String wtxt, String save, String load) {
		parser_fxt.Hctx_(Xoh_wtr_ctx.Hdump);
		parser_fxt.Test_html_full_str(wtxt, save);
		make_fxt.Test__make(save, make_fxt.Page_chkr().Body_(load));
	}
	public String Glyph_1__wtxt() {
		hiero_fxt.Init_hiero_A1_B1();
		return "<hiero>A1</hiero>";
	}
	public String Glyph_1__html(boolean hiero_dir_has_value) {
		String hiero_dir = Hiero_dir(hiero_dir_has_value);
		return String_.Concat_lines_nl
		( "<table class='mw-hiero-table mw-hiero-outer' dir='ltr'>"
		, "  <tr>"
		, "    <td>"
		, "      <table class=\"mw-hiero-table\">"
		, "        <tr>"
		, "          <td>"
		, "            <img style='margin: 1px; height: 38px;' src='" + hiero_dir + "hiero_A1.png' title='A1' alt='A1'" + hdump_atr + "/>"
		, "          </td>"
		, "        </tr>"
		, "      </table>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		);
	}
	public String Cartouche__wtxt() {
		hiero_fxt.Init_hiero_A1_B1().Init_hiero_cartouche();
		return "<hiero>< A1 ></hiero>";
	}
	public String Cartouche__html(boolean hiero_dir_has_value) {
		String hiero_dir = Hiero_dir(hiero_dir_has_value);
		return String_.Concat_lines_nl_skip_last
		( "<table class='mw-hiero-table mw-hiero-outer' dir='ltr'>"
		, "  <tr>"
		, "    <td>"
		, "      <table class=\"mw-hiero-table\">"
		, "        <tr>"
		, "          <td>"
		, "            <img src='" + hiero_dir + "hiero_Ca1.png' height='44' title='&lt;' alt='&lt;'" + hdump_atr + "/>"
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
		, "            <img style='margin: 1px; height: 38px;' src='" + hiero_dir + "hiero_A1.png' title='A1' alt='A1'" + hdump_atr + "/>"
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
		, "            <img src='" + hiero_dir + "hiero_Ca2.png' height='44' title='&gt;' alt='&gt;'" + hdump_atr + "/>"
		, "          </td>"
		, "        </tr>"
		, "      </table>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		);
	}
	private String Hiero_dir(boolean hiero_dir_has_value) {
		return hiero_dir_has_value ? "file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/" : "";
	}
}
