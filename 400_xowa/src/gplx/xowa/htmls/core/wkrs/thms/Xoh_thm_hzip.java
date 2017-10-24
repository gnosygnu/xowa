/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.htmls.core.wkrs.thms; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.primitives.*; import gplx.core.brys.*; import gplx.core.threads.poolables.*; import gplx.core.encoders.*;
import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.wkrs.imgs.*; import gplx.xowa.htmls.core.wkrs.thms.divs.*; 
public class Xoh_thm_hzip implements Xoh_hzip_wkr, Gfo_poolable_itm {
	private final    Xoh_img_hzip img_hzip = new Xoh_img_hzip();
	private final    Xoh_thm_wtr wtr = new Xoh_thm_wtr();
	private final    Bry_obj_ref capt_1 = Bry_obj_ref.New_empty();
	public int Tid() {return Xoh_hzip_dict_.Tid__thm;}
	public String Key() {return Xoh_hzip_dict_.Key__thm;}
	public byte[] Hook() {return hook;} private byte[] hook;
	public Gfo_poolable_itm Encode1(Xoh_hzip_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, boolean wkr_is_root, byte[] src, Object data_obj) {
		Xoh_thm_data data = (Xoh_thm_data)data_obj;
		if (!data.Rng_valid()) {
			bfr.Add_mid(src, data.Src_bgn(), data.Src_end());
			return this;
		}
		Xoh_thm_caption_data capt_data = data.Capt_data();
		int div_1_width = data.Div_1_width();
										  flag_bldr.Set_as_bool(Flag__xowa_alt_text_exists		, capt_data.Xowa_alt_text_exists());
		boolean capt_3_exists				= flag_bldr.Set_as_bool(Flag__capt_3_exists				, capt_data.Capt_3_exists());
										  flag_bldr.Set_as_bool(Flag__capt_2_is_tidy			, capt_data.Capt_2_exists() && capt_data.Capt_2_is_tidy());
		boolean capt_2_exists				= flag_bldr.Set_as_bool(Flag__capt_2_exists				, capt_data.Capt_2_exists());
		boolean div_1_width_exists			= flag_bldr.Set_as_bool(Flag__div_1_width_exists		, div_1_width != 220);
									      flag_bldr.Set_as_byte(Flag__div_0_align				, data.Div_0_align());
		bfr.Add(hook);
		Gfo_hzip_int_.Encode(1, bfr, flag_bldr.Encode());
		if (div_1_width_exists) Gfo_hzip_int_.Encode(2, bfr, div_1_width);
		if (capt_data.Capt_1_exists())	bfr.Add_mid(src, capt_data.Capt_1_bgn(), capt_data.Capt_1_end());
		bfr.Add_byte(Xoh_hzip_dict_.Escape);
		if (capt_2_exists)				bfr.Add_hzip_mid(src, capt_data.Capt_2_bgn(), capt_data.Capt_2_end());
		if (capt_3_exists)				bfr.Add_hzip_mid(src, capt_data.Capt_3_bgn(), capt_data.Capt_3_end());
		img_hzip.Encode1(bfr, hdoc_wkr, hctx, hpg, Bool_.N, src, data.Img_data());
		return this;
	}
	public void Decode1(Bry_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, Bry_rdr rdr, byte[] src, int src_bgn, int src_end, Xoh_data_itm data_itm) {
		int flag = rdr.Read_hzip_int(1); flag_bldr.Decode(flag);
		boolean xowa_alt_text_exists			= flag_bldr.Get_as_bool(Flag__xowa_alt_text_exists);
		boolean capt_3_exists					= flag_bldr.Get_as_bool(Flag__capt_3_exists);
		boolean capt_2_is_tidy					= flag_bldr.Get_as_bool(Flag__capt_2_is_tidy);
		boolean capt_2_exists					= flag_bldr.Get_as_bool(Flag__capt_2_exists);
		boolean div_1_width_exists				= flag_bldr.Get_as_bool(Flag__div_1_width_exists);
		int div_0_align						= flag_bldr.Get_as_int(Flag__div_0_align);
		int div_1_width = 220;
		if (div_1_width_exists) div_1_width = rdr.Read_hzip_int(2);
		int capt_1_bgn = rdr.Pos(); int capt_1_end = rdr.Find_fwd_lr();
		capt_1.Mid_(src, capt_1_bgn, capt_1_end);
		byte[] capt_2_bry = capt_2_exists ? rdr.Read_bry_to() : Bry_.Empty;
		byte[] capt_3_bry = capt_3_exists ? rdr.Read_bry_to() : Bry_.Empty;
		Xoh_img_data img_data = (Xoh_img_data)hctx.Pool_mgr__data().Get_by_tid(Xoh_hzip_dict_.Tid__img);
		img_hzip.Decode1(bfr, hdoc_wkr, hctx, hpg, rdr, src, rdr.Pos(), src_end, img_data);
		img_hzip.Wtr().Init_by_decode(hpg, hctx, src, img_data);
		int fsdb_w = img_hzip.Wtr().Fsdb_itm().Html_w();
		if (fsdb_w > 0)				// fsdb_w has value; this occurs when itm is found in user's file_cache; NOTE: do not do !div_1_width_exists b/c all thms have div_1_w; PAGE:en.w:Paris; DATE:2016-06-18
			div_1_width = fsdb_w;	// override div_1_width, else widths will default to wrong value and not be auto-corrected by post-processing; DATE:2016-06-18
		wtr.Write(bfr, hpg, hctx, src, img_data.Img_is_vid(), div_0_align, div_1_width, img_hzip.Wtr(), img_hzip.Anch_href_bry(), capt_1, capt_2_exists, capt_2_is_tidy, capt_2_bry, capt_3_exists, capt_3_bry, xowa_alt_text_exists);
		img_data.Pool__rls();
	}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_thm_hzip rv = new Xoh_thm_hzip(); rv.pool_mgr = mgr; rv.pool_idx = idx; rv.hook = (byte[])args[0]; return rv;}
	private final    Int_flag_bldr flag_bldr = new Int_flag_bldr().Pow_ary_bld_(1, 1	, 1, 1, 1, 3);	
	private static final int // SERIALIZED
	  Flag__xowa_alt_text_exists		=  0
	, Flag__capt_3_exists				=  1
	, Flag__capt_2_is_tidy				=  2
	, Flag__capt_2_exists				=  3
	, Flag__div_1_width_exists			=  4
	, Flag__div_0_align					=  5	// "", "tnone", "tleft", "tcenter", "tright"
	;
}
