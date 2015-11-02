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
package gplx.xowa.htmls.core.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.langs.htmls.parsers.*; import gplx.xowa.htmls.core.wkrs.txts.*; import gplx.xowa.htmls.core.wkrs.escapes.*; import gplx.xowa.htmls.core.wkrs.spaces.*;
public class Xoh_parser_ {
	public static Html_doc_parser new_(Xoh_wkr wkr, Xow_wiki wiki) {
		Html_doc_parser rv = new Html_doc_parser();
		rv.Reg_txt(new Xoh_txt_parse(wkr)).Reg
		( new Xoh_doc_wkr__tag(wkr, wiki)
		, new Xoh_escape_parse(wkr)
		, new Xoh_space_parse(wkr)
		);
		return rv;
	}
}
