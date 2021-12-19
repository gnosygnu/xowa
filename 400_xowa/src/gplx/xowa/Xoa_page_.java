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
package gplx.xowa;
import gplx.libs.ios.IoConsts;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.BoolUtl;
public class Xoa_page_ {
	public static final byte Edit_mode_create = 1, Edit_mode_update = 2;
	public static final String Main_page_str = "Main_Page";
	public static final byte[] Main_page_bry = BryUtl.NewA7(Main_page_str);	// NOTE; may not work for non-english wikis
	public static final byte[] Main_page_bry_empty = BryUtl.Empty;
	public static final int Page_len_max = 2048 * IoConsts.LenKB;	// REF.MW: DefaultSettings.php; $wgMaxArticleSize = 2048;
	public static byte[] Url_bry_safe(Xoa_url url, Xow_wiki wiki, Xoa_ttl ttl) {
		byte[] rv = url == null ? BryUtl.Empty : url.To_bry(BoolUtl.Y, BoolUtl.Y);
		if (BryUtl.IsNullOrEmpty(rv))
			rv = BryUtl.Add(wiki == null ? BryUtl.Empty : wiki.Domain_bry(), gplx.xowa.htmls.hrefs.Xoh_href_.Bry__wiki, ttl == null ? BryUtl.Empty : ttl.Full_db());
		return rv;
	}
}
