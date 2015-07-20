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
package gplx;
import gplx.core.threads.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.management.RuntimeErrorException;
import gplx.core.strings.*;
public class ProcessAdp implements GfoInvkAble, RlsAble {
	public boolean Enabled() {return enabled;} public ProcessAdp Enabled_(boolean v) {enabled = v; return this;} private boolean enabled = true;
	public byte Exe_exists() {return exe_exists;} public ProcessAdp Exe_exists_(byte v) {exe_exists = v; return this;} private byte exe_exists = Bool_.__byte;
	public Io_url Exe_url() {return exe_url;} public ProcessAdp Exe_url_(Io_url val) {exe_url = val; exe_exists = Bool_.__byte; return this;} Io_url exe_url;
	public String Args_str() {return args_str;} public ProcessAdp Args_str_(String val) {args_str = val; return this;} private String args_str = "";
	public Bry_fmtr Args_fmtr() {return args_fmtr;} Bry_fmtr args_fmtr = Bry_fmtr.new_("");
	public byte Run_mode() {return run_mode;} public ProcessAdp Run_mode_(byte v) {run_mode = v; return this;} private byte run_mode = Run_mode_sync_block;
	public static final byte Run_mode_async = 0, Run_mode_sync_block = 1, Run_mode_sync_timeout = 2;
	public int Exit_code() {return exit_code;} int exit_code;
	public boolean Exit_code_pass() {return exit_code == Exit_pass;}
	public String Rslt_out() {return rslt_out;} private String rslt_out;
	public Io_url Working_dir() {return working_dir;} public ProcessAdp Working_dir_(Io_url v) {working_dir = v; return this;} Io_url working_dir;
	public ProcessAdp Cmd_args(String cmd, String args) {this.Exe_url_(Io_url_.new_fil_(cmd)); this.args_fmtr.Fmt_(args); return this;}
	public ProcessAdp WhenBgn_add(GfoInvkAbleCmd cmd) {whenBgnList.Add(cmd); return this;}
	public ProcessAdp WhenBgn_del(GfoInvkAbleCmd cmd) {whenBgnList.Del(cmd); return this;}
	public int Thread_timeout() {return thread_timeout;} public ProcessAdp Thread_timeout_seconds_(int v) {thread_timeout = v * 1000; return this;} int thread_timeout = 0;
	public int Thread_interval() {return thread_interval;} public ProcessAdp Thread_interval_(int v) {thread_interval = v; return this;} int thread_interval = 20;
	public String Thread_kill_name() {return thread_kill_name;} public ProcessAdp Thread_kill_name_(String v) {thread_kill_name = v; return this;} private String thread_kill_name = "";
	public Io_url Tmp_dir() {return tmp_dir;} @gplx.Virtual public ProcessAdp Tmp_dir_(Io_url v) {tmp_dir = v; return this;} Io_url tmp_dir;
	private ProcessAdp WhenBgn_run() {return Invk_cmds(whenBgnList);} List_adp whenBgnList = List_adp_.new_();
	public ProcessAdp WhenEnd_add(GfoInvkAbleCmd cmd) {whenEndList.Add(cmd); return this;}
	public ProcessAdp WhenEnd_del(GfoInvkAbleCmd cmd) {whenEndList.Del(cmd); return this;}
	public Gfo_usr_dlg Prog_dlg() {return prog_dlg;} public ProcessAdp Prog_dlg_(Gfo_usr_dlg v) {prog_dlg = v; return this;} Gfo_usr_dlg prog_dlg;
	public String Prog_fmt() {return prog_fmt;} public ProcessAdp Prog_fmt_(String v) {prog_fmt = v; return this;} private String prog_fmt = "";	// NOTE: set to "", else cmds that do not set prog_fmt will fail on fmtr.Fmt(null)
	private GfoInvkAble owner;
	private ProcessAdp WhenEnd_run() {return Invk_cmds(whenEndList);} List_adp whenEndList = List_adp_.new_();
	private ProcessAdp Invk_cmds(List_adp list) {
		for (Object o : list)
			((GfoInvkAbleCmd)o).Invk();
		return this;
	}
	public ProcessAdp Run(Object... args) {
		if (String_.Len_eq_0(exe_url.Raw())) return this;	// noop if exe_url is "";
		if (!args_fmtr.Fmt_null()) {
			Bry_bfr tmp_bfr = Bry_bfr.new_();
			args_fmtr.Bld_bfr_many(tmp_bfr, args);
			args_str = tmp_bfr.Xto_str_and_clear();
		}
		prog_dlg.Log_many(GRP_KEY, "run", "running process: ~{0} ~{1}", exe_url.Raw(), args_str);
		exit_code = Exit_init;
		switch (run_mode) {
			case Run_mode_async:		return Run_async();
			case Run_mode_sync_timeout:	return Run_wait();
			case Run_mode_sync_block:	return Run_wait_sync();
			default:					throw Err_.new_unhandled(run_mode);
		}
	}
	public String[] Xto_process_bldr_args(String... args) {
		String args_str = args_fmtr.Bld_str_many(args);
		return Xto_process_bldr_args_utl(exe_url, args_str);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_enabled))					return enabled;
		else if	(ctx.Match(k, Invk_enabled_))					enabled = m.ReadBool("v");
		else if	(ctx.Match(k, Invk_cmd))						return exe_url.Raw();
		else if	(ctx.Match(k, Invk_cmd_))						this.Exe_url_(Bry_fmtr_eval_mgr_.Eval_url(cmd_url_eval, m.ReadBry("cmd")));
		else if	(ctx.Match(k, Invk_args))						return String_.new_u8(args_fmtr.Fmt());
		else if	(ctx.Match(k, Invk_args_))						args_fmtr.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_cmd_args_))					{this.Exe_url_(Bry_fmtr_eval_mgr_.Eval_url(cmd_url_eval, m.ReadBry("cmd"))); args_fmtr.Fmt_(m.ReadBry("args"));}
		else if	(ctx.Match(k, Invk_mode_))						run_mode = m.ReadByte("v");
		else if	(ctx.Match(k, Invk_timeout_))					thread_timeout = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_tmp_dir_))					tmp_dir = m.ReadIoUrl("v");
		else if	(ctx.Match(k, Invk_owner))						return owner;
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	static final String Invk_cmd = "cmd", Invk_cmd_ = "cmd_", Invk_args = "args", Invk_args_ = "args_", Invk_cmd_args_ = "cmd_args_", Invk_enabled = "enabled", Invk_enabled_ = "enabled_", Invk_mode_ = "mode_", Invk_timeout_ = "timeout_", Invk_tmp_dir_ = "tmp_dir_", Invk_owner = "owner";
	Bry_fmtr_eval_mgr cmd_url_eval;
	public static ProcessAdp ini_(GfoInvkAble owner, Gfo_usr_dlg usr_dlg, ProcessAdp process, Bry_fmtr_eval_mgr cmd_url_eval, byte run_mode, int timeout, String cmd_url_fmt, String args_fmt, String... args_keys) {
		process.Run_mode_(run_mode).Thread_timeout_seconds_(timeout);
		process.cmd_url_eval = cmd_url_eval;
		Io_url cmd_url = Bry_fmtr_eval_mgr_.Eval_url(cmd_url_eval, Bry_.new_u8(cmd_url_fmt));
		process.Exe_url_(cmd_url).Tmp_dir_(cmd_url.OwnerDir());
		process.Args_fmtr().Fmt_(args_fmt).Keys_(args_keys);
		process.owner = owner;
		process.Prog_dlg_(usr_dlg);
		return process;	// return process for chaining
	}
	public static String Escape_ampersands_if_process_is_cmd(boolean os_is_wnt, String exe_url, String exe_args) {
		return (	os_is_wnt
				&&	String_.Eq(exe_url, "cmd"))
			? String_.Replace(exe_args, "&", "^&")	// escape ampersands
			: exe_args
			;
	}
		private Bry_fmtr notify_fmtr = Bry_fmtr.new_("", "process_exe_name", "process_exe_args", "process_seconds"); Bry_bfr notify_bfr = Bry_bfr.reset_(255);
	public Process UnderProcess() {return process;} Process process;
	public void Rls() {if (process != null) process.destroy();}
	public ProcessAdp Run_wait_sync() {
		if (Env_.Mode_testing()) return Test_runs_add();
		Process_bgn();
		Process_start();
		Process_run_and_end();
		return this;
	}
	public ProcessAdp Run_start() {
		if (Env_.Mode_testing()) return Test_runs_add();
		Process_bgn();
		Process_start();
		return this;
	}
    public ProcessAdp Run_async() {
		if (Env_.Mode_testing()) return Test_runs_add();
        Process_bgn();
        Thread_ProcessAdp_async thread = new Thread_ProcessAdp_async(this);
        thread.start();
		return this;
    }
    public ProcessAdp Run_wait() {
		if (Env_.Mode_testing()) return Test_runs_add();
        int notify_interval = 100; int notify_checkpoint = notify_interval;
        int elapsed = 0;
		try {
	        Process_bgn();
	        Thread_ProcessAdp_sync thread = new Thread_ProcessAdp_sync(this);
	        thread.start();
	//        thread_timeout = 15000;
	        boolean thread_run = false;
	        notify_fmtr.Fmt_(prog_fmt);
	        while (thread.isAlive()) {
	        	thread_run = true;
	        	long prv = Env_.TickCount();
	        	Thread_adp_.Sleep(thread_interval);
//	        	try {thread.join(thread_interval);}
//	        	catch (InterruptedException e) {throw Err_.err_key_(e, "gplx.ProcessAdp", "thread interrupted at join");}
	        	long cur = Env_.TickCount();
	        	int dif = (int)(cur - prv); 
	        	elapsed += dif;
	        	if (prog_dlg != null) {
	        		if (elapsed > notify_checkpoint) {
	        			elapsed = notify_checkpoint;
	        			notify_checkpoint += notify_interval;
	        			notify_fmtr.Bld_bfr_many(notify_bfr, exe_url.NameAndExt(), args_str, elapsed / 1000);
	        			prog_dlg.Prog_none(GRP_KEY, "notify.prog", notify_bfr.Xto_str_and_clear());
	        		}
	        	}
	        	if (thread_timeout == 0) break;
	        	if (elapsed > thread_timeout) {
	        		thread.interrupt();
	        		thread.Cancel();
	        		try {thread.join();}
	        		catch (InterruptedException e) {throw Err_.new_exc(e, "core", "thread interrupted at timeout");}
	        		break;
	        	}
	        }
	        if (!thread_run) {
        		try {thread.join();}
        		catch (InterruptedException e) {throw Err_.new_exc(e, "core", "thread interrupted at join 2");}	        	
	        }
		} catch (Exception exc) {
			Tfds.Write(Err_.Message_gplx_full(exc));
		}
		if (elapsed != notify_checkpoint) {
			notify_fmtr.Bld_bfr_many(notify_bfr, exe_url.NameAndExt(), args_str, elapsed / 1000);
			if (prog_dlg != null) prog_dlg.Prog_none(GRP_KEY, "notify.prog", notify_bfr.Xto_str_and_clear());
		}
        return this;
    }
    public synchronized void Process_post(String result) {
        exit_code = process.exitValue();
        rslt_out = result;
        WhenEnd_run();
        process.destroy();    	
    }
    String Kill() {
    	if (thread_kill_name == String_.Empty) return "";
//		Runtime rt = Runtime.getRuntime();
		String kill_exe = "", kill_args = "";
		if (Op_sys.Cur().Tid_is_wnt()) {
			kill_exe = "taskkill";
			kill_args = "/F /IM ";
		}
		else {
			kill_exe = "kill";
			kill_args = "-9 ";			
		}
		kill_args += thread_kill_name;
		ProcessAdp kill_process = new ProcessAdp().Exe_url_(Io_url_.new_fil_(kill_exe)).Args_str_(kill_args).Thread_kill_name_("");
		boolean pass = kill_process.Run_wait().Exit_code_pass();
		return "killed|" + kill_exe + "|" + kill_args + "|" + pass + "|" + exe_url.Raw() + "|" + args_str;
    }
	synchronized void Process_bgn() {
		exit_code = Exit_init;
		rslt_out = "";
		WhenBgn_run();
		pb = new ProcessBuilder(Xto_process_bldr_args_utl(exe_url, args_str));
		pb.redirectErrorStream(true);								// NOTE: need to redirectErrorStream or rdr.readLine() will hang; see inkscape and Ostfriesland Verkehr-de.svg
		if (working_dir != null)
			pb.directory(new File(working_dir.Xto_api()));
		else if (!exe_url.OwnerDir().EqNull())						// only set workingDir if ownerDir is not null; NOTE: workingDir necessary for AdvMame; probably not a bad thing to do 
			pb.directory(new File(exe_url.OwnerDir().Xto_api()));
	}	ProcessBuilder pb;
	protected Process Process_start() {
		try {process = pb.start();}
		catch (IOException e) {
			java.util.List<String> command_list = pb.command();
			String[] command_ary = new String[command_list.size()];
			command_ary = command_list.toArray(command_ary);
			throw Err_.new_exc(e, "core", "process start failed", "args", String_.Concat_with_str(" ", command_ary));
		}
		return process;
	}
	void Process_run_and_end() {
		String_bldr sb = String_bldr_.new_();	
	    BufferedReader rdr = new BufferedReader(new InputStreamReader(process.getInputStream()));
        try {
    	    String line = "";
		    while ((line = rdr.readLine()) != null)
		    	sb.Add_str_w_crlf(line);
	    	process.waitFor();
        }
        catch (InterruptedException e) 	{throw Err_.new_exc(e, "core", "thread interrupted at wait_for", "exe_url", exe_url.Xto_api(), "exeArgs", args_str);}
        catch (IOException e) 			{throw Err_.new_exc(e, "core", "io error", "exe_url", exe_url.Xto_api(), "exeArgs", args_str);}
        exit_code = process.exitValue();
        WhenEnd_run();
        process.destroy();
    	rslt_out = sb.Xto_str_and_clear();    	
	}
	public void Process_term() {
		try {
			process.getInputStream().close();
			process.getErrorStream().close();
		} 	catch (IOException e) {}
		process.destroy();
	}
	public static void run_wait_(Io_url url) {
		ProcessAdp process = new ProcessAdp().Exe_url_(url);
		process.Run_start();
		process.Process_run_and_end();
		return;
	}
		public static final List_adp Test_runs = List_adp_.new_();
	private ProcessAdp Test_runs_add() {Test_runs.Add(exe_url.Raw() + " " + args_str); exit_code = Exit_pass; return this;}
	public static int run_wait_arg_(Io_url url, String arg) {
		ProcessAdp process = new ProcessAdp();
		process.Exe_url_(url).Args_str_(arg).Run_wait();
		return process.Exit_code();
	}
	private static final String GRP_KEY = "gplx.process";
	public static final int Exit_pass = 0, Exit_init = -1;
	public static String[] Xto_process_bldr_args_utl(Io_url exe_url, String args_str) {		
		List_adp list = List_adp_.new_();
		list.Add(exe_url.Xto_api());
		String_bldr sb = String_bldr_.new_();
		int len = String_.Len(args_str);
		boolean in_quotes = false;
		for (int i = 0; i < len; i++) {
			char c = String_.CharAt(args_str, i);
			if (c == ' ' && !in_quotes) {	// space encountered; assume arg done
				list.Add(sb.XtoStr());
				sb.Clear();
			}
			else if (c == '"')				// NOTE: ProcessBuilder seems to have issues with quotes; do not call sb.Add()
				in_quotes = !in_quotes;
			else
				sb.Add(c);
		}
		if (sb.Has_some()) list.Add(sb.XtoStr());
		return list.To_str_ary();
	}
}
class Thread_ProcessAdp_async extends Thread {
	public Thread_ProcessAdp_async(ProcessAdp process_adp) {this.process_adp = process_adp;} ProcessAdp process_adp;
	public boolean Done() {return done;} boolean done = false;
	public void Cancel() {process_adp.UnderProcess().destroy();}
    public void run() {
    	process_adp.Run_wait();
    }
}
class Thread_ProcessAdp_sync extends Thread {
	public Thread_ProcessAdp_sync(ProcessAdp process_adp) {this.process_adp = process_adp;} private final ProcessAdp process_adp;
	public boolean Done() {return done;} private boolean done = false;
	public void Cancel() {
		process_adp.UnderProcess().destroy();
	}
    public synchronized void run() {
    	done = false;
    	try {
			Process process = process_adp.Process_start();
			StreamGobbler input_gobbler = new StreamGobbler("input", process.getInputStream());
			StreamGobbler error_gobbler = new StreamGobbler("error", process.getErrorStream());
			input_gobbler.start();
			error_gobbler.start();
	        try {process.waitFor();}
	        catch (InterruptedException e) 	{
	        	this.Cancel();
	        	String kill_rslt = process_adp.Kill();        	
	            process_adp.Process_post(kill_rslt);
	        	done = false;
	        	return;
	        }
	        while (input_gobbler.isAlive()) {
	        	try {input_gobbler.join(50);}
	        	catch (InterruptedException e) {throw Err_.new_exc(e, "core", "thread interrupted at input gobbler");}
	        }
	        while (error_gobbler.isAlive()) {
	        	try {error_gobbler.join(50);}
	        	catch (InterruptedException e) {throw Err_.new_exc(e, "core", "thread interrupted at error gobbler");}
	        }
	        String result = input_gobbler.Rslt() + "\n" + error_gobbler.Rslt();
	        process_adp.Process_post(result);
    	} 	catch (Exception e) {	// NOTE: warn; do not throw, else multiple errors if timidity not available; PAGE:fr.u:Pentatoniques_altérées/Gammes_avec_deux_notes_altérées DATE:2015-05-08
    		Gfo_usr_dlg_.I.Warn_many("", "", "process.sync failed; cmd=~{0} args=~{1}", process_adp.Exe_url().Raw(), process_adp.Args_str());    		
    	}
    	finally {done = true;}
    }
}
class StreamGobbler extends Thread {
	private final String name; private final InputStream stream;
	public StreamGobbler (String name, InputStream stream) {this.name = name; this.stream = stream;}
	public String Rslt() {return rslt;} private String rslt;
	public void run () {
		try {
			String_bldr sb = String_bldr_.new_();
			InputStreamReader isr = new InputStreamReader(stream);
			BufferedReader br = new BufferedReader(isr);
			while (true) {
				String s = br.readLine();
				if (s == null) break;
				sb.Add(s);
			}
			stream.close();
			rslt = sb.Xto_str_and_clear();
		}
		catch (Exception e) {throw Err_.new_exc(e, "io", "failed reading stream", "name", name);}
	}
}
