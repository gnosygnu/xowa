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
package gplx.gfui.controls.customs; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import gplx.gfui.ipts.*; import gplx.gfui.layouts.*; import gplx.gfui.controls.gxws.*; import gplx.gfui.controls.elems.*; import gplx.gfui.controls.standards.*; import gplx.gfui.controls.windows.*;
public class GfuiMoveElemBtn extends GfuiBtn { 	@Override public GxwElem UnderElem_make(Keyval_hash ctorArgs) {return GxwElemFactory_.Instance.lbl_();}
	@Override public void ctor_GfuiBox_base(Keyval_hash ctorArgs) {
		super.ctor_GfuiBox_base(ctorArgs);
		this.Text_("*");
		this.TipText_("move/resize");

		this.IptBnds().EventsToFwd_set(IptEventType_.None);
		this.IptBnds().Add(moveBinding);
		this.IptBnds().Add(GfuiResizeFormBnd.new_());
	}
	public void TargetElem_set(GfuiElem v) {moveBinding.TargetElem_set(v);}

	final    GfuiMoveElemBnd moveBinding = GfuiMoveElemBnd.new_();
	public static GfuiMoveElemBtn new_() {
		GfuiMoveElemBtn rv = new GfuiMoveElemBtn();
		rv.ctor_GfuiBox_base(GfuiElem_.init_focusAble_true_());
		return rv;
	}
}
class GfuiResizeFormBnd implements IptBnd {
	public String Key() {return "gplx.gfui.resizeForm";}
	public List_adp Ipts() {return args;} List_adp args = List_adp_.New();
	public IptEventType EventTypes() {return IptEventType_.KeyDown.Add(IptEventType_.MouseDown).Add(IptEventType_.MouseUp).Add(IptEventType_.MouseMove);}
	public void Exec(IptEventData iptData) {
		int val = iptData.EventType().Val();
		if		(val == IptEventType_.KeyDown.Val())		ExecKeyDown(iptData);
		else if (val == IptEventType_.MouseDown.Val())	ExecMouseDown(iptData);
		else if (val == IptEventType_.MouseUp.Val())		ExecMouseUp(iptData);
		else if (val == IptEventType_.MouseMove.Val())	ExecMouseMove(iptData);
	}
	void ExecMouseDown(IptEventData iptData) {
		active = true;
		lastPos = iptData.MousePos();
	}
	void ExecMouseMove(IptEventData iptData) {
		if (!active) return;

		PointAdp delta = iptData.MousePos().Op_subtract(lastPos);
		if (delta.Eq(PointAdp_.Zero)) return;
		ResizeForm(iptData.Sender(), XtoSize(delta));
	}
	void ExecMouseUp(IptEventData iptData) {
		active = false;
	}
	void ExecKeyDown(IptEventData iptData) {
		SizeAdp deltaSize = (SizeAdp)hash.Get_by(iptData.EventArg());
		ResizeForm(iptData.Sender(), deltaSize);
	}
	void ResizeForm(GfuiElem elem, SizeAdp deltaSize) {
		GfuiWin form = elem.OwnerWin();
		SizeAdp newSize = Op_add(form.Size(), deltaSize);
		form.Size_(newSize);
		GftGrid.LytExecRecur(form);
		form.Redraw();	// must redraw, else artifacts will remain; ex: grid may leave random lines
	}
	static SizeAdp XtoSize(PointAdp point) {return SizeAdp_.new_(point.X(), point.Y());}
	static SizeAdp Op_add(SizeAdp lhs, SizeAdp rhs) {return SizeAdp_.new_(lhs.Width() + rhs.Width(), lhs.Height() + rhs.Height());}
	public Object Srl(GfoMsg owner) {return IptBnd_.Srl(owner, this);}

	boolean active = false; PointAdp lastPos = PointAdp_.Zero; Hash_adp hash = Hash_adp_.New();
	public static GfuiResizeFormBnd new_() {return new GfuiResizeFormBnd();}
	GfuiResizeFormBnd() {
		args.Add_many(IptMouseBtn_.Right, IptMouseMove.AnyDirection);
		IptBndArgsBldr.AddWithData(args, hash, IptKey_.Ctrl.Add(IptKey_.Shift).Add(IptKey_.Up), SizeAdp_.new_(0, -10));
		IptBndArgsBldr.AddWithData(args, hash, IptKey_.Ctrl.Add(IptKey_.Shift).Add(IptKey_.Down), SizeAdp_.new_(0, 10));
		IptBndArgsBldr.AddWithData(args, hash, IptKey_.Ctrl.Add(IptKey_.Shift).Add(IptKey_.Left), SizeAdp_.new_(-10, 0));
		IptBndArgsBldr.AddWithData(args, hash, IptKey_.Ctrl.Add(IptKey_.Shift).Add(IptKey_.Right), SizeAdp_.new_(10, 0));
	}
}
