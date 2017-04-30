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
import gplx.xowa.mediawiki.languages.*;
import gplx.xowa.mediawiki.includes.content.*;
/**
* The Message cls provides methods which fulfil two basic services:
*  - fetching interfaceIsUserLang messages
*  - processing messages into a variety of formats
*
* First implemented with MediaWiki 1.17, the Message cls is intended to
* replace the old wfMsg* functions that over time grew unusable.
* @see https://www.mediawiki.org/wiki/Manual:Messages_API for equivalences
* between old and new functions.
*
* You should use the wfMessage() global function which acts as a wrapper for
* the Message cls. The wrapper let you pass parameters as arguments.
*
* The most basic usage cases would be:
*
* @code
*     // Initialize a Message Object using the 'some_key' message key
*     $message = wfMessage('some_key');
*
*     // Using two parameters those values are strings 'value1' and 'value2':
*     $message = wfMessage('some_key',
*          'value1', 'value2'
*    );
* @endcode
*
* @section message_global_fn Global function wrapper:
*
* Since wfMessage() returns a Message instance, you can chain its call with
* a method. Some of them return a Message instance too so you can chain them.
* You will find below several examples of wfMessage() usage.
*
* Fetching a message text for interfaceIsUserLang message:
*
* @code
*    $button = Xml::button(
*         wfMessage('submit')->text()
*   );
* @endcode
*
* A Message instance can be passed parameters after it has been constructed,
* use the prms() method to do so:
*
* @code
*     wfMessage('welcome-to')
*         ->prmsVar($wgSitename)
*         ->text();
* @endcode
*
* {{GRAMMAR}} and friends work correctly:
*
* @code
*    wfMessage('are-friends',
*        $user, $friend
*   );
*    wfMessage('bad-message')
*         ->rawParams('<script>...</script>')
*         ->escaped();
* @endcode
*
* @section message_language Changing language:
*
* Messages can be requested in a different language or in whatever current
* content language is being used. The methods are:
*     - Message->inContentLanguage()
*     - Message->inLanguage()
*
* Sometimes the message text ends up in the database, so content language is
* needed:
*
* @code
*    wfMessage('file-log',
*        $user, $filename
*   )->inContentLanguage()->text();
* @endcode
*
* Checking whether a message exists:
*
* @code
*    wfMessage('mysterious-message')->exists()
*    // returns a boolean whether the 'mysterious-message' key exist.
* @endcode
*
* If you want to use a different language:
*
* @code
*    $userLanguage = $user->getOption('language');
*    wfMessage('email-header')
*         ->inLanguage($userLanguage)
*         ->plain();
* @endcode
*
* @note You can parse the text only in the content or interfaceIsUserLang languages
*
* @section message_compare_old Comparison with old wfMsg* functions:
*
* Use full parsing:
*
* @code
*     // old style:
*     wfMsgExt('key', [ 'parseinline' ], 'apple');
*     // new style:
*     wfMessage('key', 'apple')->parse();
* @endcode
*
* Parseinline is used because it is more useful when pre-building HTML.
* In normal use it is better to use OutputPage::(add|wrap)WikiMsg.
*
* Places where HTML cannot be used. {{-transformation is done.
* @code
*     // old style:
*     wfMsgExt('key', [ 'parsemag' ], 'apple', 'pear');
*     // new style:
*     wfMessage('key', 'apple', 'pear')->text();
* @endcode
*
* Shortcut for escaping the message too, similar to wfMsgHTML(), but
* parameters are not replaced after escaping by default.
* @code
*     $escaped = wfMessage('key')
*          ->rawParams('apple')
*          ->escaped();
* @endcode
*
* @section message_appendix Appendix:
*
* @todo
* - test, can we have tests?
* - this documentation needs to be extended
*
* @see https://www.mediawiki.org/wiki/WfMessage()
* @see https://www.mediawiki.org/wiki/New_messages_API
* @see https://www.mediawiki.org/wiki/Localisation
*
* @since 1.17
*/
public class XomwMessage {
//		/** Use message text as-is */
	private static final int FORMAT_PLAIN = 0; // 'plain';
	/** Use normal wikitext -> HTML parsing (the result will be wrapped in a block-level HTML tag) */
	private static final int FORMAT_BLOCK_PARSE = 1; // 'block-parse';
	/** Use normal wikitext -> HTML parsing but strip the block-level wrapper */
	private static final int FORMAT_PARSE = 2; // 'parse';
	/** Transform {{..}} constructs but don't transform to HTML */
	private static final int FORMAT_TEXT = 3; // 'text';
	/** Transform {{..}} constructs, HTML-escape the result */
	private static final int FORMAT_ESCAPED = 4; // 'escaped';

	private static final int FORMAT_NULL = 5;

	private static final int PRM_TID_BEFORE = 1; // "before"
	private static final int PRM_TID_AFTER = 2;  // "after"

	/**
	* Mapping from Message::listParam() types to Language methods.
	* @var array
	*/
	private static final    Hash_adp listTypeMap = Hash_adp_.New()
	.Add_and_more("comma", "commaList")
	.Add_and_more("semicolon", "semicolonList")
	.Add_and_more("pipe", "pipeList")
	.Add_and_more("text", "listToText")
	;

	/**
	* In which language to get this message. True, which is the default,
	* means the current user language, false content language.
	*
	* @var boolean
	*/
	private boolean interfaceIsUserLang = true;

