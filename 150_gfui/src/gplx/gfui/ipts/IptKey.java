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
import gplx.core.bits.*;
public class IptKey implements IptArg {
	@gplx.Internal protected IptKey(int val, String key) {this.val = val; this.key = key;}
	public String Key()					{return key;} private final    String key;
	public int Val()					{return val;} private final    int val;
	public boolean Eq(IptArg comp)			{return String_.Eq(key, comp.Key());}
	public String XtoUiStr()			{return IptKeyStrMgr.Instance.To_str(this);}
	public IptKey Add(IptKey comp)		{return IptKey_.add_(this, comp);}
	public boolean Mod_shift()				{return Bitmask_.Has_int(val, IptKey_.Shift.Val());}
	public boolean Mod_ctrl()				{return Bitmask_.Has_int(val, IptKey_.Ctrl.Val());}
	public boolean Mod_alt()				{return Bitmask_.Has_int(val, IptKey_.Alt.Val());}
}
