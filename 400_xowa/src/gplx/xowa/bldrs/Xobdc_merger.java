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
package gplx.xowa.bldrs; import gplx.*; import gplx.xowa.*;
import gplx.core.ios.*; import gplx.core.lists.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.bldrs.wtrs.*;
public class Xobdc_merger {
	public static void Basic(Gfo_usr_dlg usr_dlg, Io_url_gen dump_url_gen, Io_url sort_dir, int memory_max, Io_line_rdr_key_gen key_gen, Io_sort_cmd make_cmd) {Basic(usr_dlg, dump_url_gen, sort_dir, memory_max, Io_sort_split_itm_sorter.Instance, key_gen, make_cmd);}
	public static void Basic(Gfo_usr_dlg usr_dlg, Io_url_gen dump_url_gen, Io_url sort_dir, int memory_max, ComparerAble row_comparer, Io_line_rdr_key_gen key_gen, Io_sort_cmd make_cmd) {
		Io_sort sort = new Io_sort().Memory_max_(memory_max);
		Io_url_gen sort_url_gen = Io_url_gen_.dir_(sort_dir);
		sort.Split(usr_dlg, dump_url_gen, sort_url_gen, row_comparer, key_gen);
		sort.Merge(usr_dlg, sort_url_gen.Prv_urls(), row_comparer, key_gen, make_cmd);
	}
	public static void Ns(Gfo_usr_dlg usr_dlg, Xob_tmp_wtr[] ttl_wtrs, String type, Io_url tmp_root, Io_url make_root, int memory_max, Io_line_rdr_key_gen key_gen, Io_make_cmd make_cmd) {
		int len = ttl_wtrs.length;
		for (int i = 0; i < len; i++) {
			Xob_tmp_wtr ttl_wtr = ttl_wtrs[i]; if (ttl_wtr == null) continue;
			Xow_ns ns = ttl_wtr.Ns_itm();
			Io_url make_dir = make_root.GenSubDir_nest(ns.Num_str(), type);
			make_cmd.Make_dir_(make_dir);
			Basic(usr_dlg
				, ttl_wtr.Url_gen()
				, tmp_root.GenSubDir_nest(ns.Num_str(), "sort")
				, memory_max, key_gen, make_cmd);
		}
	}
}
