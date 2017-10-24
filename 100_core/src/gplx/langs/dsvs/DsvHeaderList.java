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
public class DsvHeaderList {
	@gplx.Internal protected int Count() {return list.Count();}
	@gplx.Internal protected DsvHeaderItm Get_at(int i) {return (DsvHeaderItm)list.Get_at(i);}
	public DsvHeaderList Add_LeafTypes() {this.Add(new DsvHeaderItm(DsvHeaderItm.Id_LeafTypes, null)); return this;}
	public DsvHeaderList Add_LeafNames() {this.Add(new DsvHeaderItm(DsvHeaderItm.Id_LeafNames, null)); return this;}
	public DsvHeaderList Add_TableName() {this.Add(new DsvHeaderItm(DsvHeaderItm.Id_TableName, null)); return this;}
	public DsvHeaderList Add_BlankLine() {this.Add(new DsvHeaderItm(DsvHeaderItm.Id_BlankLine, null)); return this;}
	public DsvHeaderList Add_Comment(String comment) {this.Add(new DsvHeaderItm(DsvHeaderItm.Id_Comment, comment)); return this;}
	void Add(DsvHeaderItm data) {list.Add(data);}

	List_adp list = List_adp_.New();
	public static DsvHeaderList new_() {return new DsvHeaderList();} DsvHeaderList() {}
}
class DsvHeaderItm {
	public int Id() {return id;} int id;
	public Object Val() {return val;} Object val;
	@gplx.Internal protected DsvHeaderItm(int id, Object val) {this.id = id; this.val = val;}

	public static final    int
		  Id_Comment	= 1
		, Id_TableName	= 2
		, Id_BlankLine	= 3
		, Id_LeafTypes	= 4
		, Id_LeafNames	= 5
		;
}
