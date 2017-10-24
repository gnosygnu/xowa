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
package gplx.gfml; import gplx.*;
import gplx.core.stores.*; /*DataRdr_base*/
public abstract class GfmlDataRdr_base extends DataRdr_base implements DataRdr {
	@Override public String NameOfNode()			{return curNde.Hnd();}
	@Override public int FieldCount()			{return curNde.SubKeys().Count();}
	@Override public String KeyAt(int idx)		{return curNde.SubKeys().Get_at(idx).KeyTkn().Val();}
	@Override public Object ReadAt(int idx)		{return GfmlAtr.as_(curNde.SubKeys().Get_at(idx)).DatTkn().Val();}
	@Override public Object Read(String name)	{return curNde.SubKeys().FetchDataOrNull(name);}
	public boolean MoveNextPeer() {
		pos += 1;
		if (list == null || pos >= list.Count()) return false; // TODO_9: makeCurNde null; invalidate FieldAt, etc?
		curNde = list.Get_at(pos);
		return true;
	}
	@Override public DataRdr Subs() {
		GfmlDataRdrNde rv = new GfmlDataRdrNde(curNde); rv.Parse_set(this.Parse());
		if (curNde != null) rv.list = curNde.SubHnds();
		return rv;
	}
	@Override public DataRdr Subs_byName_moveFirst(String name) {
		DataRdr subRdr = Subs_byName(name);
		return subRdr.MoveNextPeer() ? subRdr : DataRdr_.Null;
	}		
	public DataRdr Subs_byName(String name) {
		GfmlDataRdrNde rv = new GfmlDataRdrNde(curNde);
		rv.list = GfmlItmHnds.new_();
		if (curNde == null) return rv;
		for (int i = 0; i < curNde.SubHnds().Count(); i++) {
			GfmlNde sub = (GfmlNde)curNde.SubHnds().Get_at(i);
			String typeName = sub.Type().NdeName();
			if (	sub.Type().IsTypeAny())		// isAnyType b/c match may not be exact; ex: type can be defined as item:key name; but actlNde may be item:key name size;
				//||	sub.Type().IsTypeNull())
				typeName = sub.Hnd();
			if (String_.Eq(typeName, name))
				rv.list.Add(sub);
		}
		return rv;
	}		
	public void Rls() {}
	public String To_str() {return curNde.To_str();}
	@gplx.Internal protected void SetNode(GfmlNde curNde) {this.curNde = curNde; this.list = curNde.SubHnds();}
	GfmlNde curNde; GfmlItmHnds list; int pos = -1;
}
