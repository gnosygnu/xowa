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
package gplx.xowa.dbs.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.dbs.*;
import gplx.dbs.*;
public class Xodb_category_tbl {
	public Db_stmt Update_stmt(Db_conn p) {return Db_stmt_.new_update_(p, Tbl_name, String_.Ary(Fld_cat_id), Fld_cat_hidden);}
	public void Update(Db_stmt stmt, int cat_id, byte cat_hidden) {
		stmt.Clear()
			.Val_byte(cat_hidden)
			.Val_int(cat_id)
			.Exec_update()
			;
	}
	public Db_stmt Insert_stmt(Db_conn p) {return Db_stmt_.new_insert_(p, Tbl_name, Fld_cat_id, Fld_cat_pages, Fld_cat_subcats, Fld_cat_files, Fld_cat_hidden, Fld_cat_file_idx);}
	public void Insert(Db_stmt stmt, int cat_id, int cat_pages, int cat_subcats, int cat_files, byte cat_hidden, int cat_file_idx) {
		stmt.Clear()
			.Val_int(cat_id)
			.Val_int(cat_pages)
			.Val_int(cat_subcats)
			.Val_int(cat_files)
			.Val_byte(cat_hidden)
			.Val_int(cat_file_idx)
			.Exec_insert()
			;
	}
	public Xodb_category_itm Select(Db_conn p, int cat_page_id) {
		Db_stmt stmt = Db_stmt_.Null;
		DataRdr rdr = DataRdr_.Null;
		try {
			stmt = Db_stmt_.new_select_(p, Tbl_name, String_.Ary(Fld_cat_id));
			rdr = stmt.Val_int(cat_page_id).Exec_select();
			if (rdr.MoveNextPeer()) {
				return	Xodb_category_itm.load_
					(	cat_page_id
					,	rdr.ReadInt(Fld_cat_file_idx)
					,	rdr.ReadByte(Fld_cat_hidden) == Bool_.Y_byte
					,	rdr.ReadInt(Fld_cat_subcats)
					,	rdr.ReadInt(Fld_cat_files)
					,	rdr.ReadInt(Fld_cat_pages)
					);
			}
		}	finally {stmt.Rls(); rdr.Rls();}
		return Xodb_category_itm.Null;
	}
	public void Select_by_cat_id_in(Cancelable cancelable, OrderedHash rv, Db_conn p, Xodb_ctx db_ctx, int bgn, int end) {
		Xodb_in_wkr_category_id wkr = new Xodb_in_wkr_category_id();
		wkr.Init(rv);
		wkr.Select_in(p, cancelable, db_ctx, bgn, end);
	}
	public static Xodb_category_itm Read_ctg(DataRdr rdr) {
		return Xodb_category_itm.load_
		(	rdr.ReadInt(Xodb_category_tbl.Fld_cat_id)
		,	rdr.ReadInt(Xodb_category_tbl.Fld_cat_file_idx)
		,	rdr.ReadByte(Xodb_category_tbl.Fld_cat_hidden) == Bool_.Y_byte
		,	rdr.ReadInt(Xodb_category_tbl.Fld_cat_subcats)
		,	rdr.ReadInt(Xodb_category_tbl.Fld_cat_files)
		,	rdr.ReadInt(Xodb_category_tbl.Fld_cat_pages)
		);		
	}
	public static final String Tbl_name = "category", Fld_cat_id = "cat_id", Fld_cat_pages = "cat_pages", Fld_cat_subcats = "cat_subcats", Fld_cat_files = "cat_files", Fld_cat_hidden = "cat_hidden", Fld_cat_file_idx = "cat_file_idx";
}
