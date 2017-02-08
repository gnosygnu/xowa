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
package gplx.xowa.mws.utls; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
public class Php_utl_ {
	public static boolean Empty(byte[] v)  {return v == null || v.length == 0;}
	public static boolean Empty(boolean v)    {return v == false;}
	public static boolean isset(byte[] v) {return v != null;}
	public static boolean isset(int v) {return v != Null_int;}
	public static boolean istrue(int v) {return v != Null_int;}
	public static boolean isnumeric(byte[] src) {
		if (src == null) return false;
		int len = src.length;
		for (int i = 0; i < len; i++) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
					break;
				default:
					return false;
			}
		}
		return true;
	}
	public static final int Null_int = Int_.Max_value;
}
