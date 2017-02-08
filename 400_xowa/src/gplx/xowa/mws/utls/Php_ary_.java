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
package gplx.xowa.mws.utls; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
public class Php_ary_ {
	public static boolean Pop_bool_or_n(List_adp list)           {return (boolean)List_adp_.Pop_or(list, false);}
	public static byte[] Pop_bry_or_null(List_adp list)       {return (byte[])List_adp_.Pop_or(list, null);}
}
