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
package gplx.ios; import gplx.*;
import org.junit.*;
public class IoUrlInfo_alias_tst {
	IoUrlInfo_alias alias;
	@Test  public void MapWntToWnt() {
		Make("usr:\\", "D:\\usr\\");
		tst_Xto_api("usr:\\dir\\fil.txt", "D:\\usr\\dir\\fil.txt");
		tst_OwnerDir("usr:\\dir\\", "usr:\\");
		tst_OwnerDir("usr:\\", "");
		tst_NameOnly("usr:\\", "usr");
	}
	@Test  public void MapToLnx() {
		Make("usr:\\", "/home/");
		tst_Xto_api("usr:\\dir\\fil.txt", "/home/dir/fil.txt");
	}
	@Test  public void MapLnxToWnt() {
		Make("usr:/", "C:\\usr\\");
		tst_Xto_api("usr:/dir/fil.txt", "C:\\usr\\dir\\fil.txt");
	}
	@Test  public void WntToWnt() {
		Make("C:\\", "X:\\");
		tst_Xto_api("C:\\dir\\fil.txt", "X:\\dir\\fil.txt");
		tst_NameOnly("C:\\", "C");
	}
	@Test  public void WntToLnx() {
		Make("C:\\", "/home/");
		tst_Xto_api("C:\\dir\\fil.txt", "/home/dir/fil.txt");
	}
	@Test  public void LnxToWnt() {
		Make("/home/", "C:\\");
		tst_Xto_api("/home/dir/fil.txt", "C:\\dir\\fil.txt");
		tst_NameOnly("/home/", "home");
		tst_NameOnly("/", "root");
	}
	void tst_Xto_api(String raw, String expd) {Tfds.Eq(expd, alias.Xto_api(raw));}
	void tst_OwnerDir(String raw, String expd) {Tfds.Eq(expd, alias.OwnerDir(raw));}
	void tst_NameOnly(String raw, String expd) {Tfds.Eq(expd, alias.NameOnly(raw));}
	void Make(String srcDir, String trgDir) {
		alias = IoUrlInfo_alias.new_(srcDir, trgDir, IoEngine_.SysKey);
	}
}
