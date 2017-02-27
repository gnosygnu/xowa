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
package gplx.xowa.mediawiki.includes.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import gplx.core.btries.*;
import gplx.core.net.*;
import gplx.xowa.mediawiki.includes.xohtml.*;
import gplx.xowa.mediawiki.includes.linkers.*;
import gplx.xowa.mediawiki.includes.parsers.tables.*;
import gplx.xowa.mediawiki.includes.parsers.hrs.*;
import gplx.xowa.mediawiki.includes.parsers.doubleunders.*;
import gplx.xowa.mediawiki.includes.parsers.headings.*;
import gplx.xowa.mediawiki.includes.parsers.lnkis.*;
import gplx.xowa.mediawiki.includes.parsers.quotes.*;
import gplx.xowa.mediawiki.includes.parsers.lnkes.*;
import gplx.xowa.mediawiki.includes.parsers.magiclinks.*;
import gplx.xowa.mediawiki.includes.parsers.nbsps.*;
/**
* PHP Parser - Processes wiki markup (which uses a more user-friendly
* syntax, such as "[[link]]" for making links), and provides a one-way
* transformation of that wiki markup it into (X)HTML output / markup
* (which in turn the browser understands, and can display).
*
* There are seven main entry points into the Parser cls:
*
* - Parser::parse()
*     produces HTML output
* - Parser::preSaveTransform()
*     produces altered wiki markup
* - Parser::preprocess()
*     removes HTML comments and expands templates
* - Parser::cleanSig() and Parser::cleanSigInSig()
*     cleans a signature before saving it to preferences
* - Parser::getSection()
*     return the content of a section from an article for section editing
* - Parser::replaceSection()
*     replaces a section by number inside an article
* - Parser::getPreloadText()
*     removes <noinclude> sections and <includeonly> tags
*
* Globals used:
*    Object: $wgContLang
*
* @warning $wgUser or $wgTitle or $wgRequest or $wgLang. Keep them away!
*
* @par Settings:
* $wgNamespacesWithSubpages
*
* @par Settings only within ParserOptions:
* $wgAllowExternalImages
* $wgAllowSpecialInclusion
* $wgInterwikiMagic
* $wgMaxArticleSize
*
* @ingroup Parser
*/
public class XomwParser implements XomwParserIface {
//		/**
//		* Update this version number when the ParserOutput format
//		* changes in an incompatible way, so the parser cache
//		* can automatically discard old data.
//		*/
//		static final VERSION = '1.6.4';
//
//		/**
//		* Update this version number when the output of serialiseHalfParsedText()
//		* changes in an incompatible way
//		*/
//		static final HALF_PARSED_VERSION = 2;
//
//		# Flags for Parser::setFunctionHook
//		static final SFH_NO_HASH = 1;
//		static final SFH_OBJECT_ARGS = 2;
//
//		# Constants needed for external link processing
//		# Everything except bracket, space, or control characters
//		# \p{Zs} is unicode 'separator, space' category. It covers the space 0x20
//		# as well as U+3000 is IDEOGRAPHIC SPACE for T21052
//		static final EXT_LINK_URL_CLASS = '[^][<>"\\x00-\\x20\\x7F\p{Zs}]';
//		# Simplified expression to match an IPv4 or IPv6 address, or
//		# at least one character of a host name (embeds EXT_LINK_URL_CLASS)
//		static final EXT_LINK_ADDR = '(?:[0-9.]+|\\[(?i:[0-9a-f:.]+)\\]|[^][<>"\\x00-\\x20\\x7F\p{Zs}])';
//		# RegExp to make image URLs (embeds IPv6 part of EXT_LINK_ADDR)
//		// @codingStandardsIgnoreStart Generic.Files.LineLength
//		static final EXT_IMAGE_REGEX = '/^(http:\/\/|https:\/\/)((?:\\[(?i:[0-9a-f:.]+)\\])?[^][<>"\\x00-\\x20\\x7F\p{Zs}]+)
//			\\/([A-Za-z0-9_.,~%\\-+&;#*?!=()@\\x80-\\xFF]+)\\.((?i)gif|png|jpg|jpeg)$/Sxu';
//		// @codingStandardsIgnoreEnd
//
//		# Regular expression for a non-newline space
//		static final SPACE_NOT_NL = '(?:\t|&nbsp;|&\#0*160;|&\#[Xx]0*[Aa]0;|\p{Zs})';
//
//		# Flags for preprocessToDom
//		static final PTD_FOR_INCLUSION = 1;
//
//		# Allowed values for this.mOutputType
//		# Parameter to startExternalParse().
//		static final OT_HTML = 1; # like parse()
//		static final OT_WIKI = 2; # like preSaveTransform()
//		static final OT_PREPROCESS = 3; # like preprocess()
//		static final OT_MSG = 3;
//		static final OT_PLAIN = 4; # like extractSections() - portions of the original are returned unchanged.
//
//		/**
//		* @var String Prefix and suffix for temporary replacement strings
//		* for the multipass parser.
//		*
//		* \x7f should never appear in input as it's disallowed in XML.
//		* Using it at the front also gives us a little extra robustness
//		* since it shouldn't match when butted up against identifier-like
//		* String constructs.
//		*
//		* Must not consist of all title characters, or else it will change
//		* the behavior of <nowiki> in a link.
//		*
//		* Must have a character that needs escaping in attributes, otherwise
//		* someone could put a strip marker in an attribute, to get around
//		* escaping quote marks, and break out of the attribute. Thus we add
//		* `'".
//		*/
//		static final MARKER_SUFFIX = "-QINU`\"'\x7f";
//		static final MARKER_PREFIX = "\x7f'\"`UNIQ-";
//
//		# Markers used for wrapping the table of contents
//		static final TOC_START = '<mw:toc>';
//		static final TOC_END = '</mw:toc>';
//
//		# Persistent:
//		public $mTagHooks = [];
//		public $mTransparentTagHooks = [];
//		public $mFunctionHooks = [];
//		public $mFunctionSynonyms = [ 0 => [], 1 => [] ];
//		public $mFunctionTagHooks = [];
//		public $mStripList = [];
//		public $mDefaultStripList = [];
//		public $mVarCache = [];
//		public $mImageParams = [];
//		public $mImageParamsMagicArray = [];
//		public $mMarkerIndex = 0;
//		public $mFirstCall = true;
//
//		# Initialised by initialiseVariables()
//
//		/**
//		* @var MagicWordArray
//		*/
//		public $mVariables;
//
//		/**
//		* @var MagicWordArray
//		*/
//		public $mSubstWords;
//		# Initialised in constructor
//		public $mConf, $mExtLinkBracketedRegex, $mUrlProtocols;
//
//		# Initialized in getPreprocessor()
//		/** @var Preprocessor */
//		public $mPreprocessor;
//
//		# Cleared with clearState():
//		/**
//		* @var ParserOutput
//		*/
//		public $mOutput;
//		public $mAutonumber;

	/**
	* @var StripState
	*/
	public XomwStripState mStripState = new XomwStripState();

//		public $mIncludeCount;
	/**
	* @var LinkHolderArray
	*/
	public XomwLinkHolderArray mLinkHolders;

	private int mLinkID;
//		public $mIncludeSizes, $mPPNodeCount, $mGeneratedPPNodeCount, $mHighestExpansionDepth;
//		public $mDefaultSort;
//		public $mTplRedirCache, $mTplDomCache, $mHeadings, $mDoubleUnderscores;
//		public $mExpensiveFunctionCount; # number of expensive parser function calls
//		public $mShowToc, $mForceTocPosition;
//
//		/**
//		* @var User
//		*/
//		public $mUser; # User Object; only used when doing pre-save transform
//
//		# Temporary
//		# These are variables reset at least once per parse regardless of $clearState

	/**
	* @var ParserOptions
	*/
	public XomwParserOptions mOptions = new XomwParserOptions();

//		/**
//		* @var Title
//		*/
//		public $mTitle;        # Title context, used for self-link rendering and similar things
//		public $mOutputType;   # Output type, one of the OT_xxx constants
//		public $ot;            # Shortcut alias, see setOutputType()
//		public $mRevisionObject; # The revision Object of the specified revision ID
//		public $mRevisionId;   # ID to display in {{REVISIONID}} tags
//		public $mRevisionTimestamp; # The timestamp of the specified revision ID
//		public $mRevisionUser; # User to display in {{REVISIONUSER}} tag
//		public $mRevisionSize; # Size to display in {{REVISIONSIZE}} variable
//		public $mRevIdForTs;   # The revision ID which was used to fetch the timestamp
//		public $mInputSize = false; # For {{PAGESIZE}} on current page.
//
//		/**
//		* @var String Deprecated accessor for the strip marker prefix.
//		* @deprecated since 1.26; use Parser::MARKER_PREFIX instead.
//		*/
//		public $mUniqPrefix = Parser::MARKER_PREFIX;
//
//		/**
//		* @var array Array with the language name of each language link (i.e. the
//		* interwiki prefix) in the key, value arbitrary. Used to avoid sending
//		* duplicate language links to the ParserOutput.
//		*/
//		public $mLangLinkLanguages;
//
//		/**
//		* @var MapCacheLRU|null
//		* @since 1.24
//		*
//		* A cache of the current revisions of titles. Keys are $title->getPrefixedDbKey()
//		*/
//		public $currentRevisionCache;
//
//		/**
//		* @var boolean Recursive call protection.
//		* This variable should be treated as if it were private.
//		*/
//		public $mInParse = false;
//
//		/** @var SectionProfiler */
//		protected $mProfiler;

	/**
	* @var LinkRenderer
	*/
	private XomwLinkRenderer mLinkRenderer;

	private int mMarkerIndex = 0;

	// XOWA
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private final    XomwEnv env;
	private final    XomwSanitizer sanitizer = new XomwSanitizer();
	private final    Xomw_table_wkr tableWkr;
	private final    Xomw_hr_wkr hrWkr = new Xomw_hr_wkr();
	private final    Xomw_doubleunder_wkr doubleunderWkr = new Xomw_doubleunder_wkr();
	private final    Xomw_heading_wkr headingWkr = new Xomw_heading_wkr();
	private final    Xomw_heading_cbk__html headingWkrCbk = new Xomw_heading_cbk__html();
	private final    Xomw_lnki_wkr lnkiWkr;
	private final    Xomw_quote_wkr quoteWkr;
	private final    Xomw_lnke_wkr lnkeWkr;
	private final    Xomw_magiclinks_wkr magiclinksWkr;
	private final    Xomw_nbsp_wkr nbspWkr = new Xomw_nbsp_wkr();
	private final    XomwBlockLevelPass blockWkr = new XomwBlockLevelPass();
	private final    Xomw_doubleunder_data doubleunder_data = new Xomw_doubleunder_data();
	private static Xomw_regex_space regex_space;
	private static Xomw_regex_boundary regex_boundary;
	private static Xomw_regex_url regex_url;
	private final    Btrie_rv trv = new Btrie_rv();
	private final    Bry_bfr tmp = Bry_bfr_.New();

	public XomwSanitizer Sanitizer() {return sanitizer;}
	public XomwStripState Strip_state() {return mStripState;}

//		/**
//		* @param array $conf
//		*/
//		public function __construct($conf = []) {
//			this.mConf = $conf;
//			this.mUrlProtocols = wfUrlProtocols();
//			this.mExtLinkBracketedRegex = '/\[(((?i)' . this.mUrlProtocols . ')' .
//				self::EXT_LINK_ADDR .
//				self::EXT_LINK_URL_CLASS . '*)\p{Zs}*([^\]\\x00-\\x08\\x0a-\\x1F]*?)\]/Su';
//			if (isset($conf['preprocessorClass'])) {
//				this.mPreprocessorClass = $conf['preprocessorClass'];
//			} elseif (defined('HPHP_VERSION')) {
//				# Preprocessor_Hash is much faster than Preprocessor_DOM under HipHop
//				this.mPreprocessorClass = 'Preprocessor_Hash';
//			} elseif (extension_loaded('domxml')) {
//				# PECL extension that conflicts with the core DOM extension (T15770)
//				wfDebug("Warning: you have the obsolete domxml extension for PHP. Please remove it!\n");
//				this.mPreprocessorClass = 'Preprocessor_Hash';
//			} elseif (extension_loaded('dom')) {
//				this.mPreprocessorClass = 'Preprocessor_DOM';
//			} else {
//				this.mPreprocessorClass = 'Preprocessor_Hash';
//			}
//			wfDebug(__CLASS__ . ": using preprocessor: {this.mPreprocessorClass}\n");
//		}
	private final    Btrie_slim_mgr protocols_trie;
	public XomwEnv Env() {return env;}
	public Xomw_lnki_wkr Lnki_wkr() {return lnkiWkr;}
	public XomwLinker              Linker()          {return linker;}         private final    XomwLinker linker;
	public byte[] Get_external_link_rel;
	private static byte[] Atr__rel;
	public XomwParser(XomwEnv env) {
		if (regex_space == null) {
			synchronized (Type_adp_.ClassOf_obj(this)) {
				regex_space = new Xomw_regex_space();
				regex_boundary = new Xomw_regex_boundary(regex_space);
				regex_url = new Xomw_regex_url(regex_space);
				Atr__rel = Bry_.new_a7("rel");
				Get_external_link_rel = Bry_.new_a7("nofollow");
			}
		}

		this.env = env;
		this.mLinkRenderer = new XomwLinkRenderer(sanitizer);
		this.linker = new XomwLinker(mLinkRenderer);
		this.protocols_trie = XomwParser.Protocols__dflt();
		this.mLinkHolders = new XomwLinkHolderArray(this);

		this.tableWkr = new Xomw_table_wkr(tmp_bfr, sanitizer, mStripState);
		this.quoteWkr = new Xomw_quote_wkr(tmp_bfr);
		this.lnkiWkr = new Xomw_lnki_wkr(this, mLinkHolders, mLinkRenderer, protocols_trie, linker, quoteWkr, tmp_bfr, mStripState);
		this.lnkeWkr = new Xomw_lnke_wkr(this, tmp_bfr, linker, sanitizer);
		this.magiclinksWkr = new Xomw_magiclinks_wkr(this, sanitizer, linker, regex_boundary, regex_url);
	}
	public void Init_by_wiki(Xowe_wiki wiki) {
		linker.Init_by_wiki(env, wiki.Lang().Lnki_trail_mgr().Trie());
		lnkeWkr.Init_by_wiki(protocols_trie, regex_url, regex_space);
		lnkiWkr.Init_by_wiki(env, wiki);
		doubleunderWkr.Init_by_wiki(doubleunder_data, wiki.Lang());
		magiclinksWkr.Init_by_wiki();
	}
	public void Init_by_page(XomwTitle ttl) {
//			pctx.Init_by_page(ttl);
	}

//		/**
//		* Reduce memory usage to reduce the impact of circular references
//		*/
//		public function __destruct() {
//			if (isset(this.mLinkHolders)) {
//				unset(this.mLinkHolders);
//			}
//			foreach ($this as $name => $value) {
//				unset(this.$name);
//			}
//		}
//
//		/**
//		* Allow extensions to clean up when the parser is cloned
//		*/
//		public function __clone() {
//			this.mInParse = false;
//
//			// T58226: When you create a reference "to" an Object field, that
//			// makes the Object field itself be a reference too (until the other
//			// reference goes out of scope). When cloning, any field that's a
//			// reference is copied as a reference in the new Object. Both of these
//			// are defined PHP5 behaviors, as inconvenient as it is for us when old
//			// hooks from PHP4 days are passing fields by reference.
//			foreach ([ 'mStripState', 'mVarCache' ] as $k) {
//				// Make a non-reference copy of the field, then rebind the field to
//				// reference the new copy.
//				$tmp = this.$k;
//				this.$k =& $tmp;
//				unset($tmp);
//			}
//
//			Hooks::run('ParserCloned', [ $this ]);
//		}
//
//		/**
//		* Do various kinds of initialisation on the first call of the parser
//		*/
//		public function firstCallInit() {
//			if (!this.mFirstCall) {
//				return;
//			}
//			this.mFirstCall = false;
//
//			CoreParserFunctions::register($this);
//			CoreTagHooks::register($this);
//			this.initialiseVariables();
//
//			Hooks::run('ParserFirstCallInit', [ &$this ]);
//		}

	/**
	* Clear Parser state
	*
	* @private
	*/
	public void clearState() {
//			if (this.mFirstCall) {
//				this.firstCallInit();
//			}
//			this.mOutput = new ParserOutput;
//			this.mOptions->registerWatcher([ this.mOutput, 'recordOption' ]);
//			this.mAutonumber = 0;
//			this.mIncludeCount = [];
		this.mLinkHolders = new XomwLinkHolderArray(this);
//			this.mLinkID = 0;
//			this.mRevisionObject = this.mRevisionTimestamp =
//				this.mRevisionId = this.mRevisionUser = this.mRevisionSize = null;
//			this.mVarCache = [];
//			this.mUser = null;
//			this.mLangLinkLanguages = [];
//			this.currentRevisionCache = null;

		this.mStripState = new XomwStripState();

//			# Clear these on every parse, T6549
//			this.mTplRedirCache = this.mTplDomCache = [];
//
//			this.mShowToc = true;
//			this.mForceTocPosition = false;
//			this.mIncludeSizes = [
//				'post-expand' => 0,
//				'arg' => 0,
//			];
//			this.mPPNodeCount = 0;
//			this.mGeneratedPPNodeCount = 0;
//			this.mHighestExpansionDepth = 0;
//			this.mDefaultSort = false;
//			this.mHeadings = [];
//			this.mDoubleUnderscores = [];
//			this.mExpensiveFunctionCount = 0;
//
//			# Fix cloning
//			if (isset(this.mPreprocessor) && this.mPreprocessor->parser !== $this) {
//				this.mPreprocessor = null;
//			}
//
//			this.mProfiler = new SectionProfiler();
//
//			Hooks::run('ParserClearState', [ &$this ]);
	}

