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
package gplx.xowa.htmls.doms;
import gplx.langs.htmls.*;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
public class Xoh_dom_ {
	private static final byte[] Lt_bry = BryUtl.NewA7("<"), Space_bry = BryUtl.NewA7(" ");
	public static byte[] Query_val_by_where(Xoh_find rv, byte[] src, byte[] where_nde, byte[] where_key, byte[] where_val, byte[] query_key, int bgn) {
		int src_len = src.length;		
		where_nde = BryUtl.Add(Lt_bry, where_nde, Space_bry);
		while (true) {
			boolean where_val_found = Select_tag(rv, src, where_nde, where_key, bgn, src_len);
			if (where_val_found) {
				int tag_bgn = rv.Tag_bgn();
				int tag_end = rv.Tag_end();
				boolean where_val_match = BryLni.Eq(src, rv.Val_bgn(), rv.Val_end(), where_val);
				if (where_val_match) {
					boolean query_val_found = Find_atr_val_in_tag(rv, src, query_key, tag_bgn, tag_end);
					if (query_val_found) {
						return BryLni.Mid(src, rv.Val_bgn(), rv.Val_end());
					}
					else
						return null;
				}
				else
					bgn = tag_end + 1;
			}
			else
				break;
		}
		return null;
	}
	public static boolean Select_tag(Xoh_find rv, byte[] src, byte[] nde, byte[] key, int rng_bgn, int rng_end) {
		int tag_bgn = BryFind.FindFwd(src, nde, 		   rng_bgn, rng_end); 					if (tag_bgn == BryFind.NotFound) return false;
		int tag_end = BryFind.FindFwd(src, AsciiByte.Gt, tag_bgn, rng_end); 					if (tag_end == BryFind.NotFound) return false;
		int key_bgn = BryFind.FindFwd(src, key, tag_bgn, tag_end);								if (key_bgn == BryFind.NotFound) return false;
		int key_end = key_bgn + key.length;
		int val_bgn = BryFind.FindFwd(src, AsciiByte.Quote, key_end, tag_end);					if (val_bgn == BryFind.NotFound) return false;
		++val_bgn;
		int val_end = BryFind.FindFwd(src, AsciiByte.Quote, val_bgn, tag_end);					if (val_end == BryFind.NotFound) return false;
		rv.Set_all(tag_bgn, tag_end, key_bgn, key_end, val_bgn, val_end);
		return true;
	}
	public static boolean Find_atr_val_in_tag(Xoh_find rv, byte[] src, byte[] key, int tag_bgn, int tag_end) {
		int key_bgn = BryFind.FindFwd(src, key, tag_bgn, tag_end);								if (key_bgn == BryFind.NotFound) return false;
		int key_end = key_bgn + key.length;
		int val_bgn = BryFind.FindFwd(src, AsciiByte.Quote, key_end, tag_end);					if (val_bgn == BryFind.NotFound) return false;
		++val_bgn;
		int val_end = BryFind.FindFwd(src, AsciiByte.Quote, val_bgn, tag_end);					if (val_end == BryFind.NotFound) return false;
		rv.Set_all(tag_bgn, tag_end, key_bgn, key_end, val_bgn, val_end);
		return true;
	}
	public static String Title_by_href(byte[] href, byte[] html_src) {
		byte[] xowa_title = Xoh_dom_.Query_val_by_where(dom_find, html_src, Gfh_tag_.Bry__a, Gfh_atr_.Bry__href, href, gplx.xowa.htmls.Xoh_consts.Atr_xowa_title_bry, 0);
		return StringUtl.NewU8(xowa_title);
	}	private static final Xoh_find dom_find = new Xoh_find();
}
