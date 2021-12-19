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
package gplx.langs.gfs;
import gplx.core.gfo_regys.GfoRegy;
import gplx.frameworks.templates.GfoTemplateFactory;
import gplx.libs.files.Io_url_;
public class GfsLibIni_core implements GfsLibIni {
	public void Ini(GfsCore core) {
		core.AddObj(GfoTemplateFactory.Instance, "factory");
		core.AddObj(GfoRegy.Instance, "GfoRegy_");
		core.AddObj(GfsCore.Instance, "GfsCore_");
		core.AddObj(gplx.core.ios.IoUrlInfoRegy.Instance, "IoUrlInfoRegy_");
		core.AddObj(gplx.core.ios.IoUrlTypeRegy.Instance, "IoUrlTypeRegy_");

		GfoRegy.Instance.Parsers().Add("Io_url", Io_url_.Parser);
	}
		public static final GfsLibIni_core Instance = new GfsLibIni_core(); GfsLibIni_core() {}
}
