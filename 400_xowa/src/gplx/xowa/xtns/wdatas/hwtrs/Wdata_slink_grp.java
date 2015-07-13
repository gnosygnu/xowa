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
package gplx.xowa.xtns.wdatas.hwtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.xowa.xtns.wdatas.core.*; import gplx.xowa.wikis.*; import gplx.xowa.apis.xowa.html.*;
class Wdata_slink_grp {
	public Wdata_slink_grp(int tid, byte[] wiki_name, Xoapi_toggle_itm toggle_itm, Wdata_toc_data toc_data) {
		this.tid = tid; this.wiki_name = wiki_name; this.toggle_itm = toggle_itm; this.toc_data = toc_data;
	}
	public int Tid() {return tid;} private final int tid;
	public byte[] Wiki_name() {return wiki_name;} private final byte[] wiki_name;
	public Wdata_toc_data Toc_data() {return toc_data;} private final  Wdata_toc_data toc_data;
	public Xoapi_toggle_itm Toggle_itm() {return toggle_itm;} private Xoapi_toggle_itm toggle_itm;
	public Ordered_hash Rows() {return rows;} private final Ordered_hash rows = Ordered_hash_.new_();
	public static void Sift(Wdata_slink_grp[] rv, Ordered_hash list) {
		for (int i = 0; i < Idx__len; ++i)
			rv[i].Rows().Clear();
		int list_len = list.Count();
		for (int i = 0; i < list_len; ++i) {
			Wdata_sitelink_itm itm = (Wdata_sitelink_itm)list.Get_at(i);				
			int idx = Idx_by_tid(itm.Domain_info().Domain_tid());
			rv[idx].Rows().Add(itm.Site(), itm);
		}
	}
	public static int Idx_by_tid(int tid) {
		switch (tid) {
			case Xow_domain_type_.Tid_wikipedia:			return Idx_w;
			case Xow_domain_type_.Tid_wiktionary:		return Idx_d;
			case Xow_domain_type_.Tid_wikisource:		return Idx_s;
			case Xow_domain_type_.Tid_wikivoyage:		return Idx_v;
			case Xow_domain_type_.Tid_wikiquote:			return Idx_q;
			case Xow_domain_type_.Tid_wikibooks:			return Idx_b;
			case Xow_domain_type_.Tid_wikiversity:		return Idx_u;
			case Xow_domain_type_.Tid_wikinews:			return Idx_n;
			default:									return Idx_x;
		}
	}
	public static byte[] Msg_by_tid(Wdata_hwtr_msgs msgs, int tid) {
		switch (tid) {
			case Idx_w: return msgs.Slink_tbl_hdr_w();
			case Idx_d: return msgs.Slink_tbl_hdr_d();
			case Idx_s: return msgs.Slink_tbl_hdr_s();
			case Idx_v: return msgs.Slink_tbl_hdr_v();
			case Idx_q: return msgs.Slink_tbl_hdr_q();
			case Idx_b: return msgs.Slink_tbl_hdr_b();
			case Idx_u: return msgs.Slink_tbl_hdr_u();
			case Idx_n: return msgs.Slink_tbl_hdr_n();
			case Idx_x: return msgs.Slink_tbl_hdr_x();
			default:	throw Exc_.new_unhandled(tid);
		}
	}
	public static byte[] Name_by_tid(int idx) {
		switch (idx) {
			case Idx_w: return Xow_domain_type_.Key_bry_wikipedia;
			case Idx_d: return Xow_domain_type_.Key_bry_wiktionary;
			case Idx_s: return Xow_domain_type_.Key_bry_wikisource;
			case Idx_v: return Xow_domain_type_.Key_bry_wikivoyage;
			case Idx_q: return Xow_domain_type_.Key_bry_wikiquote;
			case Idx_b: return Xow_domain_type_.Key_bry_wikibooks;
			case Idx_u: return Xow_domain_type_.Key_bry_wikiversity;
			case Idx_n: return Xow_domain_type_.Key_bry_wikinews;
			case Idx_x: return Name_special;
			default:	throw Exc_.new_unhandled(idx);
		}
	}
	public static final int Idx__len = 9, Idx_w = 0, Idx_d = 1, Idx_s = 2, Idx_v = 3, Idx_q = 4, Idx_b = 5, Idx_u = 6, Idx_n = 7, Idx_x = 8;
	private static final byte[] Name_special = Bry_.new_a7("special");
}
