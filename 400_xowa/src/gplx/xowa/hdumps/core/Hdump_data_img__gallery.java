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
package gplx.xowa.hdumps.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
public class Hdump_data_img__gallery extends Hdump_data_img__base {
	@Override public int Tid() {return Hdump_data_img__base.Tid_gallery;}
	public Hdump_data_img__base Init_by_gallery(int box_max, int box_w, int img_w, int img_pad) {
		this.box_max = box_max;
		this.box_w = box_w;
		this.img_w = img_w;
		this.img_pad = img_pad;
		return this;
	}
	public int Box_max() {return box_max;} private int box_max;
	public int Box_w() {return box_w;} private int box_w;
	public int Img_w() {return img_w;} private int img_w;
	public int Img_pad() {return img_pad;} private int img_pad;
	@Override public void Write_hook(Bry_bfr bfr) {
		bfr	.Add_int_variable(box_max).Add_byte_pipe()
			.Add_int_variable(box_w).Add_byte_pipe()
			.Add_int_variable(img_w).Add_byte_pipe()
			.Add_int_variable(img_pad).Add_byte_pipe()
			;
	}
}
