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
package gplx.xowa.bldrs.wms.sites; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wms.*;
import gplx.core.net.*; import gplx.dbs.cfgs.*;
import gplx.langs.jsons.*;	
class Site_json_fetcher {
	private Gfo_usr_dlg usr_dlg; private Gfo_inet_conn inet_conn; private String domain_str; private Db_cfg_tbl cfg_tbl;
	private Io_url default_url;
	public void Init(Gfo_usr_dlg usr_dlg, Gfo_inet_conn inet_conn, String domain_str, Db_cfg_tbl cfg_tbl, Io_url default_url) {
		this.usr_dlg = usr_dlg;
		this.inet_conn = inet_conn;
		this.domain_str = domain_str;
		this.cfg_tbl = cfg_tbl;
		this.default_url = default_url;
	}
	public void Init_by_wiki(Xow_wiki wiki) {
		Xoa_app app = wiki.App();
		this.usr_dlg = app.Usr_dlg();
		this.inet_conn = app.Utl__inet_conn();
		this.domain_str = wiki.Domain_str();
		this.cfg_tbl = wiki.Data__core_mgr().Tbl__cfg();
		this.default_url = app.Fsys_mgr().Bin_xowa_dir().GenSubFil_nest("cfg", "wiki", "api", "xowa_default.json");
	}
	public byte[] Get_json(Io_url custom_url) {
		byte[] rv = null;
		int trial = 0;
		while (true) {
			switch (trial) {
				case 0: rv = Get_json_from_fs(custom_url); break;
				case 1: rv = Get_json_from_db(cfg_tbl); break;
				case 2: rv = Get_json_from_wm(usr_dlg, inet_conn, domain_str, cfg_tbl); break;
				case 3: rv = Get_json_from_fs(default_url); break;
				default: throw Err_.new_("api.site", "could not find site json", "wiki", domain_str);
			}
			if (rv == null)
				++trial;
			else
				break;
		}
		return rv;
	}
	private byte[] Get_json_from_fs(Io_url url) {return url == null ? null : Io_mgr.Instance.LoadFilBryOrNull(url);}
	private byte[] Get_json_from_db(Db_cfg_tbl cfg_tbl) {return cfg_tbl.Select_bry(Cfg_grp__xowa_bldr_api, Cfg_key__xowa_bldr_api__data);}
	private byte[] Get_json_from_wm(Gfo_usr_dlg usr_dlg, Gfo_inet_conn inet_conn, String domain_str, Db_cfg_tbl cfg_tbl) {
		String api_str = "action=query&format=json&meta=siteinfo&siprop=general|namespaces|statistics|interwikimap|namespacealiases|specialpagealiases|libraries|extensions|skins|magicwords|functionhooks|showhooks|extensiontags|protocols|defaultoptions|languages";
		byte[] rv = Xowm_api_mgr.Call_by_qarg(usr_dlg, inet_conn, domain_str, api_str);
		if (rv != null) cfg_tbl.Assert_bry(Cfg_grp__xowa_bldr_api, Cfg_key__xowa_bldr_api__data, rv);
		return rv;
	}
	private static final String Cfg_grp__xowa_bldr_api = "xowa.bldr.api", Cfg_key__xowa_bldr_api__data = "data";
}
