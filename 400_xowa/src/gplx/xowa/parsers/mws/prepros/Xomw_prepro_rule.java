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
package gplx.xowa.parsers.mws.prepros; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.mws.*;
class Xomw_prepro_rule {
	public Xomw_prepro_rule(byte[] bgn, byte[] end, int min, int max, int[] names) {
		this.bgn = bgn;
		this.end = end;
		this.min = min;
		this.max = max;
		this.names = names;
	}
	public final    byte[] bgn;
	public final    byte[] end;
	public final    int min;
	public final    int max;
	public final    int[] names;
	public boolean Names_exist(int idx) {
		return idx < names.length && names[idx] != Name__invalid;
	}
	private static final    byte[] Name__tmpl_bry = Bry_.new_a7("template"), Name__targ_bry = Bry_.new_a7("tplarg");
	public static final int Name__invalid = -1, Name__null = 0, Name__tmpl = 1, Name__targ = 2;
	public static byte[] Name(int type) {
		switch (type) {
			case Name__tmpl:    return Name__tmpl_bry;
			case Name__targ:    return Name__targ_bry;
			default:
			case Name__invalid: return null;
			case Name__null:    return null;
		}
	}
}
class Xomw_prepro_elem {
	private static final    byte[] Bry__tag_end = Bry_.new_a7("</");
	public Xomw_prepro_elem(int type, byte[] name) {
		this.type = type;
		this.name = name;
		this.tag_end_lhs = Bry_.Add(Bry__tag_end, name);
	}
	public final    int type;
	public final    byte[] name;
	public final    byte[] tag_end_lhs;
	public static final int Type__comment = 0;
}
class Xomw_prepro_curchar_itm {
	public Xomw_prepro_curchar_itm(byte[] bry) {
		this.bry = bry;
		this.type = bry[0];
	}
	public byte[] bry;
	public byte type;
}
