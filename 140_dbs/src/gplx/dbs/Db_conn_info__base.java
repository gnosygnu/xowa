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
public abstract class Db_conn_info__base implements Db_conn_info {
	public Db_conn_info__base(String raw, String db_api, String database) {this.raw = raw; this.db_api = db_api; this.database = database;}
	public abstract String Key();
	public String Raw()				{return raw;} private final String raw;
	public String Db_api()			{return db_api;} private final String db_api;
	public String Database()		{return database;} protected final String database;
	public abstract Db_conn_info New_self(String raw, Keyval_hash hash);

	protected static String Bld_raw(String... ary) {// "a", "b" -> "a=b;"
		Bry_bfr bfr = Bry_bfr.reset_(255);
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			String itm = ary[i];
			bfr.Add_str_u8(itm);
			bfr.Add_byte(i % 2 == 0 ? Byte_ascii.Eq : Byte_ascii.Semic);
		}
		return bfr.To_str_and_clear();
	}
	protected static String Bld_api(Keyval_hash hash, Keyval... xtn_ary) {
		Bry_bfr bfr = Bry_bfr.new_();
		int len = hash.Count();
		for (int i = 0; i < len; ++i) {
			Keyval kv = hash.Get_at(i);
			bfr.Add_str_u8_fmt("{0}={1};", kv.Key(), kv.Val_to_str_or_empty());
		}
		for (Keyval xtn : xtn_ary) {
			if (hash.Has(xtn.Key())) continue;
			bfr.Add_str_u8_fmt("{0}={1};", xtn.Key(), xtn.Val_to_str_or_empty());
		}
		return bfr.To_str_and_clear();
	}
}
