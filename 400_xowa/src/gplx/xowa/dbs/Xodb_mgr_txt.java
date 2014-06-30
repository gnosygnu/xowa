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
package gplx.xowa.dbs; import gplx.*; import gplx.xowa.*;
import gplx.dbs.*; import gplx.xowa.ctgs.*;
public class Xodb_mgr_txt implements Xodb_mgr {
	public Xodb_mgr_txt(Xow_wiki wiki, Xow_data_mgr data_mgr) {
		this.wiki = wiki;
		load_mgr = new Xodb_load_mgr_txt(wiki);
		save_mgr = new Xodb_save_mgr_txt(wiki, load_mgr);
	}	private Xow_wiki wiki;
	public byte Tid() {return Tid_txt;} public static final byte Tid_txt = 0;
	public String Tid_name() {return "xdat";}
	public byte Data_storage_format() {return data_storage_format;} public void Data_storage_format_(byte v) {data_storage_format = v;} private byte data_storage_format = gplx.ios.Io_stream_.Tid_file;
	public Xodb_load_mgr Load_mgr() {return load_mgr;} private Xodb_load_mgr_txt load_mgr;
	public Xodb_save_mgr Save_mgr() {return save_mgr;} private Xodb_save_mgr_txt save_mgr;
	public DateAdp Dump_date_query() {
		Io_url url = wiki.Fsys_mgr().Url_ns_fil(Xow_dir_info_.Tid_page, Xow_ns_.Id_main, 0);
		return Io_mgr._.QueryFil(url).ModifiedTime();
	}
	public byte Category_version() {
		if (category_version == Xoa_ctg_mgr.Version_null) {
			if (Io_mgr._.ExistsDir(wiki.Fsys_mgr().Url_site_dir(Xow_dir_info_.Tid_category2_link)))
				category_version = Xoa_ctg_mgr.Version_2;
			else
				category_version = Xoa_ctg_mgr.Version_1;
		}
		return category_version;
	}	byte category_version = Xoa_ctg_mgr.Version_null;
	public byte Search_version() {return gplx.xowa.specials.search.Xosrh_core.Version_2;}
	public void Search_version_refresh() {throw Err_.not_implemented_();}
	public void Rls() {}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_data_storage_format))				return Xoi_dump_mgr.Wtr_tid_to_str(data_storage_format);
		else if	(ctx.Match(k, Invk_data_storage_format_))				data_storage_format = Xoi_dump_mgr.Wtr_tid_parse(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_category_version))					return this.Category_version();
		else if	(ctx.Match(k, Invk_category_version_))					category_version = m.ReadByte("v");
		else if	(ctx.Match(k, Invk_search_version))						return this.Search_version();
		else if	(ctx.Match(k, Invk_tid_name))							return Tid_name();
		return this;
	}
	public static final String
	  Invk_data_storage_format = "data_storage_format", Invk_data_storage_format_ = "data_storage_format_"
	, Invk_category_version = "category_version", Invk_category_version_ = "category_version_"
	, Invk_search_version = "search_version"
	, Invk_tid_name = "tid_name"
	;
}
