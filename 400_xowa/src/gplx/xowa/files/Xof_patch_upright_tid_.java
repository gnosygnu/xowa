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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
import gplx.core.bits.*;
public class Xof_patch_upright_tid_ {
	public static final int Tid_unpatched = 0, Tid_use_thumb_w = 1, Tid_fix_default = 2;
	public static final int Tid_all = Tid_use_thumb_w | Tid_fix_default;
	public static int Merge(boolean use_thumb_w, boolean fix_default) {
		if		(use_thumb_w && fix_default)	return Bitmask_.Add_int(Tid_use_thumb_w, Tid_fix_default);
		else if (use_thumb_w)					return Tid_use_thumb_w;
		else if (fix_default)					return Tid_fix_default;
		else									return Tid_unpatched;
	}
	public static boolean Split_use_thumb_w(int tid)		{return Bitmask_.Has_int(tid, Tid_use_thumb_w);}
	public static boolean Split_fix_default(int tid)		{return Bitmask_.Has_int(tid, Tid_fix_default);}
}
