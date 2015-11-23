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
package gplx.xowa.htmls.core.wkrs.glys; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*;	
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*; import gplx.langs.htmls.parsers.styles.*; import gplx.langs.htmls.parsers.clses.*;
public class Xoh_gly_grp_parser implements Html_atr_class_wkr {
	public int Rng_bgn() {return rng_bgn;} private int rng_bgn;
	public int Rng_end() {return rng_end;} private int rng_end;
//		private Xoh_gly_itm_parser itm_parser = new Xoh_gly_itm_parser();
	public int Parse(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, byte[] src, Html_tag_rdr tag_rdr, Html_tag ul_tag) {
		this.rng_bgn = ul_tag.Src_bgn();
		/*
		parse class for traditional
		parse style for max_width
		*/
//			Html_tag cur_tag = null;
//			while (true) {
//				cur_tag = tag_rdr.Tag__move_fwd_head();
//				if (cur_tag.Name_id() != Html_tag_.Id__li) break;	// no more <li>; break;
//				itm_parser.Parse(hdoc_wkr, hctx, src, tag_rdr, cur_tag);
//			}
//			hdoc_wkr.On_thm(this);
		Html_tag cur_tag = tag_rdr.Tag__move_fwd_tail(Html_tag_.Id__ul);
		this.rng_end = cur_tag.Src_end();
		return rng_end;
	}
	public boolean On_cls(byte[] src, int atr_idx, int atr_bgn, int atr_end, int val_bgn, int val_end) {
//			if (Bry_.Match(src, val_bgn, val_end, Bry__cls__traditional)) {
//
//			}
		return false;
	}
	public static final byte[] Atr__class__gallery = Bry_.new_a7("gallery");
}
