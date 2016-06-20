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
package gplx.xowa.guis.cbks; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
public class Xog_cbk_trg {
	Xog_cbk_trg(byte tid, byte[] page_ttl) {this.tid = tid; this.page_ttl = page_ttl;}
	public byte Tid() {return tid;} private final    byte tid;
	public byte[] Page_ttl() {return page_ttl;} private final    byte[] page_ttl;	// same as ttl.Full_db(); EX: Special:XowaDownloadCentral

	public static final byte Tid__cbk_enabled = 0, Tid__specific_page = 1;
	public static final    Xog_cbk_trg Any = new Xog_cbk_trg(Tid__cbk_enabled, null);
	public static Xog_cbk_trg New(byte[] page_ttl) {return new Xog_cbk_trg(Tid__specific_page, page_ttl);}
}
