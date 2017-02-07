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
package gplx.gfui.ipts; import gplx.*; import gplx.gfui.*;
import gplx.core.strings.*;
public interface IptArg {
	String Key();
	boolean Eq(IptArg comp);	// NOTE: this relies on unique key across all IptArgs; EX: .Key() cannot be just "left"; would have issues with key.left and mouse.left
}
class IptKeyChain implements IptArg {
	public String Key()						{return key;} private String key;
	public boolean Eq(IptArg comp)				{return String_.Eq(key, comp.Key());}
	public IptArg[] Chained()				{return chained;} IptArg[] chained;
	@gplx.Internal protected IptKeyChain(IptArg[] ary) {
		chained = ary;
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < ary.length; i++) {
			IptArg itm = ary[i];
			sb.Add_spr_unless_first(itm.Key(), ",", i);
		}
		key = sb.To_str();
	}
	public static IptKeyChain parse(String raw) {
		String[] itms = String_.Split(raw, ",");
		IptArg[] rv = new IptArg[itms.length];
		for (int i = 0; i < rv.length; i++)
			rv[i] = IptArg_.parse(String_.Trim(itms[i]));
		return new IptKeyChain(rv);
	}
}
