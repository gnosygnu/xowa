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
package gplx.xowa.wms.apis; import gplx.*; import gplx.xowa.*; import gplx.xowa.wms.*;
import gplx.core.primitives.*; import gplx.core.net.*; import gplx.langs.htmls.encoders.*;
import gplx.xowa.files.downloads.*;
import gplx.xowa.html.hrefs.*;
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
			if (pos == Bry_.NotFound) {usr_dlg.Log_many(GRP_KEY, "api_failed", "api failed: ~{0}", String_.new_u8(xml)); return false;}
			pos += Bry_xml_ii.length;

			byte[] orig_wiki = null, orig_page = null; int orig_w = 0, orig_h = 0;
			if (Parse_xml_val(tmp_rng, usr_dlg, xml, xml_len, pos, Bry_xml_width))
				orig_w = Bry_.To_int_or(xml, tmp_rng.Val_0(), tmp_rng.Val_1(), 0);

			if (Parse_xml_val(tmp_rng, usr_dlg, xml, xml_len, pos, Bry_xml_height))
				orig_h = Bry_.To_int_or(xml, tmp_rng.Val_0(), tmp_rng.Val_1(), 0);

			if (Parse_xml_val(tmp_rng, usr_dlg, xml, xml_len, pos, Bry_xml_descriptionurl)) {
				byte[] file_url = Bry_.Mid(xml, tmp_rng.Val_0(), tmp_rng.Val_1());
				url_parser.Parse(url, file_url, 0, file_url.length);
				orig_wiki = url.Segs__get_at_1st();
				byte[] page = Xoa_ttl.Replace_spaces(url.Segs__get_at_nth());
				int colon_pos = Bry_find_.Find_fwd(page, Byte_ascii.Colon, 0, page.length);
				if (colon_pos != Bry_.NotFound)
					page = Bry_.Mid(page, colon_pos + 1, page.length);
				orig_page = page;
			}
			rv.Init_all(orig_wiki, orig_page, orig_w, orig_h);
			return true;
		}
	}
	private static Int_2_ref tmp_rng = new Int_2_ref();
	private static Gfo_url_parser url_parser = new Gfo_url_parser(); private static Gfo_url url = new Gfo_url();
	private static boolean Parse_xml_val(Int_2_ref rv, Gfo_usr_dlg usr_dlg, byte[] xml, int xml_len, int pos, byte[] key) {
		int bgn = 0, end = 0;
		bgn = Bry_find_.Find_fwd(xml, key, pos, xml_len); if (bgn == Bry_.NotFound) return false;
		bgn += key.length;
		end = Bry_find_.Find_fwd(xml, Byte_ascii.Quote	, bgn, xml_len); if (end == Bry_.NotFound) return false;
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
			return tmp_bfr.Xto_str_and_clear();
		}
	}
	private static Url_encoder tmp_encoder = Url_encoder.new_http_url_().Itms_raw_diff(Byte_ascii.Space, Byte_ascii.Underline);
	private static final Bry_bfr tmp_bfr = Bry_bfr.new_();
	private static final byte[]
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
