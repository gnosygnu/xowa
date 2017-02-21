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
package gplx.xowa.bldrs.aria2; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.core.envs.*;
import gplx.xowa.apps.fsys.*; import gplx.xowa.bldrs.wms.dumps.*;
public class Aria2_lib_mgr implements Gfo_invk {
	public Process_adp Lib() {return lib;} private Process_adp lib = new Process_adp();
	public void Init_by_app(Xoae_app app) {
		Xoa_fsys_eval cmd_eval = app.Url_cmd_eval();
		Process_adp.ini_(this, app.Usr_dlg(), lib, cmd_eval, Process_adp.Run_mode_sync_block, Int_.Max_value
		, "~{<>bin_plat_dir<>}aria2" + Op_sys.Cur().Fsys_dir_spr_str() +  "aria2c"
		, Lib_args_fmt
		, "wiki_abrv", "wiki_date", "wiki_type");
	}
	// private Bry_bfr tmp_bfr = Bry_bfr_.Reset(255);
	public void Exec(Xowm_dump_file dump_file) {			
		// byte[] args_bry = lib.Args_fmtr().Bld_bry_many(tmp_bfr, dump_file.Wiki_alias(), dump_file.Dump_date(), dump_file.Dump_file_type());
		// Process_adp process = new Process_adp().Exe_url_(lib.Exe_url()).Args_str_(String_.new_u8(args_bry));
		// process.Run_wait();			
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_lib))				return lib;
		else	return Gfo_invk_.Rv_unhandled;
	}
	private static final    String Invk_lib = "lib";
	private static final    String Lib_args_fmt = String_.Concat
	( "--max-connection-per-server=2"
	, " --max-concurrent-downloads=20"
	, " --split=4"
	, " --file-allocation=prealloc"
	, " --remote-time=true"
	, " --server-stat-of=serverstats.txt"
	, " ftp://ftpmirror.your.org/pub/wikimedia/dumps/~{wiki_abrv}/~{wiki_date}/~{wiki_abrv}-~{wiki_date}-~{wiki_type}.bz2"
	, " https://dumps.wikimedia.org/~{wiki_abrv}/~{wiki_date}/~{wiki_abrv}-~{wiki_date}-~{wiki_type}.xml.bz2"
	);
}
