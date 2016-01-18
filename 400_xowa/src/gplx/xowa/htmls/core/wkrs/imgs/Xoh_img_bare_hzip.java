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
import gplx.core.brys.*; import gplx.core.threads.poolables.*; import gplx.core.encoders.*;
import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.wkrs.imgs.atrs.*;
public class Xoh_img_bare_hzip implements Xoh_hzip_wkr, Gfo_poolable_itm {
	public int Tid() {return Xoh_hzip_dict_.Tid__img_bare;}
	public String Key() {return Xoh_hzip_dict_.Key__img_bare;}
	public byte[] Hook() {return hook;} private byte[] hook;
	public Gfo_poolable_itm Encode1(Xoh_hzip_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, boolean wkr_is_root, byte[] src, Object data_obj) {
		Xoh_img_bare_data data = (Xoh_img_bare_data)data_obj;
		int img_tid = flag_bldr.Set_as_int(Flag__img_tid		, data.Img_tid());

		bfr.Add(hook);
		Gfo_hzip_int_.Encode(1, bfr, flag_bldr.Encode());
		switch (img_tid) {
			case Xoh_img_bare_data.Img_tid__hiero:
				bfr.Add_hzip_mid(src, data.Src_bgn(), data.Dir_bgn());
				bfr.Add_hzip_mid(src, data.Dir_end(), data.Src_end());
				break;
		}
		return this;
	}
	public void Decode1(Bry_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, Bry_rdr rdr, byte[] src, int src_bgn, int src_end, Xoh_data_itm data_itm) {
		Xoh_img_bare_data data = (Xoh_img_bare_data)data_itm; data.Clear();

		int flag = rdr.Read_hzip_int(1); flag_bldr.Decode(flag);
		int dir_tid	= flag_bldr.Get_as_int(Flag__img_tid);

		switch (dir_tid) {
			case Xoh_img_bare_data.Img_tid__hiero:
				int tag_0_bgn = rdr.Pos(), tag_0_end = rdr.Find_fwd_lr();
				int tag_1_bgn = rdr.Pos(), tag_1_end = rdr.Find_fwd_lr();
				data.Init_by_decode__hiero(tag_0_bgn, tag_1_end, tag_0_end, tag_1_bgn);
				break;
		}
	}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_img_bare_hzip rv = new Xoh_img_bare_hzip(); rv.pool_mgr = mgr; rv.pool_idx = idx; rv.hook = (byte[])args[0]; return rv;}
	private final Int_flag_bldr flag_bldr = new Int_flag_bldr().Pow_ary_bld_(2);	
	private static final int // SERIALIZED
	  Flag__img_tid						=  0
	;
}
