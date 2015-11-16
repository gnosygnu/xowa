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
package gplx.xowa.htmls.core.wkrs.imgs.atrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.imgs.*;
import gplx.core.brys.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*;
public class Xoh_anch_cls_parser {
	private final Bry_rdr rdr = new Bry_rdr();
	public byte Tid() {return tid;} private byte tid;
	public Html_atr Atr() {return atr;} private Html_atr atr;
	public void Parse(Bry_rdr owner_rdr, byte[] src, Html_tag tag) {
		this.atr = tag.Atrs__get_by_or_empty(Html_atr_.Bry__class);		// EX: class='image'
		int src_bgn = atr.Val_bgn(); int src_end = atr.Val_end();
		if (src_bgn == -1)
			tid = Xoh_anch_cls_.Tid__none;
		else {
			rdr.Init_by_sub(owner_rdr, "anch.cls", src_bgn, src_end);
			this.tid = rdr.Chk(Xoh_anch_cls_.Trie);
		}
	}
}
