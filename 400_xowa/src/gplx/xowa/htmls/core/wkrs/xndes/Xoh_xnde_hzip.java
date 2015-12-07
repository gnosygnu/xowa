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
package gplx.xowa.htmls.core.wkrs.xndes; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*; import gplx.core.threads.poolables.*; import gplx.xowa.wikis.ttls.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.hzips.*;
public class Xoh_xnde_hzip implements Xoh_hzip_wkr, Gfo_poolable_itm {
	public String Key() {return Xoh_hzip_dict_.Key__xnde;}
	public byte[] Hook() {return hook;} private byte[] hook;
	public Gfo_poolable_itm Encode(Xoh_hzip_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, boolean wkr_is_root, byte[] src, Object data_obj) {
//			Xoh_xnde_parser data = (Xoh_xnde_parser)data_obj;

//			int flag = data.Name_id();
//			boolean auto_exists		= flag_bldr.Set_as_bool(Flag__auto_exists		, data.Auto_id() != -1);
//			boolean capt_exists		= flag_bldr.Set_as_bool(Flag__capt_exists		, data.Capt_exists());
//			byte lnke_tid			= flag_bldr.Set_as_byte(Flag__lnke_tid			, data.Lnke_tid());
//
//			bfr.Add(hook);
//			bfr.Add_hzip_int(1, flag_bldr.Encode());									// add flag
//			bfr.Add_hzip_mid(src, data.Href_bgn(), data.Href_end());					// add href
//			if (auto_exists) bfr.Add_hzip_int(1, data.Auto_id());						// add autonumber
//			if (capt_exists) bfr.Add_hzip_mid(src, data.Capt_bgn(), data.Capt_end());	// add caption

		return this;
	}
	public int Decode(Bry_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, boolean wkr_is_root, Bry_rdr rdr, byte[] src, int src_bgn, int src_end) {
//			int flag = rdr.Read_int_by_base85(1); flag_bldr.Decode(flag);
//			boolean   auto_exists		= flag_bldr.Get_as_bool(Flag__auto_exists);
//			boolean   capt_exists		= flag_bldr.Get_as_bool(Flag__capt_exists);
//			byte lnke_tid			= flag_bldr.Get_as_byte(Flag__lnke_tid);
//
//			int href_bgn = rdr.Pos(); int href_end = rdr.Find_fwd_lr();
//			int auto_id = -1, capt_bgn = -1, capt_end = -1;
//			if (auto_exists) auto_id = rdr.Read_int_by_base85(1);
//			if (capt_exists) {capt_bgn = rdr.Pos(); capt_end = rdr.Find_fwd_lr();}
//			int rv = rdr.Pos();
//
//			bfr.Add(Html_bldr_.Bry__a_lhs_w_href);
//			bfr.Add_mid(src, href_bgn, href_end);
//			bfr.Add(Xoh_lnke_dict_.Html__atr__0).Add(Xoh_lnke_dict_.To_html_class(lnke_tid)).Add(Xoh_lnke_dict_.Html__rhs_end);
//			if		(auto_exists)	bfr.Add_byte(Byte_ascii.Brack_bgn).Add_int_variable(auto_id).Add_byte(Byte_ascii.Brack_end);
//			else if (capt_exists)	bfr.Add_mid(src, capt_bgn, capt_end);
//			else					bfr.Add_mid(src, href_bgn, href_end);
//			bfr.Add(Html_bldr_.Bry__a_rhs);
//			return rv;
		return src_end;
	}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_xnde_hzip rv = new Xoh_xnde_hzip(); rv.pool_mgr = mgr; rv.pool_idx = idx; rv.hook = (byte[])args[0]; return rv;}
//		private final Int_flag_bldr flag_bldr = new Int_flag_bldr().Pow_ary_bld_ (1, 1, 2);
//		private static final int // SERIALIZED
//		  Flag__auto_exists		=  0
//		, Flag__capt_exists		=  1
//		, Flag__lnke_tid		=  2	// "free", "autonumber", "text"
//		;
}
