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
public class App_cmd_arg {
	App_cmd_arg(byte tid, byte val_tid, String key, boolean reqd) {this.tid = tid; this.val_tid = val_tid; this.key = key; this.reqd = reqd;}
	public byte Tid() {return tid;} private byte tid;
	public byte Val_tid() {return val_tid;} public App_cmd_arg Val_tid_(byte v) {val_tid = v; return this;} private byte val_tid;
	public String Val_tid_str() {
		switch (val_tid) {
			case Val_tid_string:		return "string";
			case Val_tid_yn:			return "yes_no";
			case Val_tid_url:			return "path";
			default:					return "unknown";
		}
	}
	public String Key() {return key;} private String key;
	public boolean Reqd() {return reqd;} private boolean reqd;
	public String Reqd_str() {return reqd ? "required" : "optional";}
	public boolean Dirty() {return dirty;} public App_cmd_arg Dirty_(boolean v) {this.dirty = v; return this;} private boolean dirty;
	public String Note() {return note;} public App_cmd_arg Note_(String v) {note = v; return this;} private String note;
	public String Example() {return example;}
	public App_cmd_arg Example_(String v) {example = v; return this;} private String example;
	public App_cmd_arg Example_url_(String v) {
		Example_(v);
		val_tid = Val_tid_url;
		return this;
	}
	public App_cmd_arg Example_list_str_(String v) {
		example = String_.Concat_with_obj(" ", v);
		val_tid = Val_tid_list_string;
		return this;
	}
	public Object Val() {return val;} public App_cmd_arg Val_(Object v) {this.val = v; return this;} Object val;
	public Object Dflt() {return dflt;} public App_cmd_arg Dflt_(Object v) {dflt = v; return this;} Object dflt;
	public boolean Val_as_bool() {return Bool_.cast(val);}
	public String Val_as_str_or(String or) {return val == null ? or : (String)val;}
	public String Val_as_str() {return (String)val;}
	public int Val_as_int_or(int or) {return val == null ? or : Int_.parse_or((String)val, or);}
	public Io_url Val_as_url_rel_dir_or(Io_url owner_dir, Io_url or) {return Val_as_url_rel_url_or(owner_dir, or, true);}
	public Io_url Val_as_url_rel_fil_or(Io_url owner_dir, Io_url or) {return Val_as_url_rel_url_or(owner_dir, or, false);}
	public Io_url Val_as_url_rel_url_or(Io_url owner_dir, Io_url or, boolean dir) {return Val_as_url_rel_url_or(Val_as_str(), owner_dir, or, dir);}
	public boolean Parse(App_cmd_mgr mgr, String[] s_ary) {
		dirty = true;			
		String s = s_ary.length == 0 ? "" : s_ary[0];
		switch (val_tid) {
			case Val_tid_string:
				val = s;
				break;
			case Val_tid_url:	// NOTE: do not parse urls as it can either be absolute (C:\dir\fil.txt) or relative (fil.txt). relative cannot be parsed without knowing owner dir
				val = s;
				break;
			case Val_tid_yn:
				int v_int = Yn.parse_as_int(s);
				if (v_int == Bool_.__int) {return mgr.Errs_add(Err_parse_yn, "value must be either y or n: ~{0}", s);}
				val = v_int == Bool_.Y_int;
				break;
		}
		return true;
	}	public static final String Err_parse_yn = "parse_yn";
	public App_cmd_arg Clone() {
		App_cmd_arg rv = new App_cmd_arg(tid, val_tid, key, reqd);
		rv.val = val;
		rv.dflt = dflt;
		rv.note = note;
		rv.example = example;
		return rv;
	}
	public static App_cmd_arg req_(String key) {return new App_cmd_arg(Tid_general, Val_tid_string, key, true);}
	public static App_cmd_arg opt_(String key) {return new App_cmd_arg(Tid_general, Val_tid_string, key, false);}
	public static App_cmd_arg new_(String key, boolean reqd) {return new App_cmd_arg(Tid_general, Val_tid_string, key, reqd);}
	public static App_cmd_arg sys_help_() {return sys_help_("help");}
	public static App_cmd_arg sys_help_(String key) {return new App_cmd_arg(Tid_help, Val_tid_string, key, false);}
	public static App_cmd_arg sys_header_(String key) {return new App_cmd_arg(Tid_header, Val_tid_yn, key, false);}
	public static App_cmd_arg sys_args_(String key) {return new App_cmd_arg(Tid_args, Val_tid_yn, key, false);}
	public static final byte Tid_general = 0, Tid_help = 1, Tid_header = 2, Tid_args = 3;
	public static final byte Val_tid_string = 0, Val_tid_yn = 1, Val_tid_url = 2, Val_tid_list_string = 3;

	public static Io_url Val_as_url_rel_url_or(String val_str, Io_url owner_dir, Io_url or, boolean dir) {
		if (val_str == null) return or;
		byte val_has_dir = Op_sys.Tid_nil;	// if val_str is dir, use it literally (only checking for closing dir_spr); if it's just a name, assume a simple relative path
		if		(String_.Has(val_str, Op_sys.Lnx.Fsys_dir_spr_str()))
			val_has_dir = Op_sys.Tid_lnx;
		else if (String_.Has(val_str, Op_sys.Wnt.Fsys_dir_spr_str()))
			val_has_dir = Op_sys.Tid_wnt;
		if (val_has_dir != Op_sys.Tid_nil) {
			if (dir) {	// NOTE: need to do extra logic to guarantee trailing "/"; JAVA:7 apparently strips "/dir/" to "/dir" when passed in as argument; DATE:2013-03-20
				String val_dir_spr = val_has_dir == Op_sys.Tid_lnx ? Op_sys.Lnx.Fsys_dir_spr_str() : Op_sys.Wnt.Fsys_dir_spr_str();
				if (!String_.Has_at_end(val_str, val_dir_spr))
					val_str += val_dir_spr;
				return Io_url_.new_dir_(val_str);
			}
			else
				return Io_url_.new_fil_(val_str); 
		}
		else
			return dir ? owner_dir.GenSubDir(val_str) : owner_dir.GenSubFil(val_str); 
	}
}