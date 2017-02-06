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
package gplx.xowa.mws.media; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
// XO.MW:MW has registry and instance cache; XO only has instance
// XO.MW:SYNC:1.29; DATE:2017-02-05
public class Xomw_MediaHandlerFactory {
	private final    Hash_adp_bry handlers = Hash_adp_bry.cs();

	// XO.MW:SYNC:1.29; DATE:2017-02-05
	public Xomw_MediaHandlerFactory() {
		// Default, MediaWiki core media handlers
//			'image/jpeg' => JpegHandler::class,
		handlers.Add(Mime__image__png, new Xomw_ImageHandler(Mime__image__png));	// PngHandler
//			'image/gif' => GIFHandler::class,
//			'image/tiff' => TiffHandler::class,
//			'image/webp' => WebPHandler::class,
//			'image/x-ms-bmp' => BmpHandler::class,
//			'image/x-bmp' => BmpHandler::class,
//			'image/x-xcf' => XCFHandler::class,
//			'image/svg+xml' => SvgHandler::class, // official
//			'image/svg' => SvgHandler::class, // compat
//			'image/vnd.djvu' => DjVuHandler::class, // official
//			'image/x.djvu' => DjVuHandler::class, // compat
//			'image/x-djvu' => DjVuHandler::class, // compat

	}

	// XO.MW:SYNC:1.29; DATE:2017-02-05
	public Xomw_MediaHandler getHandler(byte[] type) {
		return (Xomw_MediaHandler)handlers.Get_by(type);
	}

	public static byte[]
	  Mime__image__jpeg                    = Bry_.new_a7("image/jpeg")
	, Mime__image__png                     = Bry_.new_a7("image/png")
	, Mime__image__gif                     = Bry_.new_a7("image/gif")
	, Mime__image__tiff                    = Bry_.new_a7("image/tiff")
	, Mime__image__webp                    = Bry_.new_a7("image/webp")
	, Mime__image__x_ms_bmp                = Bry_.new_a7("image/x-ms-bmp")
	, Mime__image__x_bmp                   = Bry_.new_a7("image/x-bmp")
	, Mime__image__x_xcf                   = Bry_.new_a7("image/x-xcf")
	, Mime__image__svg_xml                 = Bry_.new_a7("image/svg+xml")
	, Mime__image__svg                     = Bry_.new_a7("image/svg")
	, Mime__image__vnd_djvu                = Bry_.new_a7("image/vnd.djvu")
	, Mime__image__x_djvu_dot              = Bry_.new_a7("image/x.djvu")
	, Mime__image__x_djvu_dash             = Bry_.new_a7("image/x-djvu")
	;
}
