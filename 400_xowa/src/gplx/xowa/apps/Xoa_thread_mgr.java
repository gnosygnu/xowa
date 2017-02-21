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
package gplx.xowa.apps; import gplx.*; import gplx.xowa.*;
import gplx.core.threads.*;
public class Xoa_thread_mgr {
	public Gfo_thread_pool		Page_load_mgr() {return page_load_mgr;} private Gfo_thread_pool page_load_mgr = new Gfo_thread_pool();
	public Gfo_thread_pool		File_load_mgr() {return file_load_mgr;} private Gfo_thread_pool file_load_mgr = new Gfo_thread_pool();
	public void Usr_dlg_(Gfo_usr_dlg usr_dlg) {
		page_load_mgr.Usr_dlg_(usr_dlg);
		file_load_mgr.Usr_dlg_(usr_dlg);
	}
}
