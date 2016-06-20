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
package gplx.gfui.controls.standards; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import gplx.gfui.draws.*; import gplx.gfui.kits.core.*; import gplx.gfui.controls.gxws.*; import gplx.gfui.controls.elems.*;
public class GfuiLbl_ {
	public static GfuiLbl sub_(String key, GfuiElem owner) {
		GfuiLbl rv = new_();
		rv.Owner_(owner, key); // must be after ctor, else "Error creating window handle"
		rv.TextMgr().AlignH_(GfuiAlign_.Mid);
		return rv;
	}
	public static GfuiLbl kit_(Gfui_kit kit, String key, GxwElem elm, Keyval_hash ctorArgs) {
		GfuiLbl rv = new GfuiLbl();
		rv.ctor_kit_GfuiElemBase(kit, key, elm, ctorArgs);
		return rv;
	}
	public static GfuiLbl prefix_(String key, GfuiElem owner, String text) {
		GfuiLbl rv = sub_(key, owner);
		rv.Text_(text);
		rv.TextMgr().AlignH_(GfuiAlign_.Left);
		return rv;
	}
	public static GfuiLbl menu_(String key, GfuiElem owner, String text, String tipText) {
		GfuiLbl rv = sub_(key, owner);
		rv.Text_(text);
		rv.TextMgr().AlignH_(GfuiAlign_.Mid);
		rv.TipText_(tipText);
		rv.Border().All_(PenAdp_.black_());
		return rv;
	}
	public static final    String Text_Null = null;
	@gplx.Internal protected static GfuiLbl new_() {
		GfuiLbl rv = new GfuiLbl();
		rv.ctor_GfuiBox_base(GfuiElem_.init_focusAble_false_());
		return rv;
	}
}
