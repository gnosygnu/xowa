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
public abstract class GxwElemFactory_base {
	public abstract GxwElem control_();
	public abstract GxwWin win_app_();
	public abstract GxwWin win_tool_(Keyval_hash ctorArgs);
	public abstract GxwWin win_toaster_(Keyval_hash ctorArgs);
	public abstract GxwElem lbl_();
	public abstract GxwTextFld text_fld_();
	public abstract GxwTextFld text_memo_();
	public abstract GxwTextHtml text_html_();
	public abstract GxwCheckListBox checkListBox_(Keyval_hash ctorArgs);
	public abstract GxwComboBox comboBox_();
	public abstract GxwListBox listBox_();
	//	@gplx.Internal protected GxwElem spacer_() {return MockControl.new_();}
	}
