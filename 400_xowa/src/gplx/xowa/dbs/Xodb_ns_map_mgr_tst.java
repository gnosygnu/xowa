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
package gplx.xowa.dbs; import gplx.*; import gplx.xowa.*;
import org.junit.*; import gplx.dbs.*;
public class Xodb_ns_map_mgr_tst {
	@Before public void init() {fxt.Clear();} private Xodb_ns_map_mgr_fxt fxt = new Xodb_ns_map_mgr_fxt();
	@Test   public void Basic() {
		fxt.Test_parse("");
	}
	@Test   public void Ns() {
		fxt.Test_parse(String_.Concat_lines_nl
		(	""
		,	"Template"
		,	""
		,	"File~Category"
		,	""
		)
		, fxt.itm_(Xow_ns_.Id_template), fxt.itm_(Xow_ns_.Id_file, Xow_ns_.Id_category));
	}
}
class Xodb_ns_map_mgr_fxt {
	public void Clear() {} Bry_bfr bfr = Bry_bfr.new_();
	public Xodb_ns_map_itm itm_(int... ary) {return new Xodb_ns_map_itm(ary);}
	public void Test_parse(String src_str, Xodb_ns_map_itm... expd) {
		byte[] src_bry = Bry_.new_ascii_(src_str);
		Xodb_ns_map_mgr actl_mgr = Xodb_ns_map_mgr.Parse(src_bry);
		Tfds.Eq_str_lines(Xto_str(expd), Xto_str(actl_mgr.Itms()));
	}
	String Xto_str(Xodb_ns_map_itm[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Xodb_ns_map_itm itm = ary[i];
			int[] ns_ids = itm.Ns_ids();
			int ns_ids_len = ns_ids.length;
			for (int j = 0; j < ns_ids_len; j++) {
				bfr.Add_int_variable(ns_ids[j]).Add_byte_pipe();
			}
			bfr.Add_byte_nl();
		}
		return bfr.Xto_str_and_clear();
	}
}
