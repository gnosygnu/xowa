/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.ios;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateUtl;
import gplx.libs.files.Io_url;
import org.junit.*;
public abstract class IoEngine_fil_xfer_base_tst {
	@Before public void setup() {
		engine = engine_();
		fx = IoEngineFxt.new_();
		setup_hook();
		src = root.GenSubFil("src.txt"); trg = root.GenSubFil("trg.txt");
	}   protected IoEngine engine; public IoEngineFxt fx; protected Io_url src, trg, root;
	GfoDate srcModifiedTime = GfoDateUtl.ParseGplx("2010.04.12 20.26.01.000"), trgModifiedTime = GfoDateUtl.ParseGplx("2010.04.01 01.01.01.000");
	protected abstract IoEngine engine_();
	protected abstract void setup_hook();
	protected abstract Io_url AltRoot();
	@Test public void CopyFil() {
		fx.run_SaveFilText(src, "src"); fx.run_UpdateFilModifiedTime(src, srcModifiedTime);
		fx.tst_ExistsPaths(true, src);
		fx.tst_ExistsPaths(false, trg);

		IoEngine_xrg_xferFil.copy_(src, trg).Exec();
		fx.tst_ExistsPaths(true, src, trg);
		fx.tst_LoadFilStr(trg, "src");
		fx.tst_QueryFil_modifiedTime(trg, srcModifiedTime);
	}
	@Test public void CopyFil_overwrite_fail() {
		fx.run_SaveFilText(src, "src");
		fx.run_SaveFilText(trg, "trg");

		try {IoEngine_xrg_xferFil.copy_(src, trg).Exec();}
		catch (Exception exc) {
			fx.tst_ExistsPaths(true, src, trg);
			fx.tst_LoadFilStr(trg, "trg");
			return;
		}
		GfoTstr.FailBcExpdError();
	}
	@Test public void CopyFil_overwrite_pass() {
		fx.run_SaveFilText(src, "src"); fx.run_UpdateFilModifiedTime(src, srcModifiedTime);
		fx.run_SaveFilText(trg, "trg"); fx.run_UpdateFilModifiedTime(trg, trgModifiedTime);

		IoEngine_xrg_xferFil.copy_(src, trg).Overwrite_().Exec();
		fx.tst_ExistsPaths(true, src, trg);
		fx.tst_LoadFilStr(trg, "src");
		fx.tst_QueryFil_modifiedTime(trg, srcModifiedTime);
	}
	@Test public void MoveFil() {
		fx.run_SaveFilText(src, "src");
		fx.tst_ExistsPaths(true, src);
		fx.tst_ExistsPaths(false, trg);

		IoEngine_xrg_xferFil.move_(src, trg).Exec();
		fx.tst_ExistsPaths(false, src);
		fx.tst_ExistsPaths(true, trg);
	}
	@Test public void MoveFil_overwrite_fail() {
		fx.run_SaveFilText(src, "src");
		fx.run_SaveFilText(trg, "trg");

		try {IoEngine_xrg_xferFil.move_(src, trg).Exec();}
		catch (Exception exc) {
			fx.tst_ExistsPaths(true, src);
			fx.tst_ExistsPaths(true, trg);
			fx.tst_LoadFilStr(trg, "trg");
			return;
		}
		GfoTstr.FailBcExpdError();
	}
	@Test public void MoveFil_overwrite_pass() {
		fx.run_SaveFilText(src, "src"); fx.run_UpdateFilModifiedTime(src, srcModifiedTime);
		fx.run_SaveFilText(trg, "trg"); fx.run_UpdateFilModifiedTime(trg, trgModifiedTime);

		IoEngine_xrg_xferFil.move_(src, trg).Overwrite_().Exec();
		fx.tst_ExistsPaths(false, src);
		fx.tst_ExistsPaths(true, trg);
		fx.tst_LoadFilStr(trg, "src");
		fx.tst_QueryFil_modifiedTime(trg, srcModifiedTime);
	}
	@Test public void MoveFil_betweenDrives() {
		IoEngine_xrg_deleteDir.new_(AltRoot()).Recur_().ReadOnlyFails_off().Exec();
		src = root.GenSubFil_nest("dir", "fil1a.txt");
		trg = AltRoot().GenSubFil_nest("dir", "fil1b.txt");
		fx.run_SaveFilText(src, "src");
		fx.tst_ExistsPaths(true, src);
		fx.tst_ExistsPaths(false, trg);

		IoEngine_xrg_xferFil.move_(src, trg).Exec();
		fx.tst_ExistsPaths(false, src);
		fx.tst_ExistsPaths(true, trg);
	}
}
