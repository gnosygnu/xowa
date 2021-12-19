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
package gplx.xowa.addons.bldrs.exports.merges;
import gplx.dbs.Db_conn;
import gplx.dbs.Db_rdr;
import gplx.dbs.Db_stmt;
import gplx.dbs.DbmetaFldList;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
public class Merge2_copy_utl {
	private final BryWtr bfr = BryWtr.New();
	private final String tbl_name, fld_pkey, fld_blob;
	private final boolean tbl_has_blob;
	private Db_conn conn;
	public Merge2_copy_utl(String tbl_name, String fld_pkey, String fld_blob, boolean tbl_has_blob) {
		this.tbl_name = tbl_name;
		this.fld_pkey = fld_pkey;
		this.fld_blob = fld_blob;
		this.tbl_has_blob = tbl_has_blob;
	}
	public void Init_conn(Db_conn conn) {
		this.conn = conn;
	}
	public byte[] Select_blob(Db_rdr data_rdr, String fld_blob_pkey) { // NOTE: need to SELECT entire BLOB; cannot UPDATE in 1 MB increments b/c SQLite does not really concat BLOBS; REF.SQLITE: http://sqlite.1065341.n5.nabble.com/Append-data-to-a-BLOB-field-td46003.html
		int id = data_rdr.Read_int(fld_blob_pkey);
		int len_max = data_rdr.Read_int("blob_len");
		int len_cur = 1, len_gap = 1000000;
		while (len_cur < len_max) {
			// determine substr args; EX: Substr(blob_col, 1, 1000000); Substr(blob_col, 1000001, 1000000); Substr(blob_col, 2000001, 1234);
			int len_new = len_cur + len_gap;
			if (len_new >= len_max) len_gap = len_max - len_cur;	// last SELECT; Substr remainder, not full 1 MB

			// read data; note that stmt needs to be new'd for each loop b/c SQL is different
			Db_stmt stmt = conn.Stmt_sql(StringUtl.Format(StringUtl.ConcatLinesNlSkipLast    // ANSI.N; NOTE: will only run on Android SQLite
			( "SELECT  Substr({0}, {1}, {2}) AS blob_data"
			, "FROM    {3}"
			, "WHERE   {4} = {5}"
			), fld_blob, len_cur, len_gap, tbl_name, fld_pkey, id));
			Db_rdr rdr = stmt.Exec_select__rls_auto();
			try {
				if (rdr.Move_next())
					bfr.Add(rdr.Read_bry("blob_data"));
				else
					throw ErrUtl.NewArgs("failed to read blob in increments", "id", id, "len_cur", len_cur);
			}
			finally {
				rdr.Rls();
				stmt.Rls();
			}

			len_cur = len_new;
		}
		return bfr.ToBryAndClear();
	}
	public String Bld_sql(DbmetaFldList flds, int flds_end, boolean src_is_pack, byte mode, int resume__db_id) {
		bfr.AddStrA7("SELECT");
		for (int i = 0; i < flds_end; ++i) {
			bfr.AddStrA7(i == 0 ? " " : ", ");
			bfr.AddStrU8(flds.GetAt(i).Name());
		}
		bfr.AddStrU8(Bld_select_fld(mode));
		bfr.AddStrA7("\nFROM    ").AddStrU8(tbl_name);
		if (!src_is_pack) {
		}
		else {
			bfr.AddStrA7("\nWHERE   ");
			if (src_is_pack) {
				bfr.AddStrA7("trg_db_id >= ");
				bfr.AddIntVariable(resume__db_id);
				if (mode != Merge2_copy_utl.Mode__all)
					bfr.AddStrA7("\nAND ");
			}
			bfr.AddStrA7(Bld_where(mode));
		}
		if (!src_is_pack) {
			bfr.AddStrA7("\nORDER BY ");
			bfr.AddStrU8(fld_pkey);
		}
		else {
			bfr.AddStrA7("\nORDER BY trg_db_id, ");
			bfr.AddStrU8(fld_pkey);
		}
		return bfr.ToStrAndClear();
	}
	public String Bld_select_fld(byte mode) {
		switch (mode) {
			case Merge2_copy_utl.Mode__all:
			case Merge2_copy_utl.Mode__drd__small:	return tbl_has_blob ? ", " + fld_blob : "";
			case Merge2_copy_utl.Mode__drd__large:	return ", blob_len";
			default:								throw ErrUtl.NewUnhandled(mode);
		}
	}
	public String Bld_where(byte mode) {
		switch (mode) {
			case Merge2_copy_utl.Mode__all:			return "";
			case Merge2_copy_utl.Mode__drd__small:	return "blob_len <= 1000000";
			case Merge2_copy_utl.Mode__drd__large:	return "blob_len >  1000000";
			default:								throw ErrUtl.NewUnhandled(mode);
		}
	}
	public static final byte Mode__all = 0, Mode__drd__small = 1, Mode__drd__large = 2;
}
