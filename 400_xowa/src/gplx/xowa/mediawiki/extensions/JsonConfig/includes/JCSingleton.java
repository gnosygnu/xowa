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
package gplx.xowa.mediawiki.extensions.JsonConfig.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.extensions.*; import gplx.xowa.mediawiki.extensions.JsonConfig.*;
import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
public class JCSingleton {
//		/**
//		* @var array describes how a title should be handled by JsonConfig extension.
//		* The structure is an array of array of ...:
//		* { int_namespace => { name => { allows-sub-namespaces => configuration_array } } }
//		*/
//		public static $titleMap = [];
//
//		/**
//		* @var String[]|false[] containing all the namespaces handled by JsonConfig
//		* Maps namespace id (int) => namespace name (String).
//		* If false, presumes the namespace has been registered by core or another extension
//		*/
//		public static $namespaces = [];
//
//		/**
//		* @var MapCacheLRU[] contains a cache of recently resolved JCTitle's
//		*   as namespace => MapCacheLRU
//		*/
//		public static $titleMapCacheLru = [];

	/**
	* @var MapCacheLRU[] contains a cache of recently requested content objects
	*   as namespace => MapCacheLRU
	*/
	private final    Ordered_hash mapCacheLru = Ordered_hash_.New();

	public Xomw_page_fetcher Store() {return store;} public void Store_(Xomw_page_fetcher v) {this.store = v;} private Xomw_page_fetcher store;
	public XophpArray ConfigModels() {return configModels;} private final    XophpArray configModels = new XophpArray();
//		/**
//		* @var TitleParser cached invariant title parser
//		*/
//		public static $titleParser;
//
//		/**
//		* Initializes singleton state by parsing $wgJsonConfig* values
//		* @throws Exception
//		*/
//		private static function init() {
//			static $isInitialized = false;
//			if ($isInitialized) {
//				return;
//			}
//			$isInitialized = true;
//			global $wgNamespaceContentModels, $wgContentHandlers, $wgJsonConfigs, $wgJsonConfigModels;
//			list(self::$titleMap, self::$namespaces) = self::parseConfiguration(
//				$wgNamespaceContentModels,
//				$wgContentHandlers,
//				array_replace_recursive(
//					\ExtensionRegistry::getInstance()->getAttribute('JsonConfigs'), $wgJsonConfigs
//				),
//				array_replace_recursive(
//					\ExtensionRegistry::getInstance()->getAttribute('JsonConfigModels'),
//					$wgJsonConfigModels
//				)
//			);
//		}
//
//		/**
//		* @param array $namespaceContentModels $wgNamespaceContentModels
//		* @param array $contentHandlers $wgContentHandlers
//		* @param array $configs $wgJsonConfigs
//		* @param array $models $wgJsonConfigModels
//		* @param boolean $warn if true, calls wfLogWarning() for all errors
//		* @return array [ $titleMap, $namespaces ]
//		*/
//		public static function parseConfiguration(
//			array $namespaceContentModels, array $contentHandlers,
//			array $configs, array $models, $warn = true
//		) {
//			$defaultModelId = 'JsonConfig';
//			// @codingStandardsIgnoreStart - T154789
//			$warnFunc = $warn ? 'wfLogWarning' : function() {};
//			// @codingStandardsIgnoreEnd
//
//			$namespaces = [];
//			$titleMap = [];
//			foreach ($configs as $confId => &$conf) {
//				if (!is_string($confId)) {
//					$warnFunc(
//						"JsonConfig: Invalid \$wgJsonConfigs['$confId'], the key must be a String"
//					);
//					continue;
//				}
//				if (null == self::getConfObject($warnFunc, $conf, $confId)) {
//					continue; // warned inside the function
//				}
//
//				$modelId = property_exists($conf, 'model')
//					? ($conf->model ? : $defaultModelId) : $confId;
//				if (!array_key_exists($modelId, $models)) {
//					if ($modelId == $defaultModelId) {
//						$models[$defaultModelId] = null;
//					} else {
//						$warnFunc("JsonConfig: Invalid \$wgJsonConfigs['$confId']: " .
//							"Model '$modelId' is not defined in \$wgJsonConfigModels");
//						continue;
//					}
//				}
//				if (array_key_exists($modelId, $contentHandlers)) {
//					$warnFunc("JsonConfig: Invalid \$wgJsonConfigs['$confId']: Model '$modelId' is " .
//						"already registered in \$contentHandlers to {$contentHandlers[$modelId]}");
//					continue;
//				}
//				$conf->model = $modelId;
//
//				$ns = self::getConfVal($conf, 'namespace', NS_CONFIG);
//				if (!is_int($ns) || $ns % 2 !== 0) {
//					$warnFunc("JsonConfig: Invalid \$wgJsonConfigs['$confId']: " .
//						"Namespace $ns should be an even number");
//					continue;
//				}
//				// Even though we might be able to override default content model for namespace,
//				// lets keep things clean
//				if (array_key_exists($ns, $namespaceContentModels)) {
//					$warnFunc("JsonConfig: Invalid \$wgJsonConfigs['$confId']: Namespace $ns is " .
//						"already set to handle model '$namespaceContentModels[$ns]'");
//					continue;
//				}
//
//				// nsName & nsTalk are handled later
//				self::getConfVal($conf, 'pattern', '');
//				self::getConfVal($conf, 'cacheExp', 24 * 60 * 60);
//				self::getConfVal($conf, 'cacheKey', '');
//				self::getConfVal($conf, 'flaggedRevs', false);
//				self::getConfVal($conf, 'license', false);
//				$islocal = self::getConfVal($conf, 'isLocal', true);
//
//				// Decide if matching configs should be stored on this wiki
//				$storeHere = $islocal || property_exists($conf, 'store');
//				if (!$storeHere) {
//					// 'store' does not exist, use it as a flag to indicate remote storage
//					$conf->store = false;
//					$remote = self::getConfObject($warnFunc, $conf, 'remote', $confId, 'url');
//					if (null == $remote) {
//						continue; // warned inside the function
//					}
//					if (self::getConfVal($remote, 'url', '') == '') {
//						$warnFunc("JsonConfig: Invalid \$wgJsonConfigs['$confId']['remote']['url']: " .
//							"API URL is not set, and this config is not being stored locally");
//						continue;
//					}
//					self::getConfVal($remote, 'username', '');
//					self::getConfVal($remote, 'password', '');
//				} else {
//					if (property_exists($conf, 'remote')) {
//						// non-fatal -- simply ignore the 'remote' setting
//						$warnFunc("JsonConfig: In \$wgJsonConfigs['$confId']['remote'] is set for " .
//							"the config that will be stored on this wiki. " .
//							"'remote' parameter will be ignored."
//						);
//					}
//					$conf->remote = null;
//					$store = self::getConfObject($warnFunc, $conf, 'store', $confId);
//					if (null == $store) {
//						continue; // warned inside the function
//					}
//					self::getConfVal($store, 'cacheNewValue', true);
//					self::getConfVal($store, 'notifyUrl', '');
//					self::getConfVal($store, 'notifyUsername', '');
//					self::getConfVal($store, 'notifyPassword', '');
//				}
//
//				// Too lazy to write proper error messages for all parameters.
//				if ((isset($conf->nsTalk) && !is_string($conf->nsTalk)) ||
//					!is_string($conf->pattern) ||
//					!is_bool($islocal) || !is_int($conf->cacheExp) || !is_string($conf->cacheKey)
//					|| !is_bool($conf->flaggedRevs)
//				) {
//					$warnFunc("JsonConfig: Invalid type of one of the parameters in " .
//						"\$wgJsonConfigs['$confId'], please check documentation");
//					continue;
//				}
//				if (isset($remote)) {
//					if (!is_string($remote->url) || !is_string($remote->username) ||
//						!is_string($remote->password)
//					) {
//						$warnFunc("JsonConfig: Invalid type of one of the parameters in " .
//							"\$wgJsonConfigs['$confId']['remote'], please check documentation");
//						continue;
//					}
//				}
//				if (isset($store)) {
//					if (!is_bool($store->cacheNewValue) || !is_string($store->notifyUrl) ||
//						!is_string($store->notifyUsername) || !is_string($store->notifyPassword)
//					) {
//						$warnFunc("JsonConfig: Invalid type of one of the parameters in " .
//							" \$wgJsonConfigs['$confId']['store'], please check documentation");
//						continue;
//					}
//				}
//				if ($storeHere) {
//					// If nsName is given, add it to the list, together with the talk page
//					// Otherwise, create a placeholder for it
//					if (property_exists($conf, 'nsName')) {
//						if ($conf->nsName == false) {
//							// Non JC-specific namespace, don't register it
//							if (!array_key_exists($ns, $namespaces)) {
//								$namespaces[$ns] = false;
//							}
//						} elseif ($ns == NS_CONFIG) {
//							$warnFunc("JsonConfig: Parameter 'nsName' in \$wgJsonConfigs['$confId'] " .
//								"is not supported for namespace == NS_CONFIG ($ns)");
//						} else {
//							$nsName = $conf->nsName;
//							$nsTalk = isset($conf->nsTalk) ? $conf->nsTalk : ($nsName . '_talk');
//							if (!is_string($nsName) || $nsName == '') {
//								$warnFunc("JsonConfig: Invalid \$wgJsonConfigs['$confId']: " .
//										"if given, nsName must be a String");
//								continue;
//							} elseif (array_key_exists($ns, $namespaces) &&
//									$namespaces[$ns] !== null
//							) {
//								if ($namespaces[$ns] !== $nsName ||
//									$namespaces[$ns + 1] !== $nsTalk
//								) {
//									$warnFunc("JsonConfig: \$wgJsonConfigs['$confId'] - " .
//											"nsName has already been set for namespace $ns");
//								}
//							} else {
//								$namespaces[$ns] = $nsName;
//								$namespaces[$ns + 1] =
//									isset($conf->nsTalk) ? $conf->nsTalk : ($nsName . '_talk');
//							}
//						}
//					} elseif (!array_key_exists($ns, $namespaces) || $namespaces[$ns] == false) {
//						$namespaces[$ns] = null;
//					}
//				}
//
//				if (!array_key_exists($ns, $titleMap)) {
//					$titleMap[$ns] = [ $conf ];
//				} else {
//					$titleMap[$ns][] = $conf;
//				}
//			}
//
//			// Add all undeclared namespaces
//			$missingNs = 1;
//			foreach ($namespaces as $ns => $nsName) {
//				if ($nsName == null) {
//					$nsName = 'Config';
//					if ($ns !== NS_CONFIG) {
//						$nsName .= $missingNs;
//						$warnFunc(
//							"JsonConfig: Namespace $ns does not have 'nsName' defined, using '$nsName'"
//						);
//						$missingNs += 1;
//					}
//					$namespaces[$ns] = $nsName;
//					$namespaces[$ns + 1] = $nsName . '_talk';
//				}
//			}
//
//			return [ $titleMap, $namespaces ];
//		}
//
//		/**
//		* Helper function to check if configuration has a field set, and if not, set it to default
//		* @param stdClass $conf
//		* @param String $field
//		* @param mixed $default
//		* @return mixed
//		*/
//		private static function getConfVal(& $conf, $field, $default) {
//			if (property_exists($conf, $field)) {
//				return $conf->$field;
//			}
//			$conf->$field = $default;
//			return $default;
//		}
//
//		/**
//		* Helper function to check if configuration has a field set, and if not, set it to default
//		* @param $warnFunc
//		* @param $value
//		* @param String $field
//		* @param String $confId
//		* @param String $treatAsField
//		* @return null|Object|stdClass
//		*/
//		private static function getConfObject(
//			$warnFunc, & $value, $field, $confId = null, $treatAsField = null
//		) {
//			if (!$confId) {
//				$val = & $value;
//			} else {
//				if (!property_exists($value, $field)) {
//					$value->$field = null;
//				}
//				$val = & $value->$field;
//			}
//			if ($val == null || $val == true) {
//				$val = new stdClass();
//			} elseif (is_array($val)) {
//				$val = (Object)$val;
//			} elseif (is_string($val) && $treatAsField !== null) {
//				// treating this String value as a sub-field
//				$val = (Object)[ $treatAsField => $val ];
//			} elseif (!is_object($val)) {
//				$warnFunc("JsonConfig: Invalid \$wgJsonConfigs" . ($confId ? "['$confId']" : "") .
//					"['$field'], the value must be either an array or an Object");
//				return null;
//			}
//			return $val;
//		}

