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
