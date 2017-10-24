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
package gplx.xowa; import gplx.*;
public class Xoa_url_ {
	public static final int Tid_unknown = 0, Tid_page = 1, Tid_anch = 2, Tid_inet = 3, Tid_file = 4, Tid_xcmd = 5;
	public static boolean Tid_is_pagelike(int tid) {
		switch (tid) {
			case Tid_page: case Tid_anch: return true;
			default: return false;
		}
	}
	public static void Invalid_warn(String url) {Xoa_app_.Usr_dlg().Plog_many("", "", "invalid url; url=~{0}", url);}
	public static String Main_page__home_str = gplx.xowa.wikis.domains.Xow_domain_itm_.Str__home + gplx.xowa.htmls.hrefs.Xoh_href_.Str__wiki + gplx.xowa.Xoa_page_.Main_page_str;	// EX:home/wiki/Main_Page
	public static final    byte[]
	  Qarg__redirect            = Bry_.new_a7("redirect")
	, Qarg__redirect__no        = Bry_.new_a7("no")
	, Qarg__action              = Bry_.new_a7("action")
	, Qarg__action__edit        = Bry_.new_a7("edit")
	, Qarg__curid               = Bry_.new_a7("curid")
	;
}
