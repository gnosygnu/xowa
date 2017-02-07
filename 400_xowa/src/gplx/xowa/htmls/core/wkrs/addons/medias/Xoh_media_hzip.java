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
package gplx.xowa.htmls.core.wkrs.addons.medias; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.addons.*;
import gplx.core.brys.*; import gplx.core.encoders.*; import gplx.core.threads.poolables.*;
import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.wkrs.imgs.*;
public class Xoh_media_hzip implements Xoh_hzip_wkr, Gfo_poolable_itm {
	private final    Xoh_img_hzip img_hzip = new Xoh_img_hzip();
	private final    Xoh_media_wtr wtr = new Xoh_media_wtr();
	public int Tid()		{return Xoh_hzip_dict_.Tid__media;}
	public String Key()		{return Xoh_hzip_dict_.Key__media;}
	public byte[] Hook()	{return hook;} private byte[] hook;
	public Gfo_poolable_itm Encode1(Xoh_hzip_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, boolean wkr_is_root, byte[] src, Object data_obj) {
		Xoh_media_data data = (Xoh_media_data)data_obj;
		if (!data.Rng_valid()) {
			bfr.Add_mid(src, data.Src_bgn(), data.Src_end());
			return this;
		}

		// flags
		boolean is_audio = flag_bldr.Set_as_bool(Flag__is_audio, data.Is_audio());
		flag_bldr.Set(Flag__noicon, data.Aud_noicon());

		// hzip
		bfr.Add(hook);
		Gfo_hzip_int_.Encode(1, bfr, flag_bldr.Encode());

		if (is_audio) {
			bfr.Add_hzip_int(2, data.Aud_width());
			bfr.Add_hzip_mid(src, data.Lnki_ttl_bgn(), data.Lnki_ttl_end());
		}
		else {
			img_hzip.Encode1(bfr, hdoc_wkr, hctx, hpg, Bool_.N, src, data.Img_data());
		}
		return this;
	}
	public void Decode1(Bry_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, Bry_rdr rdr, byte[] src, int src_bgn, int src_end, Xoh_data_itm data_itm) {
		// flags
		int flag = rdr.Read_hzip_int(1); flag_bldr.Decode(flag);
		boolean is_audio = flag_bldr.Get_as_bool(Flag__is_audio);
		boolean aud_noicon = flag_bldr.Get_as_bool(Flag__noicon);

		// lnki_ttl
		int aud_width = -1;
		int lnki_ttl_bgn = -1, lnki_ttl_end = -1;
		if (is_audio) {
			aud_width = rdr.Read_hzip_int(2);
			lnki_ttl_bgn = rdr.Pos(); lnki_ttl_end = rdr.Find_fwd_lr();
		}

		// write
		Xoh_media_data data = (Xoh_media_data)data_itm;
		data.Init_by_decode(is_audio, aud_width, aud_noicon, lnki_ttl_bgn, lnki_ttl_end);
		if (!is_audio) {
			img_hzip.Decode1(bfr, hdoc_wkr, hctx, hpg, rdr, src, rdr.Pos(), src_end, data.Img_data());
			img_hzip.Wtr().Init_by_decode(hpg, hctx, src, data.Img_data());
		}

		wtr.Write(bfr, hpg, hctx, src, data, img_hzip.Wtr());
	}

	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_media_hzip rv = new Xoh_media_hzip(); rv.pool_mgr = mgr; rv.pool_idx = idx; rv.hook = (byte[])args[0]; return rv;}

	private final    Int_flag_bldr flag_bldr = new Int_flag_bldr().Pow_ary_bld_(1, 1);	
	private static final int // SERIALIZED
	  Flag__is_audio				=  0
	, Flag__noicon					=  1
	;
}