	/**
	* Get content Object from the local LRU cache, or null if doesn't exist
	* @param TitleValue $titleValue
	* @return null|JCContent
	*/
	public JCContent getContentFromLocalCache(String wiki, String ns, String page) {
		// Some of the titleValues are remote, and their namespace might not be declared
		// in the current wiki. Since TitleValue is a content Object, it does not validate
		// the existence of namespace, hence we use it as a simple storage.
		// Producing an artificial String key by appending (namespaceID . ':' . titleDbKey)
		// seems wasteful and redundant, plus most of the time there will be just a single
		// namespace declared, so this structure seems efficient and easy enough.
		String mapCacheLruKey = String_.Concat(wiki, "|", ns);
		Ordered_hash cache = (Ordered_hash)mapCacheLru.Get_by(mapCacheLruKey);
		if (cache == null) {
			cache = Ordered_hash_.New();
			mapCacheLru.Add(mapCacheLruKey, cache);
		} 

		return (JCContent)cache.Get_by(page);
	}

	/**
	* Get content Object for the given title.
	* Namespace ID does not need to be defined in the current wiki,
	* as long as it is defined in $wgJsonConfigs.
	* @param TitleValue|JCTitle $titleValue
	* @return boolean|JCContent Returns false if the title is not handled by the settings
	*/
	public JCContent getContent(String wiki, String ns, String page) {
		JCContent content = getContentFromLocalCache(wiki, ns, page);

		if (content == null) {
			byte[] content_bry = store.Get_wtxt(Bry_.new_u8(wiki), Bry_.new_u8(page));
			if (content_bry != null) {
				JCContentHandler handler = new JCContentHandler();
				handler.__construct(JCTabularContent.Model_id, this);
				content = handler.unserializeContent(content_bry, null, false);
			}
//				$jct = self::parseTitle($titleValue);
//				if ($jct) {
//					$store = new JCCache($jct);
//					$content = $store->get();
//					if (is_string($content)) {
//						// Convert String to the content Object if needed
//						$handler = new JCContentHandler($jct->getConfig()->model);
//						$content = $handler->unserializeContent($content, null, false);
//					}
//				} else {
//					$content = false;
//				}
//				self::mapCacheLru[$titleValue->getNamespace()]
//					->set($titleValue->getDBkey(), $content);
		}

		return content;
	}

//		/**
//		* Parse json text into a content Object for the given title.
//		* Namespace ID does not need to be defined in the current wiki,
//		* as long as it is defined in $wgJsonConfigs.
//		* @param TitleValue $titleValue
//		* @param String $jsonText json content
//		* @param boolean $isSaving if true, performs extensive validation during unserialization
//		* @return boolean|JCContent Returns false if the title is not handled by the settings
//		* @throws Exception
//		*/
//		public static function parseContent(TitleValue $titleValue, $jsonText, $isSaving = false) {
//			$jct = self::parseTitle($titleValue);
//			if ($jct) {
//				$handler = new JCContentHandler($jct->getConfig()->model);
//				return $handler->unserializeContent($jsonText, null, $isSaving);
//			}
//
//			return false;
//		}
//
//		/**
//		* Mostly for debugging purposes, this function returns initialized @gplx.Internal protected JsonConfig settings
//		* @return array[] map of namespaceIDs to list of configurations
//		*/
//		public static function getTitleMap() {
//			self::init();
//			return self::$titleMap;
//		}

