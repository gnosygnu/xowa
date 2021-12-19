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
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.xtns.wbases.*;
import gplx.types.custom.brys.fmts.fmtrs.*;
import gplx.xowa.wikis.pages.wtxts.*;
class Wdata_fmtr__toc_div implements BryBfrArg {
	private final List_adp itms = List_adp_.New(); private final  Wdata_fmtr__toc_itm fmtr_itm = new Wdata_fmtr__toc_itm();
	private byte[] tbl_hdr; 
	public void Init_by_lang(Wdata_hwtr_msgs msgs)	{this.tbl_hdr = msgs.Toc_tbl_hdr();}
	public void Init_by_wdoc(Wdata_doc wdoc)		{itms.Clear();}
	public void Add(Wdata_toc_data toc_data)		{itms.Add(toc_data);}
	public void AddToBfr(BryWtr bfr) {
		int itms_len = itms.Len();
		if (itms_len <= Xopg_toc_mgr.Hdrs_min) return;
		fmtr_itm.Init_by_itm((Wdata_toc_data[])itms.ToAryAndClear(Wdata_toc_data.class));
		fmtr.BldToBfrMany(bfr, tbl_hdr, fmtr_itm);
	}
	private final BryFmtr fmtr = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "  <div id='toc' class='toc wb-toc'>"
	, "    <div id='toctitle' class='toctitle'>"
	, "      <h2>~{hdr}</h2>"
	, "    </div>"
	, "    <ul>~{itms}"
	, "    </ul>"
	, "  </div>"
	), "hdr", "itms");
}
class Wdata_fmtr__toc_itm implements BryBfrArg {
	private Wdata_toc_data[] ary;
	public void Init_by_itm(Wdata_toc_data[] v)				{this.ary = v;}
	public void AddToBfr(BryWtr bfr) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Wdata_toc_data itm = ary[i];
			fmtr.BldToBfrMany(bfr, i + List_adp_.Base1, itm.Href(), itm.Text());
		}
	}
	private final BryFmtr fmtr = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "      <li class='toclevel-1 tocsection-~{idx}'>"
	, "        <a href='#~{href}'>"
	, "          <span class='toctext'>~{text}</span>"
	, "        </a>"
	, "      </li>"
	), "idx", "href", "text");
}
