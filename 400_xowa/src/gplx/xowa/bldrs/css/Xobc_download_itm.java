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
package gplx.xowa.bldrs.css; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
class Xobc_download_itm {
	public Xobc_download_itm(int tid, String http_str, byte[] fsys_url) {this.tid = tid; this.http_str = http_str; this.fsys_url = fsys_url;}
	public int Tid() {return tid;} private final int tid;
	public String Http_str() {return http_str;} private final String http_str;
	public byte[] Fsys_url() {return fsys_url;} private final byte[] fsys_url;
	public static final int Tid_file = 1, Tid_html = 2, Tid_css = 3;
}
