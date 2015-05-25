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
package gplx.xowa.xtns.wdatas.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.dbs.*;
class Xowmf_general_tbl implements RlsAble {
	private static final String tbl_name = "wmf_general"; private final Db_meta_fld_list flds = new Db_meta_fld_list();
	private final String fld_site_id, fld_key, fld_val;
	private final Db_conn conn;
	private Db_stmt stmt_insert, stmt_select;
	public Xowmf_general_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_site_id = flds.Add_int("site_id");
		this.fld_key = flds.Add_str("prop_key", 255);
		this.fld_val = flds.Add_str("prop_val", 4096);
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;}
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds, Db_meta_idx.new_unique_by_name(tbl_name, "main", fld_site_id, fld_key, fld_val)));}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_select = Db_stmt_.Rls(stmt_select);
	}
	public void Delete_all() {conn.Stmt_delete(tbl_name, Db_meta_fld.Ary_empy).Exec_delete();}
	public void Insert(int site_id, String key, String val) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear().Val_int(fld_site_id, site_id).Val_str(fld_key, key).Val_str(fld_val, val).Exec_insert();
	}
	public String Select_val_or_null(int site_id, String key) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_site_id);
		Db_rdr rdr = stmt_select.Clear().Crt_int(fld_site_id, site_id).Crt_str(fld_key, key).Exec_select__rls_manual();
		try {
			return rdr.Move_next() ? rdr.Read_str(fld_val) : null;
		}
		finally {rdr.Rls();}
	}
}
//class Xowmf_general_row {
//	public String Main_page() {return main_page;} private final String main_page;
//	public String Base_url() {return base_url;} private final String base_url;
//	public String Site_name() {return site_name;} private final String site_name;
//	public String Logo() {return logo;} private final String logo;
//	public String Generator() {return generator;} private final String generator;
//	public String Php_version() {return php_version;} private final String php_version;
//	public String Php_sapi() {return php_sapi;} private final String php_sapi;
//	public String Hhvm_version() {return hhvm_version;} private final String hhvm_version;
//	public String Db_type() {return db_type;} private final String db_type;
//	public String Db_version() {return db_version;} private final String db_version;
//	public String Image_whitelist_enabled() {return image_whitelist_enabled;} private final String image_whitelist_enabled;
//	public String Lang_conversion() {return lang_conversion;} private final String lang_conversion;
//	public String Title_conversion() {return title_conversion;} private final String title_conversion;
//	public String Link_prefix_charset() {return link_prefix_charset;} private final String link_prefix_charset;
//	public String Link_prefix() {return link_prefix;} private final String link_prefix;
//	public String Link_trail() {return link_trail;} private final String link_trail;
//	public String Legal_title_chars() {return legal_title_chars;} private final String legal_title_chars;
//	public String Git_hash() {return git_hash;} private final String git_hash;
//	public String Git_branch() {return git_branch;} private final String git_branch;
//	public String Case_type() {return case_type;} private final String case_type;
//	public String Lang() {return lang;} private final String lang;
//	public String Fallback() {return fallback;} private final String fallback;
//	public String Fallback_8bit_encoding() {return fallback_8bit_encoding;} private final String fallback_8bit_encoding;
//	public String Write_api() {return write_api;} private final String write_api;
//	public String Time_zone() {return time_zone;} private final String time_zone;
//	public String Time_offset() {return time_offset;} private final String time_offset;
//	public String Article_path() {return article_path;} private final String article_path;
//	public String Script_path() {return script_path;} private final String script_path;
//	public String Script() {return script;} private final String script;
//	public String Variant_article_path() {return variant_article_path;} private final String variant_article_path;
//	public String Server() {return server;} private final String server;
//	public String Server_name() {return server_name;} private final String server_name;
//	public String Wiki_id() {return wiki_id;} private final String wiki_id;
//	public String Time() {return time;} private final String time;
//	public String Miser_mode() {return miser_mode;} private final String miser_mode;
//	public String Max_upload_size() {return max_upload_size;} private final String max_upload_size;
//	public String Thumb_limits() {return thumb_limits;} private final String thumb_limits;
//	public String Image_limits() {return image_limits;} private final String image_limits;
//	public String Fav_icon() {return fav_icon;} private final String fav_icon;
//}
/*
            "mainpage": "Main Page",
            "super": "https://en.wikipedia.org/wiki/Main_Page",
            "sitename": "Wikipedia",
            "logo": "//upload.wikimedia.org/wikipedia/en/b/bc/Wiki.png",
            "generator": "MediaWiki 1.26wmf1",
            "phpversion": "5.6.99-hhvm",
            "phpsapi": "srv",
            "hhvmversion": "3.3.1",
            "dbtype": "mysql",
            "dbversion": "10.0.16-MariaDB-log",
            "imagewhitelistenabled": "",
            "langconversion": "",
            "titleconversion": "",
            "linkprefixcharset": "",
            "linkprefix": "",
            "linktrail": "/^([a-z]+)(.*)$/sD",
            "legaltitlechars": " %!\"$&'()*,\\-.\\/0-9:;=?@A-Z\\\\^_`a-z~\\x80-\\xFF+",
            "git-hash": "155a3c1e83a2fc06f7921600f439771a82c4d50c",
            "git-branch": "wmf/1.26wmf1",
            "case": "first-letter",
            "lang": "en",
            "fallback": [],
            "fallback8bitEncoding": "windows-1252",
            "writeapi": "",
            "timezone": "UTC",
            "timeoffset": 0,
            "articlepath": "/wiki/$1",
            "scriptpath": "/w",
            "script": "/w/index.php",
            "variantarticlepath": false,
            "server": "//en.wikipedia.org",
            "servername": "en.wikipedia.org",
            "wikiid": "enwiki",
            "time": "2015-04-20T05:21:44Z",
            "misermode": "",
            "maxuploadsize": 1048576000,
            "thumblimits": [
                120,
                150,
                180,
                200,
                220,
                250,
                300
            ],
            "imagelimits": [
                {
                    "width": 320,
                    "height": 240
                },
                {
                    "width": 640,
                    "height": 480
                },
                {
                    "width": 800,
                    "height": 600
                },
                {
                    "width": 1024,
                    "height": 768
                },
                {
                    "width": 1280,
                    "height": 1024
                }
            ],
            "favicon": "//bits.wikimedia.org/favicon/wikipedia.ico"


            "mainpage": "Main Page",
            "super": "https://en.wikipedia.org/wiki/Main_Page",			
            "sitename": "Wikipedia",
            "logo": "//upload.wikimedia.org/wikipedia/en/b/bc/Wiki.png",
            "linkprefix": "",
            "linktrail": "/^([a-z]+)(.*)$/sD",
            "legaltitlechars": " %!\"$&'()*,\\-.\\/0-9:;=?@A-Z\\\\^_`a-z~\\x80-\\xFF+",
            "case": "first-letter",
            "lang": "en",
            "fallback": [],
            "servername": "en.wikipedia.org",
            "wikiid": "enwiki",
		
            "generator": "MediaWiki 1.26wmf1",
            "phpversion": "5.6.99-hhvm",
            "phpsapi": "srv",
            "hhvmversion": "3.3.1",
            "dbtype": "mysql",
            "dbversion": "10.0.16-MariaDB-log",
            "imagewhitelistenabled": "",
            "langconversion": "",
            "titleconversion": "",
            "linkprefixcharset": "",
            "git-hash": "155a3c1e83a2fc06f7921600f439771a82c4d50c",
            "git-branch": "wmf/1.26wmf1",
            "fallback8bitEncoding": "windows-1252",
            "writeapi": "",
            "timezone": "UTC",
            "timeoffset": 0,
            "articlepath": "/wiki/$1",
            "scriptpath": "/w",
            "script": "/w/index.php",
            "variantarticlepath": false,
            "server": "//en.wikipedia.org",
            "time": "2015-04-20T05:21:44Z",
            "misermode": "",
            "maxuploadsize": 1048576000,
            "thumblimits": [
                120,
                150,
                180,
                200,
                220,
                250,
                300
            ],
            "imagelimits": [
                {
                    "width": 320,
                    "height": 240
                },
                {
                    "width": 640,
                    "height": 480
                },
                {
                    "width": 800,
                    "height": 600
                },
                {
                    "width": 1024,
                    "height": 768
                },
                {
                    "width": 1280,
                    "height": 1024
                }
            ],
            "favicon": "//bits.wikimedia.org/favicon/wikipedia.ico"
*/
class Xowmf_json_tbl implements RlsAble {
	private static final String tbl_name = "wmf_json"; private final Db_meta_fld_list flds = new Db_meta_fld_list();
	private final String fld_site_id, fld_text, fld_date;
	private final Db_conn conn;
	private Db_stmt stmt_insert, stmt_select;
	public Xowmf_json_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_site_id = flds.Add_int("site_id");
		this.fld_date = flds.Add_str("json_date", 20);
		this.fld_text = flds.Add_text("json_text");
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;}
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds, Db_meta_idx.new_unique_by_name(tbl_name, "main", fld_site_id)));}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_select = Db_stmt_.Rls(stmt_select);
	}
	public void Delete_all() {conn.Stmt_delete(tbl_name, Db_meta_fld.Ary_empy).Exec_delete();}
	public void Insert(int site_id, DateAdp date, byte[] text) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear().Val_int(fld_site_id, site_id).Val_str(fld_date, date.XtoStr_gplx()).Val_bry_as_str(fld_text, text).Exec_insert();
	}
	public String Select_text_or_null(int site_id) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_site_id);
		Db_rdr rdr = stmt_select.Clear().Crt_int(fld_site_id, site_id).Exec_select__rls_manual();
		try {
			return rdr.Move_next() ? rdr.Read_str(fld_text) : null;
		}
		finally {rdr.Rls();}
	}
}
class Xowmf_site_tbl implements RlsAble {
	private static final String tbl_name = "wmf_site"; private final Db_meta_fld_list flds = new Db_meta_fld_list();
	private final String fld_id, fld_name;
	private final Db_conn conn;
	private Db_stmt stmt_insert, stmt_select;
	public Xowmf_site_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_id = flds.Add_int_pkey("site_id");
		this.fld_name = flds.Add_str("site_name", 255);
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;}
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds, Db_meta_idx.new_unique_by_name(tbl_name, "name", fld_name)));}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_select = Db_stmt_.Rls(stmt_select);
	}
	public void Delete_all() {conn.Stmt_delete(tbl_name, Db_meta_fld.Ary_empy).Exec_delete();}
	public void Insert(int id, String name) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear().Val_int(fld_id, id).Val_str(fld_name, name).Exec_insert();
	}
	public int Select_id(String name) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_name);
		Db_rdr rdr = stmt_select.Clear().Crt_str(fld_name, name).Exec_select__rls_manual();
		try {
			return rdr.Move_next() ? rdr.Read_int(fld_id) : -1;
		}
		finally {rdr.Rls();}
	}
	public static Db_conn Get_conn_or_new(Io_url xowa_root) {
		Io_url wmf_data_url = xowa_root.GenSubFil_nest("bin", "any", "xowa", "cfg", "wiki", "wmf_data.sqlite3");
		Db_conn_bldr_data conn_data = Db_conn_bldr.I.Get_or_new(wmf_data_url);
		Db_conn conn = conn_data.Conn();
		if (conn_data.Created()) {
			Xowmf_site_tbl site_tbl = new Xowmf_site_tbl(conn); site_tbl.Create_tbl();
			Xowmf_ns_tbl itm_tbl = new Xowmf_ns_tbl(conn); itm_tbl.Create_tbl();
		}
		return conn;
	}
}
class Xowmf_ns_tbl implements RlsAble {
	private static final String tbl_name = "wmf_ns"; private final Db_meta_fld_list flds = new Db_meta_fld_list();
	private final String fld_site_id, fld_id, fld_case, fld_subpages, fld_content, fld_name, fld_canonical;
	private final Db_conn conn;
	private Db_stmt stmt_insert, stmt_select;
	public Xowmf_ns_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_site_id	= flds.Add_int("site_id");
		this.fld_id			= flds.Add_int("ns_id");
		this.fld_case		= flds.Add_byte("ns_case");
		this.fld_subpages	= flds.Add_byte("ns_subpages");
		this.fld_content	= flds.Add_byte("ns_content");
		this.fld_name		= flds.Add_str("ns_name", 255);
		this.fld_canonical	= flds.Add_str("ns_canonical", 255);
		conn.Rls_reg(this);
	}
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds, Db_meta_idx.new_unique_by_name(tbl_name, "main", fld_site_id, fld_id)));}
	public void Delete_all() {conn.Stmt_delete(tbl_name, Db_meta_fld.Ary_empy).Exec_delete();}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_select = Db_stmt_.Rls(stmt_select);
	}
	public void Insert(int site_id, int id, byte case_match, boolean subpages, boolean content, byte[] name, byte[] canonical) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
			.Val_int(fld_site_id, site_id).Val_int(fld_id, id).Val_byte(fld_case, case_match).Val_bool_as_byte(fld_subpages, subpages).Val_bool_as_byte(fld_content, content)
			.Val_bry_as_str(fld_name, name).Val_bry_as_str(fld_canonical, canonical).Exec_insert();
	}
	public void Select_all(Xow_ns_mgr rv, int site_id) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_site_id);
		Db_rdr rdr = stmt_select.Clear().Crt_int(fld_site_id, site_id).Exec_select__rls_manual();
		rv.Clear();
		try {
			while (rdr.Move_next()) {
				rv.Add_new
				( rdr.Read_int			(fld_id)
				, rdr.Read_bry_by_str	(fld_name)
				, rdr.Read_byte			(fld_case)
				, Bool_.N);
			}
		}
		finally {rdr.Rls();}
		rv.Init();			
	}
}
