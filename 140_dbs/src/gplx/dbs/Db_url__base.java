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
package gplx.dbs; import gplx.*;
public abstract class Db_url__base implements Db_url {
	public abstract String Tid();
	public String Xto_raw() {return raw;} private String raw = "";
	public String Xto_api() {return api;} private String api = "";
	public String Database() {return database;} private String database = "";
	public String Server() {return server;} private String server = "";
	public abstract Db_url New_self(String raw, GfoMsg m);
	protected void Ctor(String server, String database, String raw, String api) {this.server = server; this.database = database; this.raw = raw; this.api = api;}
	protected static String BldApi(GfoMsg m, KeyVal... xtnAry) {
		String_bldr sb = String_bldr_.new_();
		HashAdp hash = HashAdp_.new_();
		for (int i = 0; i < m.Args_count(); i++) {
			KeyVal kv = m.Args_getAt(i);
			sb.Add_fmt("{0}={1};", kv.Key(), kv.Val_to_str_or_empty());
			hash.AddKeyVal(kv.Key());
		}
		for (KeyVal xtn : xtnAry) {
			if (hash.Has(xtn.Key())) continue;
			sb.Add_fmt("{0}={1};", xtn.Key(), xtn.Val_to_str_or_empty());
		}
		return sb.XtoStr();
	}
	protected static String Bld_raw(String... ary) {
		Bry_bfr bfr = Bry_bfr.reset_(255);
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			String itm = ary[i];
			bfr.Add_str_utf8(itm);
			bfr.Add_byte(i % 2 == 0 ? Byte_ascii.Eq : Byte_ascii.Semic);
		}
		return bfr.Xto_str();
	}
}
