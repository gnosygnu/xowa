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
package gplx.gfui.kits.core; import gplx.*; import gplx.gfui.*; import gplx.gfui.kits.*;
public class Gfui_mnu_itm_ {
	public static String Gen_uid() {return "mnu_" + Int_.To_str(++uid_next);} private static int uid_next = 0;
	public static final int Tid_nil = 0, Tid_grp = 1, Tid_spr = 2, Tid_btn = 3, Tid_chk = 4, Tid_rdo = 5;
}
