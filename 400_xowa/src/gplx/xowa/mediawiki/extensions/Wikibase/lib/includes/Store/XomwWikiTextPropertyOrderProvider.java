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
package gplx.xowa.mediawiki.extensions.Wikibase.lib.includes.Store;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.mediawiki.*;
import gplx.langs.regxs.*;
// REF.WBASE:2020-01-19
/**
* Base cl+ass for PropertyOrderProviders, that parse the property order from a
* wikitext page.
*
* @license GPL-2.0-or-later
* @author Lucie-Aim�e Kaffee
* @author Marius Hoch
*/
abstract class XomwWikiTextPropertyOrderProvider implements XomwPropertyOrderProvider {

	/**
	* @see parent::getPropertyOrder()
	* @return null|int[] null if page doesn't exist
	* @throws PropertyOrderProviderException
	*/
	public XophpArray getPropertyOrder() {
		String pageContent = this.getPropertyOrderWikitext();
		if (pageContent == null) {
			return null;
		}
		XophpArray parsedList = this.parseList(pageContent);

		return XophpArray.array_flip(parsedList);
	}

	/**
	* Get the wikitext of the property order list.
	*
	* @return String|null
	* @throws PropertyOrderProviderException
	*/
	abstract protected String getPropertyOrderWikitext();

	/**
	* @param String pageContent
	*
	* @return String[]
	*/
	private XophpArray parseList(String pageContent) {
		pageContent = XophpRegex_.preg_replace(parseList_replace_regx, StringUtl.Empty, pageContent);

		XophpArray orderedPropertiesMatches = XophpArray.New();
		XophpRegex_.preg_match_all(
			parseList_match_regx,
			pageContent,
			orderedPropertiesMatches,
			XophpRegex_.PREG_PATTERN_ORDER
		);

		XophpArray orderedProperties = XophpArray.array_map(XophpString_.Callback_owner, "strtoupper", (XophpArray)orderedPropertiesMatches.Get_at_ary(1));

		return orderedProperties;
	}

	private static final Regx_adp
	  parseList_replace_regx = XophpRegex_.Pattern
	  (  "<!--.*?-->", XophpRegex_.MODIFIER_s)
	, parseList_match_regx = XophpRegex_.Pattern
	  //'@^\*\h*(?:\[\[(?:d:)?Property:)?(P\d+\b)@im'
	  (  "^\\*\\h*(?:\\[\\[(?:d:)?Property:)?(P\\d+\\b)", XophpRegex_.MODIFIER_i | XophpRegex_.MODIFIER_m) 
	;
}
