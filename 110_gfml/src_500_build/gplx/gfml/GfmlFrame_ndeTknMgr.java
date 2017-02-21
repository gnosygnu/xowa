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
class GfmlFrame_ndeTknMgr {		
	public int IdxAtrBgn() {return idxAtrBgn;} int idxAtrBgn, idxAtrEnd;
	public int IdxNdeBgn() {return idxNdeBgn;} public void IdxNdeBgn_set(int v) {idxNdeBgn = v;} int idxNdeBgn = 0;
	void IdxAtr_clear() {idxAtrBgn = 0; idxAtrEnd = -1;}
	@gplx.Internal protected void IdxAtrBgn_setHack(int v) {idxAtrBgn = v; idxAtrEnd = v + 1;}
	@gplx.Internal protected void IdxNdeBgn_setToAtrBgn() {
		if (idxAtrEnd == -1) return;
		idxNdeBgn = idxAtrBgn;
		this.IdxAtr_clear();	// newNode starting; old atr range no longer valid
	}
	public GfmlTkn HndTkn_pop() {GfmlTkn rv = hndTkn; hndTkn = GfmlTkn_.Null; return rv;} GfmlTkn hndTkn = GfmlTkn_.Null;
	public void HndTkn_setByDatTkn() {
		if (datTkn == GfmlTkn_.Null) return;
		hndTkn = DatTkn_pop();
	}
	@gplx.Internal protected void HndTkn_setByDatTknOrFail() {
		if (datTkn == GfmlTkn_.Null) {bldr.UsrMsgs_fail(GfmlUsrMsgs.fail_DatTkn_notFound()); return;}		// ex: < : >
		if (hndTkn != GfmlTkn_.Null) {bldr.UsrMsgs_fail(GfmlUsrMsgs.fail_HndTkn_alreadyExists()); return;}	// ex: < a:b: >
		hndTkn = DatTkn_pop();
	}		
	public GfmlTkn KeyTkn_pop() {GfmlTkn rv = keyTkn; keyTkn = GfmlTkn_.Null; return rv;} GfmlTkn keyTkn = GfmlTkn_.Null;
	public void KeyTkn_setByDatTkn() {
		if (datTkn == GfmlTkn_.Null) return;
		keyTkn = DatTkn_pop();
	}
	public void KeyTkn_setFromDataTknOrFail() {
		if (datTkn == GfmlTkn_.Null) {bldr.UsrMsgs_fail(GfmlUsrMsgs.fail_DatTkn_notFound()); return;}		// ex: < = >
		if (keyTkn != GfmlTkn_.Null) {bldr.UsrMsgs_fail(GfmlUsrMsgs.fail_KeyTkn_alreadyExists()); return;}	// ex: < a=[b=] >
		keyTkn = DatTkn_pop();
	}
	@gplx.Internal protected GfmlTkn DatTkn_pop() {GfmlTkn rv = datTkn; datTkn = GfmlTkn_.Null; return rv;} GfmlTkn datTkn = GfmlTkn_.Null;
	@gplx.Internal protected void DatTkn_set(GfmlTkn tkn) {
		this.ConsumeWaitingDatTkn(frame.CurNde());
		idxAtrBgn = frame.waitingTkns.Count();
		idxAtrEnd = idxAtrBgn + 1;
		frame.waitingTkns.Add(tkn);
		datTkn = tkn;
	}
	@gplx.Internal protected void ExecMakeAtr(GfmlTkn itmKeyTkn, GfmlTkn valTkn) {
		frame.waitingTkns.Add(valTkn);
		idxAtrEnd = frame.waitingTkns.Count();
		this.MakeAtr(itmKeyTkn, valTkn);
	}
	@gplx.Internal protected void ExecXferTkns_ndeAll(GfmlNde nde) {ExecXferTkns(nde, 0);}
	@gplx.Internal protected void ExecXferTkns_ndeBgn(GfmlNde nde) {ExecXferTkns(nde, this.IdxNdeBgn());}
	void ExecXferTkns(GfmlNde nde, int from) {
		this.ConsumeWaitingDatTkn(nde);
		GfmlFrame_nde_.TransferToNde(frame.waitingTkns, nde, from);
	}
	@gplx.Internal protected void ConsumeWaitingDatTkn(GfmlNde nde) {
		if (datTkn == GfmlTkn_.Null) return;			// no datTkn; return;
		if (	bldr.CurNdeFrame().CurNdeStartType() == GfmlNdeStartType.Dot
			&&	String_.Len_eq_0(nde.Hnd()))		// if cur hnd is empty, use datTkn for hndTkn
				frame.HndTkn_SetFromDatTkn();
		else
			this.MakeAtr(GfmlTkn_.Null, this.DatTkn_pop());
	}
	void MakeAtr(GfmlTkn k, GfmlTkn v) {
		if (k == GfmlTkn_.Null) 
			k = bldr.TypeMgr().FldPool().Keyed_PopNextAsTkn();
		GfmlAtr atr = GfmlAtr.string_(k, v);
		GfmlFrame_nde_.TransferToAtr(frame.waitingTkns, atr, idxAtrBgn, idxAtrEnd);
		frame.waitingTkns.Add_at(atr, idxAtrBgn);
		this.IdxAtr_clear();
	}
	GfmlFrame_nde frame; GfmlBldr bldr;
	public static GfmlFrame_ndeTknMgr new_(GfmlFrame_nde frame, GfmlBldr bldr) {
		GfmlFrame_ndeTknMgr rv = new GfmlFrame_ndeTknMgr();
		rv.frame = frame; rv.bldr = bldr;
		return rv;
	}	GfmlFrame_ndeTknMgr() {}
}
