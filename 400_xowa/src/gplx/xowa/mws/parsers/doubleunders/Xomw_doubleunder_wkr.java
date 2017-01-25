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
package gplx.xowa.mws.parsers.doubleunders; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*; import gplx.xowa.mws.parsers.*;
class Xomw_doubleunder_wkr {
	public boolean show_toc;
	public boolean force_toc_position;
	public boolean output__no_gallery ;
	public Xomw_doubleunder_data doubleunderscore_data = new Xomw_doubleunder_data();
	private void Match_and_remove(byte[] text, Xomw_doubleunder_data doubleunderscore_data) {
		doubleunderscore_data.Reset();
	}
	public void Do_double_underscore(byte[] text) {
		// The position of __TOC__ needs to be recorded
//			$mw = MagicWord::get( 'toc' );
//			if ( $mw->match( $text ) ) {
			this.show_toc = true;
			this.force_toc_position = true;

			// Set a placeholder. At the end we'll fill it in with the TOC.
//				$text = $mw->replace( '<!--MWTOC-->', $text, 1 );

			// Only keep the first one.
//				$text = $mw->replace( '', $text );
//			}

		// Now match and remove the rest of them
//			$mwa = MagicWord::getDoubleUnderscoreArray();
		Match_and_remove(text, doubleunderscore_data);

		if (doubleunderscore_data.no_gallery) {
			output__no_gallery = true;
		}
		if (doubleunderscore_data.no_toc && !force_toc_position) {
			this.show_toc = false;
		}
		if (	doubleunderscore_data.hidden_cat
			// &&	$this->mTitle->getNamespace() == NS_CATEGORY
		) {
			//$this->addTrackingCategory( 'hidden-category-category' );
		}
		// (T10068) Allow control over whether robots index a page.
		// __INDEX__ always overrides __NOINDEX__, see T16899
		if (doubleunderscore_data.no_index // && $this->mTitle->canUseNoindex()
			) {
			// $this->mOutput->setIndexPolicy( 'noindex' );
			// $this->addTrackingCategory( 'noindex-category' );
		}
		if (doubleunderscore_data.index //&& $this->mTitle->canUseNoindex()
			) {
			// $this->mOutput->setIndexPolicy( 'index' );
			// $this->addTrackingCategory( 'index-category' );
		}

		// Cache all double underscores in the database
		// foreach ( $this->mDoubleUnderscores as $key => $val ) {
		//   $this->mOutput->setProperty( $key, '' );
		// }
	}
}
class Xomw_doubleunder_data {
	public boolean no_gallery;
	public boolean no_toc;
	public boolean hidden_cat;
	public boolean no_index;
	public boolean index;
	public void Reset() {
		no_gallery = no_toc = hidden_cat = no_index = index = false;
	}
}
