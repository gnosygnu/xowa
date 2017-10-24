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
package gplx.xowa.xtns.insiders; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.brys.fmtrs.*;
import gplx.langs.htmls.encoders.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*; import gplx.xowa.wikis.pages.skins.*;
import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.wikis.nss.*;
class Insider_xtn_skin_itm implements Xopg_xtn_skin_itm {
	private List_adp itms = List_adp_.New();
	private Insider_html_bldr html_bldr;
	public Insider_xtn_skin_itm(Insider_html_bldr html_bldr) {this.html_bldr = html_bldr;}
	public byte Tid() {return Xopg_xtn_skin_itm_tid.Tid_sidebar;}
	public byte[] Key() {return KEY;} public static final    byte[] KEY = Bry_.new_a7("Insider");
	public List_adp Itms() {return itms;}
	public void Add(byte[] itm) {itms.Add(itm);}
	public void Write(Bry_bfr bfr, Xoae_page page) {
		html_bldr.Bld_all(bfr, page, itms);
	}
}
public class Insider_html_bldr implements gplx.core.brys.Bfr_arg {
	private Insider_xtn_mgr xtn_mgr;
	private Bry_bfr tmp_ttl = Bry_bfr_.Reset(255);
	private List_adp list; private int list_len;
	private Hash_adp_bry hash = Hash_adp_bry.cs();
	public Insider_html_bldr(Insider_xtn_mgr xtn_mgr) {this.xtn_mgr = xtn_mgr;}
	public void Bld_all(Bry_bfr bfr, Xoae_page page, List_adp list) {
		this.list = list; this.list_len = list.Count();
		hash.Clear();
		fmtr_grp.Bld_bfr_many(bfr, xtn_mgr.Msg_sidebar_ttl(), xtn_mgr.Msg_about_page(), xtn_mgr.Msg_about_ttl(), this);
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		Xowe_wiki wiki = xtn_mgr.Wiki();
		Gfo_url_encoder href_encoder = gplx.langs.htmls.encoders.Gfo_url_encoder_.Href;
		for (int i = 0; i < list_len; ++i) {
			byte[] itm = (byte[])list.Get_at(i);
			Xoa_ttl user_ttl = Xoa_ttl.Parse(wiki, Xow_ns_.Tid__user, itm);
			if (user_ttl == null) continue;
			byte[] user_ttl_bry = user_ttl.Full_db();
			if (hash.Has(user_ttl_bry)) continue;
			hash.Add(user_ttl_bry, user_ttl_bry);
			href_encoder.Encode(tmp_ttl, user_ttl_bry);
			user_ttl_bry = tmp_ttl.To_bry_and_clear();
			fmtr_itm.Bld_bfr(bfr, user_ttl_bry, user_ttl.Page_txt());
		}
	}
	private static final    Bry_fmtr
	  fmtr_grp = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "<div id='p-insiders' class='portal' role='navigation'>"
	, "  <h3>~{hdr}</h3>"
	, "  <div class='body'>"
	, "    <ul>~{itms}"
	, "      <li class='interwiki-insider'><a class='xowa-hover-off' href='/wiki/~{about_href}'>~{about_text}</a></li>"
	, "    </ul>"
	, "  </div>"
	, "</div>"
	), "hdr", "about_href", "about_text", "itms")
	, fmtr_itm = Bry_fmtr.new_
	( "\n      <li class='interwiki-insider'><a class='xowa-hover-off' href='/wiki/~{href}'>~{name}</a></li>"
	, "href", "name")
	;
}
