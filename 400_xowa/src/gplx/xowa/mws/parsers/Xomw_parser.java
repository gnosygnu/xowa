/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.mws.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
import gplx.core.btries.*; import gplx.core.net.*;
import gplx.xowa.mws.parsers.prepros.*; import gplx.xowa.mws.parsers.headings.*;
import gplx.xowa.mws.parsers.quotes.*; import gplx.xowa.mws.parsers.tables.*; import gplx.xowa.mws.parsers.hrs.*; import gplx.xowa.mws.parsers.nbsps.*;
import gplx.xowa.mws.parsers.lnkes.*; import gplx.xowa.mws.parsers.lnkis.*; import gplx.xowa.mws.parsers.magiclinks.*; import gplx.xowa.mws.parsers.doubleunders.*;
import gplx.xowa.mws.utls.*; import gplx.xowa.mws.linkers.*;
import gplx.xowa.mws.htmls.*;
public class Xomw_parser {
	private final    Xomw_parser_ctx pctx = new Xomw_parser_ctx();
	private final    Xomw_table_wkr table_wkr;
	private final    Xomw_hr_wkr hr_wkr = new Xomw_hr_wkr();
	private final    Xomw_lnke_wkr lnke_wkr;
	private final    Xomw_nbsp_wkr nbsp_wkr = new Xomw_nbsp_wkr();
	private final    Xomw_block_level_pass block_wkr = new Xomw_block_level_pass();
	private final    Xomw_heading_wkr heading_wkr = new Xomw_heading_wkr();
	private final    Xomw_magiclinks_wkr magiclinks_wkr;
	private final    Xomw_doubleunder_wkr doubleunder_wkr = new Xomw_doubleunder_wkr();
	private final    Xomw_link_renderer link_renderer;
	private final    Xomw_link_holders holders;
	private final    Xomw_heading_cbk__html heading_wkr_cbk;
	private final    Btrie_slim_mgr protocols_trie;
	private final    Xomw_doubleunder_data doubleunder_data = new Xomw_doubleunder_data();
	private static Xomw_regex_space regex_space;
	private static Xomw_regex_boundary regex_boundary;
	private static Xomw_regex_url regex_url;
	private final    Btrie_rv trv = new Btrie_rv();
	private int marker_index = 0;
	// private final    Xomw_prepro_wkr prepro_wkr = new Xomw_prepro_wkr();
	public Xomw_parser_env         Env()             {return env;}            private final    Xomw_parser_env env = new Xomw_parser_env();
	public Xomw_strip_state        Strip_state()     {return strip_state;}    private final    Xomw_strip_state strip_state = new Xomw_strip_state();
	public Xomw_sanitizer          Sanitizer()       {return sanitizer;}      private final    Xomw_sanitizer sanitizer = new Xomw_sanitizer();
	public Xomw_linker             Linker()          {return linker;}         private final    Xomw_linker linker;
	public Bry_bfr                 Tmp()             {return tmp;}            private final    Bry_bfr tmp = Bry_bfr_.New();
	public Xomw_quote_wkr          Quote_wkr()       {return quote_wkr;}      private final    Xomw_quote_wkr quote_wkr;
	public Xomw_lnki_wkr           Lnki_wkr()        {return lnki_wkr;}       private final    Xomw_lnki_wkr lnki_wkr;
	public boolean                 Output_type__wiki() {return output_type__wiki;} private final    boolean output_type__wiki = false;
	public Xomw_parser() {
		if (regex_space == null) {
			synchronized (Type_adp_.ClassOf_obj(this)) {
				regex_space = new Xomw_regex_space();
				regex_boundary = new Xomw_regex_boundary(regex_space);
				regex_url = new Xomw_regex_url(regex_space);
				Atr__rel = Bry_.new_a7("rel");
				Get_external_link_rel = Bry_.new_a7("nofollow");
			}
		}

		this.link_renderer = new Xomw_link_renderer(sanitizer);
		this.linker = new Xomw_linker(link_renderer);
		this.protocols_trie = Xomw_parser.Protocols__dflt();
		this.holders = new Xomw_link_holders(link_renderer, tmp);
		this.table_wkr = new Xomw_table_wkr(this);
		this.quote_wkr = new Xomw_quote_wkr(this);
		this.lnke_wkr = new Xomw_lnke_wkr(this);
		this.lnki_wkr = new Xomw_lnki_wkr(this, holders, link_renderer, protocols_trie);
		this.heading_wkr_cbk = new Xomw_heading_cbk__html();
		this.magiclinks_wkr = new Xomw_magiclinks_wkr(this, sanitizer, linker, regex_boundary, regex_url);
	}
	public void Init_by_wiki(Xowe_wiki wiki) {
		linker.Init_by_wiki(env, wiki.Lang().Lnki_trail_mgr().Trie());
		lnke_wkr.Init_by_wiki(protocols_trie, regex_url, regex_space);
		lnki_wkr.Init_by_wiki(env, wiki);
		doubleunder_wkr.Init_by_wiki(doubleunder_data, wiki.Lang());
		magiclinks_wkr.Init_by_wiki();
	}
	public void Init_by_page(Xoa_ttl ttl) {
		pctx.Init_by_page(ttl);
	}
	public void Internal_parse(Xomw_parser_bfr pbfr, byte[] text) {
		pbfr.Init(text);
//			$origText = text;

		// MW.HOOK:ParserBeforeInternalParse

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
		table_wkr.Do_table_stuff(pctx, pbfr);
		hr_wkr.Replace_hrs(pctx, pbfr);

		doubleunder_wkr.Do_double_underscore(pctx, pbfr);   // DONE: DATE:2017-01-27

		heading_wkr.Do_headings(pctx, pbfr, heading_wkr_cbk);
		lnki_wkr.Replace_internal_links(pctx, pbfr);
		quote_wkr.Do_all_quotes(pctx, pbfr);
		lnke_wkr.Replace_external_links(pctx, pbfr);

		// replaceInternalLinks may sometimes leave behind
		// absolute URLs, which have to be masked to hide them from replaceExternalLinks
		Xomw_parser_bfr_.Replace(pbfr, Bry__marker__noparse, Bry_.Empty);
		magiclinks_wkr.Do_magic_links(pctx, pbfr);

//			$text = $this->formatHeadings($text, $origText, $isMain);
	}

