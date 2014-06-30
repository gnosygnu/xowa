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
package gplx.xowa; import gplx.*;
public class Xow_ns_case_ {
	public static final byte Id_all = 0, Id_1st = 1;
	public static byte parse_(String s) {
		if		(String_.Eq(s, "first-letter"))		return Id_1st;
		else if	(String_.Eq(s, "case-sensitive"))	return Id_all;
		else										throw Err_mgr._.unhandled_(s);
	}
}
