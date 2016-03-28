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
package gplx.xowa.wikis.data.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
import gplx.dbs.*; import gplx.dbs.qrys.*;
import gplx.xowa.wikis.data.*;
public class Xowd_xowa_db_tbl {
	private final String tbl_name; private final Dbmeta_fld_list flds = Dbmeta_fld_list.new_();
	private final String fld_id, fld_type, fld_url, fld_ns_ids, fld_part_id, fld_guid; private boolean schema_is_1;
	private final Db_conn conn; private final Db_stmt_bldr stmt_bldr = new Db_stmt_bldr();
	public Xowd_xowa_db_tbl(Db_conn conn, boolean schema_is_1) {
		this.conn = conn; this.schema_is_1 = schema_is_1;
		this.tbl_name = "xowa_db";
		fld_id				= flds.Add_int_pkey	("db_id");
		fld_type			= flds.Add_byte		("db_type");
		fld_url				= flds.Add_str		("db_url", 512);
		if (schema_is_1) {
			fld_ns_ids = fld_part_id = fld_guid = Dbmeta_fld_itm.Key_null;
		}
		else {
			fld_ns_ids		= flds.Add_str		("db_ns_ids", 255);
			fld_part_id		= flds.Add_int		("db_part_id");
			fld_guid		= flds.Add_str		("db_guid", 36);
		}
		stmt_bldr.Conn_(conn, tbl_name, flds, fld_id);
	}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public Xowd_db_file[] Select_all(Xowd_core_db_props props, Io_url wiki_root_dir) {
		List_adp list = List_adp_.new_();
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				String ns_ids = ""; int part_id = -1; Guid_adp guid = Guid_adp_.Empty;
				if (!schema_is_1) {
					ns_ids = rdr.Read_str(fld_ns_ids);
					part_id = rdr.Read_int(fld_part_id);
					guid = Guid_adp_.parse(rdr.Read_str(fld_guid));
				}
				list.Add(Xowd_db_file.load_(props, rdr.Read_int(fld_id), rdr.Read_byte(fld_type), wiki_root_dir.GenSubFil(rdr.Read_str(fld_url)), ns_ids, part_id, guid));
			}
		}	finally {rdr.Rls();}
		list.Sort_by(Xowd_db_file_sorter__id.Instance);
		return (Xowd_db_file[])list.To_ary(Xowd_db_file.class);
	}
	public void Commit_all(Xowd_db_mgr core_data_mgr) {
		stmt_bldr.Batch_bgn();
		try {
			int len = core_data_mgr.Dbs__len();
			for (int i = 0; i < len; i++)
				Commit_itm(core_data_mgr.Dbs__get_at(i));
		}	finally {stmt_bldr.Batch_end();}
	}
	private void Commit_itm(Xowd_db_file itm) {
		Db_stmt stmt = stmt_bldr.Get(itm.Cmd_mode());
		switch (itm.Cmd_mode()) {
			case Db_cmd_mode.Tid_create:	stmt.Clear().Val_int(fld_id, itm.Id());		Commit_itm_vals(stmt, itm); stmt.Exec_insert(); break;
			case Db_cmd_mode.Tid_update:	stmt.Clear();								Commit_itm_vals(stmt, itm); stmt.Crt_int(fld_id, itm.Id()).Exec_update(); break;
			case Db_cmd_mode.Tid_delete:	stmt.Clear().Crt_int(fld_id, itm.Id()).Exec_delete();	break;
			case Db_cmd_mode.Tid_ignore:	break;
			default:						throw Err_.new_unhandled(itm.Cmd_mode());
		}
		itm.Cmd_mode_(Db_cmd_mode.Tid_ignore);
	}
	private void Commit_itm_vals(Db_stmt stmt, Xowd_db_file itm) {
		stmt.Val_byte(fld_type, itm.Tid()).Val_str(fld_url, itm.Url_rel()).Val_str(fld_ns_ids, itm.Ns_ids()).Val_int(fld_part_id, itm.Part_id()).Val_str(fld_guid, itm.Guid().To_str());
	}
}
class Xowd_db_file_sorter__id implements gplx.core.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Xowd_db_file lhs = (Xowd_db_file)lhsObj;
		Xowd_db_file rhs = (Xowd_db_file)rhsObj;
		return Int_.Compare(lhs.Id(), rhs.Id());
	}
	public static final Xowd_db_file_sorter__id Instance = new Xowd_db_file_sorter__id(); Xowd_db_file_sorter__id() {}
}
