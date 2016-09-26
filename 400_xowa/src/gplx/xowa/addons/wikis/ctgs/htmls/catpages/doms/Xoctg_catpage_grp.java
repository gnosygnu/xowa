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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*;
public class Xoctg_catpage_grp {
	private final    List_adp tmp_list = List_adp_.New();
	public Xoctg_catpage_grp(byte tid) {this.tid = tid;}
	public byte					Tid()				{return tid;} private byte tid;					// subc|page|file
	public int					Bgn()				{return bgn;} private int bgn;					// idx of 1st item; EX: 200
	public int					End()				{return end;} private int end;					// idx of nth item + 1; EX: 399
	public int					Count_by_page()		{return end - bgn;}								// count of items on page; EX: 200
	public int					Itms__len()			{return itms_len;} private int itms_len;		// count of items in entire category; EX: 456
	public Xoctg_catpage_itm[]	Itms()				{return itms;} private Xoctg_catpage_itm[] itms = Xoctg_catpage_itm.Ary_empty;
	public Xoctg_catpage_itm	Itms__get_at(int i) {
		if (i < 0 || i >= itms.length) throw Err_.new_wo_type("catpage: i is out of bounds", "i", i, "len", itms.length, "tid", tid);
		Xoctg_catpage_itm rv = itms[i]; if (rv == null) throw Err_.new_wo_type("ctg.view: itm is null", "i", i, "len", itms.length, "tid", tid);
		return rv;
	}
	public void					Itms__add(Xoctg_catpage_itm sub) {tmp_list.Add(sub);}
	public void					Itms__make() {
		tmp_list.Sort_by(Xoctg_catpage_itm_sorter__sort_key.Sorter);
		itms = (Xoctg_catpage_itm[])tmp_list.To_ary_and_clear(Xoctg_catpage_itm.class);
		bgn = 0;
		end = itms_len = itms.length;
	}
	public void Rng_(int bgn, int end) {
		this.bgn = bgn; this.end = end;
	}
}
