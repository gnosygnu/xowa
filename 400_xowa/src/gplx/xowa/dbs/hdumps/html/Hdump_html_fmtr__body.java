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
package gplx.xowa.dbs.hdumps.html; import gplx.*; import gplx.xowa.*; import gplx.xowa.dbs.*; import gplx.xowa.dbs.hdumps.*;
import gplx.core.btries.*;
class Hdump_html_fmtr__body implements Bry_fmtr_arg {
	private Bry_rdr bry_rdr = new Bry_rdr();
	private Hdump_page_itm page;
	private Gfo_usr_dlg usr_dlg; private byte[] app_dir = Bry_.Empty;
	public void Init_by_app(Gfo_usr_dlg usr_dlg, byte[] app_dir)		{this.usr_dlg = usr_dlg; this.app_dir = app_dir;}
	public void Init_by_page(Hdump_page_itm page)						{this.page = page;}
	public void XferAry(Bry_bfr bfr, int idx) {
		byte[] src = page.Page_body(); int len = src.length;
		Hdump_img_itm[] imgs = page.Img_itms(); int imgs_len = page.Img_itms().length;
		bry_rdr.Src_(src);
		int pos = 0; int rng_bgn = -1;			
		while (pos < len) {
			byte b = src[pos];
			Object o = trie.Match_bgn_w_byte(b, src, pos, len);
			if (o == null) {
				if (rng_bgn == -1) rng_bgn = pos;
			}
			else {
				if (rng_bgn != -1) {
					bfr.Add_mid(src, rng_bgn, pos);
					rng_bgn = -1;
				}
				byte tid = ((Byte_obj_val)o).Val();
				pos = trie.Match_pos();	// position after match; EX: "~{xo.img." positions after "."
				switch (tid) {
					case Tid_app_dir:	bfr.Add(app_dir); break;
					case Tid_img:		pos = Write_img(bfr, page, src, imgs, imgs_len, pos); break;
				}
				++pos;	// + 1 to skip trailing }
			}
		}
		if (rng_bgn != -1) bfr.Add_mid(src, rng_bgn, len);
	}
	private int Write_img(Bry_bfr bfr, Hdump_page_itm page, byte[] src, Hdump_img_itm[] imgs, int imgs_len, int idx_bgn) {
		int idx_val = bry_rdr.Read_int_to(Byte_ascii.Curly_end);
		int idx_end = bry_rdr.Pos(); 
		if (idx_val == bry_rdr.Or_int())			{usr_dlg.Warn_many("", "", "index is not a valid int; page=~{0} text=~{1}", page.Page_url(), Bry_.Mid(src, idx_bgn, idx_end)); return idx_end;}
		if (!Int_.Between(idx_val, 0, imgs_len))	{usr_dlg.Warn_many("", "", "index is out of range; page=~{0} idx=~{1} len=~{2}", page.Page_url(), idx_val, imgs_len); return idx_end;}
		imgs[idx_val].Write_html(bfr);
		return idx_end;
	}
	private static final byte Tid_app_dir = 0, Tid_img = 1;
	private Btrie_slim_mgr trie = Btrie_slim_mgr.cs_()
	.Add_str_byte("~{xo.dir"		, Tid_app_dir)
	.Add_str_byte("~{xo.img."		, Tid_img)
	;
}
