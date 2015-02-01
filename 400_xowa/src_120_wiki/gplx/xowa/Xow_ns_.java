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
package gplx.xowa; import gplx.*;
import gplx.core.primitives.*;
public class Xow_ns_ {
	public static final int	// PAGE:en.w:http://www.mediawiki.org/wiki/Help:Namespaces
		  Id_media				=  -2
		, Id_special			=  -1
		, Id_main				=   0				, Id_talk				=   1
		, Id_user				=   2				, Id_user_talk			=   3
		, Id_project			=   4				, Id_project_talk		=   5
		, Id_file				=   6				, Id_file_talk			=   7
		, Id_mediawiki			=   8				, Id_mediaWiki_talk		=   9
		, Id_template			=  10				, Id_template_talk		=  11
		, Id_help				=  12				, Id_help_talk			=  13
		, Id_category			=  14				, Id_category_talk		=  15
		, Id_portal				= 100				, Id_portal_talk		= 101
	    , Id_null				= Int_.MinValue
		;
	public static final String
		  Key_media				= "Media"
		, Key_special			= "Special"
		, Key_main				= "(Main)"			, Key_talk				= "Talk"
		, Key_user				= "User"			, Key_user_talk			= "User talk"
		, Key_project			= "Project"			, Key_project_talk		= "Project talk"
		, Key_file				= "File"			, Key_file_talk			= "File talk"
		, Key_mediaWiki			= "MediaWiki"		, Key_mediaWiki_talk	= "MediaWiki talk"
		, Key_template			= "Template"		, Key_template_talk		= "Template talk"
		, Key_help				= "Help"			, Key_help_talk			= "Help talk"
		, Key_category			= "Category"		, Key_category_talk		= "Category talk"
		, Key_portal			= "Portal"			, Key_portal_talk		= "Portal talk"
		, Key_module			= "Module"			, Key_module_talk		= "Module talk"
	    , Key_null				= "null"
		;
	public static final byte[] Bry_template = Bry_.new_ascii_(Key_template);
	public static final byte[] Name_ui_main = Bry_.new_ascii_(Key_main);
	public static int Canonical_id(byte[] canonical_name) {
		if (canonical_hash == null) {
			Xow_ns[] ary = Canonical;
			int len = ary.length;
			canonical_hash = OrderedHash_.new_bry_();
			for (int i = 0; i < len; i++) {
				Xow_ns ns = ary[i];
				canonical_hash.Add(ns.Name_bry(), Int_obj_val.new_(ns.Id()));
			}
		}
		Object rv_obj = canonical_hash.Fetch(canonical_name);
		return rv_obj == null ? Xow_ns_.Id_null : ((Int_obj_val)rv_obj).Val();
	}	private static OrderedHash canonical_hash;
	public static int Canonical_idx_media = 0;
	public static final Xow_ns[] Canonical = new Xow_ns[]	// REF.MW: Namespace.php|$wgCanonicalNamespaceNames
	{	Canonical_new_(Id_media,				Key_media)
	,	Canonical_new_(Id_special,				Key_special)
	,	Canonical_new_(Id_talk,					Key_talk)
	,	Canonical_new_(Id_user,					Key_user)
	,	Canonical_new_(Id_user_talk,			Key_user_talk)
	,	Canonical_new_(Id_project,				Key_project)
	,	Canonical_new_(Id_project_talk,			Key_project_talk)
	,	Canonical_new_(Id_file,					Key_file)
	,	Canonical_new_(Id_file_talk,			Key_file_talk)
	,	Canonical_new_(Id_mediawiki,			Key_mediaWiki)
	,	Canonical_new_(Id_mediaWiki_talk,		Key_mediaWiki_talk)
	,	Canonical_new_(Id_template,				Key_template)
	,	Canonical_new_(Id_template_talk,		Key_template_talk)
	,	Canonical_new_(Id_help,					Key_help)
	,	Canonical_new_(Id_help_talk,			Key_help_talk)
	,	Canonical_new_(Id_category,				Key_category)
	,	Canonical_new_(Id_category_talk,		Key_category_talk)
	};
	public static final String Ns_name_wikipedia = "Wikipedia";
	public static final byte[] Ns_name_main_bry = Bry_.new_ascii_(Key_main);
	public static final byte[] Ns_prefix_main = Bry_.new_ascii_("Main:");
	private static Xow_ns Canonical_new_(int id, String name) {return new Xow_ns(id, Xow_ns_case_.Id_1st, Bry_.new_ascii_(name), false);}	// NOTE: for id/name reference only; case_match and alias does not matter;
}
