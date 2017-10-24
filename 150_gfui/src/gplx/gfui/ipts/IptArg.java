/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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
