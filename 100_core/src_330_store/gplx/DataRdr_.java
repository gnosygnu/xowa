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
import gplx.core.strings.*;
public class DataRdr_ {
	public static final DataRdr Null = new DataRdr_null();
	public static DataRdr as_(Object obj) {return obj instanceof DataRdr ? (DataRdr)obj : null;}
	public static DataRdr cast_(Object obj) {try {return (DataRdr)obj;} catch(Exception exc) {throw Err_.type_mismatch_exc_(exc, DataRdr.class, obj);}}
}
class DataRdr_null implements DataRdr {
	public String NameOfNode() {return XtoStr();} public String XtoStr() {return "<< NULL READER >>";}
	public boolean Type_rdr() {return true;}
	public Hash_adp EnvVars() {return Hash_adp_.Noop;}
	public Io_url Uri() {return Io_url_.Empty;} public void Uri_set(Io_url s) {}
	public boolean Parse() {return parse;} public void Parse_set(boolean v) {parse = v;} private boolean parse;
	public int FieldCount() {return 0;}
	public String KeyAt(int i) {return XtoStr();}
	public Object ReadAt(int i) {return null;}
	public KeyVal KeyValAt(int i) {return KeyVal_.new_(this.KeyAt(i), this.ReadAt(i));}
	public Object Read(String name) {return null;}
	public String ReadStr(String key) {return String_.Empty;}			public String ReadStrOr(String key, String or) {return or;}
	public byte[] ReadBryByStr(String key) {return Bry_.Empty;}		public byte[] ReadBryByStrOr(String key, byte[] or) {return or;}
	public byte[] ReadBry(String key) {return Bry_.Empty;}			public byte[] ReadBryOr(String key, byte[] or) {return or;}
	public char ReadChar(String key) {return Char_.Null;}				public char ReadCharOr(String key, char or) {return or;}
	public int ReadInt(String key) {return Int_.MinValue;}				public int ReadIntOr(String key, int or) {return or;}
	public boolean ReadBool(String key) {return false;}					public boolean ReadBoolOr(String key, boolean or) {return or;}			
	public long ReadLong(String key) {return Long_.MinValue;}			public long ReadLongOr(String key, long or) {return or;}
	public double ReadDouble(String key) {return Double_.NaN;}			public double ReadDoubleOr(String key, double or) {return or;}
	public float ReadFloat(String key) {return Float_.NaN;}				public float ReadFloatOr(String key, float or) {return or;}
	public byte ReadByte(String key) {return Byte_.Min_value;}			public byte ReadByteOr(String key, byte or) {return or;}
	public DecimalAdp ReadDecimal(String key) {return DecimalAdp_.Zero;}public DecimalAdp ReadDecimalOr(String key, DecimalAdp or) {return or;}
	public DateAdp ReadDate(String key) {return DateAdp_.MinValue;}		public DateAdp ReadDateOr(String key, DateAdp or) {return or;}
	public gplx.ios.Io_stream_rdr ReadRdr(String key) {return gplx.ios.Io_stream_rdr_.Noop;}
	public boolean MoveNextPeer() {return false;}
	public DataRdr Subs() {return this;}
	public DataRdr Subs_byName(String name) {return this;}
	public DataRdr Subs_byName_moveFirst(String name) {return this;}
	public Object StoreRoot(SrlObj root, String key) {return null;}
	public boolean SrlBoolOr(String key, boolean v) {return v;}
	public byte SrlByteOr(String key, byte v) {return v;}
	public int SrlIntOr(String key, int or) {return or;}
	public long SrlLongOr(String key, long or) {return or;}
	public String SrlStrOr(String key, String or) {return or;}
	public DateAdp SrlDateOr(String key, DateAdp or) {return or;}
	public DecimalAdp SrlDecimalOr(String key, DecimalAdp or) {return or;}
	public double SrlDoubleOr(String key, double or) {return or;}
	public Object SrlObjOr(String key, Object or) {return or;}
	public void SrlList(String key, List_adp list, SrlObj proto, String itmKey) {}
	public void TypeKey_(String v) {}
	public void XtoStr_gfml(String_bldr sb) {sb.Add_str_w_crlf("NULL:;");}
	public SrlMgr SrlMgr_new(Object o) {return this;}
	public void Rls() {}
}
