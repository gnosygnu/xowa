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
package gplx.xowa; import gplx.*;
public class Xobcl_kwd_row {
	public Xobcl_kwd_row(byte[] key, byte[][] itms) {
		this.key = key; this.itms = itms;
		for (byte[] itm : itms)
			itms_hash.Add(itm, itm);
	} 
	public byte[] Key() {return key;} private byte[] key;
	public byte[][] Itms() {return itms;} private byte[][] itms;
	public boolean Itms_has(byte[] key) {return itms_hash.Has(key);} private OrderedHash itms_hash = OrderedHash_.new_bry_();
}
