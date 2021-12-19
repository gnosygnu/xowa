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
package gplx.gfui.ipts;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.String_bldr;
import gplx.types.commons.String_bldr_;
public interface IptArg {
	String Key();
	boolean Eq(IptArg comp);	// NOTE: this relies on unique key across all IptArgs; EX: .Key() cannot be just "left"; would have issues with key.left and mouse.left
}
class IptKeyChain implements IptArg {
	public String Key()						{return key;} private String key;
	public boolean Eq(IptArg comp)				{return StringUtl.Eq(key, comp.Key());}
	public IptArg[] Chained()				{return chained;} IptArg[] chained;
	public IptKeyChain(IptArg[] ary) {
		chained = ary;
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < ary.length; i++) {
			IptArg itm = ary[i];
			sb.AddSprUnlessFirst(itm.Key(), ",", i);
		}
		key = sb.ToStr();
	}
	public static IptKeyChain parse(String raw) {
		String[] itms = StringUtl.Split(raw, ",");
		IptArg[] rv = new IptArg[itms.length];
		for (int i = 0; i < rv.length; i++)
			rv[i] = IptArg_.parse(StringUtl.Trim(itms[i]));
		return new IptKeyChain(rv);
	}
}
