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
/**
* Interface for the persistence layer of LocalisationCache.
*
* The persistence layer is two-level hierarchical cache. The first level
* is the language, the second level is the item or subitem.
*
* Since the data for a whole language is rebuilt in one operation, it needs
* to have a fast and atomic method for deleting or replacing all of the
* current data for a given language. The interface reflects this bulk update
* operation. Callers writing to the cache must first call startWrite(), then
* will call set() a couple of thousand times, then will call finishWrite()
* to commit the operation. When finishWrite() is called, the cache is
* expected to delete all data previously stored for that language.
*
* The values stored are PHP variables suitable for serialize(). Implementations
* of LCStore are responsible for serializing and unserializing.
*/
interface XomwLCStore {

	/**
	* Get a value.
	* @param String code Language code
	* @param String key Cache key
	*/
	Object get(String code, String key);

	/**
	* Start a write transaction.
	* @param String code Language code
	*/
	void startWrite(String code);

	/**
	* Finish a write transaction.
	*/
	void finishWrite();

	/**
	* Set a key to a given value. startWrite() must be called before this
	* is called, and finishWrite() must be called afterwards.
	* @param String key
	* @param mixed value
	*/
	void set(String key, String value);
}