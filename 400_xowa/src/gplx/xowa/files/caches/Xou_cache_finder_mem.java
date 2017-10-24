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
package gplx.xowa.files.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.xowa.files.repos.*;
public class Xou_cache_finder_mem implements Xou_cache_finder {
	private final    Ordered_hash hash = Ordered_hash_.New_bry();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New_w_size(255);
	private final    Xof_img_size img_size = new Xof_img_size();
	private final    Xof_url_bldr url_bldr = Xof_url_bldr.new_v2();
	public boolean Find(Xow_wiki wiki, byte[] page_url, Xof_fsdb_itm cur) {
		byte[] key = Xou_cache_itm.Key_gen(tmp_bfr, cur.Lnki_wiki_abrv(), cur.Lnki_ttl(), cur.Lnki_type(), cur.Lnki_upright(), cur.Lnki_w(), cur.Lnki_h(), cur.Lnki_time(), cur.Lnki_page(), cur.User_thumb_w());
		Xof_fsdb_itm mem = (Xof_fsdb_itm)hash.Get_by(key); 
		if (mem == null) {
			cur.Init_at_cache(false, 0, 0, Io_url_.Empty);
			return false;
		}
		Xof_repo_itm repo = wiki.File__repo_mgr().Get_trg_by_id_or_null(mem.Orig_repo_id(), mem.Lnki_ttl(), page_url);
		if (repo != null)	// HACK: ignore null repo for tests that don't create repos; DATE:2016-06-18
			mem.Init_at_html(Xof_exec_tid.Tid_wiki_page, img_size, repo, url_bldr);
		cur.Init_at_cache(true, mem.Html_w(), mem.Html_h(), mem.Html_view_url());
		return true;
	}
	public void Clear() {}
	public void Add(Xof_fsdb_itm cur) {
		byte[] key = Xou_cache_itm.Key_gen(tmp_bfr, cur.Lnki_wiki_abrv(), cur.Lnki_ttl(), cur.Lnki_type(), cur.Lnki_upright(), cur.Lnki_w(), cur.Lnki_h(), cur.Lnki_time(), cur.Lnki_page(), cur.User_thumb_w());
		hash.Add(key, cur);
	}
}
