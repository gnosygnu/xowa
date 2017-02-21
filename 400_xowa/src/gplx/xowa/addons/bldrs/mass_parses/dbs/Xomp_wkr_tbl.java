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
package gplx.xowa.addons.bldrs.mass_parses.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.dbs.*;
public class Xomp_wkr_tbl implements Db_tbl {
	private final    String fld_wkr_uid, fld_wkr_url, fld_wkr_status, fld_wkr_status_time, fld_wkr_exec_count, fld_wkr_exec_time;
	private final    Db_conn conn;
	private final    Object thread_lock = new Object();
	public Xomp_wkr_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name = "xomp_wkr";
		fld_wkr_uid			= flds.Add_int_pkey("wkr_uid");			// EX: 0
		fld_wkr_url			= flds.Add_str("wkr_url", 255);			// EX: //MACHINE/C:/xowa/wiki/en.wikipedia.org/tmp/xomp
		fld_wkr_status		= flds.Add_int("wkr_status");			// EX: running; waiting
		fld_wkr_status_time	= flds.Add_str("wkr_status_time", 255);	// EX: 20160801 010203
		fld_wkr_exec_count	= flds.Add_int("wkr_exec_count");		// EX: 1000
		fld_wkr_exec_time	= flds.Add_int("wkr_exec_time");		// EX: 123
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public Dbmeta_fld_list Flds() {return flds;} private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}

	public int Init_wkrs(String wkr_url, int wkr_len) {
		// delete all with machine_name
		conn.Stmt_delete(tbl_name, fld_wkr_url).Crt_str(fld_wkr_url, wkr_url).Exec_delete();

		// get bgn_uid / end_uid
		int bgn_uid = conn.Exec_select_max_as_int(tbl_name, fld_wkr_uid, -1) + 1;
		int end_uid = bgn_uid + wkr_len;

		// insert into tbl
		Db_stmt insert_stmt = conn.Stmt_insert(tbl_name, flds);
		for (int i = bgn_uid; i < end_uid; ++i)
			Insert(insert_stmt, i, wkr_url);
		insert_stmt.Rls();
		return bgn_uid;
	}
	public int Select_count() {
		return conn.Exec_select_count_as_int(tbl_name, 0);
	}

	private void Insert(Db_stmt stmt, int wkr_uid, String wkr_url) {
		stmt.Clear()
			.Val_int(fld_wkr_uid, wkr_uid)
			.Val_str(fld_wkr_url, wkr_url).Val_int(fld_wkr_status, Status__running).Val_str(fld_wkr_status_time, Datetime_now.Get_force().XtoStr_fmt_yyyyMMdd_HHmmss())
			.Val_int(fld_wkr_exec_count, 0).Val_int(fld_wkr_exec_time, 0)
			.Exec_insert();
	}
	public void Update_exec(int wkr_uid, int wkr_exec_count, long wkr_exec_time) {
		synchronized (thread_lock) {	// LOCK:wkr_tbl is shared by multiple threads / machines
			int attempts = 0;
			while (true) {
				if (++attempts > 10) {
					Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to update status; try=~{0}", attempts);
					break;
				}
				try {
					conn.Stmt_update(tbl_name, String_.Ary(fld_wkr_uid), fld_wkr_status, fld_wkr_status_time, fld_wkr_exec_count, fld_wkr_exec_time).Clear()
						.Val_int(fld_wkr_status, Status__running).Val_str(fld_wkr_status_time, Datetime_now.Get_force().XtoStr_fmt_yyyyMMdd_HHmmss())
						.Val_int(fld_wkr_exec_count, wkr_exec_count).Val_int(fld_wkr_exec_time, (int)(wkr_exec_time / 1000))
						.Crt_int(fld_wkr_uid, wkr_uid)
						.Exec_update();
					break;	// exit loop
				} catch (Exception e) {
					Gfo_usr_dlg_.Instance.Warn_many("", "", "unable to update status; try=~{0} err=~{1}", attempts, Err_.Message_gplx_log(e));
					gplx.core.threads.Thread_adp_.Sleep(10000);
					continue;
				}
			}
		}
	}
	public void Update_status(int wkr_uid, int status) {
		synchronized (thread_lock) {	// LOCK:wkr_tbl is shared by multiple threads
			conn.Stmt_update(tbl_name, String_.Ary(fld_wkr_uid), fld_wkr_status, fld_wkr_status_time).Clear()
				.Val_int(fld_wkr_status, status).Val_str(fld_wkr_status_time, Datetime_now.Get_force().XtoStr_fmt_yyyyMMdd_HHmmss())
				.Crt_int(fld_wkr_uid, wkr_uid)
				.Exec_update();
		}
	}
	public void Rls() {}

	public static final int Status__running = 1, Status__sleeping = 2;
}