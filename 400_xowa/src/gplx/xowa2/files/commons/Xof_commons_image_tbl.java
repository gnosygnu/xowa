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
package gplx.xowa2.files.commons; import gplx.*; import gplx.xowa2.*; import gplx.xowa2.files.*;
import gplx.dbs.*;
public class Xof_commons_image_tbl implements Db_conn_itm {
	private Db_stmt stmt_insert, stmt_select_itm;
	public Db_conn Conn() {return conn;} public void Conn_(Db_conn v) {conn = Db_conn_.Reg_itm(this, conn, v);} private Db_conn conn;
	public void Conn_term() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_select_itm = Db_stmt_.Rls(stmt_select_itm);
	}
	public void Insert(String ttl, String media_type, String minor_mime, int size, int w, int h, int bits, int ext_id, String img_timestamp) {
		if (stmt_insert == null) stmt_insert = conn.New_stmt_insert(Tbl_name, Flds.To_str_ary());
		stmt_insert.Clear()
		.Val_str(ttl).Val_str(media_type).Val_str(minor_mime)
		.Val_int(size).Val_int(w).Val_int(h).Val_int(bits).Val_int(ext_id).Val_str(img_timestamp)
		.Exec_insert();
	}
	public Xof_commons_image_itm Select(byte[] ttl) {
		Db_stmt stmt = conn.New_stmt_select_all_where(Tbl_name, Flds.To_str_ary(), Fld_img_name);
		Db_rdr rdr = Db_rdr_.Null;
		try {
			rdr = stmt.Clear().Val_bry_as_str(ttl).Exec_select_as_rdr();
			if (!rdr.Move_next()) return null;
			return new Xof_commons_image_itm
			( rdr.Read_str(Fld_img_name)
			, rdr.Read_str(Fld_img_media_type)
			, rdr.Read_str(Fld_img_minor_mime)
			, rdr.Read_int(Fld_img_size)
			, rdr.Read_int(Fld_img_width)
			, rdr.Read_int(Fld_img_height)
			, rdr.Read_int(Fld_img_bits)
			, rdr.Read_int(Fld_img_ext_id)
			, rdr.Read_str(Fld_img_timestamp)
			);
		}	finally {rdr.Rls();}
	}
	private static final String Tbl_name = "image";
	private static final Db_meta_fld_list Flds = Db_meta_fld_list.new_();
	private static final String
	  Fld_img_name				= Flds.Add_str("img_name", 255)			// varbinary(255)
	, Fld_img_media_type		= Flds.Add_str("img_media_type", 255)	// enum('UNKNOWN','BITMAP','DRAWING','AUDIO','VIDEO','MULTIMEDIA','OFFICE','TEXT','EXECUTABLE','ARCHIVE')"
	, Fld_img_minor_mime		= Flds.Add_str("img_minor_mime", 255)	// DEFAULT 'unknown'"
	, Fld_img_size				= Flds.Add_int("img_size")				// int(8) unsigned
	, Fld_img_width				= Flds.Add_int("img_width")				// int(5)
	, Fld_img_height			= Flds.Add_int("img_height")			// int(5)
	, Fld_img_bits				= Flds.Add_short("img_bits")			// int(3)
	, Fld_img_ext_id			= Flds.Add_int("img_ext_id")			// xowa
	, Fld_img_timestamp			= Flds.Add_str("img_timestamp", 255)	// 20140101155749
	;
	public static Db_meta_tbl new_meta() {
		return Db_meta_tbl.new_(Tbl_name, Flds.To_fld_ary()
		, Db_meta_idx.new_normal(Tbl_name, "name", Fld_img_name, Fld_img_timestamp)
		);
	} 
}
