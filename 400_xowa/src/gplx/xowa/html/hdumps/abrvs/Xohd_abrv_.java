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
package gplx.xowa.html.hdumps.abrvs; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.hdumps.*;
import gplx.core.btries.*;
public class Xohd_abrv_ {
	public static final byte
	  Tid_dir = 1, Tid_img = 2, Tid_img_style = 3, Tid_file_play = 4, Tid_file_info = 5, Tid_file_mgnf = 6
	, Tid_hiero_dir = 7, Tid_gallery_box_max = 8, Tid_gallery_box_w = 9, Tid_gallery_img_w = 10, Tid_gallery_img_pad = 11
	, Tid_redlink = 12, Tid_toc = 13
	;
	public static final byte[]
	  Key_dir					= Bry_.new_a7("~{xowa_dir}")
	, Key_img					= Bry_.new_a7("xowa_img='")
	, Key_img_style				= Bry_.new_a7("xowa_img_style='")
	, Key_file_play				= Bry_.new_a7("<xowa_play id='")
	, Key_file_info				= Bry_.new_a7("<xowa_info id='")
	, Key_file_mgnf				= Bry_.new_a7("<xowa_mgnf id='")
	, Key_hiero_dir				= Bry_.new_a7("~{xowa_hiero_dir}")
	, Key_gallery_box_max		= Bry_.new_a7("xowa_gly_box_max='")
	, Key_gallery_box_w			= Bry_.new_a7("xowa_gly_box_w='")
	, Key_gallery_img_w			= Bry_.new_a7("xowa_gly_img_w='")
	, Key_gallery_img_pad		= Bry_.new_a7("xowa_gly_img_pad='")
	, Key_redlink				= Bry_.new_a7("xowa_redlink='")
	, Key_toc					= Bry_.new_a7("~{xowa_toc}")
	;
	public static final byte[]
	  A_href_bgn				= Bry_.new_a7("/wiki/File:")
	, Bry_img_style_bgn			= Bry_.new_a7("style='width:")
	, Bry_img_style_end			= Bry_.new_a7("px;'")
	, Html_redlink_bgn			= Bry_.Add(Bry_.new_a7("\" "), Key_redlink)
	, Html_redlink_end			= Bry_.new_a7("'>")
	;
	public static Btrie_slim_mgr new_trie() {
		Btrie_slim_mgr rv = Btrie_slim_mgr.cs_();
		trie_itm(rv, Tid_dir				, Byte_ascii.Nil		, Key_dir);
		trie_itm(rv, Tid_img				, Byte_ascii.Apos		, Key_img);
		trie_itm(rv, Tid_img_style			, Byte_ascii.Apos		, Key_img_style);
		trie_itm(rv, Tid_file_play			, Byte_ascii.Apos		, Key_file_play);
		trie_itm(rv, Tid_file_info			, Byte_ascii.Apos		, Key_file_info);
		trie_itm(rv, Tid_file_mgnf			, Byte_ascii.Apos		, Key_file_mgnf);
		trie_itm(rv, Tid_hiero_dir			, Byte_ascii.Nil		, Key_hiero_dir);
		trie_itm(rv, Tid_gallery_box_max	, Byte_ascii.Apos		, Key_gallery_box_max);
		trie_itm(rv, Tid_gallery_box_w		, Byte_ascii.Apos		, Key_gallery_box_w);
		trie_itm(rv, Tid_gallery_img_w		, Byte_ascii.Apos		, Key_gallery_img_w);
		trie_itm(rv, Tid_gallery_img_pad	, Byte_ascii.Apos		, Key_gallery_img_pad);
		trie_itm(rv, Tid_redlink			, Byte_ascii.Apos		, Key_redlink);
		trie_itm(rv, Tid_toc				, Byte_ascii.Nil		, Key_toc);
		return rv;
	}
	private static void trie_itm(Btrie_slim_mgr trie, byte tid, byte subst_end_byte, byte[] key_bry) {
		boolean elem_is_xnde = key_bry[0] == Byte_ascii.Lt;
		Hdump_html_fmtr_itm itm = new Hdump_html_fmtr_itm(tid, elem_is_xnde, subst_end_byte, key_bry);
		trie.Add_obj(key_bry, itm);
	}
}
