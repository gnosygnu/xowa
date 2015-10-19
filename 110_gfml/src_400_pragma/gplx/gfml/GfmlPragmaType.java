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
package gplx.gfml; import gplx.*;
import gplx.lists.*;
class GfmlPragmaType implements GfmlPragma {
	public String KeyOfPragma() {return pragmaKey;} private String pragmaKey = "_type";
	public void Exec(GfmlBldr bldr, GfmlNde pragmaNde) {
		Ordered_hash list = Ordered_hash_.New(); List_adp replaced = List_adp_.new_();
		for (int i = 0 ; i < pragmaNde.SubHnds().Count(); i++) {
			GfmlNde typNde = pragmaNde.SubHnds().Get_at(i);
			GfmlType type = GfmlTypeCompiler.Compile(typNde, GfmlType_.Root, bldr.TypeMgr().TypeRegy(), list);
			if (i == 0) {
				GfmlFrame_nde topFrame = GfmlFrame_nde_.as_(bldr.CurFrame());
				AssignDefaultType(type, topFrame.CurNde(), bldr);
			}					
		}
		ExecList(bldr.TypeMgr().TypeRegy(), list, replaced);
//			bldr.PragmaMgr().EndCmds_add(bldr.CurNdeFrame().DocPos().NewUp(), GfmlPragmaType_endCmd.new_(list, replaced)); // expireCmd commented out?
	}
	void AssignDefaultType(GfmlType type, GfmlNde nde, GfmlBldr bldr) {
		if (nde.Type().IsTypeNull())
			nde.Type_set(GfmlType_.new_any_());
		GfmlFld subFld = GfmlFld.new_(false, type.NdeName(), type.Key());
		nde.Type().SubFlds().Add(subFld);
		bldr.TypeMgr().OverridePool(type);
	}
	public static GfmlPragmaType new_() {return new GfmlPragmaType();} GfmlPragmaType() {}
	public GfmlType[] MakePragmaTypes(GfmlTypeMakr makr) {
		makr.MakeSubTypeAsOwner(						"_type");
		makr.MakeSubTypeAsOwner(							"type", "name", "key");
		makr.MakeSubTypeAsOwner(								"fld", "name", "default", "type");
		makr.AddSubFld(GfmlFld.new_(false,						"fld", "_type/type/fld"));
		return makr.Xto_bry();
	}
	public static final String CacheLog_key = "log:type";
	@gplx.Internal protected static void ExecList(GfmlTypRegy regy, Ordered_hash list, List_adp replaced) {
		for (Object typeObj : list) {
			GfmlType type = (GfmlType)typeObj;
			if (regy.Has(type.Key()))
				replaced.Add(type);
			regy.Add(type);
		}
	}
}
class GfmlPragmaType_endCmd implements GfmlBldrCmd {
	public String Key() {return "cmd.gfml.type.end";}
	public void Exec(GfmlBldr bldr, GfmlTkn tkn) {ExecList(bldr.TypeMgr().TypeRegy(), list, replaced);}
	@gplx.Internal protected static void ExecList(GfmlTypRegy regy, Ordered_hash list, List_adp replaced) {
		for (Object typeObj : list) {
			GfmlType type = (GfmlType)typeObj;
			regy.Del(type);
		}
		for (Object typeObj : replaced) {
			GfmlType type = (GfmlType)typeObj;
			regy.Add(type);
		}
	}
	Ordered_hash list; List_adp replaced;
	public static GfmlPragmaType_endCmd new_(Ordered_hash list, List_adp replaced) {
		GfmlPragmaType_endCmd rv = new GfmlPragmaType_endCmd();
		rv.list = list; rv.replaced = replaced;
		return rv;
	}	GfmlPragmaType_endCmd() {}
}
