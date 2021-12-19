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
package gplx.xowa.addons.bldrs.files.missing_origs.apis;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.langs.jsons.Json_ary;
import gplx.langs.jsons.Json_doc;
import gplx.langs.jsons.Json_nde;
import gplx.langs.jsons.Json_parser;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.files.downloads.Xof_download_wkr;
public class Xowmf_recentchanges_api {
	public Ordered_hash Find(Xof_download_wkr download_wkr, String api_domain, String bgn_date, String end_date, int limit) {
		// fail if web access disabled
		if (!gplx.core.ios.IoEngine_system.Web_access_enabled) {
			throw ErrUtl.NewArgs("web access must be enabled for missing_origs cmd");
		}

		Json_parser parser = new Json_parser();			
		BryWtr bfr = BryWtr.New();
		Ordered_hash list = Ordered_hash_.New_bry();
		String rcccontinue = null;
		// loop until all titles found
		while (true) {
			// generate api: https://commons.wikimedia.org/w/api.php?format=json&formatversion=2&action=query&list=recentchanges&rcnamespace=6&rctype=log&rcdir=older&rcstart=20170306000000&rcend=20170301000000&rcprop=title|ids|sizes|loginfo&rclimit=5&rccontinue=20170305235910|537908722
			bfr.AddStrA7("https://");
			bfr.AddStrA7(api_domain);
			bfr.AddStrA7("/w/api.php");
			bfr.AddStrA7("?format=json"); // json easier to use than xml
			bfr.AddStrA7("&formatversion=2"); // json easier to use than xml
			bfr.AddStrA7("&action=query");
			bfr.AddStrA7("&list=recentchanges");
			bfr.AddStrA7("&rcnamespace=6");
			bfr.AddStrA7("&rctype=log");
			bfr.AddStrA7("&rcdir=older");	// NOTE: newer can take a long time
			bfr.AddStrA7("&rcstart=").AddStrA7(end_date); // NOTE: reverse start and end date so that api is more natural; i.e.: start=20170301 end=20170307
			bfr.AddStrA7("&rcend=").AddStrA7(bgn_date);
			bfr.AddStrA7("&rcprop=title|ids|sizes|loginfo"); // list of props; NOTE: "url" / "sha1" for future; "bitdepth" always 0?
			bfr.AddStrA7("&rclimit=").AddIntVariable(limit);
			if (rcccontinue != null)
				bfr.AddStrA7("&rccontinue=").AddStrA7(rcccontinue);

			// call api
			byte[] rslt = null;
			try {
				rslt = download_wkr.Download_xrg().Exec_as_bry(bfr.ToStrAndClear());
			}
			catch (Exception e) {
				Gfo_usr_dlg_.Instance.Warn_many("", "", "wmf_api:failure while calling api; domain=~{0} err=~{1}", api_domain, ErrUtl.ToStrLog(e));
			}

			// deserialize
			Json_doc jdoc = parser.Parse(rslt);

			// get items
			Json_ary ary = (Json_ary)jdoc.Get_grp_many("query", "recentchanges");
			int ary_len = ary.Len();
			for (int i = 0; i < ary_len; i++) {
				Json_nde nde = ary.Get_as_nde(i);
				try {
					byte[] title = Xowmf_imageinfo_item.Normalize_ttl(nde.Get_as_bry("title"));
					Xowmf_recentchanges_item item = (Xowmf_recentchanges_item)list.GetByOrNull(title);
					// not in list; add it
					if (item == null) {
						item = new Xowmf_recentchanges_item();
						list.Add(title, item);
					}
					// is in list; update with latest props
					else {
						item.Logtype_push();
					}

					String logaction = nde.Get_as_str("logaction");
					item.Init_base
					( nde.Get_as_int("ns")
					, title
					, nde.Get_as_int("pageid")
					, nde.Get_as_int("revid")
					, nde.Get_as_int("old_revid")
					, nde.Get_as_int("rcid")
					, nde.Get_as_int("oldlen")
					, nde.Get_as_int("newlen")
					, nde.Get_as_int("logid")
					, nde.Get_as_str("logtype")
					, nde.Get_as_str("logaction")
					);

					Json_nde logparams_nde = nde.Get_as_nde("logparams");
					if      (StringUtl.Eq(logaction, "upload")) {
						item.Init_upload(Xowmf_imageinfo_item.Normalize_timestamp(logparams_nde.Get_as_bry("img_timestamp")));
					}
					else if (StringUtl.Eq(logaction, "move")) {
						item.Init_move
						( logparams_nde.Get_as_int("target_ns")
						, Xowmf_imageinfo_item.Normalize_ttl(logparams_nde.Get_as_bry("target_title"))
						);
					}
					else if (StringUtl.Eq(logaction, "delete")) {}// NOTE: logparams nde is empty; DATE:2017-03-07
				}
				catch (Exception e) {
					Gfo_usr_dlg_.Instance.Warn_many("", "", "wmf_api:failure while deserializing node; domain=~{0} err=~{1}", api_domain, ErrUtl.ToStrLog(e));
				}
			}

			// extract continue
			Json_nde continue_nde = (Json_nde)jdoc.Get_grp_many("continue");
			if (continue_nde == null) {
				break;
			}
			rcccontinue = continue_nde.Get_as_str("rccontinue");
		}
		return list;
	}
}
