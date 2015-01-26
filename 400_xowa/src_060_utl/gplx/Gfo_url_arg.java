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
package gplx;
public class Gfo_url_arg {
	public Gfo_url_arg(byte[] key_bry, byte[] val_bry) {this.key_bry = key_bry; this.val_bry = val_bry;}
	public byte[] Key_bry() {return key_bry;} private byte[] key_bry;
	public byte[] Val_bry() {return val_bry;} private byte[] val_bry;
	public Gfo_url_arg Val_bry_(byte[] v) {val_bry = v; return this;}
	public static final Gfo_url_arg[] Ary_empty = new Gfo_url_arg[0];
	public static Gfo_url_arg new_key_(String key) {
		return new Gfo_url_arg(Bry_.new_utf8_(key), Bry_.Empty);
	}
	public static Gfo_url_arg[] Ary(String... kvs) {
		int len = kvs.length;
		Gfo_url_arg[] rv = new Gfo_url_arg[len / 2];
		String key = null;
		for (int i = 0; i < len; ++i) {
			String s = kvs[i];
			if (i % 2 == 0)
				key = s;
			else
				rv[i / 2] = new Gfo_url_arg(Bry_.new_utf8_(key), Bry_.new_utf8_(s));
		}
		return rv;
	}
}
