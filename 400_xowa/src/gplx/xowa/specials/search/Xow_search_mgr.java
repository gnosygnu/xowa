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
import gplx.dbs.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
class Xow_search_mgr {
	private final Hash_adp_bry cache_hash = Hash_adp_bry.cs_();
	private final ListAdp rslts_list = ListAdp_.new_();
	private final Xow_search_db db = new Xow_search_db();
	public void Search(Xow_search_ui ui, byte[] raw, int rslt_bgn, int rslt_len, Xoa_wiki_mgr wiki_mgr, byte[]... wiki_domains) {
		int len = wiki_domains.length;
		for (int i = 0; i < len; ++i) {
			if (ui.Canceled()) break;
			byte[] wiki_domain = wiki_domains[i];
			Xow_wiki wiki = wiki_mgr.Get_by_key_or_null(wiki_domain);
			if (wiki == null) {Xoa_app_.Usr_dlg().Warn_many("", "", "search:wiki not found; wiki=~{0}", wiki_domain); continue;} // wiki listed in search opt, but no longer exists
			Search(ui, raw, rslt_bgn, rslt_len, wiki);
		}
	}
	private void Search(Xow_search_ui ui, byte[] raw, int rslt_bgn, int rslt_len, Xow_wiki wiki) {
		Xow_search_qry qry = Parse_qry(raw, rslt_bgn, rslt_len);
		// get cache or new
		byte[] cache_key = Bry_.Add_w_dlm(Byte_ascii.Pipe, wiki.Domain_bry(), raw);
		Xow_search_cache cache = (Xow_search_cache)cache_hash.Get_by_bry(cache_key);
		if (cache == null) {
			cache = new Xow_search_cache();
			cache_hash.Add_bry_obj(cache_key, cache);
		}
		// fill results
		cache.Get_between(rslts_list, qry.Rslts_bgn(), qry.Rslts_end());
		if (qry.Rslts_end() > cache.Count() && !cache.Done())
			db.Search(ui, wiki, qry, cache);
		ui.End();
	}
	private Xow_search_qry Parse_qry(byte[] raw, int rslt_bgn, int rslt_len) {
		return null;
	}
}
class Xow_search_ui implements Cancelable {
	public boolean Canceled() {return false;}
	public void Cancel() {}
	public void Cancel_reset() {}
	public void Add(Xow_search_rslt rslt) {
	}
	public void End() {
	}
}
