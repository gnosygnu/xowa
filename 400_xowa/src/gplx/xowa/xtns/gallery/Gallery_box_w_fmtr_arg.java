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
package gplx.xowa.xtns.gallery; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
class Gallery_box_w_fmtr_arg implements gplx.core.brys.Bfr_arg {
	private final    int width;
	public Gallery_box_w_fmtr_arg(int width) {this.width = width;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		bfr.Add(Style_bgn);
		bfr.Add_int_variable(width);
		bfr.Add(Style_end);
	}
	private static final    byte[] Style_bgn = Bry_.new_a7("style=\"width:"), Style_end = Bry_.new_a7("px;\"");
}
