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
package gplx.core.gfo_ndes; import gplx.*; import gplx.core.*;
import gplx.core.type_xtns.*; import gplx.core.stores.*;
public class GfoNde_ {
	public static final    GfoNde[] Ary_empty = new GfoNde[0];
	public static GfoNde[] ary_(GfoNde... ary) {return ary;}
	public static GfoNde as_(Object obj) {return obj instanceof GfoNde ? (GfoNde)obj : null;}		
	public static GfoNde root_(GfoNde... subs)								{return new GfoNde(GfoNde_.Type_Root, "RootName", GfoFldList_.Null, Object_.Ary_empty, GfoFldList_.Null, subs);}
	public static GfoNde tbl_(String name, GfoFldList flds, GfoNde... rows)	{return new GfoNde(GfoNde_.Type_Node, name, flds, Object_.Ary_empty, flds, rows);}
	public static GfoNde vals_(GfoFldList flds, Object[] ary)						{return new GfoNde(GfoNde_.Type_Leaf, "row", flds, ary, GfoFldList_.Null, Ary_empty);}
	public static GfoNde vals_params_(GfoFldList flds, Object... ary)			{return new GfoNde(GfoNde_.Type_Leaf, "row", flds, ary, GfoFldList_.Null, Ary_empty);}
	public static GfoNde nde_(String name, Object[] ary, GfoNde... subs)		{return new GfoNde(GfoNde_.Type_Node, name, GfoFldList_.Null, ary, GfoFldList_.Null, subs);}
	public static GfoNde rdr_(DataRdr rdr) {
		try {
			List_adp rows = List_adp_.New();
			GfoFldList flds = GfoFldList_.new_(); 
			int fldLen = rdr.FieldCount();
			for (int i = 0; i < fldLen; i++)
				flds.Add(rdr.KeyAt(i), ObjectClassXtn.Instance);
			while (rdr.MoveNextPeer()) {
				Object[] valAry = new Object[fldLen];
				for (int i = 0; i < fldLen; i++)
					valAry[i] = rdr.ReadAt(i);
				rows.Add(GfoNde_.vals_(flds, valAry));
			}
			return GfoNde_.tbl_("", flds, (GfoNde[])rows.To_ary(GfoNde.class));
		}
		finally {rdr.Rls();}
	}
	@gplx.Internal protected static final    int Type_Leaf = 1, Type_Node = 2, Type_Root = 3;
}
