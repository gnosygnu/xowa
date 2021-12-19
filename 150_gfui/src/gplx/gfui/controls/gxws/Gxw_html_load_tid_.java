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
package gplx.gfui.controls.gxws;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
public class Gxw_html_load_tid_ {
	public static final byte Tid_mem = 0, Tid_url = 1;
	public static byte Xto_tid(String s) {
		if		(StringUtl.Eq(s, "mem"))			return Tid_mem;
		else if	(StringUtl.Eq(s, "url"))			return Tid_url;
		else										throw ErrUtl.NewUnimplemented();
	}
}
