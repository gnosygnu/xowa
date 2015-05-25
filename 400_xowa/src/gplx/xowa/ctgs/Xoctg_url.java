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
package gplx.xowa.ctgs; import gplx.*; import gplx.xowa.*;
import gplx.core.primitives.*;
public class Xoctg_url {
	public byte[][]		Grp_idxs() {return grp_idxs;} private byte[][] grp_idxs = new byte[3][];
	public byte[]		Grp_fwds() {return grp_fwds;} private byte[] grp_fwds = new byte[3];
	private void Clear() {
		for (int i = 0; i < 3; i++) {
			grp_fwds[i] = Bool_.__byte;
			grp_idxs[i] = null;
		}
	}
	public Xoctg_url Parse(Gfo_usr_dlg usr_dlg, Xoa_url url) {
		this.Clear();
		Gfo_url_arg[] args = url.Args();
		int len = args.length;
		for (int i = 0; i < len; i++) {
			Gfo_url_arg arg = args[i];
			byte[] arg_key = arg.Key_bry();
			Object tid_obj = Arg_keys.Get_by_bry(arg_key);
			if (tid_obj == null) {usr_dlg.Warn_many("", "", "unknown arg_key: ~{0}", String_.new_u8(arg_key)); continue;} // ignore invalid args
			byte[] arg_val = arg.Val_bry();
			byte tid = ((Byte_obj_val)tid_obj).Val();
			switch (tid) {
				case Tid_all_bgn: 	Set_grp(arg_val, Bool_.Y_byte, Xoa_ctg_mgr.Tid_subc, Xoa_ctg_mgr.Tid_file, Xoa_ctg_mgr.Tid_page); break;	// if "from", default all grps to val; DATE:2014-02-05
				case Tid_all_end: 	Set_grp(arg_val, Bool_.N_byte, Xoa_ctg_mgr.Tid_subc, Xoa_ctg_mgr.Tid_file, Xoa_ctg_mgr.Tid_page); break;
				case Tid_subc_bgn: 	Set_grp(arg_val, Bool_.Y_byte, Xoa_ctg_mgr.Tid_subc); break;
				case Tid_subc_end:  Set_grp(arg_val, Bool_.N_byte, Xoa_ctg_mgr.Tid_subc); break;
				case Tid_file_bgn:	Set_grp(arg_val, Bool_.Y_byte, Xoa_ctg_mgr.Tid_file); break;
				case Tid_file_end:  Set_grp(arg_val, Bool_.N_byte, Xoa_ctg_mgr.Tid_file); break;
				case Tid_page_bgn:	Set_grp(arg_val, Bool_.Y_byte, Xoa_ctg_mgr.Tid_page); break;
				case Tid_page_end:  Set_grp(arg_val, Bool_.N_byte, Xoa_ctg_mgr.Tid_page); break;
			}
		}
		return this;
	}
	private void Set_grp(byte[] val, byte fwd, byte... tids) {
		int tids_len = tids.length;
		for (int i = 0; i < tids_len; i++) {
			byte tid = tids[i];
			grp_fwds[tid] = fwd;
			grp_idxs[tid] = val;
		}
	}
	public static final byte Tid_all_bgn = 0, Tid_subc_bgn = 1, Tid_subc_end = 2, Tid_file_bgn = 3, Tid_file_end = 4, Tid_page_bgn = 5, Tid_page_end = 6, Tid_all_end = 8;
	public static final Hash_adp_bry Arg_keys = Hash_adp_bry.ci_ascii_()
	.Add_bry_byte(Xoctg_fmtr_all.Url_arg_from, Tid_all_bgn)
	.Add_bry_byte(Xoctg_fmtr_all.Url_arg_until, Tid_all_end)
	.Add_bry_byte(Xoctg_fmtr_all.Url_arg_subc_bgn, Tid_subc_bgn)
	.Add_bry_byte(Xoctg_fmtr_all.Url_arg_subc_end, Tid_subc_end)
	.Add_bry_byte(Xoctg_fmtr_all.Url_arg_file_bgn, Tid_file_bgn)
	.Add_bry_byte(Xoctg_fmtr_all.Url_arg_file_end, Tid_file_end)
	.Add_bry_byte(Xoctg_fmtr_all.Url_arg_page_bgn, Tid_page_bgn)
	.Add_bry_byte(Xoctg_fmtr_all.Url_arg_page_end, Tid_page_end)
	;
}
