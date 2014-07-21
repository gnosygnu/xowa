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
public class Byte_ {
	public static final byte MinValue = Byte.MIN_VALUE;	
	public static byte[] Ary(byte... ary) {return ary;}
	public static byte[] Ary_by_ints(int... ary) {
		int ary_len = ary.length;
		byte[] rv = new byte[ary_len];
		for (int i = 0; i < ary_len; i++)
			rv[i] = int_(ary[i]);
		return rv;
	}
	public static String XtoStr(byte v) {return new Byte(v).toString();} 
	public static int XtoInt(byte v) {return v < 0 ? (int)v + 256 : v;}
	public static boolean In(byte v, byte... ary) {
		for (byte itm : ary)
			if (v == itm) return true;
		return false;
	}
	public static int Compare(byte lhs, byte rhs) {
		if		(lhs == rhs) 	return CompareAble_.Same;
		else if (lhs < rhs)		return CompareAble_.Less;
		else 					return CompareAble_.More;
	}
	public static byte cast_(Object o) {try {return (Byte)o;} catch (Exception e) {throw Err_.type_mismatch_exc_(e, byte.class, o);}}
	public static byte parse_(String raw) {return Byte.parseByte(raw);}	
	public static byte int_(int v) {return v > 127 ? (byte)(v - 256) : (byte)v;} // PERF?: (byte)(v & 0xff)
	public static byte Xto_boolean_byte(boolean v) {
		return v ? Bool_.Y_byte : Bool_.N_byte;
	}
	public static final byte Zero = 0, MaxValue_127 = 127;
}
