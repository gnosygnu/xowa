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
package gplx.dbs.metas.parsers; import gplx.*; import gplx.dbs.*; import gplx.dbs.metas.*;
import org.junit.*;
public class Dbmeta_parser__fld_tst {
	@Before public void init() {fxt.Clear();} private Dbmeta_parser__fld_fxt fxt = new Dbmeta_parser__fld_fxt();
	@Test  public void Parse_type() {
		fxt.Test_parse_type("int"					, fxt.Make_type(Dbmeta_fld_tid.Tid__int));
		fxt.Test_parse_type("varchar(255)"			, fxt.Make_type(Dbmeta_fld_tid.Tid__str, 255));
		fxt.Test_parse_type("decimal(12,10)"		, fxt.Make_type(Dbmeta_fld_tid.Tid__decimal, 12, 10));
		fxt.Test_parse_type(" int"					, fxt.Make_type(Dbmeta_fld_tid.Tid__int));
		fxt.Test_parse_type(" decimal ( 12 , 10 )"	, fxt.Make_type(Dbmeta_fld_tid.Tid__decimal, 12, 10));
	}
	@Test  public void Parse_fld() {
		fxt.Test_parse_fld("name_1 int"									, fxt.Make_fld("name_1", Dbmeta_fld_tid.Tid__int, Dbmeta_fld_itm.Nullable_unknown));
		fxt.Test_parse_fld("name_1 int null"							, fxt.Make_fld("name_1", Dbmeta_fld_tid.Tid__int, Dbmeta_fld_itm.Nullable_null));
		fxt.Test_parse_fld("name_1 int not null"						, fxt.Make_fld("name_1", Dbmeta_fld_tid.Tid__int, Dbmeta_fld_itm.Nullable_not_null));
		fxt.Test_parse_fld("name_1 int not null autoincrement"			, fxt.Make_fld("name_1", Dbmeta_fld_tid.Tid__int, Dbmeta_fld_itm.Nullable_not_null, Bool_.N, Bool_.Y));
		fxt.Test_parse_fld("name_1 int not null primary key"			, fxt.Make_fld("name_1", Dbmeta_fld_tid.Tid__int, Dbmeta_fld_itm.Nullable_not_null, Bool_.Y, Bool_.N));
		fxt.Test_parse_fld("name_1 int not null default -1"				, fxt.Make_fld("name_1", Dbmeta_fld_tid.Tid__int, Dbmeta_fld_itm.Nullable_not_null, Bool_.Y, Bool_.N, -1));
		fxt.Test_parse_fld("name_1 varchar(3) not null default 'abc'"	, fxt.Make_fld("name_1", Dbmeta_fld_tid.Tid__str, Dbmeta_fld_itm.Nullable_not_null, Bool_.Y, Bool_.N, "abc"));
	}
	@Test  public void Comment() {
		fxt.Test_parse_fld("name_1 int --a\n"							, fxt.Make_fld("name_1", Dbmeta_fld_tid.Tid__int, Dbmeta_fld_itm.Nullable_unknown));
	}
}
class Dbmeta_parser__fld_fxt {
	private final    Dbmeta_parser__fld fld_parser = new Dbmeta_parser__fld();
	private final    Sql_bry_rdr rdr = new Sql_bry_rdr();
	public void Clear() {}
	public Dbmeta_fld_tid Make_type(int tid_ansi) {return new Dbmeta_fld_tid(tid_ansi, -1, null, Int_.Min_value, Int_.Min_value);}
	public Dbmeta_fld_tid Make_type(int tid_ansi, int len_1) {return new Dbmeta_fld_tid(tid_ansi, -1, null, len_1, Int_.Min_value);}
	public Dbmeta_fld_tid Make_type(int tid_ansi, int len_1, int len_2) {return new Dbmeta_fld_tid(tid_ansi, -1, null, len_1, len_2);}
	public Dbmeta_fld_itm Make_fld(String name, int tid_ansi, int nullable) {return Make_fld(name, tid_ansi, nullable, false, false, null);}
	public Dbmeta_fld_itm Make_fld(String name, int tid_ansi, int nullable, boolean autonumber, boolean primary_key) {return Make_fld(name, tid_ansi, nullable, false, false, null);}
	public Dbmeta_fld_itm Make_fld(String name, int tid_ansi, int nullable, boolean autonumber, boolean primary_key, Object default_val) {
		Dbmeta_fld_itm rv = new Dbmeta_fld_itm(name, Make_type(tid_ansi));
		rv.Nullable_tid_(nullable);
		if (autonumber)		rv.Autonum_y_();
		if (primary_key)	rv.Primary_y_();
		rv.Default_(default_val);
		return rv;
	}
	public void Test_parse_type(String src, Dbmeta_fld_tid expd_type) {
		rdr.Init_by_src(Bry_.new_u8(src));
		Dbmeta_fld_tid actl_type = fld_parser.Parse_type(rdr);
		Tfds.Eq(expd_type.Tid_ansi()	, actl_type.Tid_ansi());
		Tfds.Eq(expd_type.Len_1()		, actl_type.Len_1());
		Tfds.Eq(expd_type.Len_2()		, actl_type.Len_2());
	}
	public void Test_parse_fld(String src, Dbmeta_fld_itm expd_fld) {
		rdr.Init_by_src(Bry_.new_u8(src));
		Dbmeta_fld_itm actl_fld = fld_parser.Parse_fld(rdr);
		Tfds.Eq(expd_fld.Name()					, actl_fld.Name());
		Tfds.Eq(expd_fld.Type().Tid_ansi()		, actl_fld.Type().Tid_ansi());
		Tfds.Eq(expd_fld.Nullable_tid()			, actl_fld.Nullable_tid());
		Tfds.Eq(Object_.Xto_str_strict_or_empty(expd_fld.Default()), Object_.Xto_str_strict_or_empty(actl_fld.Default()));
	}
}
