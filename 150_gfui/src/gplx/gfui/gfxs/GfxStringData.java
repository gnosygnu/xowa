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
package gplx.gfui.gfxs; import gplx.*; import gplx.gfui.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import gplx.core.envs.*;
import gplx.gfui.draws.*; import gplx.gfui.controls.gxws.*; import gplx.gfui.controls.elems.*; import gplx.gfui.controls.windows.*;
public class GfxStringData {
	public String Val() {
		if (ownerElem == null) return val;
		if (ownerElem.TextVal() == null) return "";
		return ownerElem.TextVal();
	}	String val = "";
	public GfuiAlign AlignH() {return alignH;} GfuiAlign alignH;
	public GfxStringData AlignH_(GfuiAlign val) {
		alignH = val;
		if (ownerElem != null) Gfo_invk_.Invk_by_val(ownerElem, GxwElem_lang.AlignH_cmd, alignH);	// needed for TextBox, since its Paint is not overriden
		TextRect_setNull();
		return this;
	}
	public GfuiAlign AlignV() {return alignV;} public GfxStringData AlignV_(GfuiAlign val) {alignV = val; return this;} GfuiAlign alignV = GfuiAlign_.Mid;
	public ColorAdp Color() {return brush.Color();}
		public SolidBrushAdp UnderBrush() {return brush;} SolidBrushAdp brush;
	public AttributedString MnemonicString() {return mnemonicString;} AttributedString mnemonicString;
	String drawn = "";
	public void MnemonicString_sync() {
		int pos = GfuiWinKeyCmdMgr.ExtractPosFromText(this.Val()); if (pos == String_.Find_none) return;
		drawn = String_.MidByLen(this.Val(), 0, pos) + String_.Mid(this.Val(), pos + 1);	// rebuild string without &
		mnemonicString = new AttributedString(drawn);
		mnemonicString.addAttribute(TextAttribute.FONT, font.UnderFont());
		mnemonicString.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, pos, pos + 1);
	}
	
		public GfxStringData Color_(ColorAdp val) {
		brush = SolidBrushAdp_.new_(val);
		if (ownerElem != null) ownerElem.Core().ForeColor_set(val);
		TextRect_setNull();
		return this;
	}
	public FontAdp Font() {return font;} FontAdp font;
	public GfxStringData Font_(FontAdp val) {
		font = val;
		if (!Env_.Mode_testing() && ownerElem != null) ownerElem.Core().TextFont_set(font);
		TextRect_setNull();
				MnemonicString_sync();
				return this;
	}
	public RectAdpF TextRect() {return textRect;} public void TextRect_set(RectAdpF val) {textRect = val;} public void TextRect_setNull() {textRect = RectAdpF.Null;} RectAdpF textRect = RectAdpF.Null;
	public RectAdpF TextRect_setX(int x) {
		textRect = RectAdpF.new_(x, textRect.Y(), textRect.Width(), textRect.Height());
		return textRect;
	}
	@gplx.Internal protected SizeAdp OwnerSize() {return ownerSize;}
	public void OwnerSize_sync(SizeAdp val) {
		ownerSize = val; TextRect_setNull();
		ownerElem.Core().Invalidate();	// NOTE: force redraw; this may be redundant in WINFORMS but needed in SWING especially when windowOpened causes resize; SWING seems to execute windowOpened -> resize -> paint -> componentResized
	} SizeAdp ownerSize = SizeAdp_.new_(20, 20); 
	@gplx.Internal protected GxwElem UnderElem() {return owner.UnderElem();}
	public void DrawData(GfxAdp gfx) {
		if (textRect.Eq(RectAdpF.Null)) {textRect = TextRect_calc(gfx);}
		gfx.DrawStringXtn(this.Val(), font, brush, textRect.X(), textRect.Y(), textRect.Width(), textRect.Height(), this);
	}
	public void Text_set(String v) {
		if (this.Val() == v) return;
		if (ownerElem != null) {
			ownerElem.TextVal_set(v);
			if (owner.CustomDraw()) ownerElem.Core().Invalidate();
		}
		else
			this.val = v;
		TextRect_setNull();
				MnemonicString_sync();
			}
	public RectAdpF TextRect_calc(GfxAdp gfx) {
				
		float[] sizeAry = gfx.MeasureStringXtn(drawn == "" ? this.Val() : drawn, font, this);
		float width = sizeAry[0], height = sizeAry[1], descent = sizeAry[2];
//		if (String_.Eq("opal.gfds 0.0.1", this.Val())) {
//			Tfds.Write(this.Val(), alignH.Val(), (int)width, ownerSize.Width());
//		}
		float x = GfuiAlign_.CalcInsideOfAxis(alignH.Val(), (int)width, ownerSize.Width());
		float y = 0; int alignVVal = alignV.Val(); float ownerHeight = ownerSize.Height();
		if 		(alignVVal == GfuiAlign_.Null.Val())	y = Int_.Min_value;
		else if (alignVVal == GfuiAlign_.Lo.Val())		y = height - descent;
		else if (alignVVal == GfuiAlign_.Mid.Val())		y = (ownerHeight - (ownerHeight - height) / 2);// - descent; // COMMENT: subtracting descent is theoretically correct, but practically results in text shifted up
		else if (alignVVal == GfuiAlign_.Hi.Val())		y = ownerHeight - descent;
		if (width > ownerElem.Core().Width()) width = ownerElem.Core().Width();	// clip to elem size or else text overflows; EX: tab buttons
				if (x < 0) x = 0; if (y < 0) y = 0;	// occurs when text is larger than elem; do not allow negative values			
		return RectAdpF.new_(x, y, width, height);
	}	GfuiElemBase owner; GxwElem ownerElem;
	public static GfxStringData new_(GfuiElemBase owner, GxwElem ownerElem) {
		GfxStringData rv = new GfxStringData();
		rv.brush = SolidBrushAdp_.Black;
		rv.alignH = GfuiAlign_.Left;
		rv.owner = owner;
		rv.ownerElem = ownerElem;
		// WORKAROUND:.NET: setting font on textBox causes odd selection behavior for MediaTimeBox
				rv.Font_(FontAdp.new_("Arial", 8, FontStyleAdp_.Plain));	// needed for TextBox, since its Paint is not overriden, and .Font property must be set
				return rv;
	}	GfxStringData() {}
	public static final    GfxStringData Null = null;
}
