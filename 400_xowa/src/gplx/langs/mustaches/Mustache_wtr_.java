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
package gplx.langs.mustaches; import gplx.*; import gplx.langs.*;
public class Mustache_wtr_ {
	public static byte[] Write_to_bry(byte[] src, Mustache_doc_itm itm) {return Write_to_bry(Bry_bfr_.New(), src, itm);}
	public static byte[] Write_to_bry(Bry_bfr bfr, byte[] src, Mustache_doc_itm itm) {
		Mustache_tkn_parser parser = new Mustache_tkn_parser();
		Mustache_tkn_itm root = parser.Parse(src, 0, src.length);
		Mustache_render_ctx ctx = new Mustache_render_ctx().Init(itm);
		Mustache_bfr mbfr = new Mustache_bfr(bfr);
		root.Render(mbfr, ctx);
		return mbfr.To_bry_and_clear();
	}
}
