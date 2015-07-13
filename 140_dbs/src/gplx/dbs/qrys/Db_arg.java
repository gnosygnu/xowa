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
package gplx.dbs.qrys; import gplx.*; import gplx.dbs.*;
public class Db_arg {
	public Db_arg(String key, Object val) {this.key = key; this.val = val;}
	public String Key() {return key;} private String key;
	public Object Val() {return val;} public void Val_(Object v) {this.val = v;} private Object val;
	public byte Val_tid() {return val_tid;} public Db_arg Val_tid_(byte v) {val_tid = v; return this;} private byte val_tid = Db_val_type.Tid_null;		
}