	/**
	* Convert wikitext to HTML
	* Do not call this function recursively.
	*
	* @param String $text Text we want to parse
	* @param Title $title
	* @param ParserOptions $options
	* @param boolean $linestart
	* @param boolean $clearState
	* @param int $revid Number to pass in {{REVISIONID}}
	* @return ParserOutput A ParserOutput
	*/
//		public function parse(
//			$text, Title $title, ParserOptions $options,
//			$linestart = true, $clearState = true, $revid = null
//		) {
	public void parse(XomwParserBfr pbfr, XomwParserCtx pctx,byte[] text, XomwTitle title, XomwParserOptions options) {this.parse(pbfr, pctx, text, title, options, true, true, -1);}
	public void parse(XomwParserBfr pbfr, XomwParserCtx pctx,
		byte[] text, XomwTitle title, XomwParserOptions options,
		boolean linestart, boolean clearState, int revid
	) {
		/**
		* First pass--just handle <nowiki> sections, pass the rest off
		* to internalParse() which does all the real work.
		*/

//			global $wgShowHostnames;
//
//			if ($clearState) {
//				// We use U+007F DELETE to construct strip markers, so we have to make
//				// sure that this character does not occur in the input text.
//				$text = strtr($text, "\x7f", "?");
//				$magicScopeVariable = this.synchronized();
//			}
//
//			this.startParse($title, $options, self::OT_HTML, $clearState);
//
//			this.currentRevisionCache = null;
//			this.mInputSize = strlen($text);
//			if (this.mOptions->getEnableLimitReport()) {
//				this.mOutput->resetParseStartTime();
//			}
//
//			$oldRevisionId = this.mRevisionId;
//			$oldRevisionObject = this.mRevisionObject;
//			$oldRevisionTimestamp = this.mRevisionTimestamp;
//			$oldRevisionUser = this.mRevisionUser;
//			$oldRevisionSize = this.mRevisionSize;
//			if ($revid !== null) {
//				this.mRevisionId = $revid;
//				this.mRevisionObject = null;
//				this.mRevisionTimestamp = null;
//				this.mRevisionUser = null;
//				this.mRevisionSize = null;
//			}
//
//			Hooks::run('ParserBeforeStrip', [ &$this, &$text, &this.mStripState ]);
//			# No more strip!
//			Hooks::run('ParserAfterStrip', [ &$this, &$text, &this.mStripState ]);
		this.internalParse(pbfr, pctx, text);
//			Hooks::run('ParserAfterParse', [ &$this, &$text, &this.mStripState ]);

		this.internalParseHalfParsed(pbfr, pctx, true, linestart);

//			/**
//			* A converted title will be provided in the output Object if title and
//			* content conversion are enabled, the article text does not contain
//			* a conversion-suppressing double-underscore tag, and no
//			* {{DISPLAYTITLE:...}} is present. DISPLAYTITLE takes precedence over
//			* automatic link conversion.
//			*/
//			if (!($options->getDisableTitleConversion()
//				|| isset(this.mDoubleUnderscores['nocontentconvert'])
//				|| isset(this.mDoubleUnderscores['notitleconvert'])
//				|| this.mOutput->getDisplayTitle() !== false)
//			) {
//				$convruletitle = this.getConverterLanguage()->getConvRuleTitle();
//				if ($convruletitle) {
//					this.mOutput->setTitleText($convruletitle);
//				} else {
//					$titleText = this.getConverterLanguage()->convertTitle($title);
//					this.mOutput->setTitleText($titleText);
//				}
//			}
//
//			# Done parsing! Compute runtime adaptive expiry if set
//			this.mOutput->finalizeAdaptiveCacheExpiry();
//
//			# Warn if too many heavyweight parser functions were used
//			if (this.mExpensiveFunctionCount > this.mOptions->getExpensiveParserFunctionLimit()) {
//				this.limitationWarn('expensive-parserfunction',
//					this.mExpensiveFunctionCount,
//					this.mOptions->getExpensiveParserFunctionLimit()
//				);
//			}
//
//			# Information on include size limits, for the benefit of users who try to skirt them
//			if (this.mOptions->getEnableLimitReport()) {
//				$max = this.mOptions->getMaxIncludeSize();
//
//				$cpuTime = this.mOutput->getTimeSinceStart('cpu');
//				if ($cpuTime !== null) {
//					this.mOutput->setLimitReportData('limitreport-cputime',
//						sprintf("%.3f", $cpuTime)
//					);
//				}
//
//				$wallTime = this.mOutput->getTimeSinceStart('wall');
//				this.mOutput->setLimitReportData('limitreport-walltime',
//					sprintf("%.3f", $wallTime)
//				);
//
//				this.mOutput->setLimitReportData('limitreport-ppvisitednodes',
//					[ this.mPPNodeCount, this.mOptions->getMaxPPNodeCount() ]
//				);
//				this.mOutput->setLimitReportData('limitreport-ppgeneratednodes',
//					[ this.mGeneratedPPNodeCount, this.mOptions->getMaxGeneratedPPNodeCount() ]
//				);
//				this.mOutput->setLimitReportData('limitreport-postexpandincludesize',
//					[ this.mIncludeSizes['post-expand'], $max ]
//				);
//				this.mOutput->setLimitReportData('limitreport-templateargumentsize',
//					[ this.mIncludeSizes['arg'], $max ]
//				);
//				this.mOutput->setLimitReportData('limitreport-expansiondepth',
//					[ this.mHighestExpansionDepth, this.mOptions->getMaxPPExpandDepth() ]
//				);
//				this.mOutput->setLimitReportData('limitreport-expensivefunctioncount',
//					[ this.mExpensiveFunctionCount, this.mOptions->getExpensiveParserFunctionLimit() ]
//				);
//				Hooks::run('ParserLimitReportPrepare', [ $this, this.mOutput ]);
//
//				$limitReport = "NewPP limit report\n";
//				if ($wgShowHostnames) {
//					$limitReport .= 'Parsed by ' . wfHostname() . "\n";
//				}
//				$limitReport .= 'Cached time: ' . this.mOutput->getCacheTime() . "\n";
//				$limitReport .= 'Cache expiry: ' . this.mOutput->getCacheExpiry() . "\n";
//				$limitReport .= 'Dynamic content: ' .
//					(this.mOutput->hasDynamicContent() ? 'true' : 'false') .
//					"\n";
//
//				foreach (this.mOutput->getLimitReportData() as $key => $value) {
//					if (Hooks::run('ParserLimitReportFormat',
//						[ $key, &$value, &$limitReport, false, false ]
//					)) {
//						$keyMsg = wfMessage($key)->inLanguage('en')->useDatabase(false);
//						$valueMsg = wfMessage([ "$key-value-text", "$key-value" ])
//							->inLanguage('en')->useDatabase(false);
//						if (!$valueMsg->exists()) {
//							$valueMsg = new RawMessage('$1');
//						}
//						if (!$keyMsg->isDisabled() && !$valueMsg->isDisabled()) {
//							$valueMsg->params($value);
//							$limitReport .= "{$keyMsg->text()}: {$valueMsg->text()}\n";
//						}
//					}
//				}
//				// Since we're not really outputting HTML, decode the entities and
//				// then re-encode the things that need hiding inside HTML comments.
//				$limitReport = htmlspecialchars_decode($limitReport);
//				Hooks::run('ParserLimitReport', [ $this, &$limitReport ]);
//
//				// Sanitize for comment. Note '-' in the replacement is U+2010,
//				// which looks much like the problematic '-'.
//				$limitReport = str_replace([ '-', '&' ], [ '-', '&amp;' ], $limitReport);
//				$text .= "\n<!-- \n$limitReport-->\n";
//
//				// Add on template profiling data in human/machine readable way
//				$dataByFunc = this.mProfiler->getFunctionStats();
//				uasort($dataByFunc, function ($a, $b) {
//					return $a['real'] < $b['real']; // descending order
//				});
//				$profileReport = [];
//				foreach (array_slice($dataByFunc, 0, 10) as $item) {
//					$profileReport[] = sprintf("%6.2f%% %8.3f %6d %s",
//						$item['%real'], $item['real'], $item['calls'],
//						htmlspecialchars($item['name']));
//				}
//				$text .= "<!--\nTransclusion expansion time report (%,ms,calls,template)\n";
//				$text .= implode("\n", $profileReport) . "\n-->\n";
//
//				this.mOutput->setLimitReportData('limitreport-timingprofile', $profileReport);
//
//				// Add other cache related metadata
//				if ($wgShowHostnames) {
//					this.mOutput->setLimitReportData('cachereport-origin', wfHostname());
//				}
//				this.mOutput->setLimitReportData('cachereport-timestamp',
//					this.mOutput->getCacheTime());
//				this.mOutput->setLimitReportData('cachereport-ttl',
//					this.mOutput->getCacheExpiry());
//				this.mOutput->setLimitReportData('cachereport-transientcontent',
//					this.mOutput->hasDynamicContent());
//
//				if (this.mGeneratedPPNodeCount > this.mOptions->getMaxGeneratedPPNodeCount() / 10) {
//					wfDebugLog('generated-pp-node-count', this.mGeneratedPPNodeCount . ' ' .
//						this.mTitle->getPrefixedDBkey());
//				}
//			}
//			this.mOutput->setText($text);
//
//			this.mRevisionId = $oldRevisionId;
//			this.mRevisionObject = $oldRevisionObject;
//			this.mRevisionTimestamp = $oldRevisionTimestamp;
//			this.mRevisionUser = $oldRevisionUser;
//			this.mRevisionSize = $oldRevisionSize;
//			this.mInputSize = false;
//			this.currentRevisionCache = null;
//
//			return this.mOutput;
	}

//		/**
//		* Half-parse wikitext to half-parsed HTML. This recursive parser entry point
//		* can be called from an extension tag hook.
//		*
//		* The output of this function IS NOT SAFE PARSED HTML; it is "half-parsed"
//		* instead, which means that lists and links have not been fully parsed yet,
//		* and strip markers are still present.
//		*
//		* Use recursiveTagParseFully() to fully parse wikitext to output-safe HTML.
//		*
//		* Use this function if you're a parser tag hook and you want to parse
//		* wikitext before or after applying additional transformations, and you
//		* intend to *return the result as hook output*, which will cause it to go
//		* through the rest of parsing process automatically.
//		*
//		* If $frame is not provided, then template variables (e.g., {{{1}}}) within
//		* $text are not expanded
//		*
//		* @param String $text Text extension wants to have parsed
//		* @param boolean|PPFrame $frame The frame to use for expanding any template variables
//		* @return String UNSAFE half-parsed HTML
//		*/
//		public function recursiveTagParse($text, $frame = false) {
//			Hooks::run('ParserBeforeStrip', [ &$this, &$text, &this.mStripState ]);
//			Hooks::run('ParserAfterStrip', [ &$this, &$text, &this.mStripState ]);
//			$text = this.internalParse($text, false, $frame);
//			return $text;
//		}
//
//		/**
//		* Fully parse wikitext to fully parsed HTML. This recursive parser entry
//		* point can be called from an extension tag hook.
//		*
//		* The output of this function is fully-parsed HTML that is safe for output.
//		* If you're a parser tag hook, you might want to use recursiveTagParse()
//		* instead.
//		*
//		* If $frame is not provided, then template variables (e.g., {{{1}}}) within
//		* $text are not expanded
//		*
//		* @since 1.25
//		*
//		* @param String $text Text extension wants to have parsed
//		* @param boolean|PPFrame $frame The frame to use for expanding any template variables
//		* @return String Fully parsed HTML
//		*/
//		public function recursiveTagParseFully($text, $frame = false) {
//			$text = this.recursiveTagParse($text, $frame);
//			$text = this.internalParseHalfParsed($text, false);
//			return $text;
//		}
//
//		/**
//		* Expand templates and variables in the text, producing valid, static wikitext.
//		* Also removes comments.
//		* Do not call this function recursively.
//		* @param String $text
//		* @param Title $title
//		* @param ParserOptions $options
//		* @param int|null $revid
//		* @param boolean|PPFrame $frame
//		* @return mixed|String
//		*/
//		public function preprocess($text, Title $title = null,
//			ParserOptions $options, $revid = null, $frame = false
//		) {
//			$magicScopeVariable = this.synchronized();
//			this.startParse($title, $options, self::OT_PREPROCESS, true);
//			if ($revid !== null) {
//				this.mRevisionId = $revid;
//			}
//			Hooks::run('ParserBeforeStrip', [ &$this, &$text, &this.mStripState ]);
//			Hooks::run('ParserAfterStrip', [ &$this, &$text, &this.mStripState ]);
//			$text = this.replaceVariables($text, $frame);
//			$text = this.mStripState->unstripBoth($text);
//			return $text;
//		}
//
//		/**
//		* Recursive parser entry point that can be called from an extension tag
//		* hook.
//		*
//		* @param String $text Text to be expanded
//		* @param boolean|PPFrame $frame The frame to use for expanding any template variables
//		* @return String
//		* @since 1.19
//		*/
//		public function recursivePreprocess($text, $frame = false) {
//			$text = this.replaceVariables($text, $frame);
//			$text = this.mStripState->unstripBoth($text);
//			return $text;
//		}
//
//		/**
//		* Process the wikitext for the "?preload=" feature. (T7210)
//		*
//		* "<noinclude>", "<includeonly>" etc. are parsed as for template
//		* transclusion, comments, templates, arguments, tags hooks and parser
//		* functions are untouched.
//		*
//		* @param String $text
//		* @param Title $title
//		* @param ParserOptions $options
//		* @param array $params
//		* @return String
//		*/
//		public function getPreloadText($text, Title $title, ParserOptions $options, $params = []) {
//			$msg = new RawMessage($text);
//			$text = $msg->params($params)->plain();
//
//			# Parser (re)initialisation
//			$magicScopeVariable = this.synchronized();
//			this.startParse($title, $options, self::OT_PLAIN, true);
//
//			$flags = PPFrame::NO_ARGS | PPFrame::NO_TEMPLATES;
//			$dom = this.preprocessToDom($text, self::PTD_FOR_INCLUSION);
//			$text = this.getPreprocessor()->newFrame()->expand($dom, $flags);
//			$text = this.mStripState->unstripBoth($text);
//			return $text;
//		}
//
//		/**
//		* Get a random String
//		*
//		* @return String
//		* @deprecated since 1.26; use wfRandomString() instead.
//		*/
//		public static function getRandomString() {
//			wfDeprecated(__METHOD__, '1.26');
//			return wfRandomString(16);
//		}
//
//		/**
//		* Set the current user.
//		* Should only be used when doing pre-save transform.
//		*
//		* @param User|null $user User Object or null (to reset)
//		*/
//		public function setUser($user) {
//			this.mUser = $user;
//		}
//
//		/**
//		* Accessor for mUniqPrefix.
//		*
//		* @return String
//		* @deprecated since 1.26; use Parser::MARKER_PREFIX instead.
//		*/
//		public function uniqPrefix() {
//			wfDeprecated(__METHOD__, '1.26');
//			return self::MARKER_PREFIX;
//		}
//
//		/**
//		* Set the context title
//		*
//		* @param Title $t
//		*/
//		public function setTitle($t) {
//			if (!$t) {
//				$t = Title::newFromText('NO TITLE');
//			}
//
//			if ($t->hasFragment()) {
//				# Strip the fragment to avoid various odd effects
//				this.mTitle = $t->createFragmentTarget('');
//			} else {
//				this.mTitle = $t;
//			}
//		}
//
//		/**
//		* Accessor for the Title Object
//		*
//		* @return Title
//		*/
//		public function getTitle() {
//			return this.mTitle;
//		}
//
//		/**
//		* Accessor/mutator for the Title Object
//		*
//		* @param Title $x Title Object or null to just get the current one
//		* @return Title
//		*/
//		public function Title($x = null) {
//			return wfSetVar(this.mTitle, $x);
//		}
//
//		/**
//		* Set the output type
//		*
//		* @param int $ot New value
//		*/
//		public function setOutputType($ot) {
//			this.mOutputType = $ot;
//			# Shortcut alias
//			this.ot = [
//				'html' => $ot == self::OT_HTML,
//				'wiki' => $ot == self::OT_WIKI,
//				'pre' => $ot == self::OT_PREPROCESS,
//				'plain' => $ot == self::OT_PLAIN,
//			];
//		}
//
//		/**
//		* Accessor/mutator for the output type
//		*
//		* @param int|null $x New value or null to just get the current one
//		* @return int
//		*/
//		public function OutputType($x = null) {
//			return wfSetVar(this.mOutputType, $x);
//		}
//
//		/**
//		* Get the ParserOutput Object
//		*
//		* @return ParserOutput
//		*/
//		public function getOutput() {
//			return this.mOutput;
//		}

	/**
	* Get the ParserOptions Object
	*
	* @return ParserOptions
	*/
	public XomwParserOptions getOptions() {
		return this.mOptions;
	}


//		/**
//		* Accessor/mutator for the ParserOptions Object
//		*
//		* @param ParserOptions $x New value or null to just get the current one
//		* @return ParserOptions Current ParserOptions Object
//		*/
//		public function Options($x = null) {
//			return wfSetVar(this.mOptions, $x);
//		}

	/**
	* @return int
	*/
	public int nextLinkID() {
		return this.mLinkID++;
	}

//		/**
//		* @param int $id
//		*/
//		public function setLinkID($id) {
//			this.mLinkID = $id;
//		}
//
//		/**
//		* Get a language Object for use in parser functions such as {{FORMATNUM:}}
//		* @return Language
//		*/
//		public function getFunctionLang() {
//			return this.getTargetLanguage();
//		}
//
//		/**
//		* Get the target language for the content being parsed. This is usually the
//		* language that the content is in.
//		*
//		* @since 1.19
//		*
//		* @throws MWException
//		* @return Language
//		*/
//		public function getTargetLanguage() {
//			$target = this.mOptions->getTargetLanguage();
//
//			if ($target !== null) {
//				return $target;
//			} elseif (this.mOptions->getInterfaceMessage()) {
//				return this.mOptions->getUserLangObj();
//			} elseif (is_null(this.mTitle)) {
//				throw new MWException(__METHOD__ . ': this.mTitle is null');
//			}
//
//			return this.mTitle->getPageLanguage();
//		}
//
//		/**
//		* Get the language Object for language conversion
//		* @return Language|null
//		*/
//		public function getConverterLanguage() {
//			return this.getTargetLanguage();
//		}
//
//		/**
//		* Get a User Object either from this.mUser, if set, or from the
//		* ParserOptions Object otherwise
//		*
//		* @return User
//		*/
//		public function getUser() {
//			if (!is_null(this.mUser)) {
//				return this.mUser;
//			}
//			return this.mOptions->getUser();
//		}
//
//		/**
//		* Get a preprocessor Object
//		*
//		* @return Preprocessor
//		*/
//		public function getPreprocessor() {
//			if (!isset(this.mPreprocessor)) {
//				$class = this.mPreprocessorClass;
//				this.mPreprocessor = new $class($this);
//			}
//			return this.mPreprocessor;
//		}

	/**
	* Get a LinkRenderer instance to make links with
	*
	* @since 1.28
	* @return LinkRenderer
	*/
	public XomwLinkRenderer getLinkRenderer() {
		if (this.mLinkRenderer == null) {
//				this.mLinkRenderer = XomwMediaWikiServices.getInstance()
//					.getLinkRendererFactory()->create();
//				this.mLinkRenderer->setStubThreshold(
//					this.getOptions()->getStubThreshold()
//				);
			this.mLinkRenderer = new XomwLinkRenderer(sanitizer);
		}

		return this.mLinkRenderer;
	}

//		/**
//		* Replaces all occurrences of HTML-style comments and the given tags
//		* in the text with a random marker and returns the next text. The output
//		* parameter $matches will be an associative array filled with data in
//		* the form:
//		*
//		* @code
//		*   'UNIQ-xxxxx' => [
//		*     'element',
//		*     'tag content',
//		*     [ 'param' => 'x' ],
//		*     '<element param="x">tag content</element>' ]
//		* @endcode
//		*
//		* @param array $elements List of element names. Comments are always extracted.
//		* @param String $text Source text String.
//		* @param array $matches Out parameter, Array: extracted tags
//		* @param String|null $uniq_prefix
//		* @return String Stripped text
//		* @since 1.26 The uniq_prefix argument is deprecated.
//		*/
//		public static function extractTagsAndParams($elements, $text, &$matches, $uniq_prefix = null) {
//			if ($uniq_prefix !== null) {
//				wfDeprecated(__METHOD__ . ' called with $prefix argument', '1.26');
//			}
//			static $n = 1;
//			$stripped = '';
//			$matches = [];
//
//			$taglist = implode('|', $elements);
//			$start = "/<($taglist)(\\s+[^>]*?|\\s*?)(\/?" . ">)|<(!--)/i";
//
//			while ($text != '') {
//				$p = preg_split($start, $text, 2, PREG_SPLIT_DELIM_CAPTURE);
//				$stripped .= $p[0];
//				if (count($p) < 5) {
//					break;
//				}
//				if (count($p) > 5) {
//					# comment
//					$element = $p[4];
//					$attributes = '';
//					$close = '';
//					$inside = $p[5];
//				} else {
//					# tag
//					$element = $p[1];
//					$attributes = $p[2];
//					$close = $p[3];
//					$inside = $p[4];
//				}
//
//				$marker = self::MARKER_PREFIX . "-$element-" . sprintf('%08X', $n++) . self::MARKER_SUFFIX;
//				$stripped .= $marker;
//
//				if ($close === '/>') {
//					# Empty element tag, <tag />
//					$content = null;
//					$text = $inside;
//					$tail = null;
//				} else {
//					if ($element === '!--') {
//						$end = '/(-->)/';
//					} else {
//						$end = "/(<\\/$element\\s*>)/i";
//					}
//					$q = preg_split($end, $inside, 2, PREG_SPLIT_DELIM_CAPTURE);
//					$content = $q[0];
//					if (count($q) < 3) {
//						# No end tag -- let it run out to the end of the text.
//						$tail = '';
//						$text = '';
//					} else {
//						$tail = $q[1];
//						$text = $q[2];
//					}
//				}
//
//				$matches[$marker] = [ $element,
//					$content,
//					Sanitizer::decodeTagAttributes($attributes),
//					"<$element$attributes$close$content$tail" ];
//			}
//			return $stripped;
//		}
//
//		/**
//		* Get a list of strippable XML-like elements
//		*
//		* @return array
//		*/
//		public function getStripList() {
//			return this.mStripList;
//		}

	/**
	* Add an item to the strip state
	* Returns the unique tag which must be inserted into the stripped text
	* The tag will be replaced with the original text in unstrip()
	*
	* @param String $text
	*
	* @return String
	*/
	public byte[] Insert_strip_item(byte[] text) {
		byte[] marker = tmp.Add_bry_many(MARKER_PREFIX, STRIP_ITEM).Add_int_variable(this.mMarkerIndex).Add(MARKER_SUFFIX).To_bry_and_clear();
		this.mMarkerIndex++;
		this.mStripState.addGeneral(marker, text);
		return marker;
	}

	/**
	* parse the wiki syntax used to render tables
	*
	* @private
	* @param String $text
	* @return String
	*/
	// XO.MOVED to Xomw_table_Wkr
	// public function doTableStuff($text) {}

	/**
	* Helper function for parse() that transforms wiki markup into half-parsed
	* HTML. Only called for $mOutputType == self::OT_HTML.
	*
	* @private
	*
	* @param String $text The text to parse
	* @param boolean $isMain Whether this is being called from the main parse() function
	* @param PPFrame|boolean $frame A pre-processor frame
	*
	* @return String
	*/
	// isMain=tru
	public void internalParse(XomwParserBfr pbfr, XomwParserCtx pctx, byte[] text) {internalParse(pbfr, pctx, text, true, false);}
	public void internalParse(XomwParserBfr pbfr, XomwParserCtx pctx, byte[] text, boolean isMain, boolean frame) {
		pbfr.Init(text);
//			$origText = text;

		// MW.HOOK:ParserBeforeInternalParse

		// if $frame is provided, then use $frame for replacing any variables
//			if ($frame) {
			// use frame depth to infer how include/noinclude tags should be handled
			// depth=0 means this is the top-level document; otherwise it's an included document
//				boolean for_inclusion = false;
//				if (!$frame->depth) {
//					$flag = 0;
//				} else {
//					$flag = Parser::PTD_FOR_INCLUSION;
//				}
//				text = prepro_wkr.Preprocess_to_xml(text, for_inclusion);
			// text = $frame->expand($dom);
//			} else {
//				// if $frame is not provided, then use old-style replaceVariables
//				text = $this->replaceVariables(text);
//			}

		// MW.HOOK:InternalParseBeforeSanitize
//			text = Sanitizer::removeHTMLtags(
//				text,
//				[ &$this, 'attributeStripCallback' ],
//				false,
//				array_keys($this->mTransparentTagHooks),
//				[],
//				[ &$this, 'addTrackingCategory' ]
//			);
		// MW.HOOK:InternalParseBeforeLinks

		// Tables need to come after variable replacement for things to work
		// properly; putting them before other transformations should keep
		// exciting things like link expansions from showing up in surprising
		// places.
		tableWkr.doTableStuff(pctx, pbfr);

		// $text = preg_replace('/(^|\n)-----*/', '\\1<hr />', $text);
		hrWkr.replaceHrs(pctx, pbfr);

		doubleunderWkr.doDoubleUnderscore(pctx, pbfr);

		headingWkr.doHeadings(pctx, pbfr, headingWkrCbk);
		lnkiWkr.replaceInternalLinks(pbfr, env, pctx);
		quoteWkr.doAllQuotes(pctx, pbfr);
		lnkeWkr.replaceExternalLinks(pctx, pbfr);

		// replaceInternalLinks may sometimes leave behind
		// absolute URLs, which have to be masked to hide them from replaceExternalLinks			
		XomwParserBfr_.Replace(pbfr, Bry__marker__noparse, Bry_.Empty); // $text = str_replace(self::MARKER_PREFIX . 'NOPARSE', '', $text);

		magiclinksWkr.doMagicLinks(pctx, pbfr);
//			$text = $this->formatHeadings($text, $origText, $isMain);
	}

	/**
	* Helper function for parse() that transforms half-parsed HTML into fully
	* parsed HTML.
	*
	* @param String $text
	* @param boolean $isMain
	* @param boolean $linestart
	* @return String
	*/
	public void internalParseHalfParsed(XomwParserBfr pbfr, XomwParserCtx pctx, boolean isMain, boolean lineStart) {
		this.mStripState.unstripGeneral(pbfr);

		// MW.HOOK:ParserAfterUnstrip

		// Clean up special characters, only run once, next-to-last before doBlockLevels
		//	$fixtags = [
		//		# French spaces, last one Guillemet-left
		//		# only if there is something before the space
		//		'/(.) (?=\\?|:|;|!|%|\\302\\273)/' => '\\1&#160;',
		//		# french spaces, Guillemet-right
		//		'/(\\302\\253) /' => '\\1&#160;',
		//		'/&#160;(!\s*important)/' => ' \\1', # Beware of CSS magic word !important, T13874.
		//	];
		//	$text = preg_replace( array_keys( $fixtags ), array_values( $fixtags ), $text );
		nbspWkr.doNbsp(pctx, pbfr);

		blockWkr.doBlockLevels(pctx, pbfr, lineStart);

		lnkiWkr.replaceLinkHolders(pbfr);

		// The input doesn't get language converted if
		// a) It's disabled
		// b) Content isn't converted
		// c) It's a conversion table
		// d) it is an interface message (which is in the user language)
//			if ( !( $this->mOptions->getDisableContentConversion()
//				|| isset( $this->mDoubleUnderscores['nocontentconvert'] ) )
//			) {
//				if ( !$this->mOptions->getInterfaceMessage() ) {
//					// The position of the convert() call should not be changed. it
//					// assumes that the links are all replaced and the only thing left
//					// is the <nowiki> mark.
//					$text = $this->getConverterLanguage()->convert( $text );
//				}
//			}

		mStripState.unstripNoWiki(pbfr);

		// MW.HOOK:ParserBeforeTidy

//			$text = $this->replaceTransparentTags( $text );
		mStripState.unstripGeneral(pbfr);

		sanitizer.normalizeCharReferences(pbfr);

//			if ( MWTidy::isEnabled() ) {
//				if ( $this->mOptions->getTidy() ) {
//					$text = MWTidy::tidy( $text );
//				}
//			}
//			else {
//				// attempt to sanitize at least some nesting problems
//				// (T4702 and quite a few others)
//				$tidyregs = [
//					// ''Something [http://www.cool.com cool''] -->
//					// <i>Something</i><a href="http://www.cool.com"..><i>cool></i></a>
//					'/(<([bi])>)(<([bi])>)?([^<]*)(<\/?a[^<]*>)([^<]*)(<\/\\4>)?(<\/\\2>)/' =>
//					'\\1\\3\\5\\8\\9\\6\\1\\3\\7\\8\\9',
//					// fix up an anchor inside another anchor, only
//					// at least for a single single nested link (T5695)
//					'/(<a[^>]+>)([^<]*)(<a[^>]+>[^<]*)<\/a>(.*)<\/a>/' =>
//					'\\1\\2</a>\\3</a>\\1\\4</a>',
//					// fix div inside inline elements- doBlockLevels won't wrap a line which
//					// contains a div, so fix it up here; replace
//					// div with escaped text
//					'/(<([aib]) [^>]+>)([^<]*)(<div([^>]*)>)(.*)(<\/div>)([^<]*)(<\/\\2>)/' =>
//					'\\1\\3&lt;div\\5&gt;\\6&lt;/div&gt;\\8\\9',
//					// remove empty italic or bold tag pairs, some
//					// introduced by rules above
//					'/<([bi])><\/\\1>/' => '',
//				];

//				$text = preg_replace(
//					array_keys( $tidyregs ),
//					array_values( $tidyregs ),
//					$text );
//			}

//			// MW.HOOK:ParserAfterTidy
	}


	// XO.MW:MOVED
	// public function doMagicLinks($text) {}

