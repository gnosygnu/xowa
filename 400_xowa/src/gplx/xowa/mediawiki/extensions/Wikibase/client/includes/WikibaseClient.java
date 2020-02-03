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
package gplx.xowa.mediawiki.extensions.Wikibase.client.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.extensions.*; import gplx.xowa.mediawiki.extensions.Wikibase.*; import gplx.xowa.mediawiki.extensions.Wikibase.client.*;
import gplx.xowa.mediawiki.*;
import gplx.xowa.mediawiki.extensions.Wikibase.lib.includes.Store.*;
public class WikibaseClient {
	private Wbase_repo_linker repoLinker;
	public WikibaseClient(Wbase_settings settings) {
		this.repoLinker = new Wbase_repo_linker
			(settings.getSetting(Wbase_settings.Setting_repoUrl)
			, settings.getSetting(Wbase_settings.Setting_repoArticlePath)
			, settings.getSetting(Wbase_settings.Setting_repoScriptPath)
			);
	}
	public Wbase_repo_linker RepoLinker() {return repoLinker;}

	private static WikibaseClient defaultInstance;
	public static WikibaseClient getDefaultInstance() {
		if (defaultInstance == null) {
			defaultInstance = new WikibaseClient(Wbase_settings.New_dflt());
		}
		return defaultInstance;
	}

//		/**
//		* @var SettingsArray
//		*/
//		private settings;
//
//		/**
//		* @var SiteLookup
//		*/
//		private siteLookup;
//
//		/**
//		* @var WikibaseServices
//		*/
//		private wikibaseServices;
//
//		/**
//		* @var PropertyDataTypeLookup|null
//		*/
//		private propertyDataTypeLookup = null;
//
//		/**
//		* @var DataTypeFactory|null
//		*/
//		private dataTypeFactory = null;
//
//		/**
//		* @var Deserializer|null
//		*/
//		private entityDeserializer = null;
//
//		/**
//		* @var Serializer|null
//		*/
//		private compactEntitySerializer = null;
//
//		/**
//		* @var EntityIdParser|null
//		*/
//		private entityIdParser = null;
//
//		/**
//		* @var EntityIdComposer|null
//		*/
//		private entityIdComposer = null;
//
//		/**
//		* @var ClientStore|null
//		*/
//		private store = null;
//
//		/**
//		* @var Site|null
//		*/
//		private site = null;
//
//		/**
//		* @var String|null
//		*/
//		private siteGroup = null;
//
//		/**
//		* @var OutputFormatSnakFormatterFactory|null
//		*/
//		private snakFormatterFactory = null;
//
//		/**
//		* @var OutputFormatValueFormatterFactory|null
//		*/
//		private valueFormatterFactory = null;
//
//		/**
//		* @var LangLinkHandler|null
//		*/
//		private langLinkHandler = null;
//
//		/**
//		* @var ClientParserOutputDataUpdater|null
//		*/
//		private parserOutputDataUpdater = null;
//
//		/**
//		* @var NamespaceChecker|null
//		*/
//		private namespaceChecker = null;
//
//		/**
//		* @var RestrictedEntityLookup|null
//		*/
//		private restrictedEntityLookup = null;
//
//		/**
//		* @var DataTypeDefinitions
//		*/
//		private dataTypeDefinitions;
//
//		/**
//		* @var EntityTypeDefinitions
//		*/
//		private entityTypeDefinitions;
//
//		/**
//		* @var RepositoryDefinitions
//		*/
//		private repositoryDefinitions;
//
//		/**
//		* @var TermLookup|null
//		*/
//		private termLookup = null;
//
//		/**
//		* @var TermBuffer|null
//		*/
//		private termBuffer = null;
//
//		/**
//		* @var PrefetchingTermLookup|null
//		*/
//		private prefetchingTermLookup = null;

