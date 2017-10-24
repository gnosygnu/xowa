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
package gplx.xowa.addons.bldrs.files.shrinks; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import gplx.core.envs.*;
import gplx.dbs.*;
import gplx.fsdb.*; import gplx.fsdb.data.*; import gplx.fsdb.meta.*;
class Xoshrink_mgr {
	private Io_url src_url, trg_url;
	private Process_adp convert_cmd;
	public void Exec(Xowe_wiki wiki) {
		// init
		src_url = wiki.Fsys_mgr().Root_dir().GenSubFil_nest("tmp", "shrink", "src.file");
		trg_url = wiki.Fsys_mgr().Root_dir().GenSubFil_nest("tmp", "shrink", "trg.file");
		Io_url convert_exe_url = wiki.Appe().Prog_mgr().App_resize_img().Exe_url();
		convert_cmd = Process_adp.New(Gfo_usr_dlg_.Instance, wiki.Appe().Url_cmd_eval(), Process_adp.Run_mode_sync_timeout, 1 * 60, convert_exe_url.Raw(), "-resample ~{w}x~{h}");

		// get bin_mgr
		Fsm_bin_mgr bin_mgr = wiki.File__mnt_mgr().Mnts__get_main().Bin_mgr();
		int len = bin_mgr.Dbs__len();

		// loop bin_dbs
		for (int i = 0; i < len; i++) {
			Shrink(bin_mgr.Dbs__get_at(i));
		}
	}
	private void Shrink(Fsm_bin_fil fil) {
		// init
		Fsd_bin_tbl tbl = fil.Tbl();
		Db_conn conn = fil.Conn();

		// prep for update
		conn.Txn_bgn("tbl_update");
		Db_stmt stmt = conn.Stmt_update(tbl.Tbl_name(), String_.Ary(tbl.fld__owner_id), tbl.fld__data);

		// get rdr
		Db_rdr rdr = conn.Stmt_select_all(tbl.Tbl_name(), tbl.Flds()).Exec_select__rls_auto();
		try {
			// loop each row and convert
			while (rdr.Move_next()) {
				// db.read and fs.save
				int id = rdr.Read_int(tbl.fld__owner_id);
				byte[] data = rdr.Read_bry(tbl.fld__data);
				Io_mgr.Instance.SaveFilBry(src_url, data);

				// convert
				convert_cmd.Run();// get w and h

				// fs.load and db.save
				data = Io_mgr.Instance.LoadFilBry(trg_url);
				tbl.Update(stmt, id, data);
			}
		} finally {
			conn.Txn_end();
			rdr.Rls();
			stmt.Rls();
		}
	}
}
