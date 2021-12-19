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
package gplx.xowa.wikis.xwikis.sitelinks.htmls;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.basics.utls.StringUtl;
class Xoa_sitelink_itm_wtr__badge {
	public static byte[] Bld_badge_class(BryWtr bfr, byte[][] badge_ary) {	// EX: "Q17437798", "Q17437796" -> "class='badge-goodarticle,badge-featuredarticle'"
		if (badge_ary == null) badge_ary = BryUtl.AryEmpty;
		bfr.Add(Cls_bgn);
		int badges_len = badge_ary.length;
		if (badges_len == 0)
			bfr.Add(Badge_none_cls);
		else {
			for (int i = 0; i < badges_len; ++i) {
				if (i != 0) bfr.AddByteComma();
				byte[] badge_itm = badge_ary[i];
				byte[] badge_cls = (byte[])badges_hash.Get_by_bry(badge_itm);
				if (badge_cls == null)
					Gfo_usr_dlg_.Instance.Warn_many("", "", "unknown badge: badge=~{0}", StringUtl.NewU8(badge_itm));
				else
					bfr.Add(badge_cls);
			}
		}
		bfr.AddByteApos();
		return bfr.ToBryAndClear();
	}
	private static final byte[]
	  Badge_none_cls	= BryUtl.NewA7("badge-none")
	, Cls_bgn			= BryUtl.NewA7(" class='")
	;
	private static final Hash_adp_bry badges_hash = Hash_adp_bry.ci_a7()
	.Add_str_obj("Q17437798", BryUtl.NewA7("badge-goodarticle"))
	.Add_str_obj("Q17437796", BryUtl.NewA7("badge-featuredarticle"))
	.Add_str_obj("Q17559452", BryUtl.NewA7("badge-recommendedarticle"))
	.Add_str_obj("Q17506997", BryUtl.NewA7("badge-featuredlist"))
	.Add_str_obj("Q17580674", BryUtl.NewA7("badge-featuredportal"))
	;
}
