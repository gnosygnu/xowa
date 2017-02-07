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
package gplx.xowa.htmls.core.wkrs.tocs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.threads.poolables.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*; import gplx.xowa.htmls.core.hzips.*;	
public class Xoh_toc_data implements Xoh_data_itm {
	public int		Tid()				{return Xoh_hzip_dict_.Tid__toc;}
	public int		Src_bgn()			{return src_bgn;} private int src_bgn;
	public int		Src_end()			{return src_end;} private int src_end;
	public byte		Toc_mode()			{return toc_mode;} private byte toc_mode;
	public void Clear() {
		this.src_bgn = this.src_end = -1;
		this.toc_mode = Toc_mode__none;
	}
	public boolean Init_by_parse(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Gfh_tag_rdr tag_rdr, byte[] src, Gfh_tag toc_lhs, Gfh_tag unused) {
		this.Clear();
		this.src_bgn = toc_lhs.Src_bgn();
		this.toc_mode = toc_lhs.Atrs__has(Xoh_toc_wtr.Atr__data__toc__mode) ? Toc_mode__pgbnr : Toc_mode__basic;
		Gfh_tag toc_rhs = tag_rdr.Tag__move_fwd_tail(toc_lhs.Name_id());
		this.src_end = toc_rhs.Src_end();
		return true;
	}
	public void Init_by_decode(byte toc_mode) {
		this.toc_mode = toc_mode;
	}

	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_toc_data rv = new Xoh_toc_data(); rv.pool_mgr = mgr; rv.pool_idx = idx; return rv;}

	public static final byte Toc_mode__basic = 0, Toc_mode__pgbnr = 1, Toc_mode__none = 2;	// SERIALIZED
}
