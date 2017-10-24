/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.dbs; import gplx.*;
public abstract class Db_conn_info__base implements Db_conn_info {
	public Db_conn_info__base(String raw, String db_api, String database) {this.raw = raw; this.db_api = db_api; this.database = database;}
	public abstract String Key();
	public String Raw()				{return raw;} private final    String raw;
	public String Db_api()			{return db_api;} private final    String db_api;
	public String Database()		{return database;} protected final    String database;
	public abstract Db_conn_info New_self(String raw, Keyval_hash hash);

	protected static String Bld_raw(String... ary) {// "a", "b" -> "a=b;"
		Bry_bfr bfr = Bry_bfr_.Reset(255);
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			String itm = ary[i];
			bfr.Add_str_u8(itm);
			bfr.Add_byte(i % 2 == 0 ? Byte_ascii.Eq : Byte_ascii.Semic);
		}
		return bfr.To_str_and_clear();
	}
	protected static String Bld_api(Keyval_hash hash, Keyval... xtn_ary) {
		Bry_bfr bfr = Bry_bfr_.New();
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
