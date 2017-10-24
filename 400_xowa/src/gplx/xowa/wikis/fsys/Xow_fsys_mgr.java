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
package gplx.xowa.wikis.fsys; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.xowa.apps.fsys.*;
import gplx.xowa.users.*;
public class Xow_fsys_mgr {
	public Xow_fsys_mgr(Io_url root_dir, Io_url file_dir) {
		this.root_dir = root_dir; this.file_dir = file_dir; this.tmp_dir = root_dir.GenSubDir("tmp");
	}
	public Io_url Root_dir()				{return root_dir;}		private final    Io_url root_dir;
	public Io_url File_dir()				{return file_dir;}		private final    Io_url file_dir;
	public Io_url Tmp_dir()					{return tmp_dir;}		private final    Io_url tmp_dir;
}
