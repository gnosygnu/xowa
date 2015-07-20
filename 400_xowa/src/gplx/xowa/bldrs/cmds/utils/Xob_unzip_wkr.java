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
package gplx.xowa.bldrs.cmds.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
public class Xob_unzip_wkr {
	private ProcessAdp decompress_bz2, decompress_zip, decompress_gz, process;
	public int Process_exit_code() {return process.Exit_code();}
	public byte Process_run_mode() {return process_run_mode;} public Xob_unzip_wkr Process_run_mode_(byte v) {process_run_mode = v; return this;} private byte process_run_mode = ProcessAdp.Run_mode_async;
	public Xob_unzip_wkr Init(Xoae_app app) {return Init(app.Prog_mgr().App_decompress_bz2(), app.Prog_mgr().App_decompress_zip(), app.Prog_mgr().App_decompress_gz());}
	public Xob_unzip_wkr Init(ProcessAdp decompress_bz2, ProcessAdp decompress_zip, ProcessAdp decompress_gz) {
		this.decompress_bz2 = decompress_bz2;
		this.decompress_zip = decompress_zip;
		this.decompress_gz  = decompress_gz;
		return this;
	}
	public void Decompress(Io_url src, Io_url trg) {
		String src_ext = src.Ext();
		if		(String_.Eq(src_ext, gplx.ios.Io_stream_.Ext_bz2))		process = decompress_bz2;
		else if	(String_.Eq(src_ext, gplx.ios.Io_stream_.Ext_zip))		process = decompress_zip;
		else if	(String_.Eq(src_ext, gplx.ios.Io_stream_.Ext_gz))		process = decompress_gz;
		else															throw Err_.new_unhandled(src_ext);
		Io_url trg_owner_dir = trg.OwnerDir();
		Io_mgr.I.CreateDirIfAbsent(trg_owner_dir);
		process.Run_mode_(process_run_mode);
		process.Run(src, trg, trg_owner_dir.Xto_api());
	}
}
