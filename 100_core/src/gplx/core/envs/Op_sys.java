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
package gplx.core.envs; import gplx.*; import gplx.core.*;
public class Op_sys {
	Op_sys(byte tid, byte sub_tid, String os_name, byte bitness, String nl_str, byte fsys_dir_spr_byte, boolean fsys_case_match) {
		this.tid = tid; this.sub_tid = sub_tid; this.os_name = os_name; this.bitness = bitness; this.nl_str = nl_str; this.fsys_dir_spr_byte = fsys_dir_spr_byte;  this.fsys_dir_spr_str = Char_.To_str((char)fsys_dir_spr_byte); this.fsys_case_match = fsys_case_match;
	}
	public byte Tid() {return tid;} private final    byte tid;
	public byte Sub_tid() {return sub_tid;} private final    byte sub_tid;
	public String Os_name() {return os_name;} private String os_name;
	public byte Bitness() {return bitness;} private final    byte bitness;
	public String Bitness_str() {return (bitness == Bitness_32 ? "32" : "64");}
	public String Nl_str() {return nl_str;} private final    String nl_str;
	public String Fsys_dir_spr_str() {return fsys_dir_spr_str;} private final    String fsys_dir_spr_str;
	public byte Fsys_dir_spr_byte() {return fsys_dir_spr_byte;} private final    byte fsys_dir_spr_byte;
	public String Fsys_http_frag_to_url_str(String raw) {return fsys_dir_spr_byte == Byte_ascii.Slash ? raw : String_.Replace(raw, Lnx.Fsys_dir_spr_str(), fsys_dir_spr_str);}
	public boolean Fsys_case_match() {return fsys_case_match;} private final    boolean fsys_case_match;
	public String Fsys_case_match_str(String s) {return String_.CaseNormalize(fsys_case_match, s);}
	public boolean Tid_is_wnt() {return tid == Tid_wnt;}
	public boolean Tid_is_lnx() {return tid == Tid_lnx;}
	public boolean Tid_is_osx() {return tid == Tid_osx;}
	public boolean Tid_is_drd() {return tid == Tid_drd;}
	public String To_str() {return os_name + Bitness_str();}

	public static final byte Tid_nil = 0, Tid_wnt = 1, Tid_lnx = 2, Tid_osx = 3, Tid_drd = 4, Tid_arm = 5;
	public static final byte Sub_tid_unknown = 0, Sub_tid_win_xp = 1, Sub_tid_win_7 = 2, Sub_tid_win_8 = 3;
	public static final byte Bitness_32 = 1, Bitness_64 = 2;
	public static final    char Nl_char_lnx = '\n';
	public static final byte Dir_spr__lnx = Byte_ascii.Slash, Dir_spr__wnt = Byte_ascii.Backslash;
	public static final    Op_sys Lnx = new_unx_flavor_(Tid_lnx, "linux", Bitness_32);
	public static final    Op_sys Osx = new_unx_flavor_(Tid_osx, "macosx", Bitness_32);
	public static final    Op_sys Drd = new_unx_flavor_(Tid_drd, "android", Bitness_32);
	public static final    Op_sys Wnt = new_wnt_(Sub_tid_unknown, Bitness_32);
	public static Op_sys Cur() {return cur_op_sys;} static Op_sys cur_op_sys = new_auto_identify_();
	public static String Fsys_path_to_lnx(String v) {
		return cur_op_sys.Tid_is_wnt() ? String_.Replace(v, Wnt.fsys_dir_spr_str, Lnx.fsys_dir_spr_str) : v; 
	}
	public static String Fsys_path_to_wnt(String v) {
		return cur_op_sys.Tid_is_wnt() ? String_.Replace(v, Lnx.fsys_dir_spr_str, Wnt.fsys_dir_spr_str) : v; 
	}
	private static Op_sys new_wnt_(byte bitness, byte sub_tid)						{return new Op_sys(Tid_wnt	, sub_tid			, "windows", bitness, "\r\n", Dir_spr__wnt	, Bool_.N);}
	private static Op_sys new_unx_flavor_(byte tid, String os_name, byte bitness)	{return new Op_sys(tid		, Sub_tid_unknown	, os_name  , bitness, "\n"  , Dir_spr__lnx	, Bool_.Y);}
	public static void Cur_(int tid) {
		switch (tid) {
			case Tid_wnt: cur_op_sys = Wnt; break;
			case Tid_lnx: cur_op_sys = Lnx; break;
			case Tid_osx: cur_op_sys = Osx; break;
			case Tid_drd: cur_op_sys = Drd; break;
			default: throw Err_.new_unhandled_default(tid);
		}
	} 
		static final String GRP_KEY = "gplx.op_sys";
//	public static Op_sys Cur_() {cur_op_sys = new_auto_identify_(); return cur_op_sys;}
	static Op_sys new_auto_identify_() {
		String os_name = "";
		try {
		String bitness_str = System.getProperty("sun.arch.data.model"); if (bitness_str == null) return Drd;
		bitness_str = bitness_str.toLowerCase();
		byte bitness_byte = Bitness_32;
		if		(String_.Eq(bitness_str, "32")) 		bitness_byte = Bitness_32;
		else if	(String_.Eq(bitness_str, "64")) 		bitness_byte = Bitness_64;
		else 											throw Err_.new_wo_type("unknown bitness; expecting 32 or 64; System.getProperty(\"bit.level\")", "val", bitness_str);
		
		os_name = System.getProperty("os.name").toLowerCase();
		String os_arch = System.getProperty("os.arch").toLowerCase();
		if (String_.Eq(os_arch, "arm"))	return new_unx_flavor_(Tid_arm, os_name, bitness_byte);	// EX:arm; DATE:2016-09-23;"arm" and "32"		
		if 		(String_.Has_at_bgn(os_name, "win")) {
			String os_version = System.getProperty("os.version").toLowerCase();//  "Windows 7".equals(osName) && "6.1".equals(osVersion);
			byte sub_tid = Sub_tid_unknown;
			if 		(String_.Eq(os_name, "windows xp") && String_.Eq(os_version, "5.1"))	sub_tid = Sub_tid_win_xp;
			else if (String_.Eq(os_name, "windows 7")  && String_.Eq(os_version, "6.1"))	sub_tid = Sub_tid_win_7;
			else if (String_.Eq(os_name, "windows 8"))										sub_tid = Sub_tid_win_8;
			return new_wnt_(bitness_byte, sub_tid);
		}
		else if	(String_.Eq(os_name, "linux")) 			return new_unx_flavor_(Tid_lnx, os_name, bitness_byte);
		else if	(String_.Has_at_bgn(os_name, "mac")) 	return new_unx_flavor_(Tid_osx, os_name, bitness_byte);	// EX:Mac OS X
		else											throw Err_.new_wo_type("unknown os_name; expecting windows, linux, mac; System.getProperty(\"os.name\")", "val", os_name);
		} catch (Exception exc) {Drd.os_name = os_name; return Drd;}
	}
	public static void Cur_is_drd_() {cur_op_sys = Drd;}
	}
