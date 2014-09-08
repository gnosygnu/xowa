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
package gplx.xowa.hdumps.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
import gplx.xowa.hdumps.core.*; import gplx.xowa.files.*;
public class Xopg_hdump_data {
	private final int file_dir_bry_len;
	public Xopg_hdump_data(Xoa_app app) {file_dir_bry_len = app.Fsys_mgr().File_dir_bry_len();}
	public int Data_count_imgs() {return data_count_imgs;} public void Data_count_imgs_(int v) {data_count_imgs = v;} private int data_count_imgs;
	public ListAdp Data() {return data;} private final ListAdp data = ListAdp_.new_();
	public void Data_add(Hdump_data_itm itm) {data.Add(itm);}
	public void Data_add_img(Hdump_data_img__base img, Xof_xfer_itm xfer_itm, int tid) {
		byte[] img_src = xfer_itm.Html_view_src();
		img_src = Bry_.Len_eq_0(img_src) ? Bry_.Empty : Bry_.Mid(img_src, file_dir_bry_len);
		img.Init_by_base(xfer_itm.Html_uid(), xfer_itm.Html_w(), xfer_itm.Html_h(), xfer_itm.Lnki_ttl(), img_src);
		data.Add(img);
		++data_count_imgs;
	}
	public byte[] Body() {return body;} public void Body_(byte[] v) {body = v;} private byte[] body;
	public void Clear() {
		data_count_imgs = 0;
		data.Clear();
		body = null;
	}
}
