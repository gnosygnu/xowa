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
package gplx.xowa.htmls.core.wkrs.xndes; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.langs.htmls.parsers.*;
import gplx.xowa.htmls.core.hzips.*;
public class Xoh_xnde_parser {
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public int Name_id() {return name_id;} private int name_id;
	public Html_atr[] Atrs() {return atrs;} private Html_atr[] atrs;
	public boolean Parse(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Html_tag_rdr tag_rdr, byte[] src, Html_tag head) {
		this.src_bgn = head.Src_bgn();
		this.name_id = head.Name_id();
		int len = head.Atrs__len();
		this.atrs = new Html_atr[len];
		for (int i = 0; i < len; ++i)
			atrs[i] = head.Atrs__get_at(i);
		this.src_end = head.Src_end();
		/*
		Html_tag tail = tag_rdr.Tag__move_fwd_tail(head.Name_id());
		this.src_end = tail.Src_end();
		// Recurse_content
		*/
		return true;
	}
}
