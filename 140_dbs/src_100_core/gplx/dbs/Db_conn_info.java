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
public interface Db_conn_info {
	String Key();
	String Server();
	String Database();
	String Str_raw();
	String Str_api();
	Db_conn_info Make_new(String raw, GfoMsg m);
}
class Db_conn_info__null extends Db_conn_info__base {
	@Override public String Key() {return Key_const;} public static final String Key_const = "null_db";
	@Override public Db_conn_info Make_new(String raw, GfoMsg m) {return this;}
	public static final Db_conn_info__null _ = new Db_conn_info__null(); Db_conn_info__null() {this.Ctor_of_db_connect("", "", "gplx_key=null_db", "");}
}
class Db_conn_info__mysql extends Db_conn_info__base {
	@Override public String Key() {return Key_const;} public static final String Key_const = "mysql";
	public String Uid() {return uid;} private String uid;
	public String Pwd() {return pwd;} private String pwd;
	public static Db_conn_info new_(String server, String database, String uid, String pwd) {
		return Db_conn_info_.parse_(BldRaw(GfoMsg_.new_cast_("Db_conn_info")
			.Add("gplx_key", Key_const)
			.Add("server", server)
			.Add("database", database)
			.Add("uid", uid)
			.Add("pwd", pwd)
			.Add("charset", "utf8")
			));
	}
	@Override public Db_conn_info Make_new(String raw, GfoMsg m) {
		Db_conn_info__mysql rv = new Db_conn_info__mysql();
		rv.Ctor_of_db_connect(m.ReadStr("server"), m.ReadStr("database"), raw, BldApi(m, KeyVal_.new_("charset", "utf8")));
		rv.uid = m.ReadStr("uid");
		rv.pwd = m.ReadStr("pwd");
		return rv;
	}
        public static final Db_conn_info__mysql _ = new Db_conn_info__mysql(); Db_conn_info__mysql() {}
}
class Db_conn_info__postgres extends Db_conn_info__base {
	@Override public String Key() {return Key_const;} public static final String Key_const = "postgresql";
	public String Uid() {return uid;} private String uid;
	public String Pwd() {return pwd;} private String pwd;
	public static Db_conn_info new_(String server, String database, String uid, String pwd) {
		return Db_conn_info_.parse_(BldRaw(GfoMsg_.new_cast_("Db_conn_info")
			.Add("gplx_key", Key_const)
			.Add("server", server)
			.Add("database", database)
			.Add("port", 5432)
			.Add("user id", uid)
			.Add("password", pwd)
			.Add("encoding", "unicode")	// needed for 1.1 provider; otherwise, ascii
			));
	}
	@Override public Db_conn_info Make_new(String raw, GfoMsg m) {
		Db_conn_info__postgres rv = new Db_conn_info__postgres();
		rv.Ctor_of_db_connect(m.ReadStr("server"), m.ReadStr("database"), raw, BldApi(m, KeyVal_.new_("encoding", "unicode")));
		rv.uid = m.ReadStr("user id");
		rv.pwd = m.ReadStr("password");
		return rv;
	}
        public static final Db_conn_info__postgres _ = new Db_conn_info__postgres(); Db_conn_info__postgres() {}
}
class Db_conn_info__tdb extends Db_conn_info__base {
	public Io_url Url() {return url;} Io_url url;
	@Override public String Key() {return Key_const;} public static final String Key_const = "tdb";
        public static Db_conn_info new_(Io_url url) {
		return Db_conn_info_.parse_(BldRaw(GfoMsg_.new_cast_("Db_conn_info")
			.Add("gplx_key", Key_const)
			.Add("url", url.Raw())
			));
	}	Db_conn_info__tdb() {}
	@Override public Db_conn_info Make_new(String raw, GfoMsg m) {
		Db_conn_info__tdb rv = new Db_conn_info__tdb();
		String urlStr = m.ReadStr("url");
		Io_url url = Io_url_.new_any_(urlStr);
		rv.Ctor_of_db_connect(urlStr, url.NameOnly(), raw, BldApi(m));
		rv.url = url;
		return rv;
	}
        public static final Db_conn_info__tdb _ = new Db_conn_info__tdb();
}
