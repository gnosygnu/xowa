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
package gplx.types.errs;
import gplx.types.basics.utls.ArrayLni;
import gplx.types.basics.utls.ClassLni;
import gplx.types.basics.utls.ErrLni;
import gplx.types.basics.utls.ObjectLni;
import gplx.types.basics.utls.StringLni;
public class Err extends RuntimeException {
	private final String type;
	private final String msg;
	private Object[] args;
	private Throwable inner;
	protected Err(String msg) {this(null, msg);}
	public Err(String type, String msg) {
		this.type = type == null ? ClassLni.SimpleNameByObj(this) : type; // NOTE: subClasses pass in null; use className for type
		this.msg = msg;
		this.args = new Object[0];
		this.inner = null;
	}
	public boolean Logged() {return logged;} public Err LoggedSetY() {this.logged = true; return this;} private boolean logged; // marks messages logged so they can be ignored; used by Gfh_utl
	public Err FrameIgnoreAdd1() {frameIgnore++; return this;} private int frameIgnore = 1;
	public Err InnerSet(Throwable v) {this.inner = v; return this;}
	public Err ArgsAddAry(Object[] v) {
		for (int i = 0; i < v.length; i += 2) {
			ArgsAdd((String)v[i], v[i + 1]);
		}
		return this;
	}
	public Err ArgsAdd(String key, Object val) {
		this.args = (Object[])ArrayLni.ResizeAddAry(args, key, val);
		return this;
	}
	@Override public String getMessage() {return ToStrFull();}
	public String ToStrFull()            {return ToStr(false, true , true , false);}
	public String ToStrLog()             {return ToStr(false, true , true , true);}
	public String ToStrLogNoTrace()      {return ToStr(false, false, true , true);}
	public String ToStrNoTraceNoArgs()   {return ToStr(false, false, false, false);}
	private String ToStr(boolean printType, boolean printTrace, boolean printArgs, boolean singleLine) {
		String rv = printType ? "<" + type + "> " : "";
		if (inner != null) {
			rv += ErrLni.Message(inner) + "\n" + "[trace]:" + ErrLni.Trace(inner) + "\n";
		}
		rv += ToStr(printArgs, msg, args);
		if (printTrace) {
			rv += "\n[trace]:" + ErrLni.Trace(this) + "\n";
		}
		if (singleLine) {
			rv = StringLni.Replace(rv, "\n", "\t");
		}
		return rv;
	}
	public static String ToStr(String msg, Object... args) {return ToStr(true, msg, args);}
	public static String ToStr(boolean printArgs, String msg, Object[] args) {
		String rv = msg;
		int len = args.length;
		if (len > 0 && printArgs) {
			rv += ":";
			for (int i = 0; i < len; i += 2) {
				Object val = i + 1 < len ? args[i + 1] : "MISSING_VAL";
				rv += " " + ObjectLni.ToStrOrNullMark(args[i]) + "=" + ObjectLni.ToStrOrNullMark(val);
			}
		}
		return rv;
	}
}
