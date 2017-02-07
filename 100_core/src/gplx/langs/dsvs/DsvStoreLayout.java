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
public class DsvStoreLayout {
	public DsvHeaderList HeaderList() {return headerList;} DsvHeaderList headerList = DsvHeaderList.new_();
	@gplx.Internal protected boolean WriteCmdSequence() {return writeCmdSequence;} @gplx.Internal protected DsvStoreLayout WriteCmdSequence_(boolean val) {writeCmdSequence = val; return this;} private boolean writeCmdSequence;

	static DsvStoreLayout new_() {return new DsvStoreLayout();}
	public static DsvStoreLayout csv_dat_() {return new_();}
	public static DsvStoreLayout csv_hdr_() {
		DsvStoreLayout rv = new_();
		rv.HeaderList().Add_LeafNames();
		return rv;
	}
	public static DsvStoreLayout dsv_brief_() {
		DsvStoreLayout rv = new_();
		rv.writeCmdSequence = true;
		return rv;
	}
	public static DsvStoreLayout dsv_full_() {
		DsvStoreLayout rv = DsvStoreLayout.new_();
		rv.writeCmdSequence = true;
		rv.HeaderList().Add_BlankLine();
		rv.HeaderList().Add_BlankLine();
		rv.HeaderList().Add_Comment("================================");
		rv.HeaderList().Add_TableName();
		rv.HeaderList().Add_Comment("================================");
		rv.HeaderList().Add_LeafTypes();
		rv.HeaderList().Add_LeafNames();
		rv.HeaderList().Add_Comment("================================");
		return rv;
	}
	public static DsvStoreLayout as_(Object obj) {return obj instanceof DsvStoreLayout ? (DsvStoreLayout)obj : null;}
	public static DsvStoreLayout cast(Object obj) {try {return (DsvStoreLayout)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, DsvStoreLayout.class, obj);}}
	public static final String Key_const = "StoreLayoutWtr";
}
