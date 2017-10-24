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
package gplx.core.brys.fmtrs; import gplx.*; import gplx.core.*; import gplx.core.brys.*;
import gplx.langs.gfs.*;
public class Bry_fmtr_eval_mgr_gfs implements Bry_fmtr_eval_mgr {
	public boolean Enabled() {return enabled;} public void Enabled_(boolean v) {enabled = v;} private boolean enabled;
	public byte[] Eval(byte[] cmd) {			
		return enabled ? Bry_.new_u8(Object_.Xto_str_strict_or_null_mark(GfsCore.Instance.ExecText(String_.new_u8(cmd)))) : null;
	}
        public static final Bry_fmtr_eval_mgr_gfs Instance = new Bry_fmtr_eval_mgr_gfs(); Bry_fmtr_eval_mgr_gfs() {}
}
