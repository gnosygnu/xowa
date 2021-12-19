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
package gplx.gfml;
import gplx.core.texts.*; /*CharStream*/
import gplx.libs.dlgs.UsrMsg;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.lists.Hash_adp_;
public class GfmlBldr {
	public GfmlDoc			Doc() {return gdoc;} GfmlDoc gdoc = GfmlDoc.new_();
	public GfmlFrame			CurFrame() {return curFrame;} GfmlFrame curFrame;
	public GfmlFrame_nde		CurNdeFrame() {return curNdeFrame;} GfmlFrame_nde curNdeFrame;
	public GfmlNde			CurNde() {return curNdeFrame.CurNde();}
	public GfmlTypeMgr		TypeMgr() {return typeMgr;} GfmlTypeMgr typeMgr = GfmlTypeMgr.new_();
	public Hash_adp Vars() {return vars;} Hash_adp vars = Hash_adp_.New();
	public int				StreamPos()	{return streamPos;} int streamPos;
	public void				ThrowErrors_set(boolean v) {throwErrors = v;} private boolean throwErrors = true;
	public int PrvSymType() {return prvSymType;} public void PrvSymType_set(int v) {prvSymType = v;} int prvSymType = GfmlNdeSymType.Null;
	public int ChainIdNext() {return chainIdNext++;} int chainIdNext = 1;
	public GfmlDoc XtoGfmlDoc(String raw) {
		Init(raw);
		CharStream stream = CharStream.pos0_(raw);
		while (stream.AtMid()) {
			streamPos = stream.Pos();	// NOTE: streamPos is defined as just before curTkn
			GfmlTkn curTkn = curFrame.Lxr().MakeTkn(stream, 0);
			curTkn.Cmd_of_Tkn().Exec(this, curTkn);
			if (stopBldr) break;
		}
		if (!stopBldr) {
			curNdeFrame.Build_end(this, GfmlFrame_nde_.OwnerRoot_);
			if (frameStack.Count() > 0) UsrMsgs_fail(GfmlUsrMsgs.fail_Frame_danglingBgn());
		}
		if (throwErrors && gdoc.UsrMsgs().Len() > 0) throw GfmlUsrMsgs.gfmlParseError(this);
		raw = "";
		return gdoc;
	}
	void Init(String raw) {
		this.raw = raw; stopBldr = false; gdoc.Clear(); vars.Clear(); frameStack.Clear(); chainIdNext = 1; 
		curNdeFrame = GfmlFrame_nde_.root_(this, gdoc.RootNde(), gdoc.RootLxr());
		curNdeFrame.NullArea_set(true);
		curFrame = curNdeFrame;
	}
	public void Frames_push(GfmlFrame newFrame) {
		frameStack.Push(curFrame);
		curFrame = newFrame;
		if (curFrame.FrameType() == GfmlFrame_.Type_nde) curNdeFrame = GfmlFrame_nde_.as_(curFrame);
	}		
	public void Frames_end() {
		if (frameStack.Count() == 0) {
			UsrMsgs_fail(GfmlUsrMsgs.fail_Frame_danglingBgn());
			return;
		}
		GfmlFrame oldFrame = curFrame;
		curFrame = frameStack.Pop();
		oldFrame.Build_end(this, curFrame);
		if (curFrame.FrameType() == GfmlFrame_.Type_nde) curNdeFrame = GfmlFrame_nde_.as_(curFrame);
	}
	public void UsrMsgs_fail(UsrMsg um) {
		GfmlUsrMsgs.MakeErr(this, um, raw);
		stopBldr = true;
	}
	GfmlFrameStack frameStack = GfmlFrameStack.new_(); boolean stopBldr = false; String raw = "";
}
