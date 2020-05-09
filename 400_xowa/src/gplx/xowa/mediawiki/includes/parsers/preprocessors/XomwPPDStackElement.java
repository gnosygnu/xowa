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
package gplx.xowa.mediawiki.includes.parsers.preprocessors; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
// MW.FILE:Preprocessor
/**
* @ingroup Parser
*/
public class XomwPPDStackElement {
	/**
	* @var String Opening character (\n for heading)
	*/
	public String open;

	/**
	* @var String Matching closing character
	*/
	public String close;

	/**
	* @var int Number of opening characters found (number of "=" for heading)
	*/
	public int count;

	/**
	* @var PPDPart[] Array of PPDPart objects describing pipe-separated parts.
	*/
	public XophpArray parts;

	/**
	* @var boolean True if the open char appeared at the start of the input line.
	*  Not set for headings.
	*/
	public boolean lineStart;

	public String partClass = "PPDPart";

	public int start_pos;
	private final    XomwPPDPart part_factory;
	public XomwPPDStackElement(XomwPPDPart part_factory, String open, String close, int count, int start_pos, boolean lineStart) {
		this.parts = new XophpArray();

		this.open = open;
		this.close = close;
		this.count = count;

		this.part_factory = part_factory;
		this.start_pos = start_pos;
		this.lineStart = lineStart;
		parts.Add(part_factory.Make_new(""));
	}

	public Xomw_prepro_accum getAccum() {
		return (Xomw_prepro_accum)Get_at(XophpArray.count(this.parts) - 1).Accum();
	}

	public void addPart(String s) {
		this.parts.Add(Make_part(s));
	}
	@gplx.Virtual protected XomwPPDPart Make_part(String s) {
		return part_factory.Make_new(s);
	}

	public XomwPPDPart getCurrentPart() {
		return (XomwPPDPart)Get_at(XophpArray.count(this.parts) - 1);
	}

	/**
	* @return array
	*/
	public XomwPPDStackElementFlags getFlags() {
		int partCount = XophpArray.count(this.parts);
		boolean findPipe = String_.EqNot(this.open, "\n") && String_.EqNot(this.open, "[");
		return new XomwPPDStackElementFlags
			( findPipe
			, findPipe && partCount > 1 && !XophpObject_.isset(Get_at(partCount - 1).eqpos)
			, String_.Eq(this.open, "\n")
			);
	}

