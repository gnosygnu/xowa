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
import gplx.core.brys.*; import gplx.core.btries.*;
import gplx.dbs.engines.sqlite.*;
public class Dbmeta_parser__fld {
	private final    Btrie_rv trv = new Btrie_rv();
	public Dbmeta_fld_itm Parse_fld(Sql_bry_rdr rdr) {	// starts after "(" or ","; EX: "(fld1 int", ", fld2 int"; ends at ")"
		byte[] name = rdr.Read_sql_identifier();
		Dbmeta_fld_tid type = this.Parse_type(rdr);
		Dbmeta_fld_itm fld = new Dbmeta_fld_itm(String_.new_u8(name), type);
		byte[] src = rdr.Src(); int src_len = rdr.Src_end();
		while (true) {
			rdr.Skip_ws();
			if (rdr.Pos() == src_len) return fld;	// eos
			switch (src[rdr.Pos()]) {
				case Byte_ascii.Comma:		return fld;
				case Byte_ascii.Paren_end:	return fld;
				case Byte_ascii.Dash:
					int nxt_pos = rdr.Pos() + 1;
					if (src[nxt_pos] == Byte_ascii.Dash) {
						nxt_pos = Bry_find_.Find_fwd(src, Byte_ascii.Nl, nxt_pos);
						rdr.Move_to(nxt_pos + 1);
					}
					else {
						throw Err_.new_("sqls.dbs", "expected double dash for comment");
					}
					return fld;
			}
			Dbmeta_fld_wkr__base type_wkr = (Dbmeta_fld_wkr__base)rdr.Chk_trie_as_obj(trv, fld_trie);
			switch (type_wkr.Tid()) {
				case Dbmeta_fld_wkr__base.Tid_end_comma:
				case Dbmeta_fld_wkr__base.Tid_end_paren:	return fld;
				default:
					rdr.Move_to(trv.Pos());
					type_wkr.Match(rdr, fld);
					break;
			}
		}
	}
	@gplx.Internal protected Dbmeta_fld_tid Parse_type(Bry_rdr rdr) {
		rdr.Skip_ws();
		Dbmeta_parser__fld_itm type_itm = (Dbmeta_parser__fld_itm)rdr.Chk_trie_as_obj(trv, type_trie);
		rdr.Move_by(type_itm.Word().length);
		int paren_itms_count = type_itm.Paren_itms_count();
		int len_1 = Int_.Min_value, len_2 = Int_.Min_value;
		if (paren_itms_count > 0) {
			rdr.Skip_ws().Chk(Byte_ascii.Paren_bgn);
			len_1 = rdr.Skip_ws().Read_int_to_non_num(); if (len_1 == Int_.Min_value) rdr.Err_wkr().Fail("invalid fld len_1");
			if (paren_itms_count == 2) {
				rdr.Skip_ws().Chk(Byte_ascii.Comma);
				len_2 = rdr.Skip_ws().Read_int_to_non_num(); if (len_2 == Int_.Min_value) rdr.Err_wkr().Fail("invalid fld len_2");
			}
			rdr.Skip_ws().Chk(Byte_ascii.Paren_end);
		}
		return new Dbmeta_fld_tid(type_itm.Tid_ansi(), type_itm.Tid_sqlite(), type_itm.Word(), len_1, len_2);
	}
	private static final    Btrie_slim_mgr fld_trie = fld_trie_init
	( Dbmeta_fld_wkr__nullable_null.Instance
	, Dbmeta_fld_wkr__nullable_not.Instance
	, Dbmeta_fld_wkr__autonumber.Instance
	, Dbmeta_fld_wkr__primary_key.Instance
	, Dbmeta_fld_wkr__default.Instance
	);
	private static Btrie_slim_mgr fld_trie_init(Dbmeta_fld_wkr__base... wkrs) {
		Btrie_slim_mgr rv = Btrie_slim_mgr.ci_a7();
		for (Dbmeta_fld_wkr__base wkr : wkrs)
			wkr.Reg(rv);
		return rv;
	}
	private static final    Btrie_slim_mgr type_trie = type_trie_init();
	private static Btrie_slim_mgr type_trie_init() {
		Btrie_slim_mgr rv = Btrie_slim_mgr.ci_a7();
		Dbmeta_parser__fld_itm.reg_many(rv, Dbmeta_fld_tid.Tid__byte		, Sqlite_tid.Tid_int		, 0, "tinyint", "int2");
		Dbmeta_parser__fld_itm.reg_many(rv, Dbmeta_fld_tid.Tid__short		, Sqlite_tid.Tid_int		, 0, "smallint");
		Dbmeta_parser__fld_itm.reg_many(rv, Dbmeta_fld_tid.Tid__int			, Sqlite_tid.Tid_int		, 0, "int", "integer", "mediumint");
		Dbmeta_parser__fld_itm.reg_many(rv, Dbmeta_fld_tid.Tid__long		, Sqlite_tid.Tid_int		, 0, "bigint", "int8");	// "UNSIGNED BIG INT"
		Dbmeta_parser__fld_itm.reg_many(rv, Dbmeta_fld_tid.Tid__str			, Sqlite_tid.Tid_text		, 1, "character", "varchar", "nchar");	// "varying character", "native character"
		Dbmeta_parser__fld_itm.reg_many(rv, Dbmeta_fld_tid.Tid__text		, Sqlite_tid.Tid_text		, 0, "text", "clob");
		Dbmeta_parser__fld_itm.reg_many(rv, Dbmeta_fld_tid.Tid__bry			, Sqlite_tid.Tid_none		, 0, "blob", "mediumblob");
		Dbmeta_parser__fld_itm.reg_many(rv, Dbmeta_fld_tid.Tid__float		, Sqlite_tid.Tid_real		, 0, "float");
		Dbmeta_parser__fld_itm.reg_many(rv, Dbmeta_fld_tid.Tid__double		, Sqlite_tid.Tid_real		, 0, "real", "double");	// "double precision"
		Dbmeta_parser__fld_itm.reg_many(rv, Dbmeta_fld_tid.Tid__decimal		, Sqlite_tid.Tid_numeric	, 0, "numeric");
		Dbmeta_parser__fld_itm.reg_many(rv, Dbmeta_fld_tid.Tid__decimal		, Sqlite_tid.Tid_numeric	, 2, "decimal");
		Dbmeta_parser__fld_itm.reg_many(rv, Dbmeta_fld_tid.Tid__decimal		, Sqlite_tid.Tid_numeric	, 2, "decimal");
		Dbmeta_parser__fld_itm.reg_many(rv, Dbmeta_fld_tid.Tid__bool		, Sqlite_tid.Tid_numeric	, 0, "boolean", "bit");		// "bit" is not SQLITE
		Dbmeta_parser__fld_itm.reg_many(rv, Dbmeta_fld_tid.Tid__date		, Sqlite_tid.Tid_numeric	, 0, "date", "datetime");
		return rv;
	}
}
class Dbmeta_parser__fld_itm {
	public Dbmeta_parser__fld_itm(int tid_ansi, int tid_sqlite, byte[] word, int paren_itms_count) {
		this.tid_ansi = tid_ansi; this.tid_sqlite = tid_sqlite;
		this.word = word; this.paren_itms_count = paren_itms_count;
	}
	public int Tid_ansi() {return tid_ansi;} private final    int tid_ansi;
	public int Tid_sqlite() {return tid_sqlite;} private final    int tid_sqlite;
	public byte[] Word() {return word;} private final    byte[] word;
	public int Paren_itms_count() {return paren_itms_count;} private final    int paren_itms_count;
	public static void reg_many(Btrie_slim_mgr trie, int tid_ansi, int tid_sqlite, int paren_itms_count, String... names_str) {
		int len = names_str.length;
		for (int i = 0; i < len; ++i) {
			byte[] name_bry = Bry_.new_a7(names_str[i]);
			Dbmeta_parser__fld_itm itm = new Dbmeta_parser__fld_itm(tid_ansi, tid_sqlite, name_bry, paren_itms_count);
			trie.Add_obj(name_bry, itm);
		}
	}
}
