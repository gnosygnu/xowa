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
public abstract class IoEngine_dir_deep_base {
	@Before public void setup() {
		engine = engine_();
		fx = IoEngineFxt.new_();
		setup_hook();
		setup_paths();
		setup_objs();
	}	protected IoEngine engine; protected Io_url fil, root; @gplx.Internal protected IoEngineFxt fx; 
	protected abstract IoEngine engine_();
	protected abstract void setup_hook();
	@Test  @gplx.Virtual public void SearchDir() {
		Io_url[] expd = paths_(src_dir0a, src_fil0a, src_dir0a_dir0a, src_dir0a_fil0a);
		Io_url[] actl = IoEngine_xrg_queryDir.new_(src).Recur_().DirInclude_().ExecAsUrlAry();
		Tfds.Eq_ary(expd, actl);
	}
	@Test  @gplx.Virtual public void MoveDirDeep() {
		fx.tst_ExistsPaths(true, srcTree); fx.tst_ExistsPaths(false, trgTree);

		engine.MoveDirDeep(IoEngine_xrg_xferDir.move_(src, trg).Recur_());
		fx.tst_ExistsPaths(false, srcTree);
		fx.tst_ExistsPaths(true, trgTree);
	}
	@Test  @gplx.Virtual public void CopyDir() {
		fx.tst_ExistsPaths(true, srcTree); fx.tst_ExistsPaths(false, trgTree);

		engine.CopyDir(src, trg);
		fx.tst_ExistsPaths(true, srcTree);
		fx.tst_ExistsPaths(true, trgTree);
	}
	@Test  @gplx.Virtual public void DeleteDir() {
		fx.tst_ExistsPaths(true, srcTree);

		engine.DeleteDirDeep(IoEngine_xrg_deleteDir.new_(src).Recur_());
		fx.tst_ExistsPaths(false, srcTree);
	}
//		@Test  public virtual void CopyDir_IgnoreExisting() {
//			fx.tst_ExistsPaths(true, srcTree); fx.tst_ExistsPaths(false, trgTree);
//			engine.SaveFilStr(trg_dir0a_fil0a, "x");	// NOTE: this file is different than src counterpart; should be overwritten by Copy
//			fx.tst_ExistsPaths(true, trg_dir0a, trg_dir0a_fil0a);
//
//			engine.CopyDir(src, trg);
//			fx.tst_ExistsPaths(true, srcTree);
//			fx.tst_ExistsPaths(true, trgTree);
//		}
//		@Test  public virtual void CopyDir_IgnoreExistingReadOnlyFile() {
//			fx.tst_ExistsPaths(true, srcTree); fx.tst_ExistsPaths(false, trgTree);
//			engine.SaveFilStr(trg_fil0a, "x");	// NOTE: this file is different than src counterpart; should be overwritten by Copy
//			fx.tst_ExistsPaths(true, trg_fil0a);
//			engine.UpdateFilAttrib(trg_fil0a, IoItmAttrib.ReadOnlyFile);
//
//			engine.CopyDir(src, trg);
//			fx.tst_ExistsPaths(true, srcTree);
//			fx.tst_ExistsPaths(true, trgTree);
//		}
//		@Test  public void MoveDir_IgnoreExisting() {
//			fx.tst_ExistsPaths(true, srcTree);
//			fx.tst_ExistsPaths(false, trgTree);
//			engine.SaveFilStr(trg_dir0a_fil0a, @"x");	// NOTE: this file is different than src counterpart; should be overwritten by Copy
//			fx.tst_ExistsPaths(true, trg_dir0a, trg_dir0a_fil0a);
//
//			engine.MoveDir(src, trg);
//
//			fx.tst_ExistsPaths(true, srcTree);
//			fx.tst_ExistsPaths(true, trgTree);
//		}
//		@Test  public virtual void ProgressUi() {
//			Console_adp__mem dialog = Console_adp__mem.new_();
//			engine.SearchDir(src).Recur_().Prog_(dialog).ExecAsDir();
//
//			Tfds.Eq(dialog.Written.Count, 3); // 3 levels
//			tst_(dialog, 0, "scan", src);
//			tst_(dialog, 1, "scan", src_dir0a);
//			tst_(dialog, 2, "scan", src_dir0a_dir0a);
//		}
//		void tst_(Console_adp__mem dialog, int i, String s, Io_url root) {
//			Object o = dialog.Written.Get_at(i);
//			IoStatusArgs args = (IoStatusArgs)o;
//			Tfds.Eq(s, args.Op);
//			Tfds.Eq(root, args.Path);
//		}
	protected Io_url src, src_dir0a, src_dir0a_dir0a;
	Io_url src_fil0a, src_dir0a_fil0a;
	protected Io_url trg, trg_dir0a, trg_dir0a_dir0a;
	Io_url trg_fil0a, trg_dir0a_fil0a;
	Io_url[] srcTree, trgTree;
	Io_url[] paths_(Io_url... ary) {return ary;}
	protected void setup_paths() {
		src = root.GenSubDir_nest("src");
		src_dir0a = root.GenSubDir_nest("src", "dir0a");
		src_dir0a_dir0a = root.GenSubDir_nest("src", "dir0a", "dir0a");
		src_fil0a = root.GenSubFil_nest("src", "fil0a.txt");
		src_dir0a_fil0a = root.GenSubFil_nest("src", "dir0a", "fil0a.txt");
		trg = root.GenSubDir_nest("trg");
		trg_dir0a = root.GenSubDir_nest("trg", "dir0a");
		trg_dir0a_dir0a = root.GenSubDir_nest("trg", "dir0a", "dir0a");
		trg_fil0a = root.GenSubFil_nest("trg", "fil0a.txt");
		trg_dir0a_fil0a = root.GenSubFil_nest("trg", "dir0a", "fil0a.txt");
		srcTree = new Io_url[] {src, src_dir0a, src_dir0a_dir0a, src_fil0a, src_dir0a_fil0a};
		trgTree = new Io_url[] {trg, trg_dir0a, trg_dir0a_dir0a, trg_fil0a, trg_dir0a_fil0a};
	}
	void setup_objs() {
		fx.run_SaveFilText(src_fil0a, "src_fil0a");				// NOTE: automatically creates src
		fx.run_SaveFilText(src_dir0a_fil0a, "src_dir0a_fil0a");	// NOTE: automatically creates src_dir0a_dir0a
		fx.tst_ExistsPaths(true, src_fil0a);
		engine.CreateDir(src_dir0a_dir0a);
	}
}
