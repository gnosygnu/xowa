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
public class Dbmeta_parser__tbl {
	private final Sql_bry_rdr rdr = new Sql_bry_rdr();
	private final Dbmeta_parser__fld fld_parser = new Dbmeta_parser__fld();
	public Dbmeta_tbl_itm Parse(byte[] src) {
		rdr.Init_by_page(Bry_.Empty, src, src.length);
		rdr.Skip_ws().Chk_trie_val(trie, Tid__create);
		rdr.Skip_ws().Chk_trie_val(trie, Tid__table);
		byte[] tbl_name = rdr.Read_sql_identifier();
		rdr.Skip_ws().Chk(Byte_ascii.Paren_bgn);
		Dbmeta_tbl_itm rv = Dbmeta_tbl_itm.New(String_.new_u8(tbl_name));
		boolean loop = true;
		while (loop) {
			Dbmeta_fld_itm fld = fld_parser.Parse_fld(rdr); if (fld == null) rdr.Err_wkr().Fail("unknown field", "src", src);
			rv.Flds().Add(fld);
			int pos = rdr.Pos();
			byte b = pos == rdr.Src_end() ? Byte_ascii.Null : src[pos];
			switch (b) {
				case Byte_ascii.Comma:		rdr.Move_by_one(); break;
				case Byte_ascii.Paren_end:	rdr.Move_by_one(); loop = false; break;
				default:					rdr.Err_wkr().Fail("premature end of flds"); break;
			}
		}
		return rv;
	}
	private static final byte Tid__create = 0, Tid__table = 1;
	private static final byte[]
	  Bry__create	= Bry_.new_a7("create")
	, Bry__table	= Bry_.new_a7("table");
	private static final Btrie_slim_mgr trie = Btrie_slim_mgr.ci_a7()
	.Add_bry_byte(Bry__create	, Tid__create)
	.Add_bry_byte(Bry__table	, Tid__table);
}
