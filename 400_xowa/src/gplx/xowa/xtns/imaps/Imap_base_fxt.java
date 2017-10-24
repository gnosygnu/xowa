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
package gplx.xowa.xtns.imaps; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.primitives.*;
import gplx.xowa.parsers.*;
import gplx.xowa.xtns.imaps.itms.*;
public class Imap_base_fxt {
	protected Xoae_app app; protected Xowe_wiki wiki;
	@gplx.Virtual public void Reset() {
		app = Xoa_app_fxt.Make__app__edit();
		wiki = Xoa_app_fxt.Make__wiki__edit(app);
		wiki.Parser_mgr().Ctx().Para().Enabled_n_();
	}
	public Imap_part_shape itm_rect_(String link, double... pts_ary) {return itm_shape_(Imap_part_.Tid_shape_rect, link, pts_ary);}
	public Imap_part_shape itm_circle_(String link, double... pts_ary) {return itm_shape_(Imap_part_.Tid_shape_circle, link, pts_ary);}
	public Imap_part_shape itm_poly_(String link, double... pts_ary) {return itm_shape_(Imap_part_.Tid_shape_poly, link, pts_ary);}
	private Imap_part_shape itm_shape_(byte tid, String link, double... pts_ary) {
		int pts_len = pts_ary.length;
		Double_obj_val[] pts_doubles = new Double_obj_val[pts_len];
		for (int i = 0; i < pts_len; ++i)
			pts_doubles[i] = Double_obj_val.new_(pts_ary[i]);
		byte[] link_bry = Bry_.new_u8(link);
		Imap_part_shape rv = new Imap_part_shape(tid, pts_doubles);
		Imap_link_owner_.Init(rv, app, wiki, link_bry, Make_link_tkn(link_bry));
		return rv;
	}
	private Xop_tkn_itm Make_link_tkn(byte[] src) {
		Xop_root_tkn root_tkn = new Xop_root_tkn();			
		wiki.Parser_mgr().Main().Parse_text_to_wdom(root_tkn, wiki.Parser_mgr().Ctx(), app.Parser_mgr().Tkn_mkr(), src, Xop_parser_.Doc_bgn_bos);
		return root_tkn.Subs_get(0);
	}
}
