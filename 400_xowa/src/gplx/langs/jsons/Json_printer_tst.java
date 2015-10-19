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
package gplx.langs.jsons; import gplx.*; import gplx.langs.*;
import org.junit.*;
public class Json_printer_tst {
	private final Json_printer_fxt fxt = new Json_printer_fxt();
	@Test   public void Root_nde() {
		fxt.Test_print(Json_doc.Make_str_by_apos("{'k1':'v1','k2':'v2'}"), String_.Concat_lines_nl
		( "{ 'k1':'v1'"
		, ", 'k2':'v2'"
		, "}"
		));
	}
	@Test   public void Root_ary() {
		fxt.Test_print(Json_doc.Make_str_by_apos("[1,2,3]"), String_.Concat_lines_nl
		( "[ 1"
		, ", 2"
		, ", 3"
		, "]"
		));
	}
	@Test   public void Ary_w_ary() {
		fxt.Test_print(Json_doc.Make_str_by_apos("[[1,2],[3,4]]"), String_.Concat_lines_nl
		( "[ "
		, "  [ 1"
		, "  , 2"
		, "  ]"
		, ", "
		, "  [ 3"
		, "  , 4"
		, "  ]"
		, "]"
		));
	}
	@Test   public void Ary_w_nde() {
		fxt.Test_print(Json_doc.Make_str_by_apos("[{'k1':'v1','k2':'v2'},{'k3':'v3','k4':'v4'}]"), String_.Concat_lines_nl
		( "[ "
		, "  { 'k1':'v1'"
		, "  , 'k2':'v2'"
		, "  }"
		, ", "
		, "  { 'k3':'v3'"
		, "  , 'k4':'v4'"
		, "  }"
		, "]"
		));
	}
	@Test   public void Nde_w_ary() {
		fxt.Test_print(Json_doc.Make_str_by_apos("{'k1':[1,2],'k2':[3,4]}"), String_.Concat_lines_nl
		( "{ 'k1':"
		, "  [ 1"
		, "  , 2"
		, "  ]"
		, ", 'k2':"
		, "  [ 3"
		, "  , 4"
		, "  ]"
		, "}"
		));
	}
//		@Test   public void Smoke() {
//			Json_printer printer = new Json_printer();
//			String url = "C:\\temp.json";
//			String s = printer.Pretty_print_as_str(Bry_.new_u8(Io_mgr.Instance.LoadFilStr(url)));
//			Io_mgr.Instance.SaveFilStr(url, s);
//		}
}
class Json_printer_fxt {
	private final Json_printer printer = new Json_printer().Opt_quote_byte_(Byte_ascii.Apos);
	public void Test_print(String raw, String expd) {
		Tfds.Eq_str_lines(expd, printer.Print_by_bry(Bry_.new_u8(raw)).To_str());
	}
}
