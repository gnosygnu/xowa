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
package gplx.xowa.xtns.poems; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.html.*;
public class Poem_nde implements Xox_xnde {
	private Xop_root_tkn xtn_root;
	public void Xtn_parse(Xow_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {// REF.MW: Poem.php|wfRenderPoemTag
		int itm_bgn = xnde.Tag_open_end(), itm_end = xnde.Tag_close_bgn();
		if (itm_bgn == src.length)	return;  // NOTE: handle inline where there is no content to parse; EX: <poem/>
		if (itm_bgn >= itm_end)		return;  // NOTE: handle inline where there is no content to parse; EX: a<poem/>b
		if (src[itm_bgn] 		== Byte_ascii.NewLine)	++itm_bgn;	// ignore 1st \n; 
		if (src[itm_end - 1] 	== Byte_ascii.NewLine				// ignore last \n; 
			&& itm_end != itm_bgn)						--itm_end;	// ...if not same as 1st \n; EX: <poem>\n</poem>
		Poem_xtn_mgr xtn_mgr = (Poem_xtn_mgr)wiki.Xtn_mgr().Get_or_fail(Poem_xtn_mgr.XTN_KEY);
		xtn_root = xtn_mgr.Parser().Parse_text_to_wdom_old_ctx(ctx, Bry_.Mid(src, itm_bgn, itm_end), true); // NOTE: ignoring paragraph mode; technically MW enables para mode, but by replacing "\n" with "<br/>\n" all the logic with para/pre mode is skipped
	}
	public void Xtn_write(Xoa_app app, Xoh_html_wtr html_wtr, Xoh_wtr_ctx opts, Xop_ctx ctx, Bry_bfr bfr, byte[] src, Xop_xnde_tkn xnde) {
		if (xtn_root == null) return;	// inline poem; write nothing; EX: <poem/>
		bfr.Add(Bry_poem_bgn);
		html_wtr.Write_tkn(bfr, ctx, opts, xtn_root.Root_src(), xnde, Xoh_html_wtr.Sub_idx_null, xtn_root);
		bfr.Add(Bry_poem_end);			
	}
	private static byte[]
	  Bry_poem_bgn = Bry_.new_ascii_("<div class=\"poem\">\n<p>\n")	// NOTE: always enclose in <p>; MW does this implicitly in its modified parse; DATE:2014-04-27
	, Bry_poem_end = Bry_.new_ascii_("\n</p>\n</div>"); 
}
