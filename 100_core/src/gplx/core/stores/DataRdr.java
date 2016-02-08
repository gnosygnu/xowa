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
	KeyVal KeyValAt(int i);

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
	gplx.core.ios.Io_stream_rdr ReadRdr(String key);

	boolean MoveNextPeer();
	DataRdr Subs();
	DataRdr Subs_byName(String name);
	DataRdr Subs_byName_moveFirst(String name);
	void XtoStr_gfml(String_bldr sb);
}
