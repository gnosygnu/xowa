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
import gplx.gfui.imgs.*;
public interface Gfui_mnu_grp extends Gfui_mnu_itm {
	String Root_key();
	void Itms_clear();
	boolean Disposed();
	Gfui_mnu_itm Itms_add_btn_cmd	(String txt, ImageAdp img, Gfo_invk invk, String invk_cmd);
	Gfui_mnu_itm Itms_add_btn_msg	(String txt, ImageAdp img, Gfo_invk invk, Gfo_invk_root_wkr root_wkr, GfoMsg msg);
	Gfui_mnu_itm Itms_add_chk_msg	(String txt, ImageAdp img, Gfo_invk invk, Gfo_invk_root_wkr root_wkr, GfoMsg msg_n, GfoMsg msg_y);
	Gfui_mnu_itm Itms_add_rdo_msg	(String txt, ImageAdp img, Gfo_invk invk, Gfo_invk_root_wkr root_wkr, GfoMsg msg);
	Gfui_mnu_grp Itms_add_grp		(String txt, ImageAdp img);
	Gfui_mnu_itm Itms_add_separator();
}
