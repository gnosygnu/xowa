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
import gplx.core.strings.*;
public class Xob_stat_itm implements NewAble {
	public String Ns() {return ns;} private String ns;
	public int  Fils;
	public long Size, SizeMax = Int_.MinValue, SizeMin = Int_.MaxValue;
	public int SizeMaxIdx, SizeMinIdx;
	public void Tally(long size, int idx) {
		Fils++;
		Size += size;
		if (size > SizeMax) {SizeMax = size; SizeMaxIdx = idx;}
		if (size < SizeMin) {SizeMin = size; SizeMinIdx = idx;}
	}
	public void XtoStr(String_bldr sb) {
		XtoStr_fld(sb, ns).XtoStr_fld(sb, Fils).XtoStr_fld(sb, Size).XtoStr_fld(sb, SizeMax).XtoStr_fld(sb, SizeMaxIdx).XtoStr_fld(sb, SizeMin);
		sb.Add(Int_.Xto_str(SizeMinIdx));
	}
	Xob_stat_itm XtoStr_fld(String_bldr sb, long v) {sb.Add(Long_.Xto_str(v)).Add(Xob_stat_itm.Dlm); return this;}
	Xob_stat_itm XtoStr_fld(String_bldr sb, int v) {sb.Add(Int_.Xto_str(v)).Add(Xob_stat_itm.Dlm); return this;}
	Xob_stat_itm XtoStr_fld(String_bldr sb, String v) {sb.Add(v).Add(Xob_stat_itm.Dlm); return this;}
	public Xob_stat_itm(String ns) {
		this.ns = ns;
	}
	public Object NewByKey(Object o) {return new Xob_stat_itm((String)o);} public static final Xob_stat_itm _ = new Xob_stat_itm(); Xob_stat_itm() {}
	public static final char Dlm = '|';
}
