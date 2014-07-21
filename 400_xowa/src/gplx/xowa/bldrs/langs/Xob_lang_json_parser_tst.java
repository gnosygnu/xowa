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
package gplx.xowa.bldrs.langs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import org.junit.*;
import gplx.intl.*;
public class Xob_lang_json_parser_tst {
	@Before public void init() {fxt.Clear();} private Xob_lang_json_parser_fxt fxt = new Xob_lang_json_parser_fxt();
	@Test  public void Core_keywords() {
		fxt.Test_parse(String_.Concat_lines_nl_skip_last
		( "{"
		, "    \"@metadata\": {"
		, "        \"authors\": []"
		, "    },"
		, "\"key_1\": \"val_1\","
		, "\"key_2\": \"val_2\","
		, "}"
		), String_.Concat_lines_nl_skip_last
		( "this.messages.load_text("
		, "<:['"
		, "key_1|val_1"
		, "key_2|val_2"
		, "']:>"
		, ");"
		));
	}
}
class Xob_lang_json_parser_fxt {
	private Xob_lang_json_parser parser = new Xob_lang_json_parser();
	public void Clear() {
	}
	public void Test_parse(String raw, String expd) {
		byte[] actl = parser.Parse(Bry_.new_utf8_(raw));
		Tfds.Eq_str_lines(expd, String_.new_utf8_(actl));
	}
}
