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
package gplx.xowa.htmls.core.htmls.utls;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.langs.htmls.Gfh_utl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.xowa.Xoa_app;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xow_wiki;
import gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx;
import gplx.xowa.htmls.hrefs.Xoh_href_;
import gplx.xowa.htmls.hrefs.Xoh_href_wtr;
public class Xoh_lnki_bldr {
	private final Xoa_app app; private final Xoh_href_wtr href_wtr; private final byte[] img_root_dir;
	private final BryWtr tmp_bfr = BryWtr.NewAndReset(255);
	private byte[] href, title, id, caption;
	private byte[] img_rel_path; private int img_w, img_h; private boolean img_pos_is_left;
	public Xoh_lnki_bldr(Xoa_app app, Xoh_href_wtr href_wtr) {
		this.app = app; this.href_wtr = href_wtr;
		this.img_root_dir = app.Fsys_mgr().Bin_xowa_file_dir().To_http_file_bry();
	}
	public Xoh_lnki_bldr Clear() {
		href = title = id = caption = null;
		img_rel_path = null; img_w = 0; img_h = 0;
		img_pos_is_left = true;
		return this;
	}
	public Xoh_lnki_bldr Id_(byte[] v) {this.id = Gfh_utl.Escape_for_atr_val_as_bry(tmp_bfr, AsciiByte.Apos, v); return this;}
	public Xoh_lnki_bldr Href_(Xow_wiki wiki, byte[] bry) {return Href_(wiki.Domain_bry(), wiki.Ttl_parse(bry));}
	public Xoh_lnki_bldr Href_(byte[] domain_bry, Xoa_ttl ttl) {
		href_wtr.Build_to_bfr(tmp_bfr, app, Xoh_wtr_ctx.Popup, domain_bry, ttl);
		this.href = tmp_bfr.ToBryAndClear();
		return this;
	}
	public Xoh_lnki_bldr Href_wo_escape_(byte[] domain_bry, byte[] v) {
		this.href = BryUtl.Add(Xoh_href_.Bry__site, domain_bry, Xoh_href_.Bry__wiki, v);
		return this;
	}
	public Xoh_lnki_bldr Title_(byte[] title) {
		this.title = Gfh_utl.Escape_for_atr_val_as_bry(tmp_bfr, AsciiByte.Apos, title);
		return this;
	}
	public Xoh_lnki_bldr Img_pos_is_left_(boolean v) {this.img_pos_is_left = v; return this;}
	public Xoh_lnki_bldr Img_16x16(byte[] rel_path) {return Img_(rel_path, 16, 16);}
	private Xoh_lnki_bldr Img_(byte[] rel_path, int w, int h) {
		this.img_rel_path = rel_path;
		this.img_w = w;
		this.img_h = h;
		return this;
	}
	public Xoh_lnki_bldr Caption_(byte[] text) {
		this.caption = Gfh_utl.Escape_html_as_bry(tmp_bfr, text, BoolUtl.Y, BoolUtl.Y, BoolUtl.Y, BoolUtl.Y, BoolUtl.Y);
		return this;
	}
	public Xoh_lnki_bldr Caption_direct_(byte[] v) {this.caption = v; return this;}
	public byte[] Bld_to_bry() {
		Bld(tmp_bfr);
		byte[] rv = tmp_bfr.ToBryAndClear();
		this.Clear();
		return rv;
	}
	public void Bld(BryWtr bfr) {
		bfr.AddStrA7("<a href='").Add(href);
		if (title != null)
			bfr.AddStrA7("' title='").Add(title);
		if (id != null)
			bfr.AddStrA7("' id='").Add(id);
		bfr.AddStrA7("'>");
		if ( img_pos_is_left && img_rel_path != null)
			Bld_img(bfr);
		if (caption != null)
			bfr.Add(caption);
		if (!img_pos_is_left && img_rel_path != null)
			Bld_img(bfr);
		bfr.AddStrA7("</a>");
	}
	private void Bld_img(BryWtr bfr) {
		bfr.AddStrA7("<img src='").Add(img_root_dir).Add(img_rel_path).AddStrA7("' width='").AddIntVariable(img_w).AddStrA7("' height='").AddIntVariable(img_h).AddStrA7("'/>");
	}
}
