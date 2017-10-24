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
import gplx.core.envs.*;
public class Xob_unzip_wkr {
	private Process_adp decompress_bz2, decompress_zip, decompress_gz, process;
	public int Process_exit_code() {return process.Exit_code();}
	public byte Process_run_mode() {return process_run_mode;} public Xob_unzip_wkr Process_run_mode_(byte v) {process_run_mode = v; return this;} private byte process_run_mode = Process_adp.Run_mode_async;
	public Xob_unzip_wkr Init(Xoae_app app) {return Init(app.Prog_mgr().App_decompress_bz2(), app.Prog_mgr().App_decompress_zip(), app.Prog_mgr().App_decompress_gz());}
	public Xob_unzip_wkr Init(Process_adp decompress_bz2, Process_adp decompress_zip, Process_adp decompress_gz) {
		this.decompress_bz2 = decompress_bz2;
		this.decompress_zip = decompress_zip;
		this.decompress_gz  = decompress_gz;
		return this;
	}
	public void Decompress(Io_url src, Io_url trg) {
		String src_ext = src.Ext();
		if		(String_.Eq(src_ext, gplx.core.ios.streams.Io_stream_tid_.Ext__bz2))		process = decompress_bz2;
		else if	(String_.Eq(src_ext, gplx.core.ios.streams.Io_stream_tid_.Ext__zip))		process = decompress_zip;
		else if	(String_.Eq(src_ext, gplx.core.ios.streams.Io_stream_tid_.Ext__gz))		process = decompress_gz;
		else															throw Err_.new_unhandled(src_ext);
		Io_url trg_owner_dir = trg.OwnerDir();
		Io_mgr.Instance.CreateDirIfAbsent(trg_owner_dir);
		process.Run_mode_(process_run_mode);
		process.Run(src, trg, trg_owner_dir.Xto_api());
	}
}
