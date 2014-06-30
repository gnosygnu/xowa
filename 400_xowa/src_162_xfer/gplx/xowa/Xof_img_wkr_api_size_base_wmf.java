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
package gplx.xowa; import gplx.*;
public class Xof_img_wkr_api_size_base_wmf extends Xof_img_wkr_api_size_base {		
	@Override public boolean Api_query_size_exec(Xof_img_wkr_api_size_base_rslts rv, Xow_wiki wiki, byte[] ttl, int width, int height, Gfo_usr_dlg usr_dlg, byte[] repo_wiki_key) {
		String src = Bld_api_url(repo_wiki_key, ttl, width, height);
//				xrg.Prog_fmt_hdr_(); // NOTE: do not uncomment; api will reuse whatever's in place
		byte[] xml = wiki.App().File_mgr().Download_mgr().Download_wkr().Download_xrg().Exec_as_bry(src);
		return xml == null ? false : Parse_xml(rv, usr_dlg, xml);
	}
	public static boolean Parse_xml(Xof_img_wkr_api_size_base_rslts rv, Gfo_usr_dlg usr_dlg, byte[] xml) {
		rv.Clear();
		int xml_len = xml.length;
		int pos = 0;
		pos = Bry_finder.Find_fwd(xml, Bry_xml_ii			, pos, xml_len); if (pos == Bry_.NotFound) return Parse_xml_failed(usr_dlg, xml);
		pos += Bry_xml_ii.length;

		if (Parse_xml_val(parse_xml_rng, usr_dlg, xml, xml_len, pos, Bry_xml_width))
			rv.Orig_w_(Bry_.X_to_int_or(xml, parse_xml_rng.Val_0(), parse_xml_rng.Val_1(), 0));

		if (Parse_xml_val(parse_xml_rng, usr_dlg, xml, xml_len, pos, Bry_xml_height))
			rv.Orig_h_(Bry_.X_to_int_or(xml, parse_xml_rng.Val_0(), parse_xml_rng.Val_1(), 0));

		if (Parse_xml_val(parse_xml_rng, usr_dlg, xml, xml_len, pos, Bry_xml_descriptionurl)) {
			byte[] file_url = Bry_.Mid(xml, parse_xml_rng.Val_0(), parse_xml_rng.Val_1());
			url_parser.Parse(url, file_url, 0, file_url.length);
			rv.Reg_wiki_(url.Site());
			byte[] page = Xoa_ttl.Replace_spaces(url.Page());
			int colon_pos = Bry_finder.Find_fwd(page, Byte_ascii.Colon, 0, page.length);
			if (colon_pos != Bry_.NotFound)
				page = Bry_.Mid(page, colon_pos + 1, page.length);
			rv.Reg_page_(page);
		}
		return true;
	}
	private static Int_2_ref parse_xml_rng = new Int_2_ref(); static Xoa_url parse_xml_url = new Xoa_url();
	private static Gfo_url_parser url_parser = new Gfo_url_parser(); static Gfo_url url = new Gfo_url();
	private static boolean Parse_xml_val(Int_2_ref rv, Gfo_usr_dlg usr_dlg, byte[] xml, int xml_len, int pos, byte[] key) {
		int bgn = 0, end = 0;
		bgn = Bry_finder.Find_fwd(xml, key					, pos, xml_len); if (bgn == Bry_.NotFound) return false;
		bgn += key.length;
		end = Bry_finder.Find_fwd(xml, Byte_ascii.Quote	, bgn, xml_len); if (end == Bry_.NotFound) return false;
		rv.Val_all_(bgn, end);
		return true;
	}
	private static boolean Parse_xml_failed(Gfo_usr_dlg usr_dlg, byte[] xml) {usr_dlg.Log_many(GRP_KEY, "api_failed", "api failed: ~{0}", String_.new_utf8_(xml)); return false;}
	public static String Bld_api_url(byte[] wiki_key, byte[] ttl, int width, int height) {
		bfr	.Add(Xoh_href_parser.Href_http_bry)				// "http://"
			.Add(wiki_key)									// "commons.wikimedia.org"
			.Add(Bry_api)									// "/w/api.php?action=query&format=xml&prop=imageinfo&iiprop=size|url&titles=File:"
			.Add(encoder.Encode(ttl))						// "A%20B.png"
			;
		if (width > 0)
			bfr.Add(Bry_width).Add_int_variable(width);		// "&iiurlwidth=800"
		if (height > 0 && width > 0)						// NOTE: height cannot be used alone; width must also exist; "iiurlheight cannot be used without iiurlwidth"
			bfr.Add(Bry_height).Add_int_variable(height);	// "&iiurlheight=600"
		return bfr.XtoStrAndClear();
	}
	private static Url_encoder encoder = Url_encoder.new_http_url_().Itms_raw_diff(Byte_ascii.Space, Byte_ascii.Underline);
	Io_url trg = Io_url_.mem_fil_("mem/temp/api.txt");
	private static final Bry_bfr bfr = Bry_bfr.new_();
	private static final byte[]
			Bry_api					= Bry_.new_ascii_("/w/api.php?action=query&format=xml&prop=imageinfo&iiprop=size|url&redirects&titles=File:")	// NOTE: using File b/c it should be canonical
		,	Bry_width				= Bry_.new_ascii_("&iiurlwidth=")
		,	Bry_height				= Bry_.new_ascii_("&iiurlheight=")
		,	Bry_xml_ii				= Bry_.new_ascii_("<ii ")
		,	Bry_xml_width			= Bry_.new_ascii_("width=\"")
		,	Bry_xml_height			= Bry_.new_ascii_("height=\"")
		,	Bry_xml_descriptionurl	= Bry_.new_ascii_("descriptionurl=\"")
		;
	public static final String GRP_KEY = "xowa.file.wmf.api";
}
