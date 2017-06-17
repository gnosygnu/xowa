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
package gplx.xowa.addons.htmls.tocs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.htmls.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.htmls.*;
class Xoh_toc_htmlr implements gplx.core.brys.Bfr_arg {
	private final    Bry_bfr numbering_bfr = Bry_bfr_.New();
	private byte[] toc_label;
	private int prv_lvl;
	private Ordered_hash toc_itms;
	public void Clear() {
		prv_lvl = 0;
	}
	public void Init(byte[] toc_label) {
		this.toc_label = toc_label;
	}
	public void To_html(Bry_bfr rv, Xoh_wtr_ctx hctx, Ordered_hash toc_itms, boolean toc_mode_is_pgbnr) {
		this.toc_itms = toc_itms;
		fmtr_div.Bld_many(rv, toc_mode_is_pgbnr ? Bry_.Empty : Bry_toc_cls, toc_label, this);
	}
	public void Test__to_html(Bry_bfr rv, Ordered_hash toc_itms) {
		this.toc_itms = toc_itms;
		Bfr_arg__add(rv);
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		int len = toc_itms.Len();
		prv_lvl = 0;
		for (int i = 0; i < len; ++i) {
			Xoh_toc_itm itm = (Xoh_toc_itm)toc_itms.Get_at(i);
			Write(bfr, itm);
		}

		// close all open levels
		for (int i = prv_lvl; i > 0; --i) {
			int indent = i * 2;
			bfr.Add_byte_repeat(Byte_ascii.Space, indent + 2).Add(Gfh_tag_.Li_rhs).Add_byte_nl();			// EX: "    </li>\n"
			bfr.Add_byte_repeat(Byte_ascii.Space, indent    ).Add(Gfh_tag_.Ul_rhs).Add_byte_nl();			// EX: "  </ul>\n"
		}
	}
	private void Write(Bry_bfr bfr, Xoh_toc_itm itm) {
		int cur_lvl = itm.Lvl();
		int indent = cur_lvl * 2;
		switch (CompareAble_.Compare(cur_lvl, prv_lvl)) {
			case CompareAble_.More:		// start new "<ul>"
				bfr.Add_byte_repeat(Byte_ascii.Space, indent).Add(Gfh_tag_.Ul_lhs).Add_byte_nl();			// EX: "  <ul>\n"
				break;
			case CompareAble_.Same:		// close old "</li>"; NOTE: Comparable_.Same will never be 1st item (so won't ever get </li><li>)
				bfr.Add_byte_repeat(Byte_ascii.Space, indent + 2).Add(Gfh_tag_.Li_rhs).Add_byte_nl();		// EX: "    </li>\n"
				break;
			case CompareAble_.Less:		// close old "</ul>" and "</li>"
				for (int j = prv_lvl; j > cur_lvl; --j) {
					bfr.Add_byte_repeat(Byte_ascii.Space, (j * 2) + 2).Add(Gfh_tag_.Li_rhs).Add_byte_nl();	// EX: "    </li>\n"
					bfr.Add_byte_repeat(Byte_ascii.Space, (j * 2)    ).Add(Gfh_tag_.Ul_rhs).Add_byte_nl();	// EX: "    </ul>\n"
				}
				bfr.Add_byte_repeat(Byte_ascii.Space, indent + 2).Add(Gfh_tag_.Li_rhs).Add_byte_nl();		// EX: "    </li>\n"
				break;
			default: throw Err_.new_unhandled_default(CompareAble_.Compare(cur_lvl, prv_lvl));
		}

		// write "<li ..."
		bfr.Add_byte_repeat(Byte_ascii.Space, indent);	// indent
		fmtr_itm.Bld_many(bfr, itm.Lvl(), itm.Uid(), itm.Anch(), itm.Path_to_bry(numbering_bfr), itm.Text());
		prv_lvl = cur_lvl;
	}
	private static final    byte[] Bry_toc_cls = Bry_.new_a7(" id=\"toc\" class=\"toc\"");
	private final    Bry_fmt 
	  fmtr_div = Bry_fmt.Auto(String_.Concat_lines_nl_skip_last
	( "<div~{toc}>"
	, "  <div id=\"toctitle\" class=\"toctitle\">"
	, "    <h2>~{contents_title}</h2>"
	, "  </div>"
	, "~{itms}</div>"
	, ""
	))
	, fmtr_itm = Bry_fmt.Auto
	( "  <li class=\"toclevel-~{level} tocsection-~{toc_idx}\"><a href=\"#~{anchor}\"><span class=\"tocnumber\">~{heading}</span> <span class=\"toctext\">~{text}</span></a>\n"
	);
}
