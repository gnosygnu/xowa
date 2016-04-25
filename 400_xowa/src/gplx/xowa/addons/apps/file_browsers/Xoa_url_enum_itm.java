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
package gplx.xowa.addons.apps.file_browsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*;
public class Xoa_url_enum_itm {
	private final    Hash_adp_bry hash = Hash_adp_bry.cs();
	public Xoa_url_enum_itm(byte[] key) {this.key = key;}
	public byte[] Key() {return key;} private final    byte[] key;
	public Xoa_url_enum_itm Add(String key, int val) {
		hash.Add_bry_int(Bry_.new_u8(key), val);
		return this;
	}
	public int Get_as_int_or(byte[] val, int or) {return hash.Get_as_int_or(val, or);}
}
