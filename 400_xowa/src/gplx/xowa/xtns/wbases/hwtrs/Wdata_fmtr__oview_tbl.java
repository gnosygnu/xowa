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
package gplx.xowa.xtns.wbases.hwtrs;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.xtns.wbases.*;
import gplx.types.custom.brys.fmts.fmtrs.*;
import gplx.langs.htmls.encoders.*;
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.apps.apis.xowa.xtns.*;
class Wdata_fmtr__oview_tbl implements BryBfrArg {
	private Xoapi_wikibase wikibase_api; private Gfo_url_encoder href_encoder;
	private Wdata_fmtr__oview_alias_itm fmtr_aliases = new Wdata_fmtr__oview_alias_itm();
	private BryFmtr slink_fmtr = BryFmtr.New("<a href='/site/~{domain_bry}/wiki/~{page_href}'>~{page_text}</a>", "domain_bry", "page_href", "page_text");
	private BryWtr tmp_bfr = BryWtr.NewWithSize(255);
	private Wdata_doc wdoc;
	private byte[] hdr_alias_y, hdr_alias_n;
	public void Init_by_ctor(Xoapi_wikibase wikibase_api, Gfo_url_encoder href_encoder) {this.wikibase_api = wikibase_api; this.href_encoder = href_encoder;}
	public void Init_by_lang(byte[] lang_0, Wdata_hwtr_msgs msgs) {
		this.hdr_alias_y = msgs.Oview_alias_y();
		this.hdr_alias_n = msgs.Oview_alias_n();
	}
	public void Init_by_wdoc(Wdata_doc wdoc) {
		this.wdoc = wdoc;
	}
	public void AddToBfr(BryWtr bfr) {
		byte[][] core_langs		= wikibase_api.Core_langs();			
		byte[] oview_label		= Wdata_langtext_itm.Get_text_or_empty(wdoc.Label_list(), core_langs);
		byte[] oview_descr		= Wdata_langtext_itm.Get_text_or_empty(wdoc.Descr_list(), core_langs);
		byte[][] oview_alias	= Alias_get_or_empty(wdoc.Alias_list(), core_langs);
		byte[] aliases_hdr		= oview_alias == BryUtl.AryEmpty ? hdr_alias_n : hdr_alias_y;
		fmtr_aliases.Init_by_itm(oview_alias);
		Wdata_sitelink_itm slink = (Wdata_sitelink_itm)wdoc.Slink_list().GetByOrNull(wikibase_api.Link_wikis());
		if (slink != null) {
			oview_label = slink_fmtr.BldToBryMany(tmp_bfr, slink.Domain_info().Domain_bry(), href_encoder.Encode(slink.Name()), oview_label);
		}
		row_fmtr.BldToBfrMany(bfr, wdoc.Qid(), oview_label, oview_descr, aliases_hdr, fmtr_aliases);
	}
	private BryFmtr row_fmtr = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "      <div class='wikibase-entitytermsview'>"
	, "        <div class='wikibase-entitytermsview-heading'>"
	, "          <h1 class='wikibase-entitytermsview-heading-label'>~{ttl_label}"
	, "            <span class='wikibase-entitytermsview-heading-label-id'>(~{ttl})</span>"
	, "          </h1>"
	, "          <div class='wikibase-entitytermsview-heading-description '>~{ttl_descr}"
	, "          </div>"
	, "          <div class='wikibase-entitytermsview-heading-aliases'>"
	, "            <ul class='wikibase-entitytermsview-aliases'>~{ttl_aliases}"
	, "            </ul>"
	, "          </div>"
	, "        </div>"
	, "      </div>"
	), "ttl", "ttl_label", "ttl_descr", "hdr_aliases", "ttl_aliases"
	);
	private static byte[][] Alias_get_or_empty(Ordered_hash list, byte[][] langs) {
		if (list == null) return BryUtl.AryEmpty;
		int langs_len = langs.length;
		for (int i = 0; i < langs_len; ++i) {
			Object itm_obj = list.GetByOrNull(langs[i]);
			if (itm_obj != null) {
				Wdata_alias_itm itm = (Wdata_alias_itm)itm_obj;
				return itm.Vals();
			}
		}
		return BryUtl.AryEmpty;
	}
}
class Wdata_fmtr__oview_alias_itm implements BryBfrArg {
	private byte[][] ary;
	public void Init_by_itm(byte[][] ary) {this.ary = ary;}
	public void AddToBfr(BryWtr bfr) {
		if (ary == null) return;
		int len = ary.length;
		for (int i = 0; i < len; ++i)
			row_fmtr.BldToBfrMany(bfr, ary[i]);
	}
	private BryFmtr row_fmtr = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "              <li class='wikibase-entitytermsview-aliases-alias'>~{itm}</li>"
	), "itm"
	);
}
