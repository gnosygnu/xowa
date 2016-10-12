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
package gplx.core.net.qargs; import gplx.*; import gplx.core.*; import gplx.core.net.*;
public class Gfo_qarg_itm {
	public Gfo_qarg_itm(byte[] key_bry, byte[] val_bry) {
		this.key_bry = key_bry;
		this.val_bry = val_bry;
	}
	public byte[]			Key_bry() {return key_bry;} private final    byte[] key_bry;
	public byte[]			Val_bry() {return val_bry;} private byte[] val_bry;
	public void				Val_bry_(byte[] v) {val_bry = v;}

	public static final    Gfo_qarg_itm[] Ary_empty = new Gfo_qarg_itm[0];
}
