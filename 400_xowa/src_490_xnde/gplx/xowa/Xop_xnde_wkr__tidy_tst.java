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
package gplx.xowa; import gplx.*;
import org.junit.*;
public class Xop_xnde_wkr__tidy_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Sub_sup_autocorrect() {
		fxt.Test_parse_page_wiki_str("<sub>a</sup>b", "<sub>a</sub>b");
		fxt.Test_parse_page_wiki_str("<sup>a</sub>b", "<sup>a</sup>b");
	}
	@Test  public void Span_font_autocorrect() {	// PURPOSE: force </font> to close <span>; EX:w:Rupee; DATE:2014-04-07
		fxt.Test_parse_page_wiki_str("<span>a</font>b", "<span>a</span>b");
	}
	@Test  public void Move_ws_char() {
		fxt.Test_parse_page_all_str("a<i> b </i>c", "a <i>b</i> c");
	}
	@Test  public void Move_ws_ent() {
		fxt.Test_parse_page_all_str("a<i>&#32;b&#32;</i>c", "a&#32;<i>b</i>&#32;c");
	}
	@Test  public void Ignore_empty_tags() {	// PURPOSE: ignore tag if marked ignore_empty; EX:uk.b:HTML; DATE:2014-03-12
		fxt.Test_parse_page_all_str("a<pre></pre>b", "ab");
	}
//		@Test  public void Escaped_div() {	// NOTE: WP <div><span>a</span></div><span>b</span>; MW: <div><span>a</div>b</span> // REVISIT: 2012-05-11; WP does harder split-span
//			fxt.Init_log_(Xop_xnde_log.Auto_closing_section, Xop_xnde_log.Escaped_xnde).Test_parse_page_wiki("<div><span></div></span>"
//				, fxt.tkn_xnde_(0, 17).Subs_
//				(	fxt.tkn_xnde_(5, 11))
//				, fxt.tkn_ignore_(17, 24)
//				);
//		}
}
