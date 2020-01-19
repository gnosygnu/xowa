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
package gplx.xowa.mediawiki.includes.cache.localisation; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.cache.*;
// MW.SRC:1.33
import gplx.xowa.mediawiki.xml.*;
import gplx.xowa.mediawiki.vendor.wikimedia.cldr_plural_rule_parser.src.*;
public class XomwLocalisationCache {
//		static final VERSION = 4;
//
//		/** Configuration associative array */
//		private conf;
//
//		/**
//		* True if recaching should only be done on an explicit call to recache().
//		* Setting this reduces the overhead of cache freshness checking, which
//		* requires doing a stat() for every extension i18n file.
//		*/
//		private manualRecache = false;
//
//		/**
//		* True to treat all files as expired until they are regenerated by this Object.
//		*/
//		private forceRecache = false;

	/**
	* The cache data. 3-d array, where the first key is the language code,
	* the second key is the item key e.g. 'messages', and the third key is
	* an item specific subkey index. Some items are not arrays and so for those
	* items, there are no subkeys.
	*/
	protected XophpArray data = XophpArray.New();

	/**
	* The persistent store Object. An instance of LCStore.
	*
	* @var LCStore
	*/
//		private XomwLCStore store = new XomwLCStoreNull();

	/**
	* A 2-d associative array, code/key, where presence indicates that the item
	* is loaded. Value arbitrary.
	*
	* For split items, if set, this indicates that all of the subitems have been
	* loaded.
	*/
	private XophpArray loadedItems = XophpArray.New();

	/**
	* A 3-d associative array, code/key/subkey, where presence indicates that
	* the subitem is loaded. Only used for the split items, i.e. messages.
	*/
//		private XophpArray loadedSubitems = XophpArray.New();

	/**
	* An array where presence of a key indicates that that language has been
	* initialised. Initialisation includes checking for cache expiry and doing
	* any necessary updates.
	*/
//		private XophpArray initialisedLangs = XophpArray.New();

	/**
	* An array mapping non-existent pseudo-languages to fallback languages. This
	* is filled by initShallowFallback() when data is requested from a language
	* that lacks a Messages*.php file.
	*/
	private XophpArray shallowFallbacks = XophpArray.New();
//
//		/**
//		* An array where the keys are codes that have been recached by this instance.
//		*/
//		private recachedLangs = [];
//
//		/**
//		* All item keys
//		*/
//		public static allKeys = [
//			'fallback', 'name+spaceNames', 'bookstoreList',
//			'magicWords', 'messages', 'rtl', 'capitalizeAllNouns', 'digitTransformTable',
//			'separatorTransformTable', 'minimumGroupingDigits',
//			'fallback8bitEncoding', 'linkPrefixExtension',
//			'linkTrail', 'linkPrefixCharset', 'name+spaceAliases',
//			'dateFormats', 'datePreferences', 'datePreferenceMigrationMap',
//			'defaultDateFormat', 'extraUserToggles', 'specialPageAliases',
//			'imageFiles', 'preloadedMessages', 'name+spaceGenderAliases',
//			'digitGroupingPattern', 'pluralRules', 'pluralRuleTypes', 'compiledPluralRules',
//		];
//
//		/**
//		* Keys for items which consist of associative arrays, which may be merged
//		* by a fallback sequence.
//		*/
//		public static mergeableMapKeys = [ 'messages', 'name+spaceNames',
//			'name+spaceAliases', 'dateFormats', 'imageFiles', 'preloadedMessages'
//		];
//
//		/**
//		* Keys for items which are a numbered array.
//		*/
//		public static mergeableListKeys = [ 'extraUserToggles' ];
//
//		/**
//		* Keys for items which contain an array of arrays of equivalent aliases
//		* for each subitem. The aliases may be merged by a fallback sequence.
//		*/
//		public static mergeableAliasListKeys = [ 'specialPageAliases' ];
//
//		/**
//		* Keys for items which contain an associative array, and may be merged if
//		* the primary value contains the special array key "inherit". That array
//		* key is removed after the first merge.
//		*/
//		public static optionalMergeKeys = [ 'bookstoreList' ];
//
//		/**
//		* Keys for items that are formatted like magicWords
//		*/
//		public static magicWordKeys = [ 'magicWords' ];

	/**
	* Keys for items where the subitems are stored in the backend separately.
	*/
	public static XophpArray splitKeys = XophpArray.New().Add("messages");

//		/**
//		* Keys which are loaded automatically by initLanguage()
//		*/
//		public static preloadedKeys = [ 'dateFormats', 'name+spaceNames' ];

