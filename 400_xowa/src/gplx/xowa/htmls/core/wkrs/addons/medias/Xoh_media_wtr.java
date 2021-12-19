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
package gplx.xowa.htmls.core.wkrs.addons.medias;
import gplx.types.custom.brys.fmts.itms.BryBfrArgFmt;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.fmts.itms.BryFmt;
import gplx.xowa.htmls.*;
import gplx.xowa.htmls.core.wkrs.*;
import gplx.types.custom.brys.wtrs.args.*;
import gplx.xowa.files.*;
import gplx.xowa.htmls.core.wkrs.imgs.*;
import gplx.xowa.htmls.core.hzips.*;
public class Xoh_media_wtr {
	private final BryBfrArgBry lnki_ttl = BryBfrArgBry.NewEmpty();
	public void Write(BryWtr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, Xoh_data_itm data_itm, Xoh_img_wtr img_wtr) {
		// make fsdb_itm
		hpg.Img_mgr().Make_aud();

		// set props and write
		Xoh_media_data data = (Xoh_media_data)data_itm;
		if (data.Is_audio()) {
			lnki_ttl.SetByMid(src, data.Lnki_ttl_bgn(), data.Lnki_ttl_end());
			if (data.Aud_noicon())
				fmt__noicon.BfrArgClear();
			else
				fmt__noicon.Set(lnki_ttl);

			int aud_width = data.Aud_width();
			fmt__audio.Bld_many(bfr, lnki_ttl, aud_width - 2, aud_width, fmt__noicon);
		}
		else {
			Xoh_img_data img_data = data.Img_data();
			lnki_ttl.SetByVal(img_data.Img_src().File_ttl_bry());

			// lnki_w
			int lnki_w = img_data.Img_xoimg().Lnki_w();
			if (lnki_w == -1) lnki_w = Xof_img_size.Thumb_width_ogv;	// no width; default to 220; EX: [[File:A.ogv]]

			fmt__video.Bld_many(bfr, img_wtr, lnki_ttl, lnki_w - 2, lnki_w);
		}
	}

	private final BryBfrArgFmt fmt__noicon = new BryBfrArgFmt(BryFmt.Auto_nl_apos("\n<div><a href='/wiki/File:~{lnki_ttl}' class='xowa_media_info' title='About this file'></a></div>"));
	private final BryFmt
	  fmt__audio = BryFmt.Auto_nl_apos
	( "<div class='xowa_media_div'>"
	, "<div><a href='' xowa_title='~{lnki_ttl}' class='xowa_media_play' style='width:~{a_width}px;max-width:~{a_max_width}px;' alt='Play sound'></a></div>~{noicon}"
	, "</div>"
	)
	, fmt__video = BryFmt.Auto_nl_apos
	( "<div class='xowa_media_div'>"
	, "<div>~{div1_img}</div>"
	, "<div><a href='' xowa_title='~{lnki_ttl}' class='xowa_media_play' style='width:~{a_width}px;max-width:~{a_max_width}px;' alt='Play sound'></a></div>"
	, "</div>"
	);
}
