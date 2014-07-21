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
	public static final byte Create = 1, Update = 2, Delete = 3, Ignore = 4;
	public static byte Xto_update(byte cur) {
		switch (cur) {
			case Create:					// ignore update if item is already marked for create
			case Delete:					// ignore update if item is already marked for delete (might want to throw error)
							return cur;		
			case Ignore:					// must mark for update
			case Update:					// return self
							return Update;
			default:		throw Err_.unhandled(cur);
		}
	}
}	