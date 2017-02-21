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
public class IoItmDir_FetchDeepOrNull_tst {
	@Before public void setup() {
		drive = Io_url_.mem_dir_("mem");
		rootDir = bldr.dir_(drive, bldr.dir_(drive.GenSubDir("sub1")));
	}	IoItm_fxt bldr = IoItm_fxt.new_(); Io_url drive; IoItmDir rootDir;
	@Test  public void FetchDeepOrNull() {
		tst_FetchDeepOrNull(rootDir, drive.GenSubDir("sub1"), true);
		tst_FetchDeepOrNull(rootDir, drive.GenSubDir("sub2"), false);
		tst_FetchDeepOrNull(rootDir.SubDirs().Get_at(0), drive.GenSubDir("sub1"), true);
		tst_FetchDeepOrNull(rootDir.SubDirs().Get_at(0), drive.GenSubDir("sub2"), false);
	}
	void tst_FetchDeepOrNull(Object rootDirObj, Io_url find, boolean expdFound) {
		IoItmDir rootDir = IoItmDir_.as_(rootDirObj);
		IoItmDir actlDir = rootDir.FetchDeepOrNull(find);
		if (actlDir == null) {
			if (expdFound) Tfds.Fail("actlDir is null, but expd dir to be found");
			else return;	// actlDir is null but expdFound was false; return;
		}
		Tfds.Eq(find.Raw(), actlDir.Url().Raw());
	}
}
