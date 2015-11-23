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
package gplx.xowa.htmls.core.wkrs.lnkes; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*; import gplx.xowa.htmls.hrefs.*;
public class Xoh_lnke_parser {
	private final Bry_rdr rdr = new Bry_rdr();
	public int Rng_bgn() {return rng_bgn;} private int rng_bgn;
	public int Rng_end() {return rng_end;} private int rng_end;
	public byte Anch_cls_type() {return anch_cls_type;} private byte anch_cls_type;
	public int Auto_id() {return auto_id;} private int auto_id;
	public int Href_bgn() {return href_bgn;} private int href_bgn;
	public int Href_end() {return href_end;} private int href_end;
	public int Capt_bgn() {return capt_bgn;} private int capt_bgn;
	public int Capt_end() {return capt_end;} private int capt_end;
	private void Clear() {
		anch_cls_type = Byte_ascii.Max_7_bit;
		auto_id = rng_bgn = rng_end = href_bgn = href_end = capt_bgn = capt_end = -1;
	}
	public int Parse(Xoh_hdoc_wkr hdoc_wkr, Html_tag_rdr tag_rdr, Html_tag anch_head) {
		this.Clear();
		this.rng_bgn = anch_head.Src_bgn();
		rdr.Init_by_hook("lnke", rng_bgn, rng_bgn);
		Html_atr href_atr = anch_head.Atrs__get_by_or_fail(Html_atr_.Bry__href);				// get href; "EX: href='http://a.org'"
		this.href_bgn = href_atr.Val_bgn(); this.href_end = href_atr.Val_end();
		this.anch_cls_type = anch_head.Atrs__cls_find_or_fail(Xoh_lnke_dict_.Hash);				// get type by class; EX: "class='external free'"
		boolean capt_exists = false;
		switch (anch_cls_type) {
			case Xoh_lnke_dict_.Type__text: capt_exists = true; break;
			case Xoh_lnke_dict_.Type__auto:
				if (tag_rdr.Read_and_move(Byte_ascii.Brack_bgn))								// HTML tidy can reparent lnkes in strange ways; DATE:2015-08-25
					this.auto_id = tag_rdr.Read_int_to(Byte_ascii.Brack_end);					// extract int; EX: "<a ...>[123]</a>"
				else
					capt_exists = true;
				break;
		}
		if (capt_exists) this.capt_bgn = anch_head.Src_end();
		Html_tag anch_tail = tag_rdr.Tag__move_fwd_tail(Html_tag_.Id__a);						// find '</a>'
		if (capt_exists) this.capt_end = anch_tail.Src_bgn();
		this.rng_end = anch_tail.Src_end();
		hdoc_wkr.On_lnke(this);
		return rng_end;
	}
}
