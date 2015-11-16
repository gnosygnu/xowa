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
package gplx.xowa.htmls.core.wkrs.escapes; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*;
import gplx.xowa.htmls.core.hzips.*;
public class Xoh_escape_parser implements Html_doc_wkr {
	private final Xoh_hdoc_wkr wkr;
	public Xoh_escape_parser(Xoh_hdoc_wkr wkr) {this.wkr = wkr;}
	public byte[] Hook() {return Xoh_hzip_dict_.Escape_bry;}
	public int Parse(byte[] src, int src_bgn, int src_end, int pos) {
		int rv = pos + 1;
		wkr.On_escape(pos, rv);
		return rv;
	}
}
