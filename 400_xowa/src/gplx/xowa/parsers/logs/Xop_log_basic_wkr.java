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
package gplx.xowa.parsers.logs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.envs.*;
import gplx.dbs.*;
import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.htmls.*;
public class Xop_log_basic_wkr implements Gfo_invk {
	private Xop_log_basic_tbl log_tbl;
	private boolean save_page_ttl, save_log_time, save_args_len, save_args_str;
	public boolean Save_src_str() {return save_src_str;} public Xop_log_basic_wkr Save_src_str_(boolean v) {save_src_str = v; return this;} private boolean save_src_str;
	public Xop_log_basic_wkr(Xop_log_basic_tbl log_tbl) {this.log_tbl = log_tbl;}
	public boolean Log_bgn(Xoae_page page, byte[] src, Xop_xnde_tkn xnde) {return true;}
	public void Log_end_xnde(Xoae_page page, int log_tid, byte[] src, Xop_xnde_tkn xnde_tkn) {
		Mwh_atr_itm[] atrs_ary = xnde_tkn.Atrs_ary();
		Log_end(page, Null_log_bgn, log_tid, Null_log_msg, src
			, xnde_tkn.Src_bgn(), xnde_tkn.Src_end()
			, atrs_ary == null ? 0 : atrs_ary.length
			, xnde_tkn.Atrs_bgn(), xnde_tkn.Atrs_end()
			);
	}
	public void Log_end(Xoae_page page, long log_bgn, int log_tid, byte[] log_msg, byte[] src, int src_bgn, int src_end, int args_len, int args_bgn, int args_end) {
		log_tbl.Insert
			( log_tid
			, log_msg == Xop_log_basic_wkr.Null_log_msg ? "" : String_.new_u8(log_msg)
			, save_log_time ?  System_.Ticks__elapsed_in_frac(log_bgn) : Xop_log_basic_wkr.Null_log_time
			, page.Db().Page().Id()
			, save_page_ttl ? String_.new_u8(page.Ttl().Full_db()) : Xop_log_basic_wkr.Null_page_ttl
			, save_args_len ? args_len : Xop_log_basic_wkr.Null_args_len
			, save_args_str ? String_.new_u8(src, args_bgn, args_end) : Xop_log_basic_wkr.Null_args_str
			, src_end - src_bgn
			, save_src_str ? String_.new_u8(src, src_bgn, src_end) : Xop_log_basic_wkr.Null_src_str
			);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_save_page_ttl_))			save_page_ttl = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_save_log_time_))			save_log_time = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_save_args_len_))			save_args_len = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_save_args_str_))			save_args_str = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_save_src_str_))			save_src_str = m.ReadYn("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String 
	  Invk_save_page_ttl_ = "save_page_ttl_", Invk_save_log_time_ = "save_log_time_"
	, Invk_save_args_len_ = "save_args_len_", Invk_save_args_str_ = "save_args_str_", Invk_save_src_str_ = "save_src_str_"
	;
	public static final    Xop_log_basic_wkr Null = null;
	public static final int Null_page_id = -1, Null_log_bgn = -1, Null_log_time = -1, Null_args_len = -1, Null_src_len = -1;
	public static final String Null_page_ttl = "", Null_args_str = "", Null_src_str = "";
	public static final    byte[] Null_log_msg = null;
	public static final int
	  Tid_gallery		= 1
	, Tid_imageMap		= 2
	, Tid_timeline		= 3
	, Tid_score			= 4
	, Tid_hiero			= 5
	, Tid_math			= 6
	, Tid_graph			= 7
	, Tid_mapframe		= 8
	, Tid_maplink		= 9
	;
}
