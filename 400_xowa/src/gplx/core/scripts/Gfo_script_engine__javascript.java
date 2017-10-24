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
public class Gfo_script_engine__javascript implements Gfo_script_engine {
	private final ScriptEngine engine;
	private final Invocable invk;
	public Gfo_script_engine__javascript() {
	    ScriptEngineManager manager = new ScriptEngineManager();
	    this.engine = manager.getEngineByName("JavaScript");
	    this.invk = (Invocable)engine;
	}
	public void Load_script(Io_url url) {
		try {engine.eval(Io_mgr.Instance.LoadFilStr(url));}
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
		try {return invk.invokeMethod(obj, func, args);}
		catch (Exception e) {
			System.out.println(e.getMessage());
			Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to invoke method; method=~{0} err=~{1}", func, Err_.Message_lang(e));
			return null;
		}
	}
	public Object Invoke_function(String func, Object... args) {
		try {return invk.invokeFunction(func, args);}
		catch (Exception e) {
			System.out.println(e.getMessage());
			Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to invoke method; method=~{0} err=~{1}", func, Err_.Message_lang(e));
			return null;
		}
	}
}
