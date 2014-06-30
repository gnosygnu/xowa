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
package gplx.stores.dsvs; import gplx.*; import gplx.stores.*;
public class DsvHeaderList {
	@gplx.Internal protected int Count() {return list.Count();}
	@gplx.Internal protected DsvHeaderItm FetchAt(int i) {return (DsvHeaderItm)list.FetchAt(i);}
	public DsvHeaderList Add_LeafTypes() {this.Add(new DsvHeaderItm(DsvHeaderItm.Id_LeafTypes, null)); return this;}
	public DsvHeaderList Add_LeafNames() {this.Add(new DsvHeaderItm(DsvHeaderItm.Id_LeafNames, null)); return this;}
	public DsvHeaderList Add_TableName() {this.Add(new DsvHeaderItm(DsvHeaderItm.Id_TableName, null)); return this;}
	public DsvHeaderList Add_BlankLine() {this.Add(new DsvHeaderItm(DsvHeaderItm.Id_BlankLine, null)); return this;}
	public DsvHeaderList Add_Comment(String comment) {this.Add(new DsvHeaderItm(DsvHeaderItm.Id_Comment, comment)); return this;}
	void Add(DsvHeaderItm data) {list.Add(data);}

	ListAdp list = ListAdp_.new_();
	public static DsvHeaderList new_() {return new DsvHeaderList();} DsvHeaderList() {}
}
class DsvHeaderItm {
	public int Id() {return id;} int id;
	public Object Val() {return val;} Object val;
	@gplx.Internal protected DsvHeaderItm(int id, Object val) {this.id = id; this.val = val;}

	public static final int
		  Id_Comment	= 1
		, Id_TableName	= 2
		, Id_BlankLine	= 3
		, Id_LeafTypes	= 4
		, Id_LeafNames	= 5
		;
}