	public void Internal_parse_half_parsed(Xomw_parser_bfr pbfr, boolean is_main, boolean line_start) {
		strip_state.Unstrip_general(pbfr);

		// MW.HOOK:ParserAfterUnstrip

		// Clean up special characters, only run once, next-to-last before doBlockLevels
		nbsp_wkr.Do_nbsp(pctx, pbfr);

		block_wkr.Do_block_levels(pctx, pbfr, line_start);

		lnki_wkr.Replace_link_holders(pctx, pbfr);

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

		strip_state.Unstrip_nowiki(pbfr);

		// MW.HOOK:ParserBeforeTidy

//			$text = $this->replaceTransparentTags( $text );
		strip_state.Unstrip_general(pbfr);

		sanitizer.Normalize_char_references(pbfr);

//			if ( MWTidy::isEnabled() ) {
//				if ( $this->mOptions->getTidy() ) {
//					$text = MWTidy::tidy( $text );
//				}
//			}
//			else {
			// attempt to sanitize at least some nesting problems
			// (T4702 and quite a few others)
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

		// MW.HOOK:ParserAfterTidy
	}
	public byte[] Armor_links(Bry_bfr trg, byte[] src, int src_bgn, int src_end) {
		// PORTED:preg_replace( '/\b((?i)' . $this->mUrlProtocols . ')/', self::MARKER_PREFIX . "NOPARSE$1", $text )
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
				trg.Add_bry_many(Xomw_strip_state.Bry__marker__bgn, Bry__noparse, protocol_bry);
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
	public byte[] Insert_strip_item(byte[] text) {
		tmp.Add_bry_many(Xomw_strip_state.Bry__marker__bgn, Bry__strip_state_item);
		tmp.Add_int_variable(marker_index);
		tmp.Add(Xomw_strip_state.Bry__marker__end);
		byte[] marker = tmp.To_bry_and_clear();
		marker_index++;
		strip_state.Add_general(marker, text);
		return marker;
	}
	public Xomw_atr_mgr Get_external_link_attribs(Xomw_atr_mgr atrs) {
		atrs.Clear();
		byte[] rel = Get_external_link_rel;

		// XO.MW.UNSUPPORTED: XO will assume target is blank; MW will set target of "_blank", "_self", etc. depending on global opt
		// $target = $this->mOptions->getExternalLinkTarget();
		atrs.Add(Atr__rel, rel);
		return atrs;
	}
	// XO.MW.UNSUPPORTED: XO will always assume "nofollow"; MW will return "nofollow" if (a) ns is in ns-exception list or (b) domain is in domain-exception list; 
	// if ($wgNoFollowLinks && !in_array($ns, $wgNoFollowNsExceptions) && !wfMatchesDomainList($url, $wgNoFollowDomainExceptions)
	public byte[] Get_external_link_rel;
	private static byte[] Atr__rel;
	private static final    byte[] Bry__strip_state_item = Bry_.new_a7("-item-"), Bry__noparse = Bry_.new_a7("NOPARSE");
	private static final    byte[] Bry__marker__noparse = Bry_.Add(Xomw_strip_state.Bry__marker__bgn, Bry__noparse);
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
