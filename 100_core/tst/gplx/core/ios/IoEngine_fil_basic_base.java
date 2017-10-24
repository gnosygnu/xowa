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
import org.junit.*; import gplx.core.texts.*;/*EncodingAdp_*/ import gplx.core.ios.streams.*;
public abstract class IoEngine_fil_basic_base {
	@Before public void setup() {
		engine = engine_();
		fx = IoEngineFxt.new_();
		setup_hook();
	}	protected IoEngine engine; protected IoEngineFxt fx; protected Io_url fil, root;
	protected abstract IoEngine engine_();
	protected abstract void setup_hook();
	@Test  @gplx.Virtual public void ExistsFil() {
		fx.tst_ExistsPaths(false, fil);
	}
	@Test  @gplx.Virtual public void ExistsFil_deep() {
		fx.tst_ExistsPaths(false, root.GenSubFil_nest("dir1", "dir2", "fil1.txt"));
	}
	@Test  @gplx.Virtual public void SaveFilStr() {
		fx.tst_ExistsPaths(false, fil, fil.OwnerDir());

		fx.run_SaveFilText(fil, "text");
		fx.tst_ExistsPaths(true, fil, fil.OwnerDir());
	}
	@Test  @gplx.Virtual public void SaveFilText_autoCreateOwnerDir() {
		fil = fil.OwnerDir().GenSubFil_nest("sub1", "fil1.txt");
		fx.tst_ExistsPaths(false, fil, fil.OwnerDir());

		fx.run_SaveFilText(fil, "text");
		fx.tst_ExistsPaths(true, fil, fil.OwnerDir());
	}
	@Test  @gplx.Virtual public void SaveFilText_overwrite() {
		fx.run_SaveFilText(fil, "text");
		fx.tst_ExistsPaths(true, fil);

		fx.run_SaveFilText(fil, "changed");
		fx.tst_LoadFilStr(fil, "changed");
	}
	@Test  @gplx.Virtual public void SaveFilText_append() {
		fx.run_SaveFilText(fil, "text");

		engine.SaveFilText_api(IoEngine_xrg_saveFilStr.new_(fil, "appended").Append_());
		fx.tst_LoadFilStr(fil, "text" + "appended");
	}
	@Test  @gplx.Virtual public void SaveFilText_caseInsensitive() {
		if (root.Info().CaseSensitive()) return;
		Io_url lcase = root.GenSubFil_nest("dir", "fil.txt");
		Io_url ucase = root.GenSubFil_nest("DIR", "FIL.TXT");
		fx.run_SaveFilText(lcase, "text");

		fx.tst_ExistsPaths(true, lcase, ucase);
		fx.tst_LoadFilStr(lcase, "text");
		fx.tst_LoadFilStr(ucase, "text");
	}
	@Test  @gplx.Virtual public void SaveFilText_readOnlyFails() {
		fx.run_SaveFilText(fil, "text");
		engine.UpdateFilAttrib(fil, IoItmAttrib.readOnly_());

		try {fx.run_SaveFilText(fil, "changed");}
		catch (Exception exc) {
			fx.tst_LoadFilStr(fil, "text");
			Err_.Noop(exc);
			return;
		}
		Tfds.Fail_expdError();
	}
	@Test  @gplx.Virtual public void LoadFilStr() {
		fx.run_SaveFilText(fil, "text");
		fx.tst_LoadFilStr(fil, "text");
	}
	@Test  @gplx.Virtual public void LoadFilStr_missingIgnored() {
		Tfds.Eq("", engine.LoadFilStr(IoEngine_xrg_loadFilStr.new_(fil).MissingIgnored_()));
	}
	@Test  @gplx.Virtual public void UpdateFilAttrib() {
		fx.run_SaveFilText(fil, "text");
		fx.tst_QueryFilReadOnly(fil, false);

		engine.UpdateFilAttrib(fil, IoItmAttrib.readOnly_());
		fx.tst_QueryFilReadOnly(fil, true);
	}
	@Test  @gplx.Virtual public void DeleteFil() {
		fx.run_SaveFilText(fil, "text");
		fx.tst_ExistsPaths(true, fil);

		engine.DeleteFil_api(IoEngine_xrg_deleteFil.new_(fil));
		fx.tst_ExistsPaths(false, fil);
	}
	@Test  @gplx.Virtual public void DeleteFil_missing_pass() {
		fil = root.GenSubFil("fileThatDoesntExist.txt");

		engine.DeleteFil_api(IoEngine_xrg_deleteFil.new_(fil).MissingFails_off());
		fx.tst_ExistsPaths(false, fil);
	}
	@Test  @gplx.Virtual public void DeleteFil_readOnly_fail() {
		fx.run_SaveFilText(fil, "text");

		engine.UpdateFilAttrib(fil, IoItmAttrib.readOnly_());
		try {engine.DeleteFil_api(IoEngine_xrg_deleteFil.new_(fil));}
		catch (Exception exc) {Err_.Noop(exc);
			fx.tst_ExistsPaths(true, fil);
			return;
		}
		Tfds.Fail_expdError();
	}
	@Test  @gplx.Virtual public void DeleteFil_readOnly_pass() {
		fx.run_SaveFilText(fil, "text");
		engine.UpdateFilAttrib(fil, IoItmAttrib.readOnly_());

		engine.DeleteFil_api(IoEngine_xrg_deleteFil.new_(fil).ReadOnlyFails_off());
		fx.tst_ExistsPaths(false, fil);
	}
	@Test  @gplx.Virtual public void QueryFil_size() {
		fx.run_SaveFilText(fil, "text");

		fx.tst_QueryFil_size(fil, String_.Len("text"));
	}
	@Test  @gplx.Virtual public void UpdateFilModifiedTime() {
		fx.run_SaveFilText(fil, "text");

		DateAdp time = Datetime_now.Dflt_add_min_(10);
		engine.UpdateFilModifiedTime(fil, time);
		fx.tst_QueryFil_modifiedTime(fil, time);
	}
	@Test  @gplx.Virtual public void OpenStreamRead() {
		fx.run_SaveFilText(fil, "text");

		int textLen = String_.Len("text");
		byte[] buffer = new byte[textLen];
		IoStream stream = IoStream_.Null;
		try {
			stream = engine.OpenStreamRead(fil);
			stream.Read(buffer, 0, textLen);
		}
		finally {stream.Rls();}
		String actl = String_.new_u8(buffer);
		Tfds.Eq("text", actl);
	}
	@Test  @gplx.Virtual public void OpenStreamWrite() {
		IoStream stream = IoEngine_xrg_openWrite.new_(fil).Exec();
		byte[] buffer = Bry_.new_u8("text");
		int textLen = String_.Len("text");
		stream.Write(buffer, 0, textLen);
		stream.Rls();

		fx.tst_LoadFilStr(fil, "text");
	}
//		@Test  public virtual void OpenStreamWrite_in_place() {
//			byte[] buffer = Bry_.new_u8("a|b|c");
//			IoStream stream = IoEngine_xrg_openWrite.new_(fil).Exec();
//			stream.Write(buffer, 0, buffer.length);
//			stream.Rls();
//			
//			buffer = Bry_.new_u8("B");
//			stream = IoEngine_xrg_openWrite.new_(fil).Exec();
//			stream.Seek(2);
//			stream.Write(buffer, 0, buffer.length);
//			stream.Rls();
//
//			fx.tst_LoadFilStr(fil, "a|B|c");
//		}
}
