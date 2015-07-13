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
package gplx.xowa.files.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.dbs.*;
public class Xofc_dir_itm {
	public Xofc_dir_itm(int id, byte[] name, byte cmd_mode) {
		this.id = id;
		this.name = name;
		this.cmd_mode = cmd_mode;
	}
	public int Id() {return id;} public void Id_(int v) {id = v;} private int id;
	public byte[] Name() {return name;} private final byte[] name;
	public byte Cmd_mode() {return cmd_mode;} public Xofc_dir_itm Cmd_mode_(byte v) {cmd_mode = v; return this;} private byte cmd_mode;
	public static final Xofc_dir_itm Null = null;
}
