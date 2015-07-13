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
public class Env_ {
	public static void Init(String[] args, String appNameAndExt, Class<?> type) {
		mode_testing = false;
		mode_debug = String_.In("GPLX_DEBUG_MODE_ENABLED", args);
		appArgs = args;
		appUrl = JarAdp_.Url_type(type).OwnerDir().GenSubFil(appNameAndExt);
	}
	public static void Init_swt(String[] args, Class<?> type) {	// DATE:2014-06-23
		mode_testing = false;
		mode_debug = String_.In("GPLX_DEBUG_MODE_ENABLED", args);
		appArgs = args;
		appUrl = JarAdp_.Url_type(type);
	}
	public static void Init_drd() {
		mode_testing = mode_debug = false;
	}
	public static void Init_testing() {mode_testing = true;}
	public static void Init_testing_n_() {mode_testing = false;}
	public static boolean Mode_testing()	{return mode_testing;}	static boolean mode_testing = true;
	public static boolean Mode_debug()		{return mode_debug;}	static boolean mode_debug = false;
	public static String[] AppArgs() {return appArgs;} static String[] appArgs;
	public static Io_url AppUrl() {
		if (mode_testing) return Io_url_.mem_fil_("mem/testing.jar");
		if (appUrl == Io_url_.Empty) throw Exc_.new_("Env_.Init was not called");
		return appUrl;
	}	static Io_url appUrl = Io_url_.Empty;
	public static void Exit() {Exit_code(0);}
	public static void Exit_code(int code) {System.exit(code);}	
	public static String UserName() {return System.getProperty("user.name");}	
	public static void GarbageCollect() {if (mode_testing) return; System.gc();}				
	public static long TickCount() {return TickCount_Test >= 0 ? TickCount_Test : System.currentTimeMillis();} 
	public static int TickCount_elapsed_in_sec(long time_bgn) {return (int)(Env_.TickCount() - time_bgn) / 1000;}
	public static int TickCount_elapsed_in_frac(long time_bgn) {return (int)(Env_.TickCount() - time_bgn);}
	public static long TickCount_Test = -1;	// in milliseconds
	public static void TickCount_normal() {TickCount_Test = -1;}
	public static long System_cpu_count() {return Runtime.getRuntime().availableProcessors();}	
	public static long System_memory_max() {return Runtime.getRuntime().maxMemory();}	
	public static long System_memory_total() {return Runtime.getRuntime().totalMemory();}	
	public static long System_memory_free() {return Runtime.getRuntime().freeMemory();}	
	public static final String LocalHost = "127.0.0.1";
	public static String NewLine_lang() {return mode_testing ? "\n" : "\n";}	
	public static String GenHdr(boolean forSourceCode, String programName, String hdr_bgn, String hdr_end) {
		String newLine = Op_sys.Lnx.Nl_str();
		String lineEnd = Op_sys.Lnx.Nl_str();
		String codeBgn = forSourceCode ? "/*" + newLine : "";
		String codeEnd = forSourceCode ? "*/" + newLine : "";
		String codeHdr = forSourceCode ? "This file is part of {0}." + newLine + newLine : "";
		String fmt = String_.Concat
			( codeBgn
			, codeHdr
			, hdr_bgn
			, "Copyright (c) 2012 gnosygnu@gmail.com", newLine
			, newLine
			, "This program is free software: you can redistribute it and/or modify", lineEnd
			, "it under the terms of the GNU Affero General Public License as", lineEnd
			, "published by the Free Software Foundation, either version 3 of the", lineEnd
			, "License, or (at your option) any later version.", newLine
			, newLine
			, "This program is distributed in the hope that it will be useful,", lineEnd
			, "but WITHOUT ANY WARRANTY; without even the implied warranty of", lineEnd
			, "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the", lineEnd
			, "GNU Affero General Public License for more details.", newLine
			, newLine
			, "You should have received a copy of the GNU Affero General Public License", lineEnd
			, "along with this program.  If not, see <http://www.gnu.org/licenses/>.", newLine
			, codeEnd
			, hdr_end
			);
		return String_.Format(fmt, programName);
	}
	public static String Env_prop__user_language() {return Env_prop(Env_prop_key__user_language);}
	public static String Env_prop(String key) {
				return System.getProperty(key);
			}	static final String Env_prop_key__user_language = "user.language";
	public static void Term_add(GfoInvkAble invk, String cmd) {
				Thread_adp thread = Thread_adp_.invk_(invk, cmd);
		Runtime.getRuntime().addShutdownHook(thread.Under_thread());
			}
}
