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
public class XomwStripState {
//		protected $prefix;
//		protected $data;
//		protected $regex;
//
//		protected $tempType, $tempMergePrefix;
//		protected $circularRefGuard;
//		protected $recursionLevel = 0;
//
//		static final UNSTRIP_RECURSION_LIMIT = 20;

	private final    Btrie_slim_mgr trie = Btrie_slim_mgr.cs();
	private final    Btrie_rv trv = new Btrie_rv();
	private final    Bry_bfr tmp_1 = Bry_bfr_.New();
	private final    Bry_bfr tmp_2 = Bry_bfr_.New();
	private boolean tmp_2_used = false;
	private int generalLen, nowikiLen;

//		/**
//		* @param String|null $prefix
//		* @since 1.26 The prefix argument should be omitted, as the strip marker
//		*  prefix String is now a constant.
//		*/
//		public function __construct($prefix = null) {
//			if ($prefix !== null) {
//				wfDeprecated(__METHOD__ . ' with called with $prefix argument' .
//					' (call with no arguments instead)', '1.26');
//			}
//			this.data = [
//				'nowiki' => [],
//				'general' => []
//			];
//			this.regex = '/' . Parser::MARKER_PREFIX . "([^\x7f<>&'\"]+)" . Parser::MARKER_SUFFIX . '/';
//			this.circularRefGuard = [];
//		}
//		public void Clear() {
//			trie.Clear();
//			generalLen = nowikiLen = 0;
//			tmp_2_used = false;
//		}

	/**
	* Add a nowiki strip item
	* @param String $marker
	* @param String $value
	*/
	public void addNoWiki(byte[] marker, byte[] val) {
		this.addItem(TYPE_NOWIKI, marker, val);
	}

	/**
	* @param String $marker
	* @param String $value
	*/
	public void addGeneral(byte[] marker, byte[] val) {
		this.addItem(TYPE_GENERAL, marker, val);
	}

	/**
	* @throws MWException
	* @param String $type
	* @param String $marker
	* @param String $value
	*/
	public void addItem(byte type, byte[] marker, byte[] val) {
		// if (!preg_match(this.regex, $marker, $m)) {
		//	throw new MWException("Invalid marker: $marker");
		// }

		// XO.MW:ported
		// this.data[$type][$m[1]] = $value;
		trie.Add_obj(marker, new XomwStripItem(type, marker, val));
		if (type == TYPE_GENERAL)
			generalLen++;
		else
			nowikiLen++;
	}

	/**
	* @param String $text
	* @return mixed
	*/
	public byte[] unstripGeneral(byte[] text) {
		return this.unstripType(TYPE_GENERAL, text);
	}

	/**
	* @param String $text
	* @return mixed
	*/
	public byte[] unstripNoWiki(byte[] text) {
		return this.unstripType(TYPE_NOWIKI, text);
	}

	/**
	* @param String $text
	* @return mixed
	*/
	public byte[] unstripBoth(byte[] text) {
		//	$text = this.unstripType('general', $text);
		//	$text = this.unstripType('nowiki', $text);
		return this.unstripType(TYPE_BOTH, text);
	}

	public byte[] unstripType(byte tid, byte[] text) {
		boolean dirty = unstripType(tid, tmp_1, text, 0, text.length);
		return dirty ? tmp_1.To_bry_and_clear() : text;
	}

	// XOWA
	public void unstripGeneral(XomwParserBfr pbfr) {unstripType(TYPE_GENERAL, pbfr);}
	public void unstripNoWiki(XomwParserBfr pbfr)  {unstripType(TYPE_NOWIKI , pbfr);}
	public void unstripBoth(XomwParserBfr pbfr)    {unstripType(TYPE_BOTH   , pbfr);}
	private boolean unstripType(byte tid, XomwParserBfr pbfr) {
		// XO.PBFR
		Bry_bfr src_bfr = pbfr.Src();
		byte[] src = src_bfr.Bfr();
		boolean dirty = unstripType(tid, pbfr.Trg(), src, 0, src_bfr.Len());
		if (dirty)
			pbfr.Switch();
		return dirty;
	}

	/**
	* @param String $type
	* @param String $text
	* @return mixed
	*/
	private boolean unstripType(byte tid, Bry_bfr trg, byte[] src, int src_bgn, int src_end) {
		//	// Shortcut
		//	if (!count(this.data[$type])) {
		//		return $text;
		//	}
		// exit early if no items for type
		if      ((tid & TYPE_GENERAL) == TYPE_GENERAL) {
			if (generalLen == 0)
				return false;
		}
		else if ((tid & TYPE_NOWIKI) == TYPE_NOWIKI) {
			if (nowikiLen == 0)
				return false;
		}

		// XO.MW:PORTED
		//	$oldType = this.tempType;
		//	this.tempType = $type;
		//	$text = preg_replace_callback(this.regex, [ $this, 'unstripCallback' ], $text);
		//	this.tempType = $oldType;
		//	return $text;
		int cur = src_bgn;
		int prv = cur;
		boolean dirty = false;
		// loop over each src char
		while (true) {
			// EOS: exit
			if (cur == src_end) {
				if (dirty)	// add remainder if dirty
					trg.Add_mid(src, prv, src_end);
				break;
			}

			// check if current pos matches strip state
			Object o = trie.Match_at(trv, src, cur, src_end);
			if (o != null) {	// match
				XomwStripItem item = (XomwStripItem)o;
				byte item_tid = item.Type();
				if ((tid & item_tid) == item_tid) {	// check if types match
					// get bfr for recursion
					Bry_bfr nested_bfr = null;
					boolean tmp_2_release = false;
					if (tmp_2_used) {
						nested_bfr = Bry_bfr_.New();
					}
					else {
						nested_bfr = tmp_2;
						tmp_2_used = true;
						tmp_2_release = true;
					}

					// recurse
					byte[] item_val = item.Val();
					if (unstripType(tid, nested_bfr, item_val, 0, item_val.length))
						item_val = nested_bfr.To_bry_and_clear();
					if (tmp_2_release)
						tmp_2_used = false;

					// add to trg
					trg.Add_mid(src, prv, cur);
					trg.Add(item_val);

					// update vars
					dirty = true;
					cur += item.Key().length;
					prv = cur;
					continue;
				}
			}
			cur++;
		}
		return dirty;
	}

