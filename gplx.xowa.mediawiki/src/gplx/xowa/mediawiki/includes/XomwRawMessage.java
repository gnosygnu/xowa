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
package gplx.xowa.mediawiki.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*;
/**
* Variant of the Message cls.
*
* Rather than treating the message key as a lookup
* value (which is passed to the MessageCache and
* translated as necessary), a RawMessage key is
* treated as the actual message.
*
* All other functionality (parsing, escaping, etc.)
* is preserved.
*
* @since 1.21
*/
class XomwRawMessage { // : XomwMessage 
//
//		/**
//		* Call the parent constructor, then store the key as
//		* the message.
//		*
//		* @see Message::__construct
//		*
//		* @param String $text Message to use.
//		* @param array $params Parameters for the message.
//		*
//		* @throws InvalidArgumentException
//		*/
//		public function __construct( $text, $params = [] ) {
//			if ( !is_string( $text ) ) {
//				throw new InvalidArgumentException( '$text must be a String' );
//			}
//
//			parent::__construct( $text, $params );
//
//			// The key is the message.
//			$this->message = $text;
//		}
//
//		/**
//		* Fetch the message (in this case, the key).
//		*
//		* @return String
//		*/
//		public function fetchMessage() {
//			// Just in case the message is unset somewhere.
//			if ( $this->message === null ) {
//				$this->message = $this->key;
//			}
//
//			return $this->message;
//		}
}
