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
import gplx.stores.*;
import gplx.stores.xmls.*; /*XmlDataRdr*/
import gplx.stores.dsvs.*; /*DsvDataWtr*/
import gplx.lists.*; /*GfoNdeRdr*/
class TdbStores {
	public static final String Dsv = "dsv";
	public static final String Xml = "xml";
	public static DataRdr rdr_(String text) {return DsvDataRdr_.dsv_(text);}
	public static DataWtr wtr_() {return DsvDataWtr_.new_();}
	@gplx.Internal protected static DsvStoreLayout FetchLayout(DataRdr rdr) {
		GfoNdeRdr ndeRdr = GfoNdeRdr_.as_(rdr); if (ndeRdr == null) return null;	// can happen for non-Dsv Rdrs (ex: Xml)
		return DsvStoreLayout.as_(ndeRdr.UnderNde().EnvVars().Get_by(DsvStoreLayout.Key_const));
	}
}