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
package gplx.xowa.htmls.core.wkrs.hdrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.hzips.tests.*;
public class Xoh_hdr_hzip_tst {
	private final Xoh_hzip_fxt fxt = new Xoh_hzip_fxt();
	@Test   public void Diff() {
		String html = String_.Concat_lines_nl_skip_last
		( "<h2 data-xotype='hdr_bgn'>"
		, "  <span class='mw-headline' id='A'><i>A</i><!--xo.hdr--></span>"
		, "</h2>"
		, "a"
		);
		String hzip = String_.Concat_lines_nl_skip_last
		( "~\"2<i>A</i>~A~"
		, "a"
		);
		fxt.Test__bicode(hzip, html);
	}
	@Test   public void Same() {
		String html = String_.Concat_lines_nl_skip_last
		( "<h6 data-xotype='hdr_bgn'>"
		, "  <span class='mw-headline' id='A'>A<!--xo.hdr--></span>"
		, "</h6>"
		, "a"
		);
		String hzip = String_.Concat_lines_nl_skip_last
		( "~\"6A~~"
		, "a"
		);
		fxt.Test__bicode(hzip, html);
	}
	@Test   public void Diff_by_underscore() {
		String html = String_.Concat_lines_nl_skip_last
		( "<h2 data-xotype='hdr_bgn'>"
		, "  <span class='mw-headline' id='A_1'>A 1<!--xo.hdr--></span>"
		, "</h2>"
		, "a"
		);
		String hzip = String_.Concat_lines_nl_skip_last
		( "~\"2A 1~~"
		, "a"
		);
		fxt.Test__bicode(hzip, html);
	}
	@Test   public void Same_w_underscore() {
		String html = String_.Concat_lines_nl_skip_last
		( "<h2 data-xotype='hdr_bgn'>"
		, "  <span class='mw-headline' id='A_1'>A_1<!--xo.hdr--></span>"
		, "</h2>"
		, "a"
		);
		String hzip = String_.Concat_lines_nl_skip_last
		( "~\"2A_1~~"
		, "a"
		);
		fxt.Test__bicode(hzip, html);
	}
}
