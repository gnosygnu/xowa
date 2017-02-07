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
package gplx.xowa.users.bmks; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
public class Xoud_bmk_dir_row {
	public Xoud_bmk_dir_row(int id, int owner, int sort, byte[] name) {
		this.id = id; this.owner = owner; this.sort = sort; this.name = name;
	}
	public int		Id() {return id;} private final int id;
	public int		Owner() {return owner;} private final int owner;
	public int		Sort() {return sort;} private final int sort;
	public byte[]	Name() {return name;} private final byte[] name;
}
