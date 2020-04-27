/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.mediawiki.includes.linkers;

// MW.SRC:v1.33.1
/**
 * @since 1.27
 */
public interface XomwLinkTarget {

	/**
	 * Get the namespace index.
	 * @since 1.27
	 *
	 * @return int Namespace index
	 */
	public int getNamespace();

	/**
	 * Convenience function to test if it is in the namespace
	 * @since 1.27
	 *
	 * @param int $ns
	 * @return bool
	 */
	public boolean inNamespace(int ns);

	/**
	 * Get the link fragment (i.e. the bit after the #) in text form.
	 * @since 1.27
	 *
	 * @return string link fragment
	 */
	public String getFragment();

	/**
	 * Whether the link target has a fragment
	 * @since 1.27
	 *
	 * @return bool
	 */
	public boolean hasFragment();

	/**
	 * Get the main part with underscores.
	 * @since 1.27
	 *
	 * @return string Main part of the link, with underscores (for use in href attributes)
	 */
	public String getDBkey();

	/**
	 * Returns the link in text form, without namespace prefix or fragment.
	 * This is computed from the DB key by replacing any underscores with spaces.
	 * @since 1.27
	 *
	 * @return string
	 */
	public String getText();

	/**
	 * Creates a new LinkTarget for a different fragment of the same page.
	 * It is expected that the same type of object will be returned, but the
	 * only requirement is that it is a LinkTarget.
	 * @since 1.27
	 *
	 * @param string $fragment The fragment name, or "" for the entire page.
	 *
	 * @return LinkTarget
	 */
	public XomwLinkTarget createFragmentTarget(String fragment);

	/**
	 * Whether this LinkTarget has an interwiki component
	 * @since 1.27
	 *
	 * @return bool
	 */
	public boolean isExternal();

	/**
	 * The interwiki component of this LinkTarget
	 * @since 1.27
	 *
	 * @return string
	 */
	public String getInterwiki();

	/**
	 * Returns an informative human readable representation of the link target,
	 * for use in logging and debugging. There is no requirement for the return
	 * value to have any relationship with the input of TitleParser.
	 * @since 1.31
	 *
	 * @return string
	 */
	public String __toString();

}
