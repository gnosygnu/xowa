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
package gplx.dbs; import gplx.*;
public class Db_fld {
	public Db_fld(String name, byte type_tid) {this.name = name; this.type_tid = type_tid;}
	public String Name() {return name;} public Db_fld Name_(String v) {name = v; return this;} private String name;
	public byte Type_tid() {return type_tid;} public Db_fld Type_tid_(byte v) {type_tid = v; return this;} private byte type_tid;
}