	// XO.MW:MOVED
	// public function magicLinkCallback($m) {}

//		/**
//		* Make a free external link, given a user-supplied URL
//		*
//		* @param String $url
//		* @param int $numPostProto
//		*   The number of characters after the protocol.
//		* @return String HTML
//		* @private
//		*/
//		public function makeFreeExternalLink($url, $numPostProto) {
//			$trail = '';
//
//			# The characters '<' and '>' (which were escaped by
//			# removeHTMLtags()) should not be included in
//			# URLs, per RFC 2396.
//			# Make &nbsp; terminate a URL as well (bug T84937)
//			$m2 = [];
//			if (preg_match(
//				'/&(lt|gt|nbsp|#x0*(3[CcEe]|[Aa]0)|#0*(60|62|160));/',
//				$url,
//				$m2,
//				PREG_OFFSET_CAPTURE
//			)) {
//				$trail = substr($url, $m2[0][1]) . $trail;
//				$url = substr($url, 0, $m2[0][1]);
//			}
//
//			# Move trailing punctuation to $trail
//			$sep = ',;\.:!?';
//			# If there is no left bracket, then consider right brackets fair game too
//			if (strpos($url, '(') === false) {
//				$sep .= ')';
//			}
//
//			$urlRev = strrev($url);
//			$numSepChars = strspn($urlRev, $sep);
//			# Don't break a trailing HTML entity by moving the ; into $trail
//			# This is in hot code, so use substr_compare to avoid having to
//			# create a new String Object for the comparison
//			if ($numSepChars && substr_compare($url, ";", -$numSepChars, 1) === 0) {
//				# more optimization: instead of running preg_match with a $
//				# anchor, which can be slow, do the match on the reversed
//				# String starting at the desired offset.
//				# un-reversed regexp is: /&([a-z]+|#x[\da-f]+|#\d+)$/i
//				if (preg_match('/\G([a-z]+|[\da-f]+x#|\d+#)&/i', $urlRev, $m2, 0, $numSepChars)) {
//					$numSepChars--;
//				}
//			}
//			if ($numSepChars) {
//				$trail = substr($url, -$numSepChars) . $trail;
//				$url = substr($url, 0, -$numSepChars);
//			}
//
//			# Verify that we still have a real URL after trail removal, and
//			# not just lone protocol
//			if (strlen($trail) >= $numPostProto) {
//				return $url . $trail;
//			}
//
//			$url = Sanitizer::cleanUrl($url);
//
//			# Is this an external image?
//			$text = this.maybeMakeExternalImage($url);
//			if ($text === false) {
//				# Not an image, make a link
//				$text = Linker::makeExternalLink($url,
//					this.getConverterLanguage()->markNoConversion($url, true),
//					true, 'free',
//					this.getExternalLinkAttribs($url), this.mTitle);
//				# Register it in the output Object...
//				# Replace unnecessary URL escape codes with their equivalent characters
//				$pasteurized = self::normalizeLinkUrl($url);
//				this.mOutput->addExternalLink($pasteurized);
//			}
//			return $text . $trail;
//		}

	// XO.MW:MOVED
	// public function doHeadings($text) {}

	// XO.MW:MOVED
	// public function doAllQuotes($text) {}

	// XO.MW:MOVED
	// public function doQuotes($text) {}
//		/**
//		* Replace external links (REL)
//		*
//		* Note: this is all very hackish and the order of execution matters a lot.
//		* Make sure to run tests/parser/parserTests.php if you change this code.
//		*
//		* @private
//		*
//		* @param String $text
//		*
//		* @throws MWException
//		* @return String
//		*/
//		public function replaceExternalLinks($text) {
//
//			$bits = preg_split(this.mExtLinkBracketedRegex, $text, -1, PREG_SPLIT_DELIM_CAPTURE);
//			if ($bits === false) {
//				throw new MWException("PCRE needs to be compiled with "
//					. "--enable-unicode-properties in order for MediaWiki to function");
//			}
//			$s = array_shift($bits);
//
//			$i = 0;
//			while ($i < count($bits)) {
//				$url = $bits[$i++];
//				$i++; // protocol
//				$text = $bits[$i++];
//				$trail = $bits[$i++];
//
//				# The characters '<' and '>' (which were escaped by
//				# removeHTMLtags()) should not be included in
//				# URLs, per RFC 2396.
//				$m2 = [];
//				if (preg_match('/&(lt|gt);/', $url, $m2, PREG_OFFSET_CAPTURE)) {
//					$text = substr($url, $m2[0][1]) . ' ' . $text;
//					$url = substr($url, 0, $m2[0][1]);
//				}
//
//				# If the link text is an image URL, replace it with an <img> tag
//				# This happened by accident in the original parser, but some people used it extensively
//				$img = this.maybeMakeExternalImage($text);
//				if ($img !== false) {
//					$text = $img;
//				}
//
//				$dtrail = '';
//
//				# Set linktype for CSS - if URL==text, link is essentially free
//				$linktype = ($text === $url) ? 'free' : 'text';
//
//				# No link text, e.g. [http://domain.tld/some.link]
//				if ($text == '') {
//					# Autonumber
//					$langObj = this.getTargetLanguage();
//					$text = '[' . $langObj->formatNum(++this.mAutonumber) . ']';
//					$linktype = 'autonumber';
//				} else {
//					# Have link text, e.g. [http://domain.tld/some.link text]s
//					# Check for trail
//					list($dtrail, $trail) = Linker::splitTrail($trail);
//				}
//
//				$text = this.getConverterLanguage()->markNoConversion($text);
//
//				$url = Sanitizer::cleanUrl($url);
//
//				# Use the encoded URL
//				# This means that users can paste URLs directly into the text
//				# Funny characters like � aren't valid in URLs anyway
//				# This was changed in August 2004
//				$s .= Linker::makeExternalLink($url, $text, false, $linktype,
//					this.getExternalLinkAttribs($url), this.mTitle) . $dtrail . $trail;
//
//				# Register link in the output Object.
//				# Replace unnecessary URL escape codes with the referenced character
//				# This prevents spammers from hiding links from the filters
//				$pasteurized = self::normalizeLinkUrl($url);
//				this.mOutput->addExternalLink($pasteurized);
//			}
//
//			return $s;
//		}
//
//		/**
//		* Get the rel attribute for a particular external link.
//		*
//		* @since 1.21
//		* @param String|boolean $url Optional URL, to extract the domain from for rel =>
//		*   nofollow if appropriate
//		* @param Title $title Optional Title, for wgNoFollowNsExceptions lookups
//		* @return String|null Rel attribute for $url
//		*/
//		public static function getExternalLinkRel($url = false, $title = null) {
//			global $wgNoFollowLinks, $wgNoFollowNsExceptions, $wgNoFollowDomainExceptions;
//			$ns = $title ? $title->getNamespace() : false;
//			if ($wgNoFollowLinks && !in_array($ns, $wgNoFollowNsExceptions)
//				&& !wfMatchesDomainList($url, $wgNoFollowDomainExceptions)
//			) {
//				return 'nofollow';
//			}
//			return null;
//		}

	/**
	* Get an associative array of additional HTML attributes appropriate for a
	* particular external link.  This currently may include rel => nofollow
	* (depending on configuration, namespace, and the URL's domain) and/or a
	* target attribute (depending on configuration).
	*
	* @param String $url URL to extract the domain from for rel =>
	*   nofollow if appropriate
	* @return array Associative array of HTML attributes
	*/
	public Xomw_atr_mgr getExternalLinkAttribs(Xomw_atr_mgr atrs) {
		atrs.Clear();
		byte[] rel = Get_external_link_rel;

		// XO.MW.UNSUPPORTED: XO will assume target is blank; MW will set target of "_blank", "_self", etc. depending on global opt
		// $target = $this->mOptions->getExternalLinkTarget();
		atrs.Add(Atr__rel, rel);
		return atrs;
	}

//		/**
//		* Replace unusual escape codes in a URL with their equivalent characters
//		*
//		* This generally follows the syntax defined in RFC 3986, with special
//		* consideration for HTTP query strings.
//		*
//		* @param String $url
//		* @return String
//		*/
//		public static function normalizeLinkUrl($url) {
//			# First, make sure unsafe characters are encoded
//			$url = preg_replace_callback('/[\x00-\x20"<>\[\\\\\]^`{|}\x7F-\xFF]/',
//				function ($m) {
//					return rawurlencode($m[0]);
//				},
//				$url
//			);
//
//			$ret = '';
//			$end = strlen($url);
//
//			# Fragment part - 'fragment'
//			$start = strpos($url, '#');
//			if ($start !== false && $start < $end) {
//				$ret = self::normalizeUrlComponent(
//					substr($url, $start, $end - $start), '"#%<>[\]^`{|}') . $ret;
//				$end = $start;
//			}
//
//			# Query part - 'query' minus &=+;
//			$start = strpos($url, '?');
//			if ($start !== false && $start < $end) {
//				$ret = self::normalizeUrlComponent(
//					substr($url, $start, $end - $start), '"#%<>[\]^`{|}&=+;') . $ret;
//				$end = $start;
//			}
//
//			# Scheme and path part - 'pchar'
//			# (we assume no userinfo or encoded colons in the host)
//			$ret = self::normalizeUrlComponent(
//				substr($url, 0, $end), '"#%<>[\]^`{|}/?') . $ret;
//
//			return $ret;
//		}
//
//		private static function normalizeUrlComponent($component, $unsafe) {
//			$callback = function ($matches) use ($unsafe) {
//				$char = urldecode($matches[0]);
//				$ord = ord($char);
//				if ($ord > 32 && $ord < 127 && strpos($unsafe, $char) === false) {
//					# Unescape it
//					return $char;
//				} else {
//					# Leave it escaped, but use uppercase for a-f
//					return strtoupper($matches[0]);
//				}
//			};
//			return preg_replace_callback('/%[0-9A-Fa-f]{2}/', $callback, $component);
//		}
//
//		/**
//		* make an image if it's allowed, either through the global
//		* option, through the exception, or through the on-wiki whitelist
//		*
//		* @param String $url
//		*
//		* @return String
//		*/
//		private function maybeMakeExternalImage($url) {
//			$imagesfrom = this.mOptions->getAllowExternalImagesFrom();
//			$imagesexception = !empty($imagesfrom);
//			$text = false;
//			# $imagesfrom could be either a single String or an array of strings, parse out the latter
//			if ($imagesexception && is_array($imagesfrom)) {
//				$imagematch = false;
//				foreach ($imagesfrom as $match) {
//					if (strpos($url, $match) === 0) {
//						$imagematch = true;
//						break;
//					}
//				}
//			} elseif ($imagesexception) {
//				$imagematch = (strpos($url, $imagesfrom) === 0);
//			} else {
//				$imagematch = false;
//			}
//
//			if (this.mOptions->getAllowExternalImages()
//				|| ($imagesexception && $imagematch)
//			) {
//				if (preg_match(self::EXT_IMAGE_REGEX, $url)) {
//					# Image found
//					$text = Linker::makeExternalImage($url);
//				}
//			}
//			if (!$text && this.mOptions->getEnableImageWhitelist()
//				&& preg_match(self::EXT_IMAGE_REGEX, $url)
//			) {
//				$whitelist = explode(
//					"\n",
//					wfMessage('external_image_whitelist')->inContentLanguage()->text()
//				);
//
//				foreach ($whitelist as $entry) {
//					# Sanitize the regex fragment, make it case-insensitive, ignore blank entries/comments
//					if (strpos($entry, '#') === 0 || $entry === '') {
//						continue;
//					}
//					if (preg_match('/' . str_replace('/', '\\/', $entry) . '/i', $url)) {
//						# Image matches a whitelist entry
//						$text = Linker::makeExternalImage($url);
//						break;
//					}
//				}
//			}
//			return $text;
//		}

	// XO.MW:MOVED
	// public function replaceInternalLinks($s) {}

