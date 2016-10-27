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
package gplx.xowa.addons.users.wikis.regys.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.users.*; import gplx.xowa.addons.users.wikis.*; import gplx.xowa.addons.users.wikis.regys.*;
import gplx.dbs.*;
public class Xou_wiki_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld__wiki_id, fld__wiki_type, fld__wiki_domain, fld__wiki_name, fld__wiki_data_date, fld__wiki_core_url, fld__wiki_data;
	private final    Db_conn conn;
	public Xou_wiki_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name				= Tbl_name_dflt;
		this.fld__wiki_id			= flds.Add_int_pkey("wiki_id");
		this.fld__wiki_type			= flds.Add_int("wiki_type");				// enum: 0=user; 1=wmf; 2=wikia;
		this.fld__wiki_domain		= flds.Add_str("wiki_domain", 255);			// EX: "en.wikipedia.org"
		this.fld__wiki_name			= flds.Add_str("wiki_name", 255);			// EX: "English Wikipedia"
		this.fld__wiki_data_date	= flds.Add_str("wiki_data_date", 16);		// EX: "20161001"
		this.fld__wiki_core_url		= flds.Add_str("wiki_core_url", 255);		// EX: "/xowa/wiki/en.wikipedia.org/en.wikipedia.org-core.xowa"
		this.fld__wiki_data			= flds.Add_text("wiki_data");				// EX: '{category_level="1",search_level="2", ...}'
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public void Create_tbl() {
		conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));
	}
	public void Insert(int id, String type, String domain, String name, String data_date, String core_url, String data) {
		conn.Stmt_insert(tbl_name, flds)
			.Val_int(fld__wiki_id, id)
			.Val_str(fld__wiki_type, type).Val_str(fld__wiki_domain, domain).Val_str(fld__wiki_name, name)
			.Val_str(fld__wiki_data_date, data_date).Val_str(fld__wiki_core_url, core_url).Val_str(fld__wiki_data, data)
			.Exec_insert()
			;
	}
	public void Upsert(int id, String key, String name, String file) {
		if (id == -1) {
		}
		else {
			conn.Stmt_update_exclude(tbl_name, flds, fld__wiki_id)
				.Val_int(fld__wiki_type, 0).Val_str(fld__wiki_domain, key).Val_str(fld__wiki_name, name)
				.Val_str(fld__wiki_data_date, "").Val_str(fld__wiki_core_url, file).Val_str(fld__wiki_data, "")
				.Crt_int(fld__wiki_id, id)
				.Exec_update()
				;
		}
	}
	public Xou_wiki_itm[] Select_all() {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds).Exec_select__rls_auto();
		try {
			List_adp list = List_adp_.New();
			while (rdr.Move_next()) {
				list.Add(Make(rdr));
			}
			return (Xou_wiki_itm[])list.To_ary_and_clear(Xou_wiki_itm.class);
		}
		finally {rdr.Rls();}
	}
	public Xou_wiki_itm Select_by_key_or_null(String key) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld__wiki_domain).Clear().Crt_str(fld__wiki_domain, key).Exec_select__rls_auto();
		try {
			return rdr.Move_next()
				? Make(rdr)
				: null;
		}
		finally {rdr.Rls();}
	}
	private Xou_wiki_itm Make(Db_rdr rdr) {
		return new Xou_wiki_itm(rdr.Read_int(fld__wiki_id), rdr.Read_int(fld__wiki_type)
			, rdr.Read_str(fld__wiki_domain), rdr.Read_str(fld__wiki_name), Io_url_.new_fil_(rdr.Read_str(fld__wiki_core_url)), rdr.Read_str(fld__wiki_data)
		);
	}

	public void Rls() {}

	public static final String Tbl_name_dflt = "user_wiki";
}
