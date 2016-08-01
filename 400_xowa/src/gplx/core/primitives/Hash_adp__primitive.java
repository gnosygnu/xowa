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
package gplx.core.primitives; import gplx.*; import gplx.core.*;
public class Hash_adp__primitive {
	private final    Hash_adp hash = Hash_adp_.New();
	public byte Get_by_str_or_max(String key) {
		Byte_obj_val rv = (Byte_obj_val)hash.Get_by(key);
		return rv == null ? Byte_.Max_value_127 : rv.Val();
	}
	public Hash_adp__primitive Add_byte(String key, byte val) {
		hash.Add(key, Byte_obj_val.new_(val));
		return this;
	}
}