	/**
	* In which language to get this message. Overrides the $interfaceIsUserLang setting.
	*
	* @var Language|boolean Explicit language Object, or false for user language
	*/
	private XomwLanguage language;

	/**
	* @var String The message key. If $keysToTry has more than one element,
	* this may change to one of the keys to try when fetching the message text.
	*/
	private byte[] key;

	/**
	* @var String[] List of keys to try when fetching the message.
	*/
	private String[] keysToTry;

	/**
	* @var array List of parameters which will be substituted into the message.
	*/
	private List_adp parameters = List_adp_.New();

	/**
	* @var String
	* @deprecated
	*/
	private int format = FORMAT_PARSE;

	/**
	* @var boolean Whether database can be used.
	*/
	private boolean useDatabase = true;

	/**
	* @var Title Title Object to use as context.
	*/
	private XomwTitle title = null;

	/**
	* @var Content Content Object representing the message.
	*/
//		private XomwContent content = null;

	/**
	* @var String
	*/
	private byte[] message;

	public void CompilerAppeasement() {
		this.key = null; this.keysToTry = null; this.message = null;
		Tfds.Write(interfaceIsUserLang, language, key, keysToTry, format, useDatabase, title, message, listTypeMap, parameters);
		Tfds.Write(FORMAT_BLOCK_PARSE, FORMAT_ESCAPED, FORMAT_PARSE, FORMAT_PLAIN, FORMAT_TEXT);
		this.extractParam(null, null, 0);
	}
	public XomwMessage(byte[] textBry, XomwLanguage language) {
		this.textBry = textBry;
		this.language = language;
	}
	public byte[] text() {return textBry;} private byte[] textBry;
	public byte[] escaped() {throw Err_.new_unimplemented();}

//		/**
//		* @since 1.17
//		* @param String|String[]|MessageSpecifier key Message key, or array of
//		* message keys to try and use the first non-empty message for, or a
//		* MessageSpecifier to copy from.
//		* @param array $prmsVar Message parameters.
//		* @param Language $language [optional] Language to use (defaults to current user language).
//		* @throws InvalidArgumentException
//		*/
//		public function __construct(key, $prmsVar = [], Language $language = null) {
//			if (key instanceof MessageSpecifier) {
//				if ($prmsVar) {
//					throw new InvalidArgumentException(
//						'$prmsVar must be empty if key is a MessageSpecifier'
//					);
//				}
//				$prmsVar = key->getParams();
//				key = key->getKey();
//			}
//
//			if (!is_string(key) && !is_array(key)) {
//				throw new InvalidArgumentException('key must be a String or an array');
//			}
//
//			this.keysToTry = (array)key;
//
//			if (empty(this.keysToTry)) {
//				throw new InvalidArgumentException('key must not be an empty list');
//			}
//
//			this.key = reset(this.keysToTry);
//
//			this.parameters = array_values($prmsVar);
//			// User language is only resolved in getLanguage(). This helps preserve the
//			// semantic intent of "user language" across serialize() and unserialize().
//			this.language = $language ?: false;
//		}

	//	/**
	//	* @see Serializable::serialize()
	//	* @since 1.26
	//	* @return String
	//	*/
	//	public function serialize() {
	//		return serialize([
	//			'interfaceIsUserLang' => this.interfaceIsUserLang,
	//			'language' => this.language ? this.language->getCode() : false,
	//			'key' => this.key,
	//			'keysToTry' => this.keysToTry,
	//			'parameters' => this.parameters,
	//			'format' => this.format,
	//			'useDatabase' => this.useDatabase,
	//			'title' => this.title,
	//		]);
	//	}
	//
	//	/**
	//	* @see Serializable::unserialize()
	//	* @since 1.26
	//	* @param String $serialized
	//	*/
	//	public function unserialize($serialized) {
	//		$data = unserialize($serialized);
	//		this.interfaceIsUserLang = $data['interfaceIsUserLang'];
	//		this.key = $data['key'];
	//		this.keysToTry = $data['keysToTry'];
	//		this.parameters = $data['parameters'];
	//		this.format = $data['format'];
	//		this.useDatabase = $data['useDatabase'];
	//		this.language = $data['language'] ? Language::factory($data['language']) : false;
	//		this.title = $data['title'];
	//	}

//		/**
//		* @since 1.24
//		*
//		* @return boolean True if this is a multi-key message, that is, if the key provided to the
//		* constructor was a fallback list of keys to try.
//		*/
//		public function isMultiKey() {
//			return count(this.keysToTry) > 1;
//		}
//
//		/**
//		* @since 1.24
//		*
//		* @return String[] The list of keys to try when fetching the message text,
//		* in order of preference.
//		*/
//		public function getKeysToTry() {
//			return this.keysToTry;
//		}

	/**
	* Returns the message key.
	*
	* If a list of multiple possible keys was supplied to the constructor, this method may
	* return any of these keys. After the message has been fetched, this method will return
	* the key that was actually used to fetch the message.
	*
	* @since 1.21
	*
	* @return String
	*/
	public byte[] getKey() {
		return this.key;
	}

//		/**
//		* Returns the message parameters.
//		*
//		* @since 1.21
//		*
//		* @return array
//		*/
//		public function getParams() {
//			return this.parameters;
//		}
//
//		/**
//		* Returns the message format.
//		*
//		* @since 1.21
//		*
//		* @return String
//		* @deprecated since 1.29 formatting is not stateful
//		*/
//		public function getFormat() {
//			wfDeprecated(__METHOD__, '1.29');
//			return this.format;
//		}

