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
package gplx.xowa.wikis.xwikis.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*;
import gplx.core.net.*;
import gplx.xowa.apps.gfs.*;
import gplx.xowa.langs.*;
import gplx.xowa.wikis.domains.*;
public class Xow_xwiki_itm_bldr {
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private final    Gfo_url_parser url_parser = new Gfo_url_parser();
	public Xow_xwiki_itm Bld_mw(Xow_domain_itm cur_domain, byte[] key, byte[] mw_url, byte[] domain_name) {return Bld_xo(cur_domain, key, Gfs_php_converter.To_gfs(tmp_bfr, mw_url), domain_name);} // EX: "//commons.wikimedia.org/wiki/Category:$1" -> "//commons.wikimedia.org/wiki/Category:~{0}"
	public Xow_xwiki_itm Bld_xo(Xow_domain_itm cur_domain, byte[] key, byte[] xo_url, byte[] domain_name) {
		byte[] domain_bry = Xow_xwiki_mgr.Get_domain_from_url(url_parser, xo_url);
		Xow_domain_itm domain_itm = Xow_domain_itm_.parse(domain_bry);
		Xol_lang_stub lang_itm = Xol_lang_stub_.Get_by_key_or_null(domain_itm.Lang_actl_key());
		int lang_id = lang_itm == null ? Xol_lang_stub_.Id__unknown : lang_itm.Id();
		if (domain_name == null) {															// no hard-coded name; currently dmoz or commons
			domain_name = (byte[])domain_name_hash.Get_by_bry(domain_bry);					// NOTE: domain_name is needed for "Related Sites" in wikivoyage
			if (domain_name == null) {
				if	(	cur_domain.Domain_type_id() != Xow_domain_tid_.Tid__wikipedia		// cur_domain is not wikipedia
					&&	domain_itm.Domain_type_id() == Xow_domain_tid_.Tid__wikipedia		// domain_itm is wikipedia
					&&	cur_domain.Lang_actl_uid() == domain_itm.Lang_actl_uid()			// cur_domain lang matches domain_lang
					) {				
					domain_name = Bry__domain_name__wikipedia;								// EX: in "en.wikivoyage.org", "en.wikipedia.org" should have name of "Wikipedia" (not "en.wikipedia.org")
				}
				else
					domain_name = domain_bry;												// default to domain_bry
			}
		}
		return new Xow_xwiki_itm(key, xo_url, lang_id, domain_itm.Domain_type_id(), domain_bry, domain_name, cur_domain.Abrv_wm());
	}
	private static final    Hash_adp_bry domain_name_hash = Hash_adp_bry.cs()
	.Add_str_obj("commons.wikimedia.org"	, Bry_.new_a7("Wikimedia Commons"))
	.Add_str_obj("www.dmoz.org"				, Bry_.new_a7("DMOZ"))
	;
	private static final    byte[] Bry__domain_name__wikipedia = Bry_.new_a7("Wikipedia");
        public static final    Xow_xwiki_itm_bldr Instance = new Xow_xwiki_itm_bldr(); Xow_xwiki_itm_bldr() {}
}
