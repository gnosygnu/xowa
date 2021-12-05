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
package gplx.xowa.htmls.hrefs;

import gplx.objects.primitives.BoolUtl;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.Bry_find_;
import gplx.objects.strings.AsciiByte;
import gplx.core.brys.fmtrs.Bry_fmtr;
import gplx.langs.htmls.encoders.Gfo_url_encoder;
import gplx.langs.htmls.encoders.Gfo_url_encoder_;
import gplx.xowa.Xoa_app;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xow_wiki;
import gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx;
import gplx.xowa.wikis.xwikis.Xow_xwiki_itm;

public class Xoh_href_wtr {	// TS:do not move to app-level
	private final Gfo_url_encoder encoder = Gfo_url_encoder_.New__html_href_mw(BoolUtl.Y).Make();
	private final Bry_bfr encoder_bfr = Bry_bfr_.Reset(255), tmp_bfr = Bry_bfr_.Reset(255);
	public byte[] Build_to_bry(Xow_wiki wiki, byte[] ttl_bry) {
		Xoa_ttl ttl = wiki.Ttl_parse(ttl_bry);
		Build_to_bfr(tmp_bfr, wiki.App(), Xoh_wtr_ctx.Basic, wiki.Domain_bry(), ttl);
		return tmp_bfr.To_bry_and_clear();
	}
	public byte[] Build_to_bry(Xow_wiki wiki, Xoa_ttl ttl) {
		Build_to_bfr(tmp_bfr, wiki.App(), Xoh_wtr_ctx.Basic, wiki.Domain_bry(), ttl);
		return tmp_bfr.To_bry_and_clear();
	}
	public byte[] Build_to_bry(Bry_bfr tmp, Xow_wiki wiki, Xoa_ttl ttl) {
		this.Build_to_bfr(tmp, wiki.App(), wiki.Domain_bry(), ttl);
		return tmp.To_bry_and_clear();
	}
	public byte[] Build_to_bry_w_qargs(Bry_bfr tmp, Xow_wiki wiki, Xoa_ttl ttl, byte[]... qargs_ary) {
		this.Build_to_bfr(tmp, wiki.App(), wiki.Domain_bry(), ttl);
		int qargs_len = qargs_ary.length / 2;
		for (int i = 0; i < qargs_len; i++) {
			tmp.Add_byte(i == 0 ? AsciiByte.Question : AsciiByte.Eq);
			tmp.Add(qargs_ary[i]);
			tmp.Add_byte(AsciiByte.Eq);
			tmp.Add(qargs_ary[i + 1]);
		}
		return tmp.To_bry_and_clear();
	}
	public void Build_to_bfr(Bry_bfr bfr, Xoa_app app, byte[] domain_bry, Xoa_ttl ttl) {Build_to_bfr(bfr, app, Xoh_wtr_ctx.Basic, domain_bry, ttl);}
	public void Build_to_bfr(Bry_bfr bfr, Xoa_app app, Xoh_wtr_ctx hctx, byte[] domain_bry, Xoa_ttl ttl) { // given ttl, write href; EX: A -> '/wiki/A' 
		byte[] page = ttl.Full_txt_raw();
		Xow_xwiki_itm xwiki = ttl.Wik_itm();
		if (xwiki == null)																		// not an xwiki; EX: [[wikt:Word]]
			Build_to_bfr_page(ttl, hctx, page, 0);												// write page only; NOTE: changed to remove leaf logic DATE:2014-09-07
		else {																					// xwiki; skip wiki and encode page only;
			byte[] wik_txt = ttl.Wik_txt();
			Build_to_bfr_page(ttl, hctx, page, wik_txt.length + 1);
		}
		if (xwiki == null) {																	// not an xwiki
			if (ttl.Anch_bgn() != 1) {															// not an anchor-only;	EX: "#A"
				if (hctx.Mode_is_popup()) {                                                     // popup parser always writes as "/site/"
					bfr.Add(Xoh_href_.Bry__site);                                               // add "/site/";    EX: /site/
					bfr.Add(domain_bry);													    // add xwiki;       EX: en_dict
					bfr.Add(Xoh_href_.Bry__wiki);											    // add "/wiki/";    EX: /wiki/
				}
				else if (hctx.Mode_is_file_dump()) {
					bfr.Add(hctx.Anch__href__bgn());                                            // add "/wiki/";    EX: /wiki/Page; can be "/home/wiki/Page" for Html__dump_to_fsys
				}
				else {
					bfr.Add(Xoh_href_.Bry__wiki);                                               // add "/wiki/";    EX: /wiki/Page
				}
			}
			else {}																				// anchor: noop
		}
		else {																					// xwiki
			if (	app.Xwiki_mgr__missing(xwiki.Domain_bry())									// xwiki is not offline; use http:
				||	hctx.Mode_is_hdump()                                                        // hdump should never dump as "/site/"
				||	hctx.Mode_is_file_dump()                                                    // filedump should never dump as "/site/"
				) {
				Bry_fmtr url_fmtr = xwiki.Url_fmtr();
				if (url_fmtr == null) {
					bfr.Add(Xoh_href_.Bry__https);												// add "https://";	EX: https://
					bfr.Add(xwiki.Domain_bry());												// add xwiki;		EX: en_dict	 
					bfr.Add(Xoh_href_.Bry__wiki);												// add "/wiki/";	EX: /wiki/
				}
				else {																			// url_fmtr exists; DATE:2015-04-22
					url_fmtr.Bld_bfr(bfr, encoder_bfr.To_bry_and_clear());						// use it and pass encoder_bfr for page_name;
					return;
				}
			}
			else {																				// xwiki is avaiable; use /site/
				bfr.Add(Xoh_href_.Bry__site);													// add "/site/";	EX: /site/
				bfr.Add(xwiki.Domain_bry());													// add xwiki;		EX: en_dict	 
				bfr.Add(Xoh_href_.Bry__wiki);													// add "/wiki/";	EX: /wiki/
			}
		}
		bfr.Add_bfr_and_clear(encoder_bfr);
	}
	private void Build_to_bfr_page(Xoa_ttl ttl, Xoh_wtr_ctx hctx, byte[] ttl_full, int page_bgn) {
		int anch_bgn = Bry_find_.Find_fwd(ttl_full, AsciiByte.Hash);	// NOTE: cannot use Anch_bgn b/c Anch_bgn has bug with whitespace
		if (anch_bgn == Bry_find_.Not_found){	// no anchor; just add page
			encoder.Encode(encoder_bfr, ttl_full, page_bgn, ttl_full.length);
			if (hctx.Mode_is_file_dump()) {
				byte[] href_end = hctx.Anch__href__end();
				if (href_end != null) encoder_bfr.Add(href_end);
			}
		}
		else {									// anchor exists; check if anchor is preceded by ws; EX: [[A #b]] -> "/wiki/A#b"
			int page_end = Bry_find_.Find_bwd_last_ws(ttl_full, anch_bgn);		// first 1st ws before #; handles multiple ws
			page_end = page_end == Bry_find_.Not_found ? anch_bgn : page_end;	// if ws not found, use # pos; else use 1st ws pos
			encoder.Encode(encoder_bfr, ttl_full, page_bgn, page_end);			// add page
			if (hctx.Mode_is_file_dump()) {
				byte[] href_end = hctx.Anch__href__end();
				if (	href_end != null
					&&	page_end - page_bgn > 0)								// handle [[#A]] which will have no page; else will dump "home/page/.html#A"; DATE:2016-04-12
					encoder_bfr.Add(href_end);
			}
			encoder.Encode(encoder_bfr, ttl_full, anch_bgn, ttl_full.length);	// add anchor
		}
	}
}
