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
package gplx.gfml; import gplx.*;
import gplx.core.texts.*; /*CharStream*/ 
public class GfmlBldr {
	@gplx.Internal protected GfmlDoc			Doc() {return gdoc;} GfmlDoc gdoc = GfmlDoc.new_();
	@gplx.Internal protected GfmlFrame			CurFrame() {return curFrame;} GfmlFrame curFrame;
	@gplx.Internal protected GfmlFrame_nde		CurNdeFrame() {return curNdeFrame;} GfmlFrame_nde curNdeFrame;
	@gplx.Internal protected GfmlNde			CurNde() {return curNdeFrame.CurNde();}
	@gplx.Internal protected GfmlTypeMgr		TypeMgr() {return typeMgr;} GfmlTypeMgr typeMgr = GfmlTypeMgr.new_();
	@gplx.Internal protected Hash_adp				Vars() {return vars;} Hash_adp vars = Hash_adp_.New();
	@gplx.Internal protected int				StreamPos()	{return streamPos;} int streamPos;
	@gplx.Internal protected void				ThrowErrors_set(boolean v) {throwErrors = v;} private boolean throwErrors = true;
	@gplx.Internal protected int PrvSymType() {return prvSymType;} @gplx.Internal protected void PrvSymType_set(int v) {prvSymType = v;} int prvSymType = GfmlNdeSymType.Null;
	@gplx.Internal protected int ChainIdNext() {return chainIdNext++;} int chainIdNext = 1;
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
		if (throwErrors && gdoc.UsrMsgs().Count() > 0) throw GfmlUsrMsgs.gfmlParseError(this);
		raw = "";
		return gdoc;
	}
	void Init(String raw) {
		this.raw = raw; stopBldr = false; gdoc.Clear(); vars.Clear(); frameStack.Clear(); chainIdNext = 1; 
		curNdeFrame = GfmlFrame_nde_.root_(this, gdoc.RootNde(), gdoc.RootLxr());
		curNdeFrame.NullArea_set(true);
		curFrame = curNdeFrame;
	}
	@gplx.Internal protected void Frames_push(GfmlFrame newFrame) {
		frameStack.Push(curFrame);
		curFrame = newFrame;
		if (curFrame.FrameType() == GfmlFrame_.Type_nde) curNdeFrame = GfmlFrame_nde_.as_(curFrame);
	}		
	@gplx.Internal protected void Frames_end() {
		if (frameStack.Count() == 0) {
			UsrMsgs_fail(GfmlUsrMsgs.fail_Frame_danglingBgn());
			return;
		}
		GfmlFrame oldFrame = curFrame;
		curFrame = frameStack.Pop();
		oldFrame.Build_end(this, curFrame);
		if (curFrame.FrameType() == GfmlFrame_.Type_nde) curNdeFrame = GfmlFrame_nde_.as_(curFrame);
	}
	@gplx.Internal protected void UsrMsgs_fail(UsrMsg um) {
		GfmlUsrMsgs.MakeErr(this, um, raw);
		stopBldr = true;
	}
	GfmlFrameStack frameStack = GfmlFrameStack.new_(); boolean stopBldr = false; String raw = "";
}
