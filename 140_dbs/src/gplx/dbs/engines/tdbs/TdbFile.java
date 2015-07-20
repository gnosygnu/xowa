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
package gplx.dbs.engines.tdbs; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
public class TdbFile {
	public int Id() {return id;} int id;
	public Io_url Path() {return url;} Io_url url;

	public static TdbFile new_(int id, Io_url url) {
		TdbFile rv = new TdbFile();
		rv.id = id; rv.url = url;
		return rv;
	}
	public static final int MainFileId = 1;
	public static TdbFile as_(Object obj) {return obj instanceof TdbFile ? (TdbFile)obj : null;}
	public static TdbFile cast_(Object obj) {try {return (TdbFile)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, TdbFile.class, obj);}}
}
