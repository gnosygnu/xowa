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
	public boolean Tid_unknown()		{return tid == Tid_int_unknown;}
	public boolean Tid_wmf()			{return tid == Tid_int_wmf;}
	public boolean Tid_view()			{return tid == Tid_int_view;}
	public boolean Tid_make()			{return tid == Tid_int_make;}
	public void Tid_view_y_()		{tid = Tid_int_view;}
	public void Tid_make_y_()		{tid = Tid_int_make;}
	private static final int
	  Tid_int_unknown	= 0
	, Tid_int_wmf		= 1
	, Tid_int_view		= 2
	, Tid_int_make		= 3
	;
//		public static Xof_fsdb_mode new_unknown()	{return new Xof_fsdb_mode(Tid_int_unknown);}
	public static Xof_fsdb_mode new_wmf()		{return new Xof_fsdb_mode(Tid_int_wmf);}
	public static Xof_fsdb_mode new_view()		{return new Xof_fsdb_mode(Tid_int_view);}
}
