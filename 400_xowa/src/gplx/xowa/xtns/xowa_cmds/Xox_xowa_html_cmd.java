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
public class Xox_xowa_html_cmd implements Xox_xnde, Xop_xnde_atr_parser {
	private byte pos_val = Pos_head_end;
	public Xop_root_tkn Xtn_root() {throw Err_.not_implemented_msg_("xowa_html_cmd.xtn_root should not be called");}
	public byte[] Xtn_html() {throw Err_.not_implemented_msg_("xowa_html_cmd.xtn_html should not be called");}
	public void Xatr_parse(Xow_wiki wiki, byte[] src, Xop_xatr_itm xatr, Object xatr_key_obj) {
		if (xatr_key_obj == null) return;
		Byte_obj_val xatr_key = (Byte_obj_val)xatr_key_obj;
		switch (xatr_key.Val()) {
			case Xatr_pos_id:			pos_val = Pos_val(xatr.Val_as_bry(src)); break;
			default:					throw Err_.unhandled(xatr_key.Val());
		}
	}
	public void Xtn_parse(Xow_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		if (!wiki.Sys_cfg().Xowa_cmd_enabled()) {	// not allowed; warn and exit
			wiki.App().Usr_dlg().Warn_many("", "", "xowa_html command only allowed in xowa_home");
			return;
		}
		Xop_xatr_itm.Xatr_parse(wiki.App(), this, atr_hash, wiki, src, xnde);
		int itm_bgn = xnde.Tag_open_end(), itm_end = xnde.Tag_close_bgn();
		if (src[itm_bgn] 		== Byte_ascii.NewLine) ++itm_bgn;	// ignore 1st \n; 
		// if (src[itm_end - 1] 	== Byte_ascii.NewLine) --itm_end;	// ignore last \n;
		byte[] raw = Bry_.Mid(src, itm_bgn, itm_end);
		if (pos_val == Pos_head_end)
			ctx.Cur_page().Html_data().Custom_head_end_concat(raw);
		else
			ctx.Cur_page().Html_data().Custom_html_end_concat(raw);
	}
	public void Xtn_write(Bry_bfr bfr, Xoa_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xop_xnde_tkn xnde, byte[] src) {}
	private static byte Pos_val(byte[] bry) {
		Object o = pos_val_hash.Get_by_bry(bry);
		if (o == null) throw Err_.new_("unknown pos:{0}", String_.new_utf8_(bry));
		return ((Byte_obj_val)o).Val();
	}
	private static final byte Pos_head_end = 1, Pos_html_end = 2;
	private static final byte[] 
	  Xatr_pos_key				= Bry_.new_ascii_("pos")
	, Xatr_pos_val__head_end	= Bry_.new_ascii_("head.end")
	, Xatr_pos_val__html_end	= Bry_.new_ascii_("html.end")
	;
	private static final Hash_adp_bry pos_val_hash = Hash_adp_bry.ci_ascii_()
	.Add_bry_byte(Xatr_pos_val__head_end, Pos_head_end)
	.Add_bry_byte(Xatr_pos_val__html_end, Pos_html_end)
	;
	private static final byte Xatr_pos_id = 1;
	private static final Hash_adp_bry atr_hash = Hash_adp_bry.ci_ascii_().Add_bry_byte(Xatr_pos_key, Xatr_pos_id);
}
