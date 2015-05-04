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
package gplx.xowa.html; import gplx.*; import gplx.xowa.*;
public abstract class Xoh_page_wtr_mgr_base {
	public byte[] Css_common_bry() {return css_common_bry;} private byte[] css_common_bry;
	public byte[] Css_wiki_bry() {return css_wiki_bry;} private byte[] css_wiki_bry;
	public void Init_css_urls(Io_url css_common_url, Io_url css_wiki_url) {
		this.css_common_bry = css_common_url.To_http_file_bry();
		this.css_wiki_bry = css_wiki_url.To_http_file_bry();
	}		
}
