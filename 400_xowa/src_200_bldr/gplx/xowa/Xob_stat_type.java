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
import gplx.core.strings.*; import gplx.xowa.tdbs.*;
public class Xob_stat_type {
	public byte Tid() {return tid;} private byte tid;
	public Xob_stat_type(byte tid) {this.tid = tid;}
	public Xob_stat_itm GetOrNew(String ns) {return (Xob_stat_itm)regy.Get_by_or_new(ns, Xob_stat_itm._);}
	public Xob_stat_itm GetAt(int i) {return (Xob_stat_itm)regy.Get_at(i);}
	public int Count() {return regy.Count();}
	public void XtoStr(String_bldr sb) {
		for (int i = 0; i < regy.Count(); i++) {
			Xob_stat_itm itm = (Xob_stat_itm)regy.Get_at(i);
			sb.Add(Xotdb_dir_info_.Tid_name(tid)).Add(Xob_stat_itm.Dlm);
			itm.XtoStr(sb);
			sb.Add(Byte_ascii.NewLine);
		}
	}
	Ordered_hash regy = Ordered_hash_.new_();
	public static final Xob_stat_type _ = new Xob_stat_type(); Xob_stat_type() {}
}
