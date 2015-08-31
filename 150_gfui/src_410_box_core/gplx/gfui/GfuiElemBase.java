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
import gplx.core.strings.*;
public class GfuiElemBase implements GfuiElem {
	//% Layout
	public GfoEvMgr EvMgr() {if (evMgr == null) evMgr = GfoEvMgr.new_(this); return evMgr;} GfoEvMgr evMgr;
	public GfoInvkCmdMgr InvkMgr() {return invkMgr;} GfoInvkCmdMgr invkMgr = GfoInvkCmdMgr.new_();
	public int X() {return underMgr.X();} public GfuiElem X_(int val) {underMgr.X_set(val); return this;}
	public int Y() {return underMgr.Y();} public GfuiElem Y_(int val) {underMgr.Y_set(val); return this;}
	public int X_max() {return underMgr.X() + underMgr.Width();} public int Y_max() {return underMgr.Y() + underMgr.Height();}
	public PointAdp Pos() {return underMgr.Pos();} public GfuiElem Pos_(PointAdp val) {underMgr.Pos_set(val); return this;} public GfuiElem Pos_(int x, int y) {return Pos_(PointAdp_.new_(x, y));}
	public int Width() {return underMgr.Width();} public GfuiElem Width_(int val) {underMgr.Width_set(val); return this;}
	public int Height() {return underMgr.Height();}
	public GfuiElem Height_(int val) {underMgr.Height_set(val); return this;}
	public SizeAdp Size() {return underMgr.Size();} public GfuiElem Size_(int w, int h) {return Size_(SizeAdp_.new_(w, h));}
	public GfuiElem Size_(SizeAdp val) {
		underMgr.Size_set(val);
		return this;
	} 
	public RectAdp Rect() {return RectAdp_.vector_(this.Pos(), this.Size());}
	public void Rect_set(RectAdp rect) {
		underMgr.Rect_set(rect);
		textMgr.OwnerSize_sync(rect.Size());
	}
	public int Gft_w() {return underElem.Core().Width();}	public GftItem Gft_w_(int v) {underElem.Core().Width_set(v); return this;}
	public int Gft_h() {return underElem.Core().Height();}	public GftItem Gft_h_(int v) {underElem.Core().Height_set(v); return this;}
	public int Gft_x() {return underElem.Core().X();}		public GftItem Gft_x_(int v) {underElem.Core().X_set(v); return this;}
	public int Gft_y() {return underElem.Core().Y();}		public GftItem Gft_y_(int v) {underElem.Core().Y_set(v); return this;}
	public GftItem Gft_rect_(RectAdp rect) {underElem.Core().Rect_set(rect); return this;}
	public GftGrid Lyt() {return lyt;} GftGrid lyt = null;
	public GfuiElemBase Lyt_activate() {lyt = GftGrid.new_(); return this;}
	public void Lyt_exec() {
		GftItem[] ary = new GftItem[subElems.Count()];
		for (int i = 0; i < ary.length; i++)
			ary[i] = (GfuiElemBase)subElems.Get_at(i);
		SizeChanged_ignore = true;
		lyt.Exec(this, ary);
		SizeChanged_ignore = false;
	}
	public void Zorder_front() {underMgr.Zorder_front();} public void Zorder_back() {underMgr.Zorder_back();}
	@gplx.Virtual public void Zorder_front_and_focus() {
		this.Zorder_front();
		this.Visible_set(true);
		this.Focus();
	}
	//% Visual
	@gplx.Virtual public boolean Visible() {return underMgr.Visible();} @gplx.Virtual public void Visible_set(boolean v) {underMgr.Visible_set(v);}
	public GfuiElem Visible_on_() {this.Visible_set(true); return this;} public GfuiElem Visible_off_() {this.Visible_set(false); return this;}
	@gplx.Virtual public ColorAdp BackColor() {return backColor;} ColorAdp backColor = ColorAdp_.White;
	public GfuiElem BackColor_(ColorAdp v) {backColor = v; underMgr.BackColor_set(backColor); return this;}
	public GfuiBorderMgr Border() {return border;} GfuiBorderMgr border = GfuiBorderMgr.new_();
	public GfuiElem Border_on_() {border.All_(PenAdp_.new_(ColorAdp_.Black, 1)); return this;}
	public GfuiElem Border_off_() {border.All_(null); return this;}
	public GfxStringData TextMgr() {return textMgr;} GfxStringData textMgr;
	public String Text() {return textMgr.Val();}
	public GfuiElem Text_any_(Object obj) {return Text_(Object_.Xto_str_strict_or_null_mark(obj));}
	@gplx.Virtual public GfuiElem Text_(String v) {
		this.TextMgr().Text_set(v);
		Click_key_set_(v);
		return this;
	}
	public GfuiElem ForeColor_(ColorAdp v) {textMgr.Color_(v); return this;}
	public void TextAlignH_(GfuiAlign v) {textMgr.AlignH_(v);}
	public GfuiElem TextAlignH_left_() {textMgr.AlignH_(GfuiAlign_.Left); return this;}
	public GfuiElem TextAlignH_right_() {textMgr.AlignH_(GfuiAlign_.Right); return this;}
	public GfuiElem TextAlignH_center_() {textMgr.AlignH_(GfuiAlign_.Mid); return this;}
	public String TipText() {return underElem.Core().TipText();} public GfuiElem TipText_(String v) {underElem.Core().TipText_set(v); return this;}
	@gplx.Virtual public void Redraw() {underMgr.Invalidate();}
	public boolean CustomDraw() {return customDraw;} public void CustomDraw_set(boolean v) {customDraw = v;} private boolean customDraw;


