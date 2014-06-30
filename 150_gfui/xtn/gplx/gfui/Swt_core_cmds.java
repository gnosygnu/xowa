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
package gplx.gfui;
import gplx.Err_;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
class Swt_core_cmds extends GxwCore_base {
	public Swt_core_cmds(Control control) {
		compositeAble = control instanceof Composite;
		this.control = control;
	} 	Control control; boolean compositeAble = false; 
	@Override public int Width() {return control.getSize().x;} @Override public void Width_set(int v) 	{if (Cfg_resize_disabled) return; control.setSize(v, this.Height());}
	@Override public int Height() {return control.getSize().y;} @Override public void Height_set(int v) {if (Cfg_resize_disabled) return; control.setSize(this.Width(), v);}
	@Override public int X() {return control.getLocation().x;} @Override public void X_set(int v) {control.setLocation(v, this.Y());}
	@Override public int Y() {return control.getLocation().y;} @Override public void Y_set(int v) {control.setLocation(this.X(), v);}
	@Override public SizeAdp Size() {return SizeAdp_.new_(this.Width(), this.Height());} @Override public void Size_set(SizeAdp v) {if (Cfg_resize_disabled) return; control.setSize(v.Width(), v.Height());}
	@Override public PointAdp Pos() {return PointAdp_.new_(this.X(), this.Y());} @Override public void Pos_set(PointAdp v) {control.setLocation(v.X(), v.Y());}
	@Override public RectAdp Rect() {return RectAdp_.new_(this.X(), this.Y(), this.Width(), this.Height());}
	@Override public void Rect_set(RectAdp v) {
		if (Cfg_resize_disabled)
			control.setLocation(v.X(), v.Y());
		else
			control.setBounds(v.X(), v.Y(), v.Width(), v.Height());
	}
	@Override public boolean Visible() {return control.isVisible();}
	@Override public void Visible_set(boolean v) {control.setVisible(v);}
	@Override public ColorAdp BackColor() {return XtoColorAdp(control.getBackground());} @Override public void BackColor_set(ColorAdp v) {control.setBackground(XtoColor(v));}
	@Override public ColorAdp ForeColor() {return XtoColorAdp(control.getForeground());} @Override public void ForeColor_set(ColorAdp v) {control.setForeground(XtoColor(v));}
	public boolean Cfg_resize_disabled = false;
	ColorAdp XtoColorAdp(Color v) {return ColorAdp_.new_(0, v.getRed(), v.getGreen(), v.getBlue());}
	Color XtoColor(ColorAdp v) {return new Color(control.getDisplay(), v.Red(), v.Green(), v.Blue());}
	@Override public FontAdp TextFont() {
		if (prv_font != null) return prv_font;
		prv_font = Swt_kit.Control_font_get(control.getFont(), this);
		return prv_font;
	}	FontAdp prv_font;
	@Override public void TextFont_set(FontAdp v) {
		Swt_kit.Control_font_set(v, this, control);
		prv_font = v;
	}
	@Override public String TipText() {return control.getToolTipText();} @Override public void TipText_set(String v) {control.setToolTipText(v);}
	@Override public void Controls_add(GxwElem sub)	{
		if (!compositeAble) throw Err_.new_("cannot add sub to control");
		Composite owner_as_composite = (Composite)control; 
		Swt_control sub_as_WxSwt = (Swt_control)sub;
		Control sub_as_swt = sub_as_WxSwt.Under_control();
		sub_as_swt.setParent(owner_as_composite);
	}
	@Override public void Controls_del(GxwElem sub)	{
		if (!compositeAble) throw Err_.new_("cannot add sub to control");
		Swt_control sub_as_WxSwt = (Swt_control)sub;
		Control sub_as_swt = sub_as_WxSwt.Under_control();
		sub_as_swt.dispose();	// SWT_NOTE: no way to officially remove sub from control; can only dispose
	}
	@Override public boolean Focus_has() {return control.isFocusControl();}
	@Override public boolean Focus_able() {return focus_able;} boolean focus_able;
	@Override public void Focus_able_(boolean v) {focus_able = v;}
	@Override public int Focus_index() {return focusIndex;} @Override public void Focus_index_set(int v) {focusIndex = v;} int focusIndex;
	@Override public void Focus() {
		if (Focus_able())
			control.forceFocus();
	}
	@Override public void Select_exec() {
		control.setFocus();
	}
	@Override public void Zorder_front() {
//		Canvas c; c.moveAbove(arg0);
	}
	@Override public void Zorder_back() {
//		Canvas c; c.moveBelow(arg0);
	}
	@Override public void Invalidate() {control.redraw(); control.update();}
	@Override public void Dispose() {control.dispose();}
}
class Swt_core_cmds_dual extends GxwCore_base {
	public Swt_core_cmds_dual(Composite outer, Control inner, int inner_adj_x, int inner_adj_y, int inner_adj_w, int inner_adj_h) {
		this.outer = outer; this.inner = inner;
		outer_is_composite = outer instanceof Composite;
		this.inner_adj_x = inner_adj_x; this.inner_adj_y = inner_adj_y; this.inner_adj_w = inner_adj_w; this.inner_adj_h = inner_adj_h;
	} 	Control outer, inner; boolean outer_is_composite = false; int inner_adj_x, inner_adj_y, inner_adj_w, inner_adj_h;
	@Override public int X() {return outer.getLocation().x;} @Override public void X_set(int v) 		{Swt_control_.X_set(outer, v);}
	@Override public int Y() {return outer.getLocation().y;} @Override public void Y_set(int v) 		{Swt_control_.Y_set(outer, v);}
	@Override public int Width() {return outer.getSize().x;} @Override public void Width_set(int v) 	{Swt_control_.W_set(outer, v); Swt_control_.W_set(outer, v + inner_adj_w);}
	@Override public int Height() {return outer.getSize().y;} @Override public void Height_set(int v) 	{Swt_control_.H_set(outer, v); Swt_control_.H_set(outer, v + inner_adj_h);}
	@Override public SizeAdp Size() {return SizeAdp_.new_(this.Width(), this.Height());} @Override public void Size_set(SizeAdp v) 	{Swt_control_.Size_set(outer, v); 	Swt_control_.Size_set(inner, v.Width() + inner_adj_w, v.Height() + inner_adj_h);}
	@Override public PointAdp Pos() {return PointAdp_.new_(this.X(), this.Y());} @Override public void Pos_set(PointAdp v) 			{Swt_control_.Pos_set(outer, v);}
	@Override public RectAdp Rect() {return RectAdp_.new_(this.X(), this.Y(), this.Width(), this.Height());} @Override public void Rect_set(RectAdp v) {Swt_control_.Rect_set(outer, v); Swt_control_.Size_set(inner, v.Width() + inner_adj_w, v.Height() + inner_adj_h);}
	@Override public boolean Visible() {return outer.isVisible();}
	@Override public void Visible_set(boolean v) {outer.setVisible(v);}
	@Override public ColorAdp BackColor() {return XtoColorAdp(inner.getBackground());} @Override public void BackColor_set(ColorAdp v) {inner.setBackground(XtoColor(v));}
	@Override public ColorAdp ForeColor() {return XtoColorAdp(inner.getForeground());} @Override public void ForeColor_set(ColorAdp v) {inner.setForeground(XtoColor(v));}
	ColorAdp XtoColorAdp(Color v) {return ColorAdp_.new_(0, v.getRed(), v.getGreen(), v.getBlue());}
	Color XtoColor(ColorAdp v) {return new Color(outer.getDisplay(), v.Red(), v.Green(), v.Blue());}
	@Override public FontAdp TextFont() {
		if (prv_font != null) return prv_font;
		prv_font = Swt_kit.Control_font_get(inner.getFont(), this);
		return prv_font;
	}	FontAdp prv_font;
	@Override public void TextFont_set(FontAdp v) {
		Swt_kit.Control_font_set(v, this, inner);
		prv_font = v;
	}
	@Override public String TipText() {return inner.getToolTipText();} @Override public void TipText_set(String v) {inner.setToolTipText(v);}
	@Override public void Controls_add(GxwElem sub)	{
		if (!outer_is_composite) throw Err_.new_("cannot add sub to outer");
		Composite owner_as_composite = (Composite)outer; 
		Swt_control sub_as_WxSwt = (Swt_control)sub;
		Control sub_as_swt = sub_as_WxSwt.Under_control();
		sub_as_swt.setParent(owner_as_composite);
	}
	@Override public void Controls_del(GxwElem sub)	{
		if (!outer_is_composite) throw Err_.new_("cannot add sub to outer");
		Swt_control sub_as_WxSwt = (Swt_control)sub;
		Control sub_as_swt = sub_as_WxSwt.Under_control();
		sub_as_swt.dispose();	// SWT_NOTE: no way to officially remove sub from outer; can only dispose
	}
	@Override public boolean Focus_has() {return inner.isFocusControl();}
	@Override public boolean Focus_able() {return focus_able;} boolean focus_able;
	@Override public void Focus_able_(boolean v) {focus_able = v;}
	@Override public int Focus_index() {return focusIndex;} @Override public void Focus_index_set(int v) {focusIndex = v;} int focusIndex;
	@Override public void Focus() {
		if (Focus_able())
			inner.forceFocus();
	}
	@Override public void Select_exec() {
		inner.setFocus();
	}
	@Override public void Zorder_front() {}
	@Override public void Zorder_back() {}
	@Override public void Invalidate() {outer.update(); inner.update();}
	@Override public void Dispose() {outer.dispose(); inner.dispose();}	
}
interface Swt_core_cmds_frames_itm {
	Control Itm();
	void Rect_set(int w, int h);
}
class Swt_core_cmds_frames_itm_manual implements Swt_core_cmds_frames_itm {
	public Swt_core_cmds_frames_itm_manual(Control control, int x, int y, int w, int h) {
		this.control = control; this.x = x; this.y = y; this.w = w; this.h = h;
	}	Control control; int x, y, w, h;
	public Control Itm() {return control;}
	public void Rect_set(int new_w, int new_h) {
		Swt_control_.Rect_set(control, x, y, new_w + w, new_h + h);
	}
}
class Swt_core_cmds_frames_itm_center_v implements Swt_core_cmds_frames_itm {
	public Swt_core_cmds_frames_itm_center_v(Control control, Swt_text_w_border margin_owner) {this.control = control; this.margin_owner = margin_owner;} Control control; Swt_text_w_border margin_owner;
	public Control Itm() {return control;}
	public void Rect_set(int new_w, int new_h) {
		int margin_t = margin_owner.margins_t;
		int margin_b = margin_owner.margins_b;
		Swt_control_.Rect_set(control, 0, margin_t, new_w, new_h - (margin_t + margin_b));
	}
}
class Swt_core_cmds_frames extends GxwCore_base {
	public Swt_core_cmds_frames(Composite outer, Swt_core_cmds_frames_itm[] frames) {
		this.outer = outer; this.frames = frames;
		frames_len = frames.length;
		this.inner = frames[frames_len - 1].Itm();
	}	Composite outer; Control inner; Swt_core_cmds_frames_itm[] frames; int frames_len;
	void Frames_w_set(int v) {
		for (int i = 0; i < frames_len; i++)
			frames[i].Rect_set(v, this.Height());
	}
	void Frames_h_set(int v) {		
		for (int i = 0; i < frames_len; i++)
			frames[i].Rect_set(this.Width(), v);
	}
	void Frames_size_set(SizeAdp v) {		
		for (int i = 0; i < frames_len; i++)
			frames[i].Rect_set(v.Width(), v.Height());
	}
	@Override public int X() {return outer.getLocation().x;} @Override public void X_set(int v) 		{Swt_control_.X_set(outer, v);}
	@Override public int Y() {return outer.getLocation().y;} @Override public void Y_set(int v) 		{Swt_control_.Y_set(outer, v);}
	@Override public int Width() {return outer.getSize().x;} @Override public void Width_set(int v) 	{Swt_control_.W_set(outer, v); Frames_w_set(v);}
	@Override public int Height() {return outer.getSize().y;} @Override public void Height_set(int v) 	{Swt_control_.H_set(outer, v); Frames_h_set(v);}
	@Override public SizeAdp Size() {return SizeAdp_.new_(this.Width(), this.Height());} @Override public void Size_set(SizeAdp v) 	{Swt_control_.Size_set(outer, v); Frames_size_set(v);}
	@Override public PointAdp Pos() {return PointAdp_.new_(this.X(), this.Y());} @Override public void Pos_set(PointAdp v) 			{Swt_control_.Pos_set(outer, v);}
	@Override public RectAdp Rect() {return RectAdp_.new_(this.X(), this.Y(), this.Width(), this.Height());} @Override public void Rect_set(RectAdp v) {Swt_control_.Rect_set(outer, v); Frames_size_set(v.Size());}
	@Override public boolean Visible() {return outer.isVisible();}
	@Override public void Visible_set(boolean v) {outer.setVisible(v);}
	@Override public ColorAdp BackColor() {return XtoColorAdp(inner.getBackground());}
	@Override public void BackColor_set(ColorAdp v) {
		Color color = XtoColor(v);
//		outer.setBackground(color);
		for (int i = 0; i < frames_len; i++)
			frames[i].Itm().setBackground(color);
	}
	@Override public ColorAdp ForeColor() {return XtoColorAdp(inner.getForeground());} @Override public void ForeColor_set(ColorAdp v) {inner.setForeground(XtoColor(v));}
	ColorAdp XtoColorAdp(Color v) {return ColorAdp_.new_(0, v.getRed(), v.getGreen(), v.getBlue());}
	Color XtoColor(ColorAdp v) {return new Color(outer.getDisplay(), v.Red(), v.Green(), v.Blue());}
	@Override public FontAdp TextFont() {
		if (prv_font != null) return prv_font;
		prv_font = Swt_kit.Control_font_get(inner.getFont(), this);
		return prv_font;
	}	FontAdp prv_font;
	@Override public void TextFont_set(FontAdp v) {
		Swt_kit.Control_font_set(v, this, inner);
		prv_font = v;
	}
	@Override public String TipText() {return inner.getToolTipText();} @Override public void TipText_set(String v) {inner.setToolTipText(v);}
	@Override public void Controls_add(GxwElem sub)	{throw Err_.not_implemented_();}
	@Override public void Controls_del(GxwElem sub)	{}
	@Override public boolean Focus_has() {return inner.isFocusControl();}
	@Override public boolean Focus_able() {return focus_able;} boolean focus_able;
	@Override public void Focus_able_(boolean v) {focus_able = v;}
	@Override public int Focus_index() {return focusIndex;} @Override public void Focus_index_set(int v) {focusIndex = v;} int focusIndex;
	@Override public void Focus() {
		if (Focus_able())
			inner.forceFocus();
	}
	@Override public void Select_exec() {
		inner.setFocus();
	}
	@Override public void Zorder_front() {}
	@Override public void Zorder_back() {}
	@Override public void Invalidate() {
		inner.redraw();
		inner.update();
	}
	@Override public void Dispose() {outer.dispose(); inner.dispose();}	
}
