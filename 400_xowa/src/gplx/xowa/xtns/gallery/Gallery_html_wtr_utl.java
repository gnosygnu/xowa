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
package gplx.xowa.xtns.gallery; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Gallery_html_wtr_utl {
	public static int Calc_itm_div_len(int v) {return v + 30;}		// 30=Thumb padding; REF.MW:ImageGallery.php|THUMB_PADDING
	public static int Calc_itm_box_w(int v) {return v + 5;}			// 5=Gallery Box padding; REF.MW:ImageGallery.php|GB_PADDING
	public static int Calc_itm_pad_w(int v) {return v + 8;}			// 8=Gallery Box borders; REF.MW:ImageGallery.php|GB_BORDERS
	public static int Calc_vpad(int mgr_itm_height, int html_h) {
		int	min_thumb_height = html_h > 17 ? html_h : 17;						// $minThumbHeight =  $thumb->height > 17 ? $thumb->height : 17;
		return (int)Math_.Floor((30 + mgr_itm_height - min_thumb_height) / 2);	// $vpad = floor(( self::THUMB_PADDING + $this->mHeights - $minThumbHeight ) /2);
	}
}
