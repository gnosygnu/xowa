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
package gplx.langs.dsvs; import gplx.*; import gplx.langs.*;
import gplx.core.gfo_ndes.*;
public class DsvDataRdrOpts {
	public boolean HasHeader() {return hasHeader;} public DsvDataRdrOpts HasHeader_(boolean val) {hasHeader = val; return this;} private boolean hasHeader = false;
	public String NewLineSep() {return newLineSep;} public DsvDataRdrOpts NewLineSep_(String val) {newLineSep = val; return this;} private String newLineSep = String_.CrLf;
	public String FldSep() {return fldSep;} public DsvDataRdrOpts FldSep_(String val) {fldSep = val; return this;} private String fldSep = ",";
	public GfoFldList Flds() {return flds;} public DsvDataRdrOpts Flds_(GfoFldList val) {flds = val; return this;} GfoFldList flds = GfoFldList_.Null;
	public static DsvDataRdrOpts new_() {return new DsvDataRdrOpts();} DsvDataRdrOpts() {}
}
