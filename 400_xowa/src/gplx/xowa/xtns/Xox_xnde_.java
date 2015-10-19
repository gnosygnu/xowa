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
package gplx.xowa.xtns; import gplx.*; import gplx.xowa.*;
import gplx.xowa.parsers.htmls.*; import gplx.xowa.parsers.xndes.*;
public class Xox_xnde_ {
	public static Mwh_atr_itm[] Xatr__set(Xowe_wiki wiki, Mwh_atr_itm_owner owner, Hash_adp_bry hash, byte[] src, Xop_xnde_tkn xnde) {
		Mwh_atr_itm[] rv = wiki.Appe().Parser_mgr().Xnde__parse_atrs(src, xnde.Atrs_bgn(), xnde.Atrs_end());
		int len = rv.length;
		for (int i = 0; i < len; ++i) {
			Mwh_atr_itm xatr = rv[i]; if (xatr.Invalid()) continue;
			owner.Xatr__set(wiki, src, xatr, hash.Get_by_bry(xatr.Key_bry()));
		}
		return rv;
	}
}
