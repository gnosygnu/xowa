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
package gplx.xowa.htmls.core.wkrs.glys; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.primitives.*; import gplx.core.brys.*; import gplx.core.threads.poolables.*; import gplx.core.encoders.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*; import gplx.xowa.htmls.hrefs.*; import gplx.xowa.htmls.core.hzips.*;
import gplx.xowa.htmls.core.wkrs.bfr_args.*; import gplx.xowa.htmls.core.wkrs.imgs.atrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*; import gplx.xowa.htmls.core.wkrs.lnkis.anchs.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.ttls.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*;
import gplx.xowa.xtns.gallery.*;
public class Xoh_gly_hzip implements Xoh_hzip_wkr, Gfo_poolable_itm {
	private final    Xoh_gly_grp_wtr grp_wtr = new Xoh_gly_grp_wtr();
	public int Tid() {return Xoh_hzip_dict_.Tid__gly;}
	public String Key() {return Xoh_hzip_dict_.Key__gly;}
	public byte[] Hook() {return hook;} private byte[] hook;
	public Gfo_poolable_itm Encode1(Xoh_hzip_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, boolean wkr_is_root, byte[] src, Object data_obj) {
		Xoh_gly_grp_data data = (Xoh_gly_grp_data)data_obj;
		int ul_style_max_w = data.Ul_style_max_w(), ul_style_w = data.Ul_style_w();
		boolean	xnde_w				= flag_bldr.Set_as_bool(Flag__xnde__w			, data.Xnde_w() != -1);
		boolean	xnde_h				= flag_bldr.Set_as_bool(Flag__xnde__h			, data.Xnde_h() != -1);
		boolean	xnde_per_row		= flag_bldr.Set_as_bool(Flag__xnde__per_row		, data.Xnde_per_row() != -1);
		boolean	capt_exists			= flag_bldr.Set_as_bool(Flag__xnde__caption		, data.Capt_bgn() != -1);
		boolean ul_style_w_diff		= flag_bldr.Set_as_bool(Flag__ul__style_w_diff	, ul_style_max_w != ul_style_w);
		boolean xtra_atr			= flag_bldr.Set_as_bool(Flag__ul__xtra_atr		, data.Xtra_atr_exists());
		boolean xtra_cls			= flag_bldr.Set_as_bool(Flag__ul__xtra_cls		, data.Xtra_cls_exists());
		boolean xtra_style			= flag_bldr.Set_as_bool(Flag__ul__xtra_style	, data.Xtra_style_exists());
		flag_bldr.Set(Flag__gly_tid, data.Gly_tid());
		int itms_len = data.Itms__len();
		bfr.Add(hook);
		Gfo_hzip_int_.Encode(1, bfr, flag_bldr.Encode());
		Gfo_hzip_int_.Encode(1, bfr, ul_style_max_w);
		if (xnde_w)			Gfo_hzip_int_.Encode(4, bfr, data.Xnde_w());
		if (xnde_h)			Gfo_hzip_int_.Encode(4, bfr, data.Xnde_h());
		if (xnde_per_row)	Gfo_hzip_int_.Encode(1, bfr, data.Xnde_per_row());
		if (ul_style_w_diff) Gfo_hzip_int_.Encode(1, bfr, ul_style_w);
		if (xtra_cls)		bfr.Add_hzip_mid(src, data.Xtra_cls_bgn(), data.Xtra_cls_end());
		if (xtra_style)		bfr.Add_hzip_mid(src, data.Xtra_style_bgn(), data.Xtra_style_end());
		if (xtra_atr)		bfr.Add_hzip_mid(src, data.Xtra_atr_bgn(), data.Xtra_atr_end());
		if (capt_exists)	bfr.Add_hzip_mid(src, data.Capt_bgn(), data.Capt_end());
		Gfo_hzip_int_.Encode(1, bfr, itms_len);
		for (int i = 0; i < itms_len; ++i) {
			Xoh_gly_itm_data itm_parser = data.Itms__get_at(i);
			bfr.Add_hzip_int(1, itm_parser.Li_w());
			bfr.Add_hzip_int(1, itm_parser.Div_1_w());
			bfr.Add_hzip_int(1, itm_parser.Div_2_margin());
			bfr.Add_byte((byte)(itm_parser.Capt_tid() + gplx.core.encoders.Base85_.A7_offset));
			bfr.Add_hzip_mid(src, itm_parser.Capt_bgn(), itm_parser.Capt_end());
			Xoh_hzip_wkr hzip_wkr = hctx.Pool_mgr__hzip().Mw__img();
			hzip_wkr.Encode1(bfr, hdoc_wkr, hctx, hpg, false, src, itm_parser.Img_data());
			hzip_wkr.Pool__rls();
		}
		return this;
	}
	public void Decode1(Bry_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, Bry_rdr rdr, byte[] src, int src_bgn, int src_end, Xoh_data_itm data_itm) {
		int flag = rdr.Read_hzip_int(1); flag_bldr.Decode(flag);
		boolean xnde_w_exists		= flag_bldr.Get_as_bool(Flag__xnde__w);
		boolean xnde_h_exists		= flag_bldr.Get_as_bool(Flag__xnde__h);
		boolean xnde_per_row_exists	= flag_bldr.Get_as_bool(Flag__xnde__per_row);
		boolean	capt_exists			= flag_bldr.Get_as_bool(Flag__xnde__caption);
		boolean ul_style_w_diff		= flag_bldr.Get_as_bool(Flag__ul__style_w_diff);
		boolean xtra_atr			= flag_bldr.Get_as_bool(Flag__ul__xtra_atr);
		boolean xtra_cls			= flag_bldr.Get_as_bool(Flag__ul__xtra_cls);
		boolean xtra_style			= flag_bldr.Get_as_bool(Flag__ul__xtra_style);
		byte    cls_tid				= flag_bldr.Get_as_byte(Flag__gly_tid);
		byte[] cls_bry = Gallery_mgr_base_.To_bry(cls_tid);
		int ul_style_max_w = rdr.Read_hzip_int(1); 
		int xnde_w = xnde_w_exists ? rdr.Read_hzip_int(4) : -1;
		int xnde_h = xnde_h_exists ? rdr.Read_hzip_int(4) : -1;
		int xnde_per_row = xnde_per_row_exists ? rdr.Read_hzip_int(1) : -1;
		int ul_style_w = ul_style_w_diff ? rdr.Read_hzip_int(1) : ul_style_max_w;
		byte[] xtra_cls_bry = xtra_cls ? rdr.Read_bry_to(): null;
		byte[] xtra_style_bry = xtra_style ? rdr.Read_bry_to(): null;
		byte[] xtra_atr_bry = xtra_atr ? rdr.Read_bry_to(): null;
		byte[] capt = capt_exists ? rdr.Read_bry_to(): null;
		int li_len = rdr.Read_hzip_int(1);
		int uid = hctx.Uid__gly__nxt();
		Xoh_gly_itm_wtr[] itm_ary = new Xoh_gly_itm_wtr[li_len];
		int li_nth = li_len - 1;
		for (int i = 0; i < li_len; ++i) {
			// init wtr for <li>
			Xoh_gly_itm_wtr itm_wtr = new Xoh_gly_itm_wtr();
			itm_ary[i] = itm_wtr;
			int li_w = rdr.Read_hzip_int(1);
			int div_1_w = rdr.Read_hzip_int(1);
			int div_3_margin = rdr.Read_hzip_int(1);
			byte capt_tid = (byte)(rdr.Read_byte() - gplx.core.encoders.Base85_.A7_offset);
			byte[] capt_bry = rdr.Read_bry_to();
			Xoh_data_itm img_data = hctx.Pool_mgr__data().Get_by_tid(Xoh_hzip_dict_.Tid__img);
			Xoh_hzip_wkr img_hzip = hctx.Pool_mgr__hzip().Mw__img();
			img_hzip.Decode1(bfr, hdoc_wkr, hctx, hpg, rdr, src, src_bgn, src_end, img_data);
			((gplx.xowa.htmls.core.wkrs.imgs.Xoh_img_data)img_data).Img_is_gallery_(true);

			// init wtr for <img>
			itm_wtr.Img_wtr().Init_by_decode(hpg, hctx, src, img_data);				
			Xof_fsdb_itm fsdb_itm = itm_wtr.Img_wtr().Fsdb_itm();
			fsdb_itm.Html_elem_tid_(Xof_html_elem.Tid_gallery_v2);
			fsdb_itm.Html_img_wkr_(itm_wtr);
			itm_wtr.Init(hctx.Mode_is_diff(), cls_tid, xnde_w, xnde_h, xnde_per_row, fsdb_itm.Html_uid(), i, li_nth, li_w, div_1_w, div_3_margin, capt_tid, capt_bry);

			// release
			img_data.Pool__rls();
			img_hzip.Pool__rls();
		}
		grp_wtr.Init(hctx.Mode_is_diff(), uid, xnde_w, xnde_h, xnde_per_row, cls_bry, ul_style_max_w, ul_style_w, xtra_cls_bry, xtra_style_bry, xtra_atr_bry, capt, itm_ary);
		grp_wtr.Bfr_arg__add(bfr);
		hpg.Xtn__gallery_exists_y_();
	}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_gly_hzip rv = new Xoh_gly_hzip(); rv.pool_mgr = mgr; rv.pool_idx = idx; rv.hook = (byte[])args[0]; return rv;}
	private final    Int_flag_bldr flag_bldr = new Int_flag_bldr().Pow_ary_bld_(1,    1, 1, 1, 1,    1, 1, 1, 3);
	private static final int // SERIALIZED
	  Flag__xnde__w				=  0
	, Flag__xnde__h				=  1
	, Flag__xnde__per_row		=  2
	, Flag__xnde__caption		=  3
	, Flag__ul__style_w_diff	=  4
	, Flag__ul__xtra_atr		=  5
	, Flag__ul__xtra_cls		=  6
	, Flag__ul__xtra_style		=  7
	, Flag__gly_tid				=  8
	;
}
