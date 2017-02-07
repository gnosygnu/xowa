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
package gplx.xowa.xtns.math; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
class Xomath_latex_itm {
	public Xomath_latex_itm(int uid, byte[] src, byte[] md5, Io_url png) {
		this.uid = uid;
		this.src = src;
		this.md5 = md5;
		this.png = png;
	}
	public int		Uid() {return uid;} private final    int uid;
	public byte[]	Src() {return src;} private final    byte[] src;
	public byte[]	Md5() {return md5;} private final    byte[] md5;
	public Io_url	Png() {return png;} private final    Io_url png;
}
