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
package gplx.fsdb.meta; import gplx.*; import gplx.fsdb.*;
public class Fsm_id_itm {
	public Fsm_id_itm(String key, int id, int version) {this.key = key; this.id = id; this.version = version;}
	public String Key() {return key;} private final String key;
	public int Id() {return id;} private final int id;
	public int Version() {return version;} private final int version;
}
