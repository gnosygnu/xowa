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
package gplx.xowa.htmls.core.wkrs.imgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*;
import gplx.xowa.htmls.core.wkrs.imgs.atrs.*;
import gplx.xowa.files.*;
public class Xoh_img_make {
	private final Xoa_file_mgr file_mgr = new Xoa_file_mgr();
	public void Make(Bry_bfr bfr, Xoh_page hpg, byte[] src, Xoh_img_parser arg) {
		Xoh_img_xoimg_parser img_xoimg = arg.Img_xoimg_parser();
		Xoh_img_src_parser img_src = arg.Img_src_parser();
		Xof_fsdb_itm fsdb_itm = hpg.Img_mgr().Make_img();
		boolean html_data_exists = false;
		if (img_xoimg.Val_exists()) {		// data-xoimg exists; use it
			fsdb_itm.Init_at_lnki(Xof_exec_tid.Tid_wiki_page, hpg.Wiki().Domain_itm().Abrv_xo(), img_src.File_ttl_bry(), img_xoimg.Lnki_type(), img_xoimg.Lnki_upright(), img_xoimg.Lnki_w(), img_xoimg.Lnki_h(), img_xoimg.Lnki_time(), img_xoimg.Lnki_page(), Xof_patch_upright_tid_.Tid_all);
			html_data_exists = file_mgr.Check_cache(fsdb_itm);
		}
		else if (arg.Img_w_exists()) {		// width exists; use it
			// fsdb_itm.Init_by_hdump(img_src.Repo_is_commons(), img_src.File_ttl_bry(), arg.Img_w(), img_src.File_time(), img_src.File_page());
			html_data_exists = true;
		}
		Write_html(bfr, src, arg, fsdb_itm, html_data_exists);
	}
	private void Write_html(Bry_bfr bfr, byte[] src, Xoh_img_parser arg, Xof_fsdb_itm fsdb_itm, boolean html_data_exists) {
		// html_data_exists = n : "<a href='/wiki/File:A.png' class='image' title='abc'><img id='xoimg_1' alt='abc' src=''/></a>"
		bfr.Add_mid(src, arg.Anch_tag_bgn(), arg.Anch_tag_end());		// '<a href="/wiki/File:A.png" class="image" title="abc">'
		bfr.Add(Html_bldr_.Bry__img_lhs);								// '<img'
		bfr.Add(Html_bldr_.Bry__id__1st);								// ' id="'
		bfr.Add(Xoh_img_mgr.Bry__html_uid);								// 'xoimg_'
		bfr.Add_int_variable(fsdb_itm.Html_uid());						// '123'
		bfr.Add(Html_bldr_.Bry__alt__nth);								// '" alt="'
		arg.Img_alt_atr().Html__add(bfr);								// 'abc'
		bfr.Add(Html_bldr_.Bry__src__nth);								// '" src="'
		bfr.Add(Html_bldr_.Bry__lhs_end_inline_w_quote);				// '"/>'
		bfr.Add(Html_bldr_.Bry__a_rhs);									// '</a>'
	}
}
