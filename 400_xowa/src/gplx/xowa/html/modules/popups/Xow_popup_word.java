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
package gplx.xowa.html.modules.popups; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.modules.*;
public class Xow_popup_word {
	public Xow_popup_word(int tid, int bfr_bgn, int idx, int bgn, int end, Xop_tkn_itm tkn) {this.tid = tid; this.bfr_bgn = bfr_bgn; this.idx = idx; this.bgn = bgn; this.end = end; this.tkn = tkn;}
	public int Tid() {return tid;} private int tid;
	public int Bfr_bgn() {return bfr_bgn;} private int bfr_bgn;
	public int Bfr_end() {return bfr_bgn + this.Len();}
	public int Idx() {return idx;} private int idx;
	public int Bgn() {return bgn;} private int bgn;
	public int End() {return end;} private int end;
	public int Len() {return end - bgn;}
	public Xop_tkn_itm Tkn() {return tkn;} private Xop_tkn_itm tkn;
}
