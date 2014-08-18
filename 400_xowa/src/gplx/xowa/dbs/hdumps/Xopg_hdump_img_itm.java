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
public class Xopg_hdump_img_itm {
	public int Uid() {return uid;} private int uid;
	public int Img_w() {return img_w;} private int img_w;
	public int Img_h() {return img_h;} private int img_h;
	public byte[] Img_src() {return img_src;} private byte[] img_src;
	public Xopg_hdump_img_itm(int uid, int img_w, int img_h, byte[] img_src) {
		this.uid = uid; this.img_w = img_w; this.img_h = img_h; this.img_src = img_src;
	}
	@Override public String toString() {
		return String_.Concat_with_str("|", Int_.XtoStr(uid), Int_.XtoStr(img_w), Int_.XtoStr(img_h), String_.new_utf8_(img_src));
	}
}
