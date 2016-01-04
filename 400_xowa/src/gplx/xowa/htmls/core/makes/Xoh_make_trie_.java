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
package gplx.xowa.htmls.core.makes; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.core.btries.*;
public class Xoh_make_trie_ {
	public static final byte
	  Tid__dir = 1, Tid__img = 2, Tid__img_style = 3, Tid__file_play = 4, Tid__file_info = 5, Tid__file_mgnf = 6
	, Tid__hiero_dir = 7, Tid__gallery_box_max = 8, Tid__gallery_box_w = 9, Tid__gallery_img_w = 10, Tid__gallery_img_pad = 11
	, Tid__toc = 12, Tid__hdr = 13
	;
	public static final byte[]
	  Bry__dir						= Bry_.new_a7("~{xowa_dir}")
	, Bry__img						= Bry_.new_a7("xowa_img=\"")
	, Bry__img_style				= Bry_.new_a7("xowa_img_style=\"")
	, Bry__file_play				= Bry_.new_a7("<xowa_play id='")
	, Bry__file_info				= Bry_.new_a7("<xowa_info id='")
	, Bry__file_mgnf				= Bry_.new_a7("<xowa_mgnf id=\"")
//		, Bry__hiero_dir				= Bry_.new_a7("~{xowa_hiero_dir}")
//		, Bry__gallery_box_max			= Bry_.new_a7("xowa_gly_box_max='")
//		, Bry__gallery_box_w			= Bry_.new_a7("xowa_gly_box_w='")
//		, Bry__gallery_img_w			= Bry_.new_a7("xowa_gly_img_w='")
//		, Bry__gallery_img_pad			= Bry_.new_a7("xowa_gly_img_pad='")
	, Bry__toc						= Bry_.new_a7("~{xowa_toc}")
	;
	public static final byte[]
	  A_href_bgn				= Bry_.new_a7("/wiki/File:")
	, Bry_img_style_bgn			= Bry_.new_a7("style='width:")
	, Bry_img_style_end			= Bry_.new_a7("px;'")
	;
	public static Btrie_slim_mgr new_trie() {
		Btrie_slim_mgr rv = Btrie_slim_mgr.cs();
		trie_itm(rv, Tid__dir				, Byte_ascii.Escape		, Bry__dir);
		trie_itm(rv, Tid__img				, Byte_ascii.Apos		, Bry__img);
		trie_itm(rv, Tid__img_style			, Byte_ascii.Apos		, Bry__img_style);
		trie_itm(rv, Tid__file_play			, Byte_ascii.Apos		, Bry__file_play);
		trie_itm(rv, Tid__file_info			, Byte_ascii.Apos		, Bry__file_info);
		trie_itm(rv, Tid__file_mgnf			, Byte_ascii.Apos		, Bry__file_mgnf);
//			trie_itm(rv, Tid__hiero_dir			, Byte_ascii.Escape		, Bry__hiero_dir);
//			trie_itm(rv, Tid__gallery_box_max	, Byte_ascii.Apos		, Bry__gallery_box_max);
//			trie_itm(rv, Tid__gallery_box_w		, Byte_ascii.Apos		, Bry__gallery_box_w);
//			trie_itm(rv, Tid__gallery_img_w		, Byte_ascii.Apos		, Bry__gallery_img_w);
//			trie_itm(rv, Tid__gallery_img_pad	, Byte_ascii.Apos		, Bry__gallery_img_pad);
		trie_itm(rv, Tid__toc				, Byte_ascii.Escape		, Bry__toc);
		return rv;
	}
	private static void trie_itm(Btrie_slim_mgr trie, byte tid, byte subst_end_byte, byte[] key_bry) {
		boolean elem_is_xnde = key_bry[0] == Byte_ascii.Lt;
		Xoh_make_trie_itm itm = new Xoh_make_trie_itm(tid, elem_is_xnde, subst_end_byte, key_bry);
		trie.Add_obj(key_bry, itm);
	}
}
