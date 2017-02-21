/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.addons.wikis.pages.syncs.core.loaders; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.pages.*; import gplx.xowa.addons.wikis.pages.syncs.*; import gplx.xowa.addons.wikis.pages.syncs.core.*;
import gplx.core.brys.*; import gplx.core.btries.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.*; import gplx.langs.htmls.docs.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.imgs.atrs.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.imgs.*;
import gplx.xowa.wikis.domains.*;
import gplx.xowa.addons.wikis.pages.syncs.core.parsers.*;
public class Xosync_page_loader {
	private final    Xoh_hdoc_ctx hctx = new Xoh_hdoc_ctx();
	private final    Gfh_tag_rdr tag_rdr = Gfh_tag_rdr.New__html();
	private final    Bry_err_wkr err_wkr = new Bry_err_wkr();
	private final    Xoh_img_src_data img_src_parser = new Xoh_img_src_data();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	public byte[] Parse(Xow_wiki wiki, Xoh_page hpg, byte[] src) {
		// init hctx, tag_rdr, err_wkr
		int src_len = src.length;
		hctx.Init_by_page(wiki, hpg);
		tag_rdr.Init(hpg.Url_bry_safe(), src, 0, src_len);
		err_wkr.Init_by_page(String_.new_u8(hpg.Url_bry_safe()), src);

		// loop for all <img>
		int pos = 0;
		Btrie_rv trv = new Btrie_rv();
		while (true) {
			// get next "<img>"
			Gfh_tag img_tag = tag_rdr.Tag__find_fwd_head(pos, src_len, Gfh_tag_.Id__img);

			// none found; add and exit
			if (img_tag.Name_id() == Gfh_tag_.Id__eos) {					
				tmp_bfr.Add_mid(src, pos, src_len); // add bytes between img_end and prv_pos
				break;
			}

			// add bytes between prv_pos and img_bgn
			tmp_bfr.Add_mid(src, pos, img_tag.Src_bgn());

			// do simple replace for @src
			Gfh_atr img_src_atr = img_tag.Atrs__get_by_or_fail(Gfh_atr_.Bry__src);
			byte[] img_src_val = img_src_atr.Val();
			byte path_tid = Xosync_img_src_parser.Src_xo_trie.Match_byte_or(trv, img_src_val, Xosync_img_src_parser.Path__unknown);
			switch (path_tid) {
				case Xosync_img_src_parser.Path__file:
					Add_img(wiki, hpg, img_tag, img_src_atr, img_src_val, path_tid, Xosync_img_src_parser.Bry__xowa_file, wiki.App().Fsys_mgr().File_dir().To_http_file_bry());
					break;
				case Xosync_img_src_parser.Path__math:
					Add_img(wiki, hpg, img_tag, img_src_atr, img_src_val, path_tid, Xosync_img_src_parser.Bry__xowa_math, wiki.App().Fsys_mgr().File_dir().GenSubDir_nest("math").To_http_file_bry());
					break;
			}

			pos = img_tag.Src_end();
		}

		// overwrite html
		src = tmp_bfr.To_bry_and_clear();
		hpg.Db().Html().Html_bry_(src);
		return src;
	}
	private Xof_fsdb_itm Add_img(Xow_wiki wiki, Xoh_page hpg, Gfh_tag img_tag, Gfh_atr img_src_atr, byte[] img_src_val, byte path_tid, byte[] src_find, byte[] src_repl) {
		// replace "xowa:/file" with "file:////xowa/file/"
		img_src_val = Bry_.Replace(img_src_val, src_find, src_repl);

		// parse src
		img_src_parser.Parse(err_wkr, hctx, wiki.Domain_bry(), img_src_atr.Val_bgn(), img_src_atr.Val_end());
		if (img_src_parser.File_ttl_bry() == null) return null; // skip images that don't follow format of "commons.wikimedia.org/thumb/7/70/A.png"; for example, enlarge buttons

		// create img
		Xof_fsdb_itm img = hpg.Img_mgr().Make_img(false);

		// use repo_tid to get fsys_root, orig_repo_name
		byte repo_tid = img_src_parser.Repo_tid();
		byte[] orig_repo_name = null, fsys_root = null;
		switch (repo_tid) {
			case Xof_repo_tid_.Tid__remote:		fsys_root = hctx.Fsys__file__comm(); orig_repo_name = Xow_domain_itm_.Bry__commons; break;
			case Xof_repo_tid_.Tid__local:		fsys_root = hctx.Fsys__file__wiki(); orig_repo_name = wiki.Domain_bry(); break;
			case Xof_repo_tid_.Tid__math:		fsys_root = hctx.Fsys__file__math(); orig_repo_name = Xof_repo_tid_.Bry__math; break;
		}

		// set vals
		img.Orig_repo_name_(orig_repo_name);
		byte[] file_ttl_bry = gplx.langs.htmls.encoders.Gfo_url_encoder_.Http_url.Decode(img_src_parser.File_ttl_bry());
		Xof_ext file_ext = Xosync_img_src_parser.Ext_by_ttl(file_ttl_bry, repo_tid);
		img.Init_by_wm_parse(hctx.Wiki__domain_itm().Abrv_xo(), img_src_parser.Repo_is_commons(), img_src_parser.File_is_orig(), file_ttl_bry, file_ext, img_src_parser.File_w(), img_src_parser.File_time(), img_src_parser.File_page());

		// recalc src based on "file:////xowa/file/"
		hctx.File__url_bldr().Init_by_repo(repo_tid, fsys_root, Bool_.N, Byte_ascii.Slash, Bool_.N, Bool_.N, 4);
		hctx.File__url_bldr().Init_by_itm(img_src_parser.File_is_orig() ? Xof_img_mode_.Tid__orig : Xof_img_mode_.Tid__thumb, file_ttl_bry, Xof_file_wkr_.Md5(file_ttl_bry), Xof_ext_.new_by_ttl_(file_ttl_bry), img_src_parser.File_w(), img_src_parser.File_time(), img_src_parser.File_page());
		Io_url html_view_url = hctx.File__url_bldr().Xto_url_by_http();

		// if (path_tid == Xosync_img_src_parser.Path__file)
		img.Init_at_gallery_end(img_tag.Atrs__get_as_int_or(Gfh_atr_.Bry__width,0), img_tag.Atrs__get_as_int_or(Gfh_atr_.Bry__height, 0), html_view_url, html_view_url);

		Xosync_hdoc_parser.Write_img_tag(tmp_bfr, img_tag, img_src_val, img.Html_uid());
		return img;
	}
}