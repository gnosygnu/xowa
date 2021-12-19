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
import gplx.frameworks.objects.ToStrAble;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.TypeIds;
public class KeyVal implements ToStrAble { // DEPRECATED
	public KeyVal(int keyTid, Object key, Object val) {this.keyTid = keyTid; this.key = key; this.val = val;}
	public int KeyTid() {return keyTid;} private int keyTid;
	public Object KeyAsObj() {return key;} private Object key;
	public KeyVal KeySet(Object v) {this.key = v; return this;}
	public String KeyToStr() {return ObjectUtl.ToStrOrNull(key);}
	public Object Val() {return val;} private Object val;
	public String ValToStrOrEmpty() {return ObjectUtl.ToStrOrEmpty(val);}
	public String ValToStrOrNull() {return ObjectUtl.ToStrOrNull(val);}
	public byte[] ValToBry() {return BryUtl.NewU8(ObjectUtl.ToStrOrNull(val));}
	public KeyVal ValSet(Object v) {this.val = v; return this;}
	public String ToStr() {return KeyToStr() + "=" + ObjectUtl.ToStrOrNullMark(val);}
	@Override public String toString() {return ToStr();}

	public static KeyVal As(Object obj)                  {return obj instanceof KeyVal ? (KeyVal)obj : null;}
	public static KeyVal NewStr(String key)              {return new KeyVal(TypeIds.IdStr, key, key);}
	public static KeyVal NewStr(String key, Object val)  {return new KeyVal(TypeIds.IdStr, key, val);}
	public static KeyVal NewInt(int key, Object val)     {return new KeyVal(TypeIds.IdInt, key, val);}
	public static KeyVal NewObj(Object key, Object val)  {return new KeyVal(TypeIds.IdObj, key, val);}
}
