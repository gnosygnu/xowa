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
package gplx.core.stores; import gplx.*; import gplx.core.*;
import gplx.core.strings.*;
public interface DataRdr extends SrlMgr, Rls_able {
	String NameOfNode(); String To_str();
	Io_url Uri(); void Uri_set(Io_url s);
	Hash_adp EnvVars();
	boolean Parse(); void Parse_set(boolean v);

	int FieldCount();
	String KeyAt(int i);
	Object ReadAt(int i);
	Keyval KeyValAt(int i);

	Object Read(String key);
	String ReadStr(String key);				String ReadStrOr(String key, String or);
	byte[] ReadBryByStr(String key);		byte[] ReadBryByStrOr(String key, byte[] or);
	byte[] ReadBry(String key);				byte[] ReadBryOr(String key, byte[] or);
	char ReadChar(String key);				char ReadCharOr(String key, char or);
	int ReadInt(String key);				int ReadIntOr(String key, int or);
	boolean ReadBool(String key);				boolean ReadBoolOr(String key, boolean or);
	long ReadLong(String key);				long ReadLongOr(String key, long or);
	double ReadDouble(String key);			double ReadDoubleOr(String key, double or);
	float ReadFloat(String key);			float ReadFloatOr(String key, float or);
	byte ReadByte(String key);				byte ReadByteOr(String key, byte or);
	Decimal_adp ReadDecimal(String key);	Decimal_adp ReadDecimalOr(String key, Decimal_adp or);
	DateAdp ReadDate(String key);			DateAdp ReadDateOr(String key, DateAdp or);
	gplx.core.ios.streams.Io_stream_rdr ReadRdr(String key);

	boolean MoveNextPeer();
	DataRdr Subs();
	DataRdr Subs_byName(String name);
	DataRdr Subs_byName_moveFirst(String name);
	void XtoStr_gfml(String_bldr sb);
}
