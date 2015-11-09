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
package gplx.xowa.htmls.core.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.xowa.files.*; import gplx.xowa.apps.fsys.*; import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.ttls.*;
public class Xoh_decode_ctx {
	private byte[] dir__file;
	public Xof_url_bldr Url_bldr() {return url_bldr;} private Xof_url_bldr url_bldr = new Xof_url_bldr();
	public byte[] Dir__root() {return dir__root;} private byte[] dir__root;
	public byte[] Dir__file__comm() {return dir__file__comm;} private byte[] dir__file__comm;
	public byte[] Dir__file__wiki() {return dir__file__wiki;} private byte[] dir__file__wiki;
	public Xow_ttl_parser Ttl_parser() {return ttl_parser;} private Xow_ttl_parser ttl_parser;
	public void Init_by_app(Xoa_app app) {
		Xoa_fsys_mgr fsys_mgr = app.Fsys_mgr();
		this.dir__root = fsys_mgr.Root_dir().To_http_file_bry();
		this.dir__file = fsys_mgr.File_dir().To_http_file_bry();
		this.dir__file__comm = Bry_.Add(dir__file, Xow_domain_itm_.Bry__commons, Byte_ascii.Slash_bry);
	}
	public void Init_by_page(Xow_wiki wiki) {
		if (dir__root == null) Init_by_app(wiki.App());	// LAZY INIT
		this.ttl_parser = wiki;
		this.dir__file__wiki = Bry_.Add(dir__file, wiki.Domain_bry(), Byte_ascii.Slash_bry);
	}
}
