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
package gplx.stores; import gplx.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import gplx.dbs.*;
public class Db_data_rdr extends DataRdr_base implements DataRdr {
	@Override public String NameOfNode() {return commandText;} public String XtoStr() {return commandText;} private String commandText;
		private ResultSet rdr;
	private int fieldCount;
	@Override public int FieldCount() {return fieldCount;}
	@Override public String KeyAt(int i) {
		String rv = null; 
		try {rv = rdr.getMetaData().getColumnLabel(i + List_adp_.Base1);}
		catch (SQLException e) {throw Exc_.new_exc(e, "db", "get columnName failed", "i", i, "commandText", commandText);}
		return rv;
	}
	@Override public Object ReadAt(int i) {
		Object rv;
		try {rv = rdr.getObject(i + List_adp_.Base1);} catch(Exception exc) {throw Exc_.new_("could not read val from dataReader; idx not found or rdr not open", "idx", i, "sql", commandText);}
		return rv;
	}
	@Override public Object Read(String key) {
		Object rv;
		try {rv = rdr.getObject(key);} catch(Exception exc) {throw Exc_.new_("could not read val from dataReader; key not found or rdr may not be open", "key", key, "sql", commandText);}
		return rv;
	}
	@Override public DateAdp ReadDate(String key) {
		Object o = this.Read(key);
		Timestamp ts = (Timestamp)o;
		GregorianCalendar g = new GregorianCalendar();		
		g.setTime(ts);
		return DateAdp_.dateTime_(g);
	}
	@Override public DecimalAdp ReadDecimal(String key) {return DecimalAdp_.db_(this.Read(key));}
	@Override public gplx.ios.Io_stream_rdr ReadRdr(String key) {
		try {
			java.io.InputStream input_stream = rdr.getBinaryStream(key);			
			return gplx.ios.Io_stream_rdr_.file_(input_stream);
		}
		catch (SQLException e) {return gplx.ios.Io_stream_rdr_.Noop;}
	}
	
	public boolean MoveNextPeer() {
		try {return rdr.next();}
		catch (Exception e) {throw Exc_.new_exc(e, "db", "could not move next; check column casting error in SQL", "sql", commandText);}
	}
	@Override public DataRdr Subs()							{throw Exc_.new_unimplemented();}
	public DataRdr Subs_byName(String fld)					{throw Exc_.new_unimplemented();}
	@Override public DataRdr Subs_byName_moveFirst(String fld)		{throw Exc_.new_unimplemented();}		
	public void Rls() {
		try {rdr.close();}
		catch (SQLException e) {throw Exc_.new_exc(e, "db", "reader dispose failed", "commandText", commandText);}
		this.EnvVars().Clear();
	}
	@gplx.Internal protected Db_data_rdr ctor_db_data_rdr(ResultSet rdr, String commandText) {
		this.rdr = rdr; this.commandText = commandText; this.Parse_set(false);
		try {fieldCount = this.rdr.getMetaData().getColumnCount();}
		catch (SQLException e) {Exc_.new_exc(e, "xo", "get columnCount failed", "commandText", commandText);}
		return this;
	}
	@Override public SrlMgr SrlMgr_new(Object o) {return new Db_data_rdr();}
}
