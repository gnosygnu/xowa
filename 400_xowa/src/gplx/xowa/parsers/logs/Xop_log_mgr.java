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
package gplx.xowa.parsers.logs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.dbs.*; import gplx.xowa.bldrs.*;
public class Xop_log_mgr implements Gfo_invk {
	private Db_conn conn;
	private Xoae_app app; private Xop_log_basic_tbl log_tbl;
	private int exec_count = 0, commit_interval = 1000;
	public Xop_log_mgr(Xoae_app app) {this.app = app;}
	public Io_url Log_dir() {return log_dir;}
	public Xop_log_mgr Log_dir_(Io_url v) {
		log_dir = v;
//			if (conn != null) {	// COMMENTED: need to implement a conn.Renew()
//				conn.Rls(); // invalidate conn; note that during build other cmds will bind Conn which will place temp.log in /temp/ dir instead of /wiki/ dir; DATE:2014-04-16
//			}
		return this;
	}	private Io_url log_dir;
	private Db_conn Conn() {
		if (conn == null) {
			if (log_dir == null) log_dir = app.Usere().Fsys_mgr().App_temp_dir();
			Xob_db_file db_file = Xob_db_file.New__temp_log(log_dir);
			conn = db_file.Conn();
		}
		return conn;
	}
	public Xop_log_invoke_wkr Make_wkr_invoke() {return new Xop_log_invoke_wkr(this.Conn());}
	public Xop_log_property_wkr Make_wkr_property() {return new Xop_log_property_wkr(this.Conn());}
	public Xop_log_basic_wkr Make_wkr() {
		if (log_tbl == null) {
			log_tbl = new Xop_log_basic_tbl(this.Conn());
			this.Conn().Meta_tbl_assert(log_tbl);
		}
		return new Xop_log_basic_wkr(log_tbl);
	}
	public void Commit_chk() {
		++exec_count;
		if ((exec_count % commit_interval) == 0)
			conn.Txn_sav();
	}
	public void Delete_all() {
		log_tbl.Delete();
	}
	public void Txn_bgn() {conn.Txn_bgn("log_mgr");}
	public void Txn_end() {conn.Txn_end();}
	public void Rls() {
		if (log_tbl != null)	log_tbl.Rls();
		if (conn != null)	{conn.Rls_conn(); conn = null;}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_commit_interval_))		commit_interval = m.ReadInt("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_commit_interval_ = "commit_interval_";
}
