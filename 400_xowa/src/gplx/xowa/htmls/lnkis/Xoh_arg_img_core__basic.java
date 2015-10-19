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
package gplx.xowa.htmls.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
public class Xoh_arg_img_core__basic implements Xoh_arg_img_core {
	private byte[] src; private int w, h;
	public Xoh_arg_img_core Init(int uid, byte[] src, int w, int h) {this.src = src; this.w = w; this.h = h; return this;}
	public void Fmt__do(Bry_bfr bfr) {
		fmtr_img_atrs.Bld_bfr_many(bfr, src, w, h);
	}
	private Bry_fmtr fmtr_img_atrs = Bry_fmtr.new_(" src=\"~{img_src}\" width=\"~{img_w}\" height=\"~{img_h}\"", "img_src", "img_w", "img_h");
}