	// XO.MW:MOVED
//		public function replaceInternalLinks2(&$s) {}

//		/**
//		* Render a forced-blue link inline; protect against double expansion of
//		* URLs if we're in a mode that prepends full URL prefixes to @gplx.Internal protected links.
//		* Since this little disaster has to split off the trail text to avoid
//		* breaking URLs in the following text without breaking trails on the
//		* wiki links, it's been made into a horrible function.
//		*
//		* @param Title $nt
//		* @param String $text
//		* @param String $trail
//		* @param String $prefix
//		* @return String HTML-wikitext mix oh yuck
//		*/
//		protected function makeKnownLinkHolder($nt, $text = '', $trail = '', $prefix = '') {
//			list($inside, $trail) = Linker::splitTrail($trail);
//
//			if ($text == '') {
//				$text = htmlspecialchars($nt->getPrefixedText());
//			}
//
//			$link = this.getLinkRenderer()->makeKnownLink(
//				$nt, new HtmlArmor("$prefix$text$inside")
//			);
//
//			return this.armorLinks($link) . $trail;
//		}
//
	/**
	* Insert a NOPARSE hacky thing into any inline links in a chunk that's
	* going to go through further parsing steps before inline URL expansion.
	*
	* Not needed quite as much as it used to be since free links are a bit
	* more sensible these days. But bracketed links are still an issue.
	*
	* @param String $text More-or-less HTML
	* @return String Less-or-more HTML with NOPARSE bits
	*/
	public byte[] armorLinks(Bry_bfr trg, byte[] src, int src_bgn, int src_end) {
		// XO.MW.PORTED
		// return preg_replace('/\b((?i)' . this.mUrlProtocols . ')/',
		//	self::MARKER_PREFIX . "NOPARSE$1", $text);
		int cur = src_bgn;
		int prv = cur;
		boolean dirty = false;
		boolean called_by_bry = trg == null;
		while (true) {
			// exit if EOS
			if (cur == src_end) {
				// if dirty, add rest of String
				if (dirty)
					trg.Add_mid(src, prv, src_end);
				break;
			}

			// check if cur matches protocol
			Object protocol_obj = protocols_trie.Match_at(trv, src, cur, src_end);
			// no match; continue
			if (protocol_obj == null) {
				cur++;
			}
			// match; add to bfr
			else {
				dirty = true;
				byte[] protocol_bry = (byte[])protocol_obj;
				if (called_by_bry) trg = Bry_bfr_.New();
				trg.Add_bry_many(XomwParser.MARKER_PREFIX, Bry__noparse, protocol_bry);
				cur += protocol_bry.length;
				prv = cur;
			}
		}
		if (called_by_bry) {
			if (dirty)
				return trg.To_bry_and_clear();
			else {
				if (src_bgn == 0 && src_end == src.length)
					return src;
				else
					return Bry_.Mid(src, src_bgn, src_end);
			}
		}
		else {
			if (dirty)
				return null;
			else {
				trg.Add_mid(src, src_bgn, src_end);
				return null;
			}
		}
	}

//		/**
//		* Return true if subpage links should be expanded on this page.
//		* @return boolean
//		*/
//		public function areSubpagesAllowed() {
//			# Some namespaces don't allow subpages
//			return MWNamespace::hasSubpages(this.mTitle->getNamespace());
//		}
//
//		/**
//		* Handle link to subpage if necessary
//		*
//		* @param String $target The source of the link
//		* @param String &$text The link text, modified as necessary
//		* @return String The full name of the link
//		* @private
//		*/
//		public function maybeDoSubpageLink($target, &$text) {
//			return Linker::normalizeSubpageLink(this.mTitle, $target, $text);
//		}
//
//		/**
//		* Make lists from lines starting with ':', '*', '#', etc. (DBL)
//		*
//		* @param String $text
//		* @param boolean $linestart Whether or not this is at the start of a line.
//		* @private
//		* @return String The lists rendered as HTML
//		*/
//		public function doBlockLevels($text, $linestart) {
//			return BlockLevelPass::doBlockLevels($text, $linestart);
//		}
//
//		/**
//		* Return value of a magic variable (like PAGENAME)
//		*
//		* @private
//		*
//		* @param int $index
//		* @param boolean|PPFrame $frame
//		*
//		* @throws MWException
//		* @return String
//		*/
//		public function getVariableValue($index, $frame = false) {
//			global $wgContLang, $wgSitename, $wgServer, $wgServerName;
//			global $wgArticlePath, $wgScriptPath, $wgStylePath;
//
//			if (is_null(this.mTitle)) {
//				// If no title set, bad things are going to happen
//				// later. Title should always be set since this
//				// should only be called in the middle of a parse
//				// operation (but the unit-tests do funky stuff)
//				throw new MWException(__METHOD__ . ' Should only be '
//					. ' called while parsing (no title set)');
//			}
//
//			/**
//			* Some of these require message or data lookups and can be
//			* expensive to check many times.
//			*/
//			if (Hooks::run('ParserGetVariableValueVarCache', [ &$this, &this.mVarCache ])) {
//				if (isset(this.mVarCache[$index])) {
//					return this.mVarCache[$index];
//				}
//			}
//
//			$ts = wfTimestamp(TS_UNIX, this.mOptions->getTimestamp());
//			Hooks::run('ParserGetVariableValueTs', [ &$this, &$ts ]);
//
//			$pageLang = this.getFunctionLang();
//
//			switch ($index) {
//				case '!':
//					$value = '|';
//					break;
//				case 'currentmonth':
//					$value = $pageLang->formatNum(MWTimestamp::getInstance($ts)->format('m'));
//					break;
//				case 'currentmonth1':
//					$value = $pageLang->formatNum(MWTimestamp::getInstance($ts)->format('n'));
//					break;
//				case 'currentmonthname':
//					$value = $pageLang->getMonthName(MWTimestamp::getInstance($ts)->format('n'));
//					break;
//				case 'currentmonthnamegen':
//					$value = $pageLang->getMonthNameGen(MWTimestamp::getInstance($ts)->format('n'));
//					break;
//				case 'currentmonthabbrev':
//					$value = $pageLang->getMonthAbbreviation(MWTimestamp::getInstance($ts)->format('n'));
//					break;
//				case 'currentday':
//					$value = $pageLang->formatNum(MWTimestamp::getInstance($ts)->format('j'));
//					break;
//				case 'currentday2':
//					$value = $pageLang->formatNum(MWTimestamp::getInstance($ts)->format('d'));
//					break;
//				case 'localmonth':
//					$value = $pageLang->formatNum(MWTimestamp::getLocalInstance($ts)->format('m'));
//					break;
//				case 'localmonth1':
//					$value = $pageLang->formatNum(MWTimestamp::getLocalInstance($ts)->format('n'));
//					break;
//				case 'localmonthname':
//					$value = $pageLang->getMonthName(MWTimestamp::getLocalInstance($ts)->format('n'));
//					break;
//				case 'localmonthnamegen':
//					$value = $pageLang->getMonthNameGen(MWTimestamp::getLocalInstance($ts)->format('n'));
//					break;
//				case 'localmonthabbrev':
//					$value = $pageLang->getMonthAbbreviation(MWTimestamp::getLocalInstance($ts)->format('n'));
//					break;
//				case 'localday':
//					$value = $pageLang->formatNum(MWTimestamp::getLocalInstance($ts)->format('j'));
//					break;
//				case 'localday2':
//					$value = $pageLang->formatNum(MWTimestamp::getLocalInstance($ts)->format('d'));
//					break;
//				case 'pagename':
//					$value = wfEscapeWikiText(this.mTitle->getText());
//					break;
//				case 'pagenamee':
//					$value = wfEscapeWikiText(this.mTitle->getPartialURL());
//					break;
//				case 'fullpagename':
//					$value = wfEscapeWikiText(this.mTitle->getPrefixedText());
//					break;
//				case 'fullpagenamee':
//					$value = wfEscapeWikiText(this.mTitle->getPrefixedURL());
//					break;
//				case 'subpagename':
//					$value = wfEscapeWikiText(this.mTitle->getSubpageText());
//					break;
//				case 'subpagenamee':
//					$value = wfEscapeWikiText(this.mTitle->getSubpageUrlForm());
//					break;
//				case 'rootpagename':
//					$value = wfEscapeWikiText(this.mTitle->getRootText());
//					break;
//				case 'rootpagenamee':
//					$value = wfEscapeWikiText(wfUrlencode(str_replace(
//						' ',
//						'_',
//						this.mTitle->getRootText()
//					)));
//					break;
//				case 'basepagename':
//					$value = wfEscapeWikiText(this.mTitle->getBaseText());
//					break;
//				case 'basepagenamee':
//					$value = wfEscapeWikiText(wfUrlencode(str_replace(
//						' ',
//						'_',
//						this.mTitle->getBaseText()
//					)));
//					break;
//				case 'talkpagename':
//					if (this.mTitle->canTalk()) {
//						$talkPage = this.mTitle->getTalkPage();
//						$value = wfEscapeWikiText($talkPage->getPrefixedText());
//					} else {
//						$value = '';
//					}
//					break;
//				case 'talkpagenamee':
//					if (this.mTitle->canTalk()) {
//						$talkPage = this.mTitle->getTalkPage();
//						$value = wfEscapeWikiText($talkPage->getPrefixedURL());
//					} else {
//						$value = '';
//					}
//					break;
//				case 'subjectpagename':
//					$subjPage = this.mTitle->getSubjectPage();
//					$value = wfEscapeWikiText($subjPage->getPrefixedText());
//					break;
//				case 'subjectpagenamee':
//					$subjPage = this.mTitle->getSubjectPage();
//					$value = wfEscapeWikiText($subjPage->getPrefixedURL());
//					break;
//				case 'pageid': // requested in T25427
//					$pageid = this.getTitle()->getArticleID();
//					if ($pageid == 0) {
//						# 0 means the page doesn't exist in the database,
//						# which means the user is previewing a new page.
//						# The vary-revision flag must be set, because the magic word
//						# will have a different value once the page is saved.
//						this.mOutput->setFlag('vary-revision');
//						wfDebug(__METHOD__ . ": {{PAGEID}} used in a new page, setting vary-revision...\n");
//					}
//					$value = $pageid ? $pageid : null;
//					break;
//				case 'revisionid':
//					# Let the edit saving system know we should parse the page
//					# *after* a revision ID has been assigned.
//					this.mOutput->setFlag('vary-revision-id');
//					wfDebug(__METHOD__ . ": {{REVISIONID}} used, setting vary-revision-id...\n");
//					$value = this.mRevisionId;
//					if (!$value && this.mOptions->getSpeculativeRevIdCallback()) {
//						$value = call_user_func(this.mOptions->getSpeculativeRevIdCallback());
//						this.mOutput->setSpeculativeRevIdUsed($value);
//					}
//					break;
//				case 'revisionday':
//					# Let the edit saving system know we should parse the page
//					# *after* a revision ID has been assigned. This is for null edits.
//					this.mOutput->setFlag('vary-revision');
//					wfDebug(__METHOD__ . ": {{REVISIONDAY}} used, setting vary-revision...\n");
//					$value = intval(substr(this.getRevisionTimestamp(), 6, 2));
//					break;
//				case 'revisionday2':
//					# Let the edit saving system know we should parse the page
//					# *after* a revision ID has been assigned. This is for null edits.
//					this.mOutput->setFlag('vary-revision');
//					wfDebug(__METHOD__ . ": {{REVISIONDAY2}} used, setting vary-revision...\n");
//					$value = substr(this.getRevisionTimestamp(), 6, 2);
//					break;
//				case 'revisionmonth':
//					# Let the edit saving system know we should parse the page
//					# *after* a revision ID has been assigned. This is for null edits.
//					this.mOutput->setFlag('vary-revision');
//					wfDebug(__METHOD__ . ": {{REVISIONMONTH}} used, setting vary-revision...\n");
//					$value = substr(this.getRevisionTimestamp(), 4, 2);
//					break;
//				case 'revisionmonth1':
//					# Let the edit saving system know we should parse the page
//					# *after* a revision ID has been assigned. This is for null edits.
//					this.mOutput->setFlag('vary-revision');
//					wfDebug(__METHOD__ . ": {{REVISIONMONTH1}} used, setting vary-revision...\n");
//					$value = intval(substr(this.getRevisionTimestamp(), 4, 2));
//					break;
//				case 'revisionyear':
//					# Let the edit saving system know we should parse the page
//					# *after* a revision ID has been assigned. This is for null edits.
//					this.mOutput->setFlag('vary-revision');
//					wfDebug(__METHOD__ . ": {{REVISIONYEAR}} used, setting vary-revision...\n");
//					$value = substr(this.getRevisionTimestamp(), 0, 4);
//					break;
//				case 'revisiontimestamp':
//					# Let the edit saving system know we should parse the page
//					# *after* a revision ID has been assigned. This is for null edits.
//					this.mOutput->setFlag('vary-revision');
//					wfDebug(__METHOD__ . ": {{REVISIONTIMESTAMP}} used, setting vary-revision...\n");
//					$value = this.getRevisionTimestamp();
//					break;
//				case 'revisionuser':
//					# Let the edit saving system know we should parse the page
//					# *after* a revision ID has been assigned for null edits.
//					this.mOutput->setFlag('vary-user');
//					wfDebug(__METHOD__ . ": {{REVISIONUSER}} used, setting vary-user...\n");
//					$value = this.getRevisionUser();
//					break;
//				case 'revisionsize':
//					$value = this.getRevisionSize();
//					break;
//				case 'namespace':
//					$value = str_replace('_', ' ', $wgContLang->getNsText(this.mTitle->getNamespace()));
//					break;
//				case 'namespacee':
//					$value = wfUrlencode($wgContLang->getNsText(this.mTitle->getNamespace()));
//					break;
//				case 'namespacenumber':
//					$value = this.mTitle->getNamespace();
//					break;
//				case 'talkspace':
//					$value = this.mTitle->canTalk()
//						? str_replace('_', ' ', this.mTitle->getTalkNsText())
//						: '';
//					break;
//				case 'talkspacee':
//					$value = this.mTitle->canTalk() ? wfUrlencode(this.mTitle->getTalkNsText()) : '';
//					break;
//				case 'subjectspace':
//					$value = str_replace('_', ' ', this.mTitle->getSubjectNsText());
//					break;
//				case 'subjectspacee':
//					$value = (wfUrlencode(this.mTitle->getSubjectNsText()));
//					break;
//				case 'currentdayname':
//					$value = $pageLang->getWeekdayName((int)MWTimestamp::getInstance($ts)->format('w') + 1);
//					break;
//				case 'currentyear':
//					$value = $pageLang->formatNum(MWTimestamp::getInstance($ts)->format('Y'), true);
//					break;
//				case 'currenttime':
//					$value = $pageLang->time(wfTimestamp(TS_MW, $ts), false, false);
//					break;
//				case 'currenthour':
//					$value = $pageLang->formatNum(MWTimestamp::getInstance($ts)->format('H'), true);
//					break;
//				case 'currentweek':
//					# @bug T6594 PHP5 has it zero padded, PHP4 does not, cast to
//					# int to remove the padding
//					$value = $pageLang->formatNum((int)MWTimestamp::getInstance($ts)->format('W'));
//					break;
//				case 'currentdow':
//					$value = $pageLang->formatNum(MWTimestamp::getInstance($ts)->format('w'));
//					break;
//				case 'localdayname':
//					$value = $pageLang->getWeekdayName(
//						(int)MWTimestamp::getLocalInstance($ts)->format('w') + 1
//					);
//					break;
//				case 'localyear':
//					$value = $pageLang->formatNum(MWTimestamp::getLocalInstance($ts)->format('Y'), true);
//					break;
//				case 'localtime':
//					$value = $pageLang->time(
//						MWTimestamp::getLocalInstance($ts)->format('YmdHis'),
//						false,
//						false
//					);
//					break;
//				case 'localhour':
//					$value = $pageLang->formatNum(MWTimestamp::getLocalInstance($ts)->format('H'), true);
//					break;
//				case 'localweek':
//					# @bug T6594 PHP5 has it zero padded, PHP4 does not, cast to
//					# int to remove the padding
//					$value = $pageLang->formatNum((int)MWTimestamp::getLocalInstance($ts)->format('W'));
//					break;
//				case 'localdow':
//					$value = $pageLang->formatNum(MWTimestamp::getLocalInstance($ts)->format('w'));
//					break;
//				case 'numberofarticles':
//					$value = $pageLang->formatNum(SiteStats::articles());
//					break;
//				case 'numberoffiles':
//					$value = $pageLang->formatNum(SiteStats::images());
//					break;
//				case 'numberofusers':
//					$value = $pageLang->formatNum(SiteStats::users());
//					break;
//				case 'numberofactiveusers':
//					$value = $pageLang->formatNum(SiteStats::activeUsers());
//					break;
//				case 'numberofpages':
//					$value = $pageLang->formatNum(SiteStats::pages());
//					break;
//				case 'numberofadmins':
//					$value = $pageLang->formatNum(SiteStats::numberingroup('sysop'));
//					break;
//				case 'numberofedits':
//					$value = $pageLang->formatNum(SiteStats::edits());
//					break;
//				case 'currenttimestamp':
//					$value = wfTimestamp(TS_MW, $ts);
//					break;
//				case 'localtimestamp':
//					$value = MWTimestamp::getLocalInstance($ts)->format('YmdHis');
//					break;
//				case 'currentversion':
//					$value = SpecialVersion::getVersion();
//					break;
//				case 'articlepath':
//					return $wgArticlePath;
//				case 'sitename':
//					return $wgSitename;
//				case 'server':
//					return $wgServer;
//				case 'servername':
//					return $wgServerName;
//				case 'scriptpath':
//					return $wgScriptPath;
//				case 'stylepath':
//					return $wgStylePath;
//				case 'directionmark':
//					return $pageLang->getDirMark();
//				case 'contentlanguage':
//					global $wgLanguageCode;
//					return $wgLanguageCode;
//				case 'pagelanguage':
//					$value = $pageLang->getCode();
//					break;
//				case 'cascadingsources':
//					$value = CoreParserFunctions::cascadingsources($this);
//					break;
//				default:
//					$ret = null;
//					Hooks::run(
//						'ParserGetVariableValueSwitch',
//						[ &$this, &this.mVarCache, &$index, &$ret, &$frame ]
//					);
//
//					return $ret;
//			}
//
//			if ($index) {
//				this.mVarCache[$index] = $value;
//			}
//
//			return $value;
//		}
//
//		/**
//		* initialise the magic variables (like CURRENTMONTHNAME) and substitution modifiers
//		*
//		* @private
//		*/
//		public function initialiseVariables() {
//			$variableIDs = MagicWord::getVariableIDs();
//			$substIDs = MagicWord::getSubstIDs();
//
//			this.mVariables = new MagicWordArray($variableIDs);
//			this.mSubstWords = new MagicWordArray($substIDs);
//		}
//
//		/**
//		* Preprocess some wikitext and return the document tree.
//		* This is the ghost of replace_variables().
//		*
//		* @param String $text The text to parse
//		* @param int $flags Bitwise combination of:
//		*   - self::PTD_FOR_INCLUSION: Handle "<noinclude>" and "<includeonly>" as if the text is being
//		*     included. Default is to assume a direct page view.
//		*
//		* The generated DOM tree must depend only on the input text and the flags.
//		* The DOM tree must be the same in OT_HTML and OT_WIKI mode, to avoid a regression of T6899.
//		*
//		* Any flag added to the $flags parameter here, or any other parameter liable to cause a
//		* change in the DOM tree for a given text, must be passed through the section identifier
//		* in the section edit link and thus back to extractSections().
//		*
//		* The output of this function is currently only cached in process memory, but a persistent
//		* cache may be implemented at a later date which takes further advantage of these strict
//		* dependency requirements.
//		*
//		* @return PPNode
//		*/
//		public function preprocessToDom($text, $flags = 0) {
//			$dom = this.getPreprocessor()->preprocessToObj($text, $flags);
//			return $dom;
//		}
//
//		/**
//		* Return a three-element array: leading whitespace, String contents, trailing whitespace
//		*
//		* @param String $s
//		*
//		* @return array
//		*/
//		public static function splitWhitespace($s) {
//			$ltrimmed = ltrim($s);
//			$w1 = substr($s, 0, strlen($s) - strlen($ltrimmed));
//			$trimmed = rtrim($ltrimmed);
//			$diff = strlen($ltrimmed) - strlen($trimmed);
//			if ($diff > 0) {
//				$w2 = substr($ltrimmed, -$diff);
//			} else {
//				$w2 = '';
//			}
//			return [ $w1, $trimmed, $w2 ];
//		}
//
//		/**
//		* Replace magic variables, templates, and template arguments
//		* with the appropriate text. Templates are substituted recursively,
//		* taking care to avoid infinite loops.
//		*
//		* Note that the substitution depends on value of $mOutputType:
//		*  self::OT_WIKI: only {{subst:}} templates
//		*  self::OT_PREPROCESS: templates but not extension tags
//		*  self::OT_HTML: all templates and extension tags
//		*
//		* @param String $text The text to transform
//		* @param boolean|PPFrame $frame Object describing the arguments passed to the
//		*   template. Arguments may also be provided as an associative array, as
//		*   was the usual case before MW1.12. Providing arguments this way may be
//		*   useful for extensions wishing to perform variable replacement
//		*   explicitly.
//		* @param boolean $argsOnly Only do argument (triple-brace) expansion, not
//		*   double-brace expansion.
//		* @return String
//		*/
//		public function replaceVariables($text, $frame = false, $argsOnly = false) {
//			# Is there any text? Also, Prevent too big inclusions!
//			$textSize = strlen($text);
//			if ($textSize < 1 || $textSize > this.mOptions->getMaxIncludeSize()) {
//				return $text;
//			}
//
//			if ($frame === false) {
//				$frame = this.getPreprocessor()->newFrame();
//			} elseif (!($frame instanceof PPFrame)) {
//				wfDebug(__METHOD__ . " called using plain parameters instead of "
//					. "a PPFrame instance. Creating custom frame.\n");
//				$frame = this.getPreprocessor()->newCustomFrame($frame);
//			}
//
//			$dom = this.preprocessToDom($text);
//			$flags = $argsOnly ? PPFrame::NO_TEMPLATES : 0;
//			$text = $frame->expand($dom, $flags);
//
//			return $text;
//		}
//
//		/**
//		* Clean up argument array - refactored in 1.9 so parserfunctions can use it, too.
//		*
//		* @param array $args
//		*
//		* @return array
//		*/
//		public static function createAssocArgs($args) {
//			$assocArgs = [];
//			$index = 1;
//			foreach ($args as $arg) {
//				$eqpos = strpos($arg, '=');
//				if ($eqpos === false) {
//					$assocArgs[$index++] = $arg;
//				} else {
//					$name = trim(substr($arg, 0, $eqpos));
//					$value = trim(substr($arg, $eqpos + 1));
//					if ($value === false) {
//						$value = '';
//					}
//					if ($name !== false) {
//						$assocArgs[$name] = $value;
//					}
//				}
//			}
//
//			return $assocArgs;
//		}
//
//		/**
//		* Warn the user when a parser limitation is reached
//		* Will warn at most once the user per limitation type
//		*
//		* The results are shown during preview and run through the Parser (See EditPage.php)
//		*
//		* @param String $limitationType Should be one of:
//		*   'expensive-parserfunction' (corresponding messages:
//		*       'expensive-parserfunction-warning',
//		*       'expensive-parserfunction-category')
//		*   'post-expand-template-argument' (corresponding messages:
//		*       'post-expand-template-argument-warning',
//		*       'post-expand-template-argument-category')
//		*   'post-expand-template-inclusion' (corresponding messages:
//		*       'post-expand-template-inclusion-warning',
//		*       'post-expand-template-inclusion-category')
//		*   'node-count-exceeded' (corresponding messages:
//		*       'node-count-exceeded-warning',
//		*       'node-count-exceeded-category')
//		*   'expansion-depth-exceeded' (corresponding messages:
//		*       'expansion-depth-exceeded-warning',
//		*       'expansion-depth-exceeded-category')
//		* @param String|int|null $current Current value
//		* @param String|int|null $max Maximum allowed, when an explicit limit has been
//		*	 exceeded, provide the values (optional)
//		*/
//		public function limitationWarn($limitationType, $current = '', $max = '') {
//			# does no harm if $current and $max are present but are unnecessary for the message
//			# Not doing ->inLanguage(this.mOptions->getUserLangObj()), since this is shown
//			# only during preview, and that would split the parser cache unnecessarily.
//			$warning = wfMessage("$limitationType-warning")->numParams($current, $max)
//				->text();
//			this.mOutput->addWarning($warning);
//			this.addTrackingCategory("$limitationType-category");
//		}
//
//		/**
//		* Return the text of a template, after recursively
//		* replacing any variables or templates within the template.
//		*
//		* @param array $piece The parts of the template
//		*   $piece['title']: the title, i.e. the part before the |
//		*   $piece['parts']: the parameter array
//		*   $piece['lineStart']: whether the brace was at the start of a line
//		* @param PPFrame $frame The current frame, contains template arguments
//		* @throws Exception
//		* @return String The text of the template
//		*/
//		public function braceSubstitution($piece, $frame) {
//
//			// Flags
//
//			// $text has been filled
//			$found = false;
//			// wiki markup in $text should be escaped
//			$nowiki = false;
//			// $text is HTML, armour it against wikitext transformation
//			$isHTML = false;
//			// Force interwiki transclusion to be done in raw mode not rendered
//			$forceRawInterwiki = false;
//			// $text is a DOM node needing expansion in a child frame
//			$isChildObj = false;
//			// $text is a DOM node needing expansion in the current frame
//			$isLocalObj = false;
//
//			# Title Object, where $text came from
//			$title = false;
//
//			# $part1 is the bit before the first |, and must contain only title characters.
//			# Various prefixes will be stripped from it later.
//			$titleWithSpaces = $frame->expand($piece['title']);
//			$part1 = trim($titleWithSpaces);
//			$titleText = false;
//
//			# Original title text preserved for various purposes
//			$originalTitle = $part1;
//
//			# $args is a list of argument nodes, starting from index 0, not including $part1
//			# @todo FIXME: If piece['parts'] is null then the call to getLength()
//			# below won't work b/c this $args isn't an Object
//			$args = (null == $piece['parts']) ? [] : $piece['parts'];
//
//			$profileSection = null; // profile templates
//
//			# SUBST
//			if (!$found) {
//				$substMatch = this.mSubstWords->matchStartAndRemove($part1);
//
//				# Possibilities for substMatch: "subst", "safesubst" or FALSE
//				# Decide whether to expand template or keep wikitext as-is.
//				if (this.ot['wiki']) {
//					if ($substMatch === false) {
//						$literal = true;  # literal when in PST with no prefix
//					} else {
//						$literal = false; # expand when in PST with subst: or safesubst:
//					}
//				} else {
//					if ($substMatch == 'subst') {
//						$literal = true;  # literal when not in PST with plain subst:
//					} else {
//						$literal = false; # expand when not in PST with safesubst: or no prefix
//					}
//				}
//				if ($literal) {
//					$text = $frame->virtualBracketedImplode('{{', '|', '}}', $titleWithSpaces, $args);
//					$isLocalObj = true;
//					$found = true;
//				}
//			}
//
//			# Variables
//			if (!$found && $args->getLength() == 0) {
//				$id = this.mVariables->matchStartToEnd($part1);
//				if ($id !== false) {
//					$text = this.getVariableValue($id, $frame);
//					if (MagicWord::getCacheTTL($id) > -1) {
//						this.mOutput->updateCacheExpiry(MagicWord::getCacheTTL($id));
//					}
//					$found = true;
//				}
//			}
//
//			# MSG, MSGNW and RAW
//			if (!$found) {
//				# Check for MSGNW:
//				$mwMsgnw = MagicWord::get('msgnw');
//				if ($mwMsgnw->matchStartAndRemove($part1)) {
//					$nowiki = true;
//				} else {
//					# Remove obsolete MSG:
//					$mwMsg = MagicWord::get('msg');
//					$mwMsg->matchStartAndRemove($part1);
//				}
//
//				# Check for RAW:
//				$mwRaw = MagicWord::get('raw');
//				if ($mwRaw->matchStartAndRemove($part1)) {
//					$forceRawInterwiki = true;
//				}
//			}
//
//			# Parser functions
//			if (!$found) {
//				$colonPos = strpos($part1, ':');
//				if ($colonPos !== false) {
//					$func = substr($part1, 0, $colonPos);
//					$funcArgs = [ trim(substr($part1, $colonPos + 1)) ];
//					$argsLength = $args->getLength();
//					for ($i = 0; $i < $argsLength; $i++) {
//						$funcArgs[] = $args->item($i);
//					}
//					try {
//						$result = this.callParserFunction($frame, $func, $funcArgs);
//					} catch (Exception $ex) {
//						throw $ex;
//					}
//
//					# The interface for parser functions allows for extracting
//					# flags into the local scope. Extract any forwarded flags
//					# here.
//					extract($result);
//				}
//			}
//
//			# Finish mangling title and then check for loops.
//			# Set $title to a Title Object and $titleText to the PDBK
//			if (!$found) {
//				$ns = NS_TEMPLATE;
//				# Split the title into page and subpage
//				$subpage = '';
//				$relative = this.maybeDoSubpageLink($part1, $subpage);
//				if ($part1 !== $relative) {
//					$part1 = $relative;
//					$ns = this.mTitle->getNamespace();
//				}
//				$title = Title::newFromText($part1, $ns);
//				if ($title) {
//					$titleText = $title->getPrefixedText();
//					# Check for language variants if the template is not found
//					if (this.getConverterLanguage()->hasVariants() && $title->getArticleID() == 0) {
//						this.getConverterLanguage()->findVariantLink($part1, $title, true);
//					}
//					# Do recursion depth check
//					$limit = this.mOptions->getMaxTemplateDepth();
//					if ($frame->depth >= $limit) {
//						$found = true;
//						$text = '<span class="error">'
//							. wfMessage('parser-template-recursion-depth-warning')
//								->numParams($limit)->inContentLanguage()->text()
//							. '</span>';
//					}
//				}
//			}
//
//			# Load from database
//			if (!$found && $title) {
//				$profileSection = this.mProfiler->scopedProfileIn($title->getPrefixedDBkey());
//				if (!$title->isExternal()) {
//					if ($title->isSpecialPage()
//						&& this.mOptions->getAllowSpecialInclusion()
//						&& this.ot['html']
//					) {
//						$specialPage = SpecialPageFactory::getPage($title->getDBkey());
//						// Pass the template arguments as URL parameters.
//						// "uselang" will have no effect since the Language Object
//						// is forced to the one defined in ParserOptions.
//						$pageArgs = [];
//						$argsLength = $args->getLength();
//						for ($i = 0; $i < $argsLength; $i++) {
//							$bits = $args->item($i)->splitArg();
//							if (strval($bits['index']) === '') {
//								$name = trim($frame->expand($bits['name'], PPFrame::STRIP_COMMENTS));
//								$value = trim($frame->expand($bits['value']));
//								$pageArgs[$name] = $value;
//							}
//						}
//
//						// Create a new context to execute the special page
//						$context = new RequestContext;
//						$context->setTitle($title);
//						$context->setRequest(new FauxRequest($pageArgs));
//						if ($specialPage && $specialPage->maxIncludeCacheTime() === 0) {
//							$context->setUser(this.getUser());
//						} else {
//							// If this page is cached, then we better not be per user.
//							$context->setUser(User::newFromName('127.0.0.1', false));
//						}
//						$context->setLanguage(this.mOptions->getUserLangObj());
//						$ret = SpecialPageFactory::capturePath(
//							$title, $context, this.getLinkRenderer());
//						if ($ret) {
//							$text = $context->getOutput()->getHTML();
//							this.mOutput->addOutputPageMetadata($context->getOutput());
//							$found = true;
//							$isHTML = true;
//							if ($specialPage && $specialPage->maxIncludeCacheTime() !== false) {
//								this.mOutput->updateRuntimeAdaptiveExpiry(
//									$specialPage->maxIncludeCacheTime()
//								);
//							}
//						}
//					} elseif (MWNamespace::isNonincludable($title->getNamespace())) {
//						$found = false; # access denied
//						wfDebug(__METHOD__ . ": template inclusion denied for " .
//							$title->getPrefixedDBkey() . "\n");
//					} else {
//						list($text, $title) = this.getTemplateDom($title);
//						if ($text !== false) {
//							$found = true;
//							$isChildObj = true;
//						}
//					}
//
//					# If the title is valid but undisplayable, make a link to it
//					if (!$found && (this.ot['html'] || this.ot['pre'])) {
//						$text = "[[:$titleText]]";
//						$found = true;
//					}
//				} elseif ($title->isTrans()) {
//					# Interwiki transclusion
//					if (this.ot['html'] && !$forceRawInterwiki) {
//						$text = this.interwikiTransclude($title, 'render');
//						$isHTML = true;
//					} else {
//						$text = this.interwikiTransclude($title, 'raw');
//						# Preprocess it like a template
//						$text = this.preprocessToDom($text, self::PTD_FOR_INCLUSION);
//						$isChildObj = true;
//					}
//					$found = true;
//				}
//
//				# Do infinite loop check
//				# This has to be done after redirect resolution to avoid infinite loops via redirects
//				if (!$frame->loopCheck($title)) {
//					$found = true;
//					$text = '<span class="error">'
//						. wfMessage('parser-template-loop-warning', $titleText)->inContentLanguage()->text()
//						. '</span>';
//					wfDebug(__METHOD__ . ": template loop broken at '$titleText'\n");
//				}
//			}
//
//			# If we haven't found text to substitute by now, we're done
//			# Recover the source wikitext and return it
//			if (!$found) {
//				$text = $frame->virtualBracketedImplode('{{', '|', '}}', $titleWithSpaces, $args);
//				if ($profileSection) {
//					this.mProfiler->scopedProfileOut($profileSection);
//				}
//				return [ 'Object' => $text ];
//			}
//
//			# Expand DOM-style return values in a child frame
//			if ($isChildObj) {
//				# Clean up argument array
//				$newFrame = $frame->newChild($args, $title);
//
//				if ($nowiki) {
//					$text = $newFrame->expand($text, PPFrame::RECOVER_ORIG);
//				} elseif ($titleText !== false && $newFrame->isEmpty()) {
//					# Expansion is eligible for the empty-frame cache
//					$text = $newFrame->cachedExpand($titleText, $text);
//				} else {
//					# Uncached expansion
//					$text = $newFrame->expand($text);
//				}
//			}
//			if ($isLocalObj && $nowiki) {
//				$text = $frame->expand($text, PPFrame::RECOVER_ORIG);
//				$isLocalObj = false;
//			}
//
//			if ($profileSection) {
//				this.mProfiler->scopedProfileOut($profileSection);
//			}
//
//			# Replace raw HTML by a placeholder
//			if ($isHTML) {
//				$text = this.insertStripItem($text);
//			} elseif ($nowiki && (this.ot['html'] || this.ot['pre'])) {
//				# Escape nowiki-style return values
//				$text = wfEscapeWikiText($text);
//			} elseif (is_string($text)
//				&& !$piece['lineStart']
//				&& preg_match('/^(?:{\\||:|;|#|\*)/', $text)
//			) {
//				# T2529: if the template begins with a table or block-level
//				# element, it should be treated as beginning a new line.
//				# This behavior is somewhat controversial.
//				$text = "\n" . $text;
//			}
//
//			if (is_string($text) && !this.incrementIncludeSize('post-expand', strlen($text))) {
//				# Error, oversize inclusion
//				if ($titleText !== false) {
//					# Make a working, properly escaped link if possible (T25588)
//					$text = "[[:$titleText]]";
//				} else {
//					# This will probably not be a working link, but at least it may
//					# provide some hint of where the problem is
//					preg_replace('/^:/', '', $originalTitle);
//					$text = "[[:$originalTitle]]";
//				}
//				$text .= this.insertStripItem('<!-- WARNING: template omitted, '
//					. 'post-expand include size too large -->');
//				this.limitationWarn('post-expand-template-inclusion');
//			}
//
//			if ($isLocalObj) {
//				$ret = [ 'Object' => $text ];
//			} else {
//				$ret = [ 'text' => $text ];
//			}
//
//			return $ret;
//		}
//
//		/**
//		* Call a parser function and return an array with text and flags.
//		*
//		* The returned array will always contain a boolean 'found', indicating
//		* whether the parser function was found or not. It may also contain the
//		* following:
//		*  text: String|Object, resulting wikitext or PP DOM Object
//		*  isHTML: boolean, $text is HTML, armour it against wikitext transformation
//		*  isChildObj: boolean, $text is a DOM node needing expansion in a child frame
//		*  isLocalObj: boolean, $text is a DOM node needing expansion in the current frame
//		*  nowiki: boolean, wiki markup in $text should be escaped
//		*
//		* @since 1.21
//		* @param PPFrame $frame The current frame, contains template arguments
//		* @param String $function Function name
//		* @param array $args Arguments to the function
//		* @throws MWException
//		* @return array
//		*/
//		public function callParserFunction($frame, $function, array $args = []) {
//			global $wgContLang;
//
//			# Case sensitive functions
//			if (isset(this.mFunctionSynonyms[1][$function])) {
//				$function = this.mFunctionSynonyms[1][$function];
//			} else {
//				# Case insensitive functions
//				$function = $wgContLang->lc($function);
//				if (isset(this.mFunctionSynonyms[0][$function])) {
//					$function = this.mFunctionSynonyms[0][$function];
//				} else {
//					return [ 'found' => false ];
//				}
//			}
//
//			list($callback, $flags) = this.mFunctionHooks[$function];
//
//			# Workaround for PHP bug 35229 and similar
//			if (!is_callable($callback)) {
//				throw new MWException("Tag hook for $function is not callable\n");
//			}
//
//			$allArgs = [ &$this ];
//			if ($flags & self::SFH_OBJECT_ARGS) {
//				# Convert arguments to PPNodes and collect for appending to $allArgs
//				$funcArgs = [];
//				foreach ($args as $k => $v) {
//					if ($v instanceof PPNode || $k === 0) {
//						$funcArgs[] = $v;
//					} else {
//						$funcArgs[] = this.mPreprocessor->newPartNodeArray([ $k => $v ])->item(0);
//					}
//				}
//
//				# Add a frame parameter, and pass the arguments as an array
//				$allArgs[] = $frame;
//				$allArgs[] = $funcArgs;
//			} else {
//				# Convert arguments to plain text and append to $allArgs
//				foreach ($args as $k => $v) {
//					if ($v instanceof PPNode) {
//						$allArgs[] = trim($frame->expand($v));
//					} elseif (is_int($k) && $k >= 0) {
//						$allArgs[] = trim($v);
//					} else {
//						$allArgs[] = trim("$k=$v");
//					}
//				}
//			}
//
//			$result = call_user_func_array($callback, $allArgs);
//
//			# The interface for function hooks allows them to return a wikitext
//			# String or an array containing the String and any flags. This mungs
//			# things around to match what this method should return.
//			if (!is_array($result)) {
//				$result =[
//					'found' => true,
//					'text' => $result,
//				];
//			} else {
//				if (isset($result[0]) && !isset($result['text'])) {
//					$result['text'] = $result[0];
//				}
//				unset($result[0]);
//				$result += [
//					'found' => true,
//				];
//			}
//
//			$noparse = true;
//			$preprocessFlags = 0;
//			if (isset($result['noparse'])) {
//				$noparse = $result['noparse'];
//			}
//			if (isset($result['preprocessFlags'])) {
//				$preprocessFlags = $result['preprocessFlags'];
//			}
//
//			if (!$noparse) {
//				$result['text'] = this.preprocessToDom($result['text'], $preprocessFlags);
//				$result['isChildObj'] = true;
//			}
//
//			return $result;
//		}
//
//		/**
//		* Get the semi-parsed DOM representation of a template with a given title,
//		* and its redirect destination title. Cached.
//		*
//		* @param Title $title
//		*
//		* @return array
//		*/
//		public function getTemplateDom($title) {
//			$cacheTitle = $title;
//			$titleText = $title->getPrefixedDBkey();
//
//			if (isset(this.mTplRedirCache[$titleText])) {
//				list($ns, $dbk) = this.mTplRedirCache[$titleText];
//				$title = Title::makeTitle($ns, $dbk);
//				$titleText = $title->getPrefixedDBkey();
//			}
//			if (isset(this.mTplDomCache[$titleText])) {
//				return [ this.mTplDomCache[$titleText], $title ];
//			}
//
//			# Cache miss, go to the database
//			list($text, $title) = this.fetchTemplateAndTitle($title);
//
//			if ($text === false) {
//				this.mTplDomCache[$titleText] = false;
//				return [ false, $title ];
//			}
//
//			$dom = this.preprocessToDom($text, self::PTD_FOR_INCLUSION);
//			this.mTplDomCache[$titleText] = $dom;
//
//			if (!$title->equals($cacheTitle)) {
//				this.mTplRedirCache[$cacheTitle->getPrefixedDBkey()] =
//					[ $title->getNamespace(), $cdb = $title->getDBkey() ];
//			}
//
//			return [ $dom, $title ];
//		}
//
//		/**
//		* Fetch the current revision of a given title. Note that the revision
//		* (and even the title) may not exist in the database, so everything
//		* contributing to the output of the parser should use this method
//		* where possible, rather than getting the revisions themselves. This
//		* method also caches its results, so using it benefits performance.
//		*
//		* @since 1.24
//		* @param Title $title
//		* @return Revision
//		*/
//		public function fetchCurrentRevisionOfTitle($title) {
//			$cacheKey = $title->getPrefixedDBkey();
//			if (!this.currentRevisionCache) {
//				this.currentRevisionCache = new MapCacheLRU(100);
//			}
//			if (!this.currentRevisionCache->has($cacheKey)) {
//				this.currentRevisionCache->set($cacheKey,
//					// Defaults to Parser::statelessFetchRevision()
//					call_user_func(this.mOptions->getCurrentRevisionCallback(), $title, $this)
//				);
//			}
//			return this.currentRevisionCache->get($cacheKey);
//		}
//
//		/**
//		* Wrapper around Revision::newFromTitle to allow passing additional parameters
//		* without passing them on to it.
//		*
//		* @since 1.24
//		* @param Title $title
//		* @param Parser|boolean $parser
//		* @return Revision|boolean False if missing
//		*/
//		public static function statelessFetchRevision(Title $title, $parser = false) {
//			$pageId = $title->getArticleID();
//			$revId = $title->getLatestRevID();
//
//			$rev = Revision::newKnownCurrent(wfGetDB(DB_REPLICA), $pageId, $revId);
//			if ($rev) {
//				$rev->setTitle($title);
//			}
//
//			return $rev;
//		}
//
//		/**
//		* Fetch the unparsed text of a template and register a reference to it.
//		* @param Title $title
//		* @return array (String or false, Title)
//		*/
//		public function fetchTemplateAndTitle($title) {
//			// Defaults to Parser::statelessFetchTemplate()
//			$templateCb = this.mOptions->getTemplateCallback();
//			$stuff = call_user_func($templateCb, $title, $this);
//			// We use U+007F DELETE to distinguish strip markers from regular text.
//			$text = $stuff['text'];
//			if (is_string($stuff['text'])) {
//				$text = strtr($text, "\x7f", "?");
//			}
//			$finalTitle = isset($stuff['finalTitle']) ? $stuff['finalTitle'] : $title;
//			if (isset($stuff['deps'])) {
//				foreach ($stuff['deps'] as $dep) {
//					this.mOutput->addTemplate($dep['title'], $dep['page_id'], $dep['rev_id']);
//					if ($dep['title']->equals(this.getTitle())) {
//						// If we transclude ourselves, the final result
//						// will change based on the new version of the page
//						this.mOutput->setFlag('vary-revision');
//					}
//				}
//			}
//			return [ $text, $finalTitle ];
//		}
//
//		/**
//		* Fetch the unparsed text of a template and register a reference to it.
//		* @param Title $title
//		* @return String|boolean
//		*/
//		public function fetchTemplate($title) {
//			return this.fetchTemplateAndTitle($title)[0];
//		}
//
//		/**
//		* Static function to get a template
//		* Can be overridden via ParserOptions::setTemplateCallback().
//		*
//		* @param Title $title
//		* @param boolean|Parser $parser
//		*
//		* @return array
//		*/
//		public static function statelessFetchTemplate($title, $parser = false) {
//			$text = $skip = false;
//			$finalTitle = $title;
//			$deps = [];
//
//			# Loop to fetch the article, with up to 1 redirect
//			// @codingStandardsIgnoreStart Generic.CodeAnalysis.ForLoopWithTestFunctionCall.NotAllowed
//			for ($i = 0; $i < 2 && is_object($title); $i++) {
//				// @codingStandardsIgnoreEnd
//				# Give extensions a chance to select the revision instead
//				$id = false; # Assume current
//				Hooks::run('BeforeParserFetchTemplateAndtitle',
//					[ $parser, $title, &$skip, &$id ]);
//
//				if ($skip) {
//					$text = false;
//					$deps[] = [
//						'title' => $title,
//						'page_id' => $title->getArticleID(),
//						'rev_id' => null
//					];
//					break;
//				}
//				# Get the revision
//				if ($id) {
//					$rev = Revision::newFromId($id);
//				} elseif ($parser) {
//					$rev = $parser->fetchCurrentRevisionOfTitle($title);
//				} else {
//					$rev = Revision::newFromTitle($title);
//				}
//				$rev_id = $rev ? $rev->getId() : 0;
//				# If there is no current revision, there is no page
//				if ($id === false && !$rev) {
//					$linkCache = LinkCache::singleton();
//					$linkCache->addBadLinkObj($title);
//				}
//
//				$deps[] = [
//					'title' => $title,
//					'page_id' => $title->getArticleID(),
//					'rev_id' => $rev_id ];
//				if ($rev && !$title->equals($rev->getTitle())) {
//					# We fetched a rev from a different title; register it too...
//					$deps[] = [
//						'title' => $rev->getTitle(),
//						'page_id' => $rev->getPage(),
//						'rev_id' => $rev_id ];
//				}
//
//				if ($rev) {
//					$content = $rev->getContent();
//					$text = $content ? $content->getWikitextForTransclusion() : null;
//
//					Hooks::run('ParserFetchTemplate',
//						[ $parser, $title, $rev, &$text, &$deps ]);
//
//					if ($text === false || $text === null) {
//						$text = false;
//						break;
//					}
//				} elseif ($title->getNamespace() == NS_MEDIAWIKI) {
//					global $wgContLang;
//					$message = wfMessage($wgContLang->lcfirst($title->getText()))->inContentLanguage();
//					if (!$message->exists()) {
//						$text = false;
//						break;
//					}
//					$content = $message->content();
//					$text = $message->plain();
//				} else {
//					break;
//				}
//				if (!$content) {
//					break;
//				}
//				# Redirect?
//				$finalTitle = $title;
//				$title = $content->getRedirectTarget();
//			}
//			return [
//				'text' => $text,
//				'finalTitle' => $finalTitle,
//				'deps' => $deps ];
//		}
//
//		/**
//		* Fetch a file and its title and register a reference to it.
//		* If 'broken' is a key in $options then the file will appear as a broken thumbnail.
//		* @param Title $title
//		* @param array $options Array of options to RepoGroup::findFile
//		* @return File|boolean
//		*/
//		public function fetchFile($title, $options = []) {
//			return this.fetchFileAndTitle($title, $options)[0];
//		}
//
//		/**
//		* Fetch a file and its title and register a reference to it.
//		* If 'broken' is a key in $options then the file will appear as a broken thumbnail.
//		* @param Title $title
//		* @param array $options Array of options to RepoGroup::findFile
//		* @return array (File or false, Title of file)
//		*/
//		public function fetchFileAndTitle($title, $options = []) {
//			$file = this.fetchFileNoRegister($title, $options);
//
//			$time = $file ? $file->getTimestamp() : false;
//			$sha1 = $file ? $file->getSha1() : false;
//			# Register the file as a dependency...
//			this.mOutput->addImage($title->getDBkey(), $time, $sha1);
//			if ($file && !$title->equals($file->getTitle())) {
//				# Update fetched file title
//				$title = $file->getTitle();
//				this.mOutput->addImage($title->getDBkey(), $time, $sha1);
//			}
//			return [ $file, $title ];
//		}
//
//		/**
//		* Helper function for fetchFileAndTitle.
//		*
//		* Also useful if you need to fetch a file but not use it yet,
//		* for example to get the file's handler.
//		*
//		* @param Title $title
//		* @param array $options Array of options to RepoGroup::findFile
//		* @return File|boolean
//		*/
//		protected function fetchFileNoRegister($title, $options = []) {
//			if (isset($options['broken'])) {
//				$file = false; // broken thumbnail forced by hook
//			} elseif (isset($options['sha1'])) { // get by (sha1,timestamp)
//				$file = RepoGroup::singleton()->findFileFromKey($options['sha1'], $options);
//			} else { // get by (name,timestamp)
//				$file = wfFindFile($title, $options);
//			}
//			return $file;
//		}
//
//		/**
//		* Transclude an interwiki link.
//		*
//		* @param Title $title
//		* @param String $action
//		*
//		* @return String
//		*/
//		public function interwikiTransclude($title, $action) {
//			global $wgEnableScaryTranscluding;
//
//			if (!$wgEnableScaryTranscluding) {
//				return wfMessage('scarytranscludedisabled')->inContentLanguage()->text();
//			}
//
//			$url = $title->getFullURL([ 'action' => $action ]);
//
//			if (strlen($url) > 255) {
//				return wfMessage('scarytranscludetoolong')->inContentLanguage()->text();
//			}
//			return this.fetchScaryTemplateMaybeFromCache($url);
//		}
//
//		/**
//		* @param String $url
//		* @return mixed|String
//		*/
//		public function fetchScaryTemplateMaybeFromCache($url) {
//			global $wgTranscludeCacheExpiry;
//			$dbr = wfGetDB(DB_REPLICA);
//			$tsCond = $dbr->timestamp(time() - $wgTranscludeCacheExpiry);
//			$obj = $dbr->selectRow('transcache', [ 'tc_time', 'tc_contents' ],
//					[ 'tc_url' => $url, "tc_time >= " . $dbr->addQuotes($tsCond) ]);
//			if ($obj) {
//				return $obj->tc_contents;
//			}
//
//			$req = MWHttpRequest::factory($url, [], __METHOD__);
//			$status = $req->execute(); // Status Object
//			if ($status->isOK()) {
//				$text = $req->getContent();
//			} elseif ($req->getStatus() != 200) {
//				// Though we failed to fetch the content, this status is useless.
//				return wfMessage('scarytranscludefailed-httpstatus')
//					->params($url, $req->getStatus() /* HTTP status */)->inContentLanguage()->text();
//			} else {
//				return wfMessage('scarytranscludefailed', $url)->inContentLanguage()->text();
//			}
//
//			$dbw = wfGetDB(DB_MASTER);
//			$dbw->replace('transcache', [ 'tc_url' ], [
//				'tc_url' => $url,
//				'tc_time' => $dbw->timestamp(time()),
//				'tc_contents' => $text
//			]);
//			return $text;
//		}
//
//		/**
//		* Triple brace replacement -- used for template arguments
//		* @private
//		*
//		* @param array $piece
//		* @param PPFrame $frame
//		*
//		* @return array
//		*/
//		public function argSubstitution($piece, $frame) {
//
//			$error = false;
//			$parts = $piece['parts'];
//			$nameWithSpaces = $frame->expand($piece['title']);
//			$argName = trim($nameWithSpaces);
//			$Object = false;
//			$text = $frame->getArgument($argName);
//			if ($text === false && $parts->getLength() > 0
//				&& (this.ot['html']
//					|| this.ot['pre']
//					|| (this.ot['wiki'] && $frame->isTemplate())
//				)
//			) {
//				# No match in frame, use the supplied default
//				$Object = $parts->item(0)->getChildren();
//			}
//			if (!this.incrementIncludeSize('arg', strlen($text))) {
//				$error = '<!-- WARNING: argument omitted, expansion size too large -->';
//				this.limitationWarn('post-expand-template-argument');
//			}
//
//			if ($text === false && $Object === false) {
//				# No match anywhere
//				$Object = $frame->virtualBracketedImplode('{{{', '|', '}}}', $nameWithSpaces, $parts);
//			}
//			if ($error !== false) {
//				$text .= $error;
//			}
//			if ($Object !== false) {
//				$ret = [ 'Object' => $Object ];
//			} else {
//				$ret = [ 'text' => $text ];
//			}
//
//			return $ret;
//		}
//
//		/**
//		* Return the text to be used for a given extension tag.
//		* This is the ghost of strip().
//		*
//		* @param array $params Associative array of parameters:
//		*     name       PPNode for the tag name
//		*     attr       PPNode for unparsed text where tag attributes are thought to be
//		*     attributes Optional associative array of parsed attributes
//		*     inner      Contents of extension element
//		*     noClose    Original text did not have a close tag
//		* @param PPFrame $frame
//		*
//		* @throws MWException
//		* @return String
//		*/
//		public function extensionSubstitution($params, $frame) {
//			static $errorStr = '<span class="error">';
//			static $errorLen = 20;
//
//			$name = $frame->expand($params['name']);
//			if (substr($name, 0, $errorLen) === $errorStr) {
//				// Probably expansion depth or node count exceeded. Just punt the
//				// error up.
//				return $name;
//			}
//
//			$attrText = !isset($params['attr']) ? null : $frame->expand($params['attr']);
//			if (substr($attrText, 0, $errorLen) === $errorStr) {
//				// See above
//				return $attrText;
//			}
//
//			// We can't safely check if the expansion for $content resulted in an
//			// error, because the content could happen to be the error String
//			// (T149622).
//			$content = !isset($params['inner']) ? null : $frame->expand($params['inner']);
//
//			$marker = self::MARKER_PREFIX . "-$name-"
//				. sprintf('%08X', this.mMarkerIndex++) . self::MARKER_SUFFIX;
//
//			$isFunctionTag = isset(this.mFunctionTagHooks[strtolower($name)]) &&
//				(this.ot['html'] || this.ot['pre']);
//			if ($isFunctionTag) {
//				$markerType = 'none';
//			} else {
//				$markerType = 'general';
//			}
//			if (this.ot['html'] || $isFunctionTag) {
//				$name = strtolower($name);
//				$attributes = Sanitizer::decodeTagAttributes($attrText);
//				if (isset($params['attributes'])) {
//					$attributes = $attributes + $params['attributes'];
//				}
//
//				if (isset(this.mTagHooks[$name])) {
//					# Workaround for PHP bug 35229 and similar
//					if (!is_callable(this.mTagHooks[$name])) {
//						throw new MWException("Tag hook for $name is not callable\n");
//					}
//					$output = call_user_func_array(this.mTagHooks[$name],
//						[ $content, $attributes, $this, $frame ]);
//				} elseif (isset(this.mFunctionTagHooks[$name])) {
//					list($callback,) = this.mFunctionTagHooks[$name];
//					if (!is_callable($callback)) {
//						throw new MWException("Tag hook for $name is not callable\n");
//					}
//
//					$output = call_user_func_array($callback, [ &$this, $frame, $content, $attributes ]);
//				} else {
//					$output = '<span class="error">Invalid tag extension name: ' .
//						htmlspecialchars($name) . '</span>';
//				}
//
//				if (is_array($output)) {
//					# Extract flags to local scope (to override $markerType)
//					$flags = $output;
//					$output = $flags[0];
//					unset($flags[0]);
//					extract($flags);
//				}
//			} else {
//				if (is_null($attrText)) {
//					$attrText = '';
//				}
//				if (isset($params['attributes'])) {
//					foreach ($params['attributes'] as $attrName => $attrValue) {
//						$attrText .= ' ' . htmlspecialchars($attrName) . '="' .
//							htmlspecialchars($attrValue) . '"';
//					}
//				}
//				if ($content === null) {
//					$output = "<$name$attrText/>";
//				} else {
//					$close = is_null($params['close']) ? '' : $frame->expand($params['close']);
//					if (substr($close, 0, $errorLen) === $errorStr) {
//						// See above
//						return $close;
//					}
//					$output = "<$name$attrText>$content$close";
//				}
//			}
//
//			if ($markerType === 'none') {
//				return $output;
//			} elseif ($markerType === 'nowiki') {
//				this.mStripState->addNoWiki($marker, $output);
//			} elseif ($markerType === 'general') {
//				this.mStripState->addGeneral($marker, $output);
//			} else {
//				throw new MWException(__METHOD__ . ': invalid marker type');
//			}
//			return $marker;
//		}
//
//		/**
//		* Increment an include size counter
//		*
//		* @param String $type The type of expansion
//		* @param int $size The size of the text
//		* @return boolean False if this inclusion would take it over the maximum, true otherwise
//		*/
//		public function incrementIncludeSize($type, $size) {
//			if (this.mIncludeSizes[$type] + $size > this.mOptions->getMaxIncludeSize()) {
//				return false;
//			} else {
//				this.mIncludeSizes[$type] += $size;
//				return true;
//			}
//		}
//
//		/**
//		* Increment the expensive function count
//		*
//		* @return boolean False if the limit has been exceeded
//		*/
//		public function incrementExpensiveFunctionCount() {
//			this.mExpensiveFunctionCount++;
//			return this.mExpensiveFunctionCount <= this.mOptions->getExpensiveParserFunctionLimit();
//		}

