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
package gplx.xowa.langs.kwds; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
public class Xol_kwd_itm {// NOTE: keeping as separate class b/c may include fmt props later; EX: thumbnail=$1
	public Xol_kwd_itm(byte[] val) {this.val = val;}
	public byte[] Val() {return val;} private byte[] val;
	public void Val_(byte[] v) {val = v;}	// should only be called by lang
	public static final Xol_kwd_itm[] Ary_empty = new Xol_kwd_itm[0];
}
