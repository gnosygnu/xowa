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
public class IoEngine_fil_basic_memory_tst extends IoEngine_fil_basic_base {
	@Override protected IoEngine engine_() {return IoEngine_.Mem_init_();}
	@Override protected void setup_hook() {
		root = Io_url_.mem_dir_("mem");
		fil = root.GenSubFil_nest("root", "fil.txt");
	}
	@Test  @Override public void OpenStreamRead() {
		super.OpenStreamRead ();
	}
	@Test  @Override public void SaveFilText_overwrite() {
		super.SaveFilText_overwrite();

		// bugfix: verify changed file in ownerDir's hash
		IoItmDir dirItm = fx.tst_ScanDir(fil.OwnerDir(), fil);
		IoItmFil_mem filItm = (IoItmFil_mem)dirItm.SubFils().Get_at(0);
		Tfds.Eq(filItm.Text(), "changed");
	}
	@Test  public void RecycleFil() {
		fx.run_SaveFilText(fil, "text");
		fx.tst_ExistsPaths(true, fil);

		IoRecycleBin bin = IoRecycleBin.Instance;
		List_adp list = Tfds.RscDir.XtoNames(); 
//			foreach (String s in list)
//				Tfds.Write(s);
		list.Del_at(0); // remove drive
		IoEngine_xrg_recycleFil recycleXrg = bin.Send_xrg(fil)
			.RootDirNames_(list)
			.AppName_("gplx.test").Time_(DateAdp_.parse_gplx("20100102_115559123")).Uuid_(Guid_adp_.Parse("467ffb41-cdfe-402f-b22b-be855425784b"));
		recycleXrg.Exec();
		fx.tst_ExistsPaths(false, fil);
		fx.tst_ExistsPaths(true, recycleXrg.RecycleUrl());

		bin.Recover(recycleXrg.RecycleUrl());
		fx.tst_ExistsPaths(true, fil);
		fx.tst_ExistsPaths(false, recycleXrg.RecycleUrl());
	}
}
