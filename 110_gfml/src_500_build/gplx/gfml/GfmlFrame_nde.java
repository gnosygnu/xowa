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
package gplx.gfml; import gplx.*;
class GfmlFrame_nde extends GfmlFrame_base {
	@Override public int		FrameType()			{return GfmlFrame_.Type_nde;}
	public GfmlNde			CurNde()			{return nde;} GfmlNde nde;
	public int				CurNdeStartType()	{return ndeStartType;} int ndeStartType;
	public GfmlDocPos		CurDocPos()			{return docPos;} GfmlDocPos docPos = GfmlDocPos_.Root;
	public void				NullArea_set(boolean v) {nullArea = v;} private boolean nullArea = false;
	public void DatTkn_set(GfmlTkn datTkn) {		// < data >
		if (nullArea) {								// tkn is unnamed atrVal for new nde; EX: < a:; data >
			nullArea = false;
			GfmlFrame_nde frame = NewFrame(GfmlNdeStartType.DatTkn);
			frame.tknMgr.DatTkn_set(datTkn);
		}
		else {
			GfmlTkn itmKeyTkn = tknMgr.KeyTkn_pop();
			if (itmKeyTkn != GfmlTkn_.Null)			// elemKey is pending: tkn is atrVal; EX: < a=data >
				tknMgr.ExecMakeAtr(itmKeyTkn, datTkn);
			else									// tkn is unnamed atrVal; EX: < a datTkn >
				tknMgr.DatTkn_set(datTkn);
		}
	}
	public void NdeBody_bgn(GfmlTkn symTkn) {		// EX: < { >
		WaitingTkns_AddSym(symTkn, GfmlNdeSymType.BodyBgn);
		GfmlFrame_nde frame = this;
		if (nullArea)								// EX: {{  NOTE: else nde is in header; don't begin nde; EX: a:{
			frame = NewFrame(GfmlNdeStartType.Brace);
		tknMgr.ExecXferTkns_ndeBgn(this.CurNde());
		frame.nullArea = true;
	}
	public void NdeInline(GfmlTkn symTkn) {			// ; 
		WaitingTkns_AddSym(symTkn, GfmlNdeSymType.Inline);
		if (nullArea)								// empty node; EX: < ;; > NOTE: this will not be called if dataTkn is present, since dataTkn starts node; EX: < a; >
			NewFrame(GfmlNdeStartType.Inline);
		bldr.Frames_end();							// NOTE: no need for frame.nullArea = true b/c frame is ended
	}
	public void KeyTkn_set(GfmlTkn symTkn) {		// EX: < = >
		WaitingTkns_AddSym(symTkn, GfmlNdeSymType.Key);
		tknMgr.KeyTkn_setFromDataTknOrFail();
	}
	public void NdeHeader_set(GfmlTkn symTkn) {		// EX: < : >
		WaitingTkns_AddSym(symTkn, GfmlNdeSymType.Hnd);
		tknMgr.IdxNdeBgn_setToAtrBgn();
		tknMgr.HndTkn_setByDatTknOrFail();
		GfmlTkn hnd = tknMgr.HndTkn_pop();
		GfmlFrame_nde frame = this;
		if (bldr.CurNdeFrame().CurNdeStartType() == GfmlNdeStartType.Prop)	// EX: < [a: > - must start new sub node;
			frame = NewFrame(GfmlNdeStartType.Hnd);
		frame.CurNde().HndTkn_set(hnd);
		bldr.TypeMgr().NdeResolve(frame.CurNde(), ownerTypeKey);
	}
	public void NdeDot(GfmlTkn symTkn) {			// EX: < . >
		GfmlFrame_nde frm = this, oldFrm = this;
		GfmlTkn hndTkn = HndTkn_GetForDotOp(frm);	// get hndTkn; needed for (a) newFrm if prpNde; or (b) curFrm 
		boolean createPrpNde = dotSymCreatesPrpNde;	// get createPrpNde b/c it will be reused later in this proc
		if (createPrpNde) {							// inside parens; . will create a prpNde EX: a:(b.c); vs a.b
			frm = NewFrame(GfmlNdeStartType.Dot);
			frm.nde.KeyedSubObj_(true);				// NOTE: must set prpNdes to keyed
			frm.nde.ChainId_(bldr.ChainIdNext());
		}
		if (hndTkn != GfmlTkn_.Null) {				// NOTE: need to check for Null to handle ")."; EX: a.b().c(); 2nd . would return Null and overwrite b
			if (frm.nde.HndTkn() == GfmlTkn_.Null)
				frm.nde.HndTkn_set(hndTkn);
			else
				tknMgr.ExecMakeAtr(GfmlTkn_.Null, hndTkn);
		}
		frm.WaitingTkns_AddSym(symTkn, GfmlNdeSymType.Dot);	// REVIEW: may want to dump into differnt nde...
		frm.tknMgr.ExecXferTkns_ndeBgn(frm.CurNde());
		oldFrm = frm;								// get oldFrm
		frm = frm.NewFrame(GfmlNdeStartType.Dot);
		NdeDot_SetChainId(createPrpNde, oldFrm, frm);
	}
	public void NdeParen_bgn(GfmlTkn symTkn) {		// EX: < ( >
		HndTkn_SetFromDatTkn();
		WaitingTkns_AddSym(symTkn, GfmlNdeSymType.HdrBgn);	// REVIEW: may want to dump into differnt nde...
		dotSymCreatesPrpNde = true;
		frmHasParens = true;
	}
	public void NdeParen_end(GfmlTkn symTkn) {		// EX: < ) >
		tknMgr.ConsumeWaitingDatTkn(bldr.CurNde());
		WaitingTkns_AddSym(symTkn, GfmlNdeSymType.HdrEnd);
		dotSymCreatesPrpNde = false;
		if (!frmHasParens) {
			bldr.Frames_end();
			bldr.CurNdeFrame().frmHasParens = false; // does this assume that auto-closing stops at paren node?
		}
	}
	public void AtrSpr(GfmlTkn symTkn) {			// EX: < , > NOTE: proc resembles NdeParen_end
		tknMgr.ConsumeWaitingDatTkn(bldr.CurNde());
		WaitingTkns_AddSym(symTkn, GfmlNdeSymType.AtrSpr);
		if (!frmHasParens) {
			bldr.Frames_end();
			GfmlFrame_nde frm = bldr.CurNdeFrame(); // get the current frm after all popped
			int idx = frm.waitingTkns.Count();		// NOTE: reset idxs b/c endFrame will automatically set to 0, and should be objCount
			frm.tknMgr.IdxAtrBgn_setHack(idx);
			frm.tknMgr.IdxNdeBgn_set(idx);
			frm.nullArea = false;					// NOTE: endFrame automatically sets nullArea to true; set to false
		}
	}
	public void NdeProp_bgn(GfmlTkn symTkn) {		// EX: < [ >
		int oldNdeBgn = tknMgr.IdxNdeBgn();	// get oldNdeBgn; needed for header atrs; EX: < a:b [d] > ndeBgn = 0 (a pos), but will start keyNde at 5 ([ pos)
		GfmlTkn keyTkn = tknMgr.KeyTkn_pop(); boolean keyTknExists = keyTkn != GfmlTkn_.Null;
		int newNdeBgn = keyTknExists ? tknMgr.IdxAtrBgn() : waitingTkns.Count(); // if there is a key, set ndeBgn end atrBgn (EX: a=[); else set end curIdx
		tknMgr.IdxNdeBgn_set(newNdeBgn);
		WaitingTkns_AddSym(symTkn, GfmlNdeSymType.PrpBgn);
		tknMgr.ConsumeWaitingDatTkn(nde);// NEEDED for KeydDefault
		GfmlFrame_nde frame = NewFrame(GfmlNdeStartType.Prop);
		tknMgr.IdxNdeBgn_set(oldNdeBgn);	// restore oldNdeBgn
		if (!keyTknExists) {
			keyTkn = bldr.TypeMgr().FldPool().Keyed_PopNextAsTkn();
		}
		frame.CurNde().KeyTkn_set(keyTkn);
		bldr.TypeMgr().NdeResolve(frame.CurNde(), ownerTypeKey);
		frame.nullArea = false;
	}
	GfmlFrame_nde NewFrame(int newNdeFrameType) {
		GfmlDocPos		newPos = docPos.NewDown(subNdeCount++);
		GfmlNde			newNde = GfmlNde.new_(tknMgr.HndTkn_pop(), GfmlType_.Null, newNdeFrameType == GfmlNdeStartType.Prop).DocPos_(newPos);
		GfmlFrame_nde	newFrm = GfmlFrame_nde_.node_(bldr, newNde);
		newFrm.ndeStartType = newNdeFrameType;
		newFrm.ownerTypeKey = this.CurNde().Type().Key();
		tknMgr.ExecXferTkns_ndeBgn(newNde);
		bldr.Frames_push(newFrm);
		bldr.TypeMgr().NdeBgn(newNde, newFrm.ownerTypeKey);
		bldr.Doc().PragmaMgr().BgnCmds_exec(newPos, bldr);
		return newFrm;
	}
	@Override public void Build_end(GfmlBldr bldr, GfmlFrame ownerFrame) {
		GfmlFrame_nde ownerNdeFrame = (GfmlFrame_nde)ownerFrame;
		tknMgr.ExecXferTkns_ndeAll(nde);
		bldr.TypeMgr().NdeEnd();
		bldr.Doc().PragmaMgr().EndCmds_exec(docPos, bldr);
		bldr.Doc().PragmaMgr().Pragmas_compile(this.CurNde().Hnd(), bldr);
		if (ownerFrame == null) return; // rootFrame; ignore below
		ownerNdeFrame.waitingTkns.Add(this.CurNde());
		int ndeType = this.CurNdeStartType();
		if (ndeType == GfmlNdeStartType.Dot){ // if i was created by dot, close owner;
			if (ownerNdeFrame.nde.ChainId() != this.nde.ChainId()) {	// can also be if (nde.ChainHead())
			}
			else
				bldr.Frames_end();	// auto-close dot ndes
		}
		else if (ndeType == GfmlNdeStartType.Prop) {}
		else {
			ownerNdeFrame.tknMgr.IdxNdeBgn_set(ownerNdeFrame.waitingTkns.Count());
			ownerNdeFrame.nullArea = true;	// reset
		}
	}
	@Override protected GfmlFrame_base MakeNew_hook() {return new GfmlFrame_nde();}
	public void IdxNdeBgn_set(int v) {tknMgr.IdxNdeBgn_set(v);}
	@gplx.Internal protected void WaitingTkns_AddSym(GfmlTkn tkn, int type) {waitingTkns.Add(tkn); bldr.PrvSymType_set(type);}
	@gplx.Internal protected void HndTkn_SetFromDatTkn() {
		GfmlTkn hndTkn = tknMgr.DatTkn_pop();
		if (hndTkn != GfmlTkn_.Null)
			nde.HndTkn_set(hndTkn);
	}
	GfmlTkn HndTkn_GetForDotOp(GfmlFrame_nde frm) {
		if (bldr.PrvSymType() == GfmlNdeSymType.HdrEnd) return GfmlTkn_.Null; // if prev was ), ignore; EX: a(). does not have hndTkn
		GfmlTkn hndTkn = frm.tknMgr.DatTkn_pop();
//			if (hndTkn == GfmlTkn_.Null) bldr.UsrMsgs_fail(GfmlUsrMsgs.fail_DatTkn_notFound());
		return hndTkn;
	}
	void NdeDot_SetChainId(boolean createPrpNde, GfmlFrame_nde oldFrm, GfmlFrame_nde frm) {
		if (createPrpNde)							// prpNde; make sure new dotNde gets same chainId
			frm.nde.ChainId_(oldFrm.nde.ChainId());
		else {										// oldNde is dotNde
			if (oldFrm.ndeStartType == GfmlNdeStartType.Dot)	// oldNde is dotNde; just take oldNde's chainId; ex: a.b.c
				frm.nde.ChainId_(oldFrm.nde.ChainId());
			else {									// oldNde is something else
				int chainId = bldr.ChainIdNext();
				oldFrm.nde.ChainId_(chainId);		// NOTE: needed for auto-closing; EX: {a.b;} a is a DatTkn nde, but needs to be closed when b is closed
				frm.nde.ChainId_(chainId);
			}
		}
	}
	GfmlBldr bldr; GfmlFrame_ndeTknMgr tknMgr; int subNdeCount = 0; String ownerTypeKey; boolean frmHasParens = false; boolean dotSymCreatesPrpNde = false;
	public static GfmlFrame_nde new_(GfmlBldr bldr, GfmlNde newNde, GfmlLxr newLxr) {
		GfmlFrame_nde rv = new GfmlFrame_nde();
		rv.bldr = bldr;
		rv.ctor_(newLxr);
		rv.tknMgr = GfmlFrame_ndeTknMgr.new_(rv, bldr);
		rv.nde = newNde;
		rv.docPos = newNde.DocPos();
		return rv;
	}
}
class GfmlFrame_nde_ {
	public static GfmlFrame_nde as_(Object obj) {return obj instanceof GfmlFrame_nde ? (GfmlFrame_nde)obj : null;}
	public static GfmlFrame_nde node_(GfmlBldr bldr, GfmlNde newNde) {return GfmlFrame_nde.new_(bldr, newNde, bldr.CurFrame().Lxr());}
	@gplx.Internal protected static GfmlFrame_nde root_(GfmlBldr bldr, GfmlNde newNde, GfmlLxr newLxr) {return GfmlFrame_nde.new_(bldr, newNde, newLxr);}
	@gplx.Internal protected static final GfmlFrame_nde OwnerRoot_ = null;
	@gplx.Internal protected static void TransferToNde(GfmlObjList waitingTkns, GfmlNde nde, int bgn) {
		int end = waitingTkns.Count();
		for (int i = bgn; i < end; i++) {
			GfmlObj tkn = waitingTkns.Get_at(i);
			nde.SubObjs_Add(tkn);
		}
		if (bgn != end) // ignore if bgn == end
			waitingTkns.Del_range(bgn, end - 1);
	}
	@gplx.Internal protected static void TransferToAtr(GfmlObjList src, GfmlAtr trg, int bgn, int end) {
		int len = end - bgn;
		if (len <= 0 || end == -1) return;	// -1 b/c calling proc passes end - 1, and end may be 0
		for (int i = 0; i < len; i++)
			trg.SubObjs_Add(src.Get_at(i + bgn));
		src.Del_range(bgn, end - 1);
	}		
}
class GfmlNdeStartType {
	public static final int
			  Hnd			= 1			// a:
			, Brace			= 2			// {	// has no header (since header would have created node first)
			, DatTkn		= 3			// c;	// has no hnd
			, Inline		= 4			// ;	// is empty (since something would have created node first)
			, Dot			= 5			// a.b;
			, Prop			= 6			// a=[b c];
			;
}
class GfmlNdeSymType {
	public static final int
		  Null		=  0
		, BodyBgn	=  1 // {
		, BodyEnd	=  2 // }
		, Inline	=  3 // ;
		, Key		=  4 // =
		, Hnd		=  5 // :
		, Dot		=  6 // .
		, HdrBgn	=  7 // (
		, HdrEnd	=  8 // )
		, PrpBgn	=  9 // [
		, PrpEnd	= 10 // ]
		, AtrSpr	= 11 // ,	
		;
}
