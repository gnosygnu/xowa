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
package gplx.core.ios.zips; import gplx.*; import gplx.core.*; import gplx.core.ios.*;
import gplx.core.progs.*;
public interface Io_zip_decompress_cmd {
	String Fail_msg();
	Io_zip_decompress_cmd Make_new();
	long Checkpoint__load_by_src_fil(Io_url src_fil);
	byte Exec(gplx.core.progs.Gfo_prog_ui prog_ui, Io_url src_fil, Io_url trg_dir, List_adp trg_fils);
	void Exec_cleanup();
}
