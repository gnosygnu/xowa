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
package gplx.xowa.wikis.tdbs.stats; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.tdbs.*;
import gplx.core.strings.*;
import gplx.xowa.wikis.nss.*;
public class Xob_stat_mgr {
	public Xob_stat_type GetOrNew(byte tid) {
		Xob_stat_type rv = (Xob_stat_type)regy.Get_by(tid);
		if (rv == null) {
			rv = new Xob_stat_type(tid);
			regy.Add(tid, rv);
		}
		return rv;
	}
	public String Print(Xow_ns_mgr nsMgr) {
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < regy.Count(); i++) {
			Xob_stat_type typ = (Xob_stat_type)regy.Get_at(i);
			sb.Add(String_.PadEnd(Xotdb_dir_info_.Tid_name(typ.Tid()), 6, " "));
		}
		sb.Add_str_w_crlf("ns");
		String[] nsAry = GetNmsAry(nsMgr);
		for (String ns : nsAry) {
			for (int i = 0; i < regy.Count(); i++) {
				Xob_stat_type typ = (Xob_stat_type)regy.Get_at(i);
				Xob_stat_itm itm = (Xob_stat_itm)typ.GetOrNew(ns);
				sb.Add(Int_.To_str_pad_bgn_zero(itm.Fils, 5)).Add(" ");
			}
			sb.Add_str_w_crlf(ns);
		}
		return sb.To_str();
	}
	public String To_str() {
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < regy.Count(); i++) {
			Xob_stat_type typ = (Xob_stat_type)regy.Get_at(i);
			typ.To_str(sb);
		}
		return sb.To_str();
	}
	String[] GetNmsAry(Xow_ns_mgr nsMgr) {
		Ordered_hash nsRegy = Ordered_hash_.New();
		for (int i = 0; i < regy.Count(); i++) {
			Xob_stat_type typ = (Xob_stat_type)regy.Get_at(i);
			for (int j = 0; j < typ.Count(); j++) {
				Xob_stat_itm itm = (Xob_stat_itm)typ.GetAt(j);
				if (!nsRegy.Has(itm.Ns()))
					nsRegy.Add_as_key_and_val(itm.Ns());
			}
		}
		return (String[])nsRegy.To_ary(String.class);
	}
	Ordered_hash regy = Ordered_hash_.New();
}
