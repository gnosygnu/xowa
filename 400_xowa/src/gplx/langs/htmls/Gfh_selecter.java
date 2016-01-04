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
package gplx.langs.htmls; import gplx.*; import gplx.langs.*;
public class Gfh_selecter {
	public static Gfh_nde[] Select(byte[] src, Gfh_nde[] ary, Hash_adp_bry hash) {
		List_adp list = List_adp_.new_();
		int xndes_len = ary.length;
		for (int i = 0; i < xndes_len; i++) {
			Gfh_nde hnde = ary[i];
			int[] atrs = hnde.Atrs();
			int atrs_len = atrs.length;
			for (int j = 0; j < atrs_len; j += 5) {
				int atr_key_bgn = atrs[j + 1];
				int atr_key_end = atrs[j + 2];
				if (hash.Get_by_mid(src, atr_key_bgn, atr_key_end) != null) {
					list.Add(hnde);
					break;
				}
			}
		}
		Gfh_nde[] rv = (Gfh_nde[])list.To_ary(Gfh_nde.class);
		list.Clear();
		return rv;
	}
}
