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
package gplx.xowa.addons.apps.cfgs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*;
import gplx.dbs.*; import gplx.dbs.utls.*;
public class Xonde_i18n_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld__nde_tid, fld__nde_id, fld__nde_lang, fld__nde_name, fld__nde_help;
	private final    Db_conn conn;
	public Xonde_i18n_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name				= "cfg_nde_i18n";
		this.fld__nde_tid			= flds.Add_int("nde_tid");				// EX: 1=grp; 2=itm
		this.fld__nde_id			= flds.Add_int("nde_id");				// EX: '2'
		this.fld__nde_lang			= flds.Add_str("nde_lang", 16);			// EX: 'en'
		this.fld__nde_name			= flds.Add_str("nde_name", 255);		// EX: 'Cfg Name'
		this.fld__nde_help			= flds.Add_str("nde_help", 4096);		// EX: 'Help text'
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Upsert(int nde_tid, int nde_id, String nde_lang, String nde_name, String nde_help) {
		Db_tbl__crud_.Upsert(conn, tbl_name, flds, String_.Ary(fld__nde_tid, fld__nde_id, fld__nde_lang), nde_tid, nde_id, nde_lang, nde_name, nde_help);
	}
	public void Select_stub() {
		Db_rdr rdr = Db_rdr_.Empty;
		rdr.Read_int(fld__nde_tid);
		rdr.Read_int(fld__nde_id);
		rdr.Read_str(fld__nde_lang);
		rdr.Read_str(fld__nde_name);
		rdr.Read_str(fld__nde_help);
	}
	public void Rls() {}
}
