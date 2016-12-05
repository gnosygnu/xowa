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
public class Xop_section_list__tst {
	private final    Xop_section_list__fxt fxt = new Xop_section_list__fxt();
	@Test   public void Basic() {
		fxt.Exec__parse
		( "== Hdr 1 =="
		, "Para 1"
		, ""
		, "== Hdr 2 =="
		, "Para 2"
		, ""
		, "== Hdr 3 =="
		, "Para 3"
		);
		fxt.Test__extract_bry_or_null("Hdr 1"
		, "== Hdr 1 =="
		, "Para 1"
		);
		fxt.Test__extract_bry_or_null("Hdr 2"
		, "== Hdr 2 =="
		, "Para 2"
		);
		fxt.Test__extract_bry_or_null("Hdr 3"
		, "== Hdr 3 =="
		, "Para 3"
		);
	}
}
class Xop_section_list__fxt {
	private final    Xop_section_list list = new Xop_section_list();
	public void Exec__parse(String... lines) {
		list.Parse(Bry_.Empty, Bry_.new_u8(String_.Concat_lines_nl_skip_last(lines)));
	}
	public void Test__extract_bry_or_null(String key, String... lines) {
		String expd = String_.Concat_lines_nl_skip_last(lines);
		byte[] actl = list.Extract_bry_or_null(Bry_.new_u8(key));
		Gftest.Eq__ary__lines(expd, actl, key);
	}
}
