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
package gplx.fsdb; import gplx.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*;
public class Fsdb_db_file {
	public Fsdb_db_file(Io_url url, Db_conn conn) {
		this.url = url; this.conn = conn;
		this.tbl__core_cfg = gplx.xowa.wikis.data.Xowd_cfg_tbl_.New(conn);
	}
	public Io_url				Url()			{return url;}				private final    Io_url url;
	public Db_conn				Conn()			{return conn;}				private final    Db_conn conn;		
	public Db_cfg_tbl			Tbl__cfg()		{return tbl__core_cfg;}		private final    Db_cfg_tbl	tbl__core_cfg;
}
