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
package gplx.xowa2.files; import gplx.*; import gplx.xowa2.*;
import gplx.xowa.files.*;
public class Xou_cache_dir {
	public Xou_cache_dir(int dir_id, byte[] dir_name) {
		this.dir_id = dir_id; this.dir_name = dir_name;
	}
	public int Dir_id() {return dir_id;} private final int dir_id;
	public byte[] Dir_name() {return dir_name;} private final byte[] dir_name;
	public byte Cmd_mode() {return cmd_mode;} public void Cmd_mode_(byte v) {cmd_mode = v;} private byte cmd_mode = gplx.dbs.Db_cmd_mode.Ignore;
}
