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
public class Bry_fmtr_itm {
	public boolean Arg;
	public int ArgIdx;
	public byte[] Dat;
	public String DatStr() {
		if (datStr == null) datStr = String_.new_utf8_(Dat);
		return datStr;
	}	String datStr;
	public static Bry_fmtr_itm arg_(int idx) {return new Bry_fmtr_itm(true, idx, Bry_.Empty);}
	public static Bry_fmtr_itm dat_(byte[] dat, int len) {return new Bry_fmtr_itm(false, -1, Bry_.Mid(dat, 0, len));}
	public static Bry_fmtr_itm dat_bry_(byte[] bry) {return new Bry_fmtr_itm(false, -1, bry);}
	Bry_fmtr_itm(boolean arg, int argIdx, byte[] dat) {
		this.Arg = arg; this.ArgIdx = argIdx; this.Dat = dat;
	}
}
