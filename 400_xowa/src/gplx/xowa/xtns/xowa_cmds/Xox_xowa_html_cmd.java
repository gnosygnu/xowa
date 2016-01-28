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
import gplx.core.primitives.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.htmls.*;
public class Xox_xowa_html_cmd implements Xox_xnde, Mwh_atr_itm_owner {
	private byte pos_val = Pos_head_end;
	public Xop_root_tkn Xtn_root() {throw Err_.new_unimplemented_w_msg("xowa_html_cmd.xtn_root should not be called");}
	public byte[] Xtn_html() {throw Err_.new_unimplemented_w_msg("xowa_html_cmd.xtn_html should not be called");}
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, Object xatr_id_obj) {
		if (xatr_id_obj == null) return;
		Byte_obj_val xatr_id = (Byte_obj_val)xatr_id_obj;
		switch (xatr_id.Val()) {
			case Xatr_pos_id:			pos_val = Pos_val(xatr.Val_as_bry()); break;
			default:					throw Err_.new_unhandled(xatr_id.Val());
		}
	}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		if (!wiki.Sys_cfg().Xowa_cmd_enabled()) {	// not allowed; warn and exit
			wiki.Appe().Usr_dlg().Warn_many("", "", "xowa_html command only allowed in xowa_home");
			return;
		}
		Xox_xnde_.Xatr__set(wiki, this, xatrs_hash, src, xnde);
		int itm_bgn = xnde.Tag_open_end(), itm_end = xnde.Tag_close_bgn();
		if (src[itm_bgn] 		== Byte_ascii.Nl) ++itm_bgn;	// ignore 1st \n; 
		// if (src[itm_end - 1] 	== Byte_ascii.Nl) --itm_end;	// ignore last \n;
		byte[] raw = Bry_.Mid(src, itm_bgn, itm_end);
		if (pos_val == Pos_head_end)
			ctx.Page().Html_data().Custom_head_end_concat(raw);
		else
			ctx.Page().Html_data().Custom_html_end_concat(raw);
	}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xop_xnde_tkn xnde, byte[] src) {}
	private static byte Pos_val(byte[] bry) {
		Object o = pos_val_hash.Get_by_bry(bry);
		if (o == null) throw Err_.new_wo_type("unknown pos", "pos", String_.new_u8(bry));
		return ((Byte_obj_val)o).Val();
	}
	private static final byte Pos_head_end = 1, Pos_html_end = 2;
	private static final byte[] 
	  Xatr_pos_key				= Bry_.new_a7("pos")
	, Xatr_pos_val__head_end	= Bry_.new_a7("head.end")
	, Xatr_pos_val__html_end	= Bry_.new_a7("html.end")
	;
	private static final Hash_adp_bry pos_val_hash = Hash_adp_bry.ci_a7()
	.Add_bry_byte(Xatr_pos_val__head_end, Pos_head_end)
	.Add_bry_byte(Xatr_pos_val__html_end, Pos_html_end)
	;
	private static final byte Xatr_pos_id = 1;
	private static final Hash_adp_bry xatrs_hash = Hash_adp_bry.ci_a7().Add_bry_byte(Xatr_pos_key, Xatr_pos_id);
}
