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
package gplx.xowa2.files.orig_regy; import gplx.*; import gplx.xowa2.*; import gplx.xowa2.files.*;
import gplx.dbs.*; import gplx.xowa.dbs.*; import gplx.xowa.files.fsdb.*; import gplx.xowa.files.wiki_orig.*;
public class Xof_orig_regy_tbl implements Db_conn_itm {
	private Xof_orig_regy_tbl__in_wkr select_in_wkr;
	public void Conn_term() {}
	public Xof_orig_regy_tbl() {
		tbl_name = "wiki_orig";
		Fld_orig_repo				= flds.Add_byte("orig_repo");
		flds.Add_byte("orig_status");
		fld_orig_ttl				= flds.Add_str("orig_ttl", 1024);
		Fld_orig_ext				= flds.Add_int("orig_ext");
		Fld_orig_w					= flds.Add_int("orig_w");
		Fld_orig_h					= flds.Add_int("orig_h");
		Fld_orig_redirect			= flds.Add_str("orig_redirect", 1024);
		meta = Db_meta_tbl.new_(tbl_name, flds.To_fld_ary()
		, Db_meta_idx.new_unique(tbl_name, "key"		, fld_orig_ttl)
		);
		select_in_wkr = new Xof_orig_regy_tbl__in_wkr(this);
	}
	public Db_conn Conn() {return conn;} public Xof_orig_regy_tbl Conn_(Db_conn v) {conn = v; conn.Itms_add(this); return this;} private Db_conn conn;
	public void Select_list(OrderedHash rv, ListAdp itms, Cancelable cancelable) {
		select_in_wkr.Init(rv, itms).Select_in(cancelable, Xodb_ctx.Null, conn, 0, itms.Count());
	}
	public boolean Select_itm_exists(byte[] ttl) {return Select_itm(ttl) != Xof_orig_regy_itm.Null;}
	public Xof_orig_regy_itm Select_itm(byte[] ttl) {
		Xof_orig_regy_itm rv = Xof_orig_regy_itm.Null;
		Db_rdr rdr = Db_rdr_.Null;
		try {
			Db_stmt stmt = conn.New_stmt_select_all_where(tbl_name, flds.To_str_ary(), fld_orig_ttl);
			rdr = stmt.Clear().Crt_bry_as_str(fld_orig_ttl, ttl).Exec_select_as_rdr();
			if (rdr.Move_next())
				rv = Make_itm(rdr);
		}
		catch (Exception e) {
			Tfds.WriteText(Err_.Message_gplx(e));			
		}
		finally {rdr.Rls();}
		return rv;
	}
	public void Insert(byte repo, byte[] ttl, int ext, int w, int h, byte[] redirect) {
		Db_stmt stmt = Db_stmt_.Null;
		try {
			stmt = conn.New_stmt_insert(tbl_name, flds.To_str_ary());
			stmt.Clear()
			.Val_byte(repo).Val_byte(Xof_wiki_orig_wkr_.Tid_found_orig).Val_bry_as_str(ttl).Val_int(ext).Val_int(w).Val_int(h).Val_bry_as_str(redirect)
			.Exec_insert();
		}	finally {stmt.Rls();}
	}
	public String Tbl_name() {return tbl_name;} private String tbl_name = "file_orig_regy";
	public String Fld_orig_ttl() {return fld_orig_ttl;}
	private String Fld_orig_repo, fld_orig_ttl, Fld_orig_ext, Fld_orig_w, Fld_orig_h, Fld_orig_redirect;
	public Db_meta_fld_list Flds() {return flds;} private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	public Db_meta_tbl Meta() {return meta;} private final Db_meta_tbl meta;
	public Xof_orig_regy_itm Make_itm(Db_rdr rdr) {
		Xof_orig_regy_itm rv = new Xof_orig_regy_itm
		( rdr.Read_bry_by_str(fld_orig_ttl)
		, Xof_wiki_orig_wkr_.Tid_found_orig
		, rdr.Read_byte(Fld_orig_repo)
		, rdr.Read_int(Fld_orig_w)
		, rdr.Read_int(Fld_orig_h)
		, rdr.Read_int(Fld_orig_ext)
		, rdr.Read_bry_by_str(Fld_orig_redirect)
		);
		return rv;
	}
}
abstract class Db_in_wkr__base {
	protected abstract int Interval();
	protected abstract Db_qry	Make_qry	(Object db_ctx, int bgn, int end);
	protected abstract void		Fill_stmt	(Db_stmt stmt, int bgn, int end);
	protected abstract void		Read_data	(Cancelable cancelable, Object db_ctx, Db_rdr rdr);
	public void Select_in(Cancelable cancelable, Object db_ctx, Db_conn conn, int full_bgn, int full_end) {
		Db_rdr rdr = Db_rdr_.Null; Db_stmt stmt = Db_stmt_.Null;
		int part_len = Interval();
		for (int part_bgn = full_bgn; part_bgn < full_end; part_bgn += part_len) {
			int part_end = part_bgn + part_len;
			if (part_end > full_end) part_end = full_end;
			try {
				stmt = conn.New_stmt(Make_qry(db_ctx, part_bgn, part_end));
				Fill_stmt(stmt, part_bgn, part_end);
				rdr = stmt.Exec_select_as_rdr();
				Read_data(cancelable, db_ctx, rdr);
			}
			finally {rdr.Rls(); stmt.Rls();}
		}
	}
	public static Object[] In_ary(int len) {
		Object[] rv = new Object[len];
		for (int i = 0; i < len; i++)
			rv[i] = "";
		return rv;
	}
}
class Xof_orig_regy_tbl__in_wkr extends Db_in_wkr__base {
	private Xof_orig_regy_tbl tbl; private ListAdp itms; private OrderedHash rv;
	public Xof_orig_regy_tbl__in_wkr(Xof_orig_regy_tbl tbl) {this.tbl = tbl;}
	public Xof_orig_regy_tbl__in_wkr Init(OrderedHash rv, ListAdp itms) {this.itms = itms; this.rv = rv; return this;}
	@Override protected int Interval() {return Sqlite_engine_.Stmt_arg_max;}
	@Override protected Db_qry Make_qry(Object db_ctx, int bgn, int end) {
		Object[] part_ary = In_ary(end - bgn);
		return Db_qry_.select_cols_(tbl.Tbl_name(), Db_crt_.in_(tbl.Fld_orig_ttl(), part_ary), tbl.Flds().To_str_ary());
	}
	@Override protected void Fill_stmt(Db_stmt stmt, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			byte[] ttl = (byte[])itms.FetchAt(i);
			stmt.Crt_bry_as_str(tbl.Fld_orig_ttl(), ttl);
		}
	}
	@Override protected void Read_data(Cancelable cancelable, Object db_ctx, Db_rdr rdr) {
		while (rdr.Move_next()) {
			if (cancelable.Canceled()) return;
			Xof_orig_regy_itm itm = tbl.Make_itm(rdr);
			byte[] itm_ttl = itm.Ttl();
			if (!rv.Has(itm_ttl))	// guard against dupes (shouldn't happen)
				rv.Add(itm_ttl, itm);
		}
	}
}
