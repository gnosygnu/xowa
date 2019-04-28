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
package gplx.objects.errs; import gplx.*; import gplx.objects.*;
import gplx.objects.strings.*;
public class Err_ {
	public static void Noop(Exception e) {}
	public static Err New_fmt(String fmt, Object... args) {return new Err(String_.Format(fmt, args));}
	public static Err New_msg(String msg) {return new Err(msg);}
	public static Err New_fmt(Exception e, String fmt, Object... args) {
		return new Err(String_.Format(fmt, args) + " exc=" + Err_.Message_lang(e));
	}

	public static Err New_null(String name) {return new Err("Object was null; name=" + name);}
	public static Err New_unhandled_default(Object o) {
		return new Err("val is not in switch; val=" + Object_.To_str(o));
	}

		public static String Message_lang(Exception e) {
		return Error.class.isAssignableFrom(e.getClass())
				? e.toString()    // java.lang.Error returns null for "getMessage()"; return "toString()" instead
				: e.getMessage();
	}
	
		public static String Trace_lang(Throwable e) {
		StackTraceElement[] ary = e.getStackTrace();
		String rv = "";
		for (int i = 0; i < ary.length; i++) {
			if (i != 0) rv += "\n";
			rv += ary[i].toString();
		}
		return rv;
	}
	}
