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
package gplx.xowa.mws.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
public class Xomw_parser_bfr_ {
	public static void Replace(Xomw_parser_bfr pbfr, byte[] find, byte[] repl) {
		// XO.PBFR
		Bry_bfr src_bfr = pbfr.Src();
		byte[] src = src_bfr.Bfr();
		int src_bgn = 0;
		int src_end = src_bfr.Len();
		Bry_bfr bfr = pbfr.Trg();

		if (Replace(bfr, Bool_.N, src, src_bgn, src_end, find, repl) != null)
			pbfr.Switch();
	}
	private static byte[] Replace(Bry_bfr bfr, boolean lone_bfr, byte[] src, int src_bgn, int src_end, byte[] find, byte[] repl) {
		boolean dirty = false;
		int cur = src_bgn;
		boolean called_by_bry = bfr == null;

		while (true) {
			int find_bgn = Bry_find_.Find_fwd(src, find, cur);
			if (find_bgn == Bry_find_.Not_found) {
				if (dirty)
					bfr.Add_mid(src, cur, src_end);
				break;
			}
			if (called_by_bry) bfr = Bry_bfr_.New();
			bfr.Add_mid(src, cur, find_bgn);
			cur += find.length;
			dirty = true;
		}

		if (dirty) {
			if (called_by_bry)
				return bfr.To_bry_and_clear();
			else
				return Bry_.Empty;
		}
		else {
			if (called_by_bry) {
				if (src_bgn == 0 && src_end == src.length)
					return src;
				else
					return Bry_.Mid(src, src_bgn, src_end);
			}
			else {
				if (lone_bfr)
					bfr.Add_mid(src, src_bgn, src_end);
				return null;
			}
		}
	}
}
