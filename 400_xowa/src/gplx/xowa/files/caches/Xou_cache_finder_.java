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
package gplx.xowa.files.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.xowa.files.repos.*;
public class Xou_cache_finder_ {
	public static final    Xou_cache_finder Noop = new Xou_cache_finder_noop();
	public static Xou_cache_finder_mem New_mem() {return new Xou_cache_finder_mem();}
	public static Xou_cache_finder New_db(Xou_cache_mgr cache_mgr) {return new Xou_cache_finder_db(cache_mgr);}
}
class Xou_cache_finder_noop implements Xou_cache_finder {
	public boolean Find(Xow_wiki wiki, byte[] page_url, Xof_fsdb_itm fsdb_itm) {
		fsdb_itm.Init_at_cache(false, 0, 0, Io_url_.Empty);
		return false;
	}
	public void Clear() {}
	public void Add(Xof_fsdb_itm fsdb_itm) {}
}
class Xou_cache_finder_db implements Xou_cache_finder {
	private final    Xou_cache_mgr cache_mgr;
	private final    Xof_img_size img_size = new Xof_img_size(); private final    Xof_url_bldr url_bldr = Xof_url_bldr.new_v2();
	public Xou_cache_finder_db(Xou_cache_mgr cache_mgr) {this.cache_mgr = cache_mgr;}
	public boolean Find(Xow_wiki wiki, byte[] page_url, Xof_fsdb_itm cur) {
		Xou_cache_itm cache_itm = cache_mgr.Get_or_null(cur); 
		if (cache_itm != null) {
			Xof_repo_itm repo = wiki.File__repo_mgr().Get_trg_by_id_or_null(cache_itm.Orig_repo_id(), cur.Lnki_ttl(), page_url);
			if (repo != null) {// unknown repo; shouldn't happen, but exit, else null ref
				cur.Init_at_orig((byte)cache_itm.Orig_repo_id(), repo.Wiki_domain(), cache_itm.Orig_ttl(), cache_itm.Orig_ext_itm(), cache_itm.Orig_w(), cache_itm.Orig_h(), Bry_.Empty);
				cur.Init_at_html(Xof_exec_tid.Tid_wiki_page, img_size, repo, url_bldr);
				if (Io_mgr.Instance.ExistsFil(cur.Html_view_url())) {
					cache_itm.Update_view_stats();
					cur.Init_at_cache(true, cur.Html_w(), cur.Html_h(), cur.Html_view_url());
					return true;
				}
			}
		}
		cur.Init_at_cache(false, 0, 0, Io_url_.Empty);
		return false;
	}
	public void Clear() {}
	public void Add(Xof_fsdb_itm cur) {}
}
