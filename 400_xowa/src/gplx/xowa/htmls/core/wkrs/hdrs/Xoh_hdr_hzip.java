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
package gplx.xowa.htmls.core.wkrs.hdrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*; import gplx.core.threads.poolables.*; import gplx.xowa.wikis.ttls.*;
import gplx.xowa.htmls.core.hzips.*;
public class Xoh_hdr_hzip implements Xoh_hzip_wkr, Gfo_poolable_itm {
	public int Tid() {return Xoh_hzip_dict_.Tid__hdr;}
	public String Key() {return Xoh_hzip_dict_.Key__hdr;}
	public byte[] Hook() {return hook;} private byte[] hook;
	public Gfo_poolable_itm Encode1(Xoh_hzip_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, boolean wkr_is_root, byte[] src, Object data_obj) {
		Xoh_hdr_data data = (Xoh_hdr_data)data_obj;
		boolean capt_rhs_exists	= flag_bldr.Set_as_bool	(Flag__capt_rhs_exists		, data.Capt_rhs_exists());
		boolean anch_is_diff		= flag_bldr.Set_as_bool	(Flag__anch_is_diff			, data.Anch_is_diff());
		int hdr_level			= flag_bldr.Set_as_int	(Flag__hdr_level			, data.Hdr_level());

		bfr.Add(hook);
		bfr.Add_hzip_int(1, flag_bldr.Encode());
		bfr.Add_hzip_mid(src, data.Capt_bgn(), data.Capt_end());								// add caption
		if (anch_is_diff)		bfr.Add_hzip_mid(src, data.Anch_bgn(), data.Anch_end());		// add anchor
		if (capt_rhs_exists)	bfr.Add_hzip_mid(src, data.Capt_rhs_bgn(), data.Capt_rhs_end());// add capt_rhs

		hctx.Hzip__stat().Hdr_add(hdr_level);
		return this;
	}
	public void Decode1(Bry_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, Bry_rdr rdr, byte[] src, int src_bgn, int src_end, Xoh_data_itm data_itm) {
		int flag = rdr.Read_hzip_int(1); flag_bldr.Decode(flag);
		boolean capt_rhs_exists		= flag_bldr.Get_as_bool(Flag__capt_rhs_exists);
		boolean anch_is_diff			= flag_bldr.Get_as_bool(Flag__anch_is_diff);
		byte hdr_level				= flag_bldr.Get_as_byte(Flag__hdr_level);

		int capt_bgn = rdr.Pos(); int capt_end = rdr.Find_fwd_lr();
		int anch_bgn = -1, anch_end = -1;
		if (anch_is_diff) {anch_bgn = rdr.Pos(); anch_end = rdr.Find_fwd_lr();}
		int capt_rhs_bgn = -1, capt_rhs_end = -1;
		if (capt_rhs_exists) {capt_rhs_bgn = rdr.Pos(); capt_rhs_end = rdr.Find_fwd_lr();}

		Xoh_hdr_data data = (Xoh_hdr_data)data_itm;
		data.Init_by_decode(hdr_level, anch_is_diff, anch_bgn, anch_end, capt_bgn, capt_end, capt_rhs_bgn, capt_rhs_end);
	}
	private final Int_flag_bldr flag_bldr = new Int_flag_bldr().Pow_ary_bld_ (1, 1, 3);
	private static final int // SERIALIZED
	  Flag__capt_rhs_exists	=  0
	, Flag__anch_is_diff		=  1
	, Flag__hdr_level			=  2
	;
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_hdr_hzip rv = new Xoh_hdr_hzip(); rv.pool_mgr = mgr; rv.pool_idx = idx; rv.hook = (byte[])args[0]; return rv;}
}
