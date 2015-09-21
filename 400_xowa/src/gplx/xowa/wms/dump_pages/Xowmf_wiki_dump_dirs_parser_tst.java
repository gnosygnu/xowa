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
package gplx.xowa.wms.dump_pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.wms.*;
import org.junit.*;
import gplx.xowa.wikis.domains.*;
public class Xowmf_wiki_dump_dirs_parser_tst {
	@Before public void init() {fxt.Clear();} private final Xowmf_wiki_dump_dirs_parser_fxt fxt = new Xowmf_wiki_dump_dirs_parser_fxt();
	@Test  public void Basic() {
		fxt.Test_parse("<a href=\"20141230/\">20141230/</a><a href=\"20150118/\">20150118/</a>", "20141230", "20150118");
	}
	@Test  public void Example() {	// http://dumps.wikimedia.org/jawiki/
		fxt.Test_parse(String_.Concat_lines_nl_skip_last
		( "<html>"
		, "<head><title>Index of /jawiki/</title></head>"
		, "<body bgcolor=\"white\">"
		, "<h1>Index of /jawiki/</h1><hr><pre><a href=\"../\">../</a>"
		, "<a href=\"20141122/\">20141122/</a>                                          25-Nov-2014 22:04                   -"
		, "<a href=\"20141211/\">20141211/</a>                                          14-Dec-2014 09:25                   -"
		, "<a href=\"20141230/\">20141230/</a>                                          02-Jan-2015 09:02                   -"
		, "<a href=\"20150118/\">20150118/</a>                                          21-Jan-2015 04:39                   -"
		, "<a href=\"20150221/\">20150221/</a>                                          24-Feb-2015 17:51                   -"
		, "<a href=\"20150313/\">20150313/</a>                                          16-Mar-2015 14:37                   -"
		, "<a href=\"20150402/\">20150402/</a>                                          05-Apr-2015 06:19                   -"
		, "<a href=\"20150422/\">20150422/</a>                                          25-Apr-2015 13:52                   -"
		, "<a href=\"20150512/\">20150512/</a>                                          15-May-2015 08:17                   -"
		, "<a href=\"20150602/\">20150602/</a>                                          16-Jun-2015 01:34                   -"
		, "<a href=\"20150703/\">20150703/</a>                                          08-Jul-2015 14:44                   -"
		, "<a href=\"latest/\">latest/</a>                                            08-Jul-2015 14:44                   -"
		, "</pre><hr></body>"
		, "</html>"
		)
		, "20141122"
		, "20141211"
		, "20141230"
		, "20150118"
		, "20150221"
		, "20150313"
		, "20150402"
		, "20150422"
		, "20150512"
		, "20150602"
		, "20150703"
		, "latest"
		);
	}
}
class Xowmf_wiki_dump_dirs_parser_fxt {
	public void Clear() {}
	public void Test_parse(String src, String... expd_dates) {
		String[] actl_dates = Xowmf_wiki_dump_dirs_parser.Parse(Xow_domain_itm_.Bry__enwiki, Bry_.new_u8(src));
		Tfds.Eq_ary_str(expd_dates, actl_dates);
	}
}
