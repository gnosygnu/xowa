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
package gplx.xowa.bldrs.cmds.texts.xmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.texts.*;
import gplx.core.ios.*; import gplx.core.ios.streams.*; import gplx.langs.xmls.*; // NOTE: gplx.langs.xmls does not support Android; DATE:2013-01-17
import gplx.xowa.wikis.nss.*;
public class Xob_siteinfo_parser_ {
	public static byte[] Extract(Io_stream_rdr src_rdr) {
		Io_buffer_rdr rdr = Io_buffer_rdr.Null;
		try {
			rdr = Io_buffer_rdr.new_(src_rdr, Io_mgr.Len_mb);	// ASSUME: siteInfo is fully contained in the 1st MB of the src_xml
			byte[] src = rdr.Bfr();
			int bgn = Bry_find_.Find_fwd(src, Bry_siteinfo_bgn, 0)  ; if (bgn == Bry_find_.Not_found) throw Err_.new_("Xob_siteinfo_parser_", "could not find <siteinfo>", "src", src);
			int end = Bry_find_.Move_fwd(src, Bry_siteinfo_end, bgn); if (end == Bry_find_.Not_found) throw Err_.new_("Xob_siteinfo_parser_", "could not find </siteinfo>", "src", src);
			return Bry_.Mid(src, bgn, end);
		}
		finally {rdr.Rls();}
	}
	public static void Parse(byte[] siteinfo_bry, Xowe_wiki wiki) {
		Xob_siteinfo_nde nde = Parse(String_.new_u8(siteinfo_bry), wiki.Ns_mgr());
		wiki.Props().Bldr_version_(Bry_.new_a7(Xoa_app_.Version));
		wiki.Props().Main_page_(nde.Main_page());
		wiki.Props().Siteinfo_mainpage_(nde.Main_page());
		Bry_bfr bfr = Bry_bfr_.New().Add_str_u8(nde.Site_name()).Add_byte_pipe().Add_str_u8(nde.Generator()).Add_byte_pipe().Add_str_u8(nde.Case_dflt()).Add_byte_pipe();
		wiki.Props().Siteinfo_misc_(bfr.To_bry_and_clear());
	}
	public static Xob_siteinfo_nde Parse(String xdoc_src, Xow_ns_mgr ns_mgr) {
		XmlDoc xdoc = XmlDoc_.parse(xdoc_src); XmlNde root = xdoc.Root();
		String site_name = "", db_name = "", generator = "", case_dflt = Xow_ns_case_.Key__1st; byte[] main_page = Xoa_page_.Main_page_bry;
		int root_len = root.SubNdes().Count();
		for (int i = 0; i < root_len; ++i) {
			XmlNde sub_nde = root.SubNdes().Get_at(i); String sub_name = sub_nde.Name();
			if		(String_.Eq(sub_name, "sitename"))		site_name = sub_nde.Text_inner();
			else if	(String_.Eq(sub_name, "generator"))		generator = sub_nde.Text_inner();
			else if	(String_.Eq(sub_name, "case"))			case_dflt = sub_nde.Text_inner();
			else if	(String_.Eq(sub_name, "dbname"))		db_name = sub_nde.Text_inner();
			else if	(String_.Eq(sub_name, "base"))		main_page = Parse_base(Bry_.new_u8(sub_nde.Text_inner()));
			else if (String_.Eq(sub_name, "namespaces"))	Parse_namespaces(sub_nde, ns_mgr, case_dflt);
			else if (String_.Eq(sub_name, "#text"))			{} // JAVA.XML.#text: ignore unexpected #text nodes
		}
		return new Xob_siteinfo_nde(site_name, db_name, main_page, generator, case_dflt, ns_mgr);
	}
	private static byte[] Parse_base(byte[] url) {
		int page_bgn = Bry_find_.Find_fwd(url, gplx.xowa.htmls.hrefs.Xoh_href_.Bry__wiki, 0);
		if (page_bgn == Bry_find_.Not_found) {							// "/wiki/" not found; EX: "http://mywiki/My_main_page"
			page_bgn = Bry_find_.Find_bwd(url, Byte_ascii.Slash);		// ASSUME last segment is page
			if (page_bgn == Bry_find_.Not_found) throw Err_.new_("Xob_siteinfo_parser_", "could not parse main page url", "url", url);
			++page_bgn;													// add 1 to position after slash
		}
		else															// "/wiki/" found
			page_bgn += gplx.xowa.htmls.hrefs.Xoh_href_.Len__wiki;		// position bgn after "/wiki/"
		return Bry_.Mid(url, page_bgn, url.length);						// extract everything after "page_bgn"; EX: "http://en.wikipedia.org/wiki/Main_Page" -> "Main_Page"
	}
	private static void Parse_namespaces(XmlNde grp_nde, Xow_ns_mgr ns_mgr, String case_dflt) {
		ns_mgr.Clear();	// NOTE: wipe out any preexisting ns; use siteinfo.xml as definitive list
		int grp_len = grp_nde.SubNdes().Count();
		for (int i = 0; i < grp_len; ++i) {
			XmlNde itm_nde = grp_nde.SubNdes().Get_at(i); if (itm_nde.Atrs().Count() == 0) continue; // JAVA.XML.#text: ignore unexpected #text nodes
			String ns_id		= itm_nde.Atrs().FetchValOr("key", null); if (ns_id == null) throw Err_.new_("Xob_siteinfo_parser_", "missing key for ns", "ns_xml", itm_nde.Text_inner());
			String case_match	= itm_nde.Atrs().FetchValOr("case", case_dflt);	// NOTE: some dumps can omit "case"; EX: https://dumps.wikimedia.org/sep11wiki; DATE:2015-11-01
			String name			= itm_nde.Text_inner();
			ns_mgr.Add_new(Int_.parse(ns_id), Bry_.new_u8(name), Xow_ns_case_.To_tid(case_match), false);
		}
		ns_mgr.Init_w_defaults();
	}
	private static final    byte[] Bry_siteinfo_bgn = Bry_.new_a7("<siteinfo>"), Bry_siteinfo_end = Bry_.new_a7("</siteinfo>");
}
