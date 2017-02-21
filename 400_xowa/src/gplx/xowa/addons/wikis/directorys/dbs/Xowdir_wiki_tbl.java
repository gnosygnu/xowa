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
package gplx.xowa.addons.wikis.directorys.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.directorys.*;
import gplx.dbs.*; import gplx.dbs.utls.*;
import gplx.langs.jsons.*;
public class Xowdir_wiki_tbl implements Db_tbl { // for domain and user-specific data only (name, url); don't replicate wiki-specific info here (like main_page)
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld__wiki_id, fld__wiki_domain, fld__wiki_core_url, fld__wiki_json;
	private final    Db_conn conn;
	private final    Json_parser json_parser = new Json_parser();
	public Xowdir_wiki_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name				= Tbl_name_dflt;
		this.fld__wiki_id			= flds.Add_int_pkey("wiki_id");
		this.fld__wiki_domain		= flds.Add_str("wiki_domain", 255);			// EX: "en.wikipedia.org"
		this.fld__wiki_core_url		= flds.Add_str("wiki_core_url", 255);		// EX: "/xowa/wiki/en.wikipedia.org/en.wikipedia.org-core.xowa"
		this.fld__wiki_json			= flds.Add_text("wiki_json");				// EX: '{category_level="1",search_level="2", ...}'
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public boolean Upsert(int id, String domain, Io_url core_url, String json) {
		return Db_tbl__crud_.Upsert(conn, tbl_name, flds, String_.Ary(fld__wiki_id), id, domain, core_url.Xto_api(), json);
	}
	public Xowdir_wiki_itm[] Select_all() {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds).Exec_select__rls_auto();
		try {
			List_adp list = List_adp_.New();
			while (rdr.Move_next()) {
				list.Add(Make(rdr));
			}
			return (Xowdir_wiki_itm[])list.To_ary_and_clear(Xowdir_wiki_itm.class);
		}
		finally {rdr.Rls();}
	}
	public Xowdir_wiki_itm Select_by_key_or_null(String key) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld__wiki_domain).Crt_str(fld__wiki_domain, key).Exec_select__rls_auto();
		try {return rdr.Move_next() ? Make(rdr) : null;}
		finally {rdr.Rls();}
	}
	public Xowdir_wiki_itm Select_by_id_or_null(int id) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld__wiki_id).Crt_int(fld__wiki_id, id).Exec_select__rls_auto();
		try {return rdr.Move_next() ? Make(rdr) : null;}
		finally {rdr.Rls();}
	}
	public void Delete_by_id(int id) {
		conn.Stmt_delete(tbl_name, fld__wiki_id).Crt_int(fld__wiki_id, id).Exec_delete();
	}
	private Xowdir_wiki_itm Make(Db_rdr rdr) {
		return new Xowdir_wiki_itm
			( rdr.Read_int(fld__wiki_id), rdr.Read_str(fld__wiki_domain)
			, Io_url_.new_fil_(rdr.Read_str(fld__wiki_core_url))
			, Xowdir_wiki_json.New_by_json(json_parser, rdr.Read_str(fld__wiki_json))
			);
	}
	public void Rls() {}

	public static final String Tbl_name_dflt = "user_wiki";
}
