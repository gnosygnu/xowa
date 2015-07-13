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
public class Meta_parser__tbl {
	private final Sql_bry_rdr rdr = new Sql_bry_rdr();
	private final Meta_parser__fld fld_parser = new Meta_parser__fld();
	private Meta_tbl_itm tbl;
	public Meta_tbl_itm Parse(byte[] src) {
		src = Bry_.Lower_ascii(src);
		rdr.Init(src);
		tbl = null;
		Parse_hdr();
		Parse_flds();
		return tbl;
	}
	private void Parse_hdr() {
		rdr.Skip_ws().Chk_bry_or_fail(Tkn_create);
		rdr.Skip_ws().Chk_bry_or_fail(Tkn_table);
		byte[] tbl_name = rdr.Read_sql_identifier();
		this.tbl = new Meta_tbl_itm(String_.new_u8(tbl_name), String_.new_u8(rdr.Src()));
		rdr.Skip_ws().Chk_byte_or_fail(Byte_ascii.Paren_bgn);
	}
	private void Parse_flds() {
		byte[] src = rdr.Src();
		while (true) {
			Meta_fld_itm fld = fld_parser.Parse_fld(rdr); if (fld == null) throw Exc_.new_("unknown field", "src", rdr.Src());
			tbl.Flds().Add(fld);
			int pos = rdr.Pos();
			byte b = pos == rdr.Src_len() ? Byte_ascii.Nil : src[pos];
			switch (b) {
				case Byte_ascii.Comma:		rdr.Pos_add_one(); break;
				case Byte_ascii.Paren_end:	rdr.Pos_add_one(); return;
				default:					throw Exc_.new_("premature end of flds", "src", rdr.Mid_by_len_safe(40));
			}
		}
	}
	private static final byte[]
	  Tkn_create	= Bry_.new_a7("create")
	, Tkn_table		= Bry_.new_a7("table")
	;
}
