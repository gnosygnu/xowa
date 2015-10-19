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
package gplx.xowa.specials.search.quicks; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*; import gplx.xowa.specials.search.*;
public class Xoa_search_itm {
	public Xoa_search_itm(byte[] url, byte[] name, byte[] descrip, byte[] img) {
		this.url = url; this.name = name; this.descrip = descrip; this.img = img;
	}
	public byte[] Url() {return url;} private final byte[] url;				// EX: en.wikipedia.org/wiki/Earth
	public byte[] Name() {return name;} private final byte[] name;			// EX: Earth
	public byte[] Descrip() {return descrip;} private final byte[] descrip;	// EX: Third planet from the Sun
	public byte[] Img() {return img;} private final byte[] img;				// EX: Earth.png
}
