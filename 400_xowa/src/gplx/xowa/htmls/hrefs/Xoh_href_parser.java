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
package gplx.xowa.htmls.hrefs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.core.primitives.*; import gplx.core.btries.*; import gplx.core.net.*;
import gplx.xowa.langs.vnts.*;
public class Xoh_href_parser {
	private final    Btrie_rv trv = new Btrie_rv();
	public void Parse_as_url(Xoa_url rv, byte[] raw, Xowe_wiki wiki, byte[] cur_page) {
		int bgn = 0;
		Object seg_obj = btrie.Match_at(trv, raw, bgn, raw.length);		// match /wiki/ or /site/ or /xcmd/
		if (seg_obj == null) {
			Xol_vnt_mgr vnt_mgr = wiki.Lang().Vnt_mgr();
			if (vnt_mgr.Enabled() && raw[0] == Byte_ascii.Slash) {
				int slash_end = Bry_find_.Find_fwd(raw, Byte_ascii.Slash, 1);
				if (vnt_mgr.Regy().Has(Bry_.Mid(raw, 1, slash_end))) {
					raw = Bry_.Add(wiki.Domain_bry(), raw);
				}
			}
		}
		else {										// something matched
			switch (((Byte_obj_val)seg_obj).Val()) {
				case Seg_xcmd_tid:									// convert "/xcmd/a" to "xowa-cmd:a"
					raw = Bry_.Add(Gfo_protocol_itm.Bry_xcmd, Bry_.Mid(raw, trv.Pos()));
					break;
				case Seg_wiki_tid:	// add domain_bry; NOTE: needed for url-like pages; EX:"/wiki/http://A"; PAGE:esolangs.org/wiki/Language_list; DATE:2015-11-14
					raw = Bry_.Add(wiki.Domain_bry(), raw);
					break;
				case Seg_site_tid:	// skip "/site"
					bgn = trv.Pos();
					break;
				default:
					break;
			}
		}
		wiki.Utl__url_parser().Parse(rv, raw, bgn, raw.length);
		switch (rv.Tid()) {
			case Xoa_url_.Tid_anch:
				rv.Wiki_bry_(wiki.Domain_bry());
				rv.Page_bry_(cur_page);
				break;
			case Xoa_url_.Tid_page:
				Xow_wiki ttl_wiki = wiki.App().Wiki_mgri().Get_by_or_make_init_n(rv.Wiki_bry());
				byte[] tmp_page = rv.Page_bry();
				if (rv.Page_is_main())
					tmp_page = ttl_wiki.Props().Main_page();
				else {
					if (tmp_page != null) {
						if (ttl_wiki != null) {
							Xoa_ttl ttl = ttl_wiki.Ttl_parse(tmp_page);
							if (ttl == null)	// invalid ttl; null out page;
								tmp_page = Bry_.Empty;
							else
								tmp_page = ttl.Full_txt_w_ttl_case();
						}
					}
				}
				rv.Page_bry_(tmp_page);
				if (tmp_page == null) {	// handle xwiki lnke's to history page else null ref; EX:[http://ru.wikipedia.org/w/index.php?title&diff=19103464&oldid=18910980 извещен]; PAGE:ru.w:Project:Заявки_на_снятие_флагов/Архив/Патрулирующие/2009 DATE:2016-11-24
					rv.Tid_(Xoa_url_.Tid_inet);
				}
				break;
		}
	}
	private static final byte Seg_wiki_tid = 0, Seg_site_tid = 1, Seg_xcmd_tid = 2;
	private static final    Btrie_slim_mgr btrie = Btrie_slim_mgr.ci_a7()	// NOTE:ci.ascii:XO_const.en; /wiki/, /site/ etc.
	.Add_bry_tid(Xoh_href_.Bry__wiki, Seg_wiki_tid)
	.Add_bry_tid(Xoh_href_.Bry__site, Seg_site_tid)
	.Add_bry_tid(Xoh_href_.Bry__xcmd, Seg_xcmd_tid);
}
