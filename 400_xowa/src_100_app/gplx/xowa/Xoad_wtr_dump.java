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
package gplx.xowa; import gplx.*;
public class Xoad_wtr_dump {
	public Xoad_wtr_dump(Io_url log_dir) {this.log_dir = log_dir;} Io_url log_dir;
	//Xoad_dump_itm[] fil_ary = new Xoad_dump_itm[64]; int fil_ary_len = 0;
	int fil_ary_size = 0; 
	final int bfr_ary_size_max = 1024 * 1024 * 1;
	public void Write(byte[] ttl, int page_idx, Gfo_msg_log msg_log) {
		int ary_len = msg_log.Ary_len(); boolean flush_me = false;
		for (int i = 0; i < ary_len; i++) {
			Gfo_msg_data data = msg_log.Ary_get(i);
			Xoad_dump_itm rv = GetByItm(data);
			if (rv.BfrLen() > bfr_ary_size_max) flush_me = true;
			fil_ary_size += rv.Write(ttl, page_idx, data);
		}
		if (fil_ary_size > bfr_ary_size_max || flush_me)
			Flush();
	}
	public void Flush() {
		int len = dump_itms.Count();
		for (int i = 0; i < len; i++) {
			Xoad_dump_itm data = (Xoad_dump_itm)dump_itms.Get_at(i);
			if (data != null) {
				data.Flush(log_dir);
				data.Rls();
			}
		}
		dump_itms.Clear();
		fil_ary_size = 0;	
	}
	Xoad_dump_itm GetByItm(Gfo_msg_data data) {
		byte[] path_bry = data.Item().Path_bry();
		Xoad_dump_itm rv = (Xoad_dump_itm)dump_itms.Get_by(path_bry);
		if (rv == null) {
			rv = new Xoad_dump_itm(path_bry, data.Item().Key_bry());
			dump_itms.Add(path_bry, rv);
		}
		return rv;
	}	private Ordered_hash dump_itms = Ordered_hash_.new_bry_();
}
class Xoad_dump_itm {
	Bry_bfr bfr = Bry_bfr.new_(4096);
	int pageIdx_last;
	public int BfrLen() {return bfr.Len();}
	public Xoad_dump_itm(byte[] ownerKey, byte[] itmKey) {
		fil_name = String_.new_u8(ownerKey) + "__" + String_.new_u8(itmKey);
	}	String fil_name;
	public void Flush(Io_url log_dir) {
		Io_url fil_url = log_dir.GenSubFil_ary(fil_name, ".txt");
		Io_mgr.I.AppendFilByt(fil_url, bfr.Bfr(), bfr.Len());
		bfr.Reset_if_gt(Io_mgr.Len_kb);
	}
	public void Rls() {
		bfr.Rls();
		bfr = null;
	}
	public int Write(byte[] ttl, int page_idx, Gfo_msg_data eny) {
		int old = bfr.Len();
		if (page_idx != pageIdx_last) {pageIdx_last = page_idx; bfr.Add(ttl).Add_byte_nl();}
		bfr.Add_byte_repeat(Byte_ascii.Space, 4);
		byte[] src = eny.Src_bry();
		if (src.length != 0) {
			int mid_bgn = eny.Src_bgn(), mid_end = eny.Src_end();
			int all_bgn = mid_bgn - 40; if (all_bgn < 0)			all_bgn = 0;
			int all_end = mid_end + 40; if (all_end > src.length)	all_end = src.length;
			Write_mid(src, all_bgn, mid_bgn);
			bfr.Add_byte(Byte_ascii.Tilde).Add_byte(Byte_ascii.Num_0).Add_byte(Byte_ascii.Tilde);
			Write_mid(src, mid_bgn, mid_end);
			bfr.Add_byte(Byte_ascii.Tilde).Add_byte(Byte_ascii.Num_1).Add_byte(Byte_ascii.Tilde);
			Write_mid(src, mid_end, all_end);
			bfr.Add_byte_nl();
		}
		return bfr.Len() - old;
	}
	private void Write_mid(byte[] src, int bgn, int end) {
		if (end - bgn == 0) return;
		for (int i = bgn; i < end; i++) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.NewLine:	bfr.Add_byte(Byte_ascii.Backslash); bfr.Add_byte(Byte_ascii.Ltr_n); break;
				case Byte_ascii.Tab:		bfr.Add_byte(Byte_ascii.Backslash); bfr.Add_byte(Byte_ascii.Ltr_t); break;
				default:					bfr.Add_byte(b); break;
			}
		}
	}
}
