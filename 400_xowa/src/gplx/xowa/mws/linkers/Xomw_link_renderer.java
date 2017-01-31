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
package gplx.xowa.mws.linkers; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
import gplx.langs.htmls.*;
import gplx.xowa.mws.htmls.*;
/*	TODO.XO
	* titleFormatter->gePrefixedTex
	* $html = HtmlArmor::getHtml($text);
	* Get_link_url
	* Normalise_special_page
	* Merge_attribs
	* Get_link_classes
*/
public class Xomw_link_renderer {
	private boolean expand_urls = false;
	private final    Xomw_html_utl html_utl = new Xomw_html_utl();
	private final    Xomw_atr_mgr attribs = new Xomw_atr_mgr();

	// XO.MW:SYNC:1.29; DATE:2017-01-31
	public void Make_link(Bry_bfr bfr, Xoa_ttl target, byte[] text, byte[] classes, Xomw_atr_mgr extra_atrs, Xomw_qry_mgr query) {
		if (target.Is_known()) {
			this.Make_known_link(bfr, target, text, extra_atrs, query);
		} else {
			this.Make_broken_link(bfr, target, text, extra_atrs, query);
		}
	}

	// If you have already looked up the proper CSS classes using LinkRenderer::getLinkClasses()
	// or some other method, use this to avoid looking it up again.
	// XO.MW:SYNC:1.29; DATE:2017-01-31
	public void Make_preloaded_link(Bry_bfr bfr, Xoa_ttl target, byte[] text, byte[] classes, Xomw_atr_mgr extra_atrs, Xomw_qry_mgr query) {
		// XO.MW.HOOK: $this->runBeginHook --> 'HtmlPageLinkRendererBegin', 'LinkBegin'

		target = Normalize_target(target);
		byte[] url = Get_link_url(target, query);
		attribs.Clear();
		attribs.Add(Gfh_atr_.Bry__href, url);	// XO.MW: add url 1st; MW does attribs["url", url] + attribs + extra_attribs
		if (classes.length > 0)                 // XO.MW:do not bother adding if empty
			attribs.Add(Gfh_atr_.Bry__class, classes);
		byte[] prefixed_text = target.Get_prefixed_text();
		if (prefixed_text != Bry_.Empty) {
			attribs.Add(Gfh_atr_.Bry__title, prefixed_text);
		}

		attribs.Merge(extra_atrs);

		if (text == null) {
			text = this.Get_link_text(target);
		}

		Build_a_element(bfr, target,text, attribs, true);
	}
	
	// XO.MW:SYNC:1.29; DATE:2017-01-31
	public void Make_known_link(Bry_bfr bfr, Xoa_ttl target, byte[] text, Xomw_atr_mgr extra_atrs, Xomw_qry_mgr query) {
		byte[] classes = Bry_.Empty;
		if (target.Is_external()) {
			classes = Bry__classes__extiw;
		}
		byte[] colour = Get_link_classes(target);
		if (colour != Bry_.Empty) {
			classes = Bry_.Add(classes, Byte_ascii.Space_bry, colour);
		}

		Make_preloaded_link(bfr, target, text, classes, extra_atrs, query);
	}
	// XO.MW:SYNC:1.29; DATE:2017-01-31
	public void Make_broken_link(Bry_bfr bfr, Xoa_ttl target, byte[] text, Xomw_atr_mgr extra_atrs, Xomw_qry_mgr query) {
		// XO.MW.HOOK: Run legacy hook

		// We don't want to include fragments for broken links, because they
		// generally make no sense.
		if (target.Has_fragment()) {
			target =  target.Create_fragment_target();
		}
		target = Normalize_target(target);

		if (query.action == null && target.Ns().Id() != gplx.xowa.wikis.nss.Xow_ns_.Tid__special) {
			query.action = Bry_.new_a7("edit");
			query.redlink = 1;
		}

		byte[] url = Get_link_url(target, query);
		attribs.Clear();
		attribs.Add(Gfh_atr_.Bry__href, url); // $attribs = ['href' => $url,] + $this->mergeAttribs($attribs, $extraAttribs);
		attribs.Add(Gfh_atr_.Bry__class, Bry_.new_a7("new"));
		attribs.Merge(extra_atrs);

//			$prefixedText = $this->titleFormatter->getPrefixedText($target);
//			if ($prefixedText !== '') {
//				// This ends up in parser cache!
//				$attribs['title'] = wfMessage('red-link-title', $prefixedText)
//					->inContentLanguage()
//					->text();
//			}

		if (text == null) {
			text = Get_link_text(target);
		}

		Build_a_element(bfr, target, text, attribs, false);
	}
	// XO.MW:SYNC:1.29; DATE:2017-01-31
	private void Build_a_element(Bry_bfr bfr, Xoa_ttl target, byte[] text, Xomw_atr_mgr attribs, boolean is_known) {
		// XO.MW.HOOK:HtmlPageLinkRendererEnd

		byte[] html = text;
//			$html = HtmlArmor::getHtml($text);

		// XO.MW.HOOK:LinkEnd

		html_utl.Raw_element(bfr, Gfh_tag_.Bry__a, attribs, html);
	}
	// XO.MW:SYNC:1.29; DATE:2017-01-31
	private byte[] Get_link_text(Xoa_ttl target) {
		byte[] prefixed_text = target.Get_prefixed_text();
		// If the target is just a fragment, with no title, we return the fragment
		// text.  Otherwise, we return the title text itself.
		if (prefixed_text == Bry_.Empty && target.Has_fragment()) {
			return target.Get_fragment();
		}
		return prefixed_text;
	}
	private byte[] Get_link_url(Xoa_ttl target, Xomw_qry_mgr query) {
		// TODO: Use a LinkTargetResolver service instead of Title

//			if ($this->forceArticlePath) {
//				$realQuery = $query;
//				$query = [];
//			}
//			else {
//				$realQuery = [];
//			}
		byte[] url = target.Get_link_url(query, false, expand_urls);

//			if ($this->forceArticlePath && $realQuery) {
//				$url = wfAppendQuery($url, $realQuery);
//			}
		return url;
	}
	// XO.MW:SYNC:1.29; DATE:2017-01-31
	private Xoa_ttl Normalize_target(Xoa_ttl target) {
		return Xomw_linker.Normalise_special_page(target);
	}
//		private function mergeAttribs( $defaults, $attribs ) {
//			if ( !$attribs ) {
//				return $defaults;
//			}
//			// Merge the custom attribs with the default ones, and iterate
//			// over that, deleting all "false" attributes.
//			$ret = [];
//			$merged = Sanitizer::mergeAttributes( $defaults, $attribs );
//			foreach ( $merged as $key => $val ) {
//				# A false value suppresses the attribute
//				if ( $val !== false ) {
//					$ret[$key] = $val;
//				}
//			}
//			return $ret;
//		}
	public byte[] Get_link_classes(Xoa_ttl target) {
		// Make sure the target is in the cache
//			$id = $this->linkCache->addLinkObj($target);
//			if ($id == 0) {
//				// Doesn't exist
//				return '';
//			}

//			if ($this->linkCache->getGoodLinkFieldObj($target, 'redirect')) {
			// Page is a redirect
//				return 'mw-redirect';
//			}
//			elseif ($this->stubThreshold > 0 && MWNamespace::isContent($target->getNamespace())
//				&& $this->linkCache->getGoodLinkFieldObj($target, 'length') < $this->stubThreshold
//			) {
			// Page is a stub
//				return 'stub';
//			}

		return Bry_.Empty;
	}
	private static final    byte[] Bry__classes__extiw = Bry_.new_a7("extiw");
}
