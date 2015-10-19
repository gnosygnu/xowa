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
package gplx.xowa.xtns.pfuncs.times; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.langs.*;
public class Pft_func_formatdate_bldr {
	public int Idx_cur() {return idx_cur;} private int idx_cur;
	public Pft_func_formatdate_bldr Idx_nxt_(int v) {idx_nxt = v; return this;} private int idx_nxt;
	public Pft_fmt_itm[] Fmt_itms() {return fmt_itms;} Pft_fmt_itm[] fmt_itms;
	public void Format(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_fmt_itm fmt_itm) {
		fmt_itm.Fmt(bfr, wiki, lang, date, this);
	}
	public void Format(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_fmt_itm[] fmt_itms) {
		this.fmt_itms = fmt_itms;
		int len = fmt_itms.length;
		idx_cur = 0; idx_nxt = -1;
		Pft_fmt_itm last = null;
		while (idx_cur < len) {
			Pft_fmt_itm fmt_itm = fmt_itms[idx_cur];
			if (fmt_itm.TypeId() == Pft_fmt_itm_.Tid_hebrew_numeral)
				last = fmt_itm;
			else {
				fmt_itm.Fmt(bfr, wiki, lang, date, this);
			}
			if (idx_nxt == -1)
				++idx_cur;
			else {
				idx_cur = idx_nxt;
				idx_nxt = -1;
			}
		}
		if (last != null) {
			int year_int = bfr.To_int_and_clear(-1);
			if (year_int != -1) {	// handle no format; EX:{{#time:xh}} DATE:2014-07-20
				date = DateAdp_.seg_(new int[]  {year_int, date.Month(), date.Day(), date.Hour(), date.Minute(), date.Second(), date.Frac()});
				last.Fmt(bfr, wiki, lang, date, this);
			}
		}
	}
}
