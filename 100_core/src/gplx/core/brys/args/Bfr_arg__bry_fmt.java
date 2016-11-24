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
package gplx.core.brys.args; import gplx.*; import gplx.core.*; import gplx.core.brys.*;
import gplx.core.brys.*;
public class Bfr_arg__bry_fmt implements Bfr_arg {
	private final    Bry_fmt fmt;
	private Object[] arg_ary;
	public Bfr_arg__bry_fmt(Bry_fmt fmt, Object... arg_ary) {
		this.fmt = fmt;
		Args_(arg_ary);
	}
	public void Bfr_arg__clear() {arg_ary = null;}
	public boolean Bfr_arg__missing() {return arg_ary == null;}

	public Bfr_arg__bry_fmt Args_(Object... arg_ary) {
		this.arg_ary = arg_ary;
		return this;
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		if (Bfr_arg__missing()) return;
		fmt.Bld_many(bfr, arg_ary);
	}
}
