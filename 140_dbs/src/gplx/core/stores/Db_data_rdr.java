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
package gplx.core.stores;
import gplx.types.basics.lists.List_adp_;
import gplx.types.errs.ErrUtl;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateUtl;
import gplx.types.commons.GfoDecimal;
import gplx.types.commons.GfoDecimalUtl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
public class Db_data_rdr extends DataRdr_base implements DataRdr {
	@Override public String NameOfNode() {return commandText;} public String To_str() {return commandText;} private String commandText;
		private ResultSet rdr;
	private int fieldCount;
	@Override public int FieldCount() {return fieldCount;}
	@Override public String KeyAt(int i) {
		String rv = null; 
		try {rv = rdr.getMetaData().getColumnLabel(i + List_adp_.Base1);}
		catch (SQLException e) {throw ErrUtl.NewArgs(e, "get columnName failed", "i", i, "commandText", commandText);}
		return rv;
	}
	@Override public Object ReadAt(int i) {
		Object rv;
		try {rv = rdr.getObject(i + List_adp_.Base1);} catch(Exception exc) {throw ErrUtl.NewArgs("could not read val from dataReader; idx not found or rdr not open", "idx", i, "sql", commandText);}
		return rv;
	}
	@Override public Object Read(String key) {
		Object rv;
		try {rv = rdr.getObject(key);} catch(Exception exc) {throw ErrUtl.NewArgs("could not read val from dataReader; key not found or rdr may not be open", "key", key, "sql", commandText);}
		return rv;
	}
	@Override public GfoDate ReadDate(String key) {
		Object o = this.Read(key);
		Timestamp ts = (Timestamp)o;
		GregorianCalendar g = new GregorianCalendar();		
		g.setTime(ts);
		return GfoDateUtl.NewByCalendar(g);
	}
	@Override public GfoDecimal ReadDecimal(String key) {return GfoDecimalUtl.NewDb(this.Read(key));}
	@Override public gplx.core.ios.streams.Io_stream_rdr ReadRdr(String key) {
		try {
			java.io.InputStream input_stream = rdr.getBinaryStream(key);			
			return gplx.core.ios.streams.Io_stream_rdr_.New__raw(input_stream);
		}
		catch (SQLException e) {return gplx.core.ios.streams.Io_stream_rdr_.Noop;}
	}
	
	public boolean MoveNextPeer() {
		try {return rdr.next();}
		catch (Exception e) {throw ErrUtl.NewArgs(e, "could not move next; check column casting error in SQL", "sql", commandText);}
	}
	@Override public DataRdr Subs()							{throw ErrUtl.NewUnimplemented();}
	public DataRdr Subs_byName(String fld)					{throw ErrUtl.NewUnimplemented();}
	@Override public DataRdr Subs_byName_moveFirst(String fld)		{throw ErrUtl.NewUnimplemented();}
	public void Rls() {
		try {rdr.close();}
		catch (SQLException e) {throw ErrUtl.NewArgs(e, "reader dispose failed", "commandText", commandText);}
		this.EnvVars().Clear();
	}
	public Db_data_rdr ctor_db_data_rdr(ResultSet rdr, String commandText) {
		this.rdr = rdr; this.commandText = commandText; this.Parse_set(false);
		try {fieldCount = this.rdr.getMetaData().getColumnCount();}
		catch (SQLException e) {ErrUtl.NewArgs(e, "get columnCount failed", "commandText", commandText);}
		return this;
	}
	@Override public SrlMgr SrlMgr_new(Object o) {return new Db_data_rdr();}
}
