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
package gplx.xowa.addons.bldrs.files.missing_origs.apis; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*; import gplx.xowa.addons.bldrs.files.missing_origs.*;
import gplx.langs.htmls.encoders.*;
import gplx.langs.jsons.*;
import gplx.xowa.files.repos.*;
import gplx.xowa.files.downloads.*;
import gplx.xowa.apps.wms.apis.origs.*;
public class Xowmf_imageinfo_api {
	private final    Xof_download_wkr download_wkr;
	private final    Ordered_hash temp_hash = Ordered_hash_.New();
	public static final    byte[] FILE_NS_PREFIX = Bry_.new_a7("File:");
	public Xowmf_imageinfo_api(Xof_download_wkr download_wkr) {
		this.download_wkr = download_wkr;
	}
	public void	Find_by_list(Ordered_hash src, byte repo_id, String api_domain, int idx) {
		// fail if web access disabled
		if (!gplx.core.ios.IoEngine_system.Web_access_enabled) {
			throw Err_.new_wo_type("web access must be enabled for missing_origs cmd");
		}

		Json_parser parser = new Json_parser();			
		Gfo_url_encoder encoder = Gfo_url_encoder_.New__http_url().Make();
		Bry_bfr bfr = Bry_bfr_.New();
		int len = src.Len();
		try {
			// loop until all titles found
			while (idx < len) {
				// generate api: EX: https://commons.wikimedia.org/w/api.php?action=query&format=json&formatversion=2&prop=imageinfo&iiprop=timestamp|size|mediatype|mime&redirects&iilimit=500&titles=File:Different%20Faces%20Neptune.jpg|File:East.svg
				// generate everything up to titles
				bfr.Add_str_a7("https://");
				bfr.Add_str_a7(api_domain);
				bfr.Add_str_a7("/w/api.php?action=query");
				bfr.Add_str_a7("&format=json"); // json easier to use than xml
				bfr.Add_str_a7("&iilimit=1"); // limit to 1 revision history (default will return more); EX:File:Different_Faces_Neptune.jpg
				bfr.Add_str_a7("&redirects"); // show redirects
				bfr.Add_str_a7("&prop=imageinfo&iiprop=timestamp|size|mediatype|mime"); // list of props; NOTE: "url" / "sha1" for future; "bitdepth" always 0?
				bfr.Add_str_a7("&titles=");

				// add titles; EX: File:A.png|File:B.png|
				for (int i = idx; i < idx + 500; i++) {
					Xowmf_imageinfo_item item = (Xowmf_imageinfo_item)src.Get_at(i);

					// skip "|" if first
					if (i != idx) bfr.Add_byte_pipe();

					// add ttl_bry
					byte[] ttl_bry = item.Lnki_ttl();
					ttl_bry = Bry_.Add(FILE_NS_PREFIX, ttl_bry); // WMF API requires "File:" prefix; EX: "File:A.png" x> "A.png"
					ttl_bry = Xoa_ttl.Replace_unders(ttl_bry); // convert to spaces else will get extra "normalize" node
					ttl_bry = encoder.Encode(ttl_bry); // encode for good form
					bfr.Add(ttl_bry);
				}
				
				// call api
				byte[] rslt = download_wkr.Download_xrg().Exec_as_bry(bfr.To_str_and_clear());

				// deserialize
				Json_doc jdoc = parser.Parse(rslt);

				// loop over pages
				Json_ary pages_ary = (Json_ary)jdoc.Get_grp_many("query", "pages");
				int pages_len = pages_ary.Len();
				for (int i = 0; i < pages_len; i++) {
					// get vars from page nde
					Json_nde page = pages_ary.Get_at_as_nde(i);
					int page_id = page.Get_as_int("page_id");
					byte[] title = page.Get_as_bry("title");

					// get vars from imageinfo node
					Json_ary info_ary = (Json_ary)page.Get_as_ary("imageinfo");
					Json_nde info_nde = (Json_nde)info_ary.Get_as_nde(0);
					byte[] timestamp = info_nde.Get_as_bry("timestamp");
					int size = info_nde.Get_as_int("size");
					int width = info_nde.Get_as_int("width");
					int height = info_nde.Get_as_int("height");
					byte[] mime = info_nde.Get_as_bry("mime");
					byte[] mediatype = info_nde.Get_as_bry("mediatype");

					// add to trg hash
					try {
						Xowmf_imageinfo_item trg_item = new Xowmf_imageinfo_item().Init_by_api_page(repo_id, page_id, title, size, width, height, mediatype, mime, timestamp);
						temp_hash.Add(trg_item.Orig_file_ttl(), trg_item);
					} catch (Exception e2) {
						Gfo_usr_dlg_.Instance.Warn_many("", "", "missing_origs:failed to deserialize api obj; domain=~{0} ttl=~{1} json=~{2} err=~{3}", api_domain, title, page.Print_as_json(), Err_.Message_gplx_log(e2));
					}
				}

				// loop over redirects
				Json_ary redirects_ary = (Json_ary)jdoc.Get_grp_many("query", "redirects");
				int redirects_len = pages_ary.Len();
				for (int i = 0; i < redirects_len; i++) {
					// get vars from redirect nde
					Json_nde redirect = redirects_ary.Get_at_as_nde(i);
					byte[] from = redirect.Get_as_bry("from");
					byte[] to = redirect.Get_as_bry("to");

					// get nde by "to" and copy redirect
					Xowmf_imageinfo_item trg_item = (Xowmf_imageinfo_item)temp_hash.Get_by_or_fail(to);
					trg_item.Init_by_api_redirect(from, to);

					// update temp_hash key
					temp_hash.Del(to);
					temp_hash.Add(from, trg_item);
				}

				// loop over hash and copy back to src
				int temp_hash_len = temp_hash.Len();
				for (int i = 0; i < temp_hash_len; i++) {
					Xowmf_imageinfo_item trg_item = (Xowmf_imageinfo_item)temp_hash.Get_at(i);
					Xowmf_imageinfo_item src_item = (Xowmf_imageinfo_item)temp_hash.Get_by(trg_item.Lnki_ttl());
					src_item.Copy_api_props(trg_item);
				}
			}
		} catch (Exception e) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "missing_origs:failure while calling wmf_api; domain=~{0} idx=~{1} err=~{2}", api_domain, idx, Err_.Message_gplx_log(e));
		}
	}
}
