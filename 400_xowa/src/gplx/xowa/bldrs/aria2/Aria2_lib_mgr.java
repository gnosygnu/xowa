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
package gplx.xowa.bldrs.aria2; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.xowa.apps.fsys.*;
public class Aria2_lib_mgr implements GfoInvkAble {
	public ProcessAdp Lib() {return lib;} private ProcessAdp lib = new ProcessAdp();
	public void Init_by_app(Xoae_app app) {
		Xoa_fsys_eval cmd_eval = app.Url_cmd_eval();
		ProcessAdp.ini_(this, app.Usr_dlg(), lib, cmd_eval, ProcessAdp.Run_mode_sync_block, Int_.MaxValue
		, "~{<>bin_plat_dir<>}aria2" + Op_sys.Cur().Fsys_dir_spr_str() +  "aria2c"
		, Lib_args_fmt
		, "wiki_abrv", "wiki_date", "wiki_type");
	}
	// private Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	public void Exec(Xob_dump_file dump_file) {			
		// byte[] args_bry = lib.Args_fmtr().Bld_bry_many(tmp_bfr, dump_file.Wiki_alias(), dump_file.Dump_date(), dump_file.Dump_file_type());
		// ProcessAdp process = new ProcessAdp().Exe_url_(lib.Exe_url()).Args_str_(String_.new_utf8_(args_bry));
		// process.Run_wait();			
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_lib))				return lib;
		else	return GfoInvkAble_.Rv_unhandled;
	}
	private static final String Invk_lib = "lib";
	private static final String Lib_args_fmt = String_.Concat
	( "--max-connection-per-server=2"
	, " --max-concurrent-downloads=20"
	, " --split=4"
	, " --file-allocation=prealloc"
	, " --remote-time=true"
	, " --server-stat-of=serverstats.txt"
	, " ftp://ftpmirror.your.org/pub/wikimedia/dumps/~{wiki_abrv}/~{wiki_date}/~{wiki_abrv}-~{wiki_date}-~{wiki_type}.bz2"
	, " http://dumps.wikimedia.org/~{wiki_abrv}/~{wiki_date}/~{wiki_abrv}-~{wiki_date}-~{wiki_type}.xml.bz2"
	);
}
