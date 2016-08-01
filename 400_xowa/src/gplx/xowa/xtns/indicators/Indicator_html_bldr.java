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
import gplx.core.brys.fmtrs.*;
public class Indicator_html_bldr implements gplx.core.brys.Bfr_arg {
	private Indicator_html_bldr_itm bldr_itm = new Indicator_html_bldr_itm();
	private Ordered_hash list = Ordered_hash_.New();
	public void Enabled_(boolean v) {enabled = v;} private boolean enabled = Bool_.Y;
	public void Clear() {
		enabled = Bool_.Y;
		list.Clear();
	}
	public int Count() {return list.Count();}
	public boolean Has(String key) {return list.Has(key);}
	public void Add(Indicator_xnde xnde) {
		if (!enabled) return;				// do not add if disabled; called from <page>; PAGE:en.s:The_Parochial_System_(Wilberforce,_1838); DATE:2015-04-29
		list.Add_if_dupe_use_nth(xnde.Name(), xnde);	// Add_if_dupe_use_nth: 2nd indicator overwrites 1st; DATE:2015-04-29
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		if (list.Count() == 0) return;		// do not build html if no items; DATE:2015-04-29
		bldr_itm.Init(list);
		fmtr_grp.Bld_bfr_many(bfr, bldr_itm);
	}
	private static final    Bry_fmtr
	  fmtr_grp = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "  <div class='mw-indicators'>~{itms}"
	, "  </div>"
	), "itms")
	;
}
class Indicator_html_bldr_itm implements gplx.core.brys.Bfr_arg {
	private Ordered_hash list;
	public void Init(Ordered_hash list) {this.list = list;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		int list_len = list.Count();
		for (int i = list_len - 1; i > -1; --i) {	// reverse order
			Indicator_xnde xnde = (Indicator_xnde)list.Get_at(i);
			fmtr_itm.Bld_bfr_many(bfr, xnde.Name(), xnde.Html());
		}
	}
	private static final    Bry_fmtr
	 fmtr_itm = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "    <div id='mw-indicator-~{name}' class='mw-indicator'>~{html}</div>"
	), "name", "html")
	;
}
