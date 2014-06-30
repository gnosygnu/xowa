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
package gplx.xowa.users.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.dbs.*;
class Xou_db_xtn_itm {
	public String Key() {return key;} private String key;
	public String Version() {return version;} private String version;
	public byte Cmd_mode() {return cmd_mode;} public Xou_db_xtn_itm Cmd_mode_(byte v) {cmd_mode = v; return this;} private byte cmd_mode;
	public Xou_db_xtn_itm Init_by_load(DataRdr rdr) {
		key = rdr.ReadStr(Xou_db_xtn_tbl.Fld_xtn_key);
		version = rdr.ReadStr(Xou_db_xtn_tbl.Fld_xtn_version);
		cmd_mode = Db_cmd_mode.Ignore;
		return this;
	}
	public Xou_db_xtn_itm Init_by_make(String key, String version) {
		this.key = key;
		this.version = version;
		cmd_mode = Db_cmd_mode.Create;
		return this;
	}
	public static final Xou_db_xtn_itm Null = new Xou_db_xtn_itm(); public Xou_db_xtn_itm() {}
}
