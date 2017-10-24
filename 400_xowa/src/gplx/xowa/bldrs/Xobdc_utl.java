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
package gplx.xowa.bldrs; import gplx.*; import gplx.xowa.*;
import gplx.core.ios.*; import gplx.core.lists.*;
import gplx.xowa.wikis.tdbs.*;
class Io_sort_filCmd_reg implements Io_sort_filCmd { // 123|bgn|end|1
	public Io_sort_filCmd_reg() {}
	public void Bfr_add(Io_line_rdr stream) {
		++itm_count;
		int key_bgn = stream.Key_pos_bgn(), key_end = stream.Key_pos_end();
		Bry_.Copy_by_pos(stream.Bfr(), key_bgn, key_end, prv_key, 0); prv_key_len = key_end - key_bgn; 
	}	byte[] prv_key = new byte[1024]; int prv_key_len = 0;
	public void Fil_bgn(Io_line_rdr stream) {
		bfr.Add_int_variable(fil_idx++).Add_byte(Byte_ascii.Pipe);
		bfr.Add_mid(stream.Bfr(), stream.Key_pos_bgn(), stream.Key_pos_end()).Add_byte(Byte_ascii.Pipe);
	}	
	public void Fil_end() {
		bfr.Add_mid(prv_key, 0, prv_key_len).Add_byte(Byte_ascii.Pipe)
			.Add_int_variable(itm_count).Add_byte(Byte_ascii.Nl);
		itm_count = 0;
	}
	public void Flush(Io_url fil) {
		Io_mgr.Instance.SaveFilBry(fil, bfr.Bfr(), bfr.Len());
	}	private Bry_bfr bfr = Bry_bfr_.New(); int fil_idx = 0; int itm_count = 0;
}
class Io_url_gen_nest implements gplx.core.ios.Io_url_gen {
	public Io_url Cur_url() {return cur_url;} Io_url cur_url;
	public Io_url Nxt_url() {cur_url = Xotdb_fsys_mgr.Url_fil(root_dir, fil_idx++, ext); return cur_url;}
	public Io_url[] Prv_urls() {
		Io_url[] rv = new Io_url[fil_idx];
		for (int i = 0; i < fil_idx; i++) {
			rv[i] = Xotdb_fsys_mgr.Url_fil(root_dir, fil_idx++, ext);
		}
		return rv;
	}
	public void Del_all() {if (Io_mgr.Instance.ExistsDir(root_dir)) Io_mgr.Instance.DeleteDirDeep(root_dir);}
	public Io_url_gen_nest(Io_url root_dir, String ext) {this.root_dir = root_dir; this.ext = Bry_.new_u8(ext);} Io_url root_dir; byte[] ext; int fil_idx;
}
