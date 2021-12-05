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
package gplx.dbs.metas.parsers; import gplx.objects.primitives.BoolUtl;
import gplx.Bry_;
import gplx.Int_;
import gplx.Object_;
import gplx.Tfds;
import gplx.dbs.DbmetaFldItm;
import gplx.dbs.DbmetaFldType;
import org.junit.Before;
import org.junit.Test;
public class Dbmeta_parser__fld_tst {
	@Before public void init() {fxt.Clear();} private Dbmeta_parser__fld_fxt fxt = new Dbmeta_parser__fld_fxt();
	@Test public void Parse_type() {
		fxt.Test_parse_type("int"					, fxt.Make_type(DbmetaFldType.TidInt));
		fxt.Test_parse_type("varchar(255)"			, fxt.Make_type(DbmetaFldType.TidStr, 255));
		fxt.Test_parse_type("decimal(12,10)"		, fxt.Make_type(DbmetaFldType.TidDecimal, 12, 10));
		fxt.Test_parse_type(" int"					, fxt.Make_type(DbmetaFldType.TidInt));
		fxt.Test_parse_type(" decimal ( 12 , 10 )"	, fxt.Make_type(DbmetaFldType.TidDecimal, 12, 10));
	}
	@Test public void Parse_fld() {
		fxt.Test_parse_fld("name_1 int"									, fxt.Make_fld("name_1", DbmetaFldType.TidInt, DbmetaFldItm.NullableUnspecified));
		fxt.Test_parse_fld("name_1 int null"							, fxt.Make_fld("name_1", DbmetaFldType.TidInt, DbmetaFldItm.NullableNull));
		fxt.Test_parse_fld("name_1 int not null"						, fxt.Make_fld("name_1", DbmetaFldType.TidInt, DbmetaFldItm.NullableNotNull));
		fxt.Test_parse_fld("name_1 int not null autoincrement"			, fxt.Make_fld("name_1", DbmetaFldType.TidInt, DbmetaFldItm.NullableNotNull, BoolUtl.N, BoolUtl.Y));
		fxt.Test_parse_fld("name_1 int not null primary key"			, fxt.Make_fld("name_1", DbmetaFldType.TidInt, DbmetaFldItm.NullableNotNull, BoolUtl.Y, BoolUtl.N));
		fxt.Test_parse_fld("name_1 int not null default -1"				, fxt.Make_fld("name_1", DbmetaFldType.TidInt, DbmetaFldItm.NullableNotNull, BoolUtl.Y, BoolUtl.N, -1));
		fxt.Test_parse_fld("name_1 varchar(3) not null default 'abc'"	, fxt.Make_fld("name_1", DbmetaFldType.TidStr, DbmetaFldItm.NullableNotNull, BoolUtl.Y, BoolUtl.N, "abc"));
	}
	@Test public void Comment() {
		fxt.Test_parse_fld("name_1 int --a\n"							, fxt.Make_fld("name_1", DbmetaFldType.TidInt, DbmetaFldItm.NullableUnspecified));
	}
}
class Dbmeta_parser__fld_fxt {
	private final Dbmeta_parser__fld fld_parser = new Dbmeta_parser__fld();
	private final Sql_bry_rdr rdr = new Sql_bry_rdr();
	public void Clear() {}
	public DbmetaFldType Make_type(int tid_ansi) {return new DbmetaFldType(tid_ansi, null, Int_.Min_value, Int_.Min_value);}
	public DbmetaFldType Make_type(int tid_ansi, int len_1) {return new DbmetaFldType(tid_ansi, null, len_1, Int_.Min_value);}
	public DbmetaFldType Make_type(int tid_ansi, int len_1, int len_2) {return new DbmetaFldType(tid_ansi, null, len_1, len_2);}
	public DbmetaFldItm Make_fld(String name, int tid_ansi, int nullable) {return Make_fld(name, tid_ansi, nullable, false, false, null);}
	public DbmetaFldItm Make_fld(String name, int tid_ansi, int nullable, boolean autonumber, boolean primary_key) {return Make_fld(name, tid_ansi, nullable, false, false, null);}
	public DbmetaFldItm Make_fld(String name, int tid_ansi, int nullable, boolean autonumber, boolean primary_key, Object default_val) {
		DbmetaFldItm rv = new DbmetaFldItm(name, Make_type(tid_ansi));
		rv.NullableSet(nullable);
		if (autonumber)		rv.AutonumSetY();
		if (primary_key)	rv.PrimarySetY();
		rv.DefaultValSet(default_val);
		return rv;
	}
	public void Test_parse_type(String src, DbmetaFldType expd_type) {
		rdr.Init_by_src(Bry_.new_u8(src));
		DbmetaFldType actl_type = fld_parser.Parse_type(rdr);
		Tfds.Eq(expd_type.Tid()	, actl_type.Tid());
		Tfds.Eq(expd_type.Len1()		, actl_type.Len1());
		Tfds.Eq(expd_type.Len2()		, actl_type.Len2());
	}
	public void Test_parse_fld(String src, DbmetaFldItm expd_fld) {
		rdr.Init_by_src(Bry_.new_u8(src));
		DbmetaFldItm actl_fld = fld_parser.Parse_fld(rdr);
		Tfds.Eq(expd_fld.Name()					, actl_fld.Name());
		Tfds.Eq(expd_fld.Type().Tid()		, actl_fld.Type().Tid());
		Tfds.Eq(expd_fld.Nullable()			, actl_fld.Nullable());
		Tfds.Eq(Object_.Xto_str_strict_or_empty(expd_fld.DefaultVal()), Object_.Xto_str_strict_or_empty(actl_fld.DefaultVal()));
	}
}
