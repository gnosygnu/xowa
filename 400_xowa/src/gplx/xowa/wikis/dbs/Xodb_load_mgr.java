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
package gplx.xowa.wikis.dbs;
import gplx.frameworks.objects.Cancelable;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.wrappers.IntRef;
import gplx.xowa.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.data.tbls.*;
public interface Xodb_load_mgr {
	void Init_by_wiki				(Xowe_wiki wiki);
	void Load_page					(Xowd_page_itm rv, Xow_ns ns);
	boolean Load_by_id					(Xowd_page_itm rv, int id);
	void Load_by_ids				(Cancelable cancelable, List_adp rv, int bgn, int end);
	boolean Load_by_ttl				(Xowd_page_itm rv, Xow_ns ns, byte[] ttl);
	void Load_by_ttls				(Xowe_wiki wiki, Cancelable cancelable, Ordered_hash rv, boolean fill_idx_fields_only, int bgn, int end);
	void Load_ttls_for_all_pages	(Cancelable cancelable, List_adp rslt_list, Xowd_page_itm rslt_nxt, Xowd_page_itm rslt_prv, IntRef rslt_count, Xow_ns ns, byte[] key, int max_results, int min_page_len, int browse_len, boolean include_redirects, boolean fetch_prv_item);
	void Load_ttls_for_search_suggest(Cancelable cancelable, List_adp rslt_list, Xow_ns ns, byte[] key, int max_results, int min_page_len, int browse_len, boolean include_redirects, boolean fetch_prv_item);
	byte[] Find_random_ttl			(Xow_ns ns);
	void Clear();	// TEST:helper function
	byte[] Load_qid					(byte[] wiki_alias, int ns_num, byte[] ttl);
	int Load_pid					(byte[] lang_key, byte[] pid_name);
	Xodb_page_rdr Get_page_rdr		(Xowe_wiki wiki);
}
