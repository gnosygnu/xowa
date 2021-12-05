/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.objects.errs;
import gplx.objects.ObjectUtl;
import gplx.objects.strings.StringUtl;
public class ErrUtl {
	public static void Noop(Exception e) {}
	public static Err NewMsg(String msg) {return new Err(msg);}
	public static Err NewFmt(String fmt, Object... args) {return new Err(StringUtl.Format(fmt, args));}
	public static Err NewFmt(Exception e, String fmt, Object... args) {return new Err(StringUtl.Format(fmt, args) + " exc=" + ErrUtl.MessageLang(e));}
	public static Err NewNull(String name) {return new Err("object was null; name=" + name);}
	public static Err NewUnhandledDefault(Object o) {return new Err("val is not in switch; val=" + ObjectUtl.ToStr(o));}
	public static Err NewUnimplemented() {return new Err("method is not implemented");}
	public static Err NewParse(Class<?> cls, String raw) {return new Err("parse failed; cls=" + cls.getCanonicalName() + "; raw=" + raw);}

	public static String MessageLang(Exception e) {
		return Error.class.isAssignableFrom(e.getClass())
				? e.toString()    // java.lang.Error returns null for "getMessage()"; return "toString()" instead
				: e.getMessage();
	}
	public static String TraceLang(Throwable e) {
		StackTraceElement[] ary = e.getStackTrace();
		String rv = "";
		for (int i = 0; i < ary.length; i++) {
			if (i != 0) rv += "\n";
			rv += ary[i].toString();
		}
		return rv;
	}
}
