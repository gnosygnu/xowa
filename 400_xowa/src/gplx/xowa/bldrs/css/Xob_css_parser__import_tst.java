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
package gplx.xowa.bldrs.css; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import org.junit.*;
public class Xob_css_parser__import_tst {
	@Before public void init() {fxt.Clear();} private Xob_css_parser__import_fxt fxt = new Xob_css_parser__import_fxt();
	@Test   public void Basic()				{fxt.Test_parse_import	(" @import url(//site/a.png)"	, " @import url('site/a.png')");}
	@Test   public void Warn_eos()			{fxt.Test_parse_warn	(" @import"						, " @import"				, "EOS");}
	@Test   public void Warn_missing()		{fxt.Test_parse_warn	(" @import ('//site/a.png')"	, " @import"				, "missing");}	// no "url("
	@Test   public void Warn_invalid()		{fxt.Test_parse_warn	(" @import url('//site')"		, " @import url('//site')"	, "invalid");}	// invalid
}
class Xob_css_parser__import_fxt extends Xob_css_parser__url_fxt {		private Xob_css_parser__import import_parser;
	@Override public void Clear() {
		super.Clear();
		this.import_parser = new Xob_css_parser__import(url_parser);
	}
	@Override protected void Exec_parse_hook() {
		this.cur_frag = import_parser.Parse(src_bry, src_bry.length, 0, 8); // 8=" @import".length
	}
	public void Test_parse_import(String src_str, String expd) {
		Exec_parse(src_str, Xob_css_tkn__base.Tid_import, expd);
	}
}	
