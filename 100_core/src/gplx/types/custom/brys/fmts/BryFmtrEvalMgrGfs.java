/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.types.custom.brys.fmts;
import gplx.langs.gfs.GfsCore;
import gplx.types.custom.brys.fmts.fmtrs.BryFmtrEvalMgr;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
public class BryFmtrEvalMgrGfs implements BryFmtrEvalMgr {
	public boolean Enabled() {return enabled;} public void EnabledSet(boolean v) {enabled = v;} private boolean enabled;
	public byte[] Eval(byte[] cmd) {            
		return enabled ? BryUtl.NewU8(ObjectUtl.ToStrOrNullMark(GfsCore.Instance.ExecText(StringUtl.NewU8(cmd)))) : null;
	}
	public static final BryFmtrEvalMgrGfs Instance = new BryFmtrEvalMgrGfs(); BryFmtrEvalMgrGfs() {}
}