	/**
	* Associative array of cached plural rules. The key is the language code,
	* the value is an array of plural rules for that language.
	*/
	protected XophpArray pluralRules = XophpArray.New();

	/**
	* Associative array of cached plural rule types. The key is the language
	* code, the value is an array of plural rule types for that language. For
	* example, pluralRuleTypes['ar'] = ['zero', 'one', 'two', 'few', 'many'].
	* The index for each rule type matches the index for the rule in
	* pluralRules, thus allowing correlation between the two. The reason we
	* don't just use the type names as the keys in pluralRules is because
	* XomwLanguage.convertPlural applies the rules based on numeric order (or
	* explicit numeric parameter), not based on the name of the rule type. For
	* example, {{plural:count|wordform1|wordform2|wordform3}}, rather than
	* {{plural:count|one=wordform1|two=wordform2|many=wordform3}}.
	*/
	private XophpArray pluralRuleTypes = XophpArray.New();

//		private mergeableKeys = null;
//
//		/**
//		* For constructor parameters, see the documentation in DefaultSettings.php
//		* for wgLocalisationCacheConf.
//		*
//		* @param array conf
//		* @throws MWException
//		*/
//		function __construct(conf) {
//			global wgCacheDirectory;
//
//			this.conf = conf;
//			storeConf = [];
//			if (!empty(conf['storecl+ass'])) {
//				storecl+ass = conf['storecl+ass'];
//			} else {
//				switch (conf['store']) {
//					case 'files':
//					case 'file':
//						storecl+ass = LCStoreCDB::cl+ass;
//						break;
//					case 'db':
//						storecl+ass = LCStoreDB::cl+ass;
//						storeConf['server'] = conf['storeServer'] ?? [];
//						break;
//					case 'array':
//						storecl+ass = LCStoreStaticArray::cl+ass;
//						break;
//					case 'detect':
//						if (!empty(conf['storeDirectory'])) {
//							storecl+ass = LCStoreCDB::cl+ass;
//						} elseif (wgCacheDirectory) {
//							storeConf['directory'] = wgCacheDirectory;
//							storecl+ass = LCStoreCDB::cl+ass;
//						} else {
//							storecl+ass = LCStoreDB::cl+ass;
//							storeConf['server'] = conf['storeServer'] ?? [];
//						}
//						break;
//					default:
//						throw new MWException(
//							'Please set wgLocalisationCacheConf[\'store\'] to something sensible.'
//						);
//				}
//			}
//
//			wfDebugLog('caches', static::cl+ass . ": using store storecl+ass");
//			if (!empty(conf['storeDirectory'])) {
//				storeConf['directory'] = conf['storeDirectory'];
//			}
//
//			this.store = new storecl+ass(storeConf);
//			foreach ([ 'manualRecache', 'forceRecache' ] as var) {
//				if (isset(conf[var])) {
//					this.var = conf[var];
//				}
//			}
//		}
//
//		/**
//		* Returns true if the given key is mergeable, that is, if it is an associative
//		* array which can be merged through a fallback sequence.
//		* @param String key
//		* @return boolean
//		*/
//		public function isMergeableKey(key) {
//			if (this.mergeableKeys === null) {
//				this.mergeableKeys = array_flip(array_merge(
//					XomwLocalisationCache.mergeableMapKeys,
//					XomwLocalisationCache.mergeableListKeys,
//					XomwLocalisationCache.mergeableAliasListKeys,
//					XomwLocalisationCache.optionalMergeKeys,
//					XomwLocalisationCache.magicWordKeys
//				));
//			}
//
//			return isset(this.mergeableKeys[key]);
//		}

	/**
	* Get a cache item.
	*
	* Warning: this may be slow for split items (messages), since it will
	* need to fetch all of the subitems from the cache individually.
	* @param String code
	* @param String key
	* @return mixed
	*/
	public Object getItem(String code, String key) {
		if (!this.loadedItems.Get_by_ary(code).isset(key)) {
//				this.loadItem(code, key);
		}

		if (String_.Eq(key, "fallback") && this.shallowFallbacks.isset(code)) {
			return this.shallowFallbacks.Get_by(code);
		}

		return this.data.Get_by_ary(code).Get_by(key);
	}

