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
package gplx.xowa.addons.htmls.sidebars; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.htmls.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.nss.*;
public class Xoh_sidebar_mgr {
	public Xoh_sidebar_mgr(Xowe_wiki wiki) {this.wiki = wiki;} private final    Xowe_wiki wiki;		
	public List_adp Grps() {return grps;} private final    List_adp grps = List_adp_.New();	// TEST:
	public byte[] Html_bry() {return html_bry;} private byte[] html_bry;
	public void Init_by_wiki() {
		try {
			Bry_bfr tmp_bfr = Bry_bfr_.New();
			byte[] sidebar = Get_sidebar_or_null(tmp_bfr, wiki);
			if (sidebar != null) Make(tmp_bfr, sidebar);
		} catch (Exception e) {
			wiki.Appe().Usr_dlg().Warn_many("", "", "sidebar failed: wiki=~{0} err=~{1}", wiki.Domain_str(), Err_.Message_gplx_log(e));
		}
	}
	private byte[] Get_sidebar_or_null(Bry_bfr tmp_bfr, Xowe_wiki wiki) {
		// if home, always return null
		if (wiki.Domain_tid() == Xow_domain_tid_.Tid__home) return null;

		// check msg_mgr; note that this checks (a) en.wikipedia.org/wiki/MediaWiki:Sidebar; (b) "sidebar" in en.gfs
		Xol_msg_itm rv_msg = Xol_msg_mgr_.Get_msg_itm(tmp_bfr, wiki, wiki.Lang(), Ttl__sidebar);

		// if found in MediaWiki:Sidebar, always return it
		byte[] rv = rv_msg.Val();
		if (rv_msg.Defined_in() == Xol_msg_itm.Defined_in__wiki && Bry_.Len_gt_0(rv)) return rv;

		// sidebar is either (a) in lang.gfs (wikia; wmf wikis without MediaWiki:Sidebar), or (b) not in lang.gfs (wmf wikis in lang.gfs without "lang.gfs"; EX:abcde.gfs)
		// if wikia, return null; else return rv; note that all "official" langs (EX: sw) fallback to en.gfs which has a sidebar; DATE:2017-01-05
		return wiki.Domain_tid() == Xow_domain_tid_.Tid__other ? null : rv;
	}
	public void Make(Bry_bfr tmp_bfr, byte[] src) {	// TEST:
		Xoh_sidebar_parser.Parse(tmp_bfr, wiki, grps, src);
		html_bry = Xoh_sidebar_htmlr.To_html(tmp_bfr, wiki, grps);
	}
	private static final    byte[] Ttl__sidebar = Bry_.new_a7("Sidebar");	// MediaWiki:Sidebar
}
