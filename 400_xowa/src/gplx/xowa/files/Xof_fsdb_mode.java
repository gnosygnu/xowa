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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
public class Xof_fsdb_mode {
	private int tid;
	Xof_fsdb_mode(int tid) {this.tid = tid;}
//		public boolean Tid_v0()			{return tid == Tid_int_v0;}
	public boolean Tid_v2_gui()		{return tid == Tid_int_v2_gui;}
	public boolean Tid_v2_bld()		{return tid == Tid_int_v2_bld;}
	public void Tid_v2_bld_y_()		{tid = Tid_int_v2_bld;}
	private static final int
	  Tid_int_v0		= 1
	, Tid_int_v2_gui	= 2
	, Tid_int_v2_bld	= 3
	;
	public static Xof_fsdb_mode new_v0()		{return new Xof_fsdb_mode(Tid_int_v0);}
	public static Xof_fsdb_mode new_v2_gui()	{return new Xof_fsdb_mode(Tid_int_v2_gui);}
}