	// XO.MW:MOVED
	// public void doDoubleUnderscore($text) {}

//		/**
//		* @see ParserOutput::addTrackingCategory()
//		* @param String $msg Message key
//		* @return boolean Whether the addition was successful
//		*/
//		public function addTrackingCategory($msg) {
//			return this.mOutput->addTrackingCategory($msg, this.mTitle);
//		}
//
//		/**
//		* This function accomplishes several tasks:
//		* 1) Auto-number headings if that option is enabled
//		* 2) Add an [edit] link to sections for users who have enabled the option and can edit the page
//		* 3) Add a Table of contents on the top for users who have enabled the option
//		* 4) Auto-anchor headings
//		*
//		* It loops through all headlines, collects the necessary data, then splits up the
//		* String and re-inserts the newly formatted headlines.
//		*
//		* @param String $text
//		* @param String $origText Original, untouched wikitext
//		* @param boolean $isMain
//		* @return mixed|String
//		* @private
//		*/
//		public function formatHeadings($text, $origText, $isMain = true) {
//			global $wgMaxTocLevel, $wgExperimentalHtmlIds;
//
//			# Inhibit editsection links if requested in the page
//			if (isset(this.mDoubleUnderscores['noeditsection'])) {
//				$maybeShowEditLink = $showEditLink = false;
//			} else {
//				$maybeShowEditLink = true; /* Actual presence will depend on ParserOptions option */
//				$showEditLink = this.mOptions->getEditSection();
//			}
//			if ($showEditLink) {
//				this.mOutput->setEditSectionTokens(true);
//			}
//
//			# Get all headlines for numbering them and adding funky stuff like [edit]
//			# links - this is for later, but we need the number of headlines right now
//			$matches = [];
//			$numMatches = preg_match_all(
//				'/<H(?P<level>[1-6])(?P<attrib>.*?>)\s*(?P<header>[\s\S]*?)\s*<\/H[1-6] *>/i',
//				$text,
//				$matches
//			);
//
//			# if there are fewer than 4 headlines in the article, do not show TOC
//			# unless it's been explicitly enabled.
//			$enoughToc = this.mShowToc &&
//				(($numMatches >= 4) || this.mForceTocPosition);
//
//			# Allow user to stipulate that a page should have a "new section"
//			# link added via __NEWSECTIONLINK__
//			if (isset(this.mDoubleUnderscores['newsectionlink'])) {
//				this.mOutput->setNewSection(true);
//			}
//
//			# Allow user to remove the "new section"
//			# link via __NONEWSECTIONLINK__
//			if (isset(this.mDoubleUnderscores['nonewsectionlink'])) {
//				this.mOutput->hideNewSection(true);
//			}
//
//			# if the String __FORCETOC__ (not case-sensitive) occurs in the HTML,
//			# override above conditions and always show TOC above first header
//			if (isset(this.mDoubleUnderscores['forcetoc'])) {
//				this.mShowToc = true;
//				$enoughToc = true;
//			}
//
//			# headline counter
//			$headlineCount = 0;
//			$numVisible = 0;
//
//			# Ugh .. the TOC should have neat indentation levels which can be
//			# passed to the skin functions. These are determined here
//			$toc = '';
//			$full = '';
//			$head = [];
//			$sublevelCount = [];
//			$levelCount = [];
//			$level = 0;
//			$prevlevel = 0;
//			$toclevel = 0;
//			$prevtoclevel = 0;
//			$markerRegex = self::MARKER_PREFIX . "-h-(\d+)-" . self::MARKER_SUFFIX;
//			$baseTitleText = this.mTitle->getPrefixedDBkey();
//			$oldType = this.mOutputType;
//			this.setOutputType(self::OT_WIKI);
//			$frame = this.getPreprocessor()->newFrame();
//			$root = this.preprocessToDom($origText);
//			$node = $root->getFirstChild();
//			$byteOffset = 0;
//			$tocraw = [];
//			$refers = [];
//
//			$headlines = $numMatches !== false ? $matches[3] : [];
//
//			foreach ($headlines as $headline) {
//				$isTemplate = false;
//				$titleText = false;
//				$sectionIndex = false;
//				$numbering = '';
//				$markerMatches = [];
//				if (preg_match("/^$markerRegex/", $headline, $markerMatches)) {
//					$serial = $markerMatches[1];
//					list($titleText, $sectionIndex) = this.mHeadings[$serial];
//					$isTemplate = ($titleText != $baseTitleText);
//					$headline = preg_replace("/^$markerRegex\\s*/", "", $headline);
//				}
//
//				if ($toclevel) {
//					$prevlevel = $level;
//				}
//				$level = $matches[1][$headlineCount];
//
//				if ($level > $prevlevel) {
//					# Increase TOC level
//					$toclevel++;
//					$sublevelCount[$toclevel] = 0;
//					if ($toclevel < $wgMaxTocLevel) {
//						$prevtoclevel = $toclevel;
//						$toc .= Linker::tocIndent();
//						$numVisible++;
//					}
//				} elseif ($level < $prevlevel && $toclevel > 1) {
//					# Decrease TOC level, find level to jump to
//
//					for ($i = $toclevel; $i > 0; $i--) {
//						if ($levelCount[$i] == $level) {
//							# Found last matching level
//							$toclevel = $i;
//							break;
//						} elseif ($levelCount[$i] < $level) {
//							# Found first matching level below current level
//							$toclevel = $i + 1;
//							break;
//						}
//					}
//					if ($i == 0) {
//						$toclevel = 1;
//					}
//					if ($toclevel < $wgMaxTocLevel) {
//						if ($prevtoclevel < $wgMaxTocLevel) {
//							# Unindent only if the previous toc level was shown :p
//							$toc .= Linker::tocUnindent($prevtoclevel - $toclevel);
//							$prevtoclevel = $toclevel;
//						} else {
//							$toc .= Linker::tocLineEnd();
//						}
//					}
//				} else {
//					# No change in level, end TOC line
//					if ($toclevel < $wgMaxTocLevel) {
//						$toc .= Linker::tocLineEnd();
//					}
//				}
//
//				$levelCount[$toclevel] = $level;
//
//				# count number of headlines for each level
//				$sublevelCount[$toclevel]++;
//				$dot = 0;
//				for ($i = 1; $i <= $toclevel; $i++) {
//					if (!empty($sublevelCount[$i])) {
//						if ($dot) {
//							$numbering .= '.';
//						}
//						$numbering .= this.getTargetLanguage()->formatNum($sublevelCount[$i]);
//						$dot = 1;
//					}
//				}
//
//				# The safe header is a version of the header text safe to use for links
//
//				# Remove link placeholders by the link text.
//				#     <!--LINK number-->
//				# turns into
//				#     link text with suffix
//				# Do this before unstrip since link text can contain strip markers
//				$safeHeadline = this.replaceLinkHoldersText($headline);
//
//				# Avoid insertion of weird stuff like <math> by expanding the relevant sections
//				$safeHeadline = this.mStripState->unstripBoth($safeHeadline);
//
//				# Strip out HTML (first regex removes any tag not allowed)
//				# Allowed tags are:
//				# * <sup> and <sub> (T10393)
//				# * <i> (T28375)
//				# * <b> (r105284)
//				# * <bdi> (T74884)
//				# * <span dir="rtl"> and <span dir="ltr"> (T37167)
//				# * <s> and <strike> (T35715)
//				# We strip any parameter from accepted tags (second regex), except dir="rtl|ltr" from <span>,
//				# to allow setting directionality in toc items.
//				$tocline = preg_replace(
//					[
//						'#<(?!/?(span|sup|sub|bdi|i|b|s|strike)(?: [^>]*)?>).*?>#',
//						'#<(/?(?:span(?: dir="(?:rtl|ltr)")?|sup|sub|bdi|i|b|s|strike))(?: .*?)?>#'
//					],
//					[ '', '<$1>' ],
//					$safeHeadline
//				);
//
//				# Strip '<span></span>', which is the result from the above if
//				# <span id="foo"></span> is used to produce an additional anchor
//				# for a section.
//				$tocline = str_replace('<span></span>', '', $tocline);
//
//				$tocline = trim($tocline);
//
//				# For the anchor, strip out HTML-y stuff period
//				$safeHeadline = preg_replace('/<.*?>/', '', $safeHeadline);
//				$safeHeadline = Sanitizer::normalizeSectionNameWhitespace($safeHeadline);
//
//				# Save headline for section edit hint before it's escaped
//				$headlineHint = $safeHeadline;
//
//				if ($wgExperimentalHtmlIds) {
//					# For reverse compatibility, provide an id that's
//					# HTML4-compatible, like we used to.
//					# It may be worth noting, academically, that it's possible for
//					# the legacy anchor to conflict with a non-legacy headline
//					# anchor on the page.  In this case likely the "correct" thing
//					# would be to either drop the legacy anchors or make sure
//					# they're numbered first.  However, this would require people
//					# to type in section names like "abc_.D7.93.D7.90.D7.A4"
//					# manually, so let's not bother worrying about it.
//					$legacyHeadline = Sanitizer::escapeId($safeHeadline,
//						[ 'noninitial', 'legacy' ]);
//					$safeHeadline = Sanitizer::escapeId($safeHeadline);
//
//					if ($legacyHeadline == $safeHeadline) {
//						# No reason to have both (in fact, we can't)
//						$legacyHeadline = false;
//					}
//				} else {
//					$legacyHeadline = false;
//					$safeHeadline = Sanitizer::escapeId($safeHeadline,
//						'noninitial');
//				}
//
//				# HTML names must be case-insensitively unique (T12721).
//				# This does not apply to Unicode characters per
//				# https://www.w3.org/TR/html5/infrastructure.html#case-sensitivity-and-String-comparison
//				# @todo FIXME: We may be changing them depending on the current locale.
//				$arrayKey = strtolower($safeHeadline);
//				if ($legacyHeadline === false) {
//					$legacyArrayKey = false;
//				} else {
//					$legacyArrayKey = strtolower($legacyHeadline);
//				}
//
//				# Create the anchor for linking from the TOC to the section
//				$anchor = $safeHeadline;
//				$legacyAnchor = $legacyHeadline;
//				if (isset($refers[$arrayKey])) {
//					// @codingStandardsIgnoreStart
//					for ($i = 2; isset($refers["${arrayKey}_$i"]); ++$i);
//					// @codingStandardsIgnoreEnd
//					$anchor .= "_$i";
//					$refers["${arrayKey}_$i"] = true;
//				} else {
//					$refers[$arrayKey] = true;
//				}
//				if ($legacyHeadline !== false && isset($refers[$legacyArrayKey])) {
//					// @codingStandardsIgnoreStart
//					for ($i = 2; isset($refers["${legacyArrayKey}_$i"]); ++$i);
//					// @codingStandardsIgnoreEnd
//					$legacyAnchor .= "_$i";
//					$refers["${legacyArrayKey}_$i"] = true;
//				} else {
//					$refers[$legacyArrayKey] = true;
//				}
//
//				# Don't number the heading if it is the only one (looks silly)
//				if (count($matches[3]) > 1 && this.mOptions->getNumberHeadings()) {
//					# the two are different if the line contains a link
//					$headline = Html::element(
//						'span',
//						[ 'class' => 'mw-headline-number' ],
//						$numbering
//					) . ' ' . $headline;
//				}
//
//				if ($enoughToc && (!isset($wgMaxTocLevel) || $toclevel < $wgMaxTocLevel)) {
//					$toc .= Linker::tocLine($anchor, $tocline,
//						$numbering, $toclevel, ($isTemplate ? false : $sectionIndex));
//				}
//
//				# Add the section to the section tree
//				# Find the DOM node for this header
//				$noOffset = ($isTemplate || $sectionIndex === false);
//				while ($node && !$noOffset) {
//					if ($node->getName() === 'h') {
//						$bits = $node->splitHeading();
//						if ($bits['i'] == $sectionIndex) {
//							break;
//						}
//					}
//					$byteOffset += mb_strlen(this.mStripState->unstripBoth(
//						$frame->expand($node, PPFrame::RECOVER_ORIG)));
//					$node = $node->getNextSibling();
//				}
//				$tocraw[] = [
//					'toclevel' => $toclevel,
//					'level' => $level,
//					'line' => $tocline,
//					'number' => $numbering,
//					'index' => ($isTemplate ? 'T-' : '') . $sectionIndex,
//					'fromtitle' => $titleText,
//					'byteoffset' => ($noOffset ? null : $byteOffset),
//					'anchor' => $anchor,
//				];
//
//				# give headline the correct <h#> tag
//				if ($maybeShowEditLink && $sectionIndex !== false) {
//					// Output edit section links as markers with styles that can be customized by skins
//					if ($isTemplate) {
//						# Put a T flag in the section identifier, to indicate to extractSections()
//						# that sections inside <includeonly> should be counted.
//						$editsectionPage = $titleText;
//						$editsectionSection = "T-$sectionIndex";
//						$editsectionContent = null;
//					} else {
//						$editsectionPage = this.mTitle->getPrefixedText();
//						$editsectionSection = $sectionIndex;
//						$editsectionContent = $headlineHint;
//					}
//					// We use a bit of pesudo-xml for editsection markers. The
//					// language converter is run later on. Using a UNIQ style marker
//					// leads to the converter screwing up the tokens when it
//					// converts stuff. And trying to insert strip tags fails too. At
//					// this point all real inputted tags have already been escaped,
//					// so we don't have to worry about a user trying to input one of
//					// these markers directly. We use a page and section attribute
//					// to stop the language converter from converting these
//					// important bits of data, but put the headline hint inside a
//					// content block because the language converter is supposed to
//					// be able to convert that piece of data.
//					// Gets replaced with html in ParserOutput::getText
//					$editlink = '<mw:editsection page="' . htmlspecialchars($editsectionPage);
//					$editlink .= '" section="' . htmlspecialchars($editsectionSection) . '"';
//					if ($editsectionContent !== null) {
//						$editlink .= '>' . $editsectionContent . '</mw:editsection>';
//					} else {
//						$editlink .= '/>';
//					}
//				} else {
//					$editlink = '';
//				}
//				$head[$headlineCount] = Linker::makeHeadline($level,
//					$matches['attrib'][$headlineCount], $anchor, $headline,
//					$editlink, $legacyAnchor);
//
//				$headlineCount++;
//			}
//
//			this.setOutputType($oldType);
//
//			# Never ever show TOC if no headers
//			if ($numVisible < 1) {
//				$enoughToc = false;
//			}
//
//			if ($enoughToc) {
//				if ($prevtoclevel > 0 && $prevtoclevel < $wgMaxTocLevel) {
//					$toc .= Linker::tocUnindent($prevtoclevel - 1);
//				}
//				$toc = Linker::tocList($toc, this.mOptions->getUserLangObj());
//				this.mOutput->setTOCHTML($toc);
//				$toc = self::TOC_START . $toc . self::TOC_END;
//				this.mOutput->addModules('mediawiki.toc');
//			}
//
//			if ($isMain) {
//				this.mOutput->setSections($tocraw);
//			}
//
//			# split up and insert constructed headlines
//			$blocks = preg_split('/<H[1-6].*?>[\s\S]*?<\/H[1-6]>/i', $text);
//			$i = 0;
//
//			// build an array of document sections
//			$sections = [];
//			foreach ($blocks as $block) {
//				// $head is zero-based, sections aren't.
//				if (empty($head[$i - 1])) {
//					$sections[$i] = $block;
//				} else {
//					$sections[$i] = $head[$i - 1] . $block;
//				}
//
//				/**
//				* Send a hook, one per section.
//				* The idea here is to be able to make section-level DIVs, but to do so in a
//				* lower-impact, more correct way than r50769
//				*
//				* $this : caller
//				* $section : the section number
//				* &$sectionContent : ref to the content of the section
//				* $showEditLinks : boolean describing whether this section has an edit link
//				*/
//				Hooks::run('ParserSectionCreate', [ $this, $i, &$sections[$i], $showEditLink ]);
//
//				$i++;
//			}
//
//			if ($enoughToc && $isMain && !this.mForceTocPosition) {
//				// append the TOC at the beginning
//				// Top anchor now in skin
//				$sections[0] = $sections[0] . $toc . "\n";
//			}
//
//			$full .= implode('', $sections);
//
//			if (this.mForceTocPosition) {
//				return str_replace('<!--MWTOC-->', $toc, $full);
//			} else {
//				return $full;
//			}
//		}
//
//		/**
//		* Transform wiki markup when saving a page by doing "\r\n" -> "\n"
//		* conversion, substituting signatures, {{subst:}} templates, etc.
//		*
//		* @param String $text The text to transform
//		* @param Title $title The Title Object for the current article
//		* @param User $user The User Object describing the current user
//		* @param ParserOptions $options Parsing options
//		* @param boolean $clearState Whether to clear the parser state first
//		* @return String The altered wiki markup
//		*/
//		public function preSaveTransform($text, Title $title, User $user,
//			ParserOptions $options, $clearState = true
//		) {
//			if ($clearState) {
//				$magicScopeVariable = this.synchronized();
//			}
//			this.startParse($title, $options, self::OT_WIKI, $clearState);
//			this.setUser($user);
//
//			// We still normalize line endings for backwards-compatibility
//			// with other code that just calls PST, but this should already
//			// be handled in TextContent subclasses
//			$text = TextContent::normalizeLineEndings($text);
//
//			if ($options->getPreSaveTransform()) {
//				$text = this.pstPass2($text, $user);
//			}
//			$text = this.mStripState->unstripBoth($text);
//
//			this.setUser(null); # Reset
//
//			return $text;
//		}
//
//		/**
//		* Pre-save transform helper function
//		*
//		* @param String $text
//		* @param User $user
//		*
//		* @return String
//		*/
//		private function pstPass2($text, $user) {
//			global $wgContLang;
//
//			# Note: This is the timestamp saved as hardcoded wikitext to
//			# the database, we use $wgContLang here in order to give
//			# everyone the same signature and use the default one rather
//			# than the one selected in each user's preferences.
//			# (see also T14815)
//			$ts = this.mOptions->getTimestamp();
//			$timestamp = MWTimestamp::getLocalInstance($ts);
//			$ts = $timestamp->format('YmdHis');
//			$tzMsg = $timestamp->getTimezoneMessage()->inContentLanguage()->text();
//
//			$d = $wgContLang->timeanddate($ts, false, false) . " ($tzMsg)";
//
//			# Variable replacement
//			# Because mOutputType is OT_WIKI, this will only process {{subst:xxx}} type tags
//			$text = this.replaceVariables($text);
//
//			# This works almost by chance, as the replaceVariables are done before the getUserSig(),
//			# which may corrupt this parser instance via its wfMessage()->text() call-
//
//			# Signatures
//			$sigText = this.getUserSig($user);
//			$text = strtr($text, [
//				'~~~~~' => $d,
//				'~~~~' => "$sigText $d",
//				'~~~' => $sigText
//			]);
//
//			# Context links ("pipe tricks"): [[|name]] and [[name (context)|]]
//			$tc = '[' . Title::legalChars() . ']';
//			$nc = '[ _0-9A-Za-z\x80-\xff-]'; # Namespaces can use non-ascii!
//
//			// [[ns:page (context)|]]
//			$p1 = "/\[\[(:?$nc+:|:|)($tc+?)(?\\($tc+\\))\\|]]/";
//			// [[ns:page(context)|]] (double-width brackets, added in r40257)
//			$p4 = "/\[\[(:?$nc+:|:|)($tc+?)(?($tc+))\\|]]/";
//			// [[ns:page (context), context|]] (using either single or double-width comma)
//			$p3 = "/\[\[(:?$nc+:|:|)($tc+?)(?\\($tc+\\)|)((?:, |,)$tc+|)\\|]]/";
//			// [[|page]] (reverse pipe trick: add context from page title)
//			$p2 = "/\[\[\\|($tc+)]]/";
//
//			# try $p1 first, to turn "[[A, B (C)|]]" into "[[A, B (C)|A, B]]"
//			$text = preg_replace($p1, '[[\\1\\2\\3|\\2]]', $text);
//			$text = preg_replace($p4, '[[\\1\\2\\3|\\2]]', $text);
//			$text = preg_replace($p3, '[[\\1\\2\\3\\4|\\2]]', $text);
//
//			$t = this.mTitle->getText();
//			$m = [];
//			if (preg_match("/^($nc+:|)$tc+?(\\($tc+\\))$/", $t, $m)) {
//				$text = preg_replace($p2, "[[$m[1]\\1$m[2]|\\1]]", $text);
//			} elseif (preg_match("/^($nc+:|)$tc+?(, $tc+|)$/", $t, $m) && "$m[1]$m[2]" != '') {
//				$text = preg_replace($p2, "[[$m[1]\\1$m[2]|\\1]]", $text);
//			} else {
//				# if there's no context, don't bother duplicating the title
//				$text = preg_replace($p2, '[[\\1]]', $text);
//			}
//
//			return $text;
//		}
//
//		/**
//		* Fetch the user's signature text, if any, and normalize to
//		* validated, ready-to-insert wikitext.
//		* If you have pre-fetched the nickname or the fancySig option, you can
//		* specify them here to save a database query.
//		* Do not reuse this parser instance after calling getUserSig(),
//		* as it may have changed if it's the $wgParser.
//		*
//		* @param User $user
//		* @param String|boolean $nickname Nickname to use or false to use user's default nickname
//		* @param boolean|null $fancySig whether the nicknname is the complete signature
//		*    or null to use default value
//		* @return String
//		*/
//		public function getUserSig(&$user, $nickname = false, $fancySig = null) {
//			global $wgMaxSigChars;
//
//			$username = $user->getName();
//
//			# If not given, retrieve from the user Object.
//			if ($nickname === false) {
//				$nickname = $user->getOption('nickname');
//			}
//
//			if (is_null($fancySig)) {
//				$fancySig = $user->getBoolOption('fancysig');
//			}
//
//			$nickname = $nickname == null ? $username : $nickname;
//
//			if (mb_strlen($nickname) > $wgMaxSigChars) {
//				$nickname = $username;
//				wfDebug(__METHOD__ . ": $username has overlong signature.\n");
//			} elseif ($fancySig !== false) {
//				# Sig. might contain markup; validate this
//				if (this.validateSig($nickname) !== false) {
//					# Validated; clean up (if needed) and return it
//					return this.cleanSig($nickname, true);
//				} else {
//					# Failed to validate; fall back to the default
//					$nickname = $username;
//					wfDebug(__METHOD__ . ": $username has bad XML tags in signature.\n");
//				}
//			}
//
//			# Make sure nickname doesnt get a sig in a sig
//			$nickname = self::cleanSigInSig($nickname);
//
//			# If we're still here, make it a link to the user page
//			$userText = wfEscapeWikiText($username);
//			$nickText = wfEscapeWikiText($nickname);
//			$msgName = $user->isAnon() ? 'signature-anon' : 'signature';
//
//			return wfMessage($msgName, $userText, $nickText)->inContentLanguage()
//				->title(this.getTitle())->text();
//		}
//
//		/**
//		* Check that the user's signature contains no bad XML
//		*
//		* @param String $text
//		* @return String|boolean An expanded String, or false if invalid.
//		*/
//		public function validateSig($text) {
//			return Xml::isWellFormedXmlFragment($text) ? $text : false;
//		}
//
//		/**
//		* Clean up signature text
//		*
//		* 1) Strip 3, 4 or 5 tildes out of signatures @see cleanSigInSig
//		* 2) Substitute all transclusions
//		*
//		* @param String $text
//		* @param boolean $parsing Whether we're cleaning (preferences save) or parsing
//		* @return String Signature text
//		*/
//		public function cleanSig($text, $parsing = false) {
//			if (!$parsing) {
//				global $wgTitle;
//				$magicScopeVariable = this.synchronized();
//				this.startParse($wgTitle, new ParserOptions, self::OT_PREPROCESS, true);
//			}
//
//			# Option to disable this feature
//			if (!this.mOptions->getCleanSignatures()) {
//				return $text;
//			}
//
//			# @todo FIXME: Regex doesn't respect extension tags or nowiki
//			#  => Move this logic to braceSubstitution()
//			$substWord = MagicWord::get('subst');
//			$substRegex = '/\{\{(?!(?:' . $substWord->getBaseRegex() . '))/x' . $substWord->getRegexCase();
//			$substText = '{{' . $substWord->getSynonym(0);
//
//			$text = preg_replace($substRegex, $substText, $text);
//			$text = self::cleanSigInSig($text);
//			$dom = this.preprocessToDom($text);
//			$frame = this.getPreprocessor()->newFrame();
//			$text = $frame->expand($dom);
//
//			if (!$parsing) {
//				$text = this.mStripState->unstripBoth($text);
//			}
//
//			return $text;
//		}
//
//		/**
//		* Strip 3, 4 or 5 tildes out of signatures.
//		*
//		* @param String $text
//		* @return String Signature text with /~{3,5}/ removed
//		*/
//		public static function cleanSigInSig($text) {
//			$text = preg_replace('/~{3,5}/', '', $text);
//			return $text;
//		}
//
//		/**
//		* Set up some variables which are usually set up in parse()
//		* so that an external function can call some class members with confidence
//		*
//		* @param Title|null $title
//		* @param ParserOptions $options
//		* @param int $outputType
//		* @param boolean $clearState
//		*/
//		public function startExternalParse(Title $title = null, ParserOptions $options,
//			$outputType, $clearState = true
//		) {
//			this.startParse($title, $options, $outputType, $clearState);
//		}
//
//		/**
//		* @param Title|null $title
//		* @param ParserOptions $options
//		* @param int $outputType
//		* @param boolean $clearState
//		*/
//		private function startParse(Title $title = null, ParserOptions $options,
//			$outputType, $clearState = true
//		) {
//			this.setTitle($title);
//			this.mOptions = $options;
//			this.setOutputType($outputType);
//			if ($clearState) {
//				this.clearState();
//			}
//		}
//
//		/**
//		* Wrapper for preprocess()
//		*
//		* @param String $text The text to preprocess
//		* @param ParserOptions $options Options
//		* @param Title|null $title Title Object or null to use $wgTitle
//		* @return String
//		*/
//		public function transformMsg($text, $options, $title = null) {
//			static $executing = false;
//
//			# Guard against infinite recursion
//			if ($executing) {
//				return $text;
//			}
//			$executing = true;
//
//			if (!$title) {
//				global $wgTitle;
//				$title = $wgTitle;
//			}
//
//			$text = this.preprocess($text, $title, $options);
//
//			$executing = false;
//			return $text;
//		}
//
//		/**
//		* Create an HTML-style tag, e.g. "<yourtag>special text</yourtag>"
//		* The callback should have the following form:
//		*    function myParserHook($text, $params, $parser, $frame) { ... }
//		*
//		* Transform and return $text. Use $parser for any required context, e.g. use
//		* $parser->getTitle() and $parser->getOptions() not $wgTitle or $wgOut->mParserOptions
//		*
//		* Hooks may return extended information by returning an array, of which the
//		* first numbered element (index 0) must be the return String, and all other
//		* entries are extracted into local variables within an @gplx.Internal protected function
//		* in the Parser class.
//		*
//		* This interface (introduced r61913) appears to be undocumented, but
//		* 'markerType' is used by some core tag hooks to override which strip
//		* array their results are placed in. **Use great caution if attempting
//		* this interface, as it is not documented and injudicious use could smash
//		* private variables.**
//		*
//		* @param String $tag The tag to use, e.g. 'hook' for "<hook>"
//		* @param callable $callback The callback function (and Object) to use for the tag
//		* @throws MWException
//		* @return callable|null The old value of the mTagHooks array associated with the hook
//		*/
//		public function setHook($tag, $callback) {
//			$tag = strtolower($tag);
//			if (preg_match('/[<>\r\n]/', $tag, $m)) {
//				throw new MWException("Invalid character {$m[0]} in setHook('$tag', ...) call");
//			}
//			$oldVal = isset(this.mTagHooks[$tag]) ? this.mTagHooks[$tag] : null;
//			this.mTagHooks[$tag] = $callback;
//			if (!in_array($tag, this.mStripList)) {
//				this.mStripList[] = $tag;
//			}
//
//			return $oldVal;
//		}
//
//		/**
//		* As setHook(), but letting the contents be parsed.
//		*
//		* Transparent tag hooks are like regular XML-style tag hooks, except they
//		* operate late in the transformation sequence, on HTML instead of wikitext.
//		*
//		* This is probably obsoleted by things dealing with parser frames?
//		* The only extension currently using it is geoserver.
//		*
//		* @since 1.10
//		* @todo better document or deprecate this
//		*
//		* @param String $tag The tag to use, e.g. 'hook' for "<hook>"
//		* @param callable $callback The callback function (and Object) to use for the tag
//		* @throws MWException
//		* @return callable|null The old value of the mTagHooks array associated with the hook
//		*/
//		public function setTransparentTagHook($tag, $callback) {
//			$tag = strtolower($tag);
//			if (preg_match('/[<>\r\n]/', $tag, $m)) {
//				throw new MWException("Invalid character {$m[0]} in setTransparentHook('$tag', ...) call");
//			}
//			$oldVal = isset(this.mTransparentTagHooks[$tag]) ? this.mTransparentTagHooks[$tag] : null;
//			this.mTransparentTagHooks[$tag] = $callback;
//
//			return $oldVal;
//		}
//
//		/**
//		* Remove all tag hooks
//		*/
//		public function clearTagHooks() {
//			this.mTagHooks = [];
//			this.mFunctionTagHooks = [];
//			this.mStripList = this.mDefaultStripList;
//		}
//
//		/**
//		* Create a function, e.g. {{sum:1|2|3}}
//		* The callback function should have the form:
//		*    function myParserFunction(&$parser, $arg1, $arg2, $arg3) { ... }
//		*
//		* Or with Parser::SFH_OBJECT_ARGS:
//		*    function myParserFunction($parser, $frame, $args) { ... }
//		*
//		* The callback may either return the text result of the function, or an array with the text
//		* in element 0, and a number of flags in the other elements. The names of the flags are
//		* specified in the keys. Valid flags are:
//		*   found                     The text returned is valid, stop processing the template. This
//		*                             is on by default.
//		*   nowiki                    Wiki markup in the return value should be escaped
//		*   isHTML                    The returned text is HTML, armour it against wikitext transformation
//		*
//		* @param String $id The magic word ID
//		* @param callable $callback The callback function (and Object) to use
//		* @param int $flags A combination of the following flags:
//		*     Parser::SFH_NO_HASH      No leading hash, i.e. {{plural:...}} instead of {{#if:...}}
//		*
//		*     Parser::SFH_OBJECT_ARGS  Pass the template arguments as PPNode objects instead of text.
//		*     This allows for conditional expansion of the parse tree, allowing you to eliminate dead
//		*     branches and thus speed up parsing. It is also possible to analyse the parse tree of
//		*     the arguments, and to control the way they are expanded.
//		*
//		*     The $frame parameter is a PPFrame. This can be used to produce expanded text from the
//		*     arguments, for instance:
//		*         $text = isset($args[0]) ? $frame->expand($args[0]) : '';
//		*
//		*     For technical reasons, $args[0] is pre-expanded and will be a String. This may change in
//		*     future versions. Please call $frame->expand() on it anyway so that your code keeps
//		*     working if/when this is changed.
//		*
//		*     If you want whitespace to be trimmed from $args, you need to do it yourself, post-
//		*     expansion.
//		*
//		*     Please read the documentation in includes/parser/Preprocessor.php for more information
//		*     about the methods available in PPFrame and PPNode.
//		*
//		* @throws MWException
//		* @return String|callable The old callback function for this name, if any
//		*/
//		public function setFunctionHook($id, $callback, $flags = 0) {
//			global $wgContLang;
//
//			$oldVal = isset(this.mFunctionHooks[$id]) ? this.mFunctionHooks[$id][0] : null;
//			this.mFunctionHooks[$id] = [ $callback, $flags ];
//
//			# Add to function cache
//			$mw = MagicWord::get($id);
//			if (!$mw) {
//				throw new MWException(__METHOD__ . '() expecting a magic word identifier.');
//			}
//
//			$synonyms = $mw->getSynonyms();
//			$sensitive = intval($mw->isCaseSensitive());
//
//			foreach ($synonyms as $syn) {
//				# Case
//				if (!$sensitive) {
//					$syn = $wgContLang->lc($syn);
//				}
//				# Add leading hash
//				if (!($flags & self::SFH_NO_HASH)) {
//					$syn = '#' . $syn;
//				}
//				# Remove trailing colon
//				if (substr($syn, -1, 1) === ':') {
//					$syn = substr($syn, 0, -1);
//				}
//				this.mFunctionSynonyms[$sensitive][$syn] = $id;
//			}
//			return $oldVal;
//		}
//
//		/**
//		* Get all registered function hook identifiers
//		*
//		* @return array
//		*/
//		public function getFunctionHooks() {
//			return array_keys(this.mFunctionHooks);
//		}
//
//		/**
//		* Create a tag function, e.g. "<test>some stuff</test>".
//		* Unlike tag hooks, tag functions are parsed at preprocessor level.
//		* Unlike parser functions, their content is not preprocessed.
//		* @param String $tag
//		* @param callable $callback
//		* @param int $flags
//		* @throws MWException
//		* @return null
//		*/
//		public function setFunctionTagHook($tag, $callback, $flags) {
//			$tag = strtolower($tag);
//			if (preg_match('/[<>\r\n]/', $tag, $m)) {
//				throw new MWException("Invalid character {$m[0]} in setFunctionTagHook('$tag', ...) call");
//			}
//			$old = isset(this.mFunctionTagHooks[$tag]) ?
//				this.mFunctionTagHooks[$tag] : null;
//			this.mFunctionTagHooks[$tag] = [ $callback, $flags ];
//
//			if (!in_array($tag, this.mStripList)) {
//				this.mStripList[] = $tag;
//			}
//
//			return $old;
//		}

