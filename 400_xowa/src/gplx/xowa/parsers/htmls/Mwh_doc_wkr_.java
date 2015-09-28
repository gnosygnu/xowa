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
package gplx.xowa.parsers.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.parsers.xndes.*;
public class Mwh_doc_wkr_ {
	public static Hash_adp_bry Nde_regy__mw() {
		Xop_xnde_tag[] ary = Xop_xnde_tag_.Ary;
		int len = ary.length;
		Hash_adp_bry rv = Hash_adp_bry.ci_a7();
		for (int i = 0; i < len; ++i) {
			Xop_xnde_tag itm = ary[i];
			rv.Add(itm.Name_bry(), itm);
		}
		return rv;
	}
}
