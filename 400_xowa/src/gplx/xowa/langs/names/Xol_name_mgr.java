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
package gplx.xowa.langs.names; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.core.primitives.*;
import gplx.xowa.xtns.cldrs.*;

public class Xol_name_mgr {
	private final    Cldr_name_loader cldr_loader;
	private final    Language_name_loader name_loader;
	private final    Io_url root_dir;
	private final    Object thread_lock = new Object(); // THREAD:Xol_name_mgr can be used by multiple wiki threads; ISSUE#:549; DATE:2019-08-14
//		private Ordered_hash hash;
//		private Keyval[] kvs;

	public Xol_name_mgr(Cldr_name_loader cldr_loader, Language_name_loader name_loader, Io_url root_dir) {
		this.cldr_loader = cldr_loader;
		this.name_loader = name_loader;
		this.root_dir = root_dir;
	}
	/**
	* @param String $code The code of the language for which to get the name
	* @param null|String $inLanguage Code of language in which to return the name (null for autonyms)
	* @param String $include 'all', 'mw' or 'mwfile'; see fetchLanguageNames()
	* @return String Language name or empty
	* @since 1.20
	*/
	public String fetchLanguageName(String code, String inLanguage, String include, byte[] page_url) {
		synchronized (thread_lock) {
		code = String_.Lower(code);
		if (include == null) include = Scope__str__all;
		Ordered_hash array = fetchLanguageNames(inLanguage, include, page_url);
		Keyval rv = (Keyval)array.Get_by(code);
		return rv == null ? "" : rv.Val_to_str_or_null();
		}
	}
	/**
	* Get an array of language names, indexed by code.
	* @param null|String $inLanguage Code of language in which to return the names
	*		Use null for autonyms (native names)
	* @param String $include One of:
	*		'all' all available languages
	*		'mw' only if the language is defined in MediaWiki or wgExtraLanguageNames (default)
	*		'mwfile' only if the language is in 'mw' *and* has a message file
	* @return array Language code => language name
	* @since 1.20
	*/
	//  = 'mw
	private Ordered_hash languageNameCache;
	private Ordered_hash lang_names_cached;
	private Ordered_hash lang_files_cached;

	public Ordered_hash fetchLanguageNames(String inLanguage, String include_str, byte[] page_url) {
		synchronized (thread_lock) {
		if (inLanguage == null) inLanguage = "null";
		String cacheKey = inLanguage + ":" + include_str;
		if (languageNameCache == null)
			languageNameCache = Ordered_hash_.New();
		Ordered_hash ret = (Ordered_hash)languageNameCache.Get_by(cacheKey);
		if (ret == null) {
			Byte_obj_val include_byte = (Byte_obj_val)Scope__hash.Get_by(include_str);
			byte include = include_byte == null ? Scope__int__all : include_byte.Val();

			Cldr_name_file cldr_file = cldr_loader.Load_or_empty(inLanguage);
			if (cldr_file == Cldr_name_file.Empty) {
				Gfo_usr_dlg_.Instance.Log_many("", "", "lang does not have a cldrName file; lang=~{0} page=~{1}", inLanguage, page_url);
			}

			if (lang_names_cached == null)
				lang_names_cached = name_loader.Load_as_hash();

			if (lang_files_cached == null) {
				lang_files_cached = Ordered_hash_.New();
				Io_url[] urls = Io_mgr.Instance.QueryDir_fils(root_dir.GenSubDir_nest("bin", "any", "xowa", "cfg", "lang", "core"));
				for (Io_url url : urls) {
					String code = url.NameOnly();
					lang_files_cached.Add(code, Keyval_.new_(code, code));
				}
			}
			
			ret = fetchLanguageNamesUncached(inLanguage, include, cldr_file.Language_names(), lang_names_cached, lang_files_cached);
			languageNameCache.Add(cacheKey, ret);
		}
		return ret;
		}
		/*
		$ret = self::$languageNameCache->get( $cacheKey );
		if ( !$ret ) {
			$ret = self::fetchLanguageNamesUncached( $inLanguage, $include );
			self::$languageNameCache->set( $cacheKey, $ret );
		}
		return $ret;
		*/
	}
//		public Keyval Get_by_or_null(String code) {
//			if (hash == null) Init();
//			return (Keyval)hash.Get_by(code);
//		}
//		public Keyval[] Get_all() {
//			if (hash == null) Init();
//			return kvs;
//		}
	/*
	private static final    Hash_adp isValidCode_cache = Hash_adp_bry.cs();
	private static final    Strcpn isValidCode_strcpn = Strcpn.New_by_concatenated_ascii(":/\\\000&<>'\"");
	private boolean isValidCode(byte[] code) {
		Bool_obj_val rv = (Bool_obj_val)isValidCode_cache.Get_by(code);
		if (rv == null) {
			// People think language codes are html safe, so enforce it.
			// Ideally we should only allow a-zA-Z0-9-
			// but, .+ and other chars are often used for {{int:}} hacks
			// see bugs T39564, T39587, T38938
			rv = 
				// Protect against path traversal
				(	isValidCode_strcpn.Exec(code) == code.length 
				// && !preg_match( MediaWikiTitleCodec::getTitleInvalidRegex(), $code );
				)
				? Bool_obj_val.True 
				: Bool_obj_val.False;
			isValidCode_cache.Add(code, rv);
		}
		return rv.Val();
	}
	*/

//		private void Init() {
//			Cldr_name_file file = cldr_loader.Load(String_.new_u8(host_lang));
//			Ordered_hash cldr_names = file.Language_names();
//			Ordered_hash lang_names = name_loader.Load_as_hash();
//
//			Ordered_hash lang_files = Ordered_hash_.New();
//			Io_url[] urls = Io_mgr.Instance.QueryDir_fils(root_dir.GenSubDir_nest("bin", "any", "xowa", "cfg", "lang", "core"));
//			foreach (Io_url url in urls) {
//				String code = url.NameOnly();
//				lang_files.Add(code, Keyval_.new_(code, code));
//			}
//
//			hash = fetchLanguageNamesUncached(host_lang, Scope__int__mw, cldr_names, lang_names, lang_files);
//			kvs = (Keyval[])hash.To_ary(typeof(Keyval));
//		}

