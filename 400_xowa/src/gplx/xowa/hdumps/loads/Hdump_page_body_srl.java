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
package gplx.xowa.hdumps.loads; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
import gplx.core.brys.*; import gplx.xowa.pages.*; import gplx.xowa.hdumps.core.*; import gplx.xowa.hdumps.pages.*; import gplx.xowa.pages.skins.*;
import gplx.xowa.html.modules.*;
public class Hdump_page_body_srl {
	private static final Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	public static void Save(Bry_bfr bfr, Xoa_page page) {
		bfr.Add_int_fixed(0, 1).Add_byte_pipe();			// version
		Xopg_hdump_data hdump_data = page.Hdump_data();
		bfr.Add_int_variable(hdump_data.Data_count_imgs());	// imgs_count
		Xopg_html_data html_data = page.Html_data();
		Save_html_modules(bfr, html_data);
		Save_data(bfr, Tid_display_ttl	, html_data.Display_ttl());
		Save_data(bfr, Tid_content_sub	, html_data.Content_sub());
		Save_sidebars(bfr, page			, html_data);
		Save_data(bfr, Tid_body			, hdump_data.Body());
	}
	private static void Save_html_modules(Bry_bfr bfr, Xopg_html_data html_data) {
		Xoh_module_mgr module_mgr = html_data.Module_mgr();
		Save_html_modules__itm(tmp_bfr, module_mgr.Itm_mathjax().Enabled());
		Save_html_modules__itm(tmp_bfr, module_mgr.Itm_popups().Bind_hover_area());
		Save_html_modules__itm(tmp_bfr, module_mgr.Itm_gallery().Enabled());
		Save_html_modules__itm(tmp_bfr, module_mgr.Itm_hiero().Enabled());
		Save_data(bfr, Tid_html_module, tmp_bfr.Xto_bry_and_clear());
	}
	public static void Load_html_modules(Hdump_page hpg, byte[] src, int bgn, int end) {
		Hdump_module_mgr module_mgr = hpg.Module_mgr();
		module_mgr.Math_exists_				(src[bgn + 0] == Byte_ascii.Ltr_y);
		module_mgr.Imap_exists_				(src[bgn + 2] == Byte_ascii.Ltr_y);
		module_mgr.Gallery_packed_exists_	(src[bgn + 4] == Byte_ascii.Ltr_y);
		module_mgr.Hiero_exists_			(src[bgn + 6] == Byte_ascii.Ltr_y);
	}
	public static void Load_html_modules(Xoh_module_mgr page_module_mgr, Hdump_page hpg) {
		Hdump_module_mgr dump_module_mgr = hpg.Module_mgr();
		page_module_mgr.Itm_mathjax().Enabled_			(dump_module_mgr.Math_exists());
		page_module_mgr.Itm_popups().Bind_hover_area_	(dump_module_mgr.Imap_exists());
		page_module_mgr.Itm_gallery().Enabled_			(dump_module_mgr.Gallery_packed_exists());
		page_module_mgr.Itm_hiero().Enabled_			(dump_module_mgr.Hiero_exists());
	}
	private static void Save_html_modules__itm(Bry_bfr tmp_bfr, boolean v) {
		tmp_bfr.Add_yn(v);
		tmp_bfr.Add_byte_pipe();
	}
	private static void Save_sidebars(Bry_bfr bfr, Xoa_page page, Xopg_html_data html_data) {
		Xopg_xtn_skin_mgr mgr = html_data.Xtn_skin_mgr();
		int len = mgr.Count();
		boolean sidebar_exists = false;
		for (int i = 0; i < len; ++i) {
			Xopg_xtn_skin_itm itm = mgr.Get_at(i);
			if (itm.Tid() == Xopg_xtn_skin_itm_tid.Tid_sidebar) {
				sidebar_exists = true;
				itm.Write(tmp_bfr, page);
			}
		}
		if (sidebar_exists)
			Save_data(bfr, Tid_sidebar_div, tmp_bfr.Xto_bry_and_clear());
	}
	private static void Save_data(Bry_bfr bfr, int tid, byte[] data) {
		if (data != null) {
			bfr.Add_byte_nl();
			bfr.Add(Sect_lhs);
			bfr.Add_int_variable(tid);
			bfr.Add(Sect_rhs);
			bfr.Add(data);
		}
	}
	public static void Load(Hdump_page hpg, Bry_rdr rdr, byte[] src) {
		rdr.Src_(src);
		Load_body_meta(hpg, rdr);
		while (!rdr.Pos_is_eos()) {
			int idx_pos = rdr.Find_fwd__pos_at_rhs(Sect_lhs);
			int idx = Byte_ascii.Xto_digit(src[idx_pos]);
			int lhs_pos = idx_pos + 3;	// 2=skip "#/>"
			if (idx == Tid_body) {
				hpg.Page_body_(Bry_.Mid(src, lhs_pos, src.length));	
				return;
			}
			else {
				int rhs_pos = rdr.Find_fwd__pos_at_lhs(Sect_lhs) - 1;	// -1 to ignore \n				
				switch (idx) {
					case Tid_html_module:	Load_html_modules(hpg, src, lhs_pos, rhs_pos); break;
					case Tid_display_ttl:	hpg.Display_ttl_(Bry_.Mid(src, lhs_pos, rhs_pos)); break;
					case Tid_content_sub:	hpg.Content_sub_(Bry_.Mid(src, lhs_pos, rhs_pos)); break;
					case Tid_sidebar_div:	hpg.Sidebar_div_(Bry_.Mid(src, lhs_pos, rhs_pos)); break;
				}
			}
		}
	}	private static final byte[] Sect_lhs = Bry_.new_ascii_("<xo_"), Sect_rhs = Bry_.new_ascii_("/>");
	private static void Load_body_meta(Hdump_page hpg, Bry_rdr rdr) {
		hpg.Version_id_(rdr.Read_int_to_pipe());
		hpg.Img_count_(rdr.Read_int_to_nl());
	}
	private static final int	// SERIALIZED
	  Tid_html_module			= 1
	, Tid_display_ttl			= 2
	, Tid_content_sub			= 3
	, Tid_sidebar_div			= 4
	, Tid_body					= 5
	;
}
