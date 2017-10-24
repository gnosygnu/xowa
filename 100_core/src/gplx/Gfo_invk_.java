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
