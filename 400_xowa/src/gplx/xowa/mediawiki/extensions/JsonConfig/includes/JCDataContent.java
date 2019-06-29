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
package gplx.xowa.mediawiki.extensions.JsonConfig.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.extensions.*; import gplx.xowa.mediawiki.extensions.JsonConfig.*;
import gplx.xowa.mediawiki.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
public class JCDataContent extends JCObjContent { //		/**
//		* Derived classes must implement this method to perform custom validation
//		* using the check(...) calls
//		*/
//		public function validateContent() {
//			if (!$this.thorough()) {
//				// We are not doing any modifications to the original, so no need to validate it
//				return;
//			}
//
//			$this.test('license', JCValidators::isStringLine(), self::isValidLicense());
//			$this.testOptional('description', [ 'en' => '' ], JCValidators::isLocalizedString());
//			$this.testOptional('sources', '', JCValidators::isString());
//		}
//
//		/** Returns a validator function to check if the value is a valid String
//		* @return callable
//		*/
//		public static function isValidLicense() {
//			return function (JCValue $v, array $path) {
//				global $wgJsonConfigAllowedLicenses, $wgLang;
//				if (!in_array($v.getValue(), $wgJsonConfigAllowedLicenses, true)) {
//					$v.error('jsonconfig-err-license', $path,
//						$wgLang.commaList($wgJsonConfigAllowedLicenses));
//					return false;
//				}
//				return true;
//			};
//		}

	/**
	* Get data as localized for the given language
	* @param Language $lang
	* @return mixed
	*/
	public XophpStdClass getLocalizedData(Xol_lang_itm lang) {
		if (!this.isValid()) {
			return null;
		}
		XophpStdClass result = new XophpStdClass();
		this.localizeData(result, lang);
		return result;
	}

	/**
	* Resolve @Override any specific localizations, and add it to $result
	* @param Object $result
	* @param Language $lang
	*/
	@gplx.Virtual protected void localizeData(XophpStdClass result, Xol_lang_itm lang) {
		XophpStdClass data = this.getData();
		if (data.Has("description")) {
			result.Set_by_as_str("description", JCUtils.pickLocalizedString(data.Get_by_as_itm("description"), lang));
		}
		XophpStdClass license = this.getLicenseObject();
		if (license != null) {
//				Xol_msg_itm msg = license.Get_by_as_obj("text");
//				String text =  msg.inLanguage($lang).plain();
//				$result.license = (Object)[
//					'code' => $license['code'],
//					'text' => $text,
//					'url' => $license['url'].inLanguage($lang).plain(),
//				];
		}
		if (data.Has("sources")) {
			result.Set_by_as_itm("sources", data.Get_by_as_itm("sources"));
		}
	}
//
//		public function renderDescription( $lang ) {
//			$description = $this->getField( 'description' );
//
//			if ( $description && !$description->error() ) {
//				$description = JCUtils::pickLocalizedString( $description->getValue(), $lang );
//				$html = Html::element( 'p', [ 'class' => 'mw-jsonconfig-description' ], $description );
//			} else {
//				$html = '';
//			}
//
//			return $html;
//		}
//
//		/**
//		* Renders license HTML, including optional "or later version" clause
//		*     <a href="...">Creative Commons 1.0</a>, or later version
//		* @return String
//		*/
//		public function renderLicense() {
//			$license = $this->getLicenseObject();
//			if ( $license ) {
//				$text = Html::element( 'a', [
//					'href' => $license['url']->plain()
//				], $license['text']->plain() );
//
//				$text = wfMessage( 'jsonconfig-license' )->rawParams( $text )->parse();
//
//				$html = Html::rawElement( 'p', [ 'class' => 'mw-jsonconfig-license' ], $text );
//			} else {
//				$html = '';
//			}
//
//			return $html;
//		}

	private XophpStdClass getLicenseObject() {
//			XophpStdClass license = this.getField("license");
//			if ( $license && !$license->error() ) {
//				$code = $license->getValue();
//
//				return [
//					'code' => $code,
//					'text' => wfMessage( 'jsonconfig-license-name-' . $code ),
//					'url' => wfMessage( 'jsonconfig-license-url-' . $code ),
//				];
//			}
		return null;
	}

//		public function renderSources( Parser $parser, Title $title, $revId, ParserOptions $options ) {
//			$sources = $this->getField( 'sources' );
//
//			if ( $sources && !$sources->error() ) {
//				$markup = $sources->getValue();
//				$html = Html::rawElement( 'p', [ 'class' => 'mw-jsonconfig-sources' ],
//					$parser->parse( $markup, $title, $options, true, true, $revId )->getRawText() );
//			} else {
//				$html = '';
//			}
//
//			return $html;
//		}
}
