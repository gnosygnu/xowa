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
package gplx.dbs.sqls.wtrs; import gplx.Bool_;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.Byte_ascii;
import gplx.Err_;
import gplx.Object_;
import gplx.String_;
import gplx.dbs.DbmetaFldItm;
import gplx.dbs.DbmetaFldType;
import gplx.dbs.Dbmeta_idx_itm;
import gplx.dbs.Dbmeta_tbl_itm;
import gplx.dbs.metas.Dbmeta_fld_mgr;
import gplx.dbs.metas.Dbmeta_idx_fld;
public class Sql_schema_wtr {
	private Bry_bfr tmp_bfr = Bry_bfr_.Reset(255);
	public Sql_schema_wtr Bfr_(Bry_bfr bfr) {this.tmp_bfr = bfr; return this;}
	public String Bld_create_idx(Dbmeta_idx_itm idx) {
		tmp_bfr.Add_str_a7("CREATE ");
		if (idx.Unique())
			tmp_bfr.Add_str_a7("UNIQUE ");
		tmp_bfr.Add_str_a7("INDEX ");
		tmp_bfr.Add_str_a7("IF NOT EXISTS ");
		tmp_bfr.Add_str_a7(idx.Name());
		tmp_bfr.Add_str_a7(" ON ");
		tmp_bfr.Add_str_a7(idx.Tbl());
		tmp_bfr.Add_str_a7(" (");
		Dbmeta_idx_fld[] flds = idx.Flds;
		int flds_len = flds.length;
		for (int i = 0; i < flds_len; ++i) {
			Dbmeta_idx_fld fld = flds[i];
			if (fld == null) continue; // fld will be null when tbl has Dbmetafld.Key_null (to support obsoleted schemas)
			if (i != 0) tmp_bfr.Add_str_a7(", ");
			tmp_bfr.Add_str_a7(fld.Name);
		}
		tmp_bfr.Add_str_a7(");");
		return tmp_bfr.To_str_and_clear();
	}
	public String Bld_create_tbl(Dbmeta_tbl_itm tbl) {
		tmp_bfr.Add_str_a7("CREATE TABLE IF NOT EXISTS ").Add_str_a7(tbl.Name()).Add_byte_nl();
		Dbmeta_fld_mgr flds = tbl.Flds();
		int len = flds.Len();
		for (int i = 0; i < len; ++i) {
			DbmetaFldItm fld = flds.Get_at(i);
			tmp_bfr.Add_byte(i == 0 ? Byte_ascii.Paren_bgn : Byte_ascii.Comma).Add_byte_space();
			Bld_fld(tmp_bfr, fld);
			tmp_bfr.Add_byte_nl();
		}
		tmp_bfr.Add_str_a7(");");
		return tmp_bfr.To_str_and_clear();
	}
	public String Bld_alter_tbl_add(String tbl, DbmetaFldItm fld) {
		tmp_bfr.Add_str_a7("ALTER TABLE ").Add_str_a7(tbl).Add_str_a7(" ADD ");
		Bld_fld(tmp_bfr, fld);
		tmp_bfr.Add_byte_semic();
		return tmp_bfr.To_str_and_clear();
	}
	public String Bld_drop_tbl(String tbl) {
		return String_.Format("DROP TABLE IF EXISTS {0};", tbl);
	}
	private void Bld_fld(Bry_bfr tmp_bfr, DbmetaFldItm fld) {
		tmp_bfr.Add_str_a7(fld.Name()).Add_byte_space();
		Tid_to_sql(tmp_bfr, fld.Type().Tid(), fld.Type().Len1()); tmp_bfr.Add_byte_space();
		switch (fld.Nullable()) {
			case DbmetaFldItm.NullableUnspecified:
			case DbmetaFldItm.NullableNotNull:		tmp_bfr.Add_str_a7("NOT NULL "); break;
			case DbmetaFldItm.NullableNull:			tmp_bfr.Add_str_a7("NULL "); break;
		}
		if (fld.DefaultVal() != DbmetaFldItm.DefaultValNull) {
			tmp_bfr.Add_str_a7("DEFAULT ");
			boolean quote = Bool_.N;
			switch (fld.Type().Tid()) {
				case DbmetaFldType.TidStr: case DbmetaFldType.TidText: quote = Bool_.Y; break;
			}
			if (quote) tmp_bfr.Add_byte_apos();
			tmp_bfr.Add_str_u8(Object_.Xto_str_strict_or_null(fld.DefaultVal()));
			if (quote) tmp_bfr.Add_byte_apos();
			tmp_bfr.Add_byte_space();
		}
		if (fld.Primary()) tmp_bfr.Add_str_a7("PRIMARY KEY ");
		if (fld.Autonum()) tmp_bfr.Add_str_a7("AUTOINCREMENT ");
		tmp_bfr.Del_by_1();	// remove trailing space
	}
	public static void Tid_to_sql(Bry_bfr tmp_bfr, int tid, int len) {// REF: https://www.sqlite.org/datatype3.html
		switch (tid) {
			case DbmetaFldType.TidBool:		tmp_bfr.Add_str_a7("boolean"); break;
			case DbmetaFldType.TidByte:		tmp_bfr.Add_str_a7("tinyint"); break;
			case DbmetaFldType.TidShort:		tmp_bfr.Add_str_a7("smallint"); break;
			case DbmetaFldType.TidInt:		tmp_bfr.Add_str_a7("integer"); break;	// NOTE: must be integer, not int, else "int PRIMARY KEY AUTONUMBER" will fail; DATE:2015-02-12
			case DbmetaFldType.TidLong:		tmp_bfr.Add_str_a7("bigint"); break;
			case DbmetaFldType.TidFloat:		tmp_bfr.Add_str_a7("float"); break;
			case DbmetaFldType.TidDouble:	tmp_bfr.Add_str_a7("double"); break;
			case DbmetaFldType.TidStr:		tmp_bfr.Add_str_a7("varchar(").Add_int_variable(len).Add_byte(Byte_ascii.Paren_end); break;
			case DbmetaFldType.TidText:		tmp_bfr.Add_str_a7("text"); break;
			case DbmetaFldType.TidBry:		tmp_bfr.Add_str_a7("blob"); break;
			default:							throw Err_.new_unhandled(tid);
		}
	}
//        public static final Sql_schema_wtr Instance = new Sql_schema_wtr();
}