	/**
	* Returns the Language of the Message.
	*
	* @since 1.23
	*
	* @return Language
	*/
	public XomwLanguage getLanguage() {
		// Defaults to false which means current user language
		return this.language;
	}

//		/**
//		* Factory function that is just wrapper for the real constructor. It is
//		* intended to be used instead of the real constructor, because it allows
//		* chaining method calls, while new objects don't.
//		*
//		* @since 1.17
//		*
//		* @param String|String[]|MessageSpecifier key
//		* @param mixed $param,... Parameters as strings.
//		*
//		* @return Message
//		*/
//		public static function newFromKey(key /*...*/) {
//			$prmsVar = func_get_args();
//			array_shift($prmsVar);
//			return new self(key, $prmsVar);
//		}
//
//		/**
//		* Transform a MessageSpecifier or a primitive value used interchangeably with
//		* specifiers (a message key String, or a key + prmsVar array) into a proper Message.
//		*
//		* Also accepts a MessageSpecifier inside an array: that's not considered a valid format
//		* but is an easy error to make due to how StatusValue stores messages internally.
//		* Further array elements are ignored in that case.
//		*
//		* @param String|array|MessageSpecifier $value
//		* @return Message
//		* @throws InvalidArgumentException
//		* @since 1.27
//		*/
//		public static function newFromSpecifier($value) {
//			$prmsVar = [];
//			if (is_array($value)) {
//				$prmsVar = $value;
//				$value = array_shift($prmsVar);
//			}
//
//			if ($value instanceof Message) { // Message, RawMessage, ApiMessage, etc
//				$message = clone($value);
//			} elseif ($value instanceof MessageSpecifier) {
//				$message = new Message($value);
//			} elseif (is_string($value)) {
//				$message = new Message($value, $prmsVar);
//			} else {
//				throw new InvalidArgumentException(__METHOD__ . ': invalid argument type '
//					. gettype($value));
//			}
//
//			return $message;
//		}
//
//		/**
//		* Factory function accepting multiple message keys and returning a message instance
//		* for the first message which is non-empty. If all messages are empty then an
//		* instance of the first message key is returned.
//		*
//		* @since 1.18
//		*
//		* @param String|String[] $keys,... Message keys, or first argument as an array of all the
//		* message keys.
//		*
//		* @return Message
//		*/
//		public static function newFallbackSequence(/*...*/) {
//			$keys = func_get_args();
//			if (func_num_args() == 1) {
//				if (is_array($keys[0])) {
//					// Allow an array to be passed as the first argument instead
//					$keys = array_values($keys[0]);
//				} else {
//					// Optimize a single String to not need special fallback handling
//					$keys = $keys[0];
//				}
//			}
//			return new self($keys);
//		}
//
//		/**
//		* Get a title Object for a mediawiki message, where it can be found in the mediawiki namespace.
//		* The title will be for the current language, if the message key is in
//		* $wgForceUIMsgAsContentMsg it will be append with the language code (except content
//		* language), because Message::inContentLanguage will also return in user language.
//		*
//		* @see $wgForceUIMsgAsContentMsg
//		* @return Title
//		* @since 1.26
//		*/
//		public function getTitle() {
//			global $wgContLang, $wgForceUIMsgAsContentMsg;
//
//			title = this.key;
//			if (
//				!this.language->equals($wgContLang)
//				&& in_array(this.key, (array)$wgForceUIMsgAsContentMsg)
//			) {
//				$code = this.language->getCode();
//				title .= '/' . $code;
//			}
//
//			return Title::makeTitle(NS_MEDIAWIKI, $wgContLang->ucfirst(strtr(title, ' ', '_')));
//		}
//
//		/**
//		* Adds parameters to the parameter list of this message.
//		*
//		* @since 1.17
//		*
//		* @param mixed ... Parameters as strings or arrays from
//		*  Message::numParam() and the like, or a single array of parameters.
//		*
//		* @return Message this
//		*/
//		public function prmsVar(/*...*/) {
//			$args = func_get_args();
//
//			// If $args has only one entry and it's an array, then it's either a
//			// non-varargs call or it happens to be a call with just a single
//			// "special" parameter. Since the "special" parameters don't have any
//			// numeric keys, we'll test that to differentiate the cases.
//			if (count($args) === 1 && isset($args[0]) && is_array($args[0])) {
//				if ($args[0] === []) {
//					$args = [];
//				} else {
//					foreach ($args[0] as key => $value) {
//						if (is_int(key)) {
//							$args = $args[0];
//							break;
//						}
//					}
//				}
//			}
//
//			this.parameters = array_merge(this.parameters, array_values($args));
//			return this;
//		}

	/**
	* Add parameters that are substituted after parsing or escaping.
	* In other words the parsing process cannot access the contents
	* of this type of parameter, and you need to make sure it is
	* sanitized beforehand.  The parser will see "$n", instead.
	*
	* @since 1.17
	*
	* @param mixed $prmsVar,... Raw parameters as strings, or a single argument that is
	* an array of raw parameters.
	*
	* @return Message this
	*/
	public XomwMessage rawParams(byte[]... prmsAry) {
		for (byte[] prmsVar : prmsAry) {
			this.parameters.Add(XomwMessage.rawParam(prmsVar));
		}
		return this;
	}

