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
package gplx.gfui.controls.windows; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import gplx.core.envs.*;
import gplx.gfui.draws.*; import gplx.gfui.envs.*; import gplx.gfui.controls.gxws.*; import gplx.gfui.controls.elems.*; import gplx.gfui.controls.standards.*; import gplx.gfui.controls.windows.*;
public class GfuiWin_toaster extends GfuiWin {	public void ShowPopup(GfuiWin owner, String text, int interval) {
		this.TaskbarParkingWindowFix(owner);
		ShowPopup(text, interval);
	}
	void ShowPopup(String text, int interval) {
		if (Env_.Mode_testing()) return;
		messageLabel.Text_(text);
		messageLabel.SelBgn_set(0);
		InitVariables(500, interval * 1000, 500);
		BeginPoppingUp();
	}
	void InitVariables(int growingArg, int fullyGrownArg, int timeForHidingArg) {
		popupState = PopupState.FullyShrunk;
		fullyGrownTimerInterval = fullyGrownArg;
		int timerEvents = 0;
		if (growingArg > 10) {
			timerEvents = Math_.Min((growingArg / 10), fullyGrown.Height());
			growingTimerInterval = growingArg / timerEvents;
			growingIncrement = fullyGrown.Height() / timerEvents;
		}
		else {
			growingTimerInterval = 10;
			growingIncrement = fullyGrown.Height();
		}

		if( timeForHidingArg > 10) {
			timerEvents = Math_.Min((timeForHidingArg / 10), fullyGrown.Height());
			shrinkingTimerInterval = timeForHidingArg / timerEvents;
			shrinkingIncrement = fullyGrown.Height() / timerEvents;
		}
		else {
			shrinkingTimerInterval = 10;
			shrinkingIncrement = fullyGrown.Height();
		}
	}
	void BeginPoppingUp() {
		RectAdp screenRect = ScreenAdp_.Primary.Rect();//WorkingArea
		int screenX_max = screenRect.X() + screenRect.Width();
		int val = popupState.Val();
		if	(val == PopupState.FullyShrunk.Val()) {
			this.Size_(SizeAdp_.new_(this.Width(), 0));
			this.Pos_(screenX_max / 2 - this.Width()/2, PopupAnchorTop);	//screenRect.Bottom - 1
			//		gplx.gfui.npis.FormNpi.BringToFrontDoNotFocus(gplx.gfui.npis.ControlNpi.Hwnd(this.UnderElem()));
						if (!this.Visible()) {
//					GfuiElem last = GfuiFocusMgr.Instance.FocusedElem();
				this.Visible_on_();
//					GfuiFocusMgr.Instance.FocusedElem_set(last);
			}
			timer.Interval_(growingTimerInterval);
			popupState = PopupState.Growing;
		}
		else if	(val == PopupState.Growing.Val()) {
			this.Redraw();
		}
		else if	(val == PopupState.FullyGrown.Val()) {
			timer.Interval_(fullyGrownTimerInterval);
			this.Redraw();
		}
		else if	(val == PopupState.Shrinking.Val()) {
			this.Size_(SizeAdp_.new_(this.Width(), 0));
			this.Pos_(screenX_max / 2 - this.Width()/2, PopupAnchorTop);	//screenRect.Bottom - 1
			timer.Interval_(fullyGrownTimerInterval);
			popupState = PopupState.FullyGrown;
		}
		timer.Enabled_on();
	}
//		public override boolean FocusGotCbk() {
//			GfuiElem last = GfuiFocusMgr.Instance.FocusedElemPrev();
//			GfuiFocusMgr.Instance.FocusedElem_set(last);
//			last.Focus();
//			return false;
//		}

