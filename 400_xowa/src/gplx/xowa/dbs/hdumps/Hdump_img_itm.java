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
package gplx.xowa.dbs.hdumps; import gplx.*; import gplx.xowa.*; import gplx.xowa.dbs.*;
public class Hdump_img_itm {
	public Hdump_img_itm(int img_id, byte[] img_src, int img_w, int img_h) {
		this.img_id = img_id;
		this.img_src = img_src;
		this.img_w = img_w;
		this.img_h = img_h;
	}
	public int Img_id() {return img_id;} private int img_id;
	public byte[] Img_src() {return img_src;} private byte[] img_src;
	public int Img_w() {return img_w;} private int img_w;
	public int Img_h() {return img_h;} private int img_h;
	public void Write_html(Bry_bfr bfr) {
		fmtr.Bld_bfr_many(bfr, img_src, img_w, img_h);
	}	private static final Bry_fmtr fmtr = Bry_fmtr.new_(" src='~{src}' width='~{w}' height='~{h}'", "src", "w", "h");
}
