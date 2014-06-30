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
package gplx.xowa.files.qrys; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.xowa.dbs.tbls.*; import gplx.xowa.files.fsdb.*; import gplx.xowa.bldrs.wikis.images.*;
public class Xof_qry_wkr_xowa implements Xof_qry_wkr {
	private Xof_wiki_finder wiki_finder;
	private Xof_img_meta_wkr img_meta_wkr;
	private int qry_count, qry_count_max = 1000;
	public Xof_qry_wkr_xowa(Xof_wiki_finder wiki_finder, Xof_img_meta_wkr img_meta_wkr) {
		this.wiki_finder = wiki_finder;
		this.img_meta_wkr = img_meta_wkr;
	}
	public byte Tid() {return Xof_qry_wkr_.Tid_xowa_db;}
	public boolean Qry_file(Xof_fsdb_itm itm) {
		byte[] ttl = itm.Lnki_ttl();
		Xoa_page page = wiki_finder.Get_page(Xow_ns_.Id_file, ttl);
		++qry_count;
		if (qry_count >= qry_count_max) {
			page.Wiki().App().Reset_all();
			qry_count = 0;
		}
		if (page.Missing()) return false;	// ttl not found in wikis; exit;
		Xow_wiki wiki = page.Wiki();
		itm.Orig_wiki_(wiki.Domain_bry());
		Xoa_ttl redirect_ttl = wiki.Redirect_mgr().Extract_redirect_loop(page.Data_raw());
		if (redirect_ttl != Xop_redirect_mgr.Extract_redirect_is_null)
			itm.Orig_redirect_(redirect_ttl.Full_txt());
		Xob_wiki_image_itm img = img_meta_wkr.Find(wiki, ttl);
		if (img == Xob_wiki_image_itm.Null) return false;	// ttl not found in image db; exit
		itm.Orig_size_(img.Width(), img.Height());
		return true;
	}
	private Xof_wiki_finder_itm finder_itm = new Xof_wiki_finder_itm();
	public boolean Qry_file2(Xof_fsdb_itm itm) {
		byte[] ttl = itm.Lnki_ttl();
		if (!wiki_finder.Find_page(finder_itm, Xow_ns_.Id_file, ttl)) return false; // ttl not found in wikis; exit;
		itm.Orig_wiki_(finder_itm.Orig_wiki().Domain_bry());
		if (finder_itm.Orig_redirect() != null)
			itm.Orig_redirect_(finder_itm.Orig_redirect().Full_txt());		
		Xob_wiki_image_itm img = img_meta_wkr.Find(finder_itm.Orig_wiki(), ttl);
		if (img == Xob_wiki_image_itm.Null) return false;	// ttl not found in image db; exit
		itm.Orig_size_(img.Width(), img.Height());
		return true;
	}
}
