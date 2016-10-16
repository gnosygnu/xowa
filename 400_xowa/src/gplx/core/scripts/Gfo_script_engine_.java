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
package gplx.core.scripts; import gplx.*; import gplx.core.*;
public class Gfo_script_engine_ {
	public static Gfo_script_engine New_by_key(String key) {
		if		(String_.Eq(key, "javascript.java"))	return new Gfo_script_engine__javascript();
		else if	(String_.Eq(key, "lua.luaj"))			return new Gfo_script_engine__luaj();
		else if	(String_.Eq(key, "noop"))				return new Gfo_script_engine__noop();
		else													throw Err_.new_unhandled(key);
	}
	public static Gfo_script_engine New_by_ext(String ext) {
		if		(String_.Eq(ext, ".js"))				return new Gfo_script_engine__javascript();
		else if	(String_.Eq(ext, ".lua"))				return new Gfo_script_engine__luaj();
		else											throw Err_.new_unhandled(ext);
	}
}
