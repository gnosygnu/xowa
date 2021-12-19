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
package gplx.dbs.metas.parsers; import gplx.dbs.*;
import gplx.core.btries.*;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
public class Dbmeta_parser__tbl {
	private final Sql_bry_rdr rdr = new Sql_bry_rdr();
	private final Dbmeta_parser__fld fld_parser = new Dbmeta_parser__fld();
	public Dbmeta_tbl_itm Parse(byte[] src) {
		rdr.InitByPage(BryUtl.Empty, src, src.length);
		rdr.SkipWs().ChkTrieVal(trie, Tid__create);
		rdr.SkipWs().ChkTrieVal(trie, Tid__table);
		byte[] tbl_name = rdr.Read_sql_identifier();
		rdr.SkipWs().Chk(AsciiByte.ParenBgn);
		Dbmeta_tbl_itm rv = Dbmeta_tbl_itm.New(StringUtl.NewU8(tbl_name));
		boolean loop = true;
		while (loop) {
			DbmetaFldItm fld = fld_parser.Parse_fld(rdr); if (fld == null) rdr.ErrWkr().Fail("unknown field", "src", src);
			rv.Flds().Add(fld);
			int pos = rdr.Pos();
			byte b = pos == rdr.SrcEnd() ? AsciiByte.Null : src[pos];
			switch (b) {
				case AsciiByte.Comma:		rdr.MoveByOne(); break;
				case AsciiByte.ParenEnd:	rdr.MoveByOne(); loop = false; break;
				default:					rdr.ErrWkr().Fail("premature end of flds"); break;
			}
		}
		return rv;
	}
	private static final byte Tid__create = 0, Tid__table = 1;
	private static final byte[]
	  Bry__create	= BryUtl.NewA7("create")
	, Bry__table	= BryUtl.NewA7("table");
	private static final Btrie_slim_mgr trie = Btrie_slim_mgr.ci_a7()
	.Add_bry_byte(Bry__create	, Tid__create)
	.Add_bry_byte(Bry__table	, Tid__table);
}
