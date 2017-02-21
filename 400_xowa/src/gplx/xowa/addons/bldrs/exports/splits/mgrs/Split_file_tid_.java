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
package gplx.xowa.addons.bldrs.exports.splits.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
public class Split_file_tid_ {
	public static final byte Tid__core = 0, Tid__data = 1, Tid__temp = 2;
	public static byte To_tid(Io_url url) {
		if (!String_.Eq(url.Ext(), ".xowa")) return Tid__temp;
		String raw = url.NameAndExt();
		if		(String_.Has(raw, "_core."))	return Tid__core;
		else if (String_.Has(raw, ".sqlite"))	return Tid__temp;
		else									return Tid__data;
	}
	public static String Make_file_name(String wiki_abrv, String wiki_date, int idx, int ns, String ext) {// EX: Xowa_simplewiki_2016-05-01_pt.0001_ns.0014.xowa
		String ns_str = ns == -1 ? "_core" : String_.Format("_ns.{0}", Int_.To_str_pad_bgn_zero(ns, 4));
		return String_.Format("Xowa_{0}_{1}_part.{2}{3}{4}", wiki_abrv, wiki_date, Int_.To_str_pad_bgn_zero(idx, 4), ns_str, ext);
	}
	public static int Get_ns_by_url(Io_url fil) {
		String raw = fil.Raw();
		if (String_.Has(raw, "_core")) return -1;
		int bgn = String_.FindFwd(raw, "ns."); if (bgn == Bry_find_.Not_found) throw Err_.new_wo_type("could not find ns in url", "fil", raw);
		bgn += 3; // ns.
		int end = String_.FindFwd(raw, ".", bgn); if (end == Bry_find_.Not_found) throw Err_.new_wo_type("could not find ns in url", "fil", raw);
		return Int_.parse(String_.Mid(raw, bgn, end));
	}
}
