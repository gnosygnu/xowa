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
package gplx.xowa.addons.htmls.scripts.apis; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.htmls.*; import gplx.xowa.addons.htmls.scripts.*;
public class Xoscript_doc_tail {
	private final    Xoscript_doc doc;
	public Xoscript_doc_tail(Xoscript_doc doc) {this.doc = doc;}
	public void Add_js_file(String file, String pos) {
		String marker = String_.Eq(pos, "top") ? "<!--XOWA.SCRIPT.TAIL-->" : "<!--XOWA.SCRIPT.TAIL-->";
		if (String_.Has_at_bgn(file, "./")) file = String_.Replace(file, "./", doc.Page().Env().Root_dir().To_http_file_str());
		String elem = "<script src=\"" + file + "\" type=\"text/javascript\"></script>\n";
		doc.Html_(String_.Replace(doc.Html(), marker, marker + elem));
	}
	public void Add_elem(String elem, String pos) {
		String marker = String_.Eq(pos, "top") ? "<!--XOWA.SCRIPT.TAIL-->" : "<!--XOWA.SCRIPT.TAIL-->";
		doc.Html_(String_.Replace(doc.Html(), marker, marker + elem));
	}
}