	/**
	* Get a subitem, for instance a single message for a given language.
	* @param String code
	* @param String key
	* @param String subkey
	* @return mixed|null
	*/
//		public XophpArray getSubitem(String code, String key, String subkey) {
//			if (!this.loadedSubitems.Get_by_ary(code).Get_by_ary(key).isset(subkey) &&
//				!this.loadedItems.Get_by_ary(code).isset(key)
//			) {
//				this.loadSubitem(code, key, subkey);
//			}
//
//			return this.data.Get_by_ary(code).Get_by_ary(key).Get_by_ary(subkey);
//		}
//
//		/**
//		* Get the list of subitem keys for a given item.
//		*
//		* This is faster than array_keys(lc.getItem(...)) for the items listed in
//		* XomwLocalisationCache.splitKeys.
//		*
//		* Will return null if the item is not found, or false if the item is not an
//		* array.
//		* @param String code
//		* @param String key
//		* @return boolean|null|String|String[]
//		*/
//		public function getSubitemList(code, key) {
//			if (in_array(key, XomwLocalisationCache.splitKeys)) {
//				return this.getSubitem(code, 'list', key);
//			} else {
//				item = this.getItem(code, key);
//				if (is_array(item)) {
//					return array_keys(item);
//				} else {
//					return false;
//				}
//			}
//		}

	/**
	* Load an item into the cache.
	* @param String code
	* @param String key
	*/
//		protected void loadItem(String code, String key) {
//			if (!this.initialisedLangs.isset(code)) {
//				this.initLanguage(code);
//			}
//
//			// Check to see if initLanguage() loaded it for us
//			if (this.loadedItems.Get_by_ary(code).Get_by_bool(key)) {
//				return;
//			}
//
//			if (this.shallowFallbacks.isset(code)) {
//				this.loadItem(this.shallowFallbacks.Get_by_str(code), key);
//
//				return;
//			}
//
//			if (XomwLocalisationCache.splitKeys.in_array(key)) {
//				XophpArray subkeyList = this.getSubitem(code, "list", key);
//				int subkeyListLen = subkeyList.Len();
//				for (int i = 0; i < subkeyListLen; i++) {
//					String subkey = subkeyList.Get_at_str(i);
//					if (this.data.Get_by_ary(code).Get_by_ary(key).isset(subkey)) {
//						continue;
//					}
//					this.data.Get_by_ary(code).Get_by_ary(key).Set(subkey, this.getSubitem(code, key, subkey));
//				}
//			} else {
//				this.data.Get_by_ary(code).Set(key, this.store.get(code, key));
//			}
//
//			this.loadedItems.Get_by_ary(code).Set(key, true);
//		}

	/**
	* Load a subitem into the cache
	* @param String code
	* @param String key
	* @param String subkey
	*/
//		protected void loadSubitem(String code, String key, String subkey) {
//			if (!XomwLocalisationCache.splitKeys.in_array(key)) {
//				this.loadItem(code, key);
//
//				return;
//			}
//
//			if (!this.initialisedLangs.isset(code)) {
//				this.initLanguage(code);
//			}
//
//			// Check to see if initLanguage() loaded it for us
//			if (this.loadedItems.Get_by_ary(code).isset(key) ||
//				this.loadedSubitems.Get_by_ary(code).Get_by_ary(key).isset(subkey)
//			) {
//				return;
//			}
//
//			if (this.shallowFallbacks.isset(code)) {
//				this.loadSubitem(this.shallowFallbacks.Get_by_str(code), key, subkey);
//
//				return;
//			}
//
//			Object value = this.store.get(code, key + ":" + subkey);
//			this.data.Get_by_ary(code).Get_by_ary(key).Set(subkey, value);
//			this.loadedSubitems.Get_by_ary(code).Get_by_ary(key).Set(subkey, true);
//		}

//		/**
//		* Returns true if the cache identified by code is missing or expired.
//		*
//		* @param String code
//		*
//		* @return boolean
//		*/
//		public function isExpired(code) {
//			if (this.forceRecache && !isset(this.recachedLangs[code])) {
//				wfDebug(__METHOD__ . "(code): forced reload\n");
//
//				return true;
//			}
//
//			deps = this.store.get(code, 'deps');
//			keys = this.store.get(code, 'list');
//			preload = this.store.get(code, 'preload');
//			// Different keys may expire separately for some stores
//			if (deps === null || keys === null || preload === null) {
//				wfDebug(__METHOD__ . "(code): cache missing, need to make one\n");
//
//				return true;
//			}
//
//			foreach (deps as dep) {
//				// Because we're unserializing stuff from cache, we
//				// could receive objects of cl+asses that don't exist
//				// anymore (e.g. uninstalled extensions)
//				// When this happens, always expire the cache
//				if (!dep instanceof CacheDependency || dep.isExpired()) {
//					wfDebug(__METHOD__ . "(code): cache for code expired due to " .
//						get_cl+ass(dep) . "\n");
//
//					return true;
//				}
//			}
//
//			return false;
//		}

