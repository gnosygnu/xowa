/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.wbases.hwtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.apps.apis.xowa.html.*;
import gplx.xowa.wikis.domains.*;
class Wdata_slink_grp {
	public Wdata_slink_grp(int tid, byte[] wiki_name, Xoapi_toggle_itm toggle_itm, Wdata_toc_data toc_data) {
		this.tid = tid; this.wiki_name = wiki_name; this.toggle_itm = toggle_itm; this.toc_data = toc_data;
	}
	public int Tid() {return tid;} private final    int tid;
	public byte[] Wiki_name() {return wiki_name;} private final    byte[] wiki_name;
	public Wdata_toc_data Toc_data() {return toc_data;} private final     Wdata_toc_data toc_data;
	public Xoapi_toggle_itm Toggle_itm() {return toggle_itm;} private Xoapi_toggle_itm toggle_itm;
	public Ordered_hash Rows() {return rows;} private final    Ordered_hash rows = Ordered_hash_.New();
	public static void Sift(Wdata_slink_grp[] rv, Ordered_hash list) {
		for (int i = 0; i < Idx__len; ++i)
			rv[i].Rows().Clear();
		int list_len = list.Count();
		for (int i = 0; i < list_len; ++i) {
			Wdata_sitelink_itm itm = (Wdata_sitelink_itm)list.Get_at(i);				
			int idx = Idx_by_tid(itm.Domain_info().Domain_type_id());
			rv[idx].Rows().Add(itm.Site(), itm);
		}
	}
	public static int Idx_by_tid(int tid) {
		switch (tid) {
			case Xow_domain_tid_.Tid__wikipedia:			return Idx_w;
			case Xow_domain_tid_.Tid__wiktionary:		return Idx_d;
			case Xow_domain_tid_.Tid__wikisource:		return Idx_s;
			case Xow_domain_tid_.Tid__wikivoyage:		return Idx_v;
			case Xow_domain_tid_.Tid__wikiquote:			return Idx_q;
			case Xow_domain_tid_.Tid__wikibooks:			return Idx_b;
			case Xow_domain_tid_.Tid__wikiversity:		return Idx_u;
			case Xow_domain_tid_.Tid__wikinews:			return Idx_n;
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
			default:	throw Err_.new_unhandled(tid);
		}
	}
	public static byte[] Name_by_tid(int idx) {
		switch (idx) {
			case Idx_w: return Xow_domain_tid_.Bry__wikipedia;
			case Idx_d: return Xow_domain_tid_.Bry__wiktionary;
			case Idx_s: return Xow_domain_tid_.Bry__wikisource;
			case Idx_v: return Xow_domain_tid_.Bry__wikivoyage;
			case Idx_q: return Xow_domain_tid_.Bry__wikiquote;
			case Idx_b: return Xow_domain_tid_.Bry__wikibooks;
			case Idx_u: return Xow_domain_tid_.Bry__wikiversity;
			case Idx_n: return Xow_domain_tid_.Bry__wikinews;
			case Idx_x: return Name_special;
			default:	throw Err_.new_unhandled(idx);
		}
	}
	public static final int Idx__len = 9, Idx_w = 0, Idx_d = 1, Idx_s = 2, Idx_v = 3, Idx_q = 4, Idx_b = 5, Idx_u = 6, Idx_n = 7, Idx_x = 8;
	private static final    byte[] Name_special = Bry_.new_a7("special");
}
