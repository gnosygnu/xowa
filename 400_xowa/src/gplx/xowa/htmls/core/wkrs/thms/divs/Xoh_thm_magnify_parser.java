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
package gplx.xowa.htmls.core.wkrs.thms.divs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.thms.*;
import gplx.core.brys.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*;
public class Xoh_thm_magnify_parser {
	public int Rng_bgn() {return rng_bgn;} private int rng_bgn;
	public int Rng_end() {return rng_end;} private int rng_end;
	public boolean Exists() {return exists;} private boolean exists;
	public Html_tag Magnify_tail_div() {return magnify_tail_div;} private Html_tag magnify_tail_div;
	public boolean Parse(Xoh_hdoc_wkr hdoc_wkr, Html_tag_rdr tag_rdr, byte[] src, Html_tag div_caption) {
		// rdr.Init_by_sub(tag_rdr.Rdr(), "thm.magnify", div_caption.Src_bgn(), div_caption.Src_end());
		this.rng_bgn = div_caption.Src_bgn(); this.rng_end = div_caption.Src_end();
		Html_tag div_magnify = tag_rdr.Tag__move_fwd_head();
		if (div_magnify.Name_id() != Html_tag_.Id__div) return false;	// NOTE: some thumbs can be artificially constructed and not have magnify div; PAGE:s.w:Asthma; DATE:2015-11-29
		if (!div_magnify.Atrs__cls_has(Cls__magnify)) return false;
		this.exists = div_magnify.Src_exists();
		if (exists) {
			magnify_tail_div = tag_rdr.Tag__move_fwd_tail(Html_tag_.Id__div);
			this.rng_end = magnify_tail_div.Src_end();
			tag_rdr.Pos_(rng_end);
		}
		return true;
	}
	private static final byte[] Cls__magnify = Bry_.new_a7("magnify");
}