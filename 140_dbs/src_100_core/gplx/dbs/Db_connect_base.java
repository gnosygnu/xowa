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
public abstract class Db_connect_base implements Db_connect {
	public abstract String Key_of_db_connect();
	public String Database() {return database;} public Db_connect_base Database_(String v) {database = v; return this;} private String database = "";
	public String Server() {return server;} public Db_connect_base Server_(String v) {server = v; return this;} private String server = "";
	public String Raw_of_db_connect() {return raw;} public Db_connect XtoStr_raw_(String v) {raw = v; return this;} private String raw = "";
	public String Api_of_db_connect() {return api;} public Db_connect XtoStr_std_(String v) {api = v; return this;} private String api = "";
	public abstract Db_connect Clone_of_db_connect(String raw, GfoMsg m);
	protected void Ctor_of_db_connect(String server, String database, String raw, String api) {this.server = server; this.database = database; this.raw = raw; this.api = api;}
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
	protected static String BldRaw(GfoMsg m) {
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < m.Args_count(); i++) {
			KeyVal itm = m.Args_getAt(i);
			sb.Add_fmt("{0}={1};", itm.Key(), itm.Val_to_str_or_empty());
		}
		return sb.XtoStr();
	}
}
