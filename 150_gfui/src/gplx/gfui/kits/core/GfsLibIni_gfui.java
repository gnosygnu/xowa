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
package gplx.gfui.kits.core; import gplx.*; import gplx.gfui.*; import gplx.gfui.kits.*;
import gplx.langs.gfs.*;
import gplx.gfui.ipts.*;
public class GfsLibIni_gfui implements GfsLibIni {
	public void Ini(GfsCore core) {
		core.AddCmd(IptCfgRegy.Instance, "IptBndMgr_");
	}
	public static final GfsLibIni_gfui Instance = new GfsLibIni_gfui(); GfsLibIni_gfui() {}
}
