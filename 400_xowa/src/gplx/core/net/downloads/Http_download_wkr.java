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
package gplx.core.net.downloads; import gplx.*; import gplx.core.*; import gplx.core.net.*;
public interface Http_download_wkr {
	String Fail_msg();
	Io_url Tmp_url();
	Http_download_wkr Make_new();
	long Checkpoint__load_by_trg_fil(Io_url trg_url);
	byte Exec(gplx.core.progs.Gfo_prog_ui prog_ui, String src_str, Io_url trg_url, long expd_size);
	void Exec_cleanup();
}
