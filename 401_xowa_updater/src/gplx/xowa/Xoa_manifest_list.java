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
public class Xoa_manifest_list {
	private final    Ordered_hash hash = Ordered_hash_.New();
	public int Len() {return hash.Len();}
	public Xoa_manifest_item Get_at(int i) {return (Xoa_manifest_item)hash.Get_at(i);}
	public void Del_at(int i) {
		Xoa_manifest_item item = (Xoa_manifest_item)hash.Get_at(i);
		hash.Del(item.Src().Raw());
	}
	public void Add(Io_url src, Io_url trg) {
		Xoa_manifest_item item = new Xoa_manifest_item(src, trg);
		hash.Add(src.Raw(), item);
	}
	public void Save(Bry_bfr bfr) {
		// save as "src\ttrg\n"
		int len = hash.Len();
		for (int i = 0; i < len; i++) {
			Xoa_manifest_item item = (Xoa_manifest_item)hash.Get_at(i);
			bfr.Add_str_u8(item.Src().Raw());
			bfr.Add_byte(Byte_ascii.Tab);
			bfr.Add_str_u8(item.Trg().Raw());
			bfr.Add_byte(Byte_ascii.Nl);
		}
	}
	public void Load(byte[] src, int src_bgn, int src_end) {
		int pos = src_bgn;
		// load by "src\ttrg\n"
		while (pos < src_end) {
			// get pos
			int tab_pos = Bry_find_.Find_fwd(src, Byte_ascii.Tab, pos, src_end);
			if (tab_pos == Bry_find_.Not_found) throw Err_.new_wo_type("failed to find tab", "excerpt", Bry_.Mid(src, pos, src_end));
			int nl_pos  = Bry_find_.Find_fwd(src, Byte_ascii.Nl, tab_pos + 1, src_end);
			if (nl_pos == Bry_find_.Not_found) throw Err_.new_wo_type("failed to find nl", "excerpt", Bry_.Mid(src, pos, src_end));

			// create
			Io_url src_url = Io_url_.new_fil_(String_.new_u8(src, pos, tab_pos));
			Io_url trg_url = Io_url_.new_fil_(String_.new_u8(src, tab_pos + 1, nl_pos));
			Xoa_manifest_item item = new Xoa_manifest_item(src_url, trg_url);
			hash.Add(src_url.Raw(), item);

			pos = nl_pos + 1;
		}
	}
}
