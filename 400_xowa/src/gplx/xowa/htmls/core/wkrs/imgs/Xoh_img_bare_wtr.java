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
package gplx.xowa.htmls.core.wkrs.imgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*; import gplx.core.threads.poolables.*; import gplx.core.brys.fmtrs.*;
import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.wkrs.imgs.atrs.*;
import gplx.xowa.files.*;
public class Xoh_img_bare_wtr implements Bfr_arg, Xoh_wtr_itm {
	private byte[] src;
	private Xoh_img_bare_data data_itm;
	private Xoh_hdoc_ctx hctx;
	public void Init_by_parse(Bry_bfr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, Xoh_img_bare_data data) {
		this.src = src; this.hctx = hctx;
		this.data_itm = data;
	}
	public boolean Init_by_decode(Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, Xoh_data_itm data_itm) {
		this.src = src; this.hctx = hctx;
		this.data_itm = (Xoh_img_bare_data)data_itm;			
		return true;
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		byte[] url_bry = Bry_.Empty;
		switch (data_itm.Img_tid()) {
			case Xoh_img_bare_data.Img_tid__hiero:		url_bry = Xoh_img_bare_data.Url__hiero; break;
			case Xoh_img_bare_data.Img_tid__imap_btn:	url_bry = Xoh_img_bare_data.Url__imap; break;
		}
		bfr.Add_mid(src, data_itm.Src_bgn(), data_itm.Dir_bgn());
		bfr.Add(hctx.Fsys__res()).Add(url_bry);	// NOTE: must use Fsys_res will be android_asset on drd; en.w:Hieroglyphics DATE:2016-01-31
		bfr.Add_mid(src, data_itm.Dir_end(), data_itm.Src_end());
	}

	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_img_bare_wtr rv = new Xoh_img_bare_wtr(); rv.pool_mgr = mgr; rv.pool_idx = idx; return rv;}
}
