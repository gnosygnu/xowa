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
package gplx.xowa.pages.skins; import gplx.*; import gplx.xowa.*; import gplx.xowa.pages.*;
public class Xopg_xtn_skin_itm_stub implements Xopg_xtn_skin_itm {
	private final byte[] val;
	public Xopg_xtn_skin_itm_stub(byte[] val) {this.val = val;}
	public byte Tid()		{return Xopg_xtn_skin_itm_tid.Tid_sidebar;}
	public byte[] Key()		{return Bry_.Empty;}
	public void Write(Bry_bfr bfr, Xoae_page page) {bfr.Add(val);}
}
