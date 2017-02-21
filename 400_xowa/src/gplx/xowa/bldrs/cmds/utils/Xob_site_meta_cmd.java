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
package gplx.xowa.bldrs.cmds.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.core.net.*;
import gplx.dbs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*; import gplx.xowa.bldrs.wms.*; import gplx.xowa.bldrs.wms.sites.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.apps.site_cfgs.*;
public class Xob_site_meta_cmd implements Xob_cmd {
	private final    Xob_bldr bldr;
	private String[] wikis; private Io_url db_url; private DateAdp cutoff_time;
	public Xob_site_meta_cmd(Xob_bldr bldr, Xow_wiki wiki) {this.bldr = bldr;}
	public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return null;}
	public String Cmd_key() {return Xob_cmd_keys.Key_site_meta;}
	public void Cmd_run() {
		Xoa_app app = bldr.App();
		if (wikis == null)			wikis = Xow_domain_regy.All;
		if (db_url == null)			db_url = app.Fsys_mgr().Cfg_site_meta_fil();
		if (cutoff_time == null)	cutoff_time = Datetime_now.Get().Add_day(-1);
		Load_all(app, db_url, wikis, cutoff_time);
	}
	private void Load_all(Xoa_app app, Io_url db_url, String[] reqd_ary, DateAdp cutoff) {
		Site_json_parser site_parser = new Site_json_parser(app.Utl__json_parser());
		Gfo_usr_dlg usr_dlg = app.Usr_dlg();
		Gfo_inet_conn inet_conn = app.Utl__inet_conn();
		Ordered_hash reqd_hash = Ordered_hash_.New();
		int reqd_len = reqd_ary.length;
		for (int i = 0; i < reqd_len; ++i)
			reqd_hash.Add_as_key_and_val(reqd_ary[i]);

		Site_core_db json_db = new Site_core_db(db_url);			
		Site_core_itm[] actl_ary = json_db.Tbl__core().Select_all_downloaded(cutoff);
		int actl_len = actl_ary.length;
		for (int i = 0; i < actl_len; ++i) {	// remove items that have been completed after cutoff date
			Site_core_itm actl_itm = actl_ary[i];
			reqd_hash.Del(String_.new_u8(actl_itm.Site_domain()));
		}
		
		reqd_len = reqd_hash.Count();
		for (int i = 0; i < reqd_len; ++i) {
			String domain_str = (String)reqd_hash.Get_at(i);
			DateAdp json_date = Datetime_now.Get();
			byte[] json_text = null;
			for (int j = 0; j < 5; ++j) {
				json_text = gplx.xowa.bldrs.wms.Xowm_api_mgr.Call_by_qarg(usr_dlg, inet_conn, domain_str, Xoa_site_cfg_loader__inet.Qarg__all);
				if (json_text == null)
					gplx.core.threads.Thread_adp_.Sleep(1000);
				else
					break;
			}
			byte[] domain_bry = Bry_.new_u8(domain_str);
			byte[] site_abrv = Xow_abrv_xo_.To_bry(domain_bry);
			json_db.Tbl__core().Insert(site_abrv, domain_bry, Bool_.N, json_date, json_text);
		}

		reqd_len = reqd_ary.length;
		for (int i = 0; i < reqd_len; ++i) {
			String domain_str = reqd_ary[i];
			byte[] site_abrv = Xow_abrv_xo_.To_bry(Bry_.new_u8(domain_str));
			Site_core_itm core_itm = json_db.Tbl__core().Select_itm(site_abrv);
			if (core_itm.Json_completed()) continue;
			Site_meta_itm meta_itm = new Site_meta_itm();
			site_parser.Parse_root(meta_itm, String_.new_u8(core_itm.Site_domain()), core_itm.Json_text());
			json_db.Save(meta_itm, site_abrv);
		}
	}
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_end() {}
	public void Cmd_term() {}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_db_url_))		this.db_url = m.ReadIoUrl("v");
		else if	(ctx.Match(k, Invk_wikis_))			this.wikis = m.ReadStrAry("v", "\n");
		else if	(ctx.Match(k, Invk_cutoff_time_))	this.cutoff_time = m.ReadDate("v");
		else										return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static String Invk_db_url_ = "db_url_", Invk_wikis_ = "wikis_", Invk_cutoff_time_ = "cutoff_time_";
}
