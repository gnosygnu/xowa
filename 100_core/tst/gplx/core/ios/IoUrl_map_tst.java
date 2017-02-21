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
package gplx.core.ios; import gplx.*; import gplx.core.*;
import org.junit.*;
public class IoUrl_map_tst {
	IoUrlFxt fx = IoUrlFxt.new_();
	@Test  public void Xto_api() {
		IoUrlInfo inf = IoUrlInfo_.alias_("tst:\\", "C:\\tst\\", IoEngine_.SysKey);
		fx.tst_Xto_api(Io_url_.new_inf_("tst:\\dir\\fil.txt", inf), "C:\\tst\\dir\\fil.txt");
		fx.tst_Xto_api(Io_url_.new_inf_("tst:\\dir\\", inf), "C:\\tst\\dir");	// no trailing \
	}		
	@Test  public void Xto_api_wce() {			
		IoUrlInfo inf = IoUrlInfo_.alias_("wce:\\", "\\SD Card\\", IoEngine_.SysKey); 
		fx.tst_Xto_api(Io_url_.new_inf_("wce:\\dir\\", inf), "\\SD Card\\dir");
	}
}
