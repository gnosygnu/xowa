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
package gplx.xowa.bldrs.oimgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.core.primitives.*;
import gplx.dbs.*; import gplx.dbs.engines.sqlite.*; import gplx.xowa.dbs.*; import gplx.xowa.dbs.tbls.*;
public class Xodb_db_file {
	Xodb_db_file(Io_url url, Db_conn conn, boolean created) {this.url = url; this.conn = conn; this.created = created;}
	public Io_url Url() {return url;} private Io_url url;
	public Db_conn Conn() {return conn;} private Db_conn conn;
	public boolean Created() {return created;} public void Created_clear() {created = false;} private boolean created;

	public static Xodb_db_file init__file_make(Io_url dir)			{return init_(dir, Name__file_make);}
	public static Xodb_db_file init__page_regy(Io_url dir)			{return init_(dir, Name__page_regy);}
	public static Xodb_db_file init__wiki_image(Io_url dir)			{return init_(dir, Name__wiki_image);}
	public static Xodb_db_file init__wiki_redirect(Io_url dir)		{return init_(dir, Name__wiki_redirect);}
	public static Xodb_db_file init__temp_log(Io_url dir)			{return init_(dir, Name__temp_log);}
	public static Xodb_db_file init_(Io_url dir, String name) {
		Io_url url = dir.GenSubFil(name);
		Bool_obj_ref created = Bool_obj_ref.n_();
		Db_conn conn = Sqlite_engine_.Conn_load_or_make_(url, created);
		if (created.Val()) {	// always create cfg table
			Xodb_xowa_cfg_tbl.Create_table(conn);
			Xodb_xowa_cfg_tbl.Create_index(conn);
		}
		return new Xodb_db_file(url, conn, created.Val());
	}
	public static final String 
	  Name__wiki_image = "xowa.wiki.image.sqlite3", Name__wiki_redirect = "xowa.wiki.redirect.sqlite3"
	, Name__file_make = "xowa.file.make.sqlite3", Name__temp_log = "xowa.temp.log.sqlite3"
	, Name__page_regy = "xowa.file.page_regy.sqlite3"
	;
}
