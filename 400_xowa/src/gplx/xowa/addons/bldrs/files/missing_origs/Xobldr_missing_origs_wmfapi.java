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
package gplx.xowa.addons.bldrs.files.missing_origs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import gplx.langs.htmls.encoders.*;
import gplx.langs.jsons.*;
import gplx.xowa.files.repos.*;
import gplx.xowa.files.downloads.*;
import gplx.xowa.apps.wms.apis.origs.*;
public class Xobldr_missing_origs_wmfapi {
//		private final    Xoapi_orig_base orig_api;
//		private final    Xof_download_wkr download_wkr;
//		private final    Xow_repo_mgr repo_mgr;
//		private final    byte[] wiki_domain;
//		private final    Xoapi_orig_rslts api_rv = new Xoapi_orig_rslts();		
	public Xobldr_missing_origs_wmfapi(Xoapi_orig_base orig_api, Xof_download_wkr download_wkr, Xow_repo_mgr repo_mgr, byte[] wiki_domain) {
//			this.orig_api = orig_api;
//			this.download_wkr = download_wkr;
//			this.repo_mgr = repo_mgr;
//			this.wiki_domain = wiki_domain;
	}
	public void	Find_by_list(Ordered_hash src, Ordered_hash trg, String api_domain, int idx) {
		// fail if web access disabled
		if (!gplx.core.ios.IoEngine_system.Web_access_enabled) {
			throw Err_.new_wo_type("web access must be enabled for missing_origs cmd");
		}

//			Json_parser parser = new Json_parser();			
		Gfo_url_encoder encoder = Gfo_url_encoder_.New__http_url().Make();
		Bry_bfr bfr = Bry_bfr_.New();
		int len = src.Len();
		try {
			// loop until all titles found
			while (idx < len) {
				// generate super api; EX: https://commons.wikimedia.org/w/api.php?action=query&format=xml&prop=imageinfo&iiprop=size|url|mediatype|mime|bitdepth|timestamp|size|sha1&redirects&iilimit=500&titles=
				bfr.Add_str_a7("https://");
				bfr.Add_str_a7(api_domain);
				bfr.Add_str_a7("/w/api.php?action=query");
				bfr.Add_str_a7("&format=json"); // json easier to use than xml
				bfr.Add_str_a7("&iilimit=1"); // limit to 1 revision history (default will return more); EX:File:Different_Faces_Neptune.jpg
				bfr.Add_str_a7("&redirects"); // show redirects
				bfr.Add_str_a7("&prop=imageinfo&iiprop=size|url|mediatype|mime|bitdepth|timestamp|size|sha1"); // list of props
				bfr.Add_str_a7("&titles=");

				// add titles; EX: File:A.png|File:B.png|
				for (int i = idx; i < idx + 500; i++) {
					Xobldr_missing_origs_item item = (Xobldr_missing_origs_item)src.Get_at(i);
					Xoa_ttl ttl = item.Lnki_ttl(); 

					// skip "|" if first
					if (i != idx) bfr.Add_byte_pipe();

					// make ttl_bry so (a) namespace is present (EX:File:); (b) spaces are present (not underscores)
					byte[] ttl_bry = ttl.Full_txt_wo_qarg();
					ttl_bry = encoder.Encode(ttl_bry);
					bfr.Add(ttl_bry);
				}
				
				// call api
//					byte[] rslt = download_wkr.Download_xrg().Exec_as_bry(bfr.To_bry_and_clear());

				// deserialize
//					Json_doc jdoc = parser.Parse(rslt);

				// loop over /query/pages
				//   for each node, deserialize orig info and add to hash by "title"
				// loop over /query/redirects
				//   for each node, retrieve from hash by "to"; add "from" as prop
				// loop over hash
				//   for each item, retrieve from src; copy props over
			}
		} catch (Exception e) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "missing_origs:failure while calling wmf_api; domain=~{0} idx=~{1} err=~{2}", api_domain, idx, Err_.Message_gplx_log(e));
		}
	}
}
class Xobldr_missing_origs_item {
	private final    Xoa_ttl lnki_ttl;
	public Xobldr_missing_origs_item(Xoa_ttl lnki_ttl) {
		this.lnki_ttl = lnki_ttl;
	}
	public Xoa_ttl Lnki_ttl() {return lnki_ttl;}
}
