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
package gplx.xowa.files.fsdb; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.core.stores.*;
import gplx.dbs.*;
class Fsdb_regy_fil_tbl {
	public Fsdb_regy_fil_itm Select(String name, boolean is_orig, int w, int thumbtime) {
		return null;
	}
	public static final String Tbl_sql = String_.Concat_lines_nl
	( "CREATE TABLE regy_fil"
	, "( regy_id           integer       NOT NULL        PRIMARY KEY       AUTOINCREMENT"
	, ", wiki_type_id      integer"
	, ", wiki_date_id      integer"
	, ", fil_name          varchar(255)"
	, ", fil_is_orig       tinyint"
	, ", fil_w             integer"
	, ", fil_h             integer"
	, ", fil_thumbtime     integer"
	, ", fil_ext           integer"
	, ", fil_size          bigint"
	, ", bin_db_id         integer"
	, ", fil_id            integer"
	, ", bin_id            integer"
	, ");"
	);
	public static final String 
	  Fld_wiki_type_id = "wiki_type_id", Fld_wiki_date_id = "wiki_date_id", Fld_fil_name = "fil_name", Fld_fil_is_orig = "fil_is_orig"
	, Fld_fil_w = "fil_w", Fld_fil_h = "fil_h", Fld_fil_thumbtime = "fil_thumbtime", Fld_fil_ext = "fil_ext", Fld_fil_size = "fil_size"
	, Fld_bin_db_id = "bin_db_id", Fld_fil_id = "fil_id", Fld_bin_id = "bin_id"
	;
	public static final Db_idx_itm
		Idx_ttl     		= Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS fsdb_fil_regy__fil       ON fsdb (fil_name, fil_is_orig, fil_w, fil_h, fil_thumbtime, bin_db_id, fil_id, bin_id);")
	;
}
class Fsdb_regy_fil_itm {
	public int Wiki_type_id() {return wiki_type_id;} private int wiki_type_id;
	public int Wiki_date_id() {return wiki_date_id;} private int wiki_date_id;
	public String Fil_name() {return fil_name;} private String fil_name;
	public boolean Fil_is_orig() {return fil_is_orig;} private boolean fil_is_orig;
	public int Fil_w() {return fil_w;} private int fil_w;
	public int Fil_h() {return fil_h;} private int fil_h;
	public int Fil_thumbtime() {return fil_thumbtime;} private int fil_thumbtime;
	public int Fil_ext() {return fil_ext;} private int fil_ext;
	public long Fil_size() {return fil_size;} private long fil_size;
	public int Bin_db_id() {return bin_db_id;} private int bin_db_id;
	public int Fil_id() {return fil_id;} private int fil_id;
	public int Bin_id() {return bin_id;} private int bin_id;
	public void Load(DataRdr rdr) {
		wiki_date_id = rdr.ReadInt(Fsdb_regy_fil_tbl.Fld_wiki_date_id);
		wiki_type_id = rdr.ReadInt(Fsdb_regy_fil_tbl.Fld_wiki_type_id);
		fil_name = rdr.ReadStr(Fsdb_regy_fil_tbl.Fld_fil_name);
		fil_is_orig = rdr.ReadByte(Fsdb_regy_fil_tbl.Fld_fil_is_orig) != Byte_.Zero;
		fil_w = rdr.ReadInt(Fsdb_regy_fil_tbl.Fld_fil_w);
		fil_h = rdr.ReadInt(Fsdb_regy_fil_tbl.Fld_fil_h);
		fil_thumbtime = rdr.ReadInt(Fsdb_regy_fil_tbl.Fld_fil_thumbtime);
		fil_ext = rdr.ReadInt(Fsdb_regy_fil_tbl.Fld_fil_ext);
		fil_size = rdr.ReadLong(Fsdb_regy_fil_tbl.Fld_fil_size);
		bin_db_id = rdr.ReadInt(Fsdb_regy_fil_tbl.Fld_bin_db_id);
		fil_id = rdr.ReadInt(Fsdb_regy_fil_tbl.Fld_fil_id);
		bin_id = rdr.ReadInt(Fsdb_regy_fil_tbl.Fld_bin_id);
	}
}
