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
import org.junit.*; import gplx.core.tests.*;
public class Json_doc_wtr_tst {
	private final    Json_doc_wtr_fxt fxt = new Json_doc_wtr_fxt();
	@Test  public void Basic() {
		fxt.Test__Bld_as_str
		( fxt.Exec__Kv_simple("k1", "v\"1")
		, fxt.Exec__Concat_apos
		( "{"
		, "  'k1':'v\\\"1'"
		, "}"));
	}
}
class Json_doc_wtr_fxt {
	public Json_doc_wtr Exec__Kv_simple(String key, String val) {
		Json_doc_wtr doc_wtr = new Json_doc_wtr();
		doc_wtr.Nde_bgn();
		doc_wtr.Kv(Bool_.N, Bry_.new_u8(key), Bry_.new_u8(val));
		doc_wtr.Nde_end();
		return doc_wtr;
	}
	public void Test__Bld_as_str(Json_doc_wtr doc_wtr, String expd) {
		Gftest.Eq__ary__lines(expd, doc_wtr.Bld_as_str());
	}
	public String Exec__Concat_apos(String... ary) {
		return Json_doc.Make_str_by_apos(ary);
	}
}
