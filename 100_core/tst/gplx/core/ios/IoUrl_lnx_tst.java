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
