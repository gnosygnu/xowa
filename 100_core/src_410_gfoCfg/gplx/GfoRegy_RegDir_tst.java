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
package gplx;
import org.junit.*;
public class GfoRegy_RegDir_tst {
	@Before public void setup() {
		regy = GfoRegy.new_();
		Io_mgr.I.InitEngine_mem();
		root = Io_url_.mem_dir_("mem/root");
	}	GfoRegy regy; Io_url root;
	@Test  public void Basic() {
		ini_fil("101_tsta.txt");
		ini_fil("102_tstb.txt");
		ini_fil("103_tstc.png");
		ini_fil("dir1", "104_tstd.txt");
		regy.RegDir(root, "*.txt", false, "_", ".");
		tst_Count(2);
		tst_Exists("tsta");
		tst_Exists("tstb");
	}
	@Test  public void Err_dupe() {
		ini_fil("101_tsta.txt");
		ini_fil("102_tsta.txt");
		try {regy.RegDir(root, "*.txt", false, "_", ".");}
		catch (Exception e) {Tfds.Err_has(e, GfoRegy.Err_Dupe); return;}
		Tfds.Fail_expdError();
	}
	@Test  public void Err_chopBgn() {
		ini_fil("123_");
		try {regy.RegDir(root, "*", false, "_", ".");}
		catch (Exception e) {Tfds.Err_has(e, GfoRegy.Err_ChopBgn); return;}
		Tfds.Fail_expdError();
	}
	@Test  public void Err_chopEnd() {
		ini_fil(".txt");
		try {regy.RegDir(root, "*.txt", false, "_", ".");}
		catch (Exception e) {Tfds.Err_has(e, GfoRegy.Err_ChopEnd); return;}
		Tfds.Fail_expdError();
	}
	void tst_Count(int expd) {Tfds.Eq(expd, regy.Count());}
	void tst_Exists(String expd) {
		GfoRegyItm itm = regy.FetchOrNull(expd);
		Tfds.Eq_nullNot(itm);
	}
	void ini_fil(String... nest) {Io_mgr.I.SaveFilStr(root.GenSubFil_nest(nest), "");}
}
