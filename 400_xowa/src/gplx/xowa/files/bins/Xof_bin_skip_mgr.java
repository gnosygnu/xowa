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
package gplx.xowa.files.bins; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.ios.*;
import gplx.xowa.files.fsdb.*;
public class Xof_bin_skip_mgr {
	private Xof_bin_skip_wkr[] wkrs = new Xof_bin_skip_wkr[0]; private int wkrs_len;
	public Xof_bin_skip_mgr(String[] wkr_keys) {
		this.wkrs_len = wkr_keys.length;
		this.wkrs = new Xof_bin_skip_wkr[wkrs_len];
		for (int i = 0; i < wkrs_len; ++i)
			wkrs[i] = New_wkr(wkr_keys[i]);
	}
	public boolean Skip(Xof_fsdb_itm fsdb, Io_stream_rdr src_rdr) {
		for (int i = 0; i < wkrs_len; ++i) {
			if (wkrs[i].Skip(fsdb, src_rdr)) return true;
		}
		return false;
	}
	private Xof_bin_skip_wkr New_wkr(String key) {
		if		(String_.Eq(key, Xof_bin_skip_wkr_.Key__page_gt_1))		return Xof_bin_skip_wkr__page_gt_1.I;
		else if	(String_.Eq(key, Xof_bin_skip_wkr_.Key__small_size))	return Xof_bin_skip_wkr__small_size.I;
		else															throw Err_.unhandled(key);
	}
}
interface Xof_bin_skip_wkr {
	String Key();
	boolean Skip(Xof_fsdb_itm fsdb, Io_stream_rdr src_rdr);
}
class Xof_bin_skip_wkr_ {
	public static final String Key__page_gt_1 = "page_gt_1", Key__small_size = "small_size";
}
class Xof_bin_skip_wkr__page_gt_1 implements Xof_bin_skip_wkr {	// prior to v2.4.3; lnkis with page > 1 was mistakenly bringing down page 1; EX: [[A.pdf|page=5]] -> page1; DATE:2015-04-21
	public String Key() {return Xof_bin_skip_wkr_.Key__page_gt_1;}
	public boolean Skip(Xof_fsdb_itm fsdb, Io_stream_rdr src_rdr) {
		boolean rv = fsdb.Lnki_page() > 1;
		if (rv)
			Xoa_app_.Usr_dlg().Note_many("", "", "src_bin_mgr:skip page gt 1: file=~{0} width=~{1} page=~{2}", fsdb.Lnki_ttl(), fsdb.File_w(), fsdb.Lnki_page());
		return rv;
	}
        public static final Xof_bin_skip_wkr__page_gt_1 I = new Xof_bin_skip_wkr__page_gt_1(); Xof_bin_skip_wkr__page_gt_1() {}
}
class Xof_bin_skip_wkr__small_size implements Xof_bin_skip_wkr {// downloads can randomly be broken; assume that anything with a small size is broken and redownload again; DATE:2015-04-21
	public String Key() {return Xof_bin_skip_wkr_.Key__small_size;}
	public boolean Skip(Xof_fsdb_itm fsdb, Io_stream_rdr src_rdr) {
		boolean rv = 
			src_rdr.Len() < 500									// file is small (< 500 bytes)
		&&	fsdb.Html_w() > 50									// only apply to images larger than 50 px (arbitrarily chosen); needed to ignore 1x1 images as well as icon-sized images
		&&	Xof_ext_.Id_is_image_wo_svg(fsdb.Lnki_ext().Id())	// only consider images; needed b/c of file_w check; note:ignore svg which can be small
		;
		if (rv)
			Xoa_app_.Usr_dlg().Note_many("", "", "src_bin_mgr:skip small file: file=~{0} width=~{1} ext=~{2} len=~{3}", fsdb.Lnki_ttl(), fsdb.Lnki_w(), fsdb.Orig_ext(), src_rdr.Len());
		return rv;
	}
        public static final Xof_bin_skip_wkr__small_size I = new Xof_bin_skip_wkr__small_size(); Xof_bin_skip_wkr__small_size() {}
}
