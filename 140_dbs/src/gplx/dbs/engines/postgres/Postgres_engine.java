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
package gplx.dbs.engines.postgres; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import gplx.core.stores.*; import gplx.dbs.engines.*; import gplx.dbs.sqls.*; import gplx.dbs.metas.*;
import java.sql.*; 
public class Postgres_engine extends Db_engine_sql_base {
	@Override public String Tid() {return Postgres_conn_info.Tid_const;}
	@Override public Sql_qry_wtr	Sql_wtr() {return Sql_qry_wtr_.New__mysql();}
	@Override public Db_engine New_clone(Db_conn_info connectInfo) {
		Postgres_engine rv = new Postgres_engine();
		rv.Ctor(connectInfo);
		return rv;
	}
	@Override public DataRdr New_rdr(ResultSet rdr, String commandText) {return Db_data_rdr_.new_(rdr, commandText);}
	@Override public Dbmeta_tbl_mgr Meta_mgr() {throw Err_.new_unimplemented();}
		@gplx.Internal @Override protected Connection Conn_make() {
		Postgres_conn_info conn_info_as_postgres = (Postgres_conn_info)conn_info;
		return Conn_make_by_url("jdbc:" + conn_info_as_postgres.Key() + "://localhost/" + conn_info_as_postgres.Database(), conn_info_as_postgres.Uid(), conn_info_as_postgres.Pwd());
	}
		public static final    Postgres_engine Instance = new Postgres_engine(); Postgres_engine() {}
}
