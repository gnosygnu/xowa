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