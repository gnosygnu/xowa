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
package gplx.dbs.metas.parsers; import gplx.*; import gplx.dbs.*; import gplx.dbs.metas.*;
import org.junit.*;
public class Meta_parser__fld_tst {
	@Before public void init() {fxt.Clear();} private Meta_parser__fld_fxt fxt = new Meta_parser__fld_fxt();
	@Test  public void Parse_type() {
		fxt.Test_parse_type("int"					, fxt.Make_type(Db_meta_fld.Tid_int));
		fxt.Test_parse_type("varchar(255)"			, fxt.Make_type(Db_meta_fld.Tid_str, 255));
		fxt.Test_parse_type("decimal(12,10)"		, fxt.Make_type(Db_meta_fld.Tid_decimal, 12, 10));
		fxt.Test_parse_type(" int"					, fxt.Make_type(Db_meta_fld.Tid_int));
		fxt.Test_parse_type(" decimal ( 12 , 10 )"	, fxt.Make_type(Db_meta_fld.Tid_decimal, 12, 10));
	}
	@Test  public void Parse_fld() {
		fxt.Test_parse_fld("name_1 int"							, fxt.Make_fld("name_1", Db_meta_fld.Tid_int, Meta_fld_itm.Nullable_unknown));
		fxt.Test_parse_fld("name_1 int null"					, fxt.Make_fld("name_1", Db_meta_fld.Tid_int, Meta_fld_itm.Nullable_null));
		fxt.Test_parse_fld("name_1 int not null"				, fxt.Make_fld("name_1", Db_meta_fld.Tid_int, Meta_fld_itm.Nullable_not_null));
		fxt.Test_parse_fld("name_1 int not null autoincrement"	, fxt.Make_fld("name_1", Db_meta_fld.Tid_int, Meta_fld_itm.Nullable_not_null, Bool_.N, Bool_.Y));
		fxt.Test_parse_fld("name_1 int not null primary key"	, fxt.Make_fld("name_1", Db_meta_fld.Tid_int, Meta_fld_itm.Nullable_not_null, Bool_.Y, Bool_.N));
		fxt.Test_parse_fld("name_1 int not null default -1"		, fxt.Make_fld("name_1", Db_meta_fld.Tid_int, Meta_fld_itm.Nullable_not_null, Bool_.Y, Bool_.N, -1));
		fxt.Test_parse_fld("name_1 int not null default 'abc'"	, fxt.Make_fld("name_1", Db_meta_fld.Tid_int, Meta_fld_itm.Nullable_not_null, Bool_.Y, Bool_.N, "abc"));
	}
}
class Meta_parser__fld_fxt {
	private final Meta_parser__fld fld_parser = new Meta_parser__fld();
	private final Sql_bry_rdr rdr = new Sql_bry_rdr();
	public void Clear() {}
	public Meta_type_itm Make_type(int tid_ansi) {return new Meta_type_itm(tid_ansi, -1, null, Int_.Min_value, Int_.Min_value);}
	public Meta_type_itm Make_type(int tid_ansi, int len_1) {return new Meta_type_itm(tid_ansi, -1, null, len_1, Int_.Min_value);}
	public Meta_type_itm Make_type(int tid_ansi, int len_1, int len_2) {return new Meta_type_itm(tid_ansi, -1, null, len_1, len_2);}
	public Meta_fld_itm Make_fld(String name, int tid_ansi, int nullable) {return Make_fld(name, tid_ansi, nullable, false, false, null);}
	public Meta_fld_itm Make_fld(String name, int tid_ansi, int nullable, boolean autonumber, boolean primary_key) {return Make_fld(name, tid_ansi, nullable, false, false, null);}
	public Meta_fld_itm Make_fld(String name, int tid_ansi, int nullable, boolean autonumber, boolean primary_key, Object default_val) {
		Meta_fld_itm rv = new Meta_fld_itm(name, Make_type(tid_ansi));
		rv.Nullable_tid_(nullable);
		if (autonumber)		rv.Autonumber_y_();
		if (primary_key)	rv.Primary_key_y_();
		rv.Default_val_(default_val);
		return rv;
	}
	public void Test_parse_type(String src, Meta_type_itm expd_type) {
		rdr.Init(Bry_.new_u8(src));
		Meta_type_itm actl_type = fld_parser.Parse_type(rdr);
		Tfds.Eq(expd_type.Tid_ansi()	, actl_type.Tid_ansi());
		Tfds.Eq(expd_type.Len_1()		, actl_type.Len_1());
		Tfds.Eq(expd_type.Len_2()		, actl_type.Len_2());
	}
	public void Test_parse_fld(String src, Meta_fld_itm expd_fld) {
		rdr.Init(Bry_.new_u8(src));
		Meta_fld_itm actl_fld = fld_parser.Parse_fld(rdr);
		Tfds.Eq(expd_fld.Name()					, actl_fld.Name());
		Tfds.Eq(expd_fld.Type().Tid_ansi()		, actl_fld.Type().Tid_ansi());
		Tfds.Eq(expd_fld.Nullable_tid()			, actl_fld.Nullable_tid());
		Tfds.Eq(Object_.Xto_str_strict_or_empty(expd_fld.Default_val()), Object_.Xto_str_strict_or_empty(actl_fld.Default_val()));
	}
}
