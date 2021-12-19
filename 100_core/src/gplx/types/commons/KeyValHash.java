/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.types.commons;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
public class KeyValHash {
	private final Ordered_hash hash = Ordered_hash_.New();
	public int        Len()               {return hash.Len();}
	public KeyValHash Clear()             {hash.Clear(); return this;}
	public boolean    Has(String key)     {return hash.Has(key);}
	public KeyVal     GetAt(int i)        {return (KeyVal)hash.GetAt(i);}
	public Object     GetByValOr(String key, Object or) {KeyVal rv = GetByAsKeyValOrNull(key); return rv == null ? or : rv.Val();}
	public Object     GetByValOrNull(String key) {return GetByValOr(key, null);}
	public Object     GetByValOrFail(String key) {return ((KeyVal)hash.GetByOrFail(key)).Val();}
	public String     GetByValAsStrOrFail(String key) {return (String)GetByValOrFail(key);}
	public KeyVal     GetByAsKeyValOrNull(String key) {return ((KeyVal)hash.GetByOrNull(key));}
	public KeyValHash Add(KeyVal kv)              {hash.Add(kv.KeyToStr(), kv); return this;}
	public KeyValHash Add(String key, Object val) {hash.Add(key, KeyVal.NewStr(key, val)); return this;}
	public KeyValHash AddIfDupeUseNth(String key, Object val) {hash.AddIfDupeUseNth(key, KeyVal.NewStr(key, val)); return this;}
	public void       Del(String key) {hash.Del(key);}
	public KeyVal[]   ToAry() {
		int len = this.Len();
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; ++i)
			rv[i] = this.GetAt(i);
		return rv;
	}
}
