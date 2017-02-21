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
package gplx.gfui.controls.standards; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import gplx.gfui.draws.*; import gplx.gfui.gfxs.*; import gplx.gfui.imgs.*; import gplx.gfui.controls.gxws.*; import gplx.gfui.controls.elems.*; import gplx.gfui.controls.windows.*;
public class GfuiChkBox extends GfuiElemBase {
	public boolean Val() {return val;} private boolean val;
	public void Val_set(boolean val) {
		this.val = val;
		Gfo_evt_mgr_.Pub_val(this, "Check_end", val);
		GfuiBtn.DoThis(this, clickMsg, clickInvkCmd);
	}	
	public void Val_sync(boolean val) {
		this.val = val;//(boolean)v; // NOTE: do not resend message
		this.Redraw();
	}	
	@gplx.Internal protected void Click_msg_(GfoMsg v) {clickMsg = v;} GfoMsg clickMsg;
	@gplx.Internal protected GfuiChkBox Click_invk(Gfo_invk_cmd v) {clickInvkCmd = v; return this;} Gfo_invk_cmd clickInvkCmd;
	public GfuiBorderMgr FocusBorder() {return focusBorder;} GfuiBorderMgr focusBorder = GfuiBorderMgr.new_();
	@Override public boolean Click_able() {return true;}
	@Override public void Click() {
		Val_set(!val);
		this.Redraw();
	}
	public GfuiAlign AlignH() {return alignH;} public void AlignH_set(GfuiAlign v) {alignH = v;} GfuiAlign alignH = GfuiAlign_.Mid;
	public GfuiAlign AlignV() {return alignV;} public void AlignV_set(GfuiAlign v) {alignV = v;} GfuiAlign alignV = GfuiAlign_.Mid;
	public PointAdp ChkAlignCustom() {return chkAlignCustom;} public void ChkAlignCustom_set(PointAdp val) {chkAlignCustom = val;} PointAdp chkAlignCustom = PointAdp_.Zero;
	@gplx.Internal protected PenAdp ChkPen() {return chkPen;} PenAdp chkPen = PenAdp_.new_(ColorAdp_.Black);
	@Override public boolean PaintCbk(PaintArgs args) {
		super.PaintCbk(args);
		GfuiChkBox_.DrawCheckBox(this, args.Graphics());
		return true;
	}
	@Override public boolean SizeChangedCbk() {
		boolean rv = super.SizeChangedCbk();
		this.Redraw();
		return rv;
	}
	@Override public boolean FocusGotCbk() {super.FocusGotCbk(); this.Redraw(); return true;}	// Redraw for focusBorder
	@Override public boolean FocusLostCbk() {super.FocusLostCbk(); this.Redraw(); return true;}	// Redraw for focusBorder
	@Override public GxwElem UnderElem_make(Keyval_hash ctorArgs) {return GxwElemFactory_.Instance.lbl_();}
	@Override public void ctor_GfuiBox_base(Keyval_hash ctorArgs) {
		super.ctor_GfuiBox_base(ctorArgs);
		focusBorder.All_(PenAdp_.new_(ColorAdp_.Gray, 1));
		Inject_(GfuiFocusXferBnd.Instance).Inject_(GfuiBtnClickBnd.Instance);
		this.CustomDraw_set(true);
	}
}
