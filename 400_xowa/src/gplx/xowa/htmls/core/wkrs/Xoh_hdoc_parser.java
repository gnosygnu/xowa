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
package gplx.xowa.htmls.core.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.langs.htmls.docs.*; import gplx.xowa.wikis.ttls.*;
import gplx.xowa.htmls.core.makes.*; import gplx.xowa.htmls.core.wkrs.tags.*; import gplx.xowa.htmls.core.wkrs.txts.*; import gplx.xowa.htmls.core.wkrs.escapes.*;
import gplx.xowa.htmls.core.hzips.*;
public class Xoh_hdoc_parser {
	private final Xoh_hdoc_wkr hdoc_wkr;
	private final Gfh_doc_parser hdoc_parser;
	private final Xoh_tag_parser tag_parser;
	public Xoh_hdoc_parser(Xoh_hdoc_wkr hdoc_wkr) {
		this.hdoc_wkr = hdoc_wkr;
		this.tag_parser = new Xoh_tag_parser(hdoc_wkr);
		int wkr_ary_len = Xoh_pool_mgr__hzip.Hooks_ary.length + 1;
		Gfh_doc_wkr[] wkr_ary = new Gfh_doc_wkr[wkr_ary_len];
		wkr_ary[0] = tag_parser;
		for (int i = 1; i < wkr_ary_len; ++i)
			wkr_ary[i] = new Xoh_escape_data(hdoc_wkr, Xoh_pool_mgr__hzip.Hooks_ary[i - 1]);
		this.hdoc_parser = new Gfh_doc_parser(new Xoh_txt_parser(hdoc_wkr), wkr_ary);
	}
	public void Parse(Xoh_hzip_bfr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src) {
		int src_len = src.length;
		tag_parser.Init(hctx, src, 0, src_len);
		hdoc_wkr.On_new_page(bfr, hpg, hctx, src, 0, src_len);
		hdoc_parser.Parse(hctx.Page__url(), src, 0, src_len);
	}
}
