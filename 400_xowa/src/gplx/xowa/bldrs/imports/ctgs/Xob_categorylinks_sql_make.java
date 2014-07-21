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
package gplx.xowa.bldrs.imports.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.imports.*;
import gplx.core.flds.*; import gplx.ios.*; import gplx.dbs.*; import gplx.xowa.dbs.*; import gplx.xowa.ctgs.*; 
public class Xob_categorylinks_sql_make implements Io_make_cmd {
	public Xob_categorylinks_sql_make(Sql_file_parser sql_parser, Xow_wiki wiki) {this.wiki = wiki; this.sql_parser = sql_parser;} private Xow_wiki wiki; Xodb_mgr_sql db_mgr; Sql_file_parser sql_parser;
	public Io_sort_cmd Make_dir_(Io_url v) {return this;}
	public void Sort_bgn() {
		usr_dlg = wiki.App().Usr_dlg();
		db_mgr = Xodb_mgr_sql.Get_or_load(wiki);
		name_id_rdr = New_registry_rdr(wiki, usr_dlg);
		cur_cat_file_max = wiki.App().Setup_mgr().Dump_mgr().Db_categorylinks_max();

		db_mgr.Delete_by_tid(Xodb_file_tid_.Tid_category);
		Xodb_fsys_mgr fsys_mgr = db_mgr.Fsys_mgr();
		Xodb_file category_file = fsys_mgr.Get_tid_root(Xodb_file_tid_.Tid_core);
		if (cur_cat_file_max > 0) {
			category_file = fsys_mgr.Make(Xodb_file_tid_.Tid_category);
			fsys_mgr.Init_by_tid_category(category_file);
		}

		cat_provider = db_mgr.Fsys_mgr().Category_provider();
		ctg_stmt = db_mgr.Tbl_category().Insert_stmt(cat_provider);
		File_open(category_file);
		first_provider = true;
	}	Db_provider cl_provider, cat_provider; Gfo_fld_rdr fld_rdr = Gfo_fld_rdr.xowa_(); Db_stmt cl_stmt = Db_stmt_.Null, ctg_stmt = Db_stmt_.Null; int row_count = 0; Gfo_usr_dlg usr_dlg; boolean first_provider;
	int[] cur_cat_counts = new int[Xoa_ctg_mgr.Tid__max]; long cur_cat_file_size, cur_cat_file_max; byte[] cur_cat_ttl = Ttl_first; int cur_cat_id; int cur_cat_file_idx;
	public byte Line_dlm() {return line_dlm;} public Xob_categorylinks_sql_make Line_dlm_(byte v) {line_dlm = v; return this;} private byte line_dlm = Byte_ascii.Nil;
	public void Sort_do(Io_line_rdr rdr) {
		byte[] ctg_name = Bry_.Empty;
		try {
			if (line_dlm == Byte_ascii.Nil) line_dlm = rdr.Line_dlm();
			fld_rdr.Ini(rdr.Bfr(), rdr.Itm_pos_bgn());
			ctg_name = fld_rdr.Read_bry_escape();
			if (!Bry_.Eq(ctg_name, cur_cat_ttl)) cur_cat_id = Ctg_grp_end(ctg_name);
			if (cur_cat_id == Cur_cat_id_null) return;
			byte ctg_tid = Ctg_tid_ltr_to_byte(fld_rdr.Read_byte());
			byte[] ctg_sortkey = fld_rdr.Read_bry_escape();
			int page_id = fld_rdr.Read_int_base85_len5();
			int ctg_added = fld_rdr.Read_int_base85_len5();			
			db_mgr.Tbl_categorylinks().Insert(cl_stmt, page_id, cur_cat_id, ctg_sortkey, ctg_added, ctg_tid);
			cur_cat_file_size += 34 + 20 + 12 + (ctg_sortkey.length * 2);	// see NOTE_1: categorylinks row size
			++cur_cat_counts[ctg_tid];
			++row_count;
			if (row_count % 100000  == 0) usr_dlg.Prog_one("", "", "inserting category row: ~{0}", row_count);
			if (row_count % 1000000 == 0) cl_provider.Txn_mgr().Txn_end_all_bgn_if_none();
		}	catch (Exception e) {usr_dlg.Warn_many("", "", "ctg_links.insert failed: name=~{0} err=~{1}", String_.new_utf8_(ctg_name), Err_.Message_gplx_brief(e));}
	}
	public void Sort_end() {
		Xodb_fsys_mgr fsys_mgr = db_mgr.Fsys_mgr();
		Ctg_grp_end(Ttl_last);
		File_close();
		db_mgr.Tbl_xowa_db().Commit_all(fsys_mgr.Core_provider(), fsys_mgr.Ary());
		if (db_mgr.Category_version() == Xoa_ctg_mgr.Version_null)	// NOTE: ctg_v1 wkr will set this to v1; only set to v2 if null  
			db_mgr.Category_version_update(false);
		cat_provider.Txn_mgr().Txn_end_all();
		ctg_stmt.Rls();
		fsys_mgr.Index_create(usr_dlg, Byte_.Ary(Xodb_file_tid_.Tid_core, Xodb_file_tid_.Tid_category), Index_categorylinks_from, Index_categorylinks_main);
		name_id_rdr.Rls();
		if (String_.Eq(sql_parser.Src_fil().NameAndExt(), Xob_ctg_v1_sql_make.Url_sql))	// delete temp xowa_categorylinks.sql file created by cat_v1
			Io_mgr._.DeleteFil(sql_parser.Src_fil());
	}
	int Ctg_grp_end(byte[] new_ctg_ttl) {
		if (cur_cat_ttl != Bry_.Empty && cur_cat_id != -1)
			db_mgr.Tbl_category().Insert(ctg_stmt, cur_cat_id, cur_cat_counts[Xoa_ctg_mgr.Tid_page], cur_cat_counts[Xoa_ctg_mgr.Tid_subc], cur_cat_counts[Xoa_ctg_mgr.Tid_file], Xoa_ctg_mgr.Hidden_n, cur_cat_file_idx);
		if (new_ctg_ttl == Ttl_last) return Cur_cat_id_null;	// last ttl; called by this.End(); exit early else will fail in Cur_cat_id_find()
		if (cur_cat_file_max > 0 && cur_cat_file_size > cur_cat_file_max) {File_close(); File_open(db_mgr.Fsys_mgr().Make(Xodb_file_tid_.Tid_category));}
		cur_cat_id = Cur_cat_id_find(new_ctg_ttl);
		for (int i = 0; i < Xoa_ctg_mgr.Tid__max; i++)
			cur_cat_counts[i] = 0;
		cur_cat_ttl = new_ctg_ttl;
		return cur_cat_id;
	}
	private void File_open(Xodb_file file) {
		cl_provider = file.Provider();
		cl_stmt = db_mgr.Tbl_categorylinks().Insert_stmt(cl_provider);
		cl_provider.Txn_mgr().Txn_bgn_if_none();
		cur_cat_file_idx = file.Id();
	}
	private void File_close() {
		cl_provider.Txn_mgr().Txn_end_all();
		if (first_provider) {
			cat_provider.Txn_mgr().Txn_bgn_if_none();
		}
		cl_stmt.Rls();			
		cur_cat_file_size = 0;
	}
	private static byte Ctg_tid_ltr_to_byte(byte ltr) {
		switch (ltr) {
			case Byte_ascii.Ltr_f:	return Xoa_ctg_mgr.Tid_file;
			case Byte_ascii.Ltr_p:	return Xoa_ctg_mgr.Tid_page;
			case Byte_ascii.Ltr_c:	return Xoa_ctg_mgr.Tid_subc;
			default:				throw Err_.unhandled(ltr);
		}
	}
	static final int Size_categorylinks_row_fixed = 4 + 14 + 2;	// page_id + ctg_added + ctg_tid
	static final int Cur_cat_id_null = -1;
	int Cur_cat_id_find(byte[] ttl) {
		while (true) {
			int compare = Bry_.Compare(ttl, 0, ttl.length, name_id_rdr.Bfr(), name_id_rdr.Key_pos_bgn(), name_id_rdr.Key_pos_end());
			switch (compare) {
				case CompareAble_.Same: return Base85_utl.XtoIntByAry(name_id_rdr.Bfr(), name_id_rdr.Key_pos_end() + 1, name_id_rdr.Itm_pos_end() - 2);
				case CompareAble_.More:
					boolean reading = name_id_rdr.Read_next();
					if (!reading) return Cur_cat_id_null; // eof return
					break;
				case CompareAble_.Less: return Cur_cat_id_null;	// stop 
			}
		}
	}
	private static Io_line_rdr New_registry_rdr(Xow_wiki wiki, Gfo_usr_dlg usr_dlg) {
		Io_url make_dir = Xob_category_registry_sql.Get_dir_output(wiki);
		usr_dlg.Prog_many("", "", "loading category_registry files: ~{0}", make_dir.Raw());
		Io_url[] urls = Io_mgr._.QueryDir_args(make_dir).ExecAsUrlAry();
		return new Io_line_rdr(usr_dlg, urls).Key_gen_(Io_line_rdr_key_gen_.first_pipe);
	}	Io_line_rdr name_id_rdr;
	private static final byte[] Ttl_last = null, Ttl_first = Bry_.Empty;
	private static final Db_idx_itm
	  Index_categorylinks_main = Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS categorylinks__cl_main ON categorylinks (cl_to_id, cl_type_id, cl_sortkey, cl_from);")
	, Index_categorylinks_from = Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS categorylinks__cl_from ON categorylinks (cl_from);")
	;
}
/*
NOTE_1: categorylinks row size: 34 + 20 + 12 + (ctg_sortkey.length * 2)
row length (data)		: 34=8+4+4+14+4				ROWID, cl_from, cl_to_id, cl_timestamp, cl_type_id
cl_main length (idx)	: 20=8+4+4+4				ROWID, cl_from, cl_to_id, cl_type_id
cl_from length (idx)	: 12=8+4					ROWID, cl_from
variable_data length	: ctg_sortkey.length * 2	sortkey is used for row and cl_main

Note the following
. ints are 4 bytes
. tinyint is assumed to be 4 bytes (should be 1, but sqlite only has one numeric datatype, so import all 4?)
. varchar(14) is assumed to be 14 bytes (should be 15? +1 for length of varchar?)
. calculations work out "too well". comparing 4 databases gets +/- .25 bytes per row. however
.. - bytes should not be possible
.. +.25 bytes is too low (18 MB out of 5.5 GB).*; there must be other bytes used for page breaks / fragmentation
*/
