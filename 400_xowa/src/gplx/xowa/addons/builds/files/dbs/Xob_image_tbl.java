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
package gplx.xowa.addons.builds.files.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.builds.*; import gplx.xowa.addons.builds.files.*;
import gplx.dbs.*; import gplx.dbs.engines.sqlite.*;
public class Xob_image_tbl {
	public Xob_image_tbl Create_table(Db_conn p) {Sqlite_engine_.Tbl_create_and_delete(p, Tbl_name, Tbl_sql); return this;}
	public Xob_image_tbl Create_index(Db_conn p) {Sqlite_engine_.Idx_create(p, Idx_img_name); return this;}
	public Db_stmt Insert_stmt(Db_conn p) {return Db_stmt_.new_insert_(p, Tbl_name, Fld_img_name, Fld_img_media_type, Fld_img_minor_mime, Fld_img_size, Fld_img_width, Fld_img_height, Fld_img_bits, Fld_img_ext_id, Fld_img_timestamp);}
	public void Insert(Db_stmt stmt, byte[] ttl, byte[] media_type, byte[] minor_mime, int size, int w, int h, int bits, int ext_id, byte[] img_timestamp) {
		stmt.Clear()
		.Val_bry_as_str(ttl)
		.Val_bry_as_str(media_type)
		.Val_bry_as_str(minor_mime)
		.Val_int(size)
		.Val_int(w)
		.Val_int(h)
		.Val_int(bits)
		.Val_int(ext_id)
		.Val_bry_as_str(img_timestamp)
		.Exec_insert();
	}
	public static final    String Tbl_name = "image"
	, Fld_img_name = "img_name", Fld_img_media_type = "img_media_type", Fld_img_minor_mime = "img_minor_mime"
	, Fld_img_size = "img_size", Fld_img_width = "img_width", Fld_img_height = "img_height", Fld_img_bits = "img_bits", Fld_img_ext_id = "img_ext_id"
	, Fld_img_timestamp = "img_timestamp"
	;
	private static final    String Tbl_sql = String_.Concat_lines_nl
	(	"CREATE TABLE IF NOT EXISTS image"
	,	"( img_name        varchar(255)    NOT NULL -- varbinary(255)"
	,	", img_media_type  varchar(64)     NOT NULL -- enum('UNKNOWN','BITMAP','DRAWING','AUDIO','VIDEO','MULTIMEDIA','OFFICE','TEXT','EXECUTABLE','ARCHIVE')"
	,	", img_minor_mime  varchar(32)     NOT NULL -- DEFAULT 'unknown'"
	,	", img_size        integer         NOT NULL -- int(8) unsigned"
	,	", img_width       integer         NOT NULL -- int(5)"
	,	", img_height      integer         NOT NULL -- int(5)"
	,	", img_bits        smallint        NOT NULL -- int(3)"
	,	", img_ext_id      int             NOT NULL -- xowa"
	,	", img_timestamp   varchar(14)     NOT NULL -- 20140101155749"
	,	");"
	);
	private static final    Db_idx_itm
		Idx_img_name     	= Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS image__img_name ON image (img_name, img_timestamp);")
	;
}
