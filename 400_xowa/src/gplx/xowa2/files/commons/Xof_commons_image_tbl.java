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
public class Xof_commons_image_tbl implements RlsAble {
	private Db_stmt stmt_insert;
	public Db_conn Conn() {return conn;}
	public void Conn_(Db_conn v) {
		conn = v;
		stmt_insert = null;
		conn.Rls_reg(this);
	}	private Db_conn conn;
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
	public void Insert(String ttl, String media_type, String minor_mime, int size, int w, int h, int bits, int ext_id, String img_timestamp) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
		.Val_str(ttl).Val_str(media_type).Val_str(minor_mime)
		.Val_int(size).Val_int(w).Val_int(h).Val_int(bits).Val_int(ext_id).Val_str(img_timestamp)
		.Exec_insert();
	}
	public Xof_commons_image_itm Select(byte[] ttl) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_img_name).Clear().Val_bry_as_str(ttl).Exec_select__rls_auto();
		try {
			if (!rdr.Move_next()) return null;
			return new Xof_commons_image_itm
			( rdr.Read_str(fld_img_name)
			, rdr.Read_str(fld_img_media_type)
			, rdr.Read_str(fld_img_minor_mime)
			, rdr.Read_int(fld_img_size)
			, rdr.Read_int(fld_img_width)
			, rdr.Read_int(fld_img_height)
			, rdr.Read_int(fld_img_bits)
			, rdr.Read_int(fld_img_ext_id)
			, rdr.Read_str(fld_img_timestamp)
			);
		}	finally {rdr.Rls();}
	}
	private static final String tbl_name = "image"; private static final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private static final String
	  fld_img_name				= flds.Add_str("img_name", 255)			// varbinary(255)
	, fld_img_media_type		= flds.Add_str("img_media_type", 255)	// enum('UNKNOWN','BITMAP','DRAWING','AUDIO','VIDEO','MULTIMEDIA','OFFICE','TEXT','EXECUTABLE','ARCHIVE')"
	, fld_img_minor_mime		= flds.Add_str("img_minor_mime", 255)	// DEFAULT 'unknown'"
	, fld_img_size				= flds.Add_int("img_size")				// int(8) unsigned
	, fld_img_width				= flds.Add_int("img_width")				// int(5)
	, fld_img_height			= flds.Add_int("img_height")			// int(5)
	, fld_img_bits				= flds.Add_short("img_bits")			// int(3)
	, fld_img_ext_id			= flds.Add_int("img_ext_id")			// xowa
	, fld_img_timestamp			= flds.Add_str("img_timestamp", 255)	// 20140101155749
	;
	public static Db_meta_tbl new_meta() {
		return Db_meta_tbl.new_(tbl_name, flds.To_fld_ary()
		, Db_meta_idx.new_normal_by_tbl(tbl_name, "name", fld_img_name, fld_img_timestamp)
		);
	} 
}
