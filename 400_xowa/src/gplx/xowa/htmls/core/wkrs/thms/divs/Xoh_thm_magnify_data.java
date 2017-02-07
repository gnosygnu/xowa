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
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*;
public class Xoh_thm_magnify_data {
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public boolean Exists() {return exists;} private boolean exists;
	public Gfh_tag Magnify_tail_div() {return magnify_tail_div;} private Gfh_tag magnify_tail_div;
	public void Clear() {
		this.exists = false;
		this.src_bgn = src_end = -1;
		this.magnify_tail_div = null;
	}
	public boolean Parse(Xoh_hdoc_wkr hdoc_wkr, Gfh_tag_rdr tag_rdr, byte[] src, Gfh_tag div_caption) {
		// rdr.Init_by_sub(tag_rdr.Rdr(), "thm.magnify", div_caption.Src_bgn(), div_caption.Src_end());
		this.src_bgn = div_caption.Src_bgn(); this.src_end = div_caption.Src_end();
		Gfh_tag div_magnify = tag_rdr.Tag__move_fwd_head();
		if (div_magnify.Name_id() != Gfh_tag_.Id__div) return false;	// NOTE: some thumbs can be artificially constructed and not have magnify div; PAGE:s.w:Asthma; DATE:2015-11-29
		if (!div_magnify.Atrs__cls_has(Cls__magnify)) return false;
		this.exists = div_magnify.Src_exists();
		if (exists) {
			magnify_tail_div = tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__div);
			this.src_end = magnify_tail_div.Src_end();
			tag_rdr.Pos_(src_end);
		}
		return true;
	}
	private static final byte[] Cls__magnify = Bry_.new_a7("magnify");
}
