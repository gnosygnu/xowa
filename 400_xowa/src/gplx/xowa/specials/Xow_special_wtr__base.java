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
package gplx.xowa.specials; import gplx.*; import gplx.xowa.*;
import gplx.langs.mustaches.*; import gplx.xowa.wikis.pages.*;
public abstract class Xow_special_wtr__base {
	public void Bld_page_by_mustache(Xoa_app app, Xoa_page page, Xow_special_page special) {
		Mustache_doc_itm mustache_root = Bld_mustache_root(app);
		if (mustache_root == null) {	// handle invalid urls; EX: Special:XowaWikiInfo?wiki=deleted_wiki
			Handle_invalid(app, page, special);
			return;
		}
		Io_url addon_dir = this.Get_addon_dir(app);
		byte[] body = Bld_html_body(addon_dir, mustache_root);
		Xopage_html_data page_data = new Xopage_html_data(special.Special__meta().Display_ttl(), body);
		Bld_tags(app, addon_dir, page_data);
		page_data.Apply(page);
	}
	@gplx.Virtual protected byte[] Bld_html_body(Io_url addon_dir, gplx.langs.mustaches.Mustache_doc_itm itm) {
		byte[] tmpl = Io_mgr.Instance.LoadFilBry(this.Get_mustache_fil(addon_dir));
		return gplx.langs.mustaches.Mustache_wtr_.Write_to_bry(Bry_bfr_.New(), tmpl, itm);
	}

	protected abstract Io_url Get_addon_dir(Xoa_app app);
	protected abstract Io_url Get_mustache_fil(Io_url addon_dir);
	protected abstract Mustache_doc_itm	Bld_mustache_root(Xoa_app app);
	protected abstract void Bld_tags(Xoa_app app, Io_url addon_dir, Xopage_html_data data);
	@gplx.Virtual protected void Handle_invalid(Xoa_app app, Xoa_page page, Xow_special_page special) {
		new Xopage_html_data(special.Special__meta().Display_ttl(), Bry_.new_a7("Not available")).Apply(page);
	}
}
