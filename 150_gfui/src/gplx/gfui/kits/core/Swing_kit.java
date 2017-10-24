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
import gplx.gfui.imgs.*; import gplx.gfui.controls.gxws.*;
import gplx.core.brys.fmtrs.*;
public class Swing_kit extends Gfui_kit_base {
	private Bry_fmtr ask_fmtr = Bry_fmtr.new_(); private Bry_bfr ask_bfr = Bry_bfr_.New();
	@Override public byte Tid() {return Gfui_kit_.Swing_tid;}
	@Override public String Key() {return "swing";}
	@Override public GxwElemFactory_base Factory() {return factory;} private GxwElemFactory_cls_lang factory = new GxwElemFactory_cls_lang();
	@Override public void Ask_ok(String grp_key, String msg_key, String fmt, Object... args) {GfuiEnv_.ShowMsg(ask_fmtr.Bld_str_many(ask_bfr, fmt, args));}
	@Override public void Kit_run() {GfuiEnv_.Run(this.Main_win());}
	@Override public void Kit_term() {this.Kit_term_cbk().Exec(); GfuiEnv_.Exit();}
	@Override public ImageAdp New_img_load(Io_url url) {return ImageAdp_.file_(url);}
	@Override protected Gxw_html New_html_impl() {return new Mem_html();}
	@Override protected Gxw_tab_mgr New_tab_mgr_impl() {return new Mem_tab_mgr();}
	@Override protected Gxw_tab_itm New_tab_itm_impl() {return new Mem_tab_itm();}
	@Override protected GxwElem New_grp_impl() {return factory.control_();}
	@Override protected GxwElem New_btn_impl() {return factory.control_();}
	@Override protected GxwElem New_combo_impl() {return factory.control_();}
	public static final    Swing_kit Instance = new Swing_kit(); Swing_kit() {}
}
