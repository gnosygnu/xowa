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
package gplx.xowa.parsers.hdrs.sections; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.hdrs.*;
import org.junit.*; import gplx.core.tests.*;
public class Xop_section_mgr__tst {
	private final    Xop_section_mgr__fxt fxt = new Xop_section_mgr__fxt();
	@Test   public void Basic() {
		fxt.Test__insert("Page_1", String_.Concat_lines_nl_skip_last
		( "A"
		, "== Hdr_1 =="
		, "B"
		, "== Hdr_2 =="
		, "C"
		), String_.Concat_lines_nl_skip_last
		( "A"
		, "== Hdr_1 ==<!--xo_meta|section_edit|Page_1|1-->"
		, "B"
		, "== Hdr_2 ==<!--xo_meta|section_edit|Page_1|2-->"
		, "C"
		));
	}
}
class Xop_section_mgr__fxt {
	private final    Xowe_wiki wiki;
	private final    Xop_section_mgr mgr = new Xop_section_mgr();
	public Xop_section_mgr__fxt() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
	}
	public void Test__insert(String page, String raw, String expd) {
		Xoa_ttl ttl = wiki.Ttl_parse(Bry_.new_u8(page));
		byte[] actl = mgr.Insert(ttl, Bry_.new_u8(raw));
		Gftest.Eq__ary__lines(expd, actl, "section_edit:insert");
	}
}