	/**
	* Initialise a language in this Object. Rebuild the cache if necessary.
	* @param String code
	* @throws MWException
	*/
	protected void initLanguage(String code) {
//			if (this.initialisedLangs.isset(code)) {
//				return;
//			}
//
//			this.initialisedLangs.Set(code, true);
//
//			// If the code is of the wrong form for a Messages*.php file, do a shallow fallback
//			if (!XomwLanguage.isValidBuiltInCode(code)) {
//				this.initShallowFallback(code, "en");
//
//				return;
//			}
//
//			// Recache the data if necessary
//			if (!this.manualRecache && this.isExpired(code)) {
//				if (XomwLanguage.isSupportedLanguage(code)) {
//					this.recache(code);
//				} else if (String_.Eq(code, "en")) {
//					throw new XomwMWException("MessagesEn.php is missing.");
//				} else {
//					this.initShallowFallback(code, "en");
//				}
//
//				return;
//			}

//			// Preload some stuff
//			preload = this.getItem(code, "preload");
//			if (preload === null) {
//				if (this.manualRecache) {
//					// No Messages*.php file. Do shallow fallback to en.
//					if (code === "en") {
//						throw new MWException("No localisation cache found for English. " .
//							"Please run maintenance/rebuildLocalisationCache.php.");
//					}
//					this.initShallowFallback(code, "en");
//
//					return;
//				} else {
//					throw new MWException("Invalid or missing localisation cache.");
//				}
//			}
//			this.data[code] = preload;
//			foreach (preload as key => item) {
//				if (in_array(key, XomwLocalisationCache.splitKeys)) {
//					foreach (item as subkey => subitem) {
//						this.loadedSubitems[code][key][subkey] = true;
//					}
//				} else {
//					this.loadedItems[code][key] = true;
//				}
//			}
	}
//
//		/**
//		* Create a fallback from one language to another, without creating a
//		* complete persistent cache.
//		* @param String primaryCode
//		* @param String fallbackCode
//		*/
//		public function initShallowFallback(primaryCode, fallbackCode) {
//			this.data[primaryCode] =& this.data[fallbackCode];
//			this.loadedItems[primaryCode] =& this.loadedItems[fallbackCode];
//			this.loadedSubitems[primaryCode] =& this.loadedSubitems[fallbackCode];
//			this.shallowFallbacks[primaryCode] = fallbackCode;
//		}
//
//		/**
//		* Read a PHP file containing localisation data.
//		* @param String _fileName
//		* @param String _fileType
//		* @throws MWException
//		* @return array
//		*/
//		protected function readPHPFile(_fileName, _fileType) {
//			// Disable APC caching
//			Wikimedia\suppressWarnings();
//			_apcEnabled = ini_set('apc.cache_by_default', '0');
//			Wikimedia\restoreWarnings();
//
//			include _fileName;
//
//			Wikimedia\suppressWarnings();
//			ini_set('apc.cache_by_default', _apcEnabled);
//			Wikimedia\restoreWarnings();
//
//			data = [];
//			if (_fileType == 'core' || _fileType == 'extension') {
//				foreach (XomwLocalisationCache.allKeys as key) {
//					// Not all keys are set in language files, so
//					// check they exist first
//					if (isset(key)) {
//						data[key] = key;
//					}
//				}
//			} elseif (_fileType == 'aliases') {
//				if (isset(aliases)) {
//					data['aliases'] = aliases;
//				}
//			} else {
//				throw new MWException(__METHOD__ . ": Invalid file type: _fileType");
//			}
//
//			return data;
//		}
//
//		/**
//		* Read a JSON file containing localisation messages.
//		* @param String fileName Name of file to read
//		* @throws MWException If there is a syntax error in the JSON file
//		* @return array Array with a 'messages' key, or empty array if the file doesn't exist
//		*/
//		public function readJSONFile(fileName) {
//			if (!is_readable(fileName)) {
//				return [];
//			}
//
//			json = file_get_contents(fileName);
//			if (json === false) {
//				return [];
//			}
//
//			data = FormatJson::decode(json, true);
//			if (data === null) {
//				throw new MWException(__METHOD__ . ": Invalid JSON file: fileName");
//			}
//
//			// Remove keys starting with '@', they're reserved for metadata and non-message data
//			foreach (data as key => unused) {
//				if (key === '' || key[0] === '@') {
//					unset(data[key]);
//				}
//			}
//
//			// The JSON format only supports messages, none of the other variables, so wrap the data
//			return [ 'messages' => data ];
//		}