	/**
	* Replace "<!--LINK-->" link placeholders with actual links, in the buffer
	* Placeholders created in Linker::link()
	*
	* @param String $text
	* @param int $options
	*/
	public void replaceLinkHolders(XomwParserBfr pbfr) {
		// this.mLinkHolders.replace(text);
		this.mLinkHolders.replace(pbfr);
	}
	private final    XomwParserBfr tmp_pbfr = new XomwParserBfr();
	public byte[] replaceLinkHolders(byte[] text) {
		// this.mLinkHolders.replace(text);
		this.mLinkHolders.replace(tmp_pbfr.Init(text));
		return tmp_pbfr.Trg().To_bry_and_clear();
	}

//		/**
//		* Replace "<!--LINK-->" link placeholders with plain text of links
//		* (not HTML-formatted).
//		*
//		* @param String $text
//		* @return String
//		*/
//		public function replaceLinkHoldersText($text) {
//			return this.mLinkHolders->replaceText($text);
//		}
//
//		/**
//		* Renders an image gallery from a text with one line per image.
//		* text labels may be given by using |-style alternative text. E.g.
//		*   Image:one.jpg|The number "1"
//		*   Image:tree.jpg|A tree
//		* given as text will return the HTML of a gallery with two images,
//		* labeled 'The number "1"' and
//		* 'A tree'.
//		*
//		* @param String $text
//		* @param array $params
//		* @return String HTML
//		*/
//		public function renderImageGallery($text, $params) {
//
//			$mode = false;
//			if (isset($params['mode'])) {
//				$mode = $params['mode'];
//			}
//
//			try {
//				$ig = ImageGalleryBase::factory($mode);
//			} catch (Exception $e) {
//				// If invalid type set, fallback to default.
//				$ig = ImageGalleryBase::factory(false);
//			}
//
//			$ig->setContextTitle(this.mTitle);
//			$ig->setShowBytes(false);
//			$ig->setShowFilename(false);
//			$ig->setParser($this);
//			$ig->setHideBadImages();
//			$ig->setAttributes(Sanitizer::validateTagAttributes($params, 'table'));
//
//			if (isset($params['showfilename'])) {
//				$ig->setShowFilename(true);
//			} else {
//				$ig->setShowFilename(false);
//			}
//			if (isset($params['caption'])) {
//				$caption = $params['caption'];
//				$caption = htmlspecialchars($caption);
//				$caption = this.replaceInternalLinks($caption);
//				$ig->setCaptionHtml($caption);
//			}
//			if (isset($params['perrow'])) {
//				$ig->setPerRow($params['perrow']);
//			}
//			if (isset($params['widths'])) {
//				$ig->setWidths($params['widths']);
//			}
//			if (isset($params['heights'])) {
//				$ig->setHeights($params['heights']);
//			}
//			$ig->setAdditionalOptions($params);
//
//			Hooks::run('BeforeParserrenderImageGallery', [ &$this, &$ig ]);
//
//			$lines = StringUtils::explode("\n", $text);
//			foreach ($lines as $line) {
//				# match lines like these:
//				# Image:someimage.jpg|This is some image
//				$matches = [];
//				preg_match("/^([^|]+)(\\|(.*))?$/", $line, $matches);
//				# Skip empty lines
//				if (count($matches) == 0) {
//					continue;
//				}
//
//				if (strpos($matches[0], '%') !== false) {
//					$matches[1] = rawurldecode($matches[1]);
//				}
//				$title = Title::newFromText($matches[1], NS_FILE);
//				if (is_null($title)) {
//					# Bogus title. Ignore these so we don't bomb out later.
//					continue;
//				}
//
//				# We need to get what handler the file uses, to figure out parameters.
//				# Note, a hook can overide the file name, and chose an entirely different
//				# file (which potentially could be of a different type and have different handler).
//				$options = [];
//				$descQuery = false;
//				Hooks::run('BeforeParserFetchFileAndTitle',
//					[ $this, $title, &$options, &$descQuery ]);
//				# Don't register it now, as TraditionalImageGallery does that later.
//				$file = this.fetchFileNoRegister($title, $options);
//				$handler = $file ? $file->getHandler() : false;
//
//				$paramMap = [
//					'img_alt' => 'gallery-@gplx.Internal protected-alt',
//					'img_link' => 'gallery-@gplx.Internal protected-link',
//				];
//				if ($handler) {
//					$paramMap = $paramMap + $handler->getParamMap();
//					// We don't want people to specify per-image widths.
//					// Additionally the width parameter would need special casing anyhow.
//					unset($paramMap['img_width']);
//				}
//
//				$mwArray = new MagicWordArray(array_keys($paramMap));
//
//				$label = '';
//				$alt = '';
//				$link = '';
//				$handlerOptions = [];
//				if (isset($matches[3])) {
//					// look for an |alt= definition while trying not to break existing
//					// captions with multiple pipes (|) in it, until a more sensible grammar
//					// is defined for images in galleries
//
//					// FIXME: Doing recursiveTagParse at this stage, and the trim before
//					// splitting on '|' is a bit odd, and different from makeImage.
//					$matches[3] = this.recursiveTagParse(trim($matches[3]));
//					// Protect LanguageConverter markup
//					$parameterMatches = StringUtils::delimiterExplode(
//						'-{', '}-', '|', $matches[3], true /* nested */
//					);
//
//					foreach ($parameterMatches as $parameterMatch) {
//						list($magicName, $match) = $mwArray->matchVariableStartToEnd($parameterMatch);
//						if ($magicName) {
//							$paramName = $paramMap[$magicName];
//
//							switch ($paramName) {
//							case 'gallery-@gplx.Internal protected-alt':
//								$alt = this.stripAltText($match, false);
//								break;
//							case 'gallery-@gplx.Internal protected-link':
//								$linkValue = strip_tags(this.replaceLinkHoldersText($match));
//								$chars = self::EXT_LINK_URL_CLASS;
//								$addr = self::EXT_LINK_ADDR;
//								$prots = this.mUrlProtocols;
//								// check to see if link matches an absolute url, if not then it must be a wiki link.
//								if (preg_match('/^-{R|(.*)}-$/', $linkValue)) {
//									// Result of LanguageConverter::markNoConversion
//									// invoked on an external link.
//									$linkValue = substr($linkValue, 4, -2);
//								}
//								if (preg_match("/^($prots)$addr$chars*$/u", $linkValue)) {
//									$link = $linkValue;
//								} else {
//									$localLinkTitle = Title::newFromText($linkValue);
//									if ($localLinkTitle !== null) {
//										$link = $localLinkTitle->getLinkURL();
//									}
//								}
//								break;
//							default:
//								// Must be a handler specific parameter.
//								if ($handler->validateParam($paramName, $match)) {
//									$handlerOptions[$paramName] = $match;
//								} else {
//									// Guess not, consider it as caption.
//									wfDebug("$parameterMatch failed parameter validation\n");
//									$label = '|' . $parameterMatch;
//								}
//							}
//
//						} else {
//							// Last pipe wins.
//							$label = '|' . $parameterMatch;
//						}
//					}
//					// Remove the pipe.
//					$label = substr($label, 1);
//				}
//
//				$ig->add($title, $label, $alt, $link, $handlerOptions);
//			}
//			$html = $ig->toHTML();
//			Hooks::run('AfterParserFetchFileAndTitle', [ $this, $ig, &$html ]);
//			return $html;
//		}
//
//		/**
//		* @param MediaHandler $handler
//		* @return array
//		*/
//		public function getImageParams($handler) {
//			if ($handler) {
//				$handlerClass = get_class($handler);
//			} else {
//				$handlerClass = '';
//			}
//			if (!isset(this.mImageParams[$handlerClass])) {
//				# Initialise static lists
//				static $internalParamNames = [
//					'horizAlign' => [ 'left', 'right', 'center', 'none' ],
//					'vertAlign' => [ 'baseline', 'sub', 'super', 'top', 'text-top', 'middle',
//						'bottom', 'text-bottom' ],
//					'frame' => [ 'thumbnail', 'manualthumb', 'framed', 'frameless',
//						'upright', 'border', 'link', 'alt', 'class' ],
//				];
//				static $internalParamMap;
//				if (!$internalParamMap) {
//					$internalParamMap = [];
//					foreach ($internalParamNames as $type => $names) {
//						foreach ($names as $name) {
//							$magicName = str_replace('-', '_', "img_$name");
//							$internalParamMap[$magicName] = [ $type, $name ];
//						}
//					}
//				}
//
//				# Add handler params
//				$paramMap = $internalParamMap;
//				if ($handler) {
//					$handlerParamMap = $handler->getParamMap();
//					foreach ($handlerParamMap as $magic => $paramName) {
//						$paramMap[$magic] = [ 'handler', $paramName ];
//					}
//				}
//				this.mImageParams[$handlerClass] = $paramMap;
//				this.mImageParamsMagicArray[$handlerClass] = new MagicWordArray(array_keys($paramMap));
//			}
//			return [ this.mImageParams[$handlerClass], this.mImageParamsMagicArray[$handlerClass] ];
//		}
//
//		/**
//		* Parse image options text and use it to make an image
//		*
//		* @param Title $title
//		* @param String $options
//		* @param LinkHolderArray|boolean $holders
//		* @return String HTML
//		*/
//		public function makeImage($title, $options, $holders = false) {
//			# Check if the options text is of the form "options|alt text"
//			# Options are:
//			#  * thumbnail  make a thumbnail with enlarge-icon and caption, alignment depends on lang
//			#  * left       no resizing, just left align. label is used for alt= only
//			#  * right      same, but right aligned
//			#  * none       same, but not aligned
//			#  * ___px      scale to ___ pixels width, no aligning. e.g. use in taxobox
//			#  * center     center the image
//			#  * frame      Keep original image size, no magnify-button.
//			#  * framed     Same as "frame"
//			#  * frameless  like 'thumb' but without a frame. Keeps user preferences for width
//			#  * upright    reduce width for upright images, rounded to full __0 px
//			#  * border     draw a 1px border around the image
//			#  * alt        Text for HTML alt attribute (defaults to empty)
//			#  * class      Set a class for img node
//			#  * link       Set the target of the image link. Can be external, interwiki, or local
//			# vertical-align values (no % or length right now):
//			#  * baseline
//			#  * sub
//			#  * super
//			#  * top
//			#  * text-top
//			#  * middle
//			#  * bottom
//			#  * text-bottom
//
//			# Protect LanguageConverter markup when splitting into parts
//			$parts = StringUtils::delimiterExplode(
//				'-{', '}-', '|', $options, true /* allow nesting */
//			);
//
//			# Give extensions a chance to select the file revision for us
//			$options = [];
//			$descQuery = false;
//			Hooks::run('BeforeParserFetchFileAndTitle',
//				[ $this, $title, &$options, &$descQuery ]);
//			# Fetch and register the file (file title may be different via hooks)
//			list($file, $title) = this.fetchFileAndTitle($title, $options);
//
//			# Get parameter map
//			$handler = $file ? $file->getHandler() : false;
//
//			list($paramMap, $mwArray) = this.getImageParams($handler);
//
//			if (!$file) {
//				this.addTrackingCategory('broken-file-category');
//			}
//
//			# Process the input parameters
//			$caption = '';
//			$params = [ 'frame' => [], 'handler' => [],
//				'horizAlign' => [], 'vertAlign' => [] ];
//			$seenformat = false;
//			foreach ($parts as $part) {
//				$part = trim($part);
//				list($magicName, $value) = $mwArray->matchVariableStartToEnd($part);
//				$validated = false;
//				if (isset($paramMap[$magicName])) {
//					list($type, $paramName) = $paramMap[$magicName];
//
//					# Special case; width and height come in one variable together
//					if ($type === 'handler' && $paramName === 'width') {
//						$parsedWidthParam = this.parseWidthParam($value);
//						if (isset($parsedWidthParam['width'])) {
//							$width = $parsedWidthParam['width'];
//							if ($handler->validateParam('width', $width)) {
//								$params[$type]['width'] = $width;
//								$validated = true;
//							}
//						}
//						if (isset($parsedWidthParam['height'])) {
//							$height = $parsedWidthParam['height'];
//							if ($handler->validateParam('height', $height)) {
//								$params[$type]['height'] = $height;
//								$validated = true;
//							}
//						}
//						# else no validation -- T15436
//					} else {
//						if ($type === 'handler') {
//							# Validate handler parameter
//							$validated = $handler->validateParam($paramName, $value);
//						} else {
//							# Validate @gplx.Internal protected parameters
//							switch ($paramName) {
//							case 'manualthumb':
//							case 'alt':
//							case 'class':
//								# @todo FIXME: Possibly check validity here for
//								# manualthumb? downstream behavior seems odd with
//								# missing manual thumbs.
//								$validated = true;
//								$value = this.stripAltText($value, $holders);
//								break;
//							case 'link':
//								$chars = self::EXT_LINK_URL_CLASS;
//								$addr = self::EXT_LINK_ADDR;
//								$prots = this.mUrlProtocols;
//								if ($value === '') {
//									$paramName = 'no-link';
//									$value = true;
//									$validated = true;
//								} elseif (preg_match("/^((?i)$prots)/", $value)) {
//									if (preg_match("/^((?i)$prots)$addr$chars*$/u", $value, $m)) {
//										$paramName = 'link-url';
//										this.mOutput->addExternalLink($value);
//										if (this.mOptions->getExternalLinkTarget()) {
//											$params[$type]['link-target'] = this.mOptions->getExternalLinkTarget();
//										}
//										$validated = true;
//									}
//								} else {
//									$linkTitle = Title::newFromText($value);
//									if ($linkTitle) {
//										$paramName = 'link-title';
//										$value = $linkTitle;
//										this.mOutput->addLink($linkTitle);
//										$validated = true;
//									}
//								}
//								break;
//							case 'frameless':
//							case 'framed':
//							case 'thumbnail':
//								// use first appearing option, discard others.
//								$validated = !$seenformat;
//								$seenformat = true;
//								break;
//							default:
//								# Most other things appear to be empty or numeric...
//								$validated = ($value === false || is_numeric(trim($value)));
//							}
//						}
//
//						if ($validated) {
//							$params[$type][$paramName] = $value;
//						}
//					}
//				}
//				if (!$validated) {
//					$caption = $part;
//				}
//			}
//
//			# Process alignment parameters
//			if ($params['horizAlign']) {
//				$params['frame']['align'] = key($params['horizAlign']);
//			}
//			if ($params['vertAlign']) {
//				$params['frame']['valign'] = key($params['vertAlign']);
//			}
//
//			$params['frame']['caption'] = $caption;
//
//			# Will the image be presented in a frame, with the caption below?
//			$imageIsFramed = isset($params['frame']['frame'])
//				|| isset($params['frame']['framed'])
//				|| isset($params['frame']['thumbnail'])
//				|| isset($params['frame']['manualthumb']);
//
//			# In the old days, [[Image:Foo|text...]] would set alt text.  Later it
//			# came to also set the caption, ordinary text after the image -- which
//			# makes no sense, because that just repeats the text multiple times in
//			# screen readers.  It *also* came to set the title attribute.
//			# Now that we have an alt attribute, we should not set the alt text to
//			# equal the caption: that's worse than useless, it just repeats the
//			# text.  This is the framed/thumbnail case.  If there's no caption, we
//			# use the unnamed parameter for alt text as well, just for the time be-
//			# ing, if the unnamed param is set and the alt param is not.
//			# For the future, we need to figure out if we want to tweak this more,
//			# e.g., introducing a title= parameter for the title; ignoring the un-
//			# named parameter entirely for images without a caption; adding an ex-
//			# plicit caption= parameter and preserving the old magic unnamed para-
//			# meter for BC; ...
//			if ($imageIsFramed) { # Framed image
//				if ($caption === '' && !isset($params['frame']['alt'])) {
//					# No caption or alt text, add the filename as the alt text so
//					# that screen readers at least get some description of the image
//					$params['frame']['alt'] = $title->getText();
//				}
//				# Do not set $params['frame']['title'] because tooltips don't make sense
//				# for framed images
//			} else { # Inline image
//				if (!isset($params['frame']['alt'])) {
//					# No alt text, use the "caption" for the alt text
//					if ($caption !== '') {
//						$params['frame']['alt'] = this.stripAltText($caption, $holders);
//					} else {
//						# No caption, fall back to using the filename for the
//						# alt text
//						$params['frame']['alt'] = $title->getText();
//					}
//				}
//				# Use the "caption" for the tooltip text
//				$params['frame']['title'] = this.stripAltText($caption, $holders);
//			}
//
//			Hooks::run('ParserMakeImageParams', [ $title, $file, &$params, $this ]);
//
//			# Linker does the rest
//			$time = isset($options['time']) ? $options['time'] : false;
//			$ret = Linker::makeImageLink($this, $title, $file, $params['frame'], $params['handler'],
//				$time, $descQuery, this.mOptions->getThumbSize());
//
//			# Give the handler a chance to modify the parser Object
//			if ($handler) {
//				$handler->parserTransformHook($this, $file);
//			}
//
//			return $ret;
//		}

