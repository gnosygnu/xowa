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
package gplx.xowa.mediawiki.extensions.Wikibase.DataValues.DataValues; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.extensions.*; import gplx.xowa.mediawiki.extensions.Wikibase.*; import gplx.xowa.mediawiki.extensions.Wikibase.DataValues.*;
// REF.MW:https://github.com/DataValues/DataValues/blob/master/src/DataValues/DataValue.php
/**
* Interface for objects that represent a single data value.
*
* @since 0.1
*/
public interface DataValue { // extends Hashable, Comparable, Serializable, Immutable 
	/**
	* Returns the identifier of the datavalues type.
	*
	* This is not to be confused with the DataType provided by the DataTypes extension.
	*
	* @since 0.1
	*
	* @return String
	*/
	//	public static function getType();

	/**
	* Returns a key that can be used to sort the data value with.
	* It can be either numeric or a String.
	*
	* @since 0.1
	*
	* @return String|float|int
	*/
	// Object getSortKey();

	/**
	* Returns the value contained by the DataValue. If this value is not simple and
	* does not have it's own type that represents it, the DataValue itself will be returned.
	* In essence, this method returns the "simplest" representation of the value.
	*
	* Example:
	* - NumberValue returns a float or integer
	* - MediaWikiTitleValue returns a Title Object
	* - QuantityValue returns itself
	*
	* @since 0.1
	*
	* @return mixed
	*/
	Object getValue();

	/**
	* Returns the value in a form suitable for an array serialization.
	*
	* For simple values (ie a String) the return value will be equal to that of {@see getValue}.
	*
	* Complex DataValues can provide a nicer implementation though, for instance a
	* geographical coordinate value could provide an array with keys latitude,
	* longitude and altitude, each pointing to a simple float value.
	*
	* @since 0.1
	*
	* @return mixed
	*/
	// Object getArrayValue();

	/**
	* Returns the whole DataValue in array form.
	*
	* The array contains:
	* - value: mixed, same as the result of {@see getArrayValue}
	* - type: String, same as the result of {@see getType}
	*
	* This is sufficient for unserialization in a factory.
	*
	* @since 0.1
	*
	* @return array
	*/
	// Object toArray();

	/**
	* Returns a deep copy of the Object.
	*
	* @since 0.1
	*
	* @return DataValue
	*/
	// DataValue getCopy();
}
