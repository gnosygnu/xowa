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
package gplx.gfui; import gplx.*;
public class Gfui_mnu_itm_ {
	public static String Gen_uid() {return "mnu_" + Int_.To_str(++uid_next);} private static int uid_next = 0;
	public static final int Tid_nil = 0, Tid_grp = 1, Tid_spr = 2, Tid_btn = 3, Tid_chk = 4, Tid_rdo = 5;
}
