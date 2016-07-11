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
package gplx.xowa.addons.bldrs.mass_parses.makes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
class Xomp_page_itm {
	public Xomp_page_itm(int id) {this.id = id;}
	public int Id() {return id;} private final    int id;
	public int Ns_id() {return ns_id;} private int ns_id;
	public byte[] Ttl_bry() {return ttl_bry;} private byte[] ttl_bry;
	public int Text_db_id() {return text_db_id;} private int text_db_id;
	public byte[] Text() {return text;} private byte[] text;

	public void Init_by_page(int ns_id, byte[] ttl_bry, int text_db_id) {
		this.ns_id = ns_id;
		this.ttl_bry = ttl_bry;
		this.text_db_id = text_db_id;
	}
	public void Init_by_text(byte[] text) {
		this.text = text;
	}

	public static final    Xomp_page_itm Null = new Xomp_page_itm(-1);
}
