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
package gplx.dbs.metas.updates;
import gplx.Tfds;
import gplx.dbs.Db_conn;
import gplx.dbs.Db_conn_;
import gplx.dbs.Dbmeta_tbl_itm;
import gplx.dbs.metas.Dbmeta_itm_tid;
import gplx.dbs.metas.Schema_db_mgr;
import gplx.objects.primitives.BoolUtl;
import org.junit.Before;
import org.junit.Test;
public class Schema_update_mgr_tst {
	@Before public void init() {fxt.Clear();} private Schema_update_mgr_fxt fxt = new Schema_update_mgr_fxt();
	@Test  public void Create() {
		fxt.Test_exec_y(new Schema_update_cmd__mock());
	}
	@Test  public void Delete() {
		fxt.Init_itm(Dbmeta_itm_tid.Tid_table, Schema_update_cmd__mock.Tbl_name);
		fxt.Test_exec_n(new Schema_update_cmd__mock());
	}
}
class Schema_update_mgr_fxt {
	private Schema_update_mgr update_mgr; private Schema_db_mgr db_mgr;
	public void Clear() {
		update_mgr = new Schema_update_mgr();
		db_mgr = new Schema_db_mgr();
	}
	public void Init_itm(int tid, String name) {
		db_mgr.Tbl_mgr().Add(Dbmeta_tbl_itm.New(name));
	}
	public void Test_exec_y(Schema_update_cmd cmd) {Test_exec(cmd, BoolUtl.Y);}
	public void Test_exec_n(Schema_update_cmd cmd) {Test_exec(cmd, BoolUtl.N);}
	private void Test_exec(Schema_update_cmd cmd, boolean expd) {
		update_mgr.Add(cmd);
		update_mgr.Update(db_mgr, Db_conn_.Noop);
		Tfds.Eq(expd, cmd.Exec_is_done());
	}
}
class Schema_update_cmd__mock implements Schema_update_cmd {
	public String Name() {return "xowa.user.cfg_regy.create";}
	public boolean Exec_is_done() {return exec_is_done;} private boolean exec_is_done;
	public void Exec(Schema_db_mgr mgr, Db_conn conn) {
		if (mgr.Tbl_mgr().Has(Tbl_name)) return;
		exec_is_done = true;
	}
	public static final String Tbl_name = "tbl_mock";
}
