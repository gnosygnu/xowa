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
package gplx.xowa.dbs.hdumps; import gplx.*; import gplx.xowa.*; import gplx.xowa.dbs.*;
public class Hdump_text_row {
	public Hdump_text_row(int page_id, int tid, int idx, int version_id, byte[] data) {
		this.page_id = page_id; this.tid = tid; this.idx = idx; this.version_id = version_id; this.data = data;
	}
	public int Page_id() {return page_id;} private int page_id;
	public int Tid() {return tid;} private int tid;
	public int Idx() {return idx;} private int idx;
	public int Version_id() {return version_id;} private int version_id;
	public byte[] Data() {return data;} private byte[] data;
	public static byte[] data_img_(Bry_bfr bfr, int uid, int img_w, int img_h, byte[] lnki_ttl, byte[] img_src_rel) {
		bfr					.Add_int_variable(uid)
			.Add_byte_pipe().Add_int_variable(img_w)
			.Add_byte_pipe().Add_int_variable(img_h)
			.Add_byte_pipe().Add(lnki_ttl)
			.Add_byte_pipe().Add(img_src_rel)
			;
		return bfr.XtoAryAndClear();
	}
}
