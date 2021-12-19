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
package gplx.dbs;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValHash;
public abstract class Db_conn_info__base implements Db_conn_info {
	public Db_conn_info__base(String raw, String db_api, String database) {this.raw = raw; this.db_api = db_api; this.database = database;}
	public abstract String Key();
	public String Raw()				{return raw;} private final String raw;
	public String Db_api()			{return db_api;} private final String db_api;
	public String Database()		{return database;} protected final String database;
	public abstract Db_conn_info New_self(String raw, KeyValHash hash);

	protected static String Bld_raw(String... ary) {// "a", "b" -> "a=b;"
		BryWtr bfr = BryWtr.NewAndReset(255);
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			String itm = ary[i];
			bfr.AddStrU8(itm);
			bfr.AddByte(i % 2 == 0 ? AsciiByte.Eq : AsciiByte.Semic);
		}
		return bfr.ToStrAndClear();
	}
	protected static String Bld_api(KeyValHash hash, KeyVal... xtn_ary) {
		BryWtr bfr = BryWtr.New();
		int len = hash.Len();
		for (int i = 0; i < len; ++i) {
			KeyVal kv = hash.GetAt(i);
			bfr.AddStrU8Fmt("{0}={1};", kv.KeyToStr(), kv.ValToStrOrEmpty());
		}
		for (KeyVal xtn : xtn_ary) {
			if (hash.Has(xtn.KeyToStr())) continue;
			bfr.AddStrU8Fmt("{0}={1};", xtn.KeyToStr(), xtn.ValToStrOrEmpty());
		}
		return bfr.ToStrAndClear();
	}
}
