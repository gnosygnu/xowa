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
package gplx.xowa; import gplx.*;
import gplx.ios.*;
class Sql_fld_mgr {
	public int Count() {return hash.Count();}
	public Sql_fld_itm Get_by_key(String fld) {return Get_by_key(Bry_.new_utf8_(fld));}
	public Sql_fld_itm Get_by_key(byte[] fld) {
		return (Sql_fld_itm)hash.Fetch(fld);
	}	private OrderedHash hash = OrderedHash_.new_bry_();
	public Sql_fld_mgr Parse(byte[] raw) {
		hash.Clear();
		int bgn = Bry_finder.Find_fwd(raw, Tkn_create_table); if (bgn == Bry_.NotFound) throw Err_.new_("could not find 'CREATE TABLE'");
		bgn = Bry_finder.Find_fwd(raw, Byte_ascii.NewLine, bgn); if (bgn == Bry_.NotFound) throw Err_.new_("could not find new line after 'CREATE TABLE'");
		bgn += Int_.Const_position_after_char;
		int end = Bry_finder.Find_fwd(raw, Tkn_unique_index); if (end == Bry_.NotFound) throw Err_.new_("could not find 'UNIQUE KEY'");
		end = Bry_finder.Find_bwd(raw, Byte_ascii.NewLine, end); if (bgn == Bry_.NotFound) throw Err_.new_("could not find new line before 'UNIQUE KEY'");
		Parse_lines(Bry_.Mid(raw, bgn, end));
		return this;
	}
	private void Parse_lines(byte[] raw) {
		byte[][] lines = Bry_.Split(raw, Byte_ascii.NewLine);
		int lines_len = lines.length;
		int fld_idx = 0;
		for (int i = 0; i < lines_len; i++) {
			byte[] line = lines[i];
			int bgn = Bry_finder.Find_fwd(line, Byte_ascii.Tick); if (bgn == Bry_.NotFound) continue;	// skip blank lines
			bgn += Int_.Const_position_after_char;
			int end = Bry_finder.Find_fwd(line, Byte_ascii.Tick, bgn); if (end == Bry_.NotFound) continue;	// skip blank lines
			byte[] key = Bry_.Mid(line, bgn, end);
			Sql_fld_itm fld = new Sql_fld_itm(fld_idx++, key);
			hash.Add(fld.Key(), fld);
		}
	}
	private static final byte[] 
		Tkn_create_table = Bry_.new_ascii_("CREATE TABLE")
	,	Tkn_unique_index = Bry_.new_ascii_("UNIQUE KEY")
	;
	public static final int Not_found = -1;
}
class Sql_fld_itm {
	public Sql_fld_itm(int idx, byte[] key) {this.idx = idx; this.key = key;}
	public int Idx() {return idx;} private int idx;
	public byte[] Key() {return key;} private byte[] key;
}
