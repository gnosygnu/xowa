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
package gplx.texts; import gplx.*;
public class RegxAdp_ {
	public static RegxAdp_mpo_find Find_args(String input, String find) {return new RegxAdp_mpo_find().Input_(input).Find_(find);}
	public static RegxAdp_mpo_replace Replace_args(String input, String find, String replace) {return new RegxAdp_mpo_replace().Input_(input).Find_(find).Replace_(replace);}
	public static RegxAdp new_(String pattern) {return new RegxAdp(pattern);}
	public static String Replace(String raw, String regx, String replace) {return Replace_args(raw, regx, replace).Exec_asStr();}
	public static boolean Match(String input, String pattern) {
		RegxAdp rv = new RegxAdp(pattern);
		return rv.Match(input, 0).Rslt();
	}
}
