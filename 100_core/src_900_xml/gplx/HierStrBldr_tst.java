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
import gplx.ios.*; import gplx.texts.*;
public class HierStrBldr_tst {
	@Before public void setup() {bldr = new HierStrBldr();}  HierStrBldr bldr;
	@Test  public void Hier0() {
		bldr.Ctor("/root/", "dir_{0}/", "idx_{0}.csv", "000");
		tst_MakeName( 0, "/root/idx_000.csv");
		tst_MakeName( 1, "/root/idx_001.csv");
		tst_MakeName(10, "/root/idx_010.csv");
	}
	@Test  public void Hier1() {
		bldr.Ctor("/root/", "dir_{0}/", "idx_{0}.csv", "000", 10);
		tst_MakeName( 0, "/root/dir_000/idx_000.csv");
		tst_MakeName( 1, "/root/dir_000/idx_001.csv");
		tst_MakeName(10, "/root/dir_010/idx_010.csv");
	}
	@Test  public void Hier2() {
		bldr.Ctor("/root/", "dir_{0}/", "idx_{0}.csv", "000", 5, 10);
		tst_MakeName(  0, "/root/dir_000/dir_000/idx_000.csv");
		tst_MakeName(  1, "/root/dir_000/dir_000/idx_001.csv");
		tst_MakeName( 10, "/root/dir_000/dir_010/idx_010.csv");
		tst_MakeName( 49, "/root/dir_000/dir_040/idx_049.csv");
		tst_MakeName( 50, "/root/dir_050/dir_050/idx_050.csv");
		tst_MakeName( 99, "/root/dir_050/dir_090/idx_099.csv");
		tst_MakeName(100, "/root/dir_100/dir_100/idx_100.csv");
		tst_MakeName(110, "/root/dir_100/dir_110/idx_110.csv");
	}
	void tst_MakeName(int val, String expd) {Tfds.Eq(expd, bldr.GenStrIdxOnly(val));}
}
