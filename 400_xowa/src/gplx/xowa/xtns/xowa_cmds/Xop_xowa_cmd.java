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
package gplx.xowa.xtns.xowa_cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*;
public class Xop_xowa_cmd implements Xox_xnde {
	public Xop_root_tkn Xtn_root() {throw Err_.new_unimplemented_w_msg("xowa_cmd.xtn_root should not be called");}
	public byte[] Xtn_html() {return xtn_html;} private byte[] xtn_html;
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		int itm_bgn = xnde.Tag_open_end(), itm_end = xnde.Tag_close_bgn();
		if (itm_bgn == src.length)	return;  // NOTE: handle inline where there is no content to parse; EX: <xowa_cmd/>
		if (src[itm_bgn] 		== Byte_ascii.Nl) ++itm_bgn;	// ignore 1st \n; 
		if (src[itm_end - 1] 	== Byte_ascii.Nl) --itm_end;	// ignore last \n;
		byte[] raw = Bry_.Mid(src, itm_bgn, itm_end);
		byte[] xtn_src = raw;
		if (wiki.Sys_cfg().Xowa_cmd_enabled()) {	// only exec if enabled for wiki
			Object rslt = wiki.Appe().Gfs_mgr().Run_str(String_.new_u8(raw));
			xtn_src = Bry_.new_u8(Object_.Xto_str_strict_or_null_mark(rslt));
		}
		Xop_tkn_mkr tkn_mkr = ctx.Tkn_mkr();
		Xop_ctx sub_ctx = Xop_ctx.New__sub__reuse_page(ctx);
		Xop_root_tkn sub_root = tkn_mkr.Root(xtn_src);
		xtn_html = wiki.Parser_mgr().Main().Expand_tmpl(sub_root, sub_ctx, ctx.Tkn_mkr(), xtn_src);
	}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		bfr.Add(xtn_html);
		//throw Err_.new_unimplemented_w_msg("xowa_cmd.xtn_write should not be called");
	}
}
