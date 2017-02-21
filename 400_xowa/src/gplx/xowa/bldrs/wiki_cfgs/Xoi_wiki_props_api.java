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
package gplx.xowa.bldrs.wiki_cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.langs.xmls.*; import gplx.core.ios.*;
public class Xoi_wiki_props_api {
	private IoEngine_xrg_downloadFil download_args = IoEngine_xrg_downloadFil.new_("", Io_url_.Empty);
	public String Api_src(String wiki_domain) {
		return String_.Concat("https://", wiki_domain, "/w/api.php?action=query&format=xml&meta=siteinfo&siprop=namespacealiases|namespaces");
	}
	public byte[] Exec_api(String src) {
		return download_args.Exec_as_bry(src);
	}
	public void Build_cfg(Bry_bfr bfr, Xoi_wiki_props_wiki wiki) {
		bfr.Add_str_a7("app.bldr.wiki_cfg_bldr.get('").Add(wiki.Wiki_domain()).Add_str_a7("').new_cmd_('wiki.ns_mgr.aliases', 'ns_mgr.add_alias_bulk(\"\n");
		int len = 0;
		len = wiki.Alias_ary().length;
		for (int i = 0; i < len; i++) {
			Xoi_wiki_props_alias alias = wiki.Alias_ary()[i];
			bfr.Add_int_variable(alias.Id()).Add_byte_pipe().Add_str_u8(alias.Alias()).Add_byte_nl();
		}
		bfr.Add_str_a7("\");');\n");
		bfr.Add_str_a7("app.bldr.wiki_cfg_bldr.get('").Add(wiki.Wiki_domain()).Add_str_a7("').new_cmd_('wiki.ns_mgr.subpages', \"");
		len = wiki.Ns_ary().length;
		boolean first = true;
		for (int i = 0; i < len; i++) {
			Xoi_wiki_props_ns ns = wiki.Ns_ary()[i];
			if (ns.Subpages_enabled()) {
				if (first) {
					first = false;
				}
				else
					bfr.Add_byte_nl();
				bfr.Add_str_a7("ns_mgr.get_by_id_or_new(").Add_int_variable(ns.Id()).Add_str_a7(").subpages_enabled_('y');"); 
			}
		}
		bfr.Add_str_a7("\");\n");
		bfr.Add_byte_nl();
	}
	public void Parse(Xoi_wiki_props_wiki wiki, String xml) {
		XmlDoc xdoc = XmlDoc_.parse(xml);
		XmlNde query_xnde = Xpath_.SelectFirst(xdoc.Root(), "query");
		XmlNde aliases_xnde = Xpath_.SelectFirst(query_xnde, "namespace"+"aliases");
		wiki.Alias_ary_(Parse_alias_ary(aliases_xnde));
		XmlNde ns_xnde = Xpath_.SelectFirst(query_xnde, "namespace"+"s");
		wiki.Ns_ary_(Parse_ns_ary(ns_xnde));
	}
	private Xoi_wiki_props_alias[] Parse_alias_ary(XmlNde xnde) {
		int xndes_len = xnde.SubNdes().Count();
		List_adp list = List_adp_.New();
		for (int i = 0; i < xndes_len; i++) {
			XmlNde sub_nde = xnde.SubNdes().Get_at(i);
			if (!String_.Eq(sub_nde.Name(), "ns")) continue;
			Xoi_wiki_props_alias sub_itm = new Xoi_wiki_props_alias();
			sub_itm.Init_by_xml(sub_nde);
			list.Add(sub_itm);
		}
		return (Xoi_wiki_props_alias[])list.To_ary_and_clear(Xoi_wiki_props_alias.class);
	}
	private Xoi_wiki_props_ns[] Parse_ns_ary(XmlNde xnde) {
		int xndes_len = xnde.SubNdes().Count();
		List_adp list = List_adp_.New();
		for (int i = 0; i < xndes_len; i++) {
			XmlNde sub_nde = xnde.SubNdes().Get_at(i);
			if (!String_.Eq(sub_nde.Name(), "ns")) continue;
			Xoi_wiki_props_ns sub_itm = new Xoi_wiki_props_ns();
			sub_itm.Init_by_xml(sub_nde);
			list.Add(sub_itm);
		}
		return (Xoi_wiki_props_ns[])list.To_ary_and_clear(Xoi_wiki_props_ns.class);
	}
}
