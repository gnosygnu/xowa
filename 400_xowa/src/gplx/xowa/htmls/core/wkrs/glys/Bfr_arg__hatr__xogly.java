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
import gplx.xowa.htmls.core.wkrs.bfr_args.*;
class Bfr_arg__hatr__xogly implements gplx.core.brys.Bfr_arg_clearable {
	private final    byte[] atr_bgn;
	private int xnde_w, xnde_h, xnde_per_row;
	public Bfr_arg__hatr__xogly() {
		this.atr_bgn = Bfr_arg__hatr_.Bld_atr_bgn(gplx.xowa.xtns.gallery.Gallery_mgr_wtr.Bry__data_xogly);
		this.Clear();
	}
	public void Set_args(int xnde_w, int xnde_h, int xnde_per_row) {
		this.xnde_w = xnde_w; this.xnde_h = xnde_h; this.xnde_per_row = xnde_per_row;
	}
	public void Clear() {xnde_w = xnde_h = xnde_per_row = -1;}
	public void Bfr_arg__clear() {this.Clear();}
	public boolean Bfr_arg__missing() {return false;} // NOTE: do not check if "xnde_w == -1 && xnde_h == -1 && xnde_per_row == -1" else will fail hzip diff; DATE:2016-07-02
	public void Bfr_arg__add(Bry_bfr bfr) {
		if (Bfr_arg__missing()) return;
		bfr.Add(atr_bgn);
		bfr.Add_int_variable(xnde_w).Add_byte_pipe();
		bfr.Add_int_variable(xnde_h).Add_byte_pipe();
		bfr.Add_int_variable(xnde_per_row);
		bfr.Add_byte_quote();
	}
}