	/*
	Scope__mw__str
	Scope__hash
	*/
	public static final byte 
		  Scope__int__mw      = 0 // cldr + Names.php + *.json|*.php 
		, Scope__int__all     = 1 // cldr + Names.php
		, Scope__int__mwFile  = 2 // *.json|*.php
		;
	public static final    String
		  Scope__str__mw      = "mw"
		, Scope__str__all     = "all"
		, Scope__str__mwFile  = "mwFile"
		;
	private static final    Hash_adp Scope__hash = Hash_adp_.New()
		.Add_and_more(Scope__str__mw    , Byte_obj_val.new_(Scope__int__mw))
		.Add_and_more(Scope__str__all   , Byte_obj_val.new_(Scope__int__all))
		.Add_and_more(Scope__str__mwFile, Byte_obj_val.new_(Scope__int__mwFile))
		;
	public static Ordered_hash fetchLanguageNamesUncached
		( String inLanguage, byte include
		, Ordered_hash cldr_names
		, Ordered_hash lang_names
		, Ordered_hash lang_files
		) {
		// XOWA: skip this for now
		// If passed an invalid language code to use, fallback to en
		// if ( $inLanguage !== null && !Language::isValidCode( $inLanguage ) ) {
		//	$inLanguage = 'en';
		Ordered_hash names = Ordered_hash_.New();

		// XOWA: 'LanguageGetTranslatedLanguageNames' is only hooked by cldr_names; REF.MW:/cldr/extension.json
		// if ( $inLanguage ) {
		//	# TODO: also include when $inLanguage is null, when this code is more efficient
		//	Hooks::run( 'LanguageGetTranslatedLanguageNames', [ &$names, $inLanguage ] );
		// }
		int cldr_names_len = cldr_names.Len();
		for (int i = 0; i < cldr_names_len; i++) {
			Keyval kv = (Keyval)cldr_names.Get_at(i);
			names.Add(kv.Key(), kv);
		}

		// REF.MW: /includes/DefaultSettings.php
		// global $wgExtraLanguageNames;

		// REF.MW: /languages/data/Names.php
		// $mwNames = $wgExtraLanguageNames + MediaWiki\Languages\Data\Names::$names;
		// TODO:also include /LocalNames/ REF.MW:/cldr/includes/LanguageNames.php
		Ordered_hash mwNames = lang_names;
		int mwNames_len = mwNames.Len();
		for (int i = 0; i < mwNames_len; i++) {
			Keyval mw_name = (Keyval)mwNames.Get_at(i);
			// # - Prefer own MediaWiki native name when not using the hook
			// # - For other mwNames just add if not added through the hook
			String code = mw_name.Key();
			if (String_.Eq(code, inLanguage) || !names.Has(code)) {
				names.Add_if_dupe_use_nth(code, Keyval_.new_(code, mw_name.Val_to_str_or_empty()));
			}
		}

		if (include == Scope__int__all) {
			names.Sort_by(Hash_kv_sorter.Instance);
			return names;
		}

		Ordered_hash returnMw = Ordered_hash_.New();
		mwNames_len = mwNames.Len();
		for (int i = 0; i < mwNames_len; i++) {
			Keyval coreName = (Keyval)mwNames.Get_at(i);
			String code = coreName.Key();
			returnMw.Add(code, (Keyval)names.Get_by(code));
		}

		// REF.MW: /languages/classes/i18n/*.json
		// REF.MW: /languages/classes/messages/Messages*.php
		if (include == Scope__int__mwFile) {
			Ordered_hash namesMwFile = Ordered_hash_.New();
			// # We do this using a foreach over the codes instead of a directory
			// # loop so that messages files in extensions will work correctly.
			int returnMwLen = returnMw.Len();
			for (int i = 0; i < returnMwLen; i++) {
				Keyval kv = (Keyval)returnMw.Get_at(i);
				String code = kv.Key();
				if (lang_files.Has(code)) {
					namesMwFile.Add(code, (Keyval)names.Get_by(code));
				}
			}

			namesMwFile.Sort_by(Hash_kv_sorter.Instance);
			return namesMwFile;
		}

		returnMw.Sort_by(Hash_kv_sorter.Instance);
		// # 'mw' option; default if it's not one of the other two options (all/mwfile)
		return returnMw;
	}
	public static Xol_name_mgr New(Io_url root_dir) {
		Io_url cldr_dir = root_dir.GenSubDir_nest("bin", "any", "xowa", "xtns", "cldr", "CldrNames");
		return new Xol_name_mgr(new Cldr_name_loader(cldr_dir), new Language_name_loader(root_dir), root_dir);
	}
}
class Hash_kv_sorter implements gplx.core.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Keyval lhs = (Keyval)lhsObj;
		Keyval rhs = (Keyval)rhsObj;
		return String_.Compare(lhs.Key(), rhs.Key());
	}
        public static final    Hash_kv_sorter Instance = new Hash_kv_sorter(); Hash_kv_sorter() {}
}
