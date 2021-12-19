/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.wikis.pages;

import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.fmts.itms.BryFmt;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.wikis.pages.htmls.Xopg_html_data;

// TODO: move pagename_for_h1 here; also test; WHEN: next major change; NOTE: may go away for XOMW
public class Xopg_page_heading implements BryBfrArg {
	private Xowe_wiki wiki;
	private Xopg_html_data html_data;
	private byte[] ttl_full_db;
	private byte[] display_title;
	private boolean mode_is_read;
	private byte[] lang_code;
	public Xopg_page_heading Init(Xowe_wiki wiki, boolean mode_is_read, Xopg_html_data html_data, byte[] ttl_full_db, byte[] display_title, byte[] lang_code) {
		this.wiki = wiki;
		this.mode_is_read = mode_is_read;
		this.ttl_full_db = ttl_full_db;
		this.html_data = html_data;
		this.display_title = display_title;
		this.lang_code = lang_code;
		return this;
	}
	public void AddToBfr(BryWtr bfr) {
		if (html_data.Xtn_pgbnr() != null) return;	// pgbnr exists; don't add title
		byte[] edit_lead_section = BryUtl.Empty;
		if (	wiki.Parser_mgr().Hdr__section_editable__mgr().Enabled()
			&&	mode_is_read) {
			BryWtr tmp_bfr = BryWtr.New();
			wiki.Parser_mgr().Hdr__section_editable__mgr().Write_html(tmp_bfr, ttl_full_db, BryUtl.Empty, Bry__lead_section_hint);
			edit_lead_section = tmp_bfr.ToBryAndClear();
		}

		fmtr.Bld_many(bfr, lang_code, display_title, edit_lead_section);
	}
	private static final byte[] Bry__lead_section_hint = BryUtl.NewU8("(Lead)");
	private final BryFmt fmtr = BryFmt.Auto_nl_apos("<h1 id='firstHeading' class='firstHeading' lang='~{lang}'>~{page_title}~{edit_lead_section}</h1>");
}
