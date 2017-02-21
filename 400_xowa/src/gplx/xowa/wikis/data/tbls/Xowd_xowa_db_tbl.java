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
package gplx.xowa.wikis.data.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
import gplx.dbs.*; import gplx.dbs.qrys.*;
import gplx.xowa.wikis.data.*;
public class Xowd_xowa_db_tbl implements Db_tbl {
	public static final    String Fld_id = "db_id", Fld_type = "db_type", Fld_url = "db_url";
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_id, fld_type, fld_url, fld_ns_ids, fld_part_id, fld_guid; private boolean schema_is_1;
	private final    Db_conn conn; private final    Db_stmt_bldr stmt_bldr = new Db_stmt_bldr();
	public Xowd_xowa_db_tbl(Db_conn conn, boolean schema_is_1) {
		this.conn = conn; this.schema_is_1 = schema_is_1;
		this.tbl_name = TBL_NAME;
		fld_id				= flds.Add_int_pkey	(Fld_id);
		fld_type			= flds.Add_byte		(Fld_type);
		fld_url				= flds.Add_str		(Fld_url, 512);
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
	public String Tbl_name() {return tbl_name;} private final    String tbl_name; public static final String TBL_NAME = "xowa_db";
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public Xow_db_file[] Select_all(Xowd_core_db_props props, Io_url wiki_root_dir) {
		List_adp list = List_adp_.New();
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				String ns_ids = ""; int part_id = -1; Guid_adp guid = Guid_adp_.Empty;
				if (!schema_is_1) {
					ns_ids = rdr.Read_str(fld_ns_ids);
					part_id = rdr.Read_int(fld_part_id);
					guid = Guid_adp_.Parse(rdr.Read_str(fld_guid));
				}
				int db_id = rdr.Read_int(fld_id);
				Xow_db_file db_file = Xow_db_file.Load(props, db_id, rdr.Read_byte(fld_type), wiki_root_dir.GenSubFil(rdr.Read_str(fld_url)), ns_ids, part_id, guid);
				list.Add(db_file);
			}
		}	finally {rdr.Rls();}
		list.Sort_by(Xow_db_file_sorter__id.Instance);
		return (Xow_db_file[])list.To_ary_and_clear(Xow_db_file.class);
	}
	public void Commit_all(Xow_db_mgr core_data_mgr) {
		stmt_bldr.Batch_bgn();
		try {
			int len = core_data_mgr.Dbs__len();
			for (int i = 0; i < len; i++)
				Commit_itm(core_data_mgr.Dbs__get_at(i));
		}	finally {stmt_bldr.Batch_end();}
	}
	public void Upsert(int id, byte tid, String url, String ns_ids, int part_id, String guid) {
		gplx.dbs.utls.Db_tbl__crud_.Upsert(conn, tbl_name, flds, String_.Ary(fld_id), id, tid, url, ns_ids, part_id, guid);
	}
	private void Commit_itm(Xow_db_file itm) {
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
	private void Commit_itm_vals(Db_stmt stmt, Xow_db_file itm) {
		stmt.Val_byte(fld_type, itm.Tid()).Val_str(fld_url, itm.Url_rel()).Val_str(fld_ns_ids, itm.Ns_ids()).Val_int(fld_part_id, itm.Part_id()).Val_str(fld_guid, itm.Guid().To_str());
	}
	public void Rls() {}

	public static Xowd_xowa_db_tbl Get_by_key(Db_tbl_owner owner) {return (Xowd_xowa_db_tbl)owner.Tbls__get_by_key(TBL_NAME);}
}
class Xow_db_file_sorter__id implements gplx.core.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Xow_db_file lhs = (Xow_db_file)lhsObj;
		Xow_db_file rhs = (Xow_db_file)rhsObj;
		return Int_.Compare(lhs.Id(), rhs.Id());
	}
	public static final    Xow_db_file_sorter__id Instance = new Xow_db_file_sorter__id(); Xow_db_file_sorter__id() {}
}
