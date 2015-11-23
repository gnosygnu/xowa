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
import gplx.core.brys.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*;
import gplx.xowa.htmls.core.wkrs.imgs.atrs.*;
import gplx.xowa.files.*;
public class Xoh_img_bldr {
	public Xoh_img_wtr			Wtr() {return wtr;} private final Xoh_img_wtr wtr = new Xoh_img_wtr();
	public Xof_fsdb_itm			Fsdb_itm() {return fsdb_itm;} private Xof_fsdb_itm fsdb_itm;
	public void Make_by_parse(Bry_bfr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, Xoh_img_parser arg) {
		Make( bfr, hpg, hctx, src, arg.Img_src().File_ttl_bry(), arg.Img_xoimg()
			, arg.Anch_href().Rel_nofollow_exists(), arg.Anch_href().Atr(), arg.Anch_cls().Atr(), arg.Anch_title()
			, arg.Img_w(), arg.Img_h(), arg.Img_src().Atr(), arg.Img_cls().Atr(), arg.Img_alt());
		wtr.Bfr_arg__add(bfr);
	}
	public void Make(Bry_bfr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, byte[] lnki_ttl, Xoh_img_xoimg_parser img_xoimg
		, boolean anch_rel_is_nofollow, Bfr_arg anch_href, Bfr_arg anch_cls, Bfr_arg anch_ttl
		, int img_w, int img_h, Bfr_arg img_src, Bfr_arg img_cls, Bfr_arg img_alt) {
		wtr.Clear();
		this.fsdb_itm = hpg.Img_mgr().Make_img();
		if (img_xoimg.Val_dat_exists()) {
			fsdb_itm.Init_at_lnki(Xof_exec_tid.Tid_wiki_page, hpg.Wiki().Domain_itm().Abrv_xo(), lnki_ttl, img_xoimg.Lnki_type(), img_xoimg.Lnki_upright(), img_xoimg.Lnki_w(), img_xoimg.Lnki_h(), img_xoimg.Lnki_time(), img_xoimg.Lnki_page(), Xof_patch_upright_tid_.Tid_all);
			hctx.File__mgr().Check_cache(fsdb_itm);
			wtr.Img_xoimg_(src, img_xoimg.Val_bgn(), img_xoimg.Val_end());
			wtr.Img_src_empty_().Img_w_(0).Img_h_(0);
		}
		else if (img_w != -1) {
			wtr.Img_w_(img_w).Img_h_(img_h).Img_src_(img_src);				
		}
		if (anch_rel_is_nofollow) wtr.Anch_rel_nofollow_();
		wtr.Anch_href_(anch_href).Anch_cls_(anch_cls).Anch_title_(anch_ttl).Anch_xowa_title_(lnki_ttl);
		wtr.Img_id_(Xoh_img_mgr.Bry__html_uid, fsdb_itm.Html_uid()).Img_alt_(img_alt).Img_cls_(img_cls);
	}
}
