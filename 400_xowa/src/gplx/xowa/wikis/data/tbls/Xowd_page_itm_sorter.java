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
package gplx.xowa.wikis.data.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
import gplx.core.lists.*; /*ComparerAble*/ import gplx.xowa.wikis.data.tbls.*;
public class Xowd_page_itm_sorter implements ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Xowd_page_itm lhs = (Xowd_page_itm)lhsObj, rhs = (Xowd_page_itm)rhsObj;
		return order * Compare_rows(compareType, lhs, rhs);
	}
	private static int Compare_rows(byte compareType, Xowd_page_itm lhs, Xowd_page_itm rhs) {
		switch (compareType) {
			case Tid_ns_ttl:	{
				int rv = Int_.Compare(lhs.Ns_id(), rhs.Ns_id());
				return rv == CompareAble_.Same ? Bry_.Compare(lhs.Ttl_page_db(), rhs.Ttl_page_db()) : rv;
			}
			case Tid_itm_len:	return Int_.Compare(lhs.Text_len(), rhs.Text_len());
			case Tid_id:		return Int_.Compare(lhs.Id(), rhs.Id());
			case Tid_ttl:		return Bry_.Compare(lhs.Ttl_page_db(), rhs.Ttl_page_db());
			case Tid_ctg_tid_sortkey:
				gplx.xowa.addons.wikis.ctgs.Xoctg_page_xtn lhs_xtn = (gplx.xowa.addons.wikis.ctgs.Xoctg_page_xtn)lhs.Xtn();
				gplx.xowa.addons.wikis.ctgs.Xoctg_page_xtn rhs_xtn = (gplx.xowa.addons.wikis.ctgs.Xoctg_page_xtn)rhs.Xtn();
				if (lhs_xtn == null || rhs_xtn == null) return CompareAble_.Same;
				int tid_comparable = Byte_.Compare(lhs_xtn.Tid(), rhs_xtn.Tid());
				if (tid_comparable != CompareAble_.Same) return tid_comparable;
				return Bry_.Compare(lhs_xtn.Sortkey(), rhs_xtn.Sortkey());
			default:			throw Err_.new_unhandled(compareType);
		}
	}
	Xowd_page_itm_sorter(byte compareType, int order) {this.compareType = compareType; this.order = order;}
	byte compareType; int order;
	static final byte Tid_ns_ttl = 0, Tid_itm_len = 2, Tid_id = 3, Tid_ttl = 4, Tid_ctg_tid_sortkey = 5;
	static final int Asc = 1, Dsc = -1;
	public static final    Xowd_page_itm_sorter TitleAsc				= new Xowd_page_itm_sorter(Tid_ttl				, Asc);
	public static final    Xowd_page_itm_sorter EnyLenDsc				= new Xowd_page_itm_sorter(Tid_itm_len			, Dsc);
	public static final    Xowd_page_itm_sorter IdAsc					= new Xowd_page_itm_sorter(Tid_id				, Asc);
	public static final    Xowd_page_itm_sorter Ns_id_TtlAsc			= new Xowd_page_itm_sorter(Tid_ns_ttl			, Asc);
	public static final    Xowd_page_itm_sorter Ctg_tid_sortkey_asc	= new Xowd_page_itm_sorter(Tid_ctg_tid_sortkey	, Asc);
}
