/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.dbs.engines.mysql;
import gplx.Err_;
import gplx.core.stores.DataRdr;
import gplx.core.stores.Db_data_rdr;
import gplx.dbs.Db_conn_info;
import gplx.dbs.engines.Db_engine;
import gplx.dbs.engines.Db_engine_sql_base;
import gplx.dbs.metas.Dbmeta_tbl_mgr;
import gplx.dbs.sqls.SqlQryWtr;
import gplx.dbs.sqls.SqlQryWtrUtl;
import gplx.dbs.wkrs.SqlWkrMgr;

import java.sql.Connection;
import java.sql.ResultSet;
public class Mysql_engine extends Db_engine_sql_base {
	@Override public String Tid() {return Mysql_conn_info.Tid_const;}
	@Override public SqlQryWtr Sql_wtr() {return SqlQryWtrUtl.NewMysql();}
	@Override public void CtorConn(SqlWkrMgr wkrMgr) {}
	@Override protected String Txn_bgn_str() {return "START TRANSACTION;";}
	@Override protected String Txn_end_str() {return "COMMIT;";}
	@Override public Db_engine New_clone(Db_conn_info connectInfo) {
		Mysql_engine rv = new Mysql_engine();
		rv.Ctor(connectInfo);
		return rv;
	}
	@Override public DataRdr New_rdr(ResultSet rdr, String commandText) {return Mysql_rdr.new_(rdr, commandText);}
	@Override public Dbmeta_tbl_mgr Meta_mgr() {throw Err_.new_unimplemented();}
		@gplx.Internal @Override protected Connection Conn_make() {
		Mysql_conn_info conn_info_as_mysql = (Mysql_conn_info)conn_info; 
		Connection rv = Conn_make_by_url("jdbc:mysql://localhost/" + conn_info_as_mysql.Database() + "?characterEncoding=UTF8&useSSL=false", conn_info_as_mysql.Uid(), conn_info_as_mysql.Pwd());
		return rv;
	}
		public static final Mysql_engine Instance = new Mysql_engine(); Mysql_engine() {}
}
class Mysql_rdr extends Db_data_rdr {
		//PATCH:MYSQL:byte actually returned as int by Jdbc ResultSet (or MYSQL impmentation); convert to byte 
	@Override public byte ReadByte(String key) {return ReadByteOr(key, Byte.MAX_VALUE);}
	@Override public byte ReadByteOr(String key, byte or) {
		Object val = Read(key);
		return val == null ? or : ((Integer)val).byteValue();
	}
	public static Mysql_rdr new_(ResultSet rdr, String commandText) {
		Mysql_rdr rv = new Mysql_rdr();
		rv.ctor_db_data_rdr(rdr, commandText);
		return rv;
	}	Mysql_rdr() {}
}
