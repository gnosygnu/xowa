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
public class Xomw_link_renderer {
	private boolean expand_urls = false;
	private final    Xomw_html_utl html_utl = new Xomw_html_utl();
	private final    Xomwh_atr_mgr attribs = new Xomwh_atr_mgr();

	// If you have already looked up the proper CSS classes using LinkRenderer::getLinkClasses()
	// or some other method, use this to avoid looking it up again.
	public void Make_preloaded_link(Bry_bfr bfr, Xoa_ttl target, byte[] text, byte[] classes, Xomwh_atr_mgr extra_atrs, byte[] query) {
		// IGNORE: $this->runBeginHook --> 'HtmlPageLinkRendererBegin', 'LinkBegin'

		// $target = $this->normalizeTarget( $target );	// normalizeSpecialPage
		byte[] url = Get_link_url(target, query);
		attribs.Clear();
		attribs.Add(Gfh_atr_.Bry__href, url);	// NOTE: add url 1st; MW does attribs["url", url] + attribs + extra_attribs
		if (classes.length > 0)	// XO:do not bother adding if empty
			attribs.Add(Gfh_atr_.Bry__class, classes);
		byte[] prefixed_text = target.Get_prefixed_text();
		if (prefixed_text != Bry_.Empty) {
			attribs.Add(Gfh_atr_.Bry__title, prefixed_text);
		}

		int extra_atrs_len = extra_atrs.Len();
		for (int i = 0; i < extra_atrs_len; i++) {
			attribs.Add_or_set(extra_atrs.Get_at(i));
		}

		if (text == null) {
			text = this.Get_link_text(target);
		}

		Build_a_element(bfr, target,text, attribs, true);
	}
	private void Build_a_element(Bry_bfr bfr, Xoa_ttl target, byte[] text, Xomwh_atr_mgr attribs, boolean is_known) {
		// IGNORE: if ( !Hooks::run( 'HtmlPageLinkRendererEnd',

		byte[] html = text;
		// $html = HtmlArmor::getHtml( $text );

		// IGNORE: if ( Hooks::isRegistered( 'LinkEnd' ) ) {

		html_utl.Raw_element(bfr, Gfh_tag_.Bry__a, attribs, html);
	}
	private byte[] Get_link_url(Xoa_ttl target, byte[] query) {
		// TODO: Use a LinkTargetResolver service instead of Title
		// if ( $this->forceArticlePath ) {
		//	$realQuery = $query;
		//	$query = [];
		// }
		// else {
		//	$realQuery = [];
		// }
		byte[] url = target.Get_link_url(query, false, expand_urls);

		// if ( $this->forceArticlePath && $realQuery ) {
		//	$url = wfAppendQuery( $url, $realQuery );
		// }
		return url;
	}
	private byte[] Get_link_text(Xoa_ttl target) {
		byte[] prefixed_text = target.Get_prefixed_text();
		// If the target is just a fragment, with no title, we return the fragment
		// text.  Otherwise, we return the title text itself.
		if (prefixed_text == Bry_.Empty && target.Has_fragment()) {
			return target.Get_fragment();
		}
		return prefixed_text;
	}
//		private function normalizeTarget( LinkTarget $target ) {
//			return Linker::normaliseSpecialPage( $target );
//		}
//		public static function normaliseSpecialPage( LinkTarget $target ) {
//			if ( $target->getNamespace() == NS_SPECIAL && !$target->isExternal() ) {
//				list( $name, $subpage ) = SpecialPageFactory::resolveAlias( $target->getDBkey() );
//				if ( !$name ) {
//					return $target;
//				}
//				$ret = SpecialPage::getTitleValueFor( $name, $subpage, $target->getFragment() );
//				return $ret;
//			} else {
//				return $target;
//			}
//		}
	private static final    byte[] Bry__classes__extiw = Bry_.new_a7("extiw");
	public void Make_known_link(Bry_bfr bfr, Xoa_ttl target, byte[] text, Xomwh_atr_mgr extra_atrs, byte[] query) {
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
	public byte[] Get_link_classes(Xoa_ttl target) {
		// Make sure the target is in the cache
//			$id = $this->linkCache->addLinkObj( $target );
//			if ( $id == 0 ) {
//				// Doesn't exist
//				return '';
//			}

//			if ( $this->linkCache->getGoodLinkFieldObj( $target, 'redirect' ) ) {
			// Page is a redirect
//				return 'mw-redirect';
//			}
//			elseif ( $this->stubThreshold > 0 && MWNamespace::isContent( $target->getNamespace() )
//				&& $this->linkCache->getGoodLinkFieldObj( $target, 'length' ) < $this->stubThreshold
//			) {
			// Page is a stub
//				return 'stub';
//			}

		return Bry_.Empty;
	}
}
