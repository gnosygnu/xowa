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
package gplx.xowa.bldrs; import gplx.*; import gplx.xowa.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*;
public class Xob_db_file {
	Xob_db_file(Io_url url, Db_conn conn) {
		this.url = url; this.conn = conn;
		this.tbl__cfg = new Db_cfg_tbl(conn, "xowa_cfg");
	}
	public Io_url			Url()		{return url;} private final Io_url url;
	public Db_conn			Conn()		{return conn;} private final Db_conn conn;
	public Db_cfg_tbl		Tbl__cfg()	{return tbl__cfg;} private final Db_cfg_tbl tbl__cfg;

	public static Xob_db_file new__file_make(Io_url dir)			{return new_(dir, Name__file_make);}
	public static Xob_db_file new__page_regy(Io_url dir)			{return new_(dir, Name__page_regy);}
	public static Xob_db_file new__wiki_image(Io_url dir)			{return new_(dir, Name__wiki_image);}
	public static Xob_db_file new__wiki_redirect(Io_url dir)		{return new_(dir, Name__wiki_redirect);}
	public static Xob_db_file new__temp_log(Io_url dir)				{return new_(dir, Name__temp_log);}
	public static Xob_db_file new_(Io_url dir, String name) {
		Io_url url = dir.GenSubFil(name);
		Db_conn_bldr_data conn_data = Db_conn_bldr.I.Get_or_new(url);
		Db_conn conn = conn_data.Conn();
		Xob_db_file rv = new Xob_db_file(url, conn);
		if (conn_data.Created())
			rv.Tbl__cfg().Create_tbl();
		return rv;
	}
	public static final String 
	  Name__wiki_image = "xowa.wiki.image.sqlite3", Name__wiki_redirect = "xowa.wiki.redirect.sqlite3"
	, Name__file_make = "xowa.file.make.sqlite3", Name__temp_log = "xowa.temp.log.sqlite3"
	, Name__page_regy = "xowa.file.page_regy.sqlite3"
	;
}
