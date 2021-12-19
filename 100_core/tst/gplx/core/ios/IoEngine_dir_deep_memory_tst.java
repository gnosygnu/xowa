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
import gplx.libs.files.Io_url_;
import org.junit.*;
public class IoEngine_dir_deep_memory_tst extends IoEngine_dir_deep_base_tst {
	@Override protected void setup_hook() {
		root = Io_url_.mem_dir_("mem/root");
	}   @Override protected IoEngine engine_() {return IoEngine_.Mem_init_();}
	@Test @Override public void SearchDir() {
		super.SearchDir();
	}
	@Test @Override public void MoveDirDeep() {
		super.MoveDirDeep();
	}
	@Test @Override public void CopyDir() {
		super.CopyDir();
	}
	@Test @Override public void DeleteDir() {
		super.DeleteDir();
	}
}
