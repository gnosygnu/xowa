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
/**
* Service interface for looking up Interwiki records.
*
* @since 1.28
*/
public interface XomwInterwikiLookup {
	/**
	* Check whether an interwiki prefix exists
	*
	* @param String $prefix Interwiki prefix to use
	* @return boolean Whether it exists
	*/
	boolean isValidInterwiki(byte[] prefix);

	/**
	* Fetch an Interwiki Object
	*
	* @param String $prefix Interwiki prefix to use
	* @return Interwiki|null|boolean
	*/
	XomwInterwiki fetch(byte[] prefix);

	/**
	* Returns all interwiki prefixes
	*
	* @param String|null $local If set, limits output to local/non-local interwikis
	* @return String[] List of prefixes
	*/
	byte[][] getAllPrefixes(boolean local);

//		/**
//		* Purge the in-process and persistent Object cache for an interwiki prefix
//		* @param String $prefix
//		*/
//		void invalidateCache(byte[] prefix);
}
