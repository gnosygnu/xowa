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
package gplx.fsdb.meta; import gplx.*; import gplx.fsdb.*;
import gplx.dbs.*; import gplx.fsdb.data.*;
public class Fsm_atr_mgr {
	private Fsdb_db_mgr core_mgr; private Fsm_atr_tbl tbl; private Fsm_atr_fil db__core; private Fsm_mnt_itm mnt_itm;
	public Fsm_atr_mgr(Fsdb_db_mgr core_mgr, Db_conn conn, Fsm_mnt_itm mnt_itm) {
		this.core_mgr = core_mgr; this.mnt_itm = mnt_itm;
		this.tbl = new Fsm_atr_tbl(conn, core_mgr.File__schema_is_1());
	}
	public void Ctor_by_load(boolean schema_thm_page) {
		this.db__core = tbl.Select_1st_or_fail(mnt_itm, core_mgr, mnt_itm.Id(), schema_thm_page);
	}
	public Fsm_atr_fil		Db__core()		{return db__core;}
	public Fsd_fil_itm		Select_fil_or_null(byte[] dir, byte[] fil)						{return db__core.Select_fil_or_null(dir, fil);}
	public boolean				Select_thm(boolean exact, Fsd_thm_itm rv, int dir_id, int fil_id)	{return db__core.Select_thm(exact, rv, dir_id, fil_id);}
	public void Txn_bgn()	{db__core.Conn().Txn_bgn("fsdb__fsm_atr_mgr");}
	public void Txn_end()	{db__core.Conn().Txn_end();}
}
