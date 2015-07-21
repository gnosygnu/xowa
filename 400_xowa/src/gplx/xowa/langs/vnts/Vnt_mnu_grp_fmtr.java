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
package gplx.xowa.langs.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
public class Vnt_mnu_grp_fmtr implements Bry_fmtr_arg {
	private Vnt_mnu_grp grp;
	private final Xolg_vnt_itm_fmtr itm_fmtr = new Xolg_vnt_itm_fmtr();
	public void Init(Vnt_mnu_grp grp, byte[] page_href, byte[] page_vnt) {
		this.grp = grp;
		itm_fmtr.Init(grp, page_href, page_vnt);
	}
	public void XferAry(Bry_bfr bfr, int idx) {
		fmtr.Bld_bfr_many(bfr, grp.Text(), itm_fmtr);
	}
	private static final Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "    <div id='p-variants' role='navigation' class='vectorMenu' aria-labelledby='p-variants-label'>"
	, "      <h3 id='p-variants-label'><span>~{grp_text}</span><a href='#'></a></h3>"
	, "      <div class='menu'>"
	, "        <ul>~{itms}"
	, "        </ul>"
	, "      </div>"
	, "    </div>"
	), "grp_text", "itms"
	);
}
class Xolg_vnt_itm_fmtr implements Bry_fmtr_arg {
	private Vnt_mnu_grp grp; private byte[] page_href, page_vnt;
	public void Init(Vnt_mnu_grp grp, byte[] page_href, byte[] page_vnt) {this.grp = grp; this.page_href = page_href; this.page_vnt = page_vnt;}
	public void XferAry(Bry_bfr bfr, int idx) {
		int len = grp.Len();
		for (int i = 0; i < len; ++i) {
			Vnt_mnu_itm itm = grp.Get_at(i);
			boolean itm_is_selected = Bry_.Eq(itm.Key(), page_vnt);
			byte[] itm_cls_selected = itm_is_selected ? Itm_cls_selected_y : Bry_.Empty;
			fmtr.Bld_bfr_many(bfr, i, itm_cls_selected, itm.Key(), itm.Text(), page_href);
		}
	}
	private static final byte[] Itm_cls_selected_y = Bry_.new_a7(" class='selected'");
	private static final Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "          <li id='ca-varlang-~{itm_idx}'~{itm_cls_selected}><a href='/wiki/~{itm_href}?xowa_vnt=~{itm_lang}' lang='~{itm_lang}' hreflang='~{itm_lang}'>~{itm_text}</a></li>"
	), "itm_idx", "itm_cls_selected", "itm_lang", "itm_text", "itm_href"
	);
}
