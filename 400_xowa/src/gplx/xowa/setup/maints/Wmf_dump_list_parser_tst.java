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
package gplx.xowa.setup.maints; import gplx.*; import gplx.xowa.*; import gplx.xowa.setup.*;
import org.junit.*;
import gplx.xowa.wikis.*;
public class Wmf_dump_list_parser_tst {
	@Before public void init() {fxt.Clear();} private Wmf_dump_list_parser_fxt fxt = new Wmf_dump_list_parser_fxt();
	@Test  public void Parse() {
		fxt.Test_parse
		(	"<li>2013-07-17 00:32:33 <a href=\"http://dumps.wikimedia.org/enwiki/20130708\">enwiki</a>: <span class=\"done\">Dump complete</span></li>"
		,	fxt.itm("enwiki", "20130708", Wmf_dump_itm.Status_tid_complete, "Dump complete", "2013-07-17 00:32:33")
		);
		fxt.Test_parse(String_.Concat_lines_nl
		(	"<li>2013-07-24 02:02:13 <a href=\"http://dumps.wikimedia.org/kawiki/20130724\">kawiki</a>: <span class=\"in-progress\">Dump in progress</span></li>"
		,	"<ul><li class=\"in-progress\"><span class=\"updates\">2013-07-24 00:54:55</span> <span class=\"status\">in-progress</span> <span class=\"title\">All pages with complete page edit history (.bz2)</span><div class=\"progress\">2013-07-24 02:02:13: kawiki (ID 18587) 22046 pages (5.5|11140.9/sec all|curr), 869000 revs (215.2|505.3/sec all|curr), 99.9%|99.9% prefetched (all|curr), ETA 2013-07-24 04:09:41 [max 2514872]</div>"
		,	"<ul><li class=\"file\">kawiki-20130724-pages-meta-history.xml.bz2 245.2 MB (written) </li></ul></li></ul>"
		)
		,	fxt.itm("kawiki", "20130724", Wmf_dump_itm.Status_tid_working, "Dump in progress", "2013-07-24 02:02:13")
		);
		fxt.Test_parse
		(	"<li>2013-07-17 00:32:33 <a href=\"http://dumps.wikimedia.org/enwiki/20130708\">enwiki</a>: <span class=\"done\">Error</span></li>"
		,	fxt.itm("enwiki", "20130708", Wmf_dump_itm.Status_tid_error, "Error", "2013-07-17 00:32:33")
		);
		fxt.Test_parse
		(	"<li>2013-11-28 06:08:56 <a href=\"zh_classicalwiki/20131128\">zh_classicalwiki</a>: <span class='done'>Dump complete</span></li>"
		,	fxt.itm("zh-classicalwiki", "20131128", Wmf_dump_itm.Status_tid_complete, "Dump complete", "2013-11-28 06:08:56")
		);
	}
//		@Test  public void Update() {	// 2014-07-06
//			Hash_adp_bry excluded_domains = Hash_adp_bry.cs_().Add_many_str
//			( "advisory.wikipedia.org", "beta.wikiversity.org", "donate.wikipedia.org", "login.wikipedia.org"
//			, "nostalgia.wikipedia.org", "outreach.wikipedia.org", "quality.wikipedia.org", "sources.wikipedia.org"
//			, "strategy.wikipedia.org", "ten.wikipedia.org", "test2.wikipedia.org", "test.wikipedia.org"
//			, "usability.wikipedia.org", "vote.wikipedia.org");
//			Wmf_dump_itm[] itms = new Wmf_dump_list_parser().Parse(Io_mgr._.LoadFilBry("C:\\xowa\\bin\\any\\html\\xowa\\maint\\backup-index.html"));
//			Array_.Sort(itms);
//			Bry_bfr sql_bfr = Bry_bfr.new_();
//			Bry_bfr bld_bfr = Bry_bfr.new_();
//			int itms_len = itms.length;
//			int counter = 1;
//			for (int i = 0; i < itms_len; i++) {
//				Wmf_dump_itm itm = itms[i];
//				byte[] abrv = itm.Wiki_abrv();
//				if (Bry_.Eq(abrv, Bry_.new_ascii_("testwikidatawiki"))) continue;
//				byte[] domain_bry = Xob_bz2_file.Parse__domain_name(abrv, 0, abrv.length);
//				if (domain_bry == null) continue;			// not a standard WMF wiki; ignore
//				if (Bry_finder.Find_fwd(domain_bry, Bry_.new_ascii_("wikimania")) != Bry_.NotFound) continue;
//				if (excluded_domains.Has(domain_bry)) continue;
//				Xow_wiki_domain domain_itm = Xow_wiki_domain_.parse_by_domain(domain_bry);
//				byte[] tid_name = Xto_display_name(Xow_wiki_domain_.Key_by_tid(domain_itm.Tid()));
//				sql_bfr
//					.Add_byte(Byte_ascii.Paren_bgn)
//					.Add_int_variable(counter++)
//					.Add_byte(Byte_ascii.Comma)
//					.Add_int_variable(1)
//					.Add_byte(Byte_ascii.Comma)
//					.Add_byte(Byte_ascii.Apos)
//					.Add(domain_itm.Lang_orig())
//					.Add_byte(Byte_ascii.Apos)
//					.Add_byte(Byte_ascii.Comma)
//					.Add_byte(Byte_ascii.Apos)
//					.Add(tid_name)
//					.Add_byte(Byte_ascii.Apos)
//					.Add_byte(Byte_ascii.Paren_end)
//					.Add_byte(Byte_ascii.Comma)
//					.Add_str("--" + String_.new_utf8_(abrv))
//					.Add_byte_nl()
//					;
//				bld_bfr
//					.Add_byte(Byte_ascii.Comma)
//					.Add_byte(Byte_ascii.Space)
//					.Add_byte(Byte_ascii.Quote)
//					.Add(domain_bry)
//					.Add_byte(Byte_ascii.Quote)
//					.Add_byte_nl()
//					;
//			}
//			Io_url temp = Io_url_.new_fil_("C:\\import_update.txt");
//			Io_mgr._.SaveFilBfr(temp, sql_bfr);
////			Io_mgr._.AppendFilBfr(temp, bld_bfr);
//		}
//		private static byte[] Xto_display_name(byte[] v) {
//			if		(Bry_.Eq(v, Xow_wiki_domain_.Key_wikimediafoundation_bry))	return Bry_.new_ascii_("Wikimedia Foundation");
//			else if	(Bry_.Eq(v, Xow_wiki_domain_.Key_species_bry))				return Bry_.new_ascii_("Wikispecies");
//			else if	(Bry_.Eq(v, Xow_wiki_domain_.Key_mediawiki_bry))			return Bry_.new_ascii_("MediaWiki");
//			else																	return Bry_.Add(Byte_ascii.Case_upper(v[0]), Bry_.Mid(v, 1, v.length));
//		}
}
class Wmf_dump_list_parser_fxt {
	public void Clear() {}
	private Wmf_dump_list_parser parser = new Wmf_dump_list_parser();
	public String itm(String wiki_abrv, String dump_date, byte status_done, String status_msg, String status_time) {
		return String_.Concat_with_str("\n", wiki_abrv, dump_date
		, Byte_.XtoStr(status_done)
		, status_msg
		, status_time
		);
	}
	public void Test_parse(String raw, String... expd) {
		Wmf_dump_itm[] actl = parser.Parse(Bry_.new_ascii_(raw));
		Tfds.Eq_str_lines(String_.Concat_lines_nl(expd), String_.Concat_lines_nl(Xto_str(actl)));
	}
	public String[] Xto_str(Wmf_dump_itm[] ary) {
		int len = ary.length;
		String[] rv = new String[len];
		for (int i = 0; i < len; i++)
			rv[i] = Xto_str(ary[i]); 
		return rv;
	}
	public static String Xto_str(Wmf_dump_itm itm) {
		DateAdp status_time = itm.Status_time();
		String status_time_str = status_time == null ? "" : status_time.XtoStr_fmt(DateAdp_.Fmt_iso8561_date_time); 
		return String_.Concat_with_str("\n", String_.new_ascii_(itm.Wiki_abrv()), itm.Dump_date().XtoStr_fmt("yyyyMMdd")
			, Byte_.XtoStr(itm.Status_tid())
			, String_.new_ascii_(itm.Status_msg())
			, status_time_str
			);
		
	}
}