	/**
	* @param String $caption
	* @param LinkHolderArray|boolean $holders
	* @return mixed|String
	*/
	public byte[] stripAltText(byte[] caption, XomwLinkHolderArray holders) {
		// Strip bad stuff out of the title (tooltip).  We can't just use
		// replaceLinkHoldersText() here, because if this function is called
		// from replaceInternalLinks2(), mLinkHolders won't be up-to-date.
		byte[] tooltip;
		if (holders != null) {
			tooltip = holders.replace(tmp_pbfr, caption);
		} else {
			tooltip = this.replaceLinkHolders(caption);
		}

		// make sure there are no placeholders in thumbnail attributes
		// that are later expanded to html- so expand them now and
		// remove the tags
		tooltip = this.mStripState.unstripBoth(tooltip);
		tooltip = sanitizer.stripAllTags(tooltip);

		return tooltip;
	}

//		/**
//		* Set a flag in the output Object indicating that the content is dynamic and
//		* shouldn't be cached.
//		* @deprecated since 1.28; use getOutput()->updateCacheExpiry()
//		*/
//		public function disableCache() {
//			wfDebug("Parser output marked as uncacheable.\n");
//			if (!this.mOutput) {
//				throw new MWException(__METHOD__ .
//					" can only be called when actually parsing something");
//			}
//			this.mOutput->updateCacheExpiry(0); // new style, for consistency
//		}
//
//		/**
//		* Callback from the Sanitizer for expanding items found in HTML attribute
//		* values, so they can be safely tested and escaped.
//		*
//		* @param String $text
//		* @param boolean|PPFrame $frame
//		* @return String
//		*/
//		public function attributeStripCallback(&$text, $frame = false) {
//			$text = this.replaceVariables($text, $frame);
//			$text = this.mStripState->unstripBoth($text);
//			return $text;
//		}
//
//		/**
//		* Accessor
//		*
//		* @return array
//		*/
//		public function getTags() {
//			return array_merge(
//				array_keys(this.mTransparentTagHooks),
//				array_keys(this.mTagHooks),
//				array_keys(this.mFunctionTagHooks)
//			);
//		}
//
//		/**
//		* Replace transparent tags in $text with the values given by the callbacks.
//		*
//		* Transparent tag hooks are like regular XML-style tag hooks, except they
//		* operate late in the transformation sequence, on HTML instead of wikitext.
//		*
//		* @param String $text
//		*
//		* @return String
//		*/
//		public function replaceTransparentTags($text) {
//			$matches = [];
//			$elements = array_keys(this.mTransparentTagHooks);
//			$text = self::extractTagsAndParams($elements, $text, $matches);
//			$replacements = [];
//
//			foreach ($matches as $marker => $data) {
//				list($element, $content, $params, $tag) = $data;
//				$tagName = strtolower($element);
//				if (isset(this.mTransparentTagHooks[$tagName])) {
//					$output = call_user_func_array(
//						this.mTransparentTagHooks[$tagName],
//						[ $content, $params, $this ]
//					);
//				} else {
//					$output = $tag;
//				}
//				$replacements[$marker] = $output;
//			}
//			return strtr($text, $replacements);
//		}
//
//		/**
//		* Break wikitext input into sections, and either pull or replace
//		* some particular section's text.
//		*
//		* External callers should use the getSection and replaceSection methods.
//		*
//		* @param String $text Page wikitext
//		* @param String|int $sectionId A section identifier String of the form:
//		*   "<flag1> - <flag2> - ... - <section number>"
//		*
//		* Currently the only recognised flag is "T", which means the target section number
//		* was derived during a template inclusion parse, in other words this is a template
//		* section edit link. If no flags are given, it was an ordinary section edit link.
//		* This flag is required to avoid a section numbering mismatch when a section is
//		* enclosed by "<includeonly>" (T8563).
//		*
//		* The section number 0 pulls the text before the first heading; other numbers will
//		* pull the given section along with its lower-level subsections. If the section is
//		* not found, $mode=get will return $newtext, and $mode=replace will return $text.
//		*
//		* Section 0 is always considered to exist, even if it only contains the empty
//		* String. If $text is the empty String and section 0 is replaced, $newText is
//		* returned.
//		*
//		* @param String $mode One of "get" or "replace"
//		* @param String $newText Replacement text for section data.
//		* @return String For "get", the extracted section text.
//		*   for "replace", the whole page with the section replaced.
//		*/
//		private function extractSections($text, $sectionId, $mode, $newText = '') {
//			global $wgTitle; # not generally used but removes an ugly failure mode
//
//			$magicScopeVariable = this.synchronized();
//			this.startParse($wgTitle, new ParserOptions, self::OT_PLAIN, true);
//			$outText = '';
//			$frame = this.getPreprocessor()->newFrame();
//
//			# Process section extraction flags
//			$flags = 0;
//			$sectionParts = explode('-', $sectionId);
//			$sectionIndex = array_pop($sectionParts);
//			foreach ($sectionParts as $part) {
//				if ($part === 'T') {
//					$flags |= self::PTD_FOR_INCLUSION;
//				}
//			}
//
//			# Check for empty input
//			if (strval($text) === '') {
//				# Only sections 0 and T-0 exist in an empty document
//				if ($sectionIndex == 0) {
//					if ($mode === 'get') {
//						return '';
//					} else {
//						return $newText;
//					}
//				} else {
//					if ($mode === 'get') {
//						return $newText;
//					} else {
//						return $text;
//					}
//				}
//			}
//
//			# Preprocess the text
//			$root = this.preprocessToDom($text, $flags);
//
//			# <h> nodes indicate section breaks
//			# They can only occur at the top level, so we can find them by iterating the root's children
//			$node = $root->getFirstChild();
//
//			# Find the target section
//			if ($sectionIndex == 0) {
//				# Section zero doesn't nest, level=big
//				$targetLevel = 1000;
//			} else {
//				while ($node) {
//					if ($node->getName() === 'h') {
//						$bits = $node->splitHeading();
//						if ($bits['i'] == $sectionIndex) {
//							$targetLevel = $bits['level'];
//							break;
//						}
//					}
//					if ($mode === 'replace') {
//						$outText .= $frame->expand($node, PPFrame::RECOVER_ORIG);
//					}
//					$node = $node->getNextSibling();
//				}
//			}
//
//			if (!$node) {
//				# Not found
//				if ($mode === 'get') {
//					return $newText;
//				} else {
//					return $text;
//				}
//			}
//
//			# Find the end of the section, including nested sections
//			do {
//				if ($node->getName() === 'h') {
//					$bits = $node->splitHeading();
//					$curLevel = $bits['level'];
//					if ($bits['i'] != $sectionIndex && $curLevel <= $targetLevel) {
//						break;
//					}
//				}
//				if ($mode === 'get') {
//					$outText .= $frame->expand($node, PPFrame::RECOVER_ORIG);
//				}
//				$node = $node->getNextSibling();
//			} while ($node);
//
//			# Write out the remainder (in replace mode only)
//			if ($mode === 'replace') {
//				# Output the replacement text
//				# Add two newlines on -- trailing whitespace in $newText is conventionally
//				# stripped by the editor, so we need both newlines to restore the paragraph gap
//				# Only add trailing whitespace if there is newText
//				if ($newText != "") {
//					$outText .= $newText . "\n\n";
//				}
//
//				while ($node) {
//					$outText .= $frame->expand($node, PPFrame::RECOVER_ORIG);
//					$node = $node->getNextSibling();
//				}
//			}
//
//			if (is_string($outText)) {
//				# Re-insert stripped tags
//				$outText = rtrim(this.mStripState->unstripBoth($outText));
//			}
//
//			return $outText;
//		}
//
//		/**
//		* This function returns the text of a section, specified by a number ($section).
//		* A section is text under a heading like == Heading == or \<h1\>Heading\</h1\>, or
//		* the first section before any such heading (section 0).
//		*
//		* If a section contains subsections, these are also returned.
//		*
//		* @param String $text Text to look in
//		* @param String|int $sectionId Section identifier as a number or String
//		* (e.g. 0, 1 or 'T-1').
//		* @param String $defaultText Default to return if section is not found
//		*
//		* @return String Text of the requested section
//		*/
//		public function getSection($text, $sectionId, $defaultText = '') {
//			return this.extractSections($text, $sectionId, 'get', $defaultText);
//		}
//
//		/**
//		* This function returns $oldtext after the content of the section
//		* specified by $section has been replaced with $text. If the target
//		* section does not exist, $oldtext is returned unchanged.
//		*
//		* @param String $oldText Former text of the article
//		* @param String|int $sectionId Section identifier as a number or String
//		* (e.g. 0, 1 or 'T-1').
//		* @param String $newText Replacing text
//		*
//		* @return String Modified text
//		*/
//		public function replaceSection($oldText, $sectionId, $newText) {
//			return this.extractSections($oldText, $sectionId, 'replace', $newText);
//		}
//
//		/**
//		* Get the ID of the revision we are parsing
//		*
//		* @return int|null
//		*/
//		public function getRevisionId() {
//			return this.mRevisionId;
//		}
//
//		/**
//		* Get the revision Object for this.mRevisionId
//		*
//		* @return Revision|null Either a Revision Object or null
//		* @since 1.23 (public since 1.23)
//		*/
//		public function getRevisionObject() {
//			if (!is_null(this.mRevisionObject)) {
//				return this.mRevisionObject;
//			}
//			if (is_null(this.mRevisionId)) {
//				return null;
//			}
//
//			$rev = call_user_func(
//				this.mOptions->getCurrentRevisionCallback(), this.getTitle(), $this
//			);
//
//			# If the parse is for a new revision, then the callback should have
//			# already been set to force the Object and should match mRevisionId.
//			# If not, try to fetch by mRevisionId for sanity.
//			if ($rev && $rev->getId() != this.mRevisionId) {
//				$rev = Revision::newFromId(this.mRevisionId);
//			}
//
//			this.mRevisionObject = $rev;
//
//			return this.mRevisionObject;
//		}
//
//		/**
//		* Get the timestamp associated with the current revision, adjusted for
//		* the default server-local timestamp
//		* @return String
//		*/
//		public function getRevisionTimestamp() {
//			if (is_null(this.mRevisionTimestamp)) {
//				global $wgContLang;
//
//				$revObject = this.getRevisionObject();
//				$timestamp = $revObject ? $revObject->getTimestamp() : wfTimestampNow();
//
//				# The cryptic '' timezone parameter tells to use the site-default
//				# timezone offset instead of the user settings.
//				# Since this value will be saved into the parser cache, served
//				# to other users, and potentially even used inside links and such,
//				# it needs to be consistent for all visitors.
//				this.mRevisionTimestamp = $wgContLang->userAdjust($timestamp, '');
//
//			}
//			return this.mRevisionTimestamp;
//		}
//
//		/**
//		* Get the name of the user that edited the last revision
//		*
//		* @return String User name
//		*/
//		public function getRevisionUser() {
//			if (is_null(this.mRevisionUser)) {
//				$revObject = this.getRevisionObject();
//
//				# if this template is subst: the revision id will be blank,
//				# so just use the current user's name
//				if ($revObject) {
//					this.mRevisionUser = $revObject->getUserText();
//				} elseif (this.ot['wiki'] || this.mOptions->getIsPreview()) {
//					this.mRevisionUser = this.getUser()->getName();
//				}
//			}
//			return this.mRevisionUser;
//		}
//
//		/**
//		* Get the size of the revision
//		*
//		* @return int|null Revision size
//		*/
//		public function getRevisionSize() {
//			if (is_null(this.mRevisionSize)) {
//				$revObject = this.getRevisionObject();
//
//				# if this variable is subst: the revision id will be blank,
//				# so just use the parser input size, because the own substituation
//				# will change the size.
//				if ($revObject) {
//					this.mRevisionSize = $revObject->getSize();
//				} else {
//					this.mRevisionSize = this.mInputSize;
//				}
//			}
//			return this.mRevisionSize;
//		}
//
//		/**
//		* Mutator for $mDefaultSort
//		*
//		* @param String $sort New value
//		*/
//		public function setDefaultSort($sort) {
//			this.mDefaultSort = $sort;
//			this.mOutput->setProperty('defaultsort', $sort);
//		}
//
//		/**
//		* Accessor for $mDefaultSort
//		* Will use the empty String if none is set.
//		*
//		* This value is treated as a prefix, so the
//		* empty String is equivalent to sorting by
//		* page name.
//		*
//		* @return String
//		*/
//		public function getDefaultSort() {
//			if (this.mDefaultSort !== false) {
//				return this.mDefaultSort;
//			} else {
//				return '';
//			}
//		}
//
//		/**
//		* Accessor for $mDefaultSort
//		* Unlike getDefaultSort(), will return false if none is set
//		*
//		* @return String|boolean
//		*/
//		public function getCustomDefaultSort() {
//			return this.mDefaultSort;
//		}
//
//		/**
//		* Try to guess the section anchor name based on a wikitext fragment
//		* presumably extracted from a heading, for example "Header" from
//		* "== Header ==".
//		*
//		* @param String $text
//		*
//		* @return String
//		*/
//		public function guessSectionNameFromWikiText($text) {
//			# Strip out wikitext links(they break the anchor)
//			$text = this.stripSectionName($text);
//			$text = Sanitizer::normalizeSectionNameWhitespace($text);
//			return '#' . Sanitizer::escapeId($text, 'noninitial');
//		}
//
//		/**
//		* Same as guessSectionNameFromWikiText(), but produces legacy anchors
//		* instead.  For use in redirects, since IE6 interprets Redirect: headers
//		* as something other than UTF-8 (apparently?), resulting in breakage.
//		*
//		* @param String $text The section name
//		* @return String An anchor
//		*/
//		public function guessLegacySectionNameFromWikiText($text) {
//			# Strip out wikitext links(they break the anchor)
//			$text = this.stripSectionName($text);
//			$text = Sanitizer::normalizeSectionNameWhitespace($text);
//			return '#' . Sanitizer::escapeId($text, [ 'noninitial', 'legacy' ]);
//		}
//
//		/**
//		* Strips a text String of wikitext for use in a section anchor
//		*
//		* Accepts a text String and then removes all wikitext from the
//		* String and leaves only the resultant text (i.e. the result of
//		* [[User:WikiSysop|Sysop]] would be "Sysop" and the result of
//		* [[User:WikiSysop]] would be "User:WikiSysop") - this is intended
//		* to create valid section anchors by mimicing the output of the
//		* parser when headings are parsed.
//		*
//		* @param String $text Text String to be stripped of wikitext
//		* for use in a Section anchor
//		* @return String Filtered text String
//		*/
//		public function stripSectionName($text) {
//			# Strip @gplx.Internal protected link markup
//			$text = preg_replace('/\[\[:?([^[|]+)\|([^[]+)\]\]/', '$2', $text);
//			$text = preg_replace('/\[\[:?([^[]+)\|?\]\]/', '$1', $text);
//
//			# Strip external link markup
//			# @todo FIXME: Not tolerant to blank link text
//			# I.E. [https://www.mediawiki.org] will render as [1] or something depending
//			# on how many empty links there are on the page - need to figure that out.
//			$text = preg_replace('/\[(?i:' . this.mUrlProtocols . ')([^ ]+?) ([^[]+)\]/', '$2', $text);
//
//			# Parse wikitext quotes (italics & bold)
//			$text = this.doQuotes($text);
//
//			# Strip HTML tags
//			$text = StringUtils::delimiterReplace('<', '>', '', $text);
//			return $text;
//		}
//
//		/**
//		* strip/replaceVariables/unstrip for preprocessor regression testing
//		*
//		* @param String $text
//		* @param Title $title
//		* @param ParserOptions $options
//		* @param int $outputType
//		*
//		* @return String
//		*/
//		public function testSrvus($text, Title $title, ParserOptions $options,
//			$outputType = self::OT_HTML
//		) {
//			$magicScopeVariable = this.synchronized();
//			this.startParse($title, $options, $outputType, true);
//
//			$text = this.replaceVariables($text);
//			$text = this.mStripState->unstripBoth($text);
//			$text = Sanitizer::removeHTMLtags($text);
//			return $text;
//		}
//
//		/**
//		* @param String $text
//		* @param Title $title
//		* @param ParserOptions $options
//		* @return String
//		*/
//		public function testPst($text, Title $title, ParserOptions $options) {
//			return this.preSaveTransform($text, $title, $options->getUser(), $options);
//		}
//
//		/**
//		* @param String $text
//		* @param Title $title
//		* @param ParserOptions $options
//		* @return String
//		*/
//		public function testPreprocess($text, Title $title, ParserOptions $options) {
//			return this.testSrvus($text, $title, $options, self::OT_PREPROCESS);
//		}
//
//		/**
//		* Call a callback function on all regions of the given text that are not
//		* inside strip markers, and replace those regions with the return value
//		* of the callback. For example, with input:
//		*
//		*  aaa<MARKER>bbb
//		*
//		* This will call the callback function twice, with 'aaa' and 'bbb'. Those
//		* two strings will be replaced with the value returned by the callback in
//		* each case.
//		*
//		* @param String $s
//		* @param callable $callback
//		*
//		* @return String
//		*/
//		public function markerSkipCallback($s, $callback) {
//			$i = 0;
//			$out = '';
//			while ($i < strlen($s)) {
//				$markerStart = strpos($s, self::MARKER_PREFIX, $i);
//				if ($markerStart === false) {
//					$out .= call_user_func($callback, substr($s, $i));
//					break;
//				} else {
//					$out .= call_user_func($callback, substr($s, $i, $markerStart - $i));
//					$markerEnd = strpos($s, self::MARKER_SUFFIX, $markerStart);
//					if ($markerEnd === false) {
//						$out .= substr($s, $markerStart);
//						break;
//					} else {
//						$markerEnd += strlen(self::MARKER_SUFFIX);
//						$out .= substr($s, $markerStart, $markerEnd - $markerStart);
//						$i = $markerEnd;
//					}
//				}
//			}
//			return $out;
//		}
//
//		/**
//		* Remove any strip markers found in the given text.
//		*
//		* @param String $text Input String
//		* @return String
//		*/
//		public function killMarkers($text) {
//			return this.mStripState->killMarkers($text);
//		}
//
//		/**
//		* Save the parser state required to convert the given half-parsed text to
//		* HTML. "Half-parsed" in this context means the output of
//		* recursiveTagParse() or internalParse(). This output has strip markers
//		* from replaceVariables (extensionSubstitution() etc.), and link
//		* placeholders from replaceLinkHolders().
//		*
//		* Returns an array which can be serialized and stored persistently. This
//		* array can later be loaded into another parser instance with
//		* unserializeHalfParsedText(). The text can then be safely incorporated into
//		* the return value of a parser hook.
//		*
//		* @param String $text
//		*
//		* @return array
//		*/
//		public function serializeHalfParsedText($text) {
//			$data = [
//				'text' => $text,
//				'version' => self::HALF_PARSED_VERSION,
//				'stripState' => this.mStripState->getSubState($text),
//				'linkHolders' => this.mLinkHolders->getSubArray($text)
//			];
//			return $data;
//		}
//
//		/**
//		* Load the parser state given in the $data array, which is assumed to
//		* have been generated by serializeHalfParsedText(). The text contents is
//		* extracted from the array, and its markers are transformed into markers
//		* appropriate for the current Parser instance. This transformed text is
//		* returned, and can be safely included in the return value of a parser
//		* hook.
//		*
//		* If the $data array has been stored persistently, the caller should first
//		* check whether it is still valid, by calling isValidHalfParsedText().
//		*
//		* @param array $data Serialized data
//		* @throws MWException
//		* @return String
//		*/
//		public function unserializeHalfParsedText($data) {
//			if (!isset($data['version']) || $data['version'] != self::HALF_PARSED_VERSION) {
//				throw new MWException(__METHOD__ . ': invalid version');
//			}
//
//			# First, extract the strip state.
//			$texts = [ $data['text'] ];
//			$texts = this.mStripState->merge($data['stripState'], $texts);
//
//			# Now renumber links
//			$texts = this.mLinkHolders->mergeForeign($data['linkHolders'], $texts);
//
//			# Should be good to go.
//			return $texts[0];
//		}
//
//		/**
//		* Returns true if the given array, presumed to be generated by
//		* serializeHalfParsedText(), is compatible with the current version of the
//		* parser.
//		*
//		* @param array $data
//		*
//		* @return boolean
//		*/
//		public function isValidHalfParsedText($data) {
//			return isset($data['version']) && $data['version'] == self::HALF_PARSED_VERSION;
//		}
//
//		/**
//		* Parsed a width param of imagelink like 300px or 200x300px
//		*
//		* @param String $value
//		*
//		* @return array
//		* @since 1.20
//		*/
//		public function parseWidthParam($value) {
//			$parsedWidthParam = [];
//			if ($value === '') {
//				return $parsedWidthParam;
//			}
//			$m = [];
//			# (T15500) In both cases (width/height and width only),
//			# permit trailing "px" for backward compatibility.
//			if (preg_match('/^([0-9]*)x([0-9]*)\s*(?:px)?\s*$/', $value, $m)) {
//				$width = intval($m[1]);
//				$height = intval($m[2]);
//				$parsedWidthParam['width'] = $width;
//				$parsedWidthParam['height'] = $height;
//			} elseif (preg_match('/^[0-9]*\s*(?:px)?\s*$/', $value)) {
//				$width = intval($value);
//				$parsedWidthParam['width'] = $width;
//			}
//			return $parsedWidthParam;
//		}
//
//		/**
//		* Lock the current instance of the parser.
//		*
//		* This is meant to stop someone from calling the parser
//		* recursively and messing up all the strip state.
//		*
//		* @throws MWException If parser is in a parse
//		* @return ScopedCallback The synchronized will be released once the return value goes out of scope.
//		*/
//		protected function synchronized() {
//			if (this.mInParse) {
//				throw new MWException("Parser state cleared while parsing. "
//					. "Did you call Parser::parse recursively?");
//			}
//			this.mInParse = true;
//
//			$recursiveCheck = new ScopedCallback(function() {
//				this.mInParse = false;
//			});
//
//			return $recursiveCheck;
//		}
//
//		/**
//		* Strip outer <p></p> tag from the HTML source of a single paragraph.
//		*
//		* Returns original HTML if the <p/> tag has any attributes, if there's no wrapping <p/> tag,
//		* or if there is more than one <p/> tag in the input HTML.
//		*
//		* @param String $html
//		* @return String
//		* @since 1.24
//		*/
//		public static function stripOuterParagraph($html) {
//			$m = [];
//			if (preg_match('/^<p>(.*)\n?<\/p>\n?$/sU', $html, $m)) {
//				if (strpos($m[1], '</p>') === false) {
//					$html = $m[1];
//				}
//			}
//
//			return $html;
//		}
//
//		/**
//		* Return this parser if it is not doing anything, otherwise
//		* get a fresh parser. You can use this method by doing
//		* $myParser = $wgParser->getFreshParser(), or more simply
//		* $wgParser->getFreshParser()->parse(...);
//		* if you're unsure if $wgParser is safe to use.
//		*
//		* @since 1.24
//		* @return Parser A parser Object that is not parsing anything
//		*/
//		public function getFreshParser() {
//			global $wgParserConf;
//			if (this.mInParse) {
//				return new $wgParserConf['class']($wgParserConf);
//			} else {
//				return $this;
//			}
//		}
//
//		/**
//		* Set's up the PHP implementation of OOUI for use in this request
//		* and instructs OutputPage to enable OOUI for itself.
//		*
//		* @since 1.26
//		*/
//		public function enableOOUI() {
//			OutputPage::setupOOUI();
//			this.mOutput->setEnableOOUI(true);
//		}
	public static final    String MARKER_PREFIX_STR = "\u007f'\"`UNIQ-";
	public static final    byte[] 
	  MARKER_PREFIX  = Bry_.new_a7(MARKER_PREFIX_STR)
	, MARKER_SUFFIX  = Bry_.new_a7("-QINU`\"'\u007f")
	;
	private static final    byte[]
	  STRIP_ITEM = Bry_.new_a7("-item-")
	, Bry__noparse = Bry_.new_a7("NOPARSE")
	;
	private static final    byte[] Bry__marker__noparse = Bry_.Add(MARKER_PREFIX, Bry__noparse);
	public static Btrie_slim_mgr Protocols__dflt() {
		Btrie_slim_mgr rv = Btrie_slim_mgr.ci_a7();
		Gfo_protocol_itm[] ary = Gfo_protocol_itm.Ary();
		for (Gfo_protocol_itm itm : ary) {
			byte[] key = itm.Text_bry();	// EX: "https://"
			rv.Add_obj(key, key);
		}
		byte[] bry__relative = Bry_.new_a7("//");
		rv.Add_obj(bry__relative, bry__relative);	// REF.MW: "$this->mUrlProtocols = wfUrlProtocols();"; "wfUrlProtocols( $includeProtocolRelative = true )"
		return rv;
	}
}
