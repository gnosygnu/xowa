/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
