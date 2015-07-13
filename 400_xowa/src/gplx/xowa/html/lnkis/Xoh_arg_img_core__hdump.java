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
package gplx.xowa.html.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
public class Xoh_arg_img_core__hdump implements Xoh_arg_img_core {
	private int uid;
	public Xoh_arg_img_core Init(int uid, byte[] img_src, int img_w, int img_h) {
		this.uid = uid;
		return this;
	}
	public void XferAry(Bry_bfr bfr, int idx) {
		bfr.Add_byte_space();
		bfr.Add(gplx.xowa.html.hdumps.abrvs.Xohd_abrv_.Key_img);
		bfr.Add_int_variable(uid);
		bfr.Add_byte_apos();
	}
}
