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
package gplx.xowa.dbs; import gplx.*; import gplx.xowa.*;
class Xodb_ns_map_mgr {
	public Xodb_ns_map_itm[] Itms() {return itms;} private Xodb_ns_map_itm[] itms;
	public static Xodb_ns_map_mgr Parse(byte[] src) {
		byte[][] lines = Bry_.Split(src, Byte_ascii.NewLine);
		int lines_len = lines.length;
		Xow_ns_mgr canonical_ns_mgr = Xow_ns_mgr_.default_(gplx.xowa.langs.cases.Xol_case_mgr_.Ascii());
		Xodb_ns_map_mgr rv = new Xodb_ns_map_mgr();
		ListAdp itms = ListAdp_.new_();
		for (int i = 0; i < lines_len; i++) {
			byte[] line = lines[i];
			if (line.length == 0) continue;	// ignore blank lines
			Xodb_ns_map_itm itm = Parse_itm(src, line, canonical_ns_mgr);
			itms.Add(itm);
		}
		rv.itms = (Xodb_ns_map_itm[])itms.XtoAryAndClear(Xodb_ns_map_itm.class);
		return rv;
	}
	private static Xodb_ns_map_itm Parse_itm(byte[] src, byte[] line, Xow_ns_mgr ns_mgr) {
		byte[][] ns_names = Bry_.Split(line, Byte_ascii.Tilde);
		int len = ns_names.length;
		int[] ns_ids = new int[len];
		for (int i = 0; i < len; i++) {
			byte[] ns_name = ns_names[i];
			Xow_ns ns = ns_mgr.Names_get_or_null(ns_name, 0, ns_name.length);
			ns_ids[i] = ns.Id();
		}
		return new Xodb_ns_map_itm(ns_ids);
	}
}
class Xodb_ns_map_itm {
	public Xodb_ns_map_itm(int[] ns_ids) {this.ns_ids = ns_ids;}
	public int[] Ns_ids() {return ns_ids;} private int[] ns_ids;
}
