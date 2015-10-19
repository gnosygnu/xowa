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
package gplx.xowa.bldrs.wms.apis; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wms.*;
public class Xoapi_orig_rslts {
	public byte[] Orig_wiki() {return orig_wiki;} private byte[] orig_wiki;
	public byte[] Orig_page() {return orig_page;} private byte[] orig_page;
	public int Orig_w() {return orig_w;} private int orig_w;
	public int Orig_h() {return orig_h;} private int orig_h;
	public void Init_all(byte[] wiki, byte[] page, int w, int h) {
		this.orig_wiki = wiki; this.orig_page = page; this.orig_w = w; this.orig_h = h;
	}
	public void Clear() {
		orig_wiki = orig_page = null;
		orig_w = orig_h = 0;
	}
}
