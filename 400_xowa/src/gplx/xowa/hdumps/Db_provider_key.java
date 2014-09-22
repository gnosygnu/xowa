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
package gplx.xowa.hdumps; import gplx.*; import gplx.xowa.*;
import gplx.dbs.*;
public class Db_provider_key {
	public Db_provider_key(String key, Db_conn_info conn_info, GfoInvkAbleCmd init_cmd) {this.key = key; this.conn_info = conn_info; this.init_cmd = init_cmd;}
	public String Key() {return key;} private final String key;
	public Db_conn_info Conn_info() {return conn_info;} private Db_conn_info conn_info;
	public GfoInvkAbleCmd Init_cmd() {return init_cmd;} private GfoInvkAbleCmd init_cmd;
}
