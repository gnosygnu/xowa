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
package gplx.xowa.addons.bldrs.infos; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*;
import org.junit.*; import gplx.core.tests.*; import gplx.xowa.wikis.domains.*;
public class Xobc_info_html__tst {
	private final    Xobc_info_html__fxt fxt = new Xobc_info_html__fxt();
	@Test 	public void Torrent__en_w()		{fxt.Test__torrent_link("en.wikipedia.org"		, "https://archive.org/download/Xowa_enwiki_latest_archive.torrent");}
	@Test 	public void Torrent__fr_d()		{fxt.Test__torrent_link("fr.wiktionary.org"		, "https://archive.org/download/Xowa_frwiki_latest_archive.torrent");}
}
class Xobc_info_html__fxt {
	public void Test__torrent_link(String domain_str, String expd) {
		Gftest.Eq__str(expd, Xobc_info_html.Make_torrent_fil("https://archive.org/download/", Xow_domain_itm_.parse(Bry_.new_u8(domain_str))));
	}
}