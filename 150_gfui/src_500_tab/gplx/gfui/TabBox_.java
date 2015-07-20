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
package gplx.gfui; import gplx.*;
public class TabBox_ {
	public static TabBox as_(Object obj) {return obj instanceof TabBox ? (TabBox)obj : null;}
	public static TabBox cast_(Object obj) {try {return (TabBox)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, TabBox.class, obj);}}
	public static TabBox new_() {
		TabBox rv = new TabBox();
		rv.ctor_GfuiBox_base(GfuiElem_.init_focusAble_false_());
		return rv;
	}
	public static int Cycle(boolean fwd, int val, int max) {
		if (fwd)
			return val == max - 1 ? 0 : val + 1;
		else
			return val == 0 ? max - 1 : val - 1;
	}
}
