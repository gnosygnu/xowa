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
package gplx.xowa.bldrs; import gplx.*; import gplx.xowa.*;
public class Db_idx_mode {
	private int tid;
	Db_idx_mode(int tid) {this.tid = tid;}
	public boolean Tid_is_bgn()	{return tid == Tid_bgn;}
	public boolean Tid_is_end()	{return tid == Tid_end;}
	public boolean Tid_is_skip()	{return tid == Tid_skip;}
	private static final int Tid_skip = 0, Tid_bgn = 1, Tid_end = 2;
	private static final String Key_skip = "skip", Key_bgn = "bgn", Key_end = "end";
	public static final Db_idx_mode
	  Itm_skip	= new Db_idx_mode(Tid_skip)
	, Itm_bgn	= new Db_idx_mode(Tid_bgn)
	, Itm_end	= new Db_idx_mode(Tid_end)
	;
	public static Db_idx_mode Xto_itm(String key) {
		if		(String_.Eq(key, Key_skip))		return Itm_skip;
		else if	(String_.Eq(key, Key_bgn))		return Itm_bgn;
		else if	(String_.Eq(key, Key_end))		return Itm_end;
		else									throw Err_.new_unhandled(key);
	}
}
