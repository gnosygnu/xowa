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
package gplx.xowa2.files.orig_regy; import gplx.*; import gplx.xowa2.*; import gplx.xowa2.files.*;
import org.junit.*;
import gplx.dbs.*; import gplx.xowa.*; import gplx.xowa.files.wiki_orig.*;
public class Xof_orig_regy_tbl_tst {
	@Before public void init() {fxt.Clear();} private Xof_orig_regy_tbl_fxt fxt = new Xof_orig_regy_tbl_fxt();
	@Test   public void Select_in() {
		Xof_orig_regy_itm itm_1 = fxt.Exec_insert("A.png", 220, 330);
		fxt.Exec_insert("B.png", 220, 330);
		Xof_orig_regy_itm itm_3 = fxt.Exec_insert("C.png", 220, 330);
		fxt.Test_select_in(String_.Ary("A.png", "C.png"), itm_1, itm_3);
	}
}
class Xof_orig_regy_tbl_fxt {
	private Xof_orig_regy_tbl tbl = new Xof_orig_regy_tbl();
	public void Clear() {
		String test_url = "test/file/en.wikipedia.org/file/orig_regy";
		Db_conn conn = Db_conn_pool.I.Set_mem(test_url, tbl.Meta());
		tbl = new Xof_orig_regy_tbl();
		tbl.Conn_(conn);
	}
	public Xof_orig_regy_itm Exec_insert(String ttl, int w, int h) {
		byte[] ttl_bry = Bry_.new_utf8_(ttl);
		Xof_orig_regy_itm rv = new Xof_orig_regy_itm(ttl_bry, Xof_wiki_orig_wkr_.Tid_found_orig, Xof_repo_itm.Repo_local, w, h, Xof_ext_.new_by_ttl_(ttl_bry).Id(), Bry_.Empty);
		tbl.Insert(rv.Repo_tid(), rv.Ttl(), rv.Orig_ext(), rv.Orig_w(), rv.Orig_h(), rv.Orig_redirect());
		return rv;
	}
	public void Test_select_in(String[] itms, Xof_orig_regy_itm... expd) {
		OrderedHash rv = OrderedHash_.new_();
		tbl.Select_list(rv, ListAdp_.new_ary_(Bry_.Ary(itms)), Cancelable_.Never);
		Tfds.Eq_str_lines(To_str_ary(expd), To_str_ary((Xof_orig_regy_itm[])rv.Xto_ary(Xof_orig_regy_itm.class)));
	}
	private static String To_str_ary(Xof_orig_regy_itm... ary) {
		Bry_bfr bfr = Bry_bfr.reset_(255);
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Xof_orig_regy_itm itm = ary[i];
			bfr	.Add_byte(itm.Repo_tid()).Add_byte_pipe()
				.Add(itm.Ttl()).Add_byte_pipe()
				.Add_int_variable(itm.Orig_ext()).Add_byte_pipe()
				.Add_int_variable(itm.Orig_w()).Add_byte_pipe()
				.Add_int_variable(itm.Orig_h()).Add_byte_pipe()
				.Add(itm.Orig_redirect()).Add_byte_pipe()
				;
			bfr.Add_byte_nl();
		}
		return bfr.Xto_str_and_clear();
	}
}
