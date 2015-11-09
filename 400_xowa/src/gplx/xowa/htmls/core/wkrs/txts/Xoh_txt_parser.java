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
package gplx.xowa.htmls.core.wkrs.txts; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.langs.htmls.parsers.*;
public class Xoh_txt_parser implements Html_txt_wkr {
	private final Xoh_hdoc_wkr wkr;
	public Xoh_txt_parser(Xoh_hdoc_wkr wkr) {this.wkr = wkr;}
	public void Init(byte[] src, int src_bgn, int src_end) {}
	public void Parse(int rng_bgn, int rng_end) {
		wkr.On_txt(rng_bgn, rng_end);
	}
}
