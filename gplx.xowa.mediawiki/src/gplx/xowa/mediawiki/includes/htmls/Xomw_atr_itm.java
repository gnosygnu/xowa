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
package gplx.xowa.mediawiki.includes.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
public class Xomw_atr_itm {
	public Xomw_atr_itm(int key_int, byte[] key, byte[] val) {
		this.key_int = key_int;
		this.key_bry = key;
		this.val = val;
	}
	public int Key_int() {return key_int;} private int key_int;
	public byte[] Key_bry() {return key_bry;} private byte[] key_bry;
	public byte[] Val() {return val;} private byte[] val;
	public void Val_(byte[] v) {this.val = v;}
}
