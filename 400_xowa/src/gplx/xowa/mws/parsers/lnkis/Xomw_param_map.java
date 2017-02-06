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
package gplx.xowa.mws.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*; import gplx.xowa.mws.parsers.*;
public class Xomw_param_map {
	private final    Ordered_hash hash = Ordered_hash_.New_bry();
	public Xomw_param_itm Get_by(byte[] name) {
		return (Xomw_param_itm)hash.Get_by(name);
	}
	public Xomw_param_itm Get_by(int name_type) {
		return null;
	}
	public byte[][] Keys() {
		int len = hash.Len();
		byte[][] rv = new byte[len][];
		for (int i = 0; i < len; i++) {
			rv[i] = ((Xomw_param_itm)hash.Get_at(i)).magic;
		}
		return rv;
	}
	public void Add(byte[] magic, byte[] type, byte[] name) {
		Xomw_param_itm itm = new Xomw_param_itm(magic, type, name);
		hash.Add(magic, itm);
	}
	public Xomw_param_map Clone() {
		Xomw_param_map rv = new Xomw_param_map();
		int len = hash.Len();
		for (int i = 0; i < len; i++) {
			Xomw_param_itm itm = (Xomw_param_itm)hash.Get_at(i);
			rv.Add(itm.magic, itm.type, itm.name);
		}
		return rv;
	}
}
class Xomw_param_list {
	public byte[] type;
	public byte[][] names;

	public static Xomw_param_list New(String type, String... names) {
		Xomw_param_list rv = new Xomw_param_list();
		rv.type = Bry_.new_u8(type);
		rv.names = Bry_.Ary(names);
		return rv;
	}
}