	/**
	* Get the output String that would result if the close is not found.
	*
	* @param boolean|int $openingCount
	* @return String
	*/
	@gplx.Virtual public Object breakSyntax(int openingCount) {
		Char_bfr bfr = new Char_bfr(16);
		if (String_.Eq(this.open, "\n")) {
			XomwPPDPart_DOM part_0 = (XomwPPDPart_DOM)Get_at(0);
			bfr.Add_bry(part_0.To_bry());
		}
		else {
			if (openingCount == -1) {
				openingCount = this.count;
			}
			bfr.Add_str(XophpString_.str_repeat(this.open, openingCount));
			boolean first = true;
			int parts_len = parts.Len();
			for (int i = 0; i < parts_len; i++) {
				XomwPPDPart_DOM part = (XomwPPDPart_DOM)Get_at(i);
				if (first) {
					first = false;
				} else {
					bfr.Add_char('|');
				}
				bfr.Add_bry(part.To_bry());
			}
		}
		return bfr.To_str_and_clear();
	}
	public void Parts__renew() {
		parts.Clear();
		this.addPart("");
	}
	private XomwPPDPart Get_at(int i) {
		return (XomwPPDPart)this.parts.Get_at(i);
	}
}
///**
// * An expansion frame, used as a context to expand the result of preprocessToObj()
// * @ingroup Parser
// */
//// @codingStandardsIgnoreStart Squiz.Classes.ValidClassName.NotCamelCaps
//class PPFrame_DOM implements PPFrame {
//	// @codingStandardsIgnoreEnd
//
//	/**
//	 * @var Preprocessor
//	 */
//	public $preprocessor;
//
//	/**
//	 * @var Parser
//	 */
//	public $parser;
//
//	/**
//	 * @var Title
//	 */
//	public $title;
//	public $titleCache;
//
//	/**
//	 * Hashtable listing templates which are disallowed for expansion in this frame,
//	 * having been encountered previously in parent frames.
//	 */
//	public $loopCheckHash;
//
//	/**
//	 * Recursion depth of this frame, top = 0
//	 * Note that this is NOT the same as expansion depth in expand()
//	 */
//	public $depth;
//
//	private $volatile = false;
//	private $ttl = null;
//
//	/**
//	 * @var array
//	 */
//	protected $childExpansionCache;
//
//	/**
//	 * Construct a new preprocessor frame.
//	 * @param Preprocessor $preprocessor The parent preprocessor
//	 */
//	public function __construct($preprocessor) {
//		this.preprocessor = $preprocessor;
//		this.parser = $preprocessor.parser;
//		this.title = this.parser.mTitle;
//		this.titleCache = [ this.title ? this.title.getPrefixedDBkey() : false ];
//		this.loopCheckHash = [];
//		this.depth = 0;
//		this.childExpansionCache = [];
//	}
//
//	/**
//	 * Create a new child frame
//	 * $args is optionally a multi-root PPNode or array containing the template arguments
//	 *
//	 * @param boolean|array $args
//	 * @param Title|boolean $title
//	 * @param int $indexOffset
//	 * @return PPTemplateFrame_DOM
//	 */
//	public function newChild($args = false, $title = false, $indexOffset = 0) {
//		$namedArgs = [];
//		$numberedArgs = [];
//		if ($title === false) {
//			$title = this.title;
//		}
//		if ($args !== false) {
//			$xpath = false;
//			if ($args instanceof PPNode) {
//				$args = $args.node;
//			}
//			for each ($args as $arg) {
//				if ($arg instanceof PPNode) {
//					$arg = $arg.node;
//				}
//				if (!$xpath || $xpath.document !== $arg.ownerDocument) {
//					$xpath = new DOMXPath($arg.ownerDocument);
//				}
//
//				$nameNodes = $xpath.query('name', $arg);
//				$value = $xpath.query('value', $arg);
//				if ($nameNodes.item(0).hasAttributes()) {
//					// Numbered parameter
//					$index = $nameNodes.item(0).attributes.getNamedItem('index').textContent;
//					$index = $index - $indexOffset;
//					if (isset($namedArgs[$index]) || isset($numberedArgs[$index])) {
//						this.parser.getOutput().addWarning(wfMessage('duplicate-args-warning',
//							wfEscapeWikiText(this.title),
//							wfEscapeWikiText($title),
//							wfEscapeWikiText($index)).text());
//						this.parser.addTrackingCategory('duplicate-args-category');
//					}
//					$numberedArgs[$index] = $value.item(0);
//					unset($namedArgs[$index]);
//				} else {
//					// Named parameter
//					$name = trim(this.expand($nameNodes.item(0), PPFrame::STRIP_COMMENTS));
//					if (isset($namedArgs[$name]) || isset($numberedArgs[$name])) {
//						this.parser.getOutput().addWarning(wfMessage('duplicate-args-warning',
//							wfEscapeWikiText(this.title),
//							wfEscapeWikiText($title),
//							wfEscapeWikiText($name)).text());
//						this.parser.addTrackingCategory('duplicate-args-category');
//					}
//					$namedArgs[$name] = $value.item(0);
//					unset($numberedArgs[$name]);
//				}
//			}
//		}
//		return new PPTemplateFrame_DOM(this.preprocessor, $this, $numberedArgs, $namedArgs, $title);
//	}
//
//	/**
//	 * @throws MWException
//	 * @param String|int $key
//	 * @param String|PPNode_DOM|DOMDocument $root
//	 * @param int $flags
//	 * @return String
//	 */
//	public function cachedExpand($key, $root, $flags = 0) {
//		// we don't have a parent, so we don't have a cache
//		return this.expand($root, $flags);
//	}
//
//	/**
//	 * @throws MWException
//	 * @param String|PPNode_DOM|DOMDocument $root
//	 * @param int $flags
//	 * @return String
//	 */
//	public function expand($root, $flags = 0) {
//		static $expansionDepth = 0;
//		if (is_string($root)) {
//			return $root;
//		}
//
//		if (++this.parser.mPPNodeCount > this.parser.mOptions.getMaxPPNodeCount()) {
//			this.parser.limitationWarn('node-count-exceeded',
//				this.parser.mPPNodeCount,
//				this.parser.mOptions.getMaxPPNodeCount()
//			);
//			return '<span class="error">Node-count limit exceeded</span>';
//		}
//
//		if ($expansionDepth > this.parser.mOptions.getMaxPPExpandDepth()) {
//			this.parser.limitationWarn('expansion-depth-exceeded',
//				$expansionDepth,
//				this.parser.mOptions.getMaxPPExpandDepth()
//			);
//			return '<span class="error">Expansion depth limit exceeded</span>';
//		}
//		++$expansionDepth;
//		if ($expansionDepth > this.parser.mHighestExpansionDepth) {
//			this.parser.mHighestExpansionDepth = $expansionDepth;
//		}
//
//		if ($root instanceof PPNode_DOM) {
//			$root = $root.node;
//		}
//		if ($root instanceof DOMDocument) {
//			$root = $root.documentElement;
//		}
//
//		$outStack = [ '', '' ];
//		$iteratorStack = [ false, $root ];
//		$indexStack = [ 0, 0 ];
//
//		while (count($iteratorStack) > 1) {
//			$level = count($outStack) - 1;
//			$iteratorNode =& $iteratorStack[$level];
//			$out =& $outStack[$level];
//			$index =& $indexStack[$level];
//
//			if ($iteratorNode instanceof PPNode_DOM) {
//				$iteratorNode = $iteratorNode.node;
//			}
//
//			if (is_array($iteratorNode)) {
//				if ($index >= count($iteratorNode)) {
//					// All done with this iterator
//					$iteratorStack[$level] = false;
//					$contextNode = false;
//				} else {
//					$contextNode = $iteratorNode[$index];
//					$index++;
//				}
//			} elseif ($iteratorNode instanceof DOMNodeList) {
//				if ($index >= $iteratorNode.length) {
//					// All done with this iterator
//					$iteratorStack[$level] = false;
//					$contextNode = false;
//				} else {
//					$contextNode = $iteratorNode.item($index);
//					$index++;
//				}
//			} else {
//				// Copy to $contextNode and then delete from iterator stack,
//				// because this is not an iterator but we do have to execute it once
//				$contextNode = $iteratorStack[$level];
//				$iteratorStack[$level] = false;
//			}
//
//			if ($contextNode instanceof PPNode_DOM) {
//				$contextNode = $contextNode.node;
//			}
//
//			$newIterator = false;
//
//			if ($contextNode === false) {
//				// nothing to do
//			} elseif (is_string($contextNode)) {
//				$out .= $contextNode;
//			} elseif (is_array($contextNode) || $contextNode instanceof DOMNodeList) {
//				$newIterator = $contextNode;
//			} elseif ($contextNode instanceof DOMNode) {
//				if ($contextNode.nodeType == XML_TEXT_NODE) {
//					$out .= $contextNode.nodeValue;
//				} elseif ($contextNode.nodeName == 'template') {
//					# Double-brace expansion
//					$xpath = new DOMXPath($contextNode.ownerDocument);
//					$titles = $xpath.query('title', $contextNode);
//					$title = $titles.item(0);
//					$parts = $xpath.query('part', $contextNode);
//					if ($flags & PPFrame::NO_TEMPLATES) {
//						$newIterator = this.virtualBracketedImplode('{{', '|', '}}', $title, $parts);
//					} else {
//						$lineStart = $contextNode.getAttribute('lineStart');
//						$params = [
//							'title' => new PPNode_DOM($title),
//							'parts' => new PPNode_DOM($parts),
//							'lineStart' => $lineStart ];
//						$ret = this.parser.braceSubstitution($params, $this);
//						if (isset($ret['Object'])) {
//							$newIterator = $ret['Object'];
//						} else {
//							$out .= $ret['text'];
//						}
//					}
//				} elseif ($contextNode.nodeName == 'tplarg') {
//					# Triple-brace expansion
//					$xpath = new DOMXPath($contextNode.ownerDocument);
//					$titles = $xpath.query('title', $contextNode);
//					$title = $titles.item(0);
//					$parts = $xpath.query('part', $contextNode);
//					if ($flags & PPFrame::NO_ARGS) {
//						$newIterator = this.virtualBracketedImplode('{{{', '|', '}}}', $title, $parts);
//					} else {
//						$params = [
//							'title' => new PPNode_DOM($title),
//							'parts' => new PPNode_DOM($parts) ];
//						$ret = this.parser.argSubstitution($params, $this);
//						if (isset($ret['Object'])) {
//							$newIterator = $ret['Object'];
//						} else {
//							$out .= $ret['text'];
//						}
//					}
//				} elseif ($contextNode.nodeName == 'comment') {
//					# HTML-style comment
//					# Remove it in HTML, pre+remove and STRIP_COMMENTS modes
//					# Not in RECOVER_COMMENTS mode (msgnw) though.
//					if ((this.parser.ot['html']
//						|| (this.parser.ot['pre'] && this.parser.mOptions.getRemoveComments())
//						|| ($flags & PPFrame::STRIP_COMMENTS)
//						) && !($flags & PPFrame::RECOVER_COMMENTS)
//					) {
//						$out .= '';
//					} elseif (this.parser.ot['wiki'] && !($flags & PPFrame::RECOVER_COMMENTS)) {
//						# Add a strip marker in PST mode so that pstPass2() can
//						# run some old-fashioned regexes on the result.
//						# Not in RECOVER_COMMENTS mode (extractSections) though.
//						$out .= this.parser.insertStripItem($contextNode.textContent);
//					} else {
//						# Recover the literal comment in RECOVER_COMMENTS and pre+no-remove
//						$out .= $contextNode.textContent;
//					}
//				} elseif ($contextNode.nodeName == 'ignore') {
//					# Output suppression used by <includeonly> etc.
//					# OT_WIKI will only respect <ignore> in substed templates.
//					# The other output types respect it unless NO_IGNORE is set.
//					# extractSections() sets NO_IGNORE and so never respects it.
//					if ((!isset(this.parent) && this.parser.ot['wiki'])
//						|| ($flags & PPFrame::NO_IGNORE)
//					) {
//						$out .= $contextNode.textContent;
//					} else {
//						$out .= '';
//					}
//				} elseif ($contextNode.nodeName == 'ext') {
//					# Extension tag
//					$xpath = new DOMXPath($contextNode.ownerDocument);
//					$names = $xpath.query('name', $contextNode);
//					$attrs = $xpath.query('attr', $contextNode);
//					$inners = $xpath.query('inner', $contextNode);
//					$closes = $xpath.query('close', $contextNode);
//					if ($flags & PPFrame::NO_TAGS) {
//						$s = '<' . this.expand($names.item(0), $flags);
//						if ($attrs.length > 0) {
//							$s .= this.expand($attrs.item(0), $flags);
//						}
//						if ($inners.length > 0) {
//							$s .= '>' . this.expand($inners.item(0), $flags);
//							if ($closes.length > 0) {
//								$s .= this.expand($closes.item(0), $flags);
//							}
//						} else {
//							$s .= '/>';
//						}
//						$out .= $s;
//					} else {
//						$params = [
//							'name' => new PPNode_DOM($names.item(0)),
//							'attr' => $attrs.length > 0 ? new PPNode_DOM($attrs.item(0)) : null,
//							'inner' => $inners.length > 0 ? new PPNode_DOM($inners.item(0)) : null,
//							'close' => $closes.length > 0 ? new PPNode_DOM($closes.item(0)) : null,
//						];
//						$out .= this.parser.extensionSubstitution($params, $this);
//					}
//				} elseif ($contextNode.nodeName == 'h') {
//					# Heading
//					$s = this.expand($contextNode.childNodes, $flags);
//
//					# Insert a heading marker only for <h> children of <root>
//					# This is to stop extractSections from going over multiple tree levels
//					if ($contextNode.parentNode.nodeName == 'root' && this.parser.ot['html']) {
//						# Insert heading index marker
//						$headingIndex = $contextNode.getAttribute('i');
//						$titleText = this.title.getPrefixedDBkey();
//						this.parser.mHeadings[] = [ $titleText, $headingIndex ];
//						$serial = count(this.parser.mHeadings) - 1;
//						$marker = Parser::MARKER_PREFIX . "-h-$serial-" . Parser::MARKER_SUFFIX;
//						$count = $contextNode.getAttribute('level');
//						$s = substr($s, 0, $count) . $marker . substr($s, $count);
//						this.parser.mStripState.addGeneral($marker, '');
//					}
//					$out .= $s;
//				} else {
//					# Generic recursive expansion
//					$newIterator = $contextNode.childNodes;
//				}
//			} else {
//				throw new MWException(__METHOD__ . ': Invalid parameter type');
//			}
//
//			if ($newIterator !== false) {
//				if ($newIterator instanceof PPNode_DOM) {
//					$newIterator = $newIterator.node;
//				}
//				$outStack[] = '';
//				$iteratorStack[] = $newIterator;
//				$indexStack[] = 0;
//			} elseif ($iteratorStack[$level] === false) {
//				// Return accumulated value to parent
//				// With tail recursion
//				while ($iteratorStack[$level] === false && $level > 0) {
//					$outStack[$level - 1] .= $out;
//					array_pop($outStack);
//					array_pop($iteratorStack);
//					array_pop($indexStack);
//					$level--;
//				}
//			}
//		}
//		--$expansionDepth;
//		return $outStack[0];
//	}
//
//	/**
//	 * @param String $sep
//	 * @param int $flags
//	 * @param String|PPNode_DOM|DOMDocument $args,...
//	 * @return String
//	 */
//	public function implodeWithFlags($sep, $flags /*, ... */) {
//		$args = array_slice(func_get_args(), 2);
//
//		$first = true;
//		$s = '';
//		for each ($args as $root) {
//			if ($root instanceof PPNode_DOM) {
//				$root = $root.node;
//			}
//			if (!is_array($root) && !($root instanceof DOMNodeList)) {
//				$root = [ $root ];
//			}
//			for each ($root as $node) {
//				if ($first) {
//					$first = false;
//				} else {
//					$s .= $sep;
//				}
//				$s .= this.expand($node, $flags);
//			}
//		}
//		return $s;
//	}
//
//	/**
//	 * Implode with no flags specified
//	 * This previously called implodeWithFlags but has now been inlined to reduce stack depth
//	 *
//	 * @param String $sep
//	 * @param String|PPNode_DOM|DOMDocument $args,...
//	 * @return String
//	 */
//	public function implode($sep /*, ... */) {
//		$args = array_slice(func_get_args(), 1);
//
//		$first = true;
//		$s = '';
//		for each ($args as $root) {
//			if ($root instanceof PPNode_DOM) {
//				$root = $root.node;
//			}
//			if (!is_array($root) && !($root instanceof DOMNodeList)) {
//				$root = [ $root ];
//			}
//			for each ($root as $node) {
//				if ($first) {
//					$first = false;
//				} else {
//					$s .= $sep;
//				}
//				$s .= this.expand($node);
//			}
//		}
//		return $s;
//	}
//
//	/**
//	 * Makes an Object that, when expand()ed, will be the same as one obtained
//	 * with implode()
//	 *
//	 * @param String $sep
//	 * @param String|PPNode_DOM|DOMDocument $args,...
//	 * @return array
//	 */
//	public function virtualImplode($sep /*, ... */) {
//		$args = array_slice(func_get_args(), 1);
//		$out = [];
//		$first = true;
//
//		for each ($args as $root) {
//			if ($root instanceof PPNode_DOM) {
//				$root = $root.node;
//			}
//			if (!is_array($root) && !($root instanceof DOMNodeList)) {
//				$root = [ $root ];
//			}
//			for each ($root as $node) {
//				if ($first) {
//					$first = false;
//				} else {
//					$out[] = $sep;
//				}
//				$out[] = $node;
//			}
//		}
//		return $out;
//	}
//
//	/**
//	 * Virtual implode with brackets
//	 * @param String $start
//	 * @param String $sep
//	 * @param String $end
//	 * @param String|PPNode_DOM|DOMDocument $args,...
//	 * @return array
//	 */
//	public function virtualBracketedImplode($start, $sep, $end /*, ... */) {
//		$args = array_slice(func_get_args(), 3);
//		$out = [ $start ];
//		$first = true;
//
//		for each ($args as $root) {
//			if ($root instanceof PPNode_DOM) {
//				$root = $root.node;
//			}
//			if (!is_array($root) && !($root instanceof DOMNodeList)) {
//				$root = [ $root ];
//			}
//			for each ($root as $node) {
//				if ($first) {
//					$first = false;
//				} else {
//					$out[] = $sep;
//				}
//				$out[] = $node;
//			}
//		}
//		$out[] = $end;
//		return $out;
//	}
//
//	public function __toString() {
//		return 'frame{}';
//	}
//
//	public function getPDBK($level = false) {
//		if ($level === false) {
//			return this.title.getPrefixedDBkey();
//		} else {
//			return isset(this.titleCache[$level]) ? this.titleCache[$level] : false;
//		}
//	}
//
//	/**
//	 * @return array
//	 */
//	public function getArguments() {
//		return [];
//	}
//
//	/**
//	 * @return array
//	 */
//	public function getNumberedArguments() {
//		return [];
//	}
//
//	/**
//	 * @return array
//	 */
//	public function getNamedArguments() {
//		return [];
//	}
//
//	/**
//	 * Returns true if there are no arguments in this frame
//	 *
//	 * @return boolean
//	 */
//	public function isEmpty() {
//		return true;
//	}
//
//	/**
//	 * @param int|String $name
//	 * @return boolean Always false in this implementation.
//	 */
//	public function getArgument($name) {
//		return false;
//	}
//
//	/**
//	 * Returns true if the infinite loop check is OK, false if a loop is detected
//	 *
//	 * @param Title $title
//	 * @return boolean
//	 */
//	public function loopCheck($title) {
//		return !isset(this.loopCheckHash[$title.getPrefixedDBkey()]);
//	}
//
//	/**
//	 * Return true if the frame is a template frame
//	 *
//	 * @return boolean
//	 */
//	public function isTemplate() {
//		return false;
//	}
//
//	/**
//	 * Get a title of frame
//	 *
//	 * @return Title
//	 */
//	public function getTitle() {
//		return this.title;
//	}
//
//	/**
//	 * Set the volatile flag
//	 *
//	 * @param boolean $flag
//	 */
//	public function setVolatile($flag = true) {
//		this.volatile = $flag;
//	}
//
//	/**
//	 * Get the volatile flag
//	 *
//	 * @return boolean
//	 */
//	public function isVolatile() {
//		return this.volatile;
//	}
//
//	/**
//	 * Set the TTL
//	 *
//	 * @param int $ttl
//	 */
//	public function setTTL($ttl) {
//		if ($ttl !== null && (this.ttl === null || $ttl < this.ttl)) {
//			this.ttl = $ttl;
//		}
//	}
//
//	/**
//	 * Get the TTL
//	 *
//	 * @return int|null
//	 */
//	public function getTTL() {
//		return this.ttl;
//	}
//}
//
///**
// * Expansion frame with template arguments
// * @ingroup Parser
// */
//// @codingStandardsIgnoreStart Squiz.Classes.ValidClassName.NotCamelCaps
//class PPTemplateFrame_DOM extends PPFrame_DOM {
//	// @codingStandardsIgnoreEnd
//
//	public $numberedArgs, $namedArgs;
//
//	/**
//	 * @var PPFrame_DOM
//	 */
//	public $parent;
//	public $numberedExpansionCache, $namedExpansionCache;
//
//	/**
//	 * @param Preprocessor $preprocessor
//	 * @param boolean|PPFrame_DOM $parent
//	 * @param array $numberedArgs
//	 * @param array $namedArgs
//	 * @param boolean|Title $title
//	 */
//	public function __construct($preprocessor, $parent = false, $numberedArgs = [],
//		$namedArgs = [], $title = false
//	) {
//		parent::__construct($preprocessor);
//
//		this.parent = $parent;
//		this.numberedArgs = $numberedArgs;
//		this.namedArgs = $namedArgs;
//		this.title = $title;
//		$pdbk = $title ? $title.getPrefixedDBkey() : false;
//		this.titleCache = $parent.titleCache;
//		this.titleCache[] = $pdbk;
//		this.loopCheckHash = /*clone*/ $parent.loopCheckHash;
//		if ($pdbk !== false) {
//			this.loopCheckHash[$pdbk] = true;
//		}
//		this.depth = $parent.depth + 1;
//		this.numberedExpansionCache = this.namedExpansionCache = [];
//	}
//
//	public function __toString() {
//		$s = 'tplframe{';
//		$first = true;
//		$args = this.numberedArgs + this.namedArgs;
//		for each ($args as $name => $value) {
//			if ($first) {
//				$first = false;
//			} else {
//				$s .= ', ';
//			}
//			$s .= "\"$name\":\"" .
//				str_replace('"', '\\"', $value.ownerDocument.saveXML($value)) . '"';
//		}
//		$s .= '}';
//		return $s;
//	}
//
//	/**
//	 * @throws MWException
//	 * @param String|int $key
//	 * @param String|PPNode_DOM|DOMDocument $root
//	 * @param int $flags
//	 * @return String
//	 */
//	public function cachedExpand($key, $root, $flags = 0) {
//		if (isset(this.parent.childExpansionCache[$key])) {
//			return this.parent.childExpansionCache[$key];
//		}
//		$retval = this.expand($root, $flags);
//		if (!this.isVolatile()) {
//			this.parent.childExpansionCache[$key] = $retval;
//		}
//		return $retval;
//	}
//
//	/**
//	 * Returns true if there are no arguments in this frame
//	 *
//	 * @return boolean
//	 */
//	public function isEmpty() {
//		return !count(this.numberedArgs) && !count(this.namedArgs);
//	}
//
//	public function getArguments() {
//		$arguments = [];
//		for each (array_merge(
//				array_keys(this.numberedArgs),
//				array_keys(this.namedArgs)) as $key) {
//			$arguments[$key] = this.getArgument($key);
//		}
//		return $arguments;
//	}
//
//	public function getNumberedArguments() {
//		$arguments = [];
//		for each (array_keys(this.numberedArgs) as $key) {
//			$arguments[$key] = this.getArgument($key);
//		}
//		return $arguments;
//	}
//
//	public function getNamedArguments() {
//		$arguments = [];
//		for each (array_keys(this.namedArgs) as $key) {
//			$arguments[$key] = this.getArgument($key);
//		}
//		return $arguments;
//	}
//
//	/**
//	 * @param int $index
//	 * @return String|boolean
//	 */
//	public function getNumberedArgument($index) {
//		if (!isset(this.numberedArgs[$index])) {
//			return false;
//		}
//		if (!isset(this.numberedExpansionCache[$index])) {
//			# No trimming for unnamed arguments
//			this.numberedExpansionCache[$index] = this.parent.expand(
//				this.numberedArgs[$index],
//				PPFrame::STRIP_COMMENTS
//			);
//		}
//		return this.numberedExpansionCache[$index];
//	}
//
//	/**
//	 * @param String $name
//	 * @return String|boolean
//	 */
//	public function getNamedArgument($name) {
//		if (!isset(this.namedArgs[$name])) {
//			return false;
//		}
//		if (!isset(this.namedExpansionCache[$name])) {
//			# Trim named arguments post-expand, for backwards compatibility
//			this.namedExpansionCache[$name] = trim(
//				this.parent.expand(this.namedArgs[$name], PPFrame::STRIP_COMMENTS));
//		}
//		return this.namedExpansionCache[$name];
//	}
//
//	/**
//	 * @param int|String $name
//	 * @return String|boolean
//	 */
//	public function getArgument($name) {
//		$text = this.getNumberedArgument($name);
//		if ($text === false) {
//			$text = this.getNamedArgument($name);
//		}
//		return $text;
//	}
//
//	/**
//	 * Return true if the frame is a template frame
//	 *
//	 * @return boolean
//	 */
//	public function isTemplate() {
//		return true;
//	}
//
//	public function setVolatile($flag = true) {
//		parent::setVolatile($flag);
//		this.parent.setVolatile($flag);
//	}
//
//	public function setTTL($ttl) {
//		parent::setTTL($ttl);
//		this.parent.setTTL($ttl);
//	}
//}
//
///**
// * Expansion frame with custom arguments
// * @ingroup Parser
// */
//// @codingStandardsIgnoreStart Squiz.Classes.ValidClassName.NotCamelCaps
//class PPCustomFrame_DOM extends PPFrame_DOM {
//	// @codingStandardsIgnoreEnd
//
//	public $args;
//
//	public function __construct($preprocessor, $args) {
//		parent::__construct($preprocessor);
//		this.args = $args;
//	}
//
//	public function __toString() {
//		$s = 'cstmframe{';
//		$first = true;
//		for each (this.args as $name => $value) {
//			if ($first) {
//				$first = false;
//			} else {
//				$s .= ', ';
//			}
//			$s .= "\"$name\":\"" .
//				str_replace('"', '\\"', $value.__toString()) . '"';
//		}
//		$s .= '}';
//		return $s;
//	}
//
//	/**
//	 * @return boolean
//	 */
//	public function isEmpty() {
//		return !count(this.args);
//	}
//
//	/**
//	 * @param int|String $index
//	 * @return String|boolean
//	 */
//	public function getArgument($index) {
//		if (!isset(this.args[$index])) {
//			return false;
//		}
//		return this.args[$index];
//	}
//
//	public function getArguments() {
//		return this.args;
//	}
//}
//
///**
// * @ingroup Parser
// */
//// @codingStandardsIgnoreStart Squiz.Classes.ValidClassName.NotCamelCaps
//class PPNode_DOM implements PPNode {
//	// @codingStandardsIgnoreEnd
//
//	/**
//	 * @var DOMElement
//	 */
//	public $node;
//	public $xpath;
//
//	public function __construct($node, $xpath = false) {
//		this.node = $node;
//	}
//
//	/**
//	 * @return DOMXPath
//	 */
//	public function getXPath() {
//		if (this.xpath === null) {
//			this.xpath = new DOMXPath(this.node.ownerDocument);
//		}
//		return this.xpath;
//	}
//
//	public function __toString() {
//		if (this.node instanceof DOMNodeList) {
//			$s = '';
//			for each (this.node as $node) {
//				$s .= $node.ownerDocument.saveXML($node);
//			}
//		} else {
//			$s = this.node.ownerDocument.saveXML(this.node);
//		}
//		return $s;
//	}
//
//	/**
//	 * @return boolean|PPNode_DOM
//	 */
//	public function getChildren() {
//		return this.node.childNodes ? new self(this.node.childNodes) : false;
//	}
//
//	/**
//	 * @return boolean|PPNode_DOM
//	 */
//	public function getFirstChild() {
//		return this.node.firstChild ? new self(this.node.firstChild) : false;
//	}
//
//	/**
//	 * @return boolean|PPNode_DOM
//	 */
//	public function getNextSibling() {
//		return this.node.nextSibling ? new self(this.node.nextSibling) : false;
//	}
//
//	/**
//	 * @param String $type
//	 *
//	 * @return boolean|PPNode_DOM
//	 */
//	public function getChildrenOfType($type) {
//		return new self(this.getXPath().query($type, this.node));
//	}
//
//	/**
//	 * @return int
//	 */
//	public function getLength() {
//		if (this.node instanceof DOMNodeList) {
//			return this.node.length;
//		} else {
//			return false;
//		}
//	}
//
//	/**
//	 * @param int $i
//	 * @return boolean|PPNode_DOM
//	 */
//	public function item($i) {
//		$item = this.node.item($i);
//		return $item ? new self($item) : false;
//	}
//
//	/**
//	 * @return String
//	 */
//	public function getName() {
//		if (this.node instanceof DOMNodeList) {
//			return '#nodelist';
//		} else {
//			return this.node.nodeName;
//		}
//	}
//
//	/**
//	 * Split a "<part>" node into an associative array containing:
//	 *  - name          PPNode name
//	 *  - index         String index
//	 *  - value         PPNode value
//	 *
//	 * @throws MWException
//	 * @return array
//	 */
//	public function splitArg() {
//		$xpath = this.getXPath();
//		$names = $xpath.query('name', this.node);
//		$values = $xpath.query('value', this.node);
//		if (!$names.length || !$values.length) {
//			throw new MWException('Invalid brace node passed to ' . __METHOD__);
//		}
//		$name = $names.item(0);
//		$index = $name.getAttribute('index');
//		return [
//			'name' => new self($name),
//			'index' => $index,
//			'value' => new self($values.item(0)) ];
//	}
//
//	/**
//	 * Split an "<ext>" node into an associative array containing name, attr, inner and close
//	 * All values in the resulting array are PPNodes. Inner and close are optional.
//	 *
//	 * @throws MWException
//	 * @return array
//	 */
//	public function splitExt() {
//		$xpath = this.getXPath();
//		$names = $xpath.query('name', this.node);
//		$attrs = $xpath.query('attr', this.node);
//		$inners = $xpath.query('inner', this.node);
//		$closes = $xpath.query('close', this.node);
//		if (!$names.length || !$attrs.length) {
//			throw new MWException('Invalid ext node passed to ' . __METHOD__);
//		}
//		$parts = [
//			'name' => new self($names.item(0)),
//			'attr' => new self($attrs.item(0)) ];
//		if ($inners.length) {
//			$parts['inner'] = new self($inners.item(0));
//		}
//		if ($closes.length) {
//			$parts['close'] = new self($closes.item(0));
//		}
//		return $parts;
//	}
//
//	/**
//	 * Split a "<h>" node
//	 * @throws MWException
//	 * @return array
//	 */
//	public function splitHeading() {
//		if (this.getName() !== 'h') {
//			throw new MWException('Invalid h node passed to ' . __METHOD__);
//		}
//		return [
//			'i' => this.node.getAttribute('i'),
//			'level' => this.node.getAttribute('level'),
//			'contents' => this.getChildren()
//		];
//	}
//}
///**
// * Stack class to help Preprocessor::preprocessToObj()
// * @ingroup Parser
// */
//// @codingStandardsIgnoreStart Squiz.Classes.ValidClassName.NotCamelCaps
//class PPDStack_Hash extends PPDStack {
//	// @codingStandardsIgnoreEnd
//
//	public function __construct() {
//		this.elementClass = 'PPDStackElement_Hash';
//		parent::__construct();
//		this.rootAccum = [];
//	}
//}
//
///**
// * @ingroup Parser
// */
//// @codingStandardsIgnoreStart Squiz.Classes.ValidClassName.NotCamelCaps
//class PPDPart_Hash extends PPDPart {
//	// @codingStandardsIgnoreEnd
//
//	public function __construct($out = '') {
//		if ($out !== '') {
//			$accum = [ $out ];
//		} else {
//			$accum = [];
//		}
//		parent::__construct($accum);
//	}
//}
//
///**
// * An expansion frame, used as a context to expand the result of preprocessToObj()
// * @ingroup Parser
// */
//// @codingStandardsIgnoreStart Squiz.Classes.ValidClassName.NotCamelCaps
//class PPFrame_Hash implements PPFrame {
//	// @codingStandardsIgnoreEnd
//
//	/**
//	 * @var Parser
//	 */
//	public $parser;
//
//	/**
//	 * @var Preprocessor
//	 */
//	public $preprocessor;
//
//	/**
//	 * @var Title
//	 */
//	public $title;
//	public $titleCache;
//
//	/**
//	 * Hashtable listing templates which are disallowed for expansion in this frame,
//	 * having been encountered previously in parent frames.
//	 */
//	public $loopCheckHash;
//
//	/**
//	 * Recursion depth of this frame, top = 0
//	 * Note that this is NOT the same as expansion depth in expand()
//	 */
//	public $depth;
//
//	private $volatile = false;
//	private $ttl = null;
//
//	/**
//	 * @var array
//	 */
//	protected $childExpansionCache;
//
//	/**
//	 * Construct a new preprocessor frame.
//	 * @param Preprocessor $preprocessor The parent preprocessor
//	 */
//	public function __construct($preprocessor) {
//		this.preprocessor = $preprocessor;
//		this.parser = $preprocessor.parser;
//		this.title = this.parser.mTitle;
//		this.titleCache = [ this.title ? this.title.getPrefixedDBkey() : false ];
//		this.loopCheckHash = [];
//		this.depth = 0;
//		this.childExpansionCache = [];
//	}
//
//	/**
//	 * Create a new child frame
//	 * $args is optionally a multi-root PPNode or array containing the template arguments
//	 *
//	 * @param array|boolean|PPNode_Hash_Array $args
//	 * @param Title|boolean $title
//	 * @param int $indexOffset
//	 * @throws MWException
//	 * @return PPTemplateFrame_Hash
//	 */
//	public function newChild($args = false, $title = false, $indexOffset = 0) {
//		$namedArgs = [];
//		$numberedArgs = [];
//		if ($title === false) {
//			$title = this.title;
//		}
//		if ($args !== false) {
//			if ($args instanceof PPNode_Hash_Array) {
//				$args = $args.value;
//			} elseif (!is_array($args)) {
//				throw new MWException(__METHOD__ . ': $args must be array or PPNode_Hash_Array');
//			}
//			for each ($args as $arg) {
//				$bits = $arg.splitArg();
//				if ($bits['index'] !== '') {
//					// Numbered parameter
//					$index = $bits['index'] - $indexOffset;
//					if (isset($namedArgs[$index]) || isset($numberedArgs[$index])) {
//						this.parser.getOutput().addWarning(wfMessage('duplicate-args-warning',
//							wfEscapeWikiText(this.title),
//							wfEscapeWikiText($title),
//							wfEscapeWikiText($index)).text());
//						this.parser.addTrackingCategory('duplicate-args-category');
//					}
//					$numberedArgs[$index] = $bits['value'];
//					unset($namedArgs[$index]);
//				} else {
//					// Named parameter
//					$name = trim(this.expand($bits['name'], PPFrame::STRIP_COMMENTS));
//					if (isset($namedArgs[$name]) || isset($numberedArgs[$name])) {
//						this.parser.getOutput().addWarning(wfMessage('duplicate-args-warning',
//							wfEscapeWikiText(this.title),
//							wfEscapeWikiText($title),
//							wfEscapeWikiText($name)).text());
//						this.parser.addTrackingCategory('duplicate-args-category');
//					}
//					$namedArgs[$name] = $bits['value'];
//					unset($numberedArgs[$name]);
//				}
//			}
//		}
//		return new PPTemplateFrame_Hash(this.preprocessor, $this, $numberedArgs, $namedArgs, $title);
//	}
//
//	/**
//	 * @throws MWException
//	 * @param String|int $key
//	 * @param String|PPNode $root
//	 * @param int $flags
//	 * @return String
//	 */
//	public function cachedExpand($key, $root, $flags = 0) {
//		// we don't have a parent, so we don't have a cache
//		return this.expand($root, $flags);
//	}
//
//	/**
//	 * @throws MWException
//	 * @param String|PPNode $root
//	 * @param int $flags
//	 * @return String
//	 */
//	public function expand($root, $flags = 0) {
//		static $expansionDepth = 0;
//		if (is_string($root)) {
//			return $root;
//		}
//
//		if (++this.parser.mPPNodeCount > this.parser.mOptions.getMaxPPNodeCount()) {
//			this.parser.limitationWarn('node-count-exceeded',
//					this.parser.mPPNodeCount,
//					this.parser.mOptions.getMaxPPNodeCount()
//			);
//			return '<span class="error">Node-count limit exceeded</span>';
//		}
//		if ($expansionDepth > this.parser.mOptions.getMaxPPExpandDepth()) {
//			this.parser.limitationWarn('expansion-depth-exceeded',
//					$expansionDepth,
//					this.parser.mOptions.getMaxPPExpandDepth()
//			);
//			return '<span class="error">Expansion depth limit exceeded</span>';
//		}
//		++$expansionDepth;
//		if ($expansionDepth > this.parser.mHighestExpansionDepth) {
//			this.parser.mHighestExpansionDepth = $expansionDepth;
//		}
//
//		$outStack = [ '', '' ];
//		$iteratorStack = [ false, $root ];
//		$indexStack = [ 0, 0 ];
//
//		while (count($iteratorStack) > 1) {
//			$level = count($outStack) - 1;
//			$iteratorNode =& $iteratorStack[$level];
//			$out =& $outStack[$level];
//			$index =& $indexStack[$level];
//
//			if (is_array($iteratorNode)) {
//				if ($index >= count($iteratorNode)) {
//					// All done with this iterator
//					$iteratorStack[$level] = false;
//					$contextNode = false;
//				} else {
//					$contextNode = $iteratorNode[$index];
//					$index++;
//				}
//			} elseif ($iteratorNode instanceof PPNode_Hash_Array) {
//				if ($index >= $iteratorNode.getLength()) {
//					// All done with this iterator
//					$iteratorStack[$level] = false;
//					$contextNode = false;
//				} else {
//					$contextNode = $iteratorNode.item($index);
//					$index++;
//				}
//			} else {
//				// Copy to $contextNode and then delete from iterator stack,
//				// because this is not an iterator but we do have to execute it once
//				$contextNode = $iteratorStack[$level];
//				$iteratorStack[$level] = false;
//			}
//
//			$newIterator = false;
//			$contextName = false;
//			$contextChildren = false;
//
//			if ($contextNode === false) {
//				// nothing to do
//			} elseif (is_string($contextNode)) {
//				$out .= $contextNode;
//			} elseif ($contextNode instanceof PPNode_Hash_Array) {
//				$newIterator = $contextNode;
//			} elseif ($contextNode instanceof PPNode_Hash_Attr) {
//				// No output
//			} elseif ($contextNode instanceof PPNode_Hash_Text) {
//				$out .= $contextNode.value;
//			} elseif ($contextNode instanceof PPNode_Hash_Tree) {
//				$contextName = $contextNode.name;
//				$contextChildren = $contextNode.getRawChildren();
//			} elseif (is_array($contextNode)) {
//				// Node descriptor array
//				if (count($contextNode) !== 2) {
//					throw new MWException(__METHOD__.
//						': found an array where a node descriptor should be');
//				}
//				list($contextName, $contextChildren) = $contextNode;
//			} else {
//				throw new MWException(__METHOD__ . ': Invalid parameter type');
//			}
//
//			// Handle node descriptor array or tree Object
//			if ($contextName === false) {
//				// Not a node, already handled above
//			} elseif ($contextName[0] === '@') {
//				// Attribute: no output
//			} elseif ($contextName === 'template') {
//				# Double-brace expansion
//				$bits = PPNode_Hash_Tree::splitRawTemplate($contextChildren);
//				if ($flags & PPFrame::NO_TEMPLATES) {
//					$newIterator = this.virtualBracketedImplode(
//						'{{', '|', '}}',
//						$bits['title'],
//						$bits['parts']
//					);
//				} else {
//					$ret = this.parser.braceSubstitution($bits, $this);
//					if (isset($ret['Object'])) {
//						$newIterator = $ret['Object'];
//					} else {
//						$out .= $ret['text'];
//					}
//				}
//			} elseif ($contextName === 'tplarg') {
//				# Triple-brace expansion
//				$bits = PPNode_Hash_Tree::splitRawTemplate($contextChildren);
//				if ($flags & PPFrame::NO_ARGS) {
//					$newIterator = this.virtualBracketedImplode(
//						'{{{', '|', '}}}',
//						$bits['title'],
//						$bits['parts']
//					);
//				} else {
//					$ret = this.parser.argSubstitution($bits, $this);
//					if (isset($ret['Object'])) {
//						$newIterator = $ret['Object'];
//					} else {
//						$out .= $ret['text'];
//					}
//				}
//			} elseif ($contextName === 'comment') {
//				# HTML-style comment
//				# Remove it in HTML, pre+remove and STRIP_COMMENTS modes
//				# Not in RECOVER_COMMENTS mode (msgnw) though.
//				if ((this.parser.ot['html']
//					|| (this.parser.ot['pre'] && this.parser.mOptions.getRemoveComments())
//					|| ($flags & PPFrame::STRIP_COMMENTS)
//					) && !($flags & PPFrame::RECOVER_COMMENTS)
//				) {
//					$out .= '';
//				} elseif (this.parser.ot['wiki'] && !($flags & PPFrame::RECOVER_COMMENTS)) {
//					# Add a strip marker in PST mode so that pstPass2() can
//					# run some old-fashioned regexes on the result.
//					# Not in RECOVER_COMMENTS mode (extractSections) though.
//					$out .= this.parser.insertStripItem($contextChildren[0]);
//				} else {
//					# Recover the literal comment in RECOVER_COMMENTS and pre+no-remove
//					$out .= $contextChildren[0];
//				}
//			} elseif ($contextName === 'ignore') {
//				# Output suppression used by <includeonly> etc.
//				# OT_WIKI will only respect <ignore> in substed templates.
//				# The other output types respect it unless NO_IGNORE is set.
//				# extractSections() sets NO_IGNORE and so never respects it.
//				if ((!isset(this.parent) && this.parser.ot['wiki'])
//					|| ($flags & PPFrame::NO_IGNORE)
//				) {
//					$out .= $contextChildren[0];
//				} else {
//					// $out .= '';
//				}
//			} elseif ($contextName === 'ext') {
//				# Extension tag
//				$bits = PPNode_Hash_Tree::splitRawExt($contextChildren) +
//					[ 'attr' => null, 'inner' => null, 'close' => null ];
//				if ($flags & PPFrame::NO_TAGS) {
//					$s = '<' . $bits['name'].getFirstChild().value;
//					if ($bits['attr']) {
//						$s .= $bits['attr'].getFirstChild().value;
//					}
//					if ($bits['inner']) {
//						$s .= '>' . $bits['inner'].getFirstChild().value;
//						if ($bits['close']) {
//							$s .= $bits['close'].getFirstChild().value;
//						}
//					} else {
//						$s .= '/>';
//					}
//					$out .= $s;
//				} else {
//					$out .= this.parser.extensionSubstitution($bits, $this);
//				}
//			} elseif ($contextName === 'h') {
//				# Heading
//				if (this.parser.ot['html']) {
//					# Expand immediately and insert heading index marker
//					$s = this.expand($contextChildren, $flags);
//					$bits = PPNode_Hash_Tree::splitRawHeading($contextChildren);
//					$titleText = this.title.getPrefixedDBkey();
//					this.parser.mHeadings[] = [ $titleText, $bits['i'] ];
//					$serial = count(this.parser.mHeadings) - 1;
//					$marker = Parser::MARKER_PREFIX . "-h-$serial-" . Parser::MARKER_SUFFIX;
//					$s = substr($s, 0, $bits['level']) . $marker . substr($s, $bits['level']);
//					this.parser.mStripState.addGeneral($marker, '');
//					$out .= $s;
//				} else {
//					# Expand in virtual stack
//					$newIterator = $contextChildren;
//				}
//			} else {
//				# Generic recursive expansion
//				$newIterator = $contextChildren;
//			}
//
//			if ($newIterator !== false) {
//				$outStack[] = '';
//				$iteratorStack[] = $newIterator;
//				$indexStack[] = 0;
//			} elseif ($iteratorStack[$level] === false) {
//				// Return accumulated value to parent
//				// With tail recursion
//				while ($iteratorStack[$level] === false && $level > 0) {
//					$outStack[$level - 1] .= $out;
//					array_pop($outStack);
//					array_pop($iteratorStack);
//					array_pop($indexStack);
//					$level--;
//				}
//			}
//		}
//		--$expansionDepth;
//		return $outStack[0];
//	}
//
//	/**
//	 * @param String $sep
//	 * @param int $flags
//	 * @param String|PPNode $args,...
//	 * @return String
//	 */
//	public function implodeWithFlags($sep, $flags /*, ... */) {
//		$args = array_slice(func_get_args(), 2);
//
//		$first = true;
//		$s = '';
//		for each ($args as $root) {
//			if ($root instanceof PPNode_Hash_Array) {
//				$root = $root.value;
//			}
//			if (!is_array($root)) {
//				$root = [ $root ];
//			}
//			for each ($root as $node) {
//				if ($first) {
//					$first = false;
//				} else {
//					$s .= $sep;
//				}
//				$s .= this.expand($node, $flags);
//			}
//		}
//		return $s;
//	}
//
//	/**
//	 * Implode with no flags specified
//	 * This previously called implodeWithFlags but has now been inlined to reduce stack depth
//	 * @param String $sep
//	 * @param String|PPNode $args,...
//	 * @return String
//	 */
//	public function implode($sep /*, ... */) {
//		$args = array_slice(func_get_args(), 1);
//
//		$first = true;
//		$s = '';
//		for each ($args as $root) {
//			if ($root instanceof PPNode_Hash_Array) {
//				$root = $root.value;
//			}
//			if (!is_array($root)) {
//				$root = [ $root ];
//			}
//			for each ($root as $node) {
//				if ($first) {
//					$first = false;
//				} else {
//					$s .= $sep;
//				}
//				$s .= this.expand($node);
//			}
//		}
//		return $s;
//	}
//
//	/**
//	 * Makes an Object that, when expand()ed, will be the same as one obtained
//	 * with implode()
//	 *
//	 * @param String $sep
//	 * @param String|PPNode $args,...
//	 * @return PPNode_Hash_Array
//	 */
//	public function virtualImplode($sep /*, ... */) {
//		$args = array_slice(func_get_args(), 1);
//		$out = [];
//		$first = true;
//
//		for each ($args as $root) {
//			if ($root instanceof PPNode_Hash_Array) {
//				$root = $root.value;
//			}
//			if (!is_array($root)) {
//				$root = [ $root ];
//			}
//			for each ($root as $node) {
//				if ($first) {
//					$first = false;
//				} else {
//					$out[] = $sep;
//				}
//				$out[] = $node;
//			}
//		}
//		return new PPNode_Hash_Array($out);
//	}
//
//	/**
//	 * Virtual implode with brackets
//	 *
//	 * @param String $start
//	 * @param String $sep
//	 * @param String $end
//	 * @param String|PPNode $args,...
//	 * @return PPNode_Hash_Array
//	 */
//	public function virtualBracketedImplode($start, $sep, $end /*, ... */) {
//		$args = array_slice(func_get_args(), 3);
//		$out = [ $start ];
//		$first = true;
//
//		for each ($args as $root) {
//			if ($root instanceof PPNode_Hash_Array) {
//				$root = $root.value;
//			}
//			if (!is_array($root)) {
//				$root = [ $root ];
//			}
//			for each ($root as $node) {
//				if ($first) {
//					$first = false;
//				} else {
//					$out[] = $sep;
//				}
//				$out[] = $node;
//			}
//		}
//		$out[] = $end;
//		return new PPNode_Hash_Array($out);
//	}
//
//	public function __toString() {
//		return 'frame{}';
//	}
//
//	/**
//	 * @param boolean $level
//	 * @return array|boolean|String
//	 */
//	public function getPDBK($level = false) {
//		if ($level === false) {
//			return this.title.getPrefixedDBkey();
//		} else {
//			return isset(this.titleCache[$level]) ? this.titleCache[$level] : false;
//		}
//	}
//
//	/**
//	 * @return array
//	 */
//	public function getArguments() {
//		return [];
//	}
//
//	/**
//	 * @return array
//	 */
//	public function getNumberedArguments() {
//		return [];
//	}
//
//	/**
//	 * @return array
//	 */
//	public function getNamedArguments() {
//		return [];
//	}
//
//	/**
//	 * Returns true if there are no arguments in this frame
//	 *
//	 * @return boolean
//	 */
//	public function isEmpty() {
//		return true;
//	}
//
//	/**
//	 * @param int|String $name
//	 * @return boolean Always false in this implementation.
//	 */
//	public function getArgument($name) {
//		return false;
//	}
//
//	/**
//	 * Returns true if the infinite loop check is OK, false if a loop is detected
//	 *
//	 * @param Title $title
//	 *
//	 * @return boolean
//	 */
//	public function loopCheck($title) {
//		return !isset(this.loopCheckHash[$title.getPrefixedDBkey()]);
//	}
//
//	/**
//	 * Return true if the frame is a template frame
//	 *
//	 * @return boolean
//	 */
//	public function isTemplate() {
//		return false;
//	}
//
//	/**
//	 * Get a title of frame
//	 *
//	 * @return Title
//	 */
//	public function getTitle() {
//		return this.title;
//	}
//
//	/**
//	 * Set the volatile flag
//	 *
//	 * @param boolean $flag
//	 */
//	public function setVolatile($flag = true) {
//		this.volatile = $flag;
//	}
//
//	/**
//	 * Get the volatile flag
//	 *
//	 * @return boolean
//	 */
//	public function isVolatile() {
//		return this.volatile;
//	}
//
//	/**
//	 * Set the TTL
//	 *
//	 * @param int $ttl
//	 */
//	public function setTTL($ttl) {
//		if ($ttl !== null && (this.ttl === null || $ttl < this.ttl)) {
//			this.ttl = $ttl;
//		}
//	}
//
//	/**
//	 * Get the TTL
//	 *
//	 * @return int|null
//	 */
//	public function getTTL() {
//		return this.ttl;
//	}
//}
//
///**
// * Expansion frame with custom arguments
// * @ingroup Parser
// */
//// @codingStandardsIgnoreStart Squiz.Classes.ValidClassName.NotCamelCaps
//class PPCustomFrame_Hash extends PPFrame_Hash {
//	// @codingStandardsIgnoreEnd
//
//	public $args;
//
//	public function __construct($preprocessor, $args) {
//		parent::__construct($preprocessor);
//		this.args = $args;
//	}
//
//	public function __toString() {
//		$s = 'cstmframe{';
//		$first = true;
//		for each (this.args as $name => $value) {
//			if ($first) {
//				$first = false;
//			} else {
//				$s .= ', ';
//			}
//			$s .= "\"$name\":\"" .
//				str_replace('"', '\\"', $value.__toString()) . '"';
//		}
//		$s .= '}';
//		return $s;
//	}
//
//	/**
//	 * @return boolean
//	 */
//	public function isEmpty() {
//		return !count(this.args);
//	}
//
//	/**
//	 * @param int|String $index
//	 * @return String|boolean
//	 */
//	public function getArgument($index) {
//		if (!isset(this.args[$index])) {
//			return false;
//		}
//		return this.args[$index];
//	}
//
//	public function getArguments() {
//		return this.args;
//	}
//}
