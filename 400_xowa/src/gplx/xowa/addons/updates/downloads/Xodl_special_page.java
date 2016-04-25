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
package gplx.xowa.addons.updates.downloads; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.updates.*;
import gplx.xowa.specials.*; import gplx.core.net.*; import gplx.xowa.wikis.pages.*;
import gplx.xowa.addons.updates.downloads.itms.*; import gplx.xowa.addons.updates.downloads.core.*;
import gplx.xowa.addons.apps.file_browsers.*;
public class Xodl_special_page implements Xows_page {
	public static Gfo_download_wkr Download_wkr = Gfo_download_wkr_.Noop;
	public void Special__gen(Xow_wiki wiki, Xoa_page page, Xoa_url url, Xoa_ttl ttl) {
		Io_url addon_dir = wiki.App().Fsys_mgr().Bin_addon_dir().GenSubDir_nest("import", "wiki_download");
		Xoa_url_arg_mgr arg_mgr = new Xoa_url_arg_mgr(null).Init(url.Qargs_ary());
		byte[] ids_bry = arg_mgr.Read_bry_or_null("ids");
		if (ids_bry == null) {
			Xopage_html_data html_data = Write_html(wiki.App(), addon_dir);
			html_data.Apply(page);
		}
		else {
			Xodl_itm_regy regy = Load_regy(addon_dir);
			int[] ids_ary = Int_.Ary_parse(String_.new_u8(ids_bry), ",");
			Xodl_itm_pack[] packs = regy.Packs__select(ids_ary);
			if (packs.length > 0) {
				Xodl_download_mgr download_mgr = new Xodl_download_mgr();
				download_mgr.Download(Download_wkr, packs);
			}
		}
	}
	private static Xodl_itm_regy Load_regy(Io_url addon_dir) {
		return Xodl_itm_regy.Load_by_json(addon_dir.GenSubFil_nest("data", "wiki_download.json"));
	}
	private static Xopage_html_data Write_html(Xoa_app app, Io_url addon_dir) {
		// write body
		Xodl_itm_regy owner_itm = Load_regy(addon_dir);
		byte[] template_src = Io_mgr.Instance.LoadFilBry(addon_dir.GenSubFil_nest("bin", "wiki_download.mustache.html"));
		Bry_bfr tmp_bfr = Bry_bfr.new_();
		byte[] body = gplx.langs.mustaches.Mustache_wtr_.Write_to_bry(tmp_bfr, template_src, owner_itm);

		// write head
		Xopage_html_data rv = new Xopage_html_data(Display_ttl, body);
		rv.Head_tags().Add(Xopg_tag_itm.New_css_file(addon_dir.GenSubFil_nest("bin", "wiki_download.css")));
		return rv;
	}

	public static final String SPECIAL_KEY = "XowaWikiDownload";
	public static final    byte[] Display_ttl = Bry_.new_a7("Download wikis");
	public Xows_special_meta Special__meta() {return new Xows_special_meta(Xows_special_meta_.Src__xowa, SPECIAL_KEY);}
	public static final    Xows_page Prototype = new Xodl_special_page();
	public Xows_page Special__clone() {return this;}
}
