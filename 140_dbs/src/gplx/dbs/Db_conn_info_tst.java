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
import gplx.frameworks.tests.GfoTstr;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValHash;
import org.junit.Before;
import org.junit.Test;
public class Db_conn_info_tst {
	@Before public void setup() {
		regy.Add(Db_conn_info_mock.Instance);
	}	private final Db_conn_info_pool regy = new Db_conn_info_pool();
	@Test public void Parse() {
		tst_Parse("gplx_key=mock;id=1;", kv_("id", "1"));							 // one; gplx_key removed
		tst_Parse("gplx_key=mock;id=1;name=me;", kv_("id", "1"), kv_("name", "me")); // many
		tst_Parse("gplx_key=mock;id=1;name=me" , kv_("id", "1"), kv_("name", "me")); // no semi-colon at end
	}
	private KeyVal kv_(String key, Object val) {return KeyVal.NewStr(key, val);}
	private void tst_Parse(String raw, KeyVal... expd) {
		Db_conn_info_mock mock = (Db_conn_info_mock)regy.Parse(raw);
		GfoTstr.EqAryObjAry(expd, mock.Kvs());
	}
}
class Db_conn_info_mock extends Db_conn_info__base {
	public Db_conn_info_mock(String raw, String db_api, String database) {super(raw, db_api, database);}
	public KeyVal[] Kvs() {return kvs;} KeyVal[] kvs;
	@Override public String Key() {return Tid_const;} public static final String Tid_const = "mock";
	@Override public Db_conn_info New_self(String raw, KeyValHash hash) {
		Db_conn_info_mock rv = new Db_conn_info_mock("", "", "");
		int len = hash.Len();
		rv.kvs = new KeyVal[len];
		for (int i = 0; i < len; ++i)
			rv.kvs[i] = hash.GetAt(i);
		return rv;
	}
	public static final Db_conn_info_mock Instance = new Db_conn_info_mock("", "", "");
}
