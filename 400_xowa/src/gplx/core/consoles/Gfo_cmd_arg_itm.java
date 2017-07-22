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
package gplx.core.consoles; import gplx.*; import gplx.core.*;
public class Gfo_cmd_arg_itm {
	public Gfo_cmd_arg_itm(int tid, boolean reqd, String key, int val_tid) {this.tid = tid; this.reqd = reqd; this.key = key; this.val_tid = val_tid;}
	public int		Tid()		{return tid;} private final    int tid;
	public boolean		Reqd()		{return reqd;} private final    boolean reqd;
	public String	Key()		{return key;} private final    String key;
	public int		Val_tid()	{return val_tid;} private int val_tid;
	public Object	Val()		{return val;} public Gfo_cmd_arg_itm Val_(Object v) {this.val = v; dirty = true; return this;} private Object val;
	public String	Note()		{return note;} public Gfo_cmd_arg_itm Note_(String v) {note = v; return this;} private String note = "";
	public String	Example()	{return example;} public Gfo_cmd_arg_itm Example_(String v) {example = v; return this;} private String example = "";
	public Object	Dflt()		{return dflt;} public Gfo_cmd_arg_itm Dflt_(Object v) {dflt = v; return this;} private Object dflt;
	public boolean		Dirty()		{return dirty;} private boolean dirty;
	public void Clear() {
		dirty = false;
		val = null;
	}
	public Gfo_cmd_arg_itm Example_url_(String v) {
		Example_(v);
		this.val_tid = Gfo_cmd_arg_itm_.Val_tid_url;
		return this;
	}
	public String Reqd_str() {return reqd ? "required" : "optional";}
	public String Val_tid_str() {
		switch (val_tid) {
			case Gfo_cmd_arg_itm_.Val_tid_string:		return "string";
			case Gfo_cmd_arg_itm_.Val_tid_yn:			return "y/n";
			case Gfo_cmd_arg_itm_.Val_tid_url:			return "path";
			default:									return "unknown";
		}
	}
	public boolean			Val_as_bool()				{return Bool_.Cast(val);}
	public boolean			Val_as_bool_or(boolean or)		{return val == null ? or : String_.Eq((String)val, "y");}
	public String		Val_as_str_or(String or)	{return val == null ? or : (String)val;}
	public String		Val_as_str()				{return (String)val;}
	public int			Val_as_int_or(int or)		{return val == null ? or : Int_.parse_or((String)val, or);}
	public Io_url		Val_as_url__rel_dir_or(Io_url owner_dir, Io_url or) {return Val_as_url__rel_url_or(Bool_.Y, owner_dir, or);}
	public Io_url		Val_as_url__rel_fil_or(Io_url owner_dir, Io_url or) {return Val_as_url__rel_url_or(Bool_.N, owner_dir, or);}
	public Io_url		Val_as_url__rel_url_or(boolean to_dir, Io_url owner_dir, Io_url or) {return Gfo_cmd_arg_itm_.Val_as_url__rel_url_or(Val_as_str(), to_dir, owner_dir, or);}
}
