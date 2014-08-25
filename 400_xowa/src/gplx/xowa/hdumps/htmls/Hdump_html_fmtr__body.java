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
package gplx.xowa.hdumps.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
import gplx.core.brys.*; import gplx.core.btries.*; import gplx.xowa.html.*; import gplx.xowa.html.lnkis.*; import gplx.xowa.hdumps.core.*;
public class Hdump_html_fmtr__body implements Bry_fmtr_arg {
	private Bry_rdr bry_rdr = new Bry_rdr();
	private Xow_wiki wiki; private Hdump_page_itm page;
	private Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_._; private byte[] file_dir;
	public void Init_by_app(Gfo_usr_dlg usr_dlg, byte [] file_dir) {this.usr_dlg = usr_dlg; this.file_dir = file_dir;}
	public void Init_by_page(Xow_wiki wiki, Hdump_page_itm page) {this.wiki = wiki; this.page = page;}
	public void XferAry(Bry_bfr bfr, int idx) {
		byte[] src = page.Page_body(); int len = src.length;
		Hdump_img_itm[] imgs = page.Img_itms(); int imgs_len = page.Img_itms().length;
		bry_rdr.Src_(src);
		int pos = 0; int rng_bgn = -1;
		Xow_html_mgr html_mgr = wiki.Html_mgr();
		Xoh_file_html_fmtr__base html_fmtr = html_mgr.Html_wtr().Lnki_wtr().File_wtr().File_wtr().Html_fmtr();
		while (pos < len) {
			byte b = src[pos];
			Object o = trie.Match_bgn_w_byte(b, src, pos, len);
			if (o == null) {
				if (rng_bgn == -1) rng_bgn = pos;
				++pos;
			}
			else {
				if (rng_bgn != -1) {
					bfr.Add_mid(src, rng_bgn, pos);
					rng_bgn = -1;
				}
				pos = trie.Match_pos();	// position after match; EX: "xowa_img='" positions after "'"
				Hdump_html_fmtr_itm itm = (Hdump_html_fmtr_itm)o;
				pos = Write_img(bfr, html_mgr, html_fmtr, page, src, imgs, imgs_len, pos, itm); // note no +1; Write_img return pos after }
			}
		}
		if (rng_bgn != -1) bfr.Add_mid(src, rng_bgn, len);
	}
	private int Write_img(Bry_bfr bfr, Xow_html_mgr html_mgr, Xoh_file_html_fmtr__base fmtr, Hdump_page_itm page, byte[] src, Hdump_img_itm[] imgs, int imgs_len, int uid_bgn, Hdump_html_fmtr_itm itm) {
		bry_rdr.Pos_(uid_bgn);
		int uid = bry_rdr.Read_int_to(Byte_ascii.Apos);
		int uid_end = bry_rdr.Pos();			// set uid_end after "'"
		int rv = uid_end;
		if (itm.Elem_is_xnde()) rv += 2;		// if xnde, skip "/>"
		if (uid == bry_rdr.Or_int())			{usr_dlg.Warn_many("", "", "index is not a valid int; page=~{0} text=~{1}", page.Page_url().Xto_full_str_safe(), Bry_.Mid_safe(src, uid_bgn, uid_end)); return uid_end;}
		if (!Int_.Between(uid, 0, imgs_len))	{usr_dlg.Warn_many("", "", "index is out of range; page=~{0} idx=~{1} len=~{2}", page.Page_url().Xto_full_str_safe(), uid, imgs_len); return uid_end;}
		Hdump_img_itm img = imgs[uid];
		int img_view_w = img.View_w();
		byte tid = itm.Tid();
		if (tid == Tid_img_style) {
			bfr.Add(Bry_img_style_bgn);
			bfr.Add_int_variable(img_view_w);
			bfr.Add(Bry_img_style_end);
			return rv;
		}
		byte[] a_title = img.Lnki_ttl();
		byte[] a_href = Bry_.Add(A_href_bgn, a_title);
		switch (tid) {
			case Tid_file_info: fmtr.Html_thumb_part_info	(bfr, uid, a_href, html_mgr.Img_media_info_btn()); return rv;
			case Tid_file_mgnf: fmtr.Html_thumb_part_magnify(bfr, uid, a_href, a_title, html_mgr.Img_thumb_magnify()); return rv;
			case Tid_file_play: fmtr.Html_thumb_part_play	(bfr, uid, img_view_w, Xoh_file_wtr__basic.Play_btn_max_width, a_href, a_title, html_mgr.Img_media_play_btn()); return rv;
		}
		byte[] img_src = Bry_.Add(file_dir, img.View_src()); 
		if (tid == Tid_img) {
			fmtr_img.Bld_bfr_many(bfr, img_src, img_view_w, img.View_h());				
		}
		return rv;
	}
	private static final Bry_fmtr fmtr_img = Bry_fmtr.new_("src='~{src}' width='~{w}' height='~{h}'", "src", "w", "h");
	private static final byte[] A_href_bgn = Bry_.new_ascii_("/wiki/File:"), Bry_img_style_bgn = Bry_.new_ascii_("style='width:"), Bry_img_style_end = Bry_.new_ascii_("px;'");
	public static final byte[]
	  Key_img			= Bry_.new_ascii_("xowa_img='")
	, Key_img_style		= Bry_.new_ascii_("xowa_img_style='")
	, Key_file_play		= Bry_.new_ascii_("<xowa_play id='")
	, Key_file_info		= Bry_.new_ascii_("<xowa_info id='")
	, Key_file_mgnf		= Bry_.new_ascii_("<xowa_mgnf id='")
	;
	private static final byte Tid_img = 1, Tid_img_style = 2, Tid_file_play = 3, Tid_file_info = 4, Tid_file_mgnf = 5;
	private static final Btrie_slim_mgr trie = trie_();
	private static Btrie_slim_mgr trie_() {
		Btrie_slim_mgr rv = Btrie_slim_mgr.cs_();
		trie_itm(rv, Tid_img		, "xowa_img='");
		trie_itm(rv, Tid_img_style	, "xowa_img_style='");
		trie_itm(rv, Tid_file_play	, "<xowa_play id='");
		trie_itm(rv, Tid_file_info	, "<xowa_info id='");
		trie_itm(rv, Tid_file_mgnf	, "<xowa_mgnf id='");
		return rv;
	}
	private static void trie_itm(Btrie_slim_mgr trie, byte tid, String key_str) {
		byte[] key_bry = Bry_.new_utf8_(key_str);
		boolean elem_is_xnde = key_bry[0] == Byte_ascii.Lt;
		Hdump_html_fmtr_itm itm = new Hdump_html_fmtr_itm(tid, key_bry, elem_is_xnde);
		trie.Add_obj(key_bry, itm);
	}
}
class Hdump_html_fmtr_itm {
	public Hdump_html_fmtr_itm(byte tid, byte[] key, boolean elem_is_xnde) {this.tid = tid; this.key = key; this.elem_is_xnde = elem_is_xnde;}
	public byte Tid() {return tid;} private byte tid;
	public byte[] Key() {return key;} private byte[] key;
	public boolean Elem_is_xnde() {return elem_is_xnde;} private boolean elem_is_xnde;
}
