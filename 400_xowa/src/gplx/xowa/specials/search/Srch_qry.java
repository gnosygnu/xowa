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
package gplx.xowa.specials.search; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.xowa.wikis.domains.*;
class Srch_qry {
	private final List_adp cmd_list = List_adp_.new_();
	public Srch_qry(byte[] search_raw, int page_idx, int page_len, Xows_ns_mgr ns_mgr, boolean async_db, Xow_domain_itm[] wiki_domains) {
		this.search_raw = search_raw; this.page_idx = page_idx; this.page_len = page_len; this.ns_mgr = ns_mgr; this.async_db = async_db; this.wiki_domains = wiki_domains;
		this.itms_bgn = page_idx * page_len;
		this.itms_end = itms_bgn + page_len;
		this.page_max = page_idx;	// default page_max to page_idx; adjust later when all results are known
		this.key = Bry_.Add_w_dlm(Byte_ascii.Pipe, search_raw, ns_mgr.To_hash_key());
		this.special_link_base_href = Bry_.Add(Bry_.new_a7("Special:Search/"), search_raw, Bry_.new_a7("?fulltext=y"));
	}
	public final byte[]				key;
	public final byte[]				search_raw;
	public final int					page_idx;
	public final int					page_len;
	public final int					itms_bgn;
	public final int					itms_end;
	public final Xows_ns_mgr			ns_mgr;
	public final Xow_domain_itm[]	wiki_domains;
	public final byte[]				special_link_base_href;
	public final boolean				async_db;
	public int page_max;
	public int			Cmds__len() {return cmd_list.Count();}
	public Xows_ui_cmd	Cmds__get_at(int i) {return (Xows_ui_cmd)cmd_list.Get_at(i);}
	public void			Cmds__add(Xows_ui_cmd cmd) {cmd_list.Add(cmd);}
}
