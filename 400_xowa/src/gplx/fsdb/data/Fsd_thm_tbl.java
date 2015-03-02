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
package gplx.fsdb.data; import gplx.*; import gplx.fsdb.*;
import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.dbs.engines.sqlite.*;
import gplx.fsdb.meta.*;
public class Fsd_thm_tbl {
	private String tbl_name = "file_data_thm"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private String fld_id, fld_owner_id, fld_w, fld_h, fld_time, fld_page, fld_bin_db_id, fld_size, fld_modified, fld_hash, fld_thumbtime;		
	private Db_conn conn; private Db_stmt stmt_insert, stmt_select_by_fil_w;
	private Fsm_atr_fil atr_fil;		
	public void Conn_(Db_conn new_conn, boolean created, boolean schema_is_1, Fsm_atr_fil atr_fil) {
		this.conn = new_conn; flds.Clear(); this.atr_fil = atr_fil;
		String fld_prefix = "";
		if (schema_is_1) {
			tbl_name		= "fsdb_xtn_thm";
			fld_prefix		= "thm_";
		}
		fld_id				= flds.Add_int(fld_prefix + "id");
		fld_owner_id		= flds.Add_int(fld_prefix + "owner_id");
		fld_w				= flds.Add_int(fld_prefix + "w");
		fld_h				= flds.Add_int(fld_prefix + "h");
		fld_time			= flds.Add_double(fld_prefix + "time");
		fld_page			= flds.Add_int(fld_prefix + "page");
		fld_bin_db_id		= flds.Add_int(fld_prefix + "bin_db_id");
		fld_size			= flds.Add_long(fld_prefix + "size");
		fld_modified		= flds.Add_str(fld_prefix + "modified", 14);		// stored as yyyyMMddHHmmss
		fld_hash			= flds.Add_str(fld_prefix + "hash", 40);
		if (created) {
			Db_meta_tbl meta = Db_meta_tbl.new_(tbl_name, flds
			, Db_meta_idx.new_unique_by_tbl(tbl_name, "pkey", fld_id)
			, Db_meta_idx.new_unique_by_tbl(tbl_name, "owner", fld_owner_id, fld_id, fld_w, fld_time, fld_page)
			);
			conn.Exec_create_tbl_and_idx(meta);
		}
		stmt_insert = stmt_select_by_fil_w = null;
		schema_thm_page_init = true;
	}
	private boolean Schema_thm_page() {
		if (schema_thm_page_init) {
			schema_thm_page = atr_fil.Abc_mgr().Cfg_mgr().Schema_thm_page();
			schema_thm_page_init = false;
			if (schema_thm_page) {
				fld_thumbtime	= Db_meta_fld.Key_null;
			}
			else {
				fld_time		= Db_meta_fld.Key_null;
				fld_page		= Db_meta_fld.Key_null;
			}
		}
		return schema_thm_page;
	}	private boolean schema_thm_page, schema_thm_page_init = true;
	public void Insert(int id, int thm_owner_id, int width, int height, double thumbtime, int page, int bin_db_id, long size, DateAdp modified, String hash) {
		if (stmt_insert == null) {
			String tmp_fld_time = this.Schema_thm_page() ? fld_time : fld_thumbtime;
			stmt_insert = conn.Rls_reg(conn.Stmt_insert(tbl_name, fld_id, fld_owner_id, fld_w, fld_h, tmp_fld_time, fld_page, fld_bin_db_id, fld_size, fld_modified, fld_hash));
		}
		stmt_insert.Clear()
		.Val_int(fld_id, id)
		.Val_int(fld_owner_id, thm_owner_id)
		.Val_int(fld_w, width)
		.Val_int(fld_h, height);
		if (this.Schema_thm_page()) {
			stmt_insert.Val_double	(fld_time, gplx.xowa.files.Xof_doc_thumb.Db_save_double(thumbtime));
			stmt_insert.Val_int		(fld_page, gplx.xowa.files.Xof_doc_page.Db_save_int(page));
		}
		else
			stmt_insert.Val_int		(fld_thumbtime, gplx.xowa.files.Xof_doc_thumb.Db_save_int(thumbtime));
		stmt_insert
		.Val_int(fld_bin_db_id, bin_db_id)
		.Val_long(fld_size, size)
		.Val_str(fld_modified, Sqlite_engine_.X_date_to_str(modified))
		.Val_str(fld_hash, hash)
		.Exec_insert();
	}
	private Db_stmt Select_by_fil_w_stmt() {
		Db_qry_select qry = Db_qry_.select_().From_(tbl_name).Cols_all_();
		gplx.core.criterias.Criteria crt 
			= this.Schema_thm_page()
			? Db_crt_.eq_many_(fld_owner_id, fld_w, fld_time, fld_page)
			: Db_crt_.eq_many_(fld_owner_id, fld_w, fld_thumbtime)
			;
		qry.Where_(crt);
		return conn.Stmt_new(qry);
	}
	public boolean Select_itm_by_fil_width(int owner_id, Fsd_thm_itm thm) {
		if (stmt_select_by_fil_w == null) stmt_select_by_fil_w = conn.Rls_reg(Select_by_fil_w_stmt());
		Db_rdr rdr = Db_rdr_.Null;
		try {
			stmt_select_by_fil_w.Clear()
				.Crt_int(fld_owner_id, owner_id)
				.Crt_int(fld_w, thm.Width())
				;
			if (this.Schema_thm_page())  {
				stmt_select_by_fil_w.Crt_double(fld_time, gplx.xowa.files.Xof_doc_thumb.Db_save_double(thm.Thumbtime()));
				stmt_select_by_fil_w.Crt_int(fld_page, gplx.xowa.files.Xof_doc_page.Db_save_int(thm.Page()));
			}
			else {
				stmt_select_by_fil_w.Crt_int(fld_time, gplx.xowa.files.Xof_doc_thumb.Db_save_int(thm.Thumbtime()));
			}
			rdr = stmt_select_by_fil_w.Exec_select_as_rdr();
			return rdr.Move_next()
				? Ctor_by_load(thm, rdr, this.Schema_thm_page())
				: false;
		}
		finally {rdr.Rls();}
	}
	private boolean Ctor_by_load(Fsd_thm_itm itm, Db_rdr rdr, boolean schema_thm_page) {
		int id = rdr.Read_int(fld_id);
		int owner_id = rdr.Read_int(fld_owner_id);
		int width = rdr.Read_int(fld_w);
		int height = rdr.Read_int(fld_h);
		long size = rdr.Read_long(fld_size);
		String modified = rdr.Read_str(fld_modified);
		String hash = rdr.Read_str(fld_hash);
		int bin_db_id = rdr.Read_int(fld_bin_db_id);
		double time = 0;
		int page = 0;
		if (schema_thm_page) {
			time = gplx.xowa.files.Xof_doc_thumb.Db_load_double(rdr, fld_time);
			page = gplx.xowa.files.Xof_doc_page.Db_load_int(rdr, fld_page);
		}
		else {
			time = gplx.xowa.files.Xof_doc_thumb.Db_load_int(rdr, fld_thumbtime);
			page = gplx.xowa.files.Xof_doc_page.Null;
		}
		itm.Ctor(id, owner_id, width, time, page, height, size, modified, hash);
		itm.Db_bin_id_(bin_db_id);
		return true;
	}
	public static final DateAdp Modified_null = null;
	public static final String Hash_null = "";
}