	//% Focus
	public boolean Focus_has() {return underElem.Core().Focus_has();}
	public boolean Focus_able() {return underElem.Core().Focus_able();} public GfuiElem Focus_able_(boolean v) {underElem.Core().Focus_able_(v); return this;}
	public String Focus_default() {return defaultFocusKey;} public GfuiElem Focus_default_(String v) {defaultFocusKey = v; return this;} private String defaultFocusKey;
	public int Focus_idx() {return focusKey_order_manual;} int focusKey_order_manual = GfuiFocusOrderer.NullVal;
	public GfuiElem Focus_idx_(int val) {
		underElem.Core().Focus_index_set(val);
		focusKey_order_manual = val;
		return this;
	}
	@gplx.Virtual public void Focus() {
		if (subElems.Count() == 0)							// if no subs, focus self
			underElem.Core().Focus();
		else if (defaultFocusKey != null) {					// if default is specified, focus it
			GfuiElem focusTarget = subElems.Get_by(defaultFocusKey); if (focusTarget == null) throw Err_.new_wo_type("could not find defaultTarget for focus", "ownerKey", this.Key_of_GfuiElem(), "defaultTarget", defaultFocusKey);
			focusTarget.Focus();	
		}
		else {												// else, activate first visible elem; NOTE: some elems are visible, but not Focus_able (ex: ImgGalleryBox)
			for (int i = 0; i < subElems.Count(); i++) {
				GfuiElem sub = subElems.Get_at(i);
				if (sub.Visible() && !String_.Eq(sub.Key_of_GfuiElem(), "statusBox")) {
					sub.Focus();
					return;
				}
			}
			underElem.Core().Focus();					// no visible subElems found; focus self
		}
	}

	//% Inputs
	public IptBndMgr IptBnds() {return iptBnds;} IptBndMgr iptBnds = IptBndMgr.new_();

	//% ActionKey
	@gplx.Virtual public void Click() {}
	@gplx.Virtual public boolean Click_able() {return false;}
	public IptKey Click_key() {return clickKey;}
	@gplx.Internal @gplx.Virtual protected void Click_key_set_(String v) {clickKey = GfuiWinKeyCmdMgr.ExtractKeyFromText(v);} IptKey clickKey = IptKey_.None;

	//% Owner
	public String Key_of_GfuiElem() {return keyIdf;} public GfuiElem Key_of_GfuiElem_(String val) {keyIdf = val; return this;} private String keyIdf;
	public GfuiElemList SubElems() {return subElems;} GfuiElemList subElems;
	public GfuiElem OwnerElem() {return ownerElem;} public GfuiElem OwnerElem_(GfuiElem owner) {ownerElem = owner; return this;} GfuiElem ownerElem = null;
	public GfuiElem Owner_(GfuiElem owner, String key) {this.Key_of_GfuiElem_(key); return Owner_(owner);}
	public GfuiElem Owner_(GfuiElem owner) {
		owner.SubElems().Add(this);
		return this;
	}

	//% Form
	@gplx.Virtual public GfuiWin OwnerWin() {return ownerForm;} public GfuiElem OwnerWin_(GfuiWin val) {ownerForm = val; return this;} GfuiWin ownerForm = null;		
	@gplx.Virtual public boolean Opened_done() {return ownerForm == null ? false : ownerForm.Opened_done();}
	@gplx.Virtual public void Opened_cbk() {
		for (int i = 0; i < subElems.Count(); i++) {
			GfuiElem elem = subElems.Get_at(i);
			elem.Opened_cbk();
		}
	}
	public void Dispose() {
		GfoEvMgr_.RlsSub(this);
		underMgr.Dispose();
	}

