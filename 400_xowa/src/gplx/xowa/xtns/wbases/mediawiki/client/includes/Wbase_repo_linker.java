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
package gplx.xowa.xtns.wbases.mediawiki.client.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.mediawiki.*; import gplx.xowa.xtns.wbases.mediawiki.client.*;
import gplx.xowa.mediawiki.*;
// https://github.com/wikimedia/mediawiki-extensions-Wikibase/blob/master/client/includes/RepoLinker.php
public class Wbase_repo_linker {
	private byte[] baseUrl;
	private byte[] articlePath;
//		private byte[] scriptPath;
	public Wbase_repo_linker(byte[] baseUrl, byte[] articlePath, byte[] scriptPath) {
		this.baseUrl = Bry_.Trim(baseUrl, 0, baseUrl.length, false, true, Bry_.mask_(256, Byte_ascii.Slash_bry)); // getBaseUrl
		this.articlePath = articlePath;
//			this.scriptPath = scriptPath;
	}

	public byte[] getPageUrl(byte[] page) {
		byte[] encodedPage = this.encodePage(page);
		return Bry_.Add(this.getBaseUrl(), XophpString.str_replace(Format_Arg1, encodedPage, this.articlePath));
	}

	private byte[] encodePage(byte[] page) {
		return gplx.langs.htmls.encoders.Gfo_url_encoder_.Mw_wfUrlencode.Encode(Bry_.Replace(page, Byte_ascii.Space, Byte_ascii.Underline));
	}

//		/**
//		* Format a link, with url encoding
//		*
//		* @param String $url
//		* @param String $text
//		* @param array $attribs
//		*
//		* @return String (html)
//		*/
//		public function formatLink( $url, $text, array $attribs = [] ) {
//			$attribs['class'] = isset( $attribs['class'] )
//				? 'extiw ' . $attribs['class']
//				: 'extiw';
//			$attribs['href'] = $url;
//			return Html::element( 'a', $attribs, $text );
//		}
//		/**
//		* Constructs an html link to an entity
//		*
//		* @param EntityId $entityId
//		* @param array $classes
//		* @param String $text Defaults to the entity id serialization.
//		*
//		* @return String (html)
//		*/
//		public function buildEntityLink( EntityId $entityId, array $classes = [], $text = null ) {
//			if ( $text === null ) {
//				$text = $entityId->getSerialization();
//			}
//			$class = 'wb-entity-link';
//			if ( $classes !== [] ) {
//				$class .= ' ' . implode( ' ', $classes );
//			}
//			return $this->formatLink(
//				$this->getEntityUrl( $entityId ),
//				$text,
//				[ 'class' => $class ]
//			);
//		}

	public byte[] getEntityTitle(byte[] entityId ) {
		byte[] title = entityId; // title = entityId.getSerialization();
		return Bry_.Add(Special_EntityPage, title);
	}

	/**
	* Constructs a link to an entity
	*/
	public byte[] getEntityUrl(byte[] entityId) {
		byte[] title = this.getEntityTitle(entityId);
		return this.getPageUrl(title);
	}

	/**
	* @return String
	*/
	public byte[] getBaseUrl() {
		// return rtrim( $this->baseUrl, '/' );
		return this.baseUrl;
	}

//		/**
//		* @return String
//		*/
//		public function getApiUrl() {
//			return $this->getBaseUrl() . $this->scriptPath . '/api.php';
//		}
//		/**
//		* @return String
//		*/
//		public function getIndexUrl() {
//			return $this->getBaseUrl() . $this->scriptPath . '/index.php';
//		}
//		/**
//		* @param String $url
//		* @param array $params
//		*
//		* @return String
//		*/
//		public function addQueryParams( $url, array $params ) {
//			return wfAppendQuery( $url, wfArrayToCgi( $params ) );
//		}

	private static final    byte[] 
	  Format_Arg1        = Bry_.new_a7("$1")
	, Special_EntityPage = Bry_.new_a7("Special:EntityPage/");
}