	void WhenTick() {
		int fullHeight = fullyGrown.Height();
		int val = popupState.Val();
		if (val == PopupState.Growing.Val()) {
			if (this.Height() < fullHeight)
				ChangeBounds(true, growingIncrement);
			else {
				this.Height_(fullHeight);
				timer.Interval_(fullyGrownTimerInterval);
				popupState = PopupState.FullyGrown;
			}
		}
		else if (val == PopupState.FullyGrown.Val()) {
			timer.Interval_(shrinkingTimerInterval);
			// if ((bKeepVisibleOnMouseOver && !bIsMouseOverPopup ) || (!bKeepVisibleOnMouseOver)) {
			popupState = PopupState.Shrinking;
		}
		else if (val == PopupState.Shrinking.Val()) {
			// if (bReShowOnMouseOver && bIsMouseOverPopup) {popupState = PopupState.Growing; break;}
			if (this.Height() > 2)	// NOTE.Val()) { does not shrink less than 2 //this.Top > screenRect.Bottom
				ChangeBounds(false, shrinkingIncrement);
			else {
//					this.Pos_(-500, -500);	// WORKAROUND:JAVA: cannot do this.Hide() b/c it will focus ownerForm; EX: typing in textApp when musicApp moves forward
				this.Visible_off_();
				popupState = PopupState.FullyShrunk;
				timer.Enabled_off();
			}
		}
	}
	static final    int PopupAnchorTop = -1;	// HACK: wxp1 showed obvious flickering with top edge
	void ChangeBounds(boolean isGrowing, int increment) {
		increment = isGrowing ? increment : -increment;
		this.Pos_(this.X(), PopupAnchorTop);	//this.Top - increment
		this.Size_(SizeAdp_.new_(this.Width(), this.Height() + increment));
	}
	@Override public GxwElem UnderElem_make(Keyval_hash ctorArgs) {return GxwElemFactory_.Instance.win_toaster_(ctorArgs);}

	@Override public void ctor_GfuiBox_base(Keyval_hash ctorArgs) {
		super.ctor_GfuiBox_base(ctorArgs);
		this.fullyGrown = SizeAdp_.new_(600, 96);
		this.Pos_(-100, -100); this.Size_(fullyGrown); super.Show(); super.Hide();// was 20,20; set to fullyGrown b/c of java
		messageLabel = GfuiTextBox_.multi_("messageLabel", this);
		messageLabel.Size_(fullyGrown.Width(), fullyGrown.Height()).ForeColor_(ColorAdp_.Green);
		messageLabel.TextMgr().Font_(FontAdp.new_("Arial", 8, FontStyleAdp_.Bold));
		messageLabel.Border_on_(true);
		messageLabel.Focus_able_(false);
//			this.Focus_able_(false);
//			this.UnderElem().Core().Focus_able_force_(false);
		timer = TimerAdp.new_(this, Tmr_cmd, 3000, false);

		GxwWin formRef = (GxwWin)this.UnderElem();
		if (formRef != null) {	// FIXME: nullCheck, needed for MediaPlaylistMgr_tst
			formRef.Pin_set(true);
			formRef.TaskbarVisible_set(false);
		}
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Tmr_cmd))		WhenTick();
		else super.Invk(ctx, ikey, k, m);
		return this;
	}	public static final    String Tmr_cmd = "Tmr";
	GfuiTextMemo messageLabel;
	TimerAdp timer;
	SizeAdp fullyGrown = SizeAdp_.Zero;
	int growingIncrement, shrinkingIncrement;
	int growingTimerInterval, shrinkingTimerInterval, fullyGrownTimerInterval;
	PopupState popupState = PopupState.FullyShrunk;
	public static GfuiWin_toaster new_(GfuiWin owner) {
		GfuiWin_toaster rv = new GfuiWin_toaster();
//			rv.Icon_(IconAdp.cfg_("popup"));
		rv.ctor_GfuiBox_base
			(new Keyval_hash()
			.Add(GfuiElem_.InitKey_focusAble, false)
			.Add(GfuiElem_.InitKey_ownerWin, owner)
			.Add(GfuiWin_.InitKey_winType, GfuiWin_.InitKey_winType_toaster)
			);
		return rv;
	}
}
class PopupState {
	public int Val() {return val;} int val;
	public PopupState(int v) {this.val = v;}
	public static final    PopupState
		  FullyShrunk	= new PopupState(1)
		, Growing		= new PopupState(2)
		, FullyGrown	= new PopupState(3)
		, Shrinking		= new PopupState(4)
		;
}
