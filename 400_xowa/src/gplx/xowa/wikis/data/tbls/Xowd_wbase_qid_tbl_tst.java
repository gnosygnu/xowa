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
package gplx.xowa.wikis.data.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
import org.junit.*; import gplx.dbs.*;
import gplx.xowa.wikis.nss.*;
public class Xowd_wbase_qid_tbl_tst {
	private final Xowd_wbase_qid_tbl_fxt fxt = new Xowd_wbase_qid_tbl_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Space() {
		fxt.Exec_insert("enwiki", Xow_ns_.Tid__main, "A B", "q1");
		fxt.Test_select("enwiki", Xow_ns_.Tid__main, "A B", "q1");
		fxt.Test_select("enwiki", Xow_ns_.Tid__main, "A_B", "q1");
	}
}
class Xowd_wbase_qid_tbl_fxt {
	private Xowd_wbase_qid_tbl qid_tbl;
	public void Clear() {
		Io_mgr.Instance.InitEngine_mem();
		Db_conn_bldr.Instance.Reg_default_mem();
		Db_conn conn = Db_conn_bldr.Instance.New(Io_url_.mem_fil_("mem/db/wbase.xowa"));
		this.qid_tbl = new Xowd_wbase_qid_tbl(conn, Bool_.N, Bool_.Y);	// simulate v2.4.2 with bad "spaces"
		qid_tbl.Create_tbl();
	}
	public void Exec_insert(String src_wiki, int src_ns, String src_ttl, String trg_ttl) {
		qid_tbl.Insert_bgn();
		qid_tbl.Insert_cmd_by_batch(Bry_.new_u8(src_wiki), src_ns, Bry_.new_u8(src_ttl), Bry_.new_u8(trg_ttl));
		qid_tbl.Insert_end();
	}
	public void Test_select(String src_wiki, int src_ns, String src_ttl, String expd) {
		byte[] actl = qid_tbl.Select_qid(Bry_.new_u8(src_wiki), Bry_.new_a7(Int_.To_str(src_ns)), Bry_.new_u8(src_ttl));
		Tfds.Eq(expd, String_.new_u8(actl));
	}
}
