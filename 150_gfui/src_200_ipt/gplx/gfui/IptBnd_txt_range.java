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
public class IptBnd_txt_range implements InjectAble, GfoInvkAble, GfoEvObj {
	public GfoEvMgr EvMgr() {if (evMgr == null) evMgr = GfoEvMgr.new_(this); return evMgr;} GfoEvMgr evMgr;
	public IptBnd_txt_range InitSrc_(GfoEvObj initSrc) {this.initSrc = initSrc; return this;}
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
		GfoEvMgr_.SubSame(initSrc, initEvt, this);
		GfoEvMgr_.SubSame(propSrc, setEvt, this);
	}	GfuiTextBox txtBox; int previewIdx; GfoEvObj propSrc, initSrc; GfoInvkAble propInvk; 
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.MatchPriv(k, Invk_dec))		PreviewCmd(-1);
		else if	(ctx.MatchPriv(k, Invk_inc))		PreviewCmd( 1);
		else if	(ctx.MatchPriv(k, Invk_upd))		return UpdateCmd();
		else if	(ctx.MatchPriv(k, setEvt))			WhenEvtCmd(m.CastObj("v"));
		else if (ctx.MatchPriv(k, initEvt))		ReadyEvtCmd();
		else	return GfoInvkAble_.Rv_unhandled;
		return GfoInvkAble_.Rv_handled;
	}	static final String Invk_dec = "txtBox_dec", Invk_inc = "txtBox_inc", Invk_upd = "txtBox_exec";
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
			int key = Int_.parse_or(txtBox.Text(), Int_.Min_value); if (key == Int_.Min_value) return GfoInvkAble_.Rv_unhandled;
			idx = GetByKey(key); if (idx == Int_.Min_value) return GfoInvkAble_.Rv_unhandled;
		}
		ExecCmd(setCmd, idx);
		return GfoInvkAble_.Rv_handled;
	}
	void ReadyEvtCmd() {
		if (getListCmd != null)
			list = (KeyVal[])GfoInvkAble_.InvkCmd(propInvk, getListCmd);
		Object curId = GfoInvkAble_.InvkCmd(propInvk, getCmd);
		WhenEvtCmd(curId);
	}
	void WhenEvtCmd(Object id) {
		int idx = GetByKey(id); if (idx == Int_.Min_value) return;
		previewIdx = idx;
		txtBox.Text_(list[idx].Val_to_str_or_empty());
	}
	void ExecCmd(String c, int idx) {
		if (!Int_.RangeCheck(idx, list.length)) return;
		GfoInvkAble_.InvkCmd_val(propInvk, setCmd, list[idx].Key_as_obj());
	}		
	int GetByKey(Object find) {
		if (list == null) ReadyEvtCmd();
		for (int i = 0; i < list.length; i++) {
			if (Object_.Eq(find, list[i].Key_as_obj())) return i;
		}
		return Int_.Min_value;
	}
	public static IptBnd_txt_range new_(GfoEvObj propSrc) {
		IptBnd_txt_range rv = new IptBnd_txt_range();
		rv.propSrc = propSrc; rv.propInvk = GfoInvkAble_.as_(propSrc);
		rv.initSrc = propSrc;
		return rv;
	}	IptBnd_txt_range() {}
//        public static IptBnd_txt_range new_() {return new IptBnd_txt_range();}
}