	//% Cbks
	@gplx.Virtual public boolean KeyDownCbk(IptEvtDataKey data)				{IptEventMgr.ExecKeyDown(this, data); return true;}
	@gplx.Virtual public boolean KeyUpCbk(IptEvtDataKey data)				{IptEventMgr.ExecKeyUp(this, data); return true;}
	@gplx.Virtual public boolean KeyHeldCbk(IptEvtDataKeyHeld data)			{IptEventMgr.ExecKeyPress(this, data); return true;}
	@gplx.Virtual public boolean MouseDownCbk(IptEvtDataMouse data)			{IptEventMgr.ExecMouseDown(this, data); return true;}
	@gplx.Virtual public boolean MouseUpCbk(IptEvtDataMouse data)			{IptEventMgr.ExecMouseUp(this, data); return true;}
	@gplx.Virtual public boolean MouseMoveCbk(IptEvtDataMouse data)			{IptEventMgr.ExecMouseMove(this, data); return true;}
	@gplx.Virtual public boolean MouseWheelCbk(IptEvtDataMouse data)		{IptEventMgr.ExecMouseWheel(this, data); return true;}
	@gplx.Virtual public boolean PaintCbk(PaintArgs args)				{border.DrawData(args.Graphics()); return true;}
	@gplx.Virtual public boolean PaintBackgroundCbk(PaintArgs args)		{return true;}
	@gplx.Virtual public boolean DisposeCbk()							{return true;}
	@gplx.Virtual public boolean VisibleChangedCbk()						{return true;}
	@gplx.Virtual public boolean FocusGotCbk() {
		GfuiFocusMgr._.FocusedElem_set(this);
		return true;
	}
	@gplx.Virtual public boolean FocusLostCbk()							{return true;}
	@gplx.Virtual public boolean SizeChangedCbk() {
		this.TextMgr().OwnerSize_sync(this.Size());
		this.Border().Bounds_sync(RectAdp_.size_(this.Size().Op_subtract(1)));
		if (SizeChanged_ignore
			|| !this.Opened_done()
			) return true;
		if (lyt != null) {
			GftGrid.LytExecRecur(this);
			return true;
		}
		return true;
	}
		
	//% InjectAble
	public GfuiElem Inject_(InjectAble sub) {sub.Inject(this); return this;}

