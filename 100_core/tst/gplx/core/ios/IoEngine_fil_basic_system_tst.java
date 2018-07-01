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
public class IoEngine_fil_basic_system_tst extends IoEngine_fil_basic_base {
	@Override protected void setup_hook() {
		root = Tfds.RscDir.GenSubDir_nest("100_core", "ioEngineTest", "_temp");
		fil = root.GenSubFil("fil.txt");
		IoEngine_xrg_deleteDir.new_(fil.OwnerDir()).Recur_().ReadOnlyFails_off().Exec();
	}	@Override protected IoEngine engine_() {return IoEngine_system.new_();}
	@Test  public void ExistsFil_IgnoreDifferentCasing() {
		if (root.Info().CaseSensitive()) return;
		fx.run_SaveFilText(fil, "text");
		fx.tst_ExistsPaths(true, fil);
		fx.tst_ExistsPaths(true, fil.OwnerDir().GenSubFil("FIL.txt"));
	}
	@Test  @gplx.Virtual public void RecycleFil() {
		fx.run_SaveFilText(fil, "text");
		fx.tst_ExistsPaths(true, fil);

		IoRecycleBin bin = IoRecycleBin.Instance;
		List_adp list = root.XtoNames(); list.Del_at(0); // remove drive
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
	@Test  @Override public void DeleteFil_missing_pass() {
		super.DeleteFil_missing_pass();
	}
	@Test  @Override public void DeleteFil_readOnly_pass() {
		super.DeleteFil_readOnly_pass ();
	}
	@Test  @Override public void SaveFilText_readOnlyFails() {
		super.SaveFilText_readOnlyFails();
	}
}
