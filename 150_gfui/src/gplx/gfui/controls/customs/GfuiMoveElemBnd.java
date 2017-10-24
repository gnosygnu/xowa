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
import gplx.gfui.ipts.*; import gplx.gfui.controls.elems.*;
import gplx.core.interfaces.*;
public class GfuiMoveElemBnd implements IptBnd, Gfo_invk, InjectAble {
	public String Key() {return "gplx.gfui.moveWidget";}
	public List_adp Ipts() {return args;} List_adp args = List_adp_.New();
	public IptEventType EventTypes() {return IptEventType_.add_(IptEventType_.KeyDown, IptEventType_.MouseDown, IptEventType_.MouseMove, IptEventType_.MouseUp);}
	public void Exec(IptEventData iptData) {
		int val = iptData.EventType().Val();
		if		(val == IptEventType_.KeyDown.Val())		ExecKeyDown(iptData);
		else if (val == IptEventType_.MouseDown.Val())		ExecMouseDown(iptData);
		else if (val == IptEventType_.MouseUp.Val())		ExecMouseUp(iptData);
		else if (val == IptEventType_.MouseMove.Val())		ExecMouseMove(iptData);
	}
	public GfuiElem TargetElem() {return targetElem;} public void TargetElem_set(GfuiElem v) {this.targetElem = v;} GfuiElem targetElem;
	public static final    String target_idk = "target";
	@gplx.Virtual public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, target_idk))				return targetElem;
		else if	(ctx.Match(k, "key"))					return key;
		else return Gfo_invk_.Rv_unhandled;
	}

	public String Key_of_GfuiElem() {return keyIdf;} public void Key_of_GfuiElem_(String val) {keyIdf = val;} private String keyIdf = "moveElemBtnBnd";
	public void Inject(Object owner) {
		GfuiElem ownerElem = (GfuiElem)owner;
		ownerElem.IptBnds().Add(this);
		ownerElem.IptBnds().EventsToFwd_set(IptEventType_.None);	// NOTE: suppress bubbling else ctrl+right will cause mediaPlayer to jump forward
	}

	void ExecMouseDown(IptEventData msg) {
		moving = true;
		anchor = msg.MousePos();
	}
	void ExecMouseMove(IptEventData msg) {
		if (!moving) return;
		targetElem.Pos_(msg.MousePos().Op_subtract(anchor).Op_add(targetElem.Pos()));
	}
	void ExecMouseUp(IptEventData msg) {
		moving = false;
	}
	void ExecKeyDown(IptEventData msg) {
		PointAdp current = targetElem.Pos();
		PointAdp offset = PointAdp_.cast(hash.Get_by(msg.EventArg()));
		targetElem.Pos_(current.Op_add(offset));
	}
	@gplx.Internal protected void Key_set(String key) {this.key = key;} private String key;
	public Object Srl(GfoMsg owner) {return IptBnd_.Srl(owner, this);}

	boolean moving = false;
	PointAdp anchor = PointAdp_.Zero; Hash_adp hash = Hash_adp_.New();
	public static GfuiMoveElemBnd new_() {return new GfuiMoveElemBnd();}
	GfuiMoveElemBnd() {
		args.Add_many(IptMouseBtn_.Left, IptMouseMove.AnyDirection);
		IptBndArgsBldr.AddWithData(args, hash, IptKey_.Ctrl.Add(IptKey_.Up), PointAdp_.new_(0, -10));
		IptBndArgsBldr.AddWithData(args, hash, IptKey_.Ctrl.Add(IptKey_.Down), PointAdp_.new_(0, 10));
		IptBndArgsBldr.AddWithData(args, hash, IptKey_.Ctrl.Add(IptKey_.Left), PointAdp_.new_(-10, 0));
		IptBndArgsBldr.AddWithData(args, hash, IptKey_.Ctrl.Add(IptKey_.Right), PointAdp_.new_(10, 0));
	}
}
class IptBndArgsBldr {
	public static void AddWithData(List_adp list, Hash_adp hash, IptArg arg, Object data) {
		list.Add(arg);
		hash.Add(arg, data);			
	}
}