	/**
	* Add parameters that are numeric and will be passed through
	* Language::formatNum before substitution
	*
	* @since 1.18
	*
	* @param mixed $param,... Numeric parameters, or a single argument that is
	* an array of numeric parameters.
	*
	* @return Message this
	*/
	public XomwMessage numParams(int... prmsAry) {
		for (int prmsVar : prmsAry) {
			this.parameters.Add(XomwMessage.numParam(prmsVar));
		}
		return this;
	}

	/**
	* Add parameters that are durations of time and will be passed through
	* Language::formatDuration before substitution
	*
	* @since 1.22
	*
	* @param int|int[] $param,... Duration parameters, or a single argument that is
	* an array of duration parameters.
	*
	* @return Message this
	*/
	public XomwMessage durationParams(int... prmsAry) {
		for (int prmsVar : prmsAry) {
			this.parameters.Add(XomwMessage.durationParam(prmsVar));
		}
		return this;
	}

	/**
	* Add parameters that are expiration times and will be passed through
	* Language::formatExpiry before substitution
	*
	* @since 1.22
	*
	* @param String|String[] $param,... Expiry parameters, or a single argument that is
	* an array of expiry parameters.
	*
	* @return Message this
	*/
	public XomwMessage expiryParams(byte[]... prmsAry) {
		for (byte[] prmsVar : prmsAry) {
			this.parameters.Add(XomwMessage.expiryParam(prmsVar));
		}
		return this;
	}

	/**
	* Add parameters that are time periods and will be passed through
	* Language::formatTimePeriod before substitution
	*
	* @since 1.22
	*
	* @param int|int[] $param,... Time period parameters, or a single argument that is
	* an array of time period parameters.
	*
	* @return Message this
	*/
	public XomwMessage timeperiodParams(int... prmsAry) {
		for (int prmsVar : prmsAry) {
			this.parameters.Add(XomwMessage.timeperiodParam(prmsVar));
		}
		return this;
	}

	/**
	* Add parameters that are file sizes and will be passed through
	* Language::formatSize before substitution
	*
	* @since 1.22
	*
	* @param int|int[] $param,... Size parameters, or a single argument that is
	* an array of size parameters.
	*
	* @return Message this
	*/
	public XomwMessage sizeParams(int... prmsAry) {
		for (int prmsVar : prmsAry) {
			this.parameters.Add(XomwMessage.sizeParam(prmsVar));
		}
		return this;
	}

	/**
	* Add parameters that are bitrates and will be passed through
	* Language::formatBitrate before substitution
	*
	* @since 1.22
	*
	* @param int|int[] $param,... Bit rate parameters, or a single argument that is
	* an array of bit rate parameters.
	*
	* @return Message this
	*/
	public XomwMessage bitrateParams(int... prmsAry) {
		for (int prmsVar : prmsAry) {
			this.parameters.Add(XomwMessage.bitrateParam(prmsVar));
		}
		return this;
	}

	/**
	* Add parameters that are plaintext and will be passed through without
	* the content being evaluated.  Plaintext parameters are not valid as
	* arguments to parser functions. This differs from XomwMessage.rawParams in
	* that the Message cls handles escaping to match the output format.
	*
	* @since 1.25
	*
	* @param String|String[] $param,... plaintext parameters, or a single argument that is
	* an array of plaintext parameters.
	*
	* @return Message this
	*/
	public XomwMessage plaintextParams(byte[]... prmsAry) {
		for (byte[] prmsVar : prmsAry) {
			this.parameters.Add(XomwMessage.plaintextParam(prmsVar));
		}
		return this;
	}

//		/**
//		* Set the language and the title from a context Object
//		*
//		* @since 1.19
//		*
//		* @param IContextSource $context
//		*
//		* @return Message this
//		*/
//		public function setContext(IContextSource $context) {
//			this.inLanguage($context->getLanguage());
//			this.title($context->getTitle());
//			this.interfaceIsUserLang = true;
//
//			return this;
//		}
//
//		/**
//		* Request the message in any language that is supported.
//		*
//		* As a side effect interfaceIsUserLang message status is unconditionally
//		* turned off.
//		*
//		* @since 1.17
//		* @param Language|String $lang Language code or Language Object.
//		* @return Message this
//		* @throws MWException
//		*/
//		public function inLanguage($lang) {
//			if ($lang instanceof Language) {
//				this.language = $lang;
//			} elseif (is_string($lang)) {
//				if (!this.language instanceof Language || this.language->getCode() != $lang) {
//					this.language = Language::factory($lang);
//				}
//			} elseif ($lang instanceof StubUserLang) {
//				this.language = false;
//			} else {
//				$type = gettype($lang);
//				throw new MWException(__METHOD__ . " must be "
//					. "passed a String or Language Object; $type given"
//				);
//			}
//			this.message = null;
//			this.interfaceIsUserLang = false;
//			return this;
//		}

