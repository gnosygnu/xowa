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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
public class Xot_defn_ {
	public static final Xot_defn Null = Xot_defn_null.Instance;
	public static final byte 
	  Tid_null = 0
	, Tid_func = 1
	, Tid_tmpl = 2
	, Tid_subst = Xol_kwd_grp_.Id_subst
	, Tid_safesubst = Xol_kwd_grp_.Id_safesubst
	, Tid_raw = Xol_kwd_grp_.Id_raw
	, Tid_msg = Xol_kwd_grp_.Id_msg
	, Tid_msgnw = Xol_kwd_grp_.Id_msgnw
	;
	public static boolean Tid_is_substing(byte v) {
		switch (v) {
			case Tid_subst: return true; // NOTE: safesubst should not return true, else stack overflow; PAGE:en.w:Wikipedia:WikiProject_Celtic_history_and_culture DATE:2015-01-02
			default: return false;
		}
	}
}
