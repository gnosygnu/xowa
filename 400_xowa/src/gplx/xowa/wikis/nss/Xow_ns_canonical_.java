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
package gplx.xowa.wikis.nss; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.primitives.*;
public class Xow_ns_canonical_ {
	public static final Xow_ns[] Ary = new Xow_ns[]	// REF.MW: Namespace.php|$wgCanonicalNamespaceNames
	{ New_itm(Xow_ns_.Tid__media					, Xow_ns_.Key__media)
	, New_itm(Xow_ns_.Tid__special					, Xow_ns_.Key__special)
	, New_itm(Xow_ns_.Tid__talk						, Xow_ns_.Key__talk)
	, New_itm(Xow_ns_.Tid__user						, Xow_ns_.Key__user)
	, New_itm(Xow_ns_.Tid__user_talk				, Xow_ns_.Key__user_talk)
	, New_itm(Xow_ns_.Tid__project					, Xow_ns_.Key__project)
	, New_itm(Xow_ns_.Tid__project_talk				, Xow_ns_.Key__project_talk)
	, New_itm(Xow_ns_.Tid__file						, Xow_ns_.Key__file)
	, New_itm(Xow_ns_.Tid__file_talk				, Xow_ns_.Key__file_talk)
	, New_itm(Xow_ns_.Tid__mediawiki				, Xow_ns_.Key__mediawiki)
	, New_itm(Xow_ns_.Tid__mediawiki_talk			, Xow_ns_.Key__mediawiki_talk)
	, New_itm(Xow_ns_.Tid__template					, Xow_ns_.Key__template)
	, New_itm(Xow_ns_.Tid__template_talk			, Xow_ns_.Key__template_talk)
	, New_itm(Xow_ns_.Tid__help						, Xow_ns_.Key__help)
	, New_itm(Xow_ns_.Tid__help_talk				, Xow_ns_.Key__help_talk)
	, New_itm(Xow_ns_.Tid__category					, Xow_ns_.Key__category)
	, New_itm(Xow_ns_.Tid__category_talk			, Xow_ns_.Key__category_talk)
	, New_itm(Xow_ns_.Tid__module					, Xow_ns_.Key__module)
	, New_itm(Xow_ns_.Tid__module_talk				, Xow_ns_.Key__module_talk)
	};
	private static Xow_ns New_itm(int id, String name) {return new Xow_ns(id, Xow_ns_case_.Tid__1st, Bry_.new_a7(name), false);}	// NOTE: for id/name reference only; case_match and alias does not matter;
	private static Ordered_hash id_hash;
	public static int To_id(byte[] key) {
		if (id_hash == null) {
			id_hash = Ordered_hash_.New_bry();
			int len = Ary.length;
			for (int i = 0; i < len; ++i) {
				Xow_ns ns = Ary[i];
				id_hash.Add(ns.Name_db(), Int_obj_val.new_(ns.Id()));
			}
		}
		Object rv_obj = id_hash.Get_by(key);
		return rv_obj == null ? Xow_ns_.Tid__null : ((Int_obj_val)rv_obj).Val();
	}
	public static String To_canonical_or_local(Xow_ns ns) {	// NOTE: prefer canonical names if they exist; otherwise use local; PAGE:sh.w:Koprno; DATE:2015-11-08
		switch (ns.Id()) {
			case Xow_ns_.Tid__media:					return Xow_ns_.Key__media;
			case Xow_ns_.Tid__special:					return Xow_ns_.Key__special;
			case Xow_ns_.Tid__talk:						return Xow_ns_.Key__talk;
			case Xow_ns_.Tid__user:						return Xow_ns_.Key__user;
			case Xow_ns_.Tid__user_talk:				return Xow_ns_.Key__user_talk;
			case Xow_ns_.Tid__project:					return Xow_ns_.Key__project;
			case Xow_ns_.Tid__project_talk:				return Xow_ns_.Key__project_talk;
			case Xow_ns_.Tid__file:						return Xow_ns_.Key__file;
			case Xow_ns_.Tid__file_talk:				return Xow_ns_.Key__file_talk;
			case Xow_ns_.Tid__mediawiki:				return Xow_ns_.Key__mediawiki;
			case Xow_ns_.Tid__mediawiki_talk:			return Xow_ns_.Key__mediawiki_talk;
			case Xow_ns_.Tid__template:					return Xow_ns_.Key__template;
			case Xow_ns_.Tid__template_talk:			return Xow_ns_.Key__template_talk;
			case Xow_ns_.Tid__help:						return Xow_ns_.Key__help;
			case Xow_ns_.Tid__help_talk:				return Xow_ns_.Key__help_talk;
			case Xow_ns_.Tid__category:					return Xow_ns_.Key__category;
			case Xow_ns_.Tid__category_talk:			return Xow_ns_.Key__category_talk;
			case Xow_ns_.Tid__module:					return Xow_ns_.Key__module;
			case Xow_ns_.Tid__module_talk:				return Xow_ns_.Key__module_talk;
			default:									return String_.new_u8(ns.Name_ui());
		}
	}
}
