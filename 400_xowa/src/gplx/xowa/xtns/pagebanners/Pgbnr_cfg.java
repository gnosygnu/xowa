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
package gplx.xowa.xtns.pagebanners; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.parsers.*;
class Pgbnr_cfg {
	public byte[] Default_file() {return null;}
	public boolean Enabled_in_ns(int ns_id) {return false;}
}
class Pgbnr_icon {
	public byte[] Name;
	public byte[] Title;
	public byte[] Url;
	public Pgbnr_icon(byte[] name, byte[] title, byte[] url) {
		this.Name = name; this.Title = title; this.Url = url;
	}
	public static final Pgbnr_icon[] Ary_empty = new Pgbnr_icon[0];
}
class Pgbnr_itm {
	public byte[] Name;
	public byte[] Tooltip;
	public byte[] Title;
	public boolean Bottomtoc;
	public boolean Toc;
	public Xoa_ttl File_ttl;
	public Pgbnr_icon[] Icons;
	public Pgbnr_itm(byte[] name, byte[] tooltip, byte[] title, boolean bottomtoc, boolean toc, Xoa_ttl file_ttl, Pgbnr_icon[] icons) {
		this.Name = name;
		this.Tooltip = tooltip; this.Title = title; this.Bottomtoc = bottomtoc; this.Toc = toc; this.File_ttl = file_ttl; this.Icons = icons;
	}
//		public static void Do(Bry_bfr bfr, Xop_ctx ctx, Pgbnr_cfg cfg, Pgbnr_itm itm) {
//			if (itm != null) {
//				byte[] name = itm.Name;
////				if ( isset( $params['icons'] ) ){
////					$out->enableOOUI();
////					$params['icons'] = self::expandIconTemplateOptions( $params['icons'] );
////				}
//				// $banner = $wpbFunctionsClass::getBannerHtml( $bannername, $params );
//			}
//			else {}
//		}
	public static void Get_banner_html(Bry_bfr bfr, byte[] banner_name, Pgbnr_itm itm, Pgbnr_cfg cfg) {
		byte[][] urls = Bry_.Ary(Bry_.Empty);  // $urls = static::getStandardSizeUrls( $bannername );
		if (urls.length == 0) return;
	}
//	public static function getBannerHtml( $bannername, $options = array() ) {
//			// @var int index variable
//			$i = 0;
//			foreach ( $urls as $url ) {
//				$size = $config->get( 'WPBStandardSizes' );
//				$size = $size[$i];
//				// add url with width and a comma if not adding the last url
//				if ( $i < count( $urls ) ) {
//					$srcset[] = "$url {$size}w";
//				}
//				$i++;
//			}
//			// create full src set from individual urls, separated by comma
//			$srcset = implode( ',', $srcset );
//			// use largest image url as src attribute
//			$bannerurl = $urls[count( $urls ) - 1];
//			$bannerfile = Title::newFromText( "File:$bannername" );
//			$templateParser = new TemplateParser( __DIR__ . '/../templates' );
//			$options['bannerfile'] = $bannerfile->getLocalUrl();
//			$options['banner'] = $bannerurl;
//			$options['srcset'] = $srcset;
//			$file = wfFindFile( $bannerfile );
//			$options['maxWidth'] = $file->getWidth();
//			$options['isHeadingOverrideEnabled'] = $config->get( 'WPBEnableHeadingOverride' );
//			$banner = $templateParser->processTemplate(
//					'banner',
//					$options
//				);
//	}

//	public static function addBanner( OutputPage $out, Skin $skin ) {
//		$config = WikidataPageBannerFunctions::getWPBConfig();
//		$title = $out->getTitle();
//		$isDiff = $out->getRequest()->getVal( 'diff' );
//		$wpbFunctionsClass = self::$wpbFunctionsClass;
//
//		// if banner-options are set and not a diff page, add banner anyway
//		if ( $out->getProperty( 'wpb-banner-options' ) !== null && !$isDiff ) {
//			$params = $out->getProperty( 'wpb-banner-options' );
//			$bannername = $params['name'];
//			$banner = $wpbFunctionsClass::getBannerHtml( $bannername, $params );
//			// attempt to get WikidataBanner
//			if ( $banner === null ) {
//				$bannername = $wpbFunctionsClass::getWikidataBanner( $title );
//				$banner = $wpbFunctionsClass::getBannerHtml( $bannername, $params );
//			}
//			// only add banner and styling if valid banner generated
//			if ( $banner !== null ) {
//				if ( isset( $params['toc'] ) ) {
//					$out->addModuleStyles( 'ext.WikidataPageBanner.toc.styles' );
//				}
//				$wpbFunctionsClass::insertBannerIntoOutputPage( $out, $banner );
//
//				// FIXME: This is currently only needed to support testing
//				$out->setProperty( 'articlebanner-name', $bannername );
//			}
//		}
//		// if the page uses no 'PAGEBANNER' invocation and if article page, insert default banner
//		elseif (
//			$title->isKnown() &&
//			$out->isArticle() &&
//			$config->get( 'WPBEnableDefaultBanner' ) &&
//			!$isDiff
//		) {
//			$ns = $title->getNamespace();
//			// banner only on specified namespaces, and not Main Page of wiki
//			if ( in_array( $ns, $config->get( 'WPBNamespaces' ) )
//				&& !$title->isMainPage() ) {
//				// first try to obtain bannername from Wikidata
//				$bannername = $wpbFunctionsClass::getWikidataBanner( $title );
//				if ( $bannername === null ) {
//					// if Wikidata banner not found, set bannername to default banner
//					$bannername = $config->get( 'WPBImage' );
//				}
//				// add title to template parameters
//				$paramsForBannerTemplate = array( 'title' => $title );
//				$banner = $wpbFunctionsClass::
//					getBannerHtml( $bannername, $paramsForBannerTemplate );
//				// only add banner and styling if valid banner generated
//				if ( $banner !== null ) {
//					$wpbFunctionsClass::insertBannerIntoOutputPage( $out, $banner );
//
//					// set articlebanner property on OutputPage
//					// FIXME: This is currently only needed to support testing
//					$out->setProperty( 'articlebanner-name', $bannername );
//				}
//			}
//		}
//		return true;
//	}
}
