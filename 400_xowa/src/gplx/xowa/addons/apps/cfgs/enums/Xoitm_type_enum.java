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
package gplx.xowa.addons.apps.cfgs.enums;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
public class Xoitm_type_enum {
	public static final int
	  Tid__bool			= 0
	, Tid__int			= 1
	, Tid__str			= 2
	, Tid__memo			= 3
	, Tid__list			= 4
	, Tid__btn			= 5
	, Tid__io_cmd		= 6
	, Tid__gui_binding	= 7
	;

	public static int To_uid(String v) {
		if		(StringUtl.Eq(v, BoolUtl.ClsValName))			return Tid__bool;
		else if	(StringUtl.Eq(v, IntUtl.ClsValName))			return Tid__int;
		else if	(StringUtl.Eq(v, StringUtl.ClsValName))		return Tid__str;
		else if	(StringUtl.Eq(v, "memo"))						return Tid__memo;
		else if	(StringUtl.Eq(v, "btn"))						return Tid__btn;
		else if	(StringUtl.Eq(v, "io.cmd"))					return Tid__io_cmd;
		else if	(StringUtl.Eq(v, "gui.binding"))				return Tid__gui_binding;
		else if (StringUtl.HasAtBgn(v, "list:"))			return Tid__list;
		else												throw ErrUtl.NewArgs("unknown cfg type enum; v=" + v);
	}
}
