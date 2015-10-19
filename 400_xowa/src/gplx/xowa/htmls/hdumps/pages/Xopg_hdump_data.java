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
package gplx.xowa.htmls.hdumps.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.hdumps.*;
import gplx.xowa.htmls.hdumps.core.*; import gplx.xowa.files.*;
import gplx.xowa.parsers.lnkis.redlinks.*;
public class Xopg_hdump_data {
	public byte[]					Body() {return body;} public void Body_(byte[] v) {body = v;} private byte[] body;
	public Xopg_redlink_idx_list	Redlink_mgr() {return redlink_mgr;} private final Xopg_redlink_idx_list redlink_mgr = new Xopg_redlink_idx_list();
	public List_adp					Imgs() {return imgs;} private final List_adp imgs = List_adp_.new_();
	public void						Imgs_add(Xohd_data_itm itm) {imgs.Add(itm);}
	public void						Imgs_add_img(Xohd_data_itm__base img, Xof_file_itm xfer, int tid) {
		img.Data_init_base
		( xfer.Lnki_ttl(), xfer.Lnki_type(), xfer.Lnki_upright(), xfer.Lnki_w(), xfer.Lnki_h(), xfer.Lnki_time(), xfer.Lnki_page()
		, xfer.Orig_repo_id(), xfer.Orig_ext().Id(), xfer.File_is_orig(), xfer.File_w()
		, xfer.Html_uid(), xfer.Html_w(), xfer.Html_h()
		);
		imgs.Add(img);
	}
	public void Clear() {
		body = null;
		imgs.Clear();
		redlink_mgr.Clear();
	}
}
