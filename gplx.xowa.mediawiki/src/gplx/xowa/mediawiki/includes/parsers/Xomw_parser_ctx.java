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
package gplx.xowa.mediawiki.includes.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import gplx.xowa.mediawiki.includes.parsers.lnkis.*;
public class Xomw_parser_ctx {
	public Xoa_ttl Page_title() {return page_title;} private Xoa_ttl page_title;
	public Xomw_image_params         Lnki_wkr__make_image__img_params = new Xomw_image_params();
	public byte[][]                  Lnki_wkr__make_image__match_magic_word = new byte[2][];
	public int[]                     Lnki_wkr__make_image__img_size = new int[2];
	public Xomw_params_mto           Linker__makeImageLink__prms = new Xomw_params_mto();

	public void Init_by_page(Xoa_ttl page_title) {
		this.page_title = page_title;
	}

	public static final int Pos__bos = -1;
}
