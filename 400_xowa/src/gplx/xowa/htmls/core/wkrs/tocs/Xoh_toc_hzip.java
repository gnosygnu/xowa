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
package gplx.xowa.htmls.core.wkrs.tocs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*; import gplx.core.threads.poolables.*; import gplx.xowa.wikis.ttls.*; import gplx.core.encoders.*;
import gplx.xowa.htmls.core.hzips.*;
public class Xoh_toc_hzip implements Xoh_hzip_wkr, Gfo_poolable_itm {
	public int Tid()		{return Xoh_hzip_dict_.Tid__toc;}
	public String Key()		{return Xoh_hzip_dict_.Key__toc;}
	public byte[] Hook()	{return hook;} private byte[] hook;
	public Gfo_poolable_itm Encode1(Xoh_hzip_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, boolean wkr_is_root, byte[] src, Object data_obj) {
		Xoh_toc_data data = (Xoh_toc_data)data_obj;
		bfr.Add(hook);
		flag_bldr.Set(Flag__toc_mode					, data.Toc_mode());
		Gfo_hzip_int_.Encode(1, bfr, flag_bldr.Encode());
		return this;
	}
	public void Decode1(Bry_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, Bry_rdr rdr, byte[] src, int src_bgn, int src_end, Xoh_data_itm data_itm) {
		Xoh_toc_data data = (Xoh_toc_data)data_itm;
		int flag = rdr.Read_hzip_int(1); flag_bldr.Decode(flag);
		byte	toc_mode					= flag_bldr.Get_as_byte(Flag__toc_mode);
		data.Init_by_decode(toc_mode);
	}

	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_toc_hzip rv = new Xoh_toc_hzip(); rv.pool_mgr = mgr; rv.pool_idx = idx; rv.hook = (byte[])args[0]; return rv;}

	private final    Int_flag_bldr flag_bldr = new Int_flag_bldr().Pow_ary_bld_(2);	
	private static final int // SERIALIZED
	  Flag__toc_mode						=  0	// 4: 0=basic; 1=pgbnr; 2,3=reserved
	;
}
