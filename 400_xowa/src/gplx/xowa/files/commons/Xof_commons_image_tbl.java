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
package gplx.xowa.files.commons; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.dbs.*;
public class Xof_commons_image_tbl implements Rls_able {
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
	private static final String tbl_name = "image"; private static final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private static final    String
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
	public static Dbmeta_tbl_itm new_meta() {
		return Dbmeta_tbl_itm.New(tbl_name, flds.To_fld_ary()
		, Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "name", fld_img_name, fld_img_timestamp)
		);
	} 
}
