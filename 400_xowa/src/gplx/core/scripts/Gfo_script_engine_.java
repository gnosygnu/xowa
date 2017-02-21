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
public class Gfo_script_engine_ {
	public static Gfo_script_engine New_by_key(String key) {
		if		(String_.Eq(key, "javascript.java"))	return new Gfo_script_engine__javascript();
		else if	(String_.Eq(key, "lua.luaj"))			return new Gfo_script_engine__luaj();
		else if	(String_.Eq(key, "noop"))				return new Gfo_script_engine__noop();
		else											throw Err_.new_unhandled(key);
	}
	public static Gfo_script_engine New_by_ext(String ext) {
		if		(String_.Eq(ext, ".js"))				return new Gfo_script_engine__javascript();
		else if	(String_.Eq(ext, ".lua"))				return new Gfo_script_engine__luaj();
		else											throw Err_.new_unhandled(ext);
	}
}
