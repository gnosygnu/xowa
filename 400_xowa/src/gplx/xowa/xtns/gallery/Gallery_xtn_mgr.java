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
package gplx.xowa.xtns.gallery; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.htmls.modules.*;
public class Gallery_xtn_mgr extends Xox_mgr_base {
	@Override public byte[] Xtn_key() {return XTN_KEY;} public static final    byte[] XTN_KEY = Bry_.new_a7("gallery");
	@Override public Xox_mgr Xtn_clone_new() {return new Gallery_xtn_mgr();}
	public Gallery_itm_parser Parser() {return parser;} private Gallery_itm_parser parser;
	public Gallery_html_wtr Html_wtr() {return html_wtr;} private Gallery_html_wtr html_wtr;
	@Override public void Xtn_init_by_wiki(Xowe_wiki wiki) {
		parser = new Gallery_itm_parser();
		parser.Init_by_wiki(wiki);
		html_wtr = new Gallery_html_wtr();
	}
}
