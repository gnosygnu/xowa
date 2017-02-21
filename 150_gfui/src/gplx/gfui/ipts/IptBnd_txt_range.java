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
package gplx.gfui.ipts; import gplx.*; import gplx.gfui.*;
import gplx.core.interfaces.*;
import gplx.gfui.controls.standards.*;
public class IptBnd_txt_range implements InjectAble, Gfo_invk, Gfo_evt_itm {
	public Gfo_evt_mgr Evt_mgr() {if (evt_mgr == null) evt_mgr = new Gfo_evt_mgr(this); return evt_mgr;} Gfo_evt_mgr evt_mgr;
	public IptBnd_txt_range InitSrc_(Gfo_evt_itm initSrc) {this.initSrc = initSrc; return this;}
	public IptBnd_txt_range InitEvt_(String initEvt) {this.initEvt = initEvt; return this;}	String initEvt;
	public IptBnd_txt_range PropSrc_(String getListCmd, String getCmd, String setCmd, String setEvt) {
		this.getListCmd = getListCmd; this.getCmd = getCmd; this.setCmd = setCmd; this.setEvt = setEvt;
		return this;
	}	String getListCmd, getCmd, setCmd, setEvt;
	public IptBnd_txt_range PropList_(Keyval[] list) {
		this.list = list;
		return this;
	}	Keyval[] list = null;
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
	}	static final    String Invk_dec = "txtBox_dec", Invk_inc = "txtBox_inc", Invk_upd = "txtBox_exec";
	void PreviewCmd(int delta) {
		int newVal = previewIdx + delta;
		if (!Int_.RangeCheck(newVal, list.length)) return;
		WhenEvtCmd(list[newVal].Key_as_obj());
	}
	Object UpdateCmd() {
		int idx = Int_.Min_value;
		String find = txtBox.Text();
		for (int i = 0; i < list.length; i++) {	// try to find .Text in list.Vals
			if (String_.Eq(find, (String)list[i].Val())) idx = i;
		}
		if (idx == Int_.Min_value) {				// try to find .Text in list.Keys
			int key = Int_.parse_or(txtBox.Text(), Int_.Min_value); if (key == Int_.Min_value) return Gfo_invk_.Rv_unhandled;
			idx = GetByKey(key); if (idx == Int_.Min_value) return Gfo_invk_.Rv_unhandled;
		}
		ExecCmd(setCmd, idx);
		return Gfo_invk_.Rv_handled;
	}
	void ReadyEvtCmd() {
		if (getListCmd != null)
			list = (Keyval[])Gfo_invk_.Invk_by_key(propInvk, getListCmd);
		Object curId = Gfo_invk_.Invk_by_key(propInvk, getCmd);
		WhenEvtCmd(curId);
	}
	void WhenEvtCmd(Object id) {
		int idx = GetByKey(id); if (idx == Int_.Min_value) return;
		previewIdx = idx;
		txtBox.Text_(list[idx].Val_to_str_or_empty());
	}
	void ExecCmd(String c, int idx) {
		if (!Int_.RangeCheck(idx, list.length)) return;
		Gfo_invk_.Invk_by_val(propInvk, setCmd, list[idx].Key_as_obj());
	}		
	int GetByKey(Object find) {
		if (list == null) ReadyEvtCmd();
		for (int i = 0; i < list.length; i++) {
			if (Object_.Eq(find, list[i].Key_as_obj())) return i;
		}
		return Int_.Min_value;
	}
	public static IptBnd_txt_range new_(Gfo_evt_itm propSrc) {
		IptBnd_txt_range rv = new IptBnd_txt_range();
		rv.propSrc = propSrc; rv.propInvk = (Gfo_invk)propSrc;
		rv.initSrc = propSrc;
		return rv;
	}	IptBnd_txt_range() {}
}
