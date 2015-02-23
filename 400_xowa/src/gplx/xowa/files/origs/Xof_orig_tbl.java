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
package gplx.xowa.files.origs; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.dbs.*; import gplx.dbs.utls.*; 
import gplx.xowa.files.fsdb.*; import gplx.xowa.files.repos.*;
public class Xof_orig_tbl {
	private String tbl_name = "file_orig_regy"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private String fld_repo, fld_ttl, fld_status, fld_ext, fld_w, fld_h, fld_redirect;
	private Db_conn conn; private final Xof_orig_tbl__in_wkr select_in_wkr = new Xof_orig_tbl__in_wkr();
	public void Conn_(Db_conn new_conn, boolean created, boolean version_is_1) {
		this.conn = new_conn; flds.Clear();
		String fld_prefix = "";
		if (version_is_1) {
			tbl_name			= "wiki_orig";
			fld_prefix			= "orig_";
		}
		fld_ttl					= flds.Add_str(fld_prefix + "ttl", 1024);
		fld_status				= flds.Add_byte("status");	// NOTE: "status" in v1 and v2
		fld_repo				= flds.Add_byte(fld_prefix + "repo");
		fld_ext					= flds.Add_int(fld_prefix + "ext");
		fld_w					= flds.Add_int(fld_prefix + "w");
		fld_h					= flds.Add_int(fld_prefix + "h");
		fld_redirect			= flds.Add_str(fld_prefix + "redirect", 1024);
		if (created) {
			Db_meta_tbl meta = Db_meta_tbl.new_(tbl_name, flds
			, Db_meta_idx.new_unique_by_tbl(tbl_name, "key", fld_ttl)
			);
			conn.Exec_create_tbl_and_idx(meta);
		}
		select_in_wkr.Ctor(this, tbl_name, flds, fld_ttl);
	}
	public void Select_by_list(OrderedHash rv, ListAdp itms) {select_in_wkr.Init(rv, itms).Select_in(Cancelable_.Never, gplx.xowa.dbs.Xodb_ctx.Null, conn, 0, itms.Count());}
	public Xof_orig_itm Select_itm(byte[] ttl) {
		Xof_orig_itm rv = Xof_orig_itm.Null;
		Db_rdr rdr = Db_rdr_.Null;
		try {
			Db_stmt stmt = conn.Stmt_select(tbl_name, flds.To_str_ary(), fld_ttl);
			rdr = stmt.Clear().Crt_bry_as_str(fld_ttl, ttl).Exec_select_as_rdr();
			if (rdr.Move_next())
				rv = Make_itm(rdr);
		}
		finally {rdr.Rls();}
		return rv;
	}
	public void Insert(byte repo, byte[] ttl, int ext, int w, int h, byte[] redirect) {
		Db_stmt stmt = Db_stmt_.Null;
		stmt = conn.Stmt_insert(tbl_name, flds);
		stmt.Clear()
		.Val_bry_as_str(fld_ttl, ttl).Val_byte(fld_status, Xof_orig_wkr_.Status_found).Val_byte(fld_repo, repo).Val_int(fld_ext, ext).Val_int(fld_w, w).Val_int(fld_h, h).Val_bry_as_str(fld_redirect, redirect)
		.Exec_insert();
	}
	public Xof_orig_itm Make_itm(Db_rdr rdr) {
		byte repo = rdr.Read_byte(fld_repo);
		Xof_orig_itm rv = new Xof_orig_itm().Init
		( repo
		, rdr.Read_bry_by_str(fld_ttl)
		, rdr.Read_int(fld_ext)
		, rdr.Read_int(fld_w)
		, rdr.Read_int(fld_h)
		, rdr.Read_bry_by_str(fld_redirect)
		, Xof_repo_itm.Repo_is_known(repo) ? Xof_orig_wkr_.Status_found : Xof_orig_wkr_.Status_missing_orig	// NOTE: orig_db may mistakenly have status of found; rely on repo to set status; PAGE:ru.w:Птичкин,_Евгений_Николаевич; DATE:2015-02-16
		);
		return rv;
	}
	public static final String Db_conn_bldr_type = "xowa.file.orig_regy";
	public static Db_conn Conn__get_or_make(Io_url root_dir, Xof_orig_tbl tbl, boolean version_is_1) {
		Io_url conn_url = root_dir.GenSubFil("wiki.orig#00.sqlite3");
		Db_conn_bldr_data conn_data = Db_conn_bldr.I.Get_or_new(Db_conn_bldr_type, conn_url);
		Db_conn conn = conn_data.Conn();
		tbl.Conn_(conn, conn_data.Created(), version_is_1);
		return conn;
	}
}
class Xof_orig_tbl__in_wkr extends Db_in_wkr__base {
	private Xof_orig_tbl tbl; private String tbl_name; private Db_meta_fld_list flds; private String fld_ttl;
	private ListAdp itms; private OrderedHash rv;		
	public void Ctor(Xof_orig_tbl tbl, String tbl_name, Db_meta_fld_list flds, String fld_ttl) {
		this.tbl = tbl; this.tbl_name = tbl_name; this.flds = flds; this.fld_ttl = fld_ttl;
	}
	public Xof_orig_tbl__in_wkr Init(OrderedHash rv, ListAdp itms) {this.itms = itms; this.rv = rv; return this;}
	@Override protected int Interval() {return gplx.dbs.engines.sqlite.Sqlite_engine_.Stmt_arg_max;}
	@Override protected Db_qry Make_qry(Object db_ctx, int bgn, int end) {
		Object[] part_ary = In_ary(end - bgn);			
		return Db_qry_.select_cols_(tbl_name, Db_crt_.in_(fld_ttl, part_ary), flds.To_str_ary());
	}
	@Override protected void Fill_stmt(Db_stmt stmt, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			Xof_fsdb_itm fsdb_itm = (Xof_fsdb_itm)itms.FetchAt(i);
			stmt.Crt_bry_as_str(fld_ttl, fsdb_itm.Lnki_ttl());
		}
	}
	@Override protected void Read_data(Cancelable cancelable, Object db_ctx, Db_rdr rdr) {
		while (rdr.Move_next()) {
			if (cancelable.Canceled()) return;
			Xof_orig_itm itm = tbl.Make_itm(rdr);
			byte[] itm_ttl = itm.Page();
			rv.Add_if_new(itm_ttl, itm);	// guard against dupes (shouldn't happen)
		}
	}
}
