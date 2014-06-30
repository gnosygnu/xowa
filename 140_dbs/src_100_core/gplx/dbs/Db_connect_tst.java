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
import org.junit.*;
public class Db_connect_tst {
	@Before public void setup() {
		regy = new Db_connect_pool();
		regy.Add(Db_connect_mock._);
	}	Db_connect_pool regy;
	@Test  public void Parse() {
		tst_Parse("gplx_key=mock;id=1;", kv_("id", "1"));							 // one; gplx_key removed
		tst_Parse("gplx_key=mock;id=1;name=me;", kv_("id", "1"), kv_("name", "me")); // many
		tst_Parse("gplx_key=mock;id=1;name=me" , kv_("id", "1"), kv_("name", "me")); // no semi-colon at end
	}
	KeyVal kv_(String key, Object val) {return KeyVal_.new_(key, val);}
	void tst_Parse(String raw, KeyVal... expd) {
		Db_connect_mock mock = (Db_connect_mock)regy.Parse(raw);
		Tfds.Eq_ary_str(expd, mock.Kvs());
	}
}
class Db_connect_mock extends Db_connect_base {
	public KeyVal[] Kvs() {return kvs;} KeyVal[] kvs;
	@Override public String Key_of_db_connect() {return KeyDef;} public static final String KeyDef = "mock";
	@Override public Db_connect Clone_of_db_connect(String raw, GfoMsg m) {
		Db_connect_mock rv = new Db_connect_mock();
		rv.kvs = new KeyVal[m.Args_count()];
		for (int i = 0; i < m.Args_count(); i++)
			rv.kvs[i] = m.Args_getAt(i);
		return rv;
	}
        public static final Db_connect_mock _ = new Db_connect_mock(); Db_connect_mock() {}
}
