/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
