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
package gplx.dbs.diffs.cmds; import gplx.*; import gplx.dbs.*; import gplx.dbs.diffs.*;
import gplx.core.srls.*; import gplx.core.brys.fmtrs.*;
import gplx.dbs.metas.*;
interface Gfdb_diff_cmd {
	void Merge_undo();
	void Merge_exec();		
}
class Gfdb_diff_ctx implements Gfo_srl_ctx {
	public Gfo_srl_mgr_wtr Wtr_bgn(String key) {return null;}
	public Gfo_srl_mgr_rdr Rdr_bgn(String key) {return null;}
	public Dbmeta_dat_mgr Rdr_subs(String key) {return null;}
	public Dbmeta_tbl_itm Tbls__get(String key) {return null;}
}
class Gfo_srl_mgr_rdr__defn {
	public String Tbl = null;
	public String[] Select_crt_cols = null;
	public String[] Delete_crt_cols = null;
}
class Gfo_srl_mgr_rdr__db {
	public Object	Get_subs	(Gfo_srl_ctx ctx, Gfo_srl_itm owner, Gfo_srl_itm proto, String defn_key, Dbmeta_dat_mgr crt_mgr) {
		List_adp list = List_adp_.New();

		Gfo_srl_mgr_rdr__defn defn = new Gfo_srl_mgr_rdr__defn();	// Get(key)
		Db_conn conn = Db_conn_.Noop;
		Db_stmt select = conn.Stmt_select(defn.Tbl, Dbmeta_fld_itm.Str_ary_empty, defn.Select_crt_cols);
		int crt_len = crt_mgr.Len();
		for (int i = 0; i < crt_len; ++i) {
			Dbmeta_dat_itm crt = crt_mgr.Get_at(i);
			switch (crt.Tid) {
				case Dbmeta_fld_tid.Tid__int: select.Crt_int(crt.Key, Int_.cast(crt.Val)); break;
			}				
		}
		Db_rdr rdr = select.Exec_select__rls_manual();
		while (rdr.Move_next()) {
			Gfo_srl_itm sub = proto.Make_new(ctx);
			list.Add(sub);
			sub.Load(ctx, owner, null);
		}
		rdr.Rls();

		return list.To_ary_and_clear(proto.getClass());
	}
	public void	Set_subs	(Gfo_srl_ctx ctx, Gfo_srl_itm owner, Gfo_srl_itm proto, Gfo_srl_itm[] subs_ary, String defn_key, Dbmeta_dat_mgr crt_mgr) {
		Gfo_srl_mgr_rdr__defn defn = new Gfo_srl_mgr_rdr__defn();	// Get(key)
		Db_conn conn = Db_conn_.Noop;
		Db_stmt delete = conn.Stmt_delete(defn.Tbl, defn.Delete_crt_cols);
		int crt_len = crt_mgr.Len();
		for (int i = 0; i < crt_len; ++i) {
			Dbmeta_dat_itm crt = crt_mgr.Get_at(i);
			switch (crt.Tid) {
				case Dbmeta_fld_tid.Tid__int: delete.Crt_int(crt.Key, Int_.cast(crt.Val)); break;
			}				
		}
		delete.Exec_delete();

		int subs_len = subs_ary.length;
		for (int i = 0; i < subs_len; ++i) {
			Gfo_srl_itm itm = subs_ary[i];
			itm.Save(ctx, owner, null);
		}
	}
}
class Gfdb_diff_cmd__idx__delete {
	public Gfdb_diff_cmd__idx__delete(Dbmeta_idx_itm old) {this.Old = old;}
	public final    Dbmeta_idx_itm Old;
}
class Gfdb_diff_cmd__idx__modify {
	public Gfdb_diff_cmd__idx__modify(Dbmeta_idx_itm old, Dbmeta_idx_itm cur) {this.Old = old; this.Cur = cur;}
	public final    Dbmeta_idx_itm Old;
	public final    Dbmeta_idx_itm Cur;
}
class Gfdb_diff_txn {
	public int Id = 0;
}
class Gfdb_diff_cmd__fld__create {
	public Gfdb_diff_cmd__fld__create(Dbmeta_fld_itm cur) {this.cur = cur;}
	private Dbmeta_fld_itm cur;
	public void Save(Gfo_srl_ctx ctx, Gfo_srl_itm owner) {
		Gfo_srl_mgr_wtr wtr = ctx.Wtr_bgn("cmd.fld");
		wtr.Set_int		("txn_id"		, ((Gfdb_diff_txn)owner).Id);
		wtr.Set_str		("name"			, cur.Name());
		wtr.Set_int		("type_tid"		, cur.Type().Tid_ansi());
		wtr.Set_int		("type_len_1"	, cur.Type().Len_1());
		wtr.Set_int		("type_len_2"	, cur.Type().Len_2());
		wtr.Set_bool	("primary"		, cur.Primary());
		wtr.Set_int		("nullable"		, cur.Nullable_tid());
		wtr.Set_str		("dflt"			, Object_.Xto_str_or(cur.Default(), null));
		wtr.Itm_end();
	}
	public void Load(Gfo_srl_ctx ctx, Gfo_srl_itm owner) {
		Gfo_srl_mgr_rdr rdr = ctx.Rdr_bgn("cmd.fld");
		String name = rdr.Get_str("name");
		int type_tid = rdr.Get_int("type_tid");
		int type_len_1 = rdr.Get_int("type_len_1");
		// int type_len_2 = rdr.Get_int("type_len_2");
		boolean primary = rdr.Get_bool("primary");
		int nullable_tid = rdr.Get_int("nullable");
		String dflt_str = rdr.Get_str("dflt");
		cur = new Dbmeta_fld_itm(name, Dbmeta_fld_tid.New(type_tid, type_len_1));
		cur.Nullable_tid_(nullable_tid);
		if (primary) cur.Primary_y_();
		if (dflt_str != null) cur.Default_(dflt_str);
		rdr.Itm_end();
//			Gfdb_diff_cmd__idx__create idx = ((Gfdb_diff_cmd__idx__create)owner);			
//			Dbmeta_tbl_itm tbl = ((Gfdb_diff_ctx)ctx).Tbls__get(idx.cur.Tbl());
//			Dbmeta_fld_tid tid = tbl.Flds().Get_by(name).Type();
	}
}
class Gfdb_diff_cmd__fld__delete {
	public Gfdb_diff_cmd__fld__delete(Dbmeta_fld_itm old) {this.Old = old;}
	public final    Dbmeta_fld_itm Old;
}
class Gfdb_diff_cmd__fld__modify {
	public Gfdb_diff_cmd__fld__modify(Dbmeta_fld_itm old, Dbmeta_fld_itm cur) {this.Old = old; this.Cur = cur;}
	public final    Dbmeta_fld_itm Old;
	public final    Dbmeta_fld_itm Cur;
}
class Gfdb_diff_cmd__tbl__create {
	public Gfdb_diff_cmd__tbl__create(Dbmeta_tbl_itm cur) {this.Cur = cur;}
	public final    Dbmeta_tbl_itm Cur;
}
class Gfdb_diff_cmd__tbl__delete {
	public Gfdb_diff_cmd__tbl__delete(Dbmeta_tbl_itm old) {this.Old = old;}
	public final    Dbmeta_tbl_itm Old;
}
class Gfdb_diff_cmd_bldr {
	public void Chk_tbls(List_adp rv, Dbmeta_tbl_mgr old_tbls, Dbmeta_tbl_mgr cur_tbls) {
		int cur_tbls_len = cur_tbls.Len();
		for (int i = 0; i < cur_tbls_len; ++i) {
			Dbmeta_tbl_itm cur_tbl = cur_tbls.Get_at(i);
			Dbmeta_tbl_itm old_tbl = old_tbls.Get_by(cur_tbl.Name());
			if (old_tbl == null)
				rv.Add(new Gfdb_diff_cmd__tbl__create(cur_tbl));
			else {
				Chk_idxs(rv, old_tbl, cur_tbl);
				Chk_flds(rv, old_tbl, cur_tbl);
				// Chk_data?
			}
		}
		int old_tbls_len = old_tbls.Len();
		for (int i = 0; i < old_tbls_len; ++i) {
			Dbmeta_tbl_itm old_tbl = old_tbls.Get_at(i);
			Dbmeta_tbl_itm cur_tbl = cur_tbls.Get_by(old_tbl.Name());
			if (cur_tbl == null)
				rv.Add(new Gfdb_diff_cmd__tbl__delete(old_tbl));
		}
	}
	public void Chk_idxs(List_adp rv, Dbmeta_tbl_itm old_tbl, Dbmeta_tbl_itm cur_tbl) {
		Dbmeta_idx_mgr old_idxs = old_tbl.Idxs(), cur_idxs = cur_tbl.Idxs();
		int cur_idxs_len = cur_idxs.Len();
		for (int i = 0; i < cur_idxs_len; ++i) {
			Dbmeta_idx_itm cur_idx = cur_idxs.Get_at(i);
			Dbmeta_idx_itm old_idx = old_idxs.Get_by(cur_idx.Name());
			if (old_idx == null)
				rv.Add(new Gfdb_diff_cmd__idx__create(cur_idx));
			else
				if (!cur_idx.Eq(old_idx))
					rv.Add(new Gfdb_diff_cmd__idx__modify(old_idx, cur_idx));
		}
		int old_idxs_len = old_idxs.Len();
		for (int i = 0; i < old_idxs_len; ++i) {
			Dbmeta_idx_itm old_idx = old_idxs.Get_at(i);
			Dbmeta_idx_itm cur_idx = cur_idxs.Get_by(old_idx.Name());
			if (cur_idx == null)
				rv.Add(new Gfdb_diff_cmd__idx__delete(old_idx));
		}
	}
	public void Chk_flds(List_adp rv, Dbmeta_tbl_itm old_tbl, Dbmeta_tbl_itm cur_tbl) {
		Dbmeta_fld_mgr old_flds = old_tbl.Flds(), cur_flds = cur_tbl.Flds();
		int cur_flds_len = cur_flds.Len();
		for (int i = 0; i < cur_flds_len; ++i) {
			Dbmeta_fld_itm cur_fld = cur_flds.Get_at(i);
			Dbmeta_fld_itm old_fld = old_flds.Get_by(cur_fld.Name());
			if (old_fld == null)
				rv.Add(new Gfdb_diff_cmd__fld__create(cur_fld));
			else
				if (!cur_fld.Eq(old_fld))
					rv.Add(new Gfdb_diff_cmd__fld__modify(old_fld, cur_fld));
		}
		int old_flds_len = old_flds.Len();
		for (int i = 0; i < old_flds_len; ++i) {
			Dbmeta_fld_itm old_fld = old_flds.Get_at(i);
			Dbmeta_fld_itm cur_fld = cur_flds.Get_by(old_fld.Name());
			if (cur_fld == null)
				rv.Add(new Gfdb_diff_cmd__fld__delete(old_fld));
		}
	}
}
class Gfdb_diff_cmd__insert {
//      else if I
//        // txn_bgn
//        // audit
//        INSERT INTO db_temp.page (diff_type, diff, *)
//        SELECT 'D', d.page_id, *
//        FROM   db_diff.page d
//               JOIN db_main.page m ON d.page_id = m.page_id
//        WHERE  d.diff_type = 0
//        AND    d.diff_idx BETWEEN lo and hi
//        
//        // update
//        INSERT INTO db_main.page
//        SELECT  d.page_title
//        FROM    db_diff.page d
//                JOIN db_main.page m        
//        // txn_end
	private Db_conn conn;
	private String exec_sql;
	public void Init(Db_conn main_conn, Db_conn diff_conn, Db_conn temp_conn, Gfdb_diff_tbl tbl) {
		this.conn = temp_conn;
		this.exec_sql = "";
//			this.exec_sql = String_.Format(String_.Concat_lines_nl_skip_last
//			( "INSERT INTO db_curr.{tbl}"
//			), Gfdb_diff_cmd_ctx.Alias__curr);
	}
	public void Merge_exec() {
		conn.Exec_sql(exec_sql);
	}
}
class Gfdb_diff_cmd_sql_bldr {
	private final    Bry_fmtr fmtr = Bry_fmtr.new_();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	public void Bld_insert(Bry_bfr bfr, String tbl_name, String[] keys, String[] vals, int rng_bgn, int rng_end) {
		fmtr.Fmt_(Insert__fmt).Keys_(Insert__keys);
		fmtr.Bld_bfr_many(bfr, tbl_name, Bld_flds(tmp_bfr, ", ", "d.", keys, vals), Bld_join(keys), rng_bgn, rng_end);
	}
	public void Bld_update(Bry_bfr bfr, String tbl_name, String[] keys, String[] vals, int rng_bgn, int rng_end) {
		fmtr.Fmt_(Update__fmt).Keys_(Update__keys);
		fmtr.Bld_bfr_many(bfr, tbl_name, Bld_flds(tmp_bfr, ", ", "d.", keys, vals), Bld_join(keys), rng_bgn, rng_end);
	}
	public void Bld_delete(Bry_bfr bfr, String tbl_name, String[] keys, int rng_bgn, int rng_end) {
		fmtr.Fmt_(Delete__fmt).Keys_(Delete__keys);
		fmtr.Bld_bfr_many(bfr, tbl_name, Bld_flds(tmp_bfr, " || '|' || ", "", keys, String_.Ary_empty), Bld_flds(tmp_bfr, " || '|' || ", "k.", keys, String_.Ary_empty), Bld_join(keys), rng_bgn, rng_end);
	}
	private static String Bld_flds(Bry_bfr tmp_bfr, String dlm, String alias, String[] keys, String[] vals) {
		int keys_len = keys.length;
		for (int i = 0; i < keys_len; ++i) {
			String key = keys[i];
			if (i != 0) tmp_bfr.Add_str_a7(dlm);
			tmp_bfr.Add_str_a7(alias).Add_str_a7(key);
		}
		int flds_len = vals.length;
		for (int i = 0; i < flds_len; ++i) {
			String val = vals[i];
			tmp_bfr.Add_str_a7(dlm);
			tmp_bfr.Add_str_a7(alias).Add_str_a7(val);
		}
		return tmp_bfr.To_str_and_clear();
	}
	private String Bld_join(String[] keys) {
		int len = keys.length;
		for (int i = 0; i < len; ++i) {
			String key = keys[i];
			tmp_bfr.Add_str_a7(i == 0 ? " ON " : " AND ");
			tmp_bfr.Add_str_a7("k.").Add_str_a7(key).Add_str_a7(" = ");
			tmp_bfr.Add_str_a7("d.").Add_str_a7(key);
		}
		return tmp_bfr.To_str_and_clear();
	}
	private static final    String[] Insert__keys = String_.Ary("tbl", "flds", "join", "rng_bgn", "rng_end");
	private static final    String Insert__fmt = String_.Concat_lines_nl_skip_last
	( "INSERT  INTO db_curr.~{tbl}"
	, "SELECT  ~{flds}"
	, "FROM    db_temp.~{tbl}_pkey k"
	, "        JOIN db_diff.~{tbl} d~{join}"
	, "WHERE   k.diff_type = 1"
	, "AND     k.diff_uid BETWEEN ~{rng_bgn} AND ~{rng_end};"
	);
	private static final    String[] Update__keys = String_.Ary("tbl", "flds", "join", "rng_bgn", "rng_end");
	private static final    String Update__fmt = String_.Concat_lines_nl_skip_last
	( "REPLACE INTO db_curr.~{tbl}" 
	, "SELECT  ~{flds}"
	, "FROM    db_temp.~{tbl}_pkey k"
	, "        JOIN db_diff.~{tbl} d~{join}"
	, "WHERE   k.diff_type = 2"
	, "AND     k.diff_uid BETWEEN ~{rng_bgn} AND ~{rng_end};"
	);
	private static final    String[] Delete__keys = String_.Ary("tbl", "pkey_where", "pkey_select", "join", "rng_bgn", "rng_end");
	private static final    String Delete__fmt = String_.Concat_lines_nl_skip_last
	( "DELETE  db_curr.~{tbl}"
	, "WHERE   ~{pkey_where} IN"
	, "(       SELECT  ~{pkey_select}"
	, "        FROM    db_temp.~{tbl}_pkey k"
	, "                JOIN db_diff.~{tbl} d~{join}"
	, "        WHERE   k.diff_type = 0"
	, "        AND     k.diff_uid BETWEEN ~{rng_bgn} AND ~{rng_end}"
	, ");"
	);
}
