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
package gplx.xowa.apps.fsys; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.core.brys.fmtrs.*;
import gplx.core.primitives.*; import gplx.xowa.users.*;
public class Xoa_fsys_eval implements Bry_fmtr_eval_mgr {
	private final Xoa_fsys_mgr app_fsys_mgr; private final Xou_fsys_mgr usr_fsys_mgr;
	public Xoa_fsys_eval(Xoa_fsys_mgr app_fsys_mgr, Xou_fsys_mgr usr_fsys_mgr) {this.app_fsys_mgr = app_fsys_mgr; this.usr_fsys_mgr = usr_fsys_mgr;}
	public boolean Enabled() {return enabled;} public void Enabled_(boolean v) {enabled = v;} private boolean enabled = true;
	public byte[] Eval(byte[] cmd) {
		Object o = hash.Get_by_bry(cmd); if (o == null) return null;
		byte val = ((Byte_obj_val)o).Val();
		switch (val) {
			case Tid__xowa_root_dir:	return app_fsys_mgr.Root_dir().RawBry();
			case Tid__bin_plat_dir:		return app_fsys_mgr.Bin_plat_dir().RawBry();
			case Tid__user_temp_dir:	return usr_fsys_mgr.App_temp_dir().RawBry();
			case Tid__user_cfg_dir:		return usr_fsys_mgr.App_data_cfg_dir().RawBry();
			default:					throw Err_.new_unhandled(val);
		}
	}
	private static final byte Tid__bin_plat_dir = 0, Tid__user_temp_dir = 1, Tid__xowa_root_dir = 2, Tid__user_cfg_dir = 3;
	private static final Hash_adp_bry hash = Hash_adp_bry.ci_a7()
	.Add_str_byte("bin_plat_dir"	, Tid__bin_plat_dir)
	.Add_str_byte("user_temp_dir"	, Tid__user_temp_dir)
	.Add_str_byte("xowa_root_dir"	, Tid__xowa_root_dir)
	.Add_str_byte("user_cfg_dir"	, Tid__user_cfg_dir)
	;
}
