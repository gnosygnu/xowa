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
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public byte Lnke_tid() {return lnke_tid;} private byte lnke_tid;
	public int Auto_id() {return auto_id;} private int auto_id;
	public int Href_bgn() {return href_bgn;} private int href_bgn;
	public int Href_end() {return href_end;} private int href_end;
	public int Capt_bgn() {return capt_bgn;} private int capt_bgn;
	public int Capt_end() {return capt_end;} private int capt_end;
	public boolean Capt_exists() {return capt_exists;} private boolean capt_exists;
	private void Clear() {
		lnke_tid = Byte_ascii.Max_7_bit;
		capt_exists = false;
		src_bgn = src_end = href_bgn = href_end = capt_bgn = capt_end = auto_id = -1;
	}
	public boolean Parse(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Html_tag_rdr tag_rdr, byte[] src, Html_tag anch_head) {
		this.Clear();
		this.src_bgn = anch_head.Src_bgn();
		rdr.Init_by_sect("lnke", src_bgn, src_bgn);
		Html_atr href_atr = anch_head.Atrs__get_by_or_fail(Html_atr_.Bry__href);				// get href; "EX: href='http://a.org'"
		this.href_bgn = href_atr.Val_bgn(); this.href_end = href_atr.Val_end();
		this.lnke_tid = anch_head.Atrs__cls_find_or_fail(Xoh_lnke_dict_.Hash);					// get type by class; EX: "class='external free'"
		this.capt_bgn = anch_head.Src_end();
		Html_tag anch_tail = tag_rdr.Tag__move_fwd_tail(Html_tag_.Id__a);						// find '</a>'
		this.capt_end = anch_tail.Src_bgn();
		switch (lnke_tid) {
			case Xoh_lnke_dict_.Type__free:
				if (!Bry_.Match(src, href_bgn, href_end, src, capt_bgn, capt_end)) 				// EX: <a href='https://a.org/. ' rel='nofollow' class='external free'>https://a.org/.</a>
					capt_exists = true;
				break;
			case Xoh_lnke_dict_.Type__text:
				capt_exists = true;
				break;
			case Xoh_lnke_dict_.Type__auto:
				if (	src[capt_bgn]		== Byte_ascii.Brack_bgn								// is capt surround by bracks; EX: "[123]"
					&&	src[capt_end - 1]	== Byte_ascii.Brack_end) {
					int tmp_id = Bry_.To_int_or(src, capt_bgn + 1, capt_end - 1, -1);			// extract int; EX: "<a ...>[123]</a>"
					if (tmp_id == -1)															// HTML tidy can reparent lnkes in strange ways; EX: "<a ...><b>[123]</b></a>" DATE:2015-08-25
						capt_exists = true;
					else
						auto_id = tmp_id;
				}
				else
					capt_exists = true;
				break;
		}
		this.src_end = anch_tail.Src_end();
		hdoc_wkr.On_lnke(this);
		return true;
	}
}
