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
package gplx.core.ios.zips; import gplx.*; import gplx.core.*; import gplx.core.ios.*;
import gplx.core.threads.*; import gplx.core.progs.*;
public class Io_zip_decompress_task implements GfoInvkAble {
//		private Gfo_prog_ui prog_ui;
	private Io_url src_fil, trg_dir;		
	private boolean async;
	private boolean canceled, paused;
//		private long src_fil_len = 0;
//		private long prog__cur = 0;
	private Io_zip_decompress_cmd cmd;
	public void Init(boolean async, Io_zip_decompress_cmd cmd, Io_url src_fil, Io_url trg_dir) {
		this.async = async; this.cmd = cmd; // this.prog_ui = prog_ui;
		this.src_fil = src_fil; this.trg_dir = trg_dir;
	}
	public String Resume__entry() {return resume__entry;} private String resume__entry;
	public long   Resume__bytes() {return resume__bytes;} private long resume__bytes;
	public boolean Canceled() {return canceled;}
	public boolean Paused() {return paused;}
	public void Prog__start() {
//			this.src_fil_len = Io_mgr.Instance.QueryFil(src_fil).Size();
		this.resume__entry = null;
		this.resume__bytes = 0;
		// load resume
		// prog_ui.Prog__init(src_fil_len);
		Thread_adp_.Run_cmd(async, "zip.decompress:" + src_fil.Raw(), this, Invk__unzip);
	}
	public byte Prog__update(long v) {
//			prog__cur += v;
		// prog_ui.Prog__update_val(prog__cur, src_fil_len);
		if		(paused)		return Status__paused;
		else if (canceled)		return Status__canceled;
		else					return Status__ok;
	}
	public boolean Prog__update_name(String name) {
		// prog_ui.Prog__update_misc("name", name);
		return canceled;
	}
	public void Prog__pause()	{
		paused = true;
		// save resume
	}
	public void Prog__resume() {
		paused = false;
	}
	public void Prog__cancel()	{
		canceled = true;
		// discard resume
	}
	public void Unzip() {
		cmd.Decompress__exec(this, src_fil, trg_dir);
//			prog_ui.Prog__end();
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__unzip))		this.Unzip();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk__unzip = "unzip";
	public static final byte Status__ok = 0, Status__paused = 1, Status__canceled = 2;
}
/*
class Notifier {
	public Prog_update_bgn() {
		for(Object o : list)
			o.Prog_update_bgn();
	}
	public Prog_update_end() {
		for(Object o : list)
			o.Prog_update_bgn();
	}
	public Prog_update_val(long cur, long all) {
		for(Object o : list)
			o.Prog_update_bgn();
	}
	public Prog_update_name(String name) {
		for(Object o : list)
			o.Prog_update_bgn();
	}
}
class unzip_prog_bar {
	public Prog_update_val(long itm_cur, long itm_all) {
		cur += itm_cur;
		double pct = (cur / all) * 100;
		pbar.style.width = pct + "%";
		pbar.text = Io_size.To_str(cur);			
	}
	public Prog_update_name(String name) {
	    file.text = "unzipping:" + name;
	}
	public Prog_update_bgn(long cur) {
		if (all == 0) {
		    panels.add(this);
		}
	    all += cur;
	}
	public Prog_update_end() {
		if (cur >= all) {
		    panels.del(this);
		}
	}	
}
class pack_prog_bar {
	public Prog_update_val(long cur, long all) {
		double pct = (cur / all) * 100;
		pbar.style.width = pct + "%";
		pbar.text = Io_size.To_str(cur);
	}
	public Prog_update_name(String name) {
	    file.text = "unzipping:" + name;
	}
	public Prog_update_bgn() {
		pbar.visible = true;
	    file.visible = true;
	    file.text = "unzipping: " + ;
	}
	public Prog_update_end() {
		pbar.visible = false;
	    file.visible = false;
	}
}
*/
