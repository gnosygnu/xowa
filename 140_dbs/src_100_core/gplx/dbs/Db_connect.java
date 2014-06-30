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
public interface Db_connect {
	String Key_of_db_connect();
	String Server();
	String Database();
	String Raw_of_db_connect();
	String Api_of_db_connect();
	Db_connect Clone_of_db_connect(String raw, GfoMsg m);
}
class Db_connect_null extends Db_connect_base {
	@Override public String Key_of_db_connect() {return KeyDef;} public static final String KeyDef = "null_db";
	@Override public Db_connect Clone_of_db_connect(String raw, GfoMsg m) {return this;}
	public static final Db_connect_null _ = new Db_connect_null(); Db_connect_null() {this.Ctor_of_db_connect("", "", "gplx_key=null_db", "");}
}
class Db_connect_mysql extends Db_connect_base {
	public String Uid() {return uid;} private String uid;
	public String Pwd() {return pwd;} private String pwd;
	@Override public String Key_of_db_connect() {return KeyDef;} public static final String KeyDef = "mysql";
	public static Db_connect new_(String server, String database, String uid, String pwd) {
		return Db_connect_.parse_(BldRaw(GfoMsg_.new_cast_("Db_connect")
			.Add("gplx_key", KeyDef)
			.Add("server", server)
			.Add("database", database)
			.Add("uid", uid)
			.Add("pwd", pwd)
			.Add("charset", "utf8")
			));
	}
	@Override public Db_connect Clone_of_db_connect(String raw, GfoMsg m) {
		Db_connect_mysql rv = new Db_connect_mysql();
		rv.Ctor_of_db_connect(m.ReadStr("server"), m.ReadStr("database"), raw, BldApi(m, KeyVal_.new_("charset", "utf8")));
		rv.uid = m.ReadStr("uid");
		rv.pwd = m.ReadStr("pwd");
		return rv;
	}
        public static final Db_connect_mysql _ = new Db_connect_mysql(); Db_connect_mysql() {}
}
class Db_connect_postgres extends Db_connect_base {
	public String Uid() {return uid;} private String uid;
	public String Pwd() {return pwd;} private String pwd;
	@Override public String Key_of_db_connect() {return KeyDef;} public static final String KeyDef = "postgresql";
	public static Db_connect new_(String server, String database, String uid, String pwd) {
		return Db_connect_.parse_(BldRaw(GfoMsg_.new_cast_("Db_connect")
			.Add("gplx_key", KeyDef)
			.Add("server", server)
			.Add("database", database)
			.Add("port", 5432)
			.Add("user id", uid)
			.Add("password", pwd)
			.Add("encoding", "unicode")	// needed for 1.1 provider; otherwise, ascii
			));
	}
	@Override public Db_connect Clone_of_db_connect(String raw, GfoMsg m) {
		Db_connect_postgres rv = new Db_connect_postgres();
		rv.Ctor_of_db_connect(m.ReadStr("server"), m.ReadStr("database"), raw, BldApi(m, KeyVal_.new_("encoding", "unicode")));
		rv.uid = m.ReadStr("user id");
		rv.pwd = m.ReadStr("password");
		return rv;
	}
        public static final Db_connect_postgres _ = new Db_connect_postgres(); Db_connect_postgres() {}
}
class Db_connect_tdb extends Db_connect_base {
	public Io_url Url() {return url;} Io_url url;
	@Override public String Key_of_db_connect() {return KeyDef;} public static final String KeyDef = "tdb";
        public static Db_connect new_(Io_url url) {
		return Db_connect_.parse_(BldRaw(GfoMsg_.new_cast_("Db_connect")
			.Add("gplx_key", KeyDef)
			.Add("url", url.Raw())
			));
	}	Db_connect_tdb() {}
	@Override public Db_connect Clone_of_db_connect(String raw, GfoMsg m) {
		Db_connect_tdb rv = new Db_connect_tdb();
		String urlStr = m.ReadStr("url");
		Io_url url = Io_url_.new_any_(urlStr);
		rv.Ctor_of_db_connect(urlStr, url.NameOnly(), raw, BldApi(m));
		rv.url = url;
		return rv;
	}
        public static final Db_connect_tdb _ = new Db_connect_tdb();
}
