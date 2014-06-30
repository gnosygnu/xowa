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
public class Fsdb_mnt_itm {
	public Fsdb_mnt_itm(int id, String name, String url) {this.id = id; this.name = name; this.url = url;}
	public int Id() {return id;} public Fsdb_mnt_itm Id_(int v) {id = v; return this;} private int id;
	public String Name() {return name;} public Fsdb_mnt_itm Name_(String v) {name = v; return this;} private String name;
	public String Url() {return url;} public Fsdb_mnt_itm Url_(String v) {url = v; return this;} private String url;
	public static final Fsdb_mnt_itm Null = new Fsdb_mnt_itm(0, "", "");
	public static final Fsdb_mnt_itm[] Ary_empty = new Fsdb_mnt_itm[0];
}
