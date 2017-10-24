/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.scripts; import gplx.*; import gplx.core.*;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import javax.script.SimpleBindings;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;

import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;
public class Gfo_script_engine__luaj implements Gfo_script_engine {
	private final ScriptEngine engine;
	private final List_adp script_list = List_adp_.New();
	private SimpleBindings bindings;
	public Gfo_script_engine__luaj() {
	    ScriptEngineManager manager = new ScriptEngineManager();
	    this.engine = manager.getEngineByName("luaj");
	}
	public void Load_script(Io_url url) {
		try {
			// create itm and add it to the list of all scripts; list is not used now, but may be used in future
			String src = Io_mgr.Instance.LoadFilStr(url);
			script_list.Add(new Gfo_script_itm__luaj(url, src));
			
			// compile the item into the "bindings" object; this allows scripts to share one "sandbox-space", allowing funcs in one script to call funcs in another
			CompiledScript compiled = ((Compilable) engine).compile(src);
			if (bindings == null) bindings = new SimpleBindings();
			compiled.eval(bindings);
		}
		catch (Exception e) {
			Warn("failed to load_script; url=~{0} err=~{1}", url, Err_.Message_lang(e));
		}
	}
	public void Put_object(String key, Object val) {
		engine.put(key, val);		
	}
	public Object Get_object(String obj_name) {
		try {return engine.get(obj_name);}
		catch (Exception e) {
			Warn("failed to get object; obj_name=~{0} err=~{1}", obj_name, Err_.Message_lang(e));
			return null;
		}
	}
	public Object Eval_script(String script) {
		try {return engine.eval(script);}
		catch (Exception e) {
			Warn("", "", "failed to eval; script=~{0} err=~{1}", script, Err_.Message_lang(e));
			return null;
		}
	}
	public Object Invoke_method(Object obj, String func, Object... args) {
		throw Err_.new_unimplemented_w_msg("luaj does not support invocable interface");	// NOTE: cannot support with Get_object, b/c Get_object needs obj_name, and only "obj" exists
	}
	public Object Invoke_function(String func, Object... args) {
		try {
			LuaValue arg = CoerceJavaToLua.coerce(args[0]);
			LuaFunction lfunc = (LuaFunction)bindings.get(func);
			LuaValue rv = lfunc.call(arg);
			return CoerceLuaToJava.coerce(rv, Object.class);
		}
		catch (Exception e) {
			Gfo_script_engine__luaj.Warn("", "", "failed to invoke method; method=~{0} err=~{1}", func, Err_.Message_lang(e));
			return null;
		}
	}
	public static void Warn(String fmt, Object... args) {
		String msg = Gfo_usr_dlg_.Instance.Warn_many("", "", fmt, args);		
		System.out.println(msg);
	}
}
class Gfo_script_itm__luaj {
	public Gfo_script_itm__luaj(Io_url url, String src) {
		this.url = url;
		this.src = src;
	}
	public Io_url Url() {return url;} private final Io_url url;
	public String Src() {return src;} private final String src;
}
