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
package gplx.xowa.mediawiki.includes.interwiki; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
class XomwInterwikiLookupAdapter {
//		/**
//		* @var SiteLookup
//		*/
//		private $siteLookup;
//
//		/**
//		* @var Interwiki[]|null associative array mapping interwiki prefixes to Interwiki objects
//		*/
//		private $interwikiMap;
//
//		function __construct(
//			SiteLookup $siteLookup,
//			array $interwikiMap = null
//		) {
//			$this->siteLookup = $siteLookup;
//			$this->interwikiMap = $interwikiMap;
//		}
//
//		/**
//		* See InterwikiLookup::isValidInterwiki
//		* It loads the whole interwiki map.
//		*
//		* @param String $prefix Interwiki prefix to use
//		* @return boolean Whether it exists
//		*/
//		public function isValidInterwiki( $prefix ) {
//
//			return array_key_exists( $prefix, $this->getInterwikiMap() );
//		}
//
//		/**
//		* See InterwikiLookup::fetch
//		* It loads the whole interwiki map.
//		*
//		* @param String $prefix Interwiki prefix to use
//		* @return Interwiki|null|boolean
//		*/
//		public function fetch( $prefix ) {
//			if ( $prefix == '' ) {
//				return null;
//			}
//
//			if ( !$this->isValidInterwiki( $prefix ) ) {
//				return false;
//			}
//
//			return $this->interwikiMap[$prefix];
//		}
//
//		/**
//		* See InterwikiLookup::getAllPrefixes
//		*
//		* @param String|null $local If set, limits output to local/non-local interwikis
//		* @return String[] List of prefixes
//		*/
//		public function getAllPrefixes( $local = null ) {
//			if ( $local === null ) {
//				return array_keys( $this->getInterwikiMap() );
//			}
//			$res = [];
//			foreach ( $this->getInterwikiMap() as $interwikiId => $interwiki ) {
//				if ( $interwiki->isLocal() === $local ) {
//					$res[] = $interwikiId;
//				}
//			}
//			return $res;
//		}
//
//		/**
//		* See InterwikiLookup::invalidateCache
//		*
//		* @param String $prefix
//		*/
//		public function invalidateCache( $prefix ) {
//			if ( !isset( $this->interwikiMap[$prefix] ) ) {
//				return;
//			}
//			$globalId = $this->interwikiMap[$prefix]->getWikiID();
//			unset( $this->interwikiMap[$prefix] );
//
//			// Reload the interwiki
//			$site = $this->siteLookup->getSites()->getSite( $globalId );
//			$interwikis = $this->getSiteInterwikis( $site );
//			$this->interwikiMap = array_merge( $this->interwikiMap, [ $interwikis[$prefix] ] );
//		}
//
//		/**
//		* Load interwiki map to use as cache
//		*/
//		private function loadInterwikiMap() {
//			$interwikiMap = [];
//			$siteList = $this->siteLookup->getSites();
//			foreach ( $siteList as $site ) {
//				$interwikis = $this->getSiteInterwikis( $site );
//				$interwikiMap = array_merge( $interwikiMap, $interwikis );
//			}
//			$this->interwikiMap = $interwikiMap;
//		}
//
//		/**
//		* Get interwikiMap attribute, load if needed.
//		*
//		* @return Interwiki[]
//		*/
//		private function getInterwikiMap() {
//			if ( $this->interwikiMap === null ) {
//				$this->loadInterwikiMap();
//			}
//			return $this->interwikiMap;
//		}
//
//		/**
//		* Load interwikis for the given site
//		*
//		* @param Site $site
//		* @return Interwiki[]
//		*/
//		private function getSiteInterwikis( Site $site ) {
//			$interwikis = [];
//			foreach ( $site->getInterwikiIds() as $interwiki ) {
//				$url = $site->getPageUrl();
//				if ( $site instanceof MediaWikiSite ) {
//					$path = $site->getFileUrl( 'api.php' );
//				} else {
//					$path = '';
//				}
//				$local = $site->getSource() === 'local';
//				// TODO: How to adapt trans?
//				$interwikis[$interwiki] = new Interwiki(
//					$interwiki,
//					$url,
//					$path,
//					$site->getGlobalId(),
//					$local
//				);
//			}
//			return $interwikis;
//		}
}
