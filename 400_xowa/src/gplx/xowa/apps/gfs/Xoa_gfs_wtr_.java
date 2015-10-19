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
package gplx.xowa.apps.gfs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
public class Xoa_gfs_wtr_ {
	public static byte[] Escape(String v) {return Escape(Bry_.new_u8(v));}
	public static byte[] Escape(byte[] v) {
		return Bry_find_.Find_fwd(v, Byte_ascii.Apos) == Bry_.NotFound ? v : Bry_.Replace(v, Byte_ascii.Apos_bry, Bry__apos_escaped);
	}	private static final byte[] Bry__apos_escaped = Bry_.new_a7("''");
	public static void Write_prop(Bry_bfr bfr, byte[] prop, byte[] val) {
		bfr.Add(prop).Add(Bry__val_bgn).Add(Xoa_gfs_wtr_.Escape(val)).Add(Bry__val_end);	// EX: "a_('b');\n"
	}	private static final byte[] Bry__val_bgn = Bry_.new_a7("_('"), Bry__val_end = Bry_.new_a7("');\n");
	public static String Write_func_chain(String... ary) {	// EX: "a.b.c"
		Bry_bfr bfr = Xoa_app_.Utl__bfr_mkr().Get_b128();	// Bry_bfr.try_none: simple-ops
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			if (i != 0) bfr.Add_byte(Byte_ascii.Dot);
			bfr.Add_str(ary[i]);				
		}
		return bfr.To_str_and_rls();
	}
}
