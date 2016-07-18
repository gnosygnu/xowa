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
package gplx.xowa.wikis.pages.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*;
public class Xopg_db_html {
	public Xopg_db_html() {this.Clear();}
	public byte[]		Html_bry()			{return html_bry;}	private byte[] html_bry;
	public void			Html_bry_(byte[] v) {this.html_bry = v;}
	public int			Zip_tid()			{return zip_tid;}	private int zip_tid;
	public int			Hzip_tid()			{return hzip_tid;}	private int hzip_tid;
	public void			Zip_tids_(int zip_tid, int hzip_tid) {this.zip_tid = zip_tid; this.hzip_tid = hzip_tid;}
	public void Clear() {
		html_bry = Bry_.Empty;	// NOTE: if null, will cause NullPointer exception on Special pages like Special:XowaDownloadCentral; DATE:2016-07-05
		zip_tid = 0;
		hzip_tid = 0;
	}
}
