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
package gplx.xowa.bldrs.wikis.images; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wikis.*;
import gplx.dbs.*;
public class Xob_wiki_image_tbl {
	public Xob_wiki_image_tbl Create_table(Db_provider p) {Sqlite_engine_.Tbl_create_and_delete(p, Tbl_name, Tbl_sql); return this;}
	public Xob_wiki_image_tbl Create_index(Db_provider p) {Sqlite_engine_.Idx_create(p, Idx_img_name); return this;}
	public Db_stmt Insert_stmt(Db_provider p) {return Db_stmt_.new_insert_(p, Tbl_name, Fld_img_name, Fld_img_media_type, Fld_img_minor_mime, Fld_img_size, Fld_img_width, Fld_img_height, Fld_img_bits, Fld_img_ext_id);}
	public void Insert(Db_stmt stmt, byte[] ttl, byte[] media_type, byte[] minor_mime, int size, int w, int h, int bits, int ext_id) {
		stmt.Clear()
		.Val_str_by_bry_(ttl)
		.Val_str_by_bry_(media_type)
		.Val_str_by_bry_(minor_mime)
		.Val_int_(size)
		.Val_int_(w)
		.Val_int_(h)
		.Val_int_(bits)
		.Val_int_(ext_id)
		.Exec_insert();
	}
	public static final String Tbl_name = "image"
	, Fld_img_name = "img_name", Fld_img_media_type = "img_media_type", Fld_img_minor_mime = "img_minor_mime"
	, Fld_img_size = "img_size", Fld_img_width = "img_width", Fld_img_height = "img_height", Fld_img_bits = "img_bits", Fld_img_ext_id = "img_ext_id"
	;
	private static final String Tbl_sql = String_.Concat_lines_nl
	(	"CREATE TABLE IF NOT EXISTS image"
	,	"( img_name        varchar(255)    NOT NULL -- varbinary(255)"
	,	", img_media_type  varchar(64)     NOT NULL -- enum('UNKNOWN','BITMAP','DRAWING','AUDIO','VIDEO','MULTIMEDIA','OFFICE','TEXT','EXECUTABLE','ARCHIVE')"
	,	", img_minor_mime  varchar(32)     NOT NULL -- DEFAULT 'unknown'"
	,	", img_size        integer         NOT NULL -- int(8) unsigned"
	,	", img_width       integer         NOT NULL -- int(5)"
	,	", img_height      integer         NOT NULL -- int(5)"
	,	", img_bits        smallint        NOT NULL -- int(3)"
	,	", img_ext_id      int             NOT NULL -- xowa"
	,	");"
	);
	private static final Db_idx_itm
		Idx_img_name     	= Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS image__img_name ON image (img_name);")
	;
}
