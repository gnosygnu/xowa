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
public class IoUrl_lnx_tst {
	IoUrlFxt fx = IoUrlFxt.new_();
	@Test  public void Raw() {
		fx.tst_Xto_gplx(Io_url_.lnx_dir_("/home/"), "/home/");
		fx.tst_Xto_gplx(Io_url_.lnx_dir_("/home"), "/home/");	// add /
		fx.tst_Xto_gplx(Io_url_.lnx_dir_("/"), "/");
		fx.tst_Xto_gplx(Io_url_.lnx_fil_("/home/fil.txt"), "/home/fil.txt");
	}
	@Test  public void Xto_api() {
		fx.tst_Xto_api(Io_url_.lnx_fil_("/home/fil.txt"), "/home/fil.txt");
		fx.tst_Xto_api(Io_url_.lnx_dir_("/home/"), "/home");		// del /
		fx.tst_Xto_api(Io_url_.lnx_dir_("/"), "/");
	}
	@Test  public void OwnerRoot() {
		fx.tst_OwnerRoot(Io_url_.lnx_dir_("/home/fil.txt"), "/");
		fx.tst_OwnerRoot(Io_url_.lnx_dir_("/home"), "/");
		fx.tst_OwnerRoot(Io_url_.lnx_dir_("root"), "/");
	}
	@Test  public void XtoNames() {
		fx.tst_XtoNames(Io_url_.lnx_dir_("/home/fil.txt"), fx.ary_("root", "home", "fil.txt"));
		fx.tst_XtoNames(Io_url_.lnx_dir_("/home"), fx.ary_("root", "home"));
	}
	@Test  public void IsDir() {
		fx.tst_IsDir(Io_url_.lnx_dir_("/home"), true);
		fx.tst_IsDir(Io_url_.lnx_fil_("/home/file.txt"), false);
	}
	@Test  public void OwnerDir() {
		fx.tst_OwnerDir(Io_url_.lnx_dir_("/home/lnxusr"), Io_url_.lnx_dir_("/home"));
		fx.tst_OwnerDir(Io_url_.lnx_dir_("/fil.txt"), Io_url_.lnx_dir_("/"));
		fx.tst_OwnerDir(Io_url_.lnx_dir_("/"), Io_url_.Empty);
	}
	@Test  public void NameAndExt() {
		fx.tst_NameAndExt(Io_url_.lnx_fil_("/fil.txt"), "fil.txt");
		fx.tst_NameAndExt(Io_url_.lnx_dir_("/dir"), "dir/");
	}
}
