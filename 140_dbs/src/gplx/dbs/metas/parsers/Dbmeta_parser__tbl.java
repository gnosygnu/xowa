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