	/**
	* Get the name of the class for a given content model
	* @param String $modelId
	* @return null|String
	*/
	public XophpClassBldr getContentClass(String modelId) {
//			global $wgJsonConfigModels;
//			$configModels = array_replace_recursive(
//				\ExtensionRegistry::getInstance()->getAttribute('JsonConfigModels'),
//				$wgJsonConfigModels
//			);
		String clz = null;
		if (configModels.Has(modelId)) {
			Object val = configModels.Get_by(modelId);
			if (Type_.Type_by_obj(val) == XophpArray.class) {
				XophpArray val_as_ary = (XophpArray)val;
				if (val_as_ary.Has("class")) {
					Gfo_usr_dlg_.Instance.Warn_many("", "", "JsonConfig: Invalid +$wgJsonConfigModels['modelId'] array " + "value, 'cl"+ "ass' not found");
				} else {
					clz = (String)val_as_ary.Get_by("class");
				}
			} else {
				clz = (String)val;
			}
		}
		if (clz == null) {
			clz = "JCContent"; // __NAMESPACE__ . '\JCContent';
		}
		return XophpEnv.Instance.ClassBldrs().Get_by_or_null(clz);
	}

//		/**
//		* Given a title (either a user-given String, or as an Object), return JCTitle
//		* @param Title|TitleValue|String $value
//		* @param int|null $namespace Only used when title is a String
//		* @return JCTitle|null|false false if unrecognized namespace,
//		* and null if namespace is handled but does not match this title
//		* @throws Exception
//		*/
//		public static function parseTitle($value, $namespace = null) {
//			if ($value == null || $value == '' || $value == false) {
//				// In some weird cases $value is null
//				return false;
//			} elseif ($value instanceof JCTitle) {
//				// Nothing to do
//				return $value;
//			} elseif ($namespace !== null && !is_integer($namespace)) {
//				throw new Exception('$namespace parameter must be either null or an integer');
//			}
//
//			// figure out the namespace ID (int) - we don't need to parse the String if ns is unknown
//			if ($value instanceof LinkTarget) {
//				if ($namespace == null) {
//					$namespace = $value->getNamespace();
//				}
//			} elseif (is_string($value)) {
//				if ($namespace == null) {
//					throw new Exception('$namespace parameter is missing for String $value');
//				}
//			} else {
//				wfLogWarning('Unexpected title param type ' . gettype($value));
//				return false;
//			}
//
//			// Search title map for the matching configuration
//			$map = self::getTitleMap();
//			if (array_key_exists($namespace, $map)) {
//				// Get appropriate LRU cache Object
//				if (!array_key_exists($namespace, self::$titleMapCacheLru)) {
//					self::$titleMapCacheLru[$namespace] = $cache = new MapCacheLRU(20);
//				} else {
//					$cache = self::$titleMapCacheLru[$namespace];
//				}
//
//				// Parse String if needed
//				// TODO: should the String parsing also be cached?
//				if (is_string($value)) {
//					$language = Language::factory('en');
//					if (!self::$titleParser) {
//						self::$titleParser =
//							new MediaWikiTitleCodec(
//								$language,
//								new GenderCache(),
//								[],
//								new FauxInterwikiLookup());
//					}
//					// Interwiki prefixes are a special case for title parsing:
//					// first letter is not capitalized, namespaces are not resolved, etc.
//					// So we prepend an interwiki prefix to fool title codec, and later remove it.
//					try {
//						$value = FauxInterwikiLookup::INTERWIKI_PREFIX . ':' . $value;
//						$parts = self::$titleParser->splitTitleString($value);
//
//						// Defensive coding - ensure the parsing has proceeded as expected
//						if ($parts['dbkey'] == '' || $parts['namespace'] !== 0 ||
//							$parts['fragment'] !== '' || $parts['local_interwiki'] !== false ||
//							$parts['interwiki'] !== FauxInterwikiLookup::INTERWIKI_PREFIX
//						) {
//							return null;
//						}
//					} catch (MalformedTitleException $e) {
//						return null;
//					}
//
//					// At this point, only support wiki namespaces that capitalize title's first char,
//					// but do not enable sub-pages.
//					// This way data can already be stored on Mediawiki namespace everywhere, or
//					// places like commons and zerowiki.
//					// Another implicit limitation: there might be an issue if data is stored on a wiki
//					// with the non-default ucfirst(), e.g. az, kaa, kk, tr -- they convert "i" to "I"
//					$dbKey = $language->ucfirst($parts['dbkey']);
//				} else {
//					$dbKey = $value->getDBkey();
//				}
//
//				// A bit weird here: cache will store JCTitle objects or false if the namespace
//				// is known to JsonConfig but the dbkey does not match. But in case the title is not
//				// handled, this function returns null instead of false if the namespace is known,
//				// and false otherwise
//				$result = $cache->get($dbKey);
//				if ($result == null) {
//					$result = false;
//					foreach ($map[$namespace] as $conf) {
//						$re = $conf->pattern;
//						if (!$re || preg_match($re, $dbKey)) {
//							$result = new JCTitle($namespace, $dbKey, $conf);
//							break;
//						}
//					}
//
//					$cache->set($dbKey, $result);
//				}
//
//				// return null if the given namespace is mentioned in the config,
//				// but title doesn't match
//				return $result ?: null;
//
//			} else {
//				// return false if JC doesn't know anything about this namespace
//				return false;
//			}
//		}
//
//		/**
//		* Returns an array with settings if the $titleValue Object is handled by the JsonConfig
//		* extension, false if unrecognized namespace,
//		* and null if namespace is handled but not this title
//		* @param TitleValue $titleValue
//		* @return stdClass|false|null
//		* @deprecated use JCSingleton::parseTitle() instead
//		*/
//		public static function getMetadata($titleValue) {
//			$jct = self::parseTitle($titleValue);
//			return $jct ? $jct->getConfig() : $jct;
//		}
//
//		/**
//		* Only register NS_CONFIG if running on the MediaWiki instance which houses
//		* the JSON configs (i.e. META)
//		* @TODO FIXME: Always return true
//		* @param array &$namespaces
//		* @return true|void
//		*/
//		public static function onCanonicalNamespaces(array &$namespaces) {
//			if (!self::jsonConfigIsStorage()) {
//				return true;
//			}
//
//			self::init();
//			foreach (self::$namespaces as $ns => $name) {
//				if ($name == false) { // must be already declared
//					if (!array_key_exists($ns, $namespaces)) {
//						wfLogWarning("JsonConfig: Invalid \$wgJsonConfigs: Namespace $ns " .
//							"has not been declared by core or other extensions");
//					}
//				} elseif (array_key_exists($ns, $namespaces)) {
//					wfLogWarning("JsonConfig: Invalid \$wgJsonConfigs: Namespace $ns => '$name' " .
//						"is already declared as '$namespaces[$ns]'");
//				} else {
//					$key = array_search($name, $namespaces);
//					if ($key !== false) {
//						wfLogWarning("JsonConfig: Invalid \$wgJsonConfigs: Namespace $ns => '$name' " .
//							"has identical name with the namespace #$key");
//					} else {
//						$namespaces[$ns] = $name;
//					}
//				}
//			}
//		}
//
//		/**
//		* Initialize state
//		* @param Title $title
//		* @param String &$modelId
//		* @return boolean
//		*/
//		public static function onContentHandlerDefaultModelFor($title, &$modelId) {
//			if (!self::jsonConfigIsStorage()) {
//				return true;
//			}
//
//			$jct = self::parseTitle($title);
//			if ($jct) {
//				$modelId = $jct->getConfig()->model;
//				return false;
//			}
//			return true;
//		}
//
//		/**
//		* Instantiate JCContentHandler if we can handle this modelId
//		* @param String $modelId
//		* @param \ContentHandler &$handler
//		* @return boolean
//		*/
//		public static function onContentHandlerForModelID($modelId, &$handler) {
//			global $wgJsonConfigModels;
//			if (!self::jsonConfigIsStorage()) {
//				return true;
//			}
//
//			self::init();
//			$models = array_replace_recursive(
//				\ExtensionRegistry::getInstance()->getAttribute('JsonConfigModels'),
//				$wgJsonConfigModels
//			);
//			if (array_key_exists($modelId, $models)) {
//				// This is one of our model IDs
//				$handler = new JCContentHandler($modelId);
//				return false;
//			}
//			return true;
//		}
//
//		/**
//		* CustomEditor hook handler
//		* @see https://www.mediawiki.org/wiki/Manual:Hooks/CustomEditor
//		*
//		* @param Article $article
//		* @param User $user
//		* @return boolean
//		*/
//		public static function onCustomEditor($article, $user) {
//			if (!$article || !self::jsonConfigIsStorage()) {
//				return true;
//			}
//			$jct = self::parseTitle($article->getTitle());
//			if (!$jct) {
//				return true;
//			}
//
//			$editor = new \EditPage($article);
//			$editor->contentFormat = JCContentHandler::CONTENT_FORMAT_JSON_PRETTY;
//			$editor->edit();
//
//			return false;
//		}
//
//		/**
//		* Declares JSON as the code editor language for Config: pages.
//		* This hook only runs if the CodeEditor extension is enabled.
//		* @param Title $title
//		* @param String &$lang Page language.
//		* @return boolean
//		*/
//		public static function onCodeEditorGetPageLanguage($title, &$lang) {
//			if (!self::jsonConfigIsStorage()) {
//				return true;
//			}
//
//			// todo/fixme? We should probably add 'json' lang to only those pages that pass parseTitle()
//			$handler = ContentHandler::getForModelID($title->getContentModel());
//			if ($handler->getDefaultFormat() == CONTENT_FORMAT_JSON || self::parseTitle($title)) {
//				$lang = 'json';
//			}
//			return true;
//		}
//
//		/**
//		* Validates that the revised contents are valid JSON.
//		* If not valid, rejects edit with error message.
//		* @param \IContextSource $context
//		* @param JCContent $content
//		* @param \Status $status
//		* @param String $summary Edit summary provided for edit.
//		* @param \User $user
//		* @param boolean $minoredit
//		* @return boolean
//		*/
//		public static function onEditFilterMergedContent(
//			/** @noinspection PhpUnusedParameterInspection */
//			$context, $content, $status, $summary, $user, $minoredit
//		) {
//			if (!self::jsonConfigIsStorage()) {
//				return true;
//			}
//
//			if (is_a($content, 'JsonConfig\JCContent')) {
//				$status->merge($content->getStatus());
//				if (!$status->isGood()) {
//					$status->setResult(false, $status->getValue());
//				}
//			}
//			return true;
//		}
//
//		/**
//		* Override a per-page specific edit page copyright warning
//		*
//		* @param Title $title
//		* @param String[] &$msg
//		*
//		* @return boolean
//		*/
//		public static function onEditPageCopyrightWarning($title, &$msg) {
//			if (self::jsonConfigIsStorage()) {
//				$jct = self::parseTitle($title);
//				if ($jct) {
//					$code = $jct->getConfig()->license;
//					if ($code) {
//						$msg = [ 'jsonconfig-license-copyrightwarning-' . $code ];
//						return false; // Do not allow any other hook handler to override this
//					}
//				}
//			}
//			return true;
//		}
//
//		/**
//		* Display a page-specific edit notice
//		*
//		* @param Title $title
//		* @param int $oldid
//		* @param array &$notices
//		* @return boolean
//		*/
//		public static function onTitleGetEditNotices(Title $title, $oldid, array &$notices) {
//			if (self::jsonConfigIsStorage()) {
//				$jct = self::parseTitle($title);
//				if ($jct) {
//					$code = $jct->getConfig()->license;
//					if ($code) {
//						$noticeText = wfMessage('jsonconfig-license-notice-' . $code)->parse();
//						$notices['jsonconfig'] =
//							wfMessage('jsonconfig-license-notice-box-' . $code)
//								->rawParams($noticeText)
//								->parseAsBlock();
//					}
//				}
//			}
//			return true;
//		}
//
//		/**
//		* Override with per-page specific copyright message
//		*
//		* @param Title $title
//		* @param String $type
//		* @param String &$msg
//		* @param String &$link
//		*
//		* @return boolean
//		*/
//		public static function onSkinCopyrightFooter($title, $type, &$msg, &$link) {
//			if (self::jsonConfigIsStorage()) {
//				$jct = self::parseTitle($title);
//				if ($jct) {
//					$code = $jct->getConfig()->license;
//					if ($code) {
//						$msg = 'jsonconfig-license';
//						$link = Html::element('a', [
//							'href' => wfMessage('jsonconfig-license-url-' . $code)->plain()
//						], wfMessage('jsonconfig-license-name-' . $code)->plain());
//						return false;
//					}
//				}
//			}
//			return true;
//		}
//
//		/**
//		* Adds CSS for pretty-printing configuration on NS_CONFIG pages.
//		* @param \OutputPage &$out
//		* @param \Skin &$skin
//		* @return boolean
//		*/
//		public static function onBeforePageDisplay(
//			/** @noinspection PhpUnusedParameterInspection */ &$out, &$skin
//		) {
//			if (!self::jsonConfigIsStorage()) {
//				return true;
//			}
//
//			$title = $out->getTitle();
//			// todo/fixme? We should probably add ext.jsonConfig style to only those pages
//			// that pass parseTitle()
//			$handler = ContentHandler::getForModelID($title->getContentModel());
//			if ($handler->getDefaultFormat() == CONTENT_FORMAT_JSON ||
//				self::parseTitle($title)
//			) {
//				$out->addModuleStyles('ext.jsonConfig');
//			}
//			return true;
//		}
//
//		public static function onMovePageIsValidMove(
//			Title $oldTitle, Title $newTitle, Status $status
//		) {
//			if (!self::jsonConfigIsStorage()) {
//				return true;
//			}
//
//			$jctOld = self::parseTitle($oldTitle);
//			if ($jctOld) {
//				$jctNew = self::parseTitle($newTitle);
//				if (!$jctNew) {
//					$status->fatal('jsonconfig-move-aborted-ns');
//					return false;
//				} elseif ($jctOld->getConfig()->model !== $jctNew->getConfig()->model) {
//					$status->fatal('jsonconfig-move-aborted-model', $jctOld->getConfig()->model,
//						$jctNew->getConfig()->model);
//					return false;
//				}
//			}
//
//			return true;
//		}
//
//		public static function onAbortMove(
//			/** @noinspection PhpUnusedParameterInspection */
//			Title $title, Title $newTitle, $wgUser, &$err, $reason
//		) {
//			if (!self::jsonConfigIsStorage()) {
//				return true;
//			}
//
//			$status = new \Status();
//			self::onMovePageIsValidMove($title, $newTitle, $status);
//			if (!$status->isOK()) {
//				$err = $status->getHTML();
//				return false;
//			}
//
//			return true;
//		}
//
//		/**
//		* Conditionally load API module 'jsondata' depending on whether or not
//		* this wiki stores any jsonconfig data
//		*
//		* @param ApiModuleManager $moduleManager Module manager instance
//		* @return boolean
//		*/
//		public static function onApiMainModuleManager(ApiModuleManager $moduleManager) {
//			global $wgJsonConfigEnableLuaSupport;
//			if ($wgJsonConfigEnableLuaSupport) {
//				$moduleManager->addModule('jsondata', 'action', 'JsonConfig\\JCDataApi');
//			}
//			return true;
//		}
//
//		public static function onPageContentSaveComplete(
//			/** @noinspection PhpUnusedParameterInspection */
//			\WikiPage $wikiPage, $user, $content, $summary, $isMinor, $isWatch,
//			$section, $flags, $revision, $status, $baseRevId
//		) {
//			return self::onArticleChangeComplete($wikiPage, $content);
//		}
//
//		public static function onArticleDeleteComplete(
//			/** @noinspection PhpUnusedParameterInspection */
//			$article, &$user, $reason, $id, $content, $logEntry
//		) {
//			return self::onArticleChangeComplete($article);
//		}
//
//		public static function onArticleUndelete(
//			/** @noinspection PhpUnusedParameterInspection */
//			$title, $created, $comment, $oldPageId
//		) {
//			return self::onArticleChangeComplete($title);
//		}
//
//		public static function onTitleMoveComplete(
//			/** @noinspection PhpUnusedParameterInspection */
//			$title, $newTitle, $wgUser, $pageid, $redirid, $reason
//		) {
//			return self::onArticleChangeComplete($title) ||
//				self::onArticleChangeComplete($newTitle);
//		}
//
//		/**
//		* Prohibit creation of the pages that are part of our namespaces but have not been explicitly
//		* allowed. Bad capitalization is due to "userCan" hook name
//		* @param Title &$title
//		* @param User &$user
//		* @param String $action
//		* @param null &$result
//		* @return boolean
//		*/
//		public static function onuserCan(
//			/** @noinspection PhpUnusedParameterInspection */
//			&$title, &$user, $action, &$result = null
//		) {
//			if (!self::jsonConfigIsStorage()) {
//				return true;
//			}
//
//			if ($action == 'create' && self::parseTitle($title) == null) {
//				// prohibit creation of the pages for the namespace that we handle,
//				// if the title is not matching declared rules
//				$result = false;
//				return false;
//			}
//			return true;
//		}
//
//		/**
//		* @param Object $value
//		* @param JCContent $content
//		* @return boolean
//		*/
//		private static function onArticleChangeComplete($value, $content = null) {
//			if (!self::jsonConfigIsStorage()) {
//				return true;
//			}
//
//			if ($value && (!$content || is_a($content, 'JsonConfig\JCContent'))) {
//				if (method_exists($value, 'getTitle')) {
//					$value = $value->getTitle();
//				}
//				$jct = self::parseTitle($value);
//				if ($jct && $jct->getConfig()->store) {
//					$store = new JCCache($jct, $content);
//					$store->resetCache();
//
//					// Handle remote site notification
//					$store = $jct->getConfig()->store;
//					if ($store->notifyUrl) {
//						$req =
//							JCUtils::initApiRequestObj($store->notifyUrl, $store->notifyUsername,
//								$store->notifyPassword);
//						if ($req) {
//							$query = [
//								'format' => 'json',
//								'action' => 'jsonconfig',
//								'command' => 'reload',
//								'title' => $jct->getNamespace() . ':' . $jct->getDBkey(),
//							];
//							JCUtils::callApi($req, $query, 'notify remote JsonConfig client');
//						}
//					}
//				}
//			}
//			return true;
//		}
//
//		/**
//		* Quick check if the current wiki will store any configurations.
//		* Faster than doing a full parsing of the $wgJsonConfigs in the JCSingleton::init()
//		* @return boolean
//		*/
//		private static function jsonConfigIsStorage() {
//			static $isStorage = null;
//			if ($isStorage == null) {
//				global $wgJsonConfigs;
//				$isStorage = false;
//				$configs = array_replace_recursive(
//					\ExtensionRegistry::getInstance()->getAttribute('JsonConfigs'),
//					$wgJsonConfigs
//				);
//				foreach ($configs as $jc) {
//					if ((!array_key_exists('isLocal', $jc) || $jc['isLocal']) ||
//						(array_key_exists('store', $jc))
//					) {
//						$isStorage = true;
//						break;
//					}
//				}
//			}
//			return $isStorage;
//		}
	public static String Singleton_Id = "JCSingleton";
}
