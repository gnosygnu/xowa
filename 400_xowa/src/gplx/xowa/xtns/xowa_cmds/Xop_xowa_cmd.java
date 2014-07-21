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
package gplx.xowa.xtns.xowa_cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.html.*;
public class Xop_xowa_cmd implements Xox_xnde {
	public Xop_root_tkn Xtn_root() {throw Err_.not_implemented_msg_("xowa_cmd.xtn_root should not be called");}
	public byte[] Xtn_html() {return xtn_html;} private byte[] xtn_html;
	public void Xtn_parse(Xow_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		int itm_bgn = xnde.Tag_open_end(), itm_end = xnde.Tag_close_bgn();
		if (itm_bgn == src.length)	return;  // NOTE: handle inline where there is no content to parse; EX: <xowa_cmd/>
		if (src[itm_bgn] 		== Byte_ascii.NewLine) ++itm_bgn;	// ignore 1st \n; 
		if (src[itm_end - 1] 	== Byte_ascii.NewLine) --itm_end;	// ignore last \n;
		byte[] raw = Bry_.Mid(src, itm_bgn, itm_end);
		byte[] xtn_src = raw;
		if (wiki.Sys_cfg().Xowa_cmd_enabled()) {	// only exec if enabled for wiki
			Object rslt = wiki.App().Gfs_mgr().Run_str(String_.new_utf8_(raw));
			xtn_src = Bry_.new_utf8_(Object_.Xto_str_strict_or_null_mark(rslt));
		}
		Xop_tkn_mkr tkn_mkr = ctx.Tkn_mkr();
		Xop_ctx sub_ctx = Xop_ctx.new_sub_(wiki);
		Xop_root_tkn sub_root = tkn_mkr.Root(xtn_src);
		xtn_html = wiki.Parser().Parse_text_to_wtxt(sub_root, sub_ctx, ctx.Tkn_mkr(), xtn_src);
	}
	public void Xtn_write(Xoa_app app, Xoh_html_wtr html_wtr, Xoh_html_wtr_ctx opts, Xop_ctx ctx, Bry_bfr bfr, byte[] src, Xop_xnde_tkn xnde) {
		throw Err_.not_implemented_msg_("xowa_cmd.xtn_write should not be called");
	}
}
