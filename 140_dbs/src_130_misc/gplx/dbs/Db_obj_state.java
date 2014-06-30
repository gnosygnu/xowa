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
public class Db_obj_state {
	public int Val() {return val;} int val;
	public Db_obj_state MarkUpdated() {return this == Retrieved ? Updated : this;} // Created/Deleted noops
	public boolean Modified() {return this == Created || this == Updated;}
	Db_obj_state(int val) {this.val = val;}
	public static final Db_obj_state
		    Created		= new Db_obj_state(1)
		,	Retrieved	= new Db_obj_state(2)
		,	Updated		= new Db_obj_state(3)
		,	Deleted		= new Db_obj_state(4)
		;
}
