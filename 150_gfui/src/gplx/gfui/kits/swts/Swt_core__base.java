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
package gplx.gfui.kits.swts; import gplx.*; import gplx.gfui.*; import gplx.gfui.kits.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import gplx.gfui.controls.gxws.GxwCore_base;
import gplx.gfui.controls.gxws.GxwElem;
import gplx.gfui.draws.*;
import gplx.gfui.kits.*;
import gplx.gfui.layouts.swts.*;

abstract class Swt_core__base extends GxwCore_base {
	private boolean focus_able;
	private int     focus_index;
	protected       Swt_layout_mgr      layout_mgr;
	protected       Swt_layout_data     layout_data;
	private         Control             sizeable, viewable;
	private         FontAdp             text_font;
	public Swt_core__base(Control sizeable, Control viewable) {
		this.sizeable = sizeable;
		this.viewable = viewable;
	}
	@Override public int                X()                             {return sizeable.getLocation().x;}
	@Override public void               X_set(int v)                    {Swt_control_.X_set(sizeable, v);}
	@Override public int                Y()                             {return sizeable.getLocation().y;}
	@Override public void               Y_set(int v)                    {Swt_control_.Y_set(sizeable, v);}
	@Override public int                Width()                         {return sizeable.getSize().x;}
	@Override public void               Width_set(int v)                {Swt_control_.W_set(sizeable, v);}
	@Override public int                Height()                        {return sizeable.getSize().y;}
	@Override public void               Height_set(int v)               {Swt_control_.H_set(sizeable, v);}
	@Override public SizeAdp            Size()                          {return SizeAdp_.new_(this.Width(), this.Height());}
	@Override public void               Size_set(SizeAdp v)             {Swt_control_.Size_set(sizeable, v);}
	@Override public PointAdp           Pos()                           {return PointAdp_.new_(this.X(), this.Y());}
	@Override public void               Pos_set(PointAdp v)             {Swt_control_.Pos_set(sizeable, v);}
	@Override public RectAdp            Rect()                          {return RectAdp_.new_(this.X(), this.Y(), this.Width(), this.Height());}
	@Override public void               Rect_set(RectAdp v)             {Swt_control_.Rect_set(sizeable, v);}
	@Override public boolean            Visible()                       {return sizeable.isVisible();}
	@Override public void               Visible_set(boolean v)          {sizeable.setVisible(v);}
	@Override public Swt_layout_mgr     Layout_mgr()                    {return layout_mgr;}
	@Override public Swt_layout_data    Layout_data()                   {return layout_data;}
	@Override public void Layout_mgr_(Swt_layout_mgr v) {
		Swt_core__base.Layout_mgr_set(sizeable, v);
		this.layout_mgr = v;
	}
	@Override public void Layout_data_(Swt_layout_data v) {
		Swt_core__base.Layout_data_set(sizeable, v);
		this.layout_data = v;
	}
	@Override public void Controls_add(GxwElem sub)	{
		if (!(sizeable instanceof Composite)) throw Err_.new_wo_type("cannot add sub to control");
		Composite owner_as_composite = (Composite)sizeable;
		Control sub_as_swt = ((Swt_control)sub).Under_control();
		sub_as_swt.setParent(owner_as_composite);
	}
	@Override public void Controls_del(GxwElem sub)	{
		if (!(sizeable instanceof Composite)) throw Err_.new_wo_type("cannot remove sub from control");
		Control sub_as_swt = ((Swt_control)sub).Under_control();
		sub_as_swt.dispose();	// SWT: no way to officially remove sub from control; can only dispose
	}

	@Override public ColorAdp           BackColor()                     {return To_color_gfui(viewable.getBackground());}
	@Override public void               BackColor_set(ColorAdp v)       {viewable.setBackground(To_color_swt(viewable, v));}
	@Override public ColorAdp           ForeColor()                     {return To_color_gfui(viewable.getForeground());}
	@Override public void               ForeColor_set(ColorAdp v)       {viewable.setForeground(To_color_swt(viewable, v));}
	@Override public String             TipText()                       {return viewable.getToolTipText();}
	@Override public void               TipText_set(String v)           {viewable.setToolTipText(v);}
	@Override public FontAdp TextFont() {
		if (text_font == null)
			text_font = Swt_core__base.Control_font_get(viewable.getFont(), this);
		return text_font;
	}
	@Override public void TextFont_set(FontAdp v) {
		Swt_core__base.Control_font_set(v, this, viewable);
		this.text_font = v;
	}
	@Override public void Select_exec()  {viewable.setFocus();}
	@Override public boolean Focus_has() {return viewable.isFocusControl();}
	@Override public void Focus() {
		if (Focus_able())
			viewable.forceFocus();
	}
	
