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
package gplx.xowa.addons.apps.cfgs.enums; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*;
public class Xoitm_db_tid {
	public static final int Tid__bool = 1, Tid__int = 2, Tid__str = 3, Tid__memo = 4;
	public static int To_int(String s) {
		if		(String_.Eq(s, "bool"))		return Tid__bool;
		else if	(String_.Eq(s, "int"))			return Tid__int;
		else if	(String_.Eq(s, "string"))	return Tid__str;
		else if	(String_.Eq(s, "memo"))			return Tid__memo;
		else									return Tid__str;
//			else throw Err_.new_wo_type("xo.cfg_maint:unknown db_type", "db_type", s);
	}
}
