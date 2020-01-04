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
package gplx.xowa.mediawiki.includes.cache; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
//	/**
//	* This class stores an arbitrary value along with its dependencies.
//	* Users should typically only use DependencyWrapper::getValueFromCache(),
//	* rather than instantiating one of these objects directly.
//	* @ingroup Cache
//	*/
//	class DependencyWrapper {
//		private $value;
//		/** @var CacheDependency[] */
//		private $deps;
//
//		/**
//		* Create an instance.
//		* @param mixed $value The user-supplied value
//		* @param CacheDependency|CacheDependency[] $deps A dependency or dependency
//		*   array. All dependencies must be objects implementing CacheDependency.
//		*/
//		function __construct( $value = false, $deps = [] ) {
//			$this->value = $value;
//
//			if ( !is_array( $deps ) ) {
//				$deps = [ $deps ];
//			}
//
//			$this->deps = $deps;
//		}
//
//		/**
//		* Returns true if any of the dependencies have expired
//		*
//		* @return boolean
//		*/
//		function isExpired() {
//			foreach ( $this->deps as $dep ) {
//				if ( $dep->isExpired() ) {
//					return true;
//				}
//			}
//
//			return false;
//		}
//
//		/**
//		* Initialise dependency values in preparation for storing. This must be
//		* called before serialization.
//		*/
//		function initialiseDeps() {
//			foreach ( $this->deps as $dep ) {
//				$dep->loadDependencyValues();
//			}
//		}
//
//		/**
//		* Get the user-defined value
//		* @return boolean|mixed
//		*/
//		function getValue() {
//			return $this->value;
//		}
//
//		/**
//		* Store the wrapper to a cache
//		*
//		* @param BagOStuff $cache
//		* @param String $key
//		* @param int $expiry
//		*/
//		function storeToCache( $cache, $key, $expiry = 0 ) {
//			$this->initialiseDeps();
//			$cache->set( $key, $this, $expiry );
//		}
//
//		/**
//		* Attempt to get a value from the cache. If the value is expired or missing,
//		* it will be generated with the callback function (if present), and the newly
//		* calculated value will be stored to the cache in a wrapper.
//		*
//		* @param BagOStuff $cache A cache Object
//		* @param String $key The cache key
//		* @param int $expiry The expiry timestamp or interval in seconds
//		* @param boolean|callable $callback The callback for generating the value, or false
//		* @param array $callbackParams The function parameters for the callback
//		* @param array $deps The dependencies to store on a cache miss. Note: these
//		*    are not the dependencies used on a cache hit! Cache hits use the stored
//		*    dependency array.
//		*
//		* @return mixed The value, or null if it was not present in the cache and no
//		*    callback was defined.
//		*/
//		static function getValueFromCache( $cache, $key, $expiry = 0, $callback = false,
//			$callbackParams = [], $deps = []
//		) {
//			$obj = $cache->get( $key );
//
//			if ( is_object( $obj ) && $obj instanceof DependencyWrapper && !$obj->isExpired() ) {
//				$value = $obj->value;
//			} elseif ( $callback ) {
//				$value = call_user_func_array( $callback, $callbackParams );
//				# Cache the newly-generated value
//				$wrapper = new DependencyWrapper( $value, $deps );
//				$wrapper->storeToCache( $cache, $key, $expiry );
//			} else {
//				$value = null;
//			}
//
//			return $value;
//		}
//	}
//
//	/**
//	* @ingroup Cache
//	*/
//	abstract class CacheDependency {
//		/**
//		* Returns true if the dependency is expired, false otherwise
//		*/
//		abstract function isExpired();
//
//		/**
//		* Hook to perform any expensive pre-serialize loading of dependency values.
//		*/
//		function loadDependencyValues() {
//		}
//	}
//
//	/**
//	* @ingroup Cache
//	*/
//	class FileDependency extends CacheDependency {
//		private $filename;
//		private $timestamp;
//
//		/**
//		* Create a file dependency
//		*
//		* @param String $filename The name of the file, preferably fully qualified
//		* @param null|boolean|int $timestamp The unix last modified timestamp, or false if the
//		*        file does not exist. If omitted, the timestamp will be loaded from
//		*        the file.
//		*
//		* A dependency on a nonexistent file will be triggered when the file is
//		* created. A dependency on an existing file will be triggered when the
//		* file is changed.
//		*/
//		function __construct( $filename, $timestamp = null ) {
//			$this->filename = $filename;
//			$this->timestamp = $timestamp;
//		}
//
//		/**
//		* @return array
//		*/
//		function __sleep() {
//			$this->loadDependencyValues();
//
//			return [ 'filename', 'timestamp' ];
//		}
//
//		function loadDependencyValues() {
//			if ( is_null( $this->timestamp ) ) {
//				MediaWiki\suppressWarnings();
//				# Dependency on a non-existent file stores "false"
//				# This is a valid concept!
//				$this->timestamp = filemtime( $this->filename );
//				MediaWiki\restoreWarnings();
//			}
//		}
//
//		/**
//		* @return boolean
//		*/
//		function isExpired() {
//			MediaWiki\suppressWarnings();
//			$lastmod = filemtime( $this->filename );
//			MediaWiki\restoreWarnings();
//			if ( $lastmod === false ) {
//				if ( $this->timestamp === false ) {
//					# Still nonexistent
//					return false;
//				} else {
//					# Deleted
//					wfDebug( "Dependency triggered: {$this->filename} deleted.\n" );
//
//					return true;
//				}
//			} else {
//				if ( $lastmod > $this->timestamp ) {
//					# Modified or created
//					wfDebug( "Dependency triggered: {$this->filename} changed.\n" );
//
//					return true;
//				} else {
//					# Not modified
//					return false;
//				}
//			}
//		}
//	}
//
//	/**
//	* @ingroup Cache
//	*/
//	class GlobalDependency extends CacheDependency {
//		private $name;
//		private $value;
//
//		function __construct( $name ) {
//			$this->name = $name;
//			$this->value = $GLOBALS[$name];
//		}
//
//		/**
//		* @return boolean
//		*/
//		function isExpired() {
//			if ( !isset( $GLOBALS[$this->name] ) ) {
//				return true;
//			}
//
//			return $GLOBALS[$this->name] != $this->value;
//		}
//	}
//
//	/**
//	* @ingroup Cache
//	*/
//	class MainConfigDependency extends CacheDependency {
//		private $name;
//		private $value;
//
//		function __construct( $name ) {
//			$this->name = $name;
//			$this->value = $this->getConfig()->get( $this->name );
//		}
//
//		private function getConfig() {
//			return MediaWikiServices::getInstance()->getMainConfig();
//		}
//
//		/**
//		* @return boolean
//		*/
//		function isExpired() {
//			if ( !$this->getConfig()->has( $this->name ) ) {
//				return true;
//			}
//
//			return $this->getConfig()->get( $this->name ) != $this->value;
//		}
//	}
//
//	/**
//	* @ingroup Cache
//	*/
//	class ConstantDependency extends CacheDependency {
//		private $name;
//		private $value;
//
//		function __construct( $name ) {
//			$this->name = $name;
//			$this->value = constant( $name );
//		}
//
//		/**
//		* @return boolean
//		*/
//		function isExpired() {
//			return constant( $this->name ) != $this->value;
//		}
//	}
