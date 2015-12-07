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
package gplx.xowa.htmls.core.wkrs.hdrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*; import gplx.core.threads.poolables.*; import gplx.xowa.wikis.ttls.*;
import gplx.xowa.htmls.core.hzips.*;
public class Xoh_hdr_hzip implements Xoh_hzip_wkr, Gfo_poolable_itm {
	public String Key() {return Xoh_hzip_dict_.Key__hdr;}
	public byte[] Hook() {return hook;} private byte[] hook;
	public Gfo_poolable_itm Encode(Xoh_hzip_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, boolean wkr_is_root, byte[] src, Object data_obj) {
		Xoh_hdr_parser data = (Xoh_hdr_parser)data_obj;
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
	public int Decode(Bry_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, boolean wkr_is_root, Bry_rdr rdr, byte[] src, int src_bgn, int src_end) {
		int flag = rdr.Read_int_by_base85(1); flag_bldr.Decode(flag);
		boolean capt_rhs_exists		= flag_bldr.Get_as_bool(Flag__capt_rhs_exists);
		boolean anch_is_diff			= flag_bldr.Get_as_bool(Flag__anch_is_diff);
		byte hdr_level				= flag_bldr.Get_as_byte(Flag__hdr_level);

		int capt_bgn = rdr.Pos(); int capt_end = rdr.Find_fwd_lr();
		int anch_bgn = -1, anch_end = -1;
		if (anch_is_diff) {
			anch_bgn = rdr.Pos(); anch_end = rdr.Find_fwd_lr();
		}
		byte[] capt_manual_end = capt_rhs_exists ? rdr.Read_bry_to() : null;
		
		bfr.Add(Bry__hdr__0).Add_byte_as_a7(hdr_level);
		bfr.Add(Bry__hdr__1);
		if (anch_is_diff) 
			bfr.Add_mid			(src, anch_bgn, anch_end);
		else
			bfr.Add_mid_w_swap	(src, capt_bgn, capt_end, Byte_ascii.Space, Byte_ascii.Underline);
		bfr.Add(Bry__hdr__2);
		bfr.Add_mid(src, capt_bgn, capt_end);
		bfr.Add(Bry__hdr__3);
		if (capt_rhs_exists)
			bfr.Add(capt_manual_end);
		bfr.Add(Bry__hdr__4).Add_byte_as_a7(hdr_level);
		bfr.Add_byte(Byte_ascii.Angle_end);
		return rdr.Pos();
	}
	private final Int_flag_bldr flag_bldr = new Int_flag_bldr().Pow_ary_bld_ (1, 1, 3);
	private static final int // SERIALIZED
	  Flag__capt_rhs_exists	=  0
	, Flag__anch_is_diff		=  1
	, Flag__hdr_level			=  2
	;
	private static final byte[]
	  Bry__hdr__0 = Bry_.new_a7("<h")
	, Bry__hdr__1 = Bry_.new_a7("><span class=\"mw-headline\" id=\"")
	, Bry__hdr__2 = Bry_.new_a7("\">")
	, Bry__hdr__3 = Bry_.new_a7("</span>")
	, Bry__hdr__4 = Bry_.new_a7("</h")
	;
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_hdr_hzip rv = new Xoh_hdr_hzip(); rv.pool_mgr = mgr; rv.pool_idx = idx; rv.hook = (byte[])args[0]; return rv;}
}
