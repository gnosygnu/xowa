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
package gplx.xowa.wikis.data.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
import org.junit.*; import gplx.dbs.*;
import gplx.xowa.nss.*;
public class Xowd_wbase_qid_tbl_tst {
	private final Xowd_wbase_qid_tbl_fxt fxt = new Xowd_wbase_qid_tbl_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Space() {
		fxt.Exec_insert("enwiki", Xow_ns_.Id_main, "A B", "q1");
		fxt.Test_select("enwiki", Xow_ns_.Id_main, "A B", "q1");
		fxt.Test_select("enwiki", Xow_ns_.Id_main, "A_B", "q1");
	}
}
class Xowd_wbase_qid_tbl_fxt {
	private Xowd_wbase_qid_tbl qid_tbl;
	public void Clear() {
		Io_mgr.I.InitEngine_mem();
		Db_conn_bldr.I.Reg_default_mem();
		Db_conn conn = Db_conn_bldr.I.New(Io_url_.mem_fil_("mem/db/wbase.xowa"));
		this.qid_tbl = new Xowd_wbase_qid_tbl(conn, Bool_.N, Bool_.Y);	// simulate v2.4.2 with bad "spaces"
		qid_tbl.Create_tbl();
	}
	public void Exec_insert(String src_wiki, int src_ns, String src_ttl, String trg_ttl) {
		qid_tbl.Insert_bgn();
		qid_tbl.Insert_cmd_by_batch(Bry_.new_u8(src_wiki), src_ns, Bry_.new_u8(src_ttl), Bry_.new_u8(trg_ttl));
		qid_tbl.Insert_end();
	}
	public void Test_select(String src_wiki, int src_ns, String src_ttl, String expd) {
		byte[] actl = qid_tbl.Select_qid(Bry_.new_u8(src_wiki), Bry_.new_a7(Int_.Xto_str(src_ns)), Bry_.new_u8(src_ttl));
		Tfds.Eq(expd, String_.new_u8(actl));
	}
}
