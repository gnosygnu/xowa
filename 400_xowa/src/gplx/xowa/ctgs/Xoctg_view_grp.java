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
package gplx.xowa.ctgs; import gplx.*; import gplx.xowa.*;
public class Xoctg_view_grp {
	public Xoctg_view_grp(byte tid) {this.tid = tid;}
	public byte Tid() {return tid;} private byte tid;
	public boolean Hdr_exists() {return hdr_exists;} public Xoctg_view_grp Hdr_exists_(boolean v) {hdr_exists = v; return this;} private boolean hdr_exists = true;
	public int Bgn() {return bgn;} public Xoctg_view_grp Bgn_(int v) {bgn = v; return this;} private int bgn;
	public int End() {return end;} public Xoctg_view_grp End_(int v) {end = v; return this;} private int end;
	public int Len() {return end - bgn;}
	public int Max() {return max;} public Xoctg_view_grp Max_(int v) {max = v; return this;} private int max;
	public int All() {return all;} public Xoctg_view_grp All_(int v) {all = v; return this;} private int all;
	public int Total() {return total;} public Xoctg_view_grp Total_(int v) {total = v; return this;} private int total;
	public Xoctg_view_itm[] Itms() {return itms;} public Xoctg_view_grp Itms_(Xoctg_view_itm[] v) {itms = v; return this;} private Xoctg_view_itm[] itms = Xoctg_view_itm.Ary_empty;
	public Xoctg_view_itm Itms_at_first() {return Itms_at(0);}
	public Xoctg_view_itm Itms_at_last() {return Itms_at(itms.length - 1);}
	Xoctg_view_itm Itms_at(int i) {
		if (i < 0 || i >= itms.length) throw Err_.new_fmt_("ctg.view: i is out of bounds; i={0} len={1} tid={2}", i, itms.length, tid);
		Xoctg_view_itm rv = itms[i]; if (rv == null) throw Err_.new_fmt_("ctg.view: itm is null; i={0} len={1} tid={2}", i, itms.length, tid);
		return rv;
	}
	public void Itms_add(Xoctg_view_itm sub) {tmp_list.Add(sub);}
	public List_adp Itms_list() {return tmp_list;} List_adp tmp_list = List_adp_.new_();
	public void Itms_make() {itms = (Xoctg_view_itm[])tmp_list.To_ary(Xoctg_view_itm.class);}
	public byte[] Itms_last_sortkey() {return itms_last_sortkey;} public Xoctg_view_grp Itms_last_sortkey_(byte[] v) {itms_last_sortkey = v; return this;} private byte[] itms_last_sortkey;
}
