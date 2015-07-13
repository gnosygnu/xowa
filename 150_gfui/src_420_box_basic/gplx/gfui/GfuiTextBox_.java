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
public class GfuiTextBox_ {
	public static GfuiTextBox as_(Object obj) {return obj instanceof GfuiTextBox ? (GfuiTextBox)obj : null;}
	public static GfuiTextBox cast_(Object obj) {try {return (GfuiTextBox)obj;} catch(Exception exc) {throw Exc_.new_type_mismatch_w_exc(exc, GfuiTextBox.class, obj);}}
	public static final String NewLine = "\n";	
	public static final String Ctor_Memo = "TextBox_Memo";

	public static GfuiTextBox new_() {
		GfuiTextBox rv = new GfuiTextBox();
		rv.ctor_GfuiBox_base(GfuiElem_.init_focusAble_true_());
		return rv;
	}
	public static GfuiTextBox fld_(String key, GfuiElem owner) {
		GfuiTextBox rv = new_();
		rv.Owner_(owner, key);
		return rv;
	}
	public static GfuiTextMemo multi_(String key, GfuiElem owner) {
		GfuiTextMemo rv = new GfuiTextMemo();
		rv.ctor_GfuiBox_base(GfuiElem_.init_focusAble_true_());
		rv.Key_of_GfuiElem_(key).Owner_(owner);
		return rv;
	}
	public static GfuiTextBox kit_(Gfui_kit kit, String key, GxwTextFld wk_textBox, KeyValHash ctorArgs) {
		GfuiTextBox rv = new GfuiTextBox();
		rv.ctor_kit_GfuiElemBase(kit, key, wk_textBox, ctorArgs);
		return rv;
	}
}
