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
package gplx.xowa.apps.wms.apis.parses; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.wms.*; import gplx.xowa.apps.wms.apis.*;
import gplx.core.brys.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.*; import gplx.langs.htmls.docs.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.xowa.htmls.core.wkrs.imgs.atrs.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*;
public class Wm_page_loader {
	private final    Xoh_hdoc_ctx hctx = new Xoh_hdoc_ctx();
	private final    Gfh_tag_rdr tag_rdr = Gfh_tag_rdr.New__html();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private final    Bry_err_wkr err_wkr = new Bry_err_wkr();
	public byte[] Parse(Xow_wiki wiki, Xoh_page hpg, byte[] src) {
		int src_len = src.length;
		tag_rdr.Init(hpg.Url_bry_safe(), src, 0, src_len);
		hctx.Init_by_page(wiki, hpg);
		Xoh_img_src_data img_src_parser = new Xoh_img_src_data();

		err_wkr.Init_by_page(String_.new_u8(hpg.Url_bry_safe()), src);

		int pos = 0;
		while (true) {
			Gfh_tag img_tag = tag_rdr.Tag__find_fwd_head(pos, src_len, Gfh_tag_.Id__img);
			if (img_tag.Name_id() == Gfh_tag_.Id__eos) {
				// add bytes between img_end and prv_pos
				tmp_bfr.Add_mid(src, pos, src_len);
				break;
			}

			// add bytes between prv_pos and img_bgn
			tmp_bfr.Add_mid(src, pos, img_tag.Src_bgn());

			// do simple replace on <img> tag
			Gfh_atr img_src_atr = img_tag.Atrs__get_by_or_fail(Gfh_atr_.Bry__src);
			byte[] img_src_val = img_src_atr.Val();
			img_src_parser.Parse(err_wkr, hctx, wiki.Domain_bry(), img_src_atr.Val_bgn(), img_src_atr.Val_end());

			img_src_val = Bry_.Replace(img_src_val, Wm_img_src_parser.Bry__xowa_src_bgn, wiki.App().Fsys_mgr().File_dir().To_http_file_bry());
			Wm_hdoc_parser_wkr.Write_img_tag(tmp_bfr, img_tag, img_src_val);

			Xof_fsdb_itm img = hpg.Img_mgr().Make_img(false);
			byte[] file_ttl_bry = gplx.langs.htmls.encoders.Gfo_url_encoder_.Http_url.Decode(img_src_parser.File_ttl_bry());
			img.Init_by_wm_parse(hctx.Wiki__domain_itm().Abrv_xo(), img_src_parser.Repo_is_commons(), img_src_parser.File_is_orig(), file_ttl_bry, img_src_parser.File_w(), img_src_parser.File_time(), img_src_parser.File_page());
			hctx.File__url_bldr().Init_by_root(img_src_parser.Repo_is_commons() ? hctx.Fsys__file__comm() : hctx.Fsys__file__wiki(), Bool_.N, Byte_ascii.Slash, Bool_.N, Bool_.N, 4);
			hctx.File__url_bldr().Init_by_itm(img_src_parser.File_is_orig() ? Xof_repo_itm_.Mode_orig : Xof_repo_itm_.Mode_thumb, file_ttl_bry, Xof_file_wkr_.Md5(file_ttl_bry), Xof_ext_.new_by_ttl_(file_ttl_bry), img_src_parser.File_w(), img_src_parser.File_time(), img_src_parser.File_page());
			img.Html_view_url_(hctx.File__url_bldr().Xto_url());
			
			pos = img_tag.Src_end();
		}
		src = tmp_bfr.To_bry_and_clear();
		hpg.Db().Html().Html_bry_(src);
		return src;
	}
}