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
package gplx.dbs.sqls.wtrs;
import gplx.dbs.DbmetaFldItm;
import gplx.dbs.DbmetaFldType;
import gplx.dbs.Dbmeta_idx_itm;
import gplx.dbs.Dbmeta_tbl_itm;
import gplx.dbs.metas.Dbmeta_fld_mgr;
import gplx.dbs.metas.Dbmeta_idx_fld;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
public class Sql_schema_wtr {
	private BryWtr tmp_bfr = BryWtr.NewAndReset(255);
	public Sql_schema_wtr Bfr_(BryWtr bfr) {this.tmp_bfr = bfr; return this;}
	public String Bld_create_idx(Dbmeta_idx_itm idx) {
		tmp_bfr.AddStrA7("CREATE ");
		if (idx.Unique())
			tmp_bfr.AddStrA7("UNIQUE ");
		tmp_bfr.AddStrA7("INDEX ");
		tmp_bfr.AddStrA7("IF NOT EXISTS ");
		tmp_bfr.AddStrA7(idx.Name());
		tmp_bfr.AddStrA7(" ON ");
		tmp_bfr.AddStrA7(idx.Tbl());
		tmp_bfr.AddStrA7(" (");
		Dbmeta_idx_fld[] flds = idx.Flds;
		int flds_len = flds.length;
		for (int i = 0; i < flds_len; ++i) {
			Dbmeta_idx_fld fld = flds[i];
			if (fld == null) continue; // fld will be null when tbl has Dbmetafld.Key_null (to support obsoleted schemas)
			if (i != 0) tmp_bfr.AddStrA7(", ");
			tmp_bfr.AddStrA7(fld.Name);
		}
		tmp_bfr.AddStrA7(");");
		return tmp_bfr.ToStrAndClear();
	}
	public String Bld_create_tbl(Dbmeta_tbl_itm tbl) {
		tmp_bfr.AddStrA7("CREATE TABLE IF NOT EXISTS ").AddStrA7(tbl.Name()).AddByteNl();
		Dbmeta_fld_mgr flds = tbl.Flds();
		int len = flds.Len();
		for (int i = 0; i < len; ++i) {
			DbmetaFldItm fld = flds.Get_at(i);
			tmp_bfr.AddByte(i == 0 ? AsciiByte.ParenBgn : AsciiByte.Comma).AddByteSpace();
			Bld_fld(tmp_bfr, fld);
			tmp_bfr.AddByteNl();
		}
		tmp_bfr.AddStrA7(");");
		return tmp_bfr.ToStrAndClear();
	}
	public String Bld_alter_tbl_add(String tbl, DbmetaFldItm fld) {
		tmp_bfr.AddStrA7("ALTER TABLE ").AddStrA7(tbl).AddStrA7(" ADD ");
		Bld_fld(tmp_bfr, fld);
		tmp_bfr.AddByteSemic();
		return tmp_bfr.ToStrAndClear();
	}
	public String Bld_drop_tbl(String tbl) {
		return StringUtl.Format("DROP TABLE IF EXISTS {0};", tbl);
	}
	private void Bld_fld(BryWtr tmp_bfr, DbmetaFldItm fld) {
		tmp_bfr.AddStrA7(fld.Name()).AddByteSpace();
		Tid_to_sql(tmp_bfr, fld.Type().Tid(), fld.Type().Len1()); tmp_bfr.AddByteSpace();
		switch (fld.Nullable()) {
			case DbmetaFldItm.NullableUnspecified:
			case DbmetaFldItm.NullableNotNull:		tmp_bfr.AddStrA7("NOT NULL "); break;
			case DbmetaFldItm.NullableNull:			tmp_bfr.AddStrA7("NULL "); break;
		}
		if (fld.DefaultVal() != DbmetaFldItm.DefaultValNull) {
			tmp_bfr.AddStrA7("DEFAULT ");
			boolean quote = BoolUtl.N;
			switch (fld.Type().Tid()) {
				case DbmetaFldType.TidStr: case DbmetaFldType.TidText: quote = BoolUtl.Y; break;
			}
			if (quote) tmp_bfr.AddByteApos();
			tmp_bfr.AddStrU8(ObjectUtl.ToStrOrNull(fld.DefaultVal()));
			if (quote) tmp_bfr.AddByteApos();
			tmp_bfr.AddByteSpace();
		}
		if (fld.Primary()) tmp_bfr.AddStrA7("PRIMARY KEY ");
		if (fld.Autonum()) tmp_bfr.AddStrA7("AUTOINCREMENT ");
		tmp_bfr.DelBy1();	// remove trailing space
	}
	public static void Tid_to_sql(BryWtr tmp_bfr, int tid, int len) {// REF: https://www.sqlite.org/datatype3.html
		switch (tid) {
			case DbmetaFldType.TidBool:		tmp_bfr.AddStrA7("boolean"); break;
			case DbmetaFldType.TidByte:		tmp_bfr.AddStrA7("tinyint"); break;
			case DbmetaFldType.TidShort:		tmp_bfr.AddStrA7("smallint"); break;
			case DbmetaFldType.TidInt:		tmp_bfr.AddStrA7("integer"); break;	// NOTE: must be integer, not int, else "int PRIMARY KEY AUTONUMBER" will fail; DATE:2015-02-12
			case DbmetaFldType.TidLong:		tmp_bfr.AddStrA7("bigint"); break;
			case DbmetaFldType.TidFloat:		tmp_bfr.AddStrA7("float"); break;
			case DbmetaFldType.TidDouble:	tmp_bfr.AddStrA7("double"); break;
			case DbmetaFldType.TidStr:		tmp_bfr.AddStrA7("varchar(").AddIntVariable(len).AddByte(AsciiByte.ParenEnd); break;
			case DbmetaFldType.TidText:		tmp_bfr.AddStrA7("text"); break;
			case DbmetaFldType.TidBry:		tmp_bfr.AddStrA7("blob"); break;
			default:							throw ErrUtl.NewUnhandled(tid);
		}
	}
//        public static final Sql_schema_wtr Instance = new Sql_schema_wtr();
}
