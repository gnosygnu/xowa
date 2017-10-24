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
