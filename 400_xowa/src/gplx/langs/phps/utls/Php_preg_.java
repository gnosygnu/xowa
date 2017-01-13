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
package gplx.langs.phps.utls; import gplx.*; import gplx.langs.*; import gplx.langs.phps.*;
import gplx.core.primitives.*;
public class Php_preg_ {
	public static byte[][] Split(Int_list list, byte[] src, int src_bgn, int src_end, byte[] dlm, boolean extend) {
		// find delimiters
		int dlm_len = dlm.length;
		byte dlm_nth = dlm[dlm_len - 1];
		int i = src_bgn;
		list.Add(src_bgn);
		while (true) {
			if (i == src_end) break;
			int dlm_end = i + dlm_len;
			if (dlm_end < src_end && Bry_.Eq(src, i, dlm_end, dlm)) {
				if (extend) {
					dlm_end = Bry_find_.Find_fwd_while(src, i, src_end, dlm_nth);
				}
				list.Add(i);
				list.Add(dlm_end);
				i = dlm_end;
			}
			else
				i++;
		}
		list.Add(src_end);

		// create brys
		int rv_len = list.Len() - 1;
		if (rv_len == 1) return null;
		byte[][] rv = new byte[rv_len][];
		for (i = 0; i < rv_len; i += 2) {
			rv[i    ] = Bry_.Mid(src, list.Get_at(i + 0), list.Get_at(i + 1));
			if (i + 1 == rv_len) break;
			rv[i + 1] = Bry_.Mid(src, list.Get_at(i + 1), list.Get_at(i + 2));
		}
		return rv;
	}
}
