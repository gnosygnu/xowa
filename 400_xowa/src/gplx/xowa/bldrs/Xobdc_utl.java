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
package gplx.xowa.bldrs; import gplx.*; import gplx.xowa.*;
import gplx.ios.*; import gplx.lists.*;
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
	}	private Bry_bfr bfr = Bry_bfr.new_(); int fil_idx = 0; int itm_count = 0;
}
class Io_url_gen_nest implements gplx.ios.Io_url_gen {
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
