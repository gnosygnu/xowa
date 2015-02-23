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
package gplx.dbs.engines.mysql; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
public class Mysql_url extends Db_url__base {
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
		Mysql_url rv = new Mysql_url();
		rv.Ctor(m.ReadStr("server"), m.ReadStr("database"), raw, BldApi(m, KeyVal_.new_("charset", "utf8")));
		rv.uid = m.ReadStr("uid");
		rv.pwd = m.ReadStr("pwd");
		return rv;
	}
        public static final Mysql_url _ = new Mysql_url(); Mysql_url() {}
}
