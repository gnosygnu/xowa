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
package gplx.xowa.xtns.indicators; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Indicator_html_bldr implements Bry_fmtr_arg {
	private Indicator_html_bldr_itm bldr_itm = new Indicator_html_bldr_itm();
	private ListAdp list = ListAdp_.new_();
	public void Add(Indicator_xnde xnde) {list.Add(xnde);}
	public void XferAry(Bry_bfr bfr, int idx) {
		bldr_itm.Init(list);
		fmtr_grp.Bld_bfr_many(bfr, bldr_itm);
	}
	private static final Bry_fmtr
	  fmtr_grp = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "  <div class='mw-indicators'>~{itms}"
	, "  </div>"
	), "itms")
	;
}
class Indicator_html_bldr_itm implements Bry_fmtr_arg {
	private ListAdp list;
	public void Init(ListAdp list) {this.list = list;}
	public void XferAry(Bry_bfr bfr, int idx) {
		int list_len = list.Count();
		for (int i = list_len - 1; i > -1; --i) {	// reverse order
			Indicator_xnde xnde = (Indicator_xnde)list.FetchAt(i);
			fmtr_itm.Bld_bfr(bfr, xnde.Name(), xnde.Html());
		}
	}
	private static final Bry_fmtr
	 fmtr_itm = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "    <div id='mw-indicator-~{name}' class='mw-indicator'>~{html}</div>"
	), "name", "html")
	;
}
