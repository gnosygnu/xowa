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
	public boolean Tid__v2__bld()		{return tid == TID__v2__bld;}
	public void Tid__v2__bld__y_()	{tid = TID__v2__bld;}
	public void Tid__v2__mp__y_()	{tid = TID__v2__mp;}
	public boolean Tid__bld()			{return tid > TID__v2__gui;}
	private static final int
	  TID__v0			= 1
	, TID__v2__gui		= 2
	, TID__v2__bld		= 3
	, TID__v2__mp		= 4
	;
	public static Xof_fsdb_mode New__v0()		{return new Xof_fsdb_mode(TID__v0);}
	public static Xof_fsdb_mode New__v2__gui()	{return new Xof_fsdb_mode(TID__v2__gui);}
}