	/**
	* @var PropertyOrderProvider|null
	*/
	private XomwPropertyOrderProvider propertyOrderProvider = null;
//
//		/**
//		* @var SidebarLinkBadgeDisplay|null
//		*/
//		private sidebarLinkBadgeDisplay = null;
//
//		/**
//		* @var WikibaseValueFormatterBuilders|null
//		*/
//		private valueFormatterBuilders = null;
//
//		/**
//		* @var WikibaseContentLanguages|null
//		*/
//		private wikibaseContentLanguages = null;
//
//		/**
//		* @warning This is for use with bootstrap code in WikibaseClient.datatypes.php only!
//		* Program logic should use WikibaseClient::getSnakFormatterFactory() instead!
//		*
//		* @return WikibaseValueFormatterBuilders
//		*/
//		public static function getDefaultValueFormatterBuilders() {
//			return self::getDefaultInstance().newWikibaseValueFormatterBuilders();
//		}
//
//		/**
//		* Returns a low level factory Object for creating formatters for well known data types.
//		*
//		* @warning This is for use with getDefaultValueFormatterBuilders() during bootstrap only!
//		* Program logic should use WikibaseClient::getSnakFormatterFactory() instead!
//		*
//		* @return WikibaseValueFormatterBuilders
//		*/
//		private function newWikibaseValueFormatterBuilders() {
//			if (this.valueFormatterBuilders == null) {
//				entityTitleLookup = new ClientSiteLinkTitleLookup(
//					this.getStore().getSiteLinkLookup(),
//					this.settings.getSetting('siteGlobalID')
//				);
//
//				this.valueFormatterBuilders = new WikibaseValueFormatterBuilders(
//					this.getContentLanguage(),
//					new FormatterLabelDescriptionLookupFactory(this.getTermLookup()),
//					new LanguageNameLookup(this.getUserLanguage().getCode()),
//					this.getRepoItemUriParser(),
//					this.settings.getSetting('geoShapeStorageBaseUrl'),
//					this.settings.getSetting('tabularDataStorageBaseUrl'),
//					this.getFormatterCache(),
//					this.settings.getSetting('sharedCacheDuration'),
//					this.getEntityLookup(),
//					this.getStore().getEntityRevisionLookup(),
//					entityTitleLookup
//				);
//			}
//
//			return this.valueFormatterBuilders;
//		}
//
//		/**
//		* @warning This is for use with bootstrap code in WikibaseClient.datatypes.php only!
//		* Program logic should use WikibaseClient::getSnakFormatterFactory() instead!
//		*
//		* @return WikibaseSnakFormatterBuilders
//		*/
//		public static function getDefaultSnakFormatterBuilders() {
//			static builders;
//
//			if (builders == null) {
//				builders = self::getDefaultInstance().newWikibaseSnakFormatterBuilders(
//					self::getDefaultValueFormatterBuilders()
//				);
//			}
//
//			return builders;
//		}
//
//		/**
//		* Returns a low level factory Object for creating formatters for well known data types.
//		*
//		* @warning This is for use with getDefaultValueFormatterBuilders() during bootstrap only!
//		* Program logic should use WikibaseClient::getSnakFormatterFactory() instead!
//		*
//		* @param WikibaseValueFormatterBuilders valueFormatterBuilders
//		*
//		* @return WikibaseSnakFormatterBuilders
//		*/
//		private function newWikibaseSnakFormatterBuilders(WikibaseValueFormatterBuilders valueFormatterBuilders) {
//			return new WikibaseSnakFormatterBuilders(
//				valueFormatterBuilders,
//				this.getStore().getPropertyInfoLookup(),
//				this.getPropertyDataTypeLookup(),
//				this.getDataTypeFactory()
//			);
//		}
//
//		public function __construct(
//			SettingsArray settings,
//			DataTypeDefinitions dataTypeDefinitions,
//			EntityTypeDefinitions entityTypeDefinitions,
//			RepositoryDefinitions repositoryDefinitions,
//			SiteLookup siteLookup
//		) {
//			this.settings = settings;
//			this.dataTypeDefinitions = dataTypeDefinitions;
//			this.entityTypeDefinitions = entityTypeDefinitions;
//			this.repositoryDefinitions = repositoryDefinitions;
//			this.siteLookup = siteLookup;
//		}
//
//		/**
//		* @return DataTypeFactory
//		*/
//		public function getDataTypeFactory() {
//			if (this.dataTypeFactory == null) {
//				this.dataTypeFactory = new DataTypeFactory(this.dataTypeDefinitions.getValueTypes());
//			}
//
//			return this.dataTypeFactory;
//		}
//
//		/**
//		* @return EntityIdParser
//		*/
//		public function getEntityIdParser() {
//			if (this.entityIdParser == null) {
//				this.entityIdParser = new DispatchingEntityIdParser(
//					this.entityTypeDefinitions.getEntityIdBuilders()
//				);
//			}
//
//			return this.entityIdParser;
//		}
//
//		/**
//		* @return EntityIdComposer
//		*/
//		public function getEntityIdComposer() {
//			if (this.entityIdComposer == null) {
//				this.entityIdComposer = new EntityIdComposer(
//					this.entityTypeDefinitions.getEntityIdComposers()
//				);
//			}
//
//			return this.entityIdComposer;
//		}
//
//		/**
//		* @return WikibaseServices
//		*/
//		public function getWikibaseServices() {
//			if (this.wikibaseServices == null) {
//				this.wikibaseServices = new MultipleRepositoryAwareWikibaseServices(
//					this.getEntityIdParser(),
//					this.getEntityIdComposer(),
//					this.repositoryDefinitions,
//					this.entityTypeDefinitions,
//					this.getDataAccessSettings(),
//					this.getMultiRepositoryServiceWiring(),
//					this.getPerRepositoryServiceWiring(),
//					MediaWikiServices::getInstance().getNameTableStoreFactory()
//				);
//			}
//
//			return this.wikibaseServices;
//		}
//
//		private function getDataAccessSettings() {
//			return new DataAccessSettings(
//				this.settings.getSetting('maxSerializedEntitySize'),
//				this.settings.getSetting('useTermsTableSearchFields'),
//				this.settings.getSetting('forceWriteTermsTableSearchFields')
//			);
//		}
//
//		private function getMultiRepositoryServiceWiring() {
//			global wgWikibaseMultiRepositoryServiceWiringFiles;
//
//			wiring = [];
//			foreach (wgWikibaseMultiRepositoryServiceWiringFiles as file) {
//				wiring = array_merge(
//					wiring,
//					require file
//				);
//			}
//			return wiring;
//		}
//
//		private function getPerRepositoryServiceWiring() {
//			global wgWikibasePerRepositoryServiceWiringFiles;
//
//			wiring = [];
//			foreach (wgWikibasePerRepositoryServiceWiringFiles as file) {
//				wiring = array_merge(
//					wiring,
//					require file
//				);
//			}
//			return wiring;
//		}
//
//		/**
//		* @return EntityLookup
//		*/
//		private function getEntityLookup() {
//			return this.getStore().getEntityLookup();
//		}
//
//		/**
//		* @return array[]
//		*/
//		private static function getDefaultEntityTypes() {
//			return require __DIR__ . '/../../lib/WikibaseLib.entitytypes.php';
//		}
//
//		/**
//		* @return TermBuffer
//		*/
//		public function getTermBuffer() {
//			if (!this.termBuffer) {
//				this.termBuffer = this.getPrefetchingTermLookup();
//			}
//
//			return this.termBuffer;
//		}
//
//		/**
//		* @return TermLookup
//		*/
//		public function getTermLookup() {
//			if (!this.termLookup) {
//				this.termLookup = this.getPrefetchingTermLookup();
//			}
//
//			return this.termLookup;
//		}
//
//		/**
//		* @return PrefetchingTermLookup
//		*/
//		private function getPrefetchingTermLookup() {
//			if (!this.prefetchingTermLookup) {
//				// TODO: This should not assume the TermBuffer instance to be a PrefetchingTermLookup
//				this.prefetchingTermLookup = this.getWikibaseServices().getTermBuffer();
//			}
//
//			return this.prefetchingTermLookup;
//		}
//
//		/**
//		* @param String displayLanguageCode
//		*
//		* XXX: This is not used by client itself, but is used by ArticlePlaceholder!
//		*
//		* @return TermSearchInteractor
//		*/
//		public function newTermSearchInteractor(displayLanguageCode) {
//			return this.getWikibaseServices().getTermSearchInteractorFactory()
//				.newInteractor(displayLanguageCode);
//		}
//
//		/**
//		* @return PropertyDataTypeLookup
//		*/
//		public function getPropertyDataTypeLookup() {
//			if (this.propertyDataTypeLookup == null) {
//				infoLookup = this.getStore().getPropertyInfoLookup();
//				retrievingLookup = new EntityRetrievingDataTypeLookup(this.getEntityLookup());
//				this.propertyDataTypeLookup = new PropertyInfoDataTypeLookup(infoLookup, retrievingLookup);
//			}
//
//			return this.propertyDataTypeLookup;
//		}
//
//		/**
//		* @return StringNormalizer
//		*/
//		public function getStringNormalizer() {
//			return this.getWikibaseServices().getStringNormalizer();
//		}
//
//		/**
//		* @return RepoLinker
//		*/
//		public function newRepoLinker() {
//			return new RepoLinker(
//				this.settings.getSetting('repoUrl'),
//				this.getRepositoryDefinitions().getConceptBaseUris(),
//				this.settings.getSetting('repoArticlePath'),
//				this.settings.getSetting('repoScriptPath')
//			);
//		}
//
//		/**
//		* @return LanguageFallbackChainFactory
//		*/
//		public function getLanguageFallbackChainFactory() {
//			return this.getWikibaseServices().getLanguageFallbackChainFactory();
//		}
//
//		/**
//		* @return LanguageFallbackLabelDescriptionLookupFactory
//		*/
//		public function getLanguageFallbackLabelDescriptionLookupFactory() {
//			return new LanguageFallbackLabelDescriptionLookupFactory(
//				this.getLanguageFallbackChainFactory(),
//				this.getTermLookup(),
//				this.getTermBuffer()
//			);
//		}
//
//		/**
//		* Returns an instance of the default store.
//		*
//		* @return ClientStore
//		*/
//		public function getStore() {
//			if (this.store == null) {
//				this.store = new DirectSqlStore(
//					this.getEntityChangeFactory(),
//					this.getEntityIdParser(),
//					this.getEntityIdComposer(),
//					this.getEntityNamespaceLookup(),
//					this.getWikibaseServices(),
//					this.getSettings(),
//					this.getRepositoryDefinitions().getDatabaseNames()[''],
//					this.getContentLanguage().getCode(),
//					LoggerFactory::getInstance('PageRandomLookup')
//				);
//			}
//
//			return this.store;
//		}
//
//		/**
//		* Overrides the default store to be used in the client app context.
//		* This is intended for use by test cases.
//		*
//		* @param ClientStore|null store
//		*
//		* @throws LogicException If MW_PHPUNIT_TEST is not defined, to avoid this
//		* method being abused in production code.
//		*/
//		public function overrideStore(ClientStore store = null) {
//			if (!defined('MW_PHPUNIT_TEST')) {
//				throw new LogicException('Overriding the store instance is only supported in test mode');
//			}
//
//			this.store = store;
//		}
//
//		/**
//		* Overrides the TermLookup to be used.
//		* This is intended for use by test cases.
//		*
//		* @param TermLookup|null lookup
//		*
//		* @throws LogicException If MW_PHPUNIT_TEST is not defined, to avoid this
//		* method being abused in production code.
//		*/
//		public function overrideTermLookup(TermLookup lookup = null) {
//			if (!defined('MW_PHPUNIT_TEST')) {
//				throw new LogicException('Overriding TermLookup is only supported in test mode');
//			}
//
//			this.termLookup = lookup;
//		}
//
//		/**
//		* @throws MWException when called to early
//		* @return Language
//		*/
//		public function getContentLanguage() {
//			global wgContLang;
//
//			// TODO: define a LanguageProvider service instead of using a global directly.
//			// NOTE: we cannot inject wgContLang in the constructor, because it may still be null
//			// when WikibaseClient is initialized. In particular, the language Object may not yet
//			// be there when the SetupAfterCache hook is run during bootstrapping.
//
//			if (!wgContLang) {
//				throw new MWException('Premature access: wgContLang is not yet initialized!');
//			}
//
//			StubObject::unstub(wgContLang);
//			return wgContLang;
//		}
//
//		/**
//		* @throws MWException when called to early
//		* @return Language
//		*/
//		private function getUserLanguage() {
//			global wgLang;
//
//			// TODO: define a LanguageProvider service instead of using a global directly.
//			// NOTE: we cannot inject wgLang in the constructor, because it may still be null
//			// when WikibaseClient is initialized. In particular, the language Object may not yet
//			// be there when the SetupAfterCache hook is run during bootstrapping.
//
//			if (!wgLang) {
//				throw new MWException('Premature access: wgLang is not yet initialized!');
//			}
//
//			StubObject::unstub(wgLang);
//			return wgLang;
//		}
//
//		/**
//		* @return SettingsArray
//		*/
//		public function getSettings() {
//			return this.settings;
//		}
//
//		/**
//		* Returns a new instance constructed from global settings.
//		* IMPORTANT: Use only when it is not feasible to inject an instance properly.
//		*
//		* @throws MWException
//		* @return self
//		*/
//		private static function newInstance() {
//			global wgWBClientDataTypes;
//
//			if (!is_array(wgWBClientDataTypes)) {
//				throw new MWException('wgWBClientDataTypes must be array. '
//					. 'Maybe you forgot to require WikibaseClient.php in your LocalSettings.php?');
//			}
//
//			dataTypeDefinitions = wgWBClientDataTypes;
//			Hooks::run('WikibaseClientDataTypes', [ &dataTypeDefinitions ]);
//
//			entityTypeDefinitionsArray = self::getDefaultEntityTypes();
//			Hooks::run('WikibaseClientEntityTypes', [ &entityTypeDefinitionsArray ]);
//
//			settings = WikibaseSettings::getClientSettings();
//
//			entityTypeDefinitions = new EntityTypeDefinitions(entityTypeDefinitionsArray);
//
//			return new self(
//				settings,
//				new DataTypeDefinitions(
//					dataTypeDefinitions,
//					settings.getSetting('disabledDataTypes')
//				),
//				entityTypeDefinitions,
//				self::getRepositoryDefinitionsFromSettings(settings, entityTypeDefinitions),
//				MediaWikiServices::getInstance().getSiteLookup()
//			);
//		}
//
//		/**
//		* @param SettingsArray settings
//		* @param EntityTypeDefinitions entityTypeDefinitions
//		*
//		* @return RepositoryDefinitions
//		*/
//		private static function getRepositoryDefinitionsFromSettings(SettingsArray settings, EntityTypeDefinitions entityTypeDefinitions) {
//			definitions = [];
//
//			// Backwards compatibility: if the old "foreignRepositories" settings is there,
//			// use its values.
//			repoSettingsArray = settings.hasSetting('foreignRepositories')
//				? settings.getSetting('foreignRepositories')
//				: settings.getSetting('repositories');
//
//			// Backwards compatibility: if settings of the "local" repository
//			// are not defined in the "repositories" settings but with individual settings,
//			// fallback to old single-repo settings
//			if (settings.hasSetting('repoDatabase')
//				&& settings.hasSetting('entityNamespaces')
//				&& settings.hasSetting('repoConceptBaseUri')
//			) {
//				definitions = [ '' => [
//					'database' => settings.getSetting('repoDatabase'),
//					'super-uri' => settings.getSetting('repoConceptBaseUri'),
//					'prefix-mapping' => [ '' => '' ],
//					'entity-namespaces' => settings.getSetting('entityNamespaces'),
//				] ];
//				unset(repoSettingsArray['']);
//			}
//
//			foreach (repoSettingsArray as repository => repositorySettings) {
//				definitions[repository] = [
//					'database' => repositorySettings['repoDatabase'],
//					'super-uri' => repositorySettings['baseUri'],
//					'entity-namespaces' => repositorySettings['entityNamespaces'],
//					'prefix-mapping' => repositorySettings['prefixMapping'],
//				];
//			}
//
//			return new RepositoryDefinitions(definitions, entityTypeDefinitions);
//		}
//
//		/**
//		* IMPORTANT: Use only when it is not feasible to inject an instance properly.
//		*
//		* @param String reset Flag: Pass "reset" to reset the default instance
//		*
//		* @return self
//		*/
//		public static function getDefaultInstance(reset = 'noreset') {
//			static instance = null;
//
//			if (instance == null || reset == 'reset') {
//				instance = self::newInstance();
//			}
//
//			return instance;
//		}
//
//		/**
//		* Returns the this client wiki's site Object.
//		*
//		* This is taken from the siteGlobalID setting, which defaults
//		* to the wiki's database name.
//		*
//		* If the configured site ID is not found in the sites table, a
//		* new Site Object is constructed from the configured ID.
//		*
//		* @throws MWException
//		* @return Site
//		*/
//		public function getSite() {
//			if (this.site == null) {
//				globalId = this.settings.getSetting('siteGlobalID');
//				localId = this.settings.getSetting('siteLocalID');
//
//				this.site = this.siteLookup.getSite(globalId);
//
//				if (!this.site) {
//					wfDebugLog(__CLASS__, __FUNCTION__ . ": Unable to resolve site ID '{globalId}'!");
//
//					this.site = new MediaWikiSite();
//					this.site.setGlobalId(globalId);
//					this.site.addLocalId(Site::ID_INTERWIKI, localId);
//					this.site.addLocalId(Site::ID_EQUIVALENT, localId);
//				}
//
//				if (!in_array(localId, this.site.getLocalIds())) {
//					wfDebugLog(__CLASS__, __FUNCTION__
//						. ": The configured local id localId does not match any local ID of site globalId: "
//						. var_export(this.site.getLocalIds(), true));
//				}
//			}
//
//			return this.site;
//		}
//
//		/**
//		* Returns the site group ID for the group to be used for language links.
//		* This is typically the group the client wiki itself belongs to, but
//		* can be configured to be otherwise using the languageLinkSiteGroup setting.
//		*
//		* @return String
//		*/
//		public function getLangLinkSiteGroup() {
//			group = this.settings.getSetting('languageLinkSiteGroup');
//
//			if (group == null) {
//				group = this.getSiteGroup();
//			}
//
//			return group;
//		}
//
//		/**
//		* Gets the site group ID from setting, which if not set then does
//		* lookup in site store.
//		*
//		* @return String
//		*/
//		private function newSiteGroup() {
//			siteGroup = this.settings.getSetting('siteGroup');
//
//			if (!siteGroup) {
//				siteId = this.settings.getSetting('siteGlobalID');
//
//				site = this.siteLookup.getSite(siteId);
//
//				if (!site) {
//					return true;
//				}
//
//				siteGroup = site.getGroup();
//			}
//
//			return siteGroup;
//		}
//
//		/**
//		* Get site group ID
//		*
//		* @return String
//		*/
//		public function getSiteGroup() {
//			if (this.siteGroup == null) {
//				this.siteGroup = this.newSiteGroup();
//			}
//
//			return this.siteGroup;
//		}
//
//		/**
//		* Returns a OutputFormatSnakFormatterFactory the provides SnakFormatters
//		* for different output formats.
//		*
//		* @return OutputFormatSnakFormatterFactory
//		*/
//		private function getSnakFormatterFactory() {
//			if (this.snakFormatterFactory == null) {
//				this.snakFormatterFactory = new OutputFormatSnakFormatterFactory(
//					this.dataTypeDefinitions.getSnakFormatterFactoryCallbacks(),
//					this.getValueFormatterFactory(),
//					this.getPropertyDataTypeLookup(),
//					this.getDataTypeFactory()
//				);
//			}
//
//			return this.snakFormatterFactory;
//		}
//
//		/**
//		* Returns a OutputFormatValueFormatterFactory the provides ValueFormatters
//		* for different output formats.
//		*
//		* @return OutputFormatValueFormatterFactory
//		*/
//		private function getValueFormatterFactory() {
//			if (this.valueFormatterFactory == null) {
//				this.valueFormatterFactory = new OutputFormatValueFormatterFactory(
//					this.dataTypeDefinitions.getFormatterFactoryCallbacks(DataTypeDefinitions::PREFIXED_MODE),
//					this.getContentLanguage(),
//					this.getLanguageFallbackChainFactory()
//				);
//			}
//
//			return this.valueFormatterFactory;
//		}
//
//		/**
//		* @return EntityIdParser
//		*/
//		private function getRepoItemUriParser() {
//			// B/C compatibility, should be removed soon
//			// TODO: Move to check repo that has item entity not the default repo
//			return new SuffixEntityIdParser(
//				this.getRepositoryDefinitions().getConceptBaseUris()[''],
//				new ItemIdParser()
//			);
//		}
//
//		/**
//		* @return NamespaceChecker
//		*/
//		public function getNamespaceChecker() {
//			if (this.namespaceChecker == null) {
//				this.namespaceChecker = new NamespaceChecker(
//					this.settings.getSetting('excludeNamespaces'),
//					this.settings.getSetting('namespaces')
//				);
//			}
//
//			return this.namespaceChecker;
//		}
//
//		/**
//		* @return LangLinkHandler
//		*/
//		public function getLangLinkHandler() {
//			if (this.langLinkHandler == null) {
//				this.langLinkHandler = new LangLinkHandler(
//					this.getLanguageLinkBadgeDisplay(),
//					this.getNamespaceChecker(),
//					this.getStore().getSiteLinkLookup(),
//					this.getStore().getEntityLookup(),
//					this.siteLookup,
//					this.settings.getSetting('siteGlobalID'),
//					this.getLangLinkSiteGroup()
//				);
//			}
//
//			return this.langLinkHandler;
//		}
//
//		/**
//		* @return ClientParserOutputDataUpdater
//		*/
//		public function getParserOutputDataUpdater() {
//			if (this.parserOutputDataUpdater == null) {
//				this.parserOutputDataUpdater = new ClientParserOutputDataUpdater(
//					this.getOtherProjectsSidebarGeneratorFactory(),
//					this.getStore().getSiteLinkLookup(),
//					this.getStore().getEntityLookup(),
//					this.settings.getSetting('siteGlobalID')
//				);
//			}
//
//			return this.parserOutputDataUpdater;
//		}
//
//		/**
//		* @return SidebarLinkBadgeDisplay
//		*/
//		public function getSidebarLinkBadgeDisplay() {
//			if (this.sidebarLinkBadgeDisplay == null) {
//				labelDescriptionLookupFactory = this.getLanguageFallbackLabelDescriptionLookupFactory();
//				badgeClassNames = this.settings.getSetting('badgeClassNames');
//				lang = this.getUserLanguage();
//
//				this.sidebarLinkBadgeDisplay = new SidebarLinkBadgeDisplay(
//					labelDescriptionLookupFactory.newLabelDescriptionLookup(lang),
//					is_array(badgeClassNames) ? badgeClassNames : [],
//					lang
//				);
//			}
//
//			return this.sidebarLinkBadgeDisplay;
//		}
//
//		/**
//		* @return LanguageLinkBadgeDisplay
//		*/
//		public function getLanguageLinkBadgeDisplay() {
//			return new LanguageLinkBadgeDisplay(
//				this.getSidebarLinkBadgeDisplay()
//			);
//		}
//
//		/**
//		* @return DeserializerFactory A factory with knowledge about items, properties, and the
//		*  elements they are made of, but no other entity types.
//		*/
//		public function getBaseDataModelDeserializerFactory() {
//			return new DeserializerFactory(
//				this.getDataValueDeserializer(),
//				this.getEntityIdParser()
//			);
//		}
//
//		/**
//		* @return InternalDeserializerFactory
//		*/
//		private function getInternalFormatDeserializerFactory() {
//			return new InternalDeserializerFactory(
//				this.getDataValueDeserializer(),
//				this.getEntityIdParser(),
//				this.getAllTypesEntityDeserializer()
//			);
//		}
//
//		/**
//		* @return DispatchingDeserializer
//		*/
//		private function getAllTypesEntityDeserializer() {
//			if (this.entityDeserializer == null) {
//				deserializerFactoryCallbacks = this.getEntityDeserializerFactoryCallbacks();
//				baseDeserializerFactory = this.getBaseDataModelDeserializerFactory();
//				deserializers = [];
//
//				foreach (deserializerFactoryCallbacks as callback) {
//					deserializers[] = call_user_func(callback, baseDeserializerFactory);
//				}
//
//				this.entityDeserializer = new DispatchingDeserializer(deserializers);
//			}
//
//			return this.entityDeserializer;
//		}
//
//		/**
//		* Returns a deserializer to deserialize statements in both current and legacy serialization.
//		*
//		* @return Deserializer
//		*/
//		public function getInternalFormatStatementDeserializer() {
//			return this.getInternalFormatDeserializerFactory().newStatementDeserializer();
//		}
//
//		/**
//		* @return callable[]
//		*/
//		public function getEntityDeserializerFactoryCallbacks() {
//			return this.entityTypeDefinitions.getDeserializerFactoryCallbacks();
//		}
//
//		/**
//		* Returns a SerializerFactory creating serializers that generate the most compact serialization.
//		* A factory returned has knowledge about items, properties, and the elements they are made of,
//		* but no other entity types.
//		*
//		* @return SerializerFactory
//		*/
//		public function getCompactBaseDataModelSerializerFactory() {
//			return this.getWikibaseServices().getCompactBaseDataModelSerializerFactory();
//		}
//
//		/**
//		* Returns an entity serializer that generates the most compact serialization.
//		*
//		* @return Serializer
//		*/
//		public function getCompactEntitySerializer() {
//			return this.getWikibaseServices().getCompactEntitySerializer();
//		}
//
//		/**
//		* @return DataValueDeserializer
//		*/
//		private function getDataValueDeserializer() {
//			return new DataValueDeserializer([
//				'String' => StringValue::class,
//				'unknown' => UnknownValue::class,
//				'globecoordinate' => GlobeCoordinateValue::class,
//				'monolingualtext' => MonolingualTextValue::class,
//				'quantity' => QuantityValue::class,
//				'time' => TimeValue::class,
//				'wikibase-entityid' => function (value) {
//					return isset(value['id'])
//						? new EntityIdValue(this.getEntityIdParser().parse(value['id']))
//						: EntityIdValue::newFromArray(value);
//				},
//			]);
//		}
//
//		/**
//		* @return OtherProjectsSidebarGeneratorFactory
//		*/
//		public function getOtherProjectsSidebarGeneratorFactory() {
//			return new OtherProjectsSidebarGeneratorFactory(
//				this.settings,
//				this.getStore().getSiteLinkLookup(),
//				this.siteLookup,
//				this.getStore().getEntityLookup(),
//				this.getSidebarLinkBadgeDisplay()
//			);
//		}
//
//		/**
//		* @return EntityChangeFactory
//		*/
//		public function getEntityChangeFactory() {
//			//TODO: take this from a setting or registry.
//			changeClasses = [
//				Item::ENTITY_TYPE => ItemChange::class,
//				// Other types of entities will use EntityChange
//			];
//
//			return new EntityChangeFactory(
//				this.getEntityDiffer(),
//				this.getEntityIdParser(),
//				changeClasses
//			);
//		}
//
//		/**
//		* @return EntityDiffer
//		*/
//		private function getEntityDiffer() {
//			entityDiffer = new EntityDiffer();
//			foreach (this.entityTypeDefinitions.getEntityDifferStrategyBuilders() as builder) {
//				entityDiffer.registerEntityDifferStrategy(call_user_func(builder));
//			}
//			return entityDiffer;
//		}
//
//		/**
//		* @return ParserFunctionRegistrant
//		*/
//		public function getParserFunctionRegistrant() {
//			return new ParserFunctionRegistrant(
//				this.settings.getSetting('allowDataTransclusion'),
//				this.settings.getSetting('allowLocalShortDesc')
//			);
//		}
//
//		/**
//		* @return StatementGroupRendererFactory
//		*/
//		private function getStatementGroupRendererFactory() {
//			return new StatementGroupRendererFactory(
//				this.getStore().getPropertyLabelResolver(),
//				new SnaksFinder(),
//				this.getRestrictedEntityLookup(),
//				this.getDataAccessSnakFormatterFactory(),
//				this.settings.getSetting('allowDataAccessInUserLanguage')
//			);
//		}
//
//		/**
//		* @return DataAccessSnakFormatterFactory
//		*/
//		public function getDataAccessSnakFormatterFactory() {
//			return new DataAccessSnakFormatterFactory(
//				this.getLanguageFallbackChainFactory(),
//				this.getSnakFormatterFactory(),
//				this.getPropertyDataTypeLookup(),
//				this.getRepoItemUriParser(),
//				this.getLanguageFallbackLabelDescriptionLookupFactory(),
//				this.settings.getSetting('allowDataAccessInUserLanguage')
//			);
//		}
//
//		/**
//		* @return Runner
//		*/
//		public function getPropertyParserFunctionRunner() {
//			return new Runner(
//				this.getStatementGroupRendererFactory(),
//				this.getStore().getSiteLinkLookup(),
//				this.getEntityIdParser(),
//				this.getRestrictedEntityLookup(),
//				this.settings.getSetting('siteGlobalID'),
//				this.settings.getSetting('allowArbitraryDataAccess')
//			);
//		}
//
//		/**
//		* @return OtherProjectsSitesProvider
//		*/
//		public function getOtherProjectsSitesProvider() {
//			return new CachingOtherProjectsSitesProvider(
//				new OtherProjectsSitesGenerator(
//					this.siteLookup,
//					this.settings.getSetting('siteGlobalID'),
//					this.settings.getSetting('specialSiteLinkGroups')
//				),
//				// TODO: Make configurable? Should be similar, maybe identical to sharedCacheType and
//				// sharedCacheDuration, but can not reuse these because this here is not shared.
//				wfGetMainCache(),
//				60 * 60
//			);
//		}
//
//		/**
//		* @return AffectedPagesFinder
//		*/
//		private function getAffectedPagesFinder() {
//			return new AffectedPagesFinder(
//				this.getStore().getUsageLookup(),
//				new TitleFactory(),
//				this.settings.getSetting('siteGlobalID'),
//				this.getContentLanguage().getCode()
//			);
//		}
//
//		/**
//		* @return ChangeHandler
//		*/
//		public function getChangeHandler() {
//			pageUpdater = new WikiPageUpdater(
//				JobQueueGroup::singleton(),
//				this.getRecentChangeFactory(),
//				MediaWikiServices::getInstance().getDBLoadBalancerFactory(),
//				this.getStore().getRecentChangesDuplicateDetector(),
//				MediaWikiServices::getInstance().getStatsdDataFactory()
//			);
//
//			pageUpdater.setPurgeCacheBatchSize(this.settings.getSetting('purgeCacheBatchSize'));
//			pageUpdater.setRecentChangesBatchSize(this.settings.getSetting('recentChangesBatchSize'));
//
//			changeListTransformer = new ChangeRunCoalescer(
//				this.getStore().getEntityRevisionLookup(),
//				this.getEntityChangeFactory(),
//				this.settings.getSetting('siteGlobalID')
//			);
//
//			return new ChangeHandler(
//				this.getAffectedPagesFinder(),
//				new TitleFactory(),
//				pageUpdater,
//				changeListTransformer,
//				this.siteLookup,
//				this.settings.getSetting('injectRecentChanges')
//			);
//		}
//
//		/**
//		* @return RecentChangeFactory
//		*/
//		public function getRecentChangeFactory() {
//			repoSite = this.siteLookup.getSite(
//				this.getRepositoryDefinitions().getDatabaseNames()['']
//			);
//			interwikiPrefixes = (repoSite !== null) ? repoSite.getInterwikiIds() : [];
//			interwikiPrefix = (interwikiPrefixes !== []) ? interwikiPrefixes[0] : null;
//
//			return new RecentChangeFactory(
//				this.getContentLanguage(),
//				new SiteLinkCommentCreator(
//					this.getContentLanguage(),
//					this.siteLookup,
//					this.settings.getSetting('siteGlobalID')
//				),
//				(new CentralIdLookupFactory()).getCentralIdLookup(),
//				(interwikiPrefix !== null) ?
//					new ExternalUserNames(interwikiPrefix, false) : null
//			);
//		}
//
//		public function getWikibaseContentLanguages() {
//			if (this.wikibaseContentLanguages == null) {
//				this.wikibaseContentLanguages = WikibaseContentLanguages::getDefaultInstance();
//			}
//
//			return this.wikibaseContentLanguages;
//		}
//
//		/**
//		* Get a ContentLanguages Object holding the languages available for labels, descriptions and aliases.
//		*
//		* @return ContentLanguages
//		*/
//		public function getTermsLanguages() {
//			return this.getWikibaseContentLanguages().getContentLanguages('term');
//		}
//
//		/**
//		* @return RestrictedEntityLookup
//		*/
//		public function getRestrictedEntityLookup() {
//			if (this.restrictedEntityLookup == null) {
//				disabledEntityTypesEntityLookup = new DisabledEntityTypesEntityLookup(
//					this.getEntityLookup(),
//					this.settings.getSetting('disabledAccessEntityTypes')
//				);
//				this.restrictedEntityLookup = new RestrictedEntityLookup(
//					disabledEntityTypesEntityLookup,
//					this.settings.getSetting('entityAccessLimit')
//				);
//			}
//
//			return this.restrictedEntityLookup;
//		}

