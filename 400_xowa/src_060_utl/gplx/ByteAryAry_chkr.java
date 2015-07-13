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
public class ByteAryAry_chkr implements Tst_chkr {
	public Class<?> TypeOf() {return byte[][].class;}
	public ByteAryAry_chkr Val_(byte[][] v) {ary = v; return this;}  byte[][] ary;
	public int Chk(Tst_mgr mgr, String path, Object actl_obj) {
		byte[][] actl = (byte[][])actl_obj;
		int actl_len = actl.length;
		int expd_len = ary.length;
		int err = 0;
		err += mgr.Tst_val(true, path, "len", ary.length, actl_len);
		for (int i = 0; i < expd_len; i++) {
			byte[] actl_itm = i >= actl_len ? null : actl[i];
			err += mgr.Tst_val(false, path, "ary:" + Int_.Xto_str(i), String_.new_u8(ary[i]), String_.new_u8(actl_itm));
		}
		return err;
	}
}
