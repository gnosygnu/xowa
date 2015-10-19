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
package gplx.xowa.guis.views; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.gfui.*;
public class Xog_layout_box implements GfoInvkAble {
	public GfoInvkAble Owner() {return owner;} public Xog_layout_box Owner_(GfoInvkAble v) {owner = v; return this;} GfoInvkAble owner;
	public int X_abs() {return x_abs;} public Xog_layout_box X_abs_(int v) {x_abs = v; return this;} private int x_abs = Int_.Min_value;
	public int Y_abs() {return y_abs;} public Xog_layout_box Y_abs_(int v) {y_abs = v; return this;} private int y_abs = Int_.Min_value;
	public int W_abs() {return w_abs;} public Xog_layout_box W_abs_(int v) {w_abs = v; return this;} private int w_abs = Int_.Min_value;
	public int H_abs() {return h_abs;} public Xog_layout_box H_abs_(int v) {h_abs = v; return this;} private int h_abs = Int_.Min_value;
	public int X_rel() {return x_rel;} public Xog_layout_box X_rel_(int v) {x_rel = v; return this;} private int x_rel = Int_.Min_value;
	public int Y_rel() {return y_rel;} public Xog_layout_box Y_rel_(int v) {y_rel = v; return this;} private int y_rel = Int_.Min_value;
	public int W_rel() {return w_rel;} public Xog_layout_box W_rel_(int v) {w_rel = v; return this;} private int w_rel = Int_.Min_value;
	public int H_rel() {return h_rel;} public Xog_layout_box H_rel_(int v) {h_rel = v; return this;} private int h_rel = Int_.Min_value;
	public String Text() {return text;} public Xog_layout_box Text_(String v) {text = v; return this;} private String text;
	public String Font_name() {return font_name;} public Xog_layout_box Font_name_(String v) {font_name = v; return this;} private String font_name;
	public float Font_size() {return font_size;} public Xog_layout_box Font_size_(float v) {font_size = v; return this;} float font_size = Float_.NaN;
	public FontStyleAdp Font_style() {return font_style;} public Xog_layout_box Font_style_(FontStyleAdp v) {font_style = v; return this;} FontStyleAdp font_style;
	public byte Mode() {return mode;} public Xog_layout_box Mode_(byte v) {mode = v; return this;} private byte mode = Mode_rel;
	public void Adj_size(Rect_ref rect) {
		if (w_abs > -1) rect.W_(w_abs);	if (w_rel != Int_.Min_value) rect.W_(w_rel + rect.W());
		if (h_abs > -1) rect.H_(h_abs);	if (h_rel != Int_.Min_value) rect.H_(h_rel + rect.H());
	}
	public void Adj_pos(Rect_ref rect) {
		if (x_abs > -1) rect.X_(x_abs);	if (x_rel != Int_.Min_value) rect.X_(x_rel + rect.X());
		if (y_abs > -1) rect.Y_(y_abs);	if (y_rel != Int_.Min_value) rect.Y_(y_rel + rect.Y());
	}
	public void Adj_text(GfuiElem elem) {
		if (text != null) elem.Text_(text);
		if (font_name != null || !Float_.IsNaN(font_size) || font_style != null)
			elem.TextMgr().Font_(Font_make(font_name, font_size, font_style));
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_x_abs_))					x_abs = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_y_abs_))					y_abs = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_w_abs_))					w_abs = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_h_abs_))					h_abs = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_x_rel_))					x_rel = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_y_rel_))					y_rel = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_w_rel_))					w_rel = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_h_rel_))					h_rel = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_size_abs_))				{int[] ary = Int_.Ary_parse(m.ReadStr("v"), 2, null); if (ary != null) {w_abs = ary[0]; h_abs = ary[1];}}
		else if	(ctx.Match(k, Invk_size_rel_))				{int[] ary = Int_.Ary_parse(m.ReadStr("v"), 2, null); if (ary != null) {w_rel = ary[0]; h_rel = ary[1];}}
		else if	(ctx.Match(k, Invk_pos_abs_))				{int[] ary = Int_.Ary_parse(m.ReadStr("v"), 2, null); if (ary != null) {x_abs = ary[0]; y_abs = ary[1];}}
		else if	(ctx.Match(k, Invk_pos_rel_))				{int[] ary = Int_.Ary_parse(m.ReadStr("v"), 2, null); if (ary != null) {x_rel = ary[0]; y_rel = ary[1];}}
		else if	(ctx.Match(k, Invk_rect_abs_))				{int[] ary = Int_.Ary_parse(m.ReadStr("v"), 4, null); if (ary != null) {w_abs = ary[0]; h_abs = ary[1]; x_abs = ary[2]; y_abs = ary[3];}}
		else if	(ctx.Match(k, Invk_rect_rel_))				{int[] ary = Int_.Ary_parse(m.ReadStr("v"), 4, null); if (ary != null) {w_rel = ary[0]; h_rel = ary[1]; x_rel = ary[2]; y_rel = ary[3];}}
		else if	(ctx.Match(k, Invk_text_))					text = m.ReadStr("v");
		else if	(ctx.Match(k, Invk_font_name_))				font_name = m.ReadStr("v");
		else if	(ctx.Match(k, Invk_font_size_))				font_size = m.ReadFloat("v");
		else if	(ctx.Match(k, Invk_font_style_))			font_style = FontStyleAdp_.parse(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_mode_))					mode = String_.Eq(m.ReadStr("v"), "abs") ? Mode_abs : Mode_rel;
		else if	(ctx.Match(k, Invk_owner))					return owner;
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	public static final byte Mode_abs = 0, Mode_rel = 1;
	static final String Invk_x_abs_ = "x_abs_", Invk_y_abs_ = "y_abs_", Invk_w_abs_ = "w_abs_", Invk_h_abs_ = "h_abs_", Invk_x_rel_ = "x_rel_", Invk_y_rel_ = "y_rel_", Invk_w_rel_ = "w_rel_", Invk_h_rel_ = "h_rel_"
		, Invk_size_abs_ = "size_abs_", Invk_pos_abs_ = "pos_abs_", Invk_rect_abs_ = "rect_abs_", Invk_size_rel_ = "size_rel_", Invk_pos_rel_ = "pos_rel_", Invk_rect_rel_ = "rect_rel_"
		, Invk_text_ = "text_"
		, Invk_font_name_ = "font_name_", Invk_font_size_ = "font_size_", Invk_font_style_ = "font_style_", Invk_mode_ = "mode_", Invk_owner = "owner";
	FontAdp Font_make(String font_name, float font_size, FontStyleAdp font_style) {
		String new_font_name = font_name == null ? "Arial" : font_name;
		float new_font_size = Float_.IsNaN(font_size) ? 8 : font_size;
		FontStyleAdp new_font_style = font_style == null ? FontStyleAdp_.Plain : font_style;
		return FontAdp.new_(new_font_name, new_font_size, new_font_style);
	}
}
