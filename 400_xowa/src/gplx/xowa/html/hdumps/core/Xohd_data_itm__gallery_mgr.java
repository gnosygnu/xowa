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
package gplx.xowa.html.hdumps.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.hdumps.*;
public class Xohd_data_itm__gallery_mgr implements Xohd_data_itm {
	public Xohd_data_itm__gallery_mgr(int uid, int box_max) {
		this.uid = uid;
		this.box_max = box_max;
	}
	public int Data_tid() {return Xohd_data_tid.Tid_gallery;}
	public void Data_write(Bry_bfr bfr) {
		bfr	.Add_int_variable(Xohd_data_tid.Tid_gallery).Add_byte_pipe()
			.Add_int_variable(box_max).Add_byte_pipe()
			;
		bfr.Add_byte_nl();
	}
	public String Data_print() {return Int_.Xto_str(box_max);}
	public int Uid() {return uid;} private int uid;
	public int Box_max() {return box_max;} private int box_max;
}