	/**
	* Get the compiled plural rules for a given language from the XML files.
	* @since 1.20
	* @param String code
	* @return array|null
	*/
	public XophpArray getCompiledPluralRules(String code) {
		XophpArray rules = this.getPluralRules(code);
		if (rules == null) {
			return null;
		}
//			try {
			XophpArray compiledRules = XomwEvaluator.compile(rules);
//			} catch (CLDRPluralRuleError e) {
//				wfDebugLog("l10n", e.getMessage());
//
//				return [];
//			}

		return compiledRules;
	}

	/**
	* Get the plural rules for a given language from the XML files.
	* Cached.
	* @since 1.20
	* @param String code
	* @return array|null
	*/
	public XophpArray getPluralRules(String code) {
		if (XophpObject_.is_null(this.pluralRules)) {
			this.loadPluralFiles();
		}
		return (XophpArray)XophpObject_.coalesce(this.pluralRules.Get_by_ary(code), null);
	}
//
//		/**
//		* Get the plural rule types for a given language from the XML files.
//		* Cached.
//		* @since 1.22
//		* @param String code
//		* @return array|null
//		*/
//		public function getPluralRuleTypes(code) {
//			if (this.pluralRuleTypes === null) {
//				this.loadPluralFiles();
//			}
//			return this.pluralRuleTypes[code] ?? null;
//		}

	/**
	* Load the plural XML files.
	*/
	protected void loadPluralFiles() {
		String cldrPlural = Io_url_.new_dir_(IP).GenSubFil_nest("languages", "data", "plurals.xml").Xto_api();
		String mwPlural = Io_url_.new_dir_(IP).GenSubFil_nest("languages", "data", "plurals-mediawiki.xml").Xto_api();
		// Load CLDR plural rules
		this.loadPluralFile(cldrPlural);
		if (XophpIo_.file_exists(mwPlural)) {
			// Override or extend
			this.loadPluralFile(mwPlural);
		}
	}
	/**
	* Load a plural XML file with the given filename, compile the relevant
	* rules, and save the compiled rules in a process-local cache.
	*
	* @param String fileName
	* @throws MWException
	*/
	protected void loadPluralFile(String fileName) {
		// Use file_get_contents instead of DOMDocument::load (T58439)
		String xml = XophpIo_.file_get_contents(fileName);
		if (!XophpString_.is_true(xml)) {
			if (gplx.core.envs.Env_.Mode_testing()) {
				xml = String_.Concat_lines_nl
					( "<pluralRules locales=\"en\"><pluralRule count=\"one\">i = 1 and v = 0</pluralRule></pluralRules>"
					);
			}
			else {
				throw new gplx.xowa.mediawiki.includes.exception.XomwMWException("Unable to read plurals file " + fileName);
			}
		}
		XophpDOMDocument doc = new XophpDOMDocument();
		doc.loadXML(xml);
		XophpDOMNodeList rulesets = doc.getElementsByTagName("pluralRules");
		int rulesets_len = rulesets.count();
		for (int i = 0; i < rulesets_len; i++) {
			XophpDOMNode ruleset = rulesets.item(i);
			String codes = ruleset.getAttribute("locales");
			XophpArray rules = XophpArray.New();
			XophpArray ruleTypes = XophpArray.New();
			XophpDOMNodeList ruleElements = ruleset.getElementsByTagName("pluralRule");
			int elements_len = ruleElements.count();
			for (int j = 0; j < elements_len; j++) {
				XophpDOMNode elt = ruleElements.item(j);
				String ruleType = elt.getAttribute("count");
				if (String_.Eq(ruleType, "other")) {
					// Don"t record "other" rules, which have an empty condition
					continue;
				}
				rules.Add(elt.nodeValue);
				ruleTypes.Add(ruleType);
			}
			String[] code_ary = XophpString_.explode(" ", codes);
			for (String code : code_ary) {
				this.pluralRules.Set(code, rules);
				this.pluralRuleTypes.Set(code, ruleTypes);
			}
		}
	}

