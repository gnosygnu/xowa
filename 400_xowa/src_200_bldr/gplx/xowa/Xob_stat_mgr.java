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
public class Xob_stat_mgr {
	public Xob_stat_type GetOrNew(byte tid) {
		Xob_stat_type rv = (Xob_stat_type)regy.Fetch(tid);
		if (rv == null) {
			rv = new Xob_stat_type(tid);
			regy.Add(tid, rv);
		}
		return rv;
	}
	public String Print(Xow_ns_mgr nsMgr) {
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < regy.Count(); i++) {
			Xob_stat_type typ = (Xob_stat_type)regy.FetchAt(i);
			sb.Add(String_.PadEnd(Xow_dir_info_.Tid_name(typ.Tid()), 6, " "));
		}
		sb.Add_str_w_crlf("ns");
		String[] nsAry = GetNmsAry(nsMgr);
		for (String ns : nsAry) {
			for (int i = 0; i < regy.Count(); i++) {
				Xob_stat_type typ = (Xob_stat_type)regy.FetchAt(i);
				Xob_stat_itm itm = (Xob_stat_itm)typ.GetOrNew(ns);
				sb.Add(Int_.Xto_str_pad_bgn(itm.Fils, 5)).Add(" ");
			}
			sb.Add_str_w_crlf(ns);
		}
		return sb.XtoStr();
	}
	public String XtoStr() {
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < regy.Count(); i++) {
			Xob_stat_type typ = (Xob_stat_type)regy.FetchAt(i);
			typ.XtoStr(sb);
		}
		return sb.XtoStr();
	}
	String[] GetNmsAry(Xow_ns_mgr nsMgr) {
		OrderedHash nsRegy = OrderedHash_.new_();
		for (int i = 0; i < regy.Count(); i++) {
			Xob_stat_type typ = (Xob_stat_type)regy.FetchAt(i);
			for (int j = 0; j < typ.Count(); j++) {
				Xob_stat_itm itm = (Xob_stat_itm)typ.GetAt(j);
				if (!nsRegy.Has(itm.Ns()))
					nsRegy.AddKeyVal(itm.Ns());
			}
		}
		return (String[])nsRegy.XtoAry(String.class);
	}
	OrderedHash regy = OrderedHash_.new_();
}
