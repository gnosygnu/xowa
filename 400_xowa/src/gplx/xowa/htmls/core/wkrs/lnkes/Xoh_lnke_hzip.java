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
import gplx.core.brys.*; import gplx.core.threads.poolables.*; import gplx.xowa.wikis.ttls.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.hzips.*;
public class Xoh_lnke_hzip implements Xoh_hzip_wkr, Gfo_poolable_itm {
	public int Tid() {return Xoh_hzip_dict_.Tid__lnke;}
	public String Key() {return Xoh_hzip_dict_.Key__lnke;}
	public byte[] Hook() {return hook;} private byte[] hook;
	public Gfo_poolable_itm Encode1(Xoh_hzip_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, boolean wkr_is_root, byte[] src, Object data_obj) {
		Xoh_lnke_data data = (Xoh_lnke_data)data_obj;
		boolean title_exists	= flag_bldr.Set_as_bool(Flag__title_exists		, data.Title_exists());
		boolean auto_exists		= flag_bldr.Set_as_bool(Flag__auto_exists		, data.Auto_exists());
		boolean capt_exists		= flag_bldr.Set_as_bool(Flag__capt_exists		, data.Capt_exists());
		byte    lnke_tid		= flag_bldr.Set_as_byte(Flag__lnke_tid			, data.Lnke_tid());
		bfr.Add(hook);
		bfr.Add_hzip_int(1, flag_bldr.Encode());										// add flag
		bfr.Add_hzip_mid(src, data.Href_bgn(), data.Href_end());						// add href
		if (auto_exists)	bfr.Add_hzip_int(1, data.Auto_id());						// add autonumber
		if (capt_exists)	bfr.Add_hzip_mid(src, data.Capt_bgn(), data.Capt_end());	// add caption
		if (title_exists)	bfr.Add_hzip_mid(src, data.Title_bgn(), data.Title_end());	// add title
		hctx.Hzip__stat().Lnke_add(lnke_tid);
		return this;
	}
	public void Decode1(Bry_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, Bry_rdr rdr, byte[] src, int src_bgn, int src_end, Xoh_data_itm data_itm) {
		int flag = rdr.Read_hzip_int(1); flag_bldr.Decode(flag);
		boolean   title_exists		= flag_bldr.Get_as_bool(Flag__title_exists);
		boolean   auto_exists		= flag_bldr.Get_as_bool(Flag__auto_exists);
		boolean   capt_exists		= flag_bldr.Get_as_bool(Flag__capt_exists);
		byte   lnke_tid			= flag_bldr.Get_as_byte(Flag__lnke_tid);
		int href_bgn = rdr.Pos(); int href_end = rdr.Find_fwd_lr();
		int auto_id = -1, capt_bgn = -1, capt_end = -1, title_bgn = -1, title_end = -1;
		if (auto_exists) auto_id = rdr.Read_hzip_int(1);
		if (capt_exists)	{capt_bgn = rdr.Pos(); capt_end = rdr.Find_fwd_lr();}
		if (title_exists)	{title_bgn = rdr.Pos(); title_end = rdr.Find_fwd_lr();}
		Xoh_lnke_data data = (Xoh_lnke_data)data_itm;
		data.Init_by_decode(lnke_tid, auto_id, href_bgn, href_end, capt_bgn, capt_end, capt_exists, title_bgn, title_end);
	}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_lnke_hzip rv = new Xoh_lnke_hzip(); rv.pool_mgr = mgr; rv.pool_idx = idx; rv.hook = (byte[])args[0]; return rv;}
	private final    Int_flag_bldr flag_bldr = new Int_flag_bldr().Pow_ary_bld_ (1, 1, 1, 2);
	private static final int // SERIALIZED
	  Flag__title_exists	=  0
	, Flag__auto_exists		=  1
	, Flag__capt_exists		=  2
	, Flag__lnke_tid		=  3	// "free", "autonumber", "text"
	;
}
