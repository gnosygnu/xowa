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
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;

import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
public class Gfh_script_engine__luaj implements Gfh_script_engine {
	private final ScriptEngine engine;
//	private final Invocable invk;
	String sss = "";
	public Gfh_script_engine__luaj() {
	    ScriptEngineManager manager = new ScriptEngineManager();
	    this.engine = manager.getEngineByName("luaj");
//	    this.invk = (Invocable)engine;
	}
	public void Load_script(Io_url url) {
		try {
			sss = Io_mgr.Instance.LoadFilStr(url);
			engine.eval(sss);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to load_script; url=~{0} err=~{1}", url, Err_.Message_lang(e));
		}
	}
	public void Put_object(String key, Object val) {
		engine.put(key, val);		
	}
	public Object Get_object(String obj_name) {
		try {return engine.get(obj_name);}
		catch (Exception e) {
			System.out.println(e.getMessage());
			Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to get object; obj_name=~{0} err=~{1}", obj_name, Err_.Message_lang(e));
			return null;
		}
	}
	public Object Eval_script(String script) {
		try {return engine.eval(script);}
		catch (Exception e) {
			System.out.println(e.getMessage());
			Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to eval; script=~{0} err=~{1}", script, Err_.Message_lang(e));
			return null;
		}
	}
	public Object Invoke_method(Object obj, String func, Object... args) {
		try {
			CompiledScript script = ((Compilable) engine).compile(sss);
			Bindings sb = new SimpleBindings();
			script.eval(sb);
//			LuaTable lfunc = (LuaTable)sb.get("xo_script");
//			LuaClosure lfunc2 = (LuaClosure)lfunc.get(func);
			LuaValue arg = CoerceJavaToLua.coerce(args[0]);
			LuaFunction lfunc = (LuaFunction)sb.get("write_end");
			lfunc.call(arg);
			return null;
//			return engine.eval(obj);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to invoke method; method=~{0} err=~{1}", func, Err_.Message_lang(e));
			return null;
		}
	}
	public Object Invoke_function(String func, Object... args) {
		try {
			CompiledScript script = ((Compilable) engine).compile(sss);
			Bindings sb = new SimpleBindings();
			script.eval(sb);
//			LuaTable lfunc = (LuaTable)sb.get("xo_script");
//			LuaClosure lfunc2 = (LuaClosure)lfunc.get(func);
			LuaValue arg = CoerceJavaToLua.coerce(args[0]);
			LuaFunction lfunc = (LuaFunction)sb.get(func);
			lfunc.call(arg);
			return null;
//			return engine.eval(obj);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to invoke method; method=~{0} err=~{1}", func, Err_.Message_lang(e));
			return null;
		}
	}
}
