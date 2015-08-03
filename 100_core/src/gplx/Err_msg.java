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
package gplx;
public class Err_msg {
	private final String msg; private Object[] args;
	public Err_msg(String type, String msg, Object[] args) {
		this.type = type;
		this.msg = msg;
		this.args = args;
	}
	public String Type() {return type;} private final String type;
	public void Args_add(Object[] add) {
		this.args = (Object[])Array_.Resize_add(args, add);
	}
	public String To_str()			{return To_str_w_type(type, msg, args);}
	public String To_str_wo_type()	{return To_str(msg, args);}
	public String To_str_wo_args()	{return To_str(msg);}
	public static String To_str(String msg, Object... args) {return To_str_w_type(null, msg, args);}
	public static String To_str_w_type(String type, String msg, Object... args) {
		String rv = String_.Len_eq_0(type) ? "" : "<" + type + "> ";
		rv += msg;
		int len = args.length;
		if (len > 0) {
			rv += ":";
			for (int i = 0; i < len; i += 2) {
				Object key = args[i];
				Object val = i < len ? args[i + 1] : "MISSING_VAL";
				rv += " " + Object_.Xto_str_strict_or_null_mark(key) + "=" + Object_.Xto_str_strict_or_null_mark(val);
			}
		}
		return rv;
	}
}
