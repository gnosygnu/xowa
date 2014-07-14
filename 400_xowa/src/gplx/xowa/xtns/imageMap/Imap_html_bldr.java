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
package gplx.xowa.xtns.imageMap; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
class Imap_html_bldr {
	private Pts_fmtr_arg pts_fmtr_arg = new Pts_fmtr_arg();
	public void Bld_map(Bry_bfr rslt_bfr, Xow_wiki wiki, Imap_itm[] itms) {
		int itms_len = itms.length;
		// Imap_itm img_itm = itms[0];
		for (int i = 0; i < itms_len; ++i) {
			Imap_itm itm = itms[i];
			switch (itm.Itm_tid()) {
				case Imap_itm_.Tid_shape_dflt:
				case Imap_itm_.Tid_shape_rect:
				case Imap_itm_.Tid_shape_circle:
				case Imap_itm_.Tid_shape_poly:
					Bld_map_shape(rslt_bfr, wiki, (Imap_itm_shape)itm);
					break;
			}
		}
	}
	private void Bld_map_shape(Bry_bfr rslt_bfr, Xow_wiki wiki, Imap_itm_shape itm) {
		byte[] shape_name = Imap_itm_.Xto_str(itm.Itm_tid());
		pts_fmtr_arg.Init(itm.Shape_pts());
		area_fmtr.Bld_bfr_many(rslt_bfr, itm.Shape_link_href(), itm.Shape_link_caption(), shape_name, pts_fmtr_arg);
	}
	private static final Bry_fmtr area_fmtr = Bry_fmtr.new_
	("<area href=\"~{href}\" shape=\"~{shape}\" coords=\"~{pts}\" alt=\"{caption}\" title=\"~{caption}\"/>"
	, "href", "caption", "shape", "pts"
	);
}
class Pts_fmtr_arg implements Bry_fmtr_arg {
	private Double_obj_val[] pts;
	public void Init(Double_obj_val[] pts) {this.pts = pts;}
	public void XferAry(Bry_bfr bfr, int idx) {
		int pts_len = pts.length;
		for (int i = 0; i < pts_len; ++i) {
			if (i != 0) bfr.Add_byte(Byte_ascii.Comma);
			bfr.Add_double(pts[i].Val());
		}
	}
}
