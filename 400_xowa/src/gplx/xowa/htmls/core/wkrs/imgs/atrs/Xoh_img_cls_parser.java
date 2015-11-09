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
public class Xoh_img_cls_parser {
	private final Bry_rdr rdr = new Bry_rdr();
	public byte Cls_tid() {return cls_tid;} private byte cls_tid;
	public int Other_bgn() {return other_bgn;} private int other_bgn;
	public int Other_end() {return other_end;} private int other_end;
	public boolean Other_exists() {return other_end > other_bgn;}
	public void Parse(Bry_rdr owner_rdr, byte[] src, Html_tag tag) {
		Html_atr atr = tag.Atrs__get_by_or_empty(Html_atr_.Bry__class);						// EX: class='thumbborder'
		Parse(owner_rdr, src, atr.Val_bgn(), atr.Val_end());
	}
	public void Parse(Bry_rdr owner_rdr, byte[] src, int src_bgn, int src_end) {
		if (src_bgn == -1) {
			this.cls_tid = Xoh_img_cls_.Tid__none;
			this.other_bgn = this.other_end = -1;
			return;
		}
		rdr.Init_by_sub(owner_rdr, "img.cls", src_bgn, src_end);
		this.cls_tid = rdr.Chk(Xoh_img_cls_.Trie);
		if (rdr.Is(Byte_ascii.Space)) {
			this.other_bgn = rdr.Pos();
			this.other_end = src_end;
		}
		else
			other_bgn = other_end = -1;
	}
}
