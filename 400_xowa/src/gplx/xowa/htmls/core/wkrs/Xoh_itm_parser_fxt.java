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
import gplx.core.brys.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*;
public abstract class Xoh_itm_parser_fxt {
	protected final    Xoae_app app;
	protected final    Xowe_wiki wiki;
	private final    Bry_err_wkr err_wkr = new Bry_err_wkr();
	protected byte[] src; protected int src_len;
	protected final    Xoh_hdoc_ctx hctx = new Xoh_hdoc_ctx();
	public Xoh_itm_parser_fxt() {
		this.app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
		hctx.Init_by_app(app);
	}
	private Xoh_itm_parser Parser() {return Parser_get();}
	public abstract Xoh_itm_parser Parser_get();
	public void Test__parse__fail(String src_str, String expd) {
		Gfo_usr_dlg_.Test__list__init();
		Parser().Fail_throws_err_(Bool_.N);
		Exec_parse(src_str);
		Parser().Fail_throws_err_(Bool_.Y);
		Tfds.Eq_str(expd, Gfo_usr_dlg_.Test__list__term__get_1st());
	}
	public void Exec_parse(String src_str) {
		this.src = Bry_.new_u8(src_str); this.src_len = src.length;
		Xoh_page hpg = new Xoh_page(); // NOTE: no need to pass url and ttl now
		hctx.Init_by_page(wiki, hpg);
		err_wkr.Init_by_page(Xoa_page_.Main_page_str, src);
		Exec_parse_hook(err_wkr, hctx, 0, src_len);
	}
	public abstract void Exec_parse_hook(Bry_err_wkr err_wkr, Xoh_hdoc_ctx hctx, int src_bgn, int src_end);
}
