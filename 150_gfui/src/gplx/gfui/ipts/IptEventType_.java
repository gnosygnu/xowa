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
package gplx.gfui.ipts; import gplx.*; import gplx.gfui.*;
import gplx.core.bits.*; import gplx.core.primitives.*;
public class IptEventType_ {
	static EnmMgr enmMgr = EnmMgr.new_().BitRngEnd_(128);
	public static final    IptEventType 
		  None			= new_(   0, "none")
		, KeyDown		= new_(   1, "keyDown")
		, KeyUp			= new_(   2, "keyUp")
		, KeyPress		= new_(   4, "keyPress")
		, MouseDown		= new_(   8, "mouseDown")
		, MouseUp		= new_(  16, "mouseUp")
		, MouseMove		= new_(  32, "mouseMove")
		, MouseWheel	= new_(  64, "mouseWheel")
		, MousePress	= new_( 128, "mousePress");
	public static IptEventType add_(IptEventType... ary) {
		if (ary.length == 0) return IptEventType_.None;
		int newVal = ary[0].Val();
		for (int i = 1; i < ary.length; i++)
			newVal = Bitmask_.Flip_int(true, newVal, ary[i].Val());
		return getOrNew_(newVal);
	}
	static IptEventType getOrNew_(int v) {
		IptEventType rv = (IptEventType)enmMgr.Get(v);
		return (rv == null) ? new_(v, enmMgr.GetStr(v)) : rv;
	}
	static IptEventType new_(int val, String name) {
		IptEventType rv = new IptEventType(val, name);
		enmMgr.RegObj(val, name, rv);
		return rv;
	}
	public static boolean Has(IptEventType val, IptEventType find) {
		if (find == IptEventType_.None && val != IptEventType_.None) return false; // check .None manually b/c 0 is identity when BitShifting
		return Bitmask_.Has_int(val.Val(), find.Val());
	}
	public static IptEventType default_(IptArg[] args) {
		IptEventType rv = IptEventType_.None;
		for (IptArg arg : args)
			rv = rv.Add(IptArg_.EventType_default(arg));
		return rv;
	}
}
