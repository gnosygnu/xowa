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
package gplx.xowa.apps.boots; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.core.consoles.*;  import gplx.core.envs.*;
import gplx.dbs.*;
import gplx.gfui.*; import gplx.gfui.kits.core.*; import gplx.xowa.guis.views.boots.*;
import gplx.xowa.langs.*;
import gplx.xowa.users.*;	
public class Xoa_boot_mgr {
	private Gfo_usr_dlg usr_dlg; private Gfo_usr_dlg__log log_wtr;
	public void Run(String[] args, Xoa_cmd_arg_mgr arg_mgr) {
		try {
			Init_env(args);
			if (arg_mgr.Process(usr_dlg, args, Env_.AppUrl().OwnerDir()))
				Run_app(arg_mgr);
		}
		catch (Exception e) {
			String err_str = Err_.Message_gplx_full(e);
			log_wtr.Log_to_err(err_str);
			Console_adp__sys.Instance.Write_str_w_nl(err_str);
			if (log_wtr.Log_dir() == null) log_wtr.Log_dir_(Env_.AppUrl().OwnerDir().GenSubFil("xowa.log"));
			log_wtr.Queue_enabled_(false);
		}
	}
	private void Init_env(String[] args) {
		// add global loggers
		Gfo_usr_dlg_.Instance = usr_dlg = Xoa_app_.New__usr_dlg__console();
		Gfo_log_.Instance__set(new gplx.xowa.apps.shells.Gfo_log__console());
		log_wtr = usr_dlg.Log_wkr(); log_wtr.Log_to_session_fmt("env.init: version=~{0}", Xoa_app_.Version);

		// init env
		GfuiEnv_.Init_swt(args, Xoa_app_.class); 
		Io_url jar_url = Env_.AppUrl();
		Xoa_app_.Build_date = Io_mgr.Instance.QueryFil(jar_url).ModifiedTime().XtoUtc().XtoStr_fmt(Xoa_app_.Build_date_fmt);
		log_wtr.Log_to_session_fmt("env.init: jar_url=~{0}; build_date=~{1}", jar_url.NameAndExt(), Xoa_app_.Build_date);
		log_wtr.Log_to_session_fmt("env.init: op_sys=~{0}", Op_sys.Cur().To_str());
	}
	private void Run_app(Xoa_cmd_arg_mgr arg_mgr) {
		boolean app_type_is_gui = false;
		Xoae_app app = null;
		try {
			// pull vars from command-line args
			Io_url root_dir = arg_mgr.Fsys__root_dir();
			Xoa_app_.Op_sys_str = arg_mgr.Fsys__bin_dir();
			Xoa_app_.User_agent = String_.Format("XOWA/{0} ({1}) [gnosygnu@gmail.com]", Xoa_app_.Version, Xoa_app_.Op_sys_str);

			// prep splash window
			Xoa_app_mode app_type = arg_mgr.App_type();
			app_type_is_gui = app_type.Tid_is_gui();
			Xog_splash_win splash_win = new Xog_splash_win(app_type_is_gui);

			// change default db from mock to sqlite
			Db_conn_bldr.Instance.Reg_default_sqlite();

			// ctor app
			app = new Xoae_app(usr_dlg, app_type
			, root_dir
			, arg_mgr.Fsys__wiki_dir()
			, root_dir.GenSubDir("file")
			, arg_mgr.Fsys__user_dir()
			, root_dir.GenSubDir_nest("user", "anonymous", "wiki")
			, Xoa_app_.Op_sys_str);
			usr_dlg.Log_wkr().Queue_enabled_(false); // NOTE: needs to be called after new Xoae_app
			app.Addon_mgr().Add_dflts_by_app(app).Run_by_app(app);

			try {
				app.Sys_cfg().Lang_(System_lang());

				String launch_url = arg_mgr.Gui__home_page();
				if (launch_url != null) gplx.xowa.guis.views.Xog_startup_tabs.Manual = launch_url;

				// prep tcp-server
				Gfo_usr_dlg_.Instance.Log_wkr().Log_to_session_fmt("app.boot:servers");
				app.Tcp_server().Rdr_port_(arg_mgr.Tcp__port_recv()).Wtr_port_(arg_mgr.Tcp__port_send());

				// prep http-server
				gplx.xowa.apps.servers.http.Http_server_mgr server_mgr = app.Http_server();
				server_mgr.Port_(arg_mgr.Http__port(), false);
				server_mgr.Home_(Bry_.new_u8(arg_mgr.Http__home_page()));
				server_mgr.Wkr_pool().Init(arg_mgr.Http__max_clients(), arg_mgr.Http__max_clients_timeout());

				// add safelisted Special pages
				String special_pages_safelist = arg_mgr.Http__special_pages_safelist();
				if (special_pages_safelist != null) {
					byte[][] special_pages = Bry_split_.Split(Bry_.new_u8(special_pages_safelist), Byte_ascii.Pipe);

					// --http_server.special_pages_safelist "" should mean ignore all
					if (special_pages.length == 0) {
						special_pages = new byte[][] {Bry_.Empty};
					}
					for (byte[] special_page : special_pages) {
						app.Special_regy().Safelist_pages().Add_as_key_and_val(special_page);
					}
				}

				Gfo_usr_dlg_.Instance.Log_wkr().Log_to_session_fmt("app.boot:app.init");
				app.Init_by_app();
			}
			catch (Exception e) {usr_dlg.Warn_many("", "", "app init failed: ~{0}", Err_.Message_gplx_full(e));}
			app.Usr_dlg().Log_wkr_(app.Log_wtr());	// NOTE: log_wtr must be set for cmd-line (else process will fail);

			// run gfs; prefs.gfs and app.gfs
			Gfo_usr_dlg_.Instance.Log_wkr().Log_to_session_fmt("app.boot:gfs.run");
			Io_url cmd_file = arg_mgr.Cmd__file();
			try {app.Gfs_mgr().Run_url(cmd_file);}
			catch (Exception e) {
				usr_dlg.Warn_many("", "", "script file failed: ~{0} ~{1}", cmd_file.Raw(), Err_.Message_gplx_full(e));
				if (app_type_is_gui)
					GfuiEnv_.ShowMsg(Err_.Message_gplx_full(e));
			}

			// launch
			Gfo_usr_dlg_.Instance.Log_wkr().Log_to_session_fmt("app.boot:app.launch");
			app.Launch();
			if		(app_type.Tid_is_tcp())		app.Tcp_server().Run();
			else if	(app_type.Tid_is_http())	app.Http_server().Run();
			else {
				String cmd_text = arg_mgr.Cmd__text();
				if (cmd_text != null) {
					gplx.xowa.apps.servers.Gxw_html_server.Init_gui_for_server(app, null); // NOTE: must init kit else "app.shell.fetch_page" will fail; DATE:2015-04-30
					Console_adp__sys.Instance.Write_str_w_nl_utf8(Object_.Xto_str_strict_or_empty(app.Gfs_mgr().Run_str(cmd_text)));
				}
				if (app_type_is_gui)
					app.Gui_mgr().Run(splash_win);
				else	// teardown app, else lua will keep process running
					gplx.xowa.xtns.scribunto.Scrib_core_mgr.Term_all(app);
			}
		}
		catch (Exception e) {usr_dlg.Warn_many("", "", "app launch failed: ~{0}", Err_.Message_gplx_full(e));}
		finally {
			if (app != null && app_type_is_gui)	// only cancel if app_type_is_gui is true; (force cmd_line to end process)
				app.Setup_mgr().Cmd_mgr().Canceled_y_();
		}
	}
	private static byte[] System_lang() {
		String lang_code = System_.Prop__user_language();
		byte[] lang_code_bry = Bry_.new_a7(lang_code);
		Xol_lang_stub lang_itm = Xol_lang_stub_.Get_by_key_or_null(lang_code_bry);
		return lang_itm == null ? Xol_lang_itm_.Key_en : lang_itm.Key();
	}
}
	