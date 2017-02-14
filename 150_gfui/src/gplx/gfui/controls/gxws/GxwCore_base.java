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
import gplx.gfui.draws.*;
import gplx.gfui.layouts.swts.*;
public abstract class GxwCore_base {
	public abstract void Controls_add(GxwElem sub);
	public abstract void Controls_del(GxwElem sub);
	public abstract int Width(); public abstract void Width_set(int v);
	public abstract int Height(); public abstract void Height_set(int v);
	public abstract int X(); public abstract void X_set(int v);
	public abstract int Y(); public abstract void Y_set(int v);
	public abstract SizeAdp Size(); public abstract void Size_set(SizeAdp v);
	public abstract PointAdp Pos(); public abstract void Pos_set(PointAdp v);
	public abstract RectAdp Rect(); public abstract void Rect_set(RectAdp v);
	public abstract boolean Visible(); public abstract void Visible_set(boolean v);
	public abstract ColorAdp BackColor(); public abstract void BackColor_set(ColorAdp v);
	public abstract ColorAdp ForeColor(); public abstract void ForeColor_set(ColorAdp v);
	public abstract FontAdp TextFont(); public abstract void TextFont_set(FontAdp v);
	public abstract String TipText(); public abstract void TipText_set(String v);
	public abstract Swt_layout_mgr Layout_mgr();
	public abstract void Layout_mgr_(Swt_layout_mgr v);
	public abstract Swt_layout_data Layout_data();
	public abstract void Layout_data_(Swt_layout_data v);

	public abstract int Focus_index(); public abstract void Focus_index_set(int v);
	public abstract boolean Focus_able(); public abstract void Focus_able_(boolean v);
	public abstract boolean Focus_has();
	public abstract void Focus();
	public abstract void Select_exec();
	public abstract void Zorder_front(); public abstract void Zorder_back();
	public abstract void Invalidate();
	public abstract void Dispose();
}
