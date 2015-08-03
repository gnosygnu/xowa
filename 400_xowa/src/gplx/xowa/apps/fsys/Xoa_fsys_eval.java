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
package gplx.xowa.apps.fsys; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.core.primitives.*; import gplx.xowa.users.*;
public class Xoa_fsys_eval implements Bry_fmtr_eval_mgr {
	private final Xoa_fsys_mgr app_fsys_mgr; private final Xou_fsys_mgr usr_fsys_mgr;
	public Xoa_fsys_eval(Xoa_fsys_mgr app_fsys_mgr, Xou_fsys_mgr usr_fsys_mgr) {this.app_fsys_mgr = app_fsys_mgr; this.usr_fsys_mgr = usr_fsys_mgr;}
	public boolean Enabled() {return enabled;} public void Enabled_(boolean v) {enabled = v;} private boolean enabled = true;
	public byte[] Eval(byte[] cmd) {
		Object o = hash.Get_by_bry(cmd); if (o == null) return null;
		byte val = ((Byte_obj_val)o).Val();
		switch (val) {
			case Tid_xowa_root_dir:		return app_fsys_mgr.Root_dir().RawBry();
			case Tid_bin_plat_dir:		return app_fsys_mgr.Bin_plat_dir().RawBry();
			case Tid_user_temp_dir:		return usr_fsys_mgr.App_temp_dir().RawBry();
			case Tid_user_cfg_dir:		return usr_fsys_mgr.App_data_cfg_dir().RawBry();
			default:					throw Err_.new_unhandled(val);
		}
	}
	private static final byte Tid_bin_plat_dir = 0, Tid_user_temp_dir = 1, Tid_xowa_root_dir = 2, Tid_user_cfg_dir = 3;
	private static final Hash_adp_bry hash = Hash_adp_bry.ci_a7()
	.Add_str_byte("bin_plat_dir"	, Tid_bin_plat_dir)
	.Add_str_byte("user_temp_dir"	, Tid_user_temp_dir)
	.Add_str_byte("xowa_root_dir"	, Tid_xowa_root_dir)
	.Add_str_byte("user_cfg_dir"	, Tid_user_cfg_dir)
	;
}