	@Override public boolean            Focus_able()                    {return focus_able;}
	@Override public void               Focus_able_(boolean v)          {focus_able = v;}
	@Override public int                Focus_index()                   {return focus_index;}
	@Override public void               Focus_index_set(int v)          {focus_index = v;}	
	@Override public void               Zorder_front()                  {} // TODO.FUTURE: Canvas c; c.moveAbove(arg0);
	@Override public void               Zorder_back()                   {} // TODO.FUTURE: Canvas c; c.moveBelow(arg0);

	protected static ColorAdp To_color_gfui(Color v)                  {return ColorAdp_.new_(0, v.getRed(), v.getGreen(), v.getBlue());}
	protected static Color To_color_swt(Control control, ColorAdp v)  {return new Color(control.getDisplay(), v.Red(), v.Green(), v.Blue());}
	private static FontAdp Control_font_get(Font font, GxwCore_base owner) {
		FontData fontData = font.getFontData()[0];
		FontAdp rv = FontAdp.new_(fontData.getName(), fontData.getHeight(), FontStyleAdp_.lang_(fontData.getStyle()));	// NOTE: swt style constants match swing
		rv.OwnerGxwCore_(owner);
		return rv;
	}
	private static void Control_font_set(FontAdp font, GxwCore_base owner, Control control) {
		font.OwnerGxwCore_(owner);
		FontData fontData = new FontData(font.Name(), (int)font.Size(), font.Style().Val());
		Font rv = new Font(control.getDisplay(), fontData);
		control.setFont(rv);
	}
	private static void Layout_mgr_set(Control control, Swt_layout_mgr v) {
		Swt_layout_mgr__grid gfui_layout = (Swt_layout_mgr__grid)v; 
		GridLayout swt_layout = new GridLayout();
		swt_layout.numColumns = gfui_layout.Cols();
		if (gfui_layout.Margin_w() > -1) swt_layout.marginWidth = gfui_layout.Margin_w();
		if (gfui_layout.Margin_h() > -1) swt_layout.marginHeight = gfui_layout.Margin_h();
		if (gfui_layout.Spacing_w() > -1) swt_layout.horizontalSpacing = gfui_layout.Spacing_w();
		if (gfui_layout.Spacing_h() > -1) swt_layout.verticalSpacing = gfui_layout.Spacing_h();
		
		Composite control_as_composite = (Composite)control;
		control_as_composite.setLayout(swt_layout);		
	}
	private static void Layout_data_set(Control control, Swt_layout_data v) {
		Swt_layout_data__grid gfui_data = (Swt_layout_data__grid)v;

		GridData swt_data = new GridData();
		if (gfui_data.Grab_excess_w()) swt_data.grabExcessHorizontalSpace = gfui_data.Grab_excess_w();
		if (gfui_data.Grab_excess_h()) swt_data.grabExcessVerticalSpace = gfui_data.Grab_excess_h();
		if (gfui_data.Align_w() != Swt_layout_data__grid.Align__null) swt_data.horizontalAlignment = gfui_data.Align_w();
		if (gfui_data.Align_h() != Swt_layout_data__grid.Align__null) swt_data.verticalAlignment   = gfui_data.Align_h();
		if (gfui_data.Hint_w() > -1) swt_data.widthHint = gfui_data.Hint_w();
		if (gfui_data.Hint_h() > -1) swt_data.heightHint = gfui_data.Hint_h();
		if (gfui_data.Min_w() > -1) swt_data.minimumWidth = gfui_data.Min_w();
		if (gfui_data.Min_h() > -1) swt_data.minimumHeight = gfui_data.Min_h();
		
		control.setLayoutData(swt_data);
		control.getParent().layout(true, true);		
	}	
}
