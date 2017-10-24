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
public class IptMouseBtn implements IptArg {
	public IptMouseBtn(int val, String key) {this.val = val; this.key = key;}
	public String Key()					{return key;} private String key;
	public int Val()					{return val;} int val;
	public boolean Eq(IptArg comp)			{return String_.Eq(key, comp.Key());}
}
