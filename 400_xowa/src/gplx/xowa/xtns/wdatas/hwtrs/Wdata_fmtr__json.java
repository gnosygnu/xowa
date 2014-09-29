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
import gplx.json.*;
class Wdata_fmtr__json implements Bry_fmtr_arg {
	private byte[] tbl_hdr; private Json_doc jdoc; private Json_parser jdoc_parser; private Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	public void Init_by_ctor(Json_parser jdoc_parser) {this.jdoc_parser = jdoc_parser;}
	public void Init_by_lang(byte[] tbl_hdr) {this.tbl_hdr = tbl_hdr;}
	public void Init_by_wdoc(Wdata_fmtr__toc_div fmtr_toc, Json_doc jdoc) {
		fmtr_toc.Add(tbl_hdr);
		this.jdoc = jdoc;
	}
	public void XferAry(Bry_bfr bfr, int idx) {
		if (jdoc == null) return;
		Wdata_wiki_mgr.Write_json_as_html(jdoc_parser, tmp_bfr, jdoc.Src());
		fmtr.Bld_bfr_many(bfr, tbl_hdr, tmp_bfr.XtoAryAndClear());
	}
	private Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "<h2>~{hdr}</h2>"
	, ""
	, "~{json}"
	), "hdr", "json");
}