	/**
	* Request the message in the wiki's content language,
	* unless it is disabled for this message.
	*
	* @since 1.17
	* @see $wgForceUIMsgAsContentMsg
	*
	* @return Message this
	*/
	public XomwMessage inContentLanguage() {
//			global $wgForceUIMsgAsContentMsg;
//			if (in_array(this.key, (array)$wgForceUIMsgAsContentMsg)) {
//				return this;
//			}
//
//			global $wgContLang;
//			this.inLanguage($wgContLang);
//			return this;
		return this;
	}

//		/**
//		* Allows manipulating the interfaceIsUserLang message flag directly.
//		* Can be used to restore the flag after setting a language.
//		*
//		* @since 1.20
//		*
//		* @param boolean $interfaceIsUserLang
//		*
//		* @return Message this
//		*/
//		public function setInterfaceMessageFlag($interfaceIsUserLang) {
//			this.interfaceIsUserLang = (boolean)$interfaceIsUserLang;
//			return this;
//		}
//
//		/**
//		* Enable or disable database use.
//		*
//		* @since 1.17
//		*
//		* @param boolean $useDatabase
//		*
//		* @return Message this
//		*/
//		public function useDatabase($useDatabase) {
//			this.useDatabase = (boolean)$useDatabase;
//			this.message = null;
//			return this;
//		}
//
//		/**
//		* Set the Title Object to use as context when transforming the message
//		*
//		* @since 1.18
//		*
//		* @param Title title
//		*
//		* @return Message this
//		*/
//		public function title(title) {
//			this.title = title;
//			return this;
//		}

	/**
	* Returns the message as a Content Object.
	*
	* @return Content
	*/
	public XomwContent contentFunc() {
//			if (this.content == null) {
//				this.content = new XomwMessageContent(this);
//			}
//
//			return this.content;
		throw Err_.new_unimplemented();
	}

	/**
	* Returns the message parsed from wikitext to HTML.
	*
	* @since 1.17
	*
	* @param String|null $format One of the FORMAT_* constants. Null means use whatever was used
	*   the last time (this is for B/C and should be avoided).
	*
	* @return String HTML
	*/

	// NOTE: causes issues in C# source; E2A7BC; http://www.fileformat.info/info/unicode/char/29fc/index.htm
	private static final    byte[] LeftPointingCurvedAngleBracket = gplx.core.encoders.Hex_utl_.Parse_hex_to_bry("E2A7BC");
	public byte[] toString(int format) {
		if (format == FORMAT_NULL) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "toString import implicit.*; format=~{0} key=~{1}", format, key);
			format = this.format;
		}
//			byte[] s = this.fetchMessage();
		byte[] s = Bry_.Empty;

		if (s == null) {
			// Err on the side of safety, ensure that the output
			// is always html safe in the event the message key is
			// missing, since in that case its highly likely the
			// message key is user-controlled.
			// LeftPointingCurvedAngleBracket is used instead of '<' to side-step any
			// double-escaping issues.
			// (Keep synchronised with mw.Message#toString in JS.)
			return Bry_.Escape_html(Bry_.Add(LeftPointingCurvedAngleBracket, key, LeftPointingCurvedAngleBracket));
		}

		// Replace $* with a list of parameters for &uselang=qqx.
//			if (XophpString.strpos(s, "$*") != false) {
//				String paramlist = "";
//				if (this.parameters != []) {
//					paramlist = ": $" . implode(", $", range(1, count(this.parameters)));
//				}
//				s = str_replace("$*", paramlist, s);
//			}

//			// Replace parameters before text parsing
//			s = this.replaceParameters(s, "before", format);
//
//			// Maybe transform using the full parser
//			if (format == XomwMessage.FORMAT_PARSE) {
//				s = this.parseText(s);
//				s = Parser::stripOuterParagraph(s);
//			} elseif (format == XomwMessage.FORMAT_BLOCK_PARSE) {
//				s = this.parseText(s);
//			} elseif (format == XomwMessage.FORMAT_TEXT) {
//				s = this.transformText(s);
//			} elseif (format == XomwMessage.FORMAT_ESCAPED) {
//				s = this.transformText(s);
//				s = htmlspecialchars(s, ENT_QUOTES, "UTF-8", false);
//			}
//
//			// Raw parameter replacement
//			s = this.replaceParameters(s, "after", format);

		return s;
	}

//		/**
//		* Magic method implementation of the above (for PHP >= 5.2.0), so we can do, eg:
//		*     $foo = new Message(key);
//		*     $String = "<abbr>$foo</abbr>";
//		*
//		* @since 1.18
//		*
//		* @return String
//		*/
//		public function __toString() {
//			// PHP doesn't allow __toString to throw exceptions and will
//			// trigger a fatal error if it does. So, catch any exceptions.
//
//			try {
//				return this.toString(XomwMessage.FORMAT_PARSE);
//			} catch (Exception $ex) {
//				try {
//					trigger_error("Exception caught in " . __METHOD__ . " (message " . this.key . "): "
//						. $ex, E_USER_WARNING);
//				} catch (Exception $ex) {
//					// Doh! Cause a fatal error after all?
//				}
//
//				return '⧼' . htmlspecialchars(this.key) . '⧽';
//			}
//		}
//
//		/**
//		* Fully parse the text from wikitext to HTML.
//		*
//		* @since 1.17
//		*
//		* @return String Parsed HTML.
//		*/
//		public function parse() {
//			this.format = XomwMessage.FORMAT_PARSE;
//			return this.toString(XomwMessage.FORMAT_PARSE);
//		}

	/**
	* Returns the message text. {{-transformation is done.
	*
	* @since 1.17
	*
	* @return String Unescaped message text.
	*/
	public byte[] textMw() {
		this.format = XomwMessage.FORMAT_TEXT;
		return this.toString(XomwMessage.FORMAT_TEXT);
	}

