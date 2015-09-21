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
public class Json_doc_tst {
	private final Json_qry_mgr_fxt fxt = new Json_qry_mgr_fxt();
	@Test  public void Select() {
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
		byte[][] qry_bry = Bry_split_.Split(Bry_.new_u8(qry), Byte_ascii.Slash);
		Tfds.Eq(expd, doc.Get_val_as_str_or(qry_bry, null));
	}
}
