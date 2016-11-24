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
public class GxwElemFactory_cls_mock extends GxwElemFactory_base {
	@Override public GxwElem control_() {return GxwElem_mock_base.new_();}
	@Override public GxwWin win_app_() {return MockForm.Instance;}
	@Override public GxwWin win_tool_(Keyval_hash ctorArgs)	{return MockForm.Instance;}
	@Override public GxwWin win_toaster_(Keyval_hash ctorArgs)	{return MockForm.Instance;}
	@Override public GxwElem lbl_() {return GxwElem_mock_base.new_();}
	@Override public GxwTextFld text_fld_() {return new MockTextBox();}
	@Override public GxwTextFld text_memo_() {return new MockTextBoxMulti();}
	@Override public GxwTextHtml text_html_() {return new MockTextBoxMulti();}
	@Override public GxwCheckListBox checkListBox_(Keyval_hash ctorArgs) {throw Err_.new_unimplemented();}
	@Override public GxwComboBox comboBox_() {return new MockComboBox();}
	@Override public GxwListBox listBox_() {return new MockListBox();}
}
