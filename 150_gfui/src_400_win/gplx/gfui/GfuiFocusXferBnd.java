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
class GfuiFocusXferBnd implements InjectAble, GfoInvkAble {
	public void Inject(Object owner) {
		GfuiElem elem = GfuiElem_.cast_(owner);		
		IptBnd_.cmd_to_(IptCfg_.Null, elem, this, Invk_FocusNext, IptKey_.Down);
		IptBnd_.cmd_to_(IptCfg_.Null, elem, this, Invk_FocusPrev, IptKey_.Up);
	}
	@gplx.Internal protected void Focus(GfuiElem cur, boolean fwd) {
		List_adp allElemsInOwnerWin = List_adp_.new_(); AddSubs(cur.OwnerWin(), allElemsInOwnerWin);
		int curIdx = allElemsInOwnerWin.Idx_of(cur);
		GfuiElem target = cur;
		while (true) {	// find next visible elem
			int cycle = TabBox_.Cycle(fwd, curIdx, allElemsInOwnerWin.Count());
			target = GfuiElem_.cast_(allElemsInOwnerWin.Get_at(cycle));
			if (target.Visible()) break;
			if (cycle == curIdx) break;	// either (a) one elem in allElemsInOwnerWin or (b) n elems, and cycled back to start; break, else infinite loop
			curIdx = cycle;
		}
		target.Focus();
	}
	void AddSubs(GfuiElem owner, List_adp allElemsInOwnerWin) {
		for (int i = 0; i < owner.SubElems().Count(); i++) {
			GfuiElemBase sub = (GfuiElemBase)owner.SubElems().Get_at(i);
			if (sub.Click_able()) allElemsInOwnerWin.Add(sub);
			AddSubs(sub, allElemsInOwnerWin);
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_FocusNext))			Focus(GfuiElem_.cast_(ctx.MsgSrc()), true);
		else if	(ctx.Match(k, Invk_FocusPrev))			Focus(GfuiElem_.cast_(ctx.MsgSrc()), false);
		else return GfoInvkAble_.Rv_unhandled;
		return this;
	}	public static final String Invk_FocusNext = "FocusNext", Invk_FocusPrev = "FocusPrev";
	public static final GfuiFocusXferBnd _ = new GfuiFocusXferBnd(); GfuiFocusXferBnd() {}
}