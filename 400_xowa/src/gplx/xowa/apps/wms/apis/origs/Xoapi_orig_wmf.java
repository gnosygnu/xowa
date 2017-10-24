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
package gplx.xowa.apps.wms.apis.origs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.wms.*; import gplx.xowa.apps.wms.apis.*;
import gplx.core.primitives.*; import gplx.core.net.*; import gplx.core.envs.*;
import gplx.langs.htmls.encoders.*; 
import gplx.xowa.files.downloads.*;
import gplx.xowa.htmls.hrefs.*;
public class Xoapi_orig_wmf extends Xoapi_orig_base {
	@Override public boolean Api_query_size_exec(Xoapi_orig_rslts rv, Xof_download_wkr download_wkr, byte[] ttl, int width, int height, Gfo_usr_dlg usr_dlg, byte[] repo_wiki_key) {
		if (Env_.Mode_testing()) return false; // TEST: disable during tests else scrib_lib_title will try to call WMF API; DATE:2015-03-20			
		String src = Bld_api_url(repo_wiki_key, ttl, width, height);
		// xrg.Prog_fmt_hdr_(); // TOMBSTONE: do not uncomment; api will reuse whatever's in place
		byte[] xml = download_wkr.Download_xrg().Exec_as_bry(src);
		return xml == null ? false : Parse_xml(rv, usr_dlg, xml);
	}
	public static boolean Parse_xml(Xoapi_orig_rslts rv, Gfo_usr_dlg usr_dlg, byte[] xml) {
		synchronized (tmp_rng) {
			rv.Clear();
			int xml_len = xml.length;
			int pos = 0;
			pos = Bry_find_.Find_fwd(xml, Bry_xml_ii			, pos, xml_len); 
			if (pos == Bry_find_.Not_found) {usr_dlg.Log_many(GRP_KEY, "api_failed", "api failed: ~{0}", String_.new_u8(xml)); return false;}
			pos += Bry_xml_ii.length;

			byte[] orig_wiki = null, orig_page = null; int orig_w = 0, orig_h = 0;
			if (Parse_xml_val(tmp_rng, usr_dlg, xml, xml_len, pos, Bry_xml_width))
				orig_w = Bry_.To_int_or(xml, tmp_rng.Val_0(), tmp_rng.Val_1(), 0);

			if (Parse_xml_val(tmp_rng, usr_dlg, xml, xml_len, pos, Bry_xml_height))
				orig_h = Bry_.To_int_or(xml, tmp_rng.Val_0(), tmp_rng.Val_1(), 0);

			if (Parse_xml_val(tmp_rng, usr_dlg, xml, xml_len, pos, Bry_xml_descriptionurl)) {
				byte[] file_url = Bry_.Mid(xml, tmp_rng.Val_0(), tmp_rng.Val_1());
				Gfo_url url = url_parser.Parse(file_url, 0, file_url.length);
				orig_wiki = url.Segs__get_at_1st();
				byte[] page = Xoa_ttl.Replace_spaces(url.Segs__get_at_nth());
				int colon_pos = Bry_find_.Find_fwd(page, Byte_ascii.Colon, 0, page.length);
				if (colon_pos != Bry_find_.Not_found)
					page = Bry_.Mid(page, colon_pos + 1, page.length);
				orig_page = page;
			}
			rv.Init_all(orig_wiki, orig_page, orig_w, orig_h);
			return true;
		}
	}
	private static Int_2_ref tmp_rng = new Int_2_ref();
	private static Gfo_url_parser url_parser = new Gfo_url_parser();
	private static boolean Parse_xml_val(Int_2_ref rv, Gfo_usr_dlg usr_dlg, byte[] xml, int xml_len, int pos, byte[] key) {
		int bgn = 0, end = 0;
		bgn = Bry_find_.Find_fwd(xml, key, pos, xml_len); if (bgn == Bry_find_.Not_found) return false;
		bgn += key.length;
		end = Bry_find_.Find_fwd(xml, Byte_ascii.Quote	, bgn, xml_len); if (end == Bry_find_.Not_found) return false;
		rv.Val_all_(bgn, end);
		return true;
	}
	public static String Bld_api_url(byte[] wiki_key, byte[] ttl, int width, int height) {
		synchronized (tmp_bfr) {
			tmp_bfr.Add(Xoh_href_.Bry__https)						// "https://"
				.Add(wiki_key)										// "commons.wikimedia.org"
				.Add(Bry_api)										// "/w/api.php?action=query&format=xml&prop=imageinfo&iiprop=size|url&titles=File:"
				.Add(tmp_encoder.Encode(ttl))						// "A%20B.png"
				;
			if (width > 0)
				tmp_bfr.Add(Bry_width).Add_int_variable(width);		// "&iiurlwidth=800"
			if (height > 0 && width > 0)							// NOTE: height cannot be used alone; width must also exist; "iiurlheight cannot be used without iiurlwidth"
				tmp_bfr.Add(Bry_height).Add_int_variable(height);	// "&iiurlheight=600"
			return tmp_bfr.To_str_and_clear();
		}
	}
	private static Gfo_url_encoder tmp_encoder = Gfo_url_encoder_.New__http_url().Init__diff__one(Byte_ascii.Space, Byte_ascii.Underline).Make();
	private static final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private static final    byte[]
	  Bry_api					= Bry_.new_a7("/w/api.php?action=query&format=xml&prop=imageinfo&iiprop=size|url&redirects&titles=File:")	// NOTE: using File b/c it should be canonical
	, Bry_width					= Bry_.new_a7("&iiurlwidth=")
	, Bry_height				= Bry_.new_a7("&iiurlheight=")
	, Bry_xml_ii				= Bry_.new_a7("<ii ")
	, Bry_xml_width				= Bry_.new_a7("width=\"")
	, Bry_xml_height			= Bry_.new_a7("height=\"")
	, Bry_xml_descriptionurl	= Bry_.new_a7("descriptionurl=\"")
	;
	public static final String GRP_KEY = "xowa.file.wmf.api";
}