	public static String IP;
	/**
	* Read the data from the source files for a given language, and register
	* the relevant dependencies in the deps array. If the localisation
	* exists, the data array is returned, otherwise false is returned.
	*
	* @param String code
	* @param array &deps
	* @return array
	*/
	protected XophpArray readSourceFilesAndRegisterDeps(String code, XophpArray deps) {
		// This reads in the PHP i18n file with non-messages l10n data
//			String fileName = XomwLanguage.getMessagesFileName(code);
//			if (!file_exists(fileName)) {
//				data = XophpArray.New();
//			} else {
//				deps.Add(new FileDependency(fileName));
//				data = this.readPHPFile(fileName, "core");
//			}
//
//			// Load CLDR plural rules for JavaScript
//			data.Set("pluralRules", this.getPluralRules(code));
//			// And for PHP
//			data.Set("compiledPluralRules", this.getCompiledPluralRules(code));
//			// Load plural rule types
//			data.Set("pluralRuleTypes", this.getPluralRuleTypes(code));
//
//			deps.Set("plurals", new FileDependency("IP/languages/data/plurals.xml"));
//			deps.Set("plurals-mw", new FileDependency("IP/languages/data/plurals-mediawiki.xml"));
//
//			return data;
		return null;
	}

//		/**
//		* Merge two localisation values, a primary and a fallback, overwriting the
//		* primary value in place.
//		* @param String key
//		* @param mixed &value
//		* @param mixed fallbackValue
//		*/
//		protected function mergeItem(key, &value, fallbackValue) {
//			if (!is_null(value)) {
//				if (!is_null(fallbackValue)) {
//					if (in_array(key, XomwLocalisationCache.mergeableMapKeys)) {
//						value = value + fallbackValue;
//					} elseif (in_array(key, XomwLocalisationCache.mergeableListKeys)) {
//						value = array_unique(array_merge(fallbackValue, value));
//					} elseif (in_array(key, XomwLocalisationCache.mergeableAliasListKeys)) {
//						value = array_merge_recursive(value, fallbackValue);
//					} elseif (in_array(key, XomwLocalisationCache.optionalMergeKeys)) {
//						if (!empty(value['inherit'])) {
//							value = array_merge(fallbackValue, value);
//						}
//
//						if (isset(value['inherit'])) {
//							unset(value['inherit']);
//						}
//					} elseif (in_array(key, XomwLocalisationCache.magicWordKeys)) {
//						this.mergeMagicWords(value, fallbackValue);
//					}
//				}
//			} else {
//				value = fallbackValue;
//			}
//		}
//
//		/**
//		* @param mixed &value
//		* @param mixed fallbackValue
//		*/
//		protected function mergeMagicWords(&value, fallbackValue) {
//			foreach (fallbackValue as magicName => fallbackInfo) {
//				if (!isset(value[magicName])) {
//					value[magicName] = fallbackInfo;
//				} else {
//					oldSynonyms = array_slice(fallbackInfo, 1);
//					newSynonyms = array_slice(value[magicName], 1);
//					synonyms = array_values(array_unique(array_merge(
//						newSynonyms, oldSynonyms)));
//					value[magicName] = array_merge([ fallbackInfo[0] ], synonyms);
//				}
//			}
//		}
//
//		/**
//		* Given an array mapping language code to localisation value, such as is
//		* found in extension *.i18n.php files, iterate through a fallback sequence
//		* to merge the given data with an existing primary value.
//		*
//		* Returns true if any data from the extension array was used, false
//		* otherwise.
//		* @param array codeSequence
//		* @param String key
//		* @param mixed &value
//		* @param mixed fallbackValue
//		* @return boolean
//		*/
//		protected function mergeExtensionItem(codeSequence, key, &value, fallbackValue) {
//			used = false;
//			foreach (codeSequence as code) {
//				if (isset(fallbackValue[code])) {
//					this.mergeItem(key, value, fallbackValue[code]);
//					used = true;
//				}
//			}
//
//			return used;
//		}
//
//		/**
//		* Gets the combined list of messages dirs from
//		* core and extensions
//		*
//		* @since 1.25
//		* @return array
//		*/
//		public function getMessagesDirs() {
//			global IP;
//
//			config = MediaWikiServices::getInstance().getMainConfig();
//			messagesDirs = config.get('MessagesDirs');
//			return [
//				'core' => "IP/languages/i18n",
//				'exif' => "IP/languages/i18n/exif",
//				'api' => "IP/includes/api/i18n",
//				'oojs-ui' => "IP/resources/lib/ooui/i18n",
//			] + messagesDirs;
//		}

