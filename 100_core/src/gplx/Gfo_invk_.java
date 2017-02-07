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
import gplx.core.primitives.*;
public class Gfo_invk_ {
	public static final String Mutator_suffix = "_";
	public static final    Gfo_invk Noop = new Gfo_invk__noop();
	public static final    String_obj_val 
	  Rv_unhandled	= String_obj_val.new_("Unhandled")
	, Rv_handled	= String_obj_val.new_("Handled")
	, Rv_host		= String_obj_val.new_("Host")
	, Rv_cancel		= String_obj_val.new_("Cancel")
	, Rv_error		= String_obj_val.new_("Error");

	public static Gfo_invk as_(Object obj) {return obj instanceof Gfo_invk ? (Gfo_invk)obj : null;}

	public static Object Invk_no_key(Gfo_invk invk)						{return Invk_by_msg(invk, "", GfoMsg_.Null);}
	public static Object Invk_by_key(Gfo_invk invk, String k)			{return Invk_by_msg(invk, k	, GfoMsg_.Null);}
	public static Object Invk_by_val(Gfo_invk invk, String k, Object v)	{return Invk_by_msg(invk, k	, GfoMsg_.new_cast_(k).Add("v", v));}
	public static Object Invk_by_msg(Gfo_invk invk, String k, GfoMsg m)	{
		Object rv = invk.Invk(GfsCtx.Instance, 0, k, m);
		if (rv == Gfo_invk_.Rv_unhandled) throw Err_.new_wo_type("invkable did not handle message", "key", k);
		return rv;
	}
}
class Gfo_invk__noop implements Gfo_invk {
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return this;}
}
