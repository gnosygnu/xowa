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
package gplx.xowa.parsers.tblws; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.htmls.*;
public interface Xop_tblw_tkn extends Xop_tkn_itm {
	int Tblw_tid();
	boolean Tblw_xml();
	int Tblw_subs_len(); void Tblw_subs_len_add_();
	int Atrs_bgn();
	int Atrs_end();
	void Atrs_rng_set(int bgn, int end);
	Mwh_atr_itm[] Atrs_ary(); Xop_tblw_tkn Atrs_ary_as_tblw_(Mwh_atr_itm[] v);
}
