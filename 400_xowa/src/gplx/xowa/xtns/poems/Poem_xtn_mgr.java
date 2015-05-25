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
package gplx.xowa.xtns.poems; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Poem_xtn_mgr extends Xox_mgr_base {
	@Override public byte[] Xtn_key() {return XTN_KEY;} public static final byte[] XTN_KEY = Bry_.new_a7("poem");
	@Override public Xox_mgr Clone_new() {return new Poem_xtn_mgr();}
	public Xop_parser Parser() {return parser;} private Xop_parser parser;
	@Override public void Xtn_init_by_wiki(Xowe_wiki wiki) {
		parser = new Xop_parser(wiki, wiki.Parser().Tmpl_lxr_mgr(), wiki.Parser().Wtxt_lxr_mgr());
		parser.Init_by_wiki(wiki);
		parser.Init_by_lang(wiki.Lang());
	}
}
