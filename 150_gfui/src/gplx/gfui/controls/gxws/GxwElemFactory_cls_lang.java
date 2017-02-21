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
package gplx.gfui.controls.gxws; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import gplx.gfui.controls.elems.*; import gplx.gfui.controls.windows.*;
public class GxwElemFactory_cls_lang extends GxwElemFactory_base {
	@Override public GxwElem control_() {return new GxwElem_lang();}
	@Override public GxwWin win_tool_(Keyval_hash ctorArgs)	{
				GfuiWin ownerForm = (GfuiWin)ctorArgs.Get_val_or(GfuiElem_.InitKey_ownerWin, null);
		GxwWin ownerElem = ownerForm == null ? null : (GxwWin)ownerForm.UnderElem(); 
		return GxwWin_jdialog.new_(ownerElem);
//		return GxwWin_lang.new_();
			}
	@Override public GxwWin win_toaster_(Keyval_hash ctorArgs)	{
				GfsCtx ctx = GfsCtx.new_(); ctx.Match("", "");
		GfuiWin ownerForm = (GfuiWin)ctorArgs.Get_val_or(GfuiElem_.InitKey_ownerWin, null);
		GxwWin ownerElem = ownerForm == null ? null : (GxwWin)ownerForm.UnderElem(); 
		return GxwWin_jwindow.new_(ownerElem);
//		return GxwWin_lang.new_();
			}
	@Override public GxwWin win_app_()			{return GxwWin_lang.new_();}
	@Override public GxwElem lbl_()			{return new GxwElem_lang();}
	@Override public GxwTextFld text_fld_()	{return GxwTextBox_lang_.fld_();}
	@Override public GxwTextFld text_memo_()	{return GxwTextBox_lang_.memo_();}
	@Override public GxwTextHtml text_html_()	{return new GxwTextHtml_lang().ctor();}
	@Override public GxwCheckListBox checkListBox_(Keyval_hash ctorArgs) {return new GxwCheckListBox_lang();}
	@Override public GxwComboBox comboBox_()	{return GxwComboBox_lang.new_();}
	@Override public GxwListBox listBox_()		{return GxwListBox_lang.new_();}
}
