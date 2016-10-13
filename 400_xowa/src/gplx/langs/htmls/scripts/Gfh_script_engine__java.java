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
import java.io.FileReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
public class Gfh_script_engine__java implements Gfh_script_engine {
	private final ScriptEngine engine;
	private final Invocable invk;
	public Gfh_script_engine__java() {
	    ScriptEngineManager manager = new ScriptEngineManager();
	    this.engine = manager.getEngineByName("JavaScript");
	    this.invk = (Invocable)engine;
	}
	public void Load_script(Io_url url) {
		FileReader rdr = null; 
		try {
			rdr = new FileReader(url.Xto_api());
			engine.eval(rdr);
//			return engine.eval(script);
		} catch (Exception e) {
			System.out.println(e);
		}
		finally {
			try {
				rdr.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	public void Put_object(String key, Object val) {
		engine.put(key, val);		
	}
	public Object Get_object(String obj_name) {
		try {
			return engine.get(obj_name);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	public Object Invoke_method(Object obj, String func, Object... args) {
		try {
			return invk.invokeMethod(obj, func, args);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
