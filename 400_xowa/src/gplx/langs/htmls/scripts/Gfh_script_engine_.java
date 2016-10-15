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
package gplx.langs.htmls.scripts; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
public class Gfh_script_engine_ {
	public static Gfh_script_engine New_by_key(String key) {
		if		(String_.Eq(key, "javascript.java"))	return new Gfh_script_engine__javascript();
		else if	(String_.Eq(key, "lua.luaj"))			return new Gfh_script_engine__luaj();
		else if	(String_.Eq(key, "noop"))				return new Gfh_script_engine__noop();
		else													throw Err_.new_unhandled(key);
	}
	public static Gfh_script_engine New_by_ext(String ext) {
		if		(String_.Eq(ext, ".js"))				return new Gfh_script_engine__javascript();
		else if	(String_.Eq(ext, ".lua"))				return new Gfh_script_engine__luaj();
		else											throw Err_.new_unhandled(ext);
	}
}
