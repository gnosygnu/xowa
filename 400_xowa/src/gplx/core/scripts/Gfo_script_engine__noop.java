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
public class Gfo_script_engine__noop implements Gfo_script_engine {
	public void Load_script(Io_url url) {}
	public Object Get_object(String obj_name) {return null;}
	public void Put_object(String name, Object obj) {}
	public Object Eval_script(String script) {return null;}
	public Object Invoke_method(Object obj, String func, Object... args) {return null;}
	public Object Invoke_function(String func, Object... args) {return null;}
}
