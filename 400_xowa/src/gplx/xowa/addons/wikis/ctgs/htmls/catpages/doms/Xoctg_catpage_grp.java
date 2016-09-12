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
	public Xoctg_catpage_grp(byte tid) {this.tid = tid;}
	public byte					Tid()				{return tid;} private byte tid;	// subc|page|file
	public int					Bgn()				{return bgn;} private int bgn;
	public int					End()				{return end;} private int end;
	public int					Len()				{return end - bgn;}
	public int					Total()				{return total;} private int total;
	public Xoctg_catpage_itm[]	Itms()				{return itms;} private Xoctg_catpage_itm[] itms = Xoctg_catpage_itm.Ary_empty;
	public byte[]				Itms__nth_sortkey() {return itms__nth_sortkey;}
	public Xoctg_catpage_itm	Itms__get_at_0th()	{return Itms__get_at(0);}
	public Xoctg_catpage_itm	Itms__get_at_nth()	{return Itms__get_at(itms.length - 1);}
	private Xoctg_catpage_itm	Itms__get_at(int i) {
		if (i < 0 || i >= itms.length) throw Err_.new_wo_type("ctg.view: i is out of bounds", "i", i, "len", itms.length, "tid", tid);
		Xoctg_catpage_itm rv = itms[i]; if (rv == null) throw Err_.new_wo_type("ctg.view: itm is null", "i", i, "len", itms.length, "tid", tid);
		return rv;
	}
	public void					Itms__add(Xoctg_catpage_itm sub) {tmp_list.Add(sub);}
	public void					Itms__make() {
		itms = (Xoctg_catpage_itm[])tmp_list.To_ary(Xoctg_catpage_itm.class);
		total = end = itms.length;
	}
	public Xoctg_catpage_grp Itms__nth_sortkey_(byte[] v) {itms__nth_sortkey = v; return this;} private byte[] itms__nth_sortkey;

	public void Init(int bgn, int end) {this.bgn = bgn; this.end = end;}	// TEST:
	public List_adp Itms_list() {return tmp_list;} private final    List_adp tmp_list = List_adp_.New();	// TEST
}
