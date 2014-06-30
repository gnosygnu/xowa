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
public class GfoInvkAble_ {
	public static GfoInvkAble as_(Object obj) {return obj instanceof GfoInvkAble ? (GfoInvkAble)obj : null;}
	public static GfoInvkAble cast_(Object obj) {try {return (GfoInvkAble)obj;} catch(Exception exc) {throw Err_.type_mismatch_exc_(exc, GfoInvkAble.class, obj);}}
	public static final String_obj_val Rv_unhandled = String_obj_val.new_("Unhandled"), Rv_handled = String_obj_val.new_("Handled"), Rv_host = String_obj_val.new_("Host")
		, Rv_cancel = String_obj_val.new_("Cancel"), Rv_error = String_obj_val.new_("Error");

	public static Object InvkCmd(GfoInvkAble invk, String k)				{return InvkCmd_msg(invk, k, GfoMsg_.Null);}
	public static Object InvkCmd_val(GfoInvkAble invk, String k, Object v)	{return InvkCmd_msg(invk, k, GfoMsg_.new_cast_(k).Add("v", v));}
	public static Object InvkCmd_msg(GfoInvkAble invk, String k, GfoMsg m)	{
		Object rv = invk.Invk(GfsCtx._, 0, k, m);
		if (rv == GfoInvkAble_.Rv_unhandled) throw Err_.new_("invkable did not handle message").Add("key", k);
		return rv;
	}
	public static final GfoInvkAble Null = new GfoInvkAble_null();
}
class GfoInvkAble_null implements GfoInvkAble {
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return this;}
}
