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
package gplx.xowa.htmls.core.wkrs.glys; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.langs.htmls.*;
class Bfr_arg__elem__capt implements gplx.core.brys.Bfr_arg_clearable {
	private byte[] capt;
	public Bfr_arg__elem__capt() {
		this.Clear();
	}
	public void Capt_(byte[] v) {this.capt = v;}
	public void Clear() {capt = null;}
	public void Bfr_arg__clear() {this.Clear();}
	public boolean Bfr_arg__missing() {return capt == null;}
	public void Bfr_arg__add(Bry_bfr bfr) {		// EX: '\n<li class="gallerycaption">Z</li>'
		if (Bfr_arg__missing()) return;
		bfr.Add_byte_nl();
		bfr.Add(Gfh_tag_.Li_lhs_bgn);			// '<li'
		Gfh_atr_.Add(bfr, Gfh_atr_.Bry__class, Xoh_gly_grp_data.Atr__cls__gallerycaption);	// ' class="gallerycaption"'
		bfr.Add_byte(Byte_ascii.Angle_end);		// '>'
		bfr.Add(capt);
		bfr.Add(Gfh_tag_.Li_rhs);				// '</li>'
	}
}