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
package gplx.xowa.html.hdumps.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.hdumps.*;
import gplx.core.brys.*;
import gplx.dbs.*; import gplx.xowa.bldrs.oimgs.*;
class Xob_lnki_regy_mgr {
	private final OrderedHash hash = OrderedHash_.new_bry_(); private final Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	private Rl_dump_tbl dump_tbl = new Rl_dump_tbl(); private Db_stmt dump_insert;
	private int itm_count, itm_max = 10000000;
	public void Init_by_wiki(Xodb_db_file make) {
		dump_insert = dump_tbl.Insert_stmt(make.Conn());
	}
	public void Page_bgn(int page_id) {this.page_id = page_id;} private int page_id;
	public void Add(int uid, int ns_id, byte[] ttl) {
		Xob_lnki_regy_itm regy_itm = Get_or_new(ns_id, ttl);
		regy_itm.Pages().Add(new Redlink_page_itm(page_id, uid));
		if (++itm_count > itm_max)
			Save();
	}
	private Xob_lnki_regy_itm Get_or_new(int ns_id, byte[] ttl) {
		byte[] hash_key = tmp_bfr.Add_int_variable(ns_id).Add_byte_pipe().Add(ttl).Xto_bry_and_clear();
		Xob_lnki_regy_itm rv = (Xob_lnki_regy_itm)hash.Fetch(hash_key);
		if (rv == null) {
			rv = new Xob_lnki_regy_itm(hash_key, ns_id, ttl);
			hash.Add(hash_key, rv);
		}
		return rv;
	}
	public void Save() {
		int len = hash.Count();
		for (int i = 0; i < len; ++i) {
			Xob_lnki_regy_itm itm = (Xob_lnki_regy_itm)hash.FetchAt(i);
			dump_tbl.Insert(dump_insert, itm.Ns_id(), itm.Ttl(), itm.Pages_to_bry(tmp_bfr));
		}
	}
}
class Xob_lnki_regy_itm {
	public Xob_lnki_regy_itm(byte[] key, int ns_id, byte[] ttl) {this.key = key; this.ns_id = ns_id; this.ttl = ttl;}
	public byte[] Key() {return key;} private final byte[] key;
	public int Ns_id() {return ns_id;} private final int ns_id;
	public byte[] Ttl() {return ttl;} private final byte[] ttl;
	public ListAdp Pages() {return pages;} private final ListAdp pages = ListAdp_.new_();
	public byte[] Pages_to_bry(Bry_bfr bfr) {
		int len = pages.Count();
		for (int i = 0; i < len; ++i) {
			Redlink_page_itm page_itm = (Redlink_page_itm)pages.FetchAt(i);
			page_itm.Write_to_bfr(bfr);
		}
		return bfr.Xto_bry_and_clear();
	}
}
class Redlink_page_itm {
	public Redlink_page_itm(int page_id, int lnki_uid) {this.page_id = page_id; this.lnki_uid = lnki_uid;}
	public int Page_id() {return page_id;} private final int page_id;
	public int Lnki_uid() {return lnki_uid;} private final int lnki_uid;
	public void Write_to_bfr(Bry_bfr bfr) {
		bfr	.Add_int_variable(page_id).Add_byte_comma()
			.Add_int_variable(lnki_uid).Add_byte_semic()
			;
	}
}
class Redlink_wkr {
	public void Bld() {
		Bry_bfr bfr = Bry_bfr.reset_(Io_mgr.Len_mb);
		Db_rdr rdr = Db_rdr_.Null;
		int cur_page_id = -1;
		while (rdr.Move_next()) {
			int lnki_page_id			= rdr.Read_int(0);
			if (lnki_page_id != cur_page_id) {
				Save(cur_page_id, bfr.Xto_bry_and_clear());
				cur_page_id = lnki_page_id;
			}
			int html_uid			= rdr.Read_int(1);
			bfr.Add_int_variable(html_uid).Add_byte_pipe();
		}
		Save(cur_page_id, bfr.Xto_bry_and_clear());;
	}
	private void Save(int page_id, byte[] data) {
		if (page_id == -1 || data.length == 0) return;
	}
	public void Gen() {
		Bry_rdr bry_rdr = new Bry_rdr();
		Db_rdr rdr = Db_rdr_.Null;
		Db_stmt stmt = Db_stmt_.Null;
		while (rdr.Move_next()) {
			int lnki_id				= rdr.Read_int(0);
			int lnki_page_id		= rdr.Read_int(1);
			byte[] page_ids			= rdr.Read_bry(2);
			Save_rl_html(stmt, lnki_id, lnki_page_id, bry_rdr.Src_(page_ids));
		}
	}
	private void Save_rl_html(Db_stmt stmt, int lnki_id, int lnki_page_id, Bry_rdr rdr) {
		while (!rdr.Pos_is_eos()) {
			int page_id = rdr.Read_int_to_comma();
			int html_uid = rdr.Read_int_to_semic();
			stmt.Val_int(page_id).Val_int(html_uid).Val_int(lnki_id).Exec_insert();
		}
	}
}
/*	
CREATE TABLE xtn_gallery
( page_id			integer		NOT NULL
, html_uid			integer		NOT NULL
, box_max			integer		NOT NULL
, box_w				integer		NOT NULL
, img_w				integer		NOT NULL
, img_pad			integer		NOT NULL
);

CREATE TABLE rl_dump
( lnki_ns						int						NOT NULL
, lnki_ttl						varchar(255)            NOT NULL
, page_ids						varchar(max)            NOT NULL	--pair of page_id,html_uid; 0,1;5,2
);

CREATE TABLE rl_regy
( lnki_id                     integer                 NOT NULL PRIMARY KEY AUTOINCREMENT
, lnki_ns                     integer                 NOT NULL
, lnki_ttl                    varchar(255)            NOT NULL
, page_id                     integer                 NOT NULL
);

INSERT INTO rl_regy (lnki_ns, lnki_ttl)
SELECT	lnki_ns, lnki_ttl
FROM	rl_dump
GROUP BY lnki_ns, lnki_ttl
;

CREATE UNIQUE INDEX rl_regy__lnki_ns_ttl ON rl_regy (lnki_ns, lnki_ttl);

REPLACE INTO rl_regy
SELECT	r.lnki_id
,		r.lnki_ns
,		r.lnki_ttl
,		Coalesce(p.page_id, -1)
FROM	rl_regy r
		LEFT JOIN page_db.page p ON r.lnki_ns = p.page_namespace AND r.lnki_ttl = p.page_title
;

--CREATE UNIQUE INDEX rl_regy__lnki_ns_ttl ON rl_regy (lnki_ns, lnki_ttl);

SELECT	r.lnki_ns
,		r.lnki_ttl
,		r.page_id
,		r.lnki_id
,		d.page_ids
FROM	rl_dump d
	    JOIN rl_regy r ON d.lnki_ns = r.lnki_ns AND d.lnki_ttl = r.lnki_ttl
WHERE	r.page_id = -1
ORDER BY r.lnki_id
;

CREATE TABLE rl_html
( page_id                     integer                 NOT NULL
, html_uid                    integer                 NOT NULL
, lnki_id                     integer                 NOT NULL
, lnki_page_id                integer                 NOT NULL
)

SELECT	page_id
,       html_uid
,		lnki_id
,		lnki_page_id
WHERE	lnki_page_id = -1
ORDER BY page_id, html_uid
;
*/
class Rl_dump_tbl {
	public Db_stmt Insert_stmt(Db_conn conn) {return Db_stmt_.new_insert_(conn, Tbl_name, Fld_lnki_ns, Fld_lnki_ttl, Fld_page_ids);}
	public void Insert(Db_stmt stmt, int lnki_ns, byte[] lnki_ttl, byte[] page_ids) {
		stmt.Val_int(lnki_ns).Val_bry_as_str(lnki_ttl).Val_bry(page_ids).Exec_insert();
	}
	public static final String Tbl_name = "rl_dump", Fld_lnki_ns = "lnki_ns", Fld_lnki_ttl = "lnki_ttl", Fld_page_ids = "page_ids";
	public static final String Tbl_sql = String_.Concat_lines_crlf_skipLast
	( "CREATE TABLE rl_dump"
	, "( lnki_ns                     integer                 NOT NULL"
	, ", lnki_ttl                    varchar(255)            NOT NULL"
	, ", page_ids                    mediumblob              NOT NULL"
	, ");"
	);
	public static final Db_idx_itm
	  Idx_lnki_ns_ttl	= Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS rl_dump__lnki_ns_ttl ON rl_dump (lnki_ns, lnki_ttl);")
	;
}
class Rl_html_tbl {
	public static final String Tbl_name = "rl_html", Fld_page_id = "page_id", Fld_html_uid = "html_uid", Fld_lnki_id = "lnki_id", Fld_lnki_page_id = "lnki_page_id";
//	CREATE TABLE rl_html
//	( page_id                     integer                 NOT NULL
//	, html_uid                    integer                 NOT NULL
//	, lnki_id                     integer                 NOT NULL
//	, lnki_page_id                integer                 NOT NULL
//	)
	public static final String Tbl_sql = String_.Concat_lines_crlf_skipLast
	( "CREATE TABLE rl_html"
	, "( page_id                     integer                 NOT NULL"
	, ", html_uid                    integer                 NOT NULL"
	, ", lnki_id                     integer                 NOT NULL"
	, ", lnki_page_id                integer                 NOT NULL"
	, ");"
	);
	public static final Db_idx_itm
	  Idx_lnki_ns_ttl	= Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS rl_dump__lnki_ns_ttl ON rl_dump (lnki_ns, lnki_ttl);")
	;
}
