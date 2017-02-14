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
import gplx.gfui.draws.*; import gplx.gfui.ipts.*;
import gplx.gfui.layouts.swts.*;
public class GxwCore_mock extends GxwCore_base {
	@Override public int Width() {return size.Width();} @Override public void Width_set(int v) {size = SizeAdp_.new_(v, size.Height());}
	@Override public int Height() {return size.Height();} @Override public void Height_set(int v) {size = SizeAdp_.new_(size.Width(), v);}
	@Override public int X() {return pos.X();} @Override public void X_set(int v) {pos = PointAdp_.new_(v, pos.Y());}
	@Override public int Y() {return pos.Y();} @Override public void Y_set(int v) {pos = PointAdp_.new_(pos.X(), v);}
	@Override public SizeAdp Size() {return size;} @Override public void Size_set(SizeAdp v) {size = v;} SizeAdp size = SizeAdp_.Zero;
	@Override public PointAdp Pos() {return pos;} @Override public void Pos_set(PointAdp v) {pos = v;} PointAdp pos = PointAdp_.Zero;
	@Override public RectAdp Rect() {return RectAdp_.vector_(pos, size);} @Override public void Rect_set(RectAdp v) {size = v.Size(); pos = v.Pos();}
	@Override public boolean Visible() {return visible;} @Override public void Visible_set(boolean v) {visible = v;} private boolean visible = true;	// HACK: default all controls to visible (should be set to visible only when form is loaded); will cause FocusKeyMgrTst.SubWidget test to fail
	@Override public ColorAdp BackColor() {return backColor;} @Override public void BackColor_set(ColorAdp v) {backColor = v;} ColorAdp backColor = ColorAdp_.Null;
	@Override public ColorAdp ForeColor() {return textColor;} @Override public void ForeColor_set(ColorAdp v) {textColor = v;} ColorAdp textColor = ColorAdp_.Null;
	@Override public FontAdp TextFont() {return font;} @Override public void TextFont_set(FontAdp v) {font = v;} FontAdp font;
	@Override public String TipText() {return tipText;} @Override public void TipText_set(String v) {tipText = v;} private String tipText;
	@Override public Swt_layout_mgr Layout_mgr() {return null;}
	@Override public void Layout_mgr_(Swt_layout_mgr v) {}
	@Override public Swt_layout_data Layout_data() {return null;}
	@Override public void Layout_data_(Swt_layout_data v) {}

	@Override public void Controls_add(GxwElem sub) {list.Add(sub);}
	@Override public void Controls_del(GxwElem sub) {list.Del(sub);}
	@Override public int Focus_index() {return focusKeyIndex;} @Override public void Focus_index_set(int v) {focusKeyIndex = v;} int focusKeyIndex;
	@Override public boolean Focus_able() {return canFocus;} @Override public void Focus_able_(boolean v) {canFocus = v;} private boolean canFocus = true;
	@Override public boolean Focus_has() {return false;}
	@Override public void Focus() {}
	@Override public void Select_exec() {}
	@Override public void Zorder_front() {} @Override public void Zorder_back() {}
	@Override public void Invalidate() {} @Override public void Dispose() {}

	public void SendKey(IptKey key) {}
	List_adp list = List_adp_.New();
}
