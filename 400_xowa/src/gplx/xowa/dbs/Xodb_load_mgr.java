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
package gplx.xowa.dbs; import gplx.*; import gplx.xowa.*;
import gplx.core.primitives.*; import gplx.xowa.bldrs.cmds.ctgs.*; import gplx.xowa.ctgs.*; import gplx.xowa.specials.search.*; import gplx.xowa.wikis.data.tbls.*;
public interface Xodb_load_mgr {
	void Load_init				(Xowe_wiki wiki);
	void Load_page				(Xowd_page_itm rv, Xow_ns ns, boolean timestamp_enabled);
	boolean Load_by_id				(Xowd_page_itm rv, int id);
	void Load_by_ids			(Cancelable cancelable, ListAdp rv, int bgn, int end);
	boolean Load_by_ttl			(Xowd_page_itm rv, Xow_ns ns, byte[] ttl);
	void Load_by_ttls			(Cancelable cancelable, OrderedHash rv, boolean fill_idx_fields_only, int bgn, int end);
	int Load_ctg_count			(byte[] ttl);
	boolean Load_ctg_v1			(Xoctg_view_ctg rv, byte[] ttl);
	boolean Load_ctg_v2			(Xoctg_data_ctg rv, byte[] ttl);
	void Load_ctg_v2a			(Xoctg_view_ctg rv, Xoctg_url url_ctg, byte[] ttl_bry, int limit);
	Xowd_page_itm[] Load_ctg_list	(byte[][] ctg_ttls);
	void Load_search			(Cancelable cancelable, ListAdp rv, byte[] search, int results_max);		
	void Load_ttls_for_all_pages (Cancelable cancelable, ListAdp rslt_list, Xowd_page_itm rslt_nxt, Xowd_page_itm rslt_prv, Int_obj_ref rslt_count, Xow_ns ns, byte[] key, int max_results, int min_page_len, int browse_len, boolean include_redirects, boolean fetch_prv_item);
	void Load_ttls_for_search_suggest(Cancelable cancelable, ListAdp rslt_list, Xow_ns ns, byte[] key, int max_results, int min_page_len, int browse_len, boolean include_redirects, boolean fetch_prv_item);
	byte[] Find_random_ttl		(Xow_ns ns);
	void Clear();	// TEST:helper function
	byte[] Load_qid				(byte[] wiki_alias, byte[] ns_num, byte[] ttl);
	int Load_pid				(byte[] lang_key, byte[] pid_name);
}
