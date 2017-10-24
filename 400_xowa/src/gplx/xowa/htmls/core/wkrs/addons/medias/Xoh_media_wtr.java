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
package gplx.xowa.htmls.core.wkrs.addons.medias; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.addons.*;
import gplx.core.brys.*; import gplx.core.primitives.*; import gplx.core.brys.fmtrs.*; import gplx.core.threads.poolables.*; import gplx.core.brys.args.*;
import gplx.xowa.files.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.wkrs.imgs.*; import gplx.xowa.htmls.core.wkrs.bfr_args.*;
import gplx.xowa.htmls.sections.*; import gplx.xowa.htmls.core.hzips.*;
public class Xoh_media_wtr {
	private final    Bfr_arg__bry lnki_ttl = Bfr_arg__bry.New_empty();
	public void Write(Bry_bfr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, Xoh_data_itm data_itm, Xoh_img_wtr img_wtr) {
		// make fsdb_itm
		hpg.Img_mgr().Make_aud();

		// set props and write
		Xoh_media_data data = (Xoh_media_data)data_itm;
		if (data.Is_audio()) {
			lnki_ttl.Set_by_mid(src, data.Lnki_ttl_bgn(), data.Lnki_ttl_end());
			if (data.Aud_noicon())
				fmt__noicon.Bfr_arg__clear();
			else
				fmt__noicon.Args_(lnki_ttl);

			int aud_width = data.Aud_width();
			fmt__audio.Bld_many(bfr, lnki_ttl, aud_width - 2, aud_width, fmt__noicon);
		}
		else {
			Xoh_img_data img_data = data.Img_data();
			lnki_ttl.Set_by_val(img_data.Img_src().File_ttl_bry());

			// lnki_w
			int lnki_w = img_data.Img_xoimg().Lnki_w();
			if (lnki_w == -1) lnki_w = Xof_img_size.Thumb_width_ogv;	// no width; default to 220; EX: [[File:A.ogv]]

			fmt__video.Bld_many(bfr, img_wtr, lnki_ttl, lnki_w - 2, lnki_w);
		}
	}

	private final    Bfr_arg__bry_fmt fmt__noicon = new Bfr_arg__bry_fmt(Bry_fmt.Auto_nl_apos("\n<div><a href='/wiki/File:~{lnki_ttl}' class='xowa_media_info' title='About this file'></a></div>"));
	private final    Bry_fmt 
	  fmt__audio = Bry_fmt.Auto_nl_apos
	( "<div class='xowa_media_div'>"
	, "<div><a href='' xowa_title='~{lnki_ttl}' class='xowa_media_play' style='width:~{a_width}px;max-width:~{a_max_width}px;' alt='Play sound'></a></div>~{noicon}"
	, "</div>"
	)
	, fmt__video = Bry_fmt.Auto_nl_apos
	( "<div class='xowa_media_div'>"
	, "<div>~{div1_img}</div>"
	, "<div><a href='' xowa_title='~{lnki_ttl}' class='xowa_media_play' style='width:~{a_width}px;max-width:~{a_max_width}px;' alt='Play sound'></a></div>"
	, "</div>"
	);
}
