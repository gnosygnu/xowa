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
package gplx.xowa.wikis.tdbs.hives; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.tdbs.*;
import gplx.core.lists.*;
public class Xowd_regy_mgr_ {
	public static int FindSlot(ComparerAble comparer, Object[] ary, Object itm) {if (itm == null) throw Err_.new_null();
		int aryLen = ary.length;
		switch (aryLen) {
			case 0: throw Err_.new_wo_type("ary cannot have 0 itms");
			case 1: return 0;
		}
		int lo = -1, hi = aryLen - 1; // NOTE: -1 is necessary; see test
		int curPos = (hi - lo) / 2;
		int delta = 1;
		while (true) {
			Object curSeg = ary[curPos];
			int comp = curSeg == null ? CompareAble_.More : comparer.compare(itm, curSeg);	// nulls should only happen for lastAry 
//				if (dbg) {
//					Tfds.Write(curPos, itm.toString(), comp, comp.toString(), curSeg.toString());
//				}
			if		(comp == CompareAble_.Same) return curPos;
			else if	(comp >  CompareAble_.Same) {lo = curPos; delta = 1;}
			else if	(comp <  CompareAble_.Same) {hi = curPos; delta = -1;}
			int dif = hi - lo;
			if (dif == 1 || dif == 0)	return hi;	// NOTE: can be 0 when ary.length == 1 || 2; also, sometimes 0 in some situations
			else						curPos += (dif / 2) * delta;
		}
	}
}