//		/**
//		* Returns the message text as-is, only parameters are substituted.
//		*
//		* @since 1.17
//		*
//		* @return String Unescaped untransformed message text.
//		*/
//		public function plain() {
//			this.format = XomwMessage.FORMAT_PLAIN;
//			return this.toString(XomwMessage.FORMAT_PLAIN);
//		}
//
//		/**
//		* Returns the parsed message text which is always surrounded by a block element.
//		*
//		* @since 1.17
//		*
//		* @return String HTML
//		*/
//		public function parseAsBlock() {
//			this.format = XomwMessage.FORMAT_BLOCK_PARSE;
//			return this.toString(XomwMessage.FORMAT_BLOCK_PARSE);
//		}
//
//		/**
//		* Returns the message text. {{-transformation is done and the result
//		* is escaped excluding any raw parameters.
//		*
//		* @since 1.17
//		*
//		* @return String Escaped message text.
//		*/
//		public function escaped() {
//			this.format = XomwMessage.FORMAT_ESCAPED;
//			return this.toString(XomwMessage.FORMAT_ESCAPED);
//		}
//
//		/**
//		* Check whether a message key has been defined currently.
//		*
//		* @since 1.17
//		*
//		* @return boolean
//		*/
//		public function exists() {
//			return this.fetchMessage() !== false;
//		}
//
//		/**
//		* Check whether a message does not exist, or is an empty String
//		*
//		* @since 1.18
//		* @todo FIXME: Merge with isDisabled()?
//		*
//		* @return boolean
//		*/
//		public function isBlank() {
//			$message = this.fetchMessage();
//			return $message === false || $message === '';
//		}
//
//		/**
//		* Check whether a message does not exist, is an empty String, or is "-".
//		*
//		* @since 1.18
//		*
//		* @return boolean
//		*/
//		public function isDisabled() {
//			$message = this.fetchMessage();
//			return $message === false || $message === '' || $message === '-';
//		}

	/**
	* @since 1.17
	*
	* @param mixed $raw
	*
	* @return array Array with a single "raw" key.
	*/
	private static XomwMessagePrm_raw rawParam(byte[] raw) {
		return new XomwMessagePrm_raw(raw);
	}

	/**
	* @since 1.18
	*
	* @param mixed $num
	*
	* @return array Array with a single "num" key.
	*/
	private static XomwMessagePrm_num numParam(int num) {
		return new XomwMessagePrm_num(num);
	}

	/**
	* @since 1.22
	*
	* @param int $duration
	*
	* @return int[] Array with a single "duration" key.
	*/
	private static XomwMessagePrm_duration durationParam(int duration) {
		return new XomwMessagePrm_duration(duration);
	}

	/**
	* @since 1.22
	*
	* @param String $expiry
	*
	* @return String[] Array with a single "expiry" key.
	*/
	private static XomwMessagePrm_expiry expiryParam(byte[] expiry) {
		return new XomwMessagePrm_expiry(expiry);
	}

	/**
	* @since 1.22
	*
	* @param int $period
	*
	* @return int[] Array with a single "period" key.
	*/
	private static XomwMessagePrm_period timeperiodParam(int period) {
		return new XomwMessagePrm_period(period);
	}

	/**
	* @since 1.22
	*
	* @param int $size
	*
	* @return int[] Array with a single "size" key.
	*/
	private static XomwMessagePrm_size sizeParam(int size) {
		return new XomwMessagePrm_size(size);
	}

	/**
	* @since 1.22
	*
	* @param int $bitrate
	*
	* @return int[] Array with a single "bitrate" key.
	*/
	private static XomwMessagePrm_bitrate bitrateParam(int bitrate) {
		return new XomwMessagePrm_bitrate(bitrate);
	}

	/**
	* @since 1.25
	*
	* @param String $plaintext
	*
	* @return String[] Array with a single "plaintext" key.
	*/
	private static XomwMessagePrm_plaintext plaintextParam(byte[] plaintext) {
		return new XomwMessagePrm_plaintext(plaintext);
	}

	/**
	* @since 1.29
	*
	* @param array $list
	* @param String $type 'comma', 'semicolon', 'pipe', 'text'
	* @return array Array with "list" and "type" keys.
	*/
//		private static XomwMessagePrm_list listParam(byte[][] list, String type) { // array $list, $type = 'text'
//			if (!listTypeMap.Has(type)) {
//				throw new XophpInvalidArgumentException(
//					"Invalid type " + type
//				);
//			}
//			return new XomwMessagePrm_list(list, type);
//		}

	/**
	* Substitutes any parameters into the message text.
	*
	* @since 1.17
	*
	* @param String $message The message text.
	* @param String $type Either "before" or "after".
	* @param String $format One of the FORMAT_* constants.
	*
	* @return String
	*/
	// DFLT:type="before"
