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
import gplx.core.srls.*; import gplx.dbs.metas.*;

class Gfdb_diff_cmd__idx__create implements Gfo_srl_itm {
	public Gfdb_diff_cmd__idx__create(Dbmeta_idx_itm cur) {this.cur = cur;}
	public Dbmeta_idx_itm cur;
	private Gfdb_diff_cmd__idx__fld[] flds = new Gfdb_diff_cmd__idx__fld[0];
	public Gfo_srl_itm Make_new(Gfo_srl_ctx ctx) {return new Gfdb_diff_cmd__idx__create();} Gfdb_diff_cmd__idx__create() {} 
	private int idx_uid;
	// *_sdif_ddl_idx : txn_uid,idx_uid,idx_tbl,idx_name,idx_unique
	public void Save(Gfo_srl_ctx ctx, Gfo_srl_itm owner, Gfo_srl_mgr_wtr wtr) {
		wtr.Itm_bgn("idx");
		wtr.Set_int		("idx_uid"		, idx_uid);
		wtr.Set_str		("idx_tbl"		, cur.Tbl());
		wtr.Set_str		("idx_name"		, cur.Name());
		wtr.Set_bool	("idx_unique"	, cur.Unique());
		wtr.Set_subs	(ctx, this, Gfdb_diff_cmd__idx__fld.Instance, flds, ctx.Rdr_subs("idx_fld").Add_int("idx_uid", idx_uid));
		wtr.Itm_end();
	}
	public void Load(Gfo_srl_ctx ctx, Gfo_srl_itm owner, Gfo_srl_mgr_rdr rdr) {
		rdr.Itm_bgn("idx");
		this.idx_uid	= rdr.Get_int		("idx_uid");
		String tbl		= rdr.Get_str		("idx_tbl");
		String name		= rdr.Get_str		("idx_name");
		boolean unique		= rdr.Get_bool		("idx_unique");
		this.flds = (Gfdb_diff_cmd__idx__fld[])rdr.Get_subs(ctx, this, Gfdb_diff_cmd__idx__fld.Instance, ctx.Rdr_subs("idx_fld").Add_int("idx_uid", idx_uid));
		cur = new Dbmeta_idx_itm(unique, tbl, name, Dbmeta_idx_fld.Ary_empty);
		rdr.Itm_end();
	}
	public void Exec(Db_conn conn) {
		conn.Meta_idx_create(cur);
	}
}
class Gfdb_diff_cmd__idx__fld implements Gfo_srl_itm {
	public Gfdb_diff_cmd__idx__fld(Dbmeta_idx_fld cur) {this.cur = cur;}
	private Dbmeta_idx_fld cur;
	public Gfo_srl_itm Make_new(Gfo_srl_ctx ctx) {return new Gfdb_diff_cmd__idx__fld();} Gfdb_diff_cmd__idx__fld() {} 
	// *_sdif_ddl_idx_fld : idx_uid,fld_order,fld_name,fld_asc
	public void Save(Gfo_srl_ctx ctx, Gfo_srl_itm owner, Gfo_srl_mgr_wtr wtr) {
		wtr.Itm_bgn("idx_fld");
		wtr.Set_str		("fld_name"		, cur.Name);
		wtr.Set_int		("fld_asc"		, cur.Sort_tid);
		wtr.Itm_end();
	}
	public void Load(Gfo_srl_ctx ctx, Gfo_srl_itm owner, Gfo_srl_mgr_rdr rdr) {
		rdr.Itm_bgn("idx_fld");
		String name		= rdr.Get_str		("fld_name");
		int sort_tid	= rdr.Get_int		("fld_sort");
		cur = new Dbmeta_idx_fld(name, sort_tid);
		rdr.Itm_end();
	}

        public static final    Gfdb_diff_cmd__idx__fld Instance = new Gfdb_diff_cmd__idx__fld();
}
//	class Gfdb_diff_cmd__tbl__fld : Gfo_srl_itm {
//		public Gfdb_diff_cmd__tbl__fld(Dbmeta_fld_itm cur) {this.cur = cur;}
//		private Dbmeta_fld_itm cur;
//		public String Name;
//		public int Type_tid;
//		public int Len1;
//		public int Len2;
//		public int Nullable;
//		public boolean Primary;
//		public boolean Autonum;
//
//		public Gfo_srl_itm Make_new(Gfo_srl_ctx ctx) {return new Gfdb_diff_cmd__tbl__fld();} Gfdb_diff_cmd__tbl__fld() {} 
//		// *_sdif_ddl_tbl_fld : tbl_uid,fld_idx,fld_name,fld_type,fld_len1, fld_len2, fld_nullable, fld_primary, fld_autonumber
//		public void Save(Gfo_srl_ctx ctx, Gfo_srl_itm owner, Gfo_srl_mgr_wtr wtr) {
//			wtr.Itm_bgn("tbl_fld");
//			wtr.Set_str		("fld_name"		, cur.Name());
//			wtr.Set_int		("fld_type"		, cur.Type().Tid_ansi());
//			wtr.Set_int		("fld_len1"		, cur.Type().Len_1());
//			wtr.Set_int		("fld_len2"		, cur.Type().Len_2());
//			wtr.Set_int		("fld_nullable"	, cur.Nullable_tid());
//			wtr.Set_bool	("fld_primary"	, cur.Primary());
//			wtr.Set_bool	("fld_autonum"	, cur.Autonum());
//			wtr.Set_str		("fld_dflt"		, Object_.Xto_str_or(cur.Default(), null));
////			wtr.Set_int		("fld_asc"		, cur.Sort_tid);
//			wtr.Itm_end();
//		}
//		public void Load(Gfo_srl_ctx ctx, Gfo_srl_itm owner, Gfo_srl_mgr_rdr rdr) {
//			rdr.Itm_bgn("tbl_fld");
//			String name = rdr.Get_str("name");
//			int type_tid = rdr.Get_int("type_tid");
//			int type_len_1 = rdr.Get_int("type_len_1");
//			boolean primary = rdr.Get_bool("primary");
//			int nullable_tid = rdr.Get_int("nullable");
//			String dflt_str = rdr.Get_str("dflt");
//			cur = new Dbmeta_fld_itm(name, Dbmeta_fld_tid.New(type_tid, type_len_1));
//			cur.Nullable_tid_(nullable_tid);
//			if (primary) cur.Primary_y_();
//			if (dflt_str != null) cur.Default_(dflt_str);
//			rdr.Itm_end();
////			Gfdb_diff_cmd__idx__create idx = ((Gfdb_diff_cmd__idx__create)owner);			
////			Dbmeta_tbl_itm tbl = ((Gfdb_diff_ctx)ctx).Tbls__get(idx.cur.Tbl());
////			Dbmeta_fld_tid tid = tbl.Flds().Get_by(name).Type();
//		}
//
//        public static final    Gfdb_diff_cmd__tbl__fld Instance = new Gfdb_diff_cmd__tbl__fld();
//	}
