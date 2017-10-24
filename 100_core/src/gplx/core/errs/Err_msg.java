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
package gplx.core.errs; import gplx.*; import gplx.core.*;
public class Err_msg {
	private final    String msg; private Object[] args;
	public Err_msg(String type, String msg, Object[] args) {
		this.type = type;
		this.msg = msg;
		this.args = args;
	}
	public String Type() {return type;} private final    String type;
	public void Args_add(Object[] add) {
		this.args = (Object[])Array_.Resize_add(args, add);
	}
	public String To_str()			{return To_str_w_type(type, msg, args);}
	public String To_str_wo_type()	{return To_str(msg, args);}
	public String To_str_wo_args()	{return To_str(msg);}

	public static String To_str(String msg, Object... args) {return To_str_w_type(null, msg, args);}
	public static String To_str_w_type(String type, String msg, Object... args) {
		String rv = (type == null) ? "" : "<" + type + "> ";
		rv += msg;
		int len = args.length;
		if (len > 0) {
			rv += ":";
			for (int i = 0; i < len; i += 2) {
				Object key = args[i];
				Object val = i + 1 < len ? args[i + 1] : "MISSING_VAL";
				rv += " " + Object_.Xto_str_strict_or_null_mark(key) + "=" + Object_.Xto_str_strict_or_null_mark(val);
			}
		}
		return rv;
	}
}