//		private byte[] replaceParameters(byte[] message, int type, int format) {
//			$replacementKeys = [];
//			foreach (this.parameters as $n => $param) {
//				list($paramType, $value) = this.extractParam($param, $format);
//				if ($type === $paramType) {
//					$replacementKeys['$' . ($n + 1)] = $value;
//				}
//			}
//			$message = strtr($message, $replacementKeys);
//			return $message;
//			return null;
//		}

	/**
	* Extracts the parameter type and preprocessed the value if needed.
	*
	* @since 1.18
	*
	* @param mixed $param Parameter as defined in this cls.
	* @param String $format One of the FORMAT_* constants.
	*
	* @return array Array with the parameter type (either "before" or "after") and the value.
	*/
	private void extractParam(XomwMessageVal rv, Object param, int format) {
		if (Type_adp_.Implements_intf_obj(param, XomwMessagePrm.class)) {
			XomwMessagePrm prm = (XomwMessagePrm)param;
			switch (prm.Tid()) {
				case XomwMessagePrm.Tid__raw:
					rv.Set(PRM_TID_AFTER, ((XomwMessagePrm_raw)prm).raw);
					break;
				case XomwMessagePrm.Tid__num:
					// Replace number prmsVar always in before step for now.
					// No support for combined raw and num prmsVar
					rv.Set(PRM_TID_BEFORE, this.getLanguage().formatNum(((XomwMessagePrm_num)prm).numAsBry()));
					break;
//					case XomwMessagePrm.Tid__duration:
//						return [PRM_TID_BEFORE, this.getLanguage()->formatDuration($param['duration']) ];
//						break;
//					case XomwMessagePrm.Tid__expiry:
//						return [PRM_TID_BEFORE, this.getLanguage()->formatExpiry($param['expiry']) ];
//						break;
//					case XomwMessagePrm.Tid__period:
//						return [PRM_TID_BEFORE, this.getLanguage()->formatTimePeriod($param['period']) ];
//						break;
//					case XomwMessagePrm.Tid__size:
//						return [PRM_TID_BEFORE, this.getLanguage()->formatSize($param['size']) ];
//						break;
//					case XomwMessagePrm.Tid__bitrate:
//						return [PRM_TID_BEFORE, this.getLanguage()->formatBitrate($param['bitrate']) ];
//						break;
//					case XomwMessagePrm.Tid__plaintext:
//						return [PRM_TID_AFTER, this.formatPlaintext($param['plaintext'], $format) ];
//						break;
//					case XomwMessagePrm.Tid__list:
//						return this.formatListParam($param['list'], $param['type'], $format);
//						break;
				default: 
					String warning = "Invalid parameter for message '" + this.getKey() + "': " +
						prm.toString();
					Gfo_usr_dlg_.Instance.Warn_many("", "", warning);

					rv.Set(PRM_TID_BEFORE, Bry_.new_a7("[INVALID]"));
					break;
			}
//			}
//			else if ($param instanceof Message) {
//				// Match language, flags, etc. to the current message.
//				$msg = clone $param;
//				if ($msg->language !== this.language || $msg->useDatabase !== this.useDatabase) {
//					// Cache depends on these parameters
//					$msg->message = null;
//				}
//				$msg->interfaceIsUserLang = this.interfaceIsUserLang;
//				$msg->language = this.language;
//				$msg->useDatabase = this.useDatabase;
//				$msg->title = this.title;
//
//				// DWIM
//				if ($format === 'block-parse') {
//					$format = 'parse';
//				}
//				$msg->format = $format;
//
//				// Message objects should not be before parameters because
//				// then they'll get double escaped. If the message needs to be
//				// escaped, it'll happen right here when we call toString().
//				return [PRM_TID_AFTER, $msg->toString($format) ];
//			}
//			else {
//				rv.Set(PRM_TID_BEFORE, param);
		}
	}

