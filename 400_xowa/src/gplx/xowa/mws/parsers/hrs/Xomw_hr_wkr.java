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
package gplx.xowa.mws.parsers.hrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*; import gplx.xowa.mws.parsers.*;
import gplx.langs.phps.utls.*;
public class Xomw_hr_wkr {// THREAD.UNSAFE: caching for repeated calls
	private Bry_bfr bfr;
	public void Replace_hrs(Xomw_parser_ctx pctx, Xomw_parser_bfr pbfr) {	// REF.MW: text = preg_replace('/(^|\n)-----*/', '\\1<hr />', text);
		// XO.PBFR
		Bry_bfr src_bfr = pbfr.Src();
		byte[] src = src_bfr.Bfr();
		int src_bgn = 0;
		int src_end = src_bfr.Len();
		this.bfr = pbfr.Trg();

		boolean dirty = false;

		// do separate check for "-----" at start of String;
		int cur = 0;
		if (Bry_.Eq(src, 0, Len__wtxt__hr__bos, Bry__wtxt__hr__bos)) {
			cur = Replace_hr(Bool_.N, src, src_bgn, src_end, 0, Len__wtxt__hr__bos);
			dirty = true;
		}

		// loop
		while (true) {
			// find next "\n-----"
			int find_bgn = Bry_find_.Find_fwd(src, Bry__wtxt__hr__mid, cur, src_end);

			// nothing found; exit
			if (find_bgn == Bry_find_.Not_found) {
				if (dirty) {
					bfr.Add_mid(src, cur, src_end);
				}
				break;
			}
			
			// something found
			cur = Replace_hr(Bool_.Y, src, cur, src_end, find_bgn, Len__wtxt__hr__mid);
			dirty = true;
		}
		if (dirty)
			pbfr.Switch();
	}
	private int Replace_hr(boolean mid, byte[] src, int cur, int src_end, int find_bgn, int tkn_len) {
		// something found; add to bfr
		if (mid) {
			bfr.Add_mid(src, cur, find_bgn);	// add everything before "\n-----"
			bfr.Add_byte_nl();
		}
		bfr.Add(Bry__html__hr);

		// set dirty / cur and continue
		cur = find_bgn + tkn_len;
		cur = Bry_find_.Find_fwd_while(src, cur, src_end, Byte_ascii.Dash);	// gobble up trailing "-"; the "*" in "-----*" from the regex above
		return cur;
	}
	private static final    byte[] 
	  Bry__wtxt__hr__mid = Bry_.new_a7("\n-----")
	, Bry__wtxt__hr__bos = Bry_.new_a7("-----")
	, Bry__html__hr      = Bry_.new_a7("<hr />")
	;
	private static final    int 
	  Len__wtxt__hr__mid = Bry__wtxt__hr__mid.length
	, Len__wtxt__hr__bos = Bry__wtxt__hr__bos.length
	;
}
