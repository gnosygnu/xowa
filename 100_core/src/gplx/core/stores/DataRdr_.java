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
package gplx.core.stores;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.lists.Hash_adp_;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.CharUtl;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.utls.FloatUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateUtl;
import gplx.types.commons.GfoDecimal;
import gplx.types.commons.GfoDecimalUtl;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
import gplx.types.commons.KeyVal;
import gplx.types.commons.String_bldr;
import gplx.types.errs.ErrUtl;
public class DataRdr_ {
	public static final DataRdr Null = new DataRdr_null();
	public static DataRdr as_(Object obj) {return obj instanceof DataRdr ? (DataRdr)obj : null;}
	public static DataRdr cast(Object obj) {try {return (DataRdr)obj;} catch(Exception exc) {throw ErrUtl.NewCast(exc, DataRdr.class, obj);}}

	public static Object Read_1st_row_and_1st_fld(DataRdr rdr) {
		try {return rdr.MoveNextPeer() ? rdr.ReadAt(0) : null;}
		finally {rdr.Rls();}
	}

}
class DataRdr_null implements DataRdr {
	public String NameOfNode() {return To_str();} public String To_str() {return "<< NULL READER >>";}
	public boolean Type_rdr() {return true;}
	public Hash_adp EnvVars() {return Hash_adp_.Noop;}
	public Io_url Uri() {return Io_url_.Empty;} public void Uri_set(Io_url s) {}
	public boolean Parse() {return parse;} public void Parse_set(boolean v) {parse = v;} private boolean parse;
	public int FieldCount() {return 0;}
	public String KeyAt(int i) {return To_str();}
	public Object ReadAt(int i) {return null;}
	public KeyVal KeyValAt(int i) {return KeyVal.NewStr(this.KeyAt(i), this.ReadAt(i));}
	public Object Read(String name) {return null;}
	public String ReadStr(String key) {return StringUtl.Empty;}            public String ReadStrOr(String key, String or) {return or;}
	public byte[] ReadBryByStr(String key) {return BryUtl.Empty;}            public byte[] ReadBryByStrOr(String key, byte[] or) {return or;}
	public byte[] ReadBry(String key) {return BryUtl.Empty;}                public byte[] ReadBryOr(String key, byte[] or) {return or;}
	public char ReadChar(String key) {return CharUtl.Null;}                public char ReadCharOr(String key, char or) {return or;}
	public int ReadInt(String key) {return IntUtl.MinValue;}                public int ReadIntOr(String key, int or) {return or;}
	public boolean ReadBool(String key) {return false;}                    public boolean ReadBoolOr(String key, boolean or) {return or;}            
	public long ReadLong(String key) {return LongUtl.MinValue;}            public long ReadLongOr(String key, long or) {return or;}
	public double ReadDouble(String key) {return DoubleUtl.NaN;}            public double ReadDoubleOr(String key, double or) {return or;}
	public float ReadFloat(String key) {return FloatUtl.NaN;}                public float ReadFloatOr(String key, float or) {return or;}
	public byte ReadByte(String key) {return ByteUtl.MinValue;}            public byte ReadByteOr(String key, byte or) {return or;}
	public GfoDecimal ReadDecimal(String key) {return GfoDecimalUtl.Zero;}public GfoDecimal ReadDecimalOr(String key, GfoDecimal or) {return or;}
	public GfoDate ReadDate(String key) {return GfoDateUtl.MinValue;}        public GfoDate ReadDateOr(String key, GfoDate or) {return or;}
	public gplx.core.ios.streams.Io_stream_rdr ReadRdr(String key) {return gplx.core.ios.streams.Io_stream_rdr_.Noop;}
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
	public GfoDate SrlDateOr(String key, GfoDate or) {return or;}
	public GfoDecimal SrlDecimalOr(String key, GfoDecimal or) {return or;}
	public double SrlDoubleOr(String key, double or) {return or;}
	public Object SrlObjOr(String key, Object or) {return or;}
	public void SrlList(String key, List_adp list, SrlObj proto, String itmKey) {}
	public void TypeKey_(String v) {}
	public void XtoStr_gfml(String_bldr sb) {sb.AddStrWithCrlf("NULL:;");}
	public SrlMgr SrlMgr_new(Object o) {return this;}
	public void Rls() {}
}
