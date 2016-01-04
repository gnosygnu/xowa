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
package gplx.xowa.xtns.imaps; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.primitives.*;
class Imap_pts_fmtr_arg implements gplx.core.brys.Bfr_arg {
	private double scale = 1; private Double_obj_val[] pts;
	public void Scale_(double v) {this.scale = v;}
	public void Pts_(Double_obj_val[] v) {this.pts = v;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		int pts_len = pts.length;
		for (int i = 0; i < pts_len; ++i) {
			Double_obj_val pt = pts[i];
			if (i != 0) bfr.Add_byte_comma();
			int pt_int = (int)pt.Val();
			pt_int = (int)Math_.Round(scale * pt_int, 0);
			bfr.Add_int_variable(pt_int);
		}
	}
}
