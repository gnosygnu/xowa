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
package gplx.gfui; import gplx.*;
public abstract class GxwElemFactory_base {
	@gplx.Internal protected abstract GxwElem control_();
	@gplx.Internal protected abstract GxwWin win_app_();
	@gplx.Internal protected abstract GxwWin win_tool_(KeyValHash ctorArgs);
	@gplx.Internal protected abstract GxwWin win_toaster_(KeyValHash ctorArgs);
	@gplx.Internal protected abstract GxwElem lbl_();
	@gplx.Internal protected abstract GxwTextFld text_fld_();
	@gplx.Internal protected abstract GxwTextFld text_memo_();
	@gplx.Internal protected abstract GxwTextHtml text_html_();
	@gplx.Internal protected abstract GxwCheckListBox checkListBox_(KeyValHash ctorArgs);
	@gplx.Internal protected abstract GxwComboBox comboBox_();
	@gplx.Internal protected abstract GxwListBox listBox_();
	//	@gplx.Internal protected GxwElem spacer_() {return MockControl.new_();}
	}
class GxwElemFactory_ {
	public static GxwElemFactory_base _ = new GxwElemFactory_cls_mock();
	public static void winForms_() {_ = new GxwElemFactory_cls_lang();}
		public static void swt_(org.eclipse.swt.widgets.Display display) {_ = new GxwElemFactory_swt(display);}
	}
class GxwElemFactory_cls_lang extends GxwElemFactory_base {
	@gplx.Internal @Override protected GxwElem control_() {return new GxwElem_lang();}
	@gplx.Internal @Override protected GxwWin win_tool_(KeyValHash ctorArgs)	{
				GfuiWin ownerForm = (GfuiWin)ctorArgs.FetchValOr(GfuiElem_.InitKey_ownerWin, null);
		GxwWin ownerElem = ownerForm == null ? null : (GxwWin)ownerForm.UnderElem(); 
		return GxwWin_jdialog.new_(ownerElem);
//		return GxwWin_lang.new_();
			}
	@gplx.Internal @Override protected GxwWin win_toaster_(KeyValHash ctorArgs)	{
				GfsCtx ctx = GfsCtx.new_(); ctx.Match("", "");
		GfuiWin ownerForm = (GfuiWin)ctorArgs.FetchValOr(GfuiElem_.InitKey_ownerWin, null);
		GxwWin ownerElem = ownerForm == null ? null : (GxwWin)ownerForm.UnderElem(); 
		return GxwWin_jwindow.new_(ownerElem);
//		return GxwWin_lang.new_();
			}
	@gplx.Internal @Override protected GxwWin win_app_()			{return GxwWin_lang.new_();}
	@gplx.Internal @Override protected GxwElem lbl_()			{return new GxwElem_lang();}
	@gplx.Internal @Override protected GxwTextFld text_fld_()	{return GxwTextBox_lang_.fld_();}
	@gplx.Internal @Override protected GxwTextFld text_memo_()	{return GxwTextBox_lang_.memo_();}
	@gplx.Internal @Override protected GxwTextHtml text_html_()	{return new GxwTextHtml_lang().ctor();}
	@gplx.Internal @Override protected GxwCheckListBox checkListBox_(KeyValHash ctorArgs) {return new GxwCheckListBox_lang();}
	@gplx.Internal @Override protected GxwComboBox comboBox_()	{return GxwComboBox_lang.new_();}
	@gplx.Internal @Override protected GxwListBox listBox_()		{return GxwListBox_lang.new_();}
}
class GxwElemFactory_cls_mock extends GxwElemFactory_base {
	@gplx.Internal @Override protected GxwElem control_() {return GxwElem_mock_base.new_();}
	@gplx.Internal @Override protected GxwWin win_app_() {return MockForm._;}
	@gplx.Internal @Override protected GxwWin win_tool_(KeyValHash ctorArgs)	{return MockForm._;}
	@gplx.Internal @Override protected GxwWin win_toaster_(KeyValHash ctorArgs)	{return MockForm._;}
	@gplx.Internal @Override protected GxwElem lbl_() {return GxwElem_mock_base.new_();}
	@gplx.Internal @Override protected GxwTextFld text_fld_() {return new MockTextBox();}
	@gplx.Internal @Override protected GxwTextFld text_memo_() {return new MockTextBoxMulti();}
	@gplx.Internal @Override protected GxwTextHtml text_html_() {return new MockTextBoxMulti();}
	@gplx.Internal @Override protected GxwCheckListBox checkListBox_(KeyValHash ctorArgs) {throw Exc_.new_unimplemented();}
	@gplx.Internal @Override protected GxwComboBox comboBox_() {return new MockComboBox();}
	@gplx.Internal @Override protected GxwListBox listBox_() {return new MockListBox();}
}
