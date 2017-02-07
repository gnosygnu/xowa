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
package gplx.langs.htmls.styles; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
public class Gfh_style_itm implements To_str_able {
	public Gfh_style_itm(int idx, byte[] key, byte[] val) {this.idx = idx; this.key = key; this.val = val;}
	public int Idx() {return idx;} private final int idx;
	public byte[] Key() {return key;} private final byte[] key;
	public byte[] Val() {return val;} private final byte[] val;
	public String To_str() {return String_.new_u8(Bry_.Add(key, Byte_ascii.Colon_bry, val, Byte_ascii.Semic_bry));}
}
