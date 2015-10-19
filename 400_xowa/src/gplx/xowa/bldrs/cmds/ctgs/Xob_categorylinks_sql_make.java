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
package gplx.xowa.bldrs.cmds.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.core.flds.*; import gplx.ios.*; import gplx.dbs.*; import gplx.xowa.wikis.dbs.*; import gplx.xowa.wikis.ctgs.*; 
import gplx.xowa.bldrs.sqls.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
public class Xob_categorylinks_sql_make implements Io_make_cmd {
	private Gfo_usr_dlg usr_dlg; private final Xowe_wiki wiki; private final Db_idx_mode idx_mode; private Xowd_db_mgr core_db_mgr;
	private final Sql_file_parser sql_parser; private final Gfo_fld_rdr fld_rdr = Gfo_fld_rdr.xowa_(); private Io_line_rdr name_id_rdr;
	private Xowd_cat_core_tbl cat_core_tbl; private Xowd_cat_link_tbl cat_link_tbl;
	private long cat_db_size, cat_db_max; private int cur_row_count;
	private int[] cur_cat_counts = new int[Xoa_ctg_mgr.Tid__max]; private byte[] cur_cat_ttl = Ttl_first; private int cur_cat_id; private int cur_cat_file_idx;
	public Xob_categorylinks_sql_make(Sql_file_parser sql_parser, Xowe_wiki wiki, Db_idx_mode idx_mode) {
		this.wiki = wiki; this.sql_parser = sql_parser; this.idx_mode = idx_mode; usr_dlg = wiki.Appe().Usr_dlg();
	}
	public void Sort_bgn() {			
		this.core_db_mgr	= wiki.Db_mgr_as_sql().Core_data_mgr();
		this.name_id_rdr	= New_registry_rdr(wiki, usr_dlg);
		this.cat_db_max		= wiki.Appe().Api_root().Bldr().Wiki().Import().Cat_link_db_max();
		boolean one_file		= core_db_mgr.Props().Layout_text().Tid_is_all_or_few();
		if (!one_file)		// cat is in its own dbs: delete dbs
			core_db_mgr.Dbs__delete_by_tid(Xowd_db_file_.Tid_cat, Xowd_db_file_.Tid_cat_core, Xowd_db_file_.Tid_cat_link);	// delete existing category files else upgrade won't work
		Db_make(Bool_.Y);
		if (one_file) {		// cat is in single db; delete tbls;
			cat_core_tbl.Delete_all();
			cat_link_tbl.Delete_all();
		}
	}
	public void Sort_do(Io_line_rdr rdr) {
		byte[] new_cat_ttl = Bry_.Empty;
		try {
			fld_rdr.Ini(rdr.Bfr(), rdr.Itm_pos_bgn());
			new_cat_ttl = fld_rdr.Read_bry_escape();
			if (!Bry_.Eq(new_cat_ttl, cur_cat_ttl))		// ttl changed
				cur_cat_id = Save_ctg(new_cat_ttl);
			if (cur_cat_id == Cur_cat_id_null) return;
			byte cat_tid = Convert_cat_tid_to_byte(fld_rdr.Read_byte());
			byte[] cat_sortkey = fld_rdr.Read_bry_escape();
			int page_id = fld_rdr.Read_int_base85_len5();
			int cat_timestamp = fld_rdr.Read_int_base85_len5();			
			cat_link_tbl.Insert_cmd_by_batch(page_id, cur_cat_id, cat_tid, cat_sortkey, cat_timestamp);
			cat_db_size += 34 + 20 + 12 + (cat_sortkey.length * 2);	// NOTE_1: categorylinks row size
			++cur_cat_counts[cat_tid];
			++cur_row_count;
			if (cur_row_count % 100000  == 0) usr_dlg.Prog_one("", "", "inserting category row: ~{0}", cur_row_count);
			if (cur_row_count % 1000000 == 0) {cat_core_tbl.Conn().Txn_sav(); cat_link_tbl.Conn().Txn_sav();}
		}	catch (Exception e) {usr_dlg.Warn_many("", "", "ctg_links.insert failed: name=~{0} err=~{1}", String_.new_u8(new_cat_ttl), Err_.Message_gplx_full(e));}
	}
	public void Sort_end() {
		Save_ctg(Ttl_last);
		Db_close();			
		Xodb_mgr_sql db_mgr_sql = wiki.Db_mgr_as_sql();
		if (db_mgr_sql.Category_version() == Xoa_ctg_mgr.Version_null)	// NOTE: ctg_v1 wkr will set this to v1; only set to v2 if null  
			db_mgr_sql.Category_version_update(false);
		usr_dlg.Log_many("", "", "import.category.v2: insert done; committing; rows=~{0}", cur_row_count);
		name_id_rdr.Rls();
		if (String_.Eq(sql_parser.Src_fil().NameAndExt(), Xob_ctg_v1_sql_make.Url_sql))	// delete temp xowa_categorylinks.sql file created by cat_v1
			Io_mgr.Instance.DeleteFil(sql_parser.Src_fil());
	}
	private int Save_ctg(byte[] new_ctg_ttl) {
		if (cur_cat_ttl != Bry_.Empty && cur_cat_id != -1)
			cat_core_tbl.Insert_cmd_by_batch(cur_cat_id, cur_cat_counts[Xoa_ctg_mgr.Tid_page], cur_cat_counts[Xoa_ctg_mgr.Tid_subc], cur_cat_counts[Xoa_ctg_mgr.Tid_file], Xoa_ctg_mgr.Hidden_n, cur_cat_file_idx);
		if (new_ctg_ttl == Ttl_last) return Cur_cat_id_null;	// last ttl; called by this.End(); exit early else will fail in Cur_cat_id_find()
		if (cat_db_size > cat_db_max) {Db_close(); Db_make(Bool_.N);}
		cur_cat_id = Cur_cat_id_find(new_ctg_ttl);
		for (int i = 0; i < Xoa_ctg_mgr.Tid__max; i++)
			cur_cat_counts[i] = 0;
		cur_cat_ttl = new_ctg_ttl;
		return cur_cat_id;
	}
	private void Db_make(boolean first) {
		boolean one_file = core_db_mgr.Props().Layout_text().Tid_is_all_or_few();
		if (first) {	// create cat_core
			Xowd_db_file cat_core_db = one_file ? core_db_mgr.Db__cat_core() : core_db_mgr.Dbs__make_by_tid(Xowd_db_file_.Tid_cat_core);
			this.cat_core_tbl = cat_core_db.Tbl__cat_core().Create_tbl();
		}
		Xowd_db_file cat_link_db = one_file ? core_db_mgr.Db__core() : core_db_mgr.Dbs__make_by_tid(Xowd_db_file_.Tid_cat_link);
		this.cat_link_tbl = cat_link_db.Tbl__cat_link();
		if (	(one_file && first)
			||	!one_file)
			cat_link_tbl.Create_tbl();
		this.cur_cat_file_idx = cat_link_db.Id();
		cat_core_tbl.Insert_bgn();
		cat_link_tbl.Insert_bgn();
		if (idx_mode.Tid_is_bgn()) cat_link_tbl.Create_idx();
	}
	private void Db_close() {
		cat_core_tbl.Insert_end();
		cat_link_tbl.Insert_end();
		cat_db_size = 0;
		if (idx_mode.Tid_is_end()) cat_link_tbl.Create_idx();
	}
	private static byte Convert_cat_tid_to_byte(byte ltr) {
		switch (ltr) {
			case Byte_ascii.Ltr_f:	return Xoa_ctg_mgr.Tid_file;
			case Byte_ascii.Ltr_p:	return Xoa_ctg_mgr.Tid_page;
			case Byte_ascii.Ltr_c:	return Xoa_ctg_mgr.Tid_subc;
			default:				throw Err_.new_unhandled(ltr);
		}
	}
	public Io_sort_cmd Make_dir_(Io_url v) {return this;}
	private int Cur_cat_id_find(byte[] ttl) {
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
	private static final int Cur_cat_id_null = -1;
	private static Io_line_rdr New_registry_rdr(Xowe_wiki wiki, Gfo_usr_dlg usr_dlg) {
		Io_url make_dir = Xob_category_registry_sql.Tmp_dir(wiki);
		usr_dlg.Prog_many("", "", "loading category_registry files: ~{0}", make_dir.Raw());
		Io_url[] urls = Io_mgr.Instance.QueryDir_args(make_dir).ExecAsUrlAry();
		return new Io_line_rdr(usr_dlg, urls).Key_gen_(Io_line_rdr_key_gen_.first_pipe);
	}
	private static final byte[] Ttl_last = null, Ttl_first = Bry_.Empty;
}
/*
NOTE_1: categorylinks row size: 34 + 20 + 12 + (cat_sortkey.length * 2)
row length (data)		: 34=8+4+4+14+4				ROWID, cl_from, cl_to_id, cl_timestamp, cl_type_id
cl_main length (idx)	: 20=8+4+4+4				ROWID, cl_from, cl_to_id, cl_type_id
cl_from length (idx)	: 12=8+4					ROWID, cl_from
variable_data length	: cat_sortkey.length * 2	sortkey is used for row and cl_main

Note the following
. ints are 4 bytes
. tinyint is assumed to be 4 bytes (should be 1, but sqlite only has one numeric datatype, so import all 4?)
. varchar(14) is assumed to be 14 bytes (should be 15? +1 for length of varchar?)
. calculations work out "too well". comparing 4 databases gets +/- .25 bytes per row. however
.. - bytes should not be possible
.. +.25 bytes is too low (18 MB out of 5.5 GB).*; there must be other bytes used for page breaks / fragmentation
*/
