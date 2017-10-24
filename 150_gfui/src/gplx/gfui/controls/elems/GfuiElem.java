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
package gplx.gfui.controls.elems; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import gplx.gfui.draws.*; import gplx.gfui.gfxs.*; import gplx.gfui.ipts.*; import gplx.gfui.layouts.*; import gplx.gfui.imgs.*; import gplx.gfui.controls.gxws.*; import gplx.gfui.controls.*; import gplx.gfui.controls.windows.*;
import gplx.gfui.layouts.swts.*;
import gplx.core.interfaces.*;
public interface GfuiElem extends Gfo_invk, GxwCbkHost, IptBndsOwner, GftItem, Gfo_evt_itm {
	//% Layout
	int X(); GfuiElem X_(int val);
	int Y(); GfuiElem Y_(int val);
	int X_max();
	int Y_max();
	GfuiElem Pos_(PointAdp val); GfuiElem Pos_(int x, int y);
	int Width(); GfuiElem Width_(int val);
	int Height(); GfuiElem Height_(int val);
	GfuiElem Size_(SizeAdp val); GfuiElem Size_(int w, int h); 
	RectAdp Rect(); void Rect_set(RectAdp rect);
	void Zorder_front(); void Zorder_back();
	PointAdp Pos();
	SizeAdp Size();
	Swt_layout_mgr Layout_mgr(); void Layout_mgr_(Swt_layout_mgr v);
	Swt_layout_data Layout_data(); void Layout_data_(Swt_layout_data v);
	
	//% Visual
	boolean Visible(); void Visible_set(boolean v); GfuiElem Visible_on_(); GfuiElem Visible_off_();
	ColorAdp BackColor(); GfuiElem BackColor_(ColorAdp v);
	GfuiBorderMgr Border(); GfuiElem Border_on_(); GfuiElem Border_off_();
	void Redraw();

	//% Text
	GfxStringData TextMgr();
	String Text(); GfuiElem Text_(String v); GfuiElem Text_any_(Object v);
	GfuiElem ForeColor_(ColorAdp v);
	void TextAlignH_(GfuiAlign v);
	GfuiElem TextAlignH_left_(); GfuiElem TextAlignH_right_(); GfuiElem TextAlignH_center_();
	String TipText(); GfuiElem TipText_(String v);

	//% Focus
	boolean Focus_has();
	boolean Focus_able(); GfuiElem Focus_able_(boolean v);
	int Focus_idx(); GfuiElem Focus_idx_(int val);
	String Focus_default(); GfuiElem Focus_default_(String v);
	void Focus();

	//% Action
	void Click();

	//% Elem Tree Hierarchy (Owners And Subs)
	//String Key_of_GfuiElem(); 
	GfuiElem Key_of_GfuiElem_(String val);	
	GfuiElem OwnerElem(); GfuiElem OwnerElem_(GfuiElem val); GfuiElem Owner_(GfuiElem owner); GfuiElem Owner_(GfuiElem owner, String key);
	GfuiElemList SubElems();
	GfuiElem Inject_(InjectAble sub);

	//% Form
	GfuiWin OwnerWin(); GfuiElem OwnerWin_(GfuiWin val);		
	void Opened_cbk(); boolean Opened_done();
	void Dispose();

	//% Infrastructure
	GxwElem UnderElem();
	GxwElem UnderElem_make(Keyval_hash ctorArgs);
	void ctor_GfuiBox_base(Keyval_hash ctorArgs);
	void Invoke(Gfo_invk_cmd cmd);
}
