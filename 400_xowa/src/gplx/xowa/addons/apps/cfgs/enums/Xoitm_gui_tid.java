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
public class Xoitm_gui_tid {	// SERIALIZED
	public static final int Tid__checkbox = 1, Tid__numeric = 2, Tid__select = 3, Tid__textbox = 4, Tid__fs_file = 5, Tid__fs_dir = 6, Tid__memo = 7, Tid__button = 8, Tid__label = 9;
	public static final String 
	  Str__checkbox		= "checkbox"
	, Str__numeric		= "numeric"
	, Str__select		= "select"
	, Str__textbox		= "textbox"
	, Str__fs_file		= "fs_file"
	, Str__fs_dir		= "fs_dir"
	, Str__memo			= "memo"
	, Str__button		= "button"
	, Str__label		= "label"
	;

	public static int To_tid(String str) {
		if		(String_.Eq(str, Str__checkbox))	return Tid__checkbox;
		else if	(String_.Eq(str, Str__numeric))		return Tid__numeric;
		else if	(String_.Eq(str, Str__select))		return Tid__select;
		else if	(String_.Eq(str, Str__textbox))		return Tid__textbox;
		else if	(String_.Eq(str, Str__fs_file))		return Tid__fs_file;
		else if	(String_.Eq(str, Str__fs_dir))		return Tid__fs_dir;
		else if	(String_.Eq(str, Str__memo))		return Tid__memo;
		else if	(String_.Eq(str, Str__button))		return Tid__button;
		else if	(String_.Eq(str, Str__label))		return Tid__label;
		else										throw Err_.new_unhandled_default(str);
	}
	public static String Infer_gui_type(String db_type) {
		if (String_.Eq(db_type, "bool"))
			return Xoitm_gui_tid.Str__checkbox;
		else if (String_.Eq(db_type, "memo"))
			return Xoitm_gui_tid.Str__memo;
		else
			return Xoitm_gui_tid.Str__textbox;
	}
}
