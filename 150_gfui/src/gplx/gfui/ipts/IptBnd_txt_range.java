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
package gplx.gfui.ipts;
import gplx.core.interfaces.*;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.Gfo_invk_;
import gplx.frameworks.invks.GfsCtx;
import gplx.gfui.controls.standards.*;
import gplx.frameworks.evts.Gfo_evt_itm;
import gplx.frameworks.evts.Gfo_evt_mgr;
import gplx.frameworks.evts.Gfo_evt_mgr_;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.KeyVal;
public class IptBnd_txt_range implements InjectAble, Gfo_invk, Gfo_evt_itm {
	public Gfo_evt_mgr Evt_mgr() {if (evt_mgr == null) evt_mgr = new Gfo_evt_mgr(this); return evt_mgr;} Gfo_evt_mgr evt_mgr;
	public IptBnd_txt_range InitSrc_(Gfo_evt_itm initSrc) {this.initSrc = initSrc; return this;}
	public IptBnd_txt_range InitEvt_(String initEvt) {this.initEvt = initEvt; return this;}	String initEvt;
	public IptBnd_txt_range PropSrc_(String getListCmd, String getCmd, String setCmd, String setEvt) {
		this.getListCmd = getListCmd; this.getCmd = getCmd; this.setCmd = setCmd; this.setEvt = setEvt;
		return this;
	}	String getListCmd, getCmd, setCmd, setEvt;
	public IptBnd_txt_range PropList_(KeyVal[] list) {
		this.list = list;
		return this;
	}	KeyVal[] list = null;
	public void Inject(Object owner) {
		txtBox = GfuiTextBox_.cast(owner);
		txtBox.TextAlignH_center_();
		IptBnd_.cmd_to_(IptCfg_.Null, txtBox, this, Invk_dec, IptKey_.Down, IptMouseWheel_.Down);
		IptBnd_.cmd_to_(IptCfg_.Null, txtBox, this, Invk_inc, IptKey_.Up, IptMouseWheel_.Up);
		IptBnd_.cmd_to_(IptCfg_.Null, txtBox, this, Invk_upd, IptKey_.Enter, IptMouseBtn_.Middle);
		Gfo_evt_mgr_.Sub_same(initSrc, initEvt, this);
		Gfo_evt_mgr_.Sub_same(propSrc, setEvt, this);
	}	GfuiTextBox txtBox; int previewIdx; Gfo_evt_itm propSrc, initSrc; Gfo_invk propInvk; 
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.MatchPriv(k, Invk_dec))		PreviewCmd(-1);
		else if	(ctx.MatchPriv(k, Invk_inc))		PreviewCmd( 1);
		else if	(ctx.MatchPriv(k, Invk_upd))		return UpdateCmd();
		else if	(ctx.MatchPriv(k, setEvt))			WhenEvtCmd(m.CastObj("v"));
		else if (ctx.MatchPriv(k, initEvt))		ReadyEvtCmd();
		else	return Gfo_invk_.Rv_unhandled;
		return Gfo_invk_.Rv_handled;
	}	static final String Invk_dec = "txtBox_dec", Invk_inc = "txtBox_inc", Invk_upd = "txtBox_exec";
	void PreviewCmd(int delta) {
		int newVal = previewIdx + delta;
		if (!IntUtl.RangeCheck(newVal, list.length)) return;
		WhenEvtCmd(list[newVal].KeyAsObj());
	}
	Object UpdateCmd() {
		int idx = IntUtl.MinValue;
		String find = txtBox.Text();
		for (int i = 0; i < list.length; i++) {	// try to find .Text in list.Vals
			if (StringUtl.Eq(find, (String)list[i].Val())) idx = i;
		}
		if (idx == IntUtl.MinValue) {				// try to find .Text in list.Keys
			int key = IntUtl.ParseOr(txtBox.Text(), IntUtl.MinValue); if (key == IntUtl.MinValue) return Gfo_invk_.Rv_unhandled;
			idx = GetByKey(key); if (idx == IntUtl.MinValue) return Gfo_invk_.Rv_unhandled;
		}
		ExecCmd(setCmd, idx);
		return Gfo_invk_.Rv_handled;
	}
	void ReadyEvtCmd() {
		if (getListCmd != null)
			list = (KeyVal[])Gfo_invk_.Invk_by_key(propInvk, getListCmd);
		Object curId = Gfo_invk_.Invk_by_key(propInvk, getCmd);
		WhenEvtCmd(curId);
	}
	void WhenEvtCmd(Object id) {
		int idx = GetByKey(id); if (idx == IntUtl.MinValue) return;
		previewIdx = idx;
		txtBox.Text_(list[idx].ValToStrOrEmpty());
	}
	void ExecCmd(String c, int idx) {
		if (!IntUtl.RangeCheck(idx, list.length)) return;
		Gfo_invk_.Invk_by_val(propInvk, setCmd, list[idx].KeyAsObj());
	}		
	int GetByKey(Object find) {
		if (list == null) ReadyEvtCmd();
		for (int i = 0; i < list.length; i++) {
			if (ObjectUtl.Eq(find, list[i].KeyAsObj())) return i;
		}
		return IntUtl.MinValue;
	}
	public static IptBnd_txt_range new_(Gfo_evt_itm propSrc) {
		IptBnd_txt_range rv = new IptBnd_txt_range();
		rv.propSrc = propSrc; rv.propInvk = (Gfo_invk)propSrc;
		rv.initSrc = propSrc;
		return rv;
	}	IptBnd_txt_range() {}
}
