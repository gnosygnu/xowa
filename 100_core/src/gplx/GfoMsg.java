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
package gplx;
import gplx.core.interfaces.*;
public interface GfoMsg {
	String	Key();
	GfoMsg	CloneNew();
	String	To_str();
	GfoMsg	Clear();
	GfoMsg  Parse_(boolean v);

	int		Args_count();
	Keyval	Args_getAt(int i);
	GfoMsg	Args_ovr(String k, Object v);
	void	Args_reset();
	GfoMsg	Add(String k, Object v);
	int		Subs_count();
	GfoMsg	Subs_getAt(int i);
	GfoMsg	Subs_add(GfoMsg m);
	GfoMsg	Subs_(GfoMsg... ary);

	boolean	ReadBool(String k);
	boolean	ReadBoolOr(String k, boolean or);
	boolean	ReadBoolOrFalse(String k);
	boolean	ReadBoolOrTrue(String k);
	int		ReadInt(String k);
	int		ReadIntOr(String k, int or);
	long	ReadLong(String k);
	long	ReadLongOr(String k, long or);
	float	ReadFloat(String k);
	float	ReadFloatOr(String k, float or);
	double	ReadDouble(String k);
	double	ReadDoubleOr(String k, double or);
	DateAdp	ReadDate(String k);
	DateAdp	ReadDateOr(String k, DateAdp or);
	Decimal_adp ReadDecimal(String k);
	Decimal_adp ReadDecimalOr(String k, Decimal_adp or);
	String	ReadStr(String k);
	String	ReadStrOr(String k, String or);
	Io_url	ReadIoUrl(String k);
	Io_url	ReadIoUrlOr(String k, Io_url url);
	boolean	ReadYn(String k);
	boolean	ReadYn_toggle(String k, boolean cur);
	boolean	ReadYnOrY(String k);
	byte	ReadByte(String k);
	byte[]	ReadBry(String k);
	byte[]	ReadBryOr(String k, byte[] or);
	Object	ReadObj(String k);
	Object	ReadObj(String k, ParseAble parseAble);
	Object	ReadObjOr(String k, ParseAble parseAble, Object or);
	String[]ReadStrAry(String k, String spr);
	String[]ReadStrAryIgnore(String k, String spr, String ignore);
	byte[][]ReadBryAry(String k, byte spr);
	Object  ReadValAt(int i);
	Object	CastObj(String k);
	Object	CastObjOr(String k, Object or);
}
