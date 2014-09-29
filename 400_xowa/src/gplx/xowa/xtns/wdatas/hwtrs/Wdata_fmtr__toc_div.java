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
class Wdata_fmtr__toc_div implements Bry_fmtr_arg {
	private Wdata_fmtr__toc_itm fmtr_itm = new Wdata_fmtr__toc_itm();
	private byte[] tbl_hdr; private ListAdp itms = ListAdp_.new_();
	public void Init_by_ctor(Url_encoder href_encoder) {fmtr_itm.Init_by_ctor(href_encoder);}
	public void Init_by_lang(Wdata_hwtr_msgs msgs) {this.tbl_hdr = msgs.Toc_tbl_hdr();}
	public void Init_by_wdoc(Wdata_doc wdoc) {itms.Clear();}
	public void Add(byte[] hdr) {itms.Add(hdr);}
	public void XferAry(Bry_bfr bfr, int idx) {
		int itms_len = itms.Count();
		if (itms_len < 3) return;
		fmtr_itm.Init_by_page((byte[][])itms.XtoAryAndClear(byte[].class));
		fmtr.Bld_bfr_many(bfr, tbl_hdr, fmtr_itm);
	}
	private Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "<p>"
	, "  <div id='toc' class='toc'>"
	, "    <div id='toctitle'><h2>~{hdr}</h2></div>"
	, "    <ul>~{itms}"
	, "    </ul>"
	, "  </div}"
	, "</p>"
	), "hdr", "itms");
}
class Wdata_fmtr__toc_itm implements Bry_fmtr_arg {
	private byte[][] hdrs; private Url_encoder href_encoder;
	public void Init_by_ctor(Url_encoder href_encoder) {this.href_encoder = href_encoder;}
	public void Init_by_page(byte[][] hdrs) {this.hdrs = hdrs;}
	public void XferAry(Bry_bfr bfr, int idx) {
		int len = hdrs.length;
		for (int i = 0; i < len; ++i) {
			byte[] hdr = hdrs[i];
			fmtr.Bld_bfr_many(bfr, i + ListAdp_.Base1, href_encoder.Encode(hdr), hdr);
		}
	}
	private Bry_fmtr fmtr = Bry_fmtr.new_
	( "      <li class='toclevel-1 tocsection-~{idx}'><a href='#~{hdr}'><span class='tocnumber'>~{idx}</span> <span class='toctext'>~{hdr}</span></a></li>"
	, "idx", "href", "text");
}
