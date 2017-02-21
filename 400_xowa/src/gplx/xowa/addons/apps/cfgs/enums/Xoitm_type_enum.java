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
package gplx.xowa.addons.apps.cfgs.enums; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*;
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
		if		(String_.Eq(v, Bool_.Cls_val_name))			return Tid__bool;
		else if	(String_.Eq(v, Int_.Cls_val_name))			return Tid__int;
		else if	(String_.Eq(v, String_.Cls_val_name))		return Tid__str;
		else if	(String_.Eq(v, "memo"))						return Tid__memo;
		else if	(String_.Eq(v, "btn"))						return Tid__btn;
		else if	(String_.Eq(v, "io.cmd"))					return Tid__io_cmd;
		else if	(String_.Eq(v, "gui.binding"))				return Tid__gui_binding;
		else if (String_.Has_at_bgn(v, "list:"))			return Tid__list;
		else												throw Err_.new_wo_type("unknown cfg type enum; v=" + v);
	}
}
