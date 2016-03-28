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
package gplx.xowa.langs.durations; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
public class Xol_interval_itm {
	public Xol_interval_itm(Xol_duration_itm duration_itm, long val) {this.duration_itm = duration_itm; this.val = val;}
	public Xol_duration_itm Duration_itm() {return duration_itm;} private Xol_duration_itm duration_itm;
	public long Val() {return val;} private long val;
	public static Keyval[] Xto_kv_ary(Xol_interval_itm[] ary) {
		int len = ary.length;
		Keyval[] rv = new Keyval[len];
		for (int i = 0; i < len; i++) {
			Xol_interval_itm itm = ary[i];
			rv[i] = Keyval_.new_(itm.Duration_itm().Name_str(), (int)itm.Val());	// double for scribunto
		}
		return rv;
	}
}
