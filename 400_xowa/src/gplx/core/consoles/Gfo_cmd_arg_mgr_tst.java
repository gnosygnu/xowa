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
package gplx.core.consoles; import gplx.*; import gplx.core.*;
import org.junit.*; import gplx.core.tests.*; import gplx.core.envs.*;
public class Gfo_cmd_arg_mgr_tst {		
	@Before public void init() {fxt.Clear();} private final Gfo_cmd_arg_mgr_fxt fxt = new Gfo_cmd_arg_mgr_fxt();
	@Test  public void Val__many() {
		fxt.Init_args(fxt.Make_arg("a"), fxt.Make_arg("b"));
		fxt.Exec_process("--a", "0", "--b", "1");
		fxt.Test_errs_none();
		fxt.Test_actl(fxt.Make_chkr("a", "0"), fxt.Make_chkr("b", "1"));
	}
	@Test  public void Val__yn() {
		fxt.Init_args(fxt.Make_arg("a", Gfo_cmd_arg_itm_.Val_tid_yn), fxt.Make_arg("b", Gfo_cmd_arg_itm_.Val_tid_yn));
		fxt.Exec_process("--a", "y", "--b", "n");
		fxt.Test_errs_none();
		fxt.Test_actl(fxt.Make_chkr("a", Bool_.Y), fxt.Make_chkr("b", Bool_.N));
	}
	@Test  public void Dflt() {
		fxt.Init_args(fxt.Make_arg("a", Gfo_cmd_arg_itm_.Val_tid_yn).Dflt_(Bool_.Y));
		fxt.Exec_process("--a", "n").Test_actl(fxt.Make_chkr("a", Bool_.N));	// if val, use it
		fxt.Exec_process()			.Test_actl(fxt.Make_chkr("a", Bool_.Y));	// if no val, use default
	}
	@Test  public void Err__key__unknown() {
		fxt.Init_args(fxt.Make_arg("a"));
		fxt.Exec_process("--b");
		fxt.Test_errs(Gfo_cmd_arg_mgr_.Err__key__unknown);			
	}
	@Test  public void Err__key__missing() {
		fxt.Init_args(fxt.Make_arg("a"));
		fxt.Exec_process("a");
		fxt.Test_errs(Gfo_cmd_arg_mgr_.Err__key__missing);
	}
	@Test  public void Err__key__dupe() {
		fxt.Init_args(fxt.Make_arg("a"));
		fxt.Exec_process("--a", "0", "--a", "0");
		fxt.Test_errs(Gfo_cmd_arg_mgr_.Err__key__duplicate);
	}
	@Test  public void Err__val__reqd() {
		fxt.Init_args(fxt.Make_arg("a", Bool_.Y), fxt.Make_arg("b", Bool_.N));
		fxt.Exec_process("--b", "1");
		fxt.Test_errs(Gfo_cmd_arg_mgr_.Err__val__required);
	}
	@Test  public void Err__val__parse__yn() {
		fxt.Init_args(fxt.Make_arg("a", Gfo_cmd_arg_itm_.Val_tid_yn));
		fxt.Exec_process("--a", "x");
		fxt.Test_errs(Gfo_cmd_arg_mgr_.Err__val__invalid__yn);
	}
	@Test  public void Val_as_url_rel_dir_or() {	// PURPOSE: "/xowa" -> "/xowa/"
		String root_dir = Op_sys.Cur().Tid_is_wnt() ? "C:\\" : "/", dir_spr = Op_sys.Cur().Fsys_dir_spr_str();
		fxt.Test_val_as_url_rel_dir_or(root_dir, dir_spr, root_dir + "sub"				, root_dir + "sub" + dir_spr);						// /sub   -> /sub/
		fxt.Test_val_as_url_rel_dir_or(root_dir, dir_spr, root_dir + "sub" + dir_spr	, root_dir + "sub" + dir_spr);						// /sub/  -> /sub/
		fxt.Test_val_as_url_rel_dir_or(root_dir, dir_spr, "sub"							, root_dir + "dir" + dir_spr + "sub" + dir_spr);	// sub    -> /dir/sub/
	}
}
class Gfo_cmd_arg_mgr_fxt {
	private final Tst_mgr tst_mgr = new Tst_mgr();
	public Gfo_usr_dlg Usr_dlg() {return usr_dlg;} Gfo_usr_dlg usr_dlg;
	public Gfo_cmd_arg_mgr Mgr() {return mgr;} private final Gfo_cmd_arg_mgr mgr = new Gfo_cmd_arg_mgr();
	public Gfo_cmd_arg_mgr_fxt Clear() {
		if (usr_dlg == null)
			usr_dlg = Gfo_usr_dlg_.Test();
		mgr.Reset();
		usr_dlg.Gui_wkr().Clear();
		return this;
	}
	public Gfo_cmd_arg_itm Make_arg(String key)						{return Make_arg(key, false);}
	public Gfo_cmd_arg_itm Make_arg(String key, int tid)			{return Make_arg(key, false, tid);}
	public Gfo_cmd_arg_itm Make_arg(String key, boolean reqd)			{return Gfo_cmd_arg_itm_.new_(key, reqd, Gfo_cmd_arg_itm_.Val_tid_string);}
	public Gfo_cmd_arg_itm Make_arg(String key, boolean reqd, int tid)	{return Gfo_cmd_arg_itm_.new_(key, reqd, tid);}
	public Gfo_cmd_itm_chkr Make_chkr(String key, Object val) {return new Gfo_cmd_itm_chkr(key, val);}
	public Gfo_cmd_arg_mgr_fxt Init_args(Gfo_cmd_arg_itm... v) {mgr.Reg_many(v); return this;}
	public Gfo_cmd_arg_mgr_fxt Exec_process(String... v) {mgr.Parse(v); return this;}
	public Gfo_cmd_arg_mgr_fxt Test_actl(Gfo_cmd_itm_chkr... expd) {
		Gfo_cmd_arg_itm[] actl = mgr.To_ary();
		tst_mgr.Tst_ary("", expd, actl);
		return this;
	}
	public Gfo_cmd_arg_mgr_fxt Test_errs_none() {return Test_errs(String_.Ary_empty);}
	public Gfo_cmd_arg_mgr_fxt Test_errs(String... expd) {
		String[] actl = mgr.Errs__to_str_ary();
		int len = actl.length;
		for (int i = 0; i < len; ++i) {	// extract key part; EX: "unknown key: abc" -> unknown key
			actl[i] = String_.GetStrBefore(actl[i], ":");
		}
		Tfds.Eq_ary_str(expd, actl);
		return this;
	}
	public Gfo_cmd_arg_mgr_fxt Test_write(String... expd) {
		Tfds.Eq_ary_str(expd, ((Gfo_usr_dlg__gui_test)usr_dlg.Gui_wkr()).Msgs().To_str_ary_and_clear());
		return this;
	}
	public void Test_val_as_url_rel_dir_or(String root_dir, String dir_spr, String val, String expd) {
		Io_url actl = Make_arg("key").Val_(val).Val_as_url__rel_dir_or(Io_url_.new_dir_(root_dir).GenSubDir("dir"), null);
		Tfds.Eq(expd, actl.Raw());
	}
}
class Gfo_cmd_itm_chkr implements Tst_chkr {
	public Gfo_cmd_itm_chkr(String key, Object val) {this.key = key; this.val = val;} private String key; Object val;
	public Class<?> TypeOf() {return Gfo_cmd_arg_itm.class;}
	public int Chk(Tst_mgr mgr, String path, Object actl_obj) {
		Gfo_cmd_arg_itm actl = (Gfo_cmd_arg_itm)actl_obj;
		int err = 0;
		err += mgr.Tst_val(false, path, "key", key, actl.Key());
		err += mgr.Tst_val(false, path, "val", val, actl.Val());
		return err;
	}
}
