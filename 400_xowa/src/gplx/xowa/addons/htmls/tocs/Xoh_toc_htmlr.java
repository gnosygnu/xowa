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
package gplx.xowa.addons.htmls.tocs;
import gplx.langs.htmls.Gfh_tag_;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.fmts.itms.BryFmt;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx;
class Xoh_toc_htmlr implements BryBfrArg {
	private final BryWtr numbering_bfr = BryWtr.New();
	private byte[] toc_label;
	private int prv_lvl;
	private Ordered_hash toc_itms;
	public void Clear() {
		prv_lvl = 0;
	}
	public void Init(byte[] toc_label) {
		this.toc_label = toc_label;
	}
	public void To_html(BryWtr rv, Xoh_wtr_ctx hctx, Ordered_hash toc_itms, boolean toc_mode_is_pgbnr) {
		this.toc_itms = toc_itms;
		fmtr_div.Bld_many(rv, toc_mode_is_pgbnr ? BryUtl.Empty : Bry_toc_cls, toc_label, this);
	}
	public void Test__to_html(BryWtr rv, Ordered_hash toc_itms) {
		this.toc_itms = toc_itms;
		AddToBfr(rv);
	}
	public void AddToBfr(BryWtr bfr) {
		int len = toc_itms.Len();
		prv_lvl = 0;
		for (int i = 0; i < len; ++i) {
			Xoh_toc_itm itm = (Xoh_toc_itm)toc_itms.GetAt(i);
			Write(bfr, itm);
		}

		// close all open levels
		for (int i = prv_lvl; i > 0; --i) {
			int indent = i * 2;
			bfr.AddByteRepeat(AsciiByte.Space, indent + 2).Add(Gfh_tag_.Li_rhs).AddByteNl();			// EX: "    </li>\n"
			bfr.AddByteRepeat(AsciiByte.Space, indent    ).Add(Gfh_tag_.Ul_rhs).AddByteNl();			// EX: "  </ul>\n"
		}
	}
	private void Write(BryWtr bfr, Xoh_toc_itm itm) {
		int cur_lvl = itm.Lvl();
		int indent = cur_lvl * 2;
		switch (CompareAbleUtl.Compare(cur_lvl, prv_lvl)) {
			case CompareAbleUtl.More:		// start new "<ul>"
				bfr.AddByteRepeat(AsciiByte.Space, indent).Add(Gfh_tag_.Ul_lhs).AddByteNl();			// EX: "  <ul>\n"
				break;
			case CompareAbleUtl.Same:		// close old "</li>"; NOTE: Comparable_.Same will never be 1st item (so won't ever get </li><li>)
				bfr.AddByteRepeat(AsciiByte.Space, indent + 2).Add(Gfh_tag_.Li_rhs).AddByteNl();		// EX: "    </li>\n"
				break;
			case CompareAbleUtl.Less:		// close old "</ul>" and "</li>"
				for (int j = prv_lvl; j > cur_lvl; --j) {
					bfr.AddByteRepeat(AsciiByte.Space, (j * 2) + 2).Add(Gfh_tag_.Li_rhs).AddByteNl();	// EX: "    </li>\n"
					bfr.AddByteRepeat(AsciiByte.Space, (j * 2)    ).Add(Gfh_tag_.Ul_rhs).AddByteNl();	// EX: "    </ul>\n"
				}
				bfr.AddByteRepeat(AsciiByte.Space, indent + 2).Add(Gfh_tag_.Li_rhs).AddByteNl();		// EX: "    </li>\n"
				break;
			default: throw ErrUtl.NewUnhandled(CompareAbleUtl.Compare(cur_lvl, prv_lvl));
		}

		// write "<li ..."
		bfr.AddByteRepeat(AsciiByte.Space, indent);	// indent
		fmtr_itm.Bld_many(bfr, itm.Lvl(), itm.Uid(), itm.Anch(), itm.Path_to_bry(numbering_bfr), itm.Text());
		prv_lvl = cur_lvl;
	}
	private static final byte[] Bry_toc_cls = BryUtl.NewA7(" id=\"toc\" class=\"toc\"");
	private final BryFmt
	  fmtr_div = BryFmt.Auto(StringUtl.ConcatLinesNlSkipLast
	( "<div~{toc}>"
	, "  <div id=\"toctitle\" class=\"toctitle\">"
	, "    <h2>~{contents_title}</h2>"
	, "  </div>"
	, "~{itms}</div>"
	, ""
	))
	, fmtr_itm = BryFmt.Auto
	( "  <li class=\"toclevel-~{level} tocsection-~{toc_idx}\"><a href=\"#~{anchor}\"><span class=\"tocnumber\">~{heading}</span> <span class=\"toctext\">~{text}</span></a>\n"
	);
}
