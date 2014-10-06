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
package gplx;
public interface GfoMsg {
	String	Key();
	GfoMsg	CloneNew();
	String	XtoStr();
	GfoMsg	Clear();
	GfoMsg  Parse_(boolean v);

	int		Args_count();
	KeyVal	Args_getAt(int i);
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
	DecimalAdp ReadDecimal(String k);
	DecimalAdp ReadDecimalOr(String k, DecimalAdp or);
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
