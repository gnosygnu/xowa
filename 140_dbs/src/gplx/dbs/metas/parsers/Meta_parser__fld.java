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
import gplx.core.brys.*; import gplx.core.btries.*;
public class Meta_parser__fld {
	public Meta_type_itm Parse_type(Bry_rdr rdr) {
		rdr.Skip_ws();
		Object type_obj = type_trie.Match_bgn(rdr.Src(), rdr.Pos(), rdr.Src_len());
		if (type_obj == null) throw Exc_.new_("invalid fld type", "snip", rdr.Mid_by_len_safe(40));
		Meta_parser__fld_itm type_itm = (Meta_parser__fld_itm)type_obj;
		rdr.Pos_add(type_itm.Word().length);
		int paren_itms_count = type_itm.Paren_itms_count();
		int len_1 = Int_.MinValue, len_2 = Int_.MinValue;
		if (paren_itms_count > 0) {
			rdr.Skip_ws().Chk_byte_or_fail(Byte_ascii.Paren_bgn);
			len_1 = rdr.Skip_ws().Read_int_to_non_num(); if (len_1 == Int_.MinValue) throw Exc_.new_("invalid fld len_1", "snip", rdr.Mid_by_len_safe(40));
			if (paren_itms_count == 2) {
				rdr.Skip_ws().Chk_byte_or_fail(Byte_ascii.Comma);
				len_2 = rdr.Skip_ws().Read_int_to_non_num(); if (len_2 == Int_.MinValue) throw Exc_.new_("invalid fld len_2", "snip", rdr.Mid_by_len_safe(40));
			}
			rdr.Skip_ws().Chk_byte_or_fail(Byte_ascii.Paren_end);
		}
		return new Meta_type_itm(type_itm.Tid_ansi(), type_itm.Tid_sqlite(), type_itm.Word(), len_1, len_2);
	}
	public Meta_fld_itm Parse_fld(Sql_bry_rdr rdr) {	// starts after "(" or ","; EX: "(fld1 int", ", fld2 int"; ends at ")"
		byte[] name = rdr.Read_sql_identifier();
		Meta_type_itm type = this.Parse_type(rdr);
		Meta_fld_itm fld = new Meta_fld_itm(String_.new_u8(name), type);
		byte[] src = rdr.Src(); int src_len = rdr.Src_len();
		while (true) {
			rdr.Skip_ws();
			if (rdr.Pos() == src_len) return fld;	// eos
			switch (src[rdr.Pos()]) {
				case Byte_ascii.Comma:		return fld;
				case Byte_ascii.Paren_end:	return fld;
			}
			Object type_obj = fld_trie.Match_bgn(src, rdr.Pos(), src_len); if (type_obj == null) throw Exc_.new_("invalid", "snip", rdr.Mid_by_len_safe(40));
			Meta_fld_wkr__base type_wkr = (Meta_fld_wkr__base)type_obj;
			switch (type_wkr.Tid()) {
				case Meta_fld_wkr__base.Tid_end_comma:
				case Meta_fld_wkr__base.Tid_end_paren:	return fld;
				default:
					rdr.Pos_(fld_trie.Match_pos());
					type_wkr.Match(rdr, fld);
					break;
			}
		}
//			return fld;	// NOTE: will happen for tests; EX: "fld_1 int" vs "fld_1 int,"
	}
	private static final Btrie_slim_mgr fld_trie = fld_trie_init
	( Meta_fld_wkr__nullable_null.I
	, Meta_fld_wkr__nullable_not.I
	, Meta_fld_wkr__autonumber.I
	, Meta_fld_wkr__primary_key.I
	, Meta_fld_wkr__default.I
	);
	private static Btrie_slim_mgr fld_trie_init(Meta_fld_wkr__base... wkrs) {
		Btrie_slim_mgr rv = Btrie_slim_mgr.ci_ascii_();
		for (Meta_fld_wkr__base wkr : wkrs)
			wkr.Reg(rv);
		return rv;
	}
	private static final Btrie_slim_mgr type_trie = type_trie_init();
	private static Btrie_slim_mgr type_trie_init() {
		Btrie_slim_mgr rv = Btrie_slim_mgr.ci_ascii_();
		Meta_parser__fld_itm.reg_many(rv, Db_meta_fld.Tid_byte		, Sqlite_tid.Tid_int		, 0, "tinyint", "int2");
		Meta_parser__fld_itm.reg_many(rv, Db_meta_fld.Tid_short		, Sqlite_tid.Tid_int		, 0, "smallint");
		Meta_parser__fld_itm.reg_many(rv, Db_meta_fld.Tid_int		, Sqlite_tid.Tid_int		, 0, "int", "integer", "mediumint");
		Meta_parser__fld_itm.reg_many(rv, Db_meta_fld.Tid_long		, Sqlite_tid.Tid_int		, 0, "bigint", "int8");	// "UNSIGNED BIG INT"
		Meta_parser__fld_itm.reg_many(rv, Db_meta_fld.Tid_str		, Sqlite_tid.Tid_text		, 1, "character", "varchar", "nchar");	// "varying character", "native character"
		Meta_parser__fld_itm.reg_many(rv, Db_meta_fld.Tid_text		, Sqlite_tid.Tid_text		, 0, "text", "clob");
		Meta_parser__fld_itm.reg_many(rv, Db_meta_fld.Tid_bry		, Sqlite_tid.Tid_none		, 0, "blob");
		Meta_parser__fld_itm.reg_many(rv, Db_meta_fld.Tid_float		, Sqlite_tid.Tid_real		, 0, "float");
		Meta_parser__fld_itm.reg_many(rv, Db_meta_fld.Tid_double	, Sqlite_tid.Tid_real		, 0, "real", "double");	// "double precision"
		Meta_parser__fld_itm.reg_many(rv, Db_meta_fld.Tid_decimal	, Sqlite_tid.Tid_numeric	, 0, "numeric");
		Meta_parser__fld_itm.reg_many(rv, Db_meta_fld.Tid_decimal	, Sqlite_tid.Tid_numeric	, 2, "decimal");
		Meta_parser__fld_itm.reg_many(rv, Db_meta_fld.Tid_decimal	, Sqlite_tid.Tid_numeric	, 2, "decimal");
		Meta_parser__fld_itm.reg_many(rv, Db_meta_fld.Tid_bool		, Sqlite_tid.Tid_numeric	, 0, "boolean", "bit");		// "bit" is not SQLITE
		Meta_parser__fld_itm.reg_many(rv, Db_meta_fld.Tid_date		, Sqlite_tid.Tid_numeric	, 0, "date", "datetime");
		return rv;
	}
}
class Meta_parser__fld_itm {
	public Meta_parser__fld_itm(int tid_ansi, int tid_sqlite, byte[] word, int paren_itms_count) {
		this.tid_ansi = tid_ansi; this.tid_sqlite = tid_sqlite;
		this.word = word; this.paren_itms_count = paren_itms_count;
	}
	public int Tid_ansi() {return tid_ansi;} private final int tid_ansi;
	public int Tid_sqlite() {return tid_sqlite;} private final int tid_sqlite;
	public byte[] Word() {return word;} private final byte[] word;
	public int Paren_itms_count() {return paren_itms_count;} private final int paren_itms_count;
	public static void reg_many(Btrie_slim_mgr trie, int tid_ansi, int tid_sqlite, int paren_itms_count, String... names_str) {
		int len = names_str.length;
		for (int i = 0; i < len; ++i) {
			byte[] name_bry = Bry_.new_a7(names_str[i]);
			Meta_parser__fld_itm itm = new Meta_parser__fld_itm(tid_ansi, tid_sqlite, name_bry, paren_itms_count);
			trie.Add_obj(name_bry, itm);
		}
	}
}
class Sqlite_tid {
	public static final int Tid_int = 1, Tid_text = 2, Tid_none = 3, Tid_real = 4, Tid_numeric = 5;
}
