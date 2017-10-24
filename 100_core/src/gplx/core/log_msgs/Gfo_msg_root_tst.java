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
package gplx.core.log_msgs; import gplx.*; import gplx.core.*;
import org.junit.*;
public class Gfo_msg_root_tst {
	Gfo_msg_root_fxt fxt = new Gfo_msg_root_fxt();
	@Before public void setup() {fxt.Reset();}
	@Test  public void Str() {
		fxt.Clear().Expd_data_str_("failed a0 b0").Tst_data_new_many("proj.cls.proc", "err_0", "failed ~{0} ~{1}", "a0", "b0");
		fxt.Clear().Expd_data_str_("failed a1 b1").Tst_data_new_many("proj.cls.proc", "err_0", "failed ~{0} ~{1}", "a1", "b1");
	}
//		@Test  public void Item() {	// DISABLED: no longer registering items with owner;
//			fxt.Clear().Expd_item_uid_(0).Expd_item_fmtr_arg_exists_(Bool_.Y).Tst_data_new_many("proj.cls.proc", "err_0", "failed ~{0} ~{1}", "a0", "b0");
//			fxt.Clear().Expd_item_uid_(1).Expd_item_fmtr_arg_exists_(Bool_.N).Tst_data_new_many("proj.cls.proc", "err_1", "failed");
//			fxt.Clear().Expd_item_uid_(0).Tst_data_new_many("proj.cls.proc", "err_0", "failed ~{0} ~{1}", "a0", "b0");	// make sure item_uid stays the same
//		}
	@Test  public void Cache() {
		fxt.Mgr().Data_ary_len_(2);
		fxt.Clear().Expd_data_uid_(0).Tst_data_new_many("x", "err_0", "a");
		fxt.Clear().Expd_data_uid_(1).Tst_data_new_many("x", "err_0", "b");
		fxt.Clear().Expd_data_uid_(2).Tst_data_new_many("x", "err_0", "a");
		fxt.Mgr().Data_ary_clear();
		fxt.Clear().Expd_data_uid_(0).Tst_data_new_many("x", "err_0", "a");
	}
}
class Gfo_msg_root_fxt {
	Gfo_msg_root root = new Gfo_msg_root("tst");
	public Gfo_msg_root_fxt Reset() {root.Reset(); this.Clear(); return this;}
	public Gfo_msg_root_fxt Clear() {
		expd_item_uid = -1;
		expd_item_fmtr_arg_exists = Bool_.__byte;
		expd_data_uid = -1;
		expd_data_str = null;
		return this;
	}
	public Gfo_msg_root Mgr() {return root;}
	public Gfo_msg_root_fxt Expd_data_uid_(int v) {this.expd_data_uid = v; return this;} int expd_data_uid;
	public Gfo_msg_root_fxt Expd_data_str_(String v) {this.expd_data_str = v; return this;} private String expd_data_str;
	public Gfo_msg_root_fxt Expd_item_uid_(int v) {this.expd_item_uid = v; return this;} int expd_item_uid;
	public Gfo_msg_root_fxt Expd_item_fmtr_arg_exists_(boolean v) {this.expd_item_fmtr_arg_exists = v ? Bool_.Y_byte : Bool_.N_byte; return this;} private byte expd_item_fmtr_arg_exists;
	public void Tst_data_new_many(String path, String key, String fmt, Object... vals) {
		Gfo_msg_data data = root.Data_new_many(Gfo_msg_itm_.Cmd_note, path, key, fmt, vals);
		if (expd_item_uid != -1)	Tfds.Eq(expd_item_uid, data.Item().Uid());;
		if (expd_item_fmtr_arg_exists != Bool_.__byte) Tfds.Eq(Bool_.By_int(expd_item_fmtr_arg_exists), data.Item().Fmtr().Fmt_args_exist());
		if (expd_data_str != null)	Tfds.Eq(expd_data_str, data.Item().Gen_str_many(data.Vals()));
	}
}
