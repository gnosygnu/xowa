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
package gplx.dbs.diffs.builds; import gplx.*; import gplx.dbs.*; import gplx.dbs.diffs.*;
import gplx.dbs.metas.*; import gplx.dbs.diffs.itms.*;
public class Gfdb_diff_wkr__db implements Gfdb_diff_wkr {
	private Dbmeta_fld_itm[] val_flds; private int val_flds_len;
	private Gfdb_diff_tbl tbl; private Db_rdr old_rdr, new_rdr;
	private Gdif_bldr_ctx ctx;
	private Db_conn dif_conn; private Db_stmt stmt;
	private int uid, prog_interval, prog_count;
	private boolean cmd_create;
	public void Init_conn(Gdif_db dif_db, int prog_interval) {this.dif_conn = dif_db.Conn(); this.prog_interval = prog_interval;}
	public void Init_rdrs(Gdif_bldr_ctx ctx, Gfdb_diff_tbl tbl, Db_rdr old_rdr, Db_rdr new_rdr) {
		this.ctx = ctx; this.tbl = tbl; this.old_rdr = old_rdr; this.new_rdr = new_rdr;
		this.val_flds = tbl.Vals; val_flds_len = val_flds.length;
		this.uid = 0; this.prog_count = 0;

		String dif_tbl = tbl.Name; Dbmeta_fld_itm[] dif_flds = Gfdb_diff_wkr__db_.New_dif_flds(tbl.Flds);
		if (!dif_conn.Meta_tbl_exists(dif_tbl)) dif_conn.Meta_tbl_create(Dbmeta_tbl_itm.New(dif_tbl, dif_flds));
		this.stmt = dif_conn.Stmt_insert(dif_tbl, Gfdb_diff_wkr__db_.To_str_ary(dif_flds));
		dif_conn.Txn_bgn("dif_db_tbl_" + dif_tbl);
		cmd_create = true;
	}
	public void Term_tbls() {
		dif_conn.Txn_end();
	}
	public void Handle_old_missing() {Insert(Gdif_db_.Tid__insert, ++uid, new_rdr, tbl.Flds);}
	public void Handle_new_missing() {Insert(Gdif_db_.Tid__delete, ++uid, old_rdr, tbl.Flds);}
	public void Handle_same() {
		if (Gfdb_rdr_utl_.Compare(val_flds, val_flds_len, old_rdr, new_rdr) != CompareAble_.Same)
			Insert(Gdif_db_.Tid__update, ++uid, new_rdr, tbl.Flds);
	}
	private void Insert(byte dif_type, int uid, Db_rdr rdr, Dbmeta_fld_itm[] flds) {
		if (cmd_create) {
			cmd_create = false;
			ctx.Cur_cmd = ctx.Core.New_cmd(ctx, Gdif_cmd_itm.Tid__data);
			ctx.Cur_txn = ctx.Core.New_txn(ctx, ctx.Cur_cmd.Cmd_id, Gdif_txn_itm.Owner_txn__null);
		}
		stmt.Clear();
		stmt.Val_int	(Gdif_db_.Fld__dif_txn		, ctx.Cur_txn.Txn_id)
			.Val_int	(Gdif_db_.Fld__dif_uid		, uid)
			.Val_int	(Gdif_db_.Fld__dif_type		, dif_type)
			.Val_int	(Gdif_db_.Fld__dif_db_src	, -1)
			.Val_int	(Gdif_db_.Fld__dif_db_trg	, -1);
		Gfdb_rdr_utl_.Stmt_args(stmt, flds, 0, flds.length, rdr);
		stmt.Exec_insert();
		if ((++prog_count % prog_interval) == 0) dif_conn.Txn_sav();
	}
}
class Gfdb_diff_wkr__db_ {
	public static Dbmeta_fld_itm[] New_dif_flds(Dbmeta_fld_itm[] cur_flds) {
		int len = cur_flds.length;
		int sys_flds = 5;
		Dbmeta_fld_itm[] rv = new Dbmeta_fld_itm[len + sys_flds];
		rv[0] = Dbmeta_fld_itm.new_int	(Gdif_db_.Fld__dif_txn);
		rv[1] = Dbmeta_fld_itm.new_int	(Gdif_db_.Fld__dif_uid);
		rv[2] = Dbmeta_fld_itm.new_byte	(Gdif_db_.Fld__dif_type);
		rv[3] = Dbmeta_fld_itm.new_int	(Gdif_db_.Fld__dif_db_trg);
		rv[4] = Dbmeta_fld_itm.new_int	(Gdif_db_.Fld__dif_db_src);
		for (int i = 0; i < len; ++i) {
			Dbmeta_fld_itm cur_fld = cur_flds[i];
			Dbmeta_fld_itm dif_fld = new Dbmeta_fld_itm(cur_fld.Name(), cur_fld.Type());
			rv[i + sys_flds] = dif_fld;
		}
		return rv;
	}
	public static String[] To_str_ary(Dbmeta_fld_itm[] ary) {
		int len = ary.length;
		String[] rv = new String[len];
		for (int i = 0; i < len; ++i)
			rv[i] = ary[i].Name();
		return rv;
	}
}
