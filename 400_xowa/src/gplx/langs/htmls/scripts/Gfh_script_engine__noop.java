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
public class Gfh_script_engine__noop implements Gfh_script_engine {
	public void Load_script(Io_url url) {}
	public Object Get_object(String obj_name) {return null;}
	public void Put_object(String name, Object obj) {}
	public Object Eval_script(String script) {return null;}
	public Object Invoke_method(Object obj, String func, Object... args) {return null;}
	public Object Invoke_function(String func, Object... args) {return null;}
}
