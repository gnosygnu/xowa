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
package gplx.xowa.htmls.core.makes; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.core.primitives.*; import gplx.xowa.htmls.core.makes.imgs.*; import gplx.xowa.files.*; import gplx.xowa.parsers.lnkis.*;
class Xoh_make_mgr_fxt {
	private final List_adp img_list = List_adp_.new_();
	private final Xoh_page hpg = new Xoh_page();
	private Xoh_make_mgr hswap_mgr; private Xowe_wiki wiki;
	public void Clear() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
		this.wiki.Init_by_wiki();
		this.hswap_mgr = wiki.Html__hdump_mgr().Load_mgr().Make_mgr();
	}
	public void Clear_imgs() {img_list.Clear();}
	public Xoh_make_mgr_fxt Init_body(String body) {hpg.Body_(Bry_.new_u8(body)); return this;}
	public Xoh_make_mgr_fxt Init_data_gly(int uid, int box_max) {hpg.Gallery_itms().Add(uid, new Xohd_img_itm__gallery_mgr(uid, box_max)); return this;}
	public Xoh_make_mgr_fxt Init_data_img_basic(String ttl, int html_uid, int html_w, int html_h) {
		Xohd_img_itm__img img = new Xohd_img_itm__img();
		img.Data_init_base(Bry_.new_u8(ttl), Xop_lnki_type.Id_none, Xof_img_size.Upright_null, Xof_img_size.Null, Xof_img_size.Null
			, Xof_lnki_time.Null, Xof_lnki_page.Null
			, Xohd_img_itm__base.File_repo_id_commons, Xof_ext_.Id_png, Bool_.N, html_w
			, html_uid, html_w, html_h
			);
		img_list.Add(img);
		return this;
	}
	public Xoh_make_mgr_fxt Init_data_img_gly(String ttl, int html_uid, int html_w, int html_h, int box_w, int img_w, int img_pad) {
		Xohd_img_itm__gallery_itm img = new Xohd_img_itm__gallery_itm();
		img.Data_init_gallery(box_w, img_w, img_pad);
		img.Data_init_base(Bry_.new_u8(ttl), Xop_lnki_type.Id_none, Xof_img_size.Upright_null, Xof_img_size.Null, Xof_img_size.Null
			, Xof_lnki_time.Null, Xof_lnki_page.Null
			, Xohd_img_itm__base.File_repo_id_commons, Xof_ext_.Id_png, Bool_.N, html_w
			, html_uid, html_w, html_h
			);
		img_list.Add(img);
		return this;
	}
	public Xoh_make_mgr_fxt Init_data_redlink(int... uids) {
		int uids_len = uids.length;
		for (int i = 0; i < uids_len; ++i) {
			Int_obj_ref redlink_uid = Int_obj_ref.new_(uids[i]);
			hpg.Redlink_uids().Add(redlink_uid, redlink_uid);
		}
		return this;
	}
	public Xoh_make_mgr_fxt Test_html(String expd) {
		if (img_list.Count() > 0) hpg.Img_itms_((Xohd_img_itm__base[])img_list.To_ary_and_clear(Xohd_img_itm__base.class));
		byte[] actl = hswap_mgr.Parse(hpg.Body(), hpg, wiki);
		Tfds.Eq_str_lines(expd, String_.new_u8(actl));
		return this;
	}
}
