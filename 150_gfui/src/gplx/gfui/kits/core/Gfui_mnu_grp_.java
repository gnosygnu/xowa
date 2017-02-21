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
public class Gfui_mnu_grp_ {
	public static final    Gfui_mnu_grp Noop = new Gfui_mnu_grp_noop();
}
class Gfui_mnu_grp_noop implements Gfui_mnu_grp {
	public String Uid() {return "";}
	public int Tid() {return Gfui_mnu_itm_.Tid_grp;}
	public boolean Enabled() {return true;} public void Enabled_(boolean v) {}
	public boolean Disposed() {return false;}
	public String Text() {return null;} public void Text_(String v) {}
	public ImageAdp Img() {return null;} public void Img_(ImageAdp v) {}
	public boolean Selected() {return true;} public void Selected_(boolean v) {}
	public String Root_key() {return "null";}
	public Object Under() {return null;}
	public void Itms_clear() {}
	public Gfui_mnu_itm Itms_add_btn_cmd	(String txt, ImageAdp img, Gfo_invk invk, String invk_cmd) {return Gfui_mnu_itm_null.Null;}
	public Gfui_mnu_itm Itms_add_btn_msg	(String txt, ImageAdp img, Gfo_invk invk, Gfo_invk_root_wkr root_wkr, GfoMsg invk_msg) {return Gfui_mnu_itm_null.Null;}
	public Gfui_mnu_itm Itms_add_chk_msg	(String txt, ImageAdp img, Gfo_invk invk, Gfo_invk_root_wkr root_wkr, GfoMsg msg_n, GfoMsg msg_y) {return Gfui_mnu_itm_null.Null;}
	public Gfui_mnu_itm Itms_add_rdo_msg	(String txt, ImageAdp img, Gfo_invk invk, Gfo_invk_root_wkr root_wkr, GfoMsg msg) {return Gfui_mnu_itm_null.Null;}
	public Gfui_mnu_grp Itms_add_grp(String txt, ImageAdp img) {return Gfui_mnu_grp_.Noop;}
	public Gfui_mnu_itm Itms_add_separator() {return Gfui_mnu_itm_null.Null;}
}
