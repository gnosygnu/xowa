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
import gplx.core.envs.*;
public class Gfo_cmd_arg_itm_ {
	public static final int Tid_general = 0, Tid_system = 1;
	public static final int Val_tid_string = 0, Val_tid_yn = 1, Val_tid_url = 2, Val_tid_list_string = 3;
	public static Gfo_cmd_arg_itm req_(String key)									{return new Gfo_cmd_arg_itm(Tid_general, Bool_.Y, key, Val_tid_string);}
	public static Gfo_cmd_arg_itm opt_(String key)									{return new Gfo_cmd_arg_itm(Tid_general, Bool_.N, key, Val_tid_string);}
	public static Gfo_cmd_arg_itm new_(String key, boolean reqd, int val_tid)			{return new Gfo_cmd_arg_itm(Tid_general, reqd	, key, val_tid);}
	public static Gfo_cmd_arg_itm sys_(String key)									{return new Gfo_cmd_arg_itm(Tid_system , Bool_.N, key, Val_tid_yn);}
	public static Gfo_cmd_arg_itm new_(int tid, String key, boolean reqd, int val_tid)	{return new Gfo_cmd_arg_itm(tid			, reqd	, key, val_tid);}
	public static Io_url Val_as_url__rel_url_or(String raw, boolean to_dir, Io_url owner_dir, Io_url or) {
		if (raw == null) return or;
		byte val_has_dir = Op_sys.Tid_nil;	// if raw is to_dir, use it literally (only checking for closing dir_spr); if it's just a name, assume a simple relative path
		if		(String_.Has(raw, Op_sys.Lnx.Fsys_dir_spr_str()))
			val_has_dir = Op_sys.Tid_lnx;
		else if (String_.Has(raw, Op_sys.Wnt.Fsys_dir_spr_str()))
			val_has_dir = Op_sys.Tid_wnt;
		if (val_has_dir != Op_sys.Tid_nil) {
			if (to_dir) {	// NOTE: need to do extra logic to guarantee trailing "/"; JAVA:7 apparently strips "/to_dir/" to "/to_dir" when passed in as argument; DATE:2013-03-20
				String val_dir_spr = val_has_dir == Op_sys.Tid_lnx ? Op_sys.Lnx.Fsys_dir_spr_str() : Op_sys.Wnt.Fsys_dir_spr_str();
				if (!String_.Has_at_end(raw, val_dir_spr))
					raw += val_dir_spr;
				return Io_url_.new_dir_(raw);
			}
			else
				return Io_url_.new_fil_(raw); 
		}
		else
			return to_dir ? owner_dir.GenSubDir(raw) : owner_dir.GenSubFil(raw); 
	}
}