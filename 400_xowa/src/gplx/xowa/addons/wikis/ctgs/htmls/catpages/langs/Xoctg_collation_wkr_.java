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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.langs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*;
class Xoctg_collation_wkr_ {
	public static Xoctg_collation_wkr Make(Xow_wiki wiki, String wm_name) {
		// REF:"includes/collation/Collation.php|factory"			
		if		(String_.Eq(wm_name, "uppercase"))		return new Xoctg_collation_wkr__uppercase(wiki);
		else if	(String_.Eq(wm_name, "identity"))		return new Xoctg_collation_wkr__identity();
		else if	(String_.Eq(wm_name, "uca-default"))	return new Xoctg_collation_wkr__uca(wm_name, "root");
		else if	(String_.Eq(wm_name, "xx-uca-ckb"))		return new Xoctg_collation_wkr__uca(wm_name, "fa"); // FUTURE:should create custom collation class to do extra logic
		else if	(String_.Eq(wm_name, "xx-uca-et"))		return new Xoctg_collation_wkr__uca(wm_name, "et"); // FUTURE:should create custom collation class to do extra logic
		else {
			if (String_.Has_at_bgn(wm_name, "uca-"))
				return new Xoctg_collation_wkr__uca(wm_name, String_.Replace(wm_name, "uca-", ""));
			else {	// unknown collation code; log and exit
				Gfo_usr_dlg_.Instance.Warn_many("", "", "unknown collation; collation=~{0}", wm_name);
				return new Xoctg_collation_wkr__uppercase(wiki);
			}
		}
	}
}
