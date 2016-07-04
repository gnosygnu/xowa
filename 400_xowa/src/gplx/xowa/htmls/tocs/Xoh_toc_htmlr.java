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
package gplx.xowa.htmls.tocs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.langs.htmls.*;
class Xoh_toc_htmlr implements gplx.core.brys.Bfr_arg {
	private final    Bry_bfr bfr = Bry_bfr_.New();
	private final    Bry_bfr numbering_bfr = Bry_bfr_.New();
	private Ordered_hash itms;
	private int prv_lvl;
	public void Clear() {
		prv_lvl = 0;
	}
	public byte[] To_html(Ordered_hash itms, byte[] toc_title, boolean page_banner) {
		this.itms = itms;
		fmtr_div.Bld_many(bfr, page_banner ? Bry_.Empty : Bry_toc_cls, toc_title, this);
		return bfr.To_bry_and_clear();
	}
	public byte[] Test__to_html(Ordered_hash itms) {
		this.itms = itms;
		Bfr_arg__add(bfr);
		return bfr.To_bry_and_clear();
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		int len = itms.Len();
		prv_lvl = 0;
		for (int i = 0; i < len; ++i) {
			Xoh_toc_itm itm = (Xoh_toc_itm)itms.Get_at(i);
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
	, "  <div id=\"toctitle\">"
	, "    <h2>~{contents_title}</h2>"
	, "  </div>"
	, "~{itms}</div>"
	, ""
	))
	, fmtr_itm = Bry_fmt.Auto
	( "  <li class=\"toclevel-~{level} tocsection-~{toc_idx}\"><a href=\"#~{anchor}\"><span class=\"tocnumber\">~{heading}</span> <span class=\"toctext\">~{text}</span></a>\n"
	);
}
