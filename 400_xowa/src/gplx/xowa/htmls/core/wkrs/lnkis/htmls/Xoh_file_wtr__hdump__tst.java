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
package gplx.xowa.htmls.core.wkrs.lnkis.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*;
import org.junit.*; import gplx.xowa.files.*;
import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.core.hzips.*;
public class Xoh_file_wtr__hdump__tst {
	private final    Xoh_file_wtr__hdump__fxt fxt = new Xoh_file_wtr__hdump__fxt();

	@Test   public void Plain() {
		fxt.Init__hctx__hzip__none();
		fxt.Test__parse
		( "[[File:A.png]]"
		, String_.Concat_lines_nl_skip_last
		( "<a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\">"
		+ "<img data-xowa-title=\"A.png\" data-xoimg=\"0|-1|-1|-1|-1|-1\" src=\"\" width=\"0\" height=\"0\" alt=\"\"/></a>"
		));
	}
}
class Xoh_file_wtr__hdump__fxt {
	private final    Xop_fxt fxt = new Xop_fxt();
	private final    Xof_file_fxt file_fxt;
	public Xoh_file_wtr__hdump__fxt() {
		fxt.Reset();

		// default to hzip
		fxt.Hctx_(Xoh_wtr_ctx.Hdump_by_hzip_tid(Xoh_hzip_dict_.Hzip__v1));

		// create file_fx
		this.file_fxt = Xof_file_fxt.new_all(fxt.Wiki());
		fxt.Wiki().File__fsdb_mode().Tid__v2__mp__y_();
		this.Init__orig__add("A.png", 400, 300);
	}
	public void Init__hctx__hzip__none() {fxt.Hctx_(Xoh_wtr_ctx.Hdump_by_hzip_tid(Xoh_hzip_dict_.Hzip__none));}
	public void Init__hctx__hzip__v1()   {fxt.Hctx_(Xoh_wtr_ctx.Hdump_by_hzip_tid(Xoh_hzip_dict_.Hzip__plain));}
	public void Init__orig__add(String orig_ttl, int orig_w, int orig_h) {
		file_fxt.Exec_orig_add(Bool_.Y, orig_ttl, Xof_ext_.new_by_ttl_(Bry_.new_u8(orig_ttl)).Id(), orig_w, orig_h, "");
	}
	public void Test__parse(String raw, String expd) {
		fxt.Test_parse_page_wiki_str(raw, expd);
	}
}
