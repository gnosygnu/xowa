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
package gplx.dbs.engines.tdbs; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import gplx.core.stores.*;
import gplx.core.stores.xmls.*; /*XmlDataRdr*/
import gplx.langs.dsvs.*; /*DsvDataWtr*/
import gplx.core.lists.*; /*GfoNdeRdr*/
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