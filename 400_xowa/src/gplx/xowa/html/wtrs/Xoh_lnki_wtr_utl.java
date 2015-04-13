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
package gplx.xowa.html.wtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
public class Xoh_lnki_wtr_utl {
	private final Xow_wiki wiki; private final Xoh_href_parser href_parser; private final Bry_bfr tmp_bfr = Bry_bfr.new_(255);
	public Xoh_lnki_wtr_utl(Xow_wiki wiki, Xoh_href_parser href_parser) {
		this.wiki = wiki; this.href_parser = href_parser;
	}
	public byte[] Bld_href(byte[] page) {
		return href_parser.Build_to_bry(wiki, wiki.Ttl_parse(page));
	}
	public byte[] Bld_title(byte[] text) {
		return gplx.html.Html_utl.Escape_html_as_bry(tmp_bfr, text, Bool_.N, Bool_.N, Bool_.N, Bool_.Y, Bool_.Y);
	}
}