	@gplx.Virtual public GxwElem UnderElem() {return underElem;} GxwElem underElem;
	@gplx.Virtual public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, GfuiElemKeys.Redraw_cmd))				Redraw();
		else if	(ctx.Match(k, GfuiElemKeys.Key_set)) {
			String v = m.ReadStr("v");
			return ctx.Deny() ? (Object)this : Key_of_GfuiElem_(v);
		}
		else if	(ctx.Match(k, GfuiElemKeys.Text_set)) {
			String v = m.ReadStr("v");
			return ctx.Deny() ? (Object)this : Text_(v);
		}
		else if	(ctx.Match(k, GfuiElemKeys.TipText_)) {
			String v = m.ReadStr("v");
			return ctx.Deny() ? (Object)this : TipText_(v);
		}
		else if	(ctx.Match(k, GfuiElemKeys.width_)) {
			int v = m.ReadInt("v");
			return ctx.Deny() ? (Object)this : Width_(v);
		}
		else if	(ctx.Match(k, GfuiElemKeys.height_)) {
			int v = m.ReadInt("v");
			return ctx.Deny() ? (Object)this : Height_(v);
		}
		else if	(ctx.Match(k, GfuiElemKeys.x_)) {
			int v = m.ReadInt("v");
			return ctx.Deny() ? (Object)this : X_(v);
		}
		else if	(ctx.Match(k, GfuiElemKeys.y_)) {
			int v = m.ReadInt("v");
			return ctx.Deny() ? (Object)this : Y_(v);
		}
		else if	(ctx.Match(k, GfuiElemKeys.TipText))				return TipText();
		else if	(ctx.Match(k, GfuiElemKeys.font_style_set)) {
			FontStyleAdp v = (FontStyleAdp)m.ReadObj("v", FontStyleAdp_.Parser);
			return ctx.Deny() ? (Object)this : textMgr.Font().Style_(v);
		}
		else if	(ctx.Match(k, GfuiElemKeys.fore_color_set)) {
			ColorAdp v = (ColorAdp)m.ReadObj("v", ColorAdp_.Parser);
			if (ctx.Deny()) return this;
			textMgr.Color_(v);
		}
		else if	(ctx.Match(k, GfuiElemKeys.back_color_set)) {
			ColorAdp v = (ColorAdp)m.ReadObj("v", ColorAdp_.Parser);
			if (ctx.Deny()) return this;
			BackColor_(v);
		}
		else if	(ctx.Match(k, GfuiElemKeys.font_get))				return textMgr.Font();
		else if	(ctx.Match(k, GfuiElemKeys.IptRcvd_evt))			return IptEventType.HandleEvt(this, ctx, m);

		else if	(ctx.Match(k, GfuiElemKeys.OwnerBox_prp))			return ownerElem;
		else if	(ctx.Match(k, GfuiElemKeys.Focus_cmd))				Focus();
		else if	(ctx.Match(k, GfuiElemKeys.ActionExec_cmd))			Click();
		else if	(ctx.Match(k, GfuiElemKeys.Zorder_front_cmd))		Zorder_front();
		else if (ctx.Match(k, Invk_OwnerWin_cmd))					return OwnerWin();
		else {
			if (ctx.Help_browseMode()) {
				String_bldr sb = String_bldr_.new_();
				for (int i = 0; i < this.SubElems().Count(); i++) {
					GfuiElem subE = (GfuiElem)this.SubElems().Get_at(i);
					sb.Add_str_w_crlf(subE.Key_of_GfuiElem());
				}
				return sb.To_str();
			}
			else {
				Object rv = this.InvkMgr().Invk(ctx, ikey, k, m, this);
				if (rv != GfoInvkAble_.Rv_unhandled) return rv;

				Object findObj = injected.Get_by(k);
				if (findObj == null) findObj = this.subElems.Get_by(k);
				if (findObj == null) return GfoInvkAble_.Rv_unhandled;				
				return findObj;	// necessary for gplx.images
			}
		}
		return this;
	}	public static final String Invk_OwnerWin_cmd = "ownerWin";

		public void Invoke(GfoInvkAbleCmd cmd) {
		cmd.Invk();
	}
		public Gfui_kit Kit() {return kit;} private Gfui_kit kit = Gfui_kit_.Mem();

	@gplx.Virtual public void ctor_GfuiBox_base(KeyValHash ctorArgs) {
		this.kit = Swing_kit._;	// NOTE: assume that callers want Swing; SWT / Mem should be calling ctor_kit_GfuiElemBase
		underElem = UnderElem_make(ctorArgs);
		underElem.Host_set(this);
		underMgr = underElem.Core();
		subElems = GfuiElemList.new_(this);
		textMgr = GfxStringData.new_(this, underElem);
		this.Focus_able_(Bool_.cast(ctorArgs.FetchValOr(GfuiElem_.InitKey_focusAble, true)));
		underMgr.Size_set(SizeAdp_.new_(20, 20));	// NOTE: CS inits to 20,20; JAVA inits to 0,0
	}
	@gplx.Virtual public void ctor_kit_GfuiElemBase(Gfui_kit kit, String key, GxwElem underElem, KeyValHash ctorArgs) {
		this.kit = kit;
		this.keyIdf = key;
		this.underElem = underElem;
		underElem.Host_set(this);
		underMgr = underElem.Core();
		subElems = GfuiElemList.new_(this);
		textMgr = GfxStringData.new_(this, underElem);
		this.Focus_able_(Bool_.cast(ctorArgs.FetchValOr(GfuiElem_.InitKey_focusAble, true)));
//			underMgr.Size_set(SizeAdp_.new_(20, 20));	// NOTE: CS inits to 20,20; JAVA inits to 0,0
	}
	@gplx.Virtual public GxwElem UnderElem_make(KeyValHash ctorArgs) {return GxwElemFactory_._.control_();}
	public Object SubItms_getObj(String key) {return injected.Get_by(key);}
	public GfuiElemBase SubItms_add(String key, Object v) {injected.Add(key, v); return this;}
	public Ordered_hash XtnAtrs() {return xtnAtrs;} Ordered_hash xtnAtrs = Ordered_hash_.new_();
	Hash_adp injected = Hash_adp_.new_();
	GxwCore_base underMgr;
	@gplx.Internal protected static boolean SizeChanged_ignore = false;
}
class GfuiFocusMgr implements GfoEvMgrOwner {
	public static final String FocusChanged_evt = "focusChanged_evt";
	public GfoEvMgr EvMgr() {if (evMgr == null) evMgr = GfoEvMgr.new_(this); return evMgr;} GfoEvMgr evMgr;
	public GfuiElem FocusedElem() {return focusedElem;} GfuiElem focusedElem;
	public GfuiElem FocusedElemPrev() {return focusedElemPrev;} GfuiElem focusedElemPrev;
	public void FocusedElem_set(GfuiElem focused) {
		focusedElemPrev = focusedElem;
		this.focusedElem = focused;
		GfoEvMgr_.PubVal(this, FocusChanged_evt, focused);
	}
	public static final GfuiFocusMgr _ = new GfuiFocusMgr(); GfuiFocusMgr() {}
}
