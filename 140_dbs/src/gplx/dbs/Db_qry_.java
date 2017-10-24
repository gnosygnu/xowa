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
package gplx.dbs; import gplx.*;
import gplx.core.criterias.*; import gplx.dbs.qrys.*; import gplx.dbs.sqls.*; import gplx.dbs.sqls.itms.*;
public class Db_qry_ {
	public static Db_qry__select_cmd select_cols_(String tbl, Criteria crt, String... cols){return select_().From_(tbl).Where_(crt).Cols_(cols);}
	public static Db_qry__select_cmd select_val_(String tbl, String col, Criteria crt)		{return select_().From_(tbl).Where_(crt).Cols_(col);}
	public static Db_qry__select_cmd select_tbl_(String tbl)								{return select_().From_(tbl).Cols_all_();}
	public static Db_qry__select_cmd select_()												{return new Db_qry__select_cmd();}
	public static Db_qry__select_cmd select_(String tbl, String... cols)				{return new Db_qry__select_cmd().From_(tbl).Cols_(cols);}
	public static Db_qry_delete delete_(String tbl, Criteria crt)							{return Db_qry_delete.new_(tbl, crt);}
	public static Db_qry_delete delete_tbl_(String tbl)										{return Db_qry_delete.new_(tbl);}
	public static Db_qry_insert insert_(String tbl)											{return new Db_qry_insert(tbl);}
	public static Db_qry_insert insert_common_(String tbl, Keyval... pairs) {
		Db_qry_insert cmd = new Db_qry_insert(tbl);
		for (Keyval pair : pairs)
			cmd.Val_obj(pair.Key(), pair.Val());
		return cmd;
	}

	public static Sql_join_fld New_join__join(String trg_fld, String src_tbl, String src_fld)	{return new Sql_join_fld(trg_fld, src_tbl, src_fld);}
	public static Sql_join_fld New_join__same(String tbl, String fld)							{return new Sql_join_fld(fld, tbl, fld);}

	public static Db_qry_update update_(String tbl, Criteria crt) {
		Db_qry_update update = new Db_qry_update();
		update.From_(tbl);
		update.Where_(crt);
		return update;
	}
	public static Db_qry_update update_common_(String tbl, Criteria crt, Keyval... pairs) {
		Db_qry_update cmd = new Db_qry_update();
		cmd.From_(tbl); cmd.Where_(crt);
		for (Keyval pair : pairs)
			cmd.Val_obj(pair.Key(), pair.Val());
		return cmd;
	}
	public static gplx.core.gfo_ndes.GfoNde Exec_as_nde(Db_conn conn, Db_qry qry) {return gplx.core.gfo_ndes.GfoNde_.rdr_(conn.Exec_qry_as_old_rdr(qry));}
	public static Object Exec_as_obj(Db_conn conn, Db_qry__select_cmd qry) {
		gplx.core.stores.DataRdr rdr = conn.Exec_qry_as_old_rdr(qry);
		try {
			return rdr.MoveNextPeer() ? rdr.Read(qry.Cols().Flds.Get_at(0).Fld) : null;	// NOTE: need to access from flds for tdb
		}	finally {rdr.Rls();}
	}

	public static Db_qry as_(Object obj) {return obj instanceof Db_qry ? (Db_qry)obj : null;}
        public static final    Db_qry Noop = new Db_qry__noop();
	public static final int Tid_insert = 0, Tid_delete = 1, Tid_update = 2, Tid_select = 3, Tid_sql = 4, Tid_select_in_tbl = 5, Tid_flush = 6, Tid_noop = 7, Tid_pragma = 8;
}
