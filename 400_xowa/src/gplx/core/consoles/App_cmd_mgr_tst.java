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
package gplx.core.consoles; import gplx.*; import gplx.core.*;
import org.junit.*; import gplx.core.tests.*;
public class App_cmd_mgr_tst {
	App_cmd_mgr_fxt fxt = new App_cmd_mgr_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Basic() {
		fxt	.Expd_(fxt.arg_("a"), fxt.arg_("b"))
			.Args_process("--a", "0", "--b", "1")
			.Tst_errs_none()
			.Tst_actl(fxt.chkr_("a", "0"), fxt.chkr_("b", "1"));
		;
	}
	@Test  public void Dflt() {
		fxt	.Expd_(fxt.arg_("a").Val_tid_(App_cmd_arg.Val_tid_yn).Dflt_(true));
		fxt .Args_process("--a", "n").Tst_actl(fxt.chkr_("a", false));	// if val, use it
		fxt .Args_process().Tst_actl(fxt.chkr_("a", true));				// if no val, use default
	}
	@Test  public void Header_y() {
		fxt.Expd_(App_cmd_arg.sys_header_("print_license")).Args_process("--print_license", "y");
		fxt.Mgr().Fmt_hdr_("test_hdr").Print_header(fxt.Usr_dlg());
		fxt.tst_write("test_hdr");
		fxt.Clear();
	}
	@Test  public void Header_n() {
		fxt.Expd_(App_cmd_arg.sys_header_("print_license")).Args_process("--print_license", "n");
		fxt.Mgr().Fmt_hdr_("test_hdr").Print_header(fxt.Usr_dlg());
		fxt.tst_write();
	}
//		@Test  public void Help_y() {
//			fxt.Expd_(App_cmd_arg.sys_header_("help")).Args_process("--help");
////			fxt.Mgr().Fmt_help_grp("bgn ~{args} end").Fmt_help_itm_("~{0} ~{1}").Print_header(fxt.Status_mgr());
////			fxt.Tst_write("test_hdr");
//			fxt.Clear();
//		}
	@Test  public void Err_parse_yn() {
		fxt	.Expd_(fxt.arg_("a").Val_tid_(App_cmd_arg.Val_tid_yn))
			.Args_process("--a", "x")
			.Tst_errs(App_cmd_arg.Err_parse_yn);
		;
	}
	@Test  public void Err_reqd() {
		fxt	.Expd_(fxt.arg_("a", true), fxt.arg_("b", false))
			.Args_process("--b", "1")
			.Tst_errs(App_cmd_mgr.Err_argument_is_required);
		;
	}
	@Test  public void Err_dupe() {
		fxt	.Expd_(fxt.arg_("a"))
			.Args_process("--a", "0", "--a", "0")
			.Tst_errs(App_cmd_mgr.Err_argument_is_duplicate);
		;
	}
	@Test  public void Err_unknown() {
		fxt	.Expd_(fxt.arg_("a"))
			.Args_process("--b")
			.Tst_errs(App_cmd_mgr.Err_argument_is_unknown);
		;
	}
	@Test  public void Err_key_invalid() {
		fxt	.Expd_(fxt.arg_("a"))
			.Args_process("a")
			.Tst_errs(App_cmd_mgr.Err_argument_is_invalid_key);
		;
	}
	@Test  public void Val_as_url_rel_dir_or() {	// PURPOSE: "/xowa" -> "/xowa/"
		String root_dir = Op_sys.Cur().Tid_is_wnt() ? "C:\\" : "/", dir_spr = Op_sys.Cur().Fsys_dir_spr_str();
		Tst_val_as_url_rel_dir_or(root_dir, dir_spr, root_dir + "sub"			, root_dir + "sub" + dir_spr);						// /sub   -> /sub/
		Tst_val_as_url_rel_dir_or(root_dir, dir_spr, root_dir + "sub" + dir_spr	, root_dir + "sub" + dir_spr);						// /sub/  -> /sub/
		Tst_val_as_url_rel_dir_or(root_dir, dir_spr, "sub"						, root_dir + "dir" + dir_spr + "sub" + dir_spr);	// sub    -> /dir/sub/
	}
	private void Tst_val_as_url_rel_dir_or(String root_dir, String dir_spr, String val, String expd) {
		Io_url actl = fxt.arg_("key").Val_(val).Val_as_url_rel_dir_or(Io_url_.new_dir_(root_dir).GenSubDir("dir"), null);
		Tfds.Eq(expd, actl.Raw());
	}
}
class App_cmd_mgr_fxt {
	public Gfo_usr_dlg Usr_dlg() {return usr_dlg;} Gfo_usr_dlg usr_dlg;
	public App_cmd_mgr Mgr() {return mgr;} App_cmd_mgr mgr = new App_cmd_mgr(); Tst_mgr tst_mgr = new Tst_mgr();
	public App_cmd_mgr_fxt Clear() {
		if (usr_dlg == null) {
			usr_dlg = Gfo_usr_dlg_.Test();
		}
		mgr.Clear();
		usr_dlg.Gui_wkr().Clear();
		return this;
	}
	public App_cmd_arg arg_(String key) {return arg_(key, false);}
	public App_cmd_arg arg_(String key, boolean reqd) {return App_cmd_arg.new_(key, reqd);}
	public App_cmd_arg_chkr chkr_(String key, Object val) {return new App_cmd_arg_chkr(key, val);}
	public App_cmd_mgr_fxt Expd_(App_cmd_arg... v) {mgr.Expd_add_many(v); return this;}
	public App_cmd_mgr_fxt Args_process(String... v) {mgr.Args_process(v); return this;}
	public App_cmd_mgr_fxt Tst_actl_len(int v) {Tfds.Eq(v, mgr.Actl_len()); return this;}
	public App_cmd_mgr_fxt Tst_actl(App_cmd_arg_chkr... expd) {
		App_cmd_arg[] actl = mgr.Actl_ary();
		tst_mgr.Tst_ary("", expd, actl);
		return this;
	}
	public App_cmd_mgr_fxt Tst_errs_none() {return Tst_errs(String_.Ary_empty);}
	public App_cmd_mgr_fxt Tst_errs(String... expd) {
		int len = mgr.Errs_len();
		String[] actl = new String[len];
		for (int i = 0; i < len; i++) {
			Gfo_msg_data data = mgr.Errs_get(i);
			actl[i] = data.Item().Key_str();
		}
		Tfds.Eq_ary_str(expd, actl);
		return this;
	}
	public App_cmd_mgr_fxt tst_write(String... expd) {
		String[] actl = ((Gfo_usr_dlg__gui_test)usr_dlg.Gui_wkr()).Xto_str_ary();
		Tfds.Eq_ary_str(expd, actl);
		return this;
	}
}
class App_cmd_arg_chkr implements Tst_chkr {
	public App_cmd_arg_chkr(String key, Object val) {this.key = key; this.val = val;} private String key; Object val;
	public Class<?> TypeOf() {return App_cmd_arg.class;}
	public int Chk(Tst_mgr mgr, String path, Object actl_obj) {
		App_cmd_arg actl = (App_cmd_arg)actl_obj;
		int err = 0;
		err += mgr.Tst_val(false, path, "key", key, actl.Key());
		err += mgr.Tst_val(false, path, "val", val, actl.Val());
		return err;
	}
}
