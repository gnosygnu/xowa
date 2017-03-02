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
package gplx.xowa.mediawiki.includes.site; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
/**
* Provides a file-based cache of a SiteStore. The sites are stored in
* a json file. (see docs/sitescache.txt regarding format)
*
* The cache can be built with the rebuildSitesCache.php maintenance script,
* and a MediaWiki instance can be setup to use this by setting the
* 'wgSitesCacheFile' configuration to the cache file location.
*
* @since 1.25
*/
public class XomwFileBasedSiteLookup implements XomwSiteLookup {
	/**
	* @var SiteList
	*/
	private XomwSiteList sites = null;

//		/**
//		* @var String
//		*/
//		private $cacheFile;
//
//		/**
//		* @param String $cacheFile
//		*/
//		public function __construct($cacheFile) {
//			this.cacheFile = $cacheFile;
//		}

	/**
	* @since 1.25
	*
	* @return SiteList
	*/
	public XomwSiteList getSites() {
//			if (this.sites === null) {
//				this.sites = this.loadSitesFromCache();
//			}

		return this.sites;
	}

	/**
	* @param String $globalId
	*
	* @since 1.25
	*
	* @return Site|null
	*/
	public XomwSite getSite(byte[] globalId) {
//			$sites = this.getSites();
//
//			return $sites->hasSite($globalId) ? $sites->getSite($globalId) : null;
		return null;
	}

//		/**
//		* @return SiteList
//		*/
//		private function loadSitesFromCache() {
//			$data = this.loadJsonFile();
//
//			$sites = new SiteList();
//
//			// @todo lazy initialize the site objects in the site list (e.g. only when needed to access)
//			foreach ($data['sites'] as $siteArray) {
//				$sites[] = this.newSiteFromArray($siteArray);
//			}
//
//			return $sites;
//		}
//
//		/**
//		* @throws MWException
//		* @return array see docs/sitescache.txt for format of the array.
//		*/
//		private function loadJsonFile() {
//			if (!is_readable(this.cacheFile)) {
//				throw new MWException('SiteList cache file not found.');
//			}
//
//			$contents = file_get_contents(this.cacheFile);
//			$data = json_decode($contents, true);
//
//			if (!is_array($data) || !is_array($data['sites'])
//				|| !array_key_exists('sites', $data)
//			) {
//				throw new MWException('SiteStore json cache data is invalid.');
//			}
//
//			return $data;
//		}
//
//		/**
//		* @param array $data
//		*
//		* @return Site
//		*/
//		private function newSiteFromArray(array $data) {
//			$siteType = array_key_exists('type', $data) ? $data['type'] : Site::TYPE_UNKNOWN;
//			$site = Site::newForType($siteType);
//
//			$site->setGlobalId($data['globalid']);
//			$site->setForward($data['forward']);
//			$site->setGroup($data['group']);
//			$site->setLanguageCode($data['language']);
//			$site->setSource($data['source']);
//			$site->setExtraData($data['data']);
//			$site->setExtraConfig($data['config']);
//
//			foreach ($data['identifiers'] as $identifier) {
//				$site->addLocalId($identifier['type'], $identifier['key']);
//			}
//
//			return $site;
//		}
}
