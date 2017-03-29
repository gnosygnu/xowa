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
package gplx.xowa.bldrs.setups.maints; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.setups.*;
import org.junit.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.domains.*;
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
//		@Test  public void Update() {	// MAINT:QUARTERLY:2017-03-28; COUNT=830; must run home/wiki/Dashboard/Wiki_maintenance and click "update dump status"
//			Hash_adp_bry excluded_domains = Hash_adp_bry.cs().Add_many_str
//			( "advisory.wikipedia.org", "beta.wikiversity.org", "donate.wikipedia.org", "login.wikipedia.org"
//			, "nostalgia.wikipedia.org", "outreach.wikipedia.org", "quality.wikipedia.org", "sources.wikipedia.org"
//			, "strategy.wikipedia.org", "ten.wikipedia.org", "test2.wikipedia.org", "test.wikipedia.org"
//			, "usability.wikipedia.org", "vote.wikipedia.org"
//			, "bd.wikimedia.org", "dk.wikimedia.org", "mx.wikimedia.org", "nyc.wikimedia.org", "nz.wikimedia.org", "pa-us.wikimedia.org", "rs.wikimedia.org", "ua.wikimedia.org"
//			);
//			Wmf_dump_itm[] itms = new Wmf_dump_list_parser().Parse(Io_mgr.Instance.LoadFilBry("C:\\xowa\\bin\\any\\xowa\\xtns\\xowa\\maintenance\\backup-index.html"));
//			Array_.Sort(itms);
//			Bry_bfr sql_bfr = Bry_bfr_.New();
//			Bry_bfr bld_bfr = Bry_bfr_.New();
//			int itms_len = itms.length;
//			int counter = 1;
//			for (int i = 0; i < itms_len; i++) {
//				Wmf_dump_itm itm = itms[i];
//				byte[] abrv = itm.Wiki_abrv();
//				if (Bry_.Eq(abrv, Bry_.new_a7("testwikidatawiki"))) continue;
//				byte[] domain_bry = Xow_abrv_wm_.Parse_to_domain_bry(abrv);
//				if (domain_bry == null) continue;			// not a standard WMF wiki; ignore
//				if (Bry_find_.Find_fwd(domain_bry, Bry_.new_a7("wikimania")) != Bry_find_.Not_found) continue;
//				if (excluded_domains.Has(domain_bry)) continue;
//				Xow_domain_itm domain_itm = Xow_domain_itm_.parse(domain_bry);
//				byte[] tid_name = Xto_display_name(Xow_domain_tid_.Get_type_as_bry(domain_itm.Domain_type_id()));
//				sql_bfr
//					.Add_byte(Byte_ascii.Paren_bgn)
//					.Add_int_variable(counter++)
//					.Add_byte(Byte_ascii.Comma)
//					.Add_int_variable(1)
//					.Add_byte(Byte_ascii.Comma)
//					.Add_byte(Byte_ascii.Apos)
//					.Add(domain_itm.Lang_orig_key())
//					.Add_byte(Byte_ascii.Apos)
//					.Add_byte(Byte_ascii.Comma)
//					.Add_byte(Byte_ascii.Apos)
//					.Add(tid_name)
//					.Add_byte(Byte_ascii.Apos)
//					.Add_byte(Byte_ascii.Paren_end)
//					.Add_byte(Byte_ascii.Comma)
//					.Add_str_u8("--" + String_.new_u8(abrv))
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
//			Io_url temp = Io_url_.new_fil_("C:\\xowa\\user\\import_update.txt");
//			Io_mgr.Instance.SaveFilBfr(temp, sql_bfr);
////			Io_mgr.Instance.AppendFilBfr(temp, bld_bfr);
//		}
//		private static byte[] Xto_display_name(byte[] v) {
//			if		(Bry_.Eq(v, Xow_domain_tid_.Bry__wmforg))				return Bry_.new_a7("Wikimedia Foundation");
//			else if	(Bry_.Eq(v, Xow_domain_tid_.Bry__species))				return Bry_.new_a7("Wikispecies");
//			else if	(Bry_.Eq(v, Xow_domain_tid_.Bry__mediawiki))			return Bry_.new_a7("MediaWiki");
//			else															return Bry_.Add(Byte_ascii.Case_upper(v[0]), Bry_.Mid(v, 1, v.length));
//		}
}
class Wmf_dump_list_parser_fxt {
	public void Clear() {}
	private Wmf_dump_list_parser parser = new Wmf_dump_list_parser();
	public String itm(String wiki_abrv, String dump_date, byte status_done, String status_msg, String status_time) {
		return String_.Concat_with_str("\n", wiki_abrv, dump_date
		, Byte_.To_str(status_done)
		, status_msg
		, status_time
		);
	}
	public void Test_parse(String raw, String... expd) {
		Wmf_dump_itm[] actl = parser.Parse(Bry_.new_a7(raw));
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
		return String_.Concat_with_str("\n", String_.new_a7(itm.Wiki_abrv()), itm.Dump_date().XtoStr_fmt("yyyyMMdd")
			, Byte_.To_str(itm.Status_tid())
			, String_.new_a7(itm.Status_msg())
			, status_time_str
			);
		
	}
}
