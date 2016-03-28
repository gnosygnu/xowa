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
public class GfuiChkBox extends GfuiElemBase {
	public boolean Val() {return val;} private boolean val;
	public void Val_set(boolean val) {
		this.val = val;
		GfoEvMgr_.PubVal(this, "Check_end", val);
		GfuiBtn.DoThis(this, clickMsg, clickInvkCmd);
	}	
	public void Val_sync(boolean val) {
		this.val = val;//(boolean)v; // NOTE: do not resend message
		this.Redraw();
	}	
	@gplx.Internal protected void Click_msg_(GfoMsg v) {clickMsg = v;} GfoMsg clickMsg;
	@gplx.Internal protected GfuiChkBox Click_invk(GfoInvkAbleCmd v) {clickInvkCmd = v; return this;} GfoInvkAbleCmd clickInvkCmd;
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
