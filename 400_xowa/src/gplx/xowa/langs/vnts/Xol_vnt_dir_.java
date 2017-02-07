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
package gplx.xowa.langs.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
public class Xol_vnt_dir_ {
	public static final int Tid__none = 0, Tid__uni = 1, Tid__bi = 2;
	public static int Parse(byte[] v) {return hash.Get_as_int_or(v, Tid__none);}
	private static final byte[] Bry__none = Bry_.new_a7("disable"), Bry__uni = Bry_.new_a7("unidirectional"), Bry__bi = Bry_.new_a7("bidirectional");
	private static final Hash_adp_bry hash = Hash_adp_bry.cs()
	.Add_bry_int(Bry__none	, Tid__none)
	.Add_bry_int(Bry__uni	, Tid__uni)
	.Add_bry_int(Bry__bi	, Tid__bi);
}
