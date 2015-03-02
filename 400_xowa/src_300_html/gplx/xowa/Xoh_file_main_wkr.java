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
package gplx.xowa; import gplx.*;
import gplx.core.primitives.*;
import gplx.xowa.html.*; import gplx.xowa.html.lnkis.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*;
public class Xoh_file_main_wkr implements Bry_fmtr_arg {
	private Xof_xfer_itm xfer_itm; private Xoh_file_main_alts alts = new Xoh_file_main_alts(); private Xoh_file_page opt; 
	byte[] file_size_bry; Xoa_ttl ttl; Xowe_wiki wiki;
	public void Bld_html(Xowe_wiki wiki, Xop_ctx ctx, Bry_bfr bfr, Xoa_ttl ttl, Xoh_file_page opt, Xof_xfer_queue queue) {
		this.opt = opt; this.ttl = ttl; this.wiki = wiki;
		Int_2_ref size_main = opt.Size_main();
		int main_w = size_main.Val_0();
		int main_h = size_main.Val_1();
		this.xfer_itm = wiki.Html_mgr().Html_wtr().Lnki_wtr().File_wtr().Lnki_eval(ctx, queue, ttl.Page_txt(), Xop_lnki_type.Id_thumb, main_w, main_h, Xop_lnki_tkn.Upright_null, Xof_doc_thumb.Null, Xof_doc_page.Null, false, queue_add_ref);
		Xof_meta_itm meta_itm = wiki.File_mgr().Meta_mgr().Get_itm_or_new(ttl.Page_db(), Xof_xfer_itm_.Md5_(ttl.Page_db()));	// NOTE: can't use Md5 of xfer_itm, b/c xfer_itm.Ttl() b/c may be redirected; EX:w:[[Image:Alcott-L.jpg]]
		Xof_repo_itm trg_repo = null;
		trg_repo = meta_itm.Repo_itm(wiki);
		if (trg_repo == null) trg_repo = wiki.File_mgr().Repo_mgr().Repos_get_at(0).Trg();
		xfer_itm.Set__meta(meta_itm, trg_repo, Xof_repo_itm.Thumb_default_null);
		xfer_itm.Set__orig(meta_itm.Orig_w(), meta_itm.Orig_h(), Xof_img_size.Size_null);
		xfer_itm.Trg_repo_idx_(meta_itm.Vrtl_repo());

		play_btn_icon = wiki.Html_mgr().Img_media_play_btn();
		file_size_bry = Bry_.Empty;
		if (xfer_itm.Calc_by_meta(true)) {
			long file_size = Io_mgr._.QueryFil(xfer_itm.Trg_file(Xof_repo_itm.Mode_orig, Xof_img_size.Size_null_deprecated)).Size();
			file_size_bry = Bry_.new_ascii_(gplx.ios.Io_size_.Xto_str(file_size));
		}
		else {	// NOTE: PAGE:c:File:Solar_Life_Cycle.svg would not load on subsequent views; note that "xfer_itm.Atrs_calc_for_html(true)" resizes image b/c it is .svg; DATE:2013-03-01
			xfer_itm = xfer_itm.Clone();
			queue.Add(xfer_itm);
		}
		opt.Html_main().Bld_bfr_many(bfr, this);
	}	private byte[] play_btn_icon; private Bool_obj_ref queue_add_ref = Bool_obj_ref.n_();
	public void Bld_html(Xowe_wiki wiki, Bry_bfr bfr, Xof_xfer_itm xfer_itm, Xoa_ttl ttl, Xoh_file_page opt, byte[] file_size_bry, byte[] play_btn_icon, int elem_id_val) {
		this.xfer_itm = xfer_itm; 
		this.wiki = wiki; this.ttl = ttl; this.opt = opt; this.file_size_bry = file_size_bry; this.play_btn_icon = play_btn_icon; this.elem_id_val = elem_id_val;
		opt.Html_main().Bld_bfr_many(bfr, this);		
	}	private int elem_id_val;
	public void XferAry(Bry_bfr bfr, int idx) {
		alts.Ctor(xfer_itm, opt);
		Xof_ext ext = xfer_itm.Lnki_ext();
		if (ext.Id_is_thumbable_img())
			opt.Html_main_img().Bld_bfr_many(bfr, xfer_itm.Orig_w(), xfer_itm.Orig_h(), xfer_itm.Html_orig_src(), file_size_bry, ext.Mime_type(), elem_id_val, xfer_itm.Html_w(), xfer_itm.Html_h(), xfer_itm.Html_view_src(), ttl.Full_txt(), Xoa_app_.Utl__encoder_mgr().Url().Encode(ttl.Page_url()), alts);
		else if (ext.Id_is_video())	// NOTE: video must precede audio else File:***.ogg will not show thumbs
			opt.Html_main_vid().Bld_bfr_many(bfr, elem_id_val, xfer_itm.Html_view_src(), Atr_class_image, ttl.Page_db(), xfer_itm.Html_view_src(), xfer_itm.Html_w(), xfer_itm.Html_h(), Bry_.Empty, xfer_itm.Html_orig_src(), xfer_itm.Html_w(), xfer_itm.Html_w(), play_btn_icon);
		else if (ext.Id_is_audio())
			opt.Html_main_aud().Bld_bfr_many(bfr, xfer_itm.Html_orig_src(), ttl.Page_db(), xfer_itm.Html_w(), xfer_itm.Html_w(), play_btn_icon);
	}
	private static final byte[] Atr_class_image = Bry_.new_ascii_("image");
}
