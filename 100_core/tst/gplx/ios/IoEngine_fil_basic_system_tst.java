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
public class IoEngine_fil_basic_system_tst extends IoEngine_fil_basic_base {
	@Override protected void setup_hook() {
		root = Tfds.RscDir.GenSubDir_nest("100_core", "ioEngineTest", "_temp");
		fil = root.GenSubFil("fil.txt");
		IoEngine_xrg_deleteDir.new_(fil.OwnerDir()).Recur_().ReadOnlyFails_off().Exec();
	}	@Override protected IoEngine engine_() {return IoEngine_system.new_();}
	@Test public void ExistsFil_IgnoreDifferentCasing() {
		if (root.Info().CaseSensitive()) return;
		fx.run_SaveFilText(fil, "text");
		fx.tst_ExistsPaths(true, fil);
		fx.tst_ExistsPaths(true, fil.OwnerDir().GenSubFil("FIL.txt"));
	}
	@Test @gplx.Virtual public void RecycleFil() {
		fx.run_SaveFilText(fil, "text");
		fx.tst_ExistsPaths(true, fil);

		IoRecycleBin bin = IoRecycleBin._;
		ListAdp list = root.XtoNames(); list.DelAt(0); // remove drive
		IoEngine_xrg_recycleFil recycleXrg = bin.Send_xrg(fil)
			.RootDirNames_(list)
			.AppName_("gplx.test").Time_(DateAdp_.parse_gplx("20100102_115559123")).Uuid_(UuidAdp_.parse_("467ffb41-cdfe-402f-b22b-be855425784b"));
		recycleXrg.Exec();
		fx.tst_ExistsPaths(false, fil);
		fx.tst_ExistsPaths(true, recycleXrg.RecycleUrl());

		bin.Recover(recycleXrg.RecycleUrl());
		fx.tst_ExistsPaths(true, fil);
		fx.tst_ExistsPaths(false, recycleXrg.RecycleUrl());
	}
	@Test @Override public void DeleteFil_missing_pass() {
		super.DeleteFil_missing_pass();
	}
	@Test @Override public void DeleteFil_readOnly_pass() {
		super.DeleteFil_readOnly_pass ();
	}
	@Test @Override public void SaveFilText_readOnlyFails() {
		super.SaveFilText_readOnlyFails();
	}
}
