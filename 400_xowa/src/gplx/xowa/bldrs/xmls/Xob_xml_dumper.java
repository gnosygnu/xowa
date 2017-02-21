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
package gplx.xowa.bldrs.xmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.langs.xmls.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.data.tbls.*;
public class Xob_xml_dumper {
	private final Gfo_xml_wtr wtr = new Gfo_xml_wtr();
	public String Bld_str() {return wtr.Bld_str();}
	public void Write_root_bgn(Xow_ns_mgr ns_mgr, Xow_domain_itm domain, String wiki_abrv, String main_page, String ns_case, String app_version) {
		wtr.Nde_lhs_bgn_grp("mediawiki");
		wtr.Atr_kv_str_a7("xmlns"					, "http://www.mediawiki.org/xml/export-0.10/");
		wtr.Atr_kv_str_a7("xmlns:xsi"				, "http://www.w3.org/2001/XMLSchema-instance");
		wtr.Atr_kv_str_a7("xsi:schemaLocation"		, "http://www.mediawiki.org/xml/export-0.10/ http://www.mediawiki.org/xml/export-0.10.xsd");
		wtr.Atr_kv_str_a7("version"					, "0.10");
		wtr.Atr_kv_str_a7("xml:lang"				, "en");
		wtr.Nde_lhs_end();
		Write_siteinfo(domain, wiki_abrv, main_page, ns_case, app_version);
		Write_ns_mgr(ns_mgr);
	}
	public void Write_root_end() {
		wtr.Nde_rhs();
	}
	private void Write_siteinfo(Xow_domain_itm domain, String wiki_abrv, String main_page, String ns_case, String app_version) {
		wtr.Nde_lhs("siteinfo");
		wtr.Nde_txt_bry("sitename"				, Xow_domain_tid_.Get_type_as_bry(domain.Domain_type_id()));
		wtr.Nde_txt_str("dbname"				, wiki_abrv);
		wtr.Nde_txt_str("base"				, main_page);
		wtr.Nde_txt_str("generator"				, app_version);
		wtr.Nde_txt_str("case"					, ns_case);
		wtr.Nde_rhs();
	}
	private void Write_ns_mgr(Xow_ns_mgr ns_mgr) {
		wtr.Nde_lhs("namespaces");
		int len = ns_mgr.Ords_len();
		for (int i = 0; i < len; ++i) {
			Xow_ns ns = ns_mgr.Ords_get_at(i);
			Write_ns(ns);
		}
		wtr.Nde_rhs();
	}
	private void Write_ns(Xow_ns ns) {
		wtr.Nde_lhs_bgn_itm("namespace");
		wtr.Atr_kv_int("key"					, ns.Id());
		wtr.Atr_kv_str_a7("case"				, Xow_ns_case_.To_str(ns.Case_match()));
		wtr.Nde_lhs_end();
		wtr.Txt_bry(ns.Name_db());
		wtr.Nde_rhs();
	}
	public void Write_page(Xowd_page_itm page) {
		wtr.Nde_lhs("page");
		wtr.Nde_txt_bry("title"					, page.Ttl_full_db());
		wtr.Nde_txt_int("id"					, page.Id());
		Write_revision(page);
		wtr.Nde_rhs();
	}
	private void Write_revision(Xowd_page_itm page) {
		wtr.Nde_lhs("revision");
		wtr.Nde_txt_int("id"					, -1);
		wtr.Nde_txt_int("parent"				, -1);
		wtr.Nde_txt_str("timestamp"				, page.Modified_on().XtoStr_fmt_iso_8561());
		Write_revision_contributor(page);
		wtr.Nde_txt_str("comment"				, "");
		wtr.Nde_txt_str("model"					, "wikitext");
		wtr.Nde_txt_str("format"				, "text/x-wiki");
		Write_revision_text(page);
		wtr.Nde_txt_str("sha1"					, "");
		wtr.Nde_rhs();
	}
	private void Write_revision_contributor(Xowd_page_itm page) {
		wtr.Nde_lhs("contributor");
		wtr.Nde_txt_str("username"				, "");
		wtr.Nde_txt_int("id"					, -1);
		wtr.Nde_rhs();
	}
	private void Write_revision_text(Xowd_page_itm page) {
		wtr.Nde_lhs_bgn_itm("text");
		wtr.Atr_kv_str_a7("xml:space", "preserve");
		wtr.Nde_lhs_end();
		wtr.Txt_bry(page.Text());
		wtr.Nde_rhs();
	}
}
