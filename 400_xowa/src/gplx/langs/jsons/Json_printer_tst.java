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
