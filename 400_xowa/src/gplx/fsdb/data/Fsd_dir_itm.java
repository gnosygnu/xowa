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
package gplx.fsdb.data; import gplx.*; import gplx.fsdb.*;
public class Fsd_dir_itm {
	public Fsd_dir_itm(int id, int owner, String name) {this.id = id; this.owner = owner; this.name = name;}
	public int Id() {return id;} private final int id;
	public int Owner() {return owner;} private final int owner;
	public String Name() {return name;} private final String name;
	public static final Fsd_dir_itm Null = new Fsd_dir_itm(0, 0, "");
}
