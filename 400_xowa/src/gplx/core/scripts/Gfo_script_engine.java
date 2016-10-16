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
public interface Gfo_script_engine {
	void Load_script(Io_url url);
	Object Get_object(String obj_name);
	void Put_object(String name, Object obj);
	Object Eval_script(String script);
	Object Invoke_method(Object obj, String func, Object... args);
	Object Invoke_function(String func, Object... args);
}
