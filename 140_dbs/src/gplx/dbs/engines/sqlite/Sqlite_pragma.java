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
package gplx.dbs.engines.sqlite; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import gplx.core.primitives.*;	import gplx.dbs.qrys.*; import gplx.dbs.utls.*; import gplx.dbs.engines.*; import gplx.dbs.engines.sqlite.*;
public class Sqlite_pragma implements Db_qry {
	private final    String sql;
	public Sqlite_pragma(boolean parens, String key, String val) {
		String fmt = parens ? "PRAGMA {0}({1});" : "PRAGMA {0} = {1};";
		this.sql = String_.Format(fmt, key, val);
	}
	public int			Tid() {return Db_qry_.Tid_pragma;}
	public boolean			Exec_is_rdr() {return false;}
	public String		Base_table() {return "";}
	public String		To_sql__exec(gplx.dbs.sqls.Sql_qry_wtr wtr) {return sql;}

	public static final String Const__journal_mode = "journal_mode", Const__journal_mode__wal = "wal", Const__journal_mode__off = "off";
	public static Sqlite_pragma New__journal__delete()					{return new Sqlite_pragma(Bool_.N, Const__journal_mode	, "delete");}		// default
	public static Sqlite_pragma New__journal__truncate()				{return new Sqlite_pragma(Bool_.N, Const__journal_mode	, "truncate");}
	public static Sqlite_pragma New__journal__persist()					{return new Sqlite_pragma(Bool_.N, Const__journal_mode	, "persist");}
	public static Sqlite_pragma New__journal__memory()					{return new Sqlite_pragma(Bool_.N, Const__journal_mode	, "memory");}
	public static Sqlite_pragma New__journal__wal()						{return new Sqlite_pragma(Bool_.N, Const__journal_mode	, Const__journal_mode__wal);}
	public static Sqlite_pragma New__journal__off()						{return new Sqlite_pragma(Bool_.N, Const__journal_mode	, Const__journal_mode__off);}
	public static Sqlite_pragma New__synchronous__off()					{return new Sqlite_pragma(Bool_.N, "synchronous"		, "off");}
	public static Sqlite_pragma New__synchronous__normal()				{return new Sqlite_pragma(Bool_.N, "synchronous"		, "normal");}		// default if WAL
	public static Sqlite_pragma New__synchronous__full()				{return new Sqlite_pragma(Bool_.N, "synchronous"		, "full");}			// default otherwise
	public static Sqlite_pragma New__synchronous__extra()				{return new Sqlite_pragma(Bool_.N, "synchronous"		, "extra");}
	public static Sqlite_pragma New__wal_autocheckpoint(int v)			{return new Sqlite_pragma(Bool_.N, "wal_auto_checkpoint", Int_.To_str(v));}	// default is 1000
	public static Sqlite_pragma New__wal_checkpoint__passive()			{return new Sqlite_pragma(Bool_.Y, "wal_checkpoint"		, "passive");}
	public static Sqlite_pragma New__wal_checkpoint__full()				{return new Sqlite_pragma(Bool_.Y, "wal_checkpoint"		, "full");}
	public static Sqlite_pragma New__wal_checkpoint__restart()			{return new Sqlite_pragma(Bool_.Y, "wal_checkpoint"		, "restart");}
	public static Sqlite_pragma New__wal_checkpoint__truncate()			{return new Sqlite_pragma(Bool_.Y, "wal_checkpoint"		, "truncate");}
	public static Sqlite_pragma New__locking_mode__normal()				{return new Sqlite_pragma(Bool_.N, "locking_mode"		, "normal");}		// default
	public static Sqlite_pragma New__locking_mode__exclusive()			{return new Sqlite_pragma(Bool_.N, "locking_mode"		, "exclusive");}
	public static Sqlite_pragma New__page_size(int v)					{return new Sqlite_pragma(Bool_.N, "page_size"			, Int_.To_str(v));}	// default is 1024
}
