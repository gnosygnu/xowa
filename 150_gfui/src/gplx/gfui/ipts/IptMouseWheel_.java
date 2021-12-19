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
package gplx.gfui.ipts;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
public class IptMouseWheel_ {
	public static final IptMouseWheel
		  None	= new IptMouseWheel("wheel.none")
		, Up	= new IptMouseWheel("wheel.up")
		, Down	= new IptMouseWheel("wheel.down");
	public static IptMouseWheel parse(String raw) {
		if		(StringUtl.Eq(raw, None.Key()))	return None;
		else if	(StringUtl.Eq(raw, Up.Key()))		return Up;
		else if	(StringUtl.Eq(raw, Down.Key()))	return Down;
		else throw ErrUtl.NewParse(IptMouseWheel.class, raw);
	}
	public static IptMouseWheel api_(Object obj) {
		int delta = IntUtl.Cast(obj);
		if		(delta > 0)		return Up;
		else if (delta < 0)		return Down;
		else					return None;
	}
}