	/**
	* Load localisation data for a given language for both core and extensions
	* and save it to the persistent cache store and the process cache
	* @param String code
	* @throws MWException
	*/
	public void recache(String code) {
//			global wgExtensionMessagesFiles;
//
//			if (!code) {
//				throw new MWException("Invalid language code requested");
//			}
//			this.recachedLangs[code] = true;

		// Initial values
//			XophpArray initialData = array_fill_keys(XomwLocalisationCache.allKeys, null);
		XophpArray initialData = XophpArray.New(); //array_fill_keys(XomwLocalisationCache.allKeys, null);
//			coreData = initialData;
//			deps = [];
//
//			// Load the primary localisation from the source file
//			data = this.readSourceFilesAndRegisterDeps(code, deps);
//			if (data === false) {
//				wfDebug(__METHOD__ . ": no localisation file for code, using fallback to en\n");
//				coreData['fallback'] = 'en';
//			} else {
//				wfDebug(__METHOD__ . ": got localisation for code from source\n");
//
//				// Merge primary localisation
//				foreach (data as key => value) {
//					this.mergeItem(key, coreData[key], value);
//				}
//			}
//
//			// Fill in the fallback if it's not there already
//			if ((is_null(coreData['fallback']) || coreData['fallback'] === false) && code === 'en') {
//				coreData['fallback'] = false;
//				coreData['originalFallbackSequence'] = coreData['fallbackSequence'] = [];
//			} else {
//				if (!is_null(coreData['fallback'])) {
//					coreData['fallbackSequence'] = array_map('trim', explode(',', coreData['fallback']));
//				} else {
//					coreData['fallbackSequence'] = [];
//				}
//				len = count(coreData['fallbackSequence']);
//
//				// Before we add the 'en' fallback for messages, keep a copy of
//				// the original fallback sequence
//				coreData['originalFallbackSequence'] = coreData['fallbackSequence'];
//
//				// Ensure that the sequence ends at 'en' for messages
//				if (!len || coreData['fallbackSequence'][len - 1] !== 'en') {
//					coreData['fallbackSequence'][] = 'en';
//				}
//			}
//
//			codeSequence = array_merge([ code ], coreData['fallbackSequence']);
//			messageDirs = this.getMessagesDirs();
//
//			// Load non-JSON localisation data for extensions
//			extensionData = array_fill_keys(codeSequence, initialData);
//			foreach (wgExtensionMessagesFiles as extension => fileName) {
//				if (isset(messageDirs[extension])) {
//					// This extension has JSON message data; skip the PHP shim
//					continue;
//				}
//
//				data = this.readPHPFile(fileName, 'extension');
//				used = false;
//
//				foreach (data as key => item) {
//					foreach (codeSequence as csCode) {
//						if (isset(item[csCode])) {
//							this.mergeItem(key, extensionData[csCode][key], item[csCode]);
//							used = true;
//						}
//					}
//				}
//
//				if (used) {
//					deps[] = new FileDependency(fileName);
//				}
//			}
//
		// Load the localisation data for each fallback, then merge it into the full array
		XophpArray allData = initialData;
//			foreach (codeSequence as csCode) {
//				csData = initialData;
//
//				// Load core messages and the extension localisations.
//				foreach (messageDirs as dirs) {
//					foreach ((array)dirs as dir) {
//						fileName = "dir/csCode.json";
//						data = this.readJSONFile(fileName);
//
//						foreach (data as key => item) {
//							this.mergeItem(key, csData[key], item);
//						}
//
//						deps[] = new FileDependency(fileName);
//					}
//				}
//
//				// Merge non-JSON extension data
//				if (isset(extensionData[csCode])) {
//					foreach (extensionData[csCode] as key => item) {
//						this.mergeItem(key, csData[key], item);
//					}
//				}
//
//				if (csCode === code) {
//					// Merge core data into extension data
//					foreach (coreData as key => item) {
//						this.mergeItem(key, csData[key], item);
//					}
//				} else {
//					// Load the secondary localisation from the source file to
//					// avoid infinite cycles on cyclic fallbacks
//					fbData = this.readSourceFilesAndRegisterDeps(csCode, deps);
//					if (fbData !== false) {
//						// Only merge the keys that make sense to merge
//						foreach (XomwLocalisationCache.allKeys as key) {
//							if (!isset(fbData[key])) {
//								continue;
//							}
//
//							if (is_null(coreData[key]) || this.isMergeableKey(key)) {
//								this.mergeItem(key, csData[key], fbData[key]);
//							}
//						}
//					}
//				}
//
//				// Allow extensions an opportunity to adjust the data for this
//				// fallback
//				Hooks::run('LocalisationCacheRecacheFallback', [ this, csCode, &csData ]);
//
//				// Merge the data for this fallback into the final array
//				if (csCode === code) {
//					allData = csData;
//				} else {
//					foreach (XomwLocalisationCache.allKeys as key) {
//						if (!isset(csData[key])) {
//							continue;
//						}
//
//						if (is_null(allData[key]) || this.isMergeableKey(key)) {
//							this.mergeItem(key, allData[key], csData[key]);
//						}
//					}
//				}
//			}
//
//			// Add cache dependencies for any referenced globals
//			deps['wgExtensionMessagesFiles'] = new GlobalDependency('wgExtensionMessagesFiles');
//			// The 'MessagesDirs' config setting is used in LocalisationCache::getMessagesDirs().
//			// We use the key 'wgMessagesDirs' for historical reasons.
//			deps['wgMessagesDirs'] = new MainConfigDependency('MessagesDirs');
//			deps['version'] = new ConstantDependency('LocalisationCache::VERSION');
//
//			// Add dependencies to the cache entry
//			allData['deps'] = deps;
//
//			// Replace spaces with underscores in name+space names
//			allData['name+spaceNames'] = str_replace(' ', '_', allData['name+spaceNames']);
//
//			// And do the same for special page aliases. page is an array.
//			foreach (allData['specialPageAliases'] as &page) {
//				page = str_replace(' ', '_', page);
//			}
//			// Decouple the reference to prevent accidental damage
//			unset(page);
//
//			// If there were no plural rules, return an empty array
//			if (allData['pluralRules'] === null) {
//				allData['pluralRules'] = [];
//			}
		if (allData.Has("compiledPluralRules")) {
			allData.Set("compiledPluralRules", XophpArray.New());
		}
//			// If there were no plural rule types, return an empty array
//			if (allData['pluralRuleTypes'] === null) {
//				allData['pluralRuleTypes'] = [];
//			}
//
//			// Set the list keys
//			allData['list'] = [];
//			foreach (XomwLocalisationCache.splitKeys as key) {
//				allData['list'][key] = array_keys(allData[key]);
//			}
//			// Run hooks
//			purgeBlobs = true;
//			Hooks::run('LocalisationCacheRecache', [ this, code, &allData, &purgeBlobs ]);
//
//			if (is_null(allData['name+spaceNames'])) {
//				throw new MWException(__METHOD__ . ': Localisation data failed sanity check! ' .
//					'Check that your languages/messages/MessagesEn.php file is intact.');
//			}
//
//			// Set the preload key
//			allData['preload'] = this.buildPreload(allData);
//
//			// Save to the process cache and register the items loaded
//			this.data[code] = allData;
//			foreach (allData as key => item) {
//				this.loadedItems[code][key] = true;
//			}
//
//			// Save to the persistent cache
//			this.store.startWrite(code);
//			foreach (allData as key => value) {
//				if (in_array(key, XomwLocalisationCache.splitKeys)) {
//					foreach (value as subkey => subvalue) {
//						this.store.set("key:subkey", subvalue);
//					}
//				} else {
//					this.store.set(key, value);
//				}
//			}
//			this.store.finishWrite();
//
//			// Clear out the MessageBlobStore
//			// HACK: If using a null (i.e. disabled) storage backend, we
//			// can't write to the MessageBlobStore either
//			if (purgeBlobs && !this.store instanceof LCStoreNull) {
//				blobStore = new MessageBlobStore(
//					MediaWikiServices::getInstance().getResourceLoader()
//				);
//				blobStore.clear();
//			}
	}
//
//		/**
//		* Build the preload item from the given pre-cache data.
//		*
//		* The preload item will be loaded automatically, improving performance
//		* for the commonly-requested items it contains.
//		* @param array data
//		* @return array
//		*/
//		protected function buildPreload(data) {
//			preload = [ 'messages' => [] ];
//			foreach (XomwLocalisationCache.preloadedKeys as key) {
//				preload[key] = data[key];
//			}
//
//			foreach (data['preloadedMessages'] as subkey) {
//				subitem = data['messages'][subkey] ?? null;
//				preload['messages'][subkey] = subitem;
//			}
//
//			return preload;
//		}
//
//		/**
//		* Unload the data for a given language from the Object cache.
//		* Reduces memory usage.
//		* @param String code
//		*/
//		public function unload(code) {
//			unset(this.data[code]);
//			unset(this.loadedItems[code]);
//			unset(this.loadedSubitems[code]);
//			unset(this.initialisedLangs[code]);
//			unset(this.shallowFallbacks[code]);
//
//			foreach (this.shallowFallbacks as shallowCode => fbCode) {
//				if (fbCode === code) {
//					this.unload(shallowCode);
//				}
//			}
//		}
//
//		/**
//		* Unload all data
//		*/
//		public function unloadAll() {
//			foreach (this.initialisedLangs as lang => unused) {
//				this.unload(lang);
//			}
//		}
//
//		/**
//		* Disable the storage backend
//		*/
//		public function disableBackend() {
//			this.store = new LCStoreNull;
//			this.manualRecache = false;
//		}
}