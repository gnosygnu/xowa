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
package gplx.xowa.xtns.wdatas.hwtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.core.brys.fmtrs.*;
import gplx.langs.htmls.encoders.*;
class Wdata_fmtr__toc_div implements gplx.core.brys.Bfr_arg {
	private final List_adp itms = List_adp_.new_(); private final  Wdata_fmtr__toc_itm fmtr_itm = new Wdata_fmtr__toc_itm();
	private byte[] tbl_hdr; 
	public void Init_by_lang(Wdata_hwtr_msgs msgs)	{this.tbl_hdr = msgs.Toc_tbl_hdr();}
	public void Init_by_wdoc(Wdata_doc wdoc)		{itms.Clear();}
	public void Add(Wdata_toc_data toc_data)		{itms.Add(toc_data);}
	public void Bfr_arg__add(Bry_bfr bfr) {
		int itms_len = itms.Count();
		if (itms_len <= gplx.xowa.htmls.tocs.Xow_hdr_mgr.Toc_min) return;
		fmtr_itm.Init_by_itm((Wdata_toc_data[])itms.To_ary_and_clear(Wdata_toc_data.class));
		fmtr.Bld_bfr_many(bfr, tbl_hdr, fmtr_itm);
	}
	private final Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "  <div id='toc' class='toc wb-toc'>"
	, "    <div id='toctitle'>"
	, "      <h2>~{hdr}</h2>"
	, "    </div>"
	, "    <ul>~{itms}"
	, "    </ul>"
	, "  </div>"
	), "hdr", "itms");
}
class Wdata_fmtr__toc_itm implements gplx.core.brys.Bfr_arg {
	private Wdata_toc_data[] ary;
	public void Init_by_itm(Wdata_toc_data[] v)				{this.ary = v;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Wdata_toc_data itm = ary[i];
			fmtr.Bld_bfr_many(bfr, i + List_adp_.Base1, itm.Href(), itm.Text());
		}
	}
	private final Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "      <li class='toclevel-1 tocsection-~{idx}'>"
	, "        <a href='#~{href}'>"
	, "          <span class='toctext'>~{text}</span>"
	, "        </a>"
	, "      </li>"
	), "idx", "href", "text");
}
class Wdata_toc_data {
	private final Wdata_fmtr__toc_div fmtr_toc;
	private final Gfo_url_encoder href_encoder;
	private final Bry_fmtr text_fmtr = Bry_fmtr.new_("~{orig} <sup><small>(~{len})</small></sup>", "orig", "len");
	private final Bry_bfr tmp_bfr = Bry_bfr.new_(8);
	public Wdata_toc_data(Wdata_fmtr__toc_div fmtr_toc, Gfo_url_encoder href_encoder) {this.fmtr_toc = fmtr_toc; this.href_encoder = href_encoder;}
	public Wdata_toc_data Make(int itms_len) {
		this.text = itms_len_enable ? text_fmtr.Bld_bry_many(tmp_bfr, orig, itms_len) : orig;
		this.href = href_encoder.Encode(orig);
		fmtr_toc.Add(this);
		return this;
	}
	public Wdata_toc_data Itms_len_enable_n_() {itms_len_enable = false; return this;} private boolean itms_len_enable = true;
	public byte[] Orig() {return orig;} public void Orig_(byte[] v) {orig = v;} private byte[] orig;
	public byte[] Href() {return href;} private byte[] href;
	public byte[] Text() {return text;} private byte[] text;
}
