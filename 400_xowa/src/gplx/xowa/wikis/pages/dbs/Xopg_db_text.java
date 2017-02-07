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
public class Xopg_db_text {
	public byte[]		Text_bry()	{return text_bry;}	private byte[] text_bry = Bry_.Empty;	// NOTE: if null, will cause NullPointer exception on Special pages like Special:XowaDownloadCentral; DATE:2016-07-05
	public void			Text_bry_(byte[] v) {this.text_bry = v;}
	public void Clear() {
		// text_bry = Bry_.Empty;	// TOMBSTONE: do not set to Bry_.Empty else causes mass parse to noop; also causes Options/Files.Clear to noop; DATE:2016-07-15
	}
}
