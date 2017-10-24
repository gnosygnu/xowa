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
package gplx.xowa.addons.bldrs.centrals.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*;
import gplx.dbs.*;
import gplx.core.progs.*; import gplx.core.net.downloads.*; import gplx.core.gfobjs.*;
import gplx.xowa.users.data.*; import gplx.xowa.addons.bldrs.centrals.dbs.datas.*;
import gplx.xowa.wikis.pages.tags.*;
public class Xobc_data_db_upgrader {
	private static final String 
	  Cfg__last_check_date		= "xowa.bldr_central.data_db.last_check_date"
	// , Cfg__next_check_interval	= "xowa.bldr_central.data_db.next_check_interval"
	, Date_fmt = "yyyy-MM-dd HH:mm"
	;
	public static void Check_for_updates(Xobc_task_mgr task_mgr) {
		Io_url data_db_url = task_mgr.Data_db().Url();
		Xoud_cfg_mgr cfg_mgr = task_mgr.App().User().User_db_mgr().Cfg_mgr();

		// check if update needed
		//String last_check_str = cfg_mgr.Select_str_or("", Cfg__last_check_date, "");
		//int next_check_interval = cfg_mgr.Select_int_or("", Cfg__next_check_interval, 24 * 7);
		//DateAdp last_check = DateAdp_.parse_fmt_or(last_check_str, Date_fmt, null);
		//if (last_check != null) {						// check if last_check_str exists
		//	Time_span span = Datetime_now.Get().Diff(last_check);
		//	if (span.Total_hours().To_double() < next_check_interval) {	// check if enough time passed
		//		Gfo_log_.Instance.Info("xobc_db update not needed", "last_check", last_check_str, "next_check_interval", next_check_interval);
		//		return;
		//	}
		//	else
		//		Gfo_log_.Instance.Info("xobc_db update needed b/c of last_check", "last_check", last_check_str);
		//}
		//else
		//	Gfo_log_.Instance.Info("xobc_db update needed b/c of missing or invalid last_check_str", "last_check", last_check_str);

		// update needed; first, update cfg_key, then get host
		cfg_mgr.Upsert_str("", Cfg__last_check_date, Datetime_now.Get().XtoStr_fmt(Date_fmt));
		Xobc_data_db bc_db = task_mgr.Data_db();
		Xobc_host_regy_itm host_itm = bc_db.Tbl__host_regy().Select(Xobc_host_regy_tbl.Host_id__archive_org);

		// download manifest
		Http_download_wkr download_wkr = Http_download_wkr_.Proto.Make_new();
		Io_url manifest_url = data_db_url.GenNewExt(".txt");
		download_wkr.Exec(Gfo_prog_ui_.Always
			, String_.Format("http://{0}/{1}/bldr_central.data_db.txt", host_itm.Host_domain(), host_itm.Host_update_dir())
			, manifest_url, -1);

		// parse manifest and get version_id
		byte[] manifest_txt = Io_mgr.Instance.LoadFilBry(manifest_url);
		byte[][] manifest_data = Bry_split_.Split(manifest_txt, Byte_ascii.Pipe);
		int expd_version_id = Bry_.To_int(manifest_data[0]);
		Xobc_version_regy_itm actl_version = bc_db.Tbl__version_regy().Select_latest();

		// cleanup
		Io_mgr.Instance.DeleteFil(manifest_url);
		bc_db.Conn().Rls_conn();
		if (expd_version_id == actl_version.Id()) {
			Gfo_log_.Instance.Info("xobc_db update not needed", "version", expd_version_id);
			Xopg_alertify_.Exec_log(task_mgr, "Wikis are up-to-date", 30);
			return;	// version matches; exit
		}

		// version doesn't match; download new
		// next_check_interval = Bry_.To_int(manifest_data[2]);
		// cfg_mgr.Upsert_int("", Cfg__next_check_interval, next_check_interval);
		// Gfo_log_.Instance.Info("xobc_db update needed", "version", expd_version_id, "next_check_interval", next_check_interval);
		byte[] new_db_url = manifest_data[1];
		String note = String_.new_u8(manifest_data[3]);
		download_wkr.Exec(Gfo_prog_ui_.Always
			, String_.new_u8(new_db_url)
			, data_db_url, -1);
		Xopg_alertify_.Exec_log(task_mgr, "Wikis have been updated:<br/>" + note, 30);
		task_mgr.Load_or_init();
		task_mgr.Reload();
	}
}
