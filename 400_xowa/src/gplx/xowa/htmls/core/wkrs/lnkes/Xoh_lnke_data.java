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
import gplx.core.brys.*; import gplx.core.threads.poolables.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*; import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.htmls.core.hzips.*;
public class Xoh_lnke_data implements Xoh_data_itm {
	public int Tid() {return Xoh_hzip_dict_.Tid__lnke;}
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public byte Lnke_tid() {return lnke_tid;} private byte lnke_tid;
	public boolean Auto_exists() {return auto_id != -1;}
	public int Auto_id() {return auto_id;} private int auto_id;
	public int Href_bgn() {return href_bgn;} private int href_bgn;
	public int Href_end() {return href_end;} private int href_end;
	public int Capt_bgn() {return capt_bgn;} private int capt_bgn;
	public int Capt_end() {return capt_end;} private int capt_end;
	public boolean Capt_exists() {return capt_exists;} private boolean capt_exists;
	public int Title_bgn() {return title_bgn;} private int title_bgn;
	public int Title_end() {return title_end;} private int title_end;
	public boolean Title_exists() {return title_end > title_bgn;}
	public void Clear() {
		capt_exists = false;
		lnke_tid = Byte_ascii.Max_7_bit;
		src_bgn = src_end = href_bgn = href_end = capt_bgn = capt_end = auto_id = title_bgn = title_end = -1;
	}
	public void Init_by_decode(byte lnke_tid, int auto_id, int href_bgn, int href_end, int capt_bgn, int capt_end, boolean capt_exists, int title_bgn, int title_end) {
		this.lnke_tid = lnke_tid; this.auto_id = auto_id; this.href_bgn = href_bgn; this.href_end = href_end;
		this.capt_bgn = capt_bgn; this.capt_end = capt_end; this.capt_exists = capt_exists;
		this.title_bgn = title_bgn; this.title_end = title_end;
	}
	public boolean Init_by_parse(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Gfh_tag_rdr tag_rdr, byte[] src, Gfh_tag anch_head, Gfh_tag unused) {
		this.src_bgn = anch_head.Src_bgn();
		Gfh_atr href_atr = anch_head.Atrs__get_by_or_fail(Gfh_atr_.Bry__href);					// get href; "EX: href='http://a.org'"
		this.href_bgn = href_atr.Val_bgn(); this.href_end = href_atr.Val_end();
		this.lnke_tid = anch_head.Atrs__cls_find_or_fail(Xoh_lnke_dict_.Hash);					// get type by class; EX: "class='external free'"
		this.capt_bgn = anch_head.Src_end();
		Gfh_atr title_atr = anch_head.Atrs__get_by_or_empty(Gfh_atr_.Bry__title);
		this.title_bgn = title_atr.Val_bgn(); this.title_end = title_atr.Val_end();
		Gfh_tag anch_tail = tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__a);							// find '</a>'
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
		return true;
	}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_lnke_data rv = new Xoh_lnke_data(); rv.pool_mgr = mgr; rv.pool_idx = idx; return rv;}
}
