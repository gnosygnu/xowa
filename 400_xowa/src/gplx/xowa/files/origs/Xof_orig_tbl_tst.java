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
package gplx.xowa.files.origs; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import org.junit.*;
import gplx.dbs.*; import gplx.xowa.*;
import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
public class Xof_orig_tbl_tst {
	@Before public void init() {fxt.Clear();} private Xof_orig_tbl_fxt fxt = new Xof_orig_tbl_fxt();
	@Test   public void Select_in() {
		Xof_orig_itm itm_1 = fxt.Exec_insert("A.png", 220, 330);
		fxt.Exec_insert("B.png", 220, 330);
		Xof_orig_itm itm_3 = fxt.Exec_insert("C.png", 220, 330);
		fxt.Test_select_in(String_.Ary("A.png", "C.png"), itm_1, itm_3);
	}
}
class Xof_orig_tbl_fxt {
	private Xof_orig_tbl tbl;
	public void Clear() {
		Io_url test_url = Io_url_.mem_fil_("mem/file/en.wikipedia.org/file/orig_regy");
		Db_conn conn = Db_conn_bldr.I.New(test_url);
		tbl = new Xof_orig_tbl(conn, Bool_.Y);
		tbl.Create_tbl();
	}
	public Xof_orig_itm Exec_insert(String ttl, int w, int h) {
		byte[] ttl_bry = Bry_.new_utf8_(ttl);
		Xof_orig_itm rv = new Xof_orig_itm().Init(Xof_orig_itm.Repo_comm, ttl_bry, Xof_ext_.new_by_ttl_(ttl_bry).Id(), w, h, Bry_.Empty);
		tbl.Insert(rv.Repo(), rv.Page(), rv.Ext(), rv.W(), rv.H(), rv.Redirect());
		return rv;
	}
	public void Test_select_in(String[] itms, Xof_orig_itm... expd) {
		OrderedHash rv = OrderedHash_.new_();
		ListAdp list = ListAdp_.new_();
		int itms_len = itms.length;
		for (int i = 0; i < itms_len; ++i) {
			String itm = itms[i];
			Xof_fsdb_itm fsdb_itm = new Xof_fsdb_itm();
			fsdb_itm.Ctor_by_lnki(Bry_.new_utf8_(itm), Xop_lnki_type.Id_none, Xof_img_size.Null, Xof_img_size.Null, Xof_patch_upright_tid_.Tid_all, Xof_img_size.Upright_null, Xof_lnki_time.Null, Xof_lnki_page.Null);
			list.Add(fsdb_itm);
		}
		tbl.Select_by_list(rv, list);
		Tfds.Eq_str_lines(To_str_ary(expd), To_str_ary((Xof_orig_itm[])rv.Xto_ary(Xof_orig_itm.class)));
	}
	private static String To_str_ary(Xof_orig_itm... ary) {
		Bry_bfr bfr = Bry_bfr.reset_(255);
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Xof_orig_itm itm = ary[i];
			bfr	.Add_byte(itm.Repo()).Add_byte_pipe()
				.Add(itm.Page()).Add_byte_pipe()
				.Add_int_variable(itm.Ext()).Add_byte_pipe()
				.Add_int_variable(itm.W()).Add_byte_pipe()
				.Add_int_variable(itm.H()).Add_byte_pipe()
				.Add(itm.Redirect()).Add_byte_pipe()
				;
			bfr.Add_byte_nl();
		}
		return bfr.Xto_str_and_clear();
	}
}
