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
package gplx.xowa.html.hdumps; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.xowa.dbs.*; import gplx.xowa.dbs.tbls.*;
import gplx.xowa.wikis.data.*;
import gplx.xowa.html.hdumps.data.*;
public class Xowd_db_init_wkr__html implements Xowd_db_init_tbl_wkr, Xowd_db_init_db_wkr {
	public String			Tbl_key() {return Xohd_page_html_tbl.Hash_key;}
	public Object			Tbl_init(Xow_core_data_mgr core_data_mgr, Xowd_db_file db_file) {return Make_tbl(core_data_mgr, db_file, Bool_.N);}
	public byte				Db_tid()	{return Xowd_db_file_.Tid_html;}
	public Xowd_db_file		Db_make(Xowe_core_data_mgr core_data_mgr) {
		Xow_core_data_map map = core_data_mgr.Map();
		Assert_col__page_html_db_id(core_data_mgr);
		Xowd_db_file rv = map.One_file() ? core_data_mgr.Dbs__get_db_core() : Create_db(core_data_mgr, this.Db_tid());
		Xohd_page_html_tbl html_tbl = Make_tbl(core_data_mgr, rv, Bool_.Y);
		html_tbl.Create_idx();
		rv.Tbls__add(Xohd_page_html_tbl.Hash_key, html_tbl);
		return rv;
	}
	private Xohd_page_html_tbl Make_tbl(Xow_core_data_mgr core_data_mgr, Xowd_db_file db_file, boolean created) {
		Xohd_page_html_tbl rv = new Xohd_page_html_tbl();
		rv.Conn_(db_file.Conn(), created, core_data_mgr.Cfg__schema_is_1(), core_data_mgr.Cfg__db_id(), core_data_mgr.Tbl__cfg().Select_as_byte_or("xowa.schema.dbs.html", "zip_tid", gplx.ios.Io_stream_.Tid_bzip2));
		return rv;
	}
	public void Assert_col__page_html_db_id(Xowe_core_data_mgr core_data_mgr) {
		Db_cfg_tbl cfg_tbl = core_data_mgr.Tbl__cfg();
		String exists = cfg_tbl.Select_as_str_or(Xowe_core_data_mgr.Cfg_grp_db_meta, Cfg_itm_html_db_exists, "n");
		if (String_.Eq(exists, "y")) return;
		Xowd_pg_regy_tbl pg_tbl = core_data_mgr.Tbl__pg();
		Db_conn conn = core_data_mgr.Dbs__get_db_core().Conn();
		conn.Exec_ddl_append_fld(pg_tbl.Tbl_name(), pg_tbl.Fld_html_db_id());		// TODO: currently NULL; change to NOT NULL DEFAULT -1; ALTER TABLE page ADD html_db_id int NULL;
		conn.Exec_ddl_append_fld(pg_tbl.Tbl_name(), pg_tbl.Fld_page_redirect_id());	// TODO: currently NULL; change to NOT NULL DEFAULT -1; ALTER TABLE page ADD html_db_id int NULL;
		cfg_tbl.Insert(Xowe_core_data_mgr.Cfg_grp_db_meta, Cfg_itm_html_db_exists, "y");
	}
	private Xowd_db_file Create_db(Xowe_core_data_mgr core_data_mgr, byte tid) {
		Xowd_db_file rv = core_data_mgr.Dbs__add_new(tid);
		core_data_mgr.Dbs__save();
		return rv;
	}
	private static final String Cfg_itm_html_db_exists = "html_db.exists";
        public static final Xowd_db_init_wkr__html I = new Xowd_db_init_wkr__html(); Xowd_db_init_wkr__html() {}
}
