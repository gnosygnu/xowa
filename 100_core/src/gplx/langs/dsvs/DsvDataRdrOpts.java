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
package gplx.langs.dsvs; import gplx.*; import gplx.langs.*;
public class DsvDataRdrOpts {
	public boolean HasHeader() {return hasHeader;} public DsvDataRdrOpts HasHeader_(boolean val) {hasHeader = val; return this;} private boolean hasHeader = false;
	public String NewLineSep() {return newLineSep;} public DsvDataRdrOpts NewLineSep_(String val) {newLineSep = val; return this;} private String newLineSep = String_.CrLf;
	public String FldSep() {return fldSep;} public DsvDataRdrOpts FldSep_(String val) {fldSep = val; return this;} private String fldSep = ",";
	public GfoFldList Flds() {return flds;} public DsvDataRdrOpts Flds_(GfoFldList val) {flds = val; return this;} GfoFldList flds = GfoFldList_.Null;
	public static DsvDataRdrOpts new_() {return new DsvDataRdrOpts();} DsvDataRdrOpts() {}
}
