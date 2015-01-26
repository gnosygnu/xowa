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
public interface Db_url {
	String Tid();
	String Xto_raw();
	String Xto_api();
	Db_url New_self(String raw, GfoMsg m);
}
class Db_url__null extends Db_url__base {
	@Override public String Tid() {return Tid_const;} public static final String Tid_const = "null_db";
	@Override public Db_url New_self(String raw, GfoMsg m) {return this;}
	public static final Db_url__null _ = new Db_url__null(); Db_url__null() {this.Ctor("", "", "gplx_key=null_db", "");}
}
class Db_url__mysql extends Db_url__base {
	@Override public String Tid() {return Tid_const;} public static final String Tid_const = "mysql";
	public String Uid() {return uid;} private String uid;
	public String Pwd() {return pwd;} private String pwd;
	public static Db_url new_(String server, String database, String uid, String pwd) {
		return Db_url_.parse_(Bld_raw
		( "gplx_key", Tid_const
		, "server", server
		, "database", database
		, "uid", uid
		, "pwd", pwd
		, "charset", "utf8"
		));
	}
	@Override public Db_url New_self(String raw, GfoMsg m) {
		Db_url__mysql rv = new Db_url__mysql();
		rv.Ctor(m.ReadStr("server"), m.ReadStr("database"), raw, BldApi(m, KeyVal_.new_("charset", "utf8")));
		rv.uid = m.ReadStr("uid");
		rv.pwd = m.ReadStr("pwd");
		return rv;
	}
        public static final Db_url__mysql _ = new Db_url__mysql(); Db_url__mysql() {}
}
class Db_url__postgres extends Db_url__base {
	@Override public String Tid() {return Tid_const;} public static final String Tid_const = "postgresql";
	public String Uid() {return uid;} private String uid;
	public String Pwd() {return pwd;} private String pwd;
	public static Db_url new_(String server, String database, String uid, String pwd) {
		return Db_url_.parse_(Bld_raw
		( "gplx_key", Tid_const
		, "server", server
		, "database", database
		, "port", "5432"
		, "user id", uid
		, "password", pwd
		, "encoding", "unicode"	// needed for 1.1 conn; otherwise, ascii
		));
	}
	@Override public Db_url New_self(String raw, GfoMsg m) {
		Db_url__postgres rv = new Db_url__postgres();
		rv.Ctor(m.ReadStr("server"), m.ReadStr("database"), raw, BldApi(m, KeyVal_.new_("encoding", "unicode")));
		rv.uid = m.ReadStr("user id");
		rv.pwd = m.ReadStr("password");
		return rv;
	}
        public static final Db_url__postgres _ = new Db_url__postgres(); Db_url__postgres() {}
}
class Db_url__tdb extends Db_url__base {
	public Io_url Url() {return url;} Io_url url;
	@Override public String Tid() {return Tid_const;} public static final String Tid_const = "tdb";
        public static Db_url new_(Io_url url) {
		return Db_url_.parse_(Bld_raw
		( "gplx_key", Tid_const
		, "url", url.Raw()
		));
	}	Db_url__tdb() {}
	@Override public Db_url New_self(String raw, GfoMsg m) {
		Db_url__tdb rv = new Db_url__tdb();
		String urlStr = m.ReadStr("url");
		Io_url url = Io_url_.new_any_(urlStr);
		rv.Ctor(urlStr, url.NameOnly(), raw, BldApi(m));
		rv.url = url;
		return rv;
	}
        public static final Db_url__tdb _ = new Db_url__tdb();
}
