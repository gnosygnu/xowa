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
package gplx;
public class Bry_fmtr_arg_fmtr_objs implements Bry_fmtr_arg {
	public Bry_fmtr_arg_fmtr_objs Atrs_(Bry_fmtr fmtr, Object... objs) {this.fmtr = fmtr; this.objs = objs; return this;}
	public void XferAry(Bry_bfr trg, int idx) {
		fmtr.Bld_bfr_many(trg, objs);
	}
	public Bry_fmtr_arg_fmtr_objs(Bry_fmtr fmtr, Object[] objs) {this.fmtr = fmtr; this.objs = objs;} Bry_fmtr fmtr; Object[] objs;
}