	//	/**
	//	* @param array $m
	//	* @return array
	//	*/
	//	protected function unstripCallback($m) {
	//		$marker = $m[1];
	//		if (isset(this.data[this.tempType][$marker])) {
	//			if (isset(this.circularRefGuard[$marker])) {
	//				return '<span class="error">'
	//					. wfMessage('parser-unstrip-loop-warning')->inContentLanguage()->text()
	//					. '</span>';
	//			}
	//			if (this.recursionLevel >= self::UNSTRIP_RECURSION_LIMIT) {
	//				return '<span class="error">' .
	//					wfMessage('parser-unstrip-recursion-limit')
	//						->numParams(self::UNSTRIP_RECURSION_LIMIT)->inContentLanguage()->text() .
	//					'</span>';
	//			}
	//			this.circularRefGuard[$marker] = true;
	//			this.recursionLevel++;
	//			$value = this.data[this.tempType][$marker];
	//			if ($value instanceof Closure) {
	//				$value = $value();
	//			}
	//			$ret = this.unstripType(this.tempType, $value);
	//			this.recursionLevel--;
	//			unset(this.circularRefGuard[$marker]);
	//			return $ret;
	//		} else {
	//			return $m[0];
	//		}
	//	}

//		/**
//		* Get a StripState Object which is sufficient to unstrip the given text.
//		* It will contain the minimum subset of strip items necessary.
//		*
//		* @param String $text
//		*
//		* @return StripState
//		*/
//		public function getSubState($text) {
//			$subState = new StripState();
//			$pos = 0;
//			while (true) {
//				$startPos = strpos($text, Parser::MARKER_PREFIX, $pos);
//				$endPos = strpos($text, Parser::MARKER_SUFFIX, $pos);
//				if ($startPos === false || $endPos === false) {
//					break;
//				}
//
//				$endPos += strlen(Parser::MARKER_SUFFIX);
//				$marker = substr($text, $startPos, $endPos - $startPos);
//				if (!preg_match(this.regex, $marker, $m)) {
//					continue;
//				}
//
//				$key = $m[1];
//				if (isset(this.data['nowiki'][$key])) {
//					$subState->data['nowiki'][$key] = this.data['nowiki'][$key];
//				} elseif (isset(this.data['general'][$key])) {
//					$subState->data['general'][$key] = this.data['general'][$key];
//				}
//				$pos = $endPos;
//			}
//			return $subState;
//		}
//
//		/**
//		* Merge another StripState Object into this one. The strip marker keys
//		* will not be preserved. The strings in the $texts array will have their
//		* strip markers rewritten, the resulting array of strings will be returned.
//		*
//		* @param StripState $otherState
//		* @param array $texts
//		* @return array
//		*/
//		public function merge($otherState, $texts) {
//			$mergePrefix = wfRandomString(16);
//
//			foreach ($otherState->data as $type => $items) {
//				foreach ($items as $key => $value) {
//					this.data[$type]["$mergePrefix-$key"] = $value;
//				}
//			}
//
//			this.tempMergePrefix = $mergePrefix;
//			$texts = preg_replace_callback($otherState->regex, [ $this, 'mergeCallback' ], $texts);
//			this.tempMergePrefix = null;
//			return $texts;
//		}
//
//		/**
//		* @param array $m
//		* @return String
//		*/
//		protected function mergeCallback($m) {
//			$key = $m[1];
//			return Parser::MARKER_PREFIX . this.tempMergePrefix . '-' . $key . Parser::MARKER_SUFFIX;
//		}
//
//		/**
//		* Remove any strip markers found in the given text.
//		*
//		* @param String $text Input String
//		* @return String
//		*/
//		public function killMarkers($text) {
//			return preg_replace(this.regex, '', $text);
//		}
//		public static final    String Str__marker_bgn = "\u007f'\"`UNIQ-";
//		public static final    byte[] 
//		  Bry__marker__bgn		= Bry_.new_a7(Str__marker_bgn)
//		, Bry__marker__end		= Bry_.new_a7("-QINU`\"'\u007f")
//		;
	public static final byte TYPE_GENERAL = 1, TYPE_NOWIKI = 2, TYPE_BOTH = 3; 
}
class XomwStripItem {
	public XomwStripItem(byte tid, byte[] key, byte[] val) {
		this.tid = tid;
		this.key = key;
		this.val = val;
	}
	public byte Type() {return tid;} private final    byte tid;
	public byte[] Key() {return key;} private final    byte[] key;
	public byte[] Val() {return val;} private final    byte[] val;
}
