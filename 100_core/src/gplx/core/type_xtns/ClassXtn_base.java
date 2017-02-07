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
package gplx.core.type_xtns; import gplx.*; import gplx.core.*;
public abstract class ClassXtn_base {
	public abstract Class<?> UnderClass();
	public abstract Object ParseOrNull(String raw);
	@gplx.Virtual public Object XtoDb(Object obj)						{return obj;}
	@gplx.Virtual public String XtoUi(Object obj, String fmt)			{return Object_.Xto_str_strict_or_null_mark(obj);}
	@gplx.Virtual public boolean MatchesClass(Object obj) {if (obj == null) throw Err_.new_null();
		return Type_adp_.Eq_typeSafe(obj, UnderClass());
	}
	@gplx.Virtual public int compareTo(Object lhs, Object rhs) {return CompareAble_.Compare_obj(lhs, rhs);}
}
