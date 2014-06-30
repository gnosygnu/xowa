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
public class Bry_obj_ref {
	public byte[] Val() {return val;} public Bry_obj_ref Val_(byte[] v) {val = v; return this;} private byte[] val;
	@Override public int hashCode() {return CalcHashCode(val, 0, val.length);}
	@Override public boolean equals(Object obj) {return obj == null ? false : Bry_.Eq(val, ((Bry_obj_ref)obj).Val());}	// NOTE: strange, but null check needed; throws null error; EX.WP: File:Eug�ne Delacroix - La libert� guidant le peuple.jpg
	public static int CalcHashCode(byte[] ary, int bgn, int end) {
		int rv = 0;
		for (int i = bgn; i < end; i++)
			rv = (31 * rv) + ary[i];
		return rv;
	}
	public static Bry_obj_ref null_() {return new_(null);}
        public static Bry_obj_ref new_(byte[] val) {
		Bry_obj_ref rv = new Bry_obj_ref();
		rv.val = val;
		return rv;
	}	private Bry_obj_ref() {}
}
