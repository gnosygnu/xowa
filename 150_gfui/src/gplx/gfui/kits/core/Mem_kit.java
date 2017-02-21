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
import gplx.gfui.imgs.*; import gplx.gfui.controls.gxws.*; import gplx.gfui.controls.elems.*; import gplx.gfui.controls.standards.*;
public class Mem_kit extends Gfui_kit_base {
	@Override public byte Tid() {return Gfui_kit_.Mem_tid;}
	@Override public String Key() {return "mem";}
	@Override public GxwElemFactory_base Factory() {return factory;} private GxwElemFactory_cls_mock factory = new GxwElemFactory_cls_mock();
	public void New_html_impl_prototype_(Gxw_html v) {html_impl_prototype = v;} private Gxw_html html_impl_prototype;
	@Override public Gfui_html New_html(String key, GfuiElem owner, Keyval... args) {
		if (html_impl_prototype == null)
			return super.New_html(key, owner, args);
		else {
			Gfui_html rv = Gfui_html.mem_(key, html_impl_prototype);
			return rv;
		}
	}
	@Override protected Gxw_html New_html_impl() {return html_impl_prototype == null ? new Mem_html(): html_impl_prototype;}
	@Override protected Gxw_tab_mgr New_tab_mgr_impl() {return new Mem_tab_mgr();}
	@Override protected Gxw_tab_itm New_tab_itm_impl() {return new Mem_tab_itm();}
	@Override protected GxwElem New_grp_impl() {return factory.control_();}
	@Override protected GxwElem New_btn_impl() {return factory.control_();}
	@Override protected GxwElem New_combo_impl() {return factory.comboBox_();}
	@Override public ImageAdp New_img_load(Io_url url) {return ImageAdp_null.Instance;}
	public static final    Mem_kit Instance = new Mem_kit(); Mem_kit() {}
}
