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
package gplx.xowa.specials.xowa.file_browsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*; import gplx.xowa.specials.xowa.*;
public class Xosp_fbrow_rslt {
	public Xosp_fbrow_rslt(byte[] html_head, byte[] html_body) {this.html_head = html_head; this.html_body = html_body;}
	public byte[] Html_head() {return html_head;} private final byte[] html_head;
	public byte[] Html_body() {return html_body;} private final byte[] html_body;
	public static Xosp_fbrow_rslt err_(String msg) {return new Xosp_fbrow_rslt(Bry_.Empty, Bry_.new_utf8_(msg));}
}
