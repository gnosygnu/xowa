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
package gplx.core.ios;
import gplx.frameworks.tests.GfoIoTstr;
import gplx.libs.files.Io_url;
public class IoEngine_fil_xfer_system_tst extends IoEngine_fil_xfer_base_tst {
	@Override protected void setup_hook() {
		root = GfoIoTstr.RscDir.GenSubDir_nest("100_core", "ioEngineTest", "_temp");
		IoEngine_xrg_deleteDir.new_(root.OwnerDir()).Recur_().ReadOnlyFails_off().Exec();
	}   @Override protected IoEngine engine_() {return IoEngine_system.new_();}
	@Override protected Io_url AltRoot() {
		return GfoIoTstr.RscDir.GenSubDir_nest("100_core", "ioEngineTest", "_temp");
	}
}
