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
package gplx.xowa.mediawiki.extensions.Wikibase.DataValues.Common.DataValues; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.extensions.*; import gplx.xowa.mediawiki.extensions.Wikibase.*; import gplx.xowa.mediawiki.extensions.Wikibase.DataValues.*; import gplx.xowa.mediawiki.extensions.Wikibase.DataValues.Common.*;
import gplx.xowa.mediawiki.extensions.Wikibase.DataValues.DataValues.*;
// REF.MW:https://github.com/DataValues/Common/blob/master/src/DataValues/MonolingualTextValue.php
public class MonolingualTextValue implements DataValue {
	/**
	* @var String
	*/
	private String languageCode;

	/**
	* @var String
	*/
	private String text;
	/**
	* @param String languageCode
	* @param String String text
	*
	* @throws IllegalValueException
	*/
	public MonolingualTextValue(String languageCode, String text) {
//			if ( !is_string( languageCode ) || languageCode === '' ) {
//				throw new IllegalValueException( 'languageCode must be a non-empty String' );
//			}
//			if ( !is_string( String text ) ) {
//				throw new IllegalValueException( 'String text must be a String' );
//			}
		this.languageCode = languageCode;
		this.text = text;
	}

//	/**
//	 * @see Serializable::serialize
//	 *
//	 * @return String
//	 */
//	public function serialize() {
//		return serialize( [ this.languageCode, this.text ] );
//	}
//	/**
//	 * @see Serializable::unserialize
//	 *
//	 * @param String $value
//	 */
//	public function unserialize( $value ) {
//		list( languageCode, String text ) = unserialize( $value );
//		this.__construct( languageCode, String text );
//	}
//	/**
//	 * @see DataValue::getType
//	 *
//	 * @return String
//	 */
//	public static function getType() {
//		return 'monolingualtext';
//	}
//	/**
//	 * @see DataValue::getSortKey
//	 *
//	 * @return String
//	 */
//	public function getSortKey() {
//		// TODO: we might want to re-think this key. Perhaps the language should simply be omitted.
//		return this.languageCode . this.text;
//	}

	/**
	* @see DataValue::getValue
	*
	* @return self
	*/
	public Object getValue() {
		return this;
	}

	/**
	* @return String
	*/
	public String getText() {
		return this.text;
	}

	/**
	* @return String
	*/
	public String getLanguageCode() {
		return this.languageCode;
	}

//	/**
//	 * @see DataValue::getArrayValue
//	 *
//	 * @return String[]
//	 */
//	public function getArrayValue() {
//		return [
//			'text' => this.text,
//			'language' => this.languageCode,
//		];
//	}
//	/**
//	 * Constructs a new instance from the provided data. Required for @see DataValueDeserializer.
//	 * This is expected to round-trip with @see getArrayValue.
//	 *
//	 * @deprecated since 1.0.0. Static DataValue::newFromArray constructors like this are
//	 *  underspecified (not in the DataValue interface), and misleadingly named (should be named
//	 *  newFromArrayValue). Instead, use DataValue builder callbacks in @see DataValueDeserializer.
//	 *
//	 * @param mixed $data Warning! Even if this is expected to be a value as returned by
//	 *  @see getArrayValue, callers of this specific newFromArray implementation can not guarantee
//	 *  this. This is not even guaranteed to be an array!
//	 *
//	 * @throws IllegalValueException if $data is not in the expected format. Subclasses of
//	 *  InvalidArgumentException are expected and properly handled by @see DataValueDeserializer.
//	 * @return self
//	 */
//	public static function newFromArray( $data ) {
//		self::requireArrayFields( $data, [ 'language', 'text' ] );
//		return new static( $data['language'], $data['text'] );
//	}
}
