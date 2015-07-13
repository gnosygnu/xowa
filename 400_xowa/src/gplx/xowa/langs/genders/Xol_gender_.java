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
package gplx.xowa.langs.genders; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.core.primitives.*; import gplx.core.btries.*;
public class Xol_gender_ {
	public static Xol_gender new_by_lang_id(int lang_id) {return Xol_gender__basic.I;}
	public static final int Tid_male = 0, Tid_female = 1, Tid_unknown = 2;
}
class Xol_gender__basic implements Xol_gender {
	public byte[] Gender_eval(int gender, byte[] when_m, byte[] when_f, byte[] when_u) {
		switch (gender) {
			case Xol_gender_.Tid_male:		return when_m;
			case Xol_gender_.Tid_female:	return when_f;
			case Xol_gender_.Tid_unknown:	return when_u;
			default:						throw Exc_.new_unimplemented();
		}
	}
	public static final Xol_gender__basic I = new Xol_gender__basic(); Xol_gender__basic() {}
}
