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
public class Db_cmd_mode {
	Db_cmd_mode(int val) {this.val = val;}
	public int Val() {return val;} int val;
	public Db_cmd_mode MarkUpdated() {return this == Retrieved ? Updated : this;} // Created/Deleted noops
	public boolean Modified() {return this == Created || this == Updated;}
	public static final byte Tid_create = 1, Tid_update = 2, Tid_delete = 3, Tid_ignore = 4;
	public static final Db_cmd_mode
	  Created		= new Db_cmd_mode(Tid_create)
	, Updated		= new Db_cmd_mode(Tid_update)
	, Deleted		= new Db_cmd_mode(Tid_delete)
	, Retrieved		= new Db_cmd_mode(Tid_ignore)
	;
	public static byte To_update(byte cur) {
		switch (cur) {
			case Tid_create:					// ignore update if item is already marked for create
			case Tid_delete:					// ignore update if item is already marked for delete (might want to throw error)
							return cur;		
			case Tid_ignore:					// must mark for update
			case Tid_update:					// return self
							return Tid_update;
			default:		throw Exc_.new_unhandled(cur);
		}
	}
}
