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
import gplx.core.brys.*;
public class Sql_bry_rdr extends Bry_rdr {		public byte[] Read_sql_identifier() {
		this.Skip_ws();
		int bgn = pos, end = -1;
		if (pos == src_len) return null;
		if (src[pos] == Byte_ascii.Brack_bgn) {	// EX: [name with space]
			bgn = ++pos;	// set bgn after [
			end = this.Find_fwd(Byte_ascii.Brack_end);
			pos = end + 1;	// set pos after ]
		}
		else {
			this.Skip_alpha_num_under();	// ASSUME: identifier is ASCII and alpha / num / underscore
			if (pos == bgn) return null;	// String is not identifier; EX: "!@#"
			end = pos;
		}
		return Bry_.Mid(src, bgn, end);
	}
	@Override public Bry_rdr Skip_ws() {
		byte b_0 = pos < src_len ? src[pos] : Byte_ascii.Nil;
		byte bgn_1 = Byte_ascii.Nil;
		byte[] end_bry = null;
		switch (b_0) {
			case Byte_ascii.Dash:	bgn_1 = Byte_ascii.Dash;		end_bry = Comm_end_line; break;
			case Byte_ascii.Slash:	bgn_1 = Byte_ascii.Asterisk;	end_bry = Comm_end_multi; break;
			case Byte_ascii.Tab: case Byte_ascii.NewLine: case Byte_ascii.CarriageReturn: case Byte_ascii.Space:
				++pos;
				return super.Skip_ws();
			default:
				return this;					
		}
		byte b_1 = pos + 1 < src_len ? src[pos + 1] : Byte_ascii.Nil;
		if (b_1 != bgn_1) return this;
		int end_pos = Bry_finder.Find_fwd(src, end_bry, pos + 2, src_len);
		if (end_pos == Bry_finder.Not_found) return this;
		pos = end_pos + end_bry.length;
		return this.Skip_ws();
	}
	private static final byte[] Comm_end_line = Byte_ascii.NewLine_bry, Comm_end_multi = Bry_.new_a7("*/");
}
