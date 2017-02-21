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
package gplx.xowa.addons.wikis.searchs.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.domains.*;
import gplx.xowa.addons.wikis.searchs.searchers.*; import gplx.xowa.addons.wikis.searchs.specials.htmls.*; import gplx.xowa.addons.wikis.searchs.searchers.rslts.*;
public class Srch_special_searcher {
	private final    Xoae_wiki_mgr wiki_mgr;
	private final    Ordered_hash cancel_hash = Ordered_hash_.New_bry();
	private final    Srch_html_page_bldr html_page_bldr = new Srch_html_page_bldr();
	public Srch_special_searcher(Xoae_wiki_mgr wiki_mgr) {this.wiki_mgr = wiki_mgr;}
	public void Search(Xow_wiki search_wiki, Xoae_page page, boolean search_is_async, Xow_domain_itm[] domains_ary, Srch_search_qry qry) {
		Bry_bfr tmp_bfr = Bry_bfr_.New();
		html_page_bldr.Init_by_wiki(search_wiki, search_wiki.Lang().Num_mgr(), qry);
		cancel_hash.Clear();
		int domains_len = domains_ary.length;
		for (int i = 0; i < domains_len; ++i) {
			Xow_domain_itm domain = domains_ary[i];
			try {
				Xowe_wiki wiki = wiki_mgr.Get_by_or_make(domain.Domain_bry()); wiki.Init_assert();
				byte[] key = gplx.langs.htmls.Gfh_utl.Encode_id_as_bry(Bry_.Add(qry.Phrase.Orig, Byte_ascii.Pipe_bry, qry.Ns_mgr.To_hash_key(), Byte_ascii.Pipe_bry, wiki.Domain_bry()));
				Srch_rslt_list rslt_list;
				if (wiki.App().Mode().Tid_is_http()) {
					Srch_rslt_cbk__synchronous cbk_synchronous = new Srch_rslt_cbk__synchronous();
					Srch_search_addon.Get(wiki).Search(qry, cbk_synchronous);
					rslt_list = cbk_synchronous.Rslts();
				}
				else {
					Srch_special_cmd cmd = new Srch_special_cmd(this, qry, wiki, page.Tab_data().Close_mgr(), page.Tab_data().Tab().Html_itm(), key, search_is_async);
					cancel_hash.Add(key, cmd);
					cmd.Search();						// do search; will return immediately b/c async
					rslt_list = new Srch_rslt_list();	// NOTE: create an empty rslt which will be populated by async calls
				}
				html_page_bldr.Bld_tbl(tmp_bfr, rslt_list, key, wiki.Domain_bry(), search_is_async, qry.Slab_bgn, qry.Slab_end);
			}	catch (Exception e) {Xoa_app_.Usr_dlg().Warn_many("", "", "search:wiki failed; wiki=~{0} err=~{1}", domain.Domain_str(), Err_.Message_lang(e));}	// handle bad wikis, like "en.wikipedia.org-old"; DATE:2015-04-24
		}

		// generate html; note if async, this will just generate the page header
		page.Db().Text().Text_bry_(html_page_bldr.Bld_page(tmp_bfr.To_bry_and_clear()));
	}
	public void Search__done(Srch_special_cmd cmd) {
		cancel_hash.Del(cmd.key);
	}
	public void Search__cancel(byte[] cmd_key) {
		Srch_special_cmd cmd = (Srch_special_cmd)cancel_hash.Get_by(cmd_key); // if (cmd == null) return;	// ignore false calls to cancel
		cmd.On_cancel();
		cancel_hash.Del(cmd.key);
	}
}
class Srch_rslt_cbk__synchronous implements Srch_rslt_cbk {
	public Srch_rslt_list Rslts() {return rslts;} private Srch_rslt_list rslts;
	public void On_rslts_found(Srch_search_qry qry, Srch_rslt_list rslts_list, int rslts_bgn, int rslts_end) {
		this.rslts = rslts_list;	// just assign it
	}
	public void On_cancel() {}		// synchronous cannot be canceled
}
