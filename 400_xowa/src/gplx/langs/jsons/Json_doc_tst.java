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
package gplx.langs.jsons;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.BrySplit;
import gplx.types.basics.constants.AsciiByte;
import org.junit.*;
public class Json_doc_tst {
	private final Json_qry_mgr_fxt fxt = new Json_qry_mgr_fxt();
	@Test public void Select() {
		Json_doc doc = fxt.Make_json
		(	"{'0':"
		,	"  {'0_0':"
		,	"    {'0_0_0':'000'"
		,	"    },"
		,	"   '0_1':"
		,	"    {'0_1_0':'010'"
		,	"    }"
		,	"  }"
		,	"}"
		);
		fxt.Test_get_val_as_str(doc, "0/0_0/0_0_0", "000");
		fxt.Test_get_val_as_str(doc, "0/0_1/0_1_0", "010");
		fxt.Test_get_val_as_str(doc, "x", null);
	}
}
class Json_qry_mgr_fxt {
	private final Json_parser json_parser = new Json_parser();
	public Json_doc Make_json(String... ary) {return json_parser.Parse_by_apos_ary(ary);}
	public void Test_get_val_as_str(Json_doc doc, String qry, String expd){
		byte[][] qry_bry = BrySplit.Split(BryUtl.NewU8(qry), AsciiByte.Slash);
		GfoTstr.EqObj(expd, doc.Get_val_as_str_or(qry_bry, null));
	}
}