//		/**
//		* Wrapper for what ever method we use to parse wikitext.
//		*
//		* @since 1.17
//		*
//		* @param String $String Wikitext message contents.
//		*
//		* @return String Wikitext parsed into HTML.
//		*/
//		protected function parseText($String) {
//			$out = MessageCache::singleton()->parse(
//				$String,
//				this.title,
//				/*linestart*/true,
//				this.interfaceIsUserLang,
//				this.getLanguage()
//			);
//
//			return $out instanceof ParserOutput ? $out->getText() : $out;
//		}
//
//		/**
//		* Wrapper for what ever method we use to {{-transform wikitext.
//		*
//		* @since 1.17
//		*
//		* @param String $String Wikitext message contents.
//		*
//		* @return String Wikitext with {{-constructs replaced with their values.
//		*/
//		protected function transformText($String) {
//			return MessageCache::singleton()->transform(
//				$String,
//				this.interfaceIsUserLang,
//				this.getLanguage(),
//				this.title
//			);
//		}
//
//		/**
//		* Wrapper for what ever method we use to get message contents.
//		*
//		* @since 1.17
//		*
//		* @return String
//		* @throws MWException If message key array is empty.
//		*/
//		protected function fetchMessage() {
//			if (this.message === null) {
//				$cache = MessageCache::singleton();
//
//				foreach (this.keysToTry as key) {
//					$message = $cache->get(key, this.useDatabase, this.getLanguage());
//					if ($message !== false && $message !== '') {
//						break;
//					}
//				}
//
//				// NOTE: The constructor makes sure keysToTry isn't empty,
//				//       so we know that key and $message are initialized.
//				this.key = key;
//				this.message = $message;
//			}
//			return this.message;
//		}
//
//		/**
//		* Formats a message parameter wrapped with 'plaintext'. Ensures that
//		* the entire String is displayed unchanged when displayed in the output
//		* format.
//		*
//		* @since 1.25
//		*
//		* @param String $plaintext String to ensure plaintext output of
//		* @param String $format One of the FORMAT_* constants.
//		*
//		* @return String Input plaintext encoded for output to $format
//		*/
//		protected function formatPlaintext($plaintext, $format) {
//			switch ($format) {
//			case XomwMessage.FORMAT_TEXT:
//			case XomwMessage.FORMAT_PLAIN:
//				return $plaintext;
//
//			case XomwMessage.FORMAT_PARSE:
//			case XomwMessage.FORMAT_BLOCK_PARSE:
//			case XomwMessage.FORMAT_ESCAPED:
//			default:
//				return htmlspecialchars($plaintext, ENT_QUOTES);
//
//			}
//		}
//
//		/**
//		* Formats a list of parameters as a concatenated String.
//		* @since 1.29
//		* @param array $prmsVar
//		* @param String $listType
//		* @param String $format One of the FORMAT_* constants.
//		* @return array Array with the parameter type (either "before" or "after") and the value.
//		*/
//		protected function formatListParam(array $prmsVar, $listType, $format) {
//			if (!isset(XomwMessage.$listTypeMap[$listType])) {
//				$warning = 'Invalid list type for message "' . this.getKey() . '": '
//					. htmlspecialchars($listType)
//					. ' (prmsVar are ' . htmlspecialchars(serialize($prmsVar)) . ')';
//				trigger_error($warning, E_USER_WARNING);
//				$e = new Exception;
//				wfDebugLog('Bug58676', $warning . "\n" . $e->getTraceAsString());
//				return [PRM_TID_BEFORE, '[INVALID]' ];
//			}
//			$func = XomwMessage.$listTypeMap[$listType];
//
//			// Handle an empty list sensibly
//			if (!$prmsVar) {
//				return [PRM_TID_BEFORE, this.getLanguage()->$func([]) ];
//			}
//
//			// First, determine what kinds of list items we have
//			$types = [];
//			$vars = [];
//			$list = [];
//			foreach ($prmsVar as $n => $p) {
//				list($type, $value) = this.extractParam($p, $format);
//				$types[$type] = true;
//				$list[] = $value;
//				$vars[] = '$' . ($n + 1);
//			}
//
//			// Easy case: all are 'before' or 'after', so just join the
//			// values and use the same type.
//			if (count($types) === 1) {
//				return [ key($types), this.getLanguage()->$func($list) ];
//			}
//
//			// Hard case: We need to process each value per its type, then
//			// return the concatenated values as 'after'. We handle this by turning
//			// the list into a RawMessage and processing that as a parameter.
//			$vars = this.getLanguage()->$func($vars);
//			return this.extractParam(new RawMessage($vars, $prmsVar), $format);
//		}
}
class XomwMessageVal {
	public int tid;
	public byte[] val;
	public void Set(int tid, byte[] val) {
		this.tid = tid;
		this.val = val;
	}
}
abstract class XomwMessagePrm {
	public XomwMessagePrm(int tid) {this.tid = tid;}
	public int Tid() {return tid;} private int tid;
	public static final int
	  Tid__raw           = 0
	, Tid__num           = 1
	, Tid__duration      = 2
	, Tid__expiry        = 3
	, Tid__period        = 4
	, Tid__size          = 5
	, Tid__bitrate       = 6
	, Tid__plaintext     = 7
	, Tid__list          = 8
	;
}
class XomwMessagePrm_raw extends XomwMessagePrm { 	public byte[] raw;
	public XomwMessagePrm_raw(byte[] raw) {super(Tid__raw);
		this.raw = raw;
	}
}
class XomwMessagePrm_num extends XomwMessagePrm { 	public int num;
	public byte[] numAsBry() {return Int_.To_bry(num);}
	public XomwMessagePrm_num(int num) {super(Tid__num);
		this.num = num;
	}
}
class XomwMessagePrm_duration extends XomwMessagePrm { 	public int duration;
	public XomwMessagePrm_duration(int duration) {super(Tid__duration);
		this.duration = duration;
	}
}
class XomwMessagePrm_expiry extends XomwMessagePrm { 	public byte[] expiry;
	public XomwMessagePrm_expiry(byte[] expiry) {super(Tid__expiry);
		this.expiry = expiry;
	}
}
class XomwMessagePrm_period extends XomwMessagePrm { 	public int period;
	public XomwMessagePrm_period(int period) {super(Tid__period);
		this.period = period;
	}
}
class XomwMessagePrm_size extends XomwMessagePrm { 	public int size;
	public XomwMessagePrm_size(int size) {super(Tid__size);
		this.size = size;
	}
}
class XomwMessagePrm_bitrate extends XomwMessagePrm { 	public int bitrate;
	public XomwMessagePrm_bitrate(int bitrate) {super(Tid__bitrate);
		this.bitrate = bitrate;
	}
}
class XomwMessagePrm_plaintext extends XomwMessagePrm { 	public byte[] plaintext;
	public XomwMessagePrm_plaintext(byte[] plaintext) {super(Tid__plaintext);
		this.plaintext = plaintext;
	}
}
class XomwMessagePrm_list extends XomwMessagePrm { 	public byte[][] list;
	public String type;
	public XomwMessagePrm_list(byte[][] list, String type) {super(Tid__list);
		this.list = list;
		this.type = type;
	}
}
