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
package gplx.xowa.htmls.core.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.langs.htmls.parsers.*; import gplx.xowa.wikis.ttls.*;
import gplx.xowa.htmls.core.wkrs.tags.*; import gplx.xowa.htmls.core.wkrs.txts.*; import gplx.xowa.htmls.core.wkrs.escapes.*; import gplx.xowa.htmls.core.wkrs.spaces.*;
public class Xoh_hdoc_wkr__base {
	private final Html_doc_parser hdoc_parser = new Html_doc_parser();
	private final Xoh_hdoc_wkr hdoc_wkr;
	private final Xoh_doc_wkr__tag wkr__tag = new Xoh_doc_wkr__tag();
	public Xoh_hdoc_wkr__base(Xoh_hdoc_wkr hdoc_wkr) {
		this.hdoc_wkr = hdoc_wkr;
		this.hdoc_parser.Reg_txt(new Xoh_txt_parser(hdoc_wkr)).Reg_wkrs
		( wkr__tag
		, new Xoh_escape_parser(hdoc_wkr)
		, new Xoh_space_parser(hdoc_wkr)
		);
	}
	public void Parse(Bry_bfr bfr, Xow_wiki wiki, Xoh_page hpg, byte[] page_url, byte[] src) {
		int src_len = src.length;
		hdoc_wkr.On_new_page(bfr, wiki, hpg, src, 0, src_len);
		wkr__tag.Ctor(hdoc_wkr, wiki).Init(src, 0, src_len);
		hdoc_parser.Parse(src, 0, src_len);
	}
}