	/**
	* @return PropertyOrderProvider
	*/
	public XomwPropertyOrderProvider getPropertyOrderProvider() {
		if (this.propertyOrderProvider == null) {
//				title = Title::newFromText('MediaWiki:Wikibase-SortedProperties');
//				innerProvider = new WikiPagePropertyOrderProvider(title);
//
//				url = this.settings.getSetting('propertyOrderUrl');
//				if (url !== null) {
//					innerProvider = new FallbackPropertyOrderProvider(
//						innerProvider,
//						new HttpUrlPropertyOrderProvider(url, new Http())
//					);
//				}
//
//				this.propertyOrderProvider = new CachingPropertyOrderProvider(
//					innerProvider,
//					wfGetMainCache()
//				);
		}

		return this.propertyOrderProvider;
	}
//
//		/**
//		* @return EntityNamespaceLookup
//		*/
//		public function getEntityNamespaceLookup() {
//			return this.getWikibaseServices().getEntityNamespaceLookup();
//		}
//
//		/**
//		* @param Language language
//		*
//		* @return LanguageFallbackChain
//		*/
//		public function getDataAccessLanguageFallbackChain(Language language) {
//			return this.getLanguageFallbackChainFactory().newFromLanguage(
//				language,
//				LanguageFallbackChainFactory::FALLBACK_ALL
//			);
//		}
//
//		/**
//		* @return RepositoryDefinitions
//		*/
//		public function getRepositoryDefinitions() {
//			return this.repositoryDefinitions;
//		}
//
//		/**
//		* @return CacheInterface
//		*/
//		private function getFormatterCache() {
//			global wgSecretKey;
//
//			cacheType = this.settings.getSetting('sharedCacheType');
//			cacheSecret = hash('sha256', wgSecretKey);
//
//			return new SimpleCacheWithBagOStuff(
//				wfGetCache(cacheType),
//				'wikibase.client.formatter.',
//				cacheSecret
//			);
//		}
}
