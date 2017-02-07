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
package gplx.core.intls.ucas; import gplx.*; import gplx.core.*; import gplx.core.intls.*;
public class Uca_collator_ {
	public static Uca_collator New(String locale, boolean numeric_ordering) {
		Uca_collator rv = new Uca_collator__icu__4_8();
		rv.Init(locale, numeric_ordering);
		return rv;
	}
}
