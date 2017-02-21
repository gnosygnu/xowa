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
package gplx.xowa.guis.views; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.gfui.*; import gplx.gfui.draws.*; import gplx.gfui.controls.elems.*;
public class Xog_layout_box implements Gfo_invk {
	public Gfo_invk Owner() {return owner;} public Xog_layout_box Owner_(Gfo_invk v) {owner = v; return this;} Gfo_invk owner;
	public int X_abs() {return x_abs;} private int x_abs = Int_.Min_value;
	public int Y_abs() {return y_abs;} private int y_abs = Int_.Min_value;
	public int W_abs() {return w_abs;} private int w_abs = Int_.Min_value;
	public int H_abs() {return h_abs;} private int h_abs = Int_.Min_value;
	private int x_rel = Int_.Min_value;
	private int y_rel = Int_.Min_value;
	public Xog_layout_box W_rel_(int v) {w_rel = v; return this;} private int w_rel = Int_.Min_value;
	public Xog_layout_box H_rel_(int v) {h_rel = v; return this;} private int h_rel = Int_.Min_value;
	private String text;
	private String font_name;
	private float font_size = Float_.NaN;
	private FontStyleAdp font_style;
	public byte Mode() {return mode;} private byte mode = Mode_rel;
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
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final byte Mode_abs = 0, Mode_rel = 1;
	private static final String Invk_x_abs_ = "x_abs_", Invk_y_abs_ = "y_abs_", Invk_w_abs_ = "w_abs_", Invk_h_abs_ = "h_abs_", Invk_x_rel_ = "x_rel_", Invk_y_rel_ = "y_rel_", Invk_w_rel_ = "w_rel_", Invk_h_rel_ = "h_rel_"
	, Invk_size_abs_ = "size_abs_", Invk_pos_abs_ = "pos_abs_", Invk_rect_abs_ = "rect_abs_", Invk_size_rel_ = "size_rel_", Invk_pos_rel_ = "pos_rel_", Invk_rect_rel_ = "rect_rel_"
	, Invk_text_ = "text_"
	, Invk_font_name_ = "font_name_", Invk_font_size_ = "font_size_", Invk_font_style_ = "font_style_", Invk_mode_ = "mode_", Invk_owner = "owner";
	private static FontAdp Font_make(String font_name, float font_size, FontStyleAdp font_style) {
		String new_font_name = font_name == null ? "Arial" : font_name;
		float new_font_size = Float_.IsNaN(font_size) ? 8 : font_size;
		FontStyleAdp new_font_style = font_style == null ? FontStyleAdp_.Plain : font_style;
		return FontAdp.new_(new_font_name, new_font_size, new_font_style);
	}
}